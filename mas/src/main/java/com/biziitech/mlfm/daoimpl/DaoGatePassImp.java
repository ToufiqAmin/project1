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

import com.biziitech.mlfm.bg.model.ModelUser;
import com.biziitech.mlfm.custom.model.ModelGatePassCustom;
import com.biziitech.mlfm.custom.model.ModelWOInquiryData;
import com.biziitech.mlfm.custom.model.ModelWashingCustom;
import com.biziitech.mlfm.dao.DaoGatePass;
import com.biziitech.mlfm.model.ModelGatePassMst;
import com.biziitech.mlfm.model.ModelOrderOwnerFeedback;
import com.biziitech.mlfm.repository.GatePassRepository;

@Service
public class DaoGatePassImp implements DaoGatePass {
     
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	@Autowired
	private JdbcTemplate jdbc;
	
	@Autowired
	private GatePassRepository gatePassRepository;
	
	@Autowired
    private DataSource dataSource;
	
	@Override
	public List<ModelWashingCustom> getInquirySearchData(Long onwerType,Long owner,Long orderId,Date startDate,Date endDate) {
		
		
		//String qry="SELECT a.order_id,a.order_date,a.order_owner_id,a.order_owner_id,b.owner_name,d.order_item_qty_id,d.item_Qty,e.item_name,f.uom_name,g.PRODUCTION_DATE,g.PRODUCTION_QTY,h.QC_QTY,g.PRODUCTION_ID,i.MENDED_QTY,j.WASHED_QTY,k.FINISHED_QTY,l.PACKED_QTY FROM mlfm_order a INNER JOIN mlfm_order_owner b ON a.order_owner_id=b.order_owner_id INNER JOIN mlfm_order_item c ON c.order_id=a.order_id INNER JOIN mlfm_order_item_qty d ON d.order_item_id=c.item_order_id INNER JOIN mlfm_item e ON c.item_id=e.item_id INNER JOIN mlfm_uom f ON e.uom_id=f.uom_id INNER JOIN mlfm_production g ON d.order_item_qty_id=g.ORDER_ITEM_QTY_ID INNER JOIN mlfm_qc h ON g.PRODUCTION_ID=h.PRODUCTION_ID INNER JOIN mlfm_mending i ON i.PRODUCTION_ID=h.PRODUCTION_ID INNER JOIN mlfm_washing j ON j.PRODUCTION_ID=i.PRODUCTION_ID INNER JOIN mlfm_finishing k ON k.PRODUCTION_ID=j.PRODUCTION_ID INNER JOIN mlfm_packing l ON k.PRODUCTION_ID=l.PRODUCTION_ID where a.order_owner_type_id=coalesce(:order_owner_type_id, a.order_owner_type_id)";
				
		String qry="SELECT a.PRODUCTION_ID,a.PRODUCTION_DATE,a.ORDER_ID,g.owner_name,a.PRODUCTION_QTY," +
				" g.order_owner_id,f.order_date, c.item_order_id,b.order_item_qty_id,b.item_qty,d.item_name," +
				" e.uom_name,null qc_qty, null mended_qty, null mended_qty, null washed_qty, null finished_qty   FROM mlfm_production a INNER JOIN mlfm_order_item_qty b on a.ORDER_ITEM_QTY_ID=b.order_item_qty_id " +
				" and a.ACTIVE_STATUS=1  inner join mlfm_order_item c on c.item_order_id=b.order_item_id inner join " +
				" mlfm_item d on  d.item_id=c.item_id inner join mlfm_uom e on e.uom_id=b.uom_id inner join mlfm_order f on f.order_id=c.order_id " +
				" inner join mlfm_order_owner g on g.order_owner_id=f.order_owner_id inner join mlfm_order_owner_type h" +
				" on h.order_owner_type_id=g.order_owner_type_id" +
				" WHERE  h.order_owner_type_id=coalesce(:order_owner_type_id,h.order_owner_type_id)\r\n" + 
				" and g.order_owner_id=coalesce(:order_owner_id, g.order_owner_id) and f.order_id=coalesce(:order_id, f.order_id) "+
				" and a.production_date BETWEEN coalesce(:start_date,a.production_date) AND coalesce(:end_date,a.production_date)";
				
		
		/*
		String qry="SELECT a.PRODUCTION_ID,PRODUCTION_DATE,a.ORDER_ID,c.owner_name,a.PRODUCTION_QTY,b.order_owner_id,b.order_date, d.item_order_id,e.order_item_qty_id,e.item_qty,f.item_name,g.uom_name,h.QC_QTY,i.MENDED_QTY,i.MENDING_ID,j.WASHED_QTY,j.WASHING_ID,k.FINISHED_QTY,l.PACKED_QTY,l.PACKING_ID FROM mlfm_production a INNER JOIN mlfm_order b on a.ORDER_ID=b.order_id INNER JOIN mlfm_order_owner c ON b.order_owner_id=c.order_owner_id INNER JOIN mlfm_order_item d ON a.ORDER_ID=d.order_id INNER JOIN mlfm_order_item_qty e ON d.item_order_id=e.order_item_id INNER JOIN mlfm_item f ON f.item_id=d.item_id INNER JOIN mlfm_uom g ON f.uom_id=g.uom_id INNER JOIN mlfm_qc h ON a.PRODUCTION_ID=h.PRODUCTION_ID INNER JOIN mlfm_mending i ON h.PRODUCTION_ID=i.PRODUCTION_ID INNER JOIN mlfm_washing j ON i.PRODUCTION_ID=j.PRODUCTION_ID INNER JOIN mlfm_finishing k ON j.PRODUCTION_ID=k.PRODUCTION_ID INNER JOIN mlfm_packing l ON l.PRODUCTION_ID=k.PRODUCTION_ID WHERE NOT EXISTS (SELECT 1 FROM mlfm_gate_pass_chd o WHERE o.PRODUCTION_ID=a.PRODUCTION_ID) and b.order_owner_type_id=coalesce(:order_owner_type_id, b.order_owner_type_id)"+
				" and b.order_owner_id=coalesce(:order_owner_id, b.order_owner_id) and a.order_id=coalesce(:order_id, a.order_id) and a.production_date BETWEEN coalesce(:start_date,a.production_date) AND coalesce(:end_date,a.production_date)";
				
				*/
		
		RowMapper<ModelWashingCustom> serviceMapper=new RowMapper<ModelWashingCustom>() {
			@Override
			public ModelWashingCustom mapRow(ResultSet rs, int rownum) throws SQLException{
				ModelWashingCustom bn=new ModelWashingCustom();
		        
				bn.setInquiryId(rs.getLong("order_id"));
				bn.setInquiryDate(rs.getDate("order_date"));
				bn.setInquiryItemQtyId(rs.getLong("order_item_qty_id"));
				//bn.setBuyerId(rs.getLong("order_onwer_id"));
				bn.setBuyerId(rs.getLong("order_owner_id"));
				bn.setBuyerName(rs.getString("owner_name"));
				bn.setItemName(rs.getString("item_name"));
				bn.setInquiryItemQty(rs.getDouble("item_qty"));
				bn.setUomName(rs.getString("uom_name"));
				bn.setProductionId(rs.getLong("production_id"));
				bn.setProductionDate(rs.getDate("production_date"));
				bn.setProductionQty(rs.getDouble("production_qty"));
				
				
				bn.setqCQty(rs.getDouble("qc_qty"));
				bn.setMendedQty(rs.getDouble("mended_qty"));
				bn.setWashedQty(rs.getDouble("washed_qty"));
				bn.setFinishedQty(rs.getDouble("finished_qty"));
				
				
				
			
				
			return bn;
			
			}	
		};
		

		MapSqlParameterSource parameters = new MapSqlParameterSource();
	      
	      parameters.addValue("order_owner_type_id", onwerType);
	      parameters.addValue("order_owner_id", owner);
	      parameters.addValue("order_id", orderId);
	      parameters.addValue("start_date", startDate);
	      parameters.addValue("end_date", endDate);
	      
	      System.out.println("ownerType Id :" + onwerType);
	      System.out.println("owner Id :" + owner);
	      System.out.println("order Id :" + orderId);
	      System.out.println("Start Date :" + startDate);
	      System.out.println("End Date :" + endDate);
	   
	      

		return  jdbcTemplate.query(qry,parameters,serviceMapper);
	}

