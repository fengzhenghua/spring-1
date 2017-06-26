package com.tydic.unicom.crawlerframe.mail;

import java.security.Security;
import java.util.Properties;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.imap.IMAPStore;
import com.tydic.unicom.crawler.common.SysPar;
public class OpenMailFindPwd {
	public static final Logger LOG = LoggerFactory.getLogger(OpenMailFindPwd.class);
	/**
	 * 构造函数,初始化一个MimeMessage对象
	 */
	public OpenMailFindPwd() {
	}

	/**
	 * 
	 * ReceiveEmail类测试
	 * 
	 */

	public String getSaftcode() throws Exception {
		String safeCode = null;
		Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
		final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
		Properties props = System.getProperties();
		props.setProperty("mail.imap.socketFactory.class", SSL_FACTORY);
		// props.setProperty("mail.imap.socketFactory.fallback", "false");
		// props.setProperty("mail.imap.port", port);
		props.setProperty("mail.imap.socketFactory.port", SysPar.ppvo.getMailaddport());
		props.setProperty("mail.store.protocol", "imap");
		props.setProperty("mail.imap.host", SysPar.ppvo.getMailadd());
		props.setProperty("mail.imap.port", SysPar.ppvo.getMailaddport());
		props.setProperty("mail.imap.auth.login.disable", "true");
		Session session = Session.getDefaultInstance(props, null);
		session.setDebug(false);
		IMAPStore store = (IMAPStore) session.getStore("imap");
		// 以只读权限打开收件箱
		store.connect(SysPar.ppvo.getMailadd(),SysPar.ppvo.getMailuser(), SysPar.ppvo.getMailpwd());

		// 获得收件箱
		IMAPFolder folder = (IMAPFolder) store.getFolder("INBOX");
		/*
		 * Folder.READ_ONLY：只读权限 Folder.READ_WRITE：可读可写（可以修改邮件的状态）
		 */
		folder.open(Folder.READ_WRITE); // 打开收件箱
		// 由于POP3协议无法获知邮件的状态,所以getUnreadMessageCount得到的是收件箱的邮件总数
		Message msgs[] = folder.getMessages();
		LOG.info("=========mail登录成功，开始找验证码！");
		for (Message message : msgs) {
			// 如果是读过的文件就不需要读取
			try {
				message.getFlags();
			} catch (Exception e) {
				LOG.info("=========没有找到相应的验证码！");
				break;
			}
			if (message.getFlags().contains(Flags.Flag.SEEN)) {
				LOG.info("=========跳过被读取的邮件！");
				continue;
			}
			MailRevice rm = new MailRevice(message);
			String tmpSafeCode = rm.getSafeCode();
			// 解析出验证码的邮件,读取以后删除,以保证只有一封邮件
			if (!"".equals(tmpSafeCode)) {
				safeCode = tmpSafeCode;
				// message.setFlag(Flags.Flag.DELETED, true);
			}
		}
		// 得到收件箱中的所有邮件,并解析
		Message[] messages = folder.getMessages();
		// parseMessage(messages);
		// 释放资源
		folder.close(true);
		store.close();
		return safeCode;
	}

}