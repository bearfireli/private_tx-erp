package com.hntxrj.txerp.controller;

import com.hntxrj.txerp.core.util.SimpleDateFormatUtil;
import com.hntxrj.txerp.core.web.ResultVO;
import com.hntxrj.txerp.entity.base.UserStatistic;
import com.hntxrj.txerp.service.UserStatisticsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 用户每日统计量、每日用户登录量、每个功能点击量的controller层
 */
@RestController
@RequestMapping(value = "/statistic")
@Slf4j
public class UserStatisticsController {


    private final UserStatisticsService userStatisticsService;
    private ResultVO resultVO;


    @Autowired
    public UserStatisticsController(UserStatisticsService userStatisticsService) {
        resultVO = null;
        resultVO = new ResultVO();
        resultVO.setCode(0);
        resultVO.setMsg("ok");
        this.userStatisticsService = userStatisticsService;
    }


    //用户每日统计
    @RequestMapping("/userCount")
    public ResultVO userCount(
            @RequestParam(required = false) Date date
    ) {
        if (date == null || "1970-01-01".equals(timeFormat(date))) {
            date = new Date();
        }
        List<String> dateList = preSenvenDay(date);

        return resultVO.create(userStatisticsService.userCount(dateList));
    }

    //每日用户登录量统计
    @RequestMapping("/loginCount")
    public ResultVO loginCount(
            @RequestParam(required = false) Date date,
            @RequestParam(required = false) Integer compid,
            @RequestParam(required = false, defaultValue = "1") Integer appCode
    ) {
        System.out.println(date);
        if (date == null || "1970-01-01".equals(timeFormat(date))) {
            date = new Date();
        }
        List<String> dateList = preSenvenDay(date);
        return resultVO.create(userStatisticsService.loginCount(compid, dateList, appCode));

    }

    //每个功能的点击量统计(参数：功能名称)
    @RequestMapping("/functionCount")
    public ResultVO functionCount(
            @RequestParam(required = false) Date date,
            @RequestParam(required = false) String methodName,
            @RequestParam(required = false) Integer compid
    ) {
        if (date == null || "1970-01-01".equals(timeFormat(date))) {
            date = new Date();
        }
        List<String> dateList = preSenvenDay(date);
        Integer appCode = 1;

        //查询每个功能点击量（以日期、方法名称，appCode,公司名称为参数）
        List<UserStatistic> functionCount = userStatisticsService.functionCount(dateList, methodName, appCode, compid);
        return ResultVO.create(functionCount);
    }

    //查询每个功能点击排行。
    @RequestMapping("/functionRank")
    public ResultVO functionRank(
            @RequestParam(required = false) Integer compid,
            @RequestParam(required = false) Date date,
            @RequestParam(required = false, defaultValue = "1") Integer dateType,
            @RequestParam(required = false, defaultValue = "1") Integer appCode

    ) {
        if (date == null) {
            date = new Date();
        }

        String time = timeFormat(date);
        if (dateType == 2) {
            time = time.substring(0, 7);
        } else if (dateType == 3) {
            time = time.substring(0, 4);
        }
        List<UserStatistic> userStatisticList = userStatisticsService.functionRank(compid, time, appCode);
        return resultVO.create(userStatisticList);
    }


    //用户每次登录app调用的接口
    @RequestMapping("/userLogin")
    public ResultVO userLogin(Integer compid, Integer uid, Integer appCode) {
        Date date = new Date();
        String time = timeFormat(date);
        userStatisticsService.userLogin(compid, uid, appCode, time);
        return resultVO.create();
    }

    //用户每次点击某个功能时调用的接口,参数（功能名称）
    @RequestMapping("/functionClick")
    public ResultVO functionClick(String methodName, String functionName, Integer appCode, Integer compid) {
        Date date = new Date();
        String time = timeFormat(date);
        userStatisticsService.functionClick(methodName, functionName, time, appCode, compid);
        return resultVO.create();
    }

    @RequestMapping("/getEnterprise")
    public ResultVO getEnterprise() {
        return resultVO.create(userStatisticsService.getEnterprise());
    }

    private String timeFormat(Date date) {
        SimpleDateFormat format = SimpleDateFormatUtil.getSimpleDataFormat("yyyy-MM-dd");
        String timeStr = format.format(date);
        return timeStr;
    }

    //获取从当天之前七天的日期
    public List<String> preSenvenDay(Date date) {
        List<String> dateList = new ArrayList<>();
        String date1 = timeFormat(date);
        dateList.add(date1);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        for (int i = 0; i < 6; i++) {
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            dateList.add(timeFormat(calendar.getTime()));
        }
        return dateList;


    }

}