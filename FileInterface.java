import java.rmi.RemoteException;
import java.io.*;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.File;

public interface FileInterface extends java.rmi.Remote {
    public InputStream getInputStream(File f) throws IOException;
    public long getFilelength() throws RemoteException;
}
