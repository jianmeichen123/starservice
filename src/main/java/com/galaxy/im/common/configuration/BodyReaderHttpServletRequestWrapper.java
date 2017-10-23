package com.galaxy.im.common.configuration;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import jodd.io.StreamUtil;

public class BodyReaderHttpServletRequestWrapper extends HttpServletRequestWrapper{  
	  
   private final byte[] body; //用于保存读取body中数据   
      
   public BodyReaderHttpServletRequestWrapper(HttpServletRequest request) throws IOException {    
       super(request);    
       body = StreamUtil.readBytes(request.getReader(), "UTF-8");    
       }    
       
       @Override    
       public BufferedReader getReader() throws IOException {    
           return new BufferedReader(new InputStreamReader(getInputStream()));    
       }    
       
       @Override    
       public ServletInputStream getInputStream() throws IOException {    
           final ByteArrayInputStream bais = new ByteArrayInputStream(body);    
           return new ServletInputStream() {    
       
               @Override    
               public int read() throws IOException {    
                   return bais.read();    
               }  
 
               @Override  
               public boolean isFinished() {  
                   // TODO Auto-generated method stub  
                   return false;  
               }  
 
               @Override  
               public boolean isReady() {  
                   // TODO Auto-generated method stub  
                   return false;  
               }  
 
               @Override  
               public void setReadListener(ReadListener arg0) {  
                   // TODO Auto-generated method stub  
                 
           }    
       };    
   }    
  
}
