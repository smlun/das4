public class RMIOutputStreamImpl implements RMIOutputStreamInterf {

    private OutputStream out;

    public RMIOutputStreamImpl(OutputStream out) throws
            IOException {
        this.out = out;
        UnicastRemoteObject.exportObject(this, 1099);
    }

    public void write(int b) throws IOException {
        out.write(b);
    }

    public void write(byte[] b, int off, int len) throws
            IOException {
        out.write(b, off, len);
    }

    public void close() throws IOException {
        out.close();
    }

    private RMIPipe pipe;

    public RMIOutputStreamImpl(OutputStream out) throws
            IOException {
        this.out = out;
        this.pipe = new RMIPipe(out);
        UnicastRemoteObject.exportObject(this, 1099);
    }

    public int getPipeKey() {
        return pipe.getKey();
    }

    public void transfer(RMIPipe pipe) throws IOException {
        // nothing more to do here
        // pipe has been serialized and that's all we want
    }

}

public class RMIInputStreamImpl implements RMIInputStreamInterf {

    private InputStream in;
    private byte[] b;

    public RMIInputStreamImpl(InputStream in) throws IOException {
        this.in = in;
        UnicastRemoteObject.exportObject(this, 1099);
    }

    public void close() throws IOException, RemoteException {
        in.close();
    }

    public int read() throws IOException, RemoteException {
        return in.read();
    }

    public byte[] readBytes(int len) throws IOException,
            RemoteException {
        if (b == null || b.length != len)
            b = new byte[len];

        int len2 = in.read(b);
        if (len2 &lt; 0)
            return null; // EOF reached

        if (len2 != len) {
            // copy bytes to byte[] of correct length and return it
            byte[] b2 = new byte[len2];
            System.arraycopy(b, 0, b2, 0, len2);
            return b2;
        }
        else
            return b;
    }

    // For RMIPipe

    // public RMIPipe transfer(int key) throws IOException,
    //     RemoteException {
    // return new RMIPipe(key, in);
}

}
