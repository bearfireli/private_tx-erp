package com.hntxrj.txerp.server.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hntxrj.txerp.mapper.PlacingMapper;
import com.hntxrj.txerp.server.PlacingService;
import com.hntxrj.txerp.vo.PageVO;
import com.hntxrj.txerp.vo.PlacingDropDownVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

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