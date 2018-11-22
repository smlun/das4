import java.rmi.*;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.Naming;
import java.net.MalformedURLException;
import java.io.*;

public class TestServer {
    public interface Server extends Remote {
        public String sayHello() throws RemoteException;
    }

    public static class ServerImpl extends UnicastRemoteObject
            implements Server {
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

        // 2 new methods

        public OutputStream getOutputStream(File f) throws IOException {
            return new RMIOutputStream(new RMIOutputStreamImpl(new
            FileOutputStream(f)));
        }

        public InputStream getInputStream(File f) throws IOException {
            return new RMIInputStream(new RMIInputStreamImpl(new
            FileInputStream(f)));
        }

    }
    public static void main(String[] args) throws Exception {
        ServerImpl server = new ServerImpl();
        server.start();
        Thread.sleep(5 * 60 * 1000); // run for 5 minutes
        server.stop();
    }
}
