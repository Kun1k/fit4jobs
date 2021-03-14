package com.zhaw.fit4jobs.repository;

import com.zhaw.fit4jobs.models.LogModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRepository extends JpaRepository<LogModel, Long>  {

}
