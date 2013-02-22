package com.github.hoverruan.libr.mobile.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * @author Hover Ruan
 */
public class DownloadJsonTask extends DownloadTask<String> {

    @Override
    protected String readIt(InputStream stream) throws IOException {
        Reader reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[1024];
        StringBuilder builder = new StringBuilder();
        int len;
        while ((len = reader.read(buffer)) > 0) {
            builder.append(buffer, 0, len);
        }
        return builder.toString();
    }
}
