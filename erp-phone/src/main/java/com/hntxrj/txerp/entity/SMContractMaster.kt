package com.hntxrj.txerp.entity

import javax.persistence.*

@Entity
@Table(name = "sm_contractmaster", schema = "dbo")
@IdClass(SMContractMasterPK::class)
open class SMContractMaster {
    @Id
    @Column(name = "compid", nullable = false)
    var compid: String? = null

    @Id
    @Column(name = "contractid", nullable = false)
    var contractId: String? = null

    @Id
    @Column(name = "contractuid", nullable = false)
    var contractUID: String? = null

    @Basic
    @Column(name = "salesman", nullable = true)
    var salesman: String? = null

    @Basic
    @Column(name = "signdate", nullable = true)
    var signDate: java.sql.Timestamp? = null

    @Basic
    @Column(name = "effectdate", nullable = true)
    var effectDate: java.sql.Timestamp? = null

    @Basic
    @Column(name = "expiresdate", nullable = true)
    var expiresDate: java.sql.Timestamp? = null

    @Basic
    @Column(name = "contracttype", nullable = true)
    var contractType: Int? = null

    @Basic
    @Column(name = "accountday", nullable = true)
    var accountDay: Int? = null

    @Basic
    @Column(name = "lockdate", nullable = true)
    var lockDate: Int? = null

    @Basic
    @Column(name = "lockrate", nullable = true)
    var lockRate: java.math.BigDecimal? = null

    @Basic
    @Column(name = "linkman", nullable = true)
    var linkMan: String? = null

    @Basic
    @Column(name = "linktel", nullable = true)
    var linkTel: String? = null

    @Basic
    @Column(name = "contractstatus", nullable = true)
    var contractStatus: Int? = null

    @Basic
    @Column(name = "pricestyle", nullable = true)
    var priceStyle: Int? = null

    @Basic
    @Column(name = "pricefloatlimits", nullable = true)
    var priceFloatLimits: java.math.BigDecimal? = null

    @Basic
    @Column(name = "paymentterms", nullable = true)
    var paymentTerms: Int? = null

    @Basic
    @Column(name = "computestyle", nullable = true)
    var computeStyle: String? = null

    @Basic
    @Column(name = "opid", nullable = false)
    var opId: String? = null

    @Basic
    @Column(name = "createtime", nullable = true, insertable = false, updatable = false)
    var createTime: java.sql.Timestamp? = null

    @Basic
    @Column(name = "updown", nullable = true)
    var upDown: Boolean? = null

    @Basic
    @Column(name = "recstatus", nullable = true)
    var recStatus: Boolean? = null

    @Basic
    @Column(name = "payrate", nullable = false)
    var payRate: java.math.BigDecimal? = null

    @Basic
    @Column(name = "updownmark", nullable = false)
    var upDownMark: Int? = null


    override fun toString(): String =
            "Entity of type: ${javaClass.name} ( " +
                    "compid = $compid " +
                    "contractId = $contractId " +
                    "contractUID = $contractUID " +
                    "salesman = $salesman " +
                    "signDate = $signDate " +
                    "effectDate = $effectDate " +
                    "expiresDate = $expiresDate " +
                    "contractType = $contractType " +
                    "accountDay = $accountDay " +
                    "lockDate = $lockDate " +
                    "lockRate = $lockRate " +
                    "linkMan = $linkMan " +
                    "linkTel = $linkTel " +
                    "contractStatus = $contractStatus " +
                    "priceStyle = $priceStyle " +
                    "priceFloatLimits = $priceFloatLimits " +
                    "paymentTerms = $paymentTerms " +
                    "computeStyle = $computeStyle " +
                    "opId = $opId " +
                    "createTime = $createTime " +
                    "upDown = $upDown " +
                    "recStatus = $recStatus " +
                    "payRate = $payRate " +
                    "upDownMark = $upDownMark " +
                    ")"

    // constant value returned to avoid entity inequality to itself before and after it's update/merge
    override fun hashCode(): Int = 42

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as SMContractMaster

        if (compid != other.compid) return false
        if (contractId != other.contractId) return false
        if (contractUID != other.contractUID) return false
        if (salesman != other.salesman) return false
        if (signDate != other.signDate) return false
        if (effectDate != other.effectDate) return false
        if (expiresDate != other.expiresDate) return false
        if (contractType != other.contractType) return false
        if (accountDay != other.accountDay) return false
        if (lockDate != other.lockDate) return false
        if (lockRate != other.lockRate) return false
        if (linkMan != other.linkMan) return false
        if (linkTel != other.linkTel) return false
        if (contractStatus != other.contractStatus) return false
        if (priceStyle != other.priceStyle) return false
        if (priceFloatLimits != other.priceFloatLimits) return false
        if (paymentTerms != other.paymentTerms) return false
        if (computeStyle != other.computeStyle) return false
        if (opId != other.opId) return false
        if (createTime != other.createTime) return false
        if (upDown != other.upDown) return false
        if (recStatus != other.recStatus) return false
        if (payRate != other.payRate) return false
        if (upDownMark != other.upDownMark) return false

        return true
    }

}

class SMContractMasterPK : java.io.Serializable {
    @Id
    @Column(name = "compid", nullable = false)
    var compid: String? = null

    @Id
    @Column(name = "contractid", nullable = false)
    var contractId: String? = null

    @Id
    @Column(name = "contractuid", nullable = false)
    var contractUID: String? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as SMContractMasterPK

        if (compid != other.compid) return false
        if (contractId != other.contractId) return false
        if (contractUID != other.contractUID) return false

        return true
    }

    // constant value returned to avoid entity inequality to itself before and after it's update/merge
    override fun hashCode(): Int = 42

}
