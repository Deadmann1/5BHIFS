/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgData;

import java.util.TreeSet;
import pkgModels.Book;

/**
 *
 * @author schueler
 */
public class Database {
    
        TreeSet<Book> books;

    private Database() {
        books = new TreeSet<>();
        addTestData();
    }
    
    public static Database getInstance() {
        return DatabaseHolder.INSTANCE;
    }

    private static class DatabaseHolder {

        private static final Database INSTANCE = new Database();
    }
    
    
    public void setBook(Book book) throws Exception {
        books.remove(book); // Id must not be changed, so with my comperator remove & add works perfectly, also did this so the set sorts itself
        books.add(book);
    }
    
    public TreeSet<Book> getBooks() throws Exception {
        return books;
    }
    
    public Book getBook(int id) throws Exception {
        Book b = null;
        for(Book book : books) {
            if(book.getId() == id)
                b = book;
        }
        if(b == null)
            throw new Exception("No book with id: " + id + " was found!");
        return b;
    }
    
    public void deleteBook(int _id) throws Exception {
        books.remove(new Book(_id));
    }
    
    public void addBook(Book book) throws Exception {
        books.add(book);
    }
    
    
    private void addTestData() {
        books.add(new Book(1, "Robinson Crusoe", "Daniel Defoe", "robinson.png"));
        books.add(new Book(2, "Krieg und Frieden", "Leo Tolstoi", "warandpeace.png"));
        books.add(new Book(3, "Also sprach Zarathustra", "Friedrich Nietzsche", "zarathustra.png"));
        books.add(new Book(4, "Die letzten Tage der Menschheit", "Karl Kraus", "lastdays.png"));
        books.add(new Book(5, "Hundert Jahre Einsamkeit", "Gabriel García Márquez", "100years.png"));
        books.add(new Book(6, "Moby Dick", "Herman Melville", "mobydick.png"));
        books.add(new Book(7, "Der alte Mann und das Meer", "Ernest Hemingway", "oldmanandthesea.png"));
    }
}
