package main;

import accountService.*;
import base.*;
import frontend.*;
import gameMechanics.*;
import databaseService.*;
import messageSystem.*;
import utils.*;

import org.junit.Ignore;
import org.junit.runner.JUnitCore;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@Ignore
@RunWith(Suite.class)
@Suite.SuiteClasses({
	AddressTest.class,
	
	DatabaseServiceImplTest.class,
	ConnectionPoolTest.class,
	UserDataSetImplTest.class,
	
	//AccountService package
	MsgUpdateUserDataTest.class,
	MD5HashingTest.class,
	AccountServiceImplTest.class,
	SHA256HashingTest.class,
	
	//GameMechanics package
	GameSocketImplTest.class,
	GameMechanicsImplTest.class,
	GameSessionTest.class,
	UserCommandTest.class,
	MsgAddExistingUserTest.class,
	GameMapTest.class,
	
	//Frontend package
	FrontendImplTest.class,
	PageGeneratorTest.class,
	
	//messageSystem package
	AddressServiceImplTest.class,
	MessageSystemImplTest.class,
	
	//utils package
	PairTest.class,
	
	//main package
	MainTest.class
})

public class TestAll {

	public static void main(String[] args) {
		JUnitCore.runClasses(new Class[] { TestAll.class });
	}
}
