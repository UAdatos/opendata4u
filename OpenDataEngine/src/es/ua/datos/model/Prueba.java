package es.ua.datos.model;



//import java.io.Serializable;

//import org.hibernate.annotations.*;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
 
@Entity 
@Table(name="PRUEBA")
public class Prueba{
	 
	 	@Id
		@GeneratedValue
		@Column(name = "id", nullable = false)
	 	private int id;
	 	
	 	@Column(name = "desc", nullable = false)
	 	private String desc;
	 	
	 	public Prueba(){
	 		
	 	}
	 	
	 	public Prueba(int id, String desc) {
	 		super();
	 		this.id = id;
	 		this.desc = desc;
	 		}
	 	
	 	public Prueba(String desc){
	 		
	 		this.desc = desc;
	 	}

		
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		
		public String getDesc() {
			return desc;
		}
		public void setDesc(String desc) {
			this.desc = desc;
		}
		
}