	@Override
	public List<ModelWashingCustom> getWorkOrderSearchData(Long ownerType,Long owner,Long orderId,Date startDate,Date endDate) {
  
	 //String qry="SELECT a.PRODUCTION_ID,a.PRODUCTION_DATE,a.PRODUCTION_QTY,b.ORDER_ITEM_QTY_ID,b.ITEM_QTY,i.QC_QTY,c.WO_MST_ID,c.WO_DATE,d.owner_name,g.item_name,d.order_owner_id,j.MENDED_QTY,k.WASHED_QTY,l.FINISHED_QTY,m.PACKED_QTY,n.uom_name FROM mlfm_production a INNER JOIN mlfm_wo_chd b ON a.WO_CHD_ID=b.WO_CHD_ID INNER JOIN mlfm_wo_mst c ON b.WO_MST_ID=c.WO_MST_ID INNER JOIN mlfm_order_owner d ON c.ORDER_OWNER_ID=d.order_owner_id INNER JOIN mlfm_order_item_qty e ON e.order_item_qty_id=b.ORDER_ITEM_QTY_ID INNER JOIN mlfm_order_item f ON f.item_order_id=e.order_item_id INNER JOIN mlfm_item g ON g.item_id=f.item_id INNER JOIN mlfm_order h ON h.order_id=f.order_id INNER JOIN mlfm_qc i ON a.PRODUCTION_ID=i.PRODUCTION_ID INNER JOIN mlfm_mending j ON j.PRODUCTION_ID=i.PRODUCTION_ID INNER JOIN mlfm_washing k ON k.PRODUCTION_ID=j.PRODUCTION_ID INNER JOIN mlfm_finishing l ON l.PRODUCTION_ID=k.PRODUCTION_ID INNER JOIN mlfm_packing m ON l.PRODUCTION_ID=m.PRODUCTION_ID INNER JOIN mlfm_uom n ON n.uom_id=g.uom_id where h.order_owner_type_id=coalesce(:order_owner_type_id, h.order_owner_type_id)";
		
		String qry="SELECT a.PRODUCTION_ID,a.PRODUCTION_DATE,a.ORDER_ID,i.owner_name,a.PRODUCTION_QTY,i.order_owner_id,c.WO_DATE, d.order_item_qty_id,d.item_qty,f.item_name,g.uom_name,c.WO_MST_ID,c.WO_DATE FROM mlfm_production a INNER JOIN mlfm_wo_chd b ON a.WO_CHD_ID=b.WO_CHD_ID INNER JOIN mlfm_wo_mst c ON b.WO_MST_ID=c.WO_MST_ID INNER JOIN mlfm_order_item_qty d on b.ORDER_ITEM_QTY_ID=d.order_item_qty_id inner join mlfm_order_item e on d.order_item_id=e.item_order_id inner join mlfm_item f on e.item_id=f.item_id inner join mlfm_uom g on f.uom_id=g.uom_id inner join mlfm_order h on e.order_id=h.order_id inner join mlfm_order_owner i on h.order_owner_id=i.order_owner_id INNER JOIN mlfm_order_owner_type j ON i.order_owner_type_id=j.order_owner_type_id"+
				" WHERE  j.order_owner_type_id=coalesce(:order_owner_type_id,j.order_owner_type_id)\r\n" + 
				" and i.order_owner_id=coalesce(:order_owner_id, i.order_owner_id) and h.order_id=coalesce(:order_id, h.order_id) "+
				" and a.production_date BETWEEN coalesce(:start_date,a.production_date) AND coalesce(:end_date,a.production_date)";
				
		
		RowMapper<ModelWashingCustom> serviceMapper=new RowMapper<ModelWashingCustom>() {
			@Override
			public ModelWashingCustom mapRow(ResultSet rs, int rownum) throws SQLException{
				ModelWashingCustom bn=new ModelWashingCustom();
		        
				bn.setwOId(rs.getLong("wo_mst_id"));
				bn.setwODate(rs.getDate("wo_date"));
				bn.setInquiryItemQtyId(rs.getLong("order_item_qty_id"));
				bn.setBuyerId(rs.getLong("order_owner_id"));
				bn.setBuyerName(rs.getString("owner_name"));
				bn.setItemName(rs.getString("item_name"));
				bn.setInquiryItemQty(rs.getDouble("item_qty"));
				bn.setUomName(rs.getString("uom_name"));
				bn.setProductionId(rs.getLong("production_id"));
				bn.setProductionDate(rs.getDate("production_date"));
				bn.setProductionQty(rs.getDouble("production_qty"));
				
				
				
			
				
			return bn;
			
			}	
		};
		

		MapSqlParameterSource parameters = new MapSqlParameterSource();
	      
	      parameters.addValue("order_owner_type_id", ownerType);
	      parameters.addValue("order_owner_id", owner);
	      parameters.addValue("order_id", orderId);
	      parameters.addValue("start_date", startDate);
	      parameters.addValue("end_date", endDate);
	      

		return  jdbcTemplate.query(qry,parameters,serviceMapper);
	}

