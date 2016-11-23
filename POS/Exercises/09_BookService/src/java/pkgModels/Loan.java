package pkgModels;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Loan implements Serializable{

	private Date date;
	private Reader reader;
	private Book book;
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MMM.yyyy");
	
	public String getDate() {
		return DATE_FORMAT.format(date);
	}
	public void setDate(String _date) {
		try {
			this.date = DATE_FORMAT.parse(_date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	public Reader getReader() {
		return reader;
	}
	public void setReader(Reader reader) {
		this.reader = reader;
	}
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	@Override
	public String toString() {
		return "Loan [date=" + date + ", reader=" + reader + ", book=" + book + "]";
	}
	public Loan(String _date, Reader reader, Book book) {
		super();
		this.reader = reader;
		this.book = book;
                this.setDate(_date);
	}
	public Loan() {
		super();
	}
}
