public class EnvejecimientoSimulador extends Thread {

    MemoryManager memoryManager;

    public EnvejecimientoSimulador(MemoryManager memoryManager) {
        this.memoryManager = memoryManager;
    }

    @Override
    public void run() {
        while (memoryManager.isFin() == false) {
            try {
                if (memoryManager.getEnvejecimiento()) {
                    memoryManager.updatePageToReplace();
                }
                Thread.sleep(1);
                //System.out.println("Chao");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
