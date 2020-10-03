package mainuiutil;


import java.io.File;
import java.io.IOException;
import javax.swing.filechooser.FileSystemView;

/**
 *
 * @author Administrator
 */


  public  class   PFileSystemView   extends   FileSystemView   {//FileSystemView是JFileChooser的文件系统网关
  public   File   createNewFolder(File   containingDir)   throws   IOException   {//创建具有默认文件夹名称的新文件夹。
  return   null;
  }
  }