	@Override
	public void saveGatePassMst(ModelGatePassMst gatePassMst) {
		
		gatePassRepository.save(gatePassMst);
		
	}

	@Override
	public List<ModelGatePassCustom> getIGatePassMstData(Long id) {
		
		String qry="SELECT a.GATE_PASS_MST_ID,a.GATE_PASS_DATE,a.ISSUER_ID,a.PURPOSE,a.VEHICLE_TYPE_NAME,a.VEHICLE_NUMBER,a.VEHICLE_DRIVER_NAME,a.REMARKS,a.VEHICLE_DRIVER_CONTACT_NO,a.GATE_PASS_TYPE,b.order_owner_id,b.owner_name,c.user_name FROM mlfm_gate_pass_mst a INNER JOIN mlfm_order_owner b ON a.ORDER_OWNER_ID=b.order_owner_id INNER JOIN bg_user c ON a.ISSUER_ID=c.user_id WHERE a.GATE_PASS_MST_ID=coalesce(:id, a.GATE_PASS_MST_ID)";
		RowMapper<ModelGatePassCustom> serviceMapper=new RowMapper<ModelGatePassCustom>() {
			@Override
			public ModelGatePassCustom mapRow(ResultSet rs, int rownum) throws SQLException{
				ModelGatePassCustom bn=new ModelGatePassCustom();
		        
				bn.setGatePassMstId(rs.getLong("gate_pass_mst_id"));
				bn.setGatePassDate(rs.getDate("gate_pass_date"));
				bn.setGatePassType(rs.getInt("gate_pass_type"));
				bn.setIssuer(rs.getLong("issuer_id"));
				bn.setPurpose(rs.getString("purpose"));
				bn.setVehicleTypeName(rs.getString("vehicle_type_name"));
				bn.setVehicleNumber(rs.getString("vehicle_number"));
				bn.setDriverName(rs.getString("vehicle_driver_name"));
				bn.setContactNo(rs.getString("vehicle_driver_contact_no"));
				bn.setOrderOwnerId(rs.getLong("order_owner_id"));
				bn.setOwnerName(rs.getString("owner_name"));
				bn.setIssuerName(rs.getString("user_name"));
				bn.setRemarks(rs.getString("remarks"));
						
				
			return bn;
			
			}	
		};
		

		MapSqlParameterSource parameters = new MapSqlParameterSource();
	      
	      parameters.addValue("id",id);
	      

		return  jdbcTemplate.query(qry,parameters,serviceMapper);
	}
	
