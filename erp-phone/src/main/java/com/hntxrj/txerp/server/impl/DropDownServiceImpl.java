package com.hntxrj.txerp.server.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hntxrj.txerp.mapper.PublicInfoMapper;
import com.hntxrj.txerp.mapper.StockMapper;
import com.hntxrj.txerp.mapper.SupplierMapper;
import com.hntxrj.txerp.server.DropDownService;
import com.hntxrj.txerp.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DropDownServiceImpl implements DropDownService {

    private final PublicInfoMapper publicInfoMapper;
    private final StockMapper stockMapper;
    private final SupplierMapper supplierMapper;

    @Autowired
    public DropDownServiceImpl(PublicInfoMapper publicInfoMapper, StockMapper stockMapper,
                               SupplierMapper supplierMapper) {
        this.publicInfoMapper = publicInfoMapper;
        this.stockMapper = stockMapper;
        this.supplierMapper = supplierMapper;
    }

    @Override
    public List<DropDownVO> getDropDown(Integer classId, String compid) {
        return publicInfoMapper.getDropDown(classId,compid);
    }

    @Override
    public PageVO<SupplierVO> getSupplierList(String compid,Integer page,Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        List<SupplierVO> supplierList = supplierMapper.getSupplierList(compid);
        PageInfo<SupplierVO> pageInfo = new PageInfo<>(supplierList);
        PageVO<SupplierVO> pageVO = new PageVO<>();
        pageVO.format(pageInfo);
        return pageVO;
    }

    @Override
    public PageVO<StockVO> getStockList(String compid,Integer page,Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        List<StockVO> stockList = stockMapper.getStockList(compid);
        PageInfo<StockVO> pageInfo = new PageInfo<>(stockList);
        PageVO<StockVO> pageVO = new PageVO<>();
        pageVO.format(pageInfo);
        return pageVO;
    }
}
