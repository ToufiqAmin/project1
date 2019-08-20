package com.biziitech.mlfm.repository;


import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.biziitech.mlfm.bg.model.ModelObjectProcess;

public interface ObjectProcessRepository extends JpaRepository <ModelObjectProcess,Long> {
	
	@Query("select a from BG_OBJECT_PROCESS a where a.modelObject.objectId=COALESCE(:Object_Id,a.modelObject.objectId)")
	public List<ModelObjectProcess> findObjectProcessFromObject(@Param("Object_Id") Long objectId);

}
