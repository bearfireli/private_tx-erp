package com.hntxrj.txerp.controller.base;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hntxrj.txerp.server.FileUPServer;
import com.hntxrj.txerp.util.App;
import com.hntxrj.txerp.util.StringStream;
import com.hntxrj.txerp.vo.JsonVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * 小票签收上传图片
 */
@RequestMapping(value = "/fileup")
@RestController
@Scope("prototype")
@EnableAsync
public class FileUPController {
    private final FileUPServer fileUPServer;

    private final App app;

    @Autowired
    public FileUPController(FileUPServer fileUPServer, App app) {
        this.fileUPServer = fileUPServer;
        this.app = app;
    }

    /**
     * 上传图片
     *
     * @param compid  企业
     * @param opid    当前用户
     * @param id      id
     * @param base64  上传的图片
     * @param request request
     * @return json
     */
    @RequestMapping(value = "/spinsertfileImage.do")
    public JsonVo sp_insert_fileImage(String compid,
                                      String opid,
                                      String id,
                                      String base64,
                                      @RequestParam Double qiannum,
                                      HttpServletRequest request) {

        JsonVo jsonVo = new JsonVo();

        //base64  不能为空
        if (base64.isEmpty()) {
            jsonVo.setCode(1);
            jsonVo.setMsg("上传文件的为空");
            return jsonVo;
        }
        //保存的路径
        String uploadpath = app.getSpterp().get("uploadpath");
        File file = new File(uploadpath);
        //如果文件夹不存在则创建
        if (!file.exists()) {
            file.mkdirs();
        }
        //将String 装成 InputStream
        InputStream stream = StringStream.getStream(base64);
        //时间+uuid  为文件名
        SimpleDateFormat sim = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String format = sim.format(new Date());
        String uuid = format + UUID.randomUUID().toString().replace("-", "");

        //文件名称
        String destPath = uploadpath + "/" + uuid;
        //创建流
        BufferedInputStream bis = new BufferedInputStream(stream);
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(destPath));
            //输出到数组
            byte[] bt = new byte[1024 * 10 * 5];
            int len = 0;
            while ((len = bis.read(bt)) != -1) {
                bos.write(bt, 0, len);
            }
            bos.flush();

            //关闭流
            bis.close();
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
            jsonVo.setCode(1);
            jsonVo.setMsg("上传失败");
            return jsonVo;
        }

        JSONArray jsonArray = fileUPServer.sp_insert_fileImage(compid, opid, uuid, id, qiannum, "1");

        //获取返回提示
        if (jsonArray != null && jsonArray.size() > 0) {
            JSONObject jsonObject = (JSONObject) jsonArray.getJSONArray(0).get(0);
            String result = jsonObject.getString("result");
            if (result.contains("完成")) {
                jsonVo.setCode(0);
                jsonVo.setMsg(result);
                jsonVo.setData(jsonArray);
                return jsonVo;
            }
            if (result.contains("失败")) {
                jsonVo.setCode(1);
                jsonVo.setMsg(result);
                return jsonVo;
            }

        }

        jsonVo.setCode(1);
        jsonVo.setMsg("保存错误");
        return jsonVo;


    }


    /**
     * 获取签字  图片
     *
     * @param id      小票id
     * @param compid  企业
     * @param opid    当前用户
     * @param request request
     * @return json
     */
    @RequestMapping(value = "/spfiledown.do")
    public JsonVo sp_filedown(@RequestParam String id,
                              @RequestParam String compid,
                              String opid,
                              HttpServletRequest request,
                              HttpServletResponse response) {

        JsonVo jsonVo = new JsonVo();
        JSONArray jsonArray = fileUPServer.sp_filedown(id, compid, opid);

        //jsonArry  不能为空
        if (jsonArray != null && jsonArray.size() > 0) {
            JSONObject jsonObject = (JSONObject) jsonArray.getJSONArray(0).get(0);
            //库里存的地址
            String result = jsonObject.getString("result");

            //当数据库没查到对应的路径  ,未签字
            if (result.contains("未签字")) {
                jsonVo.setCode(1);
                jsonVo.setMsg(result);
                return jsonVo;

            }
            //签字就读取对应的文件 成 FileInputStream  流  ; 在把流转成  String 返回前台

//           String realPath = request.getRealPath("/");
//           String downPath =realPath +result;

            //真实路径
            String uploadpath = app.getSpterp().get("uploadpath");

            uploadpath = uploadpath + "/" + result;
            File file = new File(uploadpath);

            if (file.exists() && file.isFile()) {

                FileInputStream fileInputStream = null;
                try {
                    fileInputStream = new FileInputStream(file);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    jsonVo.setCode(1);
                    jsonVo.setMsg("文件不存在,查询失败");
                    return jsonVo;
                }

                //流转 String
                String string = StringStream.getString(fileInputStream);

                //流转  String  不能为空
                if (string == null) {
                    jsonVo.setCode(1);
                    jsonVo.setMsg("文件有误 ,查看失败");
                    return jsonVo;

                }

                jsonVo.setCode(0);
                jsonVo.setData(string);
                jsonVo.setMsg("ok");
                return jsonVo;

            }

        }
        jsonVo.setCode(1);
        jsonVo.setData(jsonArray);
        jsonVo.setMsg("查看失败");

        return jsonVo;


    }

    /**
     * 取消小票签收
     *
     * @param mark   1 上传   2  取消
     * @param compid 企业
     * @param opid   用户
     * @param id     小票id
     * @param num    方量
     * @return
     */
    @RequestMapping(value = "/editFile.do")
    public JsonVo editFile(String mark,
                           String compid,
                           String opid,
                           String id,
                           Double num) {
        JsonVo jsonVo = new JsonVo();
        //调用  dao
        JSONArray array = fileUPServer.sp_insert_fileImage(compid, opid, null, id, num, mark);
        JSONObject jsonObject = (JSONObject) array.getJSONArray(0).get(0);
        String result = jsonObject.getString("result");
        //包含失败返回失败提示
        if (result.contains("失败")) {
            jsonVo.setCode(1);
            jsonVo.setMsg(result);
            jsonVo.setData(array);
            return jsonVo;
        }
        //否则成功
        jsonVo.setCode(0);
        jsonVo.setMsg(result);
        jsonVo.setData(array);
        return jsonVo;
    }


}
