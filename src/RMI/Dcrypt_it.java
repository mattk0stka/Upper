package RMI;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by mk0stka on 29.10.15.
 */
class Dcrypt_it {

    private Properties properties;
    private InputStream inputStream;
    private RequestResponse_Apache_Interface requestResponse;


    private String username;
    private String password;

    public String page;

    public Dcrypt_it() throws Exception {

        //~~> load config
        this.properties = new Properties();
        this.inputStream = new FileInputStream("src/config.properties");
        properties.load(this.inputStream);



        this.requestResponse = new RequestResponse_Apache_Implementation();
    }

    public String getImagePosterUrl(String posterFilename) throws Exception {
        //System.out.println(Jsoup.parse(URLDecoder.decode(requestResponse.getPageContent(properties.getProperty("abload_de_url")), "UTF-8")));

        return requestResponse.sendPostPara(properties.getProperty("dcrypt_it_uploaddlc"), getMultipartEntity(posterFilename));
        //this.page = requestResponse.sendPostPara(properties.getProperty("abload_de_uploadurl"), getMultipartEntity(posterFilename));
        //this.page = requestResponse.sendPostPara(properties.getProperty("abload_de_uploadcompleteurl"), getUploadParaList(this.page));

        //System.out.println(getImageUrl(page));

        //Document document = Jsoup.parse(requestResponse.getPageContent(properties.getProperty("abload_de_url")));
        //Elements text = document.select("p.p.text-width");
        //System.out.println(text);

        //Jsoup.parse(requestResponse.sendPostPara(properties.getProperty("abload_de_loginurl"), getLoginParaList()));
        //System.out.println(Jsoup.parse(requestResponse.getPageContent(properties.getProperty("abload_de_url"))));


        //return getImageUrl(page);

    }

    //~~> works, not needed
    public List<NameValuePair> getLoginParaList() {

        List<NameValuePair> loginPara = new ArrayList<>();

        System.out.println(this.username);
        System.out.println(this.password);

        loginPara.add(new BasicNameValuePair("name", this.username));
        loginPara.add(new BasicNameValuePair("password", this.password));
        //loginPara.add(new BasicNameValuePair("remember", "remember"));

        return loginPara;
    }

    public HttpEntity getMultipartEntity(String filename) throws Exception {

        HttpEntity entity = MultipartEntityBuilder
                .create()
                .addBinaryBody("dlcfile", new File(filename), ContentType.create("application/octet-stream"),filename)
                //.addTextBody("resize", "600x600")
                .build();

        return entity;

    }

    public List<NameValuePair> getUploadParaList (String page) throws Exception {

        Document document = Jsoup.parse(page);
        Elements inputElements = document.select("input");
        String textArea = document.select("textarea").toString();
        int spiltIndexBegin = "<textarea name=\"images\" style=\"display:none;\">".length();
        int spitlIndexEnd = "</textarea>".length();

        String images = textArea.substring(spiltIndexBegin, textArea.length() - spitlIndexEnd);

        List<NameValuePair> paraList = new ArrayList<>();


        //~~> später umcoden !
        for (Element input : inputElements) {
            String name = input.attr("name");
            String value = input.attr("value");
            if (name.equals("key")) {
                paraList.add(new BasicNameValuePair(name, value));
            } else if (name.equals("gallery")) {
                paraList.add(new BasicNameValuePair(name, value));
            }
        }

        paraList.add(new BasicNameValuePair("images", images));

        return paraList;
    }

    //~~> works
    private List<String> getImageUrlLinks(String page) {

        Document document = Jsoup.parse(page);
        List<String> links = new ArrayList<>();
        Elements inputLinks = document.select("input");

        for (Element link : inputLinks) {
            links.add(link.attr("value"));
        }

        return links;
    }

    private String getImageUrl(String page) {
        Document document = Jsoup.parse(page);
        List<String> image = new ArrayList<>();
        Elements inputLinks = document.select("input");

        for (Element link : inputLinks) {
            image.add(link.attr("value"));
        }

        return image.get(image.size() - 2);

    }


}

