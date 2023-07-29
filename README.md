# LP Code Challenge
This repository includes the source code of an application developed as a response to the LP code challenge.

## Prerequisites
* A valid JDK installed (JDK 15+).

## Assumptions and Considerations

* The possible impact of _time_ (in addition to tracking tap ON and tap OFF events) in determining the fares was not considered. 
* The possibility of tackling larger tap data datasets (e.g. ones that can't fit into the memory of a common virtual or otherwise execution environment) was overlooked in favour of _simplicity in the requirements_.
* Concurrent execution of tap data processing was not attempted partly due to the aforementioned assumption, as well as _simplicity in the requirements_.
* In scenarios where a passenger travels between two stops (e.g. Stop 1 and 3) via another stop (e.g. Stop 2) legitimately, it was assumed that s/he would always tap OFF at the transit station, and also tap ON again before getting on the next service to the desired destination.
* As given in the sample dataset highlighted in the code challenge specification, it is acknowledged that there could potentially be tap OFF events without corresponding tap ONs possibly due to system or data errors.

## How to run

[**Note:** Instructions provided here are for Linux and MacOSX.]

**Step 1:** Clone the repository into your preferred directory in your local file system.

`>git clone https://github.com/poohdedoo/lp-code-challenge`

**Step 2:** Change the current working directory to the `PROJECT_HOME` directory (i.e. lp-code-challenge/.).

`>cd lp-code-challenge`

**Step 3:** Run the following command to build the project and generate an executable _jar_ file using gradle.

`>./gradlew jar`

Once done, the executable jar file (i.e. `lp-code-challenge-1.0.0.jar`) could be located in `$PROJECT_HOME/build/lib/` directory.

**Step 4:** Copy the executable jar file to your preferred location in the file system.

`>cp ./build/libs/lp-code-challenge-1.0.0.jar [YOU_PREFERRED_LOCATION]`

**Step 5:** Within the aforementioned directory, create a folder named `input`, and copy the `taps.csv` file found in `$PROJECT_HOME/input` directory into it.

**Step 6:** Finally, run the jar file using the following command.

`>java -jar lp-code-challenge-1.0.0.jar`

**Step 7:** If the application ran successfully, you should be able to see a directory named `output` created within the same working directory, and also a file named `trips.csv` inside it with trip data. 
In addition, you will also observe the following log messages on the command line and in `logs/app.log`.

```
00:28:31.424 [main] INFO  com.littlepay.code.challenge.TripProcessor - Beginning to run the trip data generation application.
00:28:31.461 [main] WARN  com.littlepay.code.challenge.persistence.CSVBasedTripDataPersister - Output directory didn't exist. Therefore, it has been created automatically.
00:28:31.466 [main] INFO  com.littlepay.code.challenge.TripProcessor - Trip data generation application has successfully concluded.
```

**\[Optional] Step 8:** To run and test the application with an extended dataset, use the `taps-extended.csv` file provided in the `$PROJECT_HOME/input` directory. Rename it as `taps.csv` and place it in `$WORK_DIR/input` directory before running the application. The corresponding output file `trips-extended.csv` can be located in `$PROJECT_HOME/output` directory, for reference.

## How to run test cases

Run the following command from within the `$PROJECT_HOME` directory to run the tests.

`>./gradlew test`