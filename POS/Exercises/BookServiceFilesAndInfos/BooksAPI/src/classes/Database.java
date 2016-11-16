package classes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import oracle.jdbc.driver.OracleDriver;

public class Database {
	static final String DB_URL = "jdbc:oracle:thin:@192.168.128.151:1521:ora11g"; //intern
    //static final String DB_URL = "jdbc:oracle:thin:@212.152.179.117:1521:ora11g"; //extern
	private Vector<Book> books = new Vector<Book>();
	private String USER = "d5bhifs15";
	private String PASS = "d5bhifs15";

   Connection conn = null;
   Statement stmt = null;
   
	public Vector<Book> getBooks(){
		ResultSet rs = this.getData("select id, titel, author, filename from books_simple order by id");
		try {
			this.books.clear();
			while (rs.next()) {
				this.books.add(new Book(rs.getString(2),rs.getInt(1),rs.getString(3), rs.getString(4)));
			}
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return this.books;
	}
	
	public Book getBook(int id) throws Exception {
		ResultSet rs = this.getData("select id, titel, author, filename from books_simple where id = "+id);
		Book retValue = null;
		try {
			while (rs.next() && retValue == null) {
				retValue =  new Book(rs.getString(2),rs.getInt(1),rs.getString(3),rs.getString(4));
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return retValue;
	}
	

	public String getImageName(int id) {
		ResultSet rs = this.getData("select filename from books_simple where id = "+id);
		String retValue = null;
		try {
			while (rs.next() && retValue == null) {
				retValue =  rs.getString(1);
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return retValue;
	}

	public Database(String username, String password){
		   this.USER  = username;
		   this.PASS = password;
	   }
	   
	public Database(){
		super();
		try {
			this.createConnection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
   public Connection createConnection() throws Exception {
		DriverManager.registerDriver(new OracleDriver());
		conn = DriverManager.getConnection(DB_URL, USER, PASS);
		return conn;
   }
	   
	   
	public ResultSet getData(String statement){
		ResultSet retValue = null;
		try{
		conn = createConnection();
		PreparedStatement stmt = null;

		stmt = conn.prepareStatement(statement,	ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		retValue  = stmt.executeQuery();
		
		}catch(Exception e){
			System.out.println("Error in get Data");
			retValue =  null;
		}
		
		return retValue;
	}
   
   public void closeCon(){
	   try {
				conn.close();
   	  } catch (SQLException e) {
   		  e.printStackTrace();
   	  }
      
	}

   
   
   
   
   
   
//   
//public Vector<Book> getBookList() {
//	ResultSet rs = this.getData("select b.bookid,b.booktitle,a.autorname from books b join bookautor ba on ba.BOOKID = b.BOOKID join autor a on ba.AUTORID = a.AUTORID");
//	Vector<Book> bl = new Vector<Book>();
//	try {
//		while (rs.next()) {
//			bl.add(new Book(rs.getString(1),rs.getInt(0),rs.getString(2)));
//		}
//		conn.close();
//	} catch (SQLException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
//	
//	return bl;
//}

public void addBook(Book book) {
	try{
		conn = createConnection();
		PreparedStatement stmt = null;

		stmt = conn.prepareStatement("insert into books_simple(titel,author,filename) values(?, ?, ?)",	ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		stmt.setString(1, book.getTitle());
		stmt.setString(2, book.getAuthor());
		stmt.setString(3, book.getFileName());
		stmt.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("Error in insert data");
		}
}

public void updateBook(Book book) {
	try{
		conn = createConnection();
		PreparedStatement stmt = null;
		String add = ((book.getFileName()==null||book.getFileName().equals(""))?", filename=null":", filename=?");
		String finished = "update books_simple set titel=? , author=?"+add+" where id=?";
		stmt = conn.prepareStatement(finished,	ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		
		int stmtcounter=1;
		stmt.setString(stmtcounter++, book.getTitle());
		stmt.setString(stmtcounter++, book.getAuthor());
		if((!(book.getFileName()==null||book.getFileName().equals("")))){
			stmt.setString(stmtcounter++, book.getFileName());
		}
		stmt.setInt(stmtcounter, book.getId());
		
		stmt.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("Error in insert data");
		}
}



public void delete(String id) {
	try{
		conn = createConnection();
		PreparedStatement stmt = null;

		stmt = conn.prepareStatement("delete from books_simple where id=?",	ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		stmt.setInt(1, Integer.parseInt(id));
		stmt.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("Error in insert data");
		}
}

public void setBookFileName(String id, String fileName) {
	try{
		conn = createConnection();
		PreparedStatement stmt = null;

		stmt = conn.prepareStatement("update  books_simple set filename = ? where id = ?",	ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		stmt.setInt(2, Integer.parseInt(id));
		stmt.setString(1, fileName);
		
		stmt.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("Error in insert data");
		}
}


/*@Path("login")
@POST
public string Login(@FormParam("email" String e, @FormParam("password") String p){
	@RequestParam
	@CookieParam
	
}
*/
	   
}