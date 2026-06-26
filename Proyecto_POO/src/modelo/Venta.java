package modelo;

import java.time.LocalDate;
import java.time.LocalTime;

public class Venta {

    // Atributos
    private int idVenta;
    private int idEmpleado;
    private int idCaja;
    private int idCliente;
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
    public Venta(int idVenta, int idEmpleado, int idCaja, int idCliente,
            LocalDate fechaVenta, LocalTime horaVenta, String tipoDespacho,
            String notaAdicional, double totalVenta, String metodoPago) {

        this.idVenta = idVenta;
        this.idEmpleado = idEmpleado;
        this.idCaja = idCaja;
        this.idCliente = idCliente;
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

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public int getIdCaja() {
        return idCaja;
    }

    public void setIdCaja(int idCaja) {
        this.idCaja = idCaja;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
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
        return "Venta{" +
                "idVenta=" + idVenta +
                ", idEmpleado=" + idEmpleado +
                ", idCaja=" + idCaja +
                ", idCliente=" + idCliente +
                ", fechaVenta=" + fechaVenta +
                ", horaVenta=" + horaVenta +
                ", tipoDespacho='" + tipoDespacho + '\'' +
                ", notaAdicional='" + notaAdicional + '\'' +
                ", totalVenta=" + totalVenta +
                ", metodoPago='" + metodoPago + '\'' +
                '}';
    }
}