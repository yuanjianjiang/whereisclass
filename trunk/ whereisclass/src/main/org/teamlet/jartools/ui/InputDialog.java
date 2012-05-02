package org.teamlet.jartools.ui;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import org.teamlet.jartools.WhereIsClass;

/**
 * 文件夹和查询类名称输入对话框
 * 
 * @author <a href="mailto:teamlet@gmail.com">David</a>
 * 
 */
public class InputDialog extends JDialog implements ActionListener {

	private static final long serialVersionUID = -6763669943254624250L;

	private JFileChooser jFileChooser = null;

	private JTextField foders = new JTextField("");

	private JTextField targetClass = new JTextField("");

	private boolean okPressed;

	private JButton okButton;

	private JButton cleanButton;

	private JButton cancelButton;

	StyledDocument document = null;

	/**
	 * 文件夹和查询类名称输入对话框
	 * 
	 * @param parent
	 */
	public InputDialog(JFrame parent) {

		super(parent, "Select folders...", true);
		this.document = (StyledDocument) GUIContext.get(GUIConstant.DOCUMENT);

		Container contentPane = getContentPane();
		JPanel p1 = new JPanel(new GridLayout(2, 2, 2, 2));
		p1.add(new JLabel("Folders:"));
		p1.add(foders);
		p1.add(getChooseJButton());
		// p1.add(getCleanJButton());
		p1.add(new JLabel("Target Name:"));
		p1.add(targetClass);
		contentPane.add("Center", p1);

		Panel p2 = new Panel();

		cleanButton = addButton(p2, "Clean");
		cancelButton = addButton(p2, "Cancel");
		okButton = addButton(p2, "Ok");
		contentPane.add("South", p2);

		setSize(340, 120);
	}

	private JButton addButton(Container c, String name) {
		JButton button = new JButton(name);
		button.addActionListener(this);
		c.add(button);
		return button;
	}

	public void actionPerformed(ActionEvent evt) {
		Object source = evt.getSource();
		if (source == okButton) {
			if (foders.getText().equalsIgnoreCase("")) {
				JOptionPane.showMessageDialog(this, "路径不能为空!");
			} else if (targetClass.getText().equalsIgnoreCase("")) {
				JOptionPane.showMessageDialog(this, "查找类名关键字不能为空!");
			} else if (!isValidedFolder(foders.getText())) {
				JOptionPane.showMessageDialog(this, "指定路径不存在或者所指是个文件!");
			} else {
				okPressed = true;
				setVisible(false);
			}

		} else if (source == cancelButton) {
			setVisible(false);
		} else if (source == cleanButton) {
			foders.setText("");
		}

	}

	public boolean showDialog() {

		okPressed = false;
		setVisible(true);
		if (okPressed) {
			insertText();
		}
		return okPressed;
	}

	private boolean isValidedFolder(String folders) {
		String targetFolders[] = folders.split(";");
		boolean result = true;
		for (int i = 0; i < targetFolders.length; i++) {
			File dir = new File(targetFolders[i]);
			boolean isFolderAndExist = dir.isDirectory() && dir.exists();
			if (!isFolderAndExist) {
				return false;
			}
		}
		return result;
	}

	private void insertText() {

		new GUIWhereIsClass(foders.getText(), targetClass.getText());
	}

	/**
	 * 覆写log方法，把输出信息写进文本窗体
	 * 
	 * 
	 */
	class GUIWhereIsClass extends WhereIsClass {

		boolean clean = false;
		Style style = null;

		public GUIWhereIsClass(String folders, String target) {
			super(folders, target);
		}

		protected void log(String info) {

			info = "\n" + info;

			style = document.getStyle(StyleContext.DEFAULT_STYLE);
			StyleConstants.setAlignment(style, StyleConstants.ALIGN_LEFT);
			StyleConstants.setFontSize(style, 14);
			StyleConstants.setSpaceAbove(style, 4);
			StyleConstants.setSpaceBelow(style, 4);

			if (!clean) {
				try {
					document.remove(0, document.getLength());
				} catch (BadLocationException badLocationException) {
					badLocationException.printStackTrace();
				}
				clean = true;
			}

			try {
				document.insertString(document.getLength(), info, style);
			} catch (BadLocationException badLocationException) {
				badLocationException.printStackTrace();
			}
		}
	}

	private JFileChooser getJFileChooser() {
		if (jFileChooser == null) {
			jFileChooser = new JFileChooser();
			jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			jFileChooser.setAcceptAllFileFilterUsed(false);
			jFileChooser.setMultiSelectionEnabled(true);
			jFileChooser.setDialogType(JFileChooser.OPEN_DIALOG);
			jFileChooser.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (e.getActionCommand().equals(JFileChooser.APPROVE_SELECTION)) {
						String content = null;
						File[] files = jFileChooser.getSelectedFiles();
						content = foders.getText();
						if (content.length() > 0) {
							content = foders.getText() + ";";

						}
						for (int i = 0; i < files.length; i++) {
							if (i > 0) {
								content = content + ";";
							}
							content = content + files[i].getAbsolutePath();
						}
						foders.setText(content);
					}
				}
			});
		}

		jFileChooser.showOpenDialog(this);
		return jFileChooser;
	}

	private JButton getChooseJButton() {
		JButton jButton = null;
		if (jButton == null) {
			jButton = new JButton("选择...");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					getJFileChooser();
				}
			});
		}
		return jButton;
	}
}
