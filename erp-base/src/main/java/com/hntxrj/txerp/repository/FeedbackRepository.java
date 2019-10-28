package com.hntxrj.txerp.repository;

import com.hntxrj.txerp.entity.base.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {


}
