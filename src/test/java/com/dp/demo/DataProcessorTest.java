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

package com.dp.demo;

import com.dp.demo.processors.file.TextFileProcessor;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class DataProcessorTest {

    TextFileProcessor textFileProcessor;
    TestOutputHandler testOutputHandler;
    String inputFilePath = "src/test/resources/in";
    String outputFilePath = "src/test/resources/in/processed/";

    @DataProvider(name = "test-file-provider")
    public Object[][] dpMethod() {
        return new Object[][] {
                {"test1.txt", "File:test1.txt    Words:14    Dots:3    MostUsedWord:this(count=3)"},
                {"test2.txt", "File:test2.txt    Words:85    Dots:1    MostUsedWord:you(count=7)"},
                {"test3.txt", "File:test3.txt    Words:4    Dots:0    MostUsedWord:this-is-a-strange(count=1)"},
                {"test4.txt", "File:test4.txt    Words:119    Dots:14    MostUsedWord:the(count=8)"},
                {"test5.txt", "File:test5.txt    Words:356    Dots:26    MostUsedWord:the(count=15)"},
        };
    }

    @BeforeMethod
    public void setUp() {
        testOutputHandler = new TestOutputHandler();
    }

    @AfterMethod
    public void tearDown() throws IOException {
        final File sourceDir = new File(outputFilePath);
        final File destinationDir = new File(inputFilePath);
        File[] files = sourceDir.listFiles();
        for (File file : files) {
            Path sourcePath = Paths.get(sourceDir.getAbsolutePath() + File.separator + file.getName());
            Path destinationPath = Paths.get(destinationDir.getAbsolutePath() + File.separator + file.getName());
            Files.move(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
        }
    }

    @Test(dataProvider = "test-file-provider")
    public void testProcess(String[] data) throws IOException {
        textFileProcessor = new TextFileProcessor(inputFilePath, outputFilePath, data[0], testOutputHandler);
        textFileProcessor.process();
        Assert.assertEquals(testOutputHandler.getText(), data[1]);
    }
}
