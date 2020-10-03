package mainuiutil;

import java.awt.Image;
import java.io.File;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import ui.ImageShowUi;

public class InUtil {
ArrayList<File> ClickedFilePath= new ArrayList<File>();
ArrayList<Integer>SelecctImages;
ArrayList<JPanel> SmallPanels = new ArrayList<JPanel>(); // 这里定义N个小的面板，上面存放标签，下面用于存放文件名的文本框
ArrayList<JLabel> SmallLabels = new ArrayList<JLabel>(); // 这里定义N个小的标签，用来存放读取的图片
ArrayList<JTextField> SmallTextFields = new ArrayList<JTextField>(); // 这里定义N个文本框，用来显示与图片相对应的文件的名称
int SelectImage;
int ImagesQuantity;
public InUtil(ArrayList<File> clickedFilePath, ArrayList<Integer> selecctImages, ArrayList<JPanel> smallPanels,
			ArrayList<JLabel> smallLabels, ArrayList<JTextField> smallTextFields, int selectImage, int imagesQuantity) {
		super();
		ClickedFilePath = clickedFilePath;
		SelecctImages = selecctImages;
		SmallPanels = smallPanels;
		SmallLabels = smallLabels;
		SmallTextFields = smallTextFields;
		SelectImage = selectImage;
		ImagesQuantity = imagesQuantity;
	}
public void work() {
if(SelecctImages.size()==1)
{
ImageIcon ic2 = null;

for (int t = 0; t < ClickedFilePath.size(); t++) {

	if (ClickedFilePath.get(t).getName().equals(SmallTextFields.get(SelectImage).getText())) {// 获取点击的图片
		System.out.println("点击的图片" + ClickedFilePath.get(t).getAbsolutePath());
		ImageIcon TemporaryIcon = new ImageIcon(ClickedFilePath.get(t).getAbsolutePath());
		Image TemporaryImage = TemporaryIcon.getImage().getScaledInstance(
				TemporaryIcon.getIconWidth(), TemporaryIcon.getIconHeight(),
				Image.SCALE_DEFAULT);
		ic2 = new ImageIcon(TemporaryImage);
	}
} // 进入详情页面
ImageShowUi jj = new ImageShowUi(ClickedFilePath, SmallTextFields, SelectImage, ImagesQuantity,
		SmallLabels, "123",SmallTextFields,SmallPanels);
jj.setVisible(true);
jj.getJLabel1().setIcon(ic2);
jj.getJLabel1().setHorizontalAlignment(SwingConstants.CENTER);

}
}

}
