package com.hntxrj.txerp.repository;

import com.hntxrj.txerp.entity.base.Feedback;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {


    Page<Feedback> findAllByPid(Integer pid, Pageable pageable);

}
