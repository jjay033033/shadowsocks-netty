/**
 * 
 */
package priv.lmoon.shadowsupdate.qrcode;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Binarizer;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;

import priv.lmoon.shadowsupdate.util.CloseUtil;
import priv.lmoon.shadowsupdate.util.UrlContent;

/**
 * @author LMoon
 * @date 2017年7月27日
 * 
 */
public class ZxingQRcoder implements QRcoder {

	private static final Logger logger = LoggerFactory.getLogger(ZxingQRcoder.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see top.lmoon.shadowsupdate.qrcode.QRcoder#encode(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public void encode(String content, String filepath) {
		try {
			String format = "png";// 图像类型
			BitMatrix bitMatrix = getBitMatrix(content);// 生成矩阵
			Path path = FileSystems.getDefault().getPath(filepath);
			MatrixToImageWriter.writeToPath(bitMatrix, format, path);// 输出图像
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("", e);
		}

	}

	private BitMatrix getBitMatrix(String content) throws WriterException {
		int width = 180; // 图像宽度
		int height = 180; // 图像高度
		Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		hints.put(EncodeHintType.MARGIN, 1);
		BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);// 生成矩阵
		return bitMatrix;
	}
	
	public void encode(String content, OutputStream os){
		try {
			String format = "jpeg";// 图像类型
			BitMatrix bitMatrix = getBitMatrix(content);// 生成矩阵			
			MatrixToImageWriter.writeToStream(bitMatrix, format, os);		
//			BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
//			ImageIO.write(bufferedImage, format, os);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see top.lmoon.shadowsupdate.qrcode.QRcoder#decode(java.lang.String)
	 */
	@Override
	public String decode(String urlStr) {
		InputStream inputStream = null;
		try {			
			inputStream = UrlContent.getUrlInputStream(urlStr);
			BufferedImage image = ImageIO.read(inputStream);
			LuminanceSource source = new BufferedImageLuminanceSource(image);
			Binarizer binarizer = new HybridBinarizer(source);
			BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
			Map<DecodeHintType, Object> hints = new HashMap<DecodeHintType, Object>();
			hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");
			hints.put(DecodeHintType.PURE_BARCODE, Boolean.TRUE);
			// hint.put(DecodeHintType., "UTF-8");
			Result result = new MultiFormatReader().decode(binaryBitmap, hints);
			return result.getText();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			CloseUtil.closeSilently(inputStream);
		}
		return null;
	}

//	public void testEncode() throws WriterException, IOException {
//		String filePath = "D://";
//		String fileName = "zxing.png";
//		JSONObject json = new JSONObject();
//		json.put("zxing", "https://github.com/zxing/zxing/tree/zxing-3.0.0/javase/src/main/java/com/google/zxing");
//		json.put("author", "shihy");
//		String content = json.toString();// 内容
//		encode(content, filePath + fileName);
//		System.out.println("输出成功.");
//	}
//
//	/**
//	 * 解析图像
//	 */
//	public void testDecode() {
//		String filePath = "D://zxing.png";
//		BufferedImage image;
//		try {
//			image = ImageIO.read(new File(filePath));
//			LuminanceSource source = new BufferedImageLuminanceSource(image);
//			Binarizer binarizer = new HybridBinarizer(source);
//			BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
//			Map<DecodeHintType, Object> hints = new HashMap<DecodeHintType, Object>();
//			hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");
//			Result result = new MultiFormatReader().decode(binaryBitmap, hints);// 对图像进行解码
//			JSONObject content = JSONObject.fromObject(result.getText());
//			System.out.println("图片中内容：  ");
//			System.out.println("author： " + content.getString("author"));
//			System.out.println("zxing：  " + content.getString("zxing"));
//			System.out.println("图片中格式：  ");
//			System.out.println("encode： " + result.getBarcodeFormat());
//		} catch (IOException e) {
//			e.printStackTrace();System.out.println(ExceptionUtil.getExceptionMessage(e));
//		} catch (NotFoundException e) {
//			e.printStackTrace();System.out.println(ExceptionUtil.getExceptionMessage(e));
//		}
//	}

	public static void main(String[] args) throws WriterException, IOException {
		ZxingQRcoder qr = new ZxingQRcoder();
		qr.encode("http://my.shadowsocks8.org/", new FileOutputStream(new File("d://tt.jpg")));
//		System.out.println(qr.decode("http://my.shadowsocks8.org/images/server03.png"));
//		 qr.testEncode();
		// qr.testDecode();
	}

}
