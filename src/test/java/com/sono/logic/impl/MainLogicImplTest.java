package com.sono.logic.impl;

import static org.junit.Assert.*;

import org.junit.Test;

public class MainLogicImplTest {

	private String sql1 = "SELECT\n" + "    *\n" + "FROM\n" + "    users\n" + "INNER JOIN user_chats \n"
			+ "INNER JOIN user_notifications";

	@Test
	public void test() {
		var mainLogicImpl = new MainLogicImpl();
		var result = mainLogicImpl.executeEntityModeBusinessLogic(sql1);
		System.out.println(result);
		assertEquals(result.getEntityList().size(), 3);
		assertTrue(result.getEntityList().get(0).contains("Pname=users"));
		assertTrue(result.getEntityList().get(0).contains("Lname=ユーザー"));
		assertTrue(result.getEntityList().get(0).contains("TableOption="));
		assertTrue(result.getEntityList().get(0).contains("left=100"));
		assertTrue(result.getEntityList().get(0).contains("Top=500"));
		assertTrue(result.getEntityList().get(0).contains("Field=“user_name”,“ユーザー名”,“”,,,,””,””,$ffffffff,””"));
		assertTrue(result.getEntityList().get(0).contains("Pname=users"));
		assertTrue(result.getEntityList().get(0).contains("Pname=users"));
		assertTrue(result.getEntityList().get(0).contains("Pname=users"));
		assertTrue(result.getEntityList().get(0).contains("Pname=users"));

	}

}
