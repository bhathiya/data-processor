package com.dp.demo.datasources;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class DataSource {

    public abstract DatasourceType getType();

    public abstract String getLocation();

    public abstract List<String> getListOfDataPoints() throws IOException;

    public Map<String, String> getProperties() {
        return new HashMap<>();
    }
}
