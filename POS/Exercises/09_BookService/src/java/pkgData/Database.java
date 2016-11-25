/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.TreeSet;
import pkgModels.Book;
import pkgModels.Loan;
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

    public void setBookFileName(String id, String fileName) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static class SingletonHolder {

        public static final Database instance = new Database();
    }

    public static Database getInstance() {
        return SingletonHolder.instance;
    }

    //BOOKS 
    public void setBook(Book book) throws Exception {
        boolean found = false;
        for (Book b : books) {
            if (b.getId() == book.getId()) {
                books.remove(b);
                books.add(book);
                found = true;
            }
        }
        if (!found) {
            throw new Exception("No book with id: " + book.getId() + " was found!");
        }
    }

    public TreeSet<Book> getBooks() throws Exception {
        return books;
    }

    public Book getBook(int _id) throws Exception {
        Book b = null;
        for (Book book : books) {
            if (book.getId() == _id) {
                b = book;
            }
        }
        if (b == null) {
            throw new Exception("No book with id: " + _id + " was found!");
        }
        return b;
    }

    public void deleteBook(int _id) throws Exception {
        boolean found = false;
        for (Book book : books) {
            if (book.getId() == _id) {
                books.remove(book);
                found = true;
            }
        }
        if(!found){
            throw new Exception("No book with id: " + _id + " was found!");
        }
    }

    public void addBook(Book book) throws Exception {
        books.add(book);
    }

    //READERS
    public TreeSet<Reader> getReaders() throws Exception {
        return readers;
    }

    public Reader getReader(int _id) throws Exception {
        Reader r = null;
        for (Reader reader : readers) {
            if (reader.getId() == _id) {
                r = reader;
            }
        }
        if (r == null) {
            throw new Exception("No reader with id: " + _id + " was found!");
        }
        return r;
    }

    public void setReader(Reader r) throws Exception {
        readers.remove(r);
        readers.add(r);
    }

    public void deleteReader(int _id) throws Exception {
        boolean found = false;
        for (Reader reader : readers) {
            if (reader.getId() == _id) {
                readers.remove(reader);
                found = true;
            }
        }
        if(!found){
            throw new Exception("No reader with id: " + _id + " was found!");
        }
    }

    public void addReader(Reader r) throws Exception {
        readers.add(r);
    }

    public void addLoan(int _id, Loan l) throws Exception {
        boolean success = false;
        for (Reader r : readers) {
            if (r.getId() == _id) {
                r.getLoans().add(l);
                Collections.sort(r.getLoans());
                success = true;
            }
        }
        if(!success){
            throw new Exception("Could not add loan!");
        }
    }

    public ArrayList<Loan> getLoans(int _id) throws Exception {
        ArrayList<Loan> ret = null;
        for (Reader r : readers) {
            if (r.getId() == _id) {
                ret =  r.getLoans();
            }
        }
        if(ret == null) {
            throw new Exception("No loans with were found!");
        }
        return ret;
    }

    /*
    Not needed right now
    public void deleteLoan(int _id, int _idLoan) throws Exception {
    }

    public void setLoan(int _id, int _idLoan, Loan l) throws Exception {
    }
    
    public Loan getLoan(int _id, int _idLoan) throws Exception {
        return null;
    }
     */
    //TESTDATA
    private void addTestData() {
        try {
            books.add(new Book(1, "Robinson Crusoe", "Daniel Defoe", "robinson.jpg"));
            books.add(new Book(2, "Krieg und Frieden", "Leo Tolstoi", "warandpeace.jpg"));
            books.add(new Book(3, "Also sprach Zarathustra", "Friedrich Nietzsche", "zarathustra.jpg"));
            books.add(new Book(4, "Die letzten Tage der Menschheit", "Karl Kraus", "lastdays.jpg"));
            books.add(new Book(5, "Hundert Jahre Einsamkeit", "Gabriel García Márquez", "100years.jpg"));
            books.add(new Book(6, "Moby Dick", "Herman Melville", "mobydick.jpg"));
            books.add(new Book(7, "Der alte Mann und das Meer", "Ernest Hemingway", "oldman.jpg"));
            books.add(new Book(7, "Nibelungensage", "Unbekannt", "nibelung.jpg"));

            Reader r = new Reader(1, "Manuel Sammer", "Gleis 9 3/4");
            Loan l = new Loan("20.NOV.2016",1,1);
            r.getLoans().add(l);
            readers.add(r);

            Reader r2 = new Reader(2, "Brynhild", "Merowingerweg 596");
            Loan l2 = new Loan("01.FEB.2012", 2, 2);
            r2.getLoans().add(l2);
            readers.add(r2);

            Reader r3 = new Reader(3, "Balmung", "cheideweg 13");
            Loan l3 = new Loan("22.MAR.2013",3,3);
            r3.getLoans().add(l3);
            readers.add(r3);

            Reader r4 = new Reader(4, "Laurin", "Tirolerzwergenweg 1200");
            Loan l4 = new Loan("22.MAR.2013",7,4);
            r4.getLoans().add(l4);
            readers.add(r4);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
