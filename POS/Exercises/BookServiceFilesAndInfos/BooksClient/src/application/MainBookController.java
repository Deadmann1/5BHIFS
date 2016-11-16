package application;
	
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

import javax.ws.rs.core.MediaType;

import application.Classes.Book;
import application.Classes.BookList;
import application.Classes.ServiceCaller;

import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.multipart.FormDataMultiPart;
import com.sun.jersey.multipart.file.FileDataBodyPart;


public class MainBookController extends Application implements Initializable{

	@FXML
	private Label txt_message;
	
	@SuppressWarnings("unused")
	private static String message;

	@FXML
	private TextField txtUrl;

	@FXML
	private TextField txtBookID;
	
	@FXML
	private TextField txtAuthor;
	
	@FXML
	private TextField txtTitle;

	@FXML
	private TextField txtImagePath;

	@FXML
	private TextField txtFileName;
	
	@FXML
	private TableView<Book> tblBooks; 
	
	@FXML
	private TableColumn<Book,Number> colId;

	@FXML
	private TableColumn<Book,String> colTitle;

	@FXML
	private TableColumn<Book,String> colAuthor;

	@FXML
	private TableColumn<Book,String> colFileName;
	
	
    @FXML
    private Button btnConn;

    @FXML
    private Button btnaddBook;

    @FXML
    private Button btnupdateBook;
    
    @FXML
    private Button btngetBook;
    
    @FXML
    private Button btngetBooks;
    
    @FXML
    private Button btnDelete;
    
    @FXML
    private Button btnDownloadImage;
    
    @FXML
    private Button btnUploadImage;
    
    @FXML
    private Button btnChooseImg;
    
    @FXML
    private ImageView imageView;
    
	private ServiceCaller webservice=null;
	

	private BookList bookList;


	private String picturePath = "/tmp/tmp_book.jpg";
	
	@FXML
	private Pane imagePane;

	@FXML
	private Label lblFileName;
	
