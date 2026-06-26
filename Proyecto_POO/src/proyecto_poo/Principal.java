package proyecto_poo;

import conexion.Conexion;
import vista.area0Login.FormLogin;
import java.sql.*;

public class Principal {

    public static void main(String[] args) {
        Conexion con = new Conexion();
        
        FormLogin form = new FormLogin();
        form.setVisible(true);
        form.setLocationRelativeTo(null);
    }
    
}
