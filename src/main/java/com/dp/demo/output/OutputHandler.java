package com.dp.demo.output;

/**
 * Handles outputs of the data processor
 */
public interface OutputHandler {

    /**
     * Writes the given text to an output
     *
     * @param text Input text
     */
    public void write(String text);

}
