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
    1. First, determine if the input number is prime. If so, send the same number to the second phase. If not, find the
    next higher prime number and send that to the second phase.
    2. Next, the prime number calculated in the first phase must be recorded in a database. Note that this means the
    database should never contain a number that isn't prime.
    3. Finally, if the original input was prime, increment the number by one. Otherwise, do nothing. This is the result
    which should be sent to the output queue.
- The system must provide near-real-time processing for up to 100 simultaneous inputs. If 100 input messages are sent,
all outputs must be available within 2000 milliseconds. The database is the slow part of the system.
- Output order is irrelevant.

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

In order from most to least preferred, you can submit your solution via:

- pull request
- link to a forked repo
- an emailed zip of your project
