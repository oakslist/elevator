package by.epam.logs;
import by.epam.applications.MessagesPanel;
import by.epam.model.beans.ConfigFile;

/**
 * write log messages into console and application
*/
public class MyLogWriter {
	
	public static int ANIMATION_BOOTS = 0;
	
	public MyLogWriter(ConfigFile configFile) {
		MyLogWriter.ANIMATION_BOOTS = configFile.getAnimationBoost();
	}
	
	public static void writeLog(String log) {
		System.out.println(log);
		if (MyLogWriter.ANIMATION_BOOTS != 0) {
			MessagesPanel.setAppLog(log);
		}
	}
	

}
