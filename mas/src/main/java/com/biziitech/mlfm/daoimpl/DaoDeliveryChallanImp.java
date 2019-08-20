package com.biziitech.mlfm.daoimpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.biziitech.mlfm.custom.model.ModelDeliverChallanCustom;
import com.biziitech.mlfm.custom.model.ModelWashingCustom;
import com.biziitech.mlfm.dao.DaoDeliveryChallan;
import com.biziitech.mlfm.model.ModelDeliveryChallan;
import com.biziitech.mlfm.model.ModelGatePassMst;
import com.biziitech.mlfm.repository.DeliveryChallanRepository;


@Service
public class DaoDeliveryChallanImp implements DaoDeliveryChallan {
  
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	@Autowired
	private JdbcTemplate jdbc;
	
	@Autowired
    private DataSource dataSource;
	
	@Autowired
	private DeliveryChallanRepository deliveryChallanRepository;
	
	@Override
	public List<ModelWashingCustom> getInquirySearchData(Long ownerType, Long owner,Long orderId) {
		String qry="SELECT a.PRODUCTION_ID,PRODUCTION_DATE,a.ORDER_ID,c.owner_name,a.PRODUCTION_QTY,b.order_owner_id,f.item_name FROM mlfm_production a INNER JOIN mlfm_order b on a.ORDER_ID=b.order_id INNER JOIN mlfm_order_owner c ON b.order_owner_id=c.order_owner_id INNER JOIN mlfm_order_owner_type g ON c.order_owner_type_id=g.order_owner_type_id INNER JOIN mlfm_order_item d ON a.ORDER_ID=d.order_id INNER JOIN mlfm_order_item_qty e ON d.item_order_id=e.order_item_id INNER JOIN mlfm_item f ON f.item_id=d.item_id"+
				" WHERE  g.order_owner_type_id=coalesce(:order_owner_type_id,g.order_owner_type_id)" + 
				" and c.order_owner_id=coalesce(:order_owner_id, c.order_owner_id) and b.order_id=coalesce(:order_id, b.order_id)";
				
		
		RowMapper<ModelWashingCustom> serviceMapper=new RowMapper<ModelWashingCustom>() {
			@Override
			public ModelWashingCustom mapRow(ResultSet rs, int rownum) throws SQLException{
				ModelWashingCustom bn=new ModelWashingCustom();
		        
				bn.setInquiryId(rs.getLong("order_id"));
						
				bn.setBuyerId(rs.getLong("order_owner_id"));
				bn.setBuyerName(rs.getString("owner_name"));
				bn.setItemName(rs.getString("item_name"));
				bn.setProductionId(rs.getLong("production_id"));
				bn.setProductionQty(rs.getDouble("production_qty"));;
					
			return bn;
			
			}	
		};
		

		MapSqlParameterSource parameters = new MapSqlParameterSource();
	      
	      parameters.addValue("order_owner_type_id", ownerType);
	      parameters.addValue("order_owner_id", owner);
	      parameters.addValue("order_id", orderId);
	 
	   
		return  jdbcTemplate.query(qry,parameters,serviceMapper);
	}

