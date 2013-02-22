package com.github.hoverruan.libr.mobile.util;

import java.io.File;

/**
 * @author Hover Ruan
 */
public class FileUtils {
    public static File newFile(File root, String... paths) {
        File targetFile = root;

        for (String path : paths) {
            targetFile = new File(targetFile, path);
        }

        return targetFile;
    }
}
