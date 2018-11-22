package cn.cjgl.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("test")
public class TestController {
	private static Logger logger = Logger.getLogger(TestController.class);
	
	@RequestMapping(value="upload.do")
	@ResponseBody
	public String upload(HttpServletRequest request, HttpServletResponse response,@RequestParam(value = "upload", required = false) MultipartFile file) throws IOException{
		logger.info("upload");

		@SuppressWarnings("unused")
		String expandedName = ""; // �ļ���չ��
		String uploadContentType = file.getContentType();
		logger.info(uploadContentType);
		if (uploadContentType.equals("image/jpeg")
				|| uploadContentType.equals("image/jpeg")) {
			expandedName = ".jpg";
		} else if (uploadContentType.equals("image/png")
				|| uploadContentType.equals("image/x-png")) {
			expandedName = ".png";
		} else if (uploadContentType.equals("image/gif")) {
			expandedName = ".gif";
		} else if (uploadContentType.equals("image/bmp")) {
			expandedName = ".bmp";
		} else {
			return "{\"result\":400,\"msg\":\"文件格式不正确（必须为.jpg/.gif/.bmp/.png文件）\"}";
		}
		if (file.getSize() > 1024 * 1024 * 20) {
			return "{\"result\":400,\"msg\":\"文件大小不得大于20M\"}";
		}

		String path = request.getSession().getServletContext().getRealPath("/img/uploadImg");  
        String fileName = file.getOriginalFilename();  
        //        String fileName = new Date().getTime()+".jpg";  
        
        File targetFile = new File(path, fileName);  
        if(!targetFile.exists()){  
            targetFile.mkdirs();  
        }  
  
        try {  
            file.transferTo(targetFile);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }
        
		return "{\"result\":200,\"msg\":\"上传成功\",\"imgurl\":\"" + request.getContextPath() + "/img/uploadImg/"+ fileName  + "\"}";
	}
	
}
