package it.haltardhrim.resources;

import com.codahale.metrics.annotation.Timed;
import it.haltardhrim.model.Paziente;
import it.haltardhrim.model.PazienteService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.ArrayList;

@Path("/pazienti")
public class PazienteResource {

    final private PazienteService ps;

    public PazienteResource() {
        ps = new PazienteService();
    }

    @POST @Timed
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Paziente createPaziente(Paziente p) {
        return ps.accogliPaziente(p);
    }

    @GET @Timed
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<Paziente> readPazienti() {
        return ps.getCodaPazienti();
    }

    @GET @Timed
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Paziente readPaziente(@PathParam("id") int id) {
        Paziente p = ps.getPaziente(id);

        if (p == null) {
            throw new WebApplicationException(404);
        }

        return p;
    }

    @PUT @Timed
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Paziente updatePaziente(Paziente p, @PathParam("id") int id) {
        return ps.aggiornaPaziente(p, id);
    }

    @DELETE @Timed
    @Path("/{id}")
    public void deletePaziente(@PathParam("id") int id) {
        ps.eliminaPaziente(id);
    }
}
