package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import db.DbException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Seller;
import model.exceptions.ValidationException;
import model.services.SellerService;


public class SellerFormController implements Initializable {

	private Seller sellerEntity;
	
	private SellerService sellerService;
	
	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();
	
	@FXML
	private TextField txtId;
	
	@FXML
	private TextField txtName;
	
	@FXML
	private Label lblErrorName;
	
	@FXML
	private Button btnSave;
	
	@FXML
	private Button btnCancel;
	
	
	public void subscribeDataChangeListener(DataChangeListener listener) {
		dataChangeListeners.add(listener);
	}
	
	public void setSellerEntity(Seller sellerEntity) {
		this.sellerEntity = sellerEntity;
	}
	
	public void setSellerService(SellerService sellerService) {
		this.sellerService = sellerService;
	}
	
	public void updateFormData() {
		if(sellerEntity == null) {
			throw new IllegalStateException("Seller Entity was null");		
		}
		txtId.setText(String.valueOf(sellerEntity.getId()));
		txtName.setText(sellerEntity.getName());
	}
	
	@FXML
	public void onBtnSaveAction(ActionEvent event) {
		if(sellerEntity == null || sellerService == null) {
			throw new IllegalStateException("Entity or Seller was null!");
		}
		try {
			sellerEntity = getFormData();
			sellerService.saveOrUpdate(sellerEntity);
			notifyDataChangeListeners();
			Alerts.showAlert("DONE", null, "Changes Saved", AlertType.INFORMATION);
			Utils.currentStage(event).close();
		}
		catch(ValidationException e) {
			setErrorMessage(e.getErrors());
		}
		catch(DbException e) {
			Alerts.showAlert("ERROR SAVING OBJECT", null, e.getMessage(), AlertType.ERROR);
		}
		
	}
	
	public void setErrorMessage(Map<String, String> errors) {
		Set<String> fields = errors.keySet();
		
		if(fields.contains("name")) {
			lblErrorName.setText(errors.get("name"));
		}
	}
	
	public void notifyDataChangeListeners() {
		for(DataChangeListener d : dataChangeListeners) {
			d.onDataChanged();
		}
	}
	
	@FXML
	public void onBtnCancelAction(ActionEvent event) {
		Utils.currentStage(event).close();
	}
	
	public Seller getFormData() {
		Seller dep = new Seller();
		
		ValidationException exception = new ValidationException("Validation Error");
		
		dep.setId(Utils.tryParseToInt(txtId.getText()));
		
		// .trim() -> Desconsidera os espaços em branco no início ou final
		if(txtName.getText() == null || txtName.getText().trim().equals("")) {
			exception.addError("name", "Field can't be empty");
		}
		dep.setName(txtName.getText());	
		
		/*
		 * Aqui, eu estou conferindo se a minha lista de errors é vazia
		 */
		if(exception.getErrors().size() > 0) {
			throw exception;
		}
		return dep;
	}
	
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}
	
	private void initializeNodes() {
		Constraints.setTextFieldInteger(txtId);
		Constraints.setTextFieldMaxLength(txtName, 40);
	}

}