	public void updateGatePassMst(ModelGatePassMst gatePass){
        try {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.update("update MLFM_GATE_PASS_MST set gate_pass_date=?,active_status=?,updated_by=?,update_timestamp=?,vehicle_type_name=?,vehicle_number=?,vehicle_driver_name=?,vehicle_driver_contact_no=?,issuer_id=?,purpose=?,gate_pass_type=?,remarks=? where gate_pass_mst_id=?",
        		gatePass.getGatePassDate(),gatePass.getActiveStatus(),gatePass.getUpdatedBy(),gatePass.getUpdateTimestamp(),gatePass.getVehicleTypeName(),gatePass.getVehicleNumber(),gatePass.getVehicleDriverName(),gatePass.getVehicleDriverContactNo(),gatePass.getModelUser().getUserId(),gatePass.getPurpose(),gatePass.getGatePassType(),gatePass.getRemarks(),gatePass.getGatePassMstId());
       }
        
        
        catch(Exception e) {
        	e.printStackTrace();
}

	}

	@Override
	public List<ModelWashingCustom> getInquiryDataById(Long id) {
		//String qry="SELECT a.PRODUCTION_ID,PRODUCTION_DATE,a.ORDER_ID,c.owner_name,a.PRODUCTION_QTY,b.order_owner_id,b.order_date, d.item_order_id,e.order_item_qty_id,e.item_qty,f.item_name,g.uom_name,h.QC_QTY,i.MENDED_QTY,i.MENDING_ID,j.WASHED_QTY,j.WASHING_ID,k.FINISHED_QTY,l.PACKED_QTY,l.PACKING_ID FROM mlfm_production a INNER JOIN mlfm_order b on a.ORDER_ID=b.order_id INNER JOIN mlfm_order_owner c ON b.order_owner_id=c.order_owner_id INNER JOIN mlfm_order_item d ON a.ORDER_ID=d.order_id INNER JOIN mlfm_order_item_qty e ON d.item_order_id=e.order_item_id INNER JOIN mlfm_item f ON f.item_id=d.item_id INNER JOIN mlfm_uom g ON f.uom_id=g.uom_id INNER JOIN mlfm_qc h ON a.PRODUCTION_ID=h.PRODUCTION_ID INNER JOIN mlfm_mending i ON h.PRODUCTION_ID=i.PRODUCTION_ID INNER JOIN mlfm_washing j ON i.PRODUCTION_ID=j.PRODUCTION_ID INNER JOIN mlfm_finishing k ON j.PRODUCTION_ID=k.PRODUCTION_ID INNER JOIN mlfm_packing l ON l.PRODUCTION_ID=k.PRODUCTION_ID where a.PRODUCTION_ID=coalesce(:PRODUCTION_ID, a.PRODUCTION_ID)";
				
		String qry="SELECT a.PRODUCTION_ID,a.PRODUCTION_DATE,a.ORDER_ID,g.owner_name,a.PRODUCTION_QTY," + 
				" g.order_owner_id,f.order_date, c.item_order_id,b.order_item_qty_id,b.item_qty,d.item_name," + 
				" e.uom_name,null qc_qty, null mended_qty, null mended_qty, null washed_qty, null finished_qty   FROM mlfm_production a INNER JOIN mlfm_order_item_qty b on a.ORDER_ITEM_QTY_ID=b.order_item_qty_id" + 
				" and a.ACTIVE_STATUS=1  inner join mlfm_order_item c on c.item_order_id=b.order_item_id inner join" + 
				" mlfm_item d on  d.item_id=c.item_id inner join mlfm_uom e on e.uom_id=b.uom_id inner join mlfm_order f on f.order_id=c.order_id" + 
				" inner join mlfm_order_owner g on g.order_owner_id=f.order_owner_id inner join mlfm_order_owner_type h" + 
				" on h.order_owner_type_id=g.order_owner_type_id" + 
				" WHERE  a.PRODUCTION_ID=coalesce(:PRODUCTION_ID,a.PRODUCTION_ID)";		
				
		
		RowMapper<ModelWashingCustom> serviceMapper=new RowMapper<ModelWashingCustom>() {
			@Override
			public ModelWashingCustom mapRow(ResultSet rs, int rownum) throws SQLException{
				ModelWashingCustom bn=new ModelWashingCustom();
		        
				bn.setInquiryId(rs.getLong("order_id"));
				bn.setInquiryDate(rs.getDate("order_date"));
				bn.setInquiryItemQtyId(rs.getLong("order_item_qty_id"));
				//bn.setBuyerId(rs.getLong("order_onwer_id"));
				bn.setBuyerId(rs.getLong("order_owner_id"));
				bn.setBuyerName(rs.getString("owner_name"));
				bn.setItemName(rs.getString("item_name"));
				bn.setInquiryItemQty(rs.getDouble("item_qty"));
				bn.setUomName(rs.getString("uom_name"));
				bn.setProductionId(rs.getLong("production_id"));
				bn.setProductionDate(rs.getDate("production_date"));
				bn.setProductionQty(rs.getDouble("production_qty"));
				
				
				
			
				
			return bn;
			
			}	
		};
		

		MapSqlParameterSource parameters = new MapSqlParameterSource();
	      
	      parameters.addValue("PRODUCTION_ID", id);
	     
	      

		return  jdbcTemplate.query(qry,parameters,serviceMapper);
	}

