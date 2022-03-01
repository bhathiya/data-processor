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

import com.dp.demo.processors.DataProcessor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * Processes a given file
 */
public abstract class FileProcessor implements DataProcessor {

    protected String inputFilePath;
    protected String outputFilePath;
    protected String fileName;

    public abstract FileType getType();

    public abstract long getNumberOfWords() throws IOException;

    public abstract long getNumberOfDots() throws IOException;

    public abstract String getMostUsedWord() throws IOException;

    @Override
    public void complete() throws IOException {
        Files.createDirectories(Path.of(outputFilePath));
        Files.move(Paths.get(inputFilePath + File.separator + fileName),
                Paths.get(outputFilePath + File.separator + fileName),
                StandardCopyOption.REPLACE_EXISTING);
    }
}
