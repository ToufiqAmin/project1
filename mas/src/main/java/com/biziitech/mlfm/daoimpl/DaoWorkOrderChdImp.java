package com.biziitech.mlfm.daoimpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.biziitech.mlfm.custom.model.ModelPIChdOrder;
import com.biziitech.mlfm.custom.model.ModelPIInquiryList;
import com.biziitech.mlfm.custom.model.ModelWOInquiryData;
import com.biziitech.mlfm.dao.DaoWorkOrderChd;
import com.biziitech.mlfm.model.ModelWOChd;
import com.biziitech.mlfm.repository.WorkOrderChdRepository;


@Service
public class DaoWorkOrderChdImp implements DaoWorkOrderChd {
	
	@Autowired
	private WorkOrderChdRepository wOChdRepository;
	
	@Autowired
    private DataSource dataSource;
  
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private NamedParameterJdbcTemplate namedJdbcTemplate;
    public void setNamedDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.namedJdbcTemplate=namedJdbcTemplate;
          }
	
	
	
	@Override
	public List<ModelPIChdOrder> getWOChdDataList(Long workOrderId) {
    //String qry ="select b.WO_CHD_ID, a.wo_mst_id, b.ORDER_ITEM_QTY_ID, g.order_id, e.owner_name, e.order_owner_id,f.item_name, b.ITEM_QTY, h.uom_name, h.uom_id, b.ITEM_RATE, b.COMMISSION_RATE, b.COMMISSION_PER_UOM, b.COMMISSION_TOTAL from mlfm_wo_mst a left outer join mlfm_wo_chd b on a.wo_mst_id=b.wo_mst_id inner join mlfm_order_item_qty c on c.ORDER_ITEM_QTY_ID=b.ORDER_ITEM_QTY_ID inner join mlfm_order_item g on g.item_order_id=c.order_item_id INNER JOIN mlfm_order_owner e on a.ORDER_OWNER_ID=e.order_owner_id inner join mlfm_item f ON f.item_id=g.item_id inner join mlfm_uom h on h.uom_id=f.uom_id where not EXISTS (select 1 from mlfm_pi_chd d where b.wo_chd_id=d.wo_chd_id and d.active_status=1 HAVING sum(d.ITEM_QTY)>=sum(b.ITEM_QTY) ) and a.active_status=1 and b.active_status=1 AND b.WO_MST_ID=COALESCE(:workOrderId, b.WO_MST_ID)";
	 String qry="select b.WO_CHD_ID, a.wo_mst_id, b.ORDER_ITEM_QTY_ID, g.order_id, e.owner_name, e.order_owner_id,f.item_name, b.ITEM_QTY, m.CURRENCY_NAME, h.uom_name, h.uom_id, b.ITEM_RATE, b.COMMISSION_RATE, b.COMMISSION_PER_UOM, b.COMMISSION_TOTAL from mlfm_wo_mst a left outer join mlfm_wo_chd b on a.wo_mst_id=b.wo_mst_id inner join mlfm_order_item_qty c on c.ORDER_ITEM_QTY_ID=b.ORDER_ITEM_QTY_ID inner join mlfm_order_item g on g.item_order_id=c.order_item_id INNER JOIN mlfm_order_owner e on a.ORDER_OWNER_ID=e.order_owner_id inner join mlfm_item f ON f.item_id=g.item_id inner join mlfm_uom h on h.uom_id=f.uom_id INNER JOIN bg_currency m ON b.CURRENCY_ID=m.CURRENCY_ID where not EXISTS (select 1 from mlfm_pi_chd d where b.wo_chd_id=d.wo_chd_id and d.active_status=1 HAVING sum(d.ITEM_QTY)>=sum(b.ITEM_QTY) ) and a.active_status=1 and b.active_status=1 AND b.WO_MST_ID=COALESCE(:workOrderId, b.WO_MST_ID)";
		
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
				bn.setCurrencyName(rs.getString("CURRENCY_NAME"));
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
		        System.out.println("currency Name :" + rs.getString("CURRENCY_NAME"));
		        
			    return bn;
			}
		};
		
		  MapSqlParameterSource parameters = new MapSqlParameterSource();
	      parameters.addValue("workOrderId", workOrderId);
	      
	     
		  System.out.println("workOrderId : " + workOrderId );
	      
	      return namedParameterJdbcTemplate.query(qry,parameters,serviceMapper);
	}



	@Override
	public void saveWOChd(ModelWOChd modelWOChd) {
		
		wOChdRepository.save(modelWOChd);
		// TODO Auto-generated method stub
		
	}



	@Override
	public List<ModelWOInquiryData> getWOChdData(Long wOChdId) {
		
		
		String  qry="SELECT wc.WO_CHD_ID,wc.ORDER_ITEM_QTY_ID,oiq.item_Qty,i.item_name,wc.ITEM_RATE,wc.ITEM_QTY,wc.REMARKS,\r\n" + 
				"wc.ACTIVE_STATUS,wc.CURRENCY_ID,wc.COMMISSION_RATE,wc.COMMISSION_PER_UOM,wc.COMMISSION_TOTAL,u.uom_id,uom.uom_id\r\n" + 
				"FROM mlfm_wo_chd wc\r\n" + 
				"INNER JOIN mlfm_order_item_qty oiq on oiq.order_item_qty_id=wc.ORDER_ITEM_QTY_ID\r\n" + 
				"INNER JOIN mlfm_order_item oi on oi.item_order_id=oiq.order_item_id\r\n" + 
				"INNER JOIN mlfm_item i ON i.item_id=oi.item_id\r\n" + 
				"LEFT OUTER JOIN mlfm_uom u on u.uom_id=i.uom_id\r\n" + 
				"LEFT OUTER JOIN mlfm_uom uom on uom.uom_id = wc.COMMISSION_PER_UOM\r\n" + 
				"LEFT OUTER JOIN bg_currency bc ON bc.CURRENCY_ID=wc.CURRENCY_ID\r\n" + 
				"WHERE wc.WO_CHD_ID=COALESCE(:wOChdId,wc.WO_CHD_ID)";
		
		/*
		 *
		 *SELECT wc.WO_CHD_ID,wc.ORDER_ITEM_QTY_ID,oiq.item_Qty,i.item_name,wc.ITEM_RATE,wc.ITEM_QTY,wc.REMARKS,
wc.ACTIVE_STATUS,wc.CURRENCY_ID,wc.COMMISSION_RATE,wc.COMMISSION_PER_UOM,wc.COMMISSION_TOTAL,u.uom_id,uom.uom_id
FROM mlfm_wo_chd wc
INNER JOIN mlfm_order_item_qty oiq on oiq.order_item_qty_id=wc.ORDER_ITEM_QTY_ID
INNER JOIN mlfm_order_item oi on oi.item_order_id=oiq.order_item_id
INNER JOIN mlfm_item i ON i.item_id=oi.item_id
LEFT OUTER JOIN mlfm_uom u on u.uom_id=i.uom_id
LEFT OUTER JOIN mlfm_uom uom on uom.uom_id = wc.COMMISSION_PER_UOM
LEFT OUTER JOIN bg_currency bc ON bc.CURRENCY_ID=wc.CURRENCY_ID
WHERE wc.WO_CHD_ID=COALESCE(@wOChdId,wc.WO_CHD_ID)
		 * 
		 * */
	
	RowMapper<ModelWOInquiryData> serviceMapper=new RowMapper<ModelWOInquiryData>() {
		@Override
		public ModelWOInquiryData mapRow(ResultSet rs, int rownum) throws SQLException{
			ModelWOInquiryData bn=new ModelWOInquiryData();
			
			bn.setwOChdId(rs.getLong("WO_CHD_ID"));
			bn.setOrderItemQuantityId(rs.getLong("ORDER_ITEM_QTY_ID"));
			bn.setItemQty(rs.getDouble("item_Qty"));
			bn.setItemName(rs.getString("item_name"));
			bn.setItemRate(rs.getDouble("ITEM_RATE"));
			bn.setwOChdItemQty(rs.getDouble("ITEM_QTY"));
			bn.setActiveStatus(rs.getInt("ACTIVE_STATUS"));
			bn.setRemarks(rs.getString("REMARKS"));
			bn.setCurrencyId(rs.getLong("CURRENCY_ID"));
			bn.setCommissionRate(rs.getDouble("COMMISSION_RATE"));
			bn.setCommissionTotal(rs.getDouble("COMMISSION_TOTAL"));
			bn.setCommissionPerUOM(rs.getLong("COMMISSION_PER_UOM"));
			bn.setUomId(rs.getLong("uom_id"));
			
			
		 return bn;
		
		}	
	};
	
	MapSqlParameterSource parameters = new MapSqlParameterSource();
	
	  parameters.addValue("wOChdId", wOChdId);
	  
	 System.out.println("query : " + qry);
      
	// TODO Auto-generated method stub
	return  namedJdbcTemplate.query(qry,parameters,serviceMapper);

	}



	@Override
	public Optional<ModelWOChd> findWOChdById(Long wOChdId) {
		
		// TODO Auto-generated method stub
		
		return wOChdRepository.findById(wOChdId);
	}



	@Override
	public List<ModelPIChdOrder> getDoneWOChdDataList(Long workOrderId) {
		 String qry="select b.WO_CHD_ID, a.wo_mst_id, b.ORDER_ITEM_QTY_ID, g.order_id, e.owner_name, e.order_owner_id,f.item_name, b.ITEM_QTY, m.CURRENCY_NAME, h.uom_name, h.uom_id, b.ITEM_RATE, b.COMMISSION_RATE, b.COMMISSION_PER_UOM, b.COMMISSION_TOTAL from mlfm_wo_mst a left outer join mlfm_wo_chd b on a.wo_mst_id=b.wo_mst_id inner join mlfm_order_item_qty c on c.ORDER_ITEM_QTY_ID=b.ORDER_ITEM_QTY_ID inner join mlfm_order_item g on g.item_order_id=c.order_item_id INNER JOIN mlfm_order_owner e on a.ORDER_OWNER_ID=e.order_owner_id inner join mlfm_item f ON f.item_id=g.item_id inner join mlfm_uom h on h.uom_id=f.uom_id INNER JOIN bg_currency m ON b.CURRENCY_ID=m.CURRENCY_ID where EXISTS (select 1 from mlfm_pi_chd d where b.wo_chd_id=d.wo_chd_id and d.active_status=1 ) and a.active_status=1 and b.active_status=1 AND b.WO_MST_ID=COALESCE(:workOrderId, b.WO_MST_ID)";
			
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
					bn.setCurrencyName(rs.getString("CURRENCY_NAME"));
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
			        System.out.println("currency Name :" + rs.getString("CURRENCY_NAME"));
			        
				    return bn;
				}
			};
			
			  MapSqlParameterSource parameters = new MapSqlParameterSource();
		      parameters.addValue("workOrderId", workOrderId);
		      
		     
			  System.out.println("workOrderId : " + workOrderId );
		      
		      return namedParameterJdbcTemplate.query(qry,parameters,serviceMapper);
		}

	

}
