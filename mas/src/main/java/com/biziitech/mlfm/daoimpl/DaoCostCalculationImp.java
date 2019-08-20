package com.biziitech.mlfm.daoimpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.biziitech.mlfm.custom.model.ModelWOInquiryData;
import com.biziitech.mlfm.dao.DaoCostCalculation;

@Service
public class DaoCostCalculationImp implements DaoCostCalculation{
   
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	@Override
	public List<ModelWOInquiryData> getSearchData(Long ownerType,String owner,String ultimateOwner,Long inquiryId,Long designId,Long userDefinedNo,String designName,Long designBy,Long itemName, Date stratDate, Date endDate)
			 {
		String qry="SELECT h.user_name, b.owner_name, a.order_id, a.order_date, c.design_id, c.design_name, c.design_date, c.designer_id, e.order_item_qty_id, f.item_name FROM mlfm_order a INNER JOIN mlfm_order_owner b ON a.order_owner_id=b.order_owner_id INNER JOIN mlfm_design c ON a.order_id=c.order_id INNER JOIN mlfm_order_item d ON a.order_id=d.order_id INNER JOIN mlfm_order_item_qty e ON d.item_order_id=e.order_item_id INNER JOIN mlfm_item f ON f.item_id=d.item_id INNER JOIN mlfm_order_owner_type g ON a.order_owner_type_id=g.order_owner_type_id INNER JOIN bg_user h ON a.entered_by=h.user_id where g.order_owner_type_id=coalesce(:order_owner_type_id,g.order_owner_type_id) and"
				+ " UPPER(trim(b.owner_name)) LIKE CONCAT('%',upper(TRIM(:owner_name)),'%') and "
				+ " UPPER(trim(b.owner_name)) LIKE CONCAT('%',upper(TRIM(:ultimate_buyer_name)),'%') and"
				+ " a.order_id=coalesce(:order_id,a.order_id) and"
				+ " c.design_id=coalesce(:design_id,c.design_id) and"
				+ " c.user_design_no=coalesce(:user_design_no,c.user_design_no) and "
				+ " UPPER(trim(c.design_name)) LIKE CONCAT('%',upper(TRIM(:design_name)),'%') and"
				+ " a.entered_by =coalesce(:design_by,a.entered_by) and"
				+ " f.item_id=coalesce(:item_name,f.item_id) and "
				+ " a.order_date between coalesce(:order_strat_date,a.order_date) and coalesce(:order_end_date,a.order_date)";
		
		RowMapper<ModelWOInquiryData> serviceMapper=new RowMapper<ModelWOInquiryData>() {
			@Override
			public ModelWOInquiryData mapRow(ResultSet rs, int rownum) throws SQLException{
				ModelWOInquiryData bn=new ModelWOInquiryData();
		
			bn.setOrderId(rs.getLong("order_id"));
			bn.setOrderOwnerName(rs.getString("owner_name"));
			bn.setOrderDate(rs.getDate("order_date"));
			bn.setDesignId(rs.getLong("design_id"));
			bn.setDesignName(rs.getString("design_name"));
			bn.setDesignDate(rs.getDate("design_date"));
			bn.setDesignBy(rs.getString("user_name"));
			bn.setOrderItemQuantityId(rs.getLong("order_item_qty_id"));
			bn.setItemName(rs.getString("item_name"));
				
			return bn;
			
			}	
		};
		

		MapSqlParameterSource parameters = new MapSqlParameterSource();
	      
	      parameters.addValue("order_owner_type_id", ownerType);
	      parameters.addValue("owner_name", owner);
	      parameters.addValue("ultimate_buyer_name", ultimateOwner);
	      parameters.addValue("order_id", inquiryId);
	      parameters.addValue("design_id", designId);
	      parameters.addValue("user_design_no", userDefinedNo);
	      parameters.addValue("design_name", designName);
	      parameters.addValue("design_by", designBy);
	      parameters.addValue("item_name", itemName);
	      parameters.addValue("order_strat_date", stratDate);
	      parameters.addValue("order_end_date", endDate);

		return  jdbcTemplate.query(qry,parameters,serviceMapper);
	}

