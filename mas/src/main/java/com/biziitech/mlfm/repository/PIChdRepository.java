package com.biziitech.mlfm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.biziitech.mlfm.model.ModelPIChd;
import com.biziitech.mlfm.model.ModelPIMst;

public interface PIChdRepository extends JpaRepository <ModelPIChd,Long> {

}
