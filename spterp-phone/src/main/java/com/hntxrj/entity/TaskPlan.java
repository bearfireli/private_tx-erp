package com.hntxrj.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "pt_taskplan")
public class TaskPlan implements Serializable {

    /* 站别代号 */
    @Column(name = "compid")
    private String compid;
    /* 任务单号 */
    @Id
    @Column(name = "taskid")
    private String taskId;
    /* 创建时间 */
    @Column(name = "createtime")
    private String createTime;
    /* 操作员代号 */
    @Column(name = "opid")
    private String opId;
    /* 预计时间 */
    @Column(name = "pretime")
    private String preTime;
    /* 任务结束时间 */
    @Column(name = "taskovertime")
    private String taskOverTime;
    /* 预计备注 */
    @Column(name = "preremark")
    private String preRemark;
    /* 预计方量 */
    @Column(name = "prenum")
    private BigDecimal preNum;
    /* 合同UID号 */
    @Column(name = "contractuid")
    private String contractUid;
    /* 子合同号 */
    @Column(name = "ccontractcode")
    private String cContractCode;
    /* 任务状态 */
    @Column(name = "taskstatus")
    private Integer taskStatus;
    /* 审核标识 */
    @Column(name = "verifystatus")
    private boolean verifyStatus;
    /* 审核员代号 */
    @Column(name = "verifyid")
    private String verifyId;
    /* 审核时间 */
    @Column(name = "verifytime")
    private String verifyTime;
    /* 工程代号 */
    @Column(name = "eppcode")
    private String eppCode;
    /* 施工单位代号 */
    @Column(name = "buildercode")
    private String builderCode;
    /* 配比编号 */
    @Column(name = "formulacode")
    private String formulaCode;
    /* 理论配比编号 */
    @Column(name = "theoryformulacode")
    private String theoryFormulaCode;
    /* 塌落度 */
    @Column(name = "slump")
    private String slump;
    /* 配比标志 */
    @Column(name = "formulastatus")
    private String formulaStatus;
    /* 配比更新时间 */
    @Column(name = "formulatime")
    private String formulaTime;
    /* 标号(强度) */
    @Column(name = "stgid")
    private String stgId;
    /* 抗折等级 */
    @Column(name = "grade")
    private String grade;
    /* 特性 */
    @Column(name = "attribute")
    private String attribute;
    /* 石料要求 */
    @Column(name = "stoneask")
    private String stoneAsk;
    /* 石子粒径 */
    @Column(name = "stonedia")
    private String stoneDia;
    /* 水泥品种 */
    @Column(name = "cementvariety")
    private String cementVariety;
    /* 其他技术要求 */
    @Column(name = "technicalrequirements")
    private String technicalRequirements;
    /* 区间(距离) */
    @Column(name = "distance")
    private BigDecimal distance;
    /* 总发车次数 */
    @Column(name = "totalvehiclenum")
    private Integer totalVehicleNum;
    /* 当日发车次数 */
    @Column(name = "todayvehiclenum")
    private Integer todayVehicleNum;
    /* 总运输方量 */
    @Column(name = "totalnum")
    private BigDecimal totalNum;
    /* 当日运输方量 */
    @Column(name = "todaynum")
    private BigDecimal todayNum;
    /* 开盘时间 */
    @Column(name = "opentime")
    private String openTime;
    /* 交货地址 */
    @Column(name = "address")
    private String address;
    /* 完成时间 */
    @Column(name = "overtime")
    private String overTime;
    /* 浇筑部位 */
    @Column(name = "placing")
    private String placing;
    /* 浇筑方式 */
    @Column(name = "placestyle")
    private String placeStyle;
    /* 现场联系人 */
    @Column(name = "linkman")
    private String linkMan;
    /* 现场联系电话 */
    @Column(name = "linktel")
    private String linkTel;
    /* 是否泵送 */
    @Column(name = "isjump")
    private String isJump;
    /* 默认泵车 */
    @Column(name = "defaultjump")
    private String defaultJump;
    /* 含税否 */
    @Column(name = "istax")
    private String isTax;
    /* 接管状态 */
    @Column(name = "linkpipestatus")
    private Integer linkPipeStatus;
    /* 接管人 */
    @Column(name = "linkpipename")
    private String linkPipeName;
    /* 接管时间 */
    @Column(name = "linkpipetime")
    private String linkPipeTime;
    /* 下载时间 */
    @Column(name = "downtime")
    private String downTime;
    /* 下载操作员代号 */
    @Column(name = "downid")
    private String downId;
    /* 下载标志 */
    @Column(name = "updown")
    private String upDown;
    /* 删除标志 */
    @Column(name = "deletemark")
    private String deleteMark;
    /* 网络下发标识 */
    @Column(name = "updownmark")
    private Integer upDownMark;
    /* 司机结算车数 */
    @Column(name = "drnum")
    private BigDecimal drNum;
    /* 提醒标识(0初始值 实验室接收提 */
    @Column(name = "warn")
    private Integer warn;
    /* null */
    @Column(name = "reportcode")
    private String reportCode;
    /* 预计车数 */
    @Column(name = "precarnum")
    private Integer preCarNum;
    /* null */
    @Column(name = "concretemark")
    private String concreteMark;
    /* null */
    @Column(name = "isexcess")
    private String isExcess;
    /* null */
    @Column(name = "jumpmark")
    private Integer jumpMark;
    /* null */
    @Column(name = "tasktype")
    private Integer taskType;
    /* null */
    @Column(name = "recstatus")
    private char recStatus;
    /* null */
    @Column(name = "producemark")
    private Integer produceMark;
    /* 派车时间间隔(分钟) */
    @Column(name = "sendvehicleinterval")
    private Integer sendVehicleInterval;
    /* 调整后时间 */
    @Column(name = "adjustmenttime")
    private String adjustmentTime;
    /* 接管完成时间 */
    @Column(name = "linkpipeovertime")
    private String linkPipeOverTime;
    /* 台班费计费否 */
    @Column(name = "tableexpensemodule")
    private Integer tableExpenseModule;
    /* 台班费人工收金额 */
    @Column(name = "tableexpensemoney")
    private BigDecimal tableExpenseMoney;
    /* 空载费计费否 */
    @Column(name = "noloadmodule")
    private Integer noloadModule;
    /* 空载费人工收金额 */
    @Column(name = "noloadmoney")
    private BigDecimal noloadMoney;
    /* 延期原因类型 */
    @Column(name = "reasontype")
    private Integer reasonType;
    /* null */
    @Column(name = "inspectcode")
    private String inSpectCode;
    /* null */
    @Column(name = "serialcode")
    private String serialCode;
    /* 任务结算价格ID号 */
    @Column(name = "accountid")
    private Integer accountId;
    /* 结算审核完成标志 */
    @Column(name = "accountverifystatus")
    private Integer accountVerifyStatus;

}
