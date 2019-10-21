package download;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class DownloadMaster {

    private List<DownloadListener> listeners;
    private List<Download> downloads;

    public DownloadMaster() {
        this.listeners = new ArrayList<>();
        this.downloads = new ArrayList<>();
    }

    public boolean addListener(DownloadListener listener) {
        return listeners.add(listener);
    }

    public void notifyListeners(Consumer<DownloadListener> consumer) {
        listeners.forEach(consumer);
    }

    public boolean addDownload(Download download) {
        return downloads.add(download);
    }

    public void runAllDownloads() {
        List<Thread> threads = downloads.stream()
                .map(Thread::new)
                .collect(Collectors.toList());

        threads.forEach(Thread::start);
        threads.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
        });

        for (Download download : downloads) {
            if (download.isDownloaded())
                notifyListeners(l -> l.onDownloadSucceed(download));
            else
                notifyListeners(l -> l.onDownloadFailed(download));
        }

    }

}
