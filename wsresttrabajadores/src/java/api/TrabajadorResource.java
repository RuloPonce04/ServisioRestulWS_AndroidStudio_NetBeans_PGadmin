package api;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import modelo.TrabajadorBean;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.DELETE;
import javax.ws.rs.PUT;
import modelo.Mensajes;
import modelo.Trabajadores;
import javax.ws.rs.core.MediaType;

@Path("trabajador")
public class TrabajadorResource {
    
    /**
     * Posible uso recuperar tregistros de una tabla
     * Retrieves representation of an instance of api.EjemploResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
    TrabajadorBean trabajadorBean = new TrabajadorBean();
    List<Trabajadores> listaTrabajadores = trabajadorBean.obtenerTodosLosTrabajadores();

    Gson gson = new Gson();
    String json = gson.toJson(listaTrabajadores);

    return json;
}
    

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{nomina}")
    public String getTrabajador(@PathParam("nomina") String nomina) {
        TrabajadorBean trabajadorBean = new TrabajadorBean();
        Trabajadores trabajador = trabajadorBean.buscarTrabajador(nomina);

        if (trabajador != null) {
            Gson gson = new Gson();
            return gson.toJson(trabajador);
        } else {
            return "Trabajador no encontrado";
        }
    }

    /**
     * Posible uso: Insertar no o mas registro en la base de datos
     * POST method for updating or creating an instance of EjemploResource
     * @param content representation for the resource
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String postJson(String content) {
        Gson gson = new Gson();
        Trabajadores trabajador = gson.fromJson(content, Trabajadores.class);

        TrabajadorBean trabajadorBean = new TrabajadorBean();
        if (trabajadorBean.agregarTrabajador(trabajador)) {
            Mensajes mensaje = new Mensajes();
            mensaje.setMensaje("El trabajador ha sido registrado");
            return gson.toJson(mensaje);
        } else {
            Mensajes mensaje = new Mensajes();
            mensaje.setMensaje("Error al registrar el trabajador");
            return gson.toJson(mensaje);
        }
    }
    
    /**
     * Posible uso: Modificar un registro existente o un grupo de registros
     * PUT method for updating or creating an instance of EjemploResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String putJson(String content) {
        Gson gson = new Gson();
        Trabajadores trabajador = gson.fromJson(content, Trabajadores.class);

        TrabajadorBean trabajadorBean = new TrabajadorBean();
        if (trabajadorBean.actualizarTrabajador(trabajador)) {
            Mensajes mensaje = new Mensajes();
            mensaje.setMensaje("El trabajador ha sido actualizado");
            return gson.toJson(mensaje);
        } else {
            Mensajes mensaje = new Mensajes();
            mensaje.setMensaje("Error al actualizar el trabajador");
            return gson.toJson(mensaje);
        }
    }
    
    
    /**
     * Posible uso: Elimina uno o varios registro de la base de datos
     * POST method for updating or creating an instance of EjemploResource
     * @param content representation for the resource
     */
    @DELETE
    @Path("/{nomina}")
    public String deleteUsuarioJson(@PathParam("nomina") String nomina) {
        TrabajadorBean trabajadorBean = new TrabajadorBean();

        if (trabajadorBean.eliminarTrabajador(nomina)) {
            return "Trabajador con nómina " + nomina + " eliminado";
        } else {
            return "No se pudo eliminar el trabajador con nómina " + nomina;
        }
    }

}
