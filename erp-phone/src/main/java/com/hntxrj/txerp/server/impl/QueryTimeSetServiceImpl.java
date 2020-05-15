package com.hntxrj.txerp.server.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.arronlong.httpclientutil.HttpClientUtil;
import com.arronlong.httpclientutil.common.HttpConfig;
import com.arronlong.httpclientutil.common.HttpHeader;
import com.arronlong.httpclientutil.exception.HttpProcessException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hntxrj.txerp.core.util.SimpleDateFormatUtil;
import com.hntxrj.txerp.mapper.QueryTimeSetMapper;
import com.hntxrj.txerp.server.QueryTimeSetService;
import com.hntxrj.txerp.vo.PageVO;
import com.hntxrj.txerp.vo.QueryTimeSetVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author qyb
 **/
@Service
@Scope("prototype")
@Slf4j
public class QueryTimeSetServiceImpl implements QueryTimeSetService {

    @Value("${app.cloud.host}")
    private String url;

    private final QueryTimeSetMapper queryTimeSetMapper;


    public QueryTimeSetServiceImpl(QueryTimeSetMapper queryTimeSetMapper) {
        this.queryTimeSetMapper = queryTimeSetMapper;
    }

    /**
     * 查询时间设置列表
     *
     * @param compid   站别代号
     * @param page     当前页
     * @param pageSize 每页显示多少条
     * @return 返回json
     */
    @Override
    public PageVO<QueryTimeSetVO> getQueryTimeSetList(String compid, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        //根据compid查询，设置的时间表
        List<QueryTimeSetVO> queryTimeSetVO = queryTimeSetMapper.getQueryTimeSetList(compid);
        for (QueryTimeSetVO time:queryTimeSetVO) {
            SimpleDateFormat sdf = SimpleDateFormatUtil.getSimpleDataFormat("yyyy-MM-dd");
            String beginTime = sdf.format(new Date()) +" 00:00:00";
            String endTime =sdf.format(new Date()) +" 23:59:59";
            Map<String, String> yesterdayTime =publicQueryTime(compid,beginTime,endTime,time.getQueryName(),queryTimeSetVO);
            time.setBeginTime(yesterdayTime.get("beginTime"));
            time.setEndTime(yesterdayTime.get("endTime"));
        }


        //查询menu表中的功能名称
        List<String> menuList = checkTokenIsNormal();
        //当没设置时间表时，把功能名称添加到集合中
        if (queryTimeSetVO.size() == 0) {
            for (String s : menuList) {
                QueryTimeSetVO queryTimeSet = new QueryTimeSetVO();
                queryTimeSet.setQueryName(s);
                queryTimeSetVO.add(queryTimeSet);
            }
        } else {
            //判断是否增加功能名称
            for (String menu : menuList) {
                //设置初始化值
                boolean s = false;
                for (QueryTimeSetVO time : queryTimeSetVO) {
                    if (menu.equals(time.getQueryName())) {
                        s = true;
                        break;
                    }
                }
                if (!s) {
                    //判断有新增功能，添加到queryTimeSet表中
                    QueryTimeSetVO queryTimeSet = new QueryTimeSetVO();
                    queryTimeSet.setQueryName(menu);
                    queryTimeSetVO.add(queryTimeSet);
                }
            }
        }
        PageInfo<QueryTimeSetVO> pageInfo = new PageInfo<>(queryTimeSetVO);
        PageVO<QueryTimeSetVO> pageVO = new PageVO<>();
        pageVO.format(pageInfo);
        return pageVO;
    }

    /**
     * 编辑默认时间
     * @param list 传递的json参数
     */
    @Override
    public void upDateQueryTime( List<QueryTimeSetVO> list){
        if (list.size() > 0) {
            for (QueryTimeSetVO queryTimeSetVO : list) {
               String compid =null;
               String queryName = null;
                SimpleDateFormat sdf = SimpleDateFormatUtil.getSimpleDataFormat("yyyy-MM-dd HH:mm:ss");
               int queryTime =0;
               String queryStartTime ="00:00";
               String queryStopTime ="00:00";
               int recStatus =0;
               int upDownMark =0;
               int upDown =0;
                if (queryTimeSetVO.getCompid()!=null && !"".equals(queryTimeSetVO.getCompid())){
                    compid =queryTimeSetVO.getCompid();
                }
                if (queryTimeSetVO.getQueryName()!=null && !"".equals(queryTimeSetVO.getQueryName())){
                    queryName =queryTimeSetVO.getQueryName();
                }
                if (queryTimeSetVO.getQuerytime()!=0){
                    queryTime =queryTimeSetVO.getQuerytime();
                }
                if (queryTimeSetVO.getQueryStartTime()!=null && !"".equals(queryTimeSetVO.getQueryStopTime())){
                    queryStartTime =queryTimeSetVO.getQueryStartTime();
                    queryStopTime =queryTimeSetVO.getQueryStopTime();
                }
                if (compid!=null && queryName!=null){
                    queryTimeSetMapper.deleteQueryTimeSet(compid,queryName);
                    queryTimeSetMapper.insetQueryTimeSet(compid,queryName,queryTime,queryStartTime,queryStopTime,recStatus,upDownMark,upDown);
                }
            }
        }
    }


