package com.example.shdemo.service;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.example.shdemo.domain.Egzamin;
import com.example.shdemo.domain.Student;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/beans.xml" })
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = false)
@Transactional
public class EgzaminManagerTest {

		@Autowired
		EgzManager egzaminManager;

		private final String PRZEDMIOT_1 = "algebra";
		//private final String PRZEDMIOT_2 = "java";

		private final int ECTS_1  = 12;
		//private final int ECTS_2  = 2; 

		private final String NAME_1 = "Marek";
		private final String NAME_2 = "Piotr";

		private final int INDEKS_1 = 206319;
		private final int INDEKS_2 = 206318;
		
		@After
		public void clear(){
			List<Student> students = egzaminManager.getAllStudents();
			List<Egzamin>	egzamins = egzaminManager.getAllEgzamin();
			
			for(Student student : students)
				egzaminManager.deleteStudent(student.getId());
			
			for(Egzamin egzamin :egzamins)
				egzaminManager.deleteEgzamin(egzamin.getId());
		}
		
		@Test
		public void addCheck(){
			Egzamin egzamin = new Egzamin(PRZEDMIOT_1,ECTS_1);
			Student student = new Student(NAME_1,INDEKS_1);
			
			egzaminManager.addEgzamin(egzamin);
			int studentId = egzaminManager.addStudent(student);
			egzaminManager.pinStudentToEgzamin(egzamin.getId(), studentId);
			
			Student retrievedStudent = egzaminManager.getStudent(INDEKS_1);
			Egzamin retrievedEgzamin = egzaminManager.getEgzamin(PRZEDMIOT_1);
			List<Student> egzaminStudents = egzaminManager.getEgzaminSrudents(retrievedEgzamin);
			
			assertEquals(egzamin, retrievedEgzamin);
			assertEquals(student,  retrievedStudent);
			assertEquals(1, egzaminStudents.size());
			assertEquals(student, egzaminStudents.get(0));			
		}
		
		@Test
		@SuppressWarnings("deprecation")
		public void getCheck(){
			Egzamin egzamin = new Egzamin(PRZEDMIOT_1, ECTS_1);
			Student students[] = {new Student(NAME_1, INDEKS_1), new Student(NAME_1, INDEKS_2)};
			
			egzaminManager.addEgzamin(egzamin);
			int studentId[] = {egzaminManager.addStudent(students[0]), egzaminManager.addStudent(students[1])};

			egzaminManager.pinStudentToEgzamin(egzamin.getId(), studentId[0]);
			egzaminManager.pinStudentToEgzamin(egzamin.getId(), studentId[1]);
						
			Egzamin retrievedEgzamin = egzaminManager.getEgzamin(egzamin.getId());
			
			List<Student> egzaminStudents = egzaminManager.getEgzaminSrudents(retrievedEgzamin);
			List<Egzamin> egzamins = egzaminManager.getAllEgzamin();
			long studentsWithName = egzaminManager.countStudentWithName(NAME_1);
		
			assertEquals(egzamin, retrievedEgzamin);
			//assertEquals(students, retrievedStudents);
			assertEquals(2, egzaminStudents.size());
			assertEquals(students, egzaminStudents.toArray());
			assertEquals(1, egzamins.size());
			assertEquals(egzamin, egzamins.get(0));
			assertEquals(2, studentsWithName);
			
		}
			
			
		@Test
		public void updateCheck(){
			Egzamin egzamin = new Egzamin(PRZEDMIOT_1,ECTS_1);
			Student students[] = {new Student(NAME_1, INDEKS_1), new Student(NAME_2, INDEKS_2)};
			
			egzaminManager.addEgzamin(egzamin);
			int studentId[] = {egzaminManager.addStudent(students[0]),
					egzaminManager.addStudent(students[1])};
			
			egzaminManager.pinStudentToEgzamin(egzamin.getId(), studentId[0]);
			egzaminManager.pinStudentToEgzamin(egzamin.getId(), studentId[1]);
			
			egzaminManager.changeName(studentId[0], NAME_2);
			
			Egzamin retrievedEgzamin = egzaminManager.getEgzamin(egzamin.getId());
			
			List<Student> studentsWithName = egzaminManager.getStudentsWithName(NAME_2);
			List<Student> egzaminStudents = egzaminManager.getEgzaminSrudents(retrievedEgzamin);
			
			
			assertEquals(2, studentsWithName.size());
			assertEquals(students[0], egzaminStudents.get(0));
			
		}
		
		@Test
		public void deleteCheck(){
			
			Egzamin egzamin = new Egzamin(PRZEDMIOT_1,ECTS_1);
			Student students[] = {new Student(NAME_1, INDEKS_1), new Student(NAME_2, INDEKS_2)};
			
			egzaminManager.addEgzamin(egzamin);
			int studentId[] = {egzaminManager.addStudent(students[0]),
					egzaminManager.addStudent(students[1])};
			
			egzaminManager.pinStudentToEgzamin(egzamin.getId(), studentId[0]);
			egzaminManager.pinStudentToEgzamin(egzamin.getId(), studentId[1]);
			
			egzaminManager.deleteStudent(egzamin, students[0].getId());
			
			List<Egzamin> retrievedEgzamins = egzaminManager.getAllEgzamin();
			List<Student> retrievedStudents = egzaminManager.getAllStudents();
			List<Student> egzaminStudents = egzaminManager.getEgzaminSrudents(retrievedEgzamins.get(0));
			
			assertEquals(1, retrievedStudents.size());
			assertEquals(students[1], retrievedStudents.get(0));
			assertEquals(1, egzaminStudents.size());
			assertEquals(null, egzaminManager.getStudent(students[0].getId()));
			
			egzaminManager.deleteEgzamin(egzamin.getId());
			
			retrievedEgzamins = egzaminManager.getAllEgzamin();
			retrievedStudents = egzaminManager.getAllStudents();
			
			assertEquals(0, retrievedEgzamins.size());
			assertEquals(0, retrievedStudents.size());
			
		}
		
}
