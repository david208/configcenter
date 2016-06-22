package com.yizhenmoney.damocles.configcenter.service;
/*package com.yizhenmoney.damocles.zookeeper.utils;

import java.util.Date;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.netflix.curator.framework.CuratorFramework;
public class SendMailService {
	private CuratorFramework client;
	private static SendMailService send = null;
	private String smtpFromMail;
	private String pwd;
	private int port;
	private String host;
	private String to;
	public static SendMailService getSendMailService(){
		if(null == send){
			send = new SendMailService();
		}
		return send;
	}
	public SendMailService(){
		try {
			PropitesUtils p = new PropitesUtils("application.properties");
		//	client= ZooKeeperConfig.getBaseZookeeperConfig(p.readValue("zoo.auth")).getBaseClient();
			smtpFromMail = new String(client.getData().forPath("/properties/email/username"),"utf-8").split("=")[1]; // 账号
			pwd = new String(client.getData().forPath("/properties/email/password"),"utf-8").split("=")[1]; // 密码
			port = Integer.parseInt(new String(client.getData().forPath("/properties/email/port"),"utf-8").split("=")[1]); // 端口
			host = new String(client.getData().forPath("/properties/email/host"),"utf-8").split("=")[1]; // 端口
			to = new String(client.getData().forPath("/properties/email/to"),"utf-8").split("=")[1];
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public boolean sendMail(String subject,  String content) {
		boolean isFlag = false;
		try {
			Properties props = new Properties();
			props.put("mail.smtp.host", host);
			props.put("mail.smtp.auth", "true");
			Session session = Session.getDefaultInstance(props);
			session.setDebug(false);

			MimeMessage message = new MimeMessage(session);
			try {
				if (to.trim().length() > 0) {
					String[] arr = to.split(",");
					int receiverCount = arr.length;
					if (receiverCount > 0) {
						InternetAddress[] address = new InternetAddress[receiverCount];
						for (int i = 0; i < receiverCount; i++) {
							address[i] = new InternetAddress(arr[i]);
						}
						message.setFrom(new InternetAddress(smtpFromMail));
						message.addRecipients(Message.RecipientType.TO, address);
						message.setSubject(subject);
						message.addHeader("charset", "UTF-8");
					} else {
						System.exit(0);
					}
				}
				 添加正文内容 
				Multipart multipart = new MimeMultipart();
				BodyPart contentPart = new MimeBodyPart();
				contentPart.setText(content);

				contentPart.setHeader("Content-Type", "text/html; charset=GBK");
				multipart.addBodyPart(contentPart);

				 添加附件 
//				for (String file : files) {
//					File usFile = new File(file);
//					MimeBodyPart fileBody = new MimeBodyPart();
//					DataSource source = new FileDataSource(file);
//					fileBody.setDataHandler(new DataHandler(source));
//					sun.misc.BASE64Encoder enc = new sun.misc.BASE64Encoder();
//					fileBody.setFileName("=?UTF-8?B?" + enc.encode(usFile.getName().getBytes()) + "?=");
//					multipart.addBodyPart(fileBody);
//				}

				message.setContent(multipart);
				message.setSentDate(new Date());
				message.saveChanges();
				Transport transport = session.getTransport("smtp");

				transport.connect(host, port, smtpFromMail, pwd);
				transport.sendMessage(message, message.getAllRecipients());
				transport.close();
				isFlag = true;
			} catch (Exception e) {
				isFlag = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isFlag;
	}
}
*/