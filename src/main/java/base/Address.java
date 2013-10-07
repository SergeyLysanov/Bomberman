package base;

import java.util.concurrent.atomic.*;

public class Address {
	static private AtomicInteger abonentIdCreator = new AtomicInteger();
	final private int abonentId;
	
	public Address(){
		this.abonentId = abonentIdCreator.incrementAndGet();
	}
	
	public int hashCode()
	{
		return abonentId;
	}
	
	public AtomicBoolean isBusy = new AtomicBoolean(false);

}
