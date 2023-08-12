package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        if (ConexionBD.probarConexion()) {
            System.out.println("Conexión exitosa a la base de datos");
        } else {
            System.out.println("Error al conectar a la base de datos");
        }
    }
}
