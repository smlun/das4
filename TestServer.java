import java.rmi.*;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.io.*;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.File;
import java.util.ArrayList;

public class TestServer {

    public static class ServerImpl extends UnicastRemoteObject implements Server {
        Registry rmiRegistry;
        private ArrayList<Server> clientList;

        public ServerImpl() throws RemoteException {
            super();
            clientList =  new ArrayList<Server>();
        }

        public boolean addClient(Server server, String clientname) throws RemoteException { 
            boolean check = false;
            this.clientList.add(server);
            System.out.println(clientname + " is registered!");           
            check = true;
            return check;         
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

        public OutputStream getOutputStream(File f) throws IOException {
            return new RMIOutputStream(new RMIOutputStreamImpl(new FileOutputStream(f)));
        }

        public InputStream getInputStream(File f) throws IOException {
            return new RMIInputStream(new RMIInputStreamImpl(new FileInputStream(f)));
        }

        public synchronized void broadcastMessage(String clientname) throws RemoteException {
            for(int i=0; i<clientList.size(); i++) {
                clientList.get(i).sendMessageToClient(clientname.toUpperCase() + " has completed downloading");
            }
        }
 
        public String sendMessageToClient(String message) throws RemoteException {
            return message; 
        }
    }

    public static void main(String[] args) throws Exception {
        ServerImpl server = new ServerImpl();
        server.start();
        // Thread.sleep(5 * 60 * 1000); // run for 5 minutes
        // server.stop();
    }
}
