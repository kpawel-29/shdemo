package com.example.shdemo.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;


@Entity
@NamedQueries({
	@NamedQuery(name = "egzamin.all", query = "select e from Egzamin e"),
	@NamedQuery(name = "egzamin.byId", query ="select e from Egzamin e where e.id = :id"),
	@NamedQuery(name = "egzamin.byPrzedmiot", query = "select e from Egzamin e where e.przedmiot = :przedmiot"),
	@NamedQuery(name = "egzamin.countEgzaminWithEcts", query = "select count(e) from Egzamin e where e.ects = :ects")
})
public class Egzamin {

	private int id;	
	private String przedmiot;
	private int ects;

	public Egzamin(int id, String przedmiot, int ects){
		super();
		this.id = id;
		this.przedmiot = przedmiot;
		this.ects = ects;
	}
	
	public Egzamin(int id) {
		super();
		this.id = id;
	}
	
	public Egzamin() {
	}
	
	public Egzamin(String przedmiot, int ects) {
		//super();
		this.przedmiot = przedmiot;
		this.ects = ects;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int getId() {
		return id;
	}
	
	@Column
	public String getPrzedmiot() {
		return przedmiot;
	}
	
	//@Column
	public int getEcts() {
		return ects;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setPrzedmiot(String przedmiot) {
		this.przedmiot = przedmiot;
	}
	public void setEcts(int ects) {
		this.ects = ects;
	}

	
	
}
