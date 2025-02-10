package model.services;

import java.util.ArrayList;
import java.util.List;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentService {
	
	private DepartmentDao dao = DaoFactory.createDepartmentDao();

	
	public List<Department> findAll(){
		System.out.println("department service");
		return dao.findAll();
	}
	
	public void saveOrUpdate(Department department) {
		if(department.getId() == null) {
			dao.insert(department);
		}
		else {
			dao.update(department);
		}
	}
}
