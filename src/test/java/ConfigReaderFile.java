import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ConfigReaderFile {
	Properties prop = new Properties();
	public String [] readConfig() throws FileNotFoundException {
		
		String propertyFilePath = FilePaths.configurationprop;
		
		LoggerUtil.logInfo(ConfigReaderFile.class, "property file path ..........."+propertyFilePath);
		String [] propinfo = new String [2];

		File fileObj = new File(propertyFilePath);
		
		 if (!fileObj.exists()) {
	            throw new FileNotFoundException("Config file NOT found at: " + propertyFilePath);
	        }
		 
		FileInputStream fis = new FileInputStream(fileObj);
		try {
			prop.load(fis);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String browser = prop.getProperty("Browser");
		LoggerUtil.logInfo(ConfigReaderFile.class, "Browser is " +browser);
		propinfo[0] = browser;
		
		
		String URL = prop.getProperty("BrowserURL");
		LoggerUtil.logInfo(ConfigReaderFile.class, "URL is " +URL);
		propinfo[1] = URL;
			
		
		return propinfo;
	}
	
	

}
