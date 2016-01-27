package RMI;

import org.apache.commons.io.FileUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Jsoup;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by mk0stka on 22.12.15.
 */
public class Loader {


    private String releaseTag;
    //private List<String> rarFiles;

    Uploaded_net uploaded_net;


    public Loader(String releaseTag) {
        this.releaseTag = releaseTag;
        //this.rarFiles = new ArrayList<>();

        try {
            this.uploaded_net = new Uploaded_net();
        } catch (Exception e) {
            System.err.println("Uploaded err.");

        }


    }

    public String getReleaseTag() {
        return this.releaseTag;
    }

    public void setDownloadList(List<String> rarFiles) {
        //this.rarFiles = rarFiles;
    }

    public List<String> getFileList(String uploadedID, String releaseTag) {

        Uploaded_net uploaded_net = null;
        try {
            uploaded_net = new Uploaded_net();
            uploaded_net.login();
            return uploaded_net.getLinks(uploadedID, releaseTag);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public void deleteAbuseFiles(List<String> abuseFiles) {

        try {
            Uploaded_net uploaded = new Uploaded_net();
            uploaded.login();

            uploaded.deleteAbuseFile(abuseFiles);

        } catch (Exception e) {
            System.out.println("err. uploaded " + e);
            deleteAbuseFiles(abuseFiles);

        }
    }

    public void editCOntainerRelinkUs(List<String> newFilesUpload, String releaseTag) throws Exception{

        List<NameValuePair> apiPara = new ArrayList<>();
        RequestResponse_Apache_Interface requestResponse = new RequestResponse_Apache_Implementation();
        JSON_Handler jsonHandler = new JSON_Handler();

        StringBuilder stringBuilder = new StringBuilder();
        for (String url : newFilesUpload) {
            if (stringBuilder.length() == 0) {
                stringBuilder.append(url);
            } else {
                stringBuilder.append(";" + url);
            }
        }

        apiPara.add(new BasicNameValuePair("api", "580f7cce2c34b4a618635115d4b927d6f56e98d4"));
        apiPara.add(new BasicNameValuePair("container_id", jsonHandler.getRelinkUsID(releaseTag)));
        apiPara.add(new BasicNameValuePair("url", stringBuilder.toString()));

        System.out.println(Jsoup.parse(requestResponse.sendPostPara("http://api.relink.us/container_edit.php", apiPara)));
    }


    //~~> FileList-Iterator-Downloader - fertig
    public void downloadRelease(List<String> rarFiles) throws Exception  {

        Uploaded_net uploaded_net = new Uploaded_net();

        uploaded_net.login();
        int counter = 0;

        for (String file : rarFiles) {
            if (counter < 10) {

                DownloadInfo downloadInfo = uploaded_net.getDirectLink(file);
                while (downloadInfo == null) {
                    uploaded_net = new Uploaded_net();
                    uploaded_net.login();
                    downloadInfo = uploaded_net.getDirectLink(file);
                    counter = 0;
                }
                //TimeUnit.SECONDS.sleep(2);
                try {
                    streamDownload(downloadInfo.getDownloadlink(), downloadInfo.getFileName(), downloadInfo.getContent_length());


                } catch (Exception e) {
                    System.err.println("hier nur ein Fehler");

                }
                counter++;

            } else {

                DownloadInfo downloadInfo = uploaded_net.getDirectLink(file);
                while (downloadInfo == null) {
                    uploaded_net = new Uploaded_net();
                    uploaded_net.login();
                    downloadInfo = uploaded_net.getDirectLink(file);
                    counter = 0;
                }
                //TimeUnit.SECONDS.sleep(1);
                try {
                    streamDownload(downloadInfo.getDownloadlink(), downloadInfo.getFileName(), downloadInfo.getContent_length());


                } catch (Exception e) {
                    System.err.println("hier nur ein Fehler 2");

                }
                counter = 0;
                uploaded_net = new Uploaded_net();
                uploaded_net.login();
            }
        }
    }

    public void downloadReleaseApache(List<String> rarFiles) throws Exception{

        Uploaded_net uploaded_net = new Uploaded_net();
        uploaded_net.login();

        int counter = 0;

        for (String file : rarFiles) {
            if (counter < 10) {

                DownloadInfo downloadInfo = uploaded_net.getDirectLink(file);
                while (downloadInfo == null) {
                    uploaded_net = new Uploaded_net();
                    uploaded_net.login();
                    downloadInfo = uploaded_net.getDirectLink(file);
                    counter = 0;
                }

                URL url = new URL(downloadInfo.getDownloadlink());
                File link = new File(downloadInfo.getFileName());
                System.out.println(link.getName() + "downloaded");

                FileUtils.copyURLToFile(url, link);
                counter++;

            } else {

                DownloadInfo downloadInfo = uploaded_net.getDirectLink(file);
                while (downloadInfo == null) {
                    uploaded_net = new Uploaded_net();
                    uploaded_net.login();
                    downloadInfo = uploaded_net.getDirectLink(file);
                    counter = 0;
                }
                //TimeUnit.SECONDS.sleep(1);

                URL url = new URL(downloadInfo.getDownloadlink());
                File link = new File(downloadInfo.getFileName());
                System.out.println(link.getName() + "downloaded");

                FileUtils.copyURLToFile(url, link);
                counter++;

                counter = 0;
                uploaded_net = new Uploaded_net();
                uploaded_net.login();
            }
        }

    }

    //~~> FileList-Iterator-Uploader - fertig
    public void uploadRelease(List<String> rarFiles, String dictionary) throws Exception {

        //~~> login Server
        String server = "ftp.uploaded.net";
        int port = 21;
        FTPClient ftpClient = new FTPClient();
        ftpClient.connect(server, port);
        ftpClient.login("4421222", "070300kostka");

        String dictionaryTemp = "";


        if (dictionary.length() > 32) {
            dictionaryTemp = dictionary.substring(0, 32);
        } else {
            dictionaryTemp = dictionary;
        }





        if (ftpClient.makeDirectory(dictionaryTemp)) {
            System.out.println("directory created");
        } else {
            System.out.println("exist directory");
        }

        if (ftpClient.changeWorkingDirectory(dictionaryTemp)) {
            System.out.println("change to workingdictionary " + dictionaryTemp);
        }


        boolean success = ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
        if (!success) {
            //throw new FTPException("Could not set binary file type.");
            System.out.println("fail");
            uploadRelease(rarFiles, dictionary);
        }
        if (ftpClient.isConnected()) {
            System.out.println("is Connetded");

        } else {
            System.out.println("not connected");

        }

        Collections.sort(rarFiles);
        List<String> tempBack = rarFiles;
        for (String filename : rarFiles) {


            InputStream inputStream = new FileInputStream(filename);
            //streamUpload(ftpClient, filename);
            ftpClient.storeFile(filename, inputStream);
            inputStream.close();
            System.out.println(filename + " uploaded");
        }


        ftpClient.logout();
        ftpClient.disconnect();

    }



    public boolean streamUpload(FTPClient ftpClient, String fileName) throws Exception {

        File file = new File(fileName);

        byte[] buffer = new byte[4096];
        float totalByteRead = 0;
        int bytesRead = -1;
        int percentCompleted = 0;
        long filesize = file.length();
        InputStream inputStream = new FileInputStream(file);
        if (inputStream == null) {
            System.out.println("inputstream null");
        }

        System.out.println(fileName);
        OutputStream outputStream;
        while ((outputStream = ftpClient.storeFileStream(fileName)) == null) {
            System.out.println("new try");

        }
        while ((bytesRead = inputStream.read(buffer)) != -1) {

            outputStream.write(buffer, 0, bytesRead);
            totalByteRead += bytesRead;

            percentCompleted = (int) (((float) (totalByteRead / filesize)) * 100);
            printProgressBar(percentCompleted);

        }


        System.out.println(ftpClient.getReplyCode());
        inputStream.close();
        outputStream.close();

        if (!ftpClient.completePendingCommand()) {
            System.err.println("upload error");
        }
        return true;

    }

    //~~> Download file - fertig
    private void streamDownload(String urlStr, String fileName, long contentLength) throws Exception{

        URL url = new URL(urlStr);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(url.openStream());
        FileOutputStream fileOutputStream = new FileOutputStream(fileName);

        byte[] buffer = new byte[1024];
        float totalByteRead = 0;
        int bytesRead = -1;
        int percentCompleted = 0;
        long fileSize = contentLength;
        System.out.println(fileName);
        while((bytesRead = bufferedInputStream.read(buffer,0,1024)) != -1)
        {
            fileOutputStream.write(buffer, 0, bytesRead);
            totalByteRead += bytesRead;
            percentCompleted = (int) (((float) (totalByteRead / fileSize)) * 100);
            printProgressBar(percentCompleted);


        }
        System.out.println("fertig");
        fileOutputStream.close();
        bufferedInputStream.close();

    }


    //~~> Command ausführen - fertig
    public String executeCommand(String command) {

        StringBuffer outputBuffer = new StringBuffer();
        Process process;

        try {
            process = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = "";

            while ((line = reader.readLine()) != null) {
                System.out.println(line);

                outputBuffer.append(line + "\n");
            }
            process.waitFor();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return outputBuffer.toString();

    }


    //~~> ProgressBar - fertig
    public void printProgressBar(int percent) {

        StringBuilder progressBar = new StringBuilder("[");
        for(int i = 0; i < 100; i++) {
            if (i < percent) {
                progressBar.append("=");
            } else if (i == percent) {
                progressBar.append(">");
            } else {
                progressBar.append(" ");
            }
        }
        progressBar.append("]" + percent + " % ");
        System.out.print("\r" + progressBar.toString());

    }



}
