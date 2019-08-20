package com.biziitech.mlfm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.biziitech.mlfm.bg.model.ModelPhone;

public interface PhoneRepository extends JpaRepository <ModelPhone,Long> {
	@Query("select a from BG_PHONE a where a.owner.orderOwnerId=:id ")
	public List<ModelPhone> findPhones(@Param("id")Long id);
	
	@Query("select a from BG_PHONE a where a.modelUser.userId=:id ")
	public List<ModelPhone> findPhonesByUserId(@Param("id")Long id);
	
	
	//created by sohel rana on 02/04/2019
	
	@Query("select a.contactPersonName from BG_PHONE a where a.activeStatus=1 and a.owner.orderOwnerId=coalesce(:id , a.owner.orderOwnerId)")
	public List<ModelPhone> findContactPerson(@Param("id")Long id);
	
}
