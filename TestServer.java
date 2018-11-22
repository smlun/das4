import java.rmi.*;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class TestServer {

    public static class ServerImpl extends UnicastRemoteObject implements Server {
        Registry rmiRegistry;

        public ServerImpl() throws RemoteException {
            super();
        }

        public void start() throws Exception {
            rmiRegistry = LocateRegistry.createRegistry(1099);
            rmiRegistry.bind("server", this);
            System.out.println("Server started");
        }

        public void stop() throws Exception {
            rmiRegistry.unbind("server");
            unexportObject(this, true);
            unexportObject(rmiRegistry, true);
            System.out.println("Server stopped");
        }
        
        public String sayHello() {
            return "Hello world";
        }   
    }
    public static void main(String[] args) throws Exception {
        ServerImpl server = new ServerImpl();
        server.start();
        Thread.sleep(5 * 60 * 1000); // run for 5 minutes
        server.stop();
    }
}