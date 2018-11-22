import java.rmi.*;

public interface Server extends Remote {
    public String sayHello() throws RemoteException;
}