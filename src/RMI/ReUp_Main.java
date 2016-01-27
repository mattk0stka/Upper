package RMI;

import org.apache.commons.io.FileUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.jsoup.Jsoup;

import java.io.*;
import java.net.URL;
import java.util.*;

/**
 * Created by mk0stka on 28.12.15.
 */
public class ReUp_Main {

    public static void main(String[] args)throws Exception {

        Uploaded_net uploaded_net = new Uploaded_net();
        uploaded_net.login();

        //RequestResponse_Apache_Interface requestResponse = new RequestResponse_Apache_Implementation();

        //System.out.println(Jsoup.parse(requestResponse.getPageContent("http://mygully.com/search.php?searchid=234422645")));


        //loader.deleteAbuseFiles(url);



        //File file = new File("exq-fantasticfour-1080p.ReUp.8.part37.rar");
        //URL url = new URL(uploaded_net.getLink("http://uploaded.net/file/bs280n61"));

        //FileUtils.copyURLToFile(url, file);
        //FileUtils.


        String str = "45.Years.German.DL.AC3.Dubbed.1080p.BluRay.x264-PsO";
        if (str.contains("1080p")) {
            System.out.println("gefunden ");
        }

        //System.out.println(post);

        //checker(uploaded_net);

        //folderCheck(uploaded_net, "nb9w0b");
        rootFolders(uploaded_net);




    }










    public JSONObject uplaod() {

        JSONParser parser = new JSONParser();
        return null;

    }


    public void test1() throws Exception{

        Uploaded_net uploaded_net = new Uploaded_net();
        uploaded_net.login();


        Dcrypt_it dcrypt = new Dcrypt_it();
        //File file = new File("4d7d206095284f0de3f89a3861e4c2a11b6ef337.dlc");

        String str = dcrypt.getImagePosterUrl("4d7d206095284f0de3f89a3861e4c2a11b6ef337.dlc").replace("<textarea>", "").replace("</textarea>", "").substring(2);
        BufferedReader br = new BufferedReader(new FileReader("abuse.json"));
        String str1;
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            str1 = sb.toString();

        } finally {
            br.close();
        }


        List<String> uploadedLinks = new ArrayList<>();

        JSONParser jsonParser = new JSONParser();
        Object obj = jsonParser.parse(str);
        JSONObject jsonObject = (JSONObject) obj;
        Object successObj = jsonObject.get("success");
        JSONObject links = (JSONObject) successObj;
        Object linksObj = links.get("links");
        JSONArray jsonArray = (JSONArray) linksObj;
        //System.out.println(jsonArray);

        Iterator<JSONObject> arrayLink = jsonArray.iterator();

        while (arrayLink.hasNext()) {

            Object objtemp = arrayLink.next();
            uploadedLinks.add((String) objtemp);

        }

        uploadedLinks.remove(0);

        for (String strtemp : uploadedLinks) {
            System.out.println(strtemp);
        }
    }


    public static void checker(Uploaded_net uploaded_net) throws Exception {

        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader("posts.json"));
        //System.out.println(obj);

        JSONObject jsonObj = (JSONObject) obj;
        JSONArray arrayPOsts = (JSONArray) jsonObj.get("posts");

        Iterator<JSONObject> objectIterator = arrayPOsts.iterator();

        while (objectIterator.hasNext()) {

            Object tempObj = objectIterator.next();
            JSONObject jsonUploadedID = (JSONObject) tempObj;
            //System.out.println(jsonUploadedID.get("uploadedID"));
            folderCheck(uploaded_net, jsonUploadedID.get("uploadedID").toString());

        }


    }

    public static String searchID2Dictionary(Uploaded_net uploaded_net, String folderName) throws Exception {

        JSONObject allFolder = uploaded_net.getFolderParaList("0");
        Object folderObj = allFolder.get("folder");
        JSONArray jsonFolder = (JSONArray) folderObj;
        Iterator<Object> folderIterator = jsonFolder.iterator();

        while (folderIterator.hasNext()) {

            JSONObject title = (JSONObject) folderIterator.next();
            if (title.get("title").toString().equals(folderName)) {
                return title.get("id").toString();
            }
        }

        return null;

    }

    public static void rootFolders(Uploaded_net uploaded_net) throws Exception {


        JSONObject allFolderFromRoot = uploaded_net.getFolderParaList("0");
        System.out.println(allFolderFromRoot);
        Object objFOlder = allFolderFromRoot.get("folder");
        JSONArray jsonArray = (JSONArray) objFOlder;
        Iterator<Object> jsonFOlder = jsonArray.iterator();

        while (jsonFOlder.hasNext()) {

            JSONObject title = (JSONObject) jsonFOlder.next();
            String id = title.get("id").toString();
            System.out.println(id);
            folderCheck(uploaded_net, id);


        }



    }


    public static void folderCheck(Uploaded_net uploaded_net, String folderID) throws Exception {


        JSONObject ouput = uploaded_net.getFolderParaList(folderID);
        Object objfiles = ouput.get("files");
        JSONArray jsonArray = (JSONArray) objfiles;
        Iterator<Object> jsonFiles = jsonArray.iterator();

        while (jsonFiles.hasNext()) {
            JSONObject isQuarantined = (JSONObject) jsonFiles.next();
            //System.out.println(isQuarantined.get("filename") + " " + isQuarantined.get("is_quarantined"));
            //System.out.println(isQuarantined.get("is_quarantined").equals("true"));
            if ((boolean) isQuarantined.get("is_quarantined")) {
                System.out.println(isQuarantined.get("filename") + " " + isQuarantined.get("is_quarantined"));

            }

        }


    }

}
