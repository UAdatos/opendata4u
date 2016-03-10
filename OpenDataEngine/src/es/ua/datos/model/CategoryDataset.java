package es.ua.datos.model;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name="CATEGORIES_DATASETS")
public class CategoryDataset implements Serializable{

	@Id
	@Column(name="ID_CATEGORY")
	private int idCategory;
	
	@Id
	@Column(name="ID_DATASET")
	private int idDataset;
	
	@ManyToOne
	@JoinColumn(name="ID_CATEGORY")
	private Category category;
	
	@ManyToOne
	@JoinColumn(name="ID_DATASET")
	private Dataset dataset;

	public int getIdCategory() {
		return idCategory;
	}

	public void setIdCategory(int idCategory) {
		this.idCategory = idCategory;
	}

	public int getIdDataset() {
		return idDataset;
	}

	public void setIdDataset(int idDataset) {
		this.idDataset = idDataset;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Dataset getDataset() {
		return dataset;
	}

	public void setDataset(Dataset dataset) {
		this.dataset = dataset;
	}
}
