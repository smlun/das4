import java.rmi.*;
import java.io.*;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.File;

public interface Server extends Remote {
    public String sayHello() throws RemoteException;
    public OutputStream getOutputStream(File f) throws IOException;      
    public InputStream getInputStream(File f) throws IOException;    
    public boolean addClient(Server serveri, String clientname) throws RemoteException;
    public void broadcastMessage(String name) throws RemoteException;
    public String sendMessageToClient(String message) throws RemoteException; 
}