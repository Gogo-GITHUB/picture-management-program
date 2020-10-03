package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;


import mainuiutil.*;

public class MainUi extends javax.swing.JFrame {
	 public ArrayList<File> ImageFiles = new ArrayList<File>();
	 public	ArrayList<JPanel> SmallPanels = new ArrayList<JPanel>(); // 这里定义N个小的面板，上面存放标签，下面用于存放文件名的文本框
	 public ArrayList<JLabel> SmallLabels = new ArrayList<JLabel>(); // 这里定义N个小的标签，用来存放读取的图片
	 public	ArrayList<JTextField> SmallTextFields = new ArrayList<JTextField>(); // 这里定义N个文本框，用来显示与图片相对应的文件的名称

	JTextField ImageNum = new JTextField();
	JScrollPane BigScrollPane; // 这里定义一个滚动条，把大的面板放在滚动条里
	ArrayList<File> ClickedFilePath = new ArrayList<File>(); // 这里定义了一个，鼠标点击的文件路径下的所有文件
	int ImagesQuantity; // 这里定义的是图片的总数
	int SelectImage = -1; // 这里定义的是选择的图片，0为第一张，-1是未选择
	ArrayList<Integer> SelecctImages = new ArrayList<Integer>();
	JFrame IntroduceFrame = new JFrame(); // 帮助里的关于介绍软件作者而弹出的面板
	JTextArea IntroduceTextArea = new JTextArea(); // 同上，介绍软件作者的文本域，被加在了面板上
	JPopupMenu PopupMenu = new JPopupMenu(); // 右键单击文件时弹出的弹出式菜单
	JMenuItem Copy = new JMenuItem(" 复制 "); // 菜单中的复制选项
	JMenuItem Delete = new JMenuItem(" 删除 "); // 菜单中的删除选项
	JMenuItem Cut = new JMenuItem(" 剪切 "); // 菜单中的剪切选项
	JMenuItem Copy2 = new JMenuItem(" 复制 "); // 菜单中的复制选项
	JMenuItem Delete2 = new JMenuItem(" 删除 "); // 菜单中的删除选项
	JMenuItem Cut2 = new JMenuItem(" 剪切 "); // 菜单中的剪切选项
	JMenuItem Paste2 = new JMenuItem("粘贴");
	JMenuItem Rename = new JMenuItem(" 重命名 "); // 菜单中的重命名选项
	JMenuItem ImageMessage = new JMenuItem(" 图片属性 "); // 菜单中的图片属性选项
	 public JPanel ImagePanel = new JPanel();
	String FilePath;
	MouseEvent E;
	File TemporaryFile;
	ImageIcon TemporaryIcon;
	String OldName;
	JPopupMenu OutPopupMenu = new JPopupMenu();
	JMenuItem Refresh = new JMenuItem("刷新");
	JMenuItem Paste = new JMenuItem("粘贴");
	JMenuItem BatchRename = new JMenuItem("重命名");
	ArrayList<BufferedInputStream> SourceFile = new ArrayList<BufferedInputStream>();
	ArrayList<FileOutputStream> NewFile = new ArrayList<FileOutputStream>();
	ArrayList<TreePath> TreePaths = new ArrayList<TreePath>();
	String SourceFileName = null;
	ArrayList<String> SourceFileNames = new ArrayList<String>();
	int CopyNum = 0;
	int PasteNumber = 0;
	String CopyName;
	String CopyPath;
	ArrayList<String> CopyNames = new ArrayList<String>();
	ArrayList<String> CopyPaths = new ArrayList<String>();
	int CutFlag = 0;
	Point pressPoint = null, releasePoint = null;
	ArrayList<Point> points = new ArrayList<Point>();
	Rectangle rect;
	Point pBegin;
	ArrayList<Integer> SelecctImagesLock = new ArrayList<Integer>();
	double x1, y1, x2, y2;
	int StartNum, NumNum;
	boolean multiChoice = false;

	/** 创建主界面 */
	public MainUi() {
		initComponents();
	}

