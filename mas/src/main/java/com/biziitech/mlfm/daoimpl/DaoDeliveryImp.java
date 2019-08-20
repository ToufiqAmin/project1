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
import com.biziitech.mlfm.dao.DaoDelivery;
import com.biziitech.mlfm.model.ModelDelivery;
import com.biziitech.mlfm.repository.DeliveryRepository;

@Service
public class DaoDeliveryImp implements DaoDelivery {
   
	
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	@Autowired
	private JdbcTemplate jdbc;
	
	@Autowired
    private DataSource dataSource;
	
	@Autowired
    private DeliveryRepository deliverRepository;
	
	@Override
	public List<ModelDeliverChallanCustom> getSearchData(Long ownerType, Long owner, Long pOId,String challanNo,Date startDate,Date endDate) {
		String qry="SELECT g.owner_name,a.DELIVER_CHALLAN_ID,a.CHALLAN_DATE,a.CHALLAN_QTY,a.USER_CHALLAN_NO,a.PO_MST_ID,b.item_name,b.item_id  FROM mlfm_deliver_challan a INNER JOIN mlfm_item b ON a.ITEM_ID=b.item_id INNER JOIN mlfm_production c ON a.PO_MST_ID=c.PO_MST_ID INNER JOIN mlfm_po d ON c.PO_ID=d.PO_ID INNER JOIN mlfm_wo_chd e ON c.WO_CHD_ID=e.WO_CHD_ID INNER JOIN mlfm_wo_mst f ON e.WO_MST_ID=f.WO_MST_ID INNER JOIN mlfm_order_owner g ON f.ORDER_OWNER_ID=g.order_owner_id INNER JOIN mlfm_order_owner_type i ON g.order_owner_type_id=i.order_owner_type_id"+
				" WHERE  i.order_owner_type_id=coalesce(:order_owner_type_id,i.order_owner_type_id)" + 
				" and g.order_owner_id=coalesce(:order_owner_id, g.order_owner_id) and d.po_id=coalesce(:po_id, d.po_id)"+
				" and a.challan_date BETWEEN coalesce(:start_date,a.challan_date ) AND coalesce(:end_date,a.challan_date )" + 
				" and a.user_challan_no LIKE CONCAT('%',:user_challan_no,'%')";
				
		
		RowMapper<ModelDeliverChallanCustom> serviceMapper=new RowMapper<ModelDeliverChallanCustom>() {
			@Override
			public ModelDeliverChallanCustom mapRow(ResultSet rs, int rownum) throws SQLException{
				ModelDeliverChallanCustom bn=new ModelDeliverChallanCustom();
		        
				bn.setDeliverChallanId(rs.getLong("deliver_challan_id"));
				bn.setChallanNo(rs.getString("user_challan_no"));
				bn.setChallanDate(rs.getDate("challan_date"));
				bn.setChallanQty(rs.getDouble("challan_qty"));
				bn.setItemName(rs.getString("item_name"));
				bn.setpOMstId(rs.getLong("po_mst_id"));
				bn.setuOMName(rs.getString("owner_name"));
				bn.setItemId(rs.getLong("item_id"));
					
			return bn;
			
			}	
		};
		

		MapSqlParameterSource parameters = new MapSqlParameterSource();
	      
	      parameters.addValue("order_owner_type_id", ownerType);
	      parameters.addValue("order_owner_id", owner);
	      parameters.addValue("po_id", pOId);
	      parameters.addValue("start_date", startDate);
	      parameters.addValue("end_date", endDate);
	      parameters.addValue("user_challan_no", challanNo);
	 
	   
		return  jdbcTemplate.query(qry,parameters,serviceMapper);
	}

	@Override
	public void saveDelivery(ModelDelivery delivery) {
		
		deliverRepository.save(delivery);
		
	}
	
	
	//
}
