/**
 * 
 */
package com.founder.enp.autopublish.config;

import java.util.ArrayList;

/**
 * @author hwj
 *
 */
public class Config {
	/** 1.栏目 */
	private static ArrayList node1 = null;
	/** 2：栏目及子栏目 */
	private static ArrayList node2 = null;
	/** 3：栏目及子孙栏目 */
	private static ArrayList node3 = null;

	private static String ip = null;
	private static String user = null;
	private static String pwd = null;
	private static String language = null;
	private static int interval = 10;
	private static int pub_time = 1;
	private static int siteid = -1;
	
	public static ArrayList getNode1() {
		return node1;
	}
	public static void setNode1(ArrayList node1) {
		Config.node1 = node1;
	}
	public static ArrayList getNode2() {
		return node2;
	}
	public static void setNode2(ArrayList node2) {
		Config.node2 = node2;
	}
	public static ArrayList getNode3() {
		return node3;
	}
	public static void setNode3(ArrayList node3) {
		Config.node3 = node3;
	}
	public static String getIp() {
		return ip;
	}
	public static void setIp(String ip) {
		Config.ip = ip;
	}
	public static String getUser() {
		return user;
	}
	public static void setUser(String user) {
		Config.user = user;
	}
	public static String getPwd() {
		return pwd;
	}
	public static void setPwd(String pwd) {
		Config.pwd = pwd;
	}
	public static String getLanguage() {
		return language;
	}
	public static void setLanguage(String language) {
		Config.language = language;
	}
	public static int getInterval() {
		return interval;
	}
	public static void setInterval(int interval) {
		Config.interval = interval;
	}
	public static int getPub_time() {
		return pub_time;
	}
	public static void setPub_time(int pub_time) {
		Config.pub_time = pub_time;
	}
	public static int getSiteid() {
		return siteid;
	}
	public static void setSiteid(int siteid) {
		Config.siteid = siteid;
	}
}
