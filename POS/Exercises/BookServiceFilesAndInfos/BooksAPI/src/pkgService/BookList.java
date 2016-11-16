package pkgService;

import java.util.Vector;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import classes.Book;
import classes.Database;

@Path("/BookList")
@XmlRootElement (name ="BookList")
public class BookList {
	
	@XmlElement (name ="books")
	private Vector<Book> books = new Vector<Book>();
	
	public BookList(){
		
	}
	
	public Vector<Book> getBooks(){
		return this.books;
	}
	
	public void setVector(Vector<Book> bv){
		this.books = bv;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_XML)
	public BookList printXMLBooks() {
		Database db = new Database();
		this.books = db.getBooks();
		return this;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_XML)
	@Path("{bookid}")
	  public Book printXMLBook(@PathParam("bookid") String id) {
		Book retValue = null;
		
		try {

			Database db = new Database();
			
			retValue = db.getBook(Integer.parseInt(id));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	    return retValue;
	  }
	
	@POST
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_XML)
	  public String saveXMLBook(Book book) {
		if(book != null){
			this.books.add(book);
			Database db = new Database();
			db.addBook(book);
		}
	    return "Added Book successfully";
	  }
	
	@PUT
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_XML)
	  public String updateXMLBook( Book book) {
		if(book != null){
			
			Database db = new Database();
			db.updateBook(book);
		}
	    return "Updated Book sucessfully";
	  }
	
	@DELETE
	@Consumes(MediaType.TEXT_XML)
	@Path("{bookid}")
	  public String deleteXMLBook(@PathParam("bookid") String id) {
		if(id != null){
			boolean found = false;
			for(int i = 0;i<this.books.size()&&!found;i++){
				if(this.books.get(i).getId() == Integer.parseInt(id)){
					this.books.remove(i);
				}
			}
			Database db = new Database();
			db.delete(id);
		}
	    return "Deleted Book successfully";
	  }
//	
//	private String marshalBook(Book b){
//		String retValue = "";
//		try {
//			JAXBContext jaxbContext = JAXBContext.newInstance(Book.class);
//			Marshaller jaxbMarshaller;
//			jaxbMarshaller = jaxbContext.createMarshaller();
//			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//			StringWriter writer = new StringWriter();
//			jaxbMarshaller.marshal(b, writer);
//			retValue=  writer.toString();
//		} catch (PropertyException e) {
//			e.printStackTrace();
//		} catch (JAXBException e) {
//			e.printStackTrace();
//		}
//		return retValue;
//	}
//	
//	private String marshalBookList(BookList bl){
//		String retValue = "";
//		try {
//			JAXBContext jaxbContext = JAXBContext.newInstance(BookList.class);
//			Marshaller jaxbMarshaller;
//			jaxbMarshaller = jaxbContext.createMarshaller();
//
//			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//			StringWriter writer = new StringWriter();
//			jaxbMarshaller.marshal(bl, writer);
//			retValue = writer.toString();
//		} catch (PropertyException e) {
//			e.printStackTrace();
//		} catch (JAXBException e) {
//			e.printStackTrace();
//		}
//		return retValue;
//	}
	
}