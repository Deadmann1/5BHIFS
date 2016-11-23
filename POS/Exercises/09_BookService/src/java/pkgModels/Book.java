package pkgModels;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class Book implements Comparable{
	
	private String title;
	
	private int id;
	
	private String author;
	
	private String fileName;
	
	public Book( int id, String title, String author, String fileName) {
		super();
		this.title = title;
		this.id = id;
		this.author = author;
		this.fileName = fileName;
	}
        public Book( int id) {
		super();
		this.id = id;
	}
        
	
	public Book(){
		super();
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getAuthor() {
		return author;
	}
	
	public void setAuthor(String author) {
		this.author = author;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public String toString() {
		return "Book [title=" + title + ", id=" + id + ", author=" + author
				+ ", fileName=" + fileName + "]";
	}

    @Override
    public int compareTo(Object o) {
        int retVal;
        Book b = (Book)o;
        if(this.id == b.id) {
            retVal = 0;
        }
        else {
            retVal = this.title.compareTo(b.title);
        }
        return retVal;
    }
	
}
