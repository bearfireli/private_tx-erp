### 配置

- 配置前端的baseUrl地址 resources => static => config => api.js
  ```javascript
  window.baseUrl = "http://192.168.0.107:8080/spterp/"; 
  ```
### 特别提醒

- 如果部署程序后出现权限组编辑后未生效，清空`User_PermissionGroupDetails`表中数据即可


### 支付配置
AuthController.java    ==> 修改 pay 方法的 <http://erp.hntxrj.com>
AliPayApiConfig.java  修改支付宝信息。


### 存储过程修改
存储过程修改后在sql下加入修改sql脚本。

### 存储过程对照

查询合同 sp_Query_SimpleContract
