package com.hntxrj.controller.base;

import com.hntxrj.util.App;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

@Scope("prototype")
public class FileController {

    @Autowired
    private App app;

    @GetMapping("/articleImage/*")
    public void fileController(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String url = request.getRequestURL().toString();
        System.out.println("[url]:" + url);
        File fileImage = new File(app.getSpterp().get("uploadpath") +
                url.split("/")[url.split("/").length - 1]);
        FileInputStream inputStream = new FileInputStream(fileImage);
        byte[] data = new byte[(int) fileImage.length()];
        int length = inputStream.read(data);
        inputStream.close();

        response.setContentType("image/png");
        OutputStream stream = response.getOutputStream();
        stream.write(data);
        stream.flush();
        stream.close();
    }


}


