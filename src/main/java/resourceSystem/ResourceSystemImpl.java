package resourceSystem;

import java.util.HashMap;
 // $codepro.audit.disable unnecessaryImport
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Logger;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import base.Resource;
import base.ResourceSystem;
import base.VFS;

public class ResourceSystemImpl implements ResourceSystem {


	private static Logger logger = Logger.getLogger("FileLogger");
    private static  final String resourcePath  = "Resources";
    private VFS  vfs             =  new VFSImpl(resourcePath);
    private Map<String, base.Resource> gameResource = new HashMap<String, base.Resource>();


    @Override
    public Resource getGameResource(String resource){
        return gameResource.get(resource);
    }

    public ResourceSystemImpl(){
        try {

            SAXParserFactory  factory    =  SAXParserFactory.newInstance();
            SAXParser         saxParser  =  factory.newSAXParser();
            SaxEmptyHandler   handler    =  new SaxEmptyHandler();
            Iterator<String>  iterator   =  vfs.getIterator("/");

            String filePath;

            while (iterator.hasNext())
            {
            	filePath = iterator.next();
            	int endInd = filePath.lastIndexOf(".");
            	String extension = filePath.substring(endInd + 1, filePath.length());
            	
            	if(extension.equalsIgnoreCase("xml"))
            	{
            		int beginInd = filePath.lastIndexOf("/");
            		String fileName = filePath.substring(beginInd + 1, endInd);
            		logger.info("Resource file : " + filePath);
	                saxParser.parse(filePath, handler );
	                Object obj = handler.getObject();
	                if(obj instanceof Resource){
	                    gameResource.put(fileName, (Resource)obj);
	                }
            	}
            }

        }  
        catch (Exception ex){
            ex.printStackTrace();

        }
    }
}