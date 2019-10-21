package download;

public class StatusDownloadListener implements DownloadListener {

    @Override
    public void onDownloadSucceed(Download download) {
        System.out.println(download.getURL() + " is succeed");
    }

    @Override
    public void onDownloadFailed(Download download) {
        System.out.println(download.getURL() + " is failed");
    }
}
