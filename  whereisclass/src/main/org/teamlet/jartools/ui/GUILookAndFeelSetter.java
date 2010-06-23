package org.teamlet.jartools.ui;

import java.awt.Font;

import javax.swing.UIManager;

/**
 * 设置UI的风格
 * 
 * @author David
 * 
 */
public class GUILookAndFeelSetter {

	public static void setDefaultLookAndFeel() {
		try {
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			Font font = new Font("Dialog", Font.PLAIN, 12);
			UIManager.put("MenuBar.font", font);
			UIManager.put("MenuItem.font", font);
			UIManager.put("Menu.font", font);
			UIManager.put("PopupMenu.font", font);
			UIManager.put("ToolBar.font", font);
			UIManager.put("ToolTip.font", font);
			UIManager.put("TabbedPane.font", font);
			UIManager.put("Label.font", font);
			UIManager.put("List.font", font);
			UIManager.put("ComboBox.font", font);
			UIManager.put("Button.font", font);
			UIManager.put("Table.font", font);
			UIManager.put("TableHeader.font", font);
			UIManager.put("Tree.font", font);
			UIManager.put("TextField.font", font);
			UIManager.put("TextArea.font", font);
			UIManager.put("TitledBorder.font", font);
			UIManager.put("OptionPane.font", font);
			UIManager.put("RadioButton.font", font);
			UIManager.put("CheckBox.font", font);
			UIManager.put("ToggleButton.font", font);
			UIManager.put("Dialog.font", font);
			UIManager.put("Panel.font", font);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
