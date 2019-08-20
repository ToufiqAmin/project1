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

import com.biziitech.mlfm.custom.model.ModelPIMstCustom;
import com.biziitech.mlfm.custom.model.ModelWashingCustom;
import com.biziitech.mlfm.dao.DaoPIReceive;


@Service
public class DaoPIReceiveImp implements DaoPIReceive {
	
	   
		@Autowired
		private NamedParameterJdbcTemplate jdbcTemplate;
		
		@Autowired
		private JdbcTemplate jdbc;
		
		
		@Autowired
	    private DataSource dataSource;


		@Override
		public List<ModelPIMstCustom> getPISearchData(Long ownerType, Long owner, Long wOMstId, Date startDate,
				Date endDate,Long pITypeId) {
			
			
		/*	String qry="SELECT a.PI_DATE,l.type_name, k.currency_name,a.PI_MST_ID,a.ORDER_OWNER_ID,h.owner_name, b.PI_CHD_ID,b.WO_CHD_ID,f.item_name, b.ITEM_QTY,b.ITEM_RATE,c.WO_MST_ID,g.uom_name,c.COMMISSION_PER_UOM,c.COMMISSION_TOTAL FROM mlfm_pi_mst a INNER JOIN mlfm_pi_chd b ON a.PI_MST_ID=b.PI_MST_ID INNER JOIN mlfm_wo_chd c ON b.WO_CHD_ID=c.WO_CHD_ID INNER JOIN mlfm_order_item_qty d ON c.ORDER_ITEM_QTY_ID=d.order_item_qty_id INNER JOIN mlfm_order_item e ON d.order_item_id=e.item_order_id INNER JOIN mlfm_item f ON e.item_id=f.item_id INNER JOIN mlfm_uom g ON b.UOM_ID=g.uom_id INNER JOIN mlfm_order_owner h ON a.ORDER_OWNER_ID=h.order_owner_id INNER JOIN mlfm_order_owner_type i ON h.order_owner_type_id=i.order_owner_type_id INNER JOIN mlfm_wo_mst j ON c.WO_MST_ID=j.WO_MST_ID INNER JOIN bg_currency k ON b.CURRENCY_ID=k.CURRENCY_ID INNER JOIN mlfm_pi_type l ON a.PI_TYPE=l.PI_TYPE_ID" +
					" WHERE  i.order_owner_type_id=coalesce(:order_owner_type_id,i.order_owner_type_id) and l.pi_type_id=coalesce(:pi_type_id,l.pi_type_id)" + 
					" and h.order_owner_id=coalesce(:order_owner_id, h.order_owner_id) and j.wo_mst_id=coalesce(:wo_mst_id, j.wo_mst_id) "+
					" and a.PI_date BETWEEN coalesce(:start_date,a.PI_date) AND coalesce(:end_date,a.PI_date)";
			*/
			
			
			String qry="SELECT a.PI_DATE,l.type_name, k.currency_name,a.PI_MST_ID,a.ORDER_OWNER_ID,h.owner_name, b.PI_CHD_ID,b.WO_CHD_ID,f.item_name, b.ITEM_QTY,b.ITEM_RATE,c.WO_MST_ID,g.uom_name,c.COMMISSION_PER_UOM,c.COMMISSION_TOTAL FROM mlfm_pi_mst a INNER JOIN mlfm_pi_chd b ON a.PI_MST_ID=b.PI_MST_ID INNER JOIN mlfm_wo_chd c ON b.WO_CHD_ID=c.WO_CHD_ID INNER JOIN mlfm_order_item_qty d ON c.ORDER_ITEM_QTY_ID=d.order_item_qty_id INNER JOIN mlfm_order_item e ON d.order_item_id=e.item_order_id INNER JOIN mlfm_item f ON e.item_id=f.item_id INNER JOIN mlfm_uom g ON b.UOM_ID=g.uom_id INNER JOIN mlfm_order_owner h ON a.ORDER_OWNER_ID=h.order_owner_id INNER JOIN mlfm_order_owner_type i ON h.order_owner_type_id=i.order_owner_type_id INNER JOIN mlfm_wo_mst j ON c.WO_MST_ID=j.WO_MST_ID INNER JOIN bg_currency k ON b.CURRENCY_ID=k.CURRENCY_ID INNER JOIN mlfm_pi_type l ON a.PI_TYPE=l.PI_TYPE_ID WHERE NOT EXISTS (SELECT 1 FROM mlfm_pi_receive_mst t WHERE a.PI_MST_ID=t.PI_MST_ID)" +
					" and i.order_owner_type_id=coalesce(:order_owner_type_id,i.order_owner_type_id) and l.pi_type_id=coalesce(:pi_type_id,l.pi_type_id)" + 
					" and h.order_owner_id=coalesce(:order_owner_id, h.order_owner_id) and j.wo_mst_id=coalesce(:wo_mst_id, j.wo_mst_id) "+
					" and a.PI_date BETWEEN coalesce(:start_date,a.PI_date) AND coalesce(:end_date,a.PI_date)";
			
			RowMapper<ModelPIMstCustom> serviceMapper=new RowMapper<ModelPIMstCustom>() {
				@Override
				public ModelPIMstCustom mapRow(ResultSet rs, int rownum) throws SQLException{
					ModelPIMstCustom bn=new ModelPIMstCustom();
			        
					bn.setpIMstId(rs.getLong("pi_mst_id"));
					bn.setpIChdId(rs.getLong("pi_chd_id"));
					bn.setwOMstId(rs.getLong("wo_mst_id"));
					bn.setOwnerName(rs.getString("owner_name"));
					bn.setpIQty(rs.getDouble("item_qty"));
					bn.setItemName(rs.getString("item_name"));
					bn.setItemRate(rs.getDouble("item_rate"));
					bn.setuOMName(rs.getString("uom_name"));
					bn.setCurrencyName(rs.getString("currency_name"));
					bn.setComissionPerUOM(rs.getDouble("commission_per_uom"));
					bn.setComissionTotal(rs.getDouble("commission_total"));
					bn.setpIDate(rs.getDate("pi_date"));
					bn.setpITypeName(rs.getString("type_name"));
					
					
				
					
				return bn;
				
				}	
			};
			

			MapSqlParameterSource parameters = new MapSqlParameterSource();
		      
		      parameters.addValue("order_owner_type_id", ownerType);
		      parameters.addValue("order_owner_id", owner);
		      parameters.addValue("wo_mst_id", wOMstId);
		      parameters.addValue("start_date", startDate);
		      parameters.addValue("end_date", endDate);
		      parameters.addValue("pi_type_id", pITypeId);
		      
		      System.out.println("ownerType Id :" + ownerType);
		      System.out.println("owner Id :" + owner);
		      System.out.println("order Id :" + wOMstId);
		      System.out.println("Start Date :" + startDate);
		      System.out.println("End Date :" + endDate);
		   
		      

			return  jdbcTemplate.query(qry,parameters,serviceMapper);
		}


