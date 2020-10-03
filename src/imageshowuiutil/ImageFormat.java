package imageshowuiutil;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.coobird.thumbnailator.Thumbnails;
import ui.ImageShowUi;

public class ImageFormat {
	double ks,jd;
	int choose;
	String OpenFilePath,formatString;
 JLabel jLabel;
	ArrayList<File> ClickPath = new ArrayList<File>();
	ArrayList<JTextField> Picturename = new ArrayList<JTextField>();
	ImageShowUi imageShowFrame;
	Image im;	File openFile;
	public ImageFormat(int choose, String openFilePath, ArrayList<File> clickPath, ArrayList<JTextField> picturename,
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

		 
		JFrame frame=new JFrame("格式转换");
        JPanel jp=new JPanel();    //创建面板
        JLabel label1=new JLabel("选择格式：");    //创建标签
        JComboBox cmb=new JComboBox();    //创建JComboBox
        JButton jButton=new JButton("确认");
        JButton jButton2=new JButton("取消");
        jButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
			switch (cmb.getSelectedIndex()) {
			case 0:
				formatString="jpg";
					break ;
			case 1:
				formatString="jpeg";
					break ;	
			case 2:
				formatString="gif";
					break ;
			case 3:
				formatString="bmp";
					break ;
			case 4:
				formatString="png";
					break ;
		default:
				throw new IllegalArgumentException("Unexpected value: " + cmb.getSelectedIndex());
			}
					String caString=openFile.getAbsolutePath().substring(0, openFile.getAbsolutePath().lastIndexOf("."));
					Thumbnails.of(openFile).scale(1f).outputFormat(formatString).toFile(new File(caString));  
					JOptionPane.showMessageDialog(null,"该格式图片在本目录下生成成功","提示",1);
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
        cmb.addItem("jpg");    //向下拉列表中添加一项
        cmb.addItem("jpeg");
        cmb.addItem("gif");
        cmb.addItem("bmp");
        cmb.addItem("png");
     
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
