package com.hntxrj.txerp.server.impl;

import com.hntxrj.txerp.entity.MsgQueue;
import com.hntxrj.txerp.server.MsgQueueService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class MsgQueueServiceImpl implements MsgQueueService {
    @Override
    public List<MsgQueue> readNotPushMsg() {
        return null;
    }

    @Override
    public void push(List<MsgQueue> msgQueues) {
        for (MsgQueue msg : msgQueues) {
            push(msg);
        }
    }


    private void push(MsgQueue msgQueue) {
        //将msgKeys转换为map
        Map<String, Object> keysMap = msgKeysToMap(msgQueue.getMsgKeys());
        // 获取企业id
        String compid = keysMap.get("COMPID").toString();
        // 获取消息类型和标签
        String msgTag = msgQueue.getMsgTag();
        String msgType = msgQueue.getMsgType();

        // TODO: 获取推送的用户

        // TODO: 进行消息推送
    }



    // 将msgKeys转换为map
    private Map<String, Object> msgKeysToMap(String msgKeys) {
        // 分割成数组
        String[] keys = msgKeys.split("\\|");
        Map<String, Object> map = new HashMap<>();
        for (String item : keys) {
            String[] mapKeyVals = item.split("=");

            if (mapKeyVals.length != 2) {
                log.warn("msgKeysToMap warn:{}", "msg_key格式异常!");
                // 分割获得的数组长度不为2，异常数据跳过
                break;
            }
            String key = mapKeyVals[0].toUpperCase(); // 转换大写，防止大小写原因无法获取成员
            String value = mapKeyVals[1];
            map.put(key, value);
        }
        return map;
    }


}