		@Override
		public List<ModelPIMstCustom> getPIReceiveDoneData(Long ownerType, Long owner, Long wOMstId, Date startDate,
				Date endDate, Long pITypeId, Date doneStartDate, Date doneEndDate) {
			// TODO Auto-generated method stub
			String qry="SELECT a.PI_DATE,l.type_name, k.currency_name,a.PI_MST_ID,a.ORDER_OWNER_ID,h.owner_name, b.PI_CHD_ID,b.WO_CHD_ID,f.item_name, b.ITEM_QTY,b.ITEM_RATE,c.WO_MST_ID,g.uom_name,c.COMMISSION_PER_UOM,c.COMMISSION_TOTAL FROM mlfm_pi_receive_mst t INNER JOIN mlfm_pi_mst a ON a.PI_MST_ID=t.PI_MST_ID INNER JOIN mlfm_pi_chd b ON a.PI_MST_ID=b.PI_MST_ID INNER JOIN mlfm_wo_chd c ON b.WO_CHD_ID=c.WO_CHD_ID INNER JOIN mlfm_order_item_qty d ON c.ORDER_ITEM_QTY_ID=d.order_item_qty_id INNER JOIN mlfm_order_item e ON d.order_item_id=e.item_order_id INNER JOIN mlfm_item f ON e.item_id=f.item_id INNER JOIN mlfm_uom g ON b.UOM_ID=g.uom_id INNER JOIN mlfm_order_owner h ON a.ORDER_OWNER_ID=h.order_owner_id INNER JOIN mlfm_order_owner_type i ON h.order_owner_type_id=i.order_owner_type_id INNER JOIN mlfm_wo_mst j ON c.WO_MST_ID=j.WO_MST_ID INNER JOIN bg_currency k ON b.CURRENCY_ID=k.CURRENCY_ID INNER JOIN mlfm_pi_type l ON a.PI_TYPE=l.PI_TYPE_ID" +
					" WHERE  i.order_owner_type_id=coalesce(:order_owner_type_id,i.order_owner_type_id) and l.pi_type_id=coalesce(:pi_type_id,l.pi_type_id)" + 
					" and h.order_owner_id=coalesce(:order_owner_id, h.order_owner_id) and j.wo_mst_id=coalesce(:wo_mst_id, j.wo_mst_id) "+
					" and a.PI_date BETWEEN coalesce(:start_date,a.PI_date) AND coalesce(:end_date,a.PI_date)"+
					" and t.receive_mst_date BETWEEN coalesce(:done_start_date,t.receive_mst_date) AND coalesce(:done_end_date,t.receive_mst_date)";
					
			
			RowMapper<ModelPIMstCustom> serviceMapper=new RowMapper<ModelPIMstCustom>() {
				@Override
				public ModelPIMstCustom mapRow(ResultSet rs, int rownum) throws SQLException{
					ModelPIMstCustom bn=new ModelPIMstCustom();
			        
					bn.setpIMstId(rs.getLong("pi_mst_id"));
					bn.setpIChdId(rs.getLong("pi_chd_id"));
					bn.setwOMstId(rs.getLong("wo_mst_id"));
					bn.setOwnerName(rs.getString("owner_name"));
					bn.setpIQty(rs.getDouble("item_qty"));
					bn.setItemName(rs.getString("item_name"));
					bn.setItemRate(rs.getDouble("item_rate"));
					bn.setuOMName(rs.getString("uom_name"));
					bn.setCurrencyName(rs.getString("currency_name"));
					bn.setComissionPerUOM(rs.getDouble("commission_per_uom"));
					bn.setComissionTotal(rs.getDouble("commission_total"));
					bn.setpIDate(rs.getDate("pi_date"));
					bn.setpITypeName(rs.getString("type_name"));
					
					
				
					
				return bn;
				
				}	
			};
			

			MapSqlParameterSource parameters = new MapSqlParameterSource();
		      
		      parameters.addValue("order_owner_type_id", ownerType);
		      parameters.addValue("order_owner_id", owner);
		      parameters.addValue("wo_mst_id", wOMstId);
		      parameters.addValue("start_date", startDate);
		      parameters.addValue("end_date", endDate);
		      parameters.addValue("pi_type_id", pITypeId);
		      
		      parameters.addValue("done_start_date", doneStartDate);
		      parameters.addValue("done_end_date", doneEndDate);
		      
		      System.out.println("ownerType Id :" + ownerType);
		      System.out.println("owner Id :" + owner);
		      System.out.println("order Id :" + wOMstId);
		      System.out.println("Start Date :" + startDate);
		      System.out.println("End Date :" + endDate);
		   
		      

			return  jdbcTemplate.query(qry,parameters,serviceMapper);
		}

		
		
		
}
