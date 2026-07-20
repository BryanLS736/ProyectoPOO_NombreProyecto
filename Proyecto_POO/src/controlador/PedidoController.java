package controlador;

import interfaces.IProductoDAO;
import modelo.Producto;
import utilidades.ItemCarrito;
import utilidades.Mensajes;

import java.util.ArrayList;
import java.util.List;

// Controller
public class PedidoController {

    private final IProductoDAO productoDAO;
    private final List<ItemCarrito> carrito;

    public PedidoController(IProductoDAO productoDAO) {
        this.productoDAO = productoDAO;
        this.carrito = new ArrayList<>();
    }

    // Agregar un producto de la tabla productos a carrito
    public boolean agregarProducto(Producto producto, String cantidadTexto) throws Exception {
        if (producto == null) {
            Mensajes.error("Selecciona un producto");
            return false;
        }

        int cantidad;
        if (cantidadTexto == null || cantidadTexto.trim().isEmpty()) {
            cantidad = 1;
        } else {
            try {
                cantidad = Integer.parseInt(cantidadTexto.trim());
            } catch (NumberFormatException e) {
                Mensajes.error("La cantidad debe ser un número entero");
                return false;
            }
            if (cantidad <= 0) {
                Mensajes.error("La cantidad debe ser mayor a 0");
                return false;
            }
        }

        // stock fresco, no el productosOriginales de la vista
        Producto actual = productoDAO.buscarProductoPorID(producto.getIdProducto());
        if (actual == null) {
            Mensajes.error("El producto ya no existe");
            return false;
        }

        int cantidadYaEnCarrito = obtenerCantidadEnCarrito(actual.getIdProducto());
        if (actual.getStock() < cantidadYaEnCarrito + cantidad) {
            Mensajes.error("Stock insuficiente. Disponible: " + (actual.getStock() - cantidadYaEnCarrito));
            return false;
        }

        ItemCarrito existente = buscarItemPorProducto(actual.getIdProducto());
        if (existente != null) {
            existente.setCantidad(existente.getCantidad() + cantidad);
        } else {
            carrito.add(new ItemCarrito(actual, cantidad));
        }

        return true;
    }

    // Editar la cantidad de un producto de la tabla carrito
    public boolean editarCantidad(int filaSeleccionada, String cantidadTexto) throws Exception {
        if (filaSeleccionada < 0 || filaSeleccionada >= carrito.size()) {
            Mensajes.error("Selecciona un producto del carrito");
            return false;
        }

        if (cantidadTexto == null || cantidadTexto.trim().isEmpty()) {
            Mensajes.error("Ingresa una cantidad");
            return false;
        }

        int cantidadNueva;
        try {
            cantidadNueva = Integer.parseInt(cantidadTexto.trim());
        } catch (NumberFormatException e) {
            Mensajes.error("La cantidad debe ser un número entero");
            return false;
        }

        if (cantidadNueva <= 0) {
            Mensajes.error("La cantidad debe ser mayor a 0");
            return false;
        }

        ItemCarrito item = carrito.get(filaSeleccionada);

        Producto actual = productoDAO.buscarProductoPorID(item.getProducto().getIdProducto());
        if (actual == null) {
            Mensajes.error("El producto ya no existe");
            return false;
        }

        if (actual.getStock() < cantidadNueva) {
            Mensajes.error("Stock insuficiente. Disponible: " + actual.getStock());
            return false;
        }

        item.setCantidad(cantidadNueva);
        return true;
    }

    // Eliminar un producto del carrito

    public boolean eliminarProducto(int filaSeleccionada) {
        if (filaSeleccionada < 0 || filaSeleccionada >= carrito.size()) {
            Mensajes.error("Selecciona un producto del carrito");
            return false;
        }
        carrito.remove(filaSeleccionada);
        return true;
    }

    // Calculo de precios: total, subtotal e igv

    public double calcularTotal() {
        return carrito.stream().mapToDouble(ItemCarrito::getPrecioTotal).sum();
    }

    public double calcularSubtotal() {
        return calcularTotal() / 1.18;
    }

    public double calcularIgv() {
        return calcularTotal() - calcularSubtotal();
    }

    // Métodos auxiliares
    public List<ItemCarrito> obtenerCarrito() {
        return carrito;
    }

    public void vaciarCarrito() {
        carrito.clear();
    }

    public int obtenerCantidadEnCarrito(int idProducto) {
        ItemCarrito item = buscarItemPorProducto(idProducto);
        return item != null ? item.getCantidad() : 0;
    }

    private ItemCarrito buscarItemPorProducto(int idProducto) {
        for (ItemCarrito item : carrito) {
            if (item.getProducto().getIdProducto() == idProducto) {
                return item;
            }
        }
        return null;
    }
}