/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg07_mekiv3;

import java.util.Vector;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

/**
 *
 * @author schueler
 */
public class Database {
  public Vector<Task> vecCustomer;
  public Watch watch;
  public ObservableList<String> log;
    
  private static Database instance;
  private Database () {}
  public static Database getInstance () {
    if (Database.instance == null) {
      Database.instance = new Database ();
    }
    return Database.instance;
  }

  
}
