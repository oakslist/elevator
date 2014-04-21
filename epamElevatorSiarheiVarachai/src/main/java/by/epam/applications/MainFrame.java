package by.epam.applications;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import by.epam.AppConstants;
import by.epam.ProgramConstants;
import by.epam.logs.OpenLogFile;


public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private PresentationPanel presentationArea;
	private MessagesPanel messageArea;
	private static JButton button = new JButton(AppConstants.APP_BUTTON_START_NAME);
	
	public MainFrame(String title) {
		super(title);
		
		//set layout manager
		setLayout(new BorderLayout());
		
		//create swing component	
		presentationArea = new PresentationPanel();
		messageArea = new MessagesPanel();
						
		//add swing components to content pane
		Container container = getContentPane();
		
		container.add(messageArea, BorderLayout.CENTER);
		container.add(button, BorderLayout.SOUTH);
		container.add(presentationArea, BorderLayout.WEST);
		
		//add behavior
		button.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				if (button.getText().equals(AppConstants.APP_BUTTON_VIEW_LOG_NAME)) {
					//open log file
					new OpenLogFile();
				}
				if (button.getText().equals(AppConstants.APP_BUTTON_ABORT_NAME)) {
					ProgramConstants.appButtonListener.setAbortButtonClicked(true);
					button.setText(AppConstants.APP_BUTTON_VIEW_LOG_NAME);
				}
				if (button.getText().equals(AppConstants.APP_BUTTON_START_NAME)) {
					ProgramConstants.appButtonListener.startButtonlistener(true);
					button.setText(AppConstants.APP_BUTTON_ABORT_NAME);
				}
			}
		});
	}
	
	public static void setButtonText(String text) {
		MainFrame.button.setText(text);
	}

	
}
