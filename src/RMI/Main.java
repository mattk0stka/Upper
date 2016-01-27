package RMI;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by mk0stka on 17.12.15.
 */
public class Main {
    public static void main(String[] args) throws Exception {

        List<String> rarFile = new ArrayList<>();
        String releaseTag = "The.Transporter.Refueled.2015.German.DL.1080p.BluRay.x264-ENCOUNTERS";

        String uploaded_url =

                "http://uploaded.net/file/gjhgrxqu/ThTrRe20GeDL10Blx2EN35781.part01.rar\n" +
                        "http://uploaded.net/file/9nuvoffv/ThTrRe20GeDL10Blx2EN35781.part02.rar\n" +
                        "http://uploaded.net/file/nxkm2iry/ThTrRe20GeDL10Blx2EN35781.part03.rar\n" +
                        "http://uploaded.net/file/owdtn51u/ThTrRe20GeDL10Blx2EN35781.part04.rar\n" +
                        "http://uploaded.net/file/8dtrllrx/ThTrRe20GeDL10Blx2EN35781.part05.rar\n" +
                        "http://uploaded.net/file/c3equwcl/ThTrRe20GeDL10Blx2EN35781.part06.rar\n" +
                        "http://uploaded.net/file/r8ki4r4v/ThTrRe20GeDL10Blx2EN35781.part07.rar\n" +
                        "http://uploaded.net/file/gpaay3jv/ThTrRe20GeDL10Blx2EN35781.part08.rar\n" +
                        "http://uploaded.net/file/2uj8gxsu/ThTrRe20GeDL10Blx2EN35781.part09.rar\n" +
                        "http://uploaded.net/file/w1u3kcyw/ThTrRe20GeDL10Blx2EN35781.part10.rar\n" +
                        "http://uploaded.net/file/00fgkomz/ThTrRe20GeDL10Blx2EN35781.part11.rar\n" +
                        "http://uploaded.net/file/ko6g13r2/ThTrRe20GeDL10Blx2EN35781.part12.rar\n" +
                        "http://uploaded.net/file/m161i2se/ThTrRe20GeDL10Blx2EN35781.part13.rar\n" +
                        "http://uploaded.net/file/6e6bhr2k/ThTrRe20GeDL10Blx2EN35781.part14.rar\n" +
                        "http://uploaded.net/file/5abg46y8/ThTrRe20GeDL10Blx2EN35781.part15.rar\n" +
                        "http://uploaded.net/file/uedkfvb9/ThTrRe20GeDL10Blx2EN35781.part16.rar\n" +
                        "http://uploaded.net/file/wpsm57s6/ThTrRe20GeDL10Blx2EN35781.part17.rar\n" +
                        "http://uploaded.net/file/tqapa3le/ThTrRe20GeDL10Blx2EN35781.part18.rar\n" +
                        "http://uploaded.net/file/z2n3b6y2/ThTrRe20GeDL10Blx2EN35781.part19.rar\n" +
                        "http://uploaded.net/file/3fgb65ih/ThTrRe20GeDL10Blx2EN35781.part20.rar\n" +
                        "http://uploaded.net/file/qhgva756/ThTrRe20GeDL10Blx2EN35781.part21.rar\n" +
                        "http://uploaded.net/file/j9kvijag/ThTrRe20GeDL10Blx2EN35781.part22.rar\n" +
                        "http://uploaded.net/file/dgp1iz3k/ThTrRe20GeDL10Blx2EN35781.part23.rar\n" +
                        "http://uploaded.net/file/swpym9a8/ThTrRe20GeDL10Blx2EN35781.part24.rar\n" +
                        "http://uploaded.net/file/wuondgg7/ThTrRe20GeDL10Blx2EN35781.part25.rar\n" +
                        "http://uploaded.net/file/af9o3s56/ThTrRe20GeDL10Blx2EN35781.part26.rar\n" +
                        "http://uploaded.net/file/lsrh5ku5/ThTrRe20GeDL10Blx2EN35781.part27.rar\n" +
                        "http://uploaded.net/file/8bt486d6/ThTrRe20GeDL10Blx2EN35781.part28.rar\n" +
                        "http://uploaded.net/file/9dr37z2b/ThTrRe20GeDL10Blx2EN35781.part29.rar\n" +
                        "http://uploaded.net/file/gf03gg54/ThTrRe20GeDL10Blx2EN35781.part30.rar\n" +
                        "http://uploaded.net/file/rij02r7m/ThTrRe20GeDL10Blx2EN35781.part31.rar\n" +
                        "http://uploaded.net/file/dotagbd6/ThTrRe20GeDL10Blx2EN35781.part32.rar\n" +
                        "http://uploaded.net/file/blyeq58p/ThTrRe20GeDL10Blx2EN35781.part33.rar\n" +
                        "http://uploaded.net/file/ko4nu4et/ThTrRe20GeDL10Blx2EN35781.part34.rar\n" +
                        "http://uploaded.net/file/lprv53cv/ThTrRe20GeDL10Blx2EN35781.part35.rar\n" +
                        "http://uploaded.net/file/2pdz7sj4/ThTrRe20GeDL10Blx2EN35781.part36.rar\n" +
                        "http://uploaded.net/file/9okitseb/ThTrRe20GeDL10Blx2EN35781.part37.rar\n" +
                        "http://uploaded.net/file/q3tgsyrq/ThTrRe20GeDL10Blx2EN35781.part38.rar\n" +
                        "http://uploaded.net/file/dllr9imi/ThTrRe20GeDL10Blx2EN35781.part39.rar\n" +
                        "http://uploaded.net/file/bztxudx4/ThTrRe20GeDL10Blx2EN35781.part40.rar\n" +
                        "http://uploaded.net/file/bnyy6a8u/ThTrRe20GeDL10Blx2EN35781.part41.rar\n" +
                        "http://uploaded.net/file/6to6ck58/ThTrRe20GeDL10Blx2EN35781.part42.rar\n" +
                        "http://uploaded.net/file/2s0qngq1/ThTrRe20GeDL10Blx2EN35781.part43.rar\n" +
                        "http://uploaded.net/file/2sv0lk5g/ThTrRe20GeDL10Blx2EN35781.part44.rar\n" +
                        "http://uploaded.net/file/ahiq2b2z/ThTrRe20GeDL10Blx2EN35781.part45.rar\n" +
                        "http://uploaded.net/file/gj2go8sj/ThTrRe20GeDL10Blx2EN35781.part46.rar";


        String[] uploaded_array = uploaded_url.split("\\s");
        System.out.println(uploaded_array.length + "Parts to download");


        Uploaded_net uploaded = new Uploaded_net();
        uploaded.login();
        int counter = 0;


        for (String link : uploaded_array) {

            if (counter < 5) {

                //String uploaded_link = uploaded.getDirectLink(link);
                //String[] temp = uploaded_link.split("/");
                //downloadUsingStream(uploaded_link, temp[temp.length - 1]);

                DownloadInfo downloadInfo = uploaded.getDirectLink(link);
                String uploaded_link = downloadInfo.getDownloadlink();
                String[] temp = uploaded_link.split("/");
                downloadUsingStream(uploaded_link, temp[temp.length - 1], downloadInfo.getContent_length());


                rarFile.add(temp[temp.length - 1]);
                counter++;
            } else {
                uploaded = new Uploaded_net();
                uploaded.login();

                DownloadInfo downloadInfo = uploaded.getDirectLink(link);
                String uploaded_link = downloadInfo.getDownloadlink();
                String[] temp = uploaded_link.split("/");
                downloadUsingStream(uploaded_link, temp[temp.length - 1], downloadInfo.getContent_length());


                rarFile.add(temp[temp.length - 1]);
                counter = 0;
            }
        }

        System.out.println("Startting sort-algo");

        Collections.sort(rarFile);
        for (String filename : rarFile) {
            System.out.println(filename);

        }

        System.out.println("unrar");
        System.out.println(executeCommand("unrar e " + rarFile.get(0)));

        for (String filename : rarFile) {
            File file = new File(filename);
            if (file.delete()) {
                System.out.println(file.getName() + " deleted");
            } else {
                System.out.println("Delete failed");
            }
        }

        rarFile.clear();


        File f = null;
        File[] paths;

        f = new File(System.getProperty("user.dir"));
        paths = f.listFiles();

        for (File path : paths) {
            String[] temp_Path = path.getPath().split("/");
            if (!temp_Path[temp_Path.length - 1].equals("config.properties") && !temp_Path[temp_Path.length - 1].equals("Upper.jar")) {

                rarFile.add(temp_Path[temp_Path.length - 1]);
            }
        }

        for (String filename : rarFile) {
            System.out.println(filename);

        }

        String dicName = "";
        StringBuilder stringBuilder = new StringBuilder();
        for (String filename : rarFile) {
            String[] tempFilename = filename.split("\\.");
            if (tempFilename[tempFilename.length - 1].equals("mkv") && !filename.contains("sample")) {

                for (int i = 0; i < tempFilename.length - 1; i++) {
                    if (stringBuilder.length() == 0) {
                        stringBuilder.append(tempFilename[i]);
                    } else {
                        stringBuilder.append("." + tempFilename[i]);
                    }
                }
                dicName = stringBuilder.toString();
                System.out.println(executeCommand("mkdir " + releaseTag));
                System.out.println("Folder created.");
            }
        }



        //Unnötige Daten löschen
        for (String filename : rarFile) {
            String[] tempFilename = filename.split("\\.");
            if (tempFilename[tempFilename.length - 1].equals("txt") || tempFilename[tempFilename.length - 1].equals("html") || tempFilename[tempFilename.length - 1].equals("url")) {
                File tempFile = new File(filename);
                System.out.println("Delete file " + tempFile.getName());
                tempFile.delete();

            } else {
                System.out.println(executeCommand("mv " + filename + " " + releaseTag + "/"));
            }

        }

        //Unique unique = new Unique();
        System.out.println(executeCommand("rar a -v128m -m0 " + dicName + " " + releaseTag + "/"));

        //~~> rar fertig


        rarFile.clear();
        f = new File(System.getProperty("user.dir"));
        paths = f.listFiles();

        for (File path : paths) {
            String[] temp_Path = path.getPath().split("/");
            if (!temp_Path[temp_Path.length - 1].equals("config.properties") && !temp_Path[temp_Path.length - 1].equals("Upper.jar") && !temp_Path[temp_Path.length - 1].equals(releaseTag)) {

                rarFile.add(temp_Path[temp_Path.length - 1]);
            }
        }

        System.out.println("rar finished...     " + rarFile.size() + " created");

        System.out.println("start uploading");

        String server = "ftp.uploaded.net";
        int port = 21;
        FTPClient ftpClient = new FTPClient();
        ftpClient.connect(server, port);
        ftpClient.login("4421222", "kostka070300");
        ftpClient.makeDirectory(dicName);

        boolean success = ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
        if (!success) {
            //throw new FTPException("Could not set binary file type.");
            System.out.println("fail");
        }

        Collections.sort(rarFile);
        for (String filename : rarFile) {

            File file = new File(filename);
            byte[] buffer = new byte[4096];
            float totalByteRead = 0;
            int bytesRead = -1;
            int percentCompleted = 0;
            long filesize = file.length();
            InputStream inputStream = new FileInputStream(file);
            OutputStream outputStream = ftpClient.storeFileStream(dicName + "/" + filename);
            System.out.println(filename);
            if (outputStream == null) {
                System.out.println("output ist null");

            }


            //int counter = 0;
            while ((bytesRead = inputStream.read(buffer)) != -1) {

                outputStream.write(buffer, 0, bytesRead);
                totalByteRead += bytesRead;

                percentCompleted = (int) (((float) (totalByteRead / filesize)) * 100);
                printProgBar(percentCompleted);

            }


            System.out.println(ftpClient.getReplyCode());
            inputStream.close();
            outputStream.close();

            if (!ftpClient.completePendingCommand()) {
                System.err.println("upload error");
            }

        }


        ftpClient.logout();
        ftpClient.disconnect();



    }



