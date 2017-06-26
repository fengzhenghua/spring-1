/**
 * 
 */
package com.tydic.unicom.apc.service.common.impl;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

//import org.owasp.esapi.Logger;

/**
 * HTTP 工具类
 * @author zhangrong
 *
 * @date   2016年5月12日
 */
public class HttpUtil {
	private static AtomicInteger counter = new AtomicInteger(0);

	/**
	 * http POST 发送客户端
	 * @param urladdr 发送地址
	 * @param info 发送消息
	 * @return
	 */
	public static String httpClient(String urladdr, String info) {
		String string = null;
		URL url = null;
		HttpURLConnection conn = null;
		//设置登录信息
		Authenticator auth = new BasicAuthenticator("aimAdmin", "aimadmin");
		Authenticator.setDefault(auth);
		int connectTimeout = Integer.valueOf(System.getProperties().getProperty("http.connect.timeout","10000")); 
		int readTimeout = Integer.valueOf(System.getProperties().getProperty("http.read.timeout","30000"));
		try {
			url = new URL(urladdr);

			conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setConnectTimeout(connectTimeout);
			conn.setReadTimeout(readTimeout);
			conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
//			conn.setRequestProperty("Content-Length", String.valueOf(info.length()));
			conn.getOutputStream().write(info.getBytes());
			conn.getOutputStream().flush();
			conn.getOutputStream().close();
			string = readContents(conn);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null)
				conn.disconnect();
		}
		return string;
	}
	
	/**
	 * 获取http 服务端的返回信息
	 * @param httpurlconnection
	 * @return
	 */
	public static String readContents(HttpURLConnection httpurlconnection) {
		String string = "";
		BufferedReader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(httpurlconnection
					.getInputStream()));

			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				string += inputLine;
			}
		} catch (IOException e) {
			e.printStackTrace();
			
		}
		return string;
	}
	
	/**
	 * http GET 发送客户端
	 * @param url 发送地址加参数
	 * @return
	 */
	public static String httpGetClient(String url){
		String result = "";
		BufferedReader in = null;
		//设置登录信息
		Authenticator auth = new BasicAuthenticator("aimAdmin", "aimadmin");
		Authenticator.setDefault(auth);
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 建立实际的连接
			connection.connect();
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}
	
	public static class BasicAuthenticator extends Authenticator {  
	    String userName;  
	    String password;  
	  
	    public BasicAuthenticator(String userName, String password) {  
	        this.userName = userName;  
	        this.password = password;  
	    }  
	  
	    @Override  
	    protected PasswordAuthentication getPasswordAuthentication() {  
	        return new PasswordAuthentication(userName, password.toCharArray());  
	    }  
	}  
	
	/**
	 * http文件下载
	 * @param httpUpUrl 文件下载地址
	 * @param saveFile 保存文件名称  文件名保存在当前程序运行目录下
	 * @return
	 */
	public static boolean httpDownload(String httpUpUrl, String saveFile) {
		int byteread = 0;
		URL url = null;
		try {
			url = new URL(httpUpUrl);
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
			return false;
		}

		try {
			
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			InputStream inStream = conn.getInputStream();
			FileOutputStream fs = new FileOutputStream(saveFile);

			byte[] buffer = new byte[1204];
			while ((byteread = inStream.read(buffer)) != -1) {
				fs.write(buffer, 0, byteread);
			}
            
			fs.flush();
			fs.close();
			inStream.close();
			if (conn != null) conn.disconnect();
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	public static String httpUploadByPath(String actionUrl,String path) throws Exception{
		// 产生随机分隔内容
		String BOUNDARY = java.util.UUID.randomUUID().toString();
		String PREFFIX = "--", LINEND = "\r\n";
		String MULTIPART_FROM_DATA = "multipart/form-data";
		String CHARSET = "UTF-8";
		// 定义URL实例
		URL url = new URL(actionUrl);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		// 设置从主机读取数据超时
		conn.setReadTimeout(10 * 1000);
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setUseCaches(false);
		conn.setRequestMethod("POST");
		// 维持长连接
		conn.setRequestProperty("connection", "keep-alive");
		conn.setRequestProperty("Charset", "UTF-8");
		// 设置文件类型
		conn.setRequestProperty("Content-Type", MULTIPART_FROM_DATA
				+ ";boundary=" + BOUNDARY);
		// 创建一个新的数据输出流，将数据写入指定基础输出流
		DataOutputStream outStream = new DataOutputStream(
		conn.getOutputStream());
		// 发送文件数据
		if (path != null) {
			// 构建发送字符串数据
			StringBuilder sb1 = new StringBuilder();
			sb1.append(PREFFIX);
			sb1.append(BOUNDARY);
			sb1.append(LINEND);
			sb1.append("Content-Disposition: form-data; name=\"file\"; filename=\""
					+ path + "\"" + LINEND);
			sb1.append("Content-Type: application/octet-stream;chartset="
					+ CHARSET + LINEND);
			sb1.append(LINEND);

			// 写入到输出流中
			outStream.write(sb1.toString().getBytes());
			// 将文件读入输入流中
			InputStream is = new FileInputStream(path);
			byte[] buffer = new byte[1024];
			int len = 0;
			// 写入输出流
			while ((len = is.read(buffer)) != -1) {
				outStream.write(buffer, 0, len);
			}
			is.close();
			// 添加换行标志
			outStream.write(LINEND.getBytes());
		}
		// 请求结束标志
				byte[] end_data = (PREFFIX + BOUNDARY + PREFFIX + LINEND).getBytes();
				outStream.write(end_data);
				// 刷新发送数据
				outStream.flush();
				outStream.close();
				// 得到响应码
				int res = conn.getResponseCode();

				InputStream in = null;
				StringBuilder sb2 = new StringBuilder();
				// 上传成功返回200
				if (res == 200) {
					in = conn.getInputStream();
					int ch;
					// 获取返回信息
					while ((ch = in.read()) != -1) {
						sb2.append((char) ch);
					}
				}
				if (conn != null) conn.disconnect();
				return sb2.toString();
	}
	/**
	 * HTTP 文件上传
	 * @param actionUrl 上传路径
	 * @param upFile 上传的文件名  文件在当前程序运行目录下
	 * @return  返回为JSON字符串  文件大小  jsonObj.getString("size"),文件URL jsonObj.getString("url");
	 * @throws IOException
	 */
	public static String httpUpload(String actionUrl, String upFile)
			throws IOException {
		String operSystem = System.getProperties().getProperty("os.name");
		String fileName="";
		File directory = new File(".");
		if (operSystem.indexOf("Windows")>0){
			fileName = directory.getCanonicalPath() + "\\" + upFile;
		}else {
			fileName = directory.getCanonicalPath() + "/" + upFile;
		}
		// 产生随机分隔内容
		String BOUNDARY = java.util.UUID.randomUUID().toString();
		String PREFFIX = "--", LINEND = "\r\n";
		String MULTIPART_FROM_DATA = "multipart/form-data";
		String CHARSET = "UTF-8";
		// 定义URL实例
		URL url = new URL(actionUrl);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		// 设置从主机读取数据超时
		conn.setReadTimeout(10 * 1000);
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setUseCaches(false);
		conn.setRequestMethod("POST");
		// 维持长连接
		conn.setRequestProperty("connection", "keep-alive");
		conn.setRequestProperty("Charset", "UTF-8");
		// 设置文件类型
		conn.setRequestProperty("Content-Type", MULTIPART_FROM_DATA
				+ ";boundary=" + BOUNDARY);
		// 创建一个新的数据输出流，将数据写入指定基础输出流
		DataOutputStream outStream = new DataOutputStream(
				conn.getOutputStream());
		// 发送文件数据
		if (fileName != null) {
			// 构建发送字符串数据
			StringBuilder sb1 = new StringBuilder();
			sb1.append(PREFFIX);
			sb1.append(BOUNDARY);
			sb1.append(LINEND);
			sb1.append("Content-Disposition: form-data; name=\"file\"; filename=\""
					+ fileName + "\"" + LINEND);
			sb1.append("Content-Type: application/octet-stream;chartset="
					+ CHARSET + LINEND);
			sb1.append(LINEND);

			// 写入到输出流中
			outStream.write(sb1.toString().getBytes());
			// 将文件读入输入流中
			InputStream is = new FileInputStream(fileName);
			byte[] buffer = new byte[1024];
			int len = 0;
			// 写入输出流
			while ((len = is.read(buffer)) != -1) {
				outStream.write(buffer, 0, len);
			}
			is.close();
			// 添加换行标志
			outStream.write(LINEND.getBytes());
		}
		// 请求结束标志
		byte[] end_data = (PREFFIX + BOUNDARY + PREFFIX + LINEND).getBytes();
		outStream.write(end_data);
		// 刷新发送数据
		outStream.flush();
		outStream.close();
		// 得到响应码
		int res = conn.getResponseCode();

		InputStream in = null;
		StringBuilder sb2 = new StringBuilder();
		// 上传成功返回200
		if (res == 200) {
			in = conn.getInputStream();
			int ch;
			// 获取返回信息
			while ((ch = in.read()) != -1) {
				sb2.append((char) ch);
			}
		}
		if (conn != null) conn.disconnect();
		return sb2.toString();
	}
	
	/**
	 * 删除文件
	 * @param fileName  文件在当前程序运行目录下
	 * @return
	 */
	public static boolean deleteFile(String fileName) {
		File directory = new File(".");
		String filePath;
		String operSystem = System.getProperties().getProperty("os.name");
		
		try {
			if (operSystem.indexOf("Windows")>0){
				filePath = directory.getCanonicalPath() + "\\" + fileName;
			}else {
				filePath = directory.getCanonicalPath() + "/" + fileName;
			}
			File file = new File(filePath);
			if (!file.exists()) {
				System.out.println("删除文件失败:" + fileName + "不存在！");
				return false;
			} else {
				if (file.isFile()){
					if (file.delete()) {
						System.out.println("删除文件" + fileName + "成功！");
						return true;
					} else {
						System.out.println("删除文件" + fileName + "失败！");
						return false;
					}
				}
				else
					return false;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 根据分隔符截取带路径的文件名称
	 * 
	 * @param inStr
	 * @param splitStr
	 * @return
	 */

	public static String getFileName (final String inStr,
			final String splitStr) {
		List<String> a = new ArrayList<String>();
		String str = inStr;
		if (str.substring(str.length() - splitStr.length()).equals(splitStr)) {
			str = str.substring(0, str.length() - splitStr.length());
		}
		while (str != null) {
			int idx = str.indexOf(splitStr);
			if (idx == -1) {
				String tmp = str.substring(0, str.length());
				a.add(tmp);
				break;
			} else {
				String tmp = str.substring(0, idx);
				a.add(tmp);
				str = str.substring(idx + splitStr.length());
			}
		}
		return a.get(a.size() - 1);
	}
	
	/**
	 * 生成请求ID
	 * @return
	 */
	public static int genID() {
		int rid = counter.incrementAndGet();
		if (rid > 99999999) {
			counter.set(1);
		}
		return rid;
	}

	/**
	 * 获取SEQUENCE
	 * @param id
	 * @return
	 */
	public static String getSequence(int id){
		String sequence=String.valueOf(id);
		int iLength = sequence.length();
		int strLen = 8-iLength;
		if (strLen <=0){
			return sequence.substring(0, 7);
		}else {
			String str="";
			for (int i=1;i<=strLen;i++){
				str = str +"0";
			}
			sequence =str + sequence;
			return sequence;
		}
	}
}
