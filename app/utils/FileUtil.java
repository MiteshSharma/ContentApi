package utils;

import org.apache.commons.lang3.RandomStringUtils;

import java.io.File;

/**
 * Created by mitesh on 30/12/16.
 */
public class FileUtil {
    public static String getFileExtension(String fileName) {
        if (fileName.contains(".")) {
            try {
                return fileName.substring(fileName.lastIndexOf(".") + 1);
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    public static String generateUniqueFileName() {
        String filename = "";
        long millis = System.currentTimeMillis();
        String rndchars = RandomStringUtils.randomAlphanumeric(16);
        filename = rndchars + "" + millis;
        return filename;
    }
}
