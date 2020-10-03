package staticutil;

import java.awt.Image;

import javax.swing.ImageIcon;

public abstract class MainUtil {
	public static String addZeroForNum(String str, int strLength) {
		int strLen = str.length();
		if (strLen < strLength) {
			while (strLen < strLength) {
				StringBuffer sb = new StringBuffer();
				sb.append("0").append(str);// 左补0
//		    sb.append(str).append("0");//右补0  
				str = sb.toString();
				strLen = str.length();
			}
		}

		return str;
	}
	public static  ImageIcon GetImageIcon(ImageIcon imageicon) {// 改变图片大小
		// File filetwo = new File(FilePath + File.separator +
		// SmallTextFields.get(SelectImage).getText());
		// ImageIcon imageicon = new ImageIcon(filetwo.getAbsolutePath());
		double h1 = imageicon.getIconHeight();
		double w1 = imageicon.getIconWidth();
		if (h1 < 77 && w1 < 100) {
			Image image = imageicon.getImage().getScaledInstance((int) w1, (int) h1, Image.SCALE_DEFAULT);// 改变大小
			ImageIcon Finalii = new ImageIcon(image);// 从新得到一个固定图片
			return Finalii;

		} else {
			if (h1 * 180 > w1 * 142) {
				Image image = imageicon.getImage().getScaledInstance((int) (w1 / (h1 / 81)), 81, Image.SCALE_DEFAULT);// 改变大小
				ImageIcon Finalii = new ImageIcon(image);// 从新得到一个固定图片
				return Finalii;
			} else {
				Image image = imageicon.getImage().getScaledInstance(105, (int) (h1 / (w1 / 105)), Image.SCALE_DEFAULT);// 改变大小
				ImageIcon Finalii = new ImageIcon(image);// 从新得到一个固定图片
				return Finalii;
			}
		}
	}
}
