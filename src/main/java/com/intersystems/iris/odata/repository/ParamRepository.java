package com.intersystems.iris.odata.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.intersystems.iris.odata.model.Param;

@Repository
public interface ParamRepository extends JpaRepository<Param, Integer>{

}
