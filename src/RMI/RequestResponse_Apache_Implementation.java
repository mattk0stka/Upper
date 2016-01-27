package RMI;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;


import java.io.*;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by mk0stka on 29.10.15.
 */
public class RequestResponse_Apache_Implementation implements RequestResponse_Apache_Interface{

    private RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(1000).build();
    private HttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build();

    //private HttpClient httpClient = HttpClientBuilder.create().build();
    private final String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.80 Safari/537.36";


    public RequestResponse_Apache_Implementation() throws Exception {

        CookieHandler.setDefault(new CookieManager());

    }


    @Override
    public Header[] getDownloadHeader(String url)  {
        //System.out.println("------------------HttpGET------------------");

        HttpGet request = new HttpGet(url);
        request.setHeader("User-Agent", USER_AGENT);
        request.setHeader("Content-Type", "text/html; charset=UTF-8");


        System.out.println("try to get Header");

        HttpResponse response = null;

        try {
            TimeUnit.SECONDS.sleep(2);
            response = httpClient.execute(request);
            System.out.println("StatusCode: " + response.getStatusLine().getStatusCode());

            return response.getAllHeaders();
        } catch (Exception e) {
            System.out.println("error");
            return null;
        }
    }




    @Override
    public String getPageContent(String url) throws Exception{

        System.out.println("------------------HttpGET------------------");

        HttpGet request = new HttpGet(url);
        request.setHeader("User-Agent", USER_AGENT);
        request.setHeader("Content-Type", "text/html; charset=UTF-8");

        HttpResponse response = null;
        try {
            response = httpClient.execute(request);
        } catch (IOException e) {
            System.out.println("Fehler hier ");
           return getPageContent(url);
        }


        if (response.getAllHeaders().equals(null)) {
            System.out.println("response header null");
            getPageContent(url);
        }

        Header[] headers = response.getAllHeaders();

        for (Header header : headers) {
            //System.out.println(header.getName() + " " + header.getValue());

        }

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        StringBuilder stringBuilder = new StringBuilder();
        String inputLine = "";

        while ((inputLine = bufferedReader.readLine()) != null) {
            stringBuilder.append(inputLine);
        }


        // ~~>  unbedingt Stream schließen, ansonsten Programm blockiert
        bufferedReader.close();


        //System.out.println("------------------HttpGET-END--------------");
        //System.out.println("StatusCode: " + "       " +response.getStatusLine().getStatusCode());

        //~~> umcoden zu while-Schleife. So lange ausführe bis VerbindungsStatusCode = 200 erreicht
        while (response.getStatusLine().getStatusCode() == 503) {
            if (response.getFirstHeader("Refresh").getValue() != null) {
                int waitingTime = Integer.valueOf(response.getFirstHeader("Refresh").getValue().split(";")[0]);
                System.out.println("time to wait: " + waitingTime + " sec.");

                String refreshLink = url + response.getFirstHeader("Refresh").getValue().substring(7, response.getFirstHeader("Refresh").getValue().length());
                TimeUnit.SECONDS.sleep(waitingTime);

                System.out.println("------------------HttpGET------------------");
                request = new HttpGet(refreshLink);
                request.setHeader("User-Agent", USER_AGENT);
                request.setHeader("Accept-Language", "de");
                request.setHeader("Content-Type", "text/html; charset=UTF-8");


                response = httpClient.execute(request);
                bufferedReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                stringBuilder.setLength(0);

                inputLine = "";

                while ((inputLine = bufferedReader.readLine()) != null) {
                    stringBuilder.append(inputLine);
                }

                bufferedReader.close();
                System.out.println("------------------HttpGET-END--------------");
                System.out.println("StatusCode: " + "       " +response.getStatusLine().getStatusCode());
                //return stringBuilder.toString();
            }

        }

        //andere Fehler werden nicht behandelt

        return stringBuilder.toString();
    }

    @Override
    public String sendPostPara(String url, List<NameValuePair> urlPara) throws Exception {

        //System.out.println("------------------HttpSEND-Para------------");

        HttpPost post = new HttpPost(url);
        post.setHeader("User-Agent", USER_AGENT);
        post.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        post.setHeader("Accept-Language", "de-DE,de;q=0.8,en-US;q=0.6,en;q=0.4");
        //post.setHeader("Connection", "keep-alive");
        //post.setHeader("Content-Type", "text/html; charset=UTF-8");


        HttpEntity entity = new UrlEncodedFormEntity(urlPara, "UTF-8");


        //post.setEntity(new UrlEncodedFormEntity(urlPara));;
        post.setEntity(entity);

        HttpResponse response = httpClient.execute(post);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        StringBuilder stringBuilder = new StringBuilder();
        String inputLine = "";

        while ((inputLine = bufferedReader.readLine()) != null) {
            stringBuilder.append(inputLine);
        }

        bufferedReader.close();
        //System.out.println("------------------HttpSEND-END-------");
        return stringBuilder.toString();
    }

    @Override
    public String sendPostPara(String url, HttpEntity entity) throws Exception {


        //System.out.println("------------------HttpSEND-Entity----------");

        HttpPost post = new HttpPost(url);
        //~~> test
        post.setHeader("user-Agent", USER_AGENT);

        post.setEntity(entity);

        HttpResponse response = httpClient.execute(post);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        StringBuilder stringBuilder = new StringBuilder();
        String inputLine = "";

        while ((inputLine = bufferedReader.readLine()) != null) {
            stringBuilder.append(inputLine);
        }


        //System.out.println("------------------HttpSEND-END-------");
        return stringBuilder.toString();
    }


    @Override
    public void getContent(String url) throws Exception {

        HttpGet request = new HttpGet(url);
        request.setHeader("User-Agent", USER_AGENT);

        HttpResponse response = httpClient.execute(request);

        OutputStream outputStream = new FileOutputStream("Jurrasic+World.ipg");

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

        byte[] b = new byte[2048];
        int length;

        while ((length = bufferedReader.read()) != -1) {
            outputStream.write(b, 0, length);
        }
        bufferedReader.close();
        outputStream.close();



    }
}
