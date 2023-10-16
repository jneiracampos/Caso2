import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UI {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            mostrarMenu();

            while (!scanner.hasNextInt()) {
                System.out.println("Opción no válida. Por favor, seleccione una opción válida.");
                scanner.next();
            }
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Ingrese el tamaño de página (en bytes): ");
                    int tamPagina = scanner.nextInt();
                    System.out.print("Ingrese el número de filas de la matriz 1: ");
                    int nf1 = scanner.nextInt();
                    System.out.print("Ingrese el número de columnas de la matriz 1 (y filas de la matriz 2): ");
                    int nc1 = scanner.nextInt();
                    System.out.print("Ingrese el número de columnas de la matriz 2: ");
                    int nc2 = scanner.nextInt();
                    generarReferencias(tamPagina, nf1, nc1, nc2);
                    break;
                case 2:
                    System.out.print("Ingrese el número de marcos de página: ");
                    int numeroMarcosPagina = scanner.nextInt();
                    System.out.print("Ingrese el nombre del archivo de referencias: ");
                    String nombreArchivoReferencias = scanner.next();
                    simularPaginacion(numeroMarcosPagina, nombreArchivoReferencias);
                    break;
                case 3:
                    System.out.println("Saliendo del programa.");
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, seleccione una opción válida.");
            }

            scanner.nextLine();
        } while (choice != 3);

        scanner.close();
    }

    private static void mostrarMenu() {
        System.out.println("Menú:");
        System.out.println("1. Generar Referencias");
        System.out.println("2. Calcular Fallas de Página");
        System.out.println("3. Salir");
        System.out.print("Seleccione una opción: ");
    }

    private static void generarReferencias(int tamPagina, int nf1, int nc1, int nc2) {        
        ReferenciaGenerador referencia = new ReferenciaGenerador(nf1, nc1, nc2, tamPagina);
        referencia.generarReferencias();
    }

    private static void simularPaginacion(int numeroMarcosPagina, String nombreArchivoReferencias) {
        ArrayList<Integer> referencias = leerReferencias(nombreArchivoReferencias);
        MemoryManager memoryManager = new MemoryManager(numeroMarcosPagina, referencias.get(referencias.size() - 1));
        PaginacionSimulador paginacionSimulador = new PaginacionSimulador(referencias, memoryManager);
        paginacionSimulador.start();
        EnvejecimientoSimulador envejecimientoSimulador = new EnvejecimientoSimulador(memoryManager);
        envejecimientoSimulador.start();
        try {
            paginacionSimulador.join();
            envejecimientoSimulador.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Fallas de página: " + memoryManager.getPageFault());
    }

    private static ArrayList<Integer> leerReferencias(String nombreArchivo) {
        ArrayList<Integer> referencias = new ArrayList<Integer>();
        try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
            String line;
            Pattern pattern = Pattern.compile("\\[\\w+-\\w+-\\w+\\], (\\d+), \\w+");
            int npValue = 0;

            while ((line = br.readLine()) != null) {
                if (line.startsWith("NP=")) {
                    npValue = Integer.parseInt(line.substring(3));
                } else {
                    Matcher matcher = pattern.matcher(line);
                    if (matcher.find()) {
                        int number = Integer.parseInt(matcher.group(1));
                        referencias.add(number);
                    }
                }
            }
            referencias.add(npValue);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //System.out.println("Extracted Values: " + referencias);
        return referencias;
    }
}
