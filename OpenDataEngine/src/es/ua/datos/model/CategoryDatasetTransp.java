package es.ua.datos.model;

import java.io.Serializable;

import javax.persistence.*;


@Entity
@Table(name="CATEGORIES_DATASETS_TRANSP")
public class CategoryDatasetTransp implements Serializable{

	
	@Id
	@Column(name="ID_CATEGORY_TRANSP")
	private int idCategoryTransp;
	
	
	@Id
	@Column(name="ID_DATASET_TRANSP")
	private int idDatasetTransp;
	
	@ManyToOne
	@JoinColumn(name="ID_CATEGORY_TRANSP")
	private CategoryTransp categoryTransp;
	
	@ManyToOne
	@JoinColumn(name="ID_DATASET_TRANSP")
	private DatasetTransp datasetTransp;
	
	public CategoryDatasetTransp() {
		// TODO Auto-generated constructor stub
	}
	
	public CategoryDatasetTransp(int idDatasetTransp, int idCategoryTransp,DatasetTransp datasetTransp,CategoryTransp categoryTransp){
		
		super();
		this.idDatasetTransp = idDatasetTransp;
		this.idCategoryTransp = idCategoryTransp;
		this.datasetTransp = datasetTransp;
		this.categoryTransp = categoryTransp;
		
		
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
	 * @return the idDatasetTransp
	 */
	public int getIdDatasetTransp() {
		return idDatasetTransp;
	}

	/**
	 * @param idDatasetTransp the idDatasetTransp to set
	 */
	public void setIdDatasetTransp(int idDatasetTransp) {
		this.idDatasetTransp = idDatasetTransp;
	}

	/**
	 * @return the categoryTransp
	 */
	public CategoryTransp getCategoryTransp() {
		return categoryTransp;
	}

	/**
	 * @param categoryTransp the categoryTransp to set
	 */
	public void setCategoryTransp(CategoryTransp categoryTransp) {
		this.categoryTransp = categoryTransp;
	}

	/**
	 * @return the datasetTransp
	 */
	public DatasetTransp getDatasetTransp() {
		return datasetTransp;
	}

	/**
	 * @param datasetTransp the datasetTransp to set
	 */
	public void setDatasetTransp(DatasetTransp datasetTransp) {
		this.datasetTransp = datasetTransp;
	}

	
	
}
