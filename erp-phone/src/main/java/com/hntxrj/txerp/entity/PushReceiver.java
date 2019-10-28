package com.hntxrj.txerp.entity;

import java.util.Date;
import java.io.Serializable;
import lombok.Data;
import javax.persistence.*;
import java.math.BigDecimal;

//@Entity
@Data
public class PushReceiver implements Serializable{

    /* 推送代号 */
    private String pushCode;
    /* 接收者 */
    private String pushTo;
    /* 接收企业 */
    private String pushCompid;

}
