package com.biziitech.mlfm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.biziitech.mlfm.bg.model.ModelObjectGroup;

public interface ObjectGroupRepository extends JpaRepository <ModelObjectGroup,Long> {

	@Query("select a from BG_OBJECT_GROUP a where a.activeStatus=1 order by a.groupName" )
	public List<ModelObjectGroup> findObjectGroup();
	
	@Query("select a from BG_OBJECT_GROUP a  where a.groupName LIKE CONCAT('%',:groupName,'%') and a.shortCode LIKE CONCAT('%',:shortCode,'%') and a.remarks LIKE CONCAT('%',:remarks,'%') and a.activeStatus=:status")
	public List <ModelObjectGroup> findObjectGroupDetails(@Param("groupName")String groupName, @Param("shortCode")String shortCode, @Param("remarks")String remarks, @Param("status") int status);

}
