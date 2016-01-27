package RMI;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by mk0stka on 23.12.15.
 */
public class Logic {

    private Loader loader;
    private List<String> rarFile;
    public String releaseTag;

    private List<String> downloadedRarFiles;

    public Logic(String releaseTag, List<String> rarFile) {
        this.rarFile = rarFile;
        this.releaseTag = releaseTag;
        this.loader = new Loader(releaseTag);

        this.downloadedRarFiles = new ArrayList<>();

    }


    public void start() {

        //~~> beginne Download
        System.out.println("start DownUpLoader-programm");



        File avFile =  new File(System.getProperty("user.dir"));
        File[] avFiles = avFile.listFiles();

        List<String> url2download = rarFile;

        for(int i = 0; i< avFiles.length -3; i++) {
            url2download.remove(0);
        }

        System.out.println(url2download.size() + " parts to download");
        System.out.println("start downloading...");


        try {
            //loader.downloadRelease(url2download);
            loader.downloadReleaseApache(url2download);

        } catch (Exception e) {
            e.printStackTrace();
        }

        avFile =  new File(System.getProperty("user.dir"));
        avFiles = avFile.listFiles();

        for (File file : avFiles) {
            if (!file.getName().equals("Upper.jar") && !file.getName().equals("config.properties") && !file.getName().equals("upload.json")) {

                downloadedRarFiles.add(file.getName());
            }

            System.out.println(file.getName() + " ~~> added to list");

        }


        //~~> Liste aller File-Pfade sortieren
        System.out.println("sorting.....");
        Collections.sort(downloadedRarFiles);
        for (String fileName : downloadedRarFiles) {
            System.out.println(fileName);

        }

        //~~> beginnge Entpacken und Files löschen
        System.out.println("start unrar");
        System.out.println(loader.executeCommand("unrar e -o+ " + downloadedRarFiles.get(0)));

        for (String fileName : downloadedRarFiles) {
            File tempFile = new File(fileName);
            if (tempFile.delete()) {
                System.out.println(tempFile.getName() + " deleted");
            } else {
                System.out.println("try to delete " + tempFile.getName() + " .... failed");
            }
        }

        downloadedRarFiles.clear();


        //~~> entpackte Files in Liste einfügen
        File file = new File(System.getProperty("user.dir"));
        File[] paths = file.listFiles();
        for (File path : paths) {
            String[] temp_Path = path.getPath().split("/");
            if (!temp_Path[temp_Path.length - 1].equals("config.properties") && !temp_Path[temp_Path.length - 1].equals("Upper.jar") && !temp_Path[temp_Path.length - 1].equals("upload.json")) {

                downloadedRarFiles.add(temp_Path[temp_Path.length - 1]);
            }
        }

        System.out.println("\n\nunrar files");
        for (String fileName : downloadedRarFiles) {
            System.out.println(fileName);

        }

        //~~> Ordner erstellen
        String dictionary = "";
        StringBuilder stringBuilder = new StringBuilder();
        for (String fileName : downloadedRarFiles) {
            String[] tempFileName = fileName.split("\\.");
            if (tempFileName[tempFileName.length - 1].equals("mkv") && !fileName.contains("sample")) {

                for (int i = 0; i < tempFileName.length - 1; i++) {
                    if (stringBuilder.length() == 0) {
                        stringBuilder.append(tempFileName[i]);
                    } else {
                        stringBuilder.append("." + tempFileName[i]);
                    }
                }
                dictionary = stringBuilder.toString();

                if (dictionary.equals(loader.getReleaseTag())) {
                    System.out.println("mkv ~~> same like folder");
                    String newTag = loader.getReleaseTag().toLowerCase();
                    String[] group = newTag.split("-");
                    newTag = group[group.length - 1] + "-" + group[0].replace(".bluray", "").replace(".x264", "");
                    System.out.println(loader.executeCommand("mv " + dictionary + ".mkv " + newTag + ".mkv"));




                    downloadedRarFiles.set(downloadedRarFiles.indexOf(dictionary + ".mkv"), newTag + ".mkv");
                    dictionary = newTag;

                }
                System.out.println(loader.executeCommand("mkdir " + loader.getReleaseTag()));
                System.out.println(loader.getReleaseTag() + " folder created");
            }
        }



        //~~> unnötige Daten löschen
        for (String fileName : downloadedRarFiles) {
            String[] tempFileName = fileName.split("\\.");
            if (tempFileName[tempFileName.length - 1].equals("txt") || tempFileName[tempFileName.length - 1].equals("html") || tempFileName[tempFileName.length - 1].equals("url") || tempFileName[tempFileName.length - 1].equals("log")) {
                File tempFile = new File(fileName);
                System.out.println("deleted file ~~>    " + tempFile.getName());
                tempFile.delete();
            } else if (fileName.equals(".JAcheck")) {
                File tempFile = new File(fileName);
                System.out.println("deleted file ~~>    " + tempFile.getName());
                tempFile.delete();
            } else {

                System.out.println("move " + fileName + "to " + loader.getReleaseTag());
                String command = "mv " + fileName + " " + loader.getReleaseTag();
                System.out.println(command);
                System.out.println(loader.executeCommand(command));

            }
        }

        //~~> Ordner packen
        Unique unique = new Unique();

        System.out.println("rar a -v128m -m0 " + dictionary + " " + loader.getReleaseTag() + "/");
        System.out.println(loader.executeCommand("rar a -v128m -m0 " + dictionary + " " + loader.getReleaseTag() + "/"));


        //Liste aller Files zum uploaden
        downloadedRarFiles.clear();
        file = new File(System.getProperty("user.dir"));
        paths = file.listFiles();


        System.out.println(loader.getReleaseTag());


        for (File path : paths) {
            String[] temp_Path = path.getPath().split("/");
            if (!temp_Path[temp_Path.length - 1].equals("config.properties") && !temp_Path[temp_Path.length - 1].equals("Upper.jar") && !temp_Path[temp_Path.length - 1].equals(releaseTag) && !temp_Path[temp_Path.length - 1].equals("upload.json")) {

                downloadedRarFiles.add(temp_Path[temp_Path.length - 1]);
            }
        }

        System.out.println("finished rar....");
        Collections.sort(downloadedRarFiles);
        for (String fileName : downloadedRarFiles) {
            System.out.println(fileName);

        }

        //~~>beginnge upload
        System.out.println(downloadedRarFiles.size() + " parts to upload");
        System.out.println("start uploading...");

        try {
            loader.uploadRelease(downloadedRarFiles, dictionary);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("\n\nstarting cleaning-programm... \n");
        downloadedRarFiles.clear();

        file = new File(System.getProperty("user.dir"));
        paths = file.listFiles();


        for (File path : paths) {
            String[] temp_Path = path.getPath().split("/");
            if (!temp_Path[temp_Path.length - 1].equals("config.properties") && !temp_Path[temp_Path.length - 1].equals("Upper.jar") && !temp_Path[temp_Path.length - 1].equals("upload.json")) {

                if (path.isFile()) {
                    System.out.println("deleted file ~~>    " + path.getName());
                    path.delete();
                } else {
                    System.out.println("move dictionary ~~>    " + path.getName() + "/");
                    loader.executeCommand("mv " + path.getName() + "/" + " ..");
                }

            }
        }

        System.out.println("start archive upload");

        try {
            JSON_Handler json_handler = new JSON_Handler();
            if (json_handler.uploadArchive()) {
                String id = json_handler.searchID2Folder(dictionary);
                String relinkID = "n.a";

                if (dictionary.length() > 32) {
                    json_handler.insertNewUpload(releaseTag, relinkID, dictionary.substring(0, 32), id);
                    System.out.println("entry set");
                } else {
                    json_handler.insertNewUpload(releaseTag, relinkID, dictionary, id);
                    System.out.println("entry set");
                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}


