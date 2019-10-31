package com.fh.shop.api.common;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FileInput {

    public  static Map fileinput(
                            CommonsMultipartFile f) throws IllegalStateException{

        HttpServletRequest request=((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();

        String realPath = request.getServletContext().getRealPath("/");
        String imgDir = realPath+"/img/";
        File file=new File(imgDir);
        if (!file.exists()) {
            file.mkdir();
        }
        String originalFilename = f.getOriginalFilename();
        String sub=originalFilename.substring(originalFilename.lastIndexOf("."));
        String newName=UUID.randomUUID().toString()+sub;
         Map map=new HashMap();
        try {
            f.transferTo(new File(imgDir+"/"+newName));
            String url="/"+"img/"+newName;
            map.put("url", url);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return map;
    }
}
