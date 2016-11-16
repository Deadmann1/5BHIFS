package pkgService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Vector;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import classes.Book;
import classes.Database;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

@Path("/BookImage")
@XmlRootElement (name ="BookImage")
public class BookImage {
	
	@XmlElement (name ="books")
	private Vector<Book> books = new Vector<Book>();
	
	public BookImage(){
		
	}
	
	public Vector<Book> getBooks(){
		return this.books;
	}
	
	public void setVector(Vector<Book> bv){
		this.books = bv;
	}
	
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Path("{bookid}")
	  public String upload(@FormDataParam("file") InputStream uploadedInputStream ,@FormDataParam("file") FormDataContentDisposition fileDetail, @PathParam("bookid") String id ) {
		try {
			this.saveFile(uploadedInputStream, fileDetail);
			Database db = new Database();
			db.setBookFileName(id,fileDetail.getFileName());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	    return "Uploaded Image";
	  }
	
	@GET
	@Produces({"image/*"})
	@Path("{bookid}")
	  public Response getBookImage(@PathParam("bookid") String id) {
		ResponseBuilder response = null;
		try {
			Database db = new Database();
			String fileName = db.getImageName(Integer.parseInt(id));
			if(fileName!= null && !fileName.equals("")){
				File file = new File("/tmp/"+fileName);
				response = Response.ok((Object) file);
				response.header("Content-Disposition", "attachment: filename="+fileName);
			}else{
				response = Response.noContent();
			}
		} catch (Exception e) {
			e.printStackTrace();
			response = Response.noContent();
		}
		
	    return response.build();
	  }
	
	private void saveFile(InputStream uploadedInputStream, FormDataContentDisposition fileDetail) throws Exception{
		FileOutputStream os = null;
		File fileToUpload = new File("/tmp/"+fileDetail.getFileName());
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