    private static void downloadUsingStream(String urlStr, String filename, long content_length) throws Exception{
        String[] temp = urlStr.split("/");
        URL url = new URL(urlStr);
        BufferedInputStream bis = new BufferedInputStream(url.openStream());
        FileOutputStream fis = new FileOutputStream(filename);

        byte[] buffer = new byte[1024];
        float totalByteRead = 0;
        int bytesRead = -1;
        int percentCompleted = 0;
        long filesize = content_length;
        System.out.println(filename);
        while((bytesRead = bis.read(buffer,0,1024)) != -1)
        {
            fis.write(buffer, 0, bytesRead);
            totalByteRead += bytesRead;
            percentCompleted = (int) (((float) (totalByteRead / filesize)) * 100);
            //updateProgress(filename,percentCompleted);
            printProgBar(percentCompleted);


        }
        System.out.println("fertig");
        fis.close();
        bis.close();

    }

    static void updateProgress(String fileName, int progressPercentage) {
        final int width = 50; // progress bar width in chars

        System.out.print("\r [");
        int i = 0;
        for (; i <= (int)(progressPercentage); i++) {
            System.out.print(".");
        }
        for (; i < 101; i++) {
            System.out.print(" ");
        }
        System.out.print("] " + progressPercentage + " %");
    }


    private static String executeCommand(String command) {

        StringBuffer output = new StringBuffer();

        Process p;
        try {
            p = Runtime.getRuntime().exec(command);

            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(p.getInputStream()));

            String line = "";
            while ((line = reader.readLine())!= null) {
                System.out.println(line);

                output.append(line + "\n");
            }
            p.waitFor();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return output.toString();

    }

    public static void printProgBar(int percent){
        StringBuilder bar = new StringBuilder("[");

        for(int i = 0; i < 50; i++){
            if( i < (percent/2)){
                bar.append("=");
            }else if( i == (percent/2)){
                bar.append(">");
            }else{
                bar.append(" ");
            }
        }

        bar.append("]   " + percent + "%     ");
        System.out.print("\r" + bar.toString());
    }

}
