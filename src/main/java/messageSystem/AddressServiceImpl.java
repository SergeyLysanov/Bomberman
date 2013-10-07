package messageSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.logging.Logger;

import base.Abonent;
import base.Address;
import base.AddressService;


public class AddressServiceImpl  implements AddressService
{
	private static Logger logger = Logger.getLogger("FileLogger");
	private Map<Class<?>, List<Address>> addresses = new HashMap<Class<?>, List<Address>>();
	
	public Address getAddress(Class<?> abonentClass){
		List<Address> addressesList = addresses.get(abonentClass);
		Address curAddress = null;
		boolean flag = false;
		logger.info("AddressServiceImpl::getAddress  addressList size: " +  addressesList.size());
		//System.out.println(addressesList.size());
		while (flag == false){
			for (int i = 0; i < addressesList.size(); i++){
			   //System.out.println("Im in cycle");
			   Address addressItem = addressesList.get(i);
			   if (addressItem.isBusy.get() == false){
				   flag = true;
				   curAddress = addressItem;
				   break;
			   }
			}
		}
		return curAddress;
	}
	
	public void setAddress(Abonent abonent)
	{
		if (addresses.get(abonent.getClass()) == null){
			List<Address> addressesList = new ArrayList<Address>();
			addressesList.add(abonent.getAddress());
			addresses.put(abonent.getClass(), addressesList);
		}else{
			List<Address> addressesList = addresses.get(abonent.getClass());
			addressesList.add(abonent.getAddress());
			
		}
	}
}
