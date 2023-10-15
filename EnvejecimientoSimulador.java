public class EnvejecimientoSimulador extends Thread {

    MemoryManager memoryManager;

    public EnvejecimientoSimulador(MemoryManager memoryManager) {
        this.memoryManager = memoryManager;
    }


    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000);
                for (int i = 0; i < memoryManager.getNumFramesInUse(); i++) {
                    
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
