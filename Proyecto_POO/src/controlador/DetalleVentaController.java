package controlador;

import dao.DetalleVentaDAO;
import dao.VentaDAO;
import interfaces.IDetalleVentaDAO;
import interfaces.IVentaDAO;
import java.util.List;
import modelo.DetalleVenta;

public class DetalleVentaController {

    private final IDetalleVentaDAO detalleVentaDAO;
    private final IVentaDAO ventaDAO;

    public DetalleVentaController() {
        this.detalleVentaDAO = new DetalleVentaDAO();
        this.ventaDAO = new VentaDAO();
    }

    public List<DetalleVenta> buscarDetallesPorVenta(int idVenta) throws Exception {
        if (idVenta <= 0) {
            throw new Exception("El ID de la venta no es válido.");
        }

        // Verificar que la venta exista antes de buscar sus detalles
        if (ventaDAO.buscarVentaPorID(idVenta) == null) {
            throw new Exception("No se encontró ninguna venta con el ID: " + idVenta);
        }

        List<DetalleVenta> lista = detalleVentaDAO.buscarDetallesPorVenta(idVenta);

        if (lista.isEmpty()) {
            throw new Exception("La venta con ID " + idVenta + " no tiene detalles registrados.");
        }

        return lista;
    }

    public DetalleVenta buscarDetallePorID(int id) throws Exception {
        if (id <= 0) {
            throw new Exception("El ID del detalle de venta no es válido.");
        }

        DetalleVenta detalle = detalleVentaDAO.buscarDetallePorID(id);

        if (detalle == null) {
            throw new Exception("No se encontró ningún detalle de venta con el ID: " + id);
        }

        return detalle;
    }
}