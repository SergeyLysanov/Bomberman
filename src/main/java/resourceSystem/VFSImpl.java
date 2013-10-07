package resourceSystem;

import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import base.VFS;

public class VFSImpl implements VFS
{
	private String root;
	public VFSImpl(String root)
	{
		this.root = root;
	}
	@Override
	public Boolean isExist(String path) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Boolean isDirectory(String path) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getAbsolutePath(String file) {
		return null;
	}
	@Override
	public byte[] getBytes(String file) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getUTF8Text(String file) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Iterator<String> getIterator(String startDir) {
		Iterator<String> iterator = new FileIterator(startDir);
		return iterator;
	}
	
	
	////////////////////////////////////////////////////////////////////////////////////
	//File iterator class
	private class FileIterator implements Iterator<String>
	{
		private Queue<File> files = new LinkedList<File>();


		public FileIterator(String path)
		{
			files.add(new File(root + path));
		}
		
		@Override
		public boolean hasNext() {
			return !files.isEmpty();
		}

		@Override
		public String next() {
			File file = files.peek();
			if(file.isDirectory())
			{
				for(File subFile : file.listFiles())
					files.add(subFile);
			}
			
			return files.poll().getAbsolutePath();
		}
		
		

		@Override
		public void remove() {
			
		}
				
	}	
}
