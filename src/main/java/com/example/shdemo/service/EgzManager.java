package com.example.shdemo.service;

import java.util.List;
import com.example.shdemo.domain.Egzamin;

public interface EgzManager {

	abstract int addEgzamin(Egzamin egzamin);
	abstract void deleteEgzamin(int id);
	abstract List<Egzamin> getAllEgzamin();
	abstract Egzamin getEgzamin(int id);
	abstract Egzamin getEgzamin(String przedmiot);
	abstract long countEgzaminWithEcts(int ects);
	abstract void changeEcts(int id, int ects);
	
}
