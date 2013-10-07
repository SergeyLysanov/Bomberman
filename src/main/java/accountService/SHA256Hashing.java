package accountService;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA256Hashing 
{
	public static String getHash(String password)
	{
		StringBuffer sb = new StringBuffer();
		try {
			MessageDigest md;
			md = MessageDigest.getInstance("SHA-256");
	        md.update(password.getBytes());
	        
	        byte byteData[] = md.digest();
	 
	        //convert the byte to hex format method 1  
	        for (int i = 0; i < byteData.length; i++) {
	         sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
	        }
	 
	        //System.out.println("Hex format : " + sb.toString());
	      
		} catch (NoSuchAlgorithmException e) 
		{
			e.printStackTrace();
		}
		
		return sb.toString();
	}
}