	@Override
	public List<ModelWOInquiryData> getDataByDesignId(Long id) {
		 
		//String qry="SELECT a.order_id, e.item_Qty, c.design_id, c.design_name, e.order_item_qty_id, f.item_name FROM mlfm_order a INNER JOIN mlfm_order_owner b ON a.order_owner_id=b.order_owner_id INNER JOIN mlfm_design c ON a.order_id=c.order_id INNER JOIN mlfm_order_item d ON a.order_id=d.order_id INNER JOIN mlfm_order_item_qty e ON d.item_order_id=e.order_item_id INNER JOIN mlfm_item f ON f.item_id=d.item_id INNER JOIN mlfm_order_owner_type g ON a.order_owner_type_id=g.order_owner_type_id where c.design_id=coalesce(:design_id,c.design_id)";
		
		//String qry="SELECT a.order_id, f.item_id, e.item_Qty, c.design_id, c.design_name, e.order_item_qty_id, f.item_name FROM mlfm_order a INNER JOIN mlfm_order_owner b ON a.order_owner_id=b.order_owner_id INNER JOIN mlfm_design c ON a.order_id=c.order_id INNER JOIN mlfm_order_item d ON a.order_id=d.order_id INNER JOIN mlfm_order_item_qty e ON d.item_order_id=e.order_item_id INNER JOIN mlfm_item f ON f.item_id=d.item_id where c.design_id=coalesce(:design_id,c.design_id)";
		
		String qry="SELECT a.design_consum_item_id,b.design_id,b.design_name,c.order_id,f.order_item_qty_id,f.item_qty,e.item_name,e.item_id FROM mlfm_design_consum_item a INNER JOIN mlfm_design b ON a.design_id=b.design_id INNER JOIN mlfm_order c ON c.order_id=b.order_id INNER JOIN mlfm_order_item d ON c.order_id=d.order_id INNER JOIN mlfm_order_item_qty f ON d.item_order_id=f.order_item_id INNER JOIN mlfm_item e ON d.item_id=e.item_id where b.design_id=coalesce(:design_id,b.design_id)";
		
		RowMapper<ModelWOInquiryData> serviceMapper=new RowMapper<ModelWOInquiryData>() {
			@Override
			public ModelWOInquiryData mapRow(ResultSet rs, int rownum) throws SQLException{
				ModelWOInquiryData bn=new ModelWOInquiryData();
		    
		    bn.setOrderId(rs.getLong("order_id"));	
			bn.setDesignId(rs.getLong("design_id"));
			bn.setDesignName(rs.getString("design_name"));
			bn.setOrderItemQuantityId(rs.getLong("order_item_qty_id"));
			bn.setItemName(rs.getString("item_name"));
			bn.setItemQty(rs.getDouble("item_qty"));
			bn.setItemId(rs.getLong("item_id"));
				
			return bn;
			
			}	
		};
		

		MapSqlParameterSource parameters = new MapSqlParameterSource();
	      
	      parameters.addValue("design_id", id);
	     

		return  jdbcTemplate.query(qry,parameters,serviceMapper);
	}

