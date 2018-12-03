import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.io.*;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.File;

public class Server extends UnicastRemoteObject implements ServerInterface, FileInterface {
    private static final long serialVersionUID = 1L;
    private ArrayList<ServerInterface> clientList;
 
    protected Server() throws RemoteException {
        clientList = new ArrayList<ServerInterface>();
    }

    public synchronized boolean clientDetails(ServerInterface serverinterface, String clientip) throws RemoteException {
        boolean chkLog = false;  
        if (clientip != "") {
            chkLog = true;
            this.clientList.add(serverinterface);
            System.out.println(clientip + " has registered!"); 
        }
        return chkLog;
    }
 
    public synchronized void broadcastMessage(String clientip) throws RemoteException {
        for(int i=0; i<clientList.size(); i++) {
            clientList.get(i).sendMessageToClient(clientip + " has completed downloading file", clientip);
        }
    }
 
    public synchronized void sendMessageToClient(String message, String ip) throws RemoteException{}

    public OutputStream getOutputStream(File f) throws IOException {
        return new RMIOutputStream(new RMIOutputStreamImpl(new FileOutputStream(f)));
    }

    public InputStream getInputStream(File f) throws IOException {
        return new RMIInputStream(new RMIInputStreamImpl(new FileInputStream(f)));
    }

    public static void main(String[] arg) throws RemoteException, MalformedURLException {
        Naming.rebind("RMIServer", new Server());
        System.out.println("Server started...");
    }
}