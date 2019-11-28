package com.hntxrj.txerp.mapper;

import com.hntxrj.txerp.entity.base.Menu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author qyb
 * @ClassName MenuMapper
 * @Description TODO
 * @Date 2019/11/15 上午10:46
 * @Version 1.0
 **/
@Mapper
public interface MenuMapper {

    List<Menu> getFunctionMenuList();
}
