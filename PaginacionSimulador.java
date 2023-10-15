import java.util.ArrayList;

public class PaginacionSimulador extends Thread {
    
    private ArrayList<Integer> referencias;
    private MemoryManager memoryManager;
    private int fallasDePagina;

    public PaginacionSimulador(ArrayList<Integer> referencias, MemoryManager memoryManager) {
        this.referencias = referencias;
        this.fallasDePagina = 0;
        this.memoryManager = memoryManager;
    }

    public int getFallasDePagina() {
        return fallasDePagina;
    }

    @Override
    public void run() {
        for (int i = 0; i < referencias.size(); i++) {
            try {
                Thread.sleep(2);
                int pageNumber = referencias.get(i);
                if (!memoryManager.isPageInMemory(pageNumber)) {
                    if (memoryManager.isPageTableFull()) {
                        int pageToReplace = memoryManager.getPageToReplace();
                        ArrayList<Integer> data = memoryManager.getDataForPageInPageTable(pageToReplace);
                        int frameNumberToReplace = data.get(0);
                        memoryManager.removePage(pageToReplace);
                        memoryManager.loadPage(pageNumber, frameNumberToReplace);
                    } else {
                        memoryManager.loadPage(pageNumber, i);
                    }
                    fallasDePagina++;
                }
                memoryManager.setIsReferenciado(pageNumber, 1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } 
        }
        memoryManager.setFin(true);
        System.out.println(getFallasDePagina());
    }

}
