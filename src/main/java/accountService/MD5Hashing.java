package accountService;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Hashing 
{
	public String getHash(String password)
	{
        MessageDigest md;
        StringBuffer hexString = new StringBuffer();
        
		try 
		{
			md = MessageDigest.getInstance("MD5");
	        md.update(password.getBytes());
	        
	        byte byteData[] = md.digest();
	 
	        //convert the byte to hex format method 1
	        StringBuffer sb = new StringBuffer();
	        for (int i = 0; i < byteData.length; i++) {
	         sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
	        }
	 
	        System.out.println("Digest(in hex format):: " + sb.toString());
	 
	        //convert the byte to hex format method 2
	    	for (int i=0;i<byteData.length;i++) {
	    		String hex=Integer.toHexString(0xff & byteData[i]);
	   	     	if(hex.length()==1) hexString.append('0');
	   	     	hexString.append(hex);
	    	}
	    	System.out.println("Digest(in hex format):: " + hexString.toString());	
		}
		catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		return hexString.toString();
	}

}
