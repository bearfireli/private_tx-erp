package com.hntxrj.txerp.api;

import com.hntxrj.txerp.core.exception.ErpException;
import com.hntxrj.txerp.im.AccountService;
import com.hntxrj.txerp.im.FriendService;
import com.hntxrj.txerp.vo.IMUserVO;
import com.hntxrj.txerp.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/im")
public class ImApi {

    private final AccountService accountService;
    private final FriendService friendService;

    @Autowired
    public ImApi(AccountService accountService, FriendService friendService) {
        this.accountService = accountService;
        this.friendService = friendService;
    }


    /**
     * 向腾讯云即时通讯IM中导入用户
     *
     * @param imUserVO 需要导入的用户对象
     */
    @PostMapping("/accountImport")
    public ResultVO accountImport(IMUserVO imUserVO) throws ErpException {
        accountService.accountImport(imUserVO);
        return ResultVO.create();
    }

    /**
     * 给指定用户添加本企业所有其他用户的好友
     *
     * @param userID 用户账号
     * @param eid    用户所属企业
     */
    @PostMapping("/friendImport")
    public ResultVO friendImport(String userID, Integer eid) {
        friendService.friendImport(userID, eid);
        return ResultVO.create();
    }


    /**
     * 删除即时通讯中所有IM的用户
     * */
    @PostMapping("multiAccountDelete")
    public ResultVO multiAccountDelete() throws ErpException {
        accountService.multiAccountDelete();
        return ResultVO.create();
    }

}
