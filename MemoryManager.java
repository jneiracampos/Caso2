import java.util.ArrayList;
import java.util.HashMap;

public class MemoryManager {
    
    private HashMap<Integer, ArrayList<Integer>> pageTable;       // Tabla de paginas (mapeo pagina -> <marco, isReferenciado, age>)
    private ArrayList<Page> virtualMemory;                        // Memoria virtual
    private ArrayList<Frame> physicalMemory;                      // Memoria fisica
    private int numPages;                                         // Numero de paginas totales
    private int numFrames;                                        // Numero de paginas en memoria
    private int pageToReplace;                                    // Pagina a reemplazar

    public MemoryManager(int numFrames, int numPages) {
        this.pageTable           = new HashMap<Integer, ArrayList<Integer>>();
        this.virtualMemory       = new ArrayList<Page>();
        this.physicalMemory      = new ArrayList<Frame>();
        this.numPages            = numPages;
        this.numFrames           = numFrames;
        this.pageToReplace       = -1;
    }

    public boolean isPageInMemory(int pageNumber) {
        return pageTable.containsKey(pageNumber);
    }

    public boolean isPageTableFull() {
        return pageTable.size() == numFrames;
    }

    public void loadPage(int pageNumber, int frameNumber) {
        ArrayList<Integer> data = new ArrayList<Integer>();
        data.set(0, frameNumber);                            //marco
        data.set(1, 1);                              //isReferenciado
        data.set(2, 1);                              //age
        pageTable.put(pageNumber, data);
    }

    public void removePage(int pageNumber) {
        if (isPageInMemory(pageNumber)) {
            pageTable.remove(pageNumber);
        }
    }

    public ArrayList<Integer> getDataForPageInPageTable(int pageNumber) {
        return pageTable.get(pageNumber);
    }

    public boolean isPageReferenced(int pageNumber) {
        return pageTable.get(pageNumber).get(1) == 1;
    }

    public void updateAge(int pageNumber) {
        int age = pageTable.get(pageNumber).get(2);
        pageTable.get(pageNumber).set(2, age + 1);
    }
    

    public int getNumFramesInUse() {
        return pageTable.size();
    }

    public int getNumTotalFrames() {
        return numPages;
    }

    public void addPageInVirtualMemory(Page page) {
        virtualMemory.set(page.getId(), page);
    }

    public void removePageInVirtualMemory(int pageNumber) {
        virtualMemory.remove(pageNumber);
    }

    public void addFrameInPhysicalMemory(Frame frame) {
        physicalMemory.set(frame.getId(), frame);
    }

    public void removeFrameInPhysicalMemory(int frameNumber) {
        physicalMemory.remove(frameNumber);
    }

    public int getPageToReplace() {
        return pageToReplace;
    }

    public void setPageToReplace(int placeToReplace) {
        this.pageToReplace = placeToReplace;
    }

    public void setIsReferenciado(int pageNumber, int isReferenciado) {
        pageTable.get(pageNumber).set(1, isReferenciado);
    }

}
