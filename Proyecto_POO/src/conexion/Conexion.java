package conexion;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;

public class Conexion {

    // Instancia única (Singleton)
    private static Conexion instancia;

    // Conexión a la base de datos
    private Connection conexion;

    // Configuración de la base de datos
    private static final String URL = "jdbc:mysql://localhost:3306/panaderia_db";
    private static final String USUARIO = "root";
    private static final String PASSWORD = "1234";

    // Constructor privado para evitar instanciación externa
    private Conexion() {
        try {
            conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(
                "Error al conectar con la base de datos", e);
        }
    }

    // Obtiene la única instancia de la clase
    public static Conexion getInstancia() {
        if (instancia == null) {
            instancia = new Conexion();
        }
        return instancia;
    }

    // Devuelve una conexión válida
    public Connection getConexion() throws SQLException {

        if (conexion == null || conexion.isClosed()) {
            conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD);
        }

        return conexion;
    }
}