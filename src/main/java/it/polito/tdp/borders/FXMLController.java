
package it.polito.tdp.borders;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

import it.polito.tdp.borders.model.Country;
import it.polito.tdp.borders.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;

public class FXMLController {

	private Model model;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtAnno"
    private TextField txtAnno; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader
    
    @FXML
    private ChoiceBox<Country> boxStati;

    @FXML
    private Button btnCerca;

    
    @FXML
    void cerca(ActionEvent event) {
    	String s = "";
    	int anno;
    	try {
    		anno = Integer.parseInt(this.txtAnno.getText());
    	}catch(NumberFormatException e) {
    		e.printStackTrace();
    		System.out.println("Inserire un anno in formato numerico!");
    		return;
    	}
    	if(anno<1816) {
    		this.txtResult.appendText("Dati mancanti per questo anno e precedenti");
    		return;
    	}
    	
    	model.creaGrafo(anno);
    	Set<Country> lista = model.viciniBreadth(this.boxStati.getValue());
    	for(Country c : lista) {
    		s += c.toString()+"\n";
    	}
    	this.txtResult.appendText("Lista stati raggiungibili:\n"+s);
    	
    }

    @FXML
    void doCalcolaConfini(ActionEvent event) {
    	this.txtResult.clear();
    	int anno;
    	try {
    		anno = Integer.parseInt(this.txtAnno.getText());
    	}catch(NumberFormatException e) {
    		e.printStackTrace();
    		System.out.println("Inserire un anno in formato numerico!");
    		return;
    	}
    	if(anno<1816) {
    		this.txtResult.appendText("Dati mancanti per questo anno e precedenti");
    		return;
    	}
    	
    	model.creaGrafo(anno);
    	this.txtResult.appendText(model.output());

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtAnno != null : "fx:id=\"txtAnno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        assert boxStati != null : "fx:id=\"boxStati\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCerca != null : "fx:id=\"btnCerca\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	this.boxStati.getItems().addAll(model.getStati());
    }
}
