package RMI;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

/**
 * Created by mk0stka on 05.01.16.
 */
public class JSON_Handler {

    private Uploaded_net uploaded_net;

    public JSON_Handler() throws Exception {
        this.uploaded_net = new Uploaded_net();
        uploaded_net.login();

    }


    public String searchID2Folder(String folderName) throws Exception {

        JSONObject allFolder = uploaded_net.getFolderParaList("0");
        Object folderObj = allFolder.get("folder");
        JSONArray jsonFolder = (JSONArray) folderObj;
        Iterator<Object> folderIterator = jsonFolder.iterator();

        while (folderIterator.hasNext()) {

            JSONObject title = (JSONObject) folderIterator.next();
            if (title.get("title").equals(folderName)) {
                return title.get("id").toString();
            }
        }

        return null;
    }

    public boolean uploadArchive() throws Exception{
        File files = new File(System.getProperty("user.dir"));
        File[] paths = files.listFiles();

        for (File tempFile : paths) {
            if (tempFile.getName().equals("upload.json")) {
                return true;
            }
        }


        //~~> upload.json-File ohne Einträge
        System.out.println("upload.json not found... start creating archive upload.json");
        FileWriter file = new FileWriter("upload.json");
        JSONArray folder = new JSONArray();
        JSONObject upload = new JSONObject();
        upload.put("upload", folder);
        file.write(upload.toJSONString());
        file.flush();
        file.close();


        paths = files.listFiles();
        for (File tempFile : paths) {
            if (tempFile.getName().equals("upload.json")) {
                return true;
            }
        }

        return false;
    }

    //~~> wird für neues Upload benötigt
    public void insertNewUpload(String releaseTag, String relinkID, String uploadFolderName, String uploadFolderID) throws Exception {

        JSONObject obj = new JSONObject();
        obj.put("releaseTag", releaseTag);
        obj.put("relinkID", relinkID);
        obj.put("uploadedFolder", uploadFolderName);
        obj.put("uploadedID", uploadFolderID);

        JSONParser parser = new JSONParser();
        Object archiveObj = parser.parse(new FileReader("upload.json"));
        JSONObject upload = (JSONObject) archiveObj;
        JSONArray uploadFolder = (JSONArray) upload.get("upload");
        uploadFolder.add(obj);

        FileWriter file = new FileWriter("upload.json");
        file.write(((JSONObject) archiveObj).toJSONString());
        file.flush();
        file.close();

    }

    public boolean existUpload(JSONObject jsonObject, String releaseTag) {

        JSONArray jsonArray = (JSONArray) jsonObject.get("upload");
        Iterator<Object> iterator = jsonArray.iterator();

        while (iterator.hasNext()) {
            JSONObject uploadObj = (JSONObject) iterator.next();

            if (uploadObj.get("releaseTag").toString().equals(releaseTag)) {
                return true;
            }

        }
        return false;
    }

    public boolean existUpload(String releaseTag) {
        JSONParser parser = new JSONParser();

        try {
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader("upload.json"));
            JSONArray jsonArray = (JSONArray) jsonObject.get("upload");

            Iterator<Object> iterator = jsonArray.iterator();

            while (iterator.hasNext()) {
                JSONObject jsonTemp = (JSONObject) iterator.next();

                if (jsonTemp.get("releaseTag").toString().equals(releaseTag)) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void editUploadedFolder(String releaseTag, String uploadedFolder) {

        JSONParser parser = new JSONParser();
        try {
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader("upload.json"));
            JSONArray jsonArray = (JSONArray) jsonObject.get("upload");

            Iterator<Object> iterator = jsonArray.iterator();

            while (iterator.hasNext()) {
                JSONObject jsontemp = (JSONObject) iterator.next();

                if (jsontemp.get("releaseTag").toString().equals(releaseTag)) {
                    System.out.println("found");
                    System.out.println("edit uploadedFolder");
                    jsontemp.remove("uploadedFolder");
                    jsontemp.put("uploadedFolder", uploadedFolder);
                }
            }

            FileWriter file = new FileWriter("upload.json");
            file.write(((JSONObject) jsonObject).toJSONString());
            file.flush();
            file.close();

        } catch (Exception e) {
            System.out.println("err. with upload.json");

        }
    }

    public void editUploadedID(String releaseTag, String uploadedID) {

        JSONParser parser = new JSONParser();
        try {
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader("upload.json"));
            JSONArray jsonArray = (JSONArray) jsonObject.get("upload");

            Iterator<Object> iterator = jsonArray.iterator();

            while (iterator.hasNext()) {
                JSONObject jsonTemp = (JSONObject) iterator.next();
                if (jsonTemp.get("releaseTag").toString().equals(releaseTag)) {
                    System.out.println(releaseTag + "   ~~> found");
                    System.out.println("edit UploadedID");
                    jsonTemp.put("uploadedID", uploadedID);
                }
            }

            FileWriter file = new FileWriter("upload.json");
            file.write(((JSONObject) jsonObject).toJSONString());
            file.flush();
            file.close();

        } catch (Exception e) {
            System.out.println("err. with upload.json");
        }
    }

