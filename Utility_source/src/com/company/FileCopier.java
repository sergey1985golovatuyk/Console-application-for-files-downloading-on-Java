package com.company;

import sun.misc.IOUtils;

import java.io.*;
import java.util.ArrayList;

public class FileCopier {
    /**
     * FileCopier's methods used to:
     * 1) collect duplicated links from List of links file
     * 2) copy downloaded file in same directory in required quantity
     */

    /**
     * Collect duplicated links and corresponding file names to the collection of String arrays
     */
    static ArrayList<String[]> bufferLinksNames = new ArrayList<>(0);

    public static void addArrayToBuffer(String link, String fileName){
        synchronized (FileCopier.class){
            bufferLinksNames.add(new String[]{link, fileName});
        }
    }

    /**
     * Copying downloaded file in same directory in required quantity (number of duplicated links) with corresponding names
     * @param url
     * @param pathName
     * @param fileName
     */
    public static void copyFile(String url, String pathName, String fileName) throws IOException {
        synchronized (FileCopier.class) {
            for (int i = 0; i < bufferLinksNames.size(); i++) {
                if (bufferLinksNames.get(i)[0].equals(url)) {
                    try (InputStream inputStream = new BufferedInputStream(new FileInputStream(new File(pathName + "/" + fileName)))) {
                        byte[] bytes = IOUtils.readFully(inputStream, -1, true);
                        OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(pathName + "/" + bufferLinksNames.get(i)[1]));
                        outputStream.write(bytes);
                        outputStream.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}

