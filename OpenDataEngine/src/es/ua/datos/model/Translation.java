package es.ua.datos.model;

import javax.persistence.*;

@Entity
@Table(name="TRANSLATIONS")
public class Translation {

	@Id @GeneratedValue
	@Column(name="ID_TRANSLATION")
	private int idTranslation;
	
	@Column(name="ID_OBJECT")
	private int idObject;
	
	@Column(name="ID_LANGUAGE")
	private int idLanguage;
	
	@Column(name="TABLE_NAME")
	private String tableName;
	
	@Column(name="FIELD_NAME")
	private String fieldName;
	
	@Column(name="TRANSLATION")
	private String translation;
	
	
	
	public Translation() {
		// TODO Auto-generated constructor stub
	}
	
	public Translation(int idTranslation,int idObject,int idLanguaje,String tableName,String fieldName, String translation){
		
		super();
		this.idTranslation = idTranslation;
		this.idObject = idObject;
		this.idLanguage = idLanguaje;
		this.tableName = tableName;
		this.fieldName = fieldName;
		this.translation = translation;
	}



	/**
	 * @return the idTranslation
	 */
	public int getIdTranslation() {
		return idTranslation;
	}



	/**
	 * @param idTranslation the idTranslation to set
	 */
	public void setIdTranslation(int idTranslation) {
		this.idTranslation = idTranslation;
	}



	/**
	 * @return the idObject
	 */
	public int getIdObject() {
		return idObject;
	}



	/**
	 * @param idObject the idObject to set
	 */
	public void setIdObject(int idObject) {
		this.idObject = idObject;
	}



	/**
	 * @return the idLanguaje
	 */
	public int getIdLanguage() {
		return idLanguage;
	}



	/**
	 * @param idLanguaje the idLanguaje to set
	 */
	public void setIdLanguaje(int idLanguage) {
		this.idLanguage = idLanguage;
	}



	/**
	 * @return the tableName
	 */
	public String getTableName() {
		return tableName;
	}



	/**
	 * @param tableName the tableName to set
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}



	/**
	 * @return the fieldName
	 */
	public String getFieldName() {
		return fieldName;
	}



	/**
	 * @param fieldName the fieldName to set
	 */
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}



	/**
	 * @return the translation
	 */
	public String getTranslation() {
		return translation;
	}



	/**
	 * @param translation the translation to set
	 */
	public void setTranslation(String translation) {
		this.translation = translation;
	}

}
