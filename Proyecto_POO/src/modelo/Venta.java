package modelo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Venta {

    // Atributos
    private int idVenta;
    private Empleado empleado;
    private Caja caja;
    private Cliente cliente;
    private String nombreCliente;
    private String direccionEntrega;
    private String telefonoContacto;
    private String dniCliente;
    private List<DetalleVenta> detalles;
    private LocalDate fechaVenta;
    private LocalTime horaVenta;
    private String tipoDespacho;
    private String notaAdicional;
    private double totalVenta;
    private String metodoPago;

    // Constructor vacío
    public Venta() {
    }

    // Constructor con todos los atributos
    public Venta(int idVenta, Empleado empleado, Caja caja, Cliente cliente, String nombreCliente, String direccionEntrega, String telefonoContacto, String dniCliente, List<DetalleVenta> detalles, LocalDate fechaVenta, LocalTime horaVenta, String tipoDespacho, String notaAdicional, double totalVenta, String metodoPago) {
        this.idVenta = idVenta;
        this.empleado = empleado;
        this.caja = caja;
        this.cliente = cliente;
        this.nombreCliente = nombreCliente;
        this.direccionEntrega = direccionEntrega;
        this.telefonoContacto = telefonoContacto;
        this.dniCliente = dniCliente;
        this.detalles = detalles;
        this.fechaVenta = fechaVenta;
        this.horaVenta = horaVenta;
        this.tipoDespacho = tipoDespacho;
        this.notaAdicional = notaAdicional;
        this.totalVenta = totalVenta;
        this.metodoPago = metodoPago;
    }

    // Getters y Setters
    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public Caja getCaja() {
        return caja;
    }

    public void setCaja(Caja caja) {
        this.caja = caja;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getDireccionEntrega() {
        return direccionEntrega;
    }

    public void setDireccionEntrega(String direccionEntrega) {
        this.direccionEntrega = direccionEntrega;
    }

    public String getTelefonoContacto() {
        return telefonoContacto;
    }

    public void setTelefonoContacto(String telefonoContacto) {
        this.telefonoContacto = telefonoContacto;
    }

    public String getDniCliente() {
        return dniCliente;
    }

    public void setDniCliente(String dniCliente) {
        this.dniCliente = dniCliente;
    }

    public List<DetalleVenta> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleVenta> detalles) {
        this.detalles = detalles;
    }

    public LocalDate getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(LocalDate fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public LocalTime getHoraVenta() {
        return horaVenta;
    }

    public void setHoraVenta(LocalTime horaVenta) {
        this.horaVenta = horaVenta;
    }

    public String getTipoDespacho() {
        return tipoDespacho;
    }

    public void setTipoDespacho(String tipoDespacho) {
        this.tipoDespacho = tipoDespacho;
    }

    public String getNotaAdicional() {
        return notaAdicional;
    }

    public void setNotaAdicional(String notaAdicional) {
        this.notaAdicional = notaAdicional;
    }

    public double getTotalVenta() {
        return totalVenta;
    }

    public void setTotalVenta(double totalVenta) {
        this.totalVenta = totalVenta;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    // toString()

    @Override
    public String toString() {
        return "Venta{" + "idVenta=" + idVenta + ", empleado=" + empleado + ", caja=" + caja + ", detalles=" + detalles + ", fechaVenta=" + fechaVenta + ", horaVenta=" + horaVenta + ", tipoDespacho=" + tipoDespacho + ", notaAdicional=" + notaAdicional + ", totalVenta=" + totalVenta + ", metodoPago=" + metodoPago + '}';
    }
    
}