package by.epam.logs;
import by.epam.ProgramConstants;


public class OpenLogFile {
		
	private String filePath = ProgramConstants.WORKING_DIRECTORY + 
			ProgramConstants.LOG_FILE_NAME;
	private String program = ProgramConstants.OPEN_LOG_FILE_PROGRAM;

	public OpenLogFile() {
		try {
			// it works in MS Windows but I'm didn't check in other systems
			Runtime.getRuntime().exec(program + " " + filePath); 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
