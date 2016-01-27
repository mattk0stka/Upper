package RMI_Loader;

import org.json.simple.JSONObject;


import javax.swing.filechooser.FileSystemView;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Created by mk0stka on 05.01.16.
 */
public interface Loader_Interface extends Remote {

    void startLoader(String releaseTag, List<String> url) throws RemoteException;

    void startReUpLoader(String[] url, String releaseTag) throws RemoteException;

    void startReUpLoaderAlpha(String releaseTag) throws RemoteException;

    JSONObject getUpload() throws RemoteException;

    void putNewUpload(String releaseTag, String uploadedID, String uploadedFolder, String relinkID) throws RemoteException;

    void deletefile(List<String> abuse) throws RemoteException;

    void setRelinkID(String releaseTag, String relinkID) throws RemoteException;

    void editUploadedFolder(String releaseTag, String uploadedFolder) throws RemoteException;

    void editUploadedID(String releaseTag, String uploadedID) throws RemoteException;

    void removeUploadByID(String relinkID) throws RemoteException;

    void removeUploadByIndey(int i) throws RemoteException;

    void removeUploadByReleaseTag(String releaseTag) throws RemoteException;

    FileSystemView getFileSystemView() throws RemoteException;

    boolean existUpload(String releaseTag) throws RemoteException;

    void notifyRelink(String releaseTag, String uploadedID) throws RemoteException;



}

