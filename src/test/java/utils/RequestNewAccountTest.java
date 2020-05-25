package utils;

import org.junit.jupiter.api.TestInstance;


import app.dao.AccountDAO;
import app.model.Account;
import app.model.enumeration.AccountRole;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
	
	//Proco Request tests:
	
	@Test
	public void testUpdateProcoRequestType_true() {
		AccountDAO.updateUserType(testUser.getUsername(), AccountRole.PENDING_PROCO);
		assertEquals(AccountDAO.getUserDetails(testUser.getUsername()).getType(), AccountRole.PENDING_PROCO);
	}
	
	@Test
	public void testRevertProcoRequest_true() {
		AccountDAO.updateUserType(testUser.getUsername(), AccountRole.PENDING_PROCO);
		AccountDAO.updateUserType(testUser.getUsername(), AccountRole.USER);
		assertEquals(AccountDAO.getUserDetails(testUser.getUsername()).getType(), AccountRole.USER);
	}
	
	@Test
	public void testProcoPreventBannedRequests_false() {
		AccountDAO.banUserForTime(testUser.getUsername(), 1, "days");
		AccountDAO.updateUserType(testUser.getUsername(), AccountRole.PENDING_PROCO);
		assertNotEquals(AccountDAO.getUserDetails(testUser.getUsername()).getType(), AccountRole.PENDING_PROCO);
	}
	
	@Test
	public void testProcoBanTimeoutRequests_true() {
		AccountDAO.banUserForTime(testUser.getUsername(), 2, "seconds");
		
		try {
			Thread.sleep(3000);
			}
			catch(InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		
		AccountDAO.updateUserType(testUser.getUsername(), AccountRole.PENDING_PROCO);
		assertEquals(AccountDAO.getUserDetails(testUser.getUsername()).getType(), AccountRole.PENDING_PROCO);
	}
	
	@Test
	public void testUpdateProcoNotInDB_null() {
		Account testUser2 = new Account("testUser2", "$2a$10$h.dl5J86rGH7I8bD9bZeZeci0pDt0.VwFTGujlnEaZXPf/q7vM5wO", 
			"Testing", "Maipatients", "123 Test Road", "Testoria", "Female", "test1@call.com", AccountRole.USER, "2000-01-01 00:00:00.000");
		AccountDAO.updateUserType(testUser2.getUsername(), AccountRole.PENDING_PROCO);
		assertNull(AccountDAO.getUserDetails(testUser2.getUsername()));
	}
	
	//Critic Request tests:
	
	@Test
	public void testUpdateCriticRequestType_true() {
		AccountDAO.updateUserType(testUser.getUsername(), AccountRole.PENDING_CRITIC);
		assertEquals(AccountDAO.getUserDetails(testUser.getUsername()).getType(), AccountRole.PENDING_CRITIC);
	}
	
	@Test
	public void testRevertCriticRequestType_true() {
		AccountDAO.updateUserType(testUser.getUsername(), AccountRole.PENDING_CRITIC);
		AccountDAO.updateUserType(testUser.getUsername(), AccountRole.USER);
		assertEquals(AccountDAO.getUserDetails(testUser.getUsername()).getType(), AccountRole.USER);
	}
	
	@Test
	public void testCriticPreventBannedRequests_false() {
		AccountDAO.banUserForTime(testUser.getUsername(), 1, "days");
		AccountDAO.updateUserType(testUser.getUsername(), AccountRole.PENDING_CRITIC);
		assertNotEquals(AccountDAO.getUserDetails(testUser.getUsername()).getType(), AccountRole.PENDING_CRITIC);
	}
	
	@Test
	public void testCriticBanTimeoutRequests_true() {
		AccountDAO.banUserForTime(testUser.getUsername(), 2, "seconds");
		
		try {
			Thread.sleep(3000);
			}
			catch(InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		
		AccountDAO.updateUserType(testUser.getUsername(), AccountRole.PENDING_CRITIC);
		assertEquals(AccountDAO.getUserDetails(testUser.getUsername()).getType(), AccountRole.PENDING_CRITIC);
	}
	
	@Test
	public void testUpdateCriticNotInDB_null() {
		Account testUser2 = new Account("testUser2", "$2a$10$h.dl5J86rGH7I8bD9bZeZeci0pDt0.VwFTGujlnEaZXPf/q7vM5wO", 
			"Testing", "Maipatients", "123 Test Road", "Testoria", "Female", "test1@call.com", AccountRole.USER, "2000-01-01 00:00:00.000");
		AccountDAO.updateUserType(testUser2.getUsername(), AccountRole.PENDING_CRITIC);
		assertNull(AccountDAO.getUserDetails(testUser2.getUsername()));
	}
	
	//Test null values
	@Test
	public void giveNullUserName_noCrash() {
		String nullUsername = null; 
		AccountDAO.updateUserType(nullUsername, AccountRole.PENDING_PROCO);
	}
}
