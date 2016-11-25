package pkg08_bookserviceclientv1;

import java.io.File;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.TreeSet;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import pkgConstants.Constants;
import pkgModels.Book;
import pkgModels.Loan;
import pkgModels.Reader;
import pkgService.RestService;

public class MainGUIController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField txtUrl;

    @FXML
    private Button btnConnect;

    @FXML
    private Button btnGetBook;

    @FXML
    private Button btnGetAllBooks;

    @FXML
    private Button btnAddBook;

    @FXML
    private Button btnUpdateBook;

    @FXML
    private Button btnDeleteBook;

    @FXML
    private Button btnUploadImage;

    @FXML
    private Button btnDownloadImage;

    @FXML
    private Button btnFillReader;

    @FXML
    private Button btnAddLoan;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtTitle;

    @FXML
    private TextField txtAuthor;

    @FXML
    private TextField txtImageName;

    @FXML
    private TableView<Book> tblBook;

    @FXML
    private TableColumn<Book, Integer> tblColID;

    @FXML
    private TableColumn<Book, String> tblColTitle;

    @FXML
    private TableColumn<Book, String> tblColAuthor;

    @FXML
    private ComboBox<Reader> cmbReader;

    @FXML
    private TextField txtDate;

    @FXML
    private ListView<Loan> lvLoans;

    @FXML
    private ImageView image;

    @FXML
    private Label lblMessage;

    @FXML
    private Button btnLoadLoans;

    @FXML
    void OnBtnGetBookClicked(ActionEvent event) {
        try {
            if (this.txtId.getText().equals("")) {
                throw new Exception(Constants.ERROR_INVALID_INPUT);
            }
            int id = Integer.parseInt(this.txtId.getText());
            this.fillBookFields(RestService.getInstance().getBook(id));
            this.lblMessage.setText("");
        } catch (NumberFormatException e) {
            this.lblMessage.setText(Constants.ERROR_INVALID_PARSE);
        } catch (Exception ex) {
            this.lblMessage.setText("error: " + ex.getMessage());
        }
    }

    @FXML
    void OnBtnAddBookClicked(ActionEvent event) {
        try {
            if (this.txtId.getText().equals("") || this.txtAuthor.getText().equals("") || this.txtTitle.getText().equals("") || this.txtImageName.getText().equals("")) {
                throw new Exception(Constants.ERROR_INVALID_INPUT);
            }
            RestService.getInstance().addBook(new Book(Integer.parseInt(txtId.getText()), txtTitle.getText(), txtAuthor.getText(), txtImageName.getText()));
            this.refreshBooks();
            this.lblMessage.setText("successfully added new book");
        } catch (NumberFormatException e) {
            this.lblMessage.setText(Constants.ERROR_INVALID_PARSE);
        } catch (Exception ex) {
            this.lblMessage.setText("error: " + ex.getMessage());
        }
    }

    @FXML
    void OnBtnAddLoanClicked(ActionEvent event) {
        try {
            if (this.txtDate.getText().equals("")) {
                throw new Exception(Constants.ERROR_INVALID_INPUT);
            }
            SimpleDateFormat parser = new SimpleDateFormat("dd.MMM.yyyy");
            parser.parse(txtDate.getText());
            Reader reader = this.cmbReader.getSelectionModel().getSelectedItem();
            Book book = this.tblBook.getSelectionModel().getSelectedItem();
            if (reader == null) {
                throw new Exception("Please select a reader!");
            } else if (book == null) {
                throw new Exception("Please select a Book!");
            }
            RestService.getInstance().addLoan(reader.getId(), new Loan(txtDate.getText(), book.getId(), reader.getId()));
            this.refreshReadersAndLoans();
            this.lblMessage.setText("successfully added new loan");
        } catch (ParseException ex) {
            this.lblMessage.setText(Constants.ERROR_INVALID_PARSE_DATE);
        } catch (Exception ex) {
            this.lblMessage.setText("error: " + ex.getMessage());
        }
    }

    @FXML
    void OnBtnConnectClicked(ActionEvent event) {
        try {
            if (this.txtUrl.getText().equals("")) {
                throw new Exception(Constants.ERROR_INVALID_INPUT);
            }
            RestService.getInstance().Connect(txtUrl.getText());
            this.setButtonBookStatus(false);
            this.lblMessage.setText("successfully connected!");
        } catch (Exception ex) {
            this.lblMessage.setText("error: " + ex.getMessage());
        }
    }

    @FXML
    void OnBtnDeleteBookClicked(ActionEvent event) {
        try {
            if (this.txtId.getText().equals("")) {
                throw new Exception(Constants.ERROR_INVALID_INPUT);
            }
            RestService.getInstance().deleteBook(Integer.parseInt(txtId.getText()));
            this.refreshBooks();
            this.lblMessage.setText("successfully deleted!");
        } catch (Exception ex) {
            this.lblMessage.setText("error: " + ex.getMessage());
        }
    }

    @FXML
    void OnBtnDownloadImageClicked(ActionEvent event) {
        try {
            if (this.txtId.getText().equals("") || RestService.getInstance().downloadImage(Integer.parseInt(this.txtId.getText()), this.txtImageName.getText()) == null) {
                this.lblMessage.setText("There is no Image for this Book");
            } else {
                this.image.setImage(this.getLocalImage());
                this.lblMessage.setText("Image downloaded successfully");
            }
        } catch (Exception ex) {
            this.lblMessage.setText("error: " + ex.getMessage());
        }
    }

    @FXML
    void OnBtnFillReaderClicked(ActionEvent event) {
        try {
            this.refreshReadersAndLoans();
            this.setButtonReaderStatus(false);
            this.lblMessage.setText("readers successfully loaded");
        } catch (NumberFormatException e) {
            this.lblMessage.setText(Constants.ERROR_INVALID_PARSE);
        } catch (Exception ex) {
            this.lblMessage.setText("error: " + ex.getMessage());
        }
    }

    @FXML
    void OnBtnGetAllBooksClicked(ActionEvent event) {
        try {
            this.refreshBooks();
            this.lblMessage.setText("successfully got all books");
        } catch (Exception ex) {
            this.lblMessage.setText("error: " + ex.getMessage());
        }
    }

    @FXML
    void OnBtnUpdateBookClicked(ActionEvent event) {
        try {
            if (this.txtId.getText().equals("") || this.txtAuthor.getText().equals("") || this.txtTitle.getText().equals("") || this.txtImageName.getText().equals("")) {
                throw new Exception(Constants.ERROR_INVALID_INPUT);
            }
            RestService.getInstance().updateBook(new Book(Integer.parseInt(txtId.getText()), txtTitle.getText(), txtAuthor.getText(), txtImageName.getText()));
            this.refreshBooks();
            this.lblMessage.setText("successfully updated book");
        } catch (NumberFormatException e) {
            this.lblMessage.setText(Constants.ERROR_INVALID_PARSE);
        } catch (Exception ex) {
            this.lblMessage.setText("error: " + ex.getMessage());
        }
    }

    @FXML
    void OnBtnUploadImageClicked(ActionEvent event) {
        try {
            if (this.txtId.getText().equals("")) {
                throw new Exception(Constants.ERROR_INVALID_INPUT);
            }
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Image File to Upload");
            fileChooser.setInitialFileName("*.jpg");
            fileChooser.setSelectedExtensionFilter(new ExtensionFilter("Images (png,jpg,bmp)", "*.jpg", "*.png", "*.jpeg", "*.bmp"));
            this.lblMessage.setText("successfully uploaded image");
            File file = fileChooser.showOpenDialog(this.image.getScene().getWindow());
            if (file != null) {
                RestService.getInstance().uploadImage(Integer.parseInt(this.txtId.getText()), file.getName());
            } else {
                this.lblMessage.setText("you didnt choose any file!");
            }
        } catch (Exception e) {
            this.lblMessage.setText("error: " + e.getMessage());
        }
    }

    @FXML
    void OnBtnLoadLoansClicked(ActionEvent event) {
        try {
            fillLoansList(this.cmbReader.getSelectionModel().getSelectedItem());
            this.lblMessage.setText("successfully laoded loans of reader");
        } catch (Exception ex) {
            this.lblMessage.setText("error: " + ex.getMessage());
        }
    }

    public void fillLoansList(Reader r) {
        ArrayList l = r.getLoans();
        ObservableList<Loan> oLoans = FXCollections.observableArrayList(l);
        this.lvLoans.setItems(oLoans);
    }

    private void fillBookFields(Book b) {
        this.txtTitle.setText(b.getTitle());
        this.txtAuthor.setText(b.getAuthor());
        this.txtImageName.setText(b.getFileName());
    }

    private Image getLocalImage() {
        return new Image("file:" + Constants.FILE_PATH);
    }

    private void setButtonBookStatus(boolean stat) {
        this.btnAddBook.setDisable(stat);
        this.btnDeleteBook.setDisable(stat);
        this.btnDownloadImage.setDisable(stat);
        this.btnGetAllBooks.setDisable(stat);
        this.btnGetBook.setDisable(stat);
        this.btnFillReader.setDisable(stat);
        this.btnUpdateBook.setDisable(stat);
        this.btnUploadImage.setDisable(stat);

    }

    private void setButtonReaderStatus(boolean stat) {
        this.btnAddLoan.setDisable(stat);
        this.btnLoadLoans.setDisable(stat);
    }

    private void refreshBooks() throws Exception {
        this.tblBook.getColumns().get(0).setVisible(false);
        this.tblBook.getColumns().get(0).setVisible(true);
        TreeSet<Book> books = RestService.getInstance().getBooks();
        ArrayList<Book> b = new ArrayList<Book>(books);
        ObservableList<Book> oBooks = FXCollections.observableArrayList(b);
        this.tblBook.setItems((ObservableList<Book>) oBooks);
    }

    private void refreshReadersAndLoans() throws Exception {
        TreeSet<Reader> readers = RestService.getInstance().getReaders();
        ArrayList<Reader> r = new ArrayList<Reader>(readers);
        ObservableList<Reader> oReaders = FXCollections.observableArrayList(r);
        this.cmbReader.setItems(oReaders);
        this.cmbReader.getSelectionModel().selectFirst();
        this.fillLoansList(cmbReader.getSelectionModel().getSelectedItem());
    }

    @FXML
    void initialize() {
        assert txtUrl != null : "fx:id=\"txtUrl\" was not injected: check your FXML file 'MainGUI.fxml'.";
        assert btnConnect != null : "fx:id=\"btnConnect\" was not injected: check your FXML file 'MainGUI.fxml'.";
        assert btnGetBook != null : "fx:id=\"btnGetBook\" was not injected: check your FXML file 'MainGUI.fxml'.";
        assert btnGetAllBooks != null : "fx:id=\"btnGetAllBooks\" was not injected: check your FXML file 'MainGUI.fxml'.";
        assert btnAddBook != null : "fx:id=\"btnAddBook\" was not injected: check your FXML file 'MainGUI.fxml'.";
        assert btnUpdateBook != null : "fx:id=\"btnUpdateBook\" was not injected: check your FXML file 'MainGUI.fxml'.";
        assert btnDeleteBook != null : "fx:id=\"btnDeleteBook\" was not injected: check your FXML file 'MainGUI.fxml'.";
        assert btnUploadImage != null : "fx:id=\"btnUploadImage\" was not injected: check your FXML file 'MainGUI.fxml'.";
        assert btnDownloadImage != null : "fx:id=\"btnDownloadImage\" was not injected: check your FXML file 'MainGUI.fxml'.";
        assert btnFillReader != null : "fx:id=\"btnFillReader\" was not injected: check your FXML file 'MainGUI.fxml'.";
        assert btnAddLoan != null : "fx:id=\"btnAddLoan\" was not injected: check your FXML file 'MainGUI.fxml'.";
        assert txtId != null : "fx:id=\"txtId\" was not injected: check your FXML file 'MainGUI.fxml'.";
        assert txtTitle != null : "fx:id=\"txtTitle\" was not injected: check your FXML file 'MainGUI.fxml'.";
        assert txtAuthor != null : "fx:id=\"txtAuthor\" was not injected: check your FXML file 'MainGUI.fxml'.";
        assert txtImageName != null : "fx:id=\"txtImageName\" was not injected: check your FXML file 'MainGUI.fxml'.";
        assert tblBook != null : "fx:id=\"tblBook\" was not injected: check your FXML file 'MainGUI.fxml'.";
        assert tblColID != null : "fx:id=\"tblColID\" was not injected: check your FXML file 'MainGUI.fxml'.";
        assert tblColTitle != null : "fx:id=\"tblColTitle\" was not injected: check your FXML file 'MainGUI.fxml'.";
        assert tblColAuthor != null : "fx:id=\"tblColAuthor\" was not injected: check your FXML file 'MainGUI.fxml'.";
        assert lblMessage != null : "fx:id=\"lblMessage\" was not injected: check your FXML file 'MainGUI.fxml'.";
        this.txtUrl.setText("http://localhost:8080/09_BookService/webresources/");

        this.tblColID.setCellValueFactory(new PropertyValueFactory<Book, Integer>("id"));
        this.tblColTitle.setCellValueFactory(new PropertyValueFactory<Book, String>("title"));
        this.tblColAuthor.setCellValueFactory(new PropertyValueFactory<Book, String>("author"));

        this.cmbReader.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
            }
        });

        this.setButtonBookStatus(true);
        this.setButtonReaderStatus(true);
        this.lblMessage.setText("init successful");

    }
}
