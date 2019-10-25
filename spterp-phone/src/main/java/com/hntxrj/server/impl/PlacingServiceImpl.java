package com.hntxrj.server.impl;

import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hntxrj.dao.BuilderDao;
import com.hntxrj.entity.PageBean;
import com.hntxrj.mapper.BuilderMapper;
import com.hntxrj.mapper.PlacingMapper;
import com.hntxrj.server.BuilderService;
import com.hntxrj.server.PlacingService;
import com.hntxrj.vo.BuilderDropDownVO;
import com.hntxrj.vo.DropDownVO;
import com.hntxrj.vo.PageVO;
import com.hntxrj.vo.PlacingDropDownVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

/**
 * 功能:   浇筑部位服务接口实现层
 */
@Service
@Scope("prototype")

public class PlacingServiceImpl implements PlacingService {


    private final PlacingMapper placingMapper;

    @Autowired
    public PlacingServiceImpl(PlacingMapper placingMapper) {
        this.placingMapper = placingMapper;
    }



    @Override
    public PageVO<PlacingDropDownVO> getPlacingDropDown(String compid, String placing, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        List<PlacingDropDownVO> placingDropDownVOS = placingMapper.getPlacingDropDown(compid, placing);
        PageInfo<PlacingDropDownVO> pageInfo = new PageInfo<>(placingDropDownVOS);
        PageVO<PlacingDropDownVO> pageVO = new PageVO<>();
        pageVO.format(pageInfo);
        return pageVO;
    }
}