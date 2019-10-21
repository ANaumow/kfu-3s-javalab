package download;

public class CountDownloadListener implements DownloadListener {

    public int succeed = 0;
    public int failed = 0;

    @Override
    public void onDownloadSucceed(Download download) {
        succeed++;
    }

    @Override
    public void onDownloadFailed(Download download) {
        failed++;
    }
}
