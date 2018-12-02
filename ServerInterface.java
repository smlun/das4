import java.rmi.RemoteException;
import java.io.*;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.File;

public interface ServerInterface extends java.rmi.Remote {
    public boolean checkClientCredintials(ServerInterface si, String name, String ip) throws RemoteException;
    public void broadcastMessage(String name, String ip) throws RemoteException;
    public void sendMessageToClient(String message, String ip) throws RemoteException;
}