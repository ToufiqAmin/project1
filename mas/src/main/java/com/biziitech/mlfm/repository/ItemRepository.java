package com.biziitech.mlfm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.biziitech.mlfm.model.ModelDesign;
import com.biziitech.mlfm.model.ModelItem;

public interface ItemRepository extends JpaRepository <ModelItem,Long>{
	
	@Query ("select a from MLFM_ITEM a where a.activeStatus=1")
	public List<ModelItem> findItems();
	
	
	@Query("select a from MLFM_ITEM a where a.itemId=:id")
	public List<ModelItem> findItemById(@Param("id") Long id);
	
	@Query("select a from MLFM_ITEM a where  a.itemName LIKE concat('%', :itemName,'%') and trim(a.remarks) like concat('%',:remarks,'%') and a.activeStatus=:active")    
			// and trim(a.remarks) like :remarks ")
	public List<ModelItem> searchItems(@Param("itemName")String itemName,@Param("remarks")String remarks, @Param("active")int active);
	


	@Query("select a from MLFM_ITEM a where a.itemTypeId.itemTypeId=:id")
	public List<ModelItem> findItemByTypeId(@Param("id") Long id);
	
	@Query(value="select * from MLFM_ITEM a WHERE a.ACTIVE_STATUS=1 ORDER BY a.ITEM_NAME", nativeQuery=true)
	public List <ModelItem> findItemListActive();


//where a.itemName like concat('%',:pItemName,'%')
}

/*
@Query("select a from MLFM_ORDER_OWNER a  "
		+ "where"
		+ " a.modelBusinessType.typeId=COALESCE(:typeId,a.modelBusinessType.typeId) "
		+ "and a.ownerCountry.countryId=COALESCE(:countryId,a.ownerCountry.countryId) "
		+ "and a.ownerName LIKE CONCAT('%',:name,'%') and a.shortCode LIKE CONCAT('%',:code,'%') "
		+ "and a.activeStatus=:status")
public List <ModelOrderOwner> findOwnerDetails(@Param("typeId")Long typeId,@Param("countryId")Long countryId,@Param("name")String name,@Param("code")String code,@Param("status") int status);
*/