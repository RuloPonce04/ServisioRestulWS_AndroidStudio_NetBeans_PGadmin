package ws;

import com.google.gson.Gson;
import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import modelo.ConexionBD;
import modelo.Mensajes;
import modelo.TrabajadorBean;
import modelo.Trabajadores;

@WebService(serviceName = "ServiciosTrabajador")
public class ServiciosTrabajador {

    @WebMethod(operationName = "recuperarTrabajador")
    public String recuperarTrabajador(@WebParam(name = "nomina") String nomina) {
        TrabajadorBean trabajadorBean = new TrabajadorBean();
        Trabajadores trabajador = trabajadorBean.buscarTrabajador(nomina);

        Gson gson = new Gson();
        String trabajadorJson = gson.toJson(trabajador);

        return trabajadorJson;
    }

    @WebMethod(operationName = "testerbd")
    public String testerbd() {
        String mensaje = "";

        if (ConexionBD.establecerConexion()) {
            mensaje = "Conexion Exitosa";
            ConexionBD.cerrarConexion();
        } else {
            mensaje = "Error de conexion";
        }

        return mensaje;
    }

    @WebMethod(operationName = "actualizarTrabajador")
        public String actualizarTrabajador(@WebParam(name = "trabajador") String trabajadorJson) {
            TrabajadorBean trabajadorBean = new TrabajadorBean();
            Gson gson = new Gson();
            Trabajadores trabajador = gson.fromJson(trabajadorJson, Trabajadores.class);

            if (trabajadorBean.actualizarTrabajador(trabajador)) {
                return "Trabajador actualizado";
            } else {
                return "El trabajador no pudo ser actualizado";
            }
        }
        
    @WebMethod(operationName = "eliminarTrabajador")
    public String eliminarTrabajador(@WebParam(name = "nomina") String nomina) {
        TrabajadorBean trabajadorBean = new TrabajadorBean();
        
        if (trabajadorBean.eliminarTrabajador(nomina)) {
            return "Trabajador eliminado";
        } else {
            return "El trabajador no pudo ser eliminado";
        }
    }
    
    @WebMethod(operationName = "listadoTrabajadores")
    public List<Trabajadores> listadoTrabajadores() {
    TrabajadorBean trabajadorBean = new TrabajadorBean();
    return trabajadorBean.obtenerTodosLosTrabajadores();
    }


}
