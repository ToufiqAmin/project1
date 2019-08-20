package com.biziitech.mlfm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.biziitech.mlfm.bg.model.ModelUser;
import com.biziitech.mlfm.model.ModelCluster;
public interface UserRepository extends JpaRepository<ModelUser,Long> {
	
	@Query("select c.userName,c.userId from BG_USER c where c.activeStatus=1")
	public List<ModelUser> getUserName();
	
	@Query("select c from BG_USER c where c.activeStatus=1 ORDER BY c.userName")
	public List<ModelUser> getUserNameInOrder();
	
	@Query("select a from BG_USER a  where a.userName LIKE CONCAT('%',:userName,'%') and a.titleName LIKE CONCAT('%',:titleName,'%') and a.passportNo LIKE CONCAT('%',:passportNo,'%') and a.activeStatus=:status")
	public List <ModelUser> findUserDetails(@Param("userName")String userName, @Param("titleName")String titleName, @Param("passportNo")String passportNo, @Param("status") int status);

	
	
	//@Query("select a from BG_USER a  where a.userName LIKE CONCAT('%',:userName,'%')")
	@Query("select a from BG_USER a  where a.userName = CONCAT(''',:userName,''')")
    //public ModelUser getUserIdByName(@Param("userName")String userName);
	public Long getUserIdByName(@Param("userName")String userName);
	
    //created by sohel rana on 10/04/2019
	
	@Query("select a from BG_USER a  where a.activeStatus=1 and a.userId=:userId ")
	public List<ModelUser> getUser(@Param("userId")Long  userId);
   
}
