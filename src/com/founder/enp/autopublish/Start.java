package com.founder.enp.autopublish;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.founder.enp.autopublish.config.Config;
import com.founder.enp.autopublish.config.ConfigManager;



/**
 * 读取文件，自动调用手动发布栏目，临时解决中国网漏稿问题
 * @author hwj
 *
 */
public class Start {
	
	private static Log log = LogFactory.getLog(Start.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//http://localhost:7001/servlet/LoginServlet?userpwd=10038bcc6fb80cc24d89f10b810b2c9a&username=cjm&userpwdorig=5982818&language=CN
		//读配置文件
		/* 0  服务器地址
		 * 1  username
		 * 2  passwd(md5)
		 * 3  language CN
		 * 4  inerval  间隔时间，多少分钟发布一次
		 * 5  publish-onstart  是否在一启动的时候就发布一次
		 * 6  node
		 * 	a.nodeid
		 * 	b.nodeid
		 * 
		 * 
		 * http://localhost:7001/enpadmin/manual/manualPubNode.jsp
		 *  post:
		 *  beginDate	2008-08-06	开始日期
			endDate	2008-08-07		结束日期
			id	7015064				nodeid
			isRefrenshArticle	1	是否发布稿件
			level	1				按栏目发布方式
                                        <option value="1">栏目</option>
                                        <option value="2">栏目及子栏目</option>
                                        <option value="3">栏目及子孙栏目</option>
		 */
		ConfigManager.init();
		String ip = Config.getIp();
		int interval = Config.getInterval();
		String loginurl = "http://"+ip+"/servlet/LoginServlet?userpwd="+Config.getPwd()+"&username="+Config.getUser()+"&language="+Config.getLanguage();
		String changesiteurl= "http://"+ip+"/enpadmin/switch.jsp?siteid="+Config.getSiteid()+"&rr=true";
		String puburl = "http://"+ip+"/enpadmin/manual/manualPubNode.jsp";
		boolean node1 = false;
		boolean node2 = false;
		boolean node3 = false;
		if(Config.getNode1().size() > 0){
			node1 = true;
		}
		if(Config.getNode2().size() > 0){
			node2 = true;
		}
		if(Config.getNode3().size() > 0){
			node3 = true;
		}
		while(true){
			try {
				String result = null;
				if(node1){
					//System.out.println
					System.out.println("开始发布node1.txt");
					result = publish(loginurl,changesiteurl,puburl,1);
					System.out.println(procResult(result));
					System.out.println("发布node1.txt结束");
				}
				if(node2){
					System.out.println("开始发布node2.txt");
					result = publish(loginurl,changesiteurl,puburl,2);
					System.out.println(procResult(result));
					System.out.println("发布node2.txt结束");
				}
				if(node3){
					System.out.println("开始发布node3.txt");
					result = publish(loginurl,changesiteurl,puburl,3);
					System.out.println(procResult(result));
					System.out.println("发布node3.txt结束");
				}
				Thread.sleep(interval*60*1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		
		//char c1 []= new char[]{'糖','也','是','，','要','快','乐'};
		//for(int i=0;i<c1.length;i++)
		//	System.out.println((int)c1[i]);
	}
	
	private static String publish(String loginurl,String changsiteurl,String puburl,int level){
		String result = null;
		try{
			HttpClient client = new HttpClient();
			GetMethod gm = new GetMethod(loginurl);
			client.getState().setCookiePolicy(CookiePolicy.COMPATIBILITY);	
			client.executeMethod(gm);
			gm = new GetMethod(changsiteurl);
			client.executeMethod(gm);
			PostMethod pm = new PostMethod(puburl);
			NameValuePair [] nvp = genNameValuePair(level);
			if(nvp != null)
				pm.setQueryString(nvp);
			client.executeMethod(pm);
			result = pm.getResponseBodyAsString();
			gm.releaseConnection();
			pm.releaseConnection();
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return result;
	}
	
	/**
	 * level 1,2,3
	 */
	private static NameValuePair[] genNameValuePair(int level){
		NameValuePair[] nvp = new NameValuePair[4];
		String name[] = new String[]{"beginDate","endDate","id","level"};
		ArrayList list = null;
		if(level == 1){
			list = Config.getNode1();			
		}else if(level == 2){
			list = Config.getNode2();
		}else if(level == 3){
			list = Config.getNode3();
		}else{
			return null;
		}
		if(list.size() == 0)
			return null;
		/*
		 * 拼id 
		 */
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<list.size();i++){
			sb.append(list.get(i)).append(",");
		}
		sb.deleteCharAt(sb.length()-1);
		//拼日期
		Calendar cal = Calendar.getInstance();
		int by = cal.get(Calendar.YEAR);
		int bm = cal.get(Calendar.MONTH)+1;
		int bd = cal.get(Calendar.DAY_OF_MONTH);
		cal.add(Calendar.DAY_OF_MONTH, -Config.getPub_time());
		int ey = cal.get(Calendar.YEAR);
		int em = cal.get(Calendar.MONTH)+1;
		int ed = cal.get(Calendar.DAY_OF_MONTH);
		String beginDate = getDate(by,bm,bd);
		String endDate = getDate(ey, em, ed);
		String value[]= new String[]{beginDate,endDate,sb.toString(),String.valueOf(level)};
		for(int i=0;i<name.length;i++){
			nvp[i] = new NameValuePair();
			nvp[i].setName(name[i]);
			nvp[i].setValue(value[i]);
		}
		return nvp;
	}
	
	private static String getDate(int by,int bm,int bd){
		return ""+by+"-"+getReguNum(bm)+"-"+getReguNum(bd);
	}
	private static String getReguNum(int num){
		String res = null;
		if(num<10)
			res = "0" + num;
		return res;
	}
	
	private static String procResult(String result){
		String res = result.substring(0,result.indexOf("<html"));
		res = res.trim();
		return res;
	}
}
