package mainuiutil;

import java.awt.Color;
import java.awt.Image;
import java.awt.Point;
import java.awt.Robot;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import staticutil.MainUtil;
import ui.ImageShowUi;
import ui.MainUi;

public class RenameUtil {
	ArrayList<File> ClickedFilePath= new ArrayList<File>();
	ArrayList<Integer>SelecctImages;
	ArrayList<JPanel> SmallPanels = new ArrayList<JPanel>(); // 这里定义N个小的面板，上面存放标签，下面用于存放文件名的文本框
	ArrayList<JLabel> SmallLabels = new ArrayList<JLabel>(); // 这里定义N个小的标签，用来存放读取的图片
	ArrayList<JTextField> SmallTextFields = new ArrayList<JTextField>(); // 这里定义N个文本框，用来显示与图片相对应的文件的名称
	File TemporaryFile;
	ImageIcon TemporaryIcon;
	int SelectImage;
	String OldName;
	String FilePath;

	public RenameUtil(ArrayList<File> clickedFilePath, ArrayList<Integer> selecctImages, ArrayList<JPanel> smallPanels,
			ArrayList<JLabel> smallLabels, ArrayList<JTextField> smallTextFields, File temporaryFile,
			ImageIcon temporaryIcon, int selectImage, String oldName, String filePath) {
		super();
		ClickedFilePath = clickedFilePath;
		SelecctImages = selecctImages;
		SmallPanels = smallPanels;
		SmallLabels = smallLabels;
		SmallTextFields = smallTextFields;
		TemporaryFile = temporaryFile;
		TemporaryIcon = temporaryIcon;
		SelectImage = selectImage;
		OldName = oldName;
		FilePath = filePath;
	}

	public void Work() {
		Robot mRobot = null;
		try {
			mRobot = new Robot();// 生成本机系统输入事件
		} catch (java.awt.AWTException awe) {
			System.err.println("new   Robot   error");
		}
		TemporaryFile = new File(FilePath + File.separator + SmallTextFields.get(SelectImage).getText());// 创建临时文件
		Point point = new Point();
		point = SmallTextFields.get(SelectImage).getLocationOnScreen();
		mRobot.mouseMove(point.x + 50, point.y + 5);// 鼠标移动
		SmallTextFields.get(SelectImage).setEditable(true);
		OldName = (String) SmallTextFields.get(SelectImage).getText();
		SmallTextFields.get(SelectImage).setBackground(Color.GRAY);

		TemporaryIcon = (ImageIcon) SmallLabels.get(SelectImage).getIcon();// 创建临时图像
		if (!TemporaryFile.renameTo(TemporaryFile)) {// 重命名此抽象路径名表示的文件
			SmallLabels.get(SelectImage).setIcon(null);// 检查返回值以确保重命名操作成功
		}
		SmallTextFields.get(SelectImage).addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(java.awt.event.ActionEvent evt) {
				try {
					RenameText();
				} catch (IOException ex) {
					Logger.getLogger(MainUi.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		});
	}

	public void RenameText() throws IOException {
		File filetwo = new File(FilePath + File.separator + SmallTextFields.get(SelectImage).getText());// 读入新名字


		if (TemporaryFile.renameTo(filetwo)) {// 重命名
			JOptionPane.showMessageDialog(null, "重命名操作成功!!!", "重命名", JOptionPane.INFORMATION_MESSAGE);

		} else {
			JOptionPane.showMessageDialog(null, "文件被其他程序占用，重命名操作失败", "重命名", JOptionPane.INFORMATION_MESSAGE);

		}
		SmallTextFields.get(SelectImage).setBackground(null);
		SmallTextFields.get(SelectImage).setEditable(false);
		SmallLabels.get(SelectImage).setIcon(MainUtil.GetImageIcon(new ImageIcon(filetwo.getAbsolutePath())));// 显示新名字
		SmallLabels.get(SelectImage).setBackground(new java.awt.Color(48, 48, 48));
	}

}
