package com.hntxrj.txerp.server.impl;

import com.hntxrj.txerp.mapper.PublicInfoMapper;
import com.hntxrj.txerp.server.DropDownService;
import com.hntxrj.txerp.vo.DropDownVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DropDownServiceImpl implements DropDownService {

    private final PublicInfoMapper publicInfoMapper;

    @Autowired
    public DropDownServiceImpl(PublicInfoMapper publicInfoMapper) {
        this.publicInfoMapper = publicInfoMapper;
    }

    @Override
    public List<DropDownVO> getDropDown(Integer classId, String compid) {
        return publicInfoMapper.getDropDown(classId,compid);
    }
}
