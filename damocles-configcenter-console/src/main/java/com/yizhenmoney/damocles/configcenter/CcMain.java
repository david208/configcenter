package com.yizhenmoney.damocles.configcenter;

import org.apache.catalina.startup.Tomcat;

public class CcMain {

	public static void main(String[] args) throws Exception {
		Tomcat tomcat = new Tomcat();
		tomcat.setPort(8085);
		tomcat.getConnector().setURIEncoding("UTF-8");
		String path = CcMain.class.getResource("/").getPath();
		tomcat.addWebapp("/", path.substring(0, path.indexOf("target")) + "src/main/webapp");
		tomcat.getConnector().setMaxPostSize(6291456);
		tomcat.getConnector().setMaxSavePostSize(6291456);
		tomcat.start();
		tomcat.getServer().await();
	}
}
