package com.biziitech.mlfm.repository;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;

import com.biziitech.mlfm.model.ModelOrder;
import com.biziitech.mlfm.model.ModelOrderOwner;
public interface OrderRepository extends JpaRepository<ModelOrder,Long> {
	@Query("select a from MLFM_ORDER a where a.orderId=:id")
	public ModelOrder findOwnerTypeId(@Param("id") Long id);
	//@Query("select a from MLFM_ORDER a where a.modelOrderOwner.ownerName LIKE CONCAT('%',:initial_Buyer,'%') and a.ultimateOwner.ownerName LIKE CONCAT('%',:ultimate_Buyer,'%') and a.orderId=COALESCE(:inquiry_Id,a.orderId) and a.refMailId LIKE CONCAT('%',:mail_Id,'%') and a.userRefNo=COALESCE(:user_inquery_no,a.userRefNo) and a.orderRemarks LIKE CONCAT('%',:remarks,'%') and a.enteredBy.userName LIKE CONCAT('%',:user,'%') and a.orderDate BETWEEN coalesce(:inq_st,a.orderDate) AND coalesce(:inq_ed,a.orderDate) and a.mailSentDate BETWEEN coalesce(:mail_st,a.mailSentDate) AND coalesce(:mail_ed,a.mailSentDate)")
	//public List<ModelOrder> findOwnerDetails(@Param("initial_Buyer") String initial_Buyer,@Param("ultimate_Buyer")String ultimate_Buyer,@Param("inquiry_Id")Long inquiry_Id,@Param("mail_Id")String mail_Id,@Param("user_inquery_no")Long user_inquery_no,@Param("remarks")String remarks,@Param("user")String user,@Param("inq_st")@DateTimeFormat(pattern="yyyy-MM-dd")Date inq_st,@Param("inq_ed")@DateTimeFormat(pattern="yyyy-MM-dd")Date inq_ed,@Param("mail_st")@DateTimeFormat(pattern="yyyy-MM-dd")Date mail_st,@Param("mail_ed")@DateTimeFormat(pattern="yyyy-MM-dd")Date mail_ed);	
	
	@Query("select a from MLFM_ORDER a where UPPER(trim(a.modelOrderOwner.ownerName)) LIKE CONCAT('%',:initial_Buyer,'%') and a.ultimateOwner.ownerName LIKE CONCAT('%',:ultimate_Buyer,'%') and a.orderId=COALESCE(:inquiry_Id,a.orderId) and a.refMailId LIKE CONCAT('%',:mail_Id,'%') and a.userRefNo=COALESCE(:user_inquery_no,a.userRefNo) and a.orderRemarks LIKE CONCAT('%',:remarks,'%') and a.enteredBy.userName LIKE CONCAT('%',:user,'%') and a.orderDate BETWEEN coalesce(:inq_st,a.orderDate) AND coalesce(:inq_ed,a.orderDate)")
	//@Query("select a from MLFM_ORDER a where UPPER(trim(a.modelOrderOwner.ownerName)) LIKE CONCAT('%',upper(TRIM(:initialBuyer)),'%') and UPPER(trim(a.ultimateOwner.ownerName)) LIKE CONCAT('%',upper(TRIM(:ultimateBuyer)),'%') and a.orderId=COALESCE(:inquiry_Id,a.orderId) and a.refMailId LIKE CONCAT('%',:mail_Id,'%') and a.userRefNo=COALESCE(:user_inquery_no,a.userRefNo) and a.orderRemarks LIKE CONCAT('%',:remarks,'%') and upper(TRIM(a.enteredBy.userName)) LIKE CONCAT('%',:user,'%') and a.orderDate BETWEEN coalesce(:inq_st,a.orderDate) AND coalesce(:inq_ed,a.orderDate)")
	public List<ModelOrder> findOwnerDetails(@Param("initial_Buyer") String initial_Buyer,@Param("ultimate_Buyer")String ultimate_Buyer,@Param("inquiry_Id")Long inquiry_Id,@Param("mail_Id")String mail_Id,@Param("user_inquery_no")Long user_inquery_no,@Param("remarks")String remarks,@Param("user")String user,@Param("inq_st")@DateTimeFormat(pattern="yyyy-MM-dd")Date inq_st,@Param("inq_ed")@DateTimeFormat(pattern="yyyy-MM-dd")Date inq_ed);	

	
	
