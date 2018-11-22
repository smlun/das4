// public class TestClient {
//
//     public static void main(String[] args) throws Exception {
//
//         String url = "rmi://localhost/server";
//         Server server = (Server) Naming.lookup(url);
//
//         System.out.println("Server says: " + server.sayHello());
//     }
//
// }
import java.rmi.*;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.Naming;
import java.net.MalformedURLException;
import java.io.*;

public class TestClient {

    final public static int BUF_SIZE = 1024 * 64;

    public static void copy(InputStream in, OutputStream out)
            throws IOException {

        if (in instanceof RMIInputStream) {
            System.out.println("using RMIPipe of RMIInputStream");
            ((RMIInputStream) in).transfer(out);
            return;
        }

        if (out instanceof RMIOutputStream) {
            System.out.println("using RMIPipe of RMIOutputStream");
            ((RMIOutputStream) out).transfer(in);
            return;
        }

        System.out.println("using byte[] read/write");
        byte[] b = new byte[BUF_SIZE];
        int len;
        while ((len = in.read(b)) >= 0) {
            out.write(b, 0, len);
        }
        in.close();
        out.close();
    }

    public static void main(String[] args) throws Exception {

        String url = "rmi://localhost/server";
        ServerInterf server = (ServerInterf) Naming.lookup(url);

        System.out.println("Server says: " + server.sayHello());

        File testFile = new File("Test100MB.tif");
        long len = testFile.length();

        long t;
        t = System.currentTimeMillis();
        download(server, testFile, new File("download.tif"));
        t = (System.currentTimeMillis() - t) / 1000;
        System.out.println("download: " + (len / t / 1000000d) +
            " MB/s");

        t = System.currentTimeMillis();
        upload(server, new File("download.tif"),
        new File("upload.tif"));
        t = (System.currentTimeMillis() - t) / 1000;
        System.out.println("upload: " + (len / t / 1000000d) +
            " MB/s");
    }

}
