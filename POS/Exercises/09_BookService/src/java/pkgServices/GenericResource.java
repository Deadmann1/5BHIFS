/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgServices;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import pkgData.Database;
import pkgModel.Book;

/**
 * REST Web Service
 *
 * @author schueler
 */
@Path("api")
public class GenericResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of GenericResource
     */
    public GenericResource() {
    }

    @GET
    @Produces({MediaType.TEXT_PLAIN})
    @Path("/welcome")
    public String getWelcome() {
        return "Willkommen auf meiner API!";
    }
/*
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("/book/{id}")
    public Book getBook(@PathParam("bookid") String id) { //id as string to get no expcetion while parsing 

        Database db = Database.getInstance();
        Book retBook;
        try {
            retBook = db.getBook(Integer.parseInt(id));
        } catch (Exception ex) {
            retBook = new Book(-99, ex.getMessage(), "-x-x-x-", "No book found with id:17");
        }
        return retBook;
    }
*/
    @POST
    @Consumes({MediaType.TEXT_XML, MediaType.TEXT_XML})
    public String newBook(Book book) throws Exception {
        String retValue = "ok";
        try {
            Database.getInstance().setBook(book);
        } catch (Exception ex) {
            retValue = ex.getMessage();
        }
        return retValue;
    }
}
