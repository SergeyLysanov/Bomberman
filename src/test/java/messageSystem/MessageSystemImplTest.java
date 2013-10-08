package messageSystem;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
import org.junit.Test;

import base.Abonent;
import base.Address;
import base.AddressService;
import base.Frontend;
import base.MessageSystem;
import base.Msg;

public class MessageSystemImplTest 
{
	//Create new abonent class
	public class AbonentClass implements Abonent
	{
		final private Address abonentAddress = new Address();
		private MessageSystem messageSystem;
		
		public AbonentClass(MessageSystem messageSystem)
		{
			this.messageSystem = messageSystem;
			messageSystem.addService(this);
		}
		
		public void readMessage()
		{
			messageSystem.execForAbonent(this);
		}
		
		public void doWork(){
			//
		}
		
		@Override
		public Address getAddress() {
			return abonentAddress;
		}
	}
	
	public class MessageToAbonent extends Msg
	{
		public MessageToAbonent(Address from, Address to)
		{
			super(from, to);
		}

		public void exec(Abonent abonent)
		{
			if(abonent instanceof AbonentClass)
			{
				exec((AbonentClass)abonent);
			}
		}
		
		public void exec(AbonentClass abonentClass)
		{
			abonentClass.doWork();
		}
	}
	
	@Test 
	public void testMessageSystemImpl()
	{
		MessageSystemImpl messageSystem = new MessageSystemImpl();
		AbonentClass spyAbonent = spy(new AbonentClass(messageSystem));
		
		//Create new message tp our abonent
		MessageToAbonent msg = new MessageToAbonent(new Address(), spyAbonent.getAddress());
		messageSystem.sendMessage(msg);
		
		spyAbonent.readMessage();
		
		verify(spyAbonent).doWork();
	}
	
	public static void main(String[] args) {
		new org.junit.runner.JUnitCore().run(MessageSystemImplTest.class);
	}
}
