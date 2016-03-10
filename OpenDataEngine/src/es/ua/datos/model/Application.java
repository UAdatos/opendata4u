package es.ua.datos.model;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.IndexColumn;

@Entity
@Table(name = "APPLICATIONS")
public class Application implements Serializable{
	
	@Id @GeneratedValue
	@Column(name="ID_APPLICATION")
	private int id;
	
	@Column(name="NAME_APPLICATION")
	private String name;
	
	@Column(name="DESCRIPTION")
	private String description;
	
	@Column(name="PUBLISHER")
	private String publisher;
	
	@Column(name="SUBMIT_DATE")
	private Date submitDate;
	
	@Column(name="SCORE")
	private int score;
	
	@Column(name="NUM_VOTES")
	private int numVotes;
	
	@Column(name="ASSESSMENT")
	private float assessment;
	
	@Column(name="WEB_APP")
	private String webApp;
	
	//@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    //@JoinColumn(name="ID_APPLICATION")
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER) 
	@Fetch(value = FetchMode.SUBSELECT) 
	@JoinColumn(name="ID_APPLICATION")
    private List<ImagesApps> images = new ArrayList<ImagesApps>();
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	@JoinColumn(name="ID_APPLICATION")
	private List<ResourcesApplication> resourcesApps = new ArrayList<ResourcesApplication>();

	public Application() {};
		
	

	public Application(int id, String name, String description,
			String publisher, Date submitDate, int score, int numVotes,
			float assessment, String webApp, List<ImagesApps> images,
			List<ResourcesApplication> resourcesApps) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.publisher = publisher;
		this.submitDate = submitDate;
		this.score = score;
		this.numVotes = numVotes;
		this.assessment = assessment;
		this.webApp = webApp;
		
		this.images = images;
		this.resourcesApps = resourcesApps;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public Date getSubmitDate() {
		return submitDate;
	}

	public void setSubmitDate(Date submitDate) {
		this.submitDate = submitDate;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getNumVotes() {
		return numVotes;
	}

	public void setNumVotes(int numVotes) {
		this.numVotes = numVotes;
	}

	public float getAssessment() {
		return assessment;
	}

	public void setAssessment(float assessment) {
		this.assessment = assessment;
	}

	public String getWebApp() {
		return webApp;
	}

	public void setWebApp(String webApp) {
		this.webApp = webApp;
	}



	public List<ImagesApps> getImages() {
		return images;
	}



	public void setImages(List<ImagesApps> images) {
		this.images = images;
	}


	
	public List<ResourcesApplication> getResourcesApps() {
		return resourcesApps;
	}



	public void setResourcesApps(List<ResourcesApplication> resourcesApps) {
		this.resourcesApps = resourcesApps;
	}

	
	

}
