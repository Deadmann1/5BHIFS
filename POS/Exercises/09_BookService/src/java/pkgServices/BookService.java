/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgServices;

import java.util.TreeSet;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import pkgData.Database;
import pkgModels.Book;

/**
 * REST Web Service
 *
 * @author Manuel Sammer
 */
@Path("books")
public class BookService {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of BookService
     */
    public BookService() {
        super();
    }

    
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.TEXT_XML})
    public TreeSet<Book> getBooks() {
        TreeSet<Book> ret;
        try{
            ret = Database.getInstance().getBooks();
        }
        catch(Exception ex) {
            throw new WebApplicationException("Internal server error was: " + ex.getMessage(),Response.Status.INTERNAL_SERVER_ERROR);
        }
        return ret;
    }
    
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.TEXT_XML})
    public Book getBook(@PathParam("id") int _id) {
        Book ret;
        try{
            ret = Database.getInstance().getBook(_id);
        }
        catch(Exception ex) {
            throw new WebApplicationException("Internal server error was: " + ex.getMessage(),Response.Status.INTERNAL_SERVER_ERROR);
        }
        return ret;
    }


    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_XML)
    public void putBook(@PathParam("id") int _id,Book b) {
        try{
            Database.getInstance().setBook(b);
        }
        catch(Exception ex){
            throw new WebApplicationException("Internal server error was: " + ex.getMessage(),Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
    
    @DELETE
    @Path("{id}")
    public void deleteBook(@PathParam("id") int _id) {
        try{
            Database.getInstance().deleteBook(_id);
        }
        catch(Exception ex){
            throw new WebApplicationException("Internal server error was: " + ex.getMessage(),Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_XML)
    public void addBook(Book b) {
        try{
            Database.getInstance().addBook(b);
        }
        catch(Exception ex){
            throw new WebApplicationException("Internal server error was: " + ex.getMessage(),Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
    
}
