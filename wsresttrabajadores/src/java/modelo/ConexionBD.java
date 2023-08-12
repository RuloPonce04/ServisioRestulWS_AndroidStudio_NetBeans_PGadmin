package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    public static Connection conexion;

    public static boolean establecerConexion() {
        try {
            Class.forName("org.postgresql.Driver");
            conexion = DriverManager.getConnection("jdbc:postgresql://localhost:5432/capaflet", "postgres", "admin");
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("Error: " + ex);
            return false;
        }
        return true;
    }

    public static void cerrarConexion() {
        try {
            conexion.close();
        } catch (SQLException ex) {
            System.out.println("ERROR: " + ex);
        }
    }
    public static boolean probarConexion() {
        if (establecerConexion()) {
            cerrarConexion();
            return true;
        }
        return false;
    }
}
