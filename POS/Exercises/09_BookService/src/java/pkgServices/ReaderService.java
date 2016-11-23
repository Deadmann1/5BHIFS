package pkgServices;

import java.util.TreeSet;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import pkgData.Database;
import pkgModels.Reader;

/**
 * REST Web Service
 *
 * @author Manuel Sammer
 */
@Path("readers")
public class ReaderService {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of ReaderService
     */
    public ReaderService() {
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.TEXT_XML})
    public TreeSet<Reader> getReaders() {
        TreeSet<Reader> ret;
        try {
            ret = Database.getInstance().getReaders();
        } catch (Exception ex) {
            throw new WebApplicationException("Internal server error was: " + ex.getMessage(), Response.Status.INTERNAL_SERVER_ERROR);
        }
        return ret;
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.TEXT_XML})
    public Reader getReader(@PathParam("id") int _id) {
        Reader ret;
        try {
            ret = Database.getInstance().getReader(_id);
        } catch (Exception ex) {
            throw new WebApplicationException("Internal server error was: " + ex.getMessage(), Response.Status.INTERNAL_SERVER_ERROR);
        }
        return ret;
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_XML)
    public void updateReader(@PathParam("id") int _id, Reader b) {
        try {
            Database.getInstance().setReader(b);
        } catch (Exception ex) {
            throw new WebApplicationException("Internal server error was: " + ex.getMessage(), Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @DELETE
    @Path("{id}")
    public void deleteReader(@PathParam("id") int _id) {
        try {
            Database.getInstance().deleteReader(_id);
        } catch (Exception ex) {
            throw new WebApplicationException("Internal server error was: " + ex.getMessage(), Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    public void addReader(Reader b) {
        try {
            Database.getInstance().addReader(b);
        } catch (Exception ex) {
            throw new WebApplicationException("Internal server error was: " + ex.getMessage(), Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
}
