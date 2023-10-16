import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class MemoryManager {
    
    private HashMap<Integer, ArrayList<Integer>> pageTable;       // Tabla de paginas (mapeo pagina -> <marco, isReferenciado, age>)
    private ArrayList<Page> virtualMemory;                        // Memoria virtual
    private ArrayList<Frame> physicalMemory;                      // Memoria fisica
    private int numPages;                                         // Numero de paginas totales
    private int numFrames;                                        // Numero de paginas en memoria
    private int pageFault;                                        // Fallas de pagina
    private int pageToReplace;                                    // Pagina a reemplazar
    private boolean envejecimiento;                               // Bandera para sincronizacion
    private boolean paginacion;                                   // Bandera para sincronizacion
    private boolean fin;                                          // Bandera para indicar si se termino de ejecutar el programa

    public MemoryManager(int numFrames, int numPages) {
        this.pageTable           = new HashMap<Integer, ArrayList<Integer>>();
        this.virtualMemory       = new ArrayList<Page>();
        this.physicalMemory      = new ArrayList<Frame>();
        this.numPages            = numPages;
        this.numFrames           = numFrames;
        this.pageFault           = 0;
        this.pageToReplace       = -1;
        this.envejecimiento      = false;
        this.paginacion          = true;
        this.fin                 = false;
    }

    /**
     * Actualiza la pagina en la tabla de paginas cuando se produce una falla de pagina
     * @param pageNumber
     */
    public synchronized void updatePageTable(int pageNumber, int i) {
        if (!isPageInMemory(pageNumber)) {
            if (isPageTableFull()) {
                int pageToReplace = getPageToReplace();
                System.out.println("Se reemplaza la pagina " + pageToReplace + " por la pagina " + pageNumber + ".");
                ArrayList<Integer> data = getDataForPageInPageTable(pageToReplace);
                int frameNumberToReplace = data.get(0);
                removePage(pageToReplace);
                loadPage(pageNumber, frameNumberToReplace);
                setIsReferenciado(pageNumber, 1);
            } else {
                if (i == 0) {
                    setPageToReplace(pageNumber);
                }
                loadPage(pageNumber, i);
            }
            pageFault++;
        } else {
            setIsReferenciado(pageNumber, 1);
        }
        setPaginacion(false);
        setEnvejecimiento(true);
        System.out.println(getPageTable());
    }

    /**
     * Actualiza la pagina que debe ser reemplazada de acuerdo con el algoritmo de envejecimiento
     */
    public synchronized void updatePageToReplace() {
        if (!isPageTableEmpty()) {
            for (int pageNumber : getKeySetInPageTable()) {
                int age = getAge(pageNumber);
                if (isPageReferenced(pageNumber)) {
                    updateAge(pageNumber, increaseAge(age));
                    setIsReferenciado(pageNumber, 0);
                } else {
                    updateAge(pageNumber, decreaseAge(age));
                }
                int ageUpdate = getAge(pageNumber);
                int agePageToReplace = getAge(pageToReplace);
                if (getDataForPageInPageTable(pageToReplace).get(0) == -1) {
                    if (getDataForPageInPageTable(pageNumber).get(0) != -1) {
                        pageToReplace = pageNumber;
                    }
                } else {
                    if (getDataForPageInPageTable(pageNumber).get(0) != -1) {
                        if (ageUpdate < agePageToReplace) {
                            pageToReplace = pageNumber;
                        }
                    }
                }
            }
        }
        setEnvejecimiento(false);
        setPaginacion(true);
        System.out.println(pageToReplace);
        System.out.println(getPageTable());
    }

    public synchronized boolean isPageInMemory(int pageNumber) {
        boolean isPageInMemory = false;
        if (pageTable.containsKey(pageNumber)) {
            if (getDataForPageInPageTable(pageNumber).get(0) != -1) {
                isPageInMemory = true;
            }
        }
        return isPageInMemory;
    }

    public synchronized boolean isPageTableFull() {
        int pagesInUse = 0;
        for (int pageNumber : getKeySetInPageTable()) {
            if (getDataForPageInPageTable(pageNumber).get(0) != -1) {
                pagesInUse++;
            }
        }
        return pagesInUse == numFrames;
    }

    public synchronized void loadPage(int pageNumber, int frameNumber) {
        ArrayList<Integer> data = new ArrayList<Integer>(3);
        data.add(frameNumber);                                                  //marco
        data.add(1);                                                          //isReferenciado
        data.add(0);                                                          //age -> (128 =2 1000 0000)
        pageTable.put(pageNumber, data);
    }

    public void removePage(int pageNumber) {
        ArrayList<Integer> data = getDataForPageInPageTable(pageNumber);
        data.set(0, -1);                                              //marco = -1 -> no esta en memoria
        data.set(1, 0);                                       //isReferenciado = 0
        data.set(2, 0);                                       //age = 0
        pageTable.put(pageNumber, data);
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

    public synchronized boolean isPageTableEmpty() {
        return pageTable.isEmpty();
    }

    public Set<Integer> getKeySetInPageTable() {
        return pageTable.keySet();
    }

    public int getAge(int pageNumber) {
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

    public int getPageToReplace() {
        return pageToReplace;
    }

    public synchronized HashMap<Integer, ArrayList<Integer>> getPageTable() {
        return pageTable;
    }

    public synchronized void setPageToReplace(int placeToReplace) {
        this.pageToReplace = placeToReplace;
    }

    public synchronized void setIsReferenciado(int pageNumber, int isReferenciado) {
        pageTable.get(pageNumber).set(1, isReferenciado);
    }

    public synchronized boolean isFin() {
        return fin;
    }

    public synchronized void setFin(boolean fin) {
        this.fin = fin;
    }

    public String decimalA8BitsBinario(int decimal) {
        String binario = Integer.toBinaryString(decimal);
        while (binario.length() < 8) {
            binario = "0" + binario;
        }
        return binario;
    }

    public int binarioADecimal(String binario) {
        return Integer.parseInt(binario, 2);
    }

    public int increaseAge(int age) {
        String binario = decimalA8BitsBinario(age);
        return binarioADecimal("1" + binario.substring(0, 7));
    }

    public int decreaseAge(int age) {
        String binario = decimalA8BitsBinario(age);
        return binarioADecimal("0" + binario.substring(0, 7));
    }

    public synchronized void setEnvejecimiento(boolean envejecimiento) {
        this.envejecimiento = envejecimiento;
    }

    public synchronized boolean getEnvejecimiento() {
        return envejecimiento;
    }

    public synchronized boolean getPaginacion() {
        return paginacion;
    }

    public synchronized void setPaginacion(boolean paginacion) {
        this.paginacion = paginacion;
    }

    public synchronized int getPageFault() {
        return pageFault;
    }

}
