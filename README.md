Welcome to the Crucible
=======================

The purpose of this project is to gauge your ability to effectively develop tests for a system based on some stated
requirements. It contains a working system but lacks appropriate tests. Your job is to write the tests that are missing.
Below you'll find the system requirements and guideliness concerning your solution. To jump in and see the system
working, check out the Application class. It has a main method that you can run to see the system in action.

Getting Started
---------------

To get started working on the project, clone the repository or download the zip (link available on the Github page). The
project is built with Gradle. If you don't have Gradle, don't worry: the project contains a script to bootstrap Gradle
for you. Just run `gradlew test` to run all the tests. `gradlew idea` will generate IntelliJ IDEA project files, and
`gradlew eclipse` will generate Eclipse project files.

System Requirements
-------------------

The system under test is a simple version of an enterprise messaging system using JMS that deals with integers as input
and output. The following points describe the required behavior of the system:

- Interaction with the system will be via JMS. Input is provided by sending a message to the queue named "NumberInput",
and output is received by receiving messages from the queue named "NumberOutput".
- Input and output messages will be in the form of a JMS TextMessage. The text of the message is the decimal
representation of the number in question.
- Processing of input proceeds in three phases:
    1. Determine if the input number is prime. If so, send the input number to the second phase. If not, calculate the
    next higher prime number, and send that to the second phase.
    2. The prime number calculated in the first phase must be recorded in a database. Send the same number to the third
    phase.
    3. Using the number from phase two: if the original input was prime, increment the number by one and send it to the
    output queue. If it wasn't prime, send the number to the output queue without incrementing.
- The system must provide near-real-time processing for up to 100 simultaneous inputs. If 100 input messages are sent,
all outputs must be available within 2000 milliseconds. The database is the slow part of the system.
- Due to the processing rules, every number recorded in the database must be prime.
- Output order is irrelevant.

Examples:

1. Send the number "13" to NumberInput. The input is prime, so it passes through phase one unchanged. The number "13" is
recorded in the database. Finally, since the input was prime, it's incremented, and the number "14" is sent to
NumberOutput.
2. Send the number "24" to NumberInput. The input isn't prime, so phase one finds the next higher prime number, which is
"29", and passes that to the next phase. The number "29" is recorded in the database. Finally, since the input wasn't
prime, the number "29" is sent to NumberOutput without being incremented.

Solution Guidelines
-------------------

Your primary goal is to express the system requirements in automated tests. The IntegrationTest class has some basic
tests in it to get you started. This is the class where additional tests should be written that verify the behavior of
the system overall.

You may not remove the artifical delay that's imposed on the database. Otherwise, you may rewrite any or all of the
code at your whim. The provided implementation is only there so that tests can be written and run against it.

In general, you should be improving the testing of the system, not removing tests. If something is labeled a "sanity
test", then that feature probably isn't tested as thoroughly as it needs to be. Add more tests to ensure correctness.

If you find the system to be lacking, you should improve it to meet the stated requirements.

The Gradle build is authoritative, meaning that tests must pass when run with Gradle from the command line.

You can submit your solution by providing a link to a forked repo with your solution.