	/* 初始化介绍软件作者的面板和文本域 */
	public void InitIntroduction() {
		IntroduceFrame.setVisible(false);
		IntroduceFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);// 点叉的时候只是隐藏面板
		IntroduceFrame.setSize(220, 130);
		IntroduceFrame.setLocationRelativeTo(null); // 弹出面板时在屏幕的中央
		IntroduceFrame.add(IntroduceTextArea);
		IntroduceTextArea.setEditable(false); // 文本设为不可编辑
	}
	public static void setUIFont()
	{
		
	       Font font = new Font("Dialog",Font.PLAIN,13);
	        java.util.Enumeration keys = UIManager.getDefaults().keys();
	        while (keys.hasMoreElements()) {
	            Object key = keys.nextElement();
	            Object value = UIManager.get(key);
	            if (value instanceof javax.swing.plaf.FontUIResource) {
	                UIManager.put(key, font);
	            }
	        }
	}
	/* 初始化一些必要的数据 */
	public void Init() {

		InitIntroduction();
		BigScrollPane = new JScrollPane(ImagePanel); // 滚动面板里加上显示用于显示图像的大面板
		ImagePanel.setLayout(null); // 图像面板的布局设为null（这点非常重要）
		jTabbedPane1.add(BigScrollPane); // 在标签化窗口中加入已有图像面板的滚动面板
		PopupMenu.add(Copy);
		// 弹出式窗口中加入复制菜单项
		// jPopupMenu.addSeparator(); // 往菜单中加横线
		/*
		 * PopupMenu.add(Cut); // 弹出式窗口中加入剪切菜单项
		 */ PopupMenu.add(Delete); // 弹出式窗口中加入删除菜单项
		PopupMenu.add(Rename); // 弹出式窗口中加入重命名菜单项
		PopupMenu.add(ImageMessage); // 弹出式窗口中加入属性菜单项

		/* PopupMenu.add(Paste); */
		OutPopupMenu.add(Refresh);
		OutPopupMenu.add(Copy2);
		OutPopupMenu.add(Paste2);
	
		OutPopupMenu.add(Delete2);
		OutPopupMenu.add(BatchRename);
		jComboBox1.addPopupMenuListener(new PopupMenuListener() {
//弹出菜单监听器
			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
			}

			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
				jTree1.setSelectionPath(TreePaths.get(jComboBox1.getSelectedIndex()));// 选择由指定路径标识的节点
				ShowImages(E, TreePaths.get(jComboBox1.getSelectedIndex()), 1);
			}

			public void popupMenuCanceled(PopupMenuEvent e) {
				System.out.println("我是3");
			}
		});

		ImagePanel.addMouseListener(new java.awt.event.MouseAdapter() {
//图形面板点击
			public void mouseReleased(MouseEvent evt) {
				OutPopupMenu(evt);
			}
		});

		BatchRename.addActionListener(new java.awt.event.ActionListener() {
//批处理监听器
			public void actionPerformed(ActionEvent e) {
				BatchRename();
			}
		});

		Delete.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(java.awt.event.ActionEvent evt) {
				Delete();
				ShowImages(E, new TreePath(0), 0);
			}
		});

		// 查看图片的属性
		ImageMessage.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(java.awt.event.ActionEvent evt) {

//				double h1 = imageicon.getIconHeight();
//				double w1 = imageicon.getIconWidth();

//				System.out.println(FilePath);
//				System.out.println(CopyNames);
//				System.out.println(ClickedFilePath);
//				System.out.println(SelectImage);
				System.out.println("-----------------------------------");

				for (int t = 0; t < ClickedFilePath.size(); t++) {

//							if (ClickedFilePath.get(t).getName().equals(SmallTextFields.get(SelectImage).getText())) {// 获取点击的图片
//								System.out.println("点击的图片" + ClickedFilePath.get(t).getAbsolutePath());
//								ImageIcon TemporaryIcon = new ImageIcon(ClickedFilePath.get(t).getAbsolutePath());
//								Image TemporaryImage = TemporaryIcon.getImage().getScaledInstance(
//										TemporaryIcon.getIconWidth(), TemporaryIcon.getIconHeight(),
//										Image.SCALE_DEFAULT);
//									ic2 = new ImageIcon(TemporaryImage);
//							}
					// test lmp
					if (ClickedFilePath.get(t).getName().equals(SmallTextFields.get(SelectImage).getText())) {// 获取点击的图片
						System.out.println("点击的图片" + ClickedFilePath.get(t).getAbsolutePath());
						String s = ClickedFilePath.get(t).getAbsolutePath();
						System.out.println("-----------------------------------");
						System.out.println(s);
						File picture = new File(String.valueOf(ClickedFilePath.get(t).getAbsolutePath()));
						ImageIcon img = new ImageIcon(String.valueOf(ClickedFilePath.get(t).getAbsolutePath()));
						System.out.println(picture.length());
						System.out.println(String.format("%.1f",picture.length()/1024.0));// 源图大小
						double imageSize = picture.length()/1024.0;
						double h = img.getIconHeight();
						double w = img.getIconWidth();
						IntroduceFrame.setVisible(true);
						IntroduceTextArea.setText("\n      图片大小:"+(int)imageSize+"KB\n" + "      宽像素:"+(int)w+"\n" + "      高像素:"+(int)h+"\n");
					}
				}

			}
		});


		Delete2.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(java.awt.event.ActionEvent evt) {
				Delete();
				ShowImages(E, new TreePath(0), 0);
			}
		});
		Rename.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(java.awt.event.ActionEvent evt) {
				if(SelecctImages.size()>1)
				{
					BatchRename();
				}else {Rename();}
				
			}
		});

		Copy.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(java.awt.event.ActionEvent evt) {
				try {
					Copythree();
					SelecctImages.clear();
				} catch (IOException ex) {
					Logger.getLogger(MainUi.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		});
		Copy2.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(java.awt.event.ActionEvent evt) {
				try {
					Copythree();
					SelecctImages.clear();
				} catch (IOException ex) {
					Logger.getLogger(MainUi.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		});

		Refresh.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(java.awt.event.ActionEvent evt) {
				ShowImages(E, new TreePath(0), 0);
			}
		});

		Paste.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(java.awt.event.ActionEvent evt) {
				try {
					/*
					 * if(CutFlag==0) { for(int i=0;i<SelecctImagesLock.size();i++) {
					 * System.out.println(CopyNames.get(i)); System.out.println(CopyPaths.get(i));
					 * Copy(CopyNames.get(i), CopyPaths.get(i)); Paste(); ShowImages(E, new
					 * TreePath(0), 0);
					 * 
					 * }} else {
					 */
					for (int i = 0; i < SelecctImagesLock.size(); i++) {
						System.out.println("hokkk");
						System.out.println(CopyNames.get(i));
						System.out.println(CopyPaths.get(i));
						Copy(CopyNames.get(i), CopyPaths.get(i));
						Paste();
						ShowImages(E, new TreePath(0), 0);

					}
					CopyNames.clear();
					CopyPaths.clear();
					SelecctImagesLock.clear();

				} catch (FileNotFoundException ex) {
					Logger.getLogger(MainUi.class.getName()).log(Level.SEVERE, null, ex);
				} catch (IOException ex) {
					Logger.getLogger(MainUi.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		});
		Paste2.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(java.awt.event.ActionEvent evt) {
				try {
					/*
					 * if(CutFlag==0) { for(int i=0;i<SelecctImagesLock.size();i++) {
					 * System.out.println(CopyNames.get(i)); System.out.println(CopyPaths.get(i));
					 * Copy(CopyNames.get(i), CopyPaths.get(i)); Paste(); ShowImages(E, new
					 * TreePath(0), 0);
					 * 
					 * }} else {
					 */for (int i = 0; i < SelecctImagesLock.size(); i++) {
						System.out.println("hokkk");
						System.out.println(CopyNames.get(i));
						System.out.println(CopyPaths.get(i));
						Copy(CopyNames.get(i), CopyPaths.get(i));
						Paste();
						ShowImages(E, new TreePath(0), 0);

					}
					CopyNames.clear();
					CopyPaths.clear();
					SelecctImagesLock.clear();

				} catch (FileNotFoundException ex) {
					Logger.getLogger(MainUi.class.getName()).log(Level.SEVERE, null, ex);
				} catch (IOException ex) {
					Logger.getLogger(MainUi.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		});
	}

	public void in() {
		InUtil kin = new InUtil(ClickedFilePath, SelecctImages, SmallPanels, SmallLabels, SmallTextFields, SelectImage,
				ImagesQuantity);
		kin.work();
	}

	public void Back() {
		jTree1.setSelectionPath(TreePaths.get(jComboBox1.getSelectedIndex() - 1));
		ShowImages(E, TreePaths.get(jComboBox1.getSelectedIndex() - 1), 1);
		jScrollPane1.getVerticalScrollBar().setValue((int) (jTree1.getRowHeight() * jTree1.getMaxSelectionRow()));// 设置滚动条的值
	}

	public void Next() {
		jTree1.setSelectionPath(TreePaths.get(jComboBox1.getSelectedIndex() + 1));
		ShowImages(E, TreePaths.get(jComboBox1.getSelectedIndex() + 1), 1);
		jScrollPane1.getVerticalScrollBar().setValue((int) (jTree1.getRowHeight() * jTree1.getMaxSelectionRow()));
	}

	public void Up() {
		if (jTree1.getSelectionPath().getParentPath() != null) {
			jTree1.setSelectionPath(jTree1.getSelectionPath().getParentPath());// 返回父节点
			System.out.println(jTree1.getMaxSelectionRow());
			for (int i = jTree1.getRowCount(); i >= jTree1.getMaxSelectionRow(); i--) {// 返回最大的选定行
				jTree1.collapseRow(i);// 指定行中的节点折叠
			}
		}
	}
public void screencapture() throws IOException {//设置截图参数

	    ScreenCapture capture = ScreenCapture.getInstance();
	    JFrame frame = new JFrame();
	    JPanel panel = new JPanel();
	    panel.setLayout(new BorderLayout());
	    JLabel imagebox = new JLabel();
	    panel.add(BorderLayout.CENTER, imagebox);
	    capture.captureImage();
	    imagebox.setIcon(capture.getPickedIcon());
		frame.addWindowListener(new WindowAdapter() {
		public void windowClosing(WindowEvent e) {

			int n = JOptionPane.showConfirmDialog(null, "是否保存", "标题", JOptionPane.YES_NO_OPTION);//弹出窗口，判断是否保存
			if (n == 0) {
				try {
					JFileChooser fileChooser;			
					{				fileChooser = new JFileChooser();// 创建文件选择对话框	
					fileChooser.setSelectedFile(new File("截图.jpg"));			
					FileNameExtensionFilter filter = new FileNameExtensionFilter("图像文件（JPG/GIF）", "JPG", "JPEG", "GIF");// 设置文件过滤器，只列出JPG或GIF格式的图片				
					fileChooser.setFileFilter(filter);			}
					
			        // 打开文件选择框（线程将被阻塞, 直到选择框被关闭）
			        int result = fileChooser.showSaveDialog(null);

					ImageIO.write(capture.getPickedImage(), "jpg", fileChooser.getSelectedFile());//写入文件
				} catch (Exception e2) {
				}
			}  
		}
	});
	    
	    frame.setLocationRelativeTo(null);
		frame.setSize(capture.getPickedImage().getWidth()+200, capture.getPickedImage().getHeight()+200);
		frame.setContentPane(panel);
	    frame.show();
	}

	public void BatchRename() {
		// ImagePanel.removeAll();
		BatchRenameUtil batchRenameUtil = new BatchRenameUtil(FilePath, SmallLabels, ClickedFilePath, SelecctImages,
				SmallTextFields);
		batchRenameUtil.work();
		ShowImages(E, new TreePath(0), 0);
	}

	public void Paste() throws FileNotFoundException {
		PasteUtil pasteUtil=new PasteUtil(SourceFileName, SmallTextFields, NewFile, FilePath, CopyName, CopyPath, SourceFile, PasteNumber, CopyNum);
		pasteUtil.Work();
		PasteNumber=pasteUtil.getPasteNumber();
		CopyNum=pasteUtil.getCopyNum();
	}

	public void CopyTwo() throws IOException {// 非剪切的复制
		CopyUtil copyUtil = new CopyUtil(CutFlag, CopyNum, SelecctImages, CopyNames, CopyPaths, SelecctImagesLock,
				SmallTextFields, FilePath, CopyName, CopyPath, SourceFileName, SourceFile);
		copyUtil.CopyInPaste();
		CutFlag = copyUtil.getCutFlag();
		CopyNum = copyUtil.getCopyNum();

	}

	public void Copythree() throws IOException {

		CopyUtil copyUtil = new CopyUtil(CutFlag, CopyNum, SelecctImages, CopyNames, CopyPaths, SelecctImagesLock,
				SmallTextFields, FilePath, CopyName, CopyPath, SourceFileName, SourceFile);
		copyUtil.Save();

	}


	public void Copy(String ssg, String fp) throws IOException {
		



		
		  CutFlag = 0;// 非剪切
		  if (CopyNum != 0) { try { SourceFile.get(CopyNum -
		  1).close(); } catch (IOException ex) { } } CopyName = ssg; CopyPath = fp;// 记录路径和名字
		  try { SourceFile.add(new BufferedInputStream(new FileInputStream(fp +
		  File.separator + ssg)));
		  
		  SourceFileName = ssg; } catch (FileNotFoundException e) {
		  e.printStackTrace(); } CopyNum++;
		 



	}



	public void OutPopupMenu(MouseEvent evt) {
		if (evt.isPopupTrigger()) { // 判断鼠标的点击是否为右键的点击
			OutPopupMenu.show(evt.getComponent(), evt.getX(), evt.getY()); // 弹出式菜单在此时弹出，并设置好了其弹出的位置
		}
	}

	public void Delete() {
		DeleteUtil deleteUtil = new DeleteUtil(SelectImage, ImagesQuantity, FilePath, SmallLabels, SmallPanels,
				SelecctImages, SmallTextFields);
		deleteUtil.Work();
		ImagesQuantity = deleteUtil.getImagesQuantity();
		SelectImage = deleteUtil.getSelectImage();

	}

	public void Rename() {

		mainuiutil.RenameUtil re = new mainuiutil.RenameUtil(ClickedFilePath, SelecctImages, SmallPanels, SmallLabels,
				SmallTextFields, TemporaryFile, TemporaryIcon, SelectImage, OldName, FilePath);
		re.Work();
	}

	public void RenameText() throws IOException {
		File filetwo = new File(FilePath + File.separator + SmallTextFields.get(SelectImage).getText());// 读入新名字

		if (TemporaryFile.renameTo(filetwo)) {// 重命名
			JOptionPane.showMessageDialog(null, "重命名操作成功!!!", "重命名", JOptionPane.INFORMATION_MESSAGE);

		} else {
			JOptionPane.showMessageDialog(null, "文件被其他程序占用，重命名操作失败", "重命名", JOptionPane.INFORMATION_MESSAGE);

		}
		SmallTextFields.get(SelectImage).setBackground(null);
		SmallTextFields.get(SelectImage).setEditable(false);
		SmallLabels.get(SelectImage).setIcon(GetImageIcon(new ImageIcon(filetwo.getAbsolutePath())));// 显示新名字
		SmallLabels.get(SelectImage).setBackground(new java.awt.Color(244, 244, 244));
	}

	public ImageIcon GetImageIcon(ImageIcon imageicon) {// 改变图片大小
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

	public void PopupMenu(MouseEvent evt) {
		if (evt.isPopupTrigger()) { // 判断鼠标的点击是否为右键的点击

			JLabel SelectLabel = new JLabel(); // 定义一个临时的标签
			SelectLabel = (JLabel) evt.getSource(); // 让它等于所点击的标签
			PopupMenu.show(evt.getComponent(), evt.getX(), evt.getY()); // 弹出式菜单在此时弹出，并设置好了其弹出的位置

			/*
			 * for (int b = 0; b < SmallLabels.size(); b++) {
			 * SmallLabels.get(b).setBackground(new java.awt.Color(48, 48, 48)); } ////
			 * 刷新图片标签的背景
			 */
			JLabel CurrentLabel = new JLabel();
			CurrentLabel = (JLabel) evt.getSource();
			CurrentLabel.setBackground(new java.awt.Color(194, 194, 194));// 点击时发生背景变化
			for (int y = 0; y < SmallLabels.size(); y++) {
				if (SmallLabels.get(y).getDisplayedMnemonic() == CurrentLabel.getDisplayedMnemonic()) {
					SelectImage = y;// 获得当前点击图标的标号
				}
			}
		}
	}


	public void InitLabelListener() {// 创建标签监听器
		for (int i = 0; i < SmallLabels.size(); i++) {
			SmallLabels.get(i).setBorder(null);// 设置边框

			SmallLabels.get(i).addMouseListener(new java.awt.event.MouseAdapter() {

				public void mouseReleased(MouseEvent evt) {
					for (int i = 0; i < SelecctImages.size(); i++) {
						SmallLabels.get(SelecctImages.get(i)).setBackground(new java.awt.Color(194, 194, 194));
						System.out.println(SelecctImages.get(i));
					}
					PopupMenu(evt); // 弹出菜单
				}

				public void mousePressed(java.awt.event.MouseEvent evt) {
					if (!evt.isControlDown()) {
						if (evt.getButton() == evt.BUTTON1) {
							SelecctImages.clear();
							for (int b = 0; b < SmallLabels.size(); b++) {
								SmallLabels.get(b).setBackground(new java.awt.Color(48, 48, 48));// 刷新图片标签的背景
							SmallTextFields.get(b).setBackground(new Color(48,48,48));
							SmallTextFields.get(b).setEditable(false);
							}
							System.out.println("ccccc");
						}
						ImageNum.setText("共"+SmallLabels.size()+"张,"+"选中图片数目：" + SelecctImages.size());
					}
					JLabel CurrentLabel = new JLabel();
					CurrentLabel = (JLabel) evt.getSource();// 获取发生事件的label
					CurrentLabel.setBackground(new java.awt.Color(194, 194, 194));// 选中时背景改变
					int clickTimes = evt.getClickCount();
					if (evt.getButton() == evt.BUTTON1) {
						for (int y = 0; y < SmallLabels.size(); y++) {
							if (SmallLabels.get(y).getDisplayedMnemonic() == CurrentLabel.getDisplayedMnemonic()) {
								SelectImage = y;
								SelecctImages.add(y);
							}
						} // 获取选中label的标号
						ImageNum.setText("共"+SmallLabels.size()+"张,"+"选中图片数目：" + SelecctImages.size());
					}
					if (evt.getButton() == evt.BUTTON3&&SelecctImages.size()==0) {
						for (int y = 0; y < SmallLabels.size(); y++) {
							if (SmallLabels.get(y).getDisplayedMnemonic() == CurrentLabel.getDisplayedMnemonic()) {
								SelectImage = y;
								SelecctImages.add(y);
							}
						} // 获取选中label的标号
						ImageNum.setText("共"+SmallLabels.size()+"张,"+"选中图片数目：" + SelecctImages.size());
					}
					
					
					
					if (clickTimes == 2) {// 双击
						ImageIcon ic2 = null;

						for (int t = 0; t < ClickedFilePath.size(); t++) {

//							if (ClickedFilePath.get(t).getName().equals(SmallTextFields.get(SelectImage).getText())) {// 获取点击的图片
//								System.out.println("点击的图片" + ClickedFilePath.get(t).getAbsolutePath());
//								ImageIcon TemporaryIcon = new ImageIcon(ClickedFilePath.get(t).getAbsolutePath());
//								Image TemporaryImage = TemporaryIcon.getImage().getScaledInstance(
//										TemporaryIcon.getIconWidth(), TemporaryIcon.getIconHeight(),
//										Image.SCALE_DEFAULT);
//									ic2 = new ImageIcon(TemporaryImage);
//							}
							// test lmp
							if (ClickedFilePath.get(t).getName().equals(SmallTextFields.get(SelectImage).getText())) {// 获取点击的图片
								System.out.println("点击的图片" + ClickedFilePath.get(t).getAbsolutePath());
								ImageIcon TemporaryIcon = new ImageIcon(ClickedFilePath.get(t).getAbsolutePath());
								System.out.println(TemporaryIcon.getIconHeight()+ "高");
								System.out.println(TemporaryIcon.getIconWidth() + "宽");
								double w = TemporaryIcon.getIconWidth();
								double h = TemporaryIcon.getIconHeight();
								// lmp增加修改
								// 初始展示应该小于等于屏幕的大小， 增加判断
								// 不应该超界限
//								Image TemporaryImage = TemporaryIcon.getImage().getScaledInstance(
//										TemporaryIcon.getIconWidth(), TemporaryIcon.getIconHeight(),
//										Image.SCALE_DEFAULT);
//								ic2 = new ImageIcon(TemporaryImage);
								if ((int)w * 1080 > (int)1920 * h && (w>=1920)){
									System.out.println("如果宽度比高度大，就选择展示宽度为最大，高度适应");
									h = h/w * 1920;
									w = 1920;

									Image TemporaryImage = TemporaryIcon.getImage().getScaledInstance(
											(int)w, (int)h,
											Image.SCALE_DEFAULT);
									ic2 = new ImageIcon(TemporaryImage);
								}else if((int)w * 1080 < (int)1920 * h && (h>=1080)){
									System.out.println("如果高度比宽度大，就选择展示高度为最大，宽度适应");
									System.out.println("b");
									w = w/h * 1080;
									Image TemporaryImage = TemporaryIcon.getImage().getScaledInstance(
											(int)w, 1080,
											Image.SCALE_DEFAULT);
									ic2 = new ImageIcon(TemporaryImage);
								}else {
									System.out.println("如果小于屏幕大小，则随意");
									Image TemporaryImage = TemporaryIcon.getImage().getScaledInstance(
										TemporaryIcon.getIconWidth(), TemporaryIcon.getIconHeight(),
										Image.SCALE_DEFAULT);
									ic2 = new ImageIcon(TemporaryImage);
								}

							}

						} // 进入详情页面
						System.out.println("aaaaa");
						ImageShowUi jj = new ImageShowUi(ClickedFilePath, SmallTextFields, SelectImage, ImagesQuantity,
								SmallLabels, "123", SmallTextFields, SmallPanels);

						jj.setVisible(true);
						jj.getJLabel1().setIcon(ic2);
						jj.getJLabel1().setHorizontalAlignment(SwingConstants.CENTER);
					}
				}
			});
		}
	}

	public void InitMouseListener() {// 创建面板监听器
		ImagePanel.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent evt) {
				Graphics2D g2 = (Graphics2D) ImagePanel.getGraphics();
				// 启用XOR模式
				g2.setXORMode(Color.white);
				// 绘制之前的矩形
				if (rect != null) {
					g2.draw(rect);
				}
				// 绘制现在的矩形
				rect = new Rectangle((int) Math.min(pBegin.getX(), evt.getPoint().getX()),
						(int) Math.min(pBegin.getY(), evt.getPoint().getY()),
						(int) Math.abs(pBegin.getX() - evt.getPoint().getX()),
						(int) Math.abs(pBegin.getY() - evt.getPoint().getY()));
				g2.draw(rect);
				g2.setPaintMode();
				g2.dispose();
			}
		});
		ImagePanel.addMouseListener(new MouseAdapter() {

			public void mouseReleased(MouseEvent evt) {
				try {
					// 画选择框（实际效果为去掉）
					Graphics2D g2 = (Graphics2D) ImagePanel.getGraphics();
					g2.setXORMode(Color.white);
					g2.draw(rect);
					g2.setPaintMode();
					g2.dispose();
				} catch (Exception e) {
				}
				pBegin = null;
				rect = null;
				System.out.println("sdjiasd2");
				releasePoint = new Point(evt.getLocationOnScreen());
				if (releasePoint.getX() > pressPoint.getX()) {
					x2 = releasePoint.getX();
					x1 = pressPoint.getX();
				} else {
					x1 = releasePoint.getX();
					x2 = pressPoint.getX();
				}
				if (releasePoint.getY() > pressPoint.getY()) {
					y2 = releasePoint.getY();
					y1 = pressPoint.getY();
				} else {
					y1 = releasePoint.getY();
					y2 = pressPoint.getY();

				}
				System.out.printf("%f\n%f\n%f\n%f\n", x1, x2, y1, y2);
				for (int b = 0; b < SmallLabels.size(); b++) {
					if (SmallLabels.get(b).getLocationOnScreen().getX() >= x1
							&& SmallLabels.get(b).getLocationOnScreen().getX() <= x2
							&& SmallLabels.get(b).getLocationOnScreen().getY() >= y1
							&& SmallLabels.get(b).getLocationOnScreen().getY() <= y2) {

						SmallLabels.get(b).setBackground(new java.awt.Color(194, 194, 194));
						SelecctImages.add(b);

					}

				}
				ImageNum.setText("共"+SmallLabels.size()+"张,"+"选中图片数目：" + SelecctImages.size());
			}

			public void mousePressed(java.awt.event.MouseEvent evt) {
				if (evt.getButton() == evt.BUTTON1) {
					for (int b = 0; b < SmallLabels.size(); b++) {

						SmallLabels.get(b).setBackground(new java.awt.Color(48, 48, 48));
						SmallTextFields.get(b).setBackground(new Color(48,48,48));
						SmallTextFields.get(b).setEditable(false);
					}

					SelecctImages.clear();
					ImageNum.setText("共"+SmallLabels.size()+"张,"+"选中图片数目：" + SelecctImages.size());
				}

				pBegin = evt.getPoint();
				ImagePanel.requestFocus();

				pressPoint = new Point(evt.getLocationOnScreen());
				for (int b = 0; b < SmallLabels.size(); b++) {
					points.add(SmallLabels.get(b).getLocationOnScreen());

				}
			}
		});

	}


	public void Open() {
		// 直接打开文件
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & GIF Images", "jpg", "gif");
		chooser.setFileFilter(filter);// 文件选择
		int returnVal = chooser.showOpenDialog(this);// 弹出窗口中文件选择器的返回状态
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			SelectImage = -1;
			ImageShowUi jj = new ImageShowUi(ClickedFilePath, SmallTextFields, SelectImage, ImagesQuantity, SmallLabels,
					chooser.getSelectedFile().getAbsolutePath(), SmallTextFields, SmallPanels);
			ImageIcon ic1 = new ImageIcon(chooser.getSelectedFile().getAbsolutePath());
			Image im = ic1.getImage().getScaledInstance(ic1.getIconWidth(), ic1.getIconHeight(), Image.SCALE_DEFAULT);
			ImageIcon ic2 = new ImageIcon(im);
			jj.getJLabel1().setIcon(ic2);
			jj.setVisible(true);
			jj.getJLabel1().setHorizontalAlignment(SwingConstants.CENTER);

		}
	}

	public void RunTree(final JTree jTree1) {// 目录树创建
		File[] roots = (new PFileSystemView()).getRoots();// 创建具有默认文件夹名称的新文件夹并返回此系统上的所有根分区
		FileNode nod = null;
		nod = new FileNode(roots[0]);
		nod.explore();
		jTree1.setModel(new DefaultTreeModel(nod));// 设置将提供数据的TreeModel
		jTree1.addTreeExpansionListener(new TreeExpansionListener() {// 为 TreeExpansion事件添加侦听 TreeExpansion

			public void treeExpanded(TreeExpansionEvent event) {
				TreePath path = event.getPath();// 获取当前路径
				FileNode node = (FileNode) path.getLastPathComponent();// 返回此路径的最后一个元素
				if (!node.isExplored()) {
					DefaultTreeModel model = ((DefaultTreeModel) jTree1.getModel());
					node.explore();
					model.nodeStructureChanged(node);// 发布treeStructureChanged事件
				}
			}

			public void treeCollapsed(TreeExpansionEvent event) {
			}
		});

		jTree1.addMouseListener(new MouseAdapter() {

			public void mousePressed(MouseEvent e) {

				ShowImages(e, new TreePath(0), 0);// 图片预览
				E = e;

			}
		});
	}

	public void ShowImages(MouseEvent e, TreePath path, int FlagTree) {
		try {

			Locale systime = Locale.CHINA;
			SimpleDateFormat timeformat = new SimpleDateFormat("yyyy-MM-dd   HH:mm:ss", systime);
			String temptime = timeformat.format(new Date());// 求得本地机的系统时间;
			System.out.println("开始的时间为:" + temptime);
			SmallPanels.clear();
			SmallLabels.clear();
			SmallTextFields.clear();
			ImagePanel.removeAll();
			ImagePanel.repaint();
			ImageFiles.clear();// 清除原来的面板
			int flag = 0;
			ImagePanel.setLayout(null);// 设置布局
			String filepath = null;
			ImagesQuantity = 0;

			JTree tree = (JTree) e.getSource();// 鼠标事件的事件源
			int row = tree.getRowForLocation(e.getX(), e.getY());// 返回指定位置的行
			if (row == -1) {
				return;// 该位置不在显示的单元格的范围内
			}
			if (FlagTree == 0) {
				path = tree.getPathForRow(row);// 返回指定行的路径
			}

			if (path == null) {
				return;// row < 0或 row >= getRowCount()
			}
			FileNode node = (FileNode) path.getLastPathComponent();// 返回此路径的最后一个元素
			if (node == null) {
				return;
			}

			try {
				filepath = node.getWorR1();
				FilePath = node.getWorR1();// 返回路径字符串
				System.out.println("node=" + path);
			} catch (IOException ex) {
				Logger.getLogger(MainUi.class.getName()).log(Level.SEVERE, null, ex);
			}
			System.out.println(filepath);

			for (int i = 0; i < jComboBox1.getItemCount(); i++) {
				if (filepath.equals(jComboBox1.getItemAt(i))) {// 查找指定索引处的列表项
					flag = 1;
				}
			}
			if (flag == 0) {// 查找不到则添加列表项
				jComboBox1.addItem(filepath);
				TreePaths.add(path);
			}
			jComboBox1.setSelectedItem(filepath);// 返回当前选定的项目。
			File[] files = node.getWorR().listFiles();// 此抽象路径名的所有文件
			ClickedFilePath.clear();
			for (int FilesQuantity = 0; FilesQuantity < files.length; FilesQuantity++) {
				ClickedFilePath.add(files[FilesQuantity]);// 加入点击文件队列
			}
			System.out.println(files.length);
			int PictureNumber = 0;
			for (int i = 0; i < files.length; i++) {
				String fileName = files[i].getName();
				String suffix = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();

				if (suffix.equals("jpg") || suffix.equals("gif") || suffix.equals("bmp") || suffix.equals("png")|| suffix.equals("jpeg")) {
					PictureNumber++;

					ImageFiles.add(files[i]);// 进入图像文件队列
				}

			}
			/*
			 * for (int CIN = 0; CIN < files.length; CIN++) {
			 * 
			 * 
			 * String ext = files[CIN].getName()
			 * .substring(files[CIN].getName().lastIndexOf("."),
			 * files[CIN].getName().length()).toLowerCase();// 获取后缀
			 * System.out.println(files[CIN].getName());
			 * 
			 * if (ext.equals(".jpg") || ext.equals(".gif") || ext.equals(".bmp")) {
			 * PictureNumber++;
			 * 
			 * System.out.println(files[CIN].getName()); ImageFiles.add(files[CIN]);//
			 * 进入图像文件队列 } }
			 */
			for (int mm = 0; mm < ImageFiles.size(); mm++) {
				SmallLabels.add(new JLabel());
				SmallPanels.add(new JPanel());
				SmallTextFields.add(new JTextField());// 创建预览图片
			}
			int i = ImageFiles.size();// 多线程处理图片
			Runnable threadimages1 = new ThreadImages(ImageFiles, 0, i / 6, SmallLabels, SmallTextFields, SmallPanels,
					ImagePanel);
			Runnable threadimages2 = new ThreadImages(ImageFiles, i / 6, i / 6 * 2, SmallLabels, SmallTextFields,
					SmallPanels, ImagePanel);
			Runnable threadimages3 = new ThreadImages(ImageFiles, i / 6 * 2, i / 6 * 3, SmallLabels, SmallTextFields,
					SmallPanels, ImagePanel);
			Runnable threadimages4 = new ThreadImages(ImageFiles, i / 6 * 3, i / 6 * 4, SmallLabels, SmallTextFields,
					SmallPanels, ImagePanel);
			Runnable threadimages5 = new ThreadImages(ImageFiles, i / 6 * 4, i / 6 * 5, SmallLabels, SmallTextFields,
					SmallPanels, ImagePanel);
			Runnable threadimages6 = new ThreadImages(ImageFiles, i / 6 * 5, i, SmallLabels, SmallTextFields,
					SmallPanels, ImagePanel);
			Thread t1 = new Thread(threadimages1);
			Thread t2 = new Thread(threadimages2);
			Thread t3 = new Thread(threadimages3);
			Thread t4 = new Thread(threadimages4);
			Thread t5 = new Thread(threadimages5);
			Thread t6 = new Thread(threadimages6);
			t1.start();
			t2.start();
			t3.start();
			t4.start();
			t5.start();
			t6.start();

			for (int j = 0; j < SmallTextFields.size(); j++) {

				SmallTextFields.get(j).setText(ImageFiles.get(j).getName());

			}

			/*
			 * try { while
			 * (t6.isAlive()||t5.isAlive()||t4.isAlive()||t3.isAlive()||t2.isAlive()||t1.
			 * isAlive()) { Thread.sleep(500); } } catch (InterruptedException ex) {
			 * Logger.getLogger(主界面.class.getName()).log(Level.SEVERE, null, ex); }
			 */
			ImagesQuantity = ImageFiles.size();
			System.out.println("图片总数为:" + ImagesQuantity);
			InitLabelListener();
			InitMouseListener();

			if (ImagesQuantity > 20) {
				BigScrollPane.getVerticalScrollBar().setVisible(true);// 将控制视口垂直视图位置的滚动条添加到滚动窗格
				if (ImagesQuantity % 5 == 0) {
					ImagePanel.setPreferredSize(new Dimension(632, 125 * (ImagesQuantity / 5)));// 设置此组件的首选大小
				} else {
					ImagePanel.setPreferredSize(new Dimension(632, 125 * (ImagesQuantity / 5 + 1)));// 构造一个
																									// Dimension并将其初始化为指定的宽度和指定的高度
				}
				BigScrollPane.getVerticalScrollBar().setValue(0);// 将此滚动条的值设置为指定的值
			} else {
				ImagePanel.setPreferredSize(new Dimension(632, 399));
			}

		} catch (StringIndexOutOfBoundsException ex) {
			ImagePanel.setPreferredSize(new Dimension(632, 399));
		}
		ImageNum.setText("共"+SmallLabels.size()+"张,"+"选中图片数目：" + SelecctImages.size());
	}


	private void initComponents() {

		jToolBar1 = new javax.swing.JToolBar();
		jButton1 = new javax.swing.JButton();
		jButton2 = new javax.swing.JButton();
		jButton3 = new javax.swing.JButton();
		jButton4 = new javax.swing.JButton();
		jButton5 = new javax.swing.JButton();
		jButton6 = new javax.swing.JButton();
		jScrollPane1 = new javax.swing.JScrollPane();
		jTree1 = new javax.swing.JTree();
		jComboBox1 = new javax.swing.JComboBox();
		jTabbedPane1 = new javax.swing.JTabbedPane();
		jComboBox2 = new javax.swing.JComboBox();
		jMenuBar1 = new javax.swing.JMenuBar();
		jMenu1 = new javax.swing.JMenu();
		jMenuItem1 = new javax.swing.JMenuItem();
		jMenuItem2 = new javax.swing.JMenuItem();
		jMenu2 = new javax.swing.JMenu();
		jMenuItem3 = new javax.swing.JMenuItem();
		jMenu3 = new javax.swing.JMenu();
		jMenuItem4 = new javax.swing.JMenuItem();
		jMenuItem5 = new javax.swing.JMenuItem();
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		jToolBar1.setRollover(true);

		jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Picture/后退.jpg"))); // 创建图标
		jButton1.setText("后退");
		jButton1.setFocusable(false);// 指示此Component是否可聚焦
		jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		jButton1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton1ActionPerformed(evt);
			}
		});
		jToolBar1.add(jButton1);
		try {
			jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Picture/前进.jpg"))); // NOI18N
			jButton2.setText("前进");
			jButton2.setFocusable(false);
			jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);// 设置文本相对于图标的水平位置。
			jButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);// 设置文本相对于图标的垂直位置。
			jButton2.addActionListener(new java.awt.event.ActionListener() {// 添加指定的侦听器
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					jButton2ActionPerformed(evt);
				}
			});
		} catch (Exception e) {
			// TODO: handle exception
		}

		jToolBar1.add(jButton2);

		jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Picture/向上.jpg"))); // NOI18N
		jButton3.setText("向上");
		jButton3.setFocusable(false);
		jButton3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		jButton3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		jButton3.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton3ActionPerformed(evt);
			}
		});
		jToolBar1.add(jButton3);

		jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Picture/刷新.jpg"))); // NOI18N
		jButton4.setText("刷新");
		jButton4.setFocusable(false);
		jButton4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		jButton4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		jButton4.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton4ActionPerformed(evt);
			}
		});
		jToolBar1.add(jButton4);

		jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Picture/删除.jpg"))); // NOI18N
		jButton5.setText("删除");
		jButton5.setFocusable(false);
		jButton5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		jButton5.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		jButton5.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton5ActionPerformed(evt);
			}
		});
		jToolBar1.add(jButton5);

		jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Picture/进入.jpg"))); // NOI18N
		jButton6.setText("进入");
		jButton6.setFocusable(false);
		jButton6.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		jButton6.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		jButton6.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton6ActionPerformed(evt);
			}
		});
		jToolBar1.add(jButton6);

		jScrollPane1.setViewportView(jTree1);
		RunTree(jTree1);

		jComboBox1.setPreferredSize(new java.awt.Dimension(62, 14));
		jComboBox2.setEditable(false);
		/*
		 * jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(new String[] {
		 * "请选择文件夹外观", " ", " ", " " }));
		 */

		jMenuBar1.setMaximumSize(new java.awt.Dimension(1000, 32769));

		jMenu1.setText("文件");

		jMenuItem1.setText("打开");
		jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jMenuItem1ActionPerformed(evt);
			}
		});
		jMenu1.add(jMenuItem1);

		jMenuItem2.setText("退出");
		jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jMenuItem2ActionPerformed(evt);
			}
		});
		jMenu1.add(jMenuItem2);

		jMenuBar1.add(jMenu1);

		jMenu2.setText("工具");

	
		jMenuItem5.setText("截图");
		jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jMenuItem5ActionPerformed(evt);
			}
		});
	
		jMenu2.add(jMenuItem5);
		jMenuBar1.add(jMenu2);

		jMenu3.setText("帮助");

		jMenuItem4.setText("关于");
		jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jMenuItem4ActionPerformed(evt);
			}
		});
		jMenu3.add(jMenuItem4);

		jMenuBar1.add(jMenu3);

		setJMenuBar(jMenuBar1);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
								.addComponent(ImageNum, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(
								layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 685,
												Short.MAX_VALUE)
										.addComponent(jComboBox1, 0, 685, Short.MAX_VALUE)))
				.addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 842, Short.MAX_VALUE));
		layout.setVerticalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
								.addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addGroup(layout.createSequentialGroup()
												.addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 20,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
												.addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 540,
														Short.MAX_VALUE)
												.addGap(2, 2, 2))
										.addGroup(layout.createSequentialGroup()
												.addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 536,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
												.addComponent(ImageNum, javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addGap(5, 5, 5)))
								.addContainerGap()));

		pack();
	}// </editor-fold>//GEN-END:initComponents

	private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem1ActionPerformed
		Open();
	}// GEN-LAST:event_jMenuItem1ActionPerformed

	private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem2ActionPerformed
		System.exit(0);
	}// GEN-LAST:event_jMenuItem2ActionPerformed

	private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem4ActionPerformed
		IntroduceFrame.setVisible(true);
		IntroduceTextArea.setText("\n      制作人:第五组\n" + "      学号:201825010424，15，16\n" + "      班级:计算机4班\n");
	}// GEN-LAST:event_jMenuItem4ActionPerformed
	private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem4ActionPerformed
