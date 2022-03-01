package com.dp.demo.datasources;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Abstract datasource class
 */
public abstract class DataSource {

    /**
     * Returns the datasource type. @see com.dp.demo.datasources.DatasourceType
     *
     * @return Data source type
     */
    public abstract DatasourceType getType();

    /**
     * Returns the datasource location
     * 
     * @return  datasource location
     */
    public abstract String getLocation();

    /**
     * Returns the list of data points in the data source
     *
     * @return List of data points
     * @throws IOException
     */
    public abstract List<String> getListOfDataPoints() throws IOException;

    /**
     * Returns data source properties
     *
     * @return Map of properties
     */
    public Map<String, String> getProperties() {
        return new HashMap<>();
    }
}
