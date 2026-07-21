package interfaces;

import java.time.LocalDate;
import java.util.List;
import modelo.Venta;

public interface IVentaDAO {
    void registrarVenta(Venta venta) throws Exception;

    List<Venta> verTodasLasVentas() throws Exception;
    
    Venta buscarVentaPorID(int id) throws Exception;

    List<Venta> buscarVentaPorRangoDeFechas(LocalDate fechaInicio, LocalDate fechaFinal) throws Exception;

    List<Venta> buscarVentaPorDniCliente(String dni) throws Exception;

    List<Venta> buscarVentaPorNombreCliente(String nombre) throws Exception;
    
    List<Venta> buscarVentaPorMetodoPago(String metodoPago) throws Exception;
    
    List<Venta> listarConFiltros(LocalDate fechaInicio, LocalDate fechaFinal, String tipoDespacho) throws Exception;
    
    double sumarVentasPorCaja(int idCaja) throws Exception;

}
