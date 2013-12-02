package com.example.shdemo.service;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.example.shdemo.domain.Egzamin;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/beans.xml" })
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
public class EgzaminManagerTest {

		@Autowired
		EgzManager egzaminManager;

		private final String PRZEDMIOT_1 = "algebra";
		private final String PRZEDMIOT_2 = "java";

		private final int ECTS_1  = 12;
		private final int ECTS_2  = 2; 
		
		@Test
		public void addCheck(){
			Egzamin egzamin = new Egzamin(PRZEDMIOT_1,ECTS_1);
			
			egzaminManager.addEgzamin(egzamin);
			
			Egzamin retrievedEgzamin = egzaminManager.getEgzamin(PRZEDMIOT_1);
			
			assertEquals(egzamin, retrievedEgzamin);
			
		}
		
		@Test
		//@SupressWarnings("deprecation")
		public void getCheck(){
			Egzamin egzamin = new Egzamin(PRZEDMIOT_1,ECTS_1);
			Egzamin egzamin2 = new Egzamin(PRZEDMIOT_2,ECTS_1);			
			
			egzaminManager.addEgzamin(egzamin);
			egzaminManager.addEgzamin(egzamin2);
			
			Egzamin retrievedEgzamin = egzaminManager.getEgzamin(PRZEDMIOT_1);
			long egzaminWithEcts = egzaminManager.countEgzaminWithEcts(ECTS_1);
			List<Egzamin> egzaminy = egzaminManager.getAllEgzamin();
			
			assertEquals(egzamin, retrievedEgzamin);
			assertEquals(2, egzaminWithEcts);
			assertEquals(2, egzaminy.size());	
			
		}
	
		@Test
		public void updateCheck(){
			Egzamin egzamin = new Egzamin(PRZEDMIOT_1,ECTS_1);
			Egzamin egzamin2 = new Egzamin(PRZEDMIOT_2,ECTS_2);
			egzaminManager.addEgzamin(egzamin);
			egzaminManager.addEgzamin(egzamin2);
			
			egzaminManager.changeEcts(egzamin.getId(), ECTS_2);
			
			List<Egzamin> egzaminy = egzaminManager.getAllEgzamin();
			long egzaminWithEcts = egzaminManager.countEgzaminWithEcts(ECTS_2);

			assertEquals(2, egzaminWithEcts);
			assertEquals(2, egzaminy.size());
		
		}
		
		@Test
		public void deleteCheck(){
			Egzamin egzamin = new Egzamin(PRZEDMIOT_1, ECTS_1);
			Egzamin egzamin2 = new Egzamin(PRZEDMIOT_2, ECTS_2);
			egzaminManager.addEgzamin(egzamin);
			egzaminManager.addEgzamin(egzamin2);
			
			int egzaminID = egzaminManager.addEgzamin(egzamin);
			egzaminManager.deleteEgzamin(egzaminID);
			List<Egzamin> egzaminy = egzaminManager.getAllEgzamin();
			
			assertEquals(1, egzaminy.size());
			assertEquals(null, egzaminManager.getEgzamin(egzaminID));
			
			
		}
		

}
