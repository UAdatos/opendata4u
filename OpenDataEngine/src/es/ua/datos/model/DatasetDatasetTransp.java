package es.ua.datos.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name="DATASETS_DATASETS_TRANSP")
public class DatasetDatasetTransp implements Serializable{
	
	@Id
	@Column(name="ID_DATASET")
	private int idDataset;
	
	@Id
	@Column(name="ID_DATASET_TRANSP")
	private int idDatasetTransp;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_DATASET")
	private Dataset dataset;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_DATASET_TRANSP")
	private DatasetTransp datasetTransp;
	
	public DatasetDatasetTransp() {
		// TODO Auto-generated constructor stub
	}
	
	public DatasetDatasetTransp(int idDataset,int idDatasetTransp, Dataset dataset, DatasetTransp datasetTrasnp){
		
		super();
		this.idDataset=idDataset;
		this.idDatasetTransp=idDatasetTransp;
		this.dataset=dataset;
		this.datasetTransp=datasetTrasnp;
	}

	/**
	 * @return the idDataset
	 */
	public int getIdDataset() {
		return idDataset;
	}

	/**
	 * @param idDataset the idDataset to set
	 */
	public void setIdDataset(int idDataset) {
		this.idDataset = idDataset;
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
	 * @return the dataset
	 */
	public Dataset getDataset() {
		return dataset;
	}

	/**
	 * @param dataset the dataset to set
	 */
	public void setDataset(Dataset dataset) {
		this.dataset = dataset;
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
