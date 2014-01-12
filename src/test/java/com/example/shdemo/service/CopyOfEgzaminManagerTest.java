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
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
public class CopyOfEgzaminManagerTest {

		@Autowired
		EgzManager egzaminManager;

		private final String PRZEDMIOT_1 = "algebra";
		//private final String PRZEDMIOT_2 = "java";
		private final String PRZEDMIOT_3 = "analiza";
		private final String PRZEDMIOT_4 = "programowanie";

		private final int ECTS_1  = 12;
		//private final int ECTS_2  = 2; 
		private final int ECTS_3  = 11;
		private final int ECTS_4  = 10;
		

		private final String NAME_1 = "Marek";
		private final String NAME_2 = "Piotr";
		private final String NAME_3 = "Jan";
		private final String NAME_4 = "Monika";

		private final int INDEKS_1 = 206319;
		private final int INDEKS_2 = 206318;
		private final int INDEKS_3 = 206317;
		private final int INDEKS_4 = 206316;
		
		//@Before
		//@After
		public void clear(){
			List<Student> students = egzaminManager.getAllStudents();
			List<Egzamin> egzamins = egzaminManager.getAllEgzamin();
			
			
			
			for(Student student : students)
				egzaminManager.deleteStudent(student.getId());
			
			for(Egzamin egzamin :egzamins)
				egzaminManager.deleteEgzamin(egzamin.getId());
		}
		
		//@Before
		public void fillDatabase(){
			Egzamin egzamin = new Egzamin(PRZEDMIOT_3,ECTS_3);
			Egzamin egzamin2 = new Egzamin(PRZEDMIOT_4,ECTS_4);
			
			Student student = new Student(NAME_3,INDEKS_3);
			Student student2 = new Student(NAME_4,INDEKS_4);

			egzaminManager.addEgzamin(egzamin);
			egzaminManager.addEgzamin(egzamin2);
			int studentId = egzaminManager.addStudent(student);	
			int studentId2 = egzaminManager.addStudent(student2);		
			
			egzaminManager.pinStudentToEgzamin(egzamin.getId(), studentId);
			egzaminManager.pinStudentToEgzamin(egzamin2.getId(), studentId2);
			

		
		}
		
		@Test
		public void addCheck(){
			fillDatabase();
			
			Egzamin egzamin = new Egzamin(PRZEDMIOT_1,ECTS_1);
			Student student = new Student(NAME_1,INDEKS_1);
			
			egzaminManager.addEgzamin(egzamin);
			int studentId = egzaminManager.addStudent(student);		
			
			int size = egzaminManager.getEgzaminSrudents(egzamin).size();
			
			egzaminManager.pinStudentToEgzamin(egzamin.getId(), studentId);
			
			Student retrievedStudent = egzaminManager.getStudent(INDEKS_1);
			Egzamin retrievedEgzamin = egzaminManager.getEgzamin(PRZEDMIOT_1);
			List<Student> egzaminStudents = egzaminManager.getEgzaminSrudents(retrievedEgzamin);
					
			assertEquals(egzamin, retrievedEgzamin);
			assertEquals(student,  retrievedStudent);
			assertEquals(size + 1, egzaminStudents.size());
			assertEquals(student, egzaminStudents.get(0));	
			
			
			
		}
		
		@Test
		@SuppressWarnings("deprecation")
		public void getCheck(){
			fillDatabase();
			int sizeEgzamin = egzaminManager.getAllEgzamin().size();
			int sizeStudent = egzaminManager.getAllStudents().size();
			long sizeStudentWithName = egzaminManager.countStudentWithName(NAME_1);
					
			Egzamin egzamin = new Egzamin(PRZEDMIOT_1, ECTS_1);
			Student students[] = {new Student(NAME_1, INDEKS_1), new Student(NAME_1, INDEKS_2)};
			
			egzaminManager.addEgzamin(egzamin);
			int studentId[] = {egzaminManager.addStudent(students[0]), egzaminManager.addStudent(students[1])};
			
			int size = egzaminManager.getEgzaminSrudents(egzamin).size();
			
			
			egzaminManager.pinStudentToEgzamin(egzamin.getId(), studentId[0]);
			egzaminManager.pinStudentToEgzamin(egzamin.getId(), studentId[1]);
						
			Egzamin retrievedEgzamin = egzaminManager.getEgzamin(egzamin.getId());
			
			List<Student> egzaminStudents = egzaminManager.getEgzaminSrudents(retrievedEgzamin);
			List<Egzamin> egzamins = egzaminManager.getAllEgzamin();
			List<Student> studentsList = egzaminManager.getAllStudents();
			long studentsWithName = egzaminManager.countStudentWithName(NAME_1);
		
			assertEquals(egzamin, retrievedEgzamin);
			//assertEquals(students, retrievedStudents);
			assertEquals(size + 2, egzaminStudents.size());
			assertEquals(students, egzaminStudents.toArray());
			assertEquals(sizeStudent + 2, studentsList.size());
			assertEquals(sizeEgzamin + 1, egzamins.size());
			assertEquals(egzamin, egzamins.get(sizeEgzamin));//porownuje z ostatnio dodanym rekordem
			assertEquals(sizeStudentWithName + 2, studentsWithName);
			
		}
			
			
		@Test
		public void updateCheck(){
			fillDatabase();
			long sizeStudentWithName = egzaminManager.countStudentWithName(NAME_1);
			
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
			
			assertEquals(sizeStudentWithName + 2, studentsWithName.size());
			assertEquals(students[0], egzaminStudents.get(0));
			
		}
		
		@Test
		public void deleteCheck(){
			fillDatabase();
			int sizeEgzamin = egzaminManager.getAllEgzamin().size();
			int sizeStudent = egzaminManager.getAllStudents().size();
			
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
			
			assertEquals(sizeEgzamin + 1, retrievedStudents.size());
			assertEquals(students[1], retrievedStudents.get(sizeStudent));//porównuje z odtatnio dodanym rekordem
			assertEquals(1, egzaminStudents.size());
			assertEquals(null, egzaminManager.getStudent(students[0].getId()));
			
			sizeEgzamin = egzaminManager.getAllEgzamin().size();
			sizeStudent = egzaminManager.getAllStudents().size();
			
			
			retrievedEgzamins = egzaminManager.getAllEgzamin();
			retrievedStudents = egzaminManager.getAllStudents();
			
			assertEquals(sizeEgzamin, retrievedEgzamins.size());
			assertEquals(sizeStudent, retrievedStudents.size());
			
		}
		
}