	@Override
	public List<ModelGatePassCustom> getAllData() {
		String qry="SELECT a.GATE_PASS_MST_ID,a.GATE_PASS_DATE,a.ISSUER_ID,a.PURPOSE,a.VEHICLE_TYPE_NAME,a.VEHICLE_NUMBER,a.VEHICLE_DRIVER_NAME,a.VEHICLE_DRIVER_CONTACT_NO,a.GATE_PASS_TYPE,b.order_owner_id,b.owner_name,c.user_name FROM mlfm_gate_pass_mst a INNER JOIN mlfm_order_owner b ON a.ORDER_OWNER_ID=b.order_owner_id INNER JOIN bg_user c ON a.ISSUER_ID=c.user_id WHERE NOT EXISTS(SELECT 1 FROM mlfm_gate_pass_chd d WHERE a.GATE_PASS_MST_ID=d.GATE_PASS_MST_ID) AND a.ACTIVE_STATUS=1";
		RowMapper<ModelGatePassCustom> serviceMapper=new RowMapper<ModelGatePassCustom>() {
			@Override
			public ModelGatePassCustom mapRow(ResultSet rs, int rownum) throws SQLException{
				ModelGatePassCustom bn=new ModelGatePassCustom();
		        
				bn.setGatePassMstId(rs.getLong("gate_pass_mst_id"));
				bn.setGatePassDate(rs.getDate("gate_pass_date"));
				bn.setGatePassType(rs.getInt("gate_pass_type"));
				bn.setIssuer(rs.getLong("issuer_id"));
				bn.setPurpose(rs.getString("purpose"));
				bn.setVehicleTypeName(rs.getString("vehicle_type_name"));
				bn.setVehicleNumber(rs.getString("vehicle_number"));
				bn.setDriverName(rs.getString("vehicle_driver_name"));
				bn.setContactNo(rs.getString("vehicle_driver_contact_no"));
				bn.setOrderOwnerId(rs.getLong("order_owner_id"));
				bn.setOwnerName(rs.getString("owner_name"));
				bn.setIssuerName(rs.getString("user_name"));
						
				
			return bn;
			
			}	
		};
		

		return  jdbcTemplate.query(qry,serviceMapper);
	}

