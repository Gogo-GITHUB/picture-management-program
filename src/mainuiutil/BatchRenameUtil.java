package mainuiutil;

import java.io.File;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import staticutil.MainUtil;

public class BatchRenameUtil {
	public BatchRenameUtil(String filePath, ArrayList<JLabel> smallLabels, ArrayList<File> clickedFilePath,
			ArrayList<Integer> selecctImages, ArrayList<JTextField> smallTextFields) {
		super();
		FilePath = filePath;
		SmallLabels = smallLabels;
		ClickedFilePath = clickedFilePath;
		SelecctImages = selecctImages;
		SmallTextFields = smallTextFields;
	}
	int StartNum,NumNum;
	String FilePath;
	ArrayList<JLabel> SmallLabels = new ArrayList<JLabel>(); // 这里定义N个小的标签，用来存放读取的图片
	ArrayList<File> ClickedFilePath = new ArrayList<File>(); // 这里定义了一个，鼠标点击的文件路径下的所有文件
	ArrayList<Integer> SelecctImages = new ArrayList<Integer>();
	ArrayList<JTextField> SmallTextFields = new ArrayList<JTextField>(); // 这里定义N个文本框，用来显示与图片相对应的文件的名称
	public void work() {
		// ImagePanel.removeAll();
		int num = 0;
		File file = new File(FilePath);	System.out.println(FilePath);
		File[] files = file.listFiles();
		ArrayList<File> filesA = new ArrayList<File>();
		ArrayList<ImageIcon> ImageIcons = new ArrayList<ImageIcon>();
		for (int i = 0; i < files.length; i++) {
			String ext = files[i].getName().substring(files[i].getName().lastIndexOf(".") + 1).toLowerCase();
			if (ext.equals("jpg") || ext.equals("gif") || ext.equals("bmp")|| ext.equals("png")||ext.equals("jpeg")) {
				filesA.add(files[i]);
			}
		} // 加入图片文件队列
		for (int i = 0; i < filesA.size(); i++) {
			if (!filesA.get(i).renameTo(filesA.get(i))) {
				// ImageIcons.add(GetImageIcon(new ImageIcon(filesA.get(i).getAbsolutePath())));
				SmallLabels.get(i).setIcon(null);// 检查返回值以确保重命名操作成功
			
			}
		}

		String string = JOptionPane.showInputDialog(null, "请输入新的名字(不包含后缀)", "批量重命名", 1);
		if (string.isEmpty())
			return;
		String string2 = JOptionPane.showInputDialog(null, "请输入起始编号", "批量重命名", 1);
		if (string2.isEmpty())
			return;
		String string3 = JOptionPane.showInputDialog(null, "请输入编号位数", "批量重命名", 1);
		if (string3.isEmpty())
			return;

		try { // 捕获parselnt()抛出的异常

			StartNum = Integer.parseInt(string2);
			NumNum = Integer.parseInt(string3);
		} catch (Exception e) {

			return;

		}
		int j = StartNum;
		String rpjString = null;
		String j2 = null;

		for (int i = 0; i < SelecctImages.size(); i++, j++) {
			String axt = filesA.get(SelecctImages.get(i)).getName()
					.substring(filesA.get(SelecctImages.get(i)).getName().lastIndexOf("."),
							filesA.get(SelecctImages.get(i)).getName().length())
					.toLowerCase();// 提取后缀
			System.out.println(filesA.get(SelecctImages.get(i)));
			rpjString = String.valueOf(j);
			j2 = MainUtil.addZeroForNum(rpjString, NumNum);
			if (SmallLabels.get(i).getIcon() == null) {// 没有图像
				filesA.get(SelecctImages.get(i))
						.renameTo(new File(FilePath + File.separator + string + "(" + j2 + ")" + axt));
				ImageIcons.add(MainUtil.GetImageIcon(new ImageIcon(FilePath + File.separator + string + "(" + j2 + ")" + axt)));// 加入新建图像
				SmallTextFields.get(SelecctImages.get(i)).setText(string + "(" + j2 + ")" + axt);
				ClickedFilePath.add(new File(FilePath + File.separator + string + "(" + j2 + ")" + axt));
			} else {
				filesA.get(SelecctImages.get(i))
						.renameTo(new File(FilePath + File.separator + string + "(" + j2 + ")" + axt));
				SmallTextFields.get(SelecctImages.get(i)).setText(string + "(" + j2 + ")" + axt);
				ClickedFilePath.add(new File(FilePath + File.separator + string + "(" + j2 + ")" + axt));
			}
		} // 按顺序改名
		for (int i = 0; i < SelecctImages.size(); i++) {
			if (SmallLabels.get(SelecctImages.get(i)).getIcon() == null) {
				SmallLabels.get(SelecctImages.get(i)).setIcon(ImageIcons.get(num));
				num++;
			} // 重新设置标签图像
		}

	}
}
