package com.company;

import java.io.*;
import java.net.*;
import java.util.*;

public class DownloadTask implements Runnable {

    // Private fields
    private String fileName;
    private String filePath;
    private String fileDownloadUrl;
    private URL url;
    private URLConnection connection;

    // Public properties
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void setFileDownloadUrl(String fileDownloadUrl) {
        this.fileDownloadUrl = fileDownloadUrl;
    }


    // Class default constructor
    public DownloadTask() {
    }

    @Override
    public synchronized void run() {
        try {
            // Create URL, connection and File to download data
            try{
                url = new URL(fileDownloadUrl);
                connection = url.openConnection();
                connection.connect();
            }
            catch (ConnectException e){
                System.out.println("Узел " +  fileDownloadUrl +  " недоступен: проверьте подключение к сети...");
                System.out.println(e.getMessage());
                System.exit(1);
            }
            catch (UnknownHostException e){
                System.out.println("Узел " +  fileDownloadUrl +  " неизвестен...");
                System.out.println(e.getMessage());
                System.exit(1);
            }

            File file = new File(filePath + "/" + fileName);
            file.createNewFile();

            // Get input stream from URL
            InputStream inputStream = new BufferedInputStream(connection.getInputStream());
            // Set an output stream
            OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(filePath + "/" + fileName));
            // Copy input stream to byte[] buffer and then to output stream
            byte[] buffer = new byte[65656];
            int numRead;
            System.out.println("Загружается файл " + fileName);
            long startTime = System.currentTimeMillis();// Download start timestamp
            while(-1 != (numRead = inputStream.read(buffer))){
                outputStream.write(buffer, 0, numRead);
            }
            outputStream.close();
            long finishTime = System.currentTimeMillis() - startTime; // Download finish timestamp

            // Bringing finish Download time time
            String finishTimeOuput = DataFormatter.formatDownloadTimeOutput(finishTime, true);
            // Download time in ms
            long downloadTime = DataFormatter.timeStampToMillisec(finishTime);
            // Average download speed in kB/s
            double averageDownloadSpeed = (file.length() / 1024) / ((finishTime) * 0.001);
            // Output download results to console
            System.out.println("Файл " + fileName + " загружен: " + DataFormatter.formatFilesSizeOutput(file.length()) + " за " + finishTimeOuput);

            ThreadsDataCollector.setAverageSpeedSum(averageDownloadSpeed);
            ThreadsDataCollector.setDownloadedFilesCount(1);
            ThreadsDataCollector.setTotalDownloadTime(downloadTime);
            ThreadsDataCollector.setTotalFilesSize(file.length());

            FileCopier.copyFile(fileDownloadUrl,filePath,fileName);

        }
        catch (MalformedURLException e) {
            System.out.println("Неверный формат URL!" + e.getMessage());

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
