package com.myflair.common.utils;



import java.io.File;
import java.nio.charset.Charset;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.google.common.io.Files;
/**
 * 读取资源文件工具类
 * @author wanjw
 * @author liqingyuan
 */
public class SysUtil {
	private static Logger logger = Logger.getLogger(SysUtil.class.getName());
	
	//资源文件名静态常量
	public final static String FTP_PROPERTIES_NAME="/ftp.properties";
	public final static String RESOURCE_PROPERTIES_NAME="/resource.properties";


    /**
	 * 入口方法，根据参数创建Properties对象
	 * @param propertiesName 资源文件名，推荐使用SysUtil类的静态常量
	 * @return Properties对象
	 */
	public static Properties getProperties(String propertiesName){
		String propertiesPath=getConfigPath(propertiesName);
		return createProperties(propertiesPath);
	}
	
	
	/**
	 * 内部方法，根据参数拼装Properties文件路径
	 * @param propertiesName 资源文件名，使用SysUtil类的静态常量
	 * @return String 文件路径
	 */
	private static String getConfigPath(String propertiesName){
		return SysUtil.class.getResource(propertiesName).getPath().replaceAll("%20", " ");
	}
	
	
	/**
	 * 内部方法，根据Properties文件路径创建Properties对象
	 * @param filefullname
	 * @return Properties对象
	 */
	private static Properties createProperties(String filefullname){
		try{
			File file = new File(filefullname);
		    Properties properties = new Properties();
		    properties.load(Files.newReader(file, Charset.forName("utf-8")));
		    return properties;
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			return null;
		}
	}
	public static String getResourceValue(String key){
		Properties resourceProperties = SysUtil.getProperties(SysUtil.RESOURCE_PROPERTIES_NAME);
		return resourceProperties.getProperty(key);
	}
}
