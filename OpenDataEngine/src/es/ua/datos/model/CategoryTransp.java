package es.ua.datos.model;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "CATEGORIES_TRANSP")
public class CategoryTransp {
	
	@Id @GeneratedValue
	@Column(name="ID_CATEGORY_TRANSP")
	private int idCategoryTransp;
	
	@Column(name="NAME_CATEGORY_TRANSP")
	private String nameCategoryTransp;
	
	@Column(name="DESCRIPTION")
	private String description;
	
	@Column(nullable=true,name="PRIORITY")
	private Integer priority;
	
	@Column(nullable=true,name="ID_HIGHER_CATEGORY")
	private Integer idHigherCategory;
	
		
		
	public CategoryTransp(){}
	
	public CategoryTransp(int idCategoryTransp, String nameCategoryTransp,String description,int priority,int idHigherCategory){
		
		super();
		this.idCategoryTransp = idCategoryTransp;
		this.nameCategoryTransp = nameCategoryTransp;
		this.description = description;
		this.priority = priority;
		this.idHigherCategory = idHigherCategory;
		
		
	}
	
	
	/**
	 * @return the idCategoryTransp
	 */
	public int getIdCategoryTransp() {
		return idCategoryTransp;
	}

	/**
	 * @param idCategoryTransp the idCategoryTransp to set
	 */
	public void setIdCategoryTransp(int idCategoryTransp) {
		this.idCategoryTransp = idCategoryTransp;
	}

	/**
	 * @return the nameCategoryTransp
	 */
	public String getNameCategoryTransp() {
		return nameCategoryTransp;
	}

	/**
	 * @param nameCategoryTransp the nameCategoryTransp to set
	 */
	public void setNameCategoryTransp(String nameCategoryTransp) {
		this.nameCategoryTransp = nameCategoryTransp;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the priority
	 */
	public int getPriority() {
		return priority;
	}

	/**
	 * @param priority the priority to set
	 */
	public void setPriority(int priority) {
		this.priority = priority;
	}



}
