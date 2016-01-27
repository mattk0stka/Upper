package RMI;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;

import java.util.List;

/**
 * Created by mk0stka on 17.12.15.
 */
public interface RequestResponse_Apache_Interface {


    String getPageContent(String url) throws Exception;

    String sendPostPara(String url, List<NameValuePair> urlPara) throws Exception;

    String sendPostPara(String url, HttpEntity entity) throws Exception;

    Header[] getDownloadHeader(String url)throws Exception;

    void getContent(String url) throws Exception;
}
