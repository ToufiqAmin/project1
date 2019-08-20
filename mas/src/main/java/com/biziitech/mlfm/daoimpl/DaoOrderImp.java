package com.biziitech.mlfm.daoimpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.sql.DataSource;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import com.biziitech.mlfm.bg.model.ModelPhone;
import com.biziitech.mlfm.custom.model.ModelInquiryList;
import com.biziitech.mlfm.custom.model.ModelPIChdOrder;
import com.biziitech.mlfm.dao.DaoOrder;
import com.biziitech.mlfm.model.ModelOrder;
import com.biziitech.mlfm.model.ModelOrderOwner;
import com.biziitech.mlfm.repository.OrderRepository;

@Service
public class DaoOrderImp implements DaoOrder{
	@Autowired
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;
    
    
    @Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	
	@Autowired
	private OrderRepository orderRepository;

	private
	List<ModelOrder>  ownerList= new ArrayList<>();
	
	private
	List<ModelOrder>  ownerDataList= new ArrayList<>();

	
	

	 public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
          }
	 
	public List<ModelOrder> getOwnerList() {
		return ownerList;
	}
	


	public void setOwnerList(List<ModelOrder> ownerList) {
		this.ownerList = ownerList;
	}
	
	
	public List<ModelOrder> getOwnerDataList() {
		return ownerDataList;
	}
	


	public void setOwnerDataList(List<ModelOrder> ownerDataList) {
		this.ownerDataList = ownerDataList;
	}

	@Override
	public void saveOrder(ModelOrder order) {
		if(order.isActive())
			order.setActiveStatus(1);
		else
			order.setActiveStatus(0);
		
		orderRepository.save(order);
	}
	
	@Override
	public List<ModelOrder> getOrderData() {

		List<ModelOrder>  tableDataFinal= new ArrayList<>();
		List<ModelOrder> tableData=orderRepository.findAll();
		for(ModelOrder order:tableData) {
			if(order.getActiveStatus()==1)
			 { order.setsActive("Yes");
			  order.setActive(true);
			 }
			 
			 else
			 {	 order.setsActive("NO");
			     order.setActive(false);
			 }
			 
			
			tableDataFinal.add(order);
		 }
		return tableDataFinal;
	}
	
	@Override
	//@ResponseBody
	 public List<ModelOrder> getOrderData(String initialBuyer, 
			 String ultimateBuyer, String userName, Date stDate, Date endDate) {
		System.out.println("dao parameter data " + userName);
		List<ModelOrder> resultList = orderRepository.findOrderOnCriteria(initialBuyer,ultimateBuyer,userName, stDate, endDate);
		//List<ModelOrder> resultList = orderRepository.findOrderOnCriteria(stDate, endDate);
		
		//List<ModelOrder> resultList = orderRepository.findAll();
		System.out.println("dao method");
		
		System.out.println("size " + resultList.size());
		
		return resultList;
	}
	

	
	
	@Override
	public Optional<ModelOrder> getOrderById(Long orderId) {
		// TODO Auto-generated method stub
		Optional<ModelOrder> order=orderRepository.findById(orderId);
		
		if(order.get().getActiveStatus()==1)
		 {
			 order.get().setActive(true);
			 
		 }
		 
		 else
		 {	 
			 order.get().setActive(false);
		 }
			order.get().setDateCheck(format.format( order.get().getOrderDate())  );
			order.get().setDateMailReceive(format.format(order.get().getMailReceiveDate()));
			order.get().setDateMailSent(format.format(order.get().getMailSentDate()));
			order.get().setDateBuyerExpectedDate(format.format(order.get().getExpectedDeliveryDate()));
		return order;
	}
	public Long getOrderOwnerTypeId(Long orderId) {
		ModelOrder order=orderRepository.findOwnerTypeId(orderId);
		return order.getModelOrderOwnerType().getOrderOwnerTypeId();
	}
	
	

	@Override
	public List<Map<String, Object>> report() {
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		for(ModelOrder order:orderRepository.findAll()) {
			Map<String,Object> item= new HashMap<String,Object>();
			item.put("id", order.getOrderId());
			item.put("type", order.getModelOrderOwnerType().getOrderOwnerTypeName());
			item.put("owner", order.getModelOrderOwner().getOwnerName());
			item.put("ultimate", order.getUltimateOwner().getOwnerName());
			item.put("contact", order.getModelOrderOwner().getContactPersonName());
			item.put("enteredBy", order.getEnteredBy().getUserName());
			item.put("entryTime", order.getEntryTimestamp());
			result.add(item);
		}
		// TODO Auto-generated method stub
		return result;
	}
