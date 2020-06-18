package com.hntxrj.txerp.entity

import javax.persistence.*

@Entity
@Table(name = "SM_ContractDetail", schema = "dbo", catalog = "SPTERPNewData")
@IdClass(SMContractDetailPK::class)
open class SMContractDetail {
    @get:Id
    @get:Column(name = "ContractUID", nullable = false)
    var contractUID: String? = null

    @get:Id
    @get:Column(name = "CContractCode", nullable = false)
    var CContractCode: String? = null

    @get:Basic
    @get:Column(name = "compid", nullable = true)
    var compid: String? = null

    @get:Basic
    @get:Column(name = "EPPCode", nullable = true)
    var EPPCode: String? = null

    @get:Basic
    @get:Column(name = "BuilderCode", nullable = true)
    var builderCode: String? = null

    @get:Basic
    @get:Column(name = "Address", nullable = true)
    var address: String? = null

    @get:Basic
    @get:Column(name = "MinJumpNum", nullable = true)
    var minJumpNum: java.math.BigDecimal? = null

    @get:Basic
    @get:Column(name = "JumpPriceMarkup", nullable = true)
    var jumpPriceMarkup: java.math.BigDecimal? = null

    @get:Basic
    @get:Column(name = "PreMoney", nullable = true)
    var preMoney: java.math.BigDecimal? = null

    @get:Basic
    @get:Column(name = "PreNum", nullable = true)
    var preNum: java.math.BigDecimal? = null

    @get:Basic
    @get:Column(name = "SuretyMoney", nullable = true)
    var suretyMoney: java.math.BigDecimal? = null

    @get:Basic
    @get:Column(name = "ContractNum", nullable = true)
    var contractNum: java.math.BigDecimal? = null

    @get:Basic
    @get:Column(name = "AccountNum", nullable = true)
    var accountNum: java.math.BigDecimal? = null

    @get:Basic
    @get:Column(name = "ProduceMoney", nullable = true)
    var produceMoney: java.math.BigDecimal? = null

    @get:Basic
    @get:Column(name = "AccountPlace", nullable = true)
    var accountPlace: String? = null

    @get:Basic
    @get:Column(name = "IsTax", nullable = true)
    var isTax: Boolean? = null

    @get:Basic
    @get:Column(name = "TaxRate", nullable = true)
    var taxRate: java.math.BigDecimal? = null

    @get:Basic
    @get:Column(name = "Distance", nullable = false)
    var distance: java.math.BigDecimal? = null

    @get:Basic
    @get:Column(name = "MatStatus", nullable = true)
    var matStatus: Int? = null

    @get:Basic
    @get:Column(name = "ContractStatus", nullable = true)
    var contractStatus: Int? = null

    @get:Basic
    @get:Column(name = "StatusTime", nullable = true)
    var statusTime: java.sql.Timestamp? = null

    @get:Basic
    @get:Column(name = "VerifyStatus", nullable = true)
    var verifyStatus: Boolean? = null

    @get:Basic
    @get:Column(name = "VerifyId", nullable = true)
    var verifyId: String? = null

    @get:Basic
    @get:Column(name = "VerifyTime", nullable = true)
    var verifyTime: java.sql.Timestamp? = null

    @get:Basic
    @get:Column(name = "OpId", nullable = false)
    var opId: String? = null

    @get:Basic
    @get:Column(name = "CreateTime", nullable = true)
    var createTime: java.sql.Timestamp? = null

    @get:Basic
    @get:Column(name = "IsGPSLocate", nullable = true)
    var isGPSLocate: Boolean? = null

    @get:Basic
    @get:Column(name = "Remarks", nullable = true)
    var remarks: String? = null

    @get:Basic
    @get:Column(name = "UpDown", nullable = true)
    var upDown: Boolean? = null

    @get:Basic
    @get:Column(name = "RecStatus", nullable = true)
    var recStatus: Boolean? = null

