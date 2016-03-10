package es.ua.datos.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;


@Entity
@Table(name="LANGUAGES")
public class Language {
	
	@Id @GeneratedValue
	@Column(name="ID_LANGUAGE")
	private int idLanguage;
	
	@Column(name="NAME")
	private String name;
	
	@Column(name="ABBREVIATION")
	private String abbreviation;
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name="ID_LANGUAGE")
	private List<Translation> traslations = new ArrayList<Translation>();
	

	public Language() {
		// TODO Auto-generated constructor stub
	}
	
	public Language(int idLanguage,String name,String abbreviation,List<Translation> traslations){
		
		super();
		this.idLanguage = idLanguage;
		this.name = name;
		this.abbreviation = abbreviation;
		this.traslations = traslations;				
	}


	/**
	 * @return the idLanguage
	 */
	public int getIdLanguage() {
		return idLanguage;
	}


	/**
	 * @param idLanguage the idLanguaje to set
	 */
	public void setIdLanguage(int idLanguage) {
		this.idLanguage = idLanguage;
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


	/**
	 * @return the abbreviation
	 */
	public String getAbreviation() {
		return abbreviation;
	}


	/**
	 * @param abreviation the abreviation to set
	 */
	public void setAbbreviation(String abreviation) {
		this.abbreviation = abreviation;
	}

	/**
	 * @return the traslations
	 */
	public List<Translation> getTraslations() {
		return traslations;
	}

	/**
	 * @param traslations the traslations to set
	 */
	public void setTraslations(List<Translation> traslations) {
		this.traslations = traslations;
	}

	
	

}
