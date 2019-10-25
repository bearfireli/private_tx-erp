package com.hntxrj.server;

import com.hntxrj.entity.UserCompProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


/**
 * @author lhr
 * @create 2018/2/26
 */
public interface UserCompProductService {

    /**
     * 查询全部企业产品
     * 平台管理员使用。
     * @param pageable  分页
     * @return  全部企业产品
     */
    Page<UserCompProduct> findAll(Pageable pageable);


    /**
     * 通过企业ID查询企业产品
     * @param compid    企业ID
     * @param pageable   分页数据
     * @return          企业产品
     */
    Page<UserCompProduct> findByCompId(String compid, Pageable pageable);

    UserCompProduct findById(Integer id) throws Throwable;

    UserCompProduct addProduct(UserCompProduct userCompProduct);

    UserCompProduct updateProduct(UserCompProduct userCompProduct);

    UserCompProduct findByProductIdAndCompId(Integer productId, String compid);


}
