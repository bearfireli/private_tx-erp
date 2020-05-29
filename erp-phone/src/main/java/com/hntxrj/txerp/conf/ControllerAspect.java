package com.hntxrj.txerp.conf;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hntxrj.txerp.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;

import okhttp3.*;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.TimeUnit;


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

    @Resource
    private RedisTemplate<String, String> redisTemplate;

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

    @Around("execution(* com.hntxrj.txerp.api.*.*(..))||" +
            "execution(* com.hntxrj.txerp.controller.stock.StockController.getRealStock(..))")
    private Object mappingAround(ProceedingJoinPoint joinPoint) throws Throwable {
        String functionName;  //拦截到的方法对应的中文名称

        String compid;  //公司代号
        Map<String, Object> map = new HashMap<>();


        //拦截到的方法的方法名
        String methodName = joinPoint.getSignature().getName();

        //判断拦截的方法是否是需要拦截的方法
        Set<String> keySet = functionMap.keySet();
        for (String key : keySet) {
            if (key.equals(methodName)) {
                functionName = functionMap.get(key);

                //   获取拦截方法的参数值   例：['01','P1910254685']
                Object[] args = joinPoint.getArgs();

                // 获取拦截方法的参数名 例：[compid,taskId]
                String[] argNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames();
                //得到拦截方法的参数compid
                if (args != null && args.length > 0) {// 无参数
                    for (int i = 0; i < args.length; i++) {
                        map.put(argNames[i], args[i]);
                    }
                    compid = String.valueOf(map.get("compid"));
                    insertMethodName(methodName, functionName, compid);
                    break;
                }
            }
        }
        return joinPoint.proceed();
    }


    /**
     * 拦截器，判断用户是否超出到期时间，如果超出到期时间，禁止访问
     */
    @Around("execution(* com.hntxrj.txerp.api.*.*(..))")
    private Object getExpireTime(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletResponse response = ((ServletRequestAttributes)
                Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getResponse();

        String compid;  //公司代号
        Map<String, Object> map = new HashMap<>();

        //   获取拦截方法的参数值   例：['01','P1910254685']
        Object[] args = joinPoint.getArgs();

        // 获取拦截方法的参数名 例：[compid,taskId]
        String[] argNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames();
        //得到拦截方法的参数compid
        if (args != null && args.length > 0) {// 无参数
            for (int i = 0; i < args.length; i++) {
                map.put(argNames[i], args[i]);
            }
        }
        if (map.get("compid") == null||"0".equals(map.get("compid"))) {
            //说明请求的是公用接口，放行
            return joinPoint.proceed();
        }

        compid = String.valueOf(map.get("compid"));
        //向erp-base项目发送请求，获取当前企业的到期时间
        String key = "expireTime" + compid;
        String value = redisTemplate.opsForValue().get(key);
        long expireTime;
        if (value == null || "0".equals(value)) {
            expireTime = getExpireTime(compid);
            redisTemplate.opsForValue().set(key, String.valueOf(expireTime), 10, TimeUnit.MINUTES);
        } else {
            expireTime = Long.parseLong(value);
        }
        //获取当前时间
        long nowTime = new Date().getTime();

        if ((expireTime - nowTime) < 0) {
            ResultVO resultVO = new ResultVO();
            resultVO.setCode(100501);
            resultVO.setMsg("您的使用权限已到期");
            assert response != null;
            response.addHeader("Content-type", "application/json; charset=utf-8");
            response.setHeader("Access-Control-Allow-Origin", "*");
            PrintWriter out = response.getWriter();
            out.append(JSON.toJSONString(resultVO));
            out.flush();
            out.close();
            return null;
        }
        return joinPoint.proceed();

    }


    //向tx-erp项目发送请求，把methodName，functionName，compid传递过去，存入数据库统计表中。
    private void insertMethodName(String methodName, String functionName, String compid) {
        String baseUrl;
        baseUrl = url + "/statistic/functionClick";
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("methodName", methodName)
                .add("functionName", functionName)
                .add("appCode", "1")
                .add("compid", compid)
                .build();
        Request request = new Request.Builder()
                .url(baseUrl)
                .post(body)
                .build();

        try {
            client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //向erp-base项目发送请求，获取当前企业的到期时间
    private long getExpireTime(String enterprise) {
        String baseUrl;
        baseUrl = url + "/v1/project/getExpireTime";
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("pid", "2")
                .add("token", "getExpireTime")
                .build();
        Request request = new Request.Builder()
                .url(baseUrl)
                .post(body)
                .addHeader("enterprise", enterprise)
                .build();

        try {
            Response response = client.newCall(request).execute();
            ResponseBody responseBody = response.body();
            if (responseBody != null) {
                String result = responseBody.string();
                JSONObject resultJSON = JSONObject.parseObject(result);
                JSONObject data = (JSONObject) resultJSON.get("data");
                if (data == null) {
                    return 0;
                }
                responseBody.close();
                return (long) data.get("expireTime");
            }
            response.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
