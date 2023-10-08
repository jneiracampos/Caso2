import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class ReferenciaGenerador {
    public static void generarReferencias(int tamPagina, int nf1, int nc1, int nc2) {
        int tamEntero = 4; // Tamaño de un entero en bytes
        int elementosPorPagina = tamPagina / tamEntero;
        int numeroDePaginas = (nf1 * nc1 + nf1 * nc2 + nf1 * nc2) / elementosPorPagina;

        try (FileWriter fileWriter = new FileWriter("referencias.txt");
             PrintWriter writer = new PrintWriter(fileWriter)) {

            writer.println("TP: " + tamPagina + ", NF: " + nf1 + ", NC1: " + nc1 + ", NC2: " + nc2 +
                    ", NR: " + (nf1 * nc1 + nf1 * nc2 + nf1 * nc2) + ", NP: " + numeroDePaginas);

            // Generar referencias
            for (int i = 0; i < nf1; i++) {
                for (int j = 0; j < nc1; j++) {
                    int pagina = (i * nc1 + j) / elementosPorPagina;
                    int offset = (i * nc1 + j) % elementosPorPagina;
                    writer.println("[A(" + i + "," + j + ")] " + pagina + " " + offset);
                }
            }

            for (int i = 0; i < nf1; i++) {
                for (int j = 0; j < nc2; j++) {
                    int pagina = (nf1 * nc1 + i * nc2 + j) / elementosPorPagina;
                    int offset = (nf1 * nc1 + i * nc2 + j) % elementosPorPagina;
                    writer.println("[B(" + i + "," + j + ")] " + pagina + " " + offset);
                }
            }

            for (int i = 0; i < nf1; i++) {
                for (int j = 0; j < nc2; j++) {
                    int pagina = (2 * nf1 * nc1 + i * nc2 + j) / elementosPorPagina;
                    int offset = (2 * nf1 * nc1 + i * nc2 + j) % elementosPorPagina;
                    writer.println("[C(" + i + "," + j + ")] " + pagina + " " + offset);
                }
            }

            System.out.println("Referencias generadas y guardadas en 'referencias.txt'.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
