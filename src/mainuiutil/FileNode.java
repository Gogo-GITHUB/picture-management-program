package mainuiutil;


import java.io.File;
import java.io.IOException;
import javax.swing.tree.DefaultMutableTreeNode;


 public class   FileNode   extends   DefaultMutableTreeNode   {
  private   boolean   explored_   =   false;

  public   FileNode(File   file)   {
  setUserObject   (file);//将此节点的用户对象设置为 file
  }
  public   boolean   getAllowChildren()   {
  return   isDirectory();//返回是否有子节点
  }
  public   boolean   isLeaf()   {
  return   !isDirectory();//返回是否属于叶子节点
  }

  public   File   getFile()   {
  return   (File)getUserObject();//返回此节点的用户对象
  }
  public   boolean   isExplored()   {
  return   explored_;//返回是否遍历过
  }
  public   boolean   isDirectory()   {
  File   file   =   getFile();
  return   file.isDirectory();//返回是否为文件夹
  }
  public   String   toString()   {//重写tostring方法，返回文件名
  File   file   =   getFile   ();
  String   filename   =   file.toString();//获得当前节点文件名
		
		  int index = filename.lastIndexOf("\\");//返回指定子字符串最后一次出现的字符串中的索引
		 		
		  return (index != -1 && index != filename.length() - 1) ?
		  filename.substring(index + 1) : filename;
		 
  }
  public   String   getString()   {
  File   file   =   getFile   ();
  String   filename   =   file.getAbsolutePath();//返回此抽象路径名的绝对路径名字符串
  return   filename;
  }
  public File getWorR(){
      File file=getFile();
      File file1=file.getAbsoluteFile();//返回此抽象路径名的绝对形式
      return file1;
  }
  public String getWorR1() throws IOException{
      File file=getFile();
      String file1=file.getPath();//将此抽象路径名转换为路径名字符串
      return file1;
  }
  public   void   explore()   {
  if   (!isDirectory())   {//是文件夹则返回
  return;
  }
  if   (!isExplored())   {//没有被遍历
  File   file   =   getFile   ();
  File   []   children   =   file.listFiles();//遍历子文件
  for   (int   i   =   0;   i   <   children.length;   ++i)   {
  if   (children[i].isDirectory())   {
  add(new   FileNode   (children[i]));//将文件夹加入
  }
  }
  explored_   =   true;
  }
  }
  }
