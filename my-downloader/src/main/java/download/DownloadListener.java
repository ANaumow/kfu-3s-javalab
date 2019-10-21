package download;

public interface DownloadListener {

    void onDownloadSucceed(Download download);

    void onDownloadFailed(Download download);

}
