package com.hntxrj.txerp.repository;

import com.hntxrj.txerp.entity.base.Menu;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Integer> {


    List<Menu> findAllByMenuStatusAndEnterpriseIn(Integer status, List<Integer> enterprises, Sort sort);

    List<Menu> findAllByEnterpriseIn(List<Integer> enterprises);

    List<Menu> findAllByEnterpriseIn(List<Integer> enterprises, Sort sort);


    List<Menu> findAllByProject(Integer project, Sort sort);


}
