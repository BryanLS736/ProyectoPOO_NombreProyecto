package controlador;

import dao.CajaDAO;
import dao.VentaDAO;
import interfaces.ICajaDAO;
import interfaces.IVentaDAO;
import java.time.LocalDate;
import java.util.List;
import modelo.Caja;
import modelo.Venta;
import utilidades.Validaciones;

public class VentaController {

    private final IVentaDAO ventaDAO;
    private final ICajaDAO cajaDAO;

    public VentaController() {
        this.ventaDAO = new VentaDAO();
        this.cajaDAO = new CajaDAO();
    }

    public void registrarVenta(Venta venta) throws Exception {
        if (venta == null) {
            throw new Exception("La venta no puede ser nula.");
        }
        if (venta.getEmpleado() == null || venta.getEmpleado().getIdEmpleado() <= 0) {
            throw new Exception("Debe asignar un empleado válido a la venta.");
        }
        if (venta.getCaja() == null || venta.getCaja().getIdCaja() <= 0) {
            throw new Exception("Debe asignar una caja válida a la venta.");
        }
        if (venta.getCliente() != null && venta.getCliente().getIdCliente() <= 0) {
            throw new Exception("El cliente asignado no es válido.");
        }
        if (venta.getNombreCliente() != null && !Validaciones.soloLetras(venta.getNombreCliente())) {
            throw new Exception("El nombre solo puede contener letras.");
        }
        if (venta.getTelefonoContacto() != null) {
            if (!Validaciones.soloNumeros(venta.getTelefonoContacto())) {
                throw new Exception("El telefono solo puede tener numeros.");
            }
            if (!(venta.getTelefonoContacto().length() == 7) && !(venta.getTelefonoContacto().length() == 9)) {
                throw new Exception("El telefono debe tener 7 numeros para fijo y 9 para numero celular.");
            }
            if (venta.getTelefonoContacto().length() == 7 && venta.getTelefonoContacto().startsWith("1")) {
                throw new Exception("El telefono fijo debe empezar por cualquier numero, menos el 1");
            }
            if (venta.getTelefonoContacto().length() == 9 && !venta.getTelefonoContacto().startsWith("9")) {
                throw new Exception("El telefono celular debe empezar con el numero 9.");
            }
        }
        if (venta.getDniCliente() != null) {
            if (!Validaciones.soloNumeros(venta.getDniCliente())) {
                throw new Exception("El dni solo puede tener numeros.");
            }
            if (!(venta.getDniCliente().length() == 8)) {
                throw new Exception("El dni debe tener solo 8 números.");
            }
        }
        if (Validaciones.campoVacio(venta.getTipoDespacho())) {
            throw new Exception("Seleccione el tipo de despacho.");
        }
        if (Validaciones.campoVacio(venta.getMetodoPago())) {
            throw new Exception("Seleccione el método de pago.");
        }
        if (venta.getTotalVenta() <= 0) {
            throw new Exception("El total de la venta debe ser mayor a 0.");
        }

        // Verificar que la caja asignada exista y esté abierta
        Caja caja = cajaDAO.buscarCajaPorID(venta.getCaja().getIdCaja());
        if (caja == null) {
            throw new Exception("La caja asignada no existe.");
        }
        if (!"Abierta".equals(caja.getEstado())) {
            throw new Exception("No se puede registrar una venta en una caja cerrada.");
        }

        ventaDAO.registrarVenta(venta);
    }

    public Venta buscarVentaPorID(int id) throws Exception {
        if (id <= 0) {
            throw new Exception("El ID de la venta no es válido.");
        }

        Venta venta = ventaDAO.buscarVentaPorID(id);

        if (venta == null) {
            throw new Exception("No se encontró ninguna venta con el ID: " + id);
        }

        return venta;
    }

    public List<Venta> verTodasLasVentas() throws Exception {
        List<Venta> lista = ventaDAO.verTodasLasVentas();

        if (lista.isEmpty()) {
            throw new Exception("No hay ventas registradas.");
        }

        return lista;
    }

    public List<Venta> buscarVentaPorRangoDeFechas(LocalDate fechaInicio, LocalDate fechaFinal) throws Exception {
        if (fechaInicio == null || fechaFinal == null) {
            throw new Exception("Las fechas de inicio y fin son obligatorias.");
        }
        if (fechaInicio.isAfter(fechaFinal)) {
            throw new Exception("La fecha de inicio no puede ser posterior a la fecha final.");
        }

        List<Venta> lista = ventaDAO.buscarVentaPorRangoDeFechas(fechaInicio, fechaFinal);

        if (lista.isEmpty()) {
            throw new Exception("No se encontraron ventas en el rango de fechas indicado.");
        }

        return lista;
    }

    public List<Venta> buscarVentaPorDniCliente(String dni) throws Exception {
        if (Validaciones.campoVacio(dni)
                || !Validaciones.soloNumeros(dni)
                || !Validaciones.longitudMinima(dni, 8)
                || !Validaciones.longitudMaxima(dni, 8)) {
            throw new Exception("El DNI debe tener exactamente 8 dígitos numéricos.");
        }

        List<Venta> lista = ventaDAO.buscarVentaPorDniCliente(dni);

        if (lista.isEmpty()) {
            throw new Exception("No se encontraron ventas para el cliente con DNI: " + dni);
        }

        return lista;
    }

    public List<Venta> buscarVentaPorNombreCliente(String nombre) throws Exception {
        if (Validaciones.campoVacio(nombre) || !Validaciones.soloLetras(nombre)) {
            throw new Exception("Ingrese un nombre válido para realizar la búsqueda.");
        }

        List<Venta> lista = ventaDAO.buscarVentaPorNombreCliente(nombre.trim());

        if (lista.isEmpty()) {
            throw new Exception("No se encontraron ventas para el cliente con nombre: " + nombre);
        }

        return lista;
    }

    public List<Venta> buscarVentaPorMetodoPago(String metodoPago) throws Exception {
        if (Validaciones.campoVacio(metodoPago)) {
            throw new Exception("Seleccione un método de pago para realizar la búsqueda.");
        }

        List<Venta> lista = ventaDAO.buscarVentaPorMetodoPago(metodoPago);

        if (lista.isEmpty()) {
            throw new Exception("No se encontraron ventas con el método de pago: " + metodoPago);
        }

        return lista;
    }
}