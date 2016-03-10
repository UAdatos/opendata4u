package es.ua.datos.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name="RESOURCES_APPLICATION")
public class ResourcesApplication implements Serializable{

	@Id @GeneratedValue
	@Column(name="ID_RESOURCE_APP")
	private int idResourceApp;
	
	@Column(name="ID_APPLICATION")
	private int idApplication;
	
	//@ManyToOne
	//@JoinColumn(name="ID_APPLICATION",nullable = false, insertable = false, updatable = false)//Comprobar esto, en CategoryDataset no lo necesitaba
    //private Application application;
	
	@Column(name="URL")
	private String url;
	
	@Column(name="NAME_RESOURCE")
	private String nameResource;
	
	@Column(name="ID_TYPE_APPLICATION")
	private int idTypeApplication;
	
	@ManyToOne
	@JoinColumn(name="ID_TYPE_APPLICATION",nullable = false, insertable = false, updatable = false)//Comprobar esto, en CategoryDataset no lo necesitaba
    private ApplicationTypes typeApplication;
	
	/*@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name="ID_TYPE_APPLICATION")
	private List<ApplicationTypes> typeApp = new ArrayList<ApplicationTypes>();*/
	
	public ResourcesApplication() {
		// TODO Auto-generated constructor stub
	}

	public ResourcesApplication(int idResourceApp, int idApplication,
			String url, String nameResource, int idApplicationType,ApplicationTypes typeApplication) {
		super();
		this.idResourceApp = idResourceApp;
		this.idApplication = idApplication;
		this.url = url;
		this.nameResource = nameResource;
		
		this.idTypeApplication = idApplicationType;
		this.typeApplication = typeApplication;
	}

	public int getIdResourceApp() {
		return idResourceApp;
	}

	public void setIdResourceApp(int idResourceApp) {
		this.idResourceApp = idResourceApp;
	}

	public int getIdApplication() {
		return idApplication;
	}

	public void setIdApplication(int idApplication) {
		this.idApplication = idApplication;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getNameResource() {
		return nameResource;
	}

	public void setNameResource(String nameResource) {
		this.nameResource = nameResource;
	}

	public int getIdTypeApplication() {
		return idTypeApplication;
	}

	public void setIdApplicationType(int idTypeApplication) {
		this.idTypeApplication = idTypeApplication;
	}

	public ApplicationTypes getTypeApplication() {
		return typeApplication;
	}

	public void setTypeApplication(ApplicationTypes typeApplication) {
		this.typeApplication = typeApplication;
	}

	public void setIdTypeApplication(int idTypeApplication) {
		this.idTypeApplication = idTypeApplication;
	}

	
}
