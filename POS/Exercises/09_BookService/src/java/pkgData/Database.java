/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgData;

import java.util.TreeSet;
import pkgModels.Book;
import pkgModels.Reader;

/**
 *
 * @author schueler
 */
public class Database {
    
        TreeSet<Book> books;
        TreeSet<Reader> readers;

    private Database() {
        books = new TreeSet<>();
        readers = new TreeSet<>();
        addTestData();
    }
    
    public static Database getInstance() {
        return DatabaseHolder.INSTANCE;
    }

   

    private static class DatabaseHolder {

        private static final Database INSTANCE = new Database();
    }
    
    
    //BOOKS 
    
    public void setBook(Book book) throws Exception {
        books.remove(book); // Id must not be changed, so with my comperator remove & add works perfectly, also did this so the set sorts itself
        books.add(book);
    }
    
    public TreeSet<Book> getBooks() throws Exception {
        return books;
    }
    
    public Book getBook(int _id) throws Exception {
        Book b = null;
        for(Book book : books) {
            if(book.getId() == _id)
                b = book;
        }
        if(b == null)
            throw new Exception("No book with id: " + _id + " was found!");
        return b;
    }
    
    public void deleteBook(int _id) throws Exception {
        books.remove(new Book(_id));
    }
    
    public void addBook(Book book) throws Exception {
        books.add(book);
    }
    
    
    //READERS
    
    public TreeSet<Reader> getReaders() throws  Exception  {
        return readers;
    }

    public Reader getReader(int _id) throws  Exception {
        Reader r = null;
        for(Reader reader : readers) {
            if(reader.getId() == _id)
                r = reader;
        }
        if(r == null)
            throw new Exception("No reader with id: " + _id + " was found!");
        return r;
    }

    public void setReader(Reader r) throws  Exception {
        readers.remove(r);
        readers.add(r);
    }

    public void deleteReader(int _id) throws  Exception {
        readers.remove(new Reader(_id));
    }

    public void addReader(Reader r) throws  Exception {
        readers.add(r); 
    }
    
    
    //TESTDATA
    
    private void addTestData() {
        books.add(new Book(1, "Robinson Crusoe", "Daniel Defoe", "robinson.png"));
        books.add(new Book(2, "Krieg und Frieden", "Leo Tolstoi", "warandpeace.png"));
        books.add(new Book(3, "Also sprach Zarathustra", "Friedrich Nietzsche", "zarathustra.png"));
        books.add(new Book(4, "Die letzten Tage der Menschheit", "Karl Kraus", "lastdays.png"));
        books.add(new Book(5, "Hundert Jahre Einsamkeit", "Gabriel García Márquez", "100years.png"));
        books.add(new Book(6, "Moby Dick", "Herman Melville", "mobydick.png"));
        books.add(new Book(7, "Der alte Mann und das Meer", "Ernest Hemingway", "oldmanandthesea.png"));
        books.add(new Book(7, "Nibelungensage", "Unbekannt", "nibelung.png"));
        
        readers.add(new Reader(1, "Manuel Sammer", "Gleis 9 3/4"));
        readers.add(new Reader(2, "Brynhild", " Merowingerweg 596"));
        readers.add(new Reader(3, "Balmung", "Scheideweg 13"));
        readers.add(new Reader(4, "Laurin", "Tirolerzwergenweg 1200"));
    }
}
