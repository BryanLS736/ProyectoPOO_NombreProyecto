package interfaces;

import java.util.List;
import modelo.Producto;

public interface IProductoDAO {
    void registrarProducto(Producto producto) throws Exception;
    void actualizarProducto(Producto producto) throws Exception;
    
    List<Producto> verTodosLosProductos() throws Exception;
    List<Producto> verProductosPorEstado(boolean activo) throws Exception;
    
    void cambiarEstadoProducto(int idProducto, boolean activo) throws Exception;
    
    Producto buscarProductoPorID(int id) throws Exception;
    List<Producto> buscarProductoPorNombre(String nombre) throws Exception;
    List<Producto> buscarProductoPorCategoria(String categoria) throws Exception;
    List<Producto> buscarProductoPorStock(Integer stockMin, Integer stockMax) throws Exception;
    List<Producto> buscarProductoPorPrecio(Double precioMin, Double precioMax) throws Exception;
}
