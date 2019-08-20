package com.biziitech.mlfm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;

import com.biziitech.mlfm.model.ModelItemType;

public interface ItemTypeRepository extends JpaRepository<ModelItemType,Long> {
	
	
	@Query("select it from MLFM_ITEM_TYPE it where it.itemTypeName LIKE CONCAT('%',:item_name,'%') AND it.levelNo=COALESCE(:level,it.levelNo) AND it.remarks LIKE CONCAT('%',:remarks,'%') AND it.activeStatus =:status")
	//  AND it.remarks LIKE CONCAT('%',:remarks,'%') 
    //	@Param("remarks")String remarks,
    public List<ModelItemType> findItemTypeDetails(@Param("item_name")String itemName,@Param("level")Integer level,@Param("remarks")String remarks,@Param("status")int status);
	
	@Query("select a from MLFM_ITEM_TYPE a where  a.activeStatus=1")
	public List<ModelItemType> findItemDetails();
	
	@Query("select a from MLFM_ITEM_TYPE a where a.activeStatus=1 and a.levelNo=0 order by a.itemTypeName")
	
	public List<ModelItemType> findItemCategory();
	
	@Query("select a from MLFM_ITEM_TYPE a where a.parentItemTypeId=:id and a.activeStatus=1 and a.levelNo=1")
	public List<ModelItemType> findItemSubcategory(@Param("id") Long id);
	
	@Query("select a from MLFM_ITEM_TYPE a where a.parentItemTypeId=:id and a.activeStatus=1 and a.levelNo=2")
	public List<ModelItemType> findItemSubsubcategory(@Param("id") Long id);
	
	
	/*created by sohel rana on 27/03/2019
	 * 
	 */
	
	@Query("select a from MLFM_ITEM_TYPE a where a.parentItemTypeId=:id and a.activeStatus=1 and a.levelNo=1")
	public List<ModelItemType> findItemSubcategoryForEdit(@Param("id") Long id);
	
	
	//created by sohel rana on 01/04/2019
	
	@Query("select a from MLFM_ITEM_TYPE a where a.itemTypeId=:id and a.activeStatus=1 and a.levelNo=1")
	public List<ModelItemType> findCatagoryForEdit(@Param("id") Long id);
	
}
