package gui;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import db.DbException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import model.entities.Department;
import model.entities.Seller;
import model.exceptions.ValidationException;
import model.services.DepartmentService;
import model.services.SellerService;


public class SellerFormController implements Initializable {

	private Seller sellerEntity;
	
	private SellerService sellerService;
	
	private DepartmentService departmentService;
	
	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();
	
	@FXML
	private TextField txtId;
	
	@FXML
	private TextField txtName;
	
	@FXML
	private TextField txtEmail;
	
	@FXML
	private DatePicker dpBirthDate;
	
	@FXML
	private TextField txtBaseSalary;
	
	@FXML
	private Label lblErrorName;
	
	@FXML
	private Label lblErrorEmail;
	
	@FXML
	private Label lblErrorBirthDate;
	
	@FXML
	private Label lblErrorBaseSalary;
	
	@FXML
	private ComboBox<Department> cmbDepartment;
	
	@FXML
	private Button btnSave;
	
	@FXML
	private Button btnCancel;
	
	private ObservableList<Department> obsList;
	
	
	public void subscribeDataChangeListener(DataChangeListener listener) {
		dataChangeListeners.add(listener);
	}
	
	public void setSellerEntity(Seller sellerEntity) {
		this.sellerEntity = sellerEntity;
	}
	
	public void setServices(SellerService sellerService, DepartmentService departmentService) {
		this.sellerService = sellerService;
		this.departmentService = departmentService;
	}
	
	
	public void updateFormData() {
		if(sellerEntity == null) {
			throw new IllegalStateException("Seller Entity was null");		
		}
		txtId.setText(String.valueOf(sellerEntity.getId()));
		txtName.setText(sellerEntity.getName());
		txtEmail.setText(sellerEntity.getEmail());
		Locale.setDefault(Locale.US);
		if(sellerEntity.getBirthDate() != null) {
			dpBirthDate.setValue(LocalDate.ofInstant(sellerEntity.getBirthDate().toInstant(), ZoneId.systemDefault()));
		}
		txtBaseSalary.setText(String.format("%.2f", sellerEntity.getBaseSalary()));
		if(sellerEntity.getDepartment() == null) {
			cmbDepartment.getSelectionModel().selectFirst();
		}
		cmbDepartment.setValue(sellerEntity.getDepartment());
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
		Constraints.setTextFieldMaxLength(txtName, 70);
		Constraints.setTextFieldMaxLength(txtEmail, 60);
		Constraints.setTextFieldDouble(txtBaseSalary);
		Utils.formatDatePicker(dpBirthDate, "dd/MM/yyyy");
		
		initializeComboBoxDepartment();
	}
	
	public void loadAssociatedObjects() {
		if(departmentService == null) {
			throw new IllegalStateException("Department Service was null");
			
		}
		List<Department> departments = departmentService.findAll();
		//obsList.addAll(departments);
		obsList = FXCollections.observableArrayList(departments);
		cmbDepartment.setItems(obsList);
	}
	
	private void initializeComboBoxDepartment() {
		Callback<ListView<Department>, ListCell<Department>> factory = lv -> new ListCell<Department>() {
			@Override
			protected void updateItem(Department item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty ? "" : item.getName());
			}
		};
		
		cmbDepartment.setCellFactory(factory);
		cmbDepartment.setButtonCell(factory.call(null));
	}

}
