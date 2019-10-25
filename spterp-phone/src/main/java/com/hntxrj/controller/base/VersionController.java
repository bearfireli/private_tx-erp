package com.hntxrj.controller.base;

import com.hntxrj.util.App;
import com.hntxrj.util.StringStream;
import com.hntxrj.vo.JsonVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by tx-zcl on 2017-10-17.
 */
@RestController
@Scope("prototype")
@EnableAsync
public class VersionController {

    private final App app;

    @Autowired
    public VersionController(App app) {
        this.app = app;
    }

    @RequestMapping(value = "/versionfile/{fName}")
    public void versionController(@PathVariable String fName, HttpServletResponse response) throws IOException {
        //分解为空直接返回
        if (fName == null || "".equals(fName)) {
            response.getWriter().print("文件名不能为空");
            return;
        }

        //获取存放路径
        String uploadpath = app.getSpterp().get("version").toString();

        System.out.println(uploadpath);
        uploadpath = uploadpath + fName + ".txt";
        File file = new File(uploadpath);
        //读取文件
        if (file.exists() && file.isFile()) {
            //常见输入流
            FileInputStream fis = new FileInputStream(file);
            //流转成String   response 到也页面
            String string = StringStream.getString(fis);
            //response到页面
            response.getWriter().print(string);
            return;
        } else {
            response.getWriter().print(fName + ".txt文件不存在");
            return;
        }

    }


    @RequestMapping("/MP_verify_dTkuIa7X6O7zPPEl.txt")
    public String weiCharAuthorization() {
        return "dTkuIa7X6O7zPPEl";
    }

    @RequestMapping("/MP_verify_3tSy9CSKAM44EqYc.txt")
    public String auth() {
        return "3tSy9CSKAM44EqYc";
    }
}
