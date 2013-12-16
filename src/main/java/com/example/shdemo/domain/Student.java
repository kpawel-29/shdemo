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
	@NamedQuery(name = "student.all", query = "select s from Student s"),
	@NamedQuery(name = "student.byIndeks", query = "select s from Student s where s.indeks =:indeks"),
	@NamedQuery(name = "student.byName", query = "select s from Student s where s.name =:name"),
	@NamedQuery(name = "student.countStudentWithName", query = "select count(s) from Student s where s.name = :name"),
	@NamedQuery(name = "student.getStudentsWithName", query = "select s from Student s where s.name =:name") 

	
	
})
public class Student {
	
	private int id;
	private String name;
	private int indeks;
	
	
	public Student(String name, int indeks) {
		super();
		this.name = name;
		this.indeks = indeks;
	}
	
	public Student() {
		
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Student(int id, String name, int indeks) {
		super();
		this.id = id;
		this.name = name;
		this.indeks = indeks;
	}
	
	@Column(nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Column(unique = true, nullable = false)
	public int getIndeks() {
		return indeks;
	}

	public void setIndeks(int indeks) {
		this.indeks = indeks;
	}
	
	

}
