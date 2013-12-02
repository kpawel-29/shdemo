package com.example.shdemo.service;

import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.shdemo.domain.Egzamin;

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
    public void changeEcts(int id, int ects){
    	Egzamin egzamin = (Egzamin) sessionFactory.getCurrentSession().get(Egzamin.class, id);
    	egzamin.setEcts(ects);
    }

	
}
