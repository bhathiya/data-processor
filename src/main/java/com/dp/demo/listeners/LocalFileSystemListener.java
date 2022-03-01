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

package com.dp.demo.listeners;

import com.dp.demo.datasources.DataSource;
import com.dp.demo.datasources.DatasourceType;
import com.dp.demo.exception.UnsupportedDatasource;
import com.dp.demo.exception.UnsupportedFileType;
import com.dp.demo.output.LogOutputHandler;
import com.dp.demo.processors.file.FileType;
import com.dp.demo.processors.file.TextFileProcessor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;

/**
 * This class monitors a specific location of a local file system.
 */
public class LocalFileSystemListener implements InputListener {

    private static final Logger log = LogManager.getLogger(LocalFileSystemListener.class);
    private final String OUTPUT_DIRECTORY_NAME = "processed";

    @Override
    public void listen(DataSource dataSource) throws IOException, InterruptedException, UnsupportedDatasource {

        if (dataSource.getType() != DatasourceType.LOCAL_FILE_SYSTEM) {
            throw new UnsupportedDatasource("Unsupported datasource: " + dataSource.getType().toString());
        }

        if (!Files.isDirectory(Paths.get(dataSource.getLocation()))) {
            throw new UnsupportedDatasource("Invalid directory: " + dataSource.getLocation());
        }

        //read current files
        List<String> fileList = dataSource.getListOfDataPoints();
        for (String fileName : fileList) {
            callFileProcessor(dataSource.getLocation(), fileName);
        }

        //monitor for new files
        WatchService watchService = FileSystems.getDefault().newWatchService();
        Path path = Paths.get(dataSource.getLocation());
        path.register(watchService, ENTRY_CREATE);
        boolean poll = true;
        while (poll) {
            WatchKey key = watchService.take();
            for (WatchEvent<?> event : key.pollEvents()) {
                LogOutputHandler.getInstance().write("New file (" + event.context().toString() +
                        ") detected. Processing...");
                callFileProcessor(path.toString(), event.context().toString());
            }
            poll = key.reset();
        }

    }

    private void callFileProcessor(String filePath, String fileName) throws IOException {
        String extension = com.google.common.io.Files.getFileExtension(fileName);
        if (FileType.TXT.toString().equals(extension.toLowerCase())) {
            TextFileProcessor textFileProcessor = new TextFileProcessor(filePath, filePath
                    + File.separator + OUTPUT_DIRECTORY_NAME, fileName, LogOutputHandler.getInstance());
            //TODO: Use a thread pool
            new Thread(textFileProcessor).start();
        } else {
            if (extension.isEmpty()) {
                extension = "(Empty)";
            }
            log.error(new UnsupportedFileType("Unsupported file type: " + extension));
        }
    }

}
