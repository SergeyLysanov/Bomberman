package main;

import accountService.*;
import base.*;
import frontend.*;
import gameMechanics.*;
import databaseService.*;
import messageSystem.*;

import org.junit.runner.JUnitCore;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)
@Suite.SuiteClasses({
	AddressTest.class,
	
	DatabaseServiceImplTest.class,
	ConnectionPoolTest.class,
	UserDataSetImplTest.class,
	
	MsgUpdateUserDataTest.class,
	MD5HashingTest.class,
	AccountServiceImplTest.class,
	SHA256HashingTest.class,
	
	GameSocketImplTest.class,
	GameMechanicsImplTest.class,
	UserCommandTest.class,
	
	FrontendImplTest.class,
	PageGeneratorTest.class,
	
	AddressServiceImplTest.class,
	MessageSystemImplTest.class
})
public class TestAll {

	public static void main(String[] args) {
		JUnitCore.runClasses(new Class[] { TestAll.class });
	}
}
