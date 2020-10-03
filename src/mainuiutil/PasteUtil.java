package mainuiutil;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JTextField;
import javax.swing.tree.TreePath;

public class PasteUtil {
	String SourceFileName = null;
	ArrayList<JTextField> SmallTextFields = new ArrayList<JTextField>(); // 这里定义N个文本框，用来显示与图片相对应的文件的名称
	ArrayList<FileOutputStream> NewFile = new ArrayList<FileOutputStream>();
	String FilePath,CopyName,CopyPath;
	ArrayList<BufferedInputStream> SourceFile = new ArrayList<BufferedInputStream>();
	int PasteNumber,CopyNum;
	public void Work() throws FileNotFoundException {
	
		
		int flag = 0;
		int PasteNum = 0;
		int FlagName = 0;
		String SourceFileNameFront = SourceFileName.substring(0, SourceFileName.indexOf(".")).toLowerCase();// 提取前缀

			if (SourceFileName.indexOf("(") != -1) {// 存在（则提取（前的
				SourceFileNameFront = SourceFileName.substring(0, SourceFileName.indexOf("(")).toLowerCase();
			}
			System.out.println((String) (SourceFileNameFront) + "aabcdefg");
			System.out.println(SourceFileName + "kaabcdefg");
			String SourceFileNameLast = SourceFileName
					.substring(SourceFileName.lastIndexOf("."), SourceFileName.length()).toLowerCase();// 提取后缀

			try {

				for (int size = 0; size < SmallTextFields.size(); size++) {

					if (SourceFileNameFront.equals(SmallTextFields.get(size).getText()
							.substring(0, SmallTextFields.get(size).getText().indexOf(".")).toLowerCase())) {// 检查是否重名

						PasteNum = 2;

					}
				}

			} catch (Exception e) {

				// TODO: handle exception
			}

			for (int size = 0; size < SmallTextFields.size(); size++) {
				/*
				 * System.out.println(SmallTextFields.get(size).getText().substring(0,
				 * SmallTextFields.get(size).getText().indexOf(".")));
				 */
				if (new String(SourceFileNameFront + "(" + PasteNum + ")").equals(SmallTextFields.get(size).getText()
						.substring(0, SmallTextFields.get(size).getText().indexOf(".")).toLowerCase())) {// 检查是否再次重名，直至不重名
					PasteNum++;
					size = 0;
				}
			}

			if (PasteNum != 0) {// 存在重名的写入
				NewFile.add(new FileOutputStream(new File(
						FilePath + File.separator + SourceFileNameFront + "(" + PasteNum + ")" + SourceFileNameLast)));
			} else {
				NewFile.add(new FileOutputStream(
						new File(FilePath + File.separator + SourceFileNameFront + SourceFileNameLast)));
			}

			try {
CopyInPaste();
			} catch (IOException e) {
				System.out.print(e);
			}
			System.out.println("2dhiasusa");
			byte[] content = new byte[1000];
			int size = 0;

			try {

				while ((size = SourceFile.get(CopyNum - 1).read(content)) != -1) {
					NewFile.get(PasteNumber).write(content, 0, size);
				} // 写入文件
				System.out.println("dhiasusa");
			} catch (IOException e) {
				e.printStackTrace();
			}
		

			try {
				NewFile.get(PasteNumber).close();// 关闭输入流
			} catch (IOException ex) {
			}
			PasteNumber++;

		}
	public void CopyInPaste() throws IOException {// 非剪切的复制
		if (CopyNum != 0) {
			try {
				SourceFile.get(CopyNum - 1).close();// 关闭前一个的输入流
			} catch (IOException ex) {
			}
		}

		try {
			SourceFile.add(new BufferedInputStream(new FileInputStream(CopyPath + File.separator + CopyName)));
//新建输入流
			SourceFileName = CopyName;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		CopyNum++;

	}
	public PasteUtil(String sourceFileName, ArrayList<JTextField> smallTextFields, ArrayList<FileOutputStream> newFile,
			String filePath, String copyName, String copyPath, ArrayList<BufferedInputStream> sourceFile,
			int pasteNumber, int copyNum) {
		super();
		SourceFileName = sourceFileName;
		SmallTextFields = smallTextFields;
		NewFile = newFile;
		FilePath = filePath;
		CopyName = copyName;
		CopyPath = copyPath;
		SourceFile = sourceFile;
		PasteNumber = pasteNumber;
		CopyNum = copyNum;
	}
	public int getPasteNumber() {
		return PasteNumber;
	}
	public int getCopyNum() {
		return CopyNum;
	}

}
