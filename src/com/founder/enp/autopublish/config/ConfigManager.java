/**
 * 
 */
package com.founder.enp.autopublish.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.founder.enp.autopublish.util.XMLProperties;


/**
 * @author hwj
 *
 */
public class ConfigManager {
	

	private static Log log = LogFactory.getLog(ConfigManager.class);
	
	private static String CONFIG_FILENAME = "config.xml";
	public static XMLProperties properties = null;
	
	private synchronized static void loadProperties(){
		if(properties == null){
			properties = new XMLProperties(getConfigFile());
			log.info("加载成功配置文件"+CONFIG_FILENAME);
		}
		try{
			Config.setInterval(Integer.parseInt(properties.getProperty("interval")));
			Config.setSiteid(Integer.parseInt(properties.getProperty("siteid")));
			Config.setIp(properties.getProperty("ip"));
			Config.setLanguage(properties.getProperty("language"));
			Config.setPub_time(Integer.parseInt(properties.getProperty("pub-time")));
			Config.setPwd(properties.getProperty("passwd"));
			Config.setUser(properties.getProperty("username"));
			
			ReadNodeIDS.readThreeFiles();
		}catch (Exception e) {
			log.error(" 请查看配置文件是否配置正常 ");
			System.exit(0);
		}
	}
	
	public static String getConfigFile(){
		return ConfigManager.class.getClassLoader().getResource(CONFIG_FILENAME).getPath();
	}
	
	public static String getProperty(String prop){
		return properties.getProperty(prop);		
	}
	
	public static void main(String[] args){
		loadProperties();
	}
	
	public static void init(){
		loadProperties();
	}
}
