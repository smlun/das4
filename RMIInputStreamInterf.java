import java.rmi.*;
import java.io.*;

public interface RMIInputStreamInterf extends Remote {

    public byte[] readBytes(int len) throws IOException, RemoteException;
    public int read() throws IOException, RemoteException;
    public void close() throws IOException, RemoteException;

}
