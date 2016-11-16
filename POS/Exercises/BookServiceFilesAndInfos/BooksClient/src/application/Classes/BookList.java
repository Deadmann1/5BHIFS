package application.Classes;


import java.util.Vector;

import javax.ws.rs.Path;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

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

	public void addBook(Book tmp) {
		this.books.addElement(tmp);
	}
	
	public void removeBook(int id){
		for(int i = 0; i< books.size();i++){
			if(this.books.get(i).getId() == id){
				this.books.remove(i);
				break;
			}
		}
	}
	
	public void updateBook(Book tmp){
		for(int i = 0; i< books.size();i++){
			if(this.books.get(i).getId() == tmp.getId()){
				this.books.get(i).setAuthor(tmp.getAuthor());
				this.books.get(i).setTitle(tmp.getTitle());
				break;
			}
		}
	}

	public int getSize() {
		return this.books.size();
	}
	
	
}