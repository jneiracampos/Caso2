import java.util.Scanner;

public class UI {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("Menú:");
            System.out.println("1. Generación de referencias");
            System.out.println("2. Calcular el número de fallas de página");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opción: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    generarReferencias();
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
        } while (choice != 3);

        scanner.close();
    }

    // En la opción 1 del menú de Main
    private static void generarReferencias() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese el tamaño de página (en bytes): ");
        int tamPagina = scanner.nextInt();
        System.out.print("Ingrese el número de filas de la matriz 1: ");
        int nf1 = scanner.nextInt();
        System.out.print("Ingrese el número de columnas de la matriz 1 (y filas de la matriz 2): ");
        int nc1 = scanner.nextInt();
        System.out.print("Ingrese el número de columnas de la matriz 2: ");
        int nc2 = scanner.nextInt();
        scanner.close();
        ReferenciaGenerador.generarReferencias(tamPagina, nf1, nc1, nc2);
    }


    private static void calcularFallasDePagina() {
        // Lógica para calcular el número de fallas de página
        // Debes implementar esta lógica en una clase separada
        // Por ejemplo, la clase PaginacionSimulador mencionada en la respuesta anterior
    }
}
