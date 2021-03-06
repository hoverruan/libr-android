package com.github.hoverruan.libr.mobile.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Hover Ruan
 */
public class DownloadImageTask extends DownloadTask<File> {
    private File storeFile;

    public DownloadImageTask(File storeFile) {
        this.storeFile = storeFile;
    }

    protected File readIt(InputStream stream) throws IOException {
        FileOutputStream fileOutputStream = null;
        try {
            File parentDir = storeFile.getParentFile();
            if (!parentDir.exists()) {
                parentDir.mkdirs();
            }
            fileOutputStream = new FileOutputStream(storeFile);
            byte[] buf = new byte[1024];
            int len;

            while ((len = stream.read(buf)) > 0) {
                fileOutputStream.write(buf, 0, len);
            }
        } finally {
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
        }

        return storeFile;
    }
}
