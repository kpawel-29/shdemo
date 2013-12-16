package com.example.shdemo.service;

import java.util.List;

import com.example.shdemo.domain.Egzamin;
import com.example.shdemo.domain.Student;

public interface EgzManager {

	abstract int addEgzamin(Egzamin egzamin);
	abstract void deleteEgzamin(int id);
	abstract List<Egzamin> getAllEgzamin();
	abstract Egzamin getEgzamin(int id);
	abstract Egzamin getEgzamin(String przedmiot);
	abstract long countEgzaminWithEcts(int ects);
	abstract void changeEcts(int id, int ects);
	
	
	abstract int addStudent(Student student);
	abstract List<Student> getAllStudents();
	abstract Student getStudent(int id);
	abstract Student getStudent(String name);

	abstract List<Student> getEgzaminSrudents(Egzamin egzamin);
	abstract void pinStudentToEgzamin(int egzaminId, int studentId);
	abstract long countStudentWithName(String name);
	abstract void changeName(int id, String name);
	abstract List<Student> getStudentsWithName(String name);
	abstract void deleteStudent(Egzamin egzamin, int id);
	abstract void deleteStudent(int id);
}