    @get:Basic
    @get:Column(name = "MortarModule", nullable = true)
    var mortarModule: Int? = null

    @get:Basic
    @get:Column(name = "MortarStgid", nullable = true)
    var mortarStgid: String? = null

    @get:Basic
    @get:Column(name = "WaterModule", nullable = true)
    var waterModule: Int? = null

    @get:Basic
    @get:Column(name = "WaterStgid", nullable = true)
    var waterStgid: String? = null

    @get:Basic
    @get:Column(name = "WaterAccountNum", nullable = true)
    var waterAccountNum: java.math.BigDecimal? = null

    @get:Basic
    @get:Column(name = "NoloadModule", nullable = true)
    var noloadModule: Int? = null

    @get:Basic
    @get:Column(name = "NoloadOderMinNum", nullable = true)
    var noloadOderMinNum: java.math.BigDecimal? = null

    @get:Basic
    @get:Column(name = "NoloadCarMinNum", nullable = true)
    var noloadCarMinNum: java.math.BigDecimal? = null

    @get:Basic
    @get:Column(name = "NoloadMoney", nullable = true)
    var noloadMoney: java.math.BigDecimal? = null

    @get:Basic
    @get:Column(name = "TableExpenseModule", nullable = true)
    var tableExpenseModule: Int? = null

    @get:Basic
    @get:Column(name = "UpDownMark", nullable = false)
    var upDownMark: Int? = null

    @get:Basic
    @get:Column(name = "NoloadMoneyStyle", nullable = true)
    var noloadMoneyStyle: Int? = null

    @get:Basic
    @get:Column(name = "NoloadAllowsNumber", nullable = true)
    var noloadAllowsNumber: Int? = null

    @get:Basic
    @get:Column(name = "NoloadMoneyNumber", nullable = true)
    var noloadMoneyNumber: java.math.BigDecimal? = null

    @get:Basic
    @get:Column(name = "SecondVerifyStatus", nullable = true)
    var secondVerifyStatus: Boolean? = null

    @get:Basic
    @get:Column(name = "SecondVerifyName", nullable = true)
    var secondVerifyName: String? = null

    @get:Basic
    @get:Column(name = "SecondVerifyTime", nullable = true)
    var secondVerifyTime: java.sql.Timestamp? = null

    @get:Basic
    @get:Column(name = "OpenTime", nullable = true)
    var openTime: java.sql.Timestamp? = null


    override fun toString(): String =
            "Entity of type: ${javaClass.name} ( " +
                    "contractUID = $contractUID " +
                    "CContractCode = $CContractCode " +
                    "compid = $compid " +
                    "EPPCode = $EPPCode " +
                    "builderCode = $builderCode " +
                    "address = $address " +
                    "minJumpNum = $minJumpNum " +
                    "jumpPriceMarkup = $jumpPriceMarkup " +
                    "preMoney = $preMoney " +
                    "preNum = $preNum " +
                    "suretyMoney = $suretyMoney " +
                    "contractNum = $contractNum " +
                    "accountNum = $accountNum " +
                    "produceMoney = $produceMoney " +
                    "accountPlace = $accountPlace " +
                    "isTax = $isTax " +
                    "taxRate = $taxRate " +
                    "distance = $distance " +
                    "matStatus = $matStatus " +
                    "contractStatus = $contractStatus " +
                    "statusTime = $statusTime " +
                    "verifyStatus = $verifyStatus " +
                    "verifyId = $verifyId " +
                    "verifyTime = $verifyTime " +
                    "opId = $opId " +
                    "createTime = $createTime " +
                    "isGPSLocate = $isGPSLocate " +
                    "remarks = $remarks " +
                    "upDown = $upDown " +
                    "recStatus = $recStatus " +
                    "mortarModule = $mortarModule " +
                    "mortarStgid = $mortarStgid " +
                    "waterModule = $waterModule " +
                    "waterStgid = $waterStgid " +
                    "waterAccountNum = $waterAccountNum " +
                    "noloadModule = $noloadModule " +
                    "noloadOderMinNum = $noloadOderMinNum " +
                    "noloadCarMinNum = $noloadCarMinNum " +
                    "noloadMoney = $noloadMoney " +
                    "tableExpenseModule = $tableExpenseModule " +
                    "upDownMark = $upDownMark " +
                    "noloadMoneyStyle = $noloadMoneyStyle " +
                    "noloadAllowsNumber = $noloadAllowsNumber " +
                    "noloadMoneyNumber = $noloadMoneyNumber " +
                    "secondVerifyStatus = $secondVerifyStatus " +
                    "secondVerifyName = $secondVerifyName " +
                    "secondVerifyTime = $secondVerifyTime " +
                    "openTime = $openTime " +
                    ")"

