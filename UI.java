import java.util.Scanner;

public class UI {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            mostrarMenu();

            while (!scanner.hasNextInt()) {
                System.out.println("Opción no válida. Por favor, seleccione una opción válida.");
                scanner.next(); // Descarta la entrada no válida
            }
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    //generarReferencias();
                    break;
                case 2:
                    calcularFallasDePagina();
                    break;
                case 3:
                    System.out.println("Saliendo del programa.");
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, seleccione una opción válida.");
            }

            scanner.nextLine(); // Descarta cualquier entrada adicional
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

    private static void generarReferencias() {
        Scanner scannerReferencia = new Scanner(System.in);
        System.out.print("Ingrese el tamaño de página (en bytes): ");
        int tamPagina = scannerReferencia.nextInt();
        System.out.print("Ingrese el número de filas de la matriz 1: ");
        int nf1 = scannerReferencia.nextInt();
        System.out.print("Ingrese el número de columnas de la matriz 1 (y filas de la matriz 2): ");
        int nc1 = scannerReferencia.nextInt();
        System.out.print("Ingrese el número de columnas de la matriz 2: ");
        int nc2 = scannerReferencia.nextInt();
        scannerReferencia.close();
        ReferenciaGenerador referencia = new ReferenciaGenerador(nf1, nc1, nc2, tamPagina);
        referencia.generarReferencias();
    }


    private static void calcularFallasDePagina() {
        // Lógica para calcular el número de fallas de página
        // Debes implementar esta lógica en una clase separada
        // Por ejemplo, la clase PaginacionSimulador mencionada en la respuesta anterior
    }
}
