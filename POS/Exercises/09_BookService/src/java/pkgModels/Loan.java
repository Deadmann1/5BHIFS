package pkgModels;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.annotation.XmlElement;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Loan implements Serializable, Comparable{

    @XmlElement(name = "dateString")
    private String date;
    private int idBook;
    private int idReader;

    public Loan(String date, int idBook, int idReader) {
        this.date = date;
        this.idBook = idBook;
        this.idReader = idReader;
    }
    
    public Loan() {
        super();
    }

    public int getIdReader() {
        return idReader;
    }

    public void setIdReader(int idReader) {
        this.idReader = idReader;
    }

    public int getIdBook() {
        return idBook;
    }

    public void setIdBook(int idBook) {
        this.idBook = idBook;
    }

    public String getDate() {
        return date;
    }

    public void setDate(Date _date) {
        this.date = date;
    }

    @Override
    public int compareTo(Object o) {
        Loan l = (Loan)o;
        return this.date.compareTo(l.date);
    }

   @Override
    public String toString() {
        String tmpDate = "error";
        /*try {
            Date tmp  = new SimpleDateFormat("dd.MM.yyyy").parse(this.getDate());
            tmpDate = tmp.toString();
        } catch (ParseException ex) {
            Logger.getLogger(Loan.class.getName()).log(Level.SEVERE, null, ex);
        }
*/
        return "Loan[" + date + ", reader=" ;
    }
    
}
