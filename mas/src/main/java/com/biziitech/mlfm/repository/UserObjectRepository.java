package com.biziitech.mlfm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.biziitech.mlfm.bg.model.ModelObjectGroup;
import com.biziitech.mlfm.model.ModelProductionPlanChd;
import com.biziitech.mlfm.model.ModelUserObject;

public interface UserObjectRepository extends JpaRepository<ModelUserObject,Long> {
	
	
	

//	@Query("select a from BG_USER_OBJECT a  where a.modelUser.userId=COALESCE(:userId,a.modelUser.userId) and a.modelObject.modelObjectGroup.shortCode=:objectGroup")
	@Query("select a from BG_USER_OBJECT a  where a.modelUser.userId=COALESCE(:userId,a.modelUser.userId) and UPPER(trim(a.modelObject.modelObjectGroup.shortCode)) LIKE CONCAT('%',:objectGroup,'%')")
	public List<ModelUserObject> findUserObjectByUserIdObjectGroup(@Param("userId")Long userId,@Param("objectGroup")String objectGroup);
	
	@Query("select a from BG_USER_OBJECT a  where a.modelUser.userId=COALESCE(:userId,a.modelUser.userId)")
	public List<ModelUserObject> findUserObjectByUserId(@Param("userId")Long userId);
			
	
	
	@Query("select a from BG_USER_OBJECT a  where a.userObjectId=COALESCE(:userObjectId,a.userObjectId)")			
	public List<ModelUserObject> findUserObject(@Param("userObjectId")Long userObjectId);
	
	
//	@Query("select a from BG_USER_OBJECT_GROUP a")
	@Query("select a from BG_OBJECT_GROUP a where a.activeStatus=1" )
	public List<ModelObjectGroup> findObjectGroup();
	
	
	//@Query("select a from BG_USER_OBJECT a  where a.modelObject.modelObjectGroup.objectGroupId=COALESCE(:objectGroupId,a.modelObject.modelObjectGroup.objectGroupId)")
	@Query("select a from BG_OBJECT a  where a.modelObjectGroup.objectGroupId=COALESCE(:objectGroupId,a.modelObjectGroup.objectGroupId)")
	public List<ModelUserObject> findUserObjectByObjectGroupId(@Param("objectGroupId")Long objectGroupId);
	
	
	//@Query("select a from BG_USER_OBJECT a where a.modelUserObject.modelUser.userId=COALESCE(:userId,a.modelUserObject.modelUser.userId) and a.modelUserObject.modelObject.objectId=COALESCE(:objectId,a.modelUserObject.modelObject.objectId)")
	
	//public List<ModelUserObject> checkUserObject(@Param("userId")Long userId,@Param("objectId")Long objectId);
	
	
	

}
