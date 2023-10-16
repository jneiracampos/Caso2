import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class ReferenciaGenerador {

    private int nf1;
    private int nc1;
    private int nc2;
    private int tamPagina;
    private int tamEntero = 4;
    private int elementosPorPagina;

    public ReferenciaGenerador(int nf1, int nc1, int nc2, int tamPagina) {
        this.nf1 = nf1;
        this.nc1 = nc1;
        this.nc2 = nc2;
        this.tamPagina = tamPagina;
    }

    public void generarReferencias() {
        elementosPorPagina = tamPagina / tamEntero;
        int numeroDePaginas = (nf1 * nc1 + nc1 * nc2 + nf1 * nc2) / elementosPorPagina;

        try (FileWriter fileWriter = new FileWriter("referencias.txt");
             PrintWriter writer = new PrintWriter(fileWriter)) {

            writer.println("TP=" + tamPagina);
            writer.println("NF=" + nf1);
            writer.println("NC1=" + nc1);
            writer.println("NC2=" + nc2);
            writer.println("NR=" + ((((nc1 + nc1) * nc2) * nf1) + (nf1 * nc2)));
            writer.println("NP=" + numeroDePaginas);

            MultiplicacionMatrices multiplicacionMatrices = new MultiplicacionMatrices();
            multiplicacionMatrices.multiplicar_matrices(nf1, nc1, nc2, writer, this);            

            System.out.println("Referencias generadas y guardadas en 'referencias.txt'.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void generarPaginaDesplazamiento(int i, int j, int k, boolean isMatrizC, PrintWriter writer) {

        if (isMatrizC) {
            int paginaC = ((nf1 * nc1 + nc1 * nc2) + i * nc2 + j) / elementosPorPagina;
            int offsetC = ((2 * nf1 * nc1 + i * nc2 + j) % elementosPorPagina) * tamEntero;
            writer.println("[C-" + i + "-" + j + "], " + paginaC + ", " + offsetC);
        } 
        else {
            int paginaA = (i * nc1 + k) / elementosPorPagina;
            int offsetA = ((i * nc1 + k) % elementosPorPagina) * tamEntero;
            writer.println("[A-" + i + "-" + k + "], " + paginaA + ", " + offsetA);

            int paginaB = (nf1 * nc1 + k * nc2 + j) / elementosPorPagina;
            int offsetB = ((nf1 * nc1 + k * nc2 + j) % elementosPorPagina) * tamEntero;
            writer.println("[B-" + k + "-" + j + "], " + paginaB + ", " + offsetB);
        }
    }
}
