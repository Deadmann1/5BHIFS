package application.Classes;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.multipart.FormDataMultiPart;
import com.sun.jersey.multipart.impl.MultiPartWriter;

public class ServiceCaller {

	private WebResource service= null;
	private String restUrl = "rest";
	private String restName = "BookList";
	private String uri;
	
	
	public ServiceCaller(String url){
		
		ClientConfig config = new DefaultClientConfig();
		config.getClasses().add(MultiPartWriter.class);
		Client client = Client.create(config);
		service = client.resource(url);
		this.uri = url;
		
	}
	
	public BookList getBooks() {
		return this.buildPath().accept(MediaType.APPLICATION_XML).get(BookList.class);
	}

	private WebResource buildPath() {
		return service.path(this.restUrl).path(this.restName);
	}

	public Book getBook(int parseInt) {
		return this.buildPath().path(new Integer(parseInt).toString()).accept(MediaType.APPLICATION_XML).get(Book.class);
	}

	public String addBook(Book tmp) {
		String retValue = "";
		try {
			retValue = this.buildPath().type(MediaType.APPLICATION_XML).post(String.class,tmp);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retValue;
	}

	public String updateBook(Book tmp) {
		String retValue = "";
		try {
			retValue = this.buildPath().type(MediaType.APPLICATION_XML).put(String.class,tmp);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retValue;
	}

	public void deleteBook(String text) {
		this.buildPath().path(text).delete();
	}
	
	public String sendImage(FormDataMultiPart multiPart, String strid){
		String retValue = this.service.path("rest").path("BookImage").path(strid).type(MediaType.MULTIPART_FORM_DATA).post(String.class,multiPart);
		return retValue;
	}
	
	public FileOutputStream getImage(String strid){
		FileOutputStream fos= null;
		try {
			URL website = new URL(this.uri +"/rest/BookImage/"+strid);
			ReadableByteChannel rbc = Channels.newChannel(website.openStream());
			
			fos = new FileOutputStream("/tmp/tmp_book.jpg");
			fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
			fos.flush();
			if(fos.getChannel().size()==0){
				fos = null;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fos;
		
		
		
	}
}
