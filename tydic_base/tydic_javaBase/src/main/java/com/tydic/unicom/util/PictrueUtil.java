package com.tydic.unicom.util;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.MemoryImageSource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/*
 * 图像处理类
 * 
 * @author yangzhifan 20151231
 * 
 */
public class PictrueUtil {
	
	/**
	 * 将BMP格式的BASE64码转为JPG格式的BASE64码
	 * @author yangzhifan
	 * @since 20151231
	 * @param base64Bmp BMP格式的BASE64编码
	 * @return JPG格式的BASE64编码
	 */
	public static String base64BmpToJpg(String base64Bmp) {
		
		if (base64Bmp == null || "".equals(base64Bmp)) // 图像数据为空
			return "";
		
		try {
			//1. BMP base64 --> BMP byte[]
			// Base64解码
			BASE64Decoder decoder = new BASE64Decoder();
			byte[] bytesBmp = decoder.decodeBuffer(base64Bmp);
			for (int i = 0; i < bytesBmp.length; ++i) {
				if (bytesBmp[i] < 0) {// 调整异常数据
					bytesBmp[i] += 256;
				}
			}
			
			
			//2. BMP byte[] --> JPG byte[]
			ByteArrayInputStream bin = new ByteArrayInputStream(bytesBmp);
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			
			Image TheImage = read(bin);
			int wideth = TheImage.getWidth(null);
			int height = TheImage.getHeight(null);
			if (wideth<0) {
				wideth = -wideth;
			}
			if (height<0) {
				height = -height;
			}
			BufferedImage tag = new BufferedImage(wideth, height,BufferedImage.TYPE_INT_RGB);
			tag.getGraphics().drawImage(TheImage, 0, 0, wideth, height, null);
//			JPEGImageEncoder imgencoder = JPEGCodec.createJPEGEncoder(bout);
//			imgencoder.encode(tag);
			ImageIO.write(tag, "JPEG" , bout);
			
			byte[] bytesJpg=bout.toByteArray();
			
			//3. JPG byte[] --> JPG base64
			BASE64Encoder encoder = new BASE64Encoder();
			return encoder.encode(bytesJpg);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "";
	}
	
	/*
	public static void main(String[] args) {
//		String srcfile = "D:\\1.bmp";
//		String dstFile = "D:\\1.jpg";
//		bmpTojpg(srcfile, dstFile);
		
		
//		String strJpg = "/9j/4AAQSkZJRgABAQEAYABgAAD/2wBDAAMCAgMCAgMDAwMEAwMEBQgFBQQEBQoHBwYIDAoMDAsKCwsNDhIQDQ4RDgsLEBYQERMUFRUVDA8XGBYUGBIUFRT/2wBDAQMEBAUEBQkFBQkUDQsNFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBT/wAARCAB+AGYDASIAAhEBAxEB/8QAHwAAAQUBAQEBAQEAAAAAAAAAAAECAwQFBgcICQoL/8QAtRAAAgEDAwIEAwUFBAQAAAF9AQIDAAQRBRIhMUEGE1FhByJxFDKBkaEII0KxwRVS0fAkM2JyggkKFhcYGRolJicoKSo0NTY3ODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqDhIWGh4iJipKTlJWWl5iZmqKjpKWmp6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uHi4+Tl5ufo6erx8vP09fb3+Pn6/8QAHwEAAwEBAQEBAQEBAQAAAAAAAAECAwQFBgcICQoL/8QAtREAAgECBAQDBAcFBAQAAQJ3AAECAxEEBSExBhJBUQdhcRMiMoEIFEKRobHBCSMzUvAVYnLRChYkNOEl8RcYGRomJygpKjU2Nzg5OkNERUZHSElKU1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6goOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3uLm6wsPExcbHyMnK0tPU1dbX2Nna4uPk5ebn6Onq8vP09fb3+Pn6/9oADAMBAAIRAxEAPwD9UKKKKACivMfj58fdB/Z+8Jx6xrMct3LcSeTbWdvjfK+Ce5wAACST6euAfzU+Mv8AwUA+IfxMmntbC7HhjSGPyW2msRJjj78vVj16beD0NOxLdj9T/GvxU8IfDq2E/iTxFp2jozbFFzOqszYztAzkng8e1fO/j3/gol8PtFmhttBkutTlYMZJmt2SNAOg55JP0r8urvxXe67cyXOp3s99cH701xIZHf6seTVCWSG6D7d6DPAC07GkHGSufouf+Cm2nowUaG0w7ushGfw2f1rrPDX/AAUV8Pauqvc6Hd2wBG6PzEZiO5Hbivy/tIWR40RZHz1O010UOl6hYgXAgmVMdfLOPzrFyszoUObZH7KfDr9ozwL8TEhTS9ZjivJW2LZ3QMcrHGTgHrx3Hv6GvTa/C/QvidqXhu9Vo3dWjcPFtcqQR0II5BB71+lH7I37W0XxSsbLw54lZLbxGyf6NIv3bpR2/wB8Acj8RxkLSkmZzp8p9UUUUVRiFFFFABUV3dR2NpNcynbFCjSOfQAZNS1z3xE1ODR/AfiG8uWKwRWMxYqMkZQjp+NAH46/tUfG7UfjP8U9Zv5bqRtIt5jBYQM+UiiHQqO27G49+QD0FeIMkbtwCx9AKt6hH52oAL8wkbHA719C/B39naDX7GHUNRTYpGcEDP61nUqxpq7OmjhpVpWR4DYaHe6kVWztJpXxziM16P4S+Dmt6uIfMtJoiw5yhGP0r7M8EfC/QfC5CwWUTt/z0K4P869T0/S7RQAsaj3xXmvF83wnuU8rjH4j5d+Hn7N/lzwyXsRcDBIZa95n+EGh3Ojtp8unxeUeM7f/AK9ek20MVvHwamcRyREk1yyrTbPUjhqcI2R8OfF39kV9Pun1HRbom1JyIW2qI/YflXjnh/xBrfwl8cWTxTmO5srhZIgrEruBGAduDtPQjuCa/SbX9Ij1axmt8ZVwQCex9a+G/wBoH4N3fhPUpdX+0SToZMlmA5yfaurD1eZ2keZi8Nyxc4o/Vz4ceL4vHvgbRdfiBVb+1SYoSMqSOQccA+1dJXzR/wAE/PEg1r4B21octLY3Uis2cjDneoH0BFfS9esfNMKKKKBBXA/HvzP+FM+MDF98adKR+Vd9VLWtOXWNHvrFsAXMDw5IyBuUjP60DW5+GHgfwpN4k8cW1sE3wqfMOPYg19u6XfW3hzSUid9qIMCvD/ht4B1HwB8cNX0HUBultPNTeBgOu0FWx2ypBxz1r3LUdHtWjMl5GXgUHIC7j+VeLiJ3ml0Pq8FSUYNrc09G8f6QxUyzsB9K9E8P+JNH1NQ1vcqw9CRn+dfO+s+JdG8NaeJ4fCEOrW3mKnz70c5PsDXoPhy007U7OC/stJXQ5WiWRraHLKNwDDk45APpWXJFRuj0oyk5WZ7ZLPai33h/lyMmsPWfiF4f0SF45JJXlH8Mce7+VUNNjfUNFZS27OOTXlXxJ1S68LWMs2l6FDqN5DIqkyFgT64x1qIWk7DqJrY7eL4rW1/dKlvaziI/xNEyms/4xaDbeN/hvfOIi8sS713LzgAmsrwF4o1XX7PTpdR8OwWAuItzeWXLKc9wa9J12CODwfq/lqdi2kpIYY42NVtKEk0ZO84OLPOv+CZetXIi8W6Gzk2sMcN0q+jsSp/RRX3XXyv+wf8ACdPBXhTVtfnkxqOqSeU8G3HlRoTtHvnk/j+J+qK9yDvFM+MrQ5Kjj2CiiirMAqK6iM9tLGrbGdCob0JHWpaKAPz38S6LJY/ERLrk3MLzWskxzmQ5OWbPrtNdxpcCX8IRxuz2NdJ+0B4Tl0nx499EpFteqsqsSMbhwwH04/OuW0O48i4QZ4r52quWXK+h91SalCNSP2lc6KHwLYXMAV4FwGDdPSn6ta22j2zBAEYjAArZtdVUJ8zcVxfiXWLeXUZ2upH+zx52qq5J/ChP3bHRGPvXN/wtKTpewexratNCtNQYTP8A61eBxXH+FvFunrY+bHHKUx0aMhvyrotH8R2t/IXtDLGNwyroVqYrldyqqbOni0SOIAiMEjvWb4htPOsLm2b/AFUsbRsPUEEEfrW6NR/dnDVlX8jXRIHPFVJ3dznjdOzOx/Z+sJbDwxeRybinnjYzDGQFHT8a9SrnvAWkPonhWyt5AyylfMdW6hm5Iroa92lFxppM+Oxc1Urzku4UUUVqcgUUUUAY/ifwrp3izTntNQto5xg7GYco2MZB7Gvke60p9E1i6s58iSCVo/mXGcHrj36/jX2hXzj+0L4ZbRdfi1uIH7NffK59JQP6gfp715uNp3jzroe1ltdxm6Tej2OHk1Mwrw1c7quqWIlM1y0IkHGS43flSRXqXkoQvjPeoL/wFpep3QluIVmfP3mHNeTFn1dN23NLRvE2kxMJAyFum49f510en63ZTOzwyxFnOeXAP5Vy9l4D0yOUKunxFP7xFdFa+BtHtpVnWzjWUdGA5FbNaXLnJdDrbK/81ACetdh4B02LUfEcKyjcsSmXHYkdM/nXnkTJbSAKea9p+E+ktFpsmoyghrj5UB/ujv8Aj/LFXh1z1EjyMbUVOjJ9Xod8AAAAMAUUUV758cFFFFABRRRQAV5b+0TqWnWvgJ7W7uYYbm4mQQRyOA0hByQo78An8K9Sr8tv2sPiJe+J/wBo6C6Msv2Gwf7Lbws3yR7W2swGcbjk5PcBfSsqqvTkjqwv8aL7M7d7mawuxIwwi+ldNpni20kjBeYKf9o4rGBTU7feRuU81j3XhWK+UmECNz0Ir5nZn2ibb0PV7HxTp/lYM6/mKuP4o09Y/wDXgn0BFeO6Z8MdWuJFH2vbGe+8ZruNH+GcdgFNzO1w47sAf5Vs5aWKdzpLO9Oo3ivFkwnua+n/AADdwXPhWwSFwxhjEbjuCK+c9NsYdMtyEUIoHYVwXwH+K0vh79pfxZpc13MdKvZo4/I3ZjWTyo/mx2OScn3Gegx1YJ/vGl2PKzClz0b9j7qopFYMAQQQeQR3pa9s+UCiiigAqpqmr2Oh2cl3qF3DZW0YLNLO4VQPqa+c/Hn7U08sl7YaFaS6eIgwN3OoaTp1VRkDt1z9K+WvGHxQvfGmpRtrWsXt9hwyxyn5QenC9P071tClzbl8jtdn1H8Xv2uNCj0q80nwndSz6m26J7owsqxjkZXI5z2PTvz0PxZq2irqC2uqaiTcTRnbJK53MXZs7ie571oaHe2OrePdXtC7Yjgby9y4JYAHB9gWas9r8Zu7G9kaOAguo6/OB8v61cqKTsjphJRhod/8Mdf/ALR0r7JM5F3DhJA3GTjt612SpJaXaRkbUNeF+Ctdnn1q3CEpcBVRtvUuoG78/lP1J9K+gLW/h1u2RyFjuY8Ajtn2PevAxGGdOd+h9FhcQpR13Oq0mFFjHtWxEzGcKo+WsHTGkSIb8Z9jW2L2OytWuZG2onJNcigm7HoOempB4w1aHQtDu7iVwmxDt5/iwcV80eAriHUPFiXs/wC6u70XN+86jLEsduw+37v8jXUfEfxjN4v1KaCJz/ZkbY+Xnf6E1zWk26adHeX8UPlPEUhgRQeU2AMfxIz+Ne1gqCg+ZnhYzENvkiz67+H/AO0pHo+h2NlrlnLOECxi5t23NjOMsCcnHtknn6V7D4T+LXhTxqn/ABK9YgeUAloJT5cigccqelfDPh68aTT7KdbbzZ1mjkKuCMgHJq1qKnSL+9jsYPLc4kUIDjG3PX8a9GVNc1jxpRT1P0KR1kUMjBlPQg5Bor84rH4x+LtL4s7y7iAXaDFeSpgenB6cCij2D7mfJ5m945urey1q9W2gTcwYuPw5rym207S9W1KGUAJgbiAPTmu98T6oLrxRqgNuikxyjIJ/u14no2vPp+ty2whR1EbDJJ9KukatXibFna2tj8QtQ1K2dZMyMuxiBlSBmqfxBt3udTEsECxxud6henBrM0B1vNduptgQ5PA+ldf4giluvDyzLsj8p1i8wE7xk9u1dH2jmT6HnniPxHJ4Ps4L7Tcf2rOu6Qk4KPnAYH1HFTfD74z+I/DwtP7ZUanatDs8yXhlO4fN8oGTwevrVPXNMii1Dypx9rOc75BzVrxHbQ2fhGK5jiUFZo4wvbBNTVoxmtQVedKXun014D8dp4hsluIpG+zHGWk+U1P478Xi+hGkafcRp5g/eSs4VQO/PToa8Y+HeuTHwhraIojS2YBAD/sE1g+GtVl8QaBrL3YDmNIGGTn78m0/oP1rzHg4p3R69PHuUbPcZZ/GXw3pOuSaCwkMeSFufLPzY7nnHevXPP07UNF02XTrgTAxZYnAOc/WvAfFng/RrS8QJp8O8gnfg5qvo/iy68GlFh/fQbwqxscBAeOK7oRSscM5NybZ9heB7VFjtzPwpgZvxpdavra0uw0R3v8AZGaQHgfNnH8q4rwj4yuJrGxlwZY3gOFkODj3xTfEviIyalMi2scebaMZUn/nktN/GNamRZanaOr7kKYPARSRRXI2niCSzj2CFH9yTRWxfIj/2Q==";
//		GenerateImage(strImg, "D:\\123.jpg");
		
		String strBmp = "Qk3OlwAAAAAAADYAAAAoAAAAZgAAAH4AAAABABgAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAsK2xm5ich4OKhX+GhHuEkYiRoJSgjX+MfGt6cGBtZVViZFJdZFBbc2Bpg3B3fGlud2FnbVpeZlNXbl1ed2ZnfW9vhnh4hXl5hnp6h3t7i319mIqKqJeajn2AdWJngWtxjXV7hG1xfGVnc11da1VVbVhVcFxXc19Yd2RbfGtignNrintzkoJ9b2FbTkA6Wk5IaV1XY1dRXVBMgHNvpZWUn4+OmYiJemlqXEtMYVJVZlldXE9TlYeNoZWdbGBqeW13hnqGgnaAfnJ8cmZuaFliZ1hhZ1hhZ1lfZ1lfZFZcZFRaYlJYYk9WWkdOUj9GXktSbFdhhHF6nYqTl4aPkoOMl4uTnpKcmI+YlYyVr6avin6IiXyGiXqEjn+Jk4OQiXeEgGp6f2l5fmh4fmh4fmh4fmh40gqwrbGbmJyHg4qFf4aEe4SRiJGglKCNf4x8a3pwYG1lVWJkUl1kUFtzYGmDcHd8aW53YWdtWl5mU1duXV53Zmd9b2+GeHiFeXmGenqHe3uLfX2IenqId3p+bXB1YmeBa3GNdXuEbXF8ZWdzXV1rVVVtWFVwXFdzX1h3ZFt8a2KCc2uKe3OSgn1vYVtOQDpaTkhpXVdjV1FdUEyAc2+llZSfj46ZiIl6aWpcS0xhUlVmWV1cT1OVh42hlZ1sYGp5bXeGeoaCdoB+cnxyZm5oWWJnWGFnWGFnWV9nWV9kVlxkVFpiUlhiT1ZaR05SP0ZuW2KLdoCTgImdipOXho+Sg4yXi5OekpyYj5iVjJWvpq+KfoiJfIaJeoSOf4mTg5CJd4SAanp/aXl+aHh+aHh+aHh+aHjSCry5vaekqJKOlY+JkI2EjZiPmKWZpY6AjXlod3NjcG5ea3BeaXJeaYFud5F+hYl2e4JscnViZmpXW25dXnRjZHttbYV3d4R4eIZ6eot/f5KEhI+BgY9+gYBvcnNgZXZgZnpiaHdgZHVeYHJcXHBaWnFcWXJeWXVhWnlmXX5tZIN0bIZ3b4l5dG9hW1ZIQmFVT25iXGpgW2leXIZ6eqSXmp2Qk5eKjn5wdmVWX2ZaYmlcZmhcZot/iZ6SnG9jbXhsdoF1gX5yfHtveXNnb2xdZmpbZGhZYmVXXWNVW15QVltLUWJSWGxZYG5bYnFeZX1qcYt2gJaDjKKPmJ2MlZmKk6KWnqygqqeep6SbpL61vpiMlpaJk5aHkZmKlJyMmY58iYFre4NtfYZwgIhygot1hYt1hdIKyMXJsq+znZmgmpSbl46Xn5afqp6qj4GOdmV0dmZzdmZze2l0gW14j3yFnouSlYKHjnh+fWpubltfb15fcmFiemxshHZ2hHh4hnp6j4ODm42NmIqKl4aJhHN2cl9kbVddaFBWa1RYbldZcVtbdV9fdF9cdGBbeGRdfGlggG9mhHVtgnNrgXFsb2FbX1FLaV1XdWljdWtmeGxskIOGqJqgnpKaloqUg3eDcGNxb2JwbmFveWx6hXiGnZCedGd1eGx4fHB8em54eGx2dGhwcWJrbV5naVpjZFZcYFJYWEpQU0NJZFRad2RrhHF4kX6FjnuCjHeBmYaPqJWepJOcoZKbrKCoua23tay1s6qzzMPMppqkpJeho5Seo5SepJShk4GOg219iHKCjniIk32NmYOTmYOT0grEwcWtqq6Xk5qTjZSQh5CbkpuonKiNf4x0Y3J1ZXJ2ZnN7aXSBbXiOe4SbiI+RfoOJc3l5ZmprWFxsW1xvXl94amqDdXWDd3eGenqPg4ObjY2ShISLen2BcHN5Zmt3YWd2XmRzXGBwWVttV1drVVVoU1BmUk1qVk9uW1J1ZFt9bmZ6a2N4aGNrXVdfUUtjV1FqXlhzaGZ/cnWTh42onKaelKCUi5qEfI92bIRzaoBzaHx7cYOFeYuLf49yZ3VrYW1mWmZiVmBeUlxZTVVVRk9VRk9VRk9VR01VR01dT1VnV11zY2mBbnWKd36UgYiVgomYg42kkZqyn6itnKWomaKxpa27r7mxqLGqoarFvMWglJ6fkpyfkJqik52llaKUgo+Ebn6IcoKNd4eSfIyXgZGXgZHSCsG+wqmmqpKOlY2HjomAiZaNlqWZpYt9inJhcHRkcXdndHtpdIBsd4x5gpmGjY57gIVvdXViZmdUWGlYWW1cXXZoaIJ0dIN3d4d7e5CEhJyOjo1/f4BvcoBvcoFuc4Ntc4Vtc3tkaHJbXWlTU2BKSlxHRFhEP1xIQWFORWxbUnhpYXRlXXFhXGhaVGFTTV9TTWBUTnJnZYR5fZeMlKqfrZ+Vp5WMoouCm4F4mH50kXtwjH91jYV6kHtwhHNneWFWZFFFUUo+SEQ4Qj4yOjkqMz0uN0EyO0U3PUo8QmFTWXpqcIJyeIx5gJJ/hpiFjJ6LkqWQmrCdpr2qs7alrrChqraqsr2xu66lrqGYob20vZqOmJqNl5yNl6GSnKaWo5WDkIVvf4lzg413h5F7i5aAkJaAkNIKwb7CrKmtmJSbkoyTjIOMlYyVoJSgi32KeGd2e2t4fm95gXB5hHF6j3yDm4mOlYKGkHt/f21ucF5fdGNkeWhpfnBwhnh4h3t7i39/j4ODlYeHh3l5e2ptemlse2htfGZsfmZsfGdrfGhpbVtcYU9QWEhEUEA7U0M+VkZBYVNNbV9ZZ1pWY1ZUXFFPWExMWE5OW1FRbWNnf3Z/kYmVpJ2soJmsnJatl5KtlI2ujIajh4CbhHuUgHaOhHuRiICTg3qJXlNhTkRQQTVBOCs1MSIrPS43SjtEVUdNYFJYd2lvj4GHkoSKl4eNmIiOnImQnouSoYyWrZqjuqewtKOsr6Cpt6uzwba+s6qyqJ+oubC5rKCqlYiSopOdpZagqZqkmIaRiHOBiXSCinWDjnmHkn2Lkn2L0grCv8OwrbGemqGXkZiQh5CUi5Sbj5uMfot/bn2Ccn+FdoCGdX6IdX6Sf4adi5CciY2ciImJd3h4Zmd+bW6GdXiHeHuLfH+Mf4KQg4aOgYSPgIOBcnV2ZGl1Y2h2Y2h3YWd5YWd/am6GdHV1ZGVlVFVYSEdMPDtNPjpOPztXSkhjVVVcUFBXSk1TSUtQRkpSSk9WTVVoYWp6dIGNiJafmq2gnLOkn7qlor6mo8SbmbiTj66GgJ16c46Nhp+imrGnoLNrY3VTSlk+MT8yJS8oGSI9LjdTRE1kVlx1Z22Nf4WmmJ6jlZujkpugj5igjZaei5SeiZOql6C4pa6zoquvoKm6rrbGu8O5sLivpq+1rLW+sryRhI6omaOqm6WsnaecipWMeIOKdoGIdH+KdoGNeYSNeYTSCsPAxLOwtKSgp5uVnJKJkpOKk5aKlo1/jIZ1hIl5ho1+iIx7hIx5gpWCiZ6MkaKPk6eTlI58fXhmZ31sbYNydYR1eId4e4x/gpOGiox/g4h4fXtrcHFfZHBeY3FeY3FbYXJaYH9scJB/gntsb2hZXFZISEU3N0U3N0U3N01AQ1hLT1BFSUpARVBHT1dQWWNeaXBreHl2hIWBko+Nn5mYrpyctKKhvaamxKupy6WkxKCdvpCPq4J/moaCmoqDnJWPpoJ6kGhgclJHVVBETlBBSl9TW3FlbX1xd4l9g56SmLaorqqcop6PmJ2MlZ2Kk5uIkZqFj6eUnbajrLGgqa2ep7uvt8vAyL20vLKpsrqxusW5w6SXoaaXoaqbpa6fqZ6Ml497ho56hY15hJN/ipmFkJmFkNIKw8DEt7S4q6euoJqhlo2WkomSkYWRjoCNjXyLkYGOlYaQkoGKkH2GmIWMoI6TqJWZsp6flIKDeGZne2ptgG5zgHB1g3N4jH+Dl4mPjX2ChHJ3eGZrbVtga1lea1hdbFZcbVVbgm90m4mOg3N4a15iVUhMPzI2PTA0Oy4yQzc9TEFJRDtEPDRATUdUYFtpdXGCi4mbjIugjo+lkpKqlpWxnJy6oaLCp6fJrq7Sra3Pra7OnZ27jYuqfn2ZcW6JhICYmJGqf3eNZ11vcGRweGlyhHiAkYaOl42SnpSZsKarxLjArqKqm4yVmomSmoeQmIWOl4KNpJCbs5+qsJ6prZ6ovrG70cXPwrnCtq22wLfAzMDKt6q0pJWfqpulsaKsoY+akn6Jkn6Jk3+Km4eSpJCbpJCb0grHxMi4tbmppayinKOck5ybkpuckJyWiJWSgZCRgY6RgoyMe4SIdXyOfIGUg4afjY6smJmMentuXF1wX2JzYWaAcHWPf4SXio6ilJqVhYqIdnt7aW5vXWJsWl9qV1xuWV1zXGB+a2+Ne4B9bXJuYWVlWl5eU1dKP0M3KzFFOkJSSFRTS1hUTVx6doeBfpOGhZuMjKaPkKqRlK6VmLOZmrqcn7+ipcalqc2qrtOrr9OusdKipcWZmrqIiKZ3dZR7eJR/epV7dot5cYOBd4OLfIWPg4uUiZGSio+Sio+jm6C3rLSonKSai5SZiJGZho+XhI2Xgo2jj5qxnaiunKesnae6rrbKv8e/tr62rbW8s7vEucGzp6+klZ6un6i5qrOpmKGahpGZhZCYhI+ciJOhjZihjZjSCsvIzLm2uqikq6SepaGYoaOao6icqJ+RnpiHlpODkI5/iYd2f4BtdIRyd4l4e5eFhqaSkoRzcmVTVGVUV2ZUWX9vdJqKj6KUmq2ep52Nk416gX9tcnFfZG1aX2pUWnFcYHpjZ3pna35scXZpbXFla3dtcn5ze1lOVjQoMkQ8SFdQX2FbbGtme6emvKOjvZmZtY6Pr5CTs5KYt5ecvZygxJ6kx6GnyqSpzqis1Kuw1a+z16yu0qmpzZKStH59nXBvj2Rhgnh0jI6GmZaLmaCRmpuPl5eMlI6FjYaAh5mQmKyhqaKWnpmKk5mIkZmFkJiEj5iDjqOPmq+bpq2bpqydp7ers8S5wbyzu7attbeutruwuK+jq6SVnrKjrMGyu7GgqaKPmJ+MlZ2Kk56LlJ+MlZ+MldIKv7zArKmtmpadnJadnpWeopmiqJymmoyZjn2MiHiFg3R+fWx1eGVse2lufm1wjnx9oIyMf25tYE5PYE9SYU9Ufm5znY2TnY6Xn5CakoGKhXJ5fGpvc2FmbVpfaVNZdF9jgWpsdWJma1tgbGFlcGZreW52g3eBgnaCYlVje3KBlY6hiIWafnyUm5u3mZq6k5i5kZa7k5q/l57Dm6LHn6bNoKfOoqnQoarRo6zTqK/WrbHZrLHWrLDUnJ7CjIywd3eZZGKEcW6JgXmPjoORnI2WlYqSkIePiYOKgn6FkoyTo5qinJGZlYmRloeQl4WQmISPmYSPoo6ZrZmkrJqlrJ2ntKiwvrO7ubC4tq21tKuztKmxq5+npJWesqSqwbO5rZ2jmoeOmYaNmYaNm4iPnYqRnYqR0gqzsLSgnaGNiZCUjpWck5ygl6Cnm6WVh5SEc4J+bnt4aXN0Y2xxXmVyYGV0Y2aGdHWahoZ5aGdbSUpbSk1dS1B9bXOgj5iYiZKSg42Id4B+a3R6Z253Y2puW2BnUVd2YWWIcXNxXmJbS1BkWF5uZW16cXqGfIiroK6Pg5ORiZyUjqWSj6qSkK+RkrKPk7eSmcCWncqZocudpc+gqNKkrNakrNakrNajq9Wjq9WlrdaosNmttNuytt6lqdGZnMR+f6VjYohqaId0boWFfIuYiZKRho6Mg4uGgIeBfYSOiI+ck5uZjpaViJKWh5GXhZCZhZCahZCijpmsmKOsmqWsnaexpK64rLa2rbW2rbWwp6+toqqonKSllp+zpavCtLqqmqCSf4aUgYiWg4qYhYybiI+biI/SCrOwtJ+coIyIj5KMk5qOmJ6SnKKWoJGDkIFwf31tenlqdHdmb3ViaXVjaHZlaIZ0dZeDhHlnaF1KTmBPUmVTWIR0eqOUnZiJk45+i4N0fnpoc3dmb3dka3ZkaXVjaHdmaXtobGxbXl1NUnFla2hcZndteYd/jJuUo5CJnI2JoIqKpJGRrZiZuZWau5Wav5edxpmgzZujzZ2lz5+n0aKq1KCq1KGr1aGr1aGr1aOu1qiw2a2027K23qis1J+iyouMsnd2nHVzknVxiYF5i4+Dj4qBioZ/iIJ+hYB8g4aAh42EjJSJkZqNl5WGkJB+iY97ho55hJiEj6SQm6aUn6iZo6ibpaqeqK2krLKpsbivt6GWnp6Smp2Ol6eZn7KkqqGRl5B9hJF+hZJ/hpOAh5SBiJSBiNIKs7C0npufjIaNkYiQl4uVmo6YnZGdjX+Mf259fGx5emt1eWhxeWZteWdseWdshXJ2k3+AeGVpYE1SZlRZbVphi3qDqZqkmYmWiXiHgHB9d2hydmdwdWZve21zg3N4eWlucmBlZ1ddX05XgXJ8ZFNidWl5h3+RjIeakZCmiYuigoWgkJSynqPEnKLFnKDIm6HKm6LPnKTOnqbQoKjSoqrUoKrUoKrUoavVoqvYpK7YqbHarbPcsrbfq6/XpajQmJnBi4mygH+fdnOOfXiNg3uNgXqJgHqHf3qFfnmCfneAgXV/kIONoZKck4SOiHaBhXF8gm14j3uGnYmUoY+appehoJOdnJCapJukrqWuwbjAlImRlIiQlYaPnI2Wo5SdmIeQjnuEjXqDjXqDjXqDjXqDjXqD0gqzsLSem5+Mho2VjJWfkZ6jlaKnmaaYipeMe4qJeYaGd4GFdH2FcnmGdHmHdXqQfYGbh4iPfIBmU1hrWV5xXmWejZarnKaWiJWDdIN7bXpzZnBzZnB0Z3GCdn6ShIp8b3NmWV1qXGJvYGpxXm10XXF8bYGFe5OEgpqGiaOKj6qOlLOWnL2fpciaocaWn8aVncaUnMaXn8mbo82dpc+gqNKfqdOgqtSfq9Whrdmlr9mpsdqrs9ywtt+rstmprdWipc2dnsaJiat3dZR3dY16dYp8eImAe4mCfomFgIl9dn93a3WOgYunmKKXiJKJd4KIdH+GcXyTf4qhjZimlJ+rnKaekZuShpCgl6CxqLGzqrOYjJaajZeej5mdjpicjZeUgo2MeIOKdoGJdYCHc36Gcn2Gcn3SCv7+/t3b3crFyqmYp6mYp62cq7KhsKaVpJqJmJeHlJSFj5OCi5J/iJSBiJeEiZ6JjaaPk6qVmWxZXnBdZHZjbLKhqq2eqJWHlH5xf3hseHJmcnNncXVpc4p/h6CVnX1zeFtRVW5iaoJyf2BMXYFofoFwiYN5lX5+mnuCoYqTs5ujxpulyJ2ny5mjx5afxpKaw4+XwZObxZefyZujzaCn1J+o1aCp1p+r16Gt2aWv2aqy3Ky03bC236+13rC03bCz27Gy2pWVuXp5mXV0kHFuiXh1in97jIWAjY2Gj310fW1ha4x/ia2eqJqLlYp4g4p2gYp1gJeDjqaSnaqYo6+gqpuOmIl9h56VnrWstaeep5yQmqCTnaaXoZ6PmZaHkZB+iYp2gYdzfoVxfIJueYBsd4Bsd9IK/v7+/v7+9PL06eTo1MvTsqGwsqGwp5alnIualYWSj4CKj36Hj3yFj3yDkH2CmoWJpY6SmoeMc2BndGNseGZxno+ZppajkoOSf3GBeWx6dGd1eGx4fHB6hnuDkYaOd21yXlRYgnd/d2p4aFlsfWyDe3GKeXaSgIOjiJK1kpy/nafLmqTImKHIlZ7FkpvCkZnCkZnDlZ3HmaHLnKTOoKfUn6jVoarXoKzYoq7apa/ZqbHbqrLbrLTdrrTdsLTdsLTcsrXdoaLIkZG1gIGhcXGPdXOLenWKf3qIhX2JkomSb2NthnmDno+Zl4iSkoCLkX2IkHuGm4eSpZOeqpulsaKsoZSekoaQs6qztq22qJ+onZGboZSep5iioZKcnI2XmYeSloKNloKNloKNloKNl4OOl4OO0gr+/v7+/v7+/v7+/v718/Xq5urSytGejZyejZyUhJGLfIaLeoOLeIGKd36Ld36YgoiljZOOeoF7aHF6aXJ8anWMfImdjp2OgJCBcoV7bX12aXd8cHyDd4GCd3+Bdn5xZ2xiWF2XjpdsZHFxaXt5b4dzcItvcJCDiayZocqdpc6iqtObo8yUnMWRmcKPl8CRmcKUnMaXn8mbo82eptCiqdahqteiq9ihrdmjr9umr9ypsN2qstystN6vtd6ytt+zt+C1t+GtsNilqc6LjbFxcZVycY11cYh6dIWAdYOnnalxZW+Ac32QgYuWhI+ciJOYhI+VgIueipWmlJ+rnKayo62mmaObj5nJwMm4r7iqoaqekpyilZ+omaOllqCjlJ6ikJuijpmlkZyolJ+rl6Kvm6avm6bSCv7+/v7+/v7+/v7+/v7+/v7+/vPx8+fj59nT2MnByKucppuKk4t4gYp2fYt1fJR+hKCIjpB8g4NweXBeaX9vfId5hpGEkoh8jIF1h31xgXpvfYV7h5CGkoF4gXVpc3NocHJobYaBim1qeG1sgW9viXN3lXqAo4iRuJihzpqj0Jyl0pSdyo2Ww4yWwI2Vv5GZw5aeyJmhy5ykzp+n0aKp1qGq16Os2aKu2qOv26av3Kmw3aqy3Kuz3a603bK237S44bi65LC03Kmt1ZicxIeLs31+nnRxjHRtgHRpd42Dj3hseHxveYJzfZOBjKSQm56LlJqFj5+MlaSSnaaXoaucpqGUnpmNl7+2v6WcpZuSm5SIkp2QmqiZo6iZo6mapKyapa+aqKuXoqiUn6uXoq+bpq+bptIK/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+9PP06OXn2dLWxLm9jXR8jXR8lXyEnYSMkn2Hi3eCZlRhhHOChHWEhniIg3eHgnaIgXWFgXWFkIWTn5Sig3mFaV1ndWpygnd/eHR/b3CAam+EZm6Ldn+fho+2jpfEl6DTl6HSl6HSjpnHiJC/iJG+ipG+kZnDmaHLm6PNnqbQoanTpKvYoqvYo6zZoq7apLDcpq/cqbDdqbDdqrHer7TftLfit7rlu73otLfirrHcparVnaTRhoqvcnCPb2d9a15sdWh2gHGAeWt4dWVykH6JrJijpZKboIuVoY6XopCbopOdpZagnZCal4uVtKu0komSjIOMiX2HmIuVqZmmrJypsKCttaOwu6W1183U3NTZ3tfb39jc/v7+0gr+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7x7u/i3N7Hur6dhIydhIyQe4WFc35xYW6BcH+BcoGCdIR+dIZ9dYh+doiCeYiMhJGYjpqAeIRrY29xaXV4cHxraHZeYHRpb4h2fZx+iKuIk7qNmcOUns+Tnc6SnM2LlsSEj72Fkb2Ikb6PmMWZoM2Zos+cpdKfqNWjrNmhrdmirtqjr9ulsN6ksNymr9ymr9yor9yrs92utuCzuOO4u+ayt+Kttd+nsdujrNmSl7yAgaFxb4dlXW9pYG+RhJJwYm90Ym2Qfomtm6alk56fi5ajkZynmKKnmKKnmKKgk52ajpilnKWTipONhI2JfYeYi5WpmabVzdPe19zi2+Dr5un08fP+/v7+/v7+/v7+/v7SCv7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/vPw8eXf4ca7wH9ufX9ufX9ufX9ufX5vf35vgnxxhXxziYB4iod+jY2Dj5SIkoB4hG9pdm9qeHBqe11db05QZmhwiYWPrYmTto2Yv46axJCbzI6Zyo2XyIeRwoKMvYWQvoiTwZCZxpmgzZqj0J2m056q1qGt2aKu2qOu3KSv3aaw4aWw3qSw3KWu26eu26mw3ayz4K+247O657K55rG45a2246q24puky42Ss3d3k2FddGBZbKKWqGhZaHNfapB+ia+dqKSUoZyLmqWVoq+gqqydp6qbpaOWoJ6SnJiPmJSLlJSLlMjDx9TP0+Tg4/Hv8f7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/tIK/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+7+7v4NzfzsjOvbS9no2ck4eZiYCWfXWLcmqBe3SHhX6Nh3+LjICKfXWBcGl4d3OEX11vYmN5ZmmDdn6bh5KyiJW3ipi+ipjBipjIiJbGiJPEg46/gIq7hI+9iZTCj5rIl5/OmKPRm6bUnqnXoq3bo67cpK/dpLDeprHipLDeo6/boq7ao6zZpq/cqbLfrLXisbjlrrfkrrfkq7fjqrbio6zTnKLFe3+dXV13Y2B1jYWXcGNxd2Nul4WQuKi1qJqnmouapJWkr6Cvq52qqpulo5agnpKczcfM1NDU4d/h7+3v/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+0gr+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7x7/HTz9eWj6qWj6p+d5JnYHt2boSFfY+De4iEeIJ5cX5vaXqAfpBPUGZmaYN8g6CDjauMlrmKlrqJlr6JlsKIlsaEksKDjr+Ai7x+ibqDjr+Jk8SPmcqVn9CYotOcptefqdqjrd6jrt+kr+ClsOGnsuOksN6jrtyhrdmfq9eirtqmst6pteGvuOWsuOSrt+OptuKptuKntNyosdiAhqlaW3tpZoF7coh7bX18aHOfjZrDssGtnq6Zip2llqmxorWxorXUzdTZ1Njl4eTw7vD+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7SCv7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/vDv89HO2o6HooqDnmZfenZuhIZ+kHx0gXVpc3JseXNvgHJ0iFRXcWduiX2Eo4OOrouYuomXu4mXv4aWv4SVwoKTwICRvn6PvH6MvIKQwImUxY2YyZOdzpag0Zqk1Zyn2KCr3KGs3aKt3qKw4KSy4qKx3qOv3aGu2qGt2aOv26ay3qm14ay45Ku346u346m24qe246Wz3KWw2IiStm1zll5efHFqg4+Cln1qeZSDkq6drKiZqaSVqKSVqNLK1Obh5/Lw8/7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/tIK/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+7+7yzcrVh4CbZl96d2+FiICSdW16Y1pjbGd1dXSJZmmDWV9+a3GSfYWogo6yiZe9iZi+ipjAhZW+gZO+gZO+gZK/gJG+gpDAhZPDipXHjZjKkZvPlJ/RmKPVm6bYnqnboKvcoq3eoa/fo7HhorDgorDgobDdoq7cpLHdprPfqLXhqrfjqrfjqrfjqLfkqLbmprLgo6/bkpzGgYmyUlR4ZWGAoZevf22AinqNmIibo5Snr6Cz1c7X7Ont/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+0gr+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7u7fDEwcxuZ4J5coWDfYptaHNXUltZWGZdXnRgZYBja45wep2Aiq6FkbWKmL6Imb+Im8KBlr59kbx+kr1/kr+Ak8CDk8OFlMaJlcmLl8uPmM+QnNCUoNSXo9eaptqcqduerNygrt6isOCgsOChseGhst+hst+itN+jteCktuGlt+Klt+Klt+KltuOmtuaisuKgrt6Uo9CLl8VaYYhOTG6IfppxYHWIeY2hkqaomayvoLPn4uj+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7SCv7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/uno6726wX55gn55gmZhak9KU0pIWUZIX1lffm12nXeCqYKQtoWUuoqYwImawYmcw4GWvnqPunyRvICTwIKUxIaVx4eWyYiWzIuXzY6Xz4+b0ZKe1JWh15ik2pqm2pyp256r3aCt35+v36Gx4aKy4qOz46O04aS24aS24aW34qW34qW246a35Ke356Ky4p6t35qp25em2Wdumzg3XW5lhWNSa4d2j6uas6uas9zV3u/s8P7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/tIK/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+09HUioWOenV+Yl1mS0ZPR0hYRUlgXGOCcn2keYetgpC2hZS6h5i/hZi/hpnAf5S8eo+6fJG8f5O+gJPAhJTEhZTGh5bJi5fLj5jPkJzSlKDWl6PXmqbanKnbn6zeoa/fo7HhobHhorLiobLfobLfobPeorTforTfo7XgpLbhpbbjpLfkpbfnorTkn7HimqzdlqfadX6rNTZcXld3aFpyinyUrZ+318/b7erv/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+0gr+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7W1NiXkp12cXxeWWRGQUxFRVdGSWNdZoZ2g6t7ibGCkLiDlLuGl76El76Elr9/lLx7kLt8kbx+kr1/kr+CksKElMSHlsiMmMyRmtGUntKXodWapdeeqdufrN6ir+GksuKnteWktOSjs+Ohst+gsd6gst2hs96hs96hs96itN+kteKjtuOlt+eituWhtOWbrt+WqNuEkLwxNVpPS2pwYnqQgpqxo7vl4ej+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7SCv7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/t7c4JaRnnVwfVtWY0E8SUREVkhLZWFqinyIsn+NtoGRuoGTvISWv4OVvoOVvn+UvHyRvHyRvH2RvH2QvYCQwH+Pv3+Pv4CNv4KMwIOOwIWQwoaRwoiTxIuZyZCezpWj05qo2Juq152s2Zyt2p6v3J6w25+x3J+x3KCy3aGz3qO04aO246W356S456S36J+y45ut4IiUwDU5XlRQb3RogJOHn9LM2O3q7/7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/tIK/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+7u3vwL7EfnmGXllmPjlGQUNXR0tpY22RgY66gZC8g5K+gpS/hJbBgpS/gZO+fpK9fJG8fJG8fZG8fI+8fo6+eoq6eYe3dYOzc36wcn2ucn2ucXytcXyteIO0gIu8iJPEkJvMlKDOmKTSmqnWnq3ana/anrDbnrDbnrDboLLdo7Tho7bjpbfnpbnop7rrpLfoobPmjprGOj5jWlZ1e2+Hu6/H4t/m/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+0gr+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7W1Nh+eYZdW2c/PEpFR11MU3BmcpaCkb2Akr2Bk76Bk76ClL9/lLx+k7t8krp7kbl5j7d4jLd2irV3iLVyg7Bwf6xreqdoc6Roc6Roc6Roc6RpdKVteadxfat1ga95hbN/jLiFkr6JmMSPnsqQos2UptGXqdSarNedr9qgsd6gs+CjteWktuamuOiitOSfseKTn8k5PWFhXnqKfpbKxNDs6u/+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7SCv7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/urp66yrskM/UEM/UEpMY1NZeGt3m4WUwIKUv4KUv4GTvoGTvn6Tu32SunuRuXqQuHaMtHSIs3GFsHCCrWp8p2d2o2FwnV5pml5pml9qm2BrnGFsnWFtm2Ftm2Ftm2Ftm2l2onJ/q3iHs4CPu4SWwYqcx5CizZao05mr1p2u26Cx3qS05Ka25qi46KOz45+v35umzjg8YGBdeYl9ldrX3/7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/tIK/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+ysnOSERVSERVQUNaXGOCb32jhpfEgpbBgZXAf5O+fZG8eY+3d421dIqycYevboSsbIGpaX6maHqlZnijZnWhZHOfZHCeZXGfZnKgZ3OhaXWjaXaianejanejbHikbnuncn+rdIOveYi0e424gJK9hJbBiZvGj6HMlqjTnK7Zo7Thpbbjp7jlprfkpbbjj53GS1R7S1R7wMDN5uXq/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+0gr+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7n5um1tLxVUWI8PlVmbYx1g6mImMiEl8SClcJ/k759kbx3jLdyh7Jtg6tofqZme6NmeKFjdZ5hc55idJ9mdaFndqJqd6NreKRteqZvfKhyfqpyfqpzf6t0gKx1ga10ga10ga10ga10ga11hLB3ibR6jLd9j7qGmMOPocyYqtWjst6ltOCqt+OsueWvvOiHlMBfbJi7wNHf4ej+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7SCv7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/ujo67e5xVpojnuJr3+PuIWWw4CUv32RvHqOuXiMt3OIs3CFsGyCqml/p2uAqG6Aq2+BrHCCrXGDrnWEsHaFsXqHs3yJtX6Lt4CNuYOPu4OPu4SQvIWRvYaSvoWSvoWSvoWSvoWSvoOSvoGTvoGTvoGTvoiaxY+hzJWn0p6t2aGw3KW04Ki34667566758rQ5ePm7/7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/tIK/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+5+nvvMTZipzHhpjDgpS/f5G8fI65eYu2d4m0c4eycYWwbYKta4CrcISvd4m0fI65gZO+hJO/iJXBiZbCi5bEjZjGkJvJkp3LlJ/NlJ/NlaDOlqHPl6LQl6PPl6PPlqPPl6TQkqHNj57KiZvGhpjDipzHj6HMk6XQmajUnazYorHdp7birrvnrrvn5en3/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+0gr+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7U2+uPocyHmcR/kbx7jbh4irV1h7JzhbByhrFzh7JziLN1irV6jrmAkr2ElsGImcaLmsePm8mRncuUn82Woc+Yo9GapdOdqNadqNaeqdeeqdehqdifq9egrNifrNigrdmcq9eaqdWVp9KTpdCSpM+SpM+SpM+SpM+YqtWesNuktuGsu+fW3fPt8Pn+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7SCv7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/tXb65al0YqZxX6NuXiKtXWHsnGDrm6Aq3GFsHaKtXqPuoCVwISYw4max4ydypKg0JSi0pij1Jmk1Zym156o2aGr3KOt3qaw4aaw4amx4qmx4qyx46uz4qq146m146q25Ka14aW04KGz3qCy3Zut2Jao05Gjzo2fypSm0Zyu2aS24a696d/l9f7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/tIK/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+1NvrkKLNhpjDfI65eYu2d4m0dIaxcoSvdYm0eo65fpO+g5jEh5rHjJ3Kj6DNlKLSlaPTmKPUmqXWnafYn6naoqzdpK7fp7Hip7HiqrLjqrLjrLHjq7PiqrXjqbXjqrbkprXhpbTgorTfobPenK7ZmKrVlKbRkKLNlqjTnK7ZorTfqbvm3uT0/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+0gr+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7S2eqNn8qFl8J9j7p7jbh6jLd3ibR1hrN4i7h9kL2ClcKImsqKnMyPn8+RodGVo9OWpNSZpNWbpteeqNmgqtujrd6lr+CqsuOqsuOsseOsseOtsuSrs+KstOOqteOrtuSoteGltOCitN+hs96dr9qarNeXqdSUptGYqtWdr9qhs96muOPc4/P+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7SCv7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/s7V54eax4GUwXyPvHuOu3qNuniLuHeKt3yPvIKVwoeax4yezo2fz5Gh0ZKi0pak1Jel1Zum15yn2J6o2aCq26Ot3qWv4Kqy46qy46yx46yx462y5Kuz4qy046q146u25Ki14aW04KK036K035+x3Jyu2Zmr1pep1Jqs152v2qCy3aS24dvi8/7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/tIK/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+7O/1wsvhhJbGgJLCfY+/fI6+fI6+e429e429gJLChpjIi53NkqLSk6PTlKTUlaXVmKXXmabYnKfZnajan6jboavcpa3ep6/grLHjrLHjrbLkrbLksLPlrrPlrrPlrLTlq7XmqbXjqLTipbTho7ThoLHenq/cnK3am6zZnK3anq/coLHeorPg2ODx/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+0gr+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7a3+2El8SEl8SBlMF/kr9+kb5+kb5+kb5+kMCDlcWImsqNn8+UpNSUpNSUpNSUpNSWo9WXpNabpticp9mep9qfqdqiqtujq9ynrN6ord+qr+GrsOKtsuSrs+SstOWqtOWrteapteOotOKltOGjtOGhs96gst2esNucrtmdr9qfsdyhs96jteDS2/Ds7/j+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7SCv7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/u3v9sPM4oSXxISXxIKVwoGUwYGUwYGUwYGUwYOTw4eXx4yczJCg0JWl1ZSk1JSk1Jak1Jij1Zmk1puk15yl2J2m2p2m2Z+m2Z+m2aKn2qSp3Kes36mu4ayx5Kuz5Ky05aq05au15qm146m146e246W246O14KGz3p+x3J6w25+x3KGz3qO14KW34qW34uPp9v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/tIK/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+3OHtipzHh5vGhprFhJjDg5fCg5fChJjDhJfEhpbGiprKjp7OkqLSl6fXlaXVlKTUlaPTl6LUl6LUmaLWmqPXnKTbmqPXm6HWmqDVnKDVn6TXo6jbp6zfq7DjqrLjq7PkqrTlq7XmqbXjqbXjp7bjpbbjpLbho7XgorTfobPeorTfo7XgpLbhpbfipbfi1d7y7vH6/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+0gr+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7t8PbFzuOKnMeJm8aJm8aImsWImsWImsWImsWImcaJmcmMnMyQoNCUpNSZqNqXptiXpNaVotSWoNSXodWZodiaotmbo9qZodiZn9aWnNOWmdGbn9SgpNmlqt2qr+KpsOOqseSps+SrteaqteaqteaotuamtualtuOltuOktuGjteCktuGlt+Klt+KmuOOqvOevwezm6/j+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7SCv7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/tzi7oudyIudyIudyIudyIudyIudyIudyIudyIqcx4qbyIucyY2dzY6ezpCf0Y6dz4+czo6bzY+ZzY+ZzZCYz5Ka0ZWc1ZWd1JWd1JWd1Jed1Jqg1Z+j2KKn2qar3qWs36at4KWv4Kex4qax4qax4qSy4qOz46O04aO04aO14KO14KS24aW34qa446i65au96K7A69jg9e7x+v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/tIK/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+7/H3x9DkjJ7HjJ7HjJ7HjZ/IjZ/IjqDJjZ/IjZ/KjJ7Ji5zJipvIiZnJiJjIiJfJh5bIiJXHhpLGh5DHho/Gh4/GipHKjpXQkJfQkpnSlJzTmZ/WmqDXnqHZoKTZoqbboajboqncoardo6zfoq3eoq3eoa/forDgorHeo7LfpLPfpbTgp7biqbjkqrnlrLvnrr3psL/rsL/r6e34/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+0gr+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7h5vGUp86Qo8qMn8aNoMeOociPosmRpMuQosuPocyNn8qMnsmLnMmKmsqJmcmIl8mBkMJ8ibt0gLRveK9ud65uda5zerN4f7p6gbx8g75+hb6DiMGFi8KHjcSJj8aNkMiKkMWIkcWHkMOGj8KMl8iTns+Yptafrd2gr9yisd6jst6ltOCntuKqueWsu+evvuqvvuqwv+uwv+ve5Pb+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7SCv7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/tLa6pSnzpCjyo2gx46hyJCjypKlzJSnzpKlzJGjzI+hyo2fyoydyouby4mZyYqXyX2KvHJ8sGVvo1limVdgmFZdmFxin2JnpmRqp2ZtqGhvqm1xrW90rXJ3sHR6sXl8tHR6sXB4r212qmpzpnaBsoOOv46czJyq2p6t2qGw3aOy3qa14ai346u65q696bHA7LHA7LHA7LHA7Nng9e/x+v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/tIK/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+7vD2yNHlkqXMkKPKjqHIj6LJkaTLk6bNlajPk6XOkqTPkaPOkKLNjp/MjZ3NjJzMjZrMhpPFgozAfIa6d4C3cXqybXSvbnWwb3WycXizdHu2d365e4K9fIO8gIa9gYe+hYjAhIq/hI3Bho/CiJHEjZjJlJ/QmKbWnqzcoK/co7LfpbTgp7biqbjkrLvnrr3pscDsscDsscDsscDss8Ds6u35/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+0gr+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7g5PCQosuQosuQosuQosuRo8ySpM2Ups+WqNGVp9CUptGTpdCUo9CSoc6Rn8+Qns6RnM6Rm8+Sm9KTnNOVndSMk8yEi8aAh8J8g75/hsGDisWHjsmLks2LksuOlMuOlMuRlMyVm9CZotafqNumr+Kkr+Ckr+Cjrt+ird6ksN6ns+GptuKruOSsueWuu+ewvemzwOyzwOyzwOyzwOyzwOzh5vf+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7SCv7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/tDX6I2fyo2fyo+hzJGjzpKkz5Sm0ZWn0pep1Jao05Wn0pSm0Zal0pSj0JOh0ZGfz5Kdz5Kc0JKb0pGa0ZKa0Y6VzouSzYqRzImQy4uSzY2Uz5CX0JOa05Sc05ae1Zef1puh2Juk2J6n26Cp3KOs36Ou36Sv4KSv4KWw4aez4am146u45K265q6756+86LG+6rPA7LPA7LPA7LPA7LPA7OHm9/7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/tIK/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+7e/2xc3ki53Ii53IjqDLkqTPk6XQlafSlqjTl6nUl6nUl6nUlqfUmKbWlqTUl6LTlaDSlJ7Sk53Rk5vSkZnQkJjPkZjRk5rVlJvWlZzXl57ZmaDbmqHanKPcnqbdoango6viqK7lpKzjo6vioqvfoareo6zfpa7hpbDhp7LjqbTlq7bnrbnnr7zosL3psb7qsr/rs8Dss8Dss8Dss8Dss8Ds3+T2/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+0gr+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7c4O6OnMyLnMmKm8iNn8qQos2SpM+UptGWqNOYqtWYqtWYqdaYqdaaqNiZp9eapdaZpNaYotaXodWXn9aVndSUnNOUnNOUm9SUm9SUm9aWndaZoNmcpNugqN+iquGlreSlreSmruWkreGiq9+kreGmr+OnsOOoseSos+SptOWrtuetuOmvu+mxvuqxvuqxvuqywOmzweqzweqzweqzweqzwera4PTv8fn+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7SCv7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/ufq88PK446czIyayouZyY6dypGgzZOiz5al0pin1Juq15uq15yq2pyq2p+q256p2p2o2p2o2p2n25ul2Zqi2Zig15ef1pae1ZWc1ZOa05KZ1Jad1pui25+n3qSs46au5amx6Kiw56ev5qSt4aGq3qav46u06Ku056y16Ky16K226a646bC667K967TA7LTA7LTA7LTA6rTA6rTA6rTA6rTA6rTA6rTA6ufr9/7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/tIK/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+6u313eHvx87ljZvLjZvLjJrKi5nJjJvIjp3KkaDNlaTRmKfUm6rXnKvYnqzcn63doq3eoazdoazeoazeoavfm6XZlp7VkJjPi5PKjJTLjpbNj5bPkZjTk5rTlZzVlp7VmKDXn6fepq7lp6/mqLDnoarem6TYoareqLHlqbLlq7Tnrbbpr7jrsLrrsrzts77stcHttMDstMDstMDqtMDqtMDqtMDqtMDqtMDqtMDq2t/07e/5/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+0gr+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7u7/jT2OyMmsqMmsqMmsqMmsqMmsqMmsqMmsqMmsqQns6UotKYptadq9uerNyird6jrt+lsOKlsOKlsOKlsOKmr+OcpdmSmtGIkMd/h76Di8KIkMeMk8ySltKPls+Olc6NlcyNlcyYoNejq+KlreSosOefqNyWn9Oep9umr+OoseSrtOeut+qyu+6zve60vu+1wO63wvC2wu62wu61weu0wOq0wOq0wOq0wOq0wOq0wOq0wOrj5/fw8vr+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7SCv7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/t7h8pSc146azouZyYuax4ybyIybyI2cyY2cyY2by5Gfz5ak1Jqo2J6s3KCu3qSv4KWw4aey46ax4qax4qWw4aav4pyl2JKbz42WyomRyIePxoaOxYCHwHqBvICHwIePxo2VzJOb0pae1Zmh2JWe0pKbz5CZzI+Yy5mi1aSt4Kex4qq05a+56rS+77XA7rbB77fD77nF8bjE8LfD77bC7LbD67XC6rTA6rO/6bO/67TA7LXB7bXB7efr+P7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/tIK/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+7e/3x8zolJzXjprQi5fLi5nJjJvHjJvHjZzJjp3Kj53Nk6HRl6XVm6nZoK7eorDgprHip7LjqbTlqLPkqLLjprDhpa7hnKXYk5zPkpvOkpvPi5TIhIzDdXy1Zm2oc3qzgIi/jZXMmqLZlJzTj5fOho/DfYa6gou+iJHElZ/Qo63eprDhqbPkr7nqtsDxt8LwucXxusbyu8fzusbwucXvuMXtt8TstcPrtMLrssDpsb7qs8DstcLutcLu2N307e/5/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+0gr+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7e4fCNldCNldCIlMqGk8WJmMWMm8eNnMiOncmPnsqQn8yUo9CZqNWerdqjseGksuKos+Sqteast+ist+itt+itt+itt+igqtuUndCUndCUndGVntKXn9aMk8yBiMOJkMmRmdCZodihqeCfqNyep9ubpNeYodSapNWdp9ijrd6qtOWrtuSst+Wyvuq7xPG6xvK7x/O7x/O8yPS7x/G7yPC5x++4xu62xO20we2yv+uxveuyvuyzv+2st+ilr+Pm6Pb+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7SCv7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/s7R6YePyoePyoWOxYOOwImVw4+dxo6ex4+eypCfy5KhzZal0Zuq16Cv3KWz46a05Kq15qy36K+56rC667K87bO97ra+76Su35We0ZWe0ZWe0p+o3Kqy6aOq452k35+m36Kq4aSs46ev5qqz56226bC57LO877K87bK87bK87bK87bC76bG657fA7b/G877I8r/J873J877K9L3K8r3K8rvJ8brI8LfF7rXC7rO/7bG87bK97rO+76y36aWv49nd8v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/tIK/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+7e73xMfkhY3IhY3IhI7Cg46/iJXBjpzEjZ3Gj57KkaDMk6LOmKfTnazZorHep7XlqLbmrLforrnqsrztsr3rs77stL/tt7/up7LgmaPUlZ/QkpvOmaLWoangnaTdmaDbnqXeo6vip7DkrLXprrnrsbzusr3utL/wsb3rsLvpr7ror7ror7vnsrvouMLswcnzv8rywMvzv8z0wM31v8z0vsvzvMryu8nxuMbvtsPvtMHtsr7ssb3rsbztq7bopa/j1Njx7O74/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+0gr+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7f4fGLk86Hj8qDi8aDjcGEj8CIlcGNm8ONncaPnsqRoMyUo8+ZqNSerdqjst+pt+equOiuueqwu+y0vu+0v+20v+23v+66wO+stOOhqdqXodKQmcyTnNCXn9aWn9eWntmcpd2iq+Kps+ewuu6zvvC3wvS3wvO3wvOyvuyuueetueWsuOSxu+W2vui8xO3Dy/TDy/TEzPXDzvbCz/fBzvbAzfW+zPS9y/O6yPG4xfG1wu6zv+2xveuwu+yrtuimsOSmsOTm6ff+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7SCv7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/s/S6ouTzoePyoOLxoSOwoWQwYiVwYyawoydxI+fyJKiy5al0Zuq1qCv3KW04au56a2767G87bO+77bA8bbB77jA77jB7rrB7rG56Kqy456o2ZWe0ZOc0JKa0ZGa0pKa1Zmi2qCp4Kaw5K2367G87rXA8bO+77G87a666Ky45K665rG96bW/6brC67/I78TN9MXO9cbP9sXQ98TS+MPR98LQ9sDP9b/N9bzK87rH87fE8LXB77K+7K+666u26Kex5aex5d3g9P7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/tIK/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+7e/2xcnkjJTPh4/Kg4vGhY/DiJPEipfDjJrCj53FkqDJlaPMmabSnKvYorDgp7Xlrbvrr73ts77vtcDxuMLzucHwu8HwusHuusHut73stbrsqLDhm6TXlJ3RjZXMjZbOj5fSlp/Xnabdo63hqrTorrnrs77vr7rrrLfoqrbkqrbisLzmtsLsu8buwMjxw8zzyM/2x9D3yNH4x9L5xtT6xdP5xNL4wtH3wc/3vsz1vMn1ucbytsLwsr7sr7rrq7boqLLmqLLm2t7z/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+0gr+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7f4e6LlMiLlMiHkMSDjMB1f7CKlcOKmMGMmsKNnsWRocqUpM2Yp9Ocq9igrt6ls+OquOisuuqwu+yyvuy2we+3wO25wO25wO25wO22veq0uumqsuOgqdyZotaSmtGQmdGPl9KWn9edpt2kruKrtemuueuxvO2uueqst+isuOatueWzv+m5xe+8x+/AyPHCy/LGzfTFzvXGz/bEz/bD0ffC0PbC0PbAz/W/zfW8yvO6x/O3xPC1we+xveuuueqptOWlsOKlsOLT2PDs7vj+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7SCv7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/tLV542Two2TwomPvoWLumdwnYuXw4uZwoyaw46ex5KhzZWk0Jmo1Jyr2KCu3qOx4ae15am356656rC86rS/7ba/7Li/7Li/7Lq/7La96rS66ay05aWu4Z6n25ef1pKb04+X0paf152m3aSu4qy26q6567C77K656qy36K2556+757XB7b3G877I8sHJ8sPK8cbK8sXM88TN9MLN9MPO9sDN9cDN9b7M9L7M9bvJ8rnG8rbD77TA7rG966656qiz5KKt36Kt36Kt3+Xo9f7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/tIK/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+z9Lki5O8i5O8g4u0e4OsbnmhhJC6h5W+ipnFjp3Jk6LOlqXRmqnWnKvYn63doa/fpLLiprTkq7bnrrrosr7qtL3qtr3qtr7ouL3otb3ntLvor7fmqrTloqvem6PalJ3VjZfSlJ/XnKjepLDkrLjsrLnrrLrqq7npqrjorrrosr7qt8Pvv8j1v8nzwcnyw8rxxcnxw8rxwsvywMvywcz0vsvzvsvzvcvzvcv0usjxuMXxtcLus7/tsLzqrrnqp7LjoKvdoKvdoKvd2t7x/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+0gr+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7s7/fDyuKOk7iOk7iCh6x2e6B6g6qAi7OFk7yLmsaPnsqUo8+XptObqdmcqtqerNygrt6isOCmseKqtOWtuOayu+iyu+i1vOm3vOe6vue3vOe1vOmyuumxueqnsOOgqN+XoNiNl9KVoNidqd+lseWtue2sueuruOqquOiquOiwvOq2wu68xfLDyvfCyvTDyfLCyPHDx/DBx/DCyPHAyPHByfK+yfG8yPK7yfK7yPS4xfG2w++zv+2xvO2wu+yvuuumseKep9qep9qep9rZ3fD+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7SCv7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/tzi8oCW0oaYyY+awnqEqGdtjnR+oYOOtYeVvYqcx42fypGjzpSl0pqo2Jqo2Jup2Zyq2p6s3KKt3qaw4aiz4ay14qq03qqy3K+13rW54rK34rC35K215K215qav4qGr35ai2IuY0pOg2Jyq4KSz5q2876u67Kq566i46Km356666LK+6rW+67i/7LjA6rvB6rvB6r7C673D7L/F7r7G77/H8L3I8LvH8brI8brH87jF8bbD77TA7rO+76+666y36J6p2pKbzpeg05yl2Nnc7/7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/tIK/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+z9ftgJbSiZvOk6DMd4GlW2GAcXmch5K5iZe/i53IjqDLkaLPlKXSmafXmafXmafXmafXmabYnajZoavcpa3cqrHepKzWoKXQqa3WsbPdsLPerrPgrLLhqrLjpq/io63hl6PZjJnTlKHZnavhpbPprrzyrLvuqrnrqLjoqbfnq7flrbjmrLXirLPgrrbgs7jjt7vku73nvMDpvsLrvsTtv8fxvMbwucXxuMXxuMXxt8TwtsPvtcHvtcDxr7rrqbTll6LTho/CkJnMmqPW2d3w/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+0gr+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7Z3+98ks6JmcmHkbRvdpVXXHdyfJqPm7+OnMSNnsuPoM2Ros+TpNGVpdWSotKQoNCNnc2NmsyRnM2Vn9CYoM+co9CTm8WNk7yZncWlqNClqdKlqtelq9qlrd6kreClr+OYpNqMmdOVotqerOKmtOqvvfOtvO+ruuyouOimtuantuOqteOlrtuhqNWlrdersNuxtd62uOK2uuO3u+S2vOW2vui1v+m0wOy0we22w++2w++2w++2wvC2wfKyve6vuuuDjr+JksWVntGhqt3b3vH+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7SCv7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/uvu9srP45GUroCDnWpth1RXcXZ+m5mjxpOhyY6ezo+fz5Gh0ZKi0pSk1I6ezomZyYSUxIGOwIWQwYiSw4uTwo+Ww4aLtn2BqoqOtpibw5ufyJ+hz6Gl1KSp26St4Kix6Jql3Y2a1JWi2p6r46e167G/9a698Ky77qm46qa156Wz46aw4Z6m1Zadyp2iz6Wn1ayu2bSz37K037G036+036204a635K+757G+6rXC7rXC7rbC8LfD8bjD9LfC87jC83F7rIuUx5mi1aix5N3g8/7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/tIK/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+29zmxMfhVFdxUlVvUFNtc3uYl6HEkZ/HjZ3NjZ3Njp7Oj5/PkKDQiJjIg5HBeoi4c36vdoKweoWzf4i1hYy5cHWgXGCJaW2VeHujm5/IkJO+mJ3KoKXXn6jboqzgl6PZjZrUlaLanqziprTqr73zrbzvrLvtqbnpp7fnnq3alqHPlZ7LlJvIi5C7goWwgYOtgYGrkpS+pKjRn6TPmqHOnqfUo6/bp7TgrLnlrrvnsb3rs7/ttsHytsLwt8LwcXyqf4e2kpzNqLHk4ePy/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+0gr+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7U1d+ZnLZIS2VKTWdNUGpweJWVn8KQnsaMnMyMnMyMnMyMnMyOnMyEksJ9iLlwe6xkbp9pdKJweaZ0fqh7g61bYYo8QGlJTXVYW4OcoMmChbCPlMGdotSdpNeep9uWotiPnNaWo9uerOKmteivvvGtvO6su+2quuqquOiYpNKGkb+LlMGRmMV4fahgY45WWIJOTnhydJ6Xm8SPlL+HjruPmMWZos+eqtalsd2oteGsuOawvOq1wPG1we+2wu5zfKlyeaZyeabAw9rm5/H+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7SCv7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/r7AymVrhD1DXEJIYUdNZmt1k5GdwYycxYmby4iayomZyYiYyIqYyIqWxIqVw4OOvH6GtXqDsHmArXV9p3N5oltiiUVJcU5SellchIuPuI+SvZidyqGm2J+n2J2m2Zai1pCd1Zel256s4qW056287qq66qq46Ka05KOx4Zik0o2ZxYqTwIiPvGpvmk1RekhLc0RFbWJljYKGr3+FrnyEromTvZmiz6Gt2ay45K265q+76a++67LA8LK/67O953iAqWJmjmJmjry+zv7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/tIK/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+5OTnpaexNDpTNDpTPEJbREpjaXORjZvBiZvEh5vKhpjIh5fHh5XFiJPEj5vJl6PPl6DNmJ/MjJS+gIiydXuka2+YXGCITVF5VFd/XF2FfH6onaDLoabTpqvdoananKXYmKLWlKDWmqbcoKzip7Pnrrvtqbfnp7Ljo67fn6rbmaXTlaHNiZK/foWyXWKNPEBpOj1lOjtjU1Z+bXGabnSdcneiho64nKPQqLHetMDss8Dss7/tscDtsb/vsLzosbnigIesUFR4UFR4rK6+2Nng/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+0gr+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7OztJKS1s/Q1Q2O1A+Q1hHS2Jrc5COmr6JmcKEmMeElsaFlcWEk8CEkL6JlsKPm8eQmsSRmcOQmMGRl8CPlr2Pk7uWmsKdocmmqdGwsdmsr9eprdahptGZn86Xn9CVntGUntKTn9WZpdmgrOCntOauu+2otualsOGfq9maptSap9OcqNSYosyVnceAhq9rb5ducpdyc5mChquTl7+RmL+Rl8Caosulrdeps92tueWtuuavu+mvvuuxv++xveezvOODiaxSVXVSVnRUVnTDxM/k5On+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7SCv7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/rq6wEpLW0BDUzk7TUFEWEpMYmxzjo+ZvIeXwIGUxYKUxISUxIKRvoCNuYSQvIqTwIyUvo6UvZacxaGlzqqu1rO337C03K+y2ri748TF7bu+5rS44Z+kz4uRwIyUxY6XypCazpSd1Jul2aKs4Km05rC77am05aOu35yo1pejz52p06Wv2aey2qy03aOq0Zufx6Kmy6qr0bC02bi85LW54bK237C236+34au136my36q24q245q+76bG/77K+6LW+5YSKq1RWdE5QbkhKaHh6mNfX3v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/tIK/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+tre9SktbQENTOTxMQkRWTE5ibHOOjpi7hZW+fpHCf5HBgpLCfo26e4i0eoawe4WveYGqen6mf4OrhIiwio62kJS8jZG5i462jpG5lJW9kZS8j5O8io+6hYy5ipLBj5nKlaDSnKXcoavfprHjq7bnsrztqrXjoq3bmqbSkp7Kkp7IlJ/Hk57FlJ3Ek5rBk5e/mp7DoqTIpanOqa3VqKzUqKzUpq3Upa3Wo63Xo6zZpbHdqrXjrLvor7/vsr/ntr7hgYilUFFrTU5oSktlYmN9wsPM/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+0gr+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7q6uyrrLNKS1tDRFQ8PU1FRlZPT2FudI2Nl7qDk7x6j8B9kcCBkcF8i7h4hLBzfadudp9pcJdmaZFnapJpbJRrbpZtcJhqbZVoa5NlaJBlZo5napJrb5h1eqWAh7SIkL+QmsuZpNakreSnseWrtOeuuOmyvO2ptOKhrdmYpNCPm8eLlb+Hj7iBirF+hayEi7KMkLiTl7ydncGcncObnsacoMieosqdpMuepM2dpc+epdKiq9insuCqueavv++xvua3veCAhaBLTGJLTGJLTGJLTGK9vsb+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7SCv7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/tna3XJ0gUJEUT9BTjw+S0BCT0VGVmFnfn6KqHyNs3yQv36RvoGSv3+Oun+Lt3iErnN9p252n2pwmWxzmm92nXF4n3Z6onZ6onZ6onZ6ond7o3qAqYCFsIqRvpacy5qi0Z6o2aOu4Kmz56u26K+56rK87bW/8K6556i04KOv256q1paizJCbw4iTuoOMs4OKsYSIsIOIrYOHrISJroeLs4eOtYqRuIyUvZCYwpKcxpegzZ2p1aWw3qu35bG/76244Kux0nl/mElLXUtNX01PYU1PYbu8w/7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/tIK/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+ycrOd3mFR0lVQUNPPD5KPD5KPD5KV1xwc3uYd4WpfpC7gpG9h5TAhpO/hpK+f4u3eYWxdX6rcXildHymd3+peYGrfISuf4ewhIqzh422jJC5kZbBlp3KoafWrLHjrLTlrbforrnrsLrusbzutL7vtsDxuMLzs77sr7vnr7vnr7vnpLDanKbQkp3FipK7gYqxe4Kpc3qhbXGZbnWccnmgc3yjdn6nfYWvhYy5ipPAkJvJmaTSo67cq7fltcDxqLPanqXEc3eOSElZTE1dUFFhUFFhvL3D/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+0gr+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7IyM1wcHxISFQ+QEw3OUU1OEIzNkBLUWJjbIVwfZ2Bj7iEkruJlb+KlsCMmMSIlMCEkLyAjLh9ibWAjLiEkLyHk7+MlcKQmsSWnsiZocudpc+hqdOlrNmpseCwuOmwuuuyvO20vu+2v/K3wfK5w/S6xPW7xfa3wvCzv+u0wOy2wu6yvuiuuuSst9+qstuiq9KbpMuTnMOOlbyKk7qIkLmEj7eCjLaGkryNmcWUn82cpteirduptOKvuui2we+jrNORmLdrcIVFR1NLTVlSVGBSVGC9vsP+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7SCv7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/sfHy2lnc0hGUj0+SDM2QC4yOSktND5FVFVddmp2lIGPtYWTuYqXv46axJOfy5GdyZCcyI2Zx4yXyJCcypWgzpij0Zyn1aGt2aix3qy14rO657K76LS867O+7LS+77bA8bnD9LvF9r3G+b3H+L7I+b7I+b7I+brF87bB77rG8r/L98DM+MLO+sXP+cnR+8LK87zE7bW95q6236ev2KCo0pehy46axpSgzJum1KCs2qey46q15q+56rO+7LjE8J6ozIWMqWNofENFUUtNWVNVYVNVYcHCxv7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/tIK/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+yMfLbmx3RkRPPD1FMzc+LzM6Ky82O0BNS1JnZGyJfIaqgo6yi5a9jpvDlKDKkqDJkZ7Kjp3Ki5vLjp7Ok6HRlqTUm6bXn6rbpK7fqLLjrbforrjpr7nqsLrrsrzttL7vtsDxuMLzusT1usT1u8X2u8X2vMb3ucTytsHvuMTwu8fzvMj0vsr2wMz2xM74v8ryu8butsHpsr3lrLjiqLTgo6/bn6vXoK3Zo6/dpLPgp7XlrLfosbvssbzqs7zplp7BeICZW2JxQERLRkpRTVFYbHB3yMrN/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+0gr+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7Kyc10c3tEQ0s8PkQ3OT8xNTsuMjg3PUhBRlpaYXx2fJ+BiK2Nlr2RnMSVocuToMyTn82Qns6Onc+Qn9GSodOUo9WZpticqduird+lsOKptOaqteast+iuueqxu+yyvO20vu+2wPG4wvO4wvO5w/S5w/S6xPW3wvC1wO62wu64xPC5xfG7x/O9yfW/y/e9yfO7x/G4xO62wuy0wOqzv+uwvemvu+muuuituOmquOiqt+mvuuuzve6wuOeuteKOlLdtc4xWW2o+QkhCRkxGSlCGipDU1df+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7SCv7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/s3Mz3t4gERBST09Qzc6PTI4OTA2NzM5QDg9TFFXbm1zknyFpY2XupGdwZajy5Kiy5Chzo6g0I2f0o+h1JGj1pOk15em2Zmo25yr3p+u4aKx5KSz5ai156q46K656q+667K87bS+77bA8bbA8bbA8bfB8rjC87bB77S/7bTA7LTA7LbC7rjE8LnF8bvH87nF8bjE8LbC7rTA7LK/67G+6q696q687Ku766u766q566q57K267LK87aiw35+m036EpV9jek1TXjxCQ0pQUVheX7GzteHi4/7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/tIK/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+zs3QhICHREBHQD5COzw+Njk7Mjg5LzU8LjFBS01jaGqIe4GgkZe6lJ7Cl6TMlKPPkqLSj6LTjqLXj6PYkqTZk6XalKbblqjdmqrgnKzioK7korHkpLPmqLXnrLfprrnqsbvssrzttr7vtL7vtL7vtb/wtsDxtcDutL/ts77ssr3rs7/rtcHttsPvuMXxtsPvtMHtsr/rsb7qr77qr77rrb7rrb3trb3trbzurbzvrbvxrrvtsLrroqjXlZfFcHWWT1RpRUpVPD9BTE9RXF9hw8TF/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+0gr+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7PztCAfINAPEM9Oz86Oz01ODowNjcsMzgrMDtBRVZYWXNscYyCiaiLlbiXosmVo8ySo9CPotONodaOoteRo9iSpNeUptmWqNuaq96creChsOOjsuSntOapt+etuOmuueqxu+yyvO20vu+0vu+1v/C2wPG3wfK2we+1wO60v+20v+20wO61we+0w/C2xfK0w++zwu6xwOywv+uvvuqvvuutvuutve2tve2tve2su+2su+6qteansN2Zn8iKjbVqboxMT2NCR1E7PkBTVlhrbnDHyMn+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7SCv7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/tXU1n15gD05QDw6Pjo7PTQ3OTAzNSwwNikuOTg7S0dIXl5he3V8mYWOrpagxJSiypOl0I+j0o2h1o6j1ZGj1pKl1pSm15ep2pyr3Z+u4KWy5Ka05Kq15qy36K656q+667K87bO97rS+77W/8LbA8bfB8rnD9LfC87bB8rXA8bXA8bbB8rfC87bE9LjG9rbF8rTD8LLB7bHA7LC/66++66696q687K687K687K27662766Wx3Z6mz4+UuYKCpmZngUlLXUFETjo9P1pdX3t+gMvNzf7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/tIK/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+6Ofop6WoSUZKQUBCOzo8NDU3MDEzLjE0LTE4NDlEPUBQUFVpZWuEdX2ahI6xiJa8jp7HjaDNjaLUjaLTkKPUkaXUk6XVlqjYnKzcorDgqLPkqbTlrLbnrbfor7nqsLrrsrzts73utb/wtsDxt8Hyt8LzucT1uMP0uMP0tcPztMLytcPztsT0t8X1uMb2tcbztMXyssPwscLvr8DtsL/srr3qrbzpq7rnqbjlprXiprLgmaTMjpW6fYKjcHCOWVtxQ0ZWQUVMP0JEXWBie36A1NXW/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+0gr+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v65uLlGQ0dBPkE8OTw2NTcvMDIxMTU0Mzs0NT81N0NESVhVWm9jbIVyfpx8i62KmMCMnsmPotOPo9KQpNORo9OUpNSaqdahrdulst6qtuKrt+OtuOauueewuuuxu+yyvO20vu+2wPG3wfK5w/S5xPW6xfa6xfa6xfa3xfW2w/W3xfW4xva5x/e6yPi2xva1xfW0xPSzw/Oxwu+xwO2vvuqvvOiqt+Oms9+hrtqeqtaNlr19g6ZscI5dXXdNT2NAQk9DRExHR0tHR0uurrDf3+D+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7SCv7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/rm3uUZDR0A9QDo3OjU0NjEwMjEyNDMzNzI0OjQ1PT1CTUlOXVJabVxlfmdxjnJ9nXWDp3qKs4CQuYaWv46ex5imz5yq06Ku2KWx26q03qm136u346255a+66K+66LC76bG86rK87bO+77XA8bbB8rjD9LbE9LbE9LTE9LTD9bTE9LXF9bXF9bbG9rTE9LPD87LC8rHB8a/A7a++6q286K6756m34Kay3JOfyYCMtnB6nmJoh1ladE9MYUZEVT09SUNETExMUExMULu7vf7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/tIK/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+ubi5SUNIQD1AOjc6NzQ3NDE0MzI0NDM1MzM3NDQ6OTpCPkBMQkdWSE1hUlluXGR9YWuIaHKVc32gf4msjpi8nqfOoqvSp7DXqLHYqrLbqrTerLXirLjkrrrmrrrmrrnnrrnnr7nqsLvssr3utL/wt8L0tcL0tsP1tMP1tMP2tMP1tMP1tMP1tMP1ssLyscHxsMDwr7/vrb7rrr3prbzorrvnqLbfpLHZg5C4ZG+XV11+Sk1oQkNZPjpLPDpGOzlERkVNUVFXUVFXv7/B/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+0gr+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v66uLpKRUhAPUA4NTg2MzY1MjUzMzMzMzMzMzMzMjQ0NDg4Nz84O0U6PktCR1ZLTmJQVWpYW3VfZH9mbYpzepmCiKmLlLSWnsGZo8efqM+hqdKkrNakrtinsN2mst6otOCotOKqteatuOmwu+ywvu6zwPKywfOzwvWzwvW0wvi0w/a0w/azwvSzwvSxwfGvv++uvu6tve2rvOmsu+eru+Stu+Shr9eXpcuDjrVweaBcY4JKTmVDRFQ/OkU/PEQ+PUVISE5SUlhiYmjFxcf+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7SCv7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/r27vEtGSUA9QDc0NzYzNjYzNjQyMzMyMDIxLzEwLjAwMDAvMS4uMi8uNjUzPjw6RkA/TUZEVkpJXk5PZVtddGhrhnd7mYaLrI6Ut5icxJmgx5qiy52lz6Cn1KGq16Ku2qSw3qey46q15q6566677bG98bC/8rLA9rPB97TC+LPB97PB97LB9LHA86++8K6976y77au67Kq66qy76K265q2545uo0IyXvoKOsnuFqGNqhU1OZEZDUT85QEE9RERBSEpKUFNTWXR0etHR0/7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/tIK/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+wsHCXFdaSEVINzQ3ODY3Ojg5ODc1NjUzNDMxMzIwMjIyMjEzMDA0MDA2MzI6NjQ/NzdDOjlHPD5LPkFRRUlaTlBmWFxzYmiBZm2IbHKRcXiXeH6fe4SkgYmshY+zi5S7j5rClKDKmKTOnanVoazaprDhqLPkrLforbvrsb7wr7/vr7/vr7/vr7/vrb3trLzsq7zpqrvorbzosL7nrLriqbfdmaXJipS3gIureYOhY2mCTlBkRURSPThBPTpCPDtDTk5UYGBmsrK139/h/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+0gr+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7Qz89taGtRTlE4NTg7OTo+PTs8Ozk6OTc3NjQ1NDI0NDI0NDQyMzUyMjYxMTcwLzcvLTgvLTkvLzswMj4xNUI0N0c5Pk0+RFVARVlDSF1MUmlVXXZbZH5ja4hsc5J1e5x8hKeDjbGJk7ePmL+Vncabos+fqNWjr9uptOKwuuuvu+mvu+mvvOiwvemvvOiuu+euu+euu+ewvua0wuiuut6ps9aXosKIkrB/iaZ4gJ1iaH9OUGRFRFI8OEM4Nz83Nj5NTVNkZGrFxcf+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7SCv7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/ujn6K2rrEtKSDs6ODo5Nzo5Nzk4Njk4Njg3NTc2NDY2NDY2NDY2NjY1NzM0NjIyNjIyODIxOTM0PDY3PzM3PjE2QDM4QjY7RjY8RzU9SEBIVExTYk9XaFVccVtkemRshWt0jnR7mnmCooCIq4eOs4+WvZWexZ2lzqSu2K2246u346y45K25466746264q264q274a684qq43Ki115WiwoWRr3eBnmpzjWNshV1lflBVakJEVj0/TDo4RD89SURCTlRTW2Rkas/P0f7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/tIK/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+zMvLX15cPj07Ojk3NzY0NzY0ODc1OTg2Ojk3Ojk3Ojk3OTc4OTY5NzY4NzY4NTY4NTU5ODg8PDxANjk8MjU4LzQ3LjM2LDM2LDM2Nj5DQUhRQkpVRk1cTVNkVVpvXWF4ZWiCa3CLcniXeX+ggoirjZK3mJzEoafQqrLcq7PcrLTdrbbdr7jfr7jfsLngsLresbvfp7HUnajIgYupZW2KWGF7TFRtSU9mRktgPkFVNjhKNzhIODZHREJTUE5fUE5frq2z3t7g/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+0gr+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7m5uanp6ZLSkhDQkA8Ozk6OTc4NzU4NzU5ODY4NzU3NjQ3NjQ3NjQ2NjQ2NjQ2NjQ2NjY2ODg5Ozs2ODg0NTcxNDYxNDYvNTYtNTY0Oz48Qkk+Q01ARVBFSVZKTV1OUmNUVmxaXnViZX9obYhwdJJ6gJ+GjK+Umb6ips6lrNGqsdattdiwuNuostWirM+cp8eXosKBi6lrc5BZYnxIUGlBSmA9RFk8RFc9Q1Q6P042OUk5O0g9PEpJSFZVVGJVVGLLys/+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7SCv7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/szMy1FQTklIRkFAPjw7OTg3NTc2NDc2NDY1MzY1MzU0MjU0MjU1MTU1MTU1MTY2Mjc4NDk5NzU4NTQ3NDQ3NDQ2NjI2NjI1NzQ5PDg8Qjk9Qzo+RTw/ST9BTUFDUERFVUpMXlJUaFhacF5he2luiXd6moaLrJicwJ+lyKiu0a2z1LK42aWsy5mgv42Us4GIp1tkfjY+VzI4Ty80SS4zRy40RTM4Rzg7Szc7SDc7SD0/S0NDT09PW1tbZ66utNzc3/7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/tIK/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+6OjntbW0SklHSklHQkE/Ojk3ODc1NjUzNTUxNTUxNDQwMzMvMzMtNDUtNDUtNTYuNTcvNjgxNTcwNTYyMzYyNDc0NTg1Njg4ODo6Ozw+PD0/PT1BPz9DQUFHQ0NJRURMSElTTE5bT1JiVFZqXF50Y2aAcXSPgIGhiIurk5a2l5q6nJ+/ipGue4Kda3KNXGN+TFJrPEBXNTpPLjNHMDRFMjVFNTlGOD1IPUJNREZSSk1XU1ReV1hiW1tnzc3R/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+0gr+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7n5+azs7JTUlBHRkQ8Ozk4NzU1NDI0NDAzMy0yMiwxMSsxMioyMyszNSo0Nio0Nis1Ni41Ni42NjA3NzM4NzU5ODY6ODk7OTo9Ozw+PD1APUBBPkFDQENEQURGQ0ZHRUlIR09ISFRLSVpNTV9QUWdcXHZoZoVzc5F/f52BhJ+FiKNydY9gY31NUGo6PVc8QFdBQ1k3O0wvMkIxNUI1N0M3OkQ8PUVGR09QUVlaW2NkZW1kZW2xsbbe3uD+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7SCv7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/uXl5aampUdGREdGRD8+PDc2NDY2MjU1LzQ0LjMzLTM0LDQ1LTQ2KzU3KzU3LDU2Ljc4MDk5Mzk5NTk4Njg3NTg2Nzg2Nzk3ODs5Oj47Pj47Pj88Pz88P0A9QEA+QkA/Rz4/ST4+SkBCT0JFVUtNX1VWbFtdc2JkemNle2VnfVpcclBSaEZIXjw+VDs+Ujo9UTQ4SS8yQjE1QjU3Qzc6RDw9RUpLU1hZYVtcZF9gaF9gaNHR0/7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/tIK/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+zMzLUlFPUlFPRkVDOjk3ODg0NzcxNjYwNTUvNTYuNjcvNjgtNzktNjgtNjcvOToyPDw2Ozs3Ojk3ODY3NzQ3NjM2NTI1ODU4PDk8Ozg7Ojc6Ojc6Ojc7Ojc+OThANDU9MTI8MjU/NTdDOj5LQkVVREdXRklZREdXQ0VXQUNVQEJUPkBUPT5UODpONDZIMTRELzJCMTVCNjhEODtFPT5GTk9XYGFpXV5mWltjvr7B4uLj/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+0gr+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7o6Oi7urpSUU9LSkhEQ0FAQDw9PTc6OjQ3NzE3ODA4OTE5OzA6PDA4Oi82Ny86OzM/Pzk/Pzs/Pjw+PD09Oj07ODs6Nzo9Oj1BPkFAPUA/PD89Oj08OT09Oz89PUM3OT80NT01OUA4PEM6QEc/RUxARU9CR1JBRlFBRVJARFE/QlI+QFI9P1M3OUsxNEQ0OEU3O0g3PEc6PEg6PUc9PkZQUVljZGxjZGy+v8Lh4eP+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7SCv7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/urq6tLS0aKioU5NS0hIRENDPT4+ODk5Mzk6Mjo7Mzs9Mjw+Mjk7MDc4MD09N0NDP0NCQENBQkNAQ0RBRUE+Qj47P0I/Q0dESEVCRkNAREE+Qj87QkE/Q0JCRj09QTk5PTk8PjpAQTxCQz9FRj9ERz9DSUBES0FETkBCTkBCTz9AUD8/UTc4SDAxQTg6R0FDT0BCTj9BTT1ASj4/R1JTW2docMDBxOLj5P7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/tIK/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+6urq0dHQoKCdQ0M9SEhCTU1HRkc/P0A4PkA1PkA0PT80PT42Pz85QUA+QkBBQ0BDRUJFR0RIQj9DPjs/PzxAQT5CPjs/PDk9Ojc7OTU8Ozk9PT1BOzs/OTk9OTw/Oj9CPEFEPkNGPUJFPEBGPEBGPEBHPkFLQUNPQkRRREVVP0FOOjxJQkRQSkxYTlFbU1ZgWFxjX2BoX2Bov7/C4+Pk/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+0gr+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7p6ejQ0M+kpKFhYVtSU0tERT1CRDlBQzdCRDlDQz1BQD4/PD9BPkFEQURHREhKRk1EQEc/O0I9OUA7Nz45NTw3Mzo0MDcyLjU1Mjk4OD45OT86OkA6PEI8PkQ7P0U9QUc7P0U6PkU5PUQ4PEM+QUtERlJGSFRKSlZISFRHR1NOTlpVVWFfYGpqa3V1dn6AgYnExMfj4+T+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7SCv7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/uzs69XV1Kurp3R1bVpcUUFDN0hIQk9NTkpHSkVCRkVCRkVCRkRBRURAR0E9RD87Qj46QT05QDo2PTg0OzYyOTUxODYzOjc3PTk5Pzs6Qjo7Qzw9RTs/RjxARzxARzxARzxARz4/R0VGTkxNV01OWFBOWlRSXlhWYl9daGdlcGxqdXJwe3Jwe8jIzOfn6P7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/tIK/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+7u7t2tvYw8TAurq2oaCfXFdgU05XS0ZPSENMRkFKQj1GPzpDPzpDPzpDPzpDPzpDPDdAOjU+ODM8NzI7NzQ8OTY+OThAPDtDPTxEPj1FPT5GPj9HP0BIQEFJQUJKRENLTEtTVFNbVFNbVVNeXlxnZ2Vwb214eHaBeHaBeHaBx8bL5eXn/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+0gr+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7s6+zX1de+vL+gnaJGQUpGQUpMR1BTTldPSlNLRk9FQEk/OkNBPEVDPkdAO0Q9OEE9OkI+O0M8O0M9PEQ9PEQ+PUU+P0dBQkpERU1ISVFMTVVQUVlXWGBhYGhdXGRZV2JhX2ppZ3Jwbnl4doF4doHLys7n5uj+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7SCv7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/unp6tLR07q4vKShpmhjbF9aY1dSW0pFTj45QkVASU1IUUhDTEQ/SEI/R0I/Rz8+Rj49RT49RT49RT9ASENETEpLU1FSWldYYF5fZ2Vmbm5tdWVkbF1bZmVjbm1rdre2vMbFydrZ3Ovq7P7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/tIK/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+7ezt2djaxcPGvbu+oZ6jXllibGdwenV+c253bWhxaGVtZGFpXl1lW1piXVxkYF9nU1RcSElRTk9XVFVdbm93iYqSenuDbm11hYSMnZumu7q/09PW6ejq/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+0gr+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7q6evKx8ynoqunoquemaKWkZqNipKGg4t+fYV4d399fISCgYmHiJCOj5eTlJyYmaGlpq6ztLyztLzFxcnOzdHg4OLv7/H+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7SCv7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/u7t79fS29fS287J0sbBys3K0tbT29bV3djX39XU3NLR2dfY4N7f5+Pk7Ojp8eXm7uPk7OPk7PHx8/7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/tIK/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7++fn69PP17u3w7Ort6+ns6+rt7ezv7+7x7+7x7u7x7u7x7+/y8fH08/P29PT39PX49PT39vf5+vr7/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+0gr+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7SCv7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/tIK";
		String strJpg = base64BmpToJpg(strBmp);
		System.out.println("----jpg-----");
		System.out.println(strJpg);
		GenerateImage(strJpg, "D:\\12345.jpg");
		
//		String[] names = ImageIO.getWriterFormatNames();
//		for (int i=0; i<names.length; i++) {
//			System.out.println (names[i]);
//		}
		
		
		System.out.println("ok ok");
	}
	*/
	

	
	
	
	//------------以下是网上源码，稍有调整------------------------------------------------
	
	
	//-----------源码1-------------------
	
	public static boolean GenerateImage(String imgStr, String imgFilePath) {// 对字节数组字符串进行Base64解码并生成图片
		if (imgStr == null) // 图像数据为空
			return false;
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			// Base64解码
			byte[] bytes = decoder.decodeBuffer(imgStr);
			for (int i = 0; i < bytes.length; ++i) {
				if (bytes[i] < 0) {// 调整异常数据
					bytes[i] += 256;
				}
			}
			// 生成jpeg图片
			OutputStream out = new FileOutputStream(imgFilePath);
			out.write(bytes);
			out.flush();
			out.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public static String GetImageStr(String imgFilePath) {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        byte[] data = null;
        
        // 读取图片字节数组
        try {
            InputStream in = new FileInputStream(imgFilePath);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);// 返回Base64编码过的字节数组字符串
    }
	
	
	
	//-----------源码2-------------------
	
	
	/**
	 * 图片格式转换 BMP -> JPG
	 * @param file
	 * @param dstFile
	 */
	public static void bmpTojpg(String file, String dstFile) {
		try {
			FileInputStream in = new FileInputStream(file);
			Image TheImage = read(in);
			int wideth = TheImage.getWidth(null);
			int height = TheImage.getHeight(null);
			if (wideth<0) {
				wideth = -wideth;
			}
			if (height<0) {
				height = -height;
			}
			BufferedImage tag = new BufferedImage(wideth, height,BufferedImage.TYPE_INT_RGB);
			tag.getGraphics().drawImage(TheImage, 0, 0, wideth, height, null);
			FileOutputStream out = new FileOutputStream(dstFile);
//			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
//			encoder.encode(tag);
			ImageIO.write(tag, "JPEG" , out);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
		}
	}

	private static int constructInt(byte[] in, int offset) {
		int ret = ((int) in[offset + 3] & 0xff);
		ret = (ret << 8) | ((int) in[offset + 2] & 0xff);
		ret = (ret << 8) | ((int) in[offset + 1] & 0xff);
		ret = (ret << 8) | ((int) in[offset + 0] & 0xff);
		return ret;//(ret>=0?ret:-ret);
	}
	
	//返回正数
	public static int constructIntPositive(byte[] in, int offset) {
		int ret = constructInt(in, offset);
		return (ret>=0?ret:-ret);
	}

	private static int constructInt3(byte[] in, int offset) {
		int ret = 0xff;
		ret = (ret << 8) | ((int) in[offset + 2] & 0xff);
		ret = (ret << 8) | ((int) in[offset + 1] & 0xff);
		ret = (ret << 8) | ((int) in[offset + 0] & 0xff);
		return (ret);
	}

//	private static long constructLong(byte[] in, int offset) {
//		long ret = ((long) in[offset + 7] & 0xff);
//		ret |= (ret << 8) | ((long) in[offset + 6] & 0xff);
//		ret |= (ret << 8) | ((long) in[offset + 5] & 0xff);
//		ret |= (ret << 8) | ((long) in[offset + 4] & 0xff);
//		ret |= (ret << 8) | ((long) in[offset + 3] & 0xff);
//		ret |= (ret << 8) | ((long) in[offset + 2] & 0xff);
//		ret |= (ret << 8) | ((long) in[offset + 1] & 0xff);
//		ret |= (ret << 8) | ((long) in[offset + 0] & 0xff);
//		return (ret);
//	}

//	private static double constructDouble(byte[] in, int offset) {
//		long ret = constructLong(in, offset);
//		return (Double.longBitsToDouble(ret));
//	}

	private static short constructShort(byte[] in, int offset) {
		short ret = (short) ((short) in[offset + 1] & 0xff);
		ret = (short) ((ret << 8) | (short) ((short) in[offset + 0] & 0xff));
		return (ret);
	}

	static class BitmapHeader {
		public int iSize, ibiSize, iWidth, iHeight, iPlanes, iBitcount,
		iCompression, iSizeimage, iXpm, iYpm, iClrused, iClrimp;

		// 读取bmp文件头信息
		public void read(InputStream fs) throws IOException {
			final int bflen = 14;
			byte bf[] = new byte[bflen];
			fs.read(bf, 0, bflen);
			final int bilen = 40;
			byte bi[] = new byte[bilen];
			fs.read(bi, 0, bilen);
			iSize = constructInt(bf, 2);
			ibiSize = constructInt(bi, 2);
			iWidth = constructIntPositive(bi, 4);
			iHeight = constructIntPositive(bi, 8);
			iPlanes = constructShort(bi, 12);
			iBitcount = constructShort(bi, 14);
			iCompression = constructInt(bi, 16);
			iSizeimage = constructInt(bi, 20);
			iXpm = constructInt(bi, 24);
			iYpm = constructInt(bi, 28);
			iClrused = constructInt(bi, 32);
			iClrimp = constructInt(bi, 36);
		}
	}

	private static Image read(InputStream fs) {
		try {
			BitmapHeader bh = new BitmapHeader();
			bh.read(fs);
			if (bh.iBitcount == 24) {
				return (readImage24(fs, bh));
			}
			if (bh.iBitcount == 32) {
				return (readImage32(fs, bh));
			}
			fs.close();
		} catch (IOException e) {
			System.out.println(e);
		}
		return (null);
	}

	// 24位
	private static Image readImage24(InputStream fs, BitmapHeader bh)
			throws IOException {
		Image image;
		if (bh.iSizeimage == 0) {
			bh.iSizeimage = ((((bh.iWidth * bh.iBitcount) + 31) & ~31) >> 3);
			bh.iSizeimage *= bh.iHeight;
		}
		int npad = (bh.iSizeimage / bh.iHeight) - bh.iWidth * 3;
		int ndata[] = new int[bh.iHeight * bh.iWidth];
		byte brgb[] = new byte[(bh.iWidth + npad) * 3 * bh.iHeight];
		fs.read(brgb, 0, (bh.iWidth + npad) * 3 * bh.iHeight);
		int nindex = 0;
		for (int j = 0; j < bh.iHeight; j++) {
			for (int i = 0; i < bh.iWidth; i++) {
				ndata[bh.iWidth * (bh.iHeight - j - 1) + i] = constructInt3(
						brgb, nindex);
				nindex += 3;
			}
			nindex += npad;
		}
		image = Toolkit.getDefaultToolkit().createImage(
				new MemoryImageSource(bh.iWidth, bh.iHeight, ndata, 0,
						bh.iWidth));
		fs.close();
		return (image);
	}

	// 32位
	private static Image readImage32(InputStream fs, BitmapHeader bh)
			throws IOException {
		Image image;
		int iRealSize = bh.iHeight * bh.iWidth;
		if (iRealSize<0) {
			iRealSize = -iRealSize;
		}
		int ndata[] = new int[iRealSize];
		byte brgb[] = new byte[iRealSize * 4];
		fs.read(brgb, 0, iRealSize * 4);
		int nindex = 0;
		//for (int j = 0; j < bh.iHeight; j++) {
		for (int j = bh.iHeight-1; j >=0 ; j--) {
			for (int i = 0; i < bh.iWidth; i++) {
				ndata[bh.iWidth * (bh.iHeight - j - 1) + i] = constructInt3(
						brgb, nindex);
				nindex += 4;
			}
		}
		image = Toolkit.getDefaultToolkit().createImage(
				new MemoryImageSource(bh.iWidth, bh.iHeight, ndata, 0,
						bh.iWidth));
		fs.close();
		return (image);
	}
	

}
