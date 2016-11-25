package pkgServices;

import java.util.ArrayList;
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
import pkgModels.Loan;
import pkgModels.Reader;

/**
 * REST Web Service
 *
 * @author Manuel Sammer
 */
@Path("readers")
public class ReaderService {

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
    public void updateReader(@PathParam("id") int _id, Reader r) {
        try {
            Database.getInstance().setReader(r);
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
    public void addReader(Reader r) {
        try {
            Database.getInstance().addReader(r);
        } catch (Exception ex) {
            throw new WebApplicationException("Internal server error was: " + ex.getMessage(), Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
    //Loan Part of Reader
    
    @GET
    @Path("/{id}/loans")
    @Produces({MediaType.APPLICATION_XML, MediaType.TEXT_XML})
    public ArrayList<Loan> getReaderLoans(@PathParam("id") int _id) {
        ArrayList<Loan> ret;
        try {
            ret = Database.getInstance().getLoans(_id);
        } catch (Exception ex) {
            throw new WebApplicationException("Internal server error was: " + ex.getMessage(), Response.Status.INTERNAL_SERVER_ERROR);
        }
        return ret;
    }
    @POST
    @Path("{id}/loans")
    @Consumes(MediaType.APPLICATION_XML)
    public void addLoan(@PathParam("id") int _id, Loan l) {
        try {
            Database.getInstance().addLoan(_id, l);
        } catch (Exception ex) {
            throw new WebApplicationException("Internal server error was: " + ex.getMessage(), Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
    /*
    Need id on loan to specifiy which loan is meant
    Not needed right now for appl
    
    @GET
    @Path("/{id}/loans/{idLoan}")
    @Produces({MediaType.APPLICATION_XML, MediaType.TEXT_XML})
    public Loan getLoan(@PathParam("id") int _id, @PathParam("idLoan") int _idLoan) {
        Loan ret;
        try {
            ret = Database.getInstance().getLoan(_id, _idLoan);
        } catch (Exception ex) {
            throw new WebApplicationException("Internal server error was: " + ex.getMessage(), Response.Status.INTERNAL_SERVER_ERROR);
        }
        return ret;
    }

    @PUT
    @Path("{id}/loans/{idLoan}")
    @Consumes(MediaType.APPLICATION_XML)
    public void updateLoan(@PathParam("id") int _id, @PathParam("idLoan") int _idLoan, Loan l) {
        try {
            Database.getInstance().setLoan(_id, _idLoan, l);
        } catch (Exception ex) {
            throw new WebApplicationException("Internal server error was: " + ex.getMessage(), Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @DELETE
    @Path("{id}/loans/{idLoan}")
    public void deleteLoan(@PathParam("id") int _id, @PathParam("idLoan") int _idLoan) {
        try {
            Database.getInstance().deleteLoan(_id, _idLoan);
        } catch (Exception ex) {
            throw new WebApplicationException("Internal server error was: " + ex.getMessage(), Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

     */
}
