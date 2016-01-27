package RMI_Loader;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Created by mk0stka on 05.01.16.
 */
public class RMI_Server {
    int port;

    public RMI_Server(int port) {
        this.port = port;
        worker();
    }


    public void worker() {

        try {
            Loader_Implementation loader = new Loader_Implementation();
            System.setProperty("java.rmi.server.hostname","85.214.226.63");
            Registry registry = LocateRegistry.createRegistry(this.port);
            registry.rebind("Loader", loader);

            System.out.println("ready for down-and-up");

        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        int port = 1099;
        RMI_Server server = new RMI_Server(port);
    }
}
