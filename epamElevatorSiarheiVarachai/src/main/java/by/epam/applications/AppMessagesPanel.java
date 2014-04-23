package by.epam.applications;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class AppMessagesPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	public static JTextArea messagesArea = new JTextArea();
	
	public AppMessagesPanel() {
		messagesArea.setEditable(false); // set textArea non-editable
		super.setBorder(new TitledBorder (new EtchedBorder(), "Messages area"));
		JScrollPane scroll = new JScrollPane(messagesArea);
	    scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	    
	    super.setLayout(new BorderLayout());  //give our JPanel a BorderLayout
	    super.add(scroll, BorderLayout.EAST);
    
	    //Add Text area in to middle panel
	    super.add(scroll);
	}
	
	public static void setAppLog(String log) {
		messagesArea.append(log + "\n");
	}
}
