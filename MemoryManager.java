import java.util.HashMap;

public class MemoryManager {
    private int[] physicalMemory;  // Memoria física (marcos de página)
    private HashMap<Integer, Integer> pageTable;  // Tabla de páginas (mapeo página -> marco)

    public MemoryManager(int numFrames) {
        physicalMemory = new int[numFrames];
        pageTable = new HashMap<>();
    }

    public boolean isPageInMemory(int pageNumber) {
        return pageTable.containsKey(pageNumber);
    }

    public int getFrameForPage(int pageNumber) {
        if (isPageInMemory(pageNumber)) {
            return pageTable.get(pageNumber);
        } else {
            return -1;  // La página no está en memoria
        }
    }

    public void loadPage(int pageNumber, int frameNumber) {
        if (!isPageInMemory(pageNumber)) {
            if (frameNumber >= 0 && frameNumber < physicalMemory.length) {
                pageTable.put(pageNumber, frameNumber);
            } else {
                throw new IllegalArgumentException("Frame number is out of bounds.");
            }
        }
    }

    public void removePage(int pageNumber) {
        if (isPageInMemory(pageNumber)) {
            pageTable.remove(pageNumber);
        }
    }

    public int getNumFramesInUse() {
        return pageTable.size();
    }

    public int getNumTotalFrames() {
        return physicalMemory.length;
    }
}
