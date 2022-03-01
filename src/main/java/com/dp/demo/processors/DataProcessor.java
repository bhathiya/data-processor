package com.dp.demo.processors;

import java.io.IOException;
import java.io.InputStream;

public interface DataProcessor extends Runnable {

    public void process() throws IOException;

    public void complete() throws IOException;

}
