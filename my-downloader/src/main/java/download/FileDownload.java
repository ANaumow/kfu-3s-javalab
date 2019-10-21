package download;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URL;

public class FileDownload extends Download {

    public FileDownload(URL url, File file) throws FileNotFoundException {
        super(url, new FileOutputStream(file));
    }

}
