package com.company;

import java.io.File;
import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.*;

public class MainClass {

    public static void main(String[] args) throws Exception {

        int threadsCount = 0; // Count of threads to be used to download files
        String outputFolder = ""; // Absolute path to directory where files should be downloaded
        String linksFileName = ""; // Absolute path + file's name.extension where links and names of files to be downloaded locate


        // Read input arguments from IntelliJ console
        //region Use this code when debugging
/*        Scanner cin = new Scanner(System.in);
        System.out.println("Введите количество потоков:");
        try{
             threadsCount = Integer.parseInt(cin.nextLine());
        }
        catch(Exception e){
            System.out.println("Неверное значение параметра 1 'количество потоков': введите целочисленное значение больше нуля.");
            System.out.println(e.getMessage());
            System.exit(1);
        }

        System.out.println("Введите путь к папкке с выходными файлами:");
        try{
            outputFolder = cin.nextLine();
            File folder = new File(outputFolder);
            if(!folder.isDirectory())
                throw new Exception("Неверный формат пути для директории хранения скачанных файлов (параметр 2)\n " +
                        "Введите корректный путь");
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            System.exit(1);
        }

        System.out.println("Введите имя файла ссылок:");
        try{
            linksFileName = cin.nextLine();
            File file = new File(linksFileName);
            if(!file.isFile())
                throw new Exception("Неверно указан файл со ссылками для скачивания (параметр 3)\n" +
                        "Введите корректный путь к файлу ссылок");
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            System.exit(1);
        }*/
        //endregion

        // Read input arguments from Command prompt's arguments array
        //region Use this code when assembly program in .jar
        try{
            threadsCount = Integer.parseInt(args[0]); // Quantity of threads
        }
        catch(Exception e){
            System.out.println("Неверное значение параметра 1 'количество потоков': введите целочисленное значение больше нуля.");
            System.out.println(e.getMessage());
            System.exit(1);
        }
        try{
            outputFolder = args[1]; // Path to output folder
            File folder = new File(outputFolder);
            if(!folder.isDirectory())
                throw new Exception("Неверный формат пути для директории хранения скачанных файлов (параметр 2)\n " +
                        "Введите корректный путь");
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            System.exit(1);
        }
        try{
            linksFileName = args[2]; // Name of file with links
            File file = new File(linksFileName);
            if(!file.isFile())
                throw new Exception("Неверно указан файл со ссылками для скачивания (параметр 3)\n" +
                        "Введите корректный путь к файлу ссылок");
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            System.exit(1);
        }
        //endregion


        // Create collection of download links from text file
        ArrayList<String> linksList = null;

        // Get links from file
        linksList = FileReader.fileToStrings(linksFileName);

        // Create arrayList of String[] to store fileUrls and names
        ArrayList<String[]> initLinksArrayList = new ArrayList<>(linksList.size());

        // Split every string in file on download link and filename
        for (int i = 0; i < linksList.size(); i++) {
            initLinksArrayList.add(linksList.get(i).split(" "));
        }

        // Copying all duplicated links to other FileCopier's buffer array and removal them from initialLinksArrayList
        for (int k = 0; k < initLinksArrayList.size(); k++) {
            for (int j = k+1; j < initLinksArrayList.size(); j++) {
                if (initLinksArrayList.get(k)[0].equals(initLinksArrayList.get(j)[0])) {
                    FileCopier.addArrayToBuffer(initLinksArrayList.get(j)[0], initLinksArrayList.get(j)[1]);
                    initLinksArrayList.remove(j);
                    j = j-1;
                }
            }
        }
        // Set number of threads
        threadsCount = (initLinksArrayList.size() <= threadsCount) ? initLinksArrayList.size() : threadsCount;

        // Set Future objects array to be able to get every thread's condition
        Future[] futures = new Future[threadsCount];

        // Create new pool of threads
        ExecutorService threadsPool = Executors.newFixedThreadPool(threadsCount);
        long startTime = System.currentTimeMillis();
// Creation of DownloadTask instance for every unique link in initialLinksArrayList and start every instance in new thread
            for (int k = 0; k < futures.length; k++) {
                DownloadTask downloadTask = new DownloadTask();
                downloadTask.setFileName(initLinksArrayList.get(k)[1]);
                downloadTask.setFilePath(outputFolder);
                downloadTask.setFileDownloadUrl(initLinksArrayList.get(k)[0]);
                Future future = threadsPool.submit(downloadTask);
                futures[k] = future;
            }
        // If number of links >= number of threads then place new DownloadTask instance with following link in list to threadsPool instead already finished task
                if (initLinksArrayList.size() > threadsCount) {
                    int threadsToAdd = initLinksArrayList.size() - threadsCount;
                    while (threadsToAdd > 0) {
                        for (int i = 0; i < futures.length; i++) {
                            if (futures[i].isDone()) {
                                DownloadTask downloadTask = new DownloadTask();
                                downloadTask.setFileName(initLinksArrayList.get((futures.length - 1) + threadsToAdd)[1]);
                                downloadTask.setFilePath(outputFolder);
                                downloadTask.setFileDownloadUrl(initLinksArrayList.get((futures.length - 1) + threadsToAdd)[0]);
                                Future future = threadsPool.submit(downloadTask);
                                futures[i] = future;
                                threadsToAdd--;
                            }
                        }
                    }
                }
// Shutdown request to pool of threads...
            threadsPool.shutdown();
        // ... and await until all threads are finished
            threadsPool.awaitTermination(20, TimeUnit.DAYS);
            if (threadsPool.isShutdown()) {
                long finishTime = System.currentTimeMillis() - startTime;
                // Displaying files download result to command prompt
                System.out.println("Завершено 100%");
                System.out.println("Загружено: " + DataFormatter.formatFilesCountOutput(ThreadsDataCollector.getDownloadedFilesCount()) + ", " + DataFormatter.formatFilesSizeOutput(ThreadsDataCollector.getTotalFilesSize()));
                System.out.println("Суммарное время работы потоков: " + DataFormatter.formatDownloadTimeOutput(ThreadsDataCollector.getDownloadTimeSum(), false));
                System.out.println("Время работы загрузки: " + DataFormatter.formatDownloadTimeOutput(DataFormatter.timeStampToMillisec(finishTime), false));
                System.out.println("Средняя скорость: " + new DecimalFormat("##.00").format(ThreadsDataCollector.getAverageSpeedSum() / ThreadsDataCollector.getDownloadedFilesCount()) + " kB/s");
            }
        }

}
