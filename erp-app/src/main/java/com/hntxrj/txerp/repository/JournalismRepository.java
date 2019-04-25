package com.hntxrj.txerp.repository;

import com.hntxrj.txerp.entity.base.Journalism;
import com.hntxrj.txerp.entity.base.Menu;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JournalismRepository extends JpaRepository<Journalism, Integer> {


}
