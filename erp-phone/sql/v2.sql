-- ================================================
-- Template generated from Template Explorer using:
-- Create Procedure (New Menu).SQL
--
-- Use the Specify Values for Template Parameters
-- command (Ctrl-Shift-M) to fill in the parameter
-- values below.
--
-- This block of comments will not be included in
-- the definition of the procedure.
-- ================================================
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE PROCEDURE p_disable_SM_ContractDetail
    -- 作废合同
    @compid varchar(11),
    @contract_uid varchar(50), -- 合同uid
    @ccontract_code varchar(50), -- 子合同代号
    @openid varchar(11) -- 操作员代号
AS
BEGIN

    update SM_ContractDetail
    set CContractCode=3
    where ContractUID = @contract_uid
      and CContractCode = @ccontract_code
      and compid = @compid

END
GO
