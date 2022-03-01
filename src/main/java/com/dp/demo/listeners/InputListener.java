package com.dp.demo.listeners;

import com.dp.demo.datasources.DataSource;
import com.dp.demo.exception.UnsupportedDatasource;

import java.io.IOException;

public interface InputListener {

    public void listen(DataSource dataSource) throws IOException, InterruptedException, UnsupportedDatasource;

}
