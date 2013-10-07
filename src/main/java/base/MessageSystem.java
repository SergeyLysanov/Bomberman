package base;

 // $codepro.audit.disable unnecessaryImport

public interface MessageSystem 
{
	public void addService(Abonent abonent);
	public void sendMessage(Msg message);
	public void execForAbonent(Abonent abonent);
	public AddressService getAddressService();
}
