package imageshowuiutil;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.jhlabs.image.CrystallizeFilter;
import com.jhlabs.image.EmbossFilter;
import com.jhlabs.image.LookupFilter;
import com.jhlabs.image.MirrorFilter;
import com.jhlabs.image.RaysFilter;
import com.jhlabs.image.SolarizeFilter;

import ui.ImageShowUi;



public class Filter {
	
	
	int choose;
	String OpenFilePath;
	File openFile;
	ArrayList<File> ClickPath = new ArrayList<File>();
	ArrayList<JTextField> Picturename = new ArrayList<JTextField>();
	ImageShowUi imageShowFrame;
	Image im;

	
	public Filter(int choose, String openFilePath, ArrayList<File> clickPath, ArrayList<JTextField> picturename,
			ImageShowUi imageShowFrame) {
		super();
		this.choose = choose;
		OpenFilePath = openFilePath;
		ClickPath = clickPath;
		Picturename = picturename;
	
		this.imageShowFrame = imageShowFrame;
	}

public  Image   InitImage() {
	try {
			if (choose == -1) {//判断是否为单独打开的图片
		 im = ImageIO.read(new File(OpenFilePath));//如果是，就获取打开照片的路径
openFile=new File(OpenFilePath);
	}
	else {
		for (int i = 0; i < ClickPath.size(); i++) {
			if (ClickPath.get(i).getName().equals(Picturename.get(choose).getText())) {

			 im = ImageIO.read(new File(ClickPath.get(i).getAbsolutePath()));//读取图片路径
			openFile=ClickPath.get(i);
			}}}
	} catch (Exception e) {
		// TODO: handle exception
	}

	return im;
	
}

	public void EmbossFilter(java.awt.event.ActionEvent evt,Image im) throws IOException {// 设置8号按钮功能为emboss滤镜

		BufferedImage img = (BufferedImage) im;// 新建一个缓冲区
		BufferedImage timg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);

		new EmbossFilter().filter(img, timg);
		JFrame f = new JFrame("Emboss滤镜");
          Show(f, img, timg);


	}
	public void RaysFilter(java.awt.event.ActionEvent evt,Image im) throws IOException {// 设置8号按钮功能为emboss滤镜

		BufferedImage img = (BufferedImage) im;// 新建一个缓冲区
		BufferedImage timg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);

		new RaysFilter().filter(img, timg);
		JFrame f = new JFrame("Rays滤镜");
          Show(f, img, timg);


	}
	public void  CrystallizeFilter(java.awt.event.ActionEvent evt,Image im) throws IOException {// 设置8号按钮功能为emboss滤镜

		BufferedImage img = (BufferedImage) im;// 新建一个缓冲区
		BufferedImage timg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);

		new CrystallizeFilter().filter(img, timg);
		JFrame f = new JFrame("crystallize滤镜");
          Show(f, img, timg);


	}
	
	public void  SolarizeFilter(java.awt.event.ActionEvent evt,Image im) throws IOException {// 设置8号按钮功能为emboss滤镜

		BufferedImage img = (BufferedImage) im;// 新建一个缓冲区
		BufferedImage timg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);

		new SolarizeFilter().filter(img, timg);
		JFrame f = new JFrame("solarize滤镜");
          Show(f, img, timg);


	}
	public void  MirrorFilter(java.awt.event.ActionEvent evt,Image im) throws IOException {// 设置8号按钮功能为emboss滤镜

		BufferedImage img = (BufferedImage) im;// 新建一个缓冲区
		BufferedImage timg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);

		new MirrorFilter().filter(img, timg);
		JFrame f = new JFrame("mirror滤镜");
          Show(f, img, timg);


	}
	public void   LookupFilter(java.awt.event.ActionEvent evt,Image im) throws IOException {// 设置8号按钮功能为emboss滤镜

		BufferedImage img = (BufferedImage) im;// 新建一个缓冲区
		BufferedImage timg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);

		new LookupFilter().filter(img, timg);
		JFrame f = new JFrame("lookup滤镜");
          Show(f, img, timg);


	}
	public void   TurnFilter(java.awt.event.ActionEvent evt,Image im) throws IOException {// 水平翻转

		BufferedImage img = (BufferedImage) im;// 新建一个缓冲区
		BufferedImage timg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
		// 获取图片的宽高
		final int width = img.getWidth();
		final int height = img.getHeight();
		System.out.println("高宽分别是"+height+width);

		// 读取出图片的所有像素
		int[] rgbs = img.getRGB(0, 0, width, height, null, 0, width);
		// 对图片的像素矩阵进行水平镜像
		for (int row = 0; row < height; row++) {
			for (int col = 0; col < width / 2; col++) {
				int temp = rgbs[row * width + col];
				rgbs[row * width + col] = rgbs[row * width + (width - 1 - col)];
				rgbs[row * width + (width - 1 - col)] = temp;
			}
		}

		// 把水平镜像后的像素矩阵设置回 bufImage
		img.setRGB(0, 0, width, height, rgbs, 0, width);

//		new TurnFilter().filter(img, timg);
		JFrame f = new JFrame("水平翻转");
		System.out.println("成功翻转");
		Show(f, img, img);


	}

	public void Show(JFrame f,BufferedImage img,BufferedImage timg) {

		f.setLocationRelativeTo(null);
		f.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {

				int n = JOptionPane.showConfirmDialog(null, "是否保存", "提示", JOptionPane.YES_NO_OPTION);// 弹出窗口，判断是否保存
				if (n == 0) {
					try {

						ImageIO.write(timg, "png", new File(openFile.getAbsolutePath()));
						ImageIcon ic1 = new ImageIcon(openFile.getAbsolutePath());
						Image im = ic1.getImage().getScaledInstance(ic1.getIconWidth(), ic1.getIconHeight(),
								Image.SCALE_DEFAULT);
						ImageIcon ic2 = new ImageIcon(im);
						imageShowFrame.getJLabel1().setIcon(ic2);
						JOptionPane.showMessageDialog(null,"滤镜添加成功","提示",1);
					} catch (Exception e2) {
						// TODO: handle exception
					}

				}
			}

		});
		f.setSize(img.getWidth() + 200, img.getHeight() + 200);
		f.getContentPane().add(new JLabel(new ImageIcon(timg)), BorderLayout.CENTER);

		f.setVisible(true);
		
		
	}
	
}
