/*
 *  Copyright (c) 2017, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *  WSO2 Inc. licenses this file to you under the Apache License,
 *  Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */

package com.dp.demo.processors.file;

import com.dp.demo.output.OutputHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

/**
 * Processes text files
 */
public class TextFileProcessor extends FileProcessor {

    private static final Logger log = LogManager.getLogger(TextFileProcessor.class);

    private boolean processed = false;
    private final FileInputStream fileInputStream;
    private final OutputHandler outputHandler;
    private long numberOfWords;
    private long numberOfDots;
    private String mostUsedWord;

    public TextFileProcessor(String inputFilePath, String outputFilePath, String fileName, OutputHandler out)
            throws FileNotFoundException {
        this.fileName = fileName;
        this.fileInputStream = new FileInputStream(inputFilePath + File.separator + fileName);
        outputHandler = out;
        this.inputFilePath = inputFilePath;
        this.outputFilePath = outputFilePath;
    }

    @Override
    public void run() {
        try {
            this.process();
        } catch (IOException e) {
            log.error("Error occurred while processing the text file: " + fileName, e);
        }
    }

    @Override
    public synchronized void process() throws IOException {
        long tempNumberOfWords = 0;
        long tempNumberOfDots = 0;
        long tempHighestOccurrence = 0;
        String tempMostOccurredWord = null;
        Map<String, Integer> wordMap = new HashMap<>();
        if (!processed) {
            Reader reader = new InputStreamReader(fileInputStream);
            int chInt;
            StringBuilder wordBuilder = new StringBuilder();
            String word;
            while ((chInt = reader.read()) != -1) {
                char ch = (char) chInt;
                //count dots
                if (ch == '.') {
                    tempNumberOfDots++;
                }

                //count words and find the most occurred word
                if (Character.isLetterOrDigit(ch) || ch == '-' || ch == '_') {
                    wordBuilder.append(ch);
                } else {
                    word = wordBuilder.toString();
                    wordBuilder.setLength(0);
                    Integer count = wordMap.get(word);
                    if (count == null) {
                        count = 0;
                    }
                    if (!word.isEmpty()) {
                        wordMap.put(word, count + 1);
                        tempNumberOfWords++;
                        if (count + 1 > tempHighestOccurrence) {
                            tempHighestOccurrence = count + 1;
                            tempMostOccurredWord = word;
                        }
                    }
                }
            }

            numberOfWords = tempNumberOfWords;
            numberOfDots = tempNumberOfDots;
            mostUsedWord = tempMostOccurredWord;
            
            processed = true;
            outputHandler.write("File:" + fileName + "    Words:" + numberOfWords + "    Dots:" + numberOfDots +
                    "    MostUsedWord:" + mostUsedWord + "(count=" + tempHighestOccurrence + ")");
            complete();
        }
    }

    @Override
    public FileType getType() {
        return FileType.TXT;
    }

    @Override
    public synchronized long getNumberOfWords() throws IOException {
        if (!processed) {
            process();
        }
        return numberOfWords;
    }

    @Override
    public synchronized long getNumberOfDots() throws IOException {
        if (!processed) {
            process();
        }
        return numberOfDots;
    }

    @Override
    public synchronized String getMostUsedWord() throws IOException {
        if (!processed) {
            process();
        }
        return mostUsedWord;
    }

}

