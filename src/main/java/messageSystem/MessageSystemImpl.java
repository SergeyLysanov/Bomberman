package messageSystem;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Logger;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

import base.AddressService;
import base.Abonent;
import base.Address;
import base.Msg;
import base.MessageSystem;

public class MessageSystemImpl implements MessageSystem {
	private static Logger logger = Logger.getLogger("FileLogger");
	private Map<Address, ConcurrentLinkedQueue<Msg>> messages = 
			new HashMap<Address, ConcurrentLinkedQueue<Msg>>();
	private AddressService addressService = new AddressServiceImpl();

	public void addService(Abonent abonent)
	{
		logger.info("MessageSystemImpl::addService abonent: " + abonent.getClass() );
		addressService.setAddress(abonent);
		messages.put(abonent.getAddress(), new ConcurrentLinkedQueue<Msg>());
	}
	
	public void sendMessage(Msg message)
	{
		Queue<Msg> messageQueue = messages.get(message.getTo());
		messageQueue.add(message);
	}
	
	public void execForAbonent(Abonent abonent)
	{
		Queue<Msg> messageQueue = messages.get(abonent.getAddress());
		while(!messageQueue.isEmpty())
		{
			Msg message = messageQueue.poll();
			abonent.getAddress().isBusy.compareAndSet(false, true);
			message.exec(abonent);
			abonent.getAddress().isBusy.compareAndSet(true, false);
		}
	}
	
	public AddressService getAddressService()
	{
		return addressService;
	}
}
