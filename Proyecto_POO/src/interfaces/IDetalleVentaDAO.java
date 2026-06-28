package interfaces;

import java.util.List;
import modelo.DetalleVenta;

public interface IDetalleVentaDAO {
    List<DetalleVenta> buscarDetallesPorVenta(int idVenta) throws Exception;

    DetalleVenta buscarDetallePorID(int id) throws Exception;
}
