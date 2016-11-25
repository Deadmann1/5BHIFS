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
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import pkgConstants.Constants;

/**
 * REST Web Service
 *
 * @author Manuel Sammer
 */
@Path("books")
public class BookService {

    /**
     * Creates a new instance of BookService
     */
    public BookService() {
        super();
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.TEXT_XML, MediaType.APPLICATION_JSON})
    public TreeSet<Book> getBooks() {
        TreeSet<Book> ret;
        try {
            ret = Database.getInstance().getBooks();
        } catch (Exception ex) {
            throw new WebApplicationException("Internal server error was: " + ex.getMessage(), Response.Status.INTERNAL_SERVER_ERROR);
        }
        return ret;
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.TEXT_XML})
    public Book getBook(@PathParam("id") int _id) {
        Book ret;
        try {
            ret = Database.getInstance().getBook(_id);
        } catch (Exception ex) {
            throw new WebApplicationException("Internal server error was: " + ex.getMessage(), Response.Status.INTERNAL_SERVER_ERROR);
        }
        return ret;
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_XML)
    public void updateBook(@PathParam("id") int _id, Book b) {
        try {
            Database.getInstance().setBook(b);
        } catch (Exception ex) {
            throw new WebApplicationException("Internal server error was: " + ex.getMessage(), Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @DELETE
    @Path("{id}")
    public void deleteBook(@PathParam("id") int _id) {
        try {
            Database.getInstance().deleteBook(_id);
        } catch (Exception ex) {
            throw new WebApplicationException("Internal server error was: " + ex.getMessage(), Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    public void addBook(Book b) {
        try {
            if(Database.getInstance().getBooks().contains(b)) {
                throw new Exception("Book already contained!");
            }
            Database.getInstance().addBook(b);
        } catch (Exception ex) {
            throw new WebApplicationException("Internal server error was: " + ex.getMessage(), Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    
    //Image Part of Book
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Path("{id}/image")
    public String upload(@FormDataParam("file") InputStream uploadedInputStream, @FormDataParam("file") FormDataContentDisposition fileDetail, @PathParam("id") String id) {
        try {
            this.saveFile(uploadedInputStream, fileDetail);
            Database db = Database.getInstance();
            db.setBookFileName(id, fileDetail.getFileName());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Uploaded Image";
    }
    @GET
    @Produces({"image/jpg"})
    @Path("{id}/image")
    public File getBookImage(@PathParam("id") int _id) throws Exception {
        File file = null;
        try {
            file = new File(Constants.FILE_PATH + Database.getInstance().getBook(_id).getFileName());
        } catch (Exception ex) {
            throw new WebApplicationException("Internal server error was: " + ex.getMessage(), Response.Status.INTERNAL_SERVER_ERROR);
        }
        return file;
    }
    
     private void saveFile(InputStream uploadedInputStream, FormDataContentDisposition fileDetail) throws Exception{
		FileOutputStream os = null;
		Database db = Database.getInstance();
		File fileToUpload = new File(Constants.FILE_PATH + fileDetail.getFileName());
		os = new FileOutputStream(fileToUpload);
		
		byte[] b = new byte[2048];
		int length;
		while((length = uploadedInputStream.read(b)) != -1){
			os.write(b,0,length);
		}
		os.flush();
		os.close();
	}
}