try {
	screencapture();
} catch (Exception e) {
	// TODO: handle exception
}
	}// GEN-LAST:event_jMenuItem4ActionPerformed
	private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton1ActionPerformed
		if (jComboBox1.getSelectedIndex() > 0) {
			Back();
		}
	}// GEN-LAST:event_jButton1ActionPerformed

	private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton2ActionPerformed
		if (jComboBox1.getSelectedIndex() + 1 < TreePaths.size()) {
			Next();
		}
	}// GEN-LAST:event_jButton2ActionPerformed

	private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton3ActionPerformed
		Up();
	}// GEN-LAST:event_jButton3ActionPerformed

	private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton4ActionPerformed
		ShowImages(E, new TreePath(0), 0);
	}// GEN-LAST:event_jButton4ActionPerformed

	private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton5ActionPerformed
		Delete();
	}// GEN-LAST:event_jButton5ActionPerformed

	private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton5ActionPerformed
		in();
	}// GEN-LAST:event_jButton5ActionPerformed

	public static void main(String args[]) {
		try {

			String look = "com.jtattoo.plaf.hifi.HiFiLookAndFeel";
			UIManager.setLookAndFeel(look);
		setUIFont();
		} catch (Exception e) {
			System.err.println("不能使第三方外观");
		}

		java.awt.EventQueue.invokeLater(new Runnable() {// 使用事件调度线程

			public void run() {
				MainUi zhu = new MainUi();
				zhu.Init();
				zhu.setResizable(false);
				zhu.setLocationRelativeTo(null);
				zhu.setVisible(true);
			}
		});
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton jButton1;
	private javax.swing.JButton jButton2;
	private javax.swing.JButton jButton3;
	private javax.swing.JButton jButton4;
	private javax.swing.JButton jButton5;
	private javax.swing.JButton jButton6;
	private javax.swing.JComboBox jComboBox1;
	private javax.swing.JComboBox jComboBox2;
	private javax.swing.JMenu jMenu1;
	private javax.swing.JMenu jMenu2;
	private javax.swing.JMenu jMenu3;
	private javax.swing.JMenuBar jMenuBar1;
	private javax.swing.JMenuItem jMenuItem1;
	private javax.swing.JMenuItem jMenuItem2;
	private javax.swing.JMenuItem jMenuItem3;
	private javax.swing.JMenuItem jMenuItem4;
	private javax.swing.JMenuItem jMenuItem5;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JTabbedPane jTabbedPane1;
	private javax.swing.JToolBar jToolBar1;
	private javax.swing.JTree jTree1;
	// End of variables declaration//GEN-END:variables
}
