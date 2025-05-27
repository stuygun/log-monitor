- To build please run mvn clean install
- Usage: java -jar log-monitor-1.0-SNAPSHOT.jar --logPath=<log.csv> --reportPath=<report.txt>

**Important Note**
- A primitive rule based report entry generator added under ideas package 
but due to the time constraint it hasn't been tested well. So, the solution works based on initial version of the implementation

**TODO**
- Report should have been generated in parseable format (e.g. in csv) in case for future processing needs
- Consider to use one of the common CSV libraries to stick with CSV RFC!

**Questions & Remarks**
- LogRotation: what if a given process END entry written in the subsequent files
- As of now the ERROR report entry has a precedence over WARNING with else if, otherwise
because duration[ERROR]>duration[WARNING] meaning that all ERROR entries can have also a corresponding WARNING entry