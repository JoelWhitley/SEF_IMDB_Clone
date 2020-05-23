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

	@BeforeAll
	public void setup() {
		testUser = new Account("testUser", "$2a$10$h.dl5J86rGH7I8bD9bZeZeci0pDt0.VwFTGujlnEaZXPf/q7vM5wO", 
				"Testy", "Testington", "123 Test Road", "Testoria", "Female", "test1@call.com", AccountRole.USER);
		
	}
	
	@Test
	public void testConstructor_assortedValues() {
		
	}
	
	@Test
	public void testUpdateUserType_true() {
		AccountDAO.updateUserType(testUser.getUsername(), AccountRole.PENDING_PROCO);
		assertEquals(AccountDAO.getUserDetails(testUser.getUsername()).getType(), AccountRole.PENDING_PROCO);
	}
	
	@Test
	public void testUpdateProcoType_true() {
		testUser = new Account("testUser", "$2a$10$h.dl5J86rGH7I8bD9bZeZeci0pDt0.VwFTGujlnEaZXPf/q7vM5wO", 
				"Testy", "Testington", "123 Test Road", "Testoria", "Female", "test1@call.com", AccountRole.PENDING_PROCO);
		AccountDAO.updateUserType(testUser.getUsername(), AccountRole.USER);
		assertEquals(AccountDAO.getUserDetails(testUser.getUsername()).getType(), AccountRole.USER);
	}

}
