package com.example.shdemo.service;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


import com.example.shdemo.domain.Egzamin;
import com.example.shdemo.domain.Student;

@Component
@Transactional
public class EgzaminManager implements  EgzManager{
	
	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	public int addEgzamin(Egzamin egzamin){
		egzamin.setId(0);
		return (Integer) sessionFactory.getCurrentSession().save(egzamin);
	}
	
	@Override
	public void deleteEgzamin(int id){
		Egzamin egzamin = (Egzamin) sessionFactory.getCurrentSession().get(Egzamin.class, id);
		sessionFactory.getCurrentSession().delete(egzamin);
	}
	@Override
	public void deleteStudent(int id){
		Student student = (Student) sessionFactory.getCurrentSession().get(Student.class, id);
		sessionFactory.getCurrentSession().delete(student);
	}
	
	@Override
    @SuppressWarnings("unchecked")
    public List<Egzamin> getAllEgzamin() {
            return sessionFactory.getCurrentSession().getNamedQuery("egzamin.all").list();
    }
    
    @Override
    public Egzamin getEgzamin(int id) {
            return (Egzamin) sessionFactory.getCurrentSession().getNamedQuery("egzamin.byId").setInteger("id", id).uniqueResult();
    }
    
    @Override
    public Egzamin getEgzamin(String przedmiot) {
            return (Egzamin) sessionFactory.getCurrentSession().getNamedQuery("egzamin.byPrzedmiot").setString("przedmiot", przedmiot).uniqueResult();
    }


    @Override
    public long countEgzaminWithEcts(int ects) {
            return (Long) sessionFactory.getCurrentSession().getNamedQuery("egzamin.countEgzaminWithEcts").setInteger("ects", ects).list().get(0);
    }
   
    @Override
    public long countStudentWithName(String name) {
            return (Long) sessionFactory.getCurrentSession().getNamedQuery("student.countStudentWithName").setString("name", name).list().get(0);
    }

    @Override
    public void changeEcts(int id, int ects){
    	Egzamin egzamin = (Egzamin) sessionFactory.getCurrentSession().get(Egzamin.class, id);
    	egzamin.setEcts(ects);
    }

    @Override
    public void changeName(int id, String name){
    	Student student = (Student) sessionFactory.getCurrentSession().get(Student.class, id);
    	student.setName(name);
    }

    @Override
    public int addStudent(Student student){
    	student.setId(0);
  
		return (Integer) sessionFactory.getCurrentSession().save(student);
		}
    
	@Override
	public  void deleteStudent(Egzamin egzamin, int id){
		Student  student = (Student) sessionFactory.getCurrentSession().get(Student.class, id);
		egzamin = (Egzamin) sessionFactory.getCurrentSession().get(Egzamin.class, egzamin.getId());
		
		for (Student studentToRemove : egzamin.getStudents())
			if (studentToRemove.getId() == student.getId()) {
				egzamin.getStudents().remove(studentToRemove);
				sessionFactory.getCurrentSession().delete(student);
						
			}
		
		}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Student>	getAllStudents(){
		return sessionFactory.getCurrentSession().getNamedQuery("student.all").list();
	}
	
	@Override
	public Student getStudent(int indeks){
		return (Student) sessionFactory.getCurrentSession().getNamedQuery("student.byIndeks").setInteger("indeks", indeks).uniqueResult();
	}

	@Override
	public Student getStudent(String name){
		return (Student) sessionFactory.getCurrentSession().getNamedQuery("student.byName").setString("imie", name).uniqueResult();
	}
	
	@Override
	public List<Student> getEgzaminSrudents(Egzamin egzamin){
		egzamin = (Egzamin) sessionFactory.getCurrentSession().get(Egzamin.class, egzamin.getId());
		return egzamin.getStudents();
	}
	
	@Override
	public void pinStudentToEgzamin(int egzaminId, int studentId){
		Egzamin egzamin = (Egzamin) sessionFactory.getCurrentSession().get(Egzamin.class, egzaminId);
		Student student = (Student) sessionFactory.getCurrentSession().get(Student.class, studentId);
		
		egzamin.getStudents().add(student);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Student> getStudentsWithName(String name) {
		return sessionFactory.getCurrentSession().getNamedQuery("student.getStudentsWithName").setString("name", name).list();
	}
	
}



