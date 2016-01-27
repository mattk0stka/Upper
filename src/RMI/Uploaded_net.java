package RMI;


import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

/**
 * Created by mk0stka on 16.12.15.
 */
public class Uploaded_net {


    private Properties properties;
    private InputStream inputStream;
    private RequestResponse_Apache_Interface requestResponse;

    private String username;
    private String password;
    private String urlLink;

    public Uploaded_net() throws Exception {

        this.properties = new Properties();
        this.inputStream = new FileInputStream("config.properties");
        properties.load(this.inputStream);

        this.username = properties.getProperty("uploaded_net_username");
        this.password = properties.getProperty("uploaded_net_password");

        this.requestResponse = new RequestResponse_Apache_Implementation();
    }


    public void login() throws Exception{

        //System.out.println(Jsoup.parse(requestResponse.getPageContent(properties.getProperty("uploaded_net_url"))));
        requestResponse.getPageContent(properties.getProperty("uploaded_net_url"));
        requestResponse.sendPostPara(properties.getProperty("uploaded_net_loginurl"), getLoginParaList());
        requestResponse.getPageContent(properties.getProperty("uploaded_net_url"));
        
    }


    public String getLink(String url) {
        Document document = null;
        try {
            document = Jsoup.parse(requestResponse.getPageContent(url));
            Element login_form = document.getElementById("download");
            Elements inputElements = login_form.getElementsByTag("form");

            return inputElements.attr("action");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public DownloadInfo getDirectLink(String link) throws Exception {


        DownloadInfo downloadInfo = new DownloadInfo();
        Document document = Jsoup.parse(requestResponse.getPageContent(link));
        Element login_form = document.getElementById("download");
        Elements inputElements = login_form.getElementsByTag("form");
        System.out.println(link);
        System.out.println("link: " + inputElements.attr("action"));

        Header[] headers = requestResponse.getDownloadHeader(inputElements.attr("action"));

        if (headers == null) {
            System.out.println("new try.....");
            return null;
        } else {

            String filename = headers[6].getValue().split("=")[1];
            filename = filename.substring(1, filename.length() - 1);

            downloadInfo.setDownloadlink(inputElements.attr("action") + "/" + filename);
            downloadInfo.setFileName(filename);

            return downloadInfo;
        }
    }

    public void deleteAbuseFile(List<String> abuseFileID) {
        List<NameValuePair> removeParaList = new ArrayList<>();

        try {
            Document document = Jsoup.parse(requestResponse.getPageContent("http://uploaded.net/manage"));
            Elements class_user = document.select("ul.user");
            Elements inputs = class_user.select("input");

            for (String url : abuseFileID) {
                removeParaList.add(new BasicNameValuePair("file[]", url.replace("http://ul.to/", "")));
            }

            for (Element input : inputs) {
                String id = input.attr("id");
                String value = input.attr("value");

                if (id.equals("user_pw")) {
                    System.out.println(value);
                    removeParaList.add(new BasicNameValuePair("pass", value));
                }

            }

            removeParaList.add(new BasicNameValuePair("opt", "mr"));
            removeParaList.add(new BasicNameValuePair("_", ""));

            System.out.println(Jsoup.parse(requestResponse.sendPostPara(properties.getProperty("uploaded_net_removeurl"), removeParaList)));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public List<String> getLinks(String uploadedID, String releaseTag) {


        List<String> fileList = new ArrayList<>();

        try {

            JSONObject jsonObject = getFolderParaList(uploadedID);
            JSONArray jsonArrayFiles = (JSONArray) jsonObject.get("files");

            System.out.println(jsonObject);
            Iterator<Object> iterator = jsonArrayFiles.iterator();

            while (iterator.hasNext()) {

                JSONObject file = (JSONObject) iterator.next();
                if (releaseTag.contains("1080p") && file.get("filename").toString().contains("1080p")) {
                    fileList.add("http://ul.to/" + file.get("id"));
                } else if (releaseTag.contains("720p") && file.get("filename").toString().contains("720p")) {
                    fileList.add("http://ul.to/" + file.get("id"));
                } else if (!releaseTag.contains("720p") && !releaseTag.contains("1080p") && !file.get("filename").toString().contains("720p") && !file.get("filename").toString().contains("1080p")) {
                    fileList.add("http://ul.to/" + file.get("id"));
                }
            }

            return fileList;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public JSONObject getFolderParaList(String folderName) throws Exception{

        List<NameValuePair> folderPara = new ArrayList<>();

        folderPara.add(new BasicNameValuePair("folder", folderName));
        folderPara.add(new BasicNameValuePair("max", "500"));
        folderPara.add(new BasicNameValuePair("start", "0"));
        folderPara.add(new BasicNameValuePair("q", ""));
        folderPara.add(new BasicNameValuePair("nav", "undefined"));
        folderPara.add(new BasicNameValuePair("_", ""));


        Document document = Jsoup.parse(requestResponse.sendPostPara(properties.getProperty("uploaded_net_folder"), folderPara));
        String returnStr = document.body().text();
        System.out.println(returnStr);
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(returnStr);


        return (JSONObject) obj;
        //return requestResponse.getPageContent(properties.getProperty("uploaded_net_folder"));
    }



    public List<NameValuePair> getLoginParaList() {

        List<NameValuePair> loginPara = new ArrayList<>();

        loginPara.add(new BasicNameValuePair("id", this.username));
        loginPara.add(new BasicNameValuePair("pw", this.password));
        loginPara.add(new BasicNameValuePair("_", ""));

        return loginPara;
    }

}
