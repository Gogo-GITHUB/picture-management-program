package ui;

import imageshowuiutil.*;
import net.coobird.thumbnailator.geometry.Positions;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ImageShowUi extends javax.swing.JFrame {


	ArrayList<File> ClickPath = new ArrayList<File>();
	ArrayList<JTextField> Picturename = new ArrayList<JTextField>();
	ArrayList<JLabel> SmallLabels = new ArrayList<JLabel>();
	ArrayList<JTextField> SmallTextFields = new ArrayList<JTextField>();
	ArrayList<JPanel> SmallPanels = new ArrayList<JPanel>();
	String FilePath;
	int choose = 0;//计数器，记录到第几张图片
	double nowmu = 1;
	int picturenumber = 0;
	int flag = 0;//开关
	AutoPlay play;//自动播放的函数
	String OpenFilePath;//直接打开图片时的路径

	public ImageShowUi(ArrayList<File> ClickPath, ArrayList<JTextField> Picturename, int choose, int picturenumber,
			ArrayList<JLabel> SmallLabels, String OpenFilePath, ArrayList<JTextField> SmallTextFields,
			ArrayList<JPanel> SmallPanels) {//总函数
		init();//初始化所有参数
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.ClickPath = ClickPath;
		this.Picturename = Picturename;
		this.choose = choose;
		this.picturenumber = picturenumber - 1;
		this.SmallLabels = SmallLabels;
		this.OpenFilePath = OpenFilePath;
		this.SmallTextFields = SmallTextFields;
		this.SmallPanels = SmallPanels;
		if (choose == -1) {//当目前照片是单独打开时，无法进行上一张下一张和播放的操作
			jButton2.setEnabled(false);
			jButton1.setEnabled(false);
			jButton5.setEnabled(false);
			jButton6.setEnabled(false);
		}
		if (choose == 0) {
			jButton1.setEnabled(false);
			jButton1.setEnabled(false);

		}
		if (choose == picturenumber - 1&&choose !=-1) {//当目前照片为最后一张时，无法进行下一张的操作
			jButton1.setEnabled(true);
			jButton2.setEnabled(false);
			jButton3.setEnabled(true);
			jButton4.setEnabled(true);
			jButton5.setEnabled(false);
			jButton6.setEnabled(false);
			
		}
		if (choose != picturenumber - 1 && choose != 0 && choose != -1) {//当目前照片不是第一张也不是最后一张时，可以进行全部操作
			jButton1.setEnabled(true);
			jButton2.setEnabled(true);
			jButton3.setEnabled(true);
			jButton4.setEnabled(true);
			jButton5.setEnabled(true);
			jButton6.setEnabled(true);
			
		}

	}

	public void Back() {//上一张图片
		if (choose >= 1) {
			for (int i = 0; i < ClickPath.size(); i++) {
				if (ClickPath.get(i).getName().equals(Picturename.get(choose - 1).getText())) {//获取图片路径
					ImageIcon ic1 = new ImageIcon(ClickPath.get(i).getAbsolutePath());

					// 增加判断
					double w = ic1.getIconWidth();
					double h = ic1.getIconHeight();
					System.out.println("正常显示lmp");
					if ((int)w * 1080 > (int)1920 * h && (w>=1920)){
						System.out.println("如果宽度比高度大，就选择展示宽度为最大，高度适应");
						h = h/w * 1920;
						w = 1920;

					}else if((int)w * 1080 < (int)1920 * h && (h>=1080)){
						System.out.println("如果高度比宽度大，就选择展示高度为最大，宽度适应");
						w = (int)w/h * 1080;
						h = 1080;
					}else {
						System.out.println("如果小于屏幕大小，则随意");
					}

					Image im = ic1.getImage().getScaledInstance((int)w, (int)h,
							Image.SCALE_DEFAULT);
					ImageIcon ic2 = new ImageIcon(im);
					getJLabel1().setIcon(ic2);
				}
			}
			choose = choose - 1;//进行上一张操作后当前选择的图片数字-1
			if (choose == 0) {
				getJButton1().setEnabled(false);
				jButton2.setEnabled(true);
				jButton3.setEnabled(true);
				jButton4.setEnabled(true);
				jButton5.setEnabled(true);
				jButton6.setEnabled(true);
				
			} else {
				getJButton1().setEnabled(true);
				jButton2.setEnabled(true);
				jButton3.setEnabled(true);
				jButton4.setEnabled(true);
				jButton5.setEnabled(true);
				jButton6.setEnabled(true);
				
			}
		}
	}

	public void Forward() {//下一张图片
		if (choose + 1 <= picturenumber) {
			for (int i = 0; i < ClickPath.size(); i++) {
				if (ClickPath.get(i).getName().equals(Picturename.get(choose + 1).getText())) {//获取图片路径
					ImageIcon ic1 = new ImageIcon(ClickPath.get(i).getAbsolutePath());
					// 增加判断
					double w = ic1.getIconWidth();
					double h = ic1.getIconHeight();
					System.out.println("下一张");
					System.out.println(w);
					System.out.println(h);
					if ((int)w * 1080 > (int)1920 * h && (w>=1920)){
						System.out.println("如果宽度比高度大，就选择展示宽度为最大，高度适应");
						h = (int)h*1920/w;
						w = 1920;
					}else if((int)w * 1080 < (int)1920 * h && (h>=1080)){
						System.out.println("如果高度比宽度大，就选择展示高度为最大，宽度适应");
						w = (int)w/h * 1080;
						h = 1080;
					}else {
						System.out.println("如果小于屏幕大小，则随意");
					}
					System.out.println(w);
					System.out.println(h);
//					Image im = ic1.getImage().getScaledInstance(ic1.getIconWidth(), ic1.getIconHeight(),
//							Image.SCALE_DEFAULT);
					Image im = ic1.getImage().getScaledInstance((int)w, (int)h,
							Image.SCALE_DEFAULT);
					ImageIcon ic2 = new ImageIcon(im);
					getJLabel1().setIcon(ic2);
				}
			}
			choose = choose + 1;//计数器
			if (choose == picturenumber) {
				jButton2.setEnabled(false);
				jButton5.setEnabled(false);
				jButton6.setEnabled(false);
				getJButton1().setEnabled(true);
				jButton3.setEnabled(true);
				jButton4.setEnabled(true);
			} else {
				jButton1.setEnabled(true);
			}
		}
	}

	public void Enlarge() {//放大图片
		if (choose == -1) {//判断是否单独打开图片
			/* 修改前
			ImageIcon ic1 = new ImageIcon(OpenFilePath);
			double w = ic1.getIconWidth();
			double h = ic1.getIconHeight();
			Image im = ic1.getImage().getScaledInstance((int) (w * (nowmu + 0.25)), (int) (h * (nowmu + 0.25)),
					Image.SCALE_DEFAULT);
			ImageIcon ic2 = new ImageIcon(im);
			getJLabel1().setIcon(ic2);
			*/

			// 修改后
			ImageIcon ic1 = new ImageIcon(OpenFilePath);
			double w = ic1.getIconWidth();
			double h = ic1.getIconHeight();
			System.out.println("正常显示lmp");
			if ((int)w * 1080 > (int)1920 * h && (w>=1920)){
				System.out.println("如果宽度比高度大，就选择展示宽度为最大，高度适应");
				h = h/w * 1920;
				w = 1920;
			}else if((int)w * 1080 < (int)1920 * h && (h>=1080)){
				System.out.println("如果高度比宽度大，就选择展示高度为最大，宽度适应");
				w = (int)w/h * 1080;
				h = 1080;
			}else {
				System.out.println("如果小于屏幕大小，则随意");
			}

			Image im = ic1.getImage().getScaledInstance((int) (w * (nowmu + 0.25)), (int) (h * (nowmu + 0.25)),
					Image.SCALE_DEFAULT);
			ImageIcon ic2 = new ImageIcon(im);
			getJLabel1().setIcon(ic2);

		} else {
			if (choose != flag) {
				nowmu = 1;
			}
			for (int i = 0; i < ClickPath.size(); i++) {
				if (ClickPath.get(i).getName().equals(Picturename.get(choose).getText())) {//获取图片路径
					/* 修改前

					ImageIcon ic1 = new ImageIcon(ClickPath.get(i).getAbsolutePath());
					double w = ic1.getIconWidth();
					double h = ic1.getIconHeight();
					Image im = ic1.getImage().getScaledInstance((int) (w * (nowmu + 0.25)), (int) (h * (nowmu + 0.25)),
							Image.SCALE_DEFAULT);
					ImageIcon ic2 = new ImageIcon(im);
					getJLabel1().setIcon(ic2);
					*/

					// 修改后
					ImageIcon ic1 = new ImageIcon(ClickPath.get(i).getAbsolutePath());
					double w = ic1.getIconWidth();
					double h = ic1.getIconHeight();
					System.out.println("正常显示lmp");
					if ((int)w * 1080 > (int)1920 * h && (w>=1920)){
						System.out.println("如果宽度比高度大，就选择展示宽度为最大，高度适应");
						h = h/w * 1920;
						w = 1920;

					}else if((int)w * 1080 < (int)1920 * h && (h>=1080)){
						System.out.println("如果高度比宽度大，就选择展示高度为最大，宽度适应");
						w = (int)w/h * 1080;
						h = 1080;
					}else {
						System.out.println("如果小于屏幕大小，则随意");
					}
					Image im = ic1.getImage().getScaledInstance((int) (w * (nowmu + 0.25)), (int) (h * (nowmu + 0.25)),
							Image.SCALE_DEFAULT);
					ImageIcon ic2 = new ImageIcon(im);
					getJLabel1().setIcon(ic2);

				}
			}
		}
		nowmu = nowmu + 0.25;
		flag = choose;
	}

	public void Decrease() {//缩小图片
		if (choose == -1) {//判断是否单独打开的图片
			ImageIcon ic1 = new ImageIcon(OpenFilePath);
//			double w = ic1.getIconWidth();
//			double h = ic1.getIconHeight();
			//修改后
			double w = ic1.getIconWidth();
			double h = ic1.getIconHeight();
			System.out.println("正常显示lmp");
			if ((int)w * 1080 > (int)1920 * h && (w>=1920)){
				System.out.println("如果宽度比高度大，就选择展示宽度为最大，高度适应");
				h = h/w * 1920;
				w = 1920;

			}else if((int)w * 1080 < (int)1920 * h && (h>=1080)){
				System.out.println("如果高度比宽度大，就选择展示高度为最大，宽度适应");
				w = (int)w/h * 1080;
				h = 1080;
			}else {
				System.out.println("如果小于屏幕大小，则随意");
			}

			Image im = ic1.getImage().getScaledInstance((int) (w * (nowmu - 0.25)), (int) (h * (nowmu - 0.25)),
					Image.SCALE_DEFAULT);
			ImageIcon ic2 = new ImageIcon(im);
			getJLabel1().setIcon(ic2);
		} else {
			if (choose != flag) {
				nowmu = 1;
			}
			for (int i = 0; i < ClickPath.size(); i++) {
				if (ClickPath.get(i).getName().equals(Picturename.get(choose).getText())) {//获取图片路径
					ImageIcon ic1 = new ImageIcon(ClickPath.get(i).getAbsolutePath());
//					double w = ic1.getIconWidth();
//					double h = ic1.getIconHeight();
					// 修改lmp
					double w = ic1.getIconWidth();
					double h = ic1.getIconHeight();
					System.out.println("正常显示lmp");
					if ((int)w * 1080 > (int)1920 * h && (w>=1920)){
						System.out.println("如果宽度比高度大，就选择展示宽度为最大，高度适应");
						h = h/w * 1920;
						w = 1920;

					}else if((int)w * 1080 < (int)1920 * h && (h>=1080)){
						System.out.println("如果高度比宽度大，就选择展示高度为最大，宽度适应");
						w = (int)w/h * 1080;
						h = 1080;
					}else {
						System.out.println("如果小于屏幕大小，则随意");
					}
					Image im = ic1.getImage().getScaledInstance((int) (w * (nowmu - 0.25)), (int) (h * (nowmu - 0.25)),
							Image.SCALE_DEFAULT);
					ImageIcon ic2 = new ImageIcon(im);
					getJLabel1().setIcon(ic2);
				}
			}
		}
		nowmu = nowmu - 0.25;
		flag = choose;
	}

	

	private void init() {//初始化所有页面参数
		jToolBar1 = new javax.swing.JToolBar();
		jButton1 = new javax.swing.JButton();
		jButton2 = new javax.swing.JButton();
		jButton3 = new javax.swing.JButton();
		jButton4 = new javax.swing.JButton();
		jButton5 = new javax.swing.JButton();
		jButton6 = new javax.swing.JButton();
		jButton8 = new javax.swing.JButton();
		jButton9 = new javax.swing.JButton();
		jButton10 = new javax.swing.JButton();
		jButton11 = new javax.swing.JButton();
		jButton12 = new javax.swing.JButton();
		jButton13 = new javax.swing.JButton();
		jButton14 = new javax.swing.JButton();
		jButton15 = new javax.swing.JButton();
		jButton16 = new javax.swing.JButton();  // 水平对称
		jScrollPane1 = new javax.swing.JScrollPane();
		jLabel1 = new javax.swing.JLabel();
        jMenu=new JMenu("菜单");
        jMenuBar=new JMenuBar();
        jMenu2=new JMenu("添加水印");

        jMenuItem2=new JMenuItem("按比例放缩");
        jMenuItem3=new JMenuItem("压缩图片");
        jMenuItem4=new JMenuItem("按长宽放缩");
        jMenuItem5=new JMenuItem("水印左侧放置");
        jMenuItem6=new JMenuItem("水印居中放置");
        jMenuItem7=new JMenuItem("水印右侧放置");
        jMenuItem8=new JMenuItem("转换图片格式");
		jToolBar1.setRollover(true);

		jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				try {
						jMenuItem2ActionPerformed(evt);
				} catch (Exception e) {
					// TODO: handle exception
				}
			
			}
		});
		jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				try {
						jMenuItem3ActionPerformed(evt);
				} catch (Exception e) {
					// TODO: handle exception
				}
			
			}
		});
		jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				try {
						jMenuItem4ActionPerformed(evt);
				} catch (Exception e) {
					// TODO: handle exception
				}
			
			}
		});
		jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				try {
						jMenuItem5ActionPerformed(evt);
				} catch (Exception e) {
					// TODO: handle exception
				}
			
			}
		});
		jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				try {
						jMenuItem6ActionPerformed(evt);
				} catch (Exception e) {
					// TODO: handle exception
				}
			
			}
		});
		jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				try {
						jMenuItem7ActionPerformed(evt);
				} catch (Exception e) {
					// TODO: handle exception
				}
			
			}
		});
		jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				try {
						jMenuItem8ActionPerformed(evt);
				} catch (Exception e) {
					// TODO: handle exception
				}
			
			}
		});
