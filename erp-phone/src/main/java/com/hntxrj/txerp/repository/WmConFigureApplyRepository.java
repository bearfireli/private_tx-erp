package com.hntxrj.txerp.repository;

import com.hntxrj.txerp.entity.WmConFigureApply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WmConFigureApplyRepository extends JpaRepository<WmConFigureApply, String> {
}
