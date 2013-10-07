package accountService;

import org.junit.*;
import static org.junit.Assert.*;


public class SHA256HashingTest {

	@Test
	public void testGetHash()
	{
		String password = "&2FnjkFWFRfs";
		String result = SHA256Hashing.getHash(password);
		assertEquals("fdc0a094e1510bdb31a65a03668d6dde8bdafb724deb3f9b3ebd432615f3499d", result);
	}

	public static void main(String[] args) {
		new org.junit.runner.JUnitCore().run(SHA256HashingTest.class);
	}
}