    public String getUploadedID(String releaseTag) {

        JSONParser parser = new JSONParser();
        try {
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader("upload.json"));
            JSONArray jsonArray = (JSONArray) jsonObject.get("upload");

            Iterator<Object> iterator = jsonArray.iterator();

            while (iterator.hasNext()) {
                JSONObject jsonTemp = (JSONObject) iterator.next();

                if (jsonTemp.get("releaseTag").toString().equals(releaseTag)) {
                    return jsonTemp.get("uploadedID").toString();
                }

            }


        } catch (Exception e) {
            System.out.println("err. with upload.json");
        }
        return null;

    }

    public void removeUploadByUploadedID(String uploadedID) {

        JSONParser parser = new JSONParser();
        try {
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader("upload.json"));
            JSONArray jsonArray = (JSONArray) jsonObject.get("upload");
            for(int i = 0; i < jsonArray.size(); i++) {
                JSONObject upload = (JSONObject) jsonArray.get(i);
                if (upload.get("uploadedID") != null) {
                    if (upload.get("uploadedID").toString().equals(uploadedID)) {
                        System.out.println("found");

                        jsonArray.remove((JSONObject) jsonArray.get(i));
                        break;
                    }
                }
            }

            FileWriter file = new FileWriter("upload.json");
            file.write(((JSONObject) jsonObject).toJSONString());
            file.flush();
            file.close();

        } catch (Exception e) {
            System.out.println("err. with uplado.json");

        }

    }

    public String getRelinkUsID(String releaseTag) {

        JSONParser parser = new JSONParser();

        try {
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader("upload.json"));
            JSONArray jsonArray = (JSONArray) jsonObject.get("upload");

            Iterator<Object> iterator = jsonArray.iterator();

            while (iterator.hasNext()) {
                JSONObject jsontemp = (JSONObject) iterator.next();

                if (jsontemp.get("releaseTag").toString().equals(releaseTag)) {
                    return jsontemp.get("relinkID").toString();
                }
            }
        } catch (Exception e) {
            System.out.println("err. with upload.json");
        }
        return null;
    }

    public boolean existRelinkID(JSONObject jsonObject, String relinkID) {

        JSONArray jsonArray = (JSONArray) jsonObject.get("upload");
        Iterator<Object> iterator = jsonArray.iterator();

        while (iterator.hasNext()) {
            JSONObject uploadObj = (JSONObject) iterator.next();

            if (uploadObj.get("relinkID").toString().equals(relinkID)) {
                return true;
            }
        }
        return false;
    }

    public void setRelinkID(String releaseTag, String relinkID) {

        JSONParser parser = new JSONParser();
        try {
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader("upload.json"));
            JSONArray jsonArray = (JSONArray) jsonObject.get("upload");

            Iterator<Object> iterator = jsonArray.iterator();

            while (iterator.hasNext()) {
                JSONObject jsontemp = (JSONObject) iterator.next();

                if (jsontemp.get("releaseTag").toString().equals(releaseTag)) {
                    System.out.println("found");
                    if (!existRelinkID(jsonObject,relinkID)) {
                        System.out.println("edit relinkID");
                        jsontemp.remove("relinkID");
                        jsontemp.put("relinkID", relinkID);


                        FileWriter file = new FileWriter("upload.json");
                        file.write(((JSONObject) jsonObject).toJSONString());
                        file.flush();
                        file.close();

                    } else {
                        System.out.println("relinkID avail");
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("err. with uplado.json");

        }
    }


    public void removeEntryByIndex(int i) {

        JSONParser parser = new JSONParser();

        try {
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader("upload.json"));
            JSONArray jsonArray = (JSONArray) jsonObject.get("upload");

            jsonArray.remove(i);

            FileWriter file = new FileWriter("upload.json");
            file.write(((JSONObject) jsonObject).toJSONString());
            file.flush();
            file.close();

            System.out.println("entry with index " + i + " deleted");

        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    public void removeEntryByReleaseTag(String releaseTag) {

        JSONParser parser = new JSONParser();

        try {
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader("upload.json"));
            JSONArray jsonArray = (JSONArray) jsonObject.get("upload");


            for(int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsonTemp = (JSONObject) jsonArray.get(i);
                if (jsonTemp.get("releaseTag").toString().equals(releaseTag)) {
                    removeEntryByIndex(i);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}
