package es.ua.datos.model;

import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name="APPLICATION_TYPES")
public class ApplicationTypes {
	
	@Id @GeneratedValue
	@Column(name="ID_TYPE_APPLICATION")
	private int idTypeApplication;
	
	@Column(name="NAME_TYPE")
	private String nameType;
	

	public ApplicationTypes() {
		// TODO Auto-generated constructor stub
	}


	public ApplicationTypes(int idApplicationType, String nameType) {
		super();
		this.idTypeApplication = idApplicationType;
		this.nameType = nameType;
	}


	public int getIdApplicationType() {
		return idTypeApplication;
	}


	public void setIdApplicationType(int idTypeApplication) {
		this.idTypeApplication = idTypeApplication;
	}


	public String getNameType() {
		return nameType;
	}


	public void setNameType(String nameType) {
		this.nameType = nameType;
	}

}