	@Override
	public List<ModelWashingCustom> getWorkOrderSearchData(Long ownerType, Long owner, Long workOrderId,Date startDate,Date endDate,Long pOId) {
		
		/*String qry="SELECT j.PO_ID, a.PRODUCTION_ID,a.PRODUCTION_QTY,c.WO_MST_ID,d.order_owner_id,d.owner_name,g.item_name,g.item_id,h.uom_id,h.uom_name FROM mlfm_production a INNER JOIN mlfm_wo_chd b ON a.WO_CHD_ID=b.WO_CHD_ID INNER JOIN mlfm_wo_mst c ON b.WO_MST_ID=c.WO_MST_ID INNER JOIN mlfm_order_owner d ON c.ORDER_OWNER_ID=d.order_owner_id INNER JOIN mlfm_order_item_qty e ON b.ORDER_ITEM_QTY_ID=e.order_item_qty_id INNER JOIN mlfm_order_item f ON e.order_item_id=f.item_order_id INNER JOIN mlfm_item g ON f.item_id=g.item_id INNER JOIN mlfm_uom h ON h.uom_id=g.uom_id INNER JOIN mlfm_order_owner_type i ON d.order_owner_type_id=i.order_owner_type_id INNER JOIN mlfm_po j ON j.PO_ID=a.PO_ID"+
				" WHERE  i.order_owner_type_id=coalesce(:order_owner_type_id,i.order_owner_type_id)\r\n" + 
				" and d.order_owner_id=coalesce(:order_owner_id, d.order_owner_id) and c.wo_mst_id=coalesce(:wo_mst_id, c.wo_mst_id) "+
				" and a.production_date BETWEEN coalesce(:start_date,a.production_date) AND coalesce(:end_date,a.production_date)";
			*/	
		
		String qry="SELECT j.PO_MST_ID,k.PO_QTY,k.PO_ID, a.PRODUCTION_ID,a.PRODUCTION_QTY,c.WO_MST_ID,d.order_owner_id,d.owner_name,g.item_name,g.item_id,h.uom_id,h.uom_name FROM mlfm_production a INNER JOIN mlfm_wo_chd b ON a.WO_CHD_ID=b.WO_CHD_ID INNER JOIN mlfm_wo_mst c ON b.WO_MST_ID=c.WO_MST_ID INNER JOIN mlfm_order_owner d ON c.ORDER_OWNER_ID=d.order_owner_id INNER JOIN mlfm_order_item_qty e ON b.ORDER_ITEM_QTY_ID=e.order_item_qty_id INNER JOIN mlfm_order_item f ON e.order_item_id=f.item_order_id INNER JOIN mlfm_item g ON f.item_id=g.item_id INNER JOIN mlfm_uom h ON h.uom_id=g.uom_id INNER JOIN mlfm_order_owner_type i ON d.order_owner_type_id=i.order_owner_type_id INNER JOIN mlfm_po_mst j ON a.PO_MST_ID=j.PO_MST_ID INNER JOIN mlfm_po_chd l ON j.PO_MST_ID=l.PO_MST_ID INNER JOIN mlfm_po k ON l.PRODUCTION_PLAN_CHD_ID=k.PRODUCTION_PLAN_CHD_ID"+
				" WHERE  i.order_owner_type_id=coalesce(:order_owner_type_id,i.order_owner_type_id)" + 
				" and d.order_owner_id=coalesce(:order_owner_id, d.order_owner_id) and c.wo_mst_id=coalesce(:wo_mst_id, c.wo_mst_id) "+
				" and a.production_date BETWEEN coalesce(:start_date,a.production_date) AND coalesce(:end_date,a.production_date)" +
				" and k.po_id=coalesce(:po_id,k.po_id)";
		
		RowMapper<ModelWashingCustom> serviceMapper=new RowMapper<ModelWashingCustom>() {
			@Override
			public ModelWashingCustom mapRow(ResultSet rs, int rownum) throws SQLException{
				ModelWashingCustom bn=new ModelWashingCustom();
		        
				bn.setwOId(rs.getLong("wo_mst_id"));
				bn.setProductionId(rs.getLong("production_id"));
				bn.setProductionQty(rs.getDouble("production_qty"));
				bn.setItemId(rs.getLong("item_id"));
				bn.setItemName(rs.getString("item_name"));
				bn.setUomId(rs.getLong("uom_id"));
				bn.setUomName(rs.getString("uom_name"));
				bn.setBuyerName(rs.getString("owner_name"));
				bn.setBuyerId(rs.getLong("order_owner_id"));
				bn.setGatePassChdId(rs.getLong("po_mst_id"));
				bn.setFinishedQty(rs.getDouble("po_qty"));
					
			return bn;
			
			}	
		};
		

		MapSqlParameterSource parameters = new MapSqlParameterSource();
	      
	      parameters.addValue("order_owner_type_id", ownerType);
	      parameters.addValue("order_owner_id", owner);
	      parameters.addValue("wo_mst_id", workOrderId);
	      parameters.addValue("start_date", startDate);
	      parameters.addValue("end_date", endDate);
	      parameters.addValue("po_id", pOId);
	 
	   
		return  jdbcTemplate.query(qry,parameters,serviceMapper);
	}

	@Override
	public void saveDeliveryChallan(ModelDeliveryChallan deliveryChallan) {
		
	   
		 deliveryChallanRepository.save(deliveryChallan);
		
	}

