package com.fh.shop.api.util;

import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import java.io.*;


public class Imgbianma {

	@SuppressWarnings("deprecation")
	public static String getImageBase(String src,HttpServletRequest request) {
		System.out.println(request.getServletContext().getRealPath("/"));
		System.out.println(src.replace(request.getContextPath(),""));
		      if(src==null||src==""){
		          return "";
		      }
		      File file = new File(request.getServletContext().getRealPath("/")+src.replace(request.getContextPath(),""));
		      if(!file.exists()) {
		          return "";
		      }
		      InputStream in = null;
		      byte[] data = null;  
		      try {
		          in = new FileInputStream(file);
		      } catch (FileNotFoundException e1) {
		          e1.printStackTrace();
		      }
		      try {  
		          data = new byte[in.available()];  
		          in.read(data);  
		          in.close();  
		      } catch (IOException e) {  
		        e.printStackTrace();  
		      } 
		      BASE64Encoder encoder = new BASE64Encoder();
		      return encoder.encode(data);
		  }
}
