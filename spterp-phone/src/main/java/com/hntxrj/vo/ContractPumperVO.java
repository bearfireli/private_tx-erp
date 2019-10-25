package com.hntxrj.vo;

import lombok.Data;
/**
 *   pumpTypeName;泵车类别中文
 *   pumptype;泵车代号
 *   pumpPrice;泵送单价
 *   tableExpense;台班费
 *   合同泵车VO类
 *
 */
@Data
public class ContractPumperVO {
    private String pumpTypeName;//泵车类别中文
    private Integer pumptype;//泵车代号
    private Double pumpPrice;//泵送单价
    private Double tableExpense;//台班费
}
