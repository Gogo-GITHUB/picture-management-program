package imageshowuiutil;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.name.Rename;

import ui.ImageShowUi;

public class Compress {
	double ks,jd;
	int choose;
	String OpenFilePath;
 JLabel jLabel;
	ArrayList<File> ClickPath = new ArrayList<File>();
	ArrayList<JTextField> Picturename = new ArrayList<JTextField>();
	ImageShowUi imageShowFrame;
	Image im;	File openFile;
	public Compress(int choose, String openFilePath, ArrayList<File> clickPath, ArrayList<JTextField> picturename,
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
	public void  proportion(java.awt.event.ActionEvent evt,Image im) throws IOException {// 设置8号按钮功能为emboss滤镜

		BufferedImage img = (BufferedImage) im;// 新建一个缓冲区
		BufferedImage timg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
		 timg=Thumbnails.of(img).scale(1).asBufferedImage();
		
          Show( img, timg);

	}
	public void Show(BufferedImage img,BufferedImage timg) {

		 
		JFrame frame=new JFrame("压缩");
        JPanel jp=new JPanel();    //创建面板
        JLabel label1=new JLabel("压缩比率：");    //创建标签
        JComboBox cmb=new JComboBox();    //创建JComboBox
        JButton jButton=new JButton("确认");
        JButton jButton2=new JButton("取消");
        jButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					System.out.println(1-cmb.getSelectedIndex()*0.1);
					Thumbnails.of(openFile).scale(1f).outputQuality(1-cmb.getSelectedIndex()*0.1).toFile(openFile);  
				ImageIcon ic1 = new ImageIcon(openFile.getAbsolutePath());
				Image im = ic1.getImage().getScaledInstance(ic1.getIconWidth(), ic1.getIconHeight(),
						Image.SCALE_DEFAULT);
				ImageIcon ic2 = new ImageIcon(im);
				imageShowFrame.getJLabel1().setIcon(ic2);
				JOptionPane.showMessageDialog(null,"图片压缩成功","提示",1);
				} catch (Exception e2) {
					// TODO: handle exception
				}
				// TODO Auto-generated method stub
	
				frame.dispose();
				
				
			}
		});
        jButton2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
			frame.dispose();
			}
		});
        cmb.addItem("100%");    //向下拉列表中添加一项
        cmb.addItem("90%");
        cmb.addItem("80%");
        cmb.addItem("70%");
        cmb.addItem("60%");
        cmb.addItem("50%");
        cmb.addItem("40%");
        cmb.addItem("30%");
        cmb.addItem("20%");
        cmb.addItem("10%");
        jp.add(label1);
        jp.add(cmb);
        jp.add(jButton);
        jp.add(jButton2);
        frame.add(jp);
        frame.setBounds(300,200,400,100);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			}

 
}