jMenu.add(jMenu2);
jMenu2.add(jMenuItem5);
jMenu2.add(jMenuItem6);
jMenu2.add(jMenuItem7);
jMenu.add(jMenuItem2);
jMenu.add(jMenuItem3);
jMenu.add(jMenuItem4);
jMenu.add(jMenuItem8);
jMenuBar.add(jMenu);
setJMenuBar(jMenuBar);
		jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Picture/2.jpg"))); // 设置上一张按键动作
		jButton1.setText("上一张");
		jButton1.setFocusable(false);
		jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		jButton1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton1ActionPerformed(evt);
			}
		});
		jToolBar1.add(jButton1);

		jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Picture/1.jpg"))); // 设置下一张按键动作
		jButton2.setText("下一张");
		jButton2.setFocusable(false);
		jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		jButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		jButton2.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton2ActionPerformed(evt);
			}
		});
		jToolBar1.add(jButton2);

		jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Picture/3.jpg"))); // 设置放大按键动作
		jButton3.setText("放大");
		jButton3.setFocusable(false);
		jButton3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		jButton3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		jButton3.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton3ActionPerformed(evt);
			}
		});
		jToolBar1.add(jButton3);

		jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Picture/4.jpg"))); // 设置缩小按键动作
		jButton4.setText("缩小");
		jButton4.setFocusable(false);
		jButton4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		jButton4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		jButton4.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton4ActionPerformed(evt);
			}
		});
		jToolBar1.add(jButton4);

		jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Picture/5.jpg"))); // 设置自动播放按键动作
		jButton5.setText("自动播放");
		jButton5.setFocusable(false);
		jButton5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		jButton5.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		jButton5.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton5ActionPerformed(evt);
			}
		});
		jToolBar1.add(jButton5);

		jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Picture/6.jpg"))); // 设置暂停播放按键动作
		jButton6.setText("暂停播放");
		jButton6.setFocusable(false);
		jButton6.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		jButton6.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		jButton6.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton6ActionPerformed(evt);
			}
		});
		jToolBar1.add(jButton6);


		jButton15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Picture/顺旋转.jpg"))); // 设置暂停播放按键动作
		jButton15.setText("旋转");
		jButton15.setFocusable(false);
		jButton15.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		jButton15.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		jButton15.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				try {
						jButton15AtionPerformed(evt);
				} catch (Exception e) {
					// TODO: handle exception
				}
			
			}
		});
		jToolBar1.add(jButton15);

		jButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Picture/EmbossFilter.jpg"))); // 设置滤镜按键动作
		jButton8.setText("Emboss滤镜");
		jButton8.setFocusable(false);
		jButton8.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		jButton8.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		jButton8.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				try {
					jButton8ActionPerformed(evt);
				} catch (IOException e) {
				
					e.printStackTrace();
				}
			}
		});
		jToolBar1.add(jButton8);

		jButton9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Picture/RaysFilter.jpg"))); // 设置滤镜按键动作
		jButton9.setText("Rays滤镜");
		jButton9.setFocusable(false);
		jButton9.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		jButton9.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		jButton9.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				try {
					jButton9ActionPerformed(evt);
				} catch (IOException e) {
					
					e.printStackTrace();
				}
			}
		});
		jToolBar1.add(jButton9);

		jButton10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Picture/SolarizeFilter.jpg"))); //设置滤镜按键动作
		jButton10.setText("Solarize滤镜");
		jButton10.setFocusable(false);
		jButton10.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		jButton10.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		jButton10.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				try {
					jButton10ActionPerformed(evt);
				} catch (IOException e) {
					
					e.printStackTrace();
				}
			}
		});
		jToolBar1.add(jButton10);

		jButton11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Picture/CrystallizeFilter.jpg"))); //设置滤镜按键动作
		jButton11.setText("Crystallize滤镜");
		jButton11.setFocusable(false);
		jButton11.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		jButton11.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		jButton11.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				try {
					jButton11ActionPerformed(evt);
				} catch (IOException e) {
				
					e.printStackTrace();
				}
			}
		});
		jToolBar1.add(jButton11);

		jButton12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Picture/MirrorFilter.jpg"))); //设置滤镜按键动作
		jButton12.setText("Mirror滤镜");
		jButton12.setFocusable(false);
		jButton12.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		jButton12.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		jButton12.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				try {
					jButton12ActionPerformed(evt);
				} catch (IOException e) {
				
					e.printStackTrace();
				}
			}
		});
		jToolBar1.add(jButton12);




		jButton13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Picture/LookupFilter.jpg"))); // 设置滤镜按键动作
		jButton13.setText("Lookup滤镜");
		jButton13.setFocusable(false);
		jButton13.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		jButton13.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		jButton13.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				try {
					jButton13ActionPerformed(evt);
				} catch (IOException e) {
				
					e.printStackTrace();
				}
			}
		});
		jToolBar1.add(jButton13);


		// 水平对称
		jButton16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Picture/对称.png"))); // 设置滤镜按键动作
		jButton16.setText("水平对称");
		jButton16.setFocusable(false);
		jButton16.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		jButton16.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		jButton16.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				try {
					jButton16ActionPerformed(evt);
				} catch (IOException e) {

					e.printStackTrace();
				}
			}
		});
		jToolBar1.add(jButton16);


		jScrollPane1.setViewportView(jLabel1);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 850,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addContainerGap(278, Short.MAX_VALUE))
				.addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 660, Short.MAX_VALUE));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 480, Short.MAX_VALUE)));

		pack();
	}

	private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//设置4号按钮功能为缩小
		Decrease();
	}

	private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//设置1号按钮功能为上一张
		Back();
	}

	private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//设置2号按钮功能为下一张
		Forward();
	}

	private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//设置3号按钮功能为放大
		Enlarge();
	}

	private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//设置5号按钮功能为开始播放
		jButton6.setEnabled(true);
		jButton2.setEnabled(false);
		getJButton1().setEnabled(false);
		jButton3.setEnabled(false);
		jButton4.setEnabled(false);
		jButton5.setEnabled(false);
		play = new AutoPlay(this);
		play.start();
	}

	private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//设置6号按钮功能为停止播放
		play.stop();
		  getJButton1().setEnabled(true);
		  jButton3.setEnabled(true);
		  jButton4.setEnabled(true);
		  jButton5.setEnabled(true);
		  if (choose == picturenumber) {
		  jButton2.setEnabled(false);}
		  else jButton2.setEnabled(true);
	}

	

	private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) throws IOException {//设置8号按钮功能为emboss滤镜
		Filter filter=new Filter(choose, OpenFilePath, ClickPath, Picturename, this);
	Image image=filter.InitImage();
		filter.EmbossFilter(evt,image);
		

	}

	private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) throws IOException {//设置9号按钮功能为rays滤镜
		
		Filter filter=new Filter(choose, OpenFilePath, ClickPath, Picturename, this);
	Image image=filter.InitImage();
		filter.RaysFilter(evt, image);
	}

	private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) throws IOException {//设置10号按钮功能为solarize滤镜
		Filter filter=new Filter(choose, OpenFilePath, ClickPath, Picturename, this);
	Image image=filter.InitImage();
		filter.SolarizeFilter(evt, image);
	}

	private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) throws IOException {//设置11号按钮功能为crystallize滤镜
		Filter filter=new Filter(choose, OpenFilePath, ClickPath, Picturename, this);
	Image image=filter.InitImage();
		filter.CrystallizeFilter(evt, image);
	}

	private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) throws IOException {//设置12号按钮功能为mirror滤镜
		Filter filter=new Filter(choose, OpenFilePath, ClickPath, Picturename, this);
	Image image=filter.InitImage();
		filter.MirrorFilter(evt, image);
	}

	private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) throws IOException {//设置13号按钮功能为lookup滤镜
		Filter filter=new Filter(choose, OpenFilePath, ClickPath, Picturename, this);
	Image image=filter.InitImage();
		filter.LookupFilter(evt, image);
	}
	// 水平翻转对称
	private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) throws IOException {//设置16号按钮功能为翻转
		Filter filter=new Filter(choose, OpenFilePath, ClickPath, Picturename, this);
		Image image=filter.InitImage();
		filter.TurnFilter(evt, image);
	}

	private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) throws IOException {// GEN-FIRST:event_jButton3ActionPerformed
System.out.println("进入了");
		ChangeSize changeSize=new ChangeSize(choose, OpenFilePath, ClickPath, Picturename, this);
changeSize.InitImage();
changeSize.proportion(evt, changeSize.InitImage());
		
	}
	private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) throws IOException {// GEN-FIRST:event_jButton3ActionPerformed
Compress changeSize=new Compress(choose, OpenFilePath, ClickPath, Picturename, this);
changeSize.InitImage();
changeSize.proportion(evt, changeSize.InitImage());
		
	}
	private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) throws IOException {// GEN-FIRST:event_jButton3ActionPerformed
		ImageFormat changeSize=new ImageFormat(choose, OpenFilePath, ClickPath, Picturename, this);
changeSize.InitImage();
changeSize.proportion(evt, changeSize.InitImage());

	}
	private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) throws IOException {// GEN-FIRST:event_jButton3ActionPerformed
ChangeSize changeSize=new ChangeSize(choose, OpenFilePath, ClickPath, Picturename, this);
changeSize.InitImage();
changeSize.high(evt, changeSize.InitImage());

	}
	private void jButton15AtionPerformed(java.awt.event.ActionEvent evt) throws IOException {//设置13号按钮功能为lookup滤镜
       Rotate rotate=new Rotate(choose, OpenFilePath, ClickPath, Picturename, this);
       rotate.InitImage();
          rotate.Rotating(evt, rotate.InitImage());

	}

	
	private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) throws IOException {// GEN-FIRST:event_jButton3ActionPerformed
  WaterMark waterMark=new WaterMark(choose, OpenFilePath, ClickPath, Picturename, this,Positions.BOTTOM_LEFT);
	
		waterMark.choosewatermark(evt, waterMark.InitImage());
		
		
	}
	private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) throws IOException {// GEN-FIRST:event_jButton3ActionPerformed
		  WaterMark waterMark=new WaterMark(choose, OpenFilePath, ClickPath, Picturename, this,Positions.BOTTOM_RIGHT);
			
				waterMark.choosewatermark(evt, waterMark.InitImage());
				
				
			}
	private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) throws IOException {// GEN-FIRST:event_jButton3ActionPerformed
		  WaterMark waterMark=new WaterMark(choose, OpenFilePath, ClickPath, Picturename, this,Positions.BOTTOM_CENTER);
			
				waterMark.choosewatermark(evt, waterMark.InitImage());
				
				
			}

	private javax.swing.JButton jButton1;
	private javax.swing.JButton jButton2;
	private javax.swing.JButton jButton3;
	private javax.swing.JButton jButton4;
	private javax.swing.JButton jButton5;
	private javax.swing.JButton jButton6;
	private javax.swing.JButton jButton8;
	private javax.swing.JButton jButton9;
	private javax.swing.JButton jButton10;
	private javax.swing.JButton jButton11;
	private javax.swing.JButton jButton12;
	private javax.swing.JButton jButton13;
	private javax.swing.JButton jButton14;
	private javax.swing.JButton jButton15;
	private javax.swing.JButton jButton16; // 水平翻转对称

	private javax.swing.JLabel jLabel1;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JToolBar jToolBar1;
	private javax.swing.JMenu jMenu;
	JMenu jMenu2;
	
	private javax.swing.JMenuItem jMenuItem8;
	private javax.swing.JMenuItem jMenuItem2;
	private javax.swing.JMenuItem jMenuItem3;
	private javax.swing.JMenuItem jMenuItem4;
	private javax.swing.JMenuItem jMenuItem5;
	private javax.swing.JMenuItem jMenuItem6;
	private javax.swing.JMenuItem jMenuItem7;
	private javax.swing.JMenuBar jMenuBar;
	public javax.swing.JLabel getJLabel1() {
		return jLabel1;
	}

	public void setJLabel1(javax.swing.JLabel jLabel1) {
		this.jLabel1 = jLabel1;
	}

	public javax.swing.JButton getJButton1() {
		return jButton1;

	}
	public javax.swing.JButton getJButton6() {
		  return jButton6;

		 }
}

