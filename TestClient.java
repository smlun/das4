import java.rmi.Naming;
import java.rmi.RemoteException;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.io.*;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.File;
import java.util.Scanner;

public class TestClient {
    final public static int BUF_SIZE = 1024 * 64;
    
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
    
    public static void upload(Server server, File src, File dest) throws IOException {
        copy (new FileInputStream(src), 
        server.getOutputStream(dest));
    }

    public static void download(Server server, File src, File dest) throws IOException {
        copy (server.getInputStream(src), 
        new FileOutputStream(dest));
    }

    // public void sendMessageToClient(String message) throws RemoteException {
    //     System.out.println(message); 
    // }

    // public void broadcastMessage(String clientname) throws RemoteException {}

    public static void main(String[] args) throws Exception {
        try 
        {
            String url = "rmi://localhost/server";
            Server server = (Server) Naming.lookup(url);

            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter The Name : ");
            String clientName = scanner.nextLine();
            System.out.println("\nConnecting To Server...\n");

            boolean check = server.addClient(server, clientName);

            if (check == true) 
            {
                File testFile = new File("test.mkv");
                long len = testFile.length();
        
                long t;
                t = System.currentTimeMillis();
                download(server, testFile, new File("download.mkv"));
                server.broadcastMessage(clientName);
                // t = (System.currentTimeMillis() - t) / 1000;
                // System.out.println("download: " + (len / t / 1000000d) + " MB/s");
        
                //t = System.currentTimeMillis();
                //upload(server, new File("download.txt"), new File("upload.txt"));
                // t = (System.currentTimeMillis() - t) / 1000;
                // System.out.println("upload: " + (len / t / 1000000d) + " MB/s");
            }
            
        }
        // Catch the exceptions that may occur � bad URL, Remote exception
        // Not bound exception or the arithmetic exception that may occur in
        // one of the methods creates an arithmetic error (e.g. divide by zero)
        catch (MalformedURLException murle) {
            System.out.println("MalformedURLException");
            System.out.println(murle);
        }
        catch (RemoteException re) {
            System.out.println("RemoteException");
            System.out.println(re);
        }
        catch (NotBoundException nbe) {
            System.out.println("NotBoundException");
            System.out.println(nbe);
        }
        catch (java.lang.ArithmeticException ae) {
            System.out.println("java.lang.ArithmeticException");
            System.out.println(ae);
        }
    }
}
