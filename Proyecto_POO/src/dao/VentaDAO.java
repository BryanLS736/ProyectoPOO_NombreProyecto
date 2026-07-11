package dao;

import conexion.Conexion;
import interfaces.IVentaDAO;
import java.time.LocalDate;
import java.util.List;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.Date;
import java.sql.Types;
import java.util.ArrayList;
import modelo.Caja;
import modelo.Empleado;
import modelo.Venta;
import utilidades.SQLUtils;

public class VentaDAO implements IVentaDAO {

    @Override
    public void registrarVenta(Venta venta) throws Exception {
        String sql = """
                     INSERT INTO Venta
                     (
                        id_empleado,
                        id_caja,
                        id_cliente,
                        nombre_cliente,
                        direccion_entrega,
                        telefono_contacto,
                        dni_cliente,
                        tipo_despacho,
                        nota_adicional,
                        total_venta,
                        metodo_pago
                     )
                     VALUES (?,?,?,?,?,?,?,?,?,?,?)
                     """;
        
        try (Connection conn = new Conexion().conectar();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ) {
            ps.setInt(1, venta.getEmpleado().getIdEmpleado());
            ps.setInt(2, venta.getCaja().getIdCaja());
            if (venta.getCliente() != null) {
                ps.setInt(3, venta.getCliente().getIdCliente());
            } else {
                ps.setNull(3, Types.INTEGER);
            }
            SQLUtils.setNullableString(ps, 4, venta.getNombreCliente(), Types.VARCHAR);
            SQLUtils.setNullableString(ps, 5, venta.getDireccionEntrega(), Types.VARCHAR);
            SQLUtils.setNullableString(ps, 6, venta.getTelefonoContacto(), Types.VARCHAR);
            SQLUtils.setNullableString(ps, 7, venta.getDniCliente(), Types.VARCHAR);
            ps.setString(8, venta.getTipoDespacho());
            SQLUtils.setNullableString(ps, 9, venta.getNotaAdicional(), Types.VARCHAR);
            ps.setDouble(10, venta.getTotalVenta());
            ps.setString(11, venta.getMetodoPago());
            ps.executeUpdate();
            
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    venta.setIdVenta(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new Exception("Error al registrar venta: " + e.getMessage());
        }
    }

    @Override
    public List<Venta> verTodasLasVentas() throws Exception {
        List<Venta> listaVentas = new ArrayList<>();
        String sql = """
                     SELECT *
                     FROM Venta
                     """;
        try (Connection conn = new Conexion().conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery();
            ) {
                
            while (rs.next()) {
                listaVentas.add(mapearVenta(rs));
            }
            return listaVentas;
        } catch (SQLException e) {
            throw new Exception("Error al cargar la lista de las ventas: " + e.getMessage());
        }
    }

    @Override
    public Venta buscarVentaPorID(int id) throws Exception {
        Venta venta = null;
        
        String sql = """
                     SELECT *
                     FROM Venta
                     WHERE id_venta = ?
                     """;
        try (Connection conn = new Conexion().conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
            ) {
            
            ps.setInt(1, id);
            
            try (ResultSet rs = ps.executeQuery();) {
                if (rs.next()) {
                    venta = mapearVenta(rs);
                }
            }
            
            return venta;
        } catch (SQLException e) {
            throw new Exception("Error al buscar la venta por ID: " + e.getMessage());
        }
    }

    @Override
    public List<Venta> buscarVentaPorRangoDeFechas(LocalDate fechaInicio, LocalDate fechaFinal) throws Exception {
        List<Venta> listaVentas = new ArrayList<>();
        
        String sql = """
                     SELECT *
                     FROM Venta
                     WHERE fecha_venta BETWEEN ? AND ?
                     """;
        try (Connection conn = new Conexion().conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
            ) {
            
            ps.setDate(1, Date.valueOf(fechaInicio));
            ps.setDate(2, Date.valueOf(fechaFinal));
            
            try (ResultSet rs = ps.executeQuery();) {
                while (rs.next()) {
                    listaVentas.add(mapearVenta(rs));
                }
            }
            
            return listaVentas;
        } catch (SQLException e) {
            throw new Exception("Error al buscar la venta por rango de fechas: " + e.getMessage());
        }
    }

    @Override
    public List<Venta> buscarVentaPorDniCliente(String dni) throws Exception {
        List<Venta> listaVentas = new ArrayList<>();
        
        String sql = """
                     SELECT *
                     FROM Venta v
                     WHERE dni _cliente = ?
                     """;
        try (Connection conn = new Conexion().conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
            ) {
            
            ps.setString(1, dni);
            
            try (ResultSet rs = ps.executeQuery();) {
                while (rs.next()) {
                    listaVentas.add(mapearVenta(rs));
                }
            }
            
            return listaVentas;
        } catch (SQLException e) {
            throw new Exception("Error al buscar la venta por dni del cliente: " + e.getMessage());
        }
    }

    @Override
    public List<Venta> buscarVentaPorNombreCliente(String nombre) throws Exception {
        List<Venta> listaVentas = new ArrayList<>();
        
        String sql = """
                     SELECT *
                     FROM Venta v
                     WHERE nombre_cliente LIKE ?
                     """;
        try (Connection conn = new Conexion().conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
            ) {
            
            ps.setString(1, "%" + nombre + "%");
            
            try (ResultSet rs = ps.executeQuery();) {
                while (rs.next()) {
                    listaVentas.add(mapearVenta(rs));
                }
            }
            
            return listaVentas;
        } catch (SQLException e) {
            throw new Exception("Error al buscar la venta por nombre del cliente: " + e.getMessage());
        }
    }

    @Override
    public List<Venta> buscarVentaPorMetodoPago(String metodoPago) throws Exception {
        List<Venta> listaVentas = new ArrayList<>();
        
        String sql = """
                     SELECT *
                     FROM Venta
                     WHERE metodo_pago = ?
                     """;
        try (Connection conn = new Conexion().conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
            ) {
            
            ps.setString(1, metodoPago);
            
            try (ResultSet rs = ps.executeQuery();) {
                while (rs.next()) {
                    listaVentas.add(mapearVenta(rs));
                }
            }
            
            return listaVentas;
        } catch (SQLException e) {
            throw new Exception("Error al buscar la venta metodo de pago: " + e.getMessage());
        }
    }
    
    @Override
    public double sumarVentasPorCaja(int idCaja) throws Exception {
        double total = 0;

        String sql = """
                 SELECT COALESCE(SUM(total_venta), 0) AS total_ventas
                 FROM Venta
                 WHERE id_caja = ?
                 """;

        try (Connection conn = new Conexion().conectar(); PreparedStatement ps = conn.prepareStatement(sql);) {

            ps.setInt(1, idCaja);

            try (ResultSet rs = ps.executeQuery();) {
                if (rs.next()) {
                    total = rs.getDouble("total_ventas");
                }
            }

            return total;
        } catch (SQLException e) {
            throw new Exception("Error al sumar las ventas de la caja: " + e.getMessage());
        }
    }
    
    private Venta mapearVenta(ResultSet rs) throws SQLException {
        Venta venta = new Venta();

        venta.setIdVenta(rs.getInt("id_venta"));
        
        Empleado empleado = new Empleado();
        empleado.setIdEmpleado(rs.getInt("id_empleado"));
        venta.setEmpleado(empleado);

        Caja caja = new Caja();
        caja.setIdCaja(rs.getInt("id_caja"));
        venta.setCaja(caja);
        
        venta.setNombreCliente(rs.getString("nombre_cliente"));
        venta.setDireccionEntrega(rs.getString("direccion_entrega"));
        venta.setTelefonoContacto(rs.getString("telefono_contacto"));
        venta.setDniCliente(rs.getString("dni_cliente"));
        
        venta.setFechaVenta(rs.getDate("fecha_venta").toLocalDate());
        venta.setHoraVenta(rs.getTime("hora_venta").toLocalTime());
        venta.setTipoDespacho(rs.getString("tipo_despacho"));
        venta.setNotaAdicional(rs.getString("nota_adicional"));
        
        venta.setTotalVenta(rs.getDouble("total_venta"));
        venta.setMetodoPago(rs.getString("metodo_pago"));
        return venta;
    }
}
