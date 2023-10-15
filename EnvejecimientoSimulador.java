public class EnvejecimientoSimulador extends Thread {

    MemoryManager memoryManager;

    public EnvejecimientoSimulador(MemoryManager memoryManager) {
        this.memoryManager = memoryManager;
    }

    public static String decimalA8BitsBinario(int decimal) {
        String binario = Integer.toBinaryString(decimal);
        while (binario.length() < 8) {
            binario = "0" + binario;
        }
        return binario;
    }

    public static int binarioADecimal(String binario) {
        return Integer.parseInt(binario, 2);
    }

    public static int increaseAge(int age) {
        String binario = decimalA8BitsBinario(age);
        return binarioADecimal("1" + binario.substring(0, 7));
    }

    public static int decreaseAge(int age) {
        String binario = decimalA8BitsBinario(age);
        return binarioADecimal("0" + binario.substring(0, 7));
    }

    @Override
    public void run() {
        while (memoryManager.isFin() == false) {
            try {
                Thread.sleep(1);
                if (memoryManager.isPageTableMinSize()) {
                    int pageToReplace = memoryManager.getPageToReplace();
                    for (int pageNumber : memoryManager.getKeySetInPageTable()) {
                        int age = memoryManager.getAge(pageNumber);
                        if (memoryManager.isPageReferenced(pageNumber)) {
                            int ageIncrementado = increaseAge(age);
                            memoryManager.updateAge(pageNumber, ageIncrementado);
                        }
                        int ageDecrementado = decreaseAge(age);
                        memoryManager.updateAge(pageNumber, ageDecrementado);
                        int ageUpdate = memoryManager.getAge(pageNumber);
                        int agePageToReplace = memoryManager.getAge(pageToReplace);
                        if (ageUpdate < agePageToReplace) {
                            pageToReplace = pageNumber;
                        }
                    }
                    memoryManager.setPageToReplace(pageToReplace);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
