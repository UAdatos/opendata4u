package es.ua.datos.model;

import javax.persistence.*;

@Entity
@Table(name = "RESOURCES")
public class Resource{
	
	//enum frecuencia{diaria,semanal,mensual,trimestral,semestral,anual};?�?� segun bd
	//enum formato{csc,pdf,xls}; ?�? segun bd
	@Id @GeneratedValue
	@Column(name="ID_RESOURCE")
	private int idResource;
	
	@Column(name="ID_DATASET")	
	private int idDataset;	

	@Column(name="FORMAT")
	private String format;//enumerado? controlamos los formatos
	
	@Column(name="URL")
	private String url;
	
	@Column(name="QUALITY")
	private int quality;
	
	
	
	
	public Resource(){}

	public Resource(int idResource,int idDataset, String format, String url, int quality) {
		super();
		this.idResource = idResource;
		this.idDataset = idDataset;
		this.format = format;
		this.url = url;
		this.quality = quality;
	}

	public int getIdResource() {
		return idResource;
	}

	public void setIdResource(int idResource) {
		this.idResource = idResource;
	}
	
	public int getIdDataset() {
		return idDataset;
	}

	public void setIdDataset(int idDataset) {
		this.idDataset = idDataset;
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

}
