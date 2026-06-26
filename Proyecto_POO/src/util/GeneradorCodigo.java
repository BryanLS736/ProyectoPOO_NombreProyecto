package util;

public class GeneradorCodigo {

    private GeneradorCodigo() {
    }

    public static String generarCodigoProducto(int numero) {
        return String.format("P%03d", numero);
    }

    public static String generarCodigoEmpleado(int numero) {
        return String.format("EMP%03d", numero);
    }

    public static String generarCodigoBoleta(int numero) {
        return String.format("BO%03d", numero);
    }

}
