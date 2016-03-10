package es.ua.datos.model;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name="APP_USES_DATASETS")
public class ApplicationDataset implements Serializable{
	
	@Id
	@Column(name="ID_APPLICATION")
	private int idApplication;
	
	@Id
	@Column(name="ID_DATASET")
	private int idDataset;
	
	@ManyToOne
	@JoinColumn(name="ID_APPLICATION")
	private Application application;
	
	@ManyToOne
	@JoinColumn(name="ID_DATASET")
	private Dataset dataset;

	public ApplicationDataset() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ApplicationDataset(int idApplication, int idDataset,
			Application application, Dataset dataset) {
		super();
		this.idApplication = idApplication;
		this.idDataset = idDataset;
		this.application = application;
		this.dataset = dataset;
	}

	public int getIdApplication() {
		return idApplication;
	}

	public void setIdApplication(int idApplication) {
		this.idApplication = idApplication;
	}

	public int getIdDataset() {
		return idDataset;
	}

	public void setIdDataset(int idDataset) {
		this.idDataset = idDataset;
	}

	public Application getApplication() {
		return application;
	}

	public void setApplication(Application application) {
		this.application = application;
	}

	public Dataset getDataset() {
		return dataset;
	}

	public void setDataset(Dataset dataset) {
		this.dataset = dataset;
	}

}
