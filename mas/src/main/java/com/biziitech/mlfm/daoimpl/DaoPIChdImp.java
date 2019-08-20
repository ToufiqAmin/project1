package com.biziitech.mlfm.daoimpl;


import java.sql.ResultSet;
import java.util.List;

import javax.sql.DataSource;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.biziitech.mlfm.custom.model.ModelPIChdOrder;
import com.biziitech.mlfm.dao.DaoPIChd;
import com.biziitech.mlfm.dao.DaoWorkOrderChd;
import com.biziitech.mlfm.model.ModelDeliveryChallan;
import com.biziitech.mlfm.model.ModelPIChd;
import com.biziitech.mlfm.repository.PIChdRepository;


@Service
public class DaoPIChdImp implements DaoPIChd{
	
	@Autowired
    private DataSource dataSource;
       
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private PIChdRepository pIChdRepository;

	@Override
	public List<ModelPIChdOrder> getPIChdDataList(Long workOrderChdId) {
		 String qry ="select b.WO_CHD_ID, a.wo_mst_id, b.ORDER_ITEM_QTY_ID, g.order_id, e.owner_name, e.order_owner_id,f.item_name, b.ITEM_QTY, h.uom_name, h.uom_id, b.ITEM_RATE, b.COMMISSION_RATE, b.COMMISSION_PER_UOM, b.COMMISSION_TOTAL from mlfm_wo_mst a left outer join mlfm_wo_chd b on a.wo_mst_id=b.wo_mst_id inner join mlfm_order_item_qty c on c.ORDER_ITEM_QTY_ID=b.ORDER_ITEM_QTY_ID inner join mlfm_order_item g on g.item_order_id=c.order_item_id INNER JOIN mlfm_order_owner e on a.ORDER_OWNER_ID=e.order_owner_id inner join mlfm_item f ON f.item_id=g.item_id inner join mlfm_uom h on h.uom_id=f.uom_id where not EXISTS (select 1 from mlfm_pi_chd d where b.wo_chd_id=d.wo_chd_id and d.active_status=1 HAVING sum(d.ITEM_QTY)>=sum(b.ITEM_QTY) ) and a.active_status=1 and b.active_status=1 AND b.WO_CHD_ID=COALESCE(:workOrderChdId, b.WO_CHD_ID)";
			String qry_sum_pi_qty ="SELECT sum(a.ITEM_QTY) FROM mlfm_pi_chd a WHERE a.WO_CHD_ID=?";
					     //:wOChdId";	
		    
			
			RowMapper<ModelPIChdOrder> serviceMapper=new RowMapper<ModelPIChdOrder>() {
					public ModelPIChdOrder mapRow(ResultSet rs, int rownum) throws SQLException{
						
						ModelPIChdOrder bn=new ModelPIChdOrder();
						Double pIQtySaved;
						bn.setwOChdId(rs.getLong("wo_chd_id"));
						bn.setwOMstId(rs.getLong("wo_mst_id"));
						bn.setOrderItemQtyId(rs.getLong("order_item_qty_id"));
						bn.setOrderId(rs.getLong("order_id"));
						bn.setOwnerName(rs.getString("owner_name"));
						bn.setItemName(rs.getString("item_name"));
						bn.setItemQty(rs.getDouble("item_qty"));
						bn.setuOMName(rs.getString("uom_name"));
						bn.setuOMId(rs.getLong("uom_id"));
						bn.setItemRate(rs.getDouble("item_rate"));
						bn.setCommissionRate(rs.getDouble("commission_rate"));
						bn.setCommissionPerUOM(rs.getDouble("commission_per_uom"));
						bn.setCommissionTotal(rs.getDouble("commission_total"));
						
				        Object[] inputs = new Object[] {bn.getwOChdId()};
				        pIQtySaved = jdbcTemplate.queryForObject(qry_sum_pi_qty, inputs, Double.class);
				        
				       // bn.setpIQtySaved(pIQtySaved);
				        
				        if(pIQtySaved==null) {
				        	
				        	double qty=0;
				        	bn.setpIQtySaved(qty);
				        	
				        }
				        
				       else {
				        	 bn.setpIQtySaved(pIQtySaved);
				        }
				       
				        System.out.println(" double sum already saved pi qty " + pIQtySaved);
				        
				        
					    return bn;
					}
				};
				
				  MapSqlParameterSource parameters = new MapSqlParameterSource();
			      parameters.addValue("workOrderChdId", workOrderChdId);
			      
			     
				  System.out.println("workOrderChdId : " + workOrderChdId );
			      
			      return namedParameterJdbcTemplate.query(qry,parameters,serviceMapper);
			}

	@Override
	public void savePIChd(ModelPIChd modelPIChd) {
		
		   pIChdRepository.save(modelPIChd);
	}
	
	//edited by KTA

