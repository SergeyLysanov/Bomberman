package frontend;

import utils.UserSession;
import base.Address;
import base.Frontend;

public class MsgUpdateUserSession extends MessageToFrontend
{
		final private UserSession userSession;
		
		public MsgUpdateUserSession(Address from, Address to, UserSession userSession)
		{
			super(from, to);
			this.userSession = userSession;
		}
		
		@Override
		public void exec(Frontend frontend) {
			frontend.updateUserSession(userSession);
		}
}
