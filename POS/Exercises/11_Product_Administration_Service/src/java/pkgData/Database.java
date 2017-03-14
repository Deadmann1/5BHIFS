/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgData;

/**
 *
 * @author Manuel Sammer
 */
public class Database {
    
    //private ArrayList<Producer> producerList;
    //private ArrayList<Product> productList;
            
    
    private Database() {
    }
    
    public static Database getInstance() {
        return DatabaseHolder.INSTANCE;
    }
    
    private static class DatabaseHolder {

        private static final Database INSTANCE = new Database();
    }
}
