import org.apache.logging.log4j.LogManager;

public final class LoggerUtil {
	/* LOG LEVELS
	 * 1.FATAL
	 * 2.ERROR
	 * 3.WARN
	 * 4.INFO
	 * 5.DEBUG
	 * 6.TRACE
	 */
	
	public static final org.apache.logging.log4j.Logger Logger = LogManager.getLogger(LoggerUtil.class.getName());
	
	//Singleton pattern
	
	private LoggerUtil() {
		
	}
	
	static void logInfo(Class className, String message) {
		log(Level.InFo,className,message,null);
	}



	private static void log(Level level, Class className, String logmessage, Throwable exceptionObj) {
		// TODO Auto-generated method stub
		
		String message = String.format("[%s] : %s",className,logmessage);
		
		switch(level) {
			
		case Warn:
			Logger.warn(message,exceptionObj);
			break;
			
		case Error:
			Logger.error(message, exceptionObj);
			break;
			
		case Fatal:
			Logger.error(message, exceptionObj);
			break;
			
		case InFo:
			Logger.info(message, exceptionObj);
			break;
		
		default:
		case Debug:
			Logger.debug(message, exceptionObj);
			break;
			
		}
		
		
			
		
	}
	

}
