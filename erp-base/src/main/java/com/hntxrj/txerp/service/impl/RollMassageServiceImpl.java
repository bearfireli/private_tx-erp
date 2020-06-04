package com.hntxrj.txerp.service.impl;

import com.hntxrj.txerp.core.exception.ErpException;
import com.hntxrj.txerp.core.util.SimpleDateFormatUtil;
import com.hntxrj.txerp.entity.base.User;
import com.hntxrj.txerp.mapper.RollMessageMapper;
import com.hntxrj.txerp.service.RollMassageService;
import com.hntxrj.txerp.service.UserService;
import com.hntxrj.txerp.vo.RollMessageVO;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class RollMassageServiceImpl implements RollMassageService {

    private final RollMessageMapper rollMessageMapper;
    private final UserService userService;
    private final SimpleDateFormat sdf = SimpleDateFormatUtil.getDefaultSimpleDataFormat();

    public RollMassageServiceImpl(RollMessageMapper rollMessageMapper, UserService userService) {
        this.rollMessageMapper = rollMessageMapper;
        this.userService = userService;
    }


    @Override
    public void addRollMessage(String token, String content, String compid, Byte type, String beginTime,
                               String endTime) throws ErpException {
        User user = userService.tokenGetUser(token);
        String currentTime = sdf.format(new Date());

        rollMessageMapper.addRollMessage(user.getUid(), content, compid, type, beginTime, endTime, currentTime);
    }

    @Override
    public void removeRollMessage(Integer id) {
        rollMessageMapper.removeRollMessage(id);
    }

    @Override
    public void updateRollMessage(Integer id, String compid, String content, String beginTime, String endTime, Byte type) {
        String currentTime = sdf.format(new Date());
        rollMessageMapper.updateRollMessage(id, compid, content, beginTime, endTime, type, currentTime);
    }

    @Override
    public List<RollMessageVO> getRollMessage(String compid) {
        String currentTime = sdf.format(new Date());
        return rollMessageMapper.getRollMessage(compid,currentTime);
    }
}
