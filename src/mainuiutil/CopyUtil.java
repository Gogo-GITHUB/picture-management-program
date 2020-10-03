package mainuiutil;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JTextField;

public class CopyUtil {
	int CutFlag,CopyNum;
	ArrayList<Integer> SelecctImages = new ArrayList<Integer>();
	ArrayList<String> CopyNames = new ArrayList<String>();
	ArrayList<String> CopyPaths = new ArrayList<String>();
	ArrayList<Integer> SelecctImagesLock = new ArrayList<Integer>();
	ArrayList<JTextField> SmallTextFields = new ArrayList<JTextField>(); // 这里定义N个文本框，用来显示与图片相对应的文件的名称
	
	String FilePath,CopyName,CopyPath;
	String SourceFileName = null;
	ArrayList<BufferedInputStream> SourceFile = new ArrayList<BufferedInputStream>();
	public CopyUtil(int cutFlag, int copyNum, ArrayList<Integer> selecctImages, ArrayList<String> copyNames,
			ArrayList<String> copyPaths, ArrayList<Integer> selecctImagesLock, ArrayList<JTextField> smallTextFields,
			String filePath, String copyName, String copyPath, String sourceFileName,
			ArrayList<BufferedInputStream> sourceFile) {
		super();
		CutFlag = cutFlag;
		CopyNum = copyNum;
		SelecctImages = selecctImages;
		CopyNames = copyNames;
		CopyPaths = copyPaths;
		SelecctImagesLock = selecctImagesLock;
		SmallTextFields = smallTextFields;
		FilePath = filePath;
		CopyName = copyName;
		CopyPath = copyPath;
		SourceFileName = sourceFileName;
		SourceFile = sourceFile;
	}
	public void Save() throws IOException {

		for (int j = 0; j < SelecctImages.size(); j++) {
			System.out.println(j);
			System.out.println("fsaddsa");
			SelecctImagesLock.add(SelecctImages.get(j));
			CopyNames.add(SmallTextFields.get(SelecctImages.get(j)).getText());
			CopyPaths.add(FilePath);
		}

	}
	public void Work(String ssg, String fp) throws IOException {

		CutFlag = 0;// 非剪切
		if (CopyNum != 0) {
			try {
				SourceFile.get(CopyNum - 1).close();
			} catch (IOException ex) {
			}
		}
		CopyName = ssg;
		CopyPath = fp;// 记录路径和名字
		try {
			SourceFile.add(new BufferedInputStream(new FileInputStream(fp + File.separator + ssg)));

			SourceFileName = ssg;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		CopyNum++;

		/*
		 * copy dCopy=new copy(CutFlag, CopyNum, SourceFile, CopyName, SmallTextFields,
		 * SelectImage, FilePath, CopyPath, SourceFileName); dCopy.CopyUtil();
		 * CutFlag=dCopy.getCutFlag(); CopyNum=dCopy.getCopyNum();
		 * 
		 * CopyName=dCopy.getCopyName();
		 * 
		 * SelectImage=dCopy.getSelectImage(); FilePath=dCopy.getFilePath();
		 * CopyPath=dCopy.getCopyPath(); SourceFileName=dCopy.getSourceFileName();
		 */

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
	public int getCutFlag() {
		return CutFlag;
	}
	public int getCopyNum() {
		return CopyNum;
	}
	
}
