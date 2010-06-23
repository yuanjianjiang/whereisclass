package org.teamlet.jartools.action;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.JFrame;

import org.teamlet.jartools.ui.GUIConstant;
import org.teamlet.jartools.ui.GUIContext;
import org.teamlet.jartools.ui.InputDialog;

/**
 * 打开输入对话框的动作
 * 
 * @author David
 * 
 */
public class OpenAction extends AbstractAction {

	private static final long serialVersionUID = 1144191006694216382L;
	JFrame parent = null;

	public OpenAction(String text, Icon icon) {
		super(text, icon);
		parent =  (JFrame) GUIContext.get(GUIConstant.TOP_JFRAME);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		InputDialog dialog = new InputDialog(parent);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		dialog.setLocation((screenSize.width - parent.getWidth() / 2) / 2,
				(screenSize.height - parent.getHeight() / 2) / 2);
		dialog.showDialog();
	}

}
