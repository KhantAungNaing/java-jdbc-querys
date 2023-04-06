package com.jdc.jdbctest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.jdc.kan.connectionassit.ConnectionManager;
import com.jdc.kan.connectionassit.DatabaseInitializer;
import com.jdc.kan.dao.MessageDao;
import com.jdc.kan.dto.Message;

@TestMethodOrder(OrderAnnotation.class)
class MessageDaoTest {

	MessageDao dao;
	Message message =  new Message("Khant Aung Naing", "OneStop-weekend-1", "kha / 21,myohthit (1)st", "09797880945",
			"khantaungnaing2001@gmail.com");;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		DatabaseInitializer.truncateTable("message");
	}

	@BeforeEach
	void setUp() throws Exception {
		dao = new MessageDao(ConnectionManager.getInstance());
	}

	@Test
	@Order(1)
	void insert() {
		var res = dao.insertIntoTable(message);
		assertEquals(message.name(), res.name());
		assertEquals(message.course(), res.course());
		assertEquals(message.address(), res.address());
		assertEquals(message.phNumber(), res.phNumber());
		assertEquals(message.email(), res.email());
		
	}
	
	@Test
	@Order(2)
	void findById() {
		var res = dao.findById(1);
		assertNotNull(res);
		assertEquals(message.name(),res.name());
		assertEquals(message.course(),res.course());
		assertEquals(message.address(),res.address());
		assertEquals(message.phNumber(),res.phNumber());
		assertEquals(message.email(),res.email());
	}
	
	@Test
	@Order(3)
	void updateById() {
		message = new Message("LuLu","JavaBasic","thamine","09777267486","luluxoxo7@gmail.com");
		var res = dao.updateById(1,message);
		assertEquals(1, res);
		var result = dao.findById(1);
		assertEquals(message.name(),result.name());
		assertEquals(message.course(),result.course());
		assertEquals(message.address(),result.address());
		assertEquals(message.phNumber(),result.phNumber());
		assertEquals(message.email(),result.email());
	}
	
	
	@Test
	@Order(4)
	void insertMany() {
		
		List<Message> messages = List.of(new Message("PhoeKyar","JavaBasic","thamine","09796288117","phoephoeKyar@gmail.com"),
				new Message("Phattee","JavaBasic","thamine","09250180999","fighterphattee@gmail.com"),
				new Message("Meemeekhel","Spring Framework","Mayangone","0977889977","meemeechit@gmail.com"));
		
		var list = dao.insertMany(messages);
		assertEquals(2, list.get(0).id());
		assertEquals(3, list.get(1).id());
		assertEquals(4, list.get(2).id());
		
	}
	
	@Test
	@Order(5)
	void insertManyNull() {
		
		var list = dao.insertMany(null);
		assertTrue(list.isEmpty());
	}
	
	
	@Test
	@Order(6)
	void deleteById() {
		
		var res = dao.deleteById(2);
		assertEquals(1,res);
	}	
	
	

}
