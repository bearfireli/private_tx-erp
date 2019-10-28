package com.hntxrj.txerp.repository;

import com.hntxrj.txerp.entity.base.Journalism;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JournalismRepository extends JpaRepository<Journalism, Integer> {


}
