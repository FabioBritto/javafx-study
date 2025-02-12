package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

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
import model.entities.Department;
import model.services.DepartmentService;

/*
 * Para que eu possa "capturar" o evento de criação ou atualização de Departments,
 * eu preciso usar o Padrão de Projeto OBSERVER. Portanto, precisarei de duas
 * classes. Uma sendo SUBJECT (a que gera o evento) e a outra OBSERVER (a que o recebe)
 * 
 * Esta é uma forma de unir dois OBJETOS de uma forma MUITO DESACOPLADA
 */
public class DepartmentFormController implements Initializable {

	private Department departmentEntity;
	
	private DepartmentService departmentService;
	
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
	
	public void setDepartmentEntity(Department departmentEntity) {
		this.departmentEntity = departmentEntity;
	}
	
	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}
	
	public void updateFormData() {
		if(departmentEntity == null) {
			throw new IllegalStateException("Department Entity was null");		
		}
		txtId.setText(String.valueOf(departmentEntity.getId()));
		txtName.setText(departmentEntity.getName());
	}
	
	@FXML
	public void onBtnSaveAction(ActionEvent event) {
		if(departmentEntity == null || departmentService == null) {
			throw new IllegalStateException("Entity or Department was null!");
		}
		try {
			departmentEntity = getFormData();
			departmentService.saveOrUpdate(departmentEntity);
			notifyDataChangeListeners();
			Alerts.showAlert("DONE", null, "Changes Saved", AlertType.INFORMATION);
			Utils.currentStage(event).close();
		}
		catch(DbException e) {
			Alerts.showAlert("ERROR SAVING OBJECT", null, e.getMessage(), AlertType.ERROR);
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
	
	public Department getFormData() {
		Department dep = new Department();
		dep.setId(Utils.tryParseToInt(txtId.getText()));
		dep.setName(txtName.getText());	
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
