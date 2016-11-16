/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgData;

import java.util.HashMap;
import pkgModel.Book;

/**
 *
 * @author schueler
 */
public class Database {
    
        HashMap<Integer,Book> hmBooks;

    private Database() {
        hmBooks = new HashMap<>();
        hmBooks.put(0, new Book());
    }
    
    public static Database getInstance() {
        return DatabaseHolder.INSTANCE;
    }
    
    private static class DatabaseHolder {

        private static final Database INSTANCE = new Database();
    }
    
    
    public void setBook(Book book) throws Exception {
        
    }
    
}