	private String localImage;
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent p = FXMLLoader.load(getClass().getResource("resources/MainBookWindow.fxml"));
			Scene scene = new Scene(p);
			scene.getStylesheets().add(getClass().getResource("resources/application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Book Client");
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	private void onAction_btnConn(ActionEvent ae){
		try {
			this.webservice = new ServiceCaller(txtUrl.getText());
			
			this.btnaddBook.setDisable(false);
			this.btngetBooks.setDisable(false);
			this.btngetBook.setDisable(false);
			this.btnupdateBook.setDisable(false);
			this.btnDelete.setDisable(false);
			this.btnDownloadImage.setDisable(false);
			this.btnChooseImg.setDisable(false);
			
			txt_message.setText("connection established");
		} catch (Exception e) {
			// e.printStackTrace();
			txt_message.setText("connection not established: "+e.getMessage());
		}
	}
	
	@FXML
	private void onAction_getBooks(ActionEvent ae){
		this.bookList =  webservice.getBooks();
		this.updateTable();
		System.out.println(this.bookList.getSize()+" Books laoded");
	}
	
	@FXML
	private void onAction_btngetBook(ActionEvent ae){
			try {
				Book b = this.webservice.getBook(Integer.parseInt(this.txtBookID.getText()));
				if(b != null){
					tblBooks.getItems().clear();
					tblBooks.getItems().add(b);
				}else{
					txt_message.setText("no book with Id Found");
				}
			} catch (UniformInterfaceException e) {
				txt_message.setText("error: "+e.getResponse().getStatus()+": "+e.getResponse().toString());
				//e.printStackTrace();
			} catch (Exception e) {
				//e.printStackTrace();
				txt_message.setText("please input valid Number");
			}
	}
	
	@FXML
	private void onAction_btnAdd(ActionEvent ae){
		try {
			if(!this.txtTitle.getText().equals("") && !this.txtAuthor.getText().equals("")){
				Book tmp = new Book(txtTitle.getText(),-1,txtAuthor.getText(),txtFileName.getText());
				String msg = this.webservice.addBook(tmp);
				if(!msg.equals("")){
					this.bookList.addBook(tmp);
					this.updateTable();
				}
				this.txt_message.setText(msg);
			}else{
				this.txt_message.setText("Insert valid title and author");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	private void updateTable(){
		ObservableList<Book> ol = FXCollections.observableArrayList(this.bookList.getBooks());
		tblBooks.setItems(ol);
	}
	
	@FXML
	private void onClick_tblBooks(MouseEvent ae){
		try {
			if(this.tblBooks.getSelectionModel().getSelectedIndex() != -1){
				Book tmp = this.tblBooks.getSelectionModel().getSelectedItem();
				this.txtBookID.setText( tmp.getId()+"");
				this.txtAuthor.setText(tmp.getAuthor());
				this.txtTitle.setText(tmp.getTitle());
				this.txtFileName.setText(tmp.getFileName());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	
	@FXML
	private void onAction_btnUpdate(ActionEvent ae){
		try {
			
			Book tmp = new Book(txtTitle.getText(),Integer.parseInt(this.txtBookID.getText()),txtAuthor.getText(),this.txtFileName.getText());
			this.txt_message.setText(this.webservice.updateBook(tmp));
			this.bookList.updateBook(tmp);
			this.updateTable();
			
		} catch (Exception e) {
			e.printStackTrace();
			this.txt_message.setText("Input valid ID-Number");
		}
		
	}
	
	@FXML
	private void onAction_btnDelete(ActionEvent ae){
		try {
			if(!this.txtBookID.getText().equals("")){
				this.webservice.deleteBook(this.txtBookID.getText());
				this.txt_message.setText("Deleted Book successfully");
				this.bookList.removeBook(Integer.parseInt(this.txtBookID.getText()));
				this.updateTable();
			}else{
				this.txt_message.setText("select a book");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void setMessage(String string) {
		message = string;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		colTitle.setCellValueFactory(new PropertyValueFactory<Book,String>("title"));
		colTitle.setCellFactory(TextFieldTableCell.forTableColumn());
		colAuthor.setCellValueFactory(new PropertyValueFactory<Book,String>("author"));
		colAuthor.setCellFactory(TextFieldTableCell.forTableColumn());
		colId.setCellValueFactory(new PropertyValueFactory<Book, Number>("id"));
		colId.setCellFactory(TextFieldTableCell.<Book,Number>forTableColumn(new NumberStringConverter()));
		colFileName.setCellValueFactory(new PropertyValueFactory<Book,String>("fileName"));
		colFileName.setCellFactory(TextFieldTableCell.forTableColumn());
		
	}

	
	public String addImage(int _id, String _nameOfImage){
		String strid = new Integer(_id).toString();
		File fileToUpload = new File(_nameOfImage);
		FormDataMultiPart multiPart = new FormDataMultiPart();
		multiPart.setMediaType(MediaType.MULTIPART_FORM_DATA_TYPE);
		multiPart.bodyPart(new FileDataBodyPart("file",fileToUpload, MediaType.APPLICATION_OCTET_STREAM_TYPE));
		webservice.sendImage(multiPart,strid);
		return _nameOfImage;
	}
	
	
	@FXML
	private void onAction_downloadImage(ActionEvent ae){
		try {
			if(!this.txtFileName.getText().equals("")&&this.webservice.getImage(this.txtBookID.getText())!=null){
				this.imageView.setImage(this.getLocalImage());
				this.txt_message.setText("Image downloaded successfully");
			}else{
				this.txt_message.setText("There is no Image for this Book");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	

	private Image getLocalImage() {
		return new Image("file:"+this.picturePath);
	}

	@FXML
	private void onAction_btnChooseImg(ActionEvent ae){
		try {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Open Image File to Upload");
			fileChooser.setInitialFileName("*.jpg");
			fileChooser.setSelectedExtensionFilter(new ExtensionFilter("Images (png,jpg,bmp)","*.jpg","*.png","*.jpeg","*.bmp"));
			File file = fileChooser.showOpenDialog(this.imageView.getScene().getWindow());
			if(file != null){
				
				this.setAbsoluteImage(file.getAbsolutePath());
				
			}else{
				this.txt_message.setText("you didnt choose any file!");
			}
		} catch (Exception e) {
			this.txt_message.setText("error: " + e.getMessage());
		}
		
	}
	

	private void setAbsoluteImage(String path) {
		this.localImage = path;
		this.imageView.setImage(new Image("file:"+path));
		this.btnUploadImage.setDisable(false);
		String[] pathSplit = path.split(File.separator);
		this.lblFileName.setText(pathSplit[pathSplit.length-1]);
		this.lblFileName.setDisable(false);
	}

	@FXML
	private void onAction_uploadImage(ActionEvent ae){
		try {
			//save filename to book
			if(!this.txtBookID.getText().equals("")){
				this.addImage(Integer.parseInt(this.txtBookID.getText()), this.localImage);
			}else{
				this.txt_message.setText("insert valid bookid");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	

	@FXML
	private void onDragOver(DragEvent e){
		final Dragboard db = e.getDragboard();
		 
        final boolean isAccepted = db.getFiles().get(0).getName().toLowerCase().endsWith(".png")
                || db.getFiles().get(0).getName().toLowerCase().endsWith(".jpeg")
                || db.getFiles().get(0).getName().toLowerCase().endsWith(".jpg");
 
        if (db.hasFiles()) {
            if (isAccepted) {
            	imagePane.setStyle("-fx-border-color: green;"
              + "-fx-border-width: 2;");
                e.acceptTransferModes(TransferMode.COPY);
            }else{
            	imagePane.setStyle("-fx-border-color: red;"
                        + "-fx-border-width: 2;");
            }
        } else {
            e.consume();
        }
	}

	
	//split files wenn grosse dateien raufgeschickt werden
	
	@FXML
	private void onDragExited(DragEvent e){
		this.imagePane.setStyle("-fx-border-color: #C6C6C6;");
	}
	
	@FXML
	private void onDragDropped(DragEvent e){
		final Dragboard db = e.getDragboard();
        boolean success = false;
        if (db.hasFiles()) {
            success = true;
            // Only get the first file from the list
            final File file = db.getFiles().get(0);
            this.setAbsoluteImage(file.getAbsolutePath());
                
        }
        e.setDropCompleted(success);
        e.consume();
	}
	
	
}
