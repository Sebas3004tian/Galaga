package control;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.Main;
import model.Player;

public class MainWindow implements Initializable{

    @FXML
    private Button playBTN;

    @FXML
    private TextField nameTF;
    
    @FXML
    void play(ActionEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader(Main.class.getResource("../ui/GameWindow.fxml"));
    	
    	if(!nameTF.getText().equalsIgnoreCase("")) {
    		Player player = new Player(nameTF.getText());
        	
        	loader.setController(new GameWindow(player));
    		Parent parent = loader.load();
    		Scene scene = new Scene(parent);
    		Stage stage = new Stage();
    		stage.setScene(scene);
    		stage.show();
    		
    		Stage stage1 = (Stage) playBTN.getScene().getWindow();
    	    stage1.close();
    	} else {
    		Alert alert = new Alert(Alert.AlertType.ERROR);
    	    alert.setHeaderText(null);
    	    alert.setTitle("Error");
    	    alert.setContentText("Ingrese el nombre");
    	    alert.showAndWait();
    	}
    	
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}

}
