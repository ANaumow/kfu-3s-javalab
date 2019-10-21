import download.CountDownloadListener;
import download.DownloadMaster;
import download.FileDownload;
import download.StatusDownloadListener;
import uniquestrings.NiceStringGenerator;
import uniquestrings.UniqueStringsHandler;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;

public class Main {

    public static void main(String[] args) throws MalformedURLException, FileNotFoundException {
        DownloadMaster downloadMaster = new DownloadMaster();
        CountDownloadListener countDownloadListener = new CountDownloadListener();
        StatusDownloadListener statusDownloadListener = new StatusDownloadListener();
        downloadMaster.addListener(countDownloadListener);
        downloadMaster.addListener(statusDownloadListener);

        UniqueStringsHandler handler = new UniqueStringsHandler(File.listRoots(), new NiceStringGenerator());

        for (String urlString : args) {
            String filename = urlString.substring(urlString.lastIndexOf("/") + 1);
            filename = handler.getUniqueFilename(filename);
            handler.register(filename);
            downloadMaster.addDownload(new FileDownload(new URL(urlString), new File(filename)));
        }

        downloadMaster.runAllDownloads();
        System.out.println(countDownloadListener.succeed + " files succeed");
        System.out.println(countDownloadListener.failed + " files failed");
    }

}
