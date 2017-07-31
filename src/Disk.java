/* Disk class uses the serialization mechanism to: 
	i. Create a backup of the LRUCache into file on the disk
	ii.Recover the cache from the file on the disk 
 */

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Disk {
	/* Creates backup of the LRUCache in file */
	public static synchronized void dumpInto(LRUCache obj, String filename) {
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(filename);
			ObjectOutputStream oos;
			try {
				oos = new ObjectOutputStream(fos);
				oos.writeObject(obj);
				oos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/* Recovers and returns the LRUCache object from the file */
	public static LRUCache recoverCache(String filename) { 
		FileInputStream fis;
		try {
			fis = new FileInputStream(filename);
			ObjectInputStream ois;
			try {
				ois = new ObjectInputStream(fis);
				try {
					LRUCache obj = (LRUCache) ois.readObject();
					ois.close();
					return obj;
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				ois.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		return null;
	}

}
