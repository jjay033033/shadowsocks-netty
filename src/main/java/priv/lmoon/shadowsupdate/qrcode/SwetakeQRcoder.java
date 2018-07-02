package priv.lmoon.shadowsupdate.qrcode;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.swetake.util.Qrcode;

import jp.sourceforge.qrcode.QRCodeDecoder;
import jp.sourceforge.qrcode.data.QRCodeImage;
import priv.lmoon.shadowsupdate.util.CloseUtil;
import priv.lmoon.shadowsupdate.util.UrlContent;

public class SwetakeQRcoder implements QRcoder{
	
	private static final Logger logger = LoggerFactory.getLogger(SwetakeQRcoder.class);
	
	@Override
	public void encode(String content,String filepath){
		encode(content, 10,filepath);
	}

	public void encode(final String content, final Integer size,String filepath){

		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(new File(filepath));

			BufferedImage bufImg = null;
			Qrcode qrcodeHandler = new Qrcode();
			// 设置二维码排错率，可选L(7%)、M(15%)、Q(25%)、H(30%)，排错率越高可存储的信息越少，但对二维码清晰度的要求越小
			qrcodeHandler.setQrcodeErrorCorrect('M');
			qrcodeHandler.setQrcodeEncodeMode('B');
			// 设置设置二维码尺寸，取值范围1-40，值越大尺寸越大，可存储的信息越大
			qrcodeHandler.setQrcodeVersion(size);
			// 获得内容的字节数组，设置编码格式
			byte[] contentBytes = content.getBytes("utf-8");
			// 图片尺寸
			int imgSize = 67 + 12 * (size - 1);
			bufImg = new BufferedImage(imgSize, imgSize,
					BufferedImage.TYPE_INT_RGB);
			Graphics2D gs = bufImg.createGraphics();
			// 设置背景颜色
			gs.setBackground(Color.WHITE);
			gs.clearRect(0, 0, imgSize, imgSize);
			// 设定图像颜色> BLACK
			gs.setColor(Color.BLACK);
			// 设置偏移量，不设置可能导致解析出错
			int pixoff = 2;
			// 输出内容> 二维码
			if (contentBytes.length > 0 && contentBytes.length < 800) {
				boolean[][] codeOut = qrcodeHandler.calQrcode(contentBytes);
				for (int i = 0; i < codeOut.length; i++) {
					for (int j = 0; j < codeOut.length; j++) {
						if (codeOut[j][i]) {
							gs.fillRect(j * 3 + pixoff, i * 3 + pixoff, 3, 3);
						}
					}
				}
			} else {
				throw new Exception("QRCode content bytes length = "
						+ contentBytes.length + " not in [0, 800].");
			}
			gs.dispose();

			bufImg.flush();
			// 生成二维码QRCode图片
			ImageIO.write(bufImg, "jpg", fos);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("",e);
		}finally{
			if(fos!=null){
				try {
					fos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	@Override
	public String decode(String urlStr) {
		BufferedImage bi = null;
		QRCodeDecoder decoder = new QRCodeDecoder(); 
		InputStream inputStream = null;					
		try {
			inputStream = new URL(urlStr).openStream();
		} catch (Exception e) {
//			e.printStackTrace();
//			logger.error("decode:", e);
			try {
				inputStream = UrlContent.getUrlInputStream(urlStr);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				logger.error("decode2:", e);
			}	
		}
		if(inputStream==null){
			return null;
		}
		try {
			bi = ImageIO.read(inputStream);
			if(bi==null){
				return null;
			}
			byte[] bytes = decoder.decode(new MyQRCodeImage(bi));
			return new String(bytes);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			CloseUtil.closeSilently(inputStream);
		}
		return null;
	}
	
	private static class MyQRCodeImage implements QRCodeImage{
		
		BufferedImage bufferedImage;
		
		public MyQRCodeImage(BufferedImage bufferedImage){
			this.bufferedImage = bufferedImage;
		}

		/* (non-Javadoc)
		 * @see jp.sourceforge.qrcode.data.QRCodeImage#getHeight()
		 */
		@Override
		public int getHeight() {
			// TODO Auto-generated method stub
			return bufferedImage.getHeight();
		}

		/* (non-Javadoc)
		 * @see jp.sourceforge.qrcode.data.QRCodeImage#getPixel(int, int)
		 */
		@Override
		public int getPixel(int x, int y) {
			// TODO Auto-generated method stub
			return bufferedImage.getRGB(x, y);
		}

		/* (non-Javadoc)
		 * @see jp.sourceforge.qrcode.data.QRCodeImage#getWidth()
		 */
		@Override
		public int getWidth() {
			// TODO Auto-generated method stub
			return bufferedImage.getWidth();
		}
		
	}
	
	public static void main(String[] args) throws Exception {
//		new QRcoder().encoderQRCoder("abcdefg", 10,"d:/abc.jpg");
		System.out.println(new SwetakeQRcoder().decode("http://www.shadowsocks8.net/images/server01.png"));
	}
}
