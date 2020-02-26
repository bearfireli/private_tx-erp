package com.hntxrj.txerp.mapper;

import com.hntxrj.txerp.vo.PushTypeVO;
import com.hntxrj.txerp.vo.RecipientVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MessageMapper {

    //获取消息推送类型列表
    List<PushTypeVO> getPushTypeList(String compid);

    /**
     * 获取消息签收人列表
     *
     * @param compid 企业id
     * @param typeId 消息类型代号
     */
    List<RecipientVO> getRecipientList(String compid, Integer typeId);

    /**
     * 保存消息推送人
     *
     * @param compid   企业id
     * @param typeId   消息类型代号
     * @param userName 推送人姓名集合
     */
    void saveRecipient(Integer uid, String compid, Integer typeId, String userName, String phone);


    /**
     * 保存消息推送人
     *
     * @param compid   企业id
     * @param typeId   消息类型代号
     * @param userName 推送人姓名集合
     */
    void updateRecipient(Integer uid, String compid, Integer typeId, String userName, String phone);

    /**
     * 保存消息推送人
     *
     * @param uid 推送人id
     */
    RecipientVO getRecipientById(Integer uid,String compid,Integer typeId);

    /**
     * 根据企业和消息推送模块删除推送人
     */
    void deleteRecipient(String compid, Integer typeId);
}
