package imageshowuiutil;

import java.awt.BorderLayout;
import java.awt.Frame;
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
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.jhlabs.image.EmbossFilter;

import net.coobird.thumbnailator.Thumbnails;
import ui.ImageShowUi;

public class Rotate {
	
	int choose;
	String OpenFilePath;
	File openFile;
	ArrayList<File> ClickPath = new ArrayList<File>();
	ArrayList<JTextField> Picturename = new ArrayList<JTextField>();
	ImageShowUi imageShowFrame;
	Image im;
JLabel jLabel;
	
	public Rotate(int choose, String openFilePath, ArrayList<File> clickPath, ArrayList<JTextField> picturename,
			ImageShowUi imageShowFrame) {
	
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
	
	public void  Rotating(java.awt.event.ActionEvent evt,Image im) throws IOException {// 设置8号按钮功能为emboss滤镜

		BufferedImage img = (BufferedImage) im;// 新建一个缓冲区
		BufferedImage timg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
		 timg=Thumbnails.of(img).scale(1).rotate(0).asBufferedImage();
		 JFrame f = new JFrame("旋转");
          Show(f, img, timg);

	}
	public void Show(JFrame f,BufferedImage img,BufferedImage timg) {
 JSlider jSlider=new JSlider(-180,180,0);
 jSlider.setMajorTickSpacing(90);
 jSlider.setMinorTickSpacing(45);
 jSlider.setPaintTicks(true);
 jSlider.setPaintLabels(true);
 
 jSlider.addChangeListener(new ChangeListener() {
	
	@Override
	public void stateChanged(ChangeEvent e) {
		try {
			BufferedImage timg=Thumbnails.of(img).scale(1).rotate(jSlider.getValue()).asBufferedImage();
			
	
jLabel.setIcon(new ImageIcon(timg));
			System.out.println(jSlider.getValue());
			
		} catch (Exception e2) {
			// TODO: handle exception
		}

		
		
		// TODO Auto-generated method stub
		
	}
});
		f.setLocationRelativeTo(null);
		f.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {

				int n = JOptionPane.showConfirmDialog(null, "是否保存", "标题", JOptionPane.YES_NO_OPTION);// 弹出窗口，判断是否保存
				if (n == 0) {
					try {

						ImageIO.write(Thumbnails.of(img).scale(1).rotate(jSlider.getValue()).asBufferedImage(), "png", new File(openFile.getAbsolutePath()));
						ImageIcon ic1 = new ImageIcon(openFile.getAbsolutePath());
						Image im = ic1.getImage().getScaledInstance(ic1.getIconWidth(), ic1.getIconHeight(),
								Image.SCALE_DEFAULT);
						ImageIcon ic2 = new ImageIcon(Thumbnails.of(img).scale(1).rotate(jSlider.getValue()).asBufferedImage());
						imageShowFrame.getJLabel1().setIcon(ic2);
					} catch (Exception e2) {
						// TODO: handle exception
					}

				}
			}

		});
		jSlider.setBounds(0, 0, img.getWidth(),10 );
		f.setSize(img.getWidth() + 200, img.getHeight() + 200);
		f.getContentPane().add(jSlider,BorderLayout.NORTH);
		jLabel=new JLabel(new ImageIcon(timg));
		f.getContentPane().add(jLabel, BorderLayout.CENTER);

		f.setVisible(true);
		
		
	}
	public void Show2(JFrame f,BufferedImage img,BufferedImage timg,int angle) {
 JSlider jSlider=new JSlider(-180,180,0);
 jSlider.setMajorTickSpacing(90);
 jSlider.setMinorTickSpacing(45);
 jSlider.setPaintTicks(true);
 jSlider.setPaintLabels(true);
 jSlider.addChangeListener(new ChangeListener() {
	
	@Override
	public void stateChanged(ChangeEvent e) {
		try {
			BufferedImage timg=Thumbnails.of(img).scale(1).rotate(jSlider.getValue()).asBufferedImage();
			
	
jLabel.setIcon(new ImageIcon(timg));
			System.out.println(jSlider.getValue());
			
		} catch (Exception e2) {
			// TODO: handle exception
		}

		
		
		// TODO Auto-generated method stub
		
	}
});
		f.setLocationRelativeTo(null);
		f.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {

				int n = JOptionPane.showConfirmDialog(null, "是否保存", "提示", JOptionPane.YES_NO_OPTION);// 弹出窗口，判断是否保存
				if (n == 0) {
					try {

						ImageIO.write(Thumbnails.of(img).scale(1).rotate(jSlider.getValue()).asBufferedImage(), "png", new File(openFile.getAbsolutePath()));
						ImageIcon ic1 = new ImageIcon(openFile.getAbsolutePath());
						Image im = ic1.getImage().getScaledInstance(ic1.getIconWidth(), ic1.getIconHeight(),
								Image.SCALE_DEFAULT);
						ImageIcon ic2 = new ImageIcon(Thumbnails.of(img).scale(1).rotate(jSlider.getValue()).asBufferedImage());
						imageShowFrame.getJLabel1().setIcon(ic2);
						JOptionPane.showMessageDialog(null,"修改成功","提示",1);
					} catch (Exception e2) {
						// TODO: handle exception
					}

				}
			}

		});
		jSlider.setBounds(0, 0, img.getWidth(),10 );
		f.setSize(img.getWidth() + 200, img.getHeight() + 200);
		f.getContentPane().add(jSlider,BorderLayout.NORTH);
	
		jLabel=new JLabel(new ImageIcon(timg));
		f.getContentPane().add(jLabel, BorderLayout.CENTER);

		f.setVisible(true);
		
		
	}
	

}
