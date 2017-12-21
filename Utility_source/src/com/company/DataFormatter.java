package com.company;
import org.jetbrains.annotations.Contract;
import java.text.DecimalFormat;
import java.util.Calendar;

public class DataFormatter {
    /**
     * DataFormatter's methods used to format Utility.jar operation results in readable view in Russian language,
     * converts timestamp to milliseconds
     */

    //region Formatting text output of download time (common)
    /**
     * Method formats download time output
     */
    public static String formatDownloadTimeOutput(long downloadTime, boolean accusativeСase){
        synchronized (DataFormatter.class){
            String totalDownloadTimeOut = "";

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(downloadTime);

            if(downloadTime < 1000){
                totalDownloadTimeOut = formatMillisecondOutput(calendar.get(Calendar.MILLISECOND),accusativeСase);

            }
            else if(downloadTime >= 1000 && downloadTime < 60000){
                totalDownloadTimeOut = formatSecondOutput(calendar.get(Calendar.SECOND),accusativeСase) + " " +  formatMillisecondOutput(calendar.get(Calendar.MILLISECOND),accusativeСase);
            }

            else if(downloadTime >= 60000 && downloadTime < 3600000){
                totalDownloadTimeOut = formatMinuteOutput(calendar.get(Calendar.MINUTE),accusativeСase) + " " +  formatSecondOutput(calendar.get(Calendar.SECOND),accusativeСase);
            }
            else if(downloadTime >= 3600000 && downloadTime < 86400000){
                totalDownloadTimeOut = formatHourOutput(calendar.get(Calendar.HOUR)) + " " +  formatMinuteOutput(calendar.get(Calendar.MINUTE),accusativeСase);
            }
            else{
                totalDownloadTimeOut = formatDayOutput(calendar.get(Calendar.DAY_OF_YEAR)) + " " + formatHourOutput(calendar.get(Calendar.HOUR));
            }
            return totalDownloadTimeOut;
        }
    }
    //endregion

    //region Formatting text output of download time in milliseconds
    /**
     * Method formats output time in milliseconds
     * @param milliseconds
     * @return
     */
    public  static String formatMillisecondOutput(int milliseconds, boolean accusativeСase) {
        String millisecformatoutput = "";
        if (milliseconds <= 20 || (milliseconds >= 100 && milliseconds <= 120) || (milliseconds >= 200 && milliseconds <= 220) || (milliseconds >= 300 && milliseconds <= 320) || (milliseconds >= 400 && milliseconds <= 420) ||
                (milliseconds >= 500 && milliseconds <= 520) || (milliseconds >= 600 && milliseconds <= 620) || (milliseconds >= 700 && milliseconds <= 720) || (milliseconds >= 800 && milliseconds <= 820) ||
                (milliseconds >= 900 && milliseconds <= 920)) {
            String strMillisec = Integer.valueOf(milliseconds).toString();
            String strMillisecSub = strMillisec.substring(strMillisec.length() - 2, strMillisec.length());
            if (strMillisecSub.length() > 1) {
                switch (strMillisecSub) {
                    case "01":
                        if (accusativeСase)
                            millisecformatoutput = milliseconds + " миллисекунду";
                        else
                            millisecformatoutput = milliseconds + " миллисекунда";
                        break;
                    case "02": case "03": case "04":
                        millisecformatoutput = milliseconds + " миллисекунды";
                        break;
                    case "05": case "06": case "07": case "08": case "09": case "10": case "11": case "12": case "13": case "14": case "15": case "16": case "17": case "18": case "19": case "20":
                        millisecformatoutput = milliseconds + " миллисекунд";
                        break;
                }
            }
            else {
                millisecformatoutput = switchFunc(milliseconds, milliseconds,accusativeСase);
                }
            }
        else{
            int reminder = milliseconds % 10;
            millisecformatoutput = switchFunc(reminder, milliseconds,accusativeСase);
        }
        return millisecformatoutput;
    }

    /**
     * Aux switch function for milliseconds displaying to avoid code duplication
     * @param caseParam
     * @param value
     * @param accusativeСase
     * @return
     */
    @Contract(pure = true)
    private static String switchFunc(int caseParam, int value, boolean accusativeСase) {
        String millisecformatoutput = "";
        switch (caseParam) {
            case 1:
                if (accusativeСase)
                    millisecformatoutput = value + " миллисекунду";
                else
                    millisecformatoutput = value + " миллисекунда";
                break;
            case 2: case 3: case 4:
                millisecformatoutput = value + " миллисекунды";
                break;
            case 5: case 6: case 7: case 8: case 9:
                millisecformatoutput = value + " миллисекунд";
        }
        return millisecformatoutput;
    }
    //endregion

    //region Formatting text output of download time in seconds
    /**
     * Method formats output time in seconds
     * @param seconds
     * @return
     */
    @Contract(pure = true)
    public  static String formatSecondOutput(int seconds, boolean accusativeСase) {
        String secFormatOutput = "";
        if (seconds <= 20) {
            switch (seconds) {
                case 1:
                    if (accusativeСase)
                        secFormatOutput = seconds + " секунду";
                    else
                        secFormatOutput = seconds + " секунда";
                    break;
                case 2: case 3: case 4:
                    secFormatOutput = seconds + " секунды";
                    break;
                case 5: case 6: case 7: case 8: case 9: case 10: case 11: case 12: case 13: case 14: case 15: case 16: case 17: case 18: case 19: case 20:
                    secFormatOutput = seconds + " секунд";
            }
        } else switch (seconds % 10) {
            case 1:
                secFormatOutput = seconds + " секунда";
                break;
            case 2: case 3: case 4:
                secFormatOutput = seconds + " секунды";
                break;
            case 5: case 6: case 7: case 8: case 9:
                secFormatOutput = seconds + " секунд";
                break;
        }
        return secFormatOutput;
    }
    //endregion

