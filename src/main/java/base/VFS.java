package base;

import java.util.Iterator;

public interface VFS 
{
	Boolean isExist(String path);
	Boolean isDirectory(String path);
	String getAbsolutePath(String file);
	byte[] getBytes(String file);
	String getUTF8Text(String file);
	Iterator<String> getIterator(String startDir);
}
