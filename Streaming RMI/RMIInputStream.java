public class RMIInputStream extends InputStream implements
        Serializable {

    RMIInputStreamInterf in;

    public RMIInputStream(RMIInputStreamInterf in) {
        this.in = in;
    }

    public int read() throws IOException {
        return in.read();
    }

    public int read(byte[] b, int off, int len) throws IOException {
        byte[] b2 = in.readBytes(len);
        if (b2 == null)
            return -1;
        int i = b2.length;
        System.arraycopy(b2, 0, b, off, i);
        return i;
    }

    public void close() throws IOException {
        super.close();
    }

    // For RMIPipe

    public void transfer(OutputStream out) throws IOException {
      RMIPipe pipe = new RMIPipe(out);
      in.transfer(pipe.getKey());
    }

}

public class RMIOutputStream extends OutputStream implements
        Serializable {

    private RMIOutputStreamInterf out;

    public RMIOutputStream(RMIOutputStreamImpl out) {
        this.out = out;
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

    // RMI Pipe

    private int pipeKey;
    public void transfer(InputStream in) throws IOException {
    		RMIPipe pipe = new RMIPipe(pipeKey, in);
    		out.transfer(pipe);
    }

}
