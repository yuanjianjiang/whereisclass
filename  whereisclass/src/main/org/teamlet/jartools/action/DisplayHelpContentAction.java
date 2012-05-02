package org.teamlet.jartools.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import org.teamlet.jartools.ui.GUIConstant;
import org.teamlet.jartools.ui.GUIContext;

/**
 * 显示帮助内容
 * 
 * @author <a href="mailto:teamlet@gmail.com">David</a>
 * 
 */
public class DisplayHelpContentAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5384279149121510493L;

	public DisplayHelpContentAction(String text, Icon icon) {
		super(text, icon);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		StyledDocument document = (StyledDocument) GUIContext.get(GUIConstant.DOCUMENT);
		Style style = document.getStyle(StyleContext.DEFAULT_STYLE);
		StyleConstants.setAlignment(style, StyleConstants.ALIGN_LEFT);
		StyleConstants.setFontSize(style, 20);
		StyleConstants.setSpaceAbove(style, 4);
		StyleConstants.setSpaceBelow(style, 4);
		StyleConstants.setBold(style, true);
		String info = "帮助信息";
		boolean clean = false;
		if (!clean) {
			try {
				document.remove(0, document.getLength());
			} catch (BadLocationException badLocationException) {
				badLocationException.printStackTrace();
			}
			clean = true;
		}
		outputMessage(document, style, info);
		StyleConstants.setFontSize(style, 14);
		StyleConstants.setBold(style, false);
		info = "\nWhereIsClass是一个在jar中查找class的工具，允许通过关键字查找在单个或多个文件夹下所有jar中匹配的类。";
		info = info + "\n需要的参数有两个，第一个是路径。允许有一个以上的路径，路径之间用分号(\";\")隔开；";
		info = info + "\n第二个是关键字，也就是要查找的类的名字。关键字可以是要查找的类名的一部分，支持模糊匹配。";
		info = info + "\n通过选择器可以多次选择路径，路径会自动累加。如果要清空路径，请点击清除按钮。";
		outputMessage(document, style, info);

		info = "\n当前版本";
		StyleConstants.setBold(style, true);
		StyleConstants.setFontSize(style, 20);
		outputMessage(document, style, info);

		info = "\n图形模式：";
		info = info + "\nGUI V1.0 2012-05-02";
		StyleConstants.setBold(style, false);
		StyleConstants.setFontSize(style, 14);
		outputMessage(document, style, info);

		info = "\n更新历史";
		StyleConstants.setBold(style, true);
		StyleConstants.setFontSize(style, 20);
		outputMessage(document, style, info);

		info = "\n图形模式：";
		info = info + "\nGUI V1.0 2012-05-02";
		info = info + "\nGUI V0.1 2010-06-23";
		info = info + "\n命令行模式：";
		info = info + "\nV0.2 2010-06-23";
		info = info + "\nV0.1 2010-06-22";
		StyleConstants.setFontSize(style, 14);
		StyleConstants.setBold(style, false);
		outputMessage(document, style, info);

		info = "\n问题反馈：";
		StyleConstants.setBold(style, true);
		StyleConstants.setFontSize(style, 20);
		outputMessage(document, style, info);

		info = "\n请邮件至：";
		info = info + "\nteamlet@gmail.com";
		info = info + "\nhttp://code.google.com/p/whereisclass/";

		StyleConstants.setFontSize(style, 14);
		StyleConstants.setBold(style, false);
		outputMessage(document, style, info);

	}

	private void outputMessage(StyledDocument document, Style style, String info) {
		try {
			document.insertString(document.getLength(), info, style);
		} catch (BadLocationException badLocationException) {
			badLocationException.printStackTrace();
		}
	}
}
