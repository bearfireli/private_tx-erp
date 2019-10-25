package com.hntxrj.conf;

import com.squareup.okhttp.*;
import lombok.extern.slf4j.Slf4j;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


/**
 * controller aop
 *
 * @author haoranliu
 */
@Component
@Aspect
@Slf4j
public class ControllerAspect {


    @Value("${app.cloud.host}")
    private String url;

    private static Map<String, String> functionMap = new HashMap<>();

    //静态代码块对functionMap进行赋值，随着类的加载只加载一次
    static {
        functionMap.put("getContractList", "简易合同");
        functionMap.put("getStgIdManage", "砼标号");
        functionMap.put("getConcreteCount", "产销统计");
        functionMap.put("getTaskPlanList", "任务单");
        functionMap.put("getProductionStatistics", "生产计划");
        functionMap.put("getSendCarList", "调度派车");
        functionMap.put("getDriverShiftLED", "派车LED");
        functionMap.put("getDriverShiftList", "司机排班");
        functionMap.put("getTaskSaleInvoiceList", "小票签收");
        functionMap.put("getDriverTransportationCarNumList", "司机工作量");
        functionMap.put("getPumpTruckSum", "泵车工作量");
        functionMap.put("getVehicleWorkloadSummaryCount", "搅拌车工作量");
        functionMap.put("getWorkloadStatistics", "搅拌车过磅");
        functionMap.put("getMatTotal", "生产消耗");
        functionMap.put("getFormulaList", "生产配比");
        functionMap.put("getWeighBySupName", "原材料过磅统计");
        functionMap.put("getMatStatistics", "原材料汇总");
        functionMap.put("getStockInSelectClose", "原材料过磅查询");
        functionMap.put("getRealStock", "实时库存");
    }

    @Autowired
    public ControllerAspect() {

    }

    @Around("execution(* com.hntxrj.api.*.*(..))||execution(* com.hntxrj.controller.stock.StockController.getRealStock(..))")
    private Object mappingAround(ProceedingJoinPoint joinPoint) throws Throwable {

        Class executionClass;// 访问的类
        Method executionMethod; // 访问的方法
        String functionName = "";  //拦截到的方法对应的中文名称

        String compid = "0";
        Map<String, Object> map = new HashMap<>();

        executionClass = joinPoint.getTarget().getClass();
        //拦截到的方法的方法名
        String methodName = joinPoint.getSignature().getName();

        //判断拦截的方法是否是需要拦截的方法
        Set<String> keySet = functionMap.keySet();
        for (String key : keySet) {
            if (methodName == key) {
                functionName = functionMap.get(key);
                Object[] args = joinPoint.getArgs();//参数值
                String[] argNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames(); // 参数名
                //得到拦截方法的参数compid
                if (args != null && args.length >= 0) {// 无参数
                    for (int i = 0; i < args.length; i++) {
                        map.put(argNames[i], args[i]);
                    }
                    compid = map.get("compid").toString();
                    sendRequest(methodName, functionName, compid);
                    break;
                }
            }
        }


        return joinPoint.proceed();
    }



    //向tx-erp项目发送请求，把methodName，functionName，compid传递过去，存入数据库统计表中。
    private void sendRequest(String methodName, String functionName, String compid) throws IOException {

        String baseurl = "";
        baseurl = url + "/statistic/functionClick";
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormEncodingBuilder()
                .add("methodName", methodName)
                .add("functionName", functionName)
                .add("appCode", "1")
                .add("compid", compid)
                .build();
        Request request = new Request.Builder()
                .url(baseurl)
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
    }

}
