package es.ua.datos.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Column;


@Entity
@Table(name = "RELATED_DATASETS")
public class RelatedDataset implements Serializable{
	
	@Id
	@Column(name="ID_DATASET")
	private int idDataset;

	@Id
	@Column(name="ID_RELATED")
	private int idRelated;
	
	@ManyToOne
	@JoinColumn(name="ID_DATASET")
	private Dataset dataset;
	@ManyToOne
	@JoinColumn(name="ID_RELATED")
	private Dataset related;
	
	public RelatedDataset() {}

	public int getIdDataset() {
		return idDataset;
	}

	public void setIdDataset(int idDataset) {
		this.idDataset = idDataset;
	}

	public int getIdRelated() {
		return idRelated;
	}

	public void setIdRelated(int idRelated) {
		this.idRelated = idRelated;
	}

	public Dataset getDataset() {
		return dataset;
	}

	public void setDataset(Dataset dataset) {
		this.dataset = dataset;
	}

	public Dataset getRelated() {
		return related;
	}

	public void setRelated(Dataset related) {
		this.related = related;
	}

}
