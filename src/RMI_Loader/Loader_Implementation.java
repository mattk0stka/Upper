package RMI_Loader;

import RMI.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

/**
 * Created by mk0stka on 05.01.16.
 */
public class Loader_Implementation extends UnicastRemoteObject implements Loader_Interface {


    public Loader_Implementation() throws java.rmi.RemoteException {

    }

    @Override
    public synchronized void startLoader(String releaseTag, List<String> url) throws RemoteException {

        System.out.println("Remote-Programm wird gestartet.");

        Logic logic = new Logic(releaseTag, url);
        logic.start();

    }

    @Override
    public synchronized void startReUpLoader(String[] url, String releaseTag) throws RemoteException {

        System.out.println("Remote-Prgramm ReUp-Loader wird gestartet");

        ReLogic reLogic = new ReLogic(releaseTag, url);
        reLogic.start();

    }

    @Override
    public void startReUpLoaderAlpha(String releaseTag) throws RemoteException {

        try {
            JSON_Handler jsonHandler = new JSON_Handler();
            String uploadedID = jsonHandler.getUploadedID(releaseTag);
            if (uploadedID != null) {
                System.out.println("Release found");

                ReLogicB reLogicB = new ReLogicB(releaseTag, uploadedID);
                reLogicB.start();

            } else {
                System.out.println("release no in the List");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public JSONObject getUpload() throws RemoteException {

        JSONParser parser = new JSONParser();
        try {
            return (JSONObject) parser.parse(new FileReader("upload.json"));
        } catch (Exception e) {
            System.out.println("err. with upload.json");
        }
        return null;
    }

    /*

    ~~> switch to JSON-Handler

     */
    @Override
    public void putNewUpload(String releaseTag, String uploadedID, String uploadedFolder, String relinkID) throws RemoteException {


        JSONParser parser = new JSONParser();
        try {
            JSON_Handler json_handler = new JSON_Handler();
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader("upload.json"));
            JSONArray jsonArray = (JSONArray) jsonObject.get("upload");

            JSONObject tempEntry = new JSONObject();
            tempEntry.put("releaseTag", releaseTag);
            tempEntry.put("uploadedID", uploadedID);
            tempEntry.put("uploadedFolder", uploadedFolder);
            tempEntry.put("relinkID", relinkID);

            if (!json_handler.existUpload(jsonObject, releaseTag)) {
                jsonArray.add(tempEntry);
                try {
                    FileWriter writer = new FileWriter("upload.json");
                    writer.write(jsonObject.toJSONString());
                    writer.flush();
                    writer.close();
                    System.out.println("release written into archive");

                } catch (IOException e) {
                    System.out.println("err. ~~> write upload.json");
                }
            } else {
                System.out.println("err. ~~> upload in archive");
            }

        } catch (Exception e) {
            System.out.println("err. ~~> with jsonHandler");
        }
    }

    @Override
    public void deletefile(List<String> abuse) throws RemoteException {

        Loader loader = new Loader("String");
        loader.deleteAbuseFiles(abuse);

    }

    @Override
    public void setRelinkID(String releaseTag, String relinkID) throws RemoteException {

        try {
            JSON_Handler jsonHandler = new JSON_Handler();
            jsonHandler.setRelinkID(releaseTag, relinkID);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void editUploadedFolder(String releaseTag, String uploadedFolder) throws RemoteException {

        try {
            JSON_Handler jsonHandler = new JSON_Handler();
            jsonHandler.editUploadedFolder(releaseTag, uploadedFolder);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void editUploadedID(String releaseTag, String uploadedID) throws RemoteException {

        try {
            JSON_Handler jsonHandler = new JSON_Handler();
            jsonHandler.editUploadedID(releaseTag, uploadedID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeUploadByID(String relinkID) throws RemoteException {

        try {
            JSON_Handler jsonHandler = new JSON_Handler();
            jsonHandler.removeUploadByUploadedID(relinkID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeUploadByIndey(int i) throws RemoteException {

        try {
            JSON_Handler jsonHandler = new JSON_Handler();
            jsonHandler.removeEntryByIndex(i);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeUploadByReleaseTag(String releaseTag) throws RemoteException {

        try {
            JSON_Handler jsonHandler = new JSON_Handler();
            jsonHandler.removeEntryByReleaseTag(releaseTag);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public FileSystemView getFileSystemView() throws RemoteException {
        return FileSystemView.getFileSystemView();
    }

    @Override
    public boolean existUpload(String releaseTag) throws RemoteException {

        try {
            JSON_Handler jsonHandler = new JSON_Handler();
            return jsonHandler.existUpload(releaseTag);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void notifyRelink(String releaseTag, String uploadedID) throws RemoteException {

        Loader loader = new Loader(releaseTag);
        try {
            loader.editCOntainerRelinkUs(loader.getFileList(uploadedID, releaseTag), releaseTag);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
