package com.tydic.unicom.uoc.service.common.impl;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.imageio.ImageIO;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class ImgCompressUtil {
	private Image img;
	private String path = "D:/";
	private int width;
	private int height;
	private HttpURLConnection httpUrl = null;
	private BASE64Encoder encoder = new sun.misc.BASE64Encoder();
	private BASE64Decoder decoder = new sun.misc.BASE64Decoder();

	/**
	 * 按照宽度还是高度进行压缩
	 * @param w int 最大宽度
	 * @param h int 最大高度
	 */
	public BufferedImage resizeFix(int w, int h) throws IOException {
		if (width / height > w / h) {
			return resizeByWidth(w);
		} else {
			return resizeByHeight(h);
		}
	}

	/**
	 * 以宽度为基准，等比例放缩图片
	 * @param w int 新宽度
	 */
	public BufferedImage resizeByWidth(int w) throws IOException {
		int h = height * w / width;
		return resize(w, h);
	}

	/**
	 * 以高度为基准，等比例缩放图片
	 * @param h int 新高度
	 */
	public BufferedImage resizeByHeight(int h) throws IOException {
		int w = width * h / height;
		return resize(w, h);
	}

	/**
	 * 强制压缩/放大图片到固定的大小
	 * @param w int 新宽度
	 * @param h int 新高度
	 */
	public BufferedImage resize(int w, int h) throws IOException {
		// SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的 优先级比速度高 生成的图片质量比较好 但速度慢
		BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		image.getGraphics().drawImage(img, 0, 0, w, h, null); // 绘制缩小后的图
		// File destFile = new File(path + "comPicture.jpg");
		// FileOutputStream out = new FileOutputStream(destFile); // 输出到文件流
		// // 可以正常实现bmp、png、gif转jpg
		// JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
		// encoder.encode(image); // JPEG编码
		// out.close();
		return image;
	}

	/**
	 * 从URL中读取图片,转换成流形式.
	 * @param destUrl
	 * @return
	 */
	public void saveToFile(String destUrl) {
		URL url = null;
		InputStream in = null;
		try {
			url = new URL(destUrl);
			httpUrl = (HttpURLConnection) url.openConnection();
			httpUrl.connect();
			httpUrl.getInputStream();
			in = httpUrl.getInputStream();
			img = ImageIO.read(in); // 构造Image对象
			width = img.getWidth(null); // 得到源图宽
			height = img.getHeight(null); // 得到源图长
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void closeHttpConn() {
		httpUrl.disconnect();
	}

	/**
	 * 读取输入流,转换为Base64字符串
	 * @param input
	 * @return
	 */
	public String generateImageBase64(BufferedImage bi) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(bi, "jpg", baos);
			byte[] bytes = baos.toByteArray();

			return encoder.encodeBuffer(bytes).trim();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * base64字符串转化成图片 对字节数组字符串进行Base64解码并生成图片
	 * @param base64String 数据内容(字符串)
	 * @param path 输出路径
	 * @return
	 */
	public void generateImage(String base64String) {
		try {
			byte[] bytes1 = decoder.decodeBuffer(base64String);
			ByteArrayInputStream bais = new ByteArrayInputStream(bytes1);
			BufferedImage bi1 = ImageIO.read(bais);
			File w2 = new File(path + "genPicture.jpg");// 可以是jpg,png,gif格式
			ImageIO.write(bi1, "jpg", w2);// 不管输出什么格式图片，此处不需改动
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
