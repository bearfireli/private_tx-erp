package com.hntxrj.txerp.im;

public interface GroupService {


    /**
     * 创建群组
     * App 管理员可以通过该接口创建群组。
     */
    Object createGroup();

    /**
     * 导入群成员
     * 该 API 接口的作用是导入群组成员，不会触发回调、不会下发通知。
     * 当 App 需要从其他即时通信系统迁移到即时通信 IM 时，使用该协议导入存量群成员数据。
     */
    Object importGroupMember();
}