	/*
	@Query("select a from MLFM_ORDER a where UPPER(trim(a.modelOrderOwner.ownerName)) LIKE CONCAT('%',:initial_Buyer,'%') and a.ultimateOwner.ownerName LIKE CONCAT('%',:ultimate_Buyer,'%') and a.orderId=COALESCE(:inquiry_Id,a.orderId) and a.refMailId LIKE CONCAT('%',:mail_Id,'%') and a.userRefNo=COALESCE(:user_inquery_no,a.userRefNo) and a.orderRemarks LIKE CONCAT('%',:remarks,'%') and a.enteredBy.userName LIKE CONCAT('%',:user,'%') and a.orderDate BETWEEN coalesce(:inq_st,a.orderDate) AND coalesce(:inq_ed,a.orderDate) and a.activeStatus=:active_status")
	public List<ModelOrder> findOwnerDetailsActive(@Param("initial_Buyer") String initial_Buyer,@Param("ultimate_Buyer")String ultimate_Buyer,@Param("inquiry_Id")Long inquiry_Id,@Param("mail_Id")String mail_Id,@Param("user_inquery_no")Long user_inquery_no,@Param("remarks")String remarks,@Param("user")String user,@Param("inq_st")@DateTimeFormat(pattern="yyyy-MM-dd")Date inq_st,@Param("inq_ed")@DateTimeFormat(pattern="yyyy-MM-dd")Date inq_ed, @Param("active_status")int active_status);	
*/
	@Query("select a from MLFM_ORDER a where a.modelOrderOwner.orderOwnerId =COALESCE(:initial_Buyer,a.modelOrderOwner.orderOwnerId ) and a.ultimateOwner.orderOwnerId =COALESCE(:ultimate_Buyer,a.ultimateOwner.orderOwnerId) and a.orderId=COALESCE(:inquiry_Id,a.orderId) and a.refMailId LIKE CONCAT('%',:mail_Id,'%') and a.userRefNo=COALESCE(:user_inquery_no,a.userRefNo) and a.orderRemarks LIKE CONCAT('%',:remarks,'%') and a.enteredBy.userName LIKE CONCAT('%',:user,'%') and a.orderDate BETWEEN coalesce(:inq_st,a.orderDate) AND coalesce(:inq_ed,a.orderDate) and a.activeStatus=:active_status")
	//@Query("select a from MLFM_ORDER a where UPPER(trim(a.modelOrderOwner.ownerName)) LIKE CONCAT('%',upper(TRIM(:initialBuyer)),'%') and UPPER(trim(a.ultimateOwner.ownerName)) LIKE CONCAT('%',upper(TRIM(:ultimateBuyer)),'%') and a.orderId=COALESCE(:inquiry_Id,a.orderId) and a.refMailId LIKE CONCAT('%',:mail_Id,'%') and a.userRefNo=COALESCE(:user_inquery_no,a.userRefNo) and a.orderRemarks LIKE CONCAT('%',:remarks,'%') and upper(TRIM(a.enteredBy.userName)) LIKE CONCAT('%',:user,'%') and a.orderDate BETWEEN coalesce(:inq_st,a.orderDate) AND coalesce(:inq_ed,a.orderDate)")
	public List<ModelOrder> findOwnerDetailsActive(@Param("initial_Buyer") Long initial_Buyer,@Param("ultimate_Buyer")Long ultimate_Buyer,@Param("inquiry_Id")Long inquiry_Id,@Param("mail_Id")String mail_Id,@Param("user_inquery_no")Long user_inquery_no,@Param("remarks")String remarks,@Param("user")String user,@Param("inq_st")@DateTimeFormat(pattern="yyyy-MM-dd")Date inq_st,@Param("inq_ed")@DateTimeFormat(pattern="yyyy-MM-dd")Date inq_ed, @Param("active_status")int active_status);	
	
