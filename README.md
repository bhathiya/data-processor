# Console Data Processor

## Prerequisite
- JDK 11

## Steps

1) Clone the repo and build it. 

    `mvn clean install`

2) Go inside `target` directory.

    `cd target`

3) Run the application with below command. 

    `java -jar data-processor-1.0.0-full.jar <Input directory path>`

    `eg. java -jar data-processor-1.0.0-full.jar /data/files/input`

    The output will be like this.

    ```
    [17:53:59.581] INFO  File:rrr.txt    Words:6    Dots:0    MostUsedWord:this(count=1)
    [17:53:59.581] INFO  File:xyz.txt    Words:12    Dots:0    MostUsedWord:we(count=4)
    [17:53:59.581] INFO  File:nnn2.txt    Words:23    Dots:0    MostUsedWord:sdc(count=5)
    [17:53:59.581] INFO  File:jj.txt    Words:5    Dots:0    MostUsedWord:wef(count=4)
    [17:53:59.581] INFO  File:uuu6.txt    Words:23    Dots:0    MostUsedWord:sdc(count=5)
    [17:53:59.581] INFO  File:new.txt    Words:66    Dots:12    MostUsedWord:one(count=12)
    [17:54:19.582] INFO  New file (rrr.txt) detected. Processing...
    [17:54:19.582] INFO  File:rrr.txt    Words:6    Dots:0    MostUsedWord:this(count=1)
    [17:54:29.581] INFO  New file (uuu2.txt) detected. Processing...
    [17:54:29.582] INFO  File:uuu2.txt    Words:23    Dots:0    MostUsedWord:sdc(count=5)
    [17:54:59.581] INFO  New file (abc.pdf) detected. Processing...
    [17:54:59.581] ERROR com.dp.demo.exception.UnsupportedFileType: Unsupported file type: pdf
    ```
    
## Notes

- A "word" consists of characters in the below set. 

        {Alphanumeric, Hyphen, Underscore}
        
- If there are multiple words with the same highest occurrence, the very first word will be returned as the most used word.

## Design

![UML Class Diagram](https://github.com/bhathiya/data-processor/blob/main/images/data-processor-uml-class-diagram.png)

This program has 4 main components. 

### Data Source

This represents a data source. Eg. Local file system

### Input Listener

This continuously observes a given data source and picks up new data points. Eg. Watcher that observes the local file system for new files

### Data Processor

This processes data in a given data point. Eg. This reads the content in a given file and does calculations

### Output Handler

This writes data processor output to a given destination.

In this application, LocalFileDataSource, which is a Data Source, represents a local file system directory. A LocalFileSystemListener, which is an Input Listener, reads the aforementioned LocalFileDataSource and picks up files in it. Then it creates threads of TextFileProcessor which is a Data Processor and hands those files to those threads. In addition to that, the LocalFileSystemListener also keeps monitoring the data source for new files and follows the same approach for those files as well. Each TextFileProcessor reads the given file and gets an InputStream from it. Then it goes through the InputStream and does all calculations in a single iteration. Once the processing is done, it passes the outputs to the LogOutputHandler which is an Output Handler. It logs the given text to a log file.

## Tests

This contains integration tests that process a given set of text files and expect a given set of outputs.
   