	@Override
	public List<ModelPIChdOrder> getPIChdDataById(Long pIChdId) {
		// TODO Auto-generated method stub
		String qry ="select r.PI_CHD_ID,r.PI_MST_ID,\r\n" + 
				"b.WO_CHD_ID, a.wo_mst_id, b.ORDER_ITEM_QTY_ID,\r\n" + 
				"g.order_id, e.owner_name, e.order_owner_id,f.item_name,\r\n" + 
				"b.ITEM_QTY, h.uom_name, h.uom_id, b.ITEM_RATE, \r\n" + 
				"b.COMMISSION_RATE, b.COMMISSION_PER_UOM, b.COMMISSION_TOTAL,\r\n" + 
				"r.CURRENCY_ID,s.CURRENCY_NAME\r\n" + 
				"from mlfm_pi_chd r\r\n" + 
				"left outer join mlfm_wo_chd b on r.WO_CHD_ID=b.WO_CHD_ID\r\n" + 
				"LEFT OUTER JOIN mlfm_wo_mst a on b.WO_MST_ID=a.WO_MST_ID \r\n" + 
				"inner join mlfm_order_item_qty c on c.ORDER_ITEM_QTY_ID=b.ORDER_ITEM_QTY_ID\r\n" + 
				"inner join mlfm_order_item g on g.item_order_id=c.order_item_id\r\n" + 
				"INNER JOIN mlfm_order_owner e on a.ORDER_OWNER_ID=e.order_owner_id\r\n" + 
				"inner join mlfm_item f ON f.item_id=g.item_id\r\n" + 
				"inner join mlfm_uom h on h.uom_id=f.uom_id\r\n" + 
				"LEFT  OUTER JOIN bg_currency s on r.CURRENCY_ID=s.CURRENCY_ID\r\n" + 
				"where r.PI_CHD_ID=COALESCE(:pIChdId, r.PI_CHD_ID)";
		String qry_sum_pi_qty ="SELECT sum(a.ITEM_QTY) FROM mlfm_pi_chd a WHERE a.PI_CHD_ID=?";
				     //:wOChdId";	
	    
		
		RowMapper<ModelPIChdOrder> serviceMapper=new RowMapper<ModelPIChdOrder>() {
				public ModelPIChdOrder mapRow(ResultSet rs, int rownum) throws SQLException{
				
					Double pIQtySaved;
					ModelPIChdOrder bn=new ModelPIChdOrder();
					bn.setpIChdId(rs.getLong("PI_CHD_ID"));
					bn.setpIMstId(rs.getLong("PI_MST_ID"));
					bn.setwOChdId(rs.getLong("wo_chd_id"));
					bn.setwOMstId(rs.getLong("wo_mst_id"));
					bn.setOrderItemQtyId(rs.getLong("order_item_qty_id"));
					bn.setOrderId(rs.getLong("order_id"));
					bn.setOwnerName(rs.getString("owner_name"));
					bn.setItemName(rs.getString("item_name"));
					bn.setItemQty(rs.getDouble("item_qty"));
					bn.setuOMName(rs.getString("uom_name"));
					bn.setuOMId(rs.getLong("uom_id"));
					bn.setItemRate(rs.getDouble("item_rate"));
					bn.setCommissionRate(rs.getDouble("commission_rate"));
					bn.setCommissionPerUOM(rs.getDouble("commission_per_uom"));
					bn.setCommissionTotal(rs.getDouble("commission_total"));
					bn.setCurrencyId(rs.getLong("CURRENCY_ID"));
					bn.setCurrencyName(rs.getString("CURRENCY_NAME"));
					
					 Object[] inputs = new Object[] {bn.getpIChdId()};
				        pIQtySaved = jdbcTemplate.queryForObject(qry_sum_pi_qty, inputs, Double.class);
				        
				       // bn.setpIQtySaved(pIQtySaved);
				        
				        if(pIQtySaved==null) {
				        	
				        	double qty=0;
				        	bn.setpIQtySaved(qty);
				        	
				        }
				        
				       else {
				        	 bn.setpIQtySaved(pIQtySaved);
				        }
				       
				        System.out.println(" double sum already saved pi qty " + pIQtySaved);
			  
			        
			        
				    return bn;
				}
			};
			
			  MapSqlParameterSource parameters = new MapSqlParameterSource();
		      parameters.addValue("pIChdId", pIChdId);
		      
		     
			  System.out.println("pIChdId : " + pIChdId );
		      
		      return namedParameterJdbcTemplate.query(qry,parameters,serviceMapper);
	}
	
	
	// end edited by KTA
	
	
	
	