	@Query("select a from MLFM_ORDER a where UPPER(trim(a.modelOrderOwner.ownerName)) LIKE CONCAT('%',upper(TRIM(:initialBuyer)),'%') and UPPER(trim(a.ultimateOwner.ownerName)) LIKE CONCAT('%',upper(TRIM(:ultimateBuyer)),'%') and upper(TRIM(a.enteredBy.userName)) LIKE CONCAT('%',upper(TRIM(:user)),'%') and a.orderDate BETWEEN coalesce(:stDate,a.orderDate) AND coalesce(:endDate,a.orderDate)")
	public List<ModelOrder> findOrderOnCriteria(@Param("initialBuyer") String initialBuyer,@Param("ultimateBuyer")String ultimateBuyer,@Param("user")String user,@Param("stDate")@DateTimeFormat(pattern="yyyy-MM-dd")Date stDate,@Param("endDate")@DateTimeFormat(pattern="yyyy-MM-dd")Date endDate);
	//@Query("select a from MLFM_ORDER a where a.orderDate BETWEEN coalesce(:stDate,a.orderDate) AND coalesce(:endDate,a.orderDate)")
	//public List<ModelOrder> findOrderOnCriteria(@Param("stDate")@DateTimeFormat(pattern="yyyy-MM-dd")Date stDate,@Param("endDate")@DateTimeFormat(pattern="yyyy-MM-dd")Date endDate);	

	
	/*
	 * if following query is used (native query) then following error is shown
	 * Caused by: javax.persistence.EntityNotFoundException: Unable to find com.biziitech.mlfm.model.ModelOrderOwner with id 0
	 * this error needs to be solved. this error is shown for hibernate query also.
	 * 
	   @Query(value="select \r\n" + 
	   		"\r\n" + 
	   		"ORDER_ID,USER_ORDER_NO,ORDER_TYPE,ORDER_DATE,REF_ORDER_ID,REF_ID,USER_REF_NO,ORDER_OWNER_TYPE_ID,ORDER_OWNER_ID,ULTIMATE_ORDER_OWNER_ID,REF_MAIL_ID,MAIL_SENT_DATE,MAIL_RECEIVE_DATE,\r\n" + 
	   		"ORDER_REMARKS,FINAL_STATUS,VERIFY_STATUS,VERIFIED_BY,ACTIVE_STATUS,ENTERED_BY,ENTRY_TIMESTAMP,UPDATED_BY,UPDATE_TIMESTAMP,APRX_ORDER_QTY,APRX_ORDER_QTY_UOM,CLUSTER_ID,\r\n" + 
	   		"CONTACT_PERSON_NAME,BUYER_EXPECTED_PRICE,BUYER_EXPECTED_DELIVERY_DATE,FLEX_FIELD1,FLEX_FIELD2,FLEX_FIELD3,FLEX_FIELD4,FLEX_FIELD5 from MLFM_ORDER a where a.order_date BETWEEN coalesce(:stDate,a.order_date) AND coalesce(:endDate,a.order_date)", nativeQuery=true)
		public List<ModelOrder> findOrderOnCriteria(@Param("stDate")@DateTimeFormat(pattern="yyyy-MM-dd")Date stDate,@Param("endDate")@DateTimeFormat(pattern="yyyy-MM-dd")Date endDate);	

	*/
	
	
	
	
	@Modifying(clearAutomatically = true)
	@Query("update MLFM_ORDER a set a.verifyRemarks = :verifyRemarks where a.id = :id")
	public void updateVerifyOrder(@Param("verifyRemarks") String verifyRemarks, @Param("id") Long id);
	
	
    /*created by sohel rana on 21/03/2019 
     */
	
	@Query("select a from MLFM_ORDER a where a.modelOrderOwner.orderOwnerId=coalesce(:owner,a.modelOrderOwner.orderOwnerId) and a.modelOrderOwner.orderOwnerId=coalesce(:ultimateOwner,a.modelOrderOwner.orderOwnerId) and a.orderId=coalesce(:orderId,a.orderId) and a.enteredBy.userId=coalesce(:user,a.enteredBy.userId) and a.modelCluster.clusterId=coalesce(:cluster,a.modelCluster.clusterId) and a.refMailId LIKE CONCAT('%',:mail,'%') and a.userRefNo=COALESCE(:user_inquery_no,a.userRefNo) and a.orderRemarks LIKE CONCAT('%',:remarks,'%') and a.orderDate BETWEEN coalesce(:inq_st,a.orderDate) AND coalesce(:inq_ed,a.orderDate) and a.modelOrderOwnerType.orderOwnerTypeId=coalesce(:typeId,a.modelOrderOwnerType.orderOwnerTypeId) and a.activeStatus=:status order by a.modelOrderOwner.ownerName")
	public List<ModelOrder> findAllOwnerDetailsByCraiteria(@Param("owner") Long owner,@Param("ultimateOwner")Long ultimateOwner,@Param("orderId")Long orderId,@Param("user")Long user,@Param("cluster")Long cluster,@Param("mail")String mail,@Param("user_inquery_no")Long user_inquery_no,@Param("remarks")String remarks,@Param("inq_st")@DateTimeFormat(pattern="yyyy-MM-dd")Date inq_st,@Param("inq_ed")@DateTimeFormat(pattern="yyyy-MM-dd")Date inq_ed,@Param("typeId")Long typeId,@Param("status")int status);
	
	
	
