package com.myflair.admin.user.controller;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSON;
  
/** 
 * 文件上传处理类 
 * <功能详细描述> 
 *  
 * @author  Administrator 
 * @version  [版本号, 2014年3月6日] 
 * @see  [相关类/方法] 
 * @since  [产品/模块版本] 
 */  
@Controller
@RequestMapping("/uploadFile")  
public class UploadAction  
{  
	String errormsg = "请重新上传";
	String rowKey = "";
	MultipartFile uploadFile;
	private static final long MAX_UPLOAD_FILE_SIZE = 10 *1024*1024;//10M

    @RequestMapping(value = "/upload1", method = RequestMethod.POST)
    public void upload(HttpServletRequest request, HttpServletResponse response)   {
    	String info = "";
    	Result result = new Result("请登录", "","");
    	String returnurl = null;
    	try {
        	boolean valid = this.valid(request);
    		if (valid) {
    			String originalFilename = uploadFile.getOriginalFilename();
    			returnurl = "images/"+originalFilename;
    			String realPath = request.getSession().getServletContext().getRealPath("/ly/images");   
                //这里不必处理IO流关闭的问题，因为FileUtils.copyInputStreamToFile()方法内部会自动把用到的IO流关掉，我是看它的源码才知道的   
                FileUtils.copyInputStreamToFile(uploadFile.getInputStream(), new File(realPath, originalFilename)); 
    		}
    		result.setMsg("success");
    		
 		    result.setOriginalUrl(returnurl);
 		    result.setSmailUrl(returnurl);
 		   info = JSON.toJSONString(result);
		} catch (Exception e) {
			 result.setMsg(e.getMessage());
			 info = JSON.toJSONString(result);
		}finally {
		    try {
				IOUtils.write(info, response.getWriter());
			 } catch (IOException e) {
			 }
		}

    }
    private boolean valid(HttpServletRequest request) throws IOException {
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if (!isMultipart) {
			errormsg = "请选择文件上传";
			return false;
		}
		uploadFile = getOneMultipartFile((MultipartHttpServletRequest) request);
		if (uploadFile == null) {
			errormsg = "请选择文件上传";
			return false;
		}
		String fileType = uploadFile.getContentType();
		if (!cheackImgFileType(fileType)) {
			errormsg = "图片格式不正确";
			return false;
		}
		long fileSize = uploadFile.getSize();
		if (fileSize > MAX_UPLOAD_FILE_SIZE) {
			errormsg = "您上传的图片超过10M，请优化后再次上传！";
			return false;
		}
		/*BufferedImage bufferedImg = ImageIO.read(uploadFile.getInputStream());
		int imgWidth = bufferedImg.getWidth();
		int imgHeight = bufferedImg.getHeight();
		System.out.println("imgWidth:"+imgWidth);
		System.out.println("imgHeight:"+imgHeight);
		if(imgWidth<300 || imgHeight<300){
			errormsg = "图片尺寸不正确，应该尺寸不小于300*300px";
			return false;
		}*/
		return true;
	}
	private MultipartFile getOneMultipartFile(MultipartHttpServletRequest req) {
		Collection<MultipartFile> files = req.getFileMap().values();
		Iterator<MultipartFile> it = files.iterator();
		MultipartFile multipartFile = null;
		if (it.hasNext()) {
			// 只取一个值
			multipartFile = it.next();
		}
		return multipartFile;
	}
	private boolean cheackImgFileType(String fileType) {
		boolean result = false;
		if (StringUtils.isNotBlank(fileType)) {
			fileType = fileType.toLowerCase();
			if (fileType.endsWith("gif") || fileType.endsWith("jpeg")||fileType.endsWith("jpg") || fileType.endsWith("bmp") || fileType.endsWith("png")) {
				result = true;
			}
		}
		return result;
	}
    class Result {
	private String msg;
	private String originalUrl;
	private String smailUrl;
	
	
	public Result(String msg, String originalUrl, String smailUrl) {
		super();
		this.msg = msg;
		this.originalUrl = originalUrl;
		this.smailUrl = smailUrl;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getOriginalUrl() {
		return originalUrl;
	}
	public void setOriginalUrl(String originalUrl) {
		this.originalUrl = originalUrl;
	}
	public String getSmailUrl() {
		return smailUrl;
	}
	public void setSmailUrl(String smailUrl) {
		this.smailUrl = smailUrl;
	}

	
    }
}  