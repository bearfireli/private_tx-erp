package com.hntxrj.vo;

import com.hntxrj.entity.PageBean;
import lombok.Data;

/**
 * 公共json视图
 * Created by 刘浩然 on 2017/8/2.
 */
@Data
public class JsonVo {

    /**
     * 操作返回码，通用0通常为成功！
     */
    private Integer code;

    /**
     * 返回的消息，如：操作成功！，服务器异常等
     */
    private String msg;

    /**
     * 泛型数据对象
     */
    private Object data;

    public JsonVo() {
        this.code = 0;
        this.msg = "ok";
        this.data = null;
    }
}
