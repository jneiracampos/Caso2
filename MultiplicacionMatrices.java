import java.io.PrintWriter;

public class MultiplicacionMatrices {

    private int[][] mat1;
    private int[][] mat2;
    private int[][] mat3;

    public void multiplicar_matrices(int nf1, int nc1, int nc2, PrintWriter writer, ReferenciaGenerador referencia) {
        inicializar_matrices(nf1, nc1, nc2);
        for (int i=0; i<nf1; i++) {
            for (int j=0; j<nc2; j++) {
                int acum = 0;
                for (int k=0; k<nc1; k++) {
                    acum += mat1[i][k] * mat2[k][j];
                    referencia.generarPaginaDesplazamiento(i, j, k, false, writer);
                }
                mat3[i][j] = acum;
                referencia.generarPaginaDesplazamiento(i, j, 0, true, writer);
            }
        }
    }

    public void inicializar_matrices(int nf1, int nc1, int nc2) {
        mat1 = new int[nf1][nc1];
        mat2 = new int[nc1][nc2];
        mat3 = new int[nf1][nc2];
        llenar_matriz(mat1, nf1, nc1);
        llenar_matriz(mat2, nc1, nc2);
    }

    public void llenar_matriz(int[][] mat, int nf, int nc) {
        for (int i=0; i<nf; i++) {
            for (int j=0; j<nc; j++) {
                mat[i][j] = (int) (Math.random() * 10);
            }
        }
    }

}
