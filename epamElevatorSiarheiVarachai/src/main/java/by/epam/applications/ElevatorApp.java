package by.epam.applications;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

import by.epam.AppConstants;

public class ElevatorApp extends JComponent {

	private static final long serialVersionUID = 1L;
	
	public ElevatorApp() {
		JFrame frame = new MainFrame("Elevator");
		frame.setSize(AppConstants.APP_MAIN_WIDTH, AppConstants.APP_MAIN_HEIGHT);
//		frame.pack();
//		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		frame.setVisible(true);
	}
	
	
}
