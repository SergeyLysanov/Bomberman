package frontend;

import base.Abonent;
import base.Address;
import base.Frontend;
//import Server.FrontendImpl;;
import base.Msg;

public abstract class MessageToFrontend extends Msg
{
	public MessageToFrontend(Address from, Address to)
	{
		super(from, to);
	}

	public void exec(Abonent abonent)
	{
		if(abonent instanceof Frontend)
		{
			exec((Frontend)abonent);
		}
	}
	
	public abstract void exec(Frontend frontend);
}
