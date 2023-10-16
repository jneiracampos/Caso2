import java.util.ArrayList;

public class PaginacionSimulador extends Thread {
    
    private ArrayList<Integer> referencias;
    private MemoryManager memoryManager;

    public PaginacionSimulador(ArrayList<Integer> referencias, MemoryManager memoryManager) {
        this.referencias = referencias;
        this.memoryManager = memoryManager;
    }

    @Override
    public void run() {
        for (int i = 0; i < referencias.size() - 1; i++) {
            try {
                if (memoryManager.getPaginacion()) {
                    int pageNumber = referencias.get(i);
                    memoryManager.updatePageTable(pageNumber, i);
                }
                Thread.sleep(2);
                //System.out.println("Hola");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } 
        }
        memoryManager.setFin(true);
    }

}
