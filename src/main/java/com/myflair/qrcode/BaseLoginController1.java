package com.myflair.qrcode;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Hashtable;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.myflair.common.utils.EncryptUtils;

/**
 * Description: 登录通用控制器
 */
@Controller
public class BaseLoginController1 {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BaseLoginController1.class);

	private static final String shareUrl="http://www.myflair.com.cn/myflair/myflair2/coupontpl.html?origin=myflairlx&mobile=";

	@RequestMapping(value = "/getERWMImage1", method = RequestMethod.GET)
	public void show1(HttpServletRequest request, HttpServletResponse response){  
        response.setHeader("Pragma", "No-cache");  
        response.setHeader("Cache-Control", "no-cache");  
        response.setDateHeader("Expires", 0);  
        response.setContentType("image/png");  
        BitMatrix bitMatrix = null;  
        try {
        	String salercodeString=request.getParameter("mobile");
//        		if(!StringUtils.isEmpty(EncryptUtils.decrypt("mobile", salercodeString))){
        			String shareUrl = BaseLoginController1.shareUrl+salercodeString;
        			Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
        			hints.put(EncodeHintType.CHARACTER_SET, "utf-8"); // 文字编码。
        			hints.put(EncodeHintType.MARGIN, 1);
        			hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        			bitMatrix = new MultiFormatWriter().encode(shareUrl, BarcodeFormat.QR_CODE, 240, 240, hints);
					InputStream inputStream = BaseLoginController1.class.getClassLoader().getResourceAsStream("sharebg.jpg");
					BufferedImage image = ImageIO.read(inputStream);
					BufferedImage image2 = MatrixToImageWriter.toBufferedImage(bitMatrix);
					Graphics2D g2d=(Graphics2D)image.getGraphics();
					g2d.setColor(Color.black);
					g2d.drawImage(image2, 220, 765, 210, 210, null);
					ImageIO.write(image, "PNG", response.getOutputStream());
					image.flush();
					image2.flush();
					bitMatrix.clear();
					inputStream.close();
					response.getOutputStream().flush();
					response.getOutputStream().close();
//				}
        } catch (WriterException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }
	

}
