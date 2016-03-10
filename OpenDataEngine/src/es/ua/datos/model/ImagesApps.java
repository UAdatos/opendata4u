package es.ua.datos.model;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "IMAGES_APPS")
public class ImagesApps implements Serializable{
	
	@Id @GeneratedValue
	@Column(name="ID_IMAGE")
	private int idImage;
	
	@Column(name="ID_APPLICATION")
	private int idApplication;
	
	@Column(name="TYPE")
	private String type;
	
	@Column(name="NAME_IMAGE")
	private String nameImage;
	
	//@ManyToOne
    //private Application application;

	public ImagesApps() {};

	public ImagesApps(int idImage, int idApplication, String type,
			String nameImage) {
		super();
		this.idImage = idImage;
		this.idApplication = idApplication;
		this.type = type;
		this.nameImage = nameImage;
	}

	public int getIdImage() {
		return idImage;
	}

	public void setIdImage(int idImage) {
		this.idImage = idImage;
	}

	public int getIdApplication() {
		return idApplication;
	}

	public void setIdApplication(int idApplication) {
		this.idApplication = idApplication;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getNameImage() {
		return nameImage;
	}

	public void setNameImage(String nameImage) {
		this.nameImage = nameImage;
	}

}
