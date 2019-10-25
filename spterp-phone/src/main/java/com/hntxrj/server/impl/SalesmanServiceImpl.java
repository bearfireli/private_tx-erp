package com.hntxrj.server.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hntxrj.mapper.SalesmanMapper;
import com.hntxrj.server.SalesmanService;
import com.hntxrj.vo.PageVO;
import com.hntxrj.vo.SalesmanDropDownVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalesmanServiceImpl implements SalesmanService {

    private final SalesmanMapper salesmanMapper;

    @Autowired
    public SalesmanServiceImpl(SalesmanMapper salesmanMapper) {
        this.salesmanMapper = salesmanMapper;
    }

    @Override
    public PageVO<SalesmanDropDownVO> getSalesmanDropDown(String salesName, String compid,
                                                          Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        List<SalesmanDropDownVO> salesmanDropDownVOS =
                salesmanMapper.getSalesmanDropDown(salesName, compid, page, pageSize);
        PageInfo<SalesmanDropDownVO> pageInfo = new PageInfo<>(salesmanDropDownVOS);
        PageVO<SalesmanDropDownVO> pageVO = new PageVO<>();
        pageVO.format(pageInfo);
        return pageVO;
    }
}