    /**
     * 查询功能菜单
     *
     * @return 返回是否通过
     */
    private List<String> checkTokenIsNormal() {
        String baseUrl = "";
        baseUrl = url + "/v1/menu/functionmenulist";
        Header[] headers = HttpHeader.custom()
                .other("version", "1")
                .build();
        //插件式配置请求参数（网址、请求参数、编码、client）
        HttpConfig config = HttpConfig.custom()
                .headers(headers)
                .url(baseUrl)
                .encoding("utf-8")
                .inenc("utf-8");
        //使用方式：
        List<String> list = new ArrayList<>();
        try {
            String result = HttpClientUtil.post(config);
            JSONArray array = JSONObject.parseObject(result).getJSONArray("data");

            for (Object o : array) {
                String menuname = JSONObject.parseObject(JSONObject.toJSONString(o)).getString("menuName");
                System.out.println(menuname);
                list.add(menuname);
            }

        } catch (HttpProcessException e) {
            log.warn("【menu功能菜单】请求功能菜单失败");

            e.printStackTrace();
        }
        return list;
    }

    /**
     * 时间查询公共接口
     * @param compid   企业id
     * @param beginTime  开始时间
     * @param endTime   结束时间
     * @param name     功能名称
     * @return   返回map
     */
    private Map<String, String> publicQueryTime(String compid, String beginTime, String endTime, String name,
                                               List<QueryTimeSetVO> queryTimeSetVO) {
        Map<String, String> map = new HashMap<>();
        SimpleDateFormat sdfTime = SimpleDateFormatUtil.getSimpleDataFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf = SimpleDateFormatUtil.getSimpleDataFormat("yyyy-MM-dd");
        DateFormat fmt = SimpleDateFormatUtil.getSimpleDataFormat("yyyyMMddHHmmss");
        if (compid != null) {
            if (name != null) {
                //获取当前时间
                String dateTime = sdfTime.format(new Date());
                for (QueryTimeSetVO timeList : queryTimeSetVO) {
                    if (timeList.getQueryName().equals(name)) {
                        if (timeList.getQuerytime() == 0) {
                            if (timeList.getQueryStartTime() != null) {
                                beginTime = beginTime.substring(0, 10) + " " + timeList.getQueryStartTime();
                            }
                            if (timeList.getQueryStopTime() != null) {
                                endTime = endTime.substring(0, 10) + " " + timeList.getQueryStopTime();
                            }
                        } else {
                            //获取开始时间
                            beginTime = beginTime.substring(0, 10) + " " + timeList.getQueryStartTime();
                            endTime = endTime.substring(0, 10) + " " + timeList.getQueryStartTime();
                            String regex = "(-? ?:?)";
                            try {
                                Date dates = fmt.parse(dateTime.replaceAll(regex, ""));
                                if (timeList.getQuerytime() > 0) {
                                    //首先根据时间间隔把结束时间调整
                                    Date date = sdf.parse(endTime.substring(0, 10));
                                    Calendar cal = Calendar.getInstance();
                                    cal.setTime(date);
                                    cal.add(Calendar.DATE, timeList.getQuerytime());
                                    endTime = sdf.format(cal.getTime()).substring(0, 10) + " " +
                                            timeList.getQueryStartTime();
                                    Date begin = fmt.parse(beginTime.replaceAll(regex, ""));
                                    //判断开始时间和结束时间是否相同,
                                    //返回1:begin大于end;
                                    //返回0:begin等于end;
                                    //返回-1:begin小于end
                                    if (begin.compareTo(dates) > 0) {
                                        //说明开始时间大于当前时间，需要把开始时间和结束时间减一天。
                                        Date dateBegin = sdf.parse(beginTime);
                                        cal.setTime(dateBegin);
                                        cal.add(Calendar.DATE, -1);
                                        beginTime = sdf.format(cal.getTime()).substring(0, 10) + " " +
                                                timeList.getQueryStartTime();
                                        Date dateEnd = sdf.parse(endTime);
                                        cal.setTime(dateEnd);
                                        cal.add(Calendar.DATE, -1);
                                        endTime = sdf.format(cal.getTime()).substring(0, 10) + " " +
                                                timeList.getQueryStartTime();
                                    }
                                } else if (timeList.getQuerytime() < 0) {
                                    //首先根据时间间隔把开始时间调整
                                    Date date = sdf.parse(beginTime.substring(0, 10));
                                    Calendar cal = Calendar.getInstance();
                                    cal.setTime(date);
                                    cal.add(Calendar.DATE, timeList.getQuerytime());
                                    beginTime = sdf.format(cal.getTime()).substring(0, 10) + " " +
                                            timeList.getQueryStartTime();
                                    Date begin = fmt.parse(endTime.replaceAll(regex, ""));
                                    //判断开始时间和结束时间是否相同,
                                    //返回1:begin大于end;
                                    //返回0:begin等于end;
                                    //返回-1:begin小于end
                                    if (begin.compareTo(dates) > 0) {
                                        //说明开始时间大于当前时间，需要把开始时间和结束时间减一天。
                                        Date dateBegin = sdf.parse(beginTime);
                                        cal.setTime(dateBegin);
                                        cal.add(Calendar.DATE, -1);
                                        beginTime = sdf.format(cal.getTime()).substring(0, 10) + " " +
                                                timeList.getQueryStartTime();
                                        Date dateEnd = sdf.parse(endTime);
                                        cal.setTime(dateEnd);
                                        cal.add(Calendar.DATE, -1);
                                        endTime = sdf.format(cal.getTime()).substring(0, 10) + " " +
                                                timeList.getQueryStartTime();
                                    }
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                }
            }
        }
        map.put("beginTime", beginTime);
        map.put("endTime", endTime);
        return map;
    }
}
