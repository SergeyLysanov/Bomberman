package accountService;

import org.junit.*;
import static org.junit.Assert.*;


public class MD5HashingTest {

	@Test
	public void testGetHash()
		throws Exception {
		MD5Hashing md5Hashing = new MD5Hashing();
		String password = "729138Az!bH";

		String result = md5Hashing.getHash(password);

		assertEquals("11a8822a3761ca537c69b1363b5d20b9", result);
	}



	public static void main(String[] args) {
		new org.junit.runner.JUnitCore().run(MD5HashingTest.class);
	}
}