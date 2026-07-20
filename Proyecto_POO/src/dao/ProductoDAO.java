package dao;

import conexion.Conexion;
import interfaces.IProductoDAO;
import java.util.List;
import modelo.Producto;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import utilidades.Constantes;
import utilidades.ResumenStock;

public class ProductoDAO implements IProductoDAO{

    @Override
    public void registrarProducto(Producto producto) throws Exception {
        String sql = """
                     INSERT INTO Producto
                     (
                        nombre,
                        categoria,
                        stock,
                        precio
                     )
                     VALUES (?, ?, ?, ?)
                     """;
        
        try (Connection conn = new Conexion().conectar();
             PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setString(1, producto.getNombre());
            ps.setString(2, producto.getCategoria());
            ps.setInt(3, producto.getStock());
            ps.setDouble(4, producto.getPrecio());
            ps.executeUpdate();
            
        } catch (SQLException e) {
            throw new Exception("Error al registrar producto: " + e.getMessage());
        }
    }

    @Override
    public void actualizarProducto(Producto producto) throws Exception {
        String sql = """
                     UPDATE Producto 
                     SET 
                     nombre = ?,
                     categoria = ?,
                     stock = ?,
                     precio = ?
                     WHERE id_producto = ?
                     """;
        try (Connection conn = new Conexion().conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
            ) {
            ps.setString(1, producto.getNombre());
            ps.setString(2, producto.getCategoria());
            ps.setInt(3, producto.getStock());
            ps.setDouble(4, producto.getPrecio());
            ps.setInt(5, producto.getIdProducto());
            
            int filas = ps.executeUpdate();
            
            if (filas == 0) {
                throw new Exception("No se encontró el producto a actualizar (ID inválido).");
            }
            
        } catch (SQLException e) {
            throw new Exception("Error al actualizar producto: " + e.getMessage());
        }
    }

    @Override
    public List<Producto> verTodosLosProductos() throws Exception {
        List<Producto> listaProductos = new ArrayList<>();
        String sql = """
                     SELECT *
                     FROM Producto
                     """;
        try (Connection conn = new Conexion().conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery();
            ) {
            
            while (rs.next()) {
                listaProductos.add(mapearProducto(rs));
            }
            return listaProductos;
        } catch (SQLException e) {
            throw new Exception("Error al cargar la lista de los productos: " + e.getMessage());
        }
    }

    @Override
    public List<Producto> verProductosPorEstado(boolean activo) throws Exception {
        List<Producto> listaProductos = new ArrayList<>();
        String sql = """
                     SELECT *
                     FROM Producto
                     WHERE activo = ?
                     """;
        try (Connection conn = new Conexion().conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
            ) {
            
            ps.setBoolean(1, activo);
            
            try (ResultSet rs = ps.executeQuery();) {
                while (rs.next()) {
                    listaProductos.add(mapearProducto(rs));
                }
            }
            
            return listaProductos;
        } catch (SQLException e) {
            throw new Exception("Error al cargar la lista de los productos filtrados por estado: " + e.getMessage());
        }
    }

    @Override
    public void cambiarEstadoProducto(int idProducto, boolean activo) throws Exception {
        String sql = """
                     UPDATE Producto 
                     SET activo = ?
                     WHERE id_producto = ?
                     """;
        try (Connection conn = new Conexion().conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
            ) {
            ps.setBoolean(1, activo);
            ps.setInt(2, idProducto);
            ps.executeUpdate();
            
        } catch (SQLException e) {
            throw new Exception("Error al cambiar el estado del producto: " + e.getMessage());
        }
    }

    @Override
    public Producto buscarProductoPorID(int id) throws Exception {
        Producto producto = null;
        
        String sql = """
                     SELECT *
                     FROM Producto
                     WHERE id_producto = ?
                     """;
        try (Connection conn = new Conexion().conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
            ) {
            
            ps.setInt(1, id);
            
            try (ResultSet rs = ps.executeQuery();) {
                if (rs.next()) {
                    producto = mapearProducto(rs);
                }
            }
            
            return producto;
        } catch (SQLException e) {
            throw new Exception("Error al buscar el producto por ID: " + e.getMessage());
        }
    }

    @Override
    public List<Producto> buscarProductoPorNombre(String nombre) throws Exception {
        List<Producto> listaProductos = new ArrayList<>();
        String sql = """
                     SELECT *
                     FROM Producto
                     WHERE nombre LIKE ?
                     """;
        try (Connection conn = new Conexion().conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
            ) {
            
            ps.setString(1, "%" + nombre + "%");
            
            try (ResultSet rs = ps.executeQuery();) {
                while (rs.next()) {
                    listaProductos.add(mapearProducto(rs));
                }
            }
            
            return listaProductos;
        } catch (SQLException e) {
            throw new Exception("Error al cargar la lista de los productos filtrados por nombre: " + e.getMessage());
        }
    }

    @Override
    public List<Producto> buscarProductoPorCategoria(String categoria) throws Exception {
        List<Producto> listaProductos = new ArrayList<>();
        String sql = """
                     SELECT *
                     FROM Producto
                     WHERE categoria = ?
                     """;
        try (Connection conn = new Conexion().conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
            ) {
            
            ps.setString(1, categoria);
            
            try (ResultSet rs = ps.executeQuery();) {
                while (rs.next()) {
                    listaProductos.add(mapearProducto(rs));
                }
            }
            
            return listaProductos;
        } catch (SQLException e) {
            throw new Exception("Error al cargar la lista de los productos filtrados por categoria: " + e.getMessage());
        }
    }

