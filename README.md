**TODO**
- Report should have been generated in parseable format (e.g. in csv) in case for future processing needs
- Consider to use one of the common CSV libraries to stick with CSV RFC!
- Consider to use CommandLineRunner to get log file and report file paths as parameters
- ReportEntryGenerator class might not be suitable for hosting future changes/extensions. 
Keeping all the violation logic in single method/class is a quesition mark.

**Questions & Remarks**
- LogRotation: what if a given process END entry written in the subsequent files
- As of now the ERROR report entry has a precedence over WARNING with else if, otherwise
because duration[ERROR]>duration[WARNING] meaning that all ERROR entries can have also a corresponding WARNING entry