	@Override
	public List<ModelWashingCustom> getWorkOrderSearchDataById(Long id) {
		String qry="SELECT k.PO_MST_ID, a.PRODUCTION_ID,a.PRODUCTION_QTY,c.WO_MST_ID,d.order_owner_id,d.owner_name,g.item_name,g.item_id,h.uom_id,h.uom_name FROM mlfm_production a INNER JOIN mlfm_wo_chd b ON a.WO_CHD_ID=b.WO_CHD_ID INNER JOIN mlfm_wo_mst c ON b.WO_MST_ID=c.WO_MST_ID INNER JOIN mlfm_order_owner d ON c.ORDER_OWNER_ID=d.order_owner_id INNER JOIN mlfm_order_item_qty e ON b.ORDER_ITEM_QTY_ID=e.order_item_qty_id INNER JOIN mlfm_order_item f ON e.order_item_id=f.item_order_id INNER JOIN mlfm_item g ON f.item_id=g.item_id INNER JOIN mlfm_uom h ON h.uom_id=g.uom_id INNER JOIN mlfm_order_owner_type i ON d.order_owner_type_id=i.order_owner_type_id INNER JOIN mlfm_po j ON j.PO_ID=a.PO_ID INNER JOIN mlfm_po_mst k ON a.PO_MST_ID=k.PO_MST_ID"+
				" WHERE  a.PRODUCTION_ID=coalesce(:PRODUCTION_ID,a.PRODUCTION_ID)";
				
		
		RowMapper<ModelWashingCustom> serviceMapper=new RowMapper<ModelWashingCustom>() {
			@Override
			public ModelWashingCustom mapRow(ResultSet rs, int rownum) throws SQLException{
				ModelWashingCustom bn=new ModelWashingCustom();
		        
				bn.setwOId(rs.getLong("wo_mst_id"));
				bn.setProductionId(rs.getLong("production_id"));
				bn.setProductionQty(rs.getDouble("production_qty"));
				bn.setItemId(rs.getLong("item_id"));
				bn.setItemName(rs.getString("item_name"));
				bn.setUomId(rs.getLong("uom_id"));
				bn.setUomName(rs.getString("uom_name"));
				bn.setBuyerName(rs.getString("owner_name"));
				bn.setBuyerId(rs.getLong("order_owner_id"));
				bn.setGatePassChdId(rs.getLong("po_mst_id"));
					
			return bn;
			
			}	
		};
		

		MapSqlParameterSource parameters = new MapSqlParameterSource();
	      
	      parameters.addValue("PRODUCTION_ID", id);
	      
	 
	   
		return  jdbcTemplate.query(qry,parameters,serviceMapper);
	}

	@Override
	public List<ModelDeliverChallanCustom> getDeliverChallanDataById(Long id) {
		String qry="SELECT a.description,a.po_mst_id, a.DELIVER_CHALLAN_ID,a.USER_CHALLAN_NO,a.CHALLAN_DATE,a.SIZE,a.GRADE,a.REMARKS, a.DELIVER_DATE,a.CHALLAN_QTY,a.ACTIVE_STATUS,b.item_name,c.uom_name,d.user_name,d.user_id,a.active_status FROM mlfm_deliver_challan a INNER JOIN mlfm_item b ON a.ITEM_ID=b.item_id INNER JOIN mlfm_uom c ON a.UOM_ID=c.uom_id INNER JOIN bg_user d ON a.DELIVER_BY=d.user_id WHERE a.DELIVER_CHALLAN_ID=coalesce(:id,a.DELIVER_CHALLAN_ID)";				
				
				
		RowMapper<ModelDeliverChallanCustom> serviceMapper=new RowMapper<ModelDeliverChallanCustom>() {
			@Override
			public ModelDeliverChallanCustom mapRow(ResultSet rs, int rownum) throws SQLException{
				ModelDeliverChallanCustom bn=new ModelDeliverChallanCustom();
		        
				bn.setDeliverChallanId(rs.getLong("deliver_challan_id"));
				bn.setChallanQty(rs.getDouble("challan_qty"));
				bn.setChallanDate(rs.getDate("challan_date"));
				bn.setDeliverDate(rs.getDate("deliver_date"));
				bn.setDeliveredBy(rs.getString("user_name"));
				bn.setSize(rs.getString("size"));
				bn.setGrade(rs.getString("grade"));
				bn.setRemarks(rs.getString("remarks"));
				bn.setDescription(rs.getString("description"));
				bn.setChallanNo(rs.getString("user_challan_no"));
				bn.setItemName(rs.getString("item_name"));
				bn.setUomName(rs.getString("uom_name"));
				bn.setpOMstId(rs.getLong("po_mst_id"));
				bn.setUserId(rs.getLong("user_id"));
				bn.setStatus(rs.getInt("active_status"));
					
			return bn;
			
			}	
		};
		

		MapSqlParameterSource parameters = new MapSqlParameterSource();
	      
	      parameters.addValue("id", id);
	      
	 
	   
		return  jdbcTemplate.query(qry,parameters,serviceMapper);
	}

	@Override
	public List<ModelDeliverChallanCustom> getDeliverChallanDoneDataById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	public void updateChallan(ModelDeliveryChallan dchallan){
        try {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.update("update MLFM_DELIVER_CHALLAN set user_challan_no=?,challan_qty=?,challan_date=?,description=?,size=?,grade=?,deliver_date=?,deliver_by=?,active_status=?,remarks=?, updated_by=?, update_timestamp=? where deliver_challan_id=?",dchallan.getUserChallanNo(),dchallan.getChallanQty(),dchallan.getChallanDate(),dchallan.getDescription(),dchallan.getSize(),dchallan.getGrade(),dchallan.getDeliverDate(),dchallan.getModelUser().getUserId(),dchallan.getActiveStatus(),dchallan.getRemarks(),dchallan.getUpdatedBy(),dchallan.getUpdateTimestap(),dchallan.getDeliverChallanId());
        	
        
        System.out.println("update success");
       }
        
        
        catch(Exception e) {
        	e.printStackTrace();
}

	}
}
