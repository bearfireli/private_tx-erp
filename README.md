# tx-erp


### 目录结构
 - erp-core ---- 核心代码，工具类，异常处理等
 - erp-driver ---- 司机业务
 - erp-base ---- 用户，权限，企业等核心业务
 - erp-phone ---- 手机erp业务

### 引入淘宝sdk
```bash
cd libs
mvn install:install-file -Dfile=taobao-sdk-java-auto_1479188381469-20181204.jar -DgroupId=com.taobao -DartifactId=sdk -Dversion=1.0.0 -Dpackaging=jar
```