	/*created by sohel rana on 24/03/2019 
	 * method: findAllOwnerDetailsByCraiterias
	 * This method searches inquiry data based on search parameters at initial
	 */
	
	@Query("select a from MLFM_ORDER a where a.modelOrderOwnerType.orderOwnerTypeId=coalesce(:typeId,a.modelOrderOwnerType.orderOwnerTypeId) and a.modelOrderOwner.orderOwnerId=coalesce(:owner,a.modelOrderOwner.orderOwnerId) and a.modelOrderOwner.orderOwnerId=coalesce(:ultimateOwner,a.modelOrderOwner.orderOwnerId) and a.orderId=coalesce(:orderId,a.orderId) and a.enteredBy.userId=coalesce(:user,a.enteredBy.userId) and a.orderDate BETWEEN coalesce(:inq_st,a.orderDate) AND coalesce(:inq_ed,a.orderDate) and a.activeStatus=:status")
	public List<ModelOrder> findAllOwnerDetailsByCraiterias(@Param("typeId")Long typeId,@Param("owner") Long owner,@Param("ultimateOwner")Long ultimateOwner,@Param("orderId")Long orderId,@Param("user")Long user,@Param("inq_st")@DateTimeFormat(pattern="yyyy-MM-dd")Date inq_st,@Param("inq_ed")@DateTimeFormat(pattern="yyyy-MM-dd")Date inq_ed,@Param("status")int status);
	
	
	@Query("select a from MLFM_ORDER a where a.orderId=coalesce(:orderId,a.orderId)")
	public List<ModelOrder> findNewInquiryData(@Param("orderId")Long orderId);
	
	@Query("select a from MLFM_ORDER a where a.orderId=coalesce(:id,a.orderId)")
	public List<ModelOrder> findNewInquiry(@Param("id") Long id);
	
	//created by sohel rana on 18/04/2019
	
	@Query("select a from MLFM_ORDER a where a.modelOrderOwner.orderOwnerId=coalesce(:ownerId,a.modelOrderOwner.orderOwnerId) and a.orderDate=:orderDate")
	public List<ModelOrder> checkInquiryData(@Param("ownerId")Long ownerId,@Param("orderDate")@DateTimeFormat(pattern="yyyy-MM-dd")Date orderDate);
	
	
	//created by sohel rana on 23/04/2019
	
	
	@Query("select a from MLFM_ORDER a where a.modelOrderOwnerType.orderOwnerTypeId=coalesce(:typeId,a.modelOrderOwnerType.orderOwnerTypeId) and a.modelOrderOwner.orderOwnerId=coalesce(:owner,a.modelOrderOwner.orderOwnerId) and a.modelOrderOwner.orderOwnerId=coalesce(:ultimateOwner,a.modelOrderOwner.orderOwnerId) and a.orderId=coalesce(:orderId,a.orderId) and a.enteredBy.userId=coalesce(:user,a.enteredBy.userId) and a.orderDate BETWEEN coalesce(:inq_st,a.orderDate) AND coalesce(:inq_ed,a.orderDate) and a.activeStatus=:status and a.verifyStatus=0 ")
	public List<ModelOrder> findNotVerifiedInquiry(@Param("typeId")Long typeId,@Param("owner") Long owner,@Param("ultimateOwner")Long ultimateOwner,@Param("orderId")Long orderId,@Param("user")Long user,@Param("inq_st")@DateTimeFormat(pattern="yyyy-MM-dd")Date inq_st,@Param("inq_ed")@DateTimeFormat(pattern="yyyy-MM-dd")Date inq_ed,@Param("status")int status);
	
