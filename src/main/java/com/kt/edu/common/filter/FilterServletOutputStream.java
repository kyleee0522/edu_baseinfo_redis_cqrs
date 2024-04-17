package com.kt.edu.common.filter;

import lombok.extern.slf4j.Slf4j;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.WriteListener;

@Slf4j
public class FilterServletOutputStream extends ServletOutputStream {
    private DataOutputStream outputStream;

    public FilterServletOutputStream(OutputStream output) {
        this.outputStream = new DataOutputStream(output);
    }

    @Override
    public void write(int b) throws IOException {
        outputStream.write(b);
    }

    @Override
    public boolean isReady() {
        return true;
    }

    @Override
    public void setWriteListener(WriteListener listener) {
        log.info("WriteListener");
    }
}