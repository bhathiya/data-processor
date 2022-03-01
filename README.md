# Console Data Processor

## Prerequisite
- JDK 11

## Steps

1) Clone the repo and build it. 

    `mvn clean install`.

2) Go inside `target` directory.

    `cd target`

3) Run the application with below command. 

    `java -jar data-processor-1.0.0-full.jar <Input directory path>`

    `eg. java -jar data-processor-1.0.0-full.jar /Users/bhathiya/Downloads/files`

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



