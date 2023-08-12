package modelo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TrabajadorBean {
    private Trabajadores trabajador = new Trabajadores();

    public Trabajadores buscarTrabajador(String nomina) {
    Trabajadores trabajador = null;

    if (ConexionBD.establecerConexion()) {
        try {
            String sql = "SELECT * FROM registros.trabajadores WHERE nomina = ?";
            PreparedStatement sentencia = ConexionBD.conexion.prepareStatement(sql);
            sentencia.setString(1, nomina); // Pasar directamente el valor de nomina como cadena de caracteres
            ResultSet consulta = sentencia.executeQuery();

            if (consulta.next()) {
                trabajador = new Trabajadores();
                trabajador.setNomina(nomina);
                trabajador.setNombre(consulta.getString("nombre"));
                trabajador.setPuesto(consulta.getString("puesto"));
            }
        } catch (SQLException ex) {
            System.out.println("ERROR: " + ex);
        } finally {
            ConexionBD.cerrarConexion();
        }
    }

    return trabajador;
}


    public boolean agregarTrabajador(Trabajadores trabajador) {
        if (ConexionBD.establecerConexion()) {
            try {
                String sql = "INSERT INTO registros.trabajadores(nomina, nombre, puesto) VALUES (?, ?, ?)";
                PreparedStatement sentencia = ConexionBD.conexion.prepareStatement(sql);
                sentencia.setInt(1, Integer.parseInt(trabajador.getNomina()));
                sentencia.setString(2, trabajador.getNombre());
                sentencia.setString(3, trabajador.getPuesto());

                sentencia.executeUpdate();

                ConexionBD.cerrarConexion();
                return true;
            } catch (SQLException ex) {
                Logger.getLogger(TrabajadorBean.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                ConexionBD.cerrarConexion();
            }
        }

        return false;
    }
    
    public boolean actualizarTrabajador(Trabajadores trabajador) {
    if (ConexionBD.establecerConexion()) {
        try {
            String sql = "UPDATE registros.trabajadores"
                    + " SET nombre=?, puesto=?"
                    + " WHERE nomina=?;";
            PreparedStatement sentencia = ConexionBD.conexion.prepareStatement(sql);

            sentencia.setString(1, trabajador.getNombre());
            sentencia.setString(2, trabajador.getPuesto());
            sentencia.setString(3, trabajador.getNomina()); // Nómina como cadena

            int filasActualizadas = sentencia.executeUpdate();

            ConexionBD.cerrarConexion();
            
            // Si se actualizó al menos una fila, retorna true; de lo contrario, false.
            return filasActualizadas > 0;
        } catch (SQLException ex) {
            Logger.getLogger(TrabajadorBean.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConexionBD.cerrarConexion();
        }
    }

        return false;
    }
    
    public boolean eliminarTrabajador(String nomina) {
        if (ConexionBD.establecerConexion()) {
            try {
                String sql = "DELETE FROM registros.trabajadores WHERE nomina = ?";
                PreparedStatement sentencia = ConexionBD.conexion.prepareStatement(sql);
                sentencia.setString(1, nomina);
                System.out.println("Nomina -> "+nomina);

                int filasEliminadas = sentencia.executeUpdate();

                ConexionBD.cerrarConexion();

                // Si se eliminó al menos una fila, retorna true; de lo contrario, false.
                return filasEliminadas > 0;
            } catch (SQLException ex) {
                Logger.getLogger(TrabajadorBean.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                ConexionBD.cerrarConexion();
            }
        }
        
        return false;
    }
    
    public List<Trabajadores> obtenerTodosLosTrabajadores() {
    List<Trabajadores> lista = new ArrayList<>();

    if (ConexionBD.establecerConexion()) {
        try {
            String sql = "SELECT * FROM registros.trabajadores";
            PreparedStatement sentencia = ConexionBD.conexion.prepareStatement(sql);
            ResultSet consulta = sentencia.executeQuery();

            while (consulta.next()) {
                Trabajadores trabajador = new Trabajadores();
                trabajador.setNomina(consulta.getString("nomina"));
                trabajador.setNombre(consulta.getString("nombre"));
                trabajador.setPuesto(consulta.getString("puesto"));
                lista.add(trabajador);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TrabajadorBean.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConexionBD.cerrarConexion();
        }
    }

    return lista;
}




    public Trabajadores getTrabajador() {
        return trabajador;
    }

    public void setTrabajador(Trabajadores trabajador) {
        this.trabajador = trabajador;
    }
}
