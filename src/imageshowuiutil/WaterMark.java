package imageshowuiutil;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;



import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import net.coobird.thumbnailator.name.Rename;
import ui.ImageShowUi;

public class WaterMark {
	int choose;
	String OpenFilePath;
JLabel jLabel;
Positions positions;
BufferedImage watermarkImage;
	ArrayList<File> ClickPath = new ArrayList<File>();
	ArrayList<JTextField> Picturename = new ArrayList<JTextField>();
	ImageShowUi imageShowFrame;
	Image im;	File openFile;
	public WaterMark(int choose, String openFilePath, ArrayList<File> clickPath, ArrayList<JTextField> picturename,
			ImageShowUi imageShowFrame,Positions positions) {
		super();
		this.choose = choose;
		OpenFilePath = openFilePath;
		ClickPath = clickPath;
		Picturename = picturename;
		this.imageShowFrame = imageShowFrame;
		this.positions=positions;
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
	public void  choosewatermark(java.awt.event.ActionEvent evt, Image im) throws IOException {// 设置8号按钮功能为emboss滤镜

		BufferedImage img = (BufferedImage) im;// 新建一个缓冲区
		BufferedImage timg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & GIF Images", "jpg", "gif","png");
		chooser.setFileFilter(filter);// 文件选择
	chooser.showDialog(null, null);

 watermarkImage = ImageIO.read( chooser.getSelectedFile().getAbsoluteFile());
timg=Thumbnails.of(new File(openFile.getAbsolutePath()))
	.scale(1)
	.watermark(positions, watermarkImage, 0.5f).asBufferedImage();
		JFrame f = new JFrame("添加水印");
		Show(f, img, timg);

	}
	public void Show(JFrame f, BufferedImage img, BufferedImage timg) {
		JSlider jSlider = new JSlider(0, 10, 5);
		jSlider.setMajorTickSpacing(5);
		jSlider.setMinorTickSpacing(1);
		jSlider.setPaintTicks(true);
		jSlider.setPaintLabels(true);

		Hashtable<Integer, JComponent> hashtable = new Hashtable<Integer, JComponent>();
		hashtable.put(0, new JLabel("水印清晰度")); // 0 刻度位置，显示 "Start"
		hashtable.put(5, new JLabel("50%")); // 0 刻度位置，显示 "Start"
		hashtable.put(10, new JLabel("100%")); // 10 刻度位置，显示 "Middle"
		

		/*
		 * 将刻度值和自定义标签的对应关系设置到滑块（设置后不再显示默认的刻度值）
		 */
		jSlider.setLabelTable(hashtable);
		jSlider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				try {
					double z = (double) jSlider.getValue();
					float k = (float)z / 10;
					BufferedImage timg = Thumbnails.of(new File(openFile.getAbsolutePath()))
							.scale(1)
							.watermark(positions, watermarkImage, k).asBufferedImage();

					jLabel.setIcon(new ImageIcon(timg));
					System.out.println(jSlider.getValue() / 10);

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
						double z = (double) jSlider.getValue();
					float k =(float) z / 10;
						ImageIO.write(Thumbnails.of(new File(openFile.getAbsolutePath()))
								.scale(1)
								.watermark(positions, watermarkImage, k).asBufferedImage(), "png",
								new File(openFile.getAbsolutePath()));
						ImageIcon ic1 = new ImageIcon(openFile.getAbsolutePath());
						Image im = ic1.getImage().getScaledInstance(ic1.getIconWidth(), ic1.getIconHeight(),
								Image.SCALE_DEFAULT);
						ImageIcon ic2 = new ImageIcon(Thumbnails.of(new File(openFile.getAbsolutePath()))
								.scale(1)
								.watermark(positions, watermarkImage, k).asBufferedImage());
						imageShowFrame.getJLabel1().setIcon(ic2);
						JOptionPane.showMessageDialog(null,"水印添加成功","提示",1);
					} catch (Exception e2) {
						// TODO: handle exception
					}

				}
			}

		});
		jSlider.setBounds(0, 0, img.getWidth(), 10);
		f.setSize(img.getWidth() + 200, img.getHeight() + 200);
		f.getContentPane().add(jSlider, BorderLayout.NORTH);
		jLabel = new JLabel(new ImageIcon(timg));
		f.getContentPane().add(jLabel, BorderLayout.CENTER);
	
		f.setVisible(true);

	}


	
}
