package download;

import java.io.*;
import java.net.URL;

public class Download implements Runnable {

    private final OutputStream outputStream;
    private final URL url;
    private boolean isDownloaded;

    public Download(URL url, OutputStream outputStream) {
        this.url = url;
        this.outputStream = outputStream;
    }

    @Override
    public void run() {
        try (InputStream inputStream = url.openStream(); outputStream) {
            int i;
            while ((i = inputStream.read()) >= 0)
                outputStream.write(i);
            isDownloaded = true;
        } catch (IOException e) {
            isDownloaded = false;
            e.printStackTrace();
        }
    }

    public OutputStream getOutputStream() {
        return outputStream;
    }

    public URL getURL() {
        return url;
    }

    public boolean isDownloaded() {
        return isDownloaded;
    }
}
