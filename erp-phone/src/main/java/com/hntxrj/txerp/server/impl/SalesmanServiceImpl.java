package com.hntxrj.txerp.server.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hntxrj.SyncPlugin;
import com.hntxrj.txerp.mapper.SalesmanMapper;
import com.hntxrj.txerp.server.SalesmanService;
import com.hntxrj.txerp.vo.PageVO;
import com.hntxrj.txerp.vo.SalesmanDropDownVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Service
public class SalesmanServiceImpl implements SalesmanService {

    private final SalesmanMapper salesmanMapper;
    private final SyncPlugin syncPlugin;

    @Autowired
    public SalesmanServiceImpl(SalesmanMapper salesmanMapper, SyncPlugin syncPlugin) {
        this.salesmanMapper = salesmanMapper;
        this.syncPlugin = syncPlugin;
    }

    @Override
    public PageVO<SalesmanDropDownVO> getSalesmanDropDown(String salesName, String compid,
                                                          Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        List<SalesmanDropDownVO> salesmanDropDownVOS =
                salesmanMapper.getSalesmanDropDown(salesName, compid);
        PageInfo<SalesmanDropDownVO> pageInfo = new PageInfo<>(salesmanDropDownVOS);
        PageVO<SalesmanDropDownVO> pageVO = new PageVO<>();
        pageVO.format(pageInfo);
        return pageVO;
    }
    @Override
    public List<SalesmanDropDownVO> getSalesmanDropDown(String salesName, String compid) {
        return salesmanMapper.getSalesmanDropDown(salesName, compid);
    }

    @Override
    public PageVO<SalesmanDropDownVO> getSaleGroup(String compid, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        List<SalesmanDropDownVO> saleGroup = salesmanMapper.getSaleGroup(compid);
        PageInfo<SalesmanDropDownVO> pageInfo = new PageInfo<>(saleGroup);
        PageVO<SalesmanDropDownVO> pageVO = new PageVO<>();
        pageVO.format(pageInfo);
        return pageVO;
    }

    @Override
    public void addSaleMan(String compid, String saleName, String department) {

        //查询所有销售员的编号
        List<String> saleCodes = salesmanMapper.selectSaleCodes(compid);
        //获取新增销售员的编号
        String saleManCode = getSaleManCode(saleCodes);
        salesmanMapper.addSaleMan(compid, saleName, saleManCode, department);
        Map<String, String> saleMan = salesmanMapper.getSaleMan(compid, saleManCode);
        try {
            syncPlugin.save(saleMan, "SM_BusinessGroup", "INS", compid);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    //得到新增销售员的编号
    private String getSaleManCode(List<String> saleCodes) {
        String saleManCode = "YW01000001";
        int code = 0;
        for (String saleCode : saleCodes) {
            String lastCode = saleCode.substring(7, 10);
            int num = Integer.parseInt(lastCode);
            if (num > code) {
                code = num;
            }
        }
        String maxSaleCode = String.valueOf(code + 1);
        if (maxSaleCode.length() == 1) {
            saleManCode = saleManCode.substring(0, 9) + maxSaleCode;
        } else if (maxSaleCode.length() == 2) {
            saleManCode = saleManCode.substring(0, 8) + maxSaleCode;
        } else {
            saleManCode = saleManCode.substring(0, 7) + maxSaleCode;
        }

        return saleManCode;
    }
}
