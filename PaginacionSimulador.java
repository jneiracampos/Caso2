import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PaginacionSimulador {
    private int numeroMarcosPagina;
    private List<Integer> referencias;
    private Map<Integer, Integer> paginaEnvejecimiento;
    private int fallasDePagina;

    public PaginacionSimulador(int numeroMarcosPagina, List<Integer> referencias) {
        this.numeroMarcosPagina = numeroMarcosPagina;
        this.referencias = referencias;
        this.paginaEnvejecimiento = new HashMap<>();
        this.fallasDePagina = 0;
    }

    public void simularPaginacion() {
        for (int referencia : referencias) {
            if (!paginaEnvejecimiento.containsKey(referencia)) {
                if (paginaEnvejecimiento.size() < numeroMarcosPagina) {
                    paginaEnvejecimiento.put(referencia, 0);
                } else {
                    int paginaAReemplazar = encontrarPaginaAReemplazar();
                    paginaEnvejecimiento.remove(paginaAReemplazar);
                    paginaEnvejecimiento.put(referencia, 0);
                }
                fallasDePagina++;
            }

            // Actualizar envejecimiento de las páginas en memoria
            for (int pagina : paginaEnvejecimiento.keySet()) {
                paginaEnvejecimiento.put(pagina, paginaEnvejecimiento.get(pagina) + 1);
            }
        }
    }

    private int encontrarPaginaAReemplazar() {
        int paginaAReemplazar = -1;
        int maxEnvejecimiento = -1;
        for (int pagina : paginaEnvejecimiento.keySet()) {
            int envejecimiento = paginaEnvejecimiento.get(pagina);
            if (envejecimiento > maxEnvejecimiento) {
                maxEnvejecimiento = envejecimiento;
                paginaAReemplazar = pagina;
            }
        }
        return paginaAReemplazar;
    }

    public int getFallasDePagina() {
        return fallasDePagina;
    }
}
