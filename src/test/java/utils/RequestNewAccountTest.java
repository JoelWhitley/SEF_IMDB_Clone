package utils;

import org.junit.jupiter.api.TestInstance;


import app.dao.AccountDAO;
import app.model.Account;
import app.model.enumeration.AccountRole;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RequestNewAccountTest {
	Account testUser;
	
	//'testUser','$2a$10$h.dl5J86rGH7I8bD9bZeZeci0pDt0.VwFTGujlnEaZXPf/q7vM5wO','test1@call.com','123 Test Road, Testoria','Testoria','USER','Female','Testing','Maipatients','2000-01-01 00:00:00.000'
	
	@BeforeEach
	public void setupTestUser() {
		testUser = new Account("testUser", "$2a$10$h.dl5J86rGH7I8bD9bZeZeci0pDt0.VwFTGujlnEaZXPf/q7vM5wO", 
				"Testing", "Maipatients", "123 Test Road", "Testoria", "Female", "test1@call.com", AccountRole.USER, "2000-01-01 00:00:00.000");
		if(AccountDAO.getUserByUsername(testUser.getUsername())!= null) {
			AccountDAO.deleteUserInDataBase(testUser.getUsername());
		}
		try{
			AccountDAO.insertAccountIntoDataBase(testUser);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void testUpdateUserType_true() {
		AccountDAO.updateUserType(testUser.getUsername(), AccountRole.PENDING_PROCO);
		assertEquals(AccountDAO.getUserDetails(testUser.getUsername()).getType(), AccountRole.PENDING_PROCO);
	}
	
	@Test
	public void testUpdateProcoType_true() {
		AccountDAO.updateUserType(testUser.getUsername(), AccountRole.USER);
		assertEquals(AccountDAO.getUserDetails(testUser.getUsername()).getType(), AccountRole.USER);
	}

}
