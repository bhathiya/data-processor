package com.dp.demo.listeners;

import com.dp.demo.datasources.DataSource;
import com.dp.demo.exception.UnsupportedDatasource;

import java.io.IOException;

/**
 * Listener of a given datasource
 */
public interface InputListener {

    /**
     * Listens to a given datasource and then calls to corresponding data processor
     *
     * @param dataSource Target data source
     * @throws IOException
     * @throws InterruptedException
     * @throws UnsupportedDatasource
     */
    public void listen(DataSource dataSource) throws IOException, InterruptedException, UnsupportedDatasource;

}
