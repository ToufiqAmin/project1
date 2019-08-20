package com.biziitech.mlfm.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.biziitech.mlfm.model.ModelOrderOwner;

public interface OrderOwnerRepository extends JpaRepository<ModelOrderOwner,Long>{
	
	// modified by sohel on 17/03/2019
	
	
	@Query("select a from MLFM_ORDER_OWNER a where a.ownerCountry.countryId=COALESCE(:countryId,a.ownerCountry.countryId) and a.ownerName LIKE CONCAT('%',:name,'%') and a.activeStatus=:status")
	public List <ModelOrderOwner> findOwnerDetailsNew(@Param("status") int status,@Param("countryId")Long countryId,@Param("name")String name);

	
	@Query("select a from MLFM_ORDER_OWNER a  where a.ownerCountry.countryId=COALESCE(:countryId,a.ownerCountry.countryId) and a.ownerName LIKE CONCAT('%',:name,'%') and a.shortCode LIKE CONCAT('%',:code,'%') and a.contactPersonName LIKE CONCAT('%',:contactPerson,'%') and a.activeStatus=:status")
	public List <ModelOrderOwner> findOwnerDetails(@Param("countryId")Long countryId,@Param("name")String name,@Param("code")String code,@Param("status") int status,@Param("contactPerson")String contactPerson);

	
	//@Query("select a from MLFM_ORDER_OWNER a  where a.modelBusinessType.typeId=COALESCE(:typeId,a.modelBusinessType.typeId) and a.ownerCountry.countryId=COALESCE(:countryId,a.ownerCountry.countryId) and a.ownerName LIKE CONCAT('%',:name,'%') and a.shortCode LIKE CONCAT('%',:code,'%') and a.activeStatus=:status")
	//public List <ModelOrderOwner> findOwnerDetails(@Param("typeId")Long typeId,@Param("countryId")Long countryId,@Param("name")String name,@Param("code")String code,@Param("status") int status);

	@Query("select b from MLFM_ORDER_OWNER b where b.activeStatus=1 ORDER BY b.ownerName")
	public List <ModelOrderOwner> findOwnerNames();
	
	/*
	@Query("select a from MLFM_ORDER_OWNER a where a.orderOwnerType.orderOwnerTypeId=:id ORDER BY a.ownerName")
	public List<ModelOrderOwner> findOwnerbyType(@Param("id") Long id);
	 
	 */
	@Query("select a from MLFM_ORDER_OWNER a where a.orderOwnerId=:id")
	public List<ModelOrderOwner> findOwnerById(@Param("id") Long id);
	
	
	@Query("select a from MLFM_ORDER_OWNER a ORDER BY a.ownerName")
	public List<ModelOrderOwner> findAllOwnerInOrder();
	
	@Query("select a from MLFM_ORDER_OWNER a where a.orderOwnerType.orderOwnerTypeId=:id ORDER BY a.ownerName")
	public List<ModelOrderOwner> findOwnerbyTypeInOrder(@Param("id") Long id);
	
	
	@Query("select a from MLFM_ORDER_OWNER a where a.activeStatus=1 and a.orderOwnerType.orderOwnerTypeId=:id ORDER BY a.ownerName")
	public List<ModelOrderOwner> findActiveOrderOwnerListByOwnerTypeId(@Param("id") Long id);
	
	@Query("select a from MLFM_ORDER_OWNER a where a.ultimateBuyer=1 and a.activeStatus=1 ORDER BY a.ownerName")
	public List<ModelOrderOwner> findUltimateBuyerActive();
	
	
	/*created by sohel rana on 28/03/2019
	 * 
	 */
	
	@Query("select a from MLFM_ORDER_OWNER a where a.orderOwnerType.orderOwnerTypeId=:id and a.activeStatus=1 ORDER BY a.ownerName")
	public List<ModelOrderOwner> findOwnerbyType(@Param("id") Long id);
	 
	
}
