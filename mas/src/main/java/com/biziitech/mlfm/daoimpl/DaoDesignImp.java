package com.biziitech.mlfm.daoimpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.biziitech.mlfm.custom.model.ModelDesignCustom;
import com.biziitech.mlfm.custom.model.ModelMachineScheduleData;
import com.biziitech.mlfm.custom.model.ModelProductionCustom;
import com.biziitech.mlfm.dao.DaoDesign;
import com.biziitech.mlfm.model.ModelDesign;
import com.biziitech.mlfm.repository.DesignRepository;
@Service
public class DaoDesignImp implements DaoDesign {
	
	@Autowired
    private DataSource dataSource;
	
	@Autowired
	private NamedParameterJdbcTemplate namedJdbcTemplate;
    public void setNamedDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.namedJdbcTemplate=namedJdbcTemplate;
          }
	
	
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	@Autowired
	private DesignRepository designRepository;
	
	private List<ModelDesign> designList;
	public List<ModelDesign> getDesignList() {
		return designList;
	}
	public void setDesignList(List<ModelDesign> designList) {
		this.designList = designList;
	}
	private Long orderId;
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	@Override
	public void saveDesign(ModelDesign design) {
		
		if(design.isActive())
			design.setActiveStatus(1);
		else
			design.setActiveStatus(0);
		
		designRepository.save(design);
	}
	@Override
	public Optional<ModelDesign> getDesignById(Long id) {
		Optional<ModelDesign> design= designRepository.findById(id);
		design.get().setDesign_Date(format.format(design.get().getDesignDate()));
		design.get().setFactoryDate(format.format(design.get().getFactPropDeliveryDate()));
		return design;
	}
	@Override
	public List<ModelDesign> getDesignFromInquery(Long id){
		List<ModelDesign> designListInquery=designRepository.findDesignFromInquery(id);
		setDesignList(designListInquery);
		return designListInquery;
	}
	@Override
	public List<ModelDesign> getDesignListActive() {
		
		return designRepository.findDesignListActive();
		// TODO Auto-generated method stub
		 
	}
	@Override
	public List<ModelDesignCustom> getDesignListNotDone(Long orderOwnerTypeId,Long orderOwnerId,Long ultimateBuyerId,Long orderId,Long itemId,Long userId,Date orderStartDate,Date orderEndDate,String userInqueryNo,String remarks,int active) {
			
		
		//,i.item_name,oiq.order_item_qty_id
//		String qry="SELECT o.order_id,o.order_date,o.ultimate_order_owner_id,o.ref_mail_id,o.order_remarks,o.user_ref_no,oo.owner_name,bu.user_name,o.active_status\r\n" + 
//				"FROM mlfm_order o\r\n" + 
//				"INNER JOIN mlfm_order_owner oo ON oo.order_owner_id=o.order_owner_id\r\n" + 
//				"INNER JOIN bg_user bu ON bu.entered_by=o.entered_by\r\n" + 
//				//"INNER JOIN mlfm_order_item oi on oi.order_id=o.order_id\r\n" + 
//				//"INNER JOIN mlfm_order_item_qty oiq ON oiq.order_item_id=oi.item_order_id\r\n" + 
//				//"INNER JOIN mlfm_item i ON i.item_id=oi.item_id\r\n" + 
//				"WHERE o.order_owner_id=COALESCE(:orderOwnerId,o.order_owner_id) \r\n" + 
//				"AND o.ultimate_order_owner_id = COALESCE(:ultimateOwnerId,o.ultimate_order_owner_id)\r\n" + 
//				"AND o.order_id =COALESCE(:orderId,o.order_id)\r\n" + 
//				//"AND i.item_id =COALESCE(:itemId,i.item_id)\r\n" + 
//				"AND bu.user_id = COALESCE(:userId,bu.user_id)\r\n" +
//				"AND UPPER(trim(o.order_remarks)) LIKE CONCAT('%',upper(TRIM(:remarks)),'%') " +
//				"and o.order_date between coalesce(:orderStartDate,o.order_date) and coalesce(:orderEndDate,o.order_date)" +
//				"AND o.active_status =COALESCE(:activeStatus,o.active_status)";
		
		/*
		//,i.item_name,oiq.order_item_qty_id
		String  qry="SELECT o.order_id,o.order_date,o.ultimate_order_owner_id,o.ref_mail_id,o.order_remarks,o.user_ref_no,oo.owner_name,bu.user_name,o.active_status\r\n" + 
				"FROM mlfm_order o\r\n" + 
				"INNER JOIN mlfm_order_owner oo ON oo.order_owner_id=o.order_owner_id\r\n" + 
				"INNER JOIN bg_user bu ON bu.entered_by=o.entered_by\r\n" + 
				//"INNER JOIN mlfm_order_item oi on oi.order_id=o.order_id\r\n" + 
				//"INNER JOIN mlfm_order_item_qty oiq ON oiq.order_item_id=oi.item_order_id\r\n" + 
				//"INNER JOIN mlfm_item i ON i.item_id=oi.item_id\r\n" + 
				"WHERE o.order_owner_id=COALESCE(:orderOwnerId,o.order_owner_id) \r\n" + 
				"AND o.ultimate_order_owner_id = COALESCE(:ultimateOwnerId,o.ultimate_order_owner_id)\r\n" + 
				"AND o.order_id =COALESCE(:orderId,o.order_id)\r\n" + 
				//"AND i.item_id =COALESCE(:itemId,i.item_id)\r\n" + 
				"AND bu.user_id = COALESCE(:userId,bu.user_id)\r\n" + 
				"AND UPPER(trim(o.order_remarks)) LIKE CONCAT('%',upper(TRIM(:remarks)),'%') " +
				"AND UPPER(trim(o.user_ref_no)) LIKE CONCAT('%',upper(TRIM(:userRefNo)),'%')" +
				"and o.order_date between coalesce(:orderStartDate,o.order_date) and coalesce(:orderEndDate,o.order_date)\r\n" + 
				"AND o.active_status =COALESCE(:activeStatus,o.active_status)";
		*/
		
				
		/*
		 * sql query
		 * SELECT o.order_id,o.order_date,o.ultimate_order_owner_id,o.ref_mail_id,o.order_remarks,o.user_ref_no,oo.owner_name,bu.user_name,o.active_status,i.item_name,oiq.order_item_qty_id
FROM mlfm_order o
INNER JOIN mlfm_order_owner oo ON oo.order_owner_id=o.order_owner_id
INNER JOIN bg_user bu ON bu.entered_by=o.entered_by
INNER JOIN mlfm_order_item oi on oi.order_id=o.order_id
INNER JOIN mlfm_order_item_qty oiq ON oiq.order_item_id=oi.item_order_id
INNER JOIN mlfm_item i ON i.item_id=oi.item_id
WHERE o.order_owner_id=COALESCE(@orderOwnerId,o.order_owner_id) 
AND o.ultimate_order_owner_id = COALESCE(@ultimateOwnerId,o.ultimate_order_owner_id)
AND o.order_id =COALESCE(@orderId,o.order_id)
AND i.item_id =COALESCE(@itemId,i.item_id)
AND bu.user_id = COALESCE(@userId,bu.user_id)
and o.order_date between coalesce(@orderStartDate,o.order_date) and coalesce(@orderEndDate,o.order_date)
AND o.active_status =COALESCE(@activeStatus,o.active_status)
		 * 
		 * 
		 * 
		 * 
		 * AND UPPER(trim(o.order_remarks)) LIKE CONCAT('%',upper(TRIM(@remarks)),'%')
		 * AND UPPER(trim(o.user_ref_no)) LIKE CONCAT('%',upper(TRIM(@userRefNo)),'%')
		 * */
		
		
	/*	
		//,i.item_name
		String  qry="SELECT oot.order_owner_type_id,o.order_id,o.order_date,o.ultimate_order_owner_id,o.ref_mail_id,o.order_remarks,\r\n" + 
				"o.user_ref_no,oo.owner_name,o.active_status,bu.user_name \r\n" + 
				"FROM mlfm_order o\r\n" + 
				"INNER JOIN mlfm_order_owner oo ON oo.order_owner_id=o.order_owner_id\r\n" + 
				"INNER JOIN mlfm_order_owner_type oot on oot.order_owner_type_id=oo.order_owner_type_id\r\n" + 
				"INNER JOIN bg_user bu ON o.entered_by=bu.user_id\r\n" + 
				//"INNER JOIN mlfm_order_item oi on oi.order_id=o.order_id\r\n" + 
				//"LEFT OUTER JOIN mlfm_order_item_qty oiq ON oiq.order_item_id=oi.item_order_id\r\n" + 
				//"LEFT OUTER JOIN mlfm_item i ON i.item_id=oi.item_id\r\n" + 
				"WHERE o.order_owner_id=COALESCE(:orderOwnerId,o.order_owner_id)\r\n" + 
				"AND oot.order_owner_type_id=COALESCE(:orderOwnerTypeId,oot.order_owner_type_id)\r\n" + 
				"AND o.ultimate_order_owner_id =COALESCE(:ultimateOwnerId,o.ultimate_order_owner_id) \r\n" + 
				"AND o.order_id =COALESCE(:orderId,o.order_id)\r\n" + 
				"AND bu.user_id = COALESCE(:userId,bu.user_id)\r\n" + 
				//"AND i.item_id = COALESCE(:itemId,i.item_id)\r\n" + 
				"AND UPPER(trim(o.order_remarks)) LIKE CONCAT('%',upper(TRIM(:remarks)),'%')" +
				"AND UPPER(trim(o.user_ref_no)) LIKE CONCAT('%',upper(TRIM(:userRefNo)),'%')" +
				"and o.order_date between coalesce(:orderStartDate,o.order_date) and coalesce(:orderEndDate,o.order_date) \r\n" + 
				"AND o.active_status =COALESCE(:activeStatus,o.active_status)";
				*/
				
				
		
		/*
		 * 
		 * SELECT oot.order_owner_type_id,o.order_id,o.order_date,o.ultimate_order_owner_id,o.ref_mail_id,o.order_remarks,
o.user_ref_no,oo.owner_name,o.active_status,bu.user_name,i.item_name 
FROM mlfm_order o
INNER JOIN mlfm_order_owner oo ON oo.order_owner_id=o.order_owner_id
INNER JOIN mlfm_order_owner_type oot on oot.order_owner_type_id=oo.order_owner_type_id
INNER JOIN bg_user bu ON o.entered_by=bu.user_id
INNER JOIN mlfm_order_item oi on oi.order_id=o.order_id
LEFT OUTER JOIN mlfm_order_item_qty oiq ON oiq.order_item_id=oi.item_order_id
LEFT OUTER JOIN mlfm_item i ON i.item_id=oi.item_id
WHERE o.order_owner_id=COALESCE(@orderOwnerId,o.order_owner_id)
AND oot.order_owner_type_id=COALESCE(@orderOwnerTypeId,oot.order_owner_type_id)
AND o.ultimate_order_owner_id =COALESCE(@ultimateOwnerId,o.ultimate_order_owner_id) 
AND o.order_id =COALESCE(@orderId,o.order_id)
AND bu.user_id = COALESCE(@userId,bu.user_id)
AND i.item_id = COALESCE(@itemId,i.item_id)
and o.order_date between coalesce(@orderStartDate,o.order_date) and coalesce(@orderEndDate,o.order_date) 
AND o.active_status =COALESCE(@activeStatus,o.active_status)




         * AND UPPER(trim(o.order_remarks)) LIKE CONCAT('%',upper(TRIM(@remarks)),'%')
		 * AND UPPER(trim(o.user_ref_no)) LIKE CONCAT('%',upper(TRIM(@userRefNo)),'%')
		 */
	
		
		/*
		String  qry="SELECT oot.order_owner_type_id,o.order_id,o.order_date,o.ultimate_order_owner_id,o.ref_mail_id,o.order_remarks, \r\n" + 
				"				o.user_ref_no,oo.owner_name,o.active_status,bu.user_name\r\n" + 
				"				FROM mlfm_order o\r\n" + 
				"				INNER JOIN mlfm_order_owner oo ON oo.order_owner_id=o.order_owner_id\r\n" + 
				"				INNER JOIN mlfm_order_owner_type oot on oot.order_owner_type_id=oo.order_owner_type_id\r\n" + 
				"				INNER JOIN bg_user bu ON o.entered_by=bu.user_id\r\n" + 
				"				WHERE o.order_owner_id=COALESCE(:orderOwnerId,o.order_owner_id)\r\n" + 
				"				AND oot.order_owner_type_id=COALESCE(:orderOwnerTypeId,oot.order_owner_type_id) \r\n" + 
				"				AND o.ultimate_order_owner_id =COALESCE(:ultimateOwnerId,o.ultimate_order_owner_id)\r\n" + 
				"				AND o.order_id =COALESCE(:orderId,o.order_id)\r\n" + 
				"				AND bu.user_id = COALESCE(:userId,bu.user_id) \r\n" + 
				"				and o.order_date between coalesce(:orderStartDate,o.order_date) and coalesce(:orderEndDate,o.order_date)\r\n" + 
				"				AND o.active_status =COALESCE(:activeStatus,o.active_status)";
		*/
		
		String  qry="SELECT oot.order_owner_type_id,o.order_id,o.order_date,o.ultimate_order_owner_id,o.ref_mail_id,o.order_remarks,\r\n" + 
				"o.user_ref_no,oo.owner_name,o.active_status,bu.user_name,o.verify_status\r\n" + 
				"FROM mlfm_order o\r\n" + 
				"INNER JOIN mlfm_order_owner oo ON oo.order_owner_id=o.order_owner_id AND o.verify_status=1\r\n" + 
				"INNER JOIN mlfm_order_owner_type oot on oot.order_owner_type_id=oo.order_owner_type_id\r\n" + 
				"INNER JOIN bg_user bu ON o.entered_by=bu.user_id\r\n" + 
				"WHERE o.order_owner_id=COALESCE(:orderOwnerId,o.order_owner_id)\r\n" + 
				"AND oot.order_owner_type_id=COALESCE(:orderOwnerTypeId,oot.order_owner_type_id)\r\n" + 
				"AND o.ultimate_order_owner_id =COALESCE(:ultimateOwnerId,o.ultimate_order_owner_id) \r\n" + 
				"AND o.order_id =COALESCE(:orderId,o.order_id)\r\n" + 
				"AND bu.user_id = COALESCE(:userId,bu.user_id)\r\n" + 
				"and o.order_date between coalesce(:orderStartDate,o.order_date) and coalesce(:orderEndDate,o.order_date) \r\n" + 
				"AND o.active_status =COALESCE(:activeStatus,o.active_status)";
		
		
		
		/*
		 * SELECT oot.order_owner_type_id,o.order_id,o.order_date,o.ultimate_order_owner_id,o.ref_mail_id,o.order_remarks, 
				o.user_ref_no,oo.owner_name,o.active_status,bu.user_name,o.verify_status
				FROM mlfm_order o
				INNER JOIN mlfm_order_owner oo ON oo.order_owner_id=o.order_owner_id AND o.verify_status=1
				INNER JOIN mlfm_order_owner_type oot on oot.order_owner_type_id=oo.order_owner_type_id
				INNER JOIN bg_user bu ON o.entered_by=bu.user_id
				WHERE o.order_owner_id=COALESCE(@orderOwnerId,o.order_owner_id)
				AND oot.order_owner_type_id=COALESCE(@orderOwnerTypeId,oot.order_owner_type_id) 
				AND o.ultimate_order_owner_id =COALESCE(@ultimateOwnerId,o.ultimate_order_owner_id)
				AND o.order_id =COALESCE(@orderId,o.order_id)
				AND bu.user_id = COALESCE(@userId,bu.user_id) 
				and o.order_date between coalesce(@orderStartDate,o.order_date) and coalesce(@orderEndDate,o.order_date)
				AND o.active_status =COALESCE(@activeStatus,o.active_status)
		 * 
		 * */
		
		
		
		RowMapper<ModelDesignCustom> serviceMapper=new RowMapper<ModelDesignCustom>() {
			@Override
			public ModelDesignCustom mapRow(ResultSet rs, int rownum) throws SQLException{
				ModelDesignCustom bn=new ModelDesignCustom();
				
				bn.setOrderId(rs.getLong("order_id"));
				bn.setOrderDate(rs.getDate("order_date"));
				bn.setUserRefNo(rs.getString("user_ref_no"));
				bn.setOrderOwnerName(rs.getString("owner_name"));
				bn.setRefMailId(rs.getString("ref_mail_id"));
				bn.setRemarks(rs.getString("order_remarks"));
				bn.setUltimateOwnerName(rs.getString("ultimate_order_owner_id"));
				bn.setUserName(rs.getString("user_name"));
				bn.setActiveStatus(rs.getInt("active_status"));
				
				//bn.setItemName(rs.getString("item_name"));
				//bn.setOrderItemQtyId(rs.getLong("order_item_qty_id"));


		    return bn;
			
			}	
		};
		
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		
		         parameters.addValue("orderOwnerTypeId", orderOwnerTypeId);
		
		         parameters.addValue("orderOwnerId", orderOwnerId);
		         parameters.addValue("ultimateOwnerId", ultimateBuyerId);
		         parameters.addValue("orderId", orderId);
			     parameters.addValue("userId", userId);
			     parameters.addValue("orderStartDate", orderStartDate);
			 	 parameters.addValue("orderEndDate", orderEndDate);
			 	// parameters.addValue("itemId", itemId);
			 	 parameters.addValue("remarks", remarks);
			 	 parameters.addValue("activeStatus", active);
			 	 
				 parameters.addValue("userRefNo", userInqueryNo);
				
				 
	    
	      
	      
	      System.out.println("query for Design noo Done Search : " + qry);
	      
		// TODO Auto-generated method stub
		return  namedJdbcTemplate.query(qry,parameters,serviceMapper);
		
		
		
		
		// TODO Auto-generated method stub
		//return null;
	}
	@Override
	public List<ModelDesignCustom> getDesignListDone(Long orderOwnerTypeId,Long orderOwnerId, Long ultimateBuyerId, Long orderId, Long itemId,
			Long userId, Date designStartDate, Date designEndDate, String userInqueryNo, String remarks, int active) {
		//oot.order_owner_type_id,
		//,i.item_name,oiq.order_item_qty_id
		String  qry="SELECT d.design_id,o.order_id,o.order_date,o.ultimate_order_owner_id,o.ref_mail_id,o.order_remarks,o.user_ref_no,oo.owner_name,bu.user_name,o.active_status\r\n" + 
				"FROM mlfm_design d \r\n" + 
				"INNER JOIN mlfm_order o on o.order_id=d.order_id\r\n" +
				"INNER JOIN mlfm_order_owner_type oot on oot.order_owner_type_id=o.order_owner_type_id " +
				"INNER JOIN mlfm_order_owner oo ON oo.order_owner_id=o.order_owner_id\r\n" + 
				"INNER JOIN bg_user bu ON bu.user_id=o.entered_by\r\n" + 
				//"INNER JOIN mlfm_order_item oi on oi.order_id=o.order_id\r\n" + 
				//"INNER JOIN mlfm_order_item_qty oiq ON oiq.order_item_id=oi.item_order_id\r\n" + 
				//"INNER JOIN mlfm_item i ON i.item_id=oi.item_id\r\n" + 
				"WHERE o.order_owner_id=COALESCE(:orderOwnerId,o.order_owner_id) \r\n" +
				"AND oot.order_owner_type_id=COALESCE(:orderOwnerTypeId,oot.order_owner_type_id)" +
				"AND o.ultimate_order_owner_id = COALESCE(:ultimateOwnerId,o.ultimate_order_owner_id)\r\n" + 
				"AND o.order_id =COALESCE(:orderId,o.order_id)\r\n" + 
				"AND bu.user_id = COALESCE(:userId,bu.user_id)\r\n" + 
				"AND UPPER(trim(o.order_remarks)) LIKE CONCAT('%',upper(TRIM(:remarks)),'%')\r\n" +
				"AND UPPER(trim(o.user_ref_no)) LIKE CONCAT('%',upper(TRIM(:userInqueryNo)),'%')" +
				"and d.design_date between coalesce(:designStartDate,d.design_date) and coalesce(:designEndDate,d.design_date)\r\n" + 
				"AND o.active_status =COALESCE(:activeStatus,o.active_status)";
		
		
		
		//"AND UPPER(trim(o.order_remarks)) LIKE CONCAT('%',upper(TRIM(:remarks)),'%') " +
		//"AND UPPER(trim(o.user_ref_no)) LIKE CONCAT('%',upper(TRIM(:userInqueryNo)),'%')" +
		
		
		/*
		 * sql query
		 * SELECT oot.order_owner_type_id,d.design_id,o.order_id,o.order_date,o.ultimate_order_owner_id,o.ref_mail_id,o.order_remarks,o.user_ref_no,oo.owner_name,bu.user_name,o.active_status,i.item_name,oiq.order_item_qty_id
FROM mlfm_design d 
INNER JOIN mlfm_order o on o.order_id=d.order_id
INNER JOIN mlfm_order_owner_type oot on oot.order_owner_type_id=o.order_owner_type_id
INNER JOIN mlfm_order_owner oo ON oo.order_owner_id=o.order_owner_id
INNER JOIN bg_user bu ON bu.user_id=o.entered_by
INNER JOIN mlfm_order_item oi on oi.order_id=o.order_id
INNER JOIN mlfm_order_item_qty oiq ON oiq.order_item_id=oi.item_order_id
INNER JOIN mlfm_item i ON i.item_id=oi.item_id
WHERE o.order_owner_id=COALESCE(@orderOwnerId,o.order_owner_id) 
AND oot.order_owner_type_id=COALESCE(@orderOwnerTypeId,oot.order_owner_type_id)
AND o.ultimate_order_owner_id = COALESCE(@ultimateOwnerId,o.ultimate_order_owner_id)
AND o.order_id =COALESCE(@orderId,o.order_id)
AND bu.user_id = COALESCE(@userId,bu.user_id)
AND UPPER(trim(o.order_remarks)) LIKE CONCAT('%',upper(TRIM(:remarks)),'%')
and d.design_date between coalesce(@designStartDate,d.design_date) and coalesce(@designEndDate,d.design_date)
AND o.active_status =COALESCE(@activeStatus,o.active_status)
		 * 
		 * 
		 * */
		RowMapper<ModelDesignCustom> serviceMapper=new RowMapper<ModelDesignCustom>() {
			@Override
			public ModelDesignCustom mapRow(ResultSet rs, int rownum) throws SQLException{
				ModelDesignCustom bn=new ModelDesignCustom();
				
				bn.setOrderId(rs.getLong("order_id"));
				bn.setOrderDate(rs.getDate("order_date"));
				bn.setUserRefNo(rs.getString("user_ref_no"));
				bn.setOrderOwnerName(rs.getString("owner_name"));
				bn.setRefMailId(rs.getString("ref_mail_id"));
				bn.setRemarks(rs.getString("order_remarks"));
				bn.setUltimateOwnerName(rs.getString("ultimate_order_owner_id"));
				bn.setUserName(rs.getString("user_name"));
				bn.setActiveStatus(rs.getInt("active_status"));
				bn.setDesignId(rs.getLong("design_id"));
				
				//bn.setItemName(rs.getString("item_name"));
				//bn.setOrderItemQtyId(rs.getLong("order_item_qty_id"));


		    return bn;
			
			}	
		};
		
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		
		parameters.addValue("orderOwnerTypeId", orderOwnerTypeId);
		
		parameters.addValue("orderOwnerId", orderOwnerId);
        parameters.addValue("ultimateOwnerId", ultimateBuyerId);
        parameters.addValue("orderId", orderId);
	     parameters.addValue("userId", userId);
	     parameters.addValue("designStartDate", designStartDate);
	 	 parameters.addValue("designEndDate", designEndDate);
	 	//parameters.addValue("itemId", itemId);
		 parameters.addValue("userInqueryNo", userInqueryNo);
		 parameters.addValue("remarks", remarks);
		 parameters.addValue("activeStatus", active);
	      
	      
	      System.out.println("query for Design Done Search : " + qry);
	      
		// TODO Auto-generated method stub
		return  namedJdbcTemplate.query(qry,parameters,serviceMapper);
		
		
	//	return null;
	}
	@Override
	public List<ModelDesignCustom> designSaveModal(ModelDesign modelDesign) {
		
		designRepository.save(modelDesign);
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<ModelDesignCustom> getDesignListByDesignId(Long designId) {
		
		
		String  qry="SELECT d.design_id,d.design_date,d.design_name,d.designer_id,d.remarks,d.active_status,d.order_id,\r\n" + 
				"d.factory_proposed_delivery_date,d.machine_type,d.user_design_no,d.NO_OF_STITCHES,d.DTM,d.lace_type_id,\r\n" + 
				"d.FABRIC_TYPE_ID,bu.user_name \r\n" + 
				"FROM mlfm_design d\r\n" + 
				"LEFT OUTER JOIN bg_user bu on bu.user_id=d.designer_id\r\n" + 
				"WHERE d.design_id=COALESCE(:designId,d.design_id)";
		/*
		 * sql query
		 * 
		 * SELECT d.design_id,d.design_date,d.design_name,d.designer_id,d.remarks,d.active_status,d.order_id,
d.factory_proposed_delivery_date,d.machine_type,d.user_design_no,d.NO_OF_STITCHES,d.DTM,d.lace_type_id,
d.FABRIC_TYPE_ID,bu.user_name 
FROM mlfm_design d
LEFT OUTER JOIN bg_user bu on bu.user_id=d.designer_id
WHERE d.design_id=COALESCE(@designId,d.design_id)
		 * 
		 * */
		RowMapper<ModelDesignCustom> serviceMapper=new RowMapper<ModelDesignCustom>() {
			@Override
			public ModelDesignCustom mapRow(ResultSet rs, int rownum) throws SQLException{
				ModelDesignCustom bn=new ModelDesignCustom();
				
				bn.setDesignId(rs.getLong("design_id"));
				bn.setOrderId(rs.getLong("order_id"));
				bn.setDesignName(rs.getString("design_name"));
				bn.setDesignerId(rs.getLong("designer_id"));
				bn.setDesignDate(rs.getDate("design_date"));
				bn.setRemarks(rs.getString("remarks"));
                bn.setActiveStatus(rs.getInt("active_status"));
                bn.setFactPropDeliveryDate(rs.getDate("factory_proposed_delivery_date"));
                bn.setMachineTypeId(rs.getLong("machine_type"));
                bn.setUserDesignNo(rs.getString("user_design_no"));
                
                bn.setFabricTypeId(rs.getLong("FABRIC_TYPE_ID"));
                bn.setdTM(rs.getString("DTM"));
                bn.setLaceTypeId(rs.getInt("lace_type_id"));
                bn.setNoOfStitches(rs.getInt("NO_OF_STITCHES"));
                
                bn.setDesignerName(rs.getString("user_name"));
				


		    return bn;
			
			}	
		};
		
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		
		  
		parameters.addValue("designId", designId);
	      
	      System.out.println("query for Design Done Search : " + qry);
	      
		// TODO Auto-generated method stub
		return  namedJdbcTemplate.query(qry,parameters,serviceMapper);
		
		
		// TODO Auto-generated method stub
	//	return null;
	}
	@Override
	public Optional<ModelDesign> findDesignById(Long designId) {
		
		
		
		// TODO Auto-generated method stub
		return designRepository.findById(designId);
	}
	
	
	//created by sohel rana on 04/04/2019
	
	@Override
	public List<ModelDesign> getDesignListForInquiryMs(Long inquiryId) {
		// TODO Auto-generated method stub
		return designRepository.findDesignFromInquery(inquiryId);
	}
	@Override
	public List<ModelDesign> getSpecNameFromDesign(Long designId) {
		// TODO Auto-generated method stub
		return designRepository.findDesign(designId);
	}
	
}
