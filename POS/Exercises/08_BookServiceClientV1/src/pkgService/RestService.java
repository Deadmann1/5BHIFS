/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgService;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.List;
import java.util.TreeSet;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart;
import pkgConstants.Constants;
import pkgModels.Book;
import pkgModels.Loan;
import pkgModels.Reader;

/**
 *
 * @author Manuel Sammer
 */
public class RestService {

    private static RestService _instance = null;

    protected RestService() {
        // Exists only to defeat instantiation.
    }

    public static RestService getInstance() {
        if (_instance == null) {
            _instance = new RestService();
        }
        return _instance;
    }

    private WebResource services;
    private Client client;
    private ClientConfig config;

    public void Connect(String url) {
        config = new DefaultClientConfig();
        client = Client.create(config);
        services = client.resource(UriBuilder.fromUri(url).build());
    }

    public TreeSet<Book> getBooks() {
        return services.path("/books").accept(MediaType.APPLICATION_XML).get(new GenericType<TreeSet<Book>>() {
        });
    }

    public Book getBook(int id) {
        return services.path("/books/" + String.valueOf(id)).accept(MediaType.APPLICATION_XML).get(Book.class);
    }

    public void deleteBook(int id) {
        services.path("/books/" + String.valueOf(id)).delete();
    }

    public void addBook(Book b) {
        services.path("/books").post(b);
    }

    public void updateBook(Book b) {
        services.path("/books/" + String.valueOf(b.getId())).put(b);
    }

    public TreeSet<Reader> getReaders() {
        return services.path("/readers").accept(MediaType.APPLICATION_XML).get(new GenericType<TreeSet<Reader>>() {
        });
    }
     public Reader getReader(int id) {
        return services.path("/readers/" + id).accept(MediaType.APPLICATION_XML).get(Reader.class);
    }

    public void uploadImage(int id, String fileName) throws Exception {
        File fileToUpload = new File(fileName);
        FormDataMultiPart multiPart = new FormDataMultiPart();
        multiPart.setMediaType(MediaType.MULTIPART_FORM_DATA_TYPE);
        multiPart.bodyPart(new FileDataBodyPart("file", fileToUpload, MediaType.APPLICATION_OCTET_STREAM_TYPE));
        services.path("/books/" + id + "/image").type(MediaType.MULTIPART_FORM_DATA_TYPE).post(String.class);
    }

    public FileOutputStream downloadImage(int id, String fileName) throws Exception {
     FileOutputStream fos = null;
        URL website = new URL(services.getURI() + "books/" + id + "/image");
        ReadableByteChannel rbc = Channels.newChannel(website.openStream());
        fos = new FileOutputStream(Constants.FILE_PATH + fileName);
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        fos.flush();
        if (fos.getChannel().size() == 0) {
            fos = null;
        }
        return fos;
    }

    public void addLoan(int id, Loan loan) {
        services.path("/readers/" + id + "/loans").post(loan);
    }
}
