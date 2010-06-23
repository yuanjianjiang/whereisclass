package org.teamlet.jartools.ui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JTextPane;


public class GUIApp extends JApplet {

	private static final long serialVersionUID = -3337092290540556086L;

	public GUIApp() {

	}

	public void init() {
		
		GUILookAndFeelSetter.setDefaultLookAndFeel();
		
		JMenuBar menuBar = GUIComponentFactory.buildMenuBar();
		JTextPane textPane = GUIComponentFactory.buildTextPane();

		setContentPane(textPane);
		setJMenuBar(menuBar);

	}



	public static void main(String[] args) {

		JFrame frame = new JFrame();
		GUIContext.put(GUIConstant.TOP_JFRAME, frame);

		GUIApp applet = new GUIApp();
		applet.init();

		frame.getContentPane().add(applet);
		frame.setTitle("WhereIsClass");
		frame.setSize(650, 500);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation((d.width - frame.getSize().width) / 2,
				(d.height - frame.getSize().height) / 2);
		frame.setVisible(true);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}

}