    // constant value returned to avoid entity inequality to itself before and after it's update/merge
    override fun hashCode(): Int = 42

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as SMContractDetail

        if (contractUID != other.contractUID) return false
        if (CContractCode != other.CContractCode) return false
        if (compid != other.compid) return false
        if (EPPCode != other.EPPCode) return false
        if (builderCode != other.builderCode) return false
        if (address != other.address) return false
        if (minJumpNum != other.minJumpNum) return false
        if (jumpPriceMarkup != other.jumpPriceMarkup) return false
        if (preMoney != other.preMoney) return false
        if (preNum != other.preNum) return false
        if (suretyMoney != other.suretyMoney) return false
        if (contractNum != other.contractNum) return false
        if (accountNum != other.accountNum) return false
        if (produceMoney != other.produceMoney) return false
        if (accountPlace != other.accountPlace) return false
        if (isTax != other.isTax) return false
        if (taxRate != other.taxRate) return false
        if (distance != other.distance) return false
        if (matStatus != other.matStatus) return false
        if (contractStatus != other.contractStatus) return false
        if (statusTime != other.statusTime) return false
        if (verifyStatus != other.verifyStatus) return false
        if (verifyId != other.verifyId) return false
        if (verifyTime != other.verifyTime) return false
        if (opId != other.opId) return false
        if (createTime != other.createTime) return false
        if (isGPSLocate != other.isGPSLocate) return false
        if (remarks != other.remarks) return false
        if (upDown != other.upDown) return false
        if (recStatus != other.recStatus) return false
        if (mortarModule != other.mortarModule) return false
        if (mortarStgid != other.mortarStgid) return false
        if (waterModule != other.waterModule) return false
        if (waterStgid != other.waterStgid) return false
        if (waterAccountNum != other.waterAccountNum) return false
        if (noloadModule != other.noloadModule) return false
        if (noloadOderMinNum != other.noloadOderMinNum) return false
        if (noloadCarMinNum != other.noloadCarMinNum) return false
        if (noloadMoney != other.noloadMoney) return false
        if (tableExpenseModule != other.tableExpenseModule) return false
        if (upDownMark != other.upDownMark) return false
        if (noloadMoneyStyle != other.noloadMoneyStyle) return false
        if (noloadAllowsNumber != other.noloadAllowsNumber) return false
        if (noloadMoneyNumber != other.noloadMoneyNumber) return false
        if (secondVerifyStatus != other.secondVerifyStatus) return false
        if (secondVerifyName != other.secondVerifyName) return false
        if (secondVerifyTime != other.secondVerifyTime) return false
        if (openTime != other.openTime) return false

        return true
    }

}

class SMContractDetailPK : java.io.Serializable {
    @get:Id
    @get:Column(name = "ContractUID", nullable = false)
    var contractUID: String? = null

    @get:Id
    @get:Column(name = "CContractCode", nullable = false)
    var CContractCode: String? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as SMContractDetailPK

        if (contractUID != other.contractUID) return false
        if (CContractCode != other.CContractCode) return false

        return true
    }

    // constant value returned to avoid entity inequality to itself before and after it's update/merge
    override fun hashCode(): Int = 42

}