	@Override
	public List<ModelPIChdOrder> getPIChdDataByWOChId(Long id) {
		String qry ="select r.PI_CHD_ID,r.PI_MST_ID,\r\n" + 
				"b.WO_CHD_ID, a.wo_mst_id, b.ORDER_ITEM_QTY_ID,\r\n" + 
				"g.order_id, e.owner_name, e.order_owner_id,f.item_name,\r\n" + 
				"b.ITEM_QTY, h.uom_name, h.uom_id, b.ITEM_RATE, \r\n" + 
				"b.COMMISSION_RATE, b.COMMISSION_PER_UOM, b.COMMISSION_TOTAL,\r\n" + 
				"r.CURRENCY_ID,s.CURRENCY_NAME\r\n" + 
				"from mlfm_pi_chd r\r\n" + 
				"left outer join mlfm_wo_chd b on r.WO_CHD_ID=b.WO_CHD_ID\r\n" + 
				"LEFT OUTER JOIN mlfm_wo_mst a on b.WO_MST_ID=a.WO_MST_ID \r\n" + 
				"inner join mlfm_order_item_qty c on c.ORDER_ITEM_QTY_ID=b.ORDER_ITEM_QTY_ID\r\n" + 
				"inner join mlfm_order_item g on g.item_order_id=c.order_item_id\r\n" + 
				"INNER JOIN mlfm_order_owner e on a.ORDER_OWNER_ID=e.order_owner_id\r\n" + 
				"inner join mlfm_item f ON f.item_id=g.item_id\r\n" + 
				"inner join mlfm_uom h on h.uom_id=f.uom_id\r\n" + 
				"LEFT  OUTER JOIN bg_currency s on r.CURRENCY_ID=s.CURRENCY_ID\r\n" + 
				"where b.WO_CHD_ID=COALESCE(:id, r.WO_CHD_ID)";
		String qry_sum_pi_qty ="SELECT sum(a.ITEM_QTY) FROM mlfm_pi_chd a WHERE a.PI_CHD_ID=?";
				     //:wOChdId";	
	    
		
		RowMapper<ModelPIChdOrder> serviceMapper=new RowMapper<ModelPIChdOrder>() {
				public ModelPIChdOrder mapRow(ResultSet rs, int rownum) throws SQLException{
				
					Double pIQtySaved;
					ModelPIChdOrder bn=new ModelPIChdOrder();
					bn.setpIChdId(rs.getLong("PI_CHD_ID"));
					bn.setpIMstId(rs.getLong("PI_MST_ID"));
					bn.setwOChdId(rs.getLong("wo_chd_id"));
					bn.setwOMstId(rs.getLong("wo_mst_id"));
					bn.setOrderItemQtyId(rs.getLong("order_item_qty_id"));
					bn.setOrderId(rs.getLong("order_id"));
					bn.setOwnerName(rs.getString("owner_name"));
					bn.setItemName(rs.getString("item_name"));
					bn.setItemQty(rs.getDouble("item_qty"));
					bn.setuOMName(rs.getString("uom_name"));
					bn.setuOMId(rs.getLong("uom_id"));
					bn.setItemRate(rs.getDouble("item_rate"));
					bn.setCommissionRate(rs.getDouble("commission_rate"));
					bn.setCommissionPerUOM(rs.getDouble("commission_per_uom"));
					bn.setCommissionTotal(rs.getDouble("commission_total"));
					bn.setCurrencyId(rs.getLong("CURRENCY_ID"));
					bn.setCurrencyName(rs.getString("CURRENCY_NAME"));
					
					 Object[] inputs = new Object[] {bn.getpIChdId()};
				        pIQtySaved = jdbcTemplate.queryForObject(qry_sum_pi_qty, inputs, Double.class);
				        
				       // bn.setpIQtySaved(pIQtySaved);
				        
				        if(pIQtySaved==null) {
				        	
				        	double qty=0;
				        	bn.setpIQtySaved(qty);
				        	
				        }
				        
				       else {
				        	 bn.setpIQtySaved(pIQtySaved);
				        }
				       
				        System.out.println("  saved pi qty " + pIQtySaved);
			  
			        
			        
				    return bn;
				}
			};
			
			  MapSqlParameterSource parameters = new MapSqlParameterSource();
		      parameters.addValue("id", id);
		      
		     
			  System.out.println("idd : " + id );
		      
		      return namedParameterJdbcTemplate.query(qry,parameters,serviceMapper);
	}
	
	
	
	public void updatePIChdData(ModelPIChd modelPIChd){
        try {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.update("update MLFM_PI_CHD set item_qty=?, updated_by=?, update_timestamp=?, currency_id=? where pi_chd_id=?",modelPIChd.getItemQty(),modelPIChd.getUpdatedBy(),modelPIChd.getUpdateTimestamp(),modelPIChd.getModelCurrency().getCurrencyId(),modelPIChd.getpIChdId());
        	
        
        System.out.println("update success");
       }
        
        
        catch(Exception e) {
        	e.printStackTrace();
}

	}
	
	
	

		}

