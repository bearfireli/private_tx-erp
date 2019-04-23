package com.hntxrj.txerp.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hntxrj.txerp.core.exception.ErpException;
import com.hntxrj.txerp.core.web.ResultVO;
import com.hntxrj.txerp.entity.base.Journalism;
import com.hntxrj.txerp.service.JournalismService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/journalism")
@Slf4j
public class JournalismController {
    private final JournalismService journalismService;
    private ResultVO resultVO;
@Autowired
    public JournalismController(JournalismService journalismService) {
    this.journalismService = journalismService;
        resultVO = new ResultVO();
        resultVO.setCode(0);
        resultVO.setMsg("ok");
        resultVO.setData(null);
    }
    @PostMapping("/selectjournalism")
    public String selectJournalism(Journalism journalism,@RequestHeader String token, HttpServletResponse response,
                                   @RequestParam(defaultValue = "1") long page,
                                   @RequestParam(defaultValue = "10") long pageSize) throws Exception {
        resultVO.setData(JSON.toJSONString(journalismService.selectJournalism(journalism,token,response,page,pageSize)));
        return JSON.toJSONString(resultVO);
    }
    @PostMapping("/journalismlist")
    public String journalismlist(Journalism journalism,@RequestHeader String token) throws Exception {
        resultVO.setData(journalismService.Journalismlist(journalism,token));
        return JSON.toJSONString(resultVO);
    }
    @PostMapping("/getJournalism")
    public String getJournalism(Integer id)throws Exception{
        resultVO.setData(journalismService.getJournalism(id));
        return JSON.toJSONString(resultVO);
    }
    @PostMapping("/updatejournalism")
    public String updateJournalism(Journalism journalism)throws Exception{
        resultVO.setData(JSON.toJSONString(journalismService.updateJournalism(journalism)));
        return JSON.toJSONString(resultVO);
    }
    @PostMapping("/deleteJournalism")
    public String deleteJournalism(Integer id) throws Exception {
        resultVO.setData(JSON.toJSONString(journalismService.deleteJournalism(id)));
        return JSON.toJSONString(resultVO);
    }

    @PostMapping("/saveJournalism")
    public String  saveJournalism(Journalism journalism , @RequestHeader String token)throws Exception{
        resultVO.setData(journalismService.addJournalism(journalism,token));
        return JSON.toJSONString(resultVO);
    }

    @PostMapping("/setHeader")
    public String setHeader(MultipartFile files,
                            @RequestHeader String token) throws ErpException {

        resultVO.setData(JSON.parse(JSON.toJSONString(journalismService.setHeader(files, token))));

        return JSON.toJSONString(resultVO);
    }


    @GetMapping("/images/{filename}")
    public void getimg(@PathVariable("filename") String fileName,HttpServletResponse response) throws ErpException, IOException {
        journalismService.getimg(fileName,response);
    }

}
