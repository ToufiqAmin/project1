package com.biziitech.mlfm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.biziitech.mlfm.bg.model.ModelUser;
import com.biziitech.mlfm.model.ModelCluster;

import java.util.List;




public interface LogonRepository extends JpaRepository <ModelUser,Long>{
	
	@Query("select a from BG_USER a where upper(a.userName)=upper(:userName) and a.securityCode=:password")
	public ModelUser getValidUserPassword(@Param("userName") String userName,@Param("password") String password);
	
	//created by sohel rana on 16/04/2019
	
	@Query("select a from BG_USER a where UPPER((a.userName)) in (UPPER(:userName)) and a.securityCode=:password")
	public List <ModelUser> getUserValidatiion(@Param("userName") String userName,@Param("password") String password);
	
	@Query("select a from BG_USER a where a.userId=:id")
	ModelUser getLogonUserName(@Param("id")Long id);
	
}
