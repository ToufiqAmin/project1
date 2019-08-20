package com.biziitech.mlfm.daoimpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.biziitech.mlfm.custom.model.ModelWashingCustom;
import com.biziitech.mlfm.dao.DaoGatePassChd;
import com.biziitech.mlfm.model.ModelGatePassChd;
import com.biziitech.mlfm.model.ModelGatePassMst;
import com.biziitech.mlfm.repository.GatePassChdRepository;
import com.biziitech.mlfm.repository.GatePassRepository;

@Service
public class DaoGatePassChdImp implements DaoGatePassChd{
    
	@Autowired
	private GatePassChdRepository gatePassChdRepository;
	
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	@Autowired
	private JdbcTemplate jdbc;
	
	@Autowired
    private DataSource dataSource;
	
	@Override
	public void saveGatePassChd(ModelGatePassChd gatePassChd) {
		
		gatePassChdRepository.save(gatePassChd);
	}

	@Override
	public List<ModelWashingCustom> getDataByChdId(Long id) {
		String qry="SELECT a.ITEM_QTY,a.GATE_PASS_CHD_ID,b.GATE_PASS_MST_ID,b.GATE_PASS_TYPE,c.PRODUCTION_ID,c.PRODUCTION_DATE,c.PRODUCTION_QTY,d.order_item_qty_id,f.item_name,g.uom_name,h.order_id,i.owner_name FROM mlfm_gate_pass_chd a INNER JOIN mlfm_gate_pass_mst b ON a.GATE_PASS_MST_ID=b.GATE_PASS_MST_ID LEFT OUTER JOIN mlfm_production c ON a.PRODUCTION_ID=c.PRODUCTION_ID INNER JOIN mlfm_order_item_qty d ON c.ORDER_ITEM_QTY_ID=d.order_item_qty_id INNER JOIN mlfm_order_item e ON d.order_item_id=e.item_order_id INNER JOIN mlfm_item f ON e.item_id=f.item_id INNER JOIN mlfm_uom g ON f.uom_id=g.uom_id INNER JOIN mlfm_order h ON e.order_id=h.order_id INNER JOIN mlfm_order_owner i ON h.order_owner_id=i.order_owner_id" + 
				" WHERE  a.GATE_PASS_CHD_ID=coalesce(:id,a.GATE_PASS_CHD_ID)";		
				
		
		RowMapper<ModelWashingCustom> serviceMapper=new RowMapper<ModelWashingCustom>() {
			@Override
			public ModelWashingCustom mapRow(ResultSet rs, int rownum) throws SQLException{
				ModelWashingCustom bn=new ModelWashingCustom();
		        
				bn.setInquiryId(rs.getLong("order_id"));
				bn.setInquiryItemQtyId(rs.getLong("order_item_qty_id"));				
				bn.setBuyerName(rs.getString("owner_name"));
				bn.setItemName(rs.getString("item_name"));
				bn.setInquiryItemQty(rs.getDouble("item_qty"));
				bn.setUomName(rs.getString("uom_name"));
				bn.setProductionId(rs.getLong("production_id"));
				bn.setProductionDate(rs.getDate("production_date"));
				bn.setProductionQty(rs.getDouble("production_qty"));
				bn.setGatePassChdId(rs.getLong("gate_pass_chd_id"));
				bn.setGatePassId(rs.getLong("gate_pass_mst_id"));
				bn.setGatePassType(rs.getInt("gate_pass_type"));
				
				
			
				
			return bn;
			
			}	
		};
		

		MapSqlParameterSource parameters = new MapSqlParameterSource();
	      
	      parameters.addValue("id", id);
	     
	      

		return  jdbcTemplate.query(qry,parameters,serviceMapper);
	}

	@Override
	public List<ModelWashingCustom> getDataByChdByProductionId(Long id) {
		String qry="SELECT a.ITEM_QTY,a.GATE_PASS_CHD_ID,b.GATE_PASS_MST_ID,b.GATE_PASS_TYPE,c.PRODUCTION_ID,c.PRODUCTION_DATE,c.PRODUCTION_QTY,d.order_item_qty_id,f.item_name,g.uom_name,h.order_id,i.owner_name FROM mlfm_gate_pass_chd a INNER JOIN mlfm_gate_pass_mst b ON a.GATE_PASS_MST_ID=b.GATE_PASS_MST_ID LEFT OUTER JOIN mlfm_production c ON a.PRODUCTION_ID=c.PRODUCTION_ID INNER JOIN mlfm_order_item_qty d ON c.ORDER_ITEM_QTY_ID=d.order_item_qty_id INNER JOIN mlfm_order_item e ON d.order_item_id=e.item_order_id INNER JOIN mlfm_item f ON e.item_id=f.item_id INNER JOIN mlfm_uom g ON f.uom_id=g.uom_id INNER JOIN mlfm_order h ON e.order_id=h.order_id INNER JOIN mlfm_order_owner i ON h.order_owner_id=i.order_owner_id" + 
				" WHERE  c.PRODUCTION_ID=coalesce(:id,c.PRODUCTION_ID)";		
				
		
		RowMapper<ModelWashingCustom> serviceMapper=new RowMapper<ModelWashingCustom>() {
			@Override
			public ModelWashingCustom mapRow(ResultSet rs, int rownum) throws SQLException{
				ModelWashingCustom bn=new ModelWashingCustom();
		        
				bn.setInquiryId(rs.getLong("order_id"));
				bn.setInquiryItemQtyId(rs.getLong("order_item_qty_id"));				
				bn.setBuyerName(rs.getString("owner_name"));
				bn.setItemName(rs.getString("item_name"));
				bn.setInquiryItemQty(rs.getDouble("item_qty"));
				bn.setUomName(rs.getString("uom_name"));
				bn.setProductionId(rs.getLong("production_id"));
				bn.setProductionDate(rs.getDate("production_date"));
				bn.setProductionQty(rs.getDouble("production_qty"));
				bn.setGatePassChdId(rs.getLong("gate_pass_chd_id"));
				bn.setGatePassId(rs.getLong("gate_pass_mst_id"));
				bn.setGatePassType(rs.getInt("gate_pass_type"));
				
				
			
				
			return bn;
			
			}	
		};
		

		MapSqlParameterSource parameters = new MapSqlParameterSource();
	      
	      parameters.addValue("id", id);
	     
	      

		return  jdbcTemplate.query(qry,parameters,serviceMapper);
	}
	
	
	public void updateGatePassChd(ModelGatePassChd gatePass){
        try {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.update("update MLFM_GATE_PASS_CHD set item_qty=?, updated_by=?, update_timestamp=? where gate_pass_chd_id=?",
        		gatePass.getItemQty(),gatePass.getUpdatedBy(),gatePass.getUpdateTimestamp(),gatePass.getGatePassChdId());
       }
        
        
        catch(Exception e) {
        	e.printStackTrace();
}

	}

}
