/*package com.yizhenmoney.damocles.configcenter;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.webapp.WebAppContext;

public class JettyApp {

	public static final int PORT = 8085;
	public static final String CONTEXT = "/";

	private static final String DEFAULT_WEBAPP_PATH = "src/main/webapp";

	*//**
	 * 创建用于开发运行调试的Jetty Server, 以src/main/webapp为Web应用目录.
	 * @throws Exception 
	 *//*
	public static void main(String[] args) throws Exception {
		Server server = new Server();
		// 设置在JVM退出时关闭Jetty的钩子。
		server.setStopAtShutdown(true);

		// 这是http的连接器
		ServerConnector connector = new ServerConnector(server);
		connector.setPort(PORT);
		// 解决Windows下重复启动Jetty居然不报告端口冲突的问题.
		connector.setReuseAddress(false);
		server.setConnectors(new Connector[] { connector });
		WebAppContext webContext = new WebAppContext(DEFAULT_WEBAPP_PATH, CONTEXT);
		 webContext.setContextPath("/");
		webContext.setDescriptor("src/main/webapp/WEB-INF/web.xml");
		// 设置webapp的位置
		webContext.setResourceBase(DEFAULT_WEBAPP_PATH);
		webContext.setClassLoader(Thread.currentThread().getContextClassLoader());
		server.setHandler(webContext);
		server.start();
		server.join();
	}
}
*/