    @Override
    public List<Producto> buscarProductoPorStock(Integer stockMin, Integer stockMax) throws Exception {
        List<Producto> listaProductos = new ArrayList<>();
        String sql = """
                     SELECT *
                     FROM Producto
                     WHERE stock BETWEEN ? AND ?
                     """;
        try (Connection conn = new Conexion().conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
            ) {
            
            ps.setInt(1, stockMin == null ? 0 : stockMin);
            ps.setInt(2, stockMax == null ? Integer.MAX_VALUE : stockMax);
                        
            try (ResultSet rs = ps.executeQuery();) {
                while (rs.next()) {
                    listaProductos.add(mapearProducto(rs));
                }
            }
            
            return listaProductos;
        } catch (SQLException e) {
            throw new Exception("Error al cargar la lista de los productos filtrados por stock: " + e.getMessage());
        }
    }

    @Override
    public List<Producto> buscarProductoPorPrecio(Double precioMin, Double precioMax) throws Exception {
        List<Producto> listaProductos = new ArrayList<>();
        String sql = """
                     SELECT *
                     FROM Producto
                     WHERE precio BETWEEN ? AND ?
                     """;
        try (Connection conn = new Conexion().conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
            ) {
            
            ps.setDouble(1, precioMin == null ? 0 : precioMin);
            ps.setDouble(2, precioMax == null ? Double.MAX_VALUE : precioMax);
                        
            try (ResultSet rs = ps.executeQuery();) {
                while (rs.next()) {
                    listaProductos.add(mapearProducto(rs));
                }
            }
            
            return listaProductos;
        } catch (SQLException e) {
            throw new Exception("Error al cargar la lista de los productos filtrados por precio: " + e.getMessage());
        }
    }
    
    @Override
    public List<Producto> listarConFiltros(String nombre, String categoria, String estadoStock) throws Exception {
        List<Producto> listaProductos = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM Producto WHERE 1=1");
        List<Object> parametros = new ArrayList<>();

        // Se anidan los filtros si en caso se solicitan
        if (nombre != null && !nombre.isBlank()) {
            sql.append(" AND nombre LIKE ?");
            parametros.add("%" + nombre + "%");
        }

        if (categoria != null && !categoria.equalsIgnoreCase("Todos")) {
            sql.append(" AND categoria = ?");
            parametros.add(categoria);
        }

        if (estadoStock != null && !estadoStock.equalsIgnoreCase("Todos")) {
            switch (estadoStock) {
                case "Sin stock" ->
                    sql.append(" AND stock = 0");
                case "Bajo" -> {
                    sql.append(" AND stock > 0 AND stock <= ?");
                    parametros.add(Constantes.STOCK_BAJO_UMBRAL);
                }
                case "Suficiente" -> {
                    sql.append(" AND stock > ?");
                    parametros.add(Constantes.STOCK_BAJO_UMBRAL);
                }
            }
        }

        try (Connection conn = new Conexion().conectar(); PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < parametros.size(); i++) {
                ps.setObject(i + 1, parametros.get(i));
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    listaProductos.add(mapearProducto(rs));
                }
            }

            return listaProductos;

        } catch (SQLException e) {
            throw new Exception("Error al filtrar productos: " + e.getMessage());
        }
    }
    
    @Override
    public ResumenStock obtenerResumenStock() throws Exception {
        String sql = """
                 SELECT
                    COUNT(*) AS total,
                    SUM(CASE WHEN stock = 0 THEN 1 ELSE 0 END) AS sin_stock,
                    SUM(CASE WHEN stock > 0 AND stock <= ? THEN 1 ELSE 0 END) AS bajo,
                    SUM(CASE WHEN stock > ? THEN 1 ELSE 0 END) AS suficiente
                 FROM Producto
                 """;

        try (Connection conn = new Conexion().conectar(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, Constantes.STOCK_BAJO_UMBRAL);
            ps.setInt(2, Constantes.STOCK_BAJO_UMBRAL);

            try (ResultSet rs = ps.executeQuery()) {
                ResumenStock resumen = new ResumenStock();
                if (rs.next()) {
                    resumen.setTotal(rs.getInt("total"));
                    resumen.setSinStock(rs.getInt("sin_stock"));
                    resumen.setBajo(rs.getInt("bajo"));
                    resumen.setSuficiente(rs.getInt("suficiente"));
                }
                return resumen;
            }

        } catch (SQLException e) {
            throw new Exception("Error al obtener el resumen de stock: " + e.getMessage());
        }
    }

    private Producto mapearProducto(ResultSet rs) throws SQLException {
        Producto producto = new Producto();

        producto.setIdProducto(rs.getInt("id_producto"));
        producto.setNombre(rs.getString("nombre"));
        producto.setCategoria(rs.getString("categoria"));
        producto.setStock(rs.getInt("stock"));
        producto.setPrecio(rs.getDouble("precio"));
        producto.setUnidMedida(rs.getString("unid_medida"));
        producto.setActivo(rs.getBoolean("activo"));
        producto.setFechaCreacion(rs.getTimestamp("fecha_creacion").toLocalDateTime());
        producto.setFechaModificacion(rs.getTimestamp("fecha_modificacion").toLocalDateTime());

        return producto;
    }
    
}
