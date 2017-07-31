/* Tests class is used to test all the methods of the classes using TestNG framework 
 * Also tests the methods in parallel execution on multiple threads
*/

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import org.testng.Assert;
import org.testng.annotations.Test;


public class Tests {
	
	Logger log = Logger.getLogger("testing");
	
	LRUCache<Integer, String> obj = new LRUCache<Integer, String>(6);
	List<Integer> keys = Arrays.asList(0, 1, 2, 3, 4, 5);
	List<String> values = Arrays.asList("zero", "one", "two", "three", "four", "five");
	
	// Tests with priorities 1-6 are sequential
	// Tests with priority 7 are parallel execution on multiple threads
	
	@Test(priority=1, description="Verify the put api")
	public void testPut() {
		for(int i=0;i<=5;i++)
		{
			obj.put(keys.get(i), values.get(i));
		}
	}
	
	@Test(priority=2, description="Verify the get api")
	public void testGet() {
		for(int i=0; i<=5; i++)
		{
			Assert.assertEquals(obj.get(i), values.get(i));
		}
		Assert.assertEquals(obj.get(6), null);
	}
	
	@Test(priority=3, description="Verify the doubly linked list keys")
	public void testDoublyLLKeys() {
		Assert.assertEquals(obj.getList().getKeyList(), keys);
	}
	
	@Test(priority=4, description="Verify the doubly linked list values")
	public void testDoublyLLValues() {
		Assert.assertEquals(obj.getList().getValueList(), values);
	}
	
	@Test(priority=5, description="Verify the dumpTo method of Disk")
	public void testDumpTo() {
		Disk.dumpInto(obj, "recoveryFile");
	}
	
	@Test(priority=6, description="Verify the recoverCache method of Disk")
	public void testRecoverCache() {
		LRUCache<Integer, String> newObj = Disk.recoverCache("recoveryFile");
		Assert.assertEquals(newObj.getList().getKeyList(), obj.getList().getKeyList());
		Assert.assertEquals(newObj.getList().getValueList(), obj.getList().getValueList());
		Assert.assertEquals(newObj.getCache().getHashMap(), obj.getCache().getHashMap());
	}
	
	/* Test get and put api on parallel threads, checks for the synchronization of the apis */
	@Test(  priority=7, threadPoolSize = 50, invocationCount = 100,
			description="Verify the get and put api's on parallel threads")
    public void testMethod() 
    {
        Long id = Thread.currentThread().getId();
        log.info("Test method executing on thread with id: " + id);
        try {
	        for(int i=0;i<=5;i++) {
	        	obj.put(i, values.get(i));
	        }
	        for(int i=0;i<=5;i++) {
	        	Assert.assertEquals(obj.get(i), values.get(i));
	        }
        } catch(Exception e) {
        	log.warning("Inside Catch!!!!");     
        	Assert.assertTrue(false); // Failure
        }
    }
	
}
