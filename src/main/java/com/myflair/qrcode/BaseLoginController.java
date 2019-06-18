package com.myflair.qrcode;

import java.io.IOException;
import java.util.Hashtable;

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
@RequestMapping("/")
public class BaseLoginController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BaseLoginController.class);
	/*@Autowired
	private DefaultKaptcha sZKaptcha ;*/
	
/*    @RequestMapping(value = "/getYzmImage", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getKaptchaImage(HttpSession session) {
    	
    	ResponseEntity<byte[]> responseEntity = null;
    	ByteArrayOutputStream out = null;
   
    	try {
	        String capText = sZKaptcha.createText(); 
	        LOGGER.debug("******************生成验证码: {}******************",capText);  
	        
	        //存贮验证码
	        String CAP_TEXT_KEY = "asdfsadf";
	        RedisUtil.getRedisValueCommands().set(RedisUtil.NAMESPACE,CAP_TEXT_KEY,capText,10,TimeUnit.MINUTES);
	        
	        // 创建图片
	        BufferedImage bi = sZKaptcha.createImage(capText);  
	
	        out = new ByteArrayOutputStream();
			ImageIO.write(bi, "JPEG", out);
			
			HttpHeaders  headers= new HttpHeaders();
			headers.setCacheControl("no-store, no-cache, must-revalidate, post-check=0, pre-check=0"); 
			headers.setPragma("no-cache");  
			headers.setExpires(0);  
			headers.setContentType(MediaType.IMAGE_JPEG);
			
			responseEntity = new ResponseEntity<byte[]>(out.toByteArray(), headers, HttpStatus.OK);
		} catch (IOException e) {
			LOGGER.error("生成验证码异常：{}", e.getMessage());
		} finally {
			if(out != null) {
				IOUtils.closeQuietly(out); //关闭流
			}
		}
        
		return responseEntity;
    }*/
	
	private static final String shareUrl="https://mktm.10101111cdn.com/html5/2015/salespull/get.html?salercode=";
    private static final String logoPath="https://img01.10101111cdn.com/mkt/bak/2015/wap/salespull/logo.png";
	
	@RequestMapping(value = "/getERWMImage", method = RequestMethod.GET)
	public void show(HttpServletRequest request, HttpServletResponse response){  
        response.setHeader("Pragma", "No-cache");  
        response.setHeader("Cache-Control", "no-cache");  
        response.setDateHeader("Expires", 0);  
        response.setContentType("image/png");  
        BitMatrix bitMatrix = null;  
        try {
        	String salercodeString=request.getParameter("salercode");
        		if(!StringUtils.isEmpty(EncryptUtils.decrypt("salercode", salercodeString))){
        			String logoPath = BaseLoginController.logoPath;
        			String shareUrl = BaseLoginController.shareUrl+salercodeString;
        			Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
        			hints.put(EncodeHintType.CHARACTER_SET, "utf-8"); // 文字编码。
        			hints.put(EncodeHintType.MARGIN, 1);
        			hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        			bitMatrix = new MultiFormatWriter().encode(shareUrl, BarcodeFormat.QR_CODE, 240, 240, hints); 
        			MatrixToImageWriter.writeToStream(bitMatrix, "png", response.getOutputStream(),logoPath);  
        			response.getOutputStream().flush();  
        			response.getOutputStream().close();  
        		}
        } catch (WriterException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }
	
	
}