	@Override
	public List<ModelWashingCustom> getGetPassDoneSearchData(Long onwerType, Long owner, Long orderId
			) {
		//String qry="SELECT a.PRODUCTION_ID,PRODUCTION_DATE,a.ORDER_ID,c.owner_name,a.PRODUCTION_QTY,b.order_owner_id,b.order_date, d.item_order_id,e.order_item_qty_id,e.item_qty,f.item_name,g.uom_name,h.QC_QTY,i.MENDED_QTY,i.MENDING_ID,j.WASHED_QTY,j.WASHING_ID,k.FINISHED_QTY,l.PACKED_QTY,l.PACKING_ID FROM mlfm_production a INNER JOIN mlfm_order b on a.ORDER_ID=b.order_id INNER JOIN mlfm_order_owner c ON b.order_owner_id=c.order_owner_id INNER JOIN mlfm_order_item d ON a.ORDER_ID=d.order_id INNER JOIN mlfm_order_item_qty e ON d.item_order_id=e.order_item_id INNER JOIN mlfm_item f ON f.item_id=d.item_id INNER JOIN mlfm_uom g ON f.uom_id=g.uom_id INNER JOIN mlfm_qc h ON a.PRODUCTION_ID=h.PRODUCTION_ID INNER JOIN mlfm_mending i ON h.PRODUCTION_ID=i.PRODUCTION_ID INNER JOIN mlfm_washing j ON i.PRODUCTION_ID=j.PRODUCTION_ID INNER JOIN mlfm_finishing k ON j.PRODUCTION_ID=k.PRODUCTION_ID INNER JOIN mlfm_packing l ON l.PRODUCTION_ID=k.PRODUCTION_ID WHERE EXISTS (SELECT 1 FROM mlfm_gate_pass_chd o WHERE o.PRODUCTION_ID=a.PRODUCTION_ID ) and b.order_owner_type_id=coalesce(:order_owner_type_id, b.order_owner_type_id)"+
				//" and b.order_owner_id=coalesce(:order_owner_id, b.order_owner_id) and a.order_id=coalesce(:order_id, a.order_id) and a.production_date BETWEEN coalesce(:start_date,a.production_date) AND coalesce(:end_date,a.production_date)";
		
	/*	String qry="SELECT a.PRODUCTION_ID,a.PRODUCTION_DATE,a.ORDER_ID,g.owner_name,a.PRODUCTION_QTY," + 
				" g.order_owner_id,f.order_date, c.item_order_id,b.order_item_qty_id,b.item_qty,d.item_name," + 
				" e.uom_name,null qc_qty, null mended_qty, null mended_qty, null washed_qty, null finished_qty   FROM mlfm_production a INNER JOIN mlfm_order_item_qty b on a.ORDER_ITEM_QTY_ID=b.order_item_qty_id" + 
				" and a.ACTIVE_STATUS=1  inner join mlfm_order_item c on c.item_order_id=b.order_item_id inner join" + 
				" mlfm_item d on  d.item_id=c.item_id inner join mlfm_uom e on e.uom_id=b.uom_id inner join mlfm_order f on f.order_id=c.order_id" + 
				" inner join mlfm_order_owner g on g.order_owner_id=f.order_owner_id inner join mlfm_order_owner_type h" + 
				" on h.order_owner_type_id=g.order_owner_type_id" + 
				" WHERE EXISTS(SELECT 1 FROM mlfm_gate_pass_chd k WHERE a.PRODUCTION_ID=k.PRODUCTION_ID) and h.order_owner_type_id=coalesce(:order_owner_type_id,h.order_owner_type_id)" + 
				" and g.order_owner_id=coalesce(:order_owner_id, g.order_owner_id) and f.order_id=coalesce(:order_id, f.order_id)" + 
				" and a.production_date BETWEEN coalesce(:start_date,a.production_date) AND coalesce(:end_date,a.production_date)"; 
		*/
		
		
		 /* String qry="SELECT a.gate_pass_chd_id,b.GATE_PASS_MST_ID,c.production_id,c.production_qty,c.production_date,h.order_id,h.order_date,i.order_owner_id,i.owner_name,d.order_item_qty_id,f.item_name,d.item_qty,g.uom_name FROM mlfm_gate_pass_chd a INNER JOIN mlfm_gate_pass_mst b ON a.GATE_PASS_MST_ID=b.GATE_PASS_MST_ID INNER JOIN mlfm_production c ON a.PRODUCTION_ID=c.PRODUCTION_ID INNER JOIN mlfm_order_item_qty d ON c.ORDER_ITEM_QTY_ID=d.order_item_qty_id INNER JOIN mlfm_order_item e ON d.order_item_id=e.item_order_id INNER JOIN mlfm_item f ON f.item_id=e.item_id INNER JOIN mlfm_uom g ON f.uom_id=g.uom_id INNER JOIN mlfm_order h ON e.order_id=h.order_id INNER JOIN mlfm_order_owner i ON h.order_owner_id=i.order_owner_id INNER JOIN mlfm_order_owner_type j ON i.order_owner_type_id=j.order_owner_type_id" + 
				" where j.order_owner_type_id=coalesce(:order_owner_type_id,j.order_owner_type_id)"+ 
				" and i.order_owner_id=coalesce(:order_owner_id,i.order_owner_id) and h.order_id=coalesce(:order_id, h.order_id)" + 
				" and b.gate_pass_date BETWEEN coalesce(:start_date,b.gate_pass_date) AND coalesce(:end_date,b.gate_pass_date)"; 
		 */ 
		
		
		/* String qry="SELECT a.PRODUCTION_ID,a.PRODUCTION_DATE,a.ORDER_ID,g.owner_name,a.PRODUCTION_QTY, g.order_owner_id,f.order_date, c.item_order_id,b.order_item_qty_id,b.item_qty,d.item_name,e.uom_name,null qc_qty, null mended_qty, null mended_qty, null washed_qty, null finished_qty   FROM mlfm_production a INNER JOIN mlfm_order_item_qty b on a.ORDER_ITEM_QTY_ID=b.order_item_qty_id 	and a.ACTIVE_STATUS=1 inner join mlfm_order_item c on c.item_order_id=b.order_item_id inner join mlfm_item d on  d.item_id=c.item_id inner join mlfm_uom e on e.uom_id=b.uom_id inner join mlfm_order f on f.order_id=c.order_id inner join mlfm_order_owner g on g.order_owner_id=f.order_owner_id inner join mlfm_order_owner_type h on h.order_owner_type_id=g.order_owner_type_id WHERE EXISTS(SELECT 1 FROM mlfm_gate_pass_chd t WHERE t.PRODUCTION_ID=a.PRODUCTION_ID)"+
				" where j.order_owner_type_id=coalesce(:order_owner_type_id,j.order_owner_type_id)"+ 
				" and i.order_owner_id=coalesce(:order_owner_id,i.order_owner_id) and h.order_id=coalesce(:order_id, h.order_id)" + 
				" and b.gate_pass_date BETWEEN coalesce(:start_date,b.gate_pass_date) AND coalesce(:end_date,b.gate_pass_date)"; 
		*/
		
		String qry="SELECT a.PRODUCTION_ID,a.PRODUCTION_DATE,a.ORDER_ID,g.owner_name,a.PRODUCTION_QTY, g.order_owner_id,f.order_date, c.item_order_id,b.order_item_qty_id,b.item_qty,d.item_name,e.uom_name FROM mlfm_production a INNER JOIN mlfm_order_item_qty b on a.ORDER_ITEM_QTY_ID=b.order_item_qty_id 	and a.ACTIVE_STATUS=1 inner join mlfm_order_item c on c.item_order_id=b.order_item_id inner join mlfm_item d on  d.item_id=c.item_id inner join mlfm_uom e on e.uom_id=b.uom_id inner join mlfm_order f on f.order_id=c.order_id inner join mlfm_order_owner g on g.order_owner_id=f.order_owner_id inner join mlfm_order_owner_type h on h.order_owner_type_id=g.order_owner_type_id WHERE EXISTS(SELECT 1 FROM mlfm_gate_pass_chd t WHERE t.PRODUCTION_ID=a.PRODUCTION_ID)"+
				" and h.order_owner_type_id=coalesce(:order_owner_type_id,h.order_owner_type_id)"+ 
				" and g.order_owner_id=coalesce(:order_owner_id,g.order_owner_id) and f.order_id=coalesce(:order_id, f.order_id)"; 
		
		 String qry_sum_item_qty ="SELECT sum(a.ITEM_QTY) FROM mlfm_gate_pass_chd a WHERE a.PRODUCTION_ID=?";
		
		
		
		RowMapper<ModelWashingCustom> serviceMapper=new RowMapper<ModelWashingCustom>() {
			@Override
			public ModelWashingCustom mapRow(ResultSet rs, int rownum) throws SQLException{
				
				Double qtySaved;
				
				ModelWashingCustom bn=new ModelWashingCustom();
		        
				bn.setInquiryId(rs.getLong("order_id"));
				bn.setInquiryDate(rs.getDate("order_date"));
				bn.setInquiryItemQtyId(rs.getLong("order_item_qty_id"));
				//bn.setBuyerId(rs.getLong("order_onwer_id"));
				bn.setBuyerId(rs.getLong("order_owner_id"));
				bn.setBuyerName(rs.getString("owner_name"));
				bn.setItemName(rs.getString("item_name"));
				bn.setInquiryItemQty(rs.getDouble("item_qty"));
				bn.setUomName(rs.getString("uom_name"));
				bn.setProductionId(rs.getLong("production_id"));
				bn.setProductionDate(rs.getDate("production_date"));
				bn.setProductionQty(rs.getDouble("production_qty"));
				
				
				Object[] inputs = new Object[] {bn.getProductionId()};
		        qtySaved = jdbc.queryForObject(qry_sum_item_qty, inputs, Double.class);
		        
               if(qtySaved==null) {
		        	
		        	double qty=0;
		        	bn.setFinishedQty(qty);
		        	
		        }
		        
		       else {
		        	 bn.setFinishedQty(qtySaved);
		            }
			
				
			return bn;
			
			}	
		};
		

		MapSqlParameterSource parameters = new MapSqlParameterSource();
	      
	      parameters.addValue("order_owner_type_id", onwerType);
	      parameters.addValue("order_owner_id", owner);
	      parameters.addValue("order_id", orderId);
	     // parameters.addValue("start_date", startDate);
	     // parameters.addValue("end_date", endDate);
	      
	      System.out.println("ownerType Id :" + onwerType);
	      System.out.println("owner Id :" + owner);
	      System.out.println("order Id :" + orderId);
	     // System.out.println("Start Date :" + startDate);
	     /// System.out.println("End Date :" + endDate);
	   
	      

		return  jdbcTemplate.query(qry,parameters,serviceMapper);
	}

}
