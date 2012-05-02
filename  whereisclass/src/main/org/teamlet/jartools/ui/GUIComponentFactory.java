package org.teamlet.jartools.ui;

import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import org.teamlet.jartools.action.DisplayHelpContentAction;
import org.teamlet.jartools.action.OpenAction;

/**
 * 构建并设置界面需要的图形组件
 * 
 * @author <a href="mailto:teamlet@gmail.com">David</a>
 * 
 */
public class GUIComponentFactory {

	/**
	 * 创建菜单条
	 * 
	 * @return
	 */
	public static JMenuBar buildMenuBar() {

		JMenuBar menuBar = new JMenuBar();
		JMenu appletMenu = new JMenu("File文件");
		// 快捷方式Alt+F
		appletMenu.setMnemonic('F');

		OpenAction openAction = new OpenAction("Open打开", null);

		JMenuItem openProject = new JMenuItem(openAction);
		JMenuItem quit = new JMenuItem("Quit退出");

		openProject.setMnemonic('O');
		quit.setMnemonic('Q');
		quit.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				System.exit(0);
			}
		});

		menuBar.add(appletMenu);
		appletMenu.add(openProject);

		appletMenu.addSeparator();
		appletMenu.add(quit);

		DisplayHelpContentAction helpAction = new DisplayHelpContentAction("显示帮助", null);
		appletMenu = new JMenu("Help帮助");
		appletMenu.setMnemonic('H');
		JMenuItem about = new JMenuItem(helpAction);
		appletMenu.add(about);
		menuBar.add(appletMenu);

		return menuBar;

	}

	/**
	 * 创建文本窗体
	 * 
	 * @return
	 */
	public static JScrollPane buildTextPane() {

		JTextPane textPane = null;

		StyleContext context = new StyleContext();
		StyledDocument document = new DefaultStyledDocument(context);
		GUIContext.put(GUIConstant.DOCUMENT, document);
		Style style = context.getStyle(StyleContext.DEFAULT_STYLE);
		StyleConstants.setAlignment(style, StyleConstants.ALIGN_LEFT);
		StyleConstants.setFontSize(style, 14);
		StyleConstants.setSpaceAbove(style, 4);
		StyleConstants.setSpaceBelow(style, 4);

		String message = "WhereIsClass 用于查找指定目录下包含的jar中存在的class，更多信息请参见帮助。";
		try {
			document.insertString(document.getLength(), message, style);
		} catch (BadLocationException badLocationException) {
			System.err.println("Oops");
		}

		textPane = new JTextPane(document);
		textPane.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(textPane, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		return scrollPane;
	}
}
