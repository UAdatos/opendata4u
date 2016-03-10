package es.ua.datos.model;

import javax.persistence.*;

@Entity
@Table(name = "RESOURCES_TRANSP")
public class ResourceTransp {
	
	@Id @GeneratedValue
	@Column(name="ID_RESOURCE_TRANSP")
	private int idResourceTransp;
	
	@Column(name="ID_DATASET_TRANSP")	
	private int idDatasetTransp;	

	@Column(name="FORMAT")
	private String format;//enumerado? controlamos los formatos
	
	@Column(name="URL")
	private String url;
	
	@Column(name="QUALITY")
	private int quality;
	
	@Column(name="NAME")
	private String name;
	
	public ResourceTransp(){}

	public ResourceTransp(int idResourceTransp,int idDatasetTransp, String format, String url, int quality, String name) {
		super();
		this.idResourceTransp = idResourceTransp;
		this.idDatasetTransp = idDatasetTransp;
		this.format = format;
		this.url = url;
		this.quality = quality;
		this.name = name;
	}
	
	public int getIdResourceTransp() {
		return idResourceTransp;
	}


	public void setIdResourceTransp(int idResourceTransp) {
		this.idResourceTransp = idResourceTransp;
	}


	public int getIdDatasetTransp() {
		return idDatasetTransp;
	}


	public void setIdDatasetTransp(int idDatasetTransp) {
		this.idDatasetTransp = idDatasetTransp;
	}


	public String getFormat() {
		return format;
	}


	public void setFormat(String format) {
		this.format = format;
	}


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}


	public int getQuality() {
		return quality;
	}


	public void setQuality(int quality) {
		this.quality = quality;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

}