	//created by sohel rana on 25/04/2019
	

	//@Query("select a from MLFM_ORDER a where a.modelOrderOwner.orderOwnerId=coalesce(:owner,a.modelOrderOwner.orderOwnerId) and a.modelOrderOwner.orderOwnerId=coalesce(:ultimateOwner,a.modelOrderOwner.orderOwnerId) and a.orderId=coalesce(:orderId,a.orderId) and a.enteredBy.userId=coalesce(:user,a.enteredBy.userId) and a.refMailId LIKE CONCAT('%',:mail,'%') and a.userRefNo=COALESCE(:user_inquery_no,a.userRefNo) and a.orderRemarks LIKE CONCAT('%',:remarks,'%') and a.orderDate BETWEEN coalesce(:inq_st,a.orderDate) AND coalesce(:inq_ed,a.orderDate) and a.modelOrderOwnerType.orderOwnerTypeId=coalesce(:typeId,a.modelOrderOwnerType.orderOwnerTypeId) and a.verifyTimestamp BETWEEN coalesce(:done_st,a.verifyTimestamp) AND coalesce(:done_ed,a.verifyTimestamp) and a.modelVerified.userId=coalesce(:verifiedBy,a.modelVerified.userId) and a.activeStatus=:status and  a.modelVerified.userId is Not Null")
	//public List<ModelOrder> findOwnerDetailsVerifiedDataByCraiteria(@Param("owner") Long owner,@Param("ultimateOwner")Long ultimateOwner,@Param("orderId")Long orderId,@Param("user")Long user,@Param("mail")String mail,@Param("user_inquery_no")Long user_inquery_no,@Param("remarks")String remarks,@Param("inq_st")@DateTimeFormat(pattern="yyyy-MM-dd")Date inq_st,@Param("inq_ed")@DateTimeFormat(pattern="yyyy-MM-dd")Date inq_ed,@Param("typeId")Long typeId,@Param("status")int status,@Param("done_st")@DateTimeFormat(pattern="yyyy-MM-dd")Date done_st,@Param("done_ed")@DateTimeFormat(pattern="yyyy-MM-dd")Date done_ed,@Param("verifiedBy")Long verifiedBy);
	
	@Query("select a from MLFM_ORDER a where a.modelOrderOwnerType.orderOwnerTypeId=coalesce(:typeId,a.modelOrderOwnerType.orderOwnerTypeId) and a.modelOrderOwner.orderOwnerId=coalesce(:owner,a.modelOrderOwner.orderOwnerId) and a.modelOrderOwner.orderOwnerId=coalesce(:ultimateOwner,a.modelOrderOwner.orderOwnerId) and a.orderId=coalesce(:orderId,a.orderId) and a.enteredBy.userId=coalesce(:user,a.enteredBy.userId) and a.orderDate BETWEEN coalesce(:inq_st,a.orderDate) AND coalesce(:inq_ed,a.orderDate) and a.activeStatus=:status and a.verifyTimestamp BETWEEN coalesce(:done_st,a.verifyTimestamp) AND coalesce(:done_ed,a.verifyTimestamp) and a.modelVerified.userId=coalesce(:verifiedBy,a.modelVerified.userId) and a.verifyStatus=1 ")
	public List<ModelOrder> findVerifiedInquiry(@Param("typeId")Long typeId,@Param("owner") Long owner,@Param("ultimateOwner")Long ultimateOwner,@Param("orderId")Long orderId,@Param("user")Long user,@Param("inq_st")@DateTimeFormat(pattern="yyyy-MM-dd")Date inq_st,@Param("inq_ed")@DateTimeFormat(pattern="yyyy-MM-dd")Date inq_ed,@Param("status")int status,@Param("done_st")@DateTimeFormat(pattern="yyyy-MM-dd")Date done_st,@Param("done_ed")@DateTimeFormat(pattern="yyyy-MM-dd")Date done_ed,@Param("verifiedBy")Long verifiedBy);
	
	//
	//a.modelVerified.userId is not null
	//a.modelVerified.userId is null
}
