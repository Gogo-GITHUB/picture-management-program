package mainuiutil;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

import javax.imageio.stream.FileImageInputStream;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class DeleteUtil {	
	int SelectImage;
		public DeleteUtil(int selectImage, int imagesQuantity, String filePath, ArrayList<JLabel> smallLabels,
			ArrayList<JPanel> smallPanels, ArrayList<Integer> selecctImages, ArrayList<JTextField> smallTextFields) {
		super();
		SelectImage = selectImage;
		ImagesQuantity = imagesQuantity;
		FilePath = filePath;
		SmallLabels = smallLabels;
		SmallPanels = smallPanels;
		SelecctImages = selecctImages;
		SmallTextFields = smallTextFields;
	}
		int ImagesQuantity;
		String FilePath;
		ArrayList<JLabel> SmallLabels = new ArrayList<JLabel>(); // 这里定义N个小的标签，用来存放读取的图片
		ArrayList<JPanel> SmallPanels = new ArrayList<JPanel>(); // 这里定义N个小的面板，上面存放标签，下面用于存放文件名的文本框
		ArrayList<Integer> SelecctImages = new ArrayList<Integer>();
		ArrayList<JTextField> SmallTextFields = new ArrayList<JTextField>(); // 这里定义N个文本框，用来显示与图片相对应的文件的名称
	public void Work() {

		for (int i = 0; i < SelecctImages.size(); i++) {
			System.out.println(SmallTextFields.get(SelecctImages.get(i)).getText());
			SmallLabels.get(SelectImage).setIcon(null);
			if (JOptionPane.showConfirmDialog(null,
					"你确定要删除" + SmallTextFields.get(SelecctImages.get(i)).getText() + "吗?") == JOptionPane.YES_OPTION) {
				try {
					BufferedInputStream bufferedInputStream=		new BufferedInputStream(new FileInputStream(FilePath + File.separator + SmallTextFields.get(SelecctImages.get(i)).getText()));
				BufferedOutputStream bufferedOutputStream=new BufferedOutputStream(new FileOutputStream(FilePath + File.separator + SmallTextFields.get(SelecctImages.get(i)).getText()));
				bufferedOutputStream.close();
					bufferedInputStream.close();
				} catch (Exception e) {
					// TODO: handle exception
				}
		
			
				
				if (new File(FilePath + File.separator + SmallTextFields.get(SelecctImages.get(i)).getText())
						.delete()) {// 删除文件
					if (ImagesQuantity - 1 > 20) {
						SmallPanels.get(SelecctImages.get(i)).setBounds(3000, 1, 0, 0);
						SmallPanels.remove(SelecctImages.get(i));
						SmallTextFields.remove(SelecctImages.get(i));
						SmallLabels.remove(SelecctImages.get(i));
						SmallPanels.trimToSize();
						SmallLabels.trimToSize();
						SmallTextFields.trimToSize();// 移除被删图片
						for (int j = SelecctImages.get(i); j < SmallPanels.size(); j++) {
							System.out.println("size" + SmallPanels.size());
							SmallPanels.get(j).setBounds((j) % 5 * 135, 1 + ((j) / 5 * 125), 120, 110);// 重新设置边界
						}
					} else {
						SmallPanels.get(SelecctImages.get(i)).setBounds(3000, 1, 0, 0);
						SmallPanels.remove(SelecctImages.get(i));
						SmallTextFields.remove(SelecctImages.get(i));
						SmallLabels.remove(SelecctImages.get(i));
						SmallPanels.trimToSize();
						SmallLabels.trimToSize();
						SmallTextFields.trimToSize();
						for (int j = SelecctImages.get(i); j < SmallPanels.size(); j++) {
							System.out.println("size" + SmallPanels.size());
							SmallPanels.get(j).setBounds((j) % 5 * 135, 1 + ((j) / 5 * 125), 120, 110);
						}
					}
					ImagesQuantity--;// 数量-1
				} else {// 无法删除
					JOptionPane.showMessageDialog(null, "文件正被另一个程序使用中，无法进行删除操作!!!", "ERROR",
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		}
	}
	public int getSelectImage() {
		return SelectImage;
	}
	public int getImagesQuantity() {
		return ImagesQuantity;
	}
}
