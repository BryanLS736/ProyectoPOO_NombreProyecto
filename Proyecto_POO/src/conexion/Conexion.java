package conexion;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;

public class Conexion {
    private static Conexion instancia;
    private Connection conexion;
    
    private final String URL = "";
    private final String USER = "";
    private final String PASSWORD = "";
    
    private Conexion() {
        try {
            conexion = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static Conexion getInstancia () {
        if (instancia == null) {
            instancia = new Conexion();
        }
        return instancia;
    }
    
    public Connection getConexion () {
        return conexion;
    }
    
}