	@Override
	public List<ModelWOInquiryData> getSearchDataWithCalculate(Long ownerType, String owner, String ultimateOwner,
			Long inquiryId, Long designId, Long userDefinedNo, String designName, Long designBy, Long itemName,
			Date stratDate, Date endDate) {
		String qry="SELECT h.user_name, b.owner_name, a.order_id, a.order_date, c.design_id, c.design_name, c.design_date, c.designer_id, e.order_item_qty_id, f.item_name FROM mlfm_order a INNER JOIN mlfm_order_owner b ON a.order_owner_id=b.order_owner_id INNER JOIN mlfm_design c ON a.order_id=c.order_id INNER JOIN mlfm_order_item d ON a.order_id=d.order_id INNER JOIN mlfm_order_item_qty e ON d.item_order_id=e.order_item_id INNER JOIN mlfm_item f ON f.item_id=d.item_id INNER JOIN mlfm_order_owner_type g ON a.order_owner_type_id=g.order_owner_type_id INNER JOIN bg_user h ON a.entered_by=h.user_id WHERE EXISTS(SELECT 1 FROM mlfm_design_cost j WHERE j.design_id=c.design_id AND j.active_status=1) and g.order_owner_type_id=coalesce(:order_owner_type_id,g.order_owner_type_id) and"
				+ " UPPER(trim(b.owner_name)) LIKE CONCAT('%',upper(TRIM(:owner_name)),'%') and "
				+ " UPPER(trim(b.owner_name)) LIKE CONCAT('%',upper(TRIM(:ultimate_buyer_name)),'%') and"
				+ " a.order_id=coalesce(:order_id,a.order_id) and"
				+ " c.design_id=coalesce(:design_id,c.design_id) and"
				+ " c.user_design_no=coalesce(:user_design_no,c.user_design_no) and "
				+ " UPPER(trim(c.design_name)) LIKE CONCAT('%',upper(TRIM(:design_name)),'%') and"
				+ " a.entered_by =coalesce(:design_by,a.entered_by) and"
				+ " f.item_id=coalesce(:item_name,f.item_id) and "
				+ " a.order_date between coalesce(:order_strat_date,a.order_date) and coalesce(:order_end_date,a.order_date)";
		
		RowMapper<ModelWOInquiryData> serviceMapper=new RowMapper<ModelWOInquiryData>() {
			@Override
			public ModelWOInquiryData mapRow(ResultSet rs, int rownum) throws SQLException{
				ModelWOInquiryData bn=new ModelWOInquiryData();
		
			bn.setOrderId(rs.getLong("order_id"));
			bn.setOrderOwnerName(rs.getString("owner_name"));
			bn.setOrderDate(rs.getDate("order_date"));
			bn.setDesignId(rs.getLong("design_id"));
			bn.setDesignName(rs.getString("design_name"));
			bn.setDesignDate(rs.getDate("design_date"));
			bn.setDesignBy(rs.getString("user_name"));
			bn.setOrderItemQuantityId(rs.getLong("order_item_qty_id"));
			bn.setItemName(rs.getString("item_name"));
				
			return bn;
			
			}	
		};
		

		MapSqlParameterSource parameters = new MapSqlParameterSource();
	      
	      parameters.addValue("order_owner_type_id", ownerType);
	      parameters.addValue("owner_name", owner);
	      parameters.addValue("ultimate_buyer_name", ultimateOwner);
	      parameters.addValue("order_id", inquiryId);
	      parameters.addValue("design_id", designId);
	      parameters.addValue("user_design_no", userDefinedNo);
	      parameters.addValue("design_name", designName);
	      parameters.addValue("design_by", designBy);
	      parameters.addValue("item_name", itemName);
	      parameters.addValue("order_strat_date", stratDate);
	      parameters.addValue("order_end_date", endDate);

		return  jdbcTemplate.query(qry,parameters,serviceMapper);
	}

	@Override
	public List<ModelWOInquiryData> getConsumCalculateDataByDesignId(Long id) {
		
   String qry="SELECT l.currency_id,j.unit_price,j.total_cost,a.design_consum_item_id,b.design_id,b.design_name,c.order_id,f.order_item_qty_id,f.item_qty,e.item_name,e.item_id, j.design_cost_id,j.uom_id,j.qty,j.total_cost FROM mlfm_design_consum_item a INNER JOIN mlfm_design b ON a.design_id=b.design_id INNER JOIN mlfm_order c ON c.order_id=b.order_id INNER JOIN mlfm_order_item d ON c.order_id=d.order_id INNER JOIN mlfm_order_item_qty f ON d.item_order_id=f.order_item_id INNER JOIN mlfm_item e ON d.item_id=e.item_id INNER JOIN mlfm_design_cost j ON a.design_id=j.design_id and a.item_id=j.item_id INNER JOIN bg_currency l ON j.CURRENCY_ID=l.CURRENCY_ID WHERE EXISTS (SELECT 1 FROM mlfm_design_cost k WHERE k.design_id=a.design_id) and b.design_id=coalesce(:design_id,b.design_id)";
		
		RowMapper<ModelWOInquiryData> serviceMapper=new RowMapper<ModelWOInquiryData>() {
			@Override
			public ModelWOInquiryData mapRow(ResultSet rs, int rownum) throws SQLException{
				ModelWOInquiryData bn=new ModelWOInquiryData();
		    
		    bn.setOrderId(rs.getLong("order_id"));	
			bn.setDesignId(rs.getLong("design_id"));
			bn.setDesignName(rs.getString("design_name"));
			bn.setOrderItemQuantityId(rs.getLong("order_item_qty_id"));
			bn.setItemName(rs.getString("item_name"));
			bn.setItemQty(rs.getDouble("item_qty"));
			bn.setItemId(rs.getLong("item_id"));
			bn.setItemRate(rs.getDouble("unit_price"));
			bn.setTotalCost(rs.getDouble("total_cost"));
			bn.setCurrencyId(rs.getLong("currency_id"));
				
			return bn;
			
			}	
		};
		

		MapSqlParameterSource parameters = new MapSqlParameterSource();
	      
	      parameters.addValue("design_id", id);
	     

		return  jdbcTemplate.query(qry,parameters,serviceMapper);
	}

}
