package com.hntxrj.txerp.server.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.arronlong.httpclientutil.HttpClientUtil;
import com.arronlong.httpclientutil.common.HttpConfig;
import com.arronlong.httpclientutil.common.HttpHeader;
import com.arronlong.httpclientutil.exception.HttpProcessException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hntxrj.txerp.mapper.QueryTimeSetMapper;
import com.hntxrj.txerp.server.QueryTimeSetService;
import com.hntxrj.txerp.vo.PageVO;
import com.hntxrj.txerp.vo.QueryTimeSetVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        for (QueryTimeSetVO q:queryTimeSetVO) {
            if (q.getQueryStartTime()!=null){
                q.setQueryStartTime(q.getQueryStartTime().substring(0,5));
            }
            if (q.getQueryStopTime()!=null){
                q.setQueryStopTime(q.getQueryStopTime().substring(0,5));
            }
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
}