/*
	@Override
	public List<ModelOrder> getAllOwner(String initial_Buyer,String ultimate_buyer,Long inquiry_Id,String mail_Id,Long user_inquery_no,String remarks,String user,Date inq_st,Date inq_ed,Date mail_st,Date mail_ed) {
		List<ModelOrder>  ownerList= new ArrayList<>();
		List<ModelOrder> resultList= orderRepository.findOwnerDetails(initial_Buyer,ultimate_buyer, inquiry_Id,mail_Id,user_inquery_no, remarks,user,inq_st,inq_ed,mail_st,mail_ed);
		for(ModelOrder owner:resultList) {
			if(owner.getActiveStatus()==1)
			 { owner.setsActive("Yes");
			  owner.setActive(true);
			 }
			 
			 else
			 {	 owner.setsActive("NO");
			     owner.setActive(false);
			 }
			 
			
			 ownerList.add(owner);
		 }
		setOwnerList(ownerList);
		return ownerList;
		
	}

*/
	
	@Override
	public List<ModelOrder> getAllOwner(String initial_Buyer,String ultimate_buyer,Long inquiry_Id,String mail_Id,Long user_inquery_no,String remarks,String user,Date inq_st,Date inq_ed) {
		List<ModelOrder>  ownerList= new ArrayList<>();
		List<ModelOrder> resultList= orderRepository.findOwnerDetails(initial_Buyer,ultimate_buyer, inquiry_Id,mail_Id,user_inquery_no, remarks,user,inq_st,inq_ed);
		for(ModelOrder owner:resultList) {
			if(owner.getActiveStatus()==1)
			 { owner.setsActive("Yes");
			  owner.setActive(true);
			 }
			 
			 else
			 {	 owner.setsActive("NO");
			     owner.setActive(false);
			 }
			 
			
			 ownerList.add(owner);
		 }
		setOwnerList(ownerList);
		return ownerList;
		
	}
	
	
	
	@Override
	public List<ModelOrder> getAllOwnerActive(Long initial_Buyer,Long ultimate_buyer,Long inquiry_Id,String mail_Id,Long user_inquery_no,String remarks,String user,Date inq_st,Date inq_ed,int active_status) {
		
		
		List<ModelOrder>  ownerList= new ArrayList<>();
		List<ModelOrder> resultList= orderRepository.findOwnerDetailsActive(initial_Buyer,ultimate_buyer, inquiry_Id,mail_Id,user_inquery_no, remarks,user,inq_st,inq_ed, active_status);
		for(ModelOrder owner:resultList) {
			if(owner.getActiveStatus()==1)
			 { owner.setsActive("Yes");
			  owner.setActive(true);
			 }
			 
			 else
			 {	 owner.setsActive("NO");
			     owner.setActive(false);
			 }
			 
			
			 ownerList.add(owner);
		 }
		setOwnerList(ownerList);
		return ownerList;
		
	}


	public void updateVerifyOrderJdbc(ModelOrder order){
       /* try {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.update("update MLFM_ORDER SET verify_remarks=? , verify_status=? where order_id=?",order.getVerifyRemarks(),order.getVerifyStatus(),order.getOrderId() );
        }
        
        catch(Exception e) {
        	e.printStackTrace();
}
*/
		
		
		try {
	        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
	        jdbcTemplate.update("update MLFM_ORDER SET verify_remarks=? , verify_status=? , verified_by=?, verify_timestamp=?, updated_by=?, update_timestamp=? where order_id=?",order.getVerifyRemarks(),order.getVerifyStatus(),order.getModelVerified().getUserId(),order.getVerifyTimestamp(),order.getUpdatedBy().getUserId(),order.getUpdateTimestap(),order.getOrderId() );
	        }
	        
	        catch(Exception e) {
	        	e.printStackTrace();
	}
	
		
		
	}
	
	
	@Override
	public List<ModelInquiryList> getOrderDeatailsData(Long ownerType,Long owner,Long ultimateOwner, Date startDate,
			Date endDate,Long user,int active) {
				
		String qry="SELECT f.cluster_name,a.verify_status,a.verified_by, a.buyer_expected_delivery_date, d.user_name,a.order_id,a.order_date,a.active_status,a.buyer_expected_price,b.owner_name,b.owner_email,a.ultimate_order_owner_id, " +
		" (select owner_name from mlfm_order_owner z where z.order_owner_id=a.ultimate_order_owner_id) ultimate_owner_name," +
				" g.item_id,i.item_name FROM mlfm_order a INNER JOIN mlfm_order_owner b ON a.order_owner_id=b.order_owner_id inner join mlfm_order_owner_type c on b.order_owner_type_id=c.order_owner_type_id inner join bg_user d on a.entered_by=d.user_id " +
				" left outer join mlfm_cluster f on a.cluster_id=f.cluster_id" +
		        " left outer join mlfm_order_item g on a.order_id=g.order_id " + 
				" left outer join mlfm_order_item_qty h on g.item_order_id=h.order_item_id " +
		        " left outer join mlfm_item i on g.item_id=i.item_id " + 
				" WHERE  c.order_owner_type_id=coalesce(:ownerType,c.order_owner_type_id)\r\n" + 
				" and b.order_owner_id=coalesce(:owner, b.order_owner_id) and b.order_owner_id=coalesce(:ultimateOwner, b.order_owner_id) and d.user_id=coalesce(:user, d.user_id) and a.active_status=coalesce(:active, a.active_status)"+
				" and a.order_date BETWEEN coalesce(:start_date,a.order_date) AND coalesce(:end_date,a.order_date) order by b.owner_name,a.order_date";
		
	
		RowMapper<ModelInquiryList> serviceMapper=new RowMapper<ModelInquiryList>() {
			public ModelInquiryList mapRow(ResultSet rs, int rownum) throws SQLException{
				
				ModelInquiryList bn=new ModelInquiryList();
				
		        bn.setOrderId(rs.getLong("order_id"));
		        bn.setInitialBuyer(rs.getString("owner_name"));
		        bn.setBuyerMail(rs.getString("owner_email"));
		        bn.setUserName(rs.getString("user_name"));
		        bn.setExpectedPrice(rs.getDouble("buyer_expected_price"));
		        bn.setOrderDate(rs.getDate("order_date"));
		      //  bn.setOrderStatus(rs.getInt("order_status"));
		       // bn.setCancelReason(rs.getString("cancel_reason"));
		       // bn.setItemName(rs.getString("item_name"));
		        bn.setBuyerExpectedDate(rs.getDate("buyer_expected_delivery_date"));
		       bn.setVerifyStatus(rs.getInt("verify_status"));
		       bn.setClusterName(rs.getString("cluster_name"));
		       
		       bn.setItemId(rs.getLong("item_id"));
		       bn.setItemName(rs.getString("item_name"));
		       bn.setUltimateBuyer(rs.getString("ultimate_owner_name"));
			    
		        
		        return bn;
			}
		};
		
		  MapSqlParameterSource parameters = new MapSqlParameterSource();
		  
		  parameters.addValue("ownerType", ownerType);
	      parameters.addValue("owner", owner);
	      parameters.addValue("user", user);
	      parameters.addValue("active", active);
	      parameters.addValue("ultimateOwner", ultimateOwner);
	      parameters.addValue("start_date", startDate);
	      parameters.addValue("end_date", endDate);
	     
	      
	      
	      return namedParameterJdbcTemplate.query(qry,parameters,serviceMapper);
	      
	}

	@Override
	public void saveNewOrder(ModelOrder order) {
		
		orderRepository.save(order);
		
	}

	@Override
	public List<ModelInquiryList> getNewOrderData(Long id) {

		String qry="SELECT a.order_remarks,a.user_ref_no, f.cluster_name, a.cluster_id, a.verify_status,a.verified_by, a.buyer_expected_delivery_date, d.user_name,a.order_id,a.order_date,a.active_status,a.buyer_expected_price,b.owner_name,b.owner_email,a.ultimate_order_owner_id FROM mlfm_order a INNER JOIN mlfm_order_owner b ON a.order_owner_id=b.order_owner_id inner join mlfm_order_owner_type c on b.order_owner_type_id=c.order_owner_type_id inner join bg_user d on a.entered_by=d.user_id left outer join mlfm_cluster f on a.cluster_id=f.cluster_id"
				+" WHERE  a.order_id=coalesce(:id,a.order_id)";
				
	
		RowMapper<ModelInquiryList> serviceMapper=new RowMapper<ModelInquiryList>() {
			public ModelInquiryList mapRow(ResultSet rs, int rownum) throws SQLException{
				
				
				
				ModelInquiryList bn=new ModelInquiryList();
						
		        bn.setOrderId(rs.getLong("order_id"));
		        bn.setInitialBuyer(rs.getString("owner_name"));
		        bn.setBuyerMail(rs.getString("owner_email"));
		        bn.setUserName(rs.getString("user_name"));
		        bn.setExpectedPrice(rs.getDouble("buyer_expected_price"));
		        bn.setOrderDate(rs.getDate("order_date"));
		      //  bn.setOrderStatus(rs.getInt("order_status"));
		       // bn.setCancelReason(rs.getString("cancel_reason"));
		       // bn.setItemName(rs.getString("item_name"));
		        bn.setBuyerExpectedDate(rs.getDate("buyer_expected_delivery_date"));
		       bn.setVerifyStatus(rs.getInt("verify_status"));
		       //bn.setClusterName(rs.getString("cluster_name"));
		     
		    	   bn.setClusterName(rs.getString("cluster_name"));
		    	   bn.setRefNo(rs.getString("user_ref_no"));
		    	   bn.setRemarks(rs.getString("order_remarks"));
		    	   bn.setActiveStatus(rs.getInt("active_status"));
		      
			    
		        
		        return bn;
			}
		};
		
		  MapSqlParameterSource parameters = new MapSqlParameterSource();
		  
		  parameters.addValue("id", id);
	     
	     
	      
	      
	      return namedParameterJdbcTemplate.query(qry,parameters,serviceMapper);
	      
	}

	@Override
	public List<ModelOrder> getAllInquiryData(Long typeId, Long owner, Long ultimateOwner, Long inquiryId,
			Long user, Date startDate, Date endDate, int status) {
		
		return orderRepository.findAllOwnerDetailsByCraiterias(typeId, owner, ultimateOwner, inquiryId, user, startDate, endDate,status);
	}

	@Override
	public List<ModelOrder> newInquiry(Long id) {
		
		return orderRepository.findNewInquiry(id);
	}
	
	
	
	public void updateInquiry(ModelOrder order){
        try {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
       // jdbcTemplate.update("update mlfm_order set order_owner_type_id=?,order_owner_id=?,ultimate_order_owner_id=?, entered_by=?,cluster_id=?,ref_id=?,order_date=?,mail_sent_date=?,mail_receive_date=?,aprx_order_qty=?,buyer_expected_delivery_date=?,active_status=?,order_remarks=?, aprx_order_qty_uom=?,updated_by=?, update_timestamp=? where order_id=?",order.getModelOrderOwnerType().getOrderOwnerTypeId(),order.getModelOrderOwner().getOrderOwnerId(),order.getUltimateOwner().getOrderOwnerId(),order.getEnteredBy().getUserId(),order.getModelCluster().getClusterId(),order.getRefId(),order.getOrderDate(),order.getMailSentDate(),order.getMailReceiveDate(),order.getAprxOrderQty(),order.getExpectedDeliveryDate(),order.getActiveStatus(),order.getOrderRemarks(),order.getModelUom().getUomId(),order.getUpdatedBy().getUserId(),order.getUpdateTimestap(),order.getOrderId());
        	
        
        jdbcTemplate.update("update mlfm_order set order_owner_type_id=?,order_owner_id=?, ultimate_order_owner_id=?, entered_by=?, cluster_id=?, contact_person_name=?, ref_id=?, order_date=?, mail_sent_date=?, mail_receive_date=?, aprx_order_qty=?, aprx_order_qty_uom=?, buyer_expected_delivery_date=?, order_remarks=?, active_status=?,updated_by=?, update_timestamp=? where order_id=?",order.getModelOrderOwnerType().getOrderOwnerTypeId(),order.getModelOrderOwner().getOrderOwnerId(),order.getUltimateOwner().getOrderOwnerId(),order.getEnteredBy().getUserId(),order.getModelCluster().getClusterId(),order.getContactPersonName(),order.getRefId(),order.getOrderDate(),order.getMailSentDate(),order.getMailReceiveDate(),order.getAprxOrderQty(),order.getModelUom().getUomId(),order.getExpectedDeliveryDate(),order.getOrderRemarks(),order.getActiveStatus(),order.getUpdatedBy().getUserId(),order.getUpdateTimestap(),order.getOrderId());
        
        System.out.println("update success");
       }
        
        
        catch(Exception e) {
        	e.printStackTrace();
}

	}

	@Override
	public List<ModelOrder> checkInquiry(Long ownerId, Date orderDate) {
		// TODO Auto-generated method stub
		return orderRepository.checkInquiryData(ownerId, orderDate);
	}

	@Override
	public List<ModelOrder> getAllOwnerBySearch(Long typeId, Long owner, Long ultimateOwner, Long inquiryId, Long user,
			Date startDate, Date endDate, int status) {
		
		return orderRepository.findNotVerifiedInquiry(typeId, owner, ultimateOwner, inquiryId, user, startDate, endDate, status);
	}

	@Override
	public List<ModelOrder> getAllVerifiedInquiryData(Long typeId, Long owner, Long ultimateOwner, Long inquiryId,
			Long user, Date startDate, Date endDate, int status,Date doneStartDate, Date doneEndDate,Long verifiedBy) {
		
		return orderRepository.findVerifiedInquiry(typeId, owner, ultimateOwner, inquiryId, user, startDate, endDate, status, doneStartDate, doneEndDate,verifiedBy);
	}
	
	
	
	  
	}
	
	

