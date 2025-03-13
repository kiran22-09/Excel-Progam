import java.io.FileNotFoundException;
import java.io.IOException;

public class SysDir {

	public static void main(String[] args)  {
		// TODO Auto-generated method stub
		
		String SysPath = System.getProperty("user.dir");
		
		System.out.println("File located dir..................."+SysPath);
		
		ConfigReaderFile configObj = new ConfigReaderFile();
		try {
			configObj.readConfig();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
