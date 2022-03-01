package com.dp.demo.processors;

import java.io.IOException;

/**
 * Processes data in a datasource
 */
public interface DataProcessor extends Runnable {

    /**
     * Processes the data in a given datasource
     *
     * @throws IOException
     */
    public void process() throws IOException;

    /**
     * Completes the processing of the given datasource
     *
     * @throws IOException
     */
    public void complete() throws IOException;

}
