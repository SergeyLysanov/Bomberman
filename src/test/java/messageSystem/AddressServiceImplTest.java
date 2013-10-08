package messageSystem;

import static org.junit.Assert.*;
import org.junit.Test;

import base.Abonent;
import base.Address;
import base.AddressService;

public class AddressServiceImplTest 
{
	//Create new abonent class
	public class AbonentClass implements Abonent
	{
		final Address abonentAddress = new Address();
		
		@Override
		public Address getAddress() {
			return abonentAddress;
		}
	}
	
	@Test
	public void testAddressServiceImp() 
	{
		AddressService addressService = new AddressServiceImpl();
		AbonentClass abonent = new AbonentClass();
		addressService.setAddress(abonent);
		Address result = addressService.getAddress(AbonentClass.class);
		
		assertEquals(abonent.getAddress(), result);
	}
	
	@Test
	public void testAddressServiceBalancer()
	{
		AddressService addressService = new AddressServiceImpl();
		AbonentClass abonent = new AbonentClass();
		AbonentClass busyAbonent = new AbonentClass();
		busyAbonent.getAddress().isBusy.set(true);
		
		addressService.setAddress(busyAbonent);
		addressService.setAddress(abonent);
		
		Address result = addressService.getAddress(AbonentClass.class);
		
		assertEquals(abonent.getAddress(), result);
	}
	
	public static void main(String[] args) {
		new org.junit.runner.JUnitCore().run(AddressServiceImplTest.class);
	}

}
