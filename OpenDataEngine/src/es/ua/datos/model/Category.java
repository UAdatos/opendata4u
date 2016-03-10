package es.ua.datos.model;


import java.util.ArrayList;
import java.util.List;


import javax.persistence.*;


@Entity
@Table(name="CATEGORIES")
public class Category{

	@Id @GeneratedValue
	@Column(name="ID_CATEGORY")
	private int idCategory;
	
	@Column(name="NAME_CATEGORY")
	private String name;
		
	//@ManyToMany(fetch=FetchType.LAZY,mappedBy = "categories")
	
	// = new ArrayList<Dataset>(); 
	
	public Category(){}
	
	public Category(int idCategory,String name){
	
		super();
		this.idCategory = idCategory;
		this.name = name;
	}
	
	public int getIdCategory() {
		return idCategory;
	}

	public void setIdCategory(int idCategory) {
		this.idCategory = idCategory;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	/*@ManyToMany
	@JoinTable(name="CATEGORIES_DATASETS",
			joinColumns={@JoinColumn(name="ID_CATEGORY")},
			inverseJoinColumns={@JoinColumn(name="ID_DATASET")})
	private List<Dataset> datasets = new ArrayList<Dataset>();	
	public List<Dataset> getDatasets() {
		return datasets;
	}

	public void setDatasets(List<Dataset> datasets) {
		this.datasets = datasets;
	}
	
	public void addDataset(Dataset dataset)
    {
        this.datasets.add(dataset);
    }*/
	
	
}
