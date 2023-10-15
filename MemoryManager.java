import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class MemoryManager {
    
    private HashMap<Integer, ArrayList<Integer>> pageTable;       // Tabla de paginas (mapeo pagina -> <marco, isReferenciado, age>)
    private ArrayList<Page> virtualMemory;                        // Memoria virtual
    private ArrayList<Frame> physicalMemory;                      // Memoria fisica
    private int numPages;                                         // Numero de paginas totales
    private int numFrames;                                        // Numero de paginas en memoria
    private int pageToReplace;                                    // Pagina a reemplazar
    private boolean fin;                                          // Bandera para indicar si se termino de ejecutar el programa

    public MemoryManager(int numFrames, int numPages) {
        this.pageTable           = new HashMap<Integer, ArrayList<Integer>>();
        this.virtualMemory       = new ArrayList<Page>();
        this.physicalMemory      = new ArrayList<Frame>();
        this.numPages            = numPages;
        this.numFrames           = numFrames;
        this.pageToReplace       = -1;
        this.fin                 = false;
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
        data.set(2, 128);                            //age -> (128 =2 1000 0000)
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

    public void updateAge(int pageNumber, int age) {
        pageTable.get(pageNumber).set(2, age);
    }

    public boolean isPageTableMinSize() {
        return pageTable.size() == 2;
    }

    public Set<Integer> getKeySetInPageTable() {
        return pageTable.keySet();
    }

    public synchronized int getAge(int pageNumber) {
        return pageTable.get(pageNumber).get(2);
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

    public synchronized int getPageToReplace() {
        return pageToReplace;
    }

    public void setPageToReplace(int placeToReplace) {
        this.pageToReplace = placeToReplace;
    }

    public void setIsReferenciado(int pageNumber, int isReferenciado) {
        pageTable.get(pageNumber).set(1, isReferenciado);
    }

    public synchronized boolean isFin() {
        return fin;
    }

    public synchronized void setFin(boolean fin) {
        this.fin = fin;
    }

}
