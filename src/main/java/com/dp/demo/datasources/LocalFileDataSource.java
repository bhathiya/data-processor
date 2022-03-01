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

package com.dp.demo.datasources;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Datasource represents the local file system
 */
public class LocalFileDataSource extends DataSource {

    private final String location;

    public LocalFileDataSource(String location) {
        this.location = location;
    }

    @Override
    public DatasourceType getType() {
        return DatasourceType.LOCAL_FILE_SYSTEM;
    }

    @Override
    public String getLocation() {
        return location;
    }

    public List<String> getListOfDataPoints() throws IOException {
        try (Stream<Path> stream = Files.walk(Paths.get(getLocation()), 1)) {
            return stream.filter(file -> !Files.isDirectory(file)).map(Path::getFileName)
                    .map(Path::toString).collect(Collectors.toList());
        }
    }

}
