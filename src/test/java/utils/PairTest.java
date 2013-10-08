package utils;

import static org.junit.Assert.*;
import org.junit.Test;
import messageSystem.AddressServiceImplTest;


public class PairTest 
{
	@Test
	public void testPair()
	{
		Pair pair = new Pair<Integer, String>(1, "second");
		pair.setFirst(2);
		pair.setSecond("third");
		
		assertEquals(2, pair.getFirst());
		assertEquals("third", pair.getSecond());
	}
	
	@Test
	public void testPairEquals()
	{
		Pair pair1 = new Pair<String, String>("First", "second");
		Pair pair2 = new Pair<String, String>("First", "second");
		
		assertTrue(pair1.equals(pair2));
	}
	
	@Test
	public void testPairNotEquals()
	{
		Pair pair1 = new Pair<String, String>("First", "second2");
		Pair pair2 = new Pair<String, String>("First2", "second");
		
		assertFalse(pair1.equals(pair2));
		
		Pair pair3 = new Pair<String, String>( "First", "First");
		Pair pair4= new Pair<String, String>("First", "second");
		
		assertFalse(pair3.equals(pair4));
		
		pair3 = new Pair<String, String>("First", "second");
		pair4= new Pair<String, String>("First", null);
		
		assertFalse(pair3.equals(pair4));
		
		assertFalse(pair3.equals(new String("")));
	}
	
	@Test
	public void testHashCode()
	{
		Pair pair = new Pair<String, String>("First", "second");
		assertNotNull(pair.hashCode());
	}
	
	public static void main(String[] args) {
		new org.junit.runner.JUnitCore().run(PairTest.class);
	}
}
