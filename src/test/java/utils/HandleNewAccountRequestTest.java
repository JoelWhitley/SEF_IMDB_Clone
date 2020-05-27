package utils;


import static org.junit.jupiter.api.Assertions.assertEquals;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import app.dao.AccountDAO;
import app.model.Account;
import app.model.enumeration.AccountRole;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class HandleNewAccountRequestTest {
	Account testProco;
	Account testCritic;
	
	@BeforeEach
	private void setUp() {
		testProco = new Account("testProco", "$2a$10$h.dl5J86rGH7I8bD9bZeZeci0pDt0.VwFTGujlnEaZXPf/q7vM5wO", 
				"Testing", "Maipatients", "123 Fake Drive", "Testoria", "Male", "test1@call.com", AccountRole.PENDING_PROCO, "2000-01-01 00:00:00.000");
		testCritic = new Account("testCritic", "$2a$10$h.dl5J86rGH7I8bD9bZeZeci0pDt0.VwFTGujlnEaZXPf/q7vM5wO", 
				"Testing", "Maipatients", "124 Fake Drive", "Testoria", "Female", "test2@call.com", AccountRole.PENDING_CRITIC, "2000-01-01 00:00:00.000");
		
		Account testAccounts[] = {testProco,testCritic};
		for(Account testUser:testAccounts) {
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
	
	}
	
	@Test
	public void updateUserType_addsProco_ProcoRequestApproved() {
		
		AccountDAO.updateUserType(testProco.getUsername(), AccountRole.PROCO);
		assertEquals(AccountDAO.getUserDetails(testProco.getUsername()).getType(), AccountRole.PROCO);
	}
	
	@Test
	public void updateUserType_addsCritic_CriticRequestApproved() {
		AccountDAO.updateUserType(testCritic.getUsername(), AccountRole.CRITIC);
		assertEquals(AccountDAO.getUserDetails(testCritic.getUsername()).getType(), AccountRole.CRITIC);
	}
	
	@Test
	public void updateUserType_revertsAccountToUser_ProcoRequestDenied() {
		AccountDAO.updateUserType(testProco.getUsername(), AccountRole.USER);
		assertEquals(AccountDAO.getUserDetails(testProco.getUsername()).getType(), AccountRole.USER);
	}
	@Test
	public void updateUserType_revertsAccountToUser_CriticRequestDenied() {
		AccountDAO.updateUserType(testCritic.getUsername(), AccountRole.USER);
		assertEquals(AccountDAO.getUserDetails(testCritic.getUsername()).getType(), AccountRole.USER);
	}
	@Test
	public void updateUserType_noCrash_nullAccountRoleIsUsed() {
		AccountRole ar = testCritic.getType();
		AccountDAO.updateUserType(testCritic.getUsername(), null);
		assertEquals(AccountDAO.getUserDetails(testCritic.getUsername()).getType(),ar);
	}
	
}