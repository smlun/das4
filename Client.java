import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.*;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.File;
import java.net.InetAddress;

public class Client extends UnicastRemoteObject implements ServerInterface, Runnable {
    final public static int BUF_SIZE = 1024 * 64;
    private static final long serialVersionUID = 1L;
    private ServerInterface server;
    private FileInterface fileServer;
    private String ClientName;
    private String ClientIP;
    private ArrayList<String> ipList;
    boolean chkExit = true;
    boolean chkLog = false;
 
    protected Client(ServerInterface serverinterface, String clientname, String clientip, FileInterface fserver) throws RemoteException {
        this.server = serverinterface;
        this.fileServer = fserver;
        this.ClientName = clientname;
        this.ClientIP = clientip;
        ipList = new ArrayList<String>();
        chkLog = server.checkClientCredintials(this, clientname, clientip);
    }

    public static void copy(InputStream in, OutputStream out) throws IOException {
        System.out.println("Streaming file...");
        byte[] b = new byte[BUF_SIZE];
        int len;
        while ((len = in.read(b)) >= 0) {
            out.write(b, 0, len);
        }
        in.close();
        out.close();
    }
    
    public static void upload(FileInterface finterface, File src, File dest) throws IOException {
        copy (new FileInputStream(src), 
        finterface.getOutputStream(dest));
    }

    public static void download(FileInterface finterface, File src, File dest) throws IOException {
        copy (finterface.getInputStream(src), 
        new FileOutputStream(dest));
    }

    public void sendMessageToClient(String message, String oip) throws RemoteException {
        System.out.println(message);
        this.ipList.add(oip);
    }
 
    public void broadcastMessage(String clientname, String clientip) throws RemoteException {}
 
    public boolean checkClientCredintials(ServerInterface serverinterface ,String clientname, String clientip) throws RemoteException {
        return true;
    }

    public void tryReconnect() {
        System.out.println(Arrays.toString(this.ipList.toArray()));
        String url = this.ipList.get(0);
        try {
            connect(url);
        }
        catch (MalformedURLException murle) {
            System.out.println("MalformedURLException");
            System.out.println(murle);
        }
        catch (RemoteException re) {
            System.out.println("RemoteException");
            System.out.println(re);
        }
        catch (NotBoundException ne) {
            System.out.println("NotBoundException");
            System.out.println(ne);
        }
        catch (UnknownHostException ue) {
            System.out.println("UnknownHostException");
            System.out.println(ue);
        }
        this.ipList.remove(0);
    }

    public void run() {
        if(chkLog) {
            try {
                System.out.println("Successfully Connected To Server");
                
                File testFile = new File("test.mkv");
                long len = testFile.length();
    
                long t;
                t = System.currentTimeMillis();
                download(fileServer, testFile, new File("download.mkv"));
                // t = (System.currentTimeMillis() - t) / 1000;
                // System.out.println("download: " + (len / t / 1000000d) + " MB/s");
                System.out.println("Stream Completed");

                if (chkExit == true) {
                    server.broadcastMessage(ClientName, ClientIP);
                }
                // upload(server, new File("download.txt"), new File("upload.txt"));
            }
            catch (MalformedURLException murle) {
                System.out.println("MalformedURLException");
                System.out.println(murle);
            }
            catch (RemoteException re) {
                System.out.println("\nUnable to reach server...\n");
                System.out.println("Attempting to connect the next available server..");
                tryReconnect();
                // System.out.println(re);
            }
            catch (IOException nbe) {
                System.out.println("NotBoundException");
                System.out.println(nbe);
            } 
        } 
    }

    public static void connect(String url) throws MalformedURLException, RemoteException, NotBoundException, UnknownHostException {
        Scanner scanner = new Scanner(System.in);
        String clientName = "";
        String clientIP = "";
          
        System.out.print("Enter The Name : ");
        clientName = scanner.nextLine();
        
        InetAddress localhost = InetAddress.getLocalHost();
        clientIP = localhost.getHostAddress().trim();
        System.out.println("\nConnecting To Server...\n");
        
        ServerInterface serverinterface = (ServerInterface)Naming.lookup("rmi://" + url + "/RMIServer");
        FileInterface fInterface = (FileInterface)Naming.lookup("rmi://" + url + "/RMIServer");
        new Thread(new Client(serverinterface , clientName, clientIP, fInterface)).start(); 
    }
    
    public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException, UnknownHostException {
        String url = "localhost";
        connect(url);
    }
}