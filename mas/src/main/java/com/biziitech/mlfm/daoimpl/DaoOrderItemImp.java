package com.biziitech.mlfm.daoimpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.biziitech.mlfm.custom.model.ModelInquiryList;
import com.biziitech.mlfm.custom.model.ModelMachineScheduleData;
import com.biziitech.mlfm.dao.DaoOrderItem;
import com.biziitech.mlfm.model.ModelDeliveryChallan;
import com.biziitech.mlfm.model.ModelItem;
import com.biziitech.mlfm.model.ModelOrder;
import com.biziitech.mlfm.model.ModelOrderItem;
import com.biziitech.mlfm.repository.OrderItemRepository;
@Service
public class DaoOrderItemImp implements DaoOrderItem {
	
	/*created by sohel on 16-03-2019
	  */
	
	
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	@Autowired
	private JdbcTemplate jdbc;
	

	@Autowired
    private DataSource dataSource;
	
	
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	private Long orderId;
	private Long itemId;
	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	private List<ModelOrderItem> itemListForOrderId;
	public List<ModelOrderItem> getItemListForOrderId() {
		return itemListForOrderId;
	}

	public void setItemListForOrderId(List<ModelOrderItem> itemListForOrderId) {
		this.itemListForOrderId = itemListForOrderId;
	}

	public Long getOrderId() {
		return orderId;
	}
	public Optional<ModelOrderItem> getItemById(Long id){
		
		
		Optional<ModelOrderItem> item= orderItemRepository.findById(id);

		if(item.get().getActiveStatus()==1)
		 {
			 item.get().setActive(true);
			 
		 }
		 
		 else
		 {	 
			 item.get().setActive(false);
		 }
		item.get().setOrderDateEdit(format.format(item.get().getOrderDate()));
		setItemId(item.get().getItemOrderId());
		setOrderId(item.get().getModelOrder().getOrderId());
		return item;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	@Autowired
	private OrderItemRepository orderItemRepository;

	@Override
	public void saveOrderItem(ModelOrderItem item) {
		//ModelOrder modelOrder=new ModelOrder();
		//modelOrder.setOrderId(getOrderId());
		//item.setModelOrder(modelOrder);
		//System.out.println("ddd");
		 
		
		 orderItemRepository.save(item);
		
	}

	@Override
	public List<ModelOrderItem> getOrderItem() {
		List<ModelOrderItem>  tableDataFinal= new ArrayList<>();
		List<ModelOrderItem> tableData=orderItemRepository.findAll();
		for(ModelOrderItem item:tableData) {
			if(item.getActiveStatus()==1)
			 { item.setsActive("Yes");
			  item.setActive(true);
			 }
			 
			 else
			 {	 item.setsActive("NO");
			     item.setActive(false);
			 }
			 
			
			tableDataFinal.add(item);
		 }
		return tableDataFinal;
		
	}

	@Override
	public List<ModelOrderItem> getOrderItemById(Long id) {
		List<ModelOrderItem>  itemList= new ArrayList<>();
		List<ModelOrderItem> resultList= orderItemRepository.findItemDetails(id);
		for(ModelOrderItem item:resultList) {
			if(item.getActiveStatus()==1)
			 { item.setsActive("Yes");
			  item.setActive(true);
			 }
			 
			 else
			 {	 item.setsActive("NO");
			     item.setActive(false);
			 }
			 
			
			 itemList.add(item);
			 System.out.println(itemList.size());
			 if(itemList.isEmpty())
				 setItemListForOrderId(null);
			 else
				 setItemListForOrderId(itemList);
		 }
		return itemList;
	}
	
	
	
	/* created by sohel on 16-03-2019 
	*/

	@Override
	public List<ModelInquiryList> getOrderItemDetailsById(Long id) {
		String qry="SELECT a.active_status,a.remarks,b.item_id,a.item_order_id,a.active_status,a.remarks,a.order_id,a.order_id,b.item_name,c.item_type_name FROM mlfm_order_item a INNER JOIN mlfm_item b ON a.item_id= b.item_id INNER JOIN mlfm_item_type c ON b.item_type_id=c.item_type_id WHERE a.item_order_id=coalesce(:id,a.item_order_id)";
		
		
		RowMapper<ModelInquiryList> serviceMapper=new RowMapper<ModelInquiryList>() {
			@Override
			public ModelInquiryList mapRow(ResultSet rs, int rownum) throws SQLException{
				ModelInquiryList bn=new ModelInquiryList();
		        
				bn.setItemOrderId(rs.getLong("item_order_id"));
				bn.setOrderId(rs.getLong("order_id"));
				bn.setItemId(rs.getLong("item_id"));
				bn.setItemName(rs.getString("item_name"));
				bn.setItemTypeName(rs.getString("item_type_name"));
				bn.setRemarks(rs.getString("remarks"));
				bn.setActiveStatus(rs.getInt("active_status"));
				
				
			
			return bn;
			
			}	
		};
		

		MapSqlParameterSource parameters = new MapSqlParameterSource();
	      
	      parameters.addValue("id", id);
	     
	      
  

		return  jdbcTemplate.query(qry,parameters,serviceMapper);
	}

	@Override
	public List<ModelOrderItem> getOrderItemDetails(Long id) {
		// TODO Auto-generated method stub
		return orderItemRepository.findItemDetails(id);
	}
	
	
	public void updateOrderItem(ModelOrderItem orderItem){
        try {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.update("update mlfm_order_item set order_date=?,item_id=?,active_status=?, remarks=?, updated_by=?, update_timestamp=? where item_order_id=?",orderItem.getOrderDate(),orderItem.getListModelItem().getItemId(),orderItem.getActiveStatus(),orderItem.getRemarks(),orderItem.getModelUpdatedBy().getUserId(),orderItem.getUpdateTimestap(),orderItem.getItemOrderId());
        	
        
        System.out.println("update success");
       }
        
        
        catch(Exception e) {
        	e.printStackTrace();
}

	}

	@Override
	public List<ModelOrderItem> getAuditDetails(Long id) {
		// TODO Auto-generated method stub
		return orderItemRepository.findOrderItem(id);
	}

	@Override
	public List<ModelOrderItem> checkOrderItem(Long orderId, Long itemId) {
		
		return orderItemRepository.checkOrderItem(orderId, itemId);
	}
	
	
}
