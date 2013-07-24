/**
 * 
 */
package com.founder.enp.autopublish.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * ��ȡ3���ļ������list
 * @author hwj
 *
 */
public class ReadNodeIDS {
	
	private static Log log = LogFactory.getLog(ReadNodeIDS.class);
	
	private static String node1 = ReadNodeIDS.class.getClassLoader().getResource("node1.txt").getPath();
	private static String node2 = ReadNodeIDS.class.getClassLoader().getResource("node2.txt").getPath();
	private static String node3 = ReadNodeIDS.class.getClassLoader().getResource("node3.txt").getPath();
	
	public static void readThreeFiles(){
		Config.setNode1(readFile(node1));
		Config.setNode2(readFile(node2));
		Config.setNode3(readFile(node3));
	}
	
	private static ArrayList readFile(String url){
		ArrayList list = new ArrayList();
		String content = getFileContent(url);
		if(content!=null && content.length()>0){
			String ids[] = content.split("\n");
			for(int i=0;i<ids.length;i++){
				ids[i] = ids[i].trim();
				if(ids[i].startsWith("//") || ids[i].length()==0)
					continue;
				else
					list.add(ids[i]);
			}
		}
		return list;
	}
	
	private static String getFileContent(String url){
		byte[] content = null;
		try {
			InputStream is = getFileInputStream(url);
			content = new byte[is.available()];
			is.read(content);
			closeFileStream(is);
		} catch (FileNotFoundException e) {
			log.warn("�ļ�û���ҵ�:"+url);
		} catch (IOException e) {
			log.error("�ر��ļ�������:"+url);
		} catch (Exception e) {
			log.error("��ȡ�ļ����ݳ���",e);
		}
		return new String(content);
	}
	
	private static InputStream getFileInputStream(String url) throws FileNotFoundException{
		File file = new File(url);
		FileInputStream fis = new FileInputStream(file);
		return fis;
	}
	
	private static void closeFileStream(InputStream is) throws IOException{
		is.close();
	}
	
	public static void main(String[] args) {
		readFile(node1);
	}
}
