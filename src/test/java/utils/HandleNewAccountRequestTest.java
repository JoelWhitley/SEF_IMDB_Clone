package utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import app.dao.AccountDAO;
import app.model.Account;
import app.model.enumeration.AccountRole;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class HandleNewAccountRequestTest {
	int procoCount = 0;
	int criticCount = 0;
	
	@BeforeEach
	private void setUp() {
		List<Account> procos =  new ArrayList<>();
		List<Account> critics =  new ArrayList<>();
		
		procos = AccountDAO.getAccountsByType(AccountRole.PROCO);
		critics = AccountDAO.getAccountsByType(AccountRole.CRITIC);
		
		procoCount = procos.size();
		criticCount = critics.size();
		System.out.print("it gets here");
	}
	
	@Test
	public void updateUserType_addsProco_ProcoRequestApproved() {
		AccountDAO.updateUserType("jwit", AccountRole.PROCO);
		List<Account> accounts =  new ArrayList<>();
		accounts = AccountDAO.getAccountsByType(AccountRole.PROCO);
		assertEquals(procoCount+1 ,accounts.size());
	}
}