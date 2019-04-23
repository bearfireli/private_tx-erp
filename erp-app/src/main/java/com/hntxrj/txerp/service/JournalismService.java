package com.hntxrj.txerp.service;

import com.hntxrj.txerp.core.exception.ErpException;
import com.hntxrj.txerp.entity.base.Journalism;
import com.hntxrj.txerp.entity.base.User;
import com.hntxrj.txerp.vo.JournalismVO;
import com.hntxrj.txerp.vo.PageVO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface JournalismService {

    /*添加新闻
     * Journalism
     * file
     * token
     * */
    Journalism addJournalism(Journalism journalism, String token) throws ErpException;

    /**
     * 读取图片
     * @param img
     * @param response
     * @throws ErpException
     * @throws IOException
     */
    void getimg(String img, HttpServletResponse response) throws ErpException, IOException;

    /**
     * 上传图片
     * @param files
     * @param token
     * @return
     * @throws ErpException
     */
    String setHeader(MultipartFile files, String token) throws ErpException;

    /*查询列表
     * Journalism
     * token
     * file
     * */
    PageVO<JournalismVO> selectJournalism(Journalism journalism, String token, HttpServletResponse response, long page, long pageSize)throws Exception;

    List<Journalism> Journalismlist(Journalism journalism, String token) throws ErpException;
    /*查询详情
     * 根据jid*/
    Journalism  getJournalism(Integer id) throws ErpException;

    /*修改新闻
     * Journalism
     * */
    Journalism updateJournalism(Journalism journalism) throws ErpException;

    /*删除企业
     * 根据jid*/
    Journalism deleteJournalism(Integer id) throws ErpException;

    User findById(Integer userId) throws ErpException;
}