    //region Formatting text output of download time in minutes
    /**
     * Method formats output time in minutes
     * @param minutes
     * @param accusativeСase
     * @return
     */
    @Contract(pure = true)
    public  static String formatMinuteOutput(int minutes, boolean accusativeСase){
        String minFormatOutput = "";
        if (minutes <= 20) {
            switch (minutes) {
                case 1:
                    if (accusativeСase)
                        minFormatOutput = minutes + " минуту";
                    else
                        minFormatOutput = minutes + " минута";
                    break;
                case 2: case 3: case 4:
                    minFormatOutput = minutes + " минуты";
                    break;
                case 5: case 6: case 7: case 8: case 9: case 10: case 11: case 12: case 13: case 14: case 15: case 16: case 17: case 18: case 19: case 20:
                    minFormatOutput = minutes + " минут";
            }
        } else switch (minutes % 10) {
            case 1:
                if (accusativeСase)
                    minFormatOutput = minutes + " минуту";
                else
                    minFormatOutput = minutes + " минута";
                break;
            case 2: case 3: case 4:
                minFormatOutput = minutes + " минуты";
                break;
            case 5: case 6: case 7: case 8: case 9:
                minFormatOutput = minutes + " минут";
                break;
        }
        return minFormatOutput;
    }
    //endregion

    //region Formatting text output of download time in hours
    /**
     * Method formats output time in hours
     * @param hours
     * @return
     */
    @Contract(pure = true)
    public  static String formatHourOutput(int hours){
        String hourFormatOutput = "";
        switch (hours){
            case 1: hourFormatOutput = hours + " час";
                break;
            case 2: case 3: case 4:
                hourFormatOutput = hours + " часа";
                break;
            case 5: case 6: case 7: case 8: case 9: case 10: case 11: case 12: case 13: case 14: case 15: case 16: case 17: case 18: case 19: case 20: case 21: case 22: case 23:
                hourFormatOutput = hours + " часов";
        }
        return hourFormatOutput;
    }
    //endregion

    //region Formatting text output of download time in days
    /**
     * Method formats output time in days
     * @param days
     * @return
     */
    @Contract(pure = true)
    public  static String formatDayOutput(int days){
        String daysFormatOutput = "";
        switch (days){
            case 1: daysFormatOutput = days + " день";
                break;
            case 2: case 3: case 4:
                daysFormatOutput = days + " дня";
                break;
            case 5: case 6: case 7: case 8: case 9: case 10: case 11: case 12: case 13: case 14: case 15: case 16: case 17: case 18: case 19: case 20:
                daysFormatOutput = days + " дней";
        }
        return daysFormatOutput;
    }
    //endregion

    //region Formatting text output of downloaded files size
    /**
     * Method formats size of file output
     * @param totalFilesSize
     * @return
     */
    public static  String formatFilesSizeOutput(long totalFilesSize){
        synchronized (DataFormatter.class){
            String totalFilesSizeOutput = "";
            DecimalFormat df = new DecimalFormat("##.00");
            double totalSize = (double)totalFilesSize;

            if(totalFilesSize < 1024){
                totalFilesSizeOutput = totalSize + " байт";
            }
            else if(totalFilesSize >= 1024 && totalFilesSize < 1048576){
                totalFilesSizeOutput = df.format(totalSize/1024) + " килобайт";
            }
            else if(totalFilesSize >= 1048576 && totalFilesSize < 1073741824){
                totalFilesSizeOutput = df.format(totalSize/1048576) + " мегабайт";
            }
            else if(totalFilesSize >=1073741824 && totalFilesSize < 1.099511627776E+12 ){
                totalFilesSizeOutput = df.format(totalSize/1073741824) + " гигабайт";
            }
            else{
                totalFilesSizeOutput = df.format(totalSize/1.099511627776E+12) + " терабайт";
            }
            return totalFilesSizeOutput;
        }
    }
    //endregion

    //region Formatting text output of count of downloaded files
    /**
     * Method formats count of file output
     * @param filesCount
     * @return
     */
    public static String formatFilesCountOutput(int filesCount) {
        synchronized (DataFormatter.class) {
            String filesCountOutput = "";
            if (filesCount <= 20) {
                switch (filesCount) {
                    case 1:
                        filesCountOutput = filesCount + " файл";
                        break;
                    case 2:
                    case 3:
                    case 4:
                        filesCountOutput = filesCount + " файла";
                        break;
                    case 5:
                    case 6:
                    case 7:
                    case 8:
                    case 9:
                    case 10:
                    case 11:
                    case 12:
                    case 13:
                    case 14:
                    case 15:
                    case 16:
                    case 17:
                    case 18:
                    case 19:
                    case 20:
                        filesCountOutput = filesCount + " файлов";
                        break;
                }
            } else {
                switch (filesCount % 10) {
                    case 1:
                        filesCountOutput = filesCount + " файл";
                        break;
                    case 2:
                    case 3:
                    case 4:
                        filesCountOutput = filesCount + " файла";
                        break;
                    case 5:
                    case 6:
                    case 7:
                    case 8:
                    case 9:
                        filesCountOutput = filesCount + " файлов";
                        break;
                }

            }
            return filesCountOutput;
        }
    }
    //endregion

    //region Converting timestamp to milliseconds
    /**
     * Convert timestamp to milliseconds
     */
    public static long timeStampToMillisec(long timeStamp) {
        Calendar resultTime = Calendar.getInstance();
        resultTime.setTimeInMillis(timeStamp);
        return resultTime.getTimeInMillis();

    }
    //endregion
}
