package com.github.hoverruan.libr.mobile.util;

import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Hover Ruan
 */
public class DownloadImageTask extends DownloadTask<File> {
    private String imageType;
    private String imageId;

    public DownloadImageTask(Context context, String imageType, String imageId) {
        super(context);
        this.imageType = imageType;
        this.imageId = imageId;
    }

    public static File calculateImageFile(Context contextParam, String imageTypeParam, String imageIdParam) {
        File imageDir = new File(contextParam.getCacheDir(), CACHE_DIR + imageTypeParam);
        if (!imageDir.isDirectory()) {
            imageDir.mkdirs();
        }

        return new File(imageDir, imageIdParam);
    }

    protected File readIt(InputStream stream) throws IOException {
        File imageFile = calculateImageFile(context, imageType, imageId);
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(imageFile);
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

        return imageFile;
    }
}
