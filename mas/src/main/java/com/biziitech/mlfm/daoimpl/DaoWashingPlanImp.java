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

import com.biziitech.mlfm.custom.model.ModelMendingPlanCustom;
import com.biziitech.mlfm.custom.model.ModelWashingPlanCustom;
import com.biziitech.mlfm.dao.DaoWashingPlan;
import com.biziitech.mlfm.model.ModelWashingPlan;
import com.biziitech.mlfm.repository.WashingPlanRepository;

@Service
public class DaoWashingPlanImp implements DaoWashingPlan{
	
	@Autowired
	private WashingPlanRepository washingPlanRepository;
	
	@Autowired
    private DataSource dataSource;
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
    
		
    public void setNamedDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.namedParameterJdbcTemplate=namedParameterJdbcTemplate;
          }
	

	@Override
	public void saveWashingPlan(ModelWashingPlan modelWashingPlan) {
		// TODO Auto-generated method stub
		washingPlanRepository.save(modelWashingPlan);
		
	}

	@Override
	public void deleteWashingPlan(Long washingPlanId) {
		// TODO Auto-generated method stub
		washingPlanRepository.deleteById(washingPlanId);
	}

	@Override
	public List<ModelWashingPlanCustom> getPendingWashingPlanDetails(Long pOId, Long buyerId, Date startDate,
			Date endDate) {
		// TODO Auto-generated method stub
		
		/*
		String qry="SELECT a.PO_MST_ID,a.PO_MST_DATE,\r\n" + 
				"a.PO_QTY,a.uom_id,a.uom_name,a.WO_CHD_ID,a.WO_MST_ID,a.WO_DATE,\r\n" + 
				"a.order_owner_id,a.owner_name,a.item_id,a.item_name,\r\n" + 
				"a.WASHING_PLAN_ID,a.WASHING_PLAN_DATE,a.WASHING_PLAN_QTY\r\n" + 
				"FROM (\r\n" + 
				"SELECT a.PO_MST_ID,a.PO_MST_DATE,\r\n" + 
				"b.PO_QTY,b.uom_id,c.uom_name,f.WO_CHD_ID,h.WO_MST_ID,h.WO_DATE,\r\n" + 
				"j.order_owner_id,k.owner_name,l.item_id,m.item_name,\r\n" + 
				"n.WASHING_PLAN_ID,n.WASHING_PLAN_DATE,n.WASHING_PLAN_QTY\r\n" + 
				"FROM mlfm_po_mst a\r\n" + 
				"INNER JOIN mlfm_po_chd b on a.PO_MST_ID=b.PO_MST_ID\r\n" + 
				"INNER JOIN mlfm_uom c on b.UOM_ID=c.uom_id\r\n" + 
				"INNER JOIN mlfm_production_plan_chd e on b.PRODUCTION_PLAN_CHD_ID=e.PRODUCTION_PLAN_CHD_ID\r\n" + 
				"INNER JOIN mlfm_production_plan_mst f on e.PRODUCTION_PLAN_MST_ID=f.PRODUCTION_PLAN_MST_ID\r\n" + 
				"INNER JOIN mlfm_wo_chd g on f.WO_CHD_ID=g.WO_CHD_ID\r\n" + 
				"INNER JOIN mlfm_wo_mst h on g.WO_MST_ID=h.WO_MST_ID\r\n" + 
				"INNER JOIN mlfm_design i on a.DESIGN_ID=i.design_id\r\n" + 
				"INNER JOIN mlfm_order j on i.order_id=j.order_id\r\n" + 
				"INNER JOIN mlfm_order_owner k on j.order_owner_id=k.order_owner_id\r\n" + 
				"INNER JOIN mlfm_order_item l on j.order_id=l.order_id\r\n" + 
				"INNER JOIN mlfm_item m on l.item_id=m.item_id\r\n" + 
				"LEFT OUTER JOIN mlfm_washing_plan n on a.PO_MST_ID=n.PO_MST_ID\r\n" + 
				") a where\r\n" + 
				"a.PO_MST_ID=coalesce(:pOId,a.PO_MST_ID) and a.order_owner_id=coalesce(:buyerId,a.order_owner_id)\r\n" + 
				"and a.PO_MST_DATE BETWEEN coalesce(:startDate,a.PO_MST_DATE) AND coalesce(:endDate,a.PO_MST_DATE)\r\n" + 
				"group by a.PO_MST_ID,a.PO_MST_DATE,\r\n" + 
				"a.PO_QTY,a.uom_id,a.uom_name,a.WO_CHD_ID,a.WO_MST_ID,a.WO_DATE,\r\n" + 
				"a.order_owner_id,a.owner_name,a.item_id,a.item_name,\r\n" + 
				"a.WASHING_PLAN_ID,a.WASHING_PLAN_DATE,a.WASHING_PLAN_QTY";
		*/
		
		String qry ="SELECT a.PO_MST_ID,a.PO_MST_DATE,b.PO_QTY,b.uom_id,c.uom_name,f.WO_CHD_ID,h.WO_MST_ID,h.WO_DATE,\r\n" + 
				"j.order_owner_id,k.owner_name,l.item_id,m.item_name,n.WASHING_PLAN_ID,n.WASHING_PLAN_DATE,n.WASHING_PLAN_QTY\r\n" + 
				"FROM mlfm_po_mst a\r\n" + 
				"INNER JOIN mlfm_po_chd b on a.PO_MST_ID=b.PO_MST_ID \r\n" + 
				"LEFT OUTER JOIN mlfm_uom c on b.UOM_ID=c.uom_id\r\n" + 
				"INNER JOIN mlfm_production_plan_chd e on b.PRODUCTION_PLAN_CHD_ID=e.PRODUCTION_PLAN_CHD_ID\r\n" + 
				"INNER JOIN mlfm_production_plan_mst f on e.PRODUCTION_PLAN_MST_ID=f.PRODUCTION_PLAN_MST_ID\r\n" + 
				"INNER JOIN mlfm_wo_chd g on f.WO_CHD_ID=g.WO_CHD_ID\r\n" + 
				"INNER JOIN mlfm_wo_mst h on g.WO_MST_ID=h.WO_MST_ID \r\n" + 
				"INNER JOIN mlfm_design i on a.DESIGN_ID=i.design_id \r\n" + 
				"INNER JOIN mlfm_order j on i.order_id=j.order_id\r\n" + 
				"INNER JOIN mlfm_order_owner k on j.order_owner_id=k.order_owner_id\r\n" + 
				"INNER JOIN mlfm_order_item l on j.order_id=l.order_id\r\n" + 
				"INNER JOIN mlfm_item m on l.item_id=m.item_id\r\n" + 
				"LEFT OUTER JOIN mlfm_washing_plan n on a.PO_MST_ID=n.PO_MST_ID  \r\n" + 
				"where a.PO_MST_ID=coalesce(:pOId,a.PO_MST_ID) \r\n" + 
				"and k.order_owner_id=coalesce(:buyerId,k.order_owner_id) \r\n" + 
				"and a.PO_MST_DATE BETWEEN coalesce(:startDate,a.PO_MST_DATE) AND coalesce(:endDate,a.PO_MST_DATE)\r\n" + 
				"group by a.PO_MST_ID,a.PO_MST_DATE,b.PO_QTY,b.uom_id,c.uom_name,f.WO_CHD_ID,h.WO_MST_ID,h.WO_DATE, \r\n" + 
				"j.order_owner_id,k.owner_name,l.item_id,m.item_name, \r\n" + 
				"n.WASHING_PLAN_ID,n.WASHING_PLAN_DATE,n.WASHING_PLAN_QTY";
		
		
		
		/*
		 * 
		 * SELECT a.PO_MST_ID,a.PO_MST_DATE,b.PO_QTY,b.uom_id,c.uom_name,f.WO_CHD_ID,h.WO_MST_ID,h.WO_DATE,
j.order_owner_id,k.owner_name,l.item_id,m.item_name,n.WASHING_PLAN_ID,n.WASHING_PLAN_DATE,n.WASHING_PLAN_QTY
FROM mlfm_po_mst a
INNER JOIN mlfm_po_chd b on a.PO_MST_ID=b.PO_MST_ID 
LEFT OUTER JOIN mlfm_uom c on b.UOM_ID=c.uom_id
INNER JOIN mlfm_production_plan_chd e on b.PRODUCTION_PLAN_CHD_ID=e.PRODUCTION_PLAN_CHD_ID
INNER JOIN mlfm_production_plan_mst f on e.PRODUCTION_PLAN_MST_ID=f.PRODUCTION_PLAN_MST_ID
INNER JOIN mlfm_wo_chd g on f.WO_CHD_ID=g.WO_CHD_ID
INNER JOIN mlfm_wo_mst h on g.WO_MST_ID=h.WO_MST_ID 
INNER JOIN mlfm_design i on a.DESIGN_ID=i.design_id 
INNER JOIN mlfm_order j on i.order_id=j.order_id
INNER JOIN mlfm_order_owner k on j.order_owner_id=k.order_owner_id
INNER JOIN mlfm_order_item l on j.order_id=l.order_id
INNER JOIN mlfm_item m on l.item_id=m.item_id
LEFT OUTER JOIN mlfm_washing_plan n on a.PO_MST_ID=n.PO_MST_ID  
where a.PO_MST_ID=coalesce(@pOId,a.PO_MST_ID) 
and k.order_owner_id=coalesce(@buyerId,k.order_owner_id) 
and a.PO_MST_DATE BETWEEN coalesce(@startDate,a.PO_MST_DATE) AND coalesce(@endDate,a.PO_MST_DATE)
group by a.PO_MST_ID,a.PO_MST_DATE,b.PO_QTY,b.uom_id,c.uom_name,f.WO_CHD_ID,h.WO_MST_ID,h.WO_DATE, 
j.order_owner_id,k.owner_name,l.item_id,m.item_name, 
n.WASHING_PLAN_ID,n.WASHING_PLAN_DATE,n.WASHING_PLAN_QTY
		 * 
		 * */
		
		
		
		
		RowMapper<ModelWashingPlanCustom> serviceMapper=new RowMapper<ModelWashingPlanCustom>() {

			@Override
			public ModelWashingPlanCustom mapRow(ResultSet rs, int rownum) throws SQLException {
				// TODO Auto-generated method stub
				ModelWashingPlanCustom bn= new ModelWashingPlanCustom();
				Double washingPlanQtyUnDone=rs.getDouble("PO_QTY")-rs.getDouble("WASHING_PLAN_QTY");
				
				bn.setwOMstId(rs.getLong("WO_MST_ID"));
				bn.setwODate(rs.getDate("WO_DATE"));
				bn.setpOId(rs.getLong("po_mst_id"));
				bn.setpODate(rs.getDate("PO_MST_DATE"));
				bn.setpOQty(rs.getDouble("PO_QTY"));
				bn.setBuyerId(rs.getLong("ORDER_OWNER_ID"));
				bn.setBuyerName(rs.getString("OWNER_NAME"));
				bn.setItemId(rs.getLong("ITEM_ID"));
				bn.setItemName(rs.getString("ITEM_NAME"));
				bn.setWashingPlanId(rs.getLong("WASHING_PLAN_ID"));
				bn.setWashingPlanDate(rs.getDate("WASHING_PLAN_DATE"));
				bn.setWashingPlanQty(rs.getDouble("WASHING_PLAN_QTY"));
				bn.setWashingPlanQtyUnDone(washingPlanQtyUnDone);
				bn.setUomId(rs.getLong("UOM_ID"));
				bn.setUomName(rs.getString("UOM_NAME"));
				
				return bn;
			}

		};
		
		MapSqlParameterSource parameters = new MapSqlParameterSource();
	      parameters.addValue("pOId", pOId);
	      System.out.println(" in daoWashingPlanImp, pOId:" +pOId);
	      parameters.addValue("buyerId", buyerId);
	      System.out.println(" in daoWashingPlanImp, buyerId:" +buyerId);
	      parameters.addValue("startDate", startDate);
	      parameters.addValue("endDate", endDate);

		System.out.println("service mapper ");
		return namedParameterJdbcTemplate.query(qry,parameters,serviceMapper);
	}

	@Override
	public List<ModelWashingPlanCustom> getCompletedWashingPlanDetails(Long pOId, Long buyerId, Date startDate,
			Date endDate) {
		// TODO Auto-generated method stub
		String qry="SELECT a.PO_MST_ID,a.PO_MST_DATE,\r\n" + 
				"a.PO_QTY,a.uom_id,a.uom_name,a.WO_CHD_ID,a.WO_MST_ID,a.WO_DATE,\r\n" + 
				"a.order_owner_id,a.owner_name,a.item_id,a.item_name,\r\n" + 
				"a.WASHING_PLAN_ID,a.WASHING_PLAN_DATE,a.WASHING_PLAN_QTY\r\n" + 
				"FROM (\r\n" + 
				"SELECT a.PO_MST_ID,a.PO_MST_DATE,\r\n" + 
				"b.PO_QTY,b.uom_id,c.uom_name,f.WO_CHD_ID,h.WO_MST_ID,h.WO_DATE,\r\n" + 
				"j.order_owner_id,k.owner_name,l.item_id,m.item_name,\r\n" + 
				"n.WASHING_PLAN_ID,n.WASHING_PLAN_DATE,n.WASHING_PLAN_QTY\r\n" + 
				"FROM mlfm_washing_plan n\r\n" + 
				"INNER JOIN mlfm_po_mst a on n.PO_MST_ID=a.PO_MST_ID\r\n" + 
				"INNER JOIN mlfm_po_chd b on a.PO_MST_ID=b.PO_MST_ID\r\n" + 
				"INNER JOIN mlfm_uom c on b.UOM_ID=c.uom_id\r\n" + 
				"INNER JOIN mlfm_production_plan_chd e on b.PRODUCTION_PLAN_CHD_ID=e.PRODUCTION_PLAN_CHD_ID\r\n" + 
				"INNER JOIN mlfm_production_plan_mst f on e.PRODUCTION_PLAN_MST_ID=f.PRODUCTION_PLAN_MST_ID\r\n" + 
				"INNER JOIN mlfm_wo_chd g on f.WO_CHD_ID=g.WO_CHD_ID\r\n" + 
				"INNER JOIN mlfm_wo_mst h on g.WO_MST_ID=h.WO_MST_ID\r\n" + 
				"INNER JOIN mlfm_design i on a.DESIGN_ID=i.design_id\r\n" + 
				"INNER JOIN mlfm_order j on i.order_id=j.order_id\r\n" + 
				"INNER JOIN mlfm_order_owner k on j.order_owner_id=k.order_owner_id\r\n" + 
				"INNER JOIN mlfm_order_item l on j.order_id=l.order_id\r\n" + 
				"INNER JOIN mlfm_item m on l.item_id=m.item_id\r\n" + 
				") a where\r\n" + 
				"a.PO_MST_ID=coalesce(:pOId,a.PO_MST_ID) and a.order_owner_id=coalesce(:buyerId,a.order_owner_id)\r\n" + 
				"and a.WASHING_PLAN_DATE BETWEEN coalesce(:startDate,a.WASHING_PLAN_DATE) AND coalesce(:endDate,a.WASHING_PLAN_DATE)\r\n" + 
				"group by a.PO_MST_ID,a.PO_MST_DATE,\r\n" + 
				"a.PO_QTY,a.uom_id,a.uom_name,a.WO_CHD_ID,a.WO_MST_ID,a.WO_DATE,\r\n" + 
				"a.order_owner_id,a.owner_name,a.item_id,a.item_name,\r\n" + 
				"a.WASHING_PLAN_ID,a.WASHING_PLAN_DATE,a.WASHING_PLAN_QTY";
		
		RowMapper<ModelWashingPlanCustom> serviceMapper=new RowMapper<ModelWashingPlanCustom>() {

			@Override
			public ModelWashingPlanCustom mapRow(ResultSet rs, int rownum) throws SQLException {
				// TODO Auto-generated method stub
				ModelWashingPlanCustom bn= new ModelWashingPlanCustom();
				Double washingPlanQtyUnDone=rs.getDouble("PO_QTY")-rs.getDouble("WASHING_PLAN_QTY");
				
				bn.setwOMstId(rs.getLong("WO_MST_ID"));
				bn.setwODate(rs.getDate("WO_DATE"));
				bn.setpOId(rs.getLong("po_mst_id"));
				bn.setpODate(rs.getDate("PO_MST_DATE"));
				bn.setpOQty(rs.getDouble("PO_QTY"));
				bn.setBuyerId(rs.getLong("ORDER_OWNER_ID"));
				bn.setBuyerName(rs.getString("OWNER_NAME"));
				bn.setItemId(rs.getLong("ITEM_ID"));
				bn.setItemName(rs.getString("ITEM_NAME"));
				bn.setWashingPlanId(rs.getLong("WASHING_PLAN_ID"));
				bn.setWashingPlanDate(rs.getDate("WASHING_PLAN_DATE"));
				bn.setWashingPlanQty(rs.getDouble("WASHING_PLAN_QTY"));
				bn.setWashingPlanQtyUnDone(washingPlanQtyUnDone);
				bn.setUomId(rs.getLong("UOM_ID"));
				bn.setUomName(rs.getString("UOM_NAME"));
				
				return bn;
			}

		};
		
		MapSqlParameterSource parameters = new MapSqlParameterSource();
	      parameters.addValue("pOId", pOId);
	      System.out.println(" in daoWashingPlanImp, pOId:" +pOId);
	      parameters.addValue("buyerId", buyerId);
	      System.out.println(" in daoWashingPlanImp, buyerId:" +buyerId);
	      parameters.addValue("startDate", startDate);
	      parameters.addValue("endDate", endDate);

		System.out.println("service mapper ");
		return namedParameterJdbcTemplate.query(qry,parameters,serviceMapper);
	}

	@Override
	public List<ModelWashingPlanCustom> getWashingPlanById(Long washingPlanId) {
		// TODO Auto-generated method stub
		String qry="SELECT a.PO_MST_ID,a.PO_MST_DATE,\r\n" + 
				"a.PO_QTY,a.uom_id,a.uom_name,a.WO_CHD_ID,a.WO_MST_ID,a.WO_DATE,\r\n" + 
				"a.order_owner_id,a.owner_name,a.item_id,a.item_name, \r\n" + 
				"a.WASHING_PLAN_ID,a.WASHING_PLAN_DATE,a.REMARKS,a.ACTIVE_STATUS,a.WASHING_PLAN_QTY \r\n" + 
				"FROM (\r\n" + 
				"SELECT a.PO_MST_ID,a.PO_MST_DATE,\r\n" + 
				"b.PO_QTY,n.uom_id,c.uom_name,f.WO_CHD_ID,h.WO_MST_ID,h.WO_DATE,\r\n" + 
				"j.order_owner_id,k.owner_name,l.item_id,m.item_name,\r\n" + 
				"n.WASHING_PLAN_ID,n.WASHING_PLAN_DATE,n.REMARKS,n.ACTIVE_STATUS,n.WASHING_PLAN_QTY\r\n" + 
				"FROM mlfm_washing_plan n\r\n" + 
				"INNER JOIN mlfm_po_mst a on n.PO_MST_ID=a.PO_MST_ID\r\n" + 
				"INNER JOIN mlfm_po_chd b on a.PO_MST_ID=b.PO_MST_ID\r\n" + 
				"INNER JOIN mlfm_production_plan_chd e on b.PRODUCTION_PLAN_CHD_ID=e.PRODUCTION_PLAN_CHD_ID\r\n" + 
				"INNER JOIN mlfm_production_plan_mst f on e.PRODUCTION_PLAN_MST_ID=f.PRODUCTION_PLAN_MST_ID\r\n" + 
				"INNER JOIN mlfm_wo_chd g on f.WO_CHD_ID=g.WO_CHD_ID\r\n" + 
				"INNER JOIN mlfm_wo_mst h on g.WO_MST_ID=h.WO_MST_ID\r\n" + 
				"INNER JOIN mlfm_design i on a.DESIGN_ID=i.design_id\r\n" + 
				"INNER JOIN mlfm_order j on i.order_id=j.order_id\r\n" + 
				"INNER JOIN mlfm_order_owner k on j.order_owner_id=k.order_owner_id\r\n" + 
				"INNER JOIN mlfm_order_item l on j.order_id=l.order_id\r\n" + 
				"INNER JOIN mlfm_item m on l.item_id=m.item_id\r\n" + 
				"LEFT OUTER JOIN mlfm_uom c on n.UOM_ID=c.uom_id\r\n" + 
				") a where \r\n" + 
				"a.WASHING_PLAN_ID=coalesce(:washingPlanId,a.WASHING_PLAN_ID)";
		
		RowMapper<ModelWashingPlanCustom> serviceMapper=new RowMapper<ModelWashingPlanCustom>() {

			@Override
			public ModelWashingPlanCustom mapRow(ResultSet rs, int rownum) throws SQLException {
				// TODO Auto-generated method stub
				ModelWashingPlanCustom bn= new ModelWashingPlanCustom();
				Double washingPlanQtyUnDone=rs.getDouble("PO_QTY")-rs.getDouble("WASHING_PLAN_QTY");
				
				bn.setwOMstId(rs.getLong("WO_MST_ID"));
				bn.setwODate(rs.getDate("WO_DATE"));
				bn.setpOId(rs.getLong("po_mst_id"));
				bn.setpODate(rs.getDate("PO_MST_DATE"));
				bn.setpOQty(rs.getDouble("PO_QTY"));
				bn.setBuyerId(rs.getLong("ORDER_OWNER_ID"));
				bn.setBuyerName(rs.getString("OWNER_NAME"));
				bn.setItemId(rs.getLong("ITEM_ID"));
				bn.setItemName(rs.getString("ITEM_NAME"));
				bn.setWashingPlanId(rs.getLong("WASHING_PLAN_ID"));
				bn.setWashingPlanDate(rs.getDate("WASHING_PLAN_DATE"));
				bn.setWashingPlanQty(rs.getDouble("WASHING_PLAN_QTY"));
				bn.setWashingPlanQtyUnDone(washingPlanQtyUnDone);
				bn.setUomId(rs.getLong("UOM_ID"));
				bn.setUomName(rs.getString("UOM_NAME"));
				bn.setWashingPlanRemarks(rs.getString("REMARKS"));
				bn.setActiveStatus(rs.getInt("ACTIVE_STATUS"));
				if(bn.getActiveStatus()==1)
				 { bn.setsActive("Active");
				  bn.setActive(true);
				 }
				 else
				 {	 bn.setsActive("InActive");
				     bn.setActive(false);
				     
				 }
				
				return bn;
			}

		};
		
		MapSqlParameterSource parameters = new MapSqlParameterSource();
	      parameters.addValue("washingPlanId", washingPlanId);
	     

		System.out.println("service mapper ");
		return namedParameterJdbcTemplate.query(qry,parameters,serviceMapper);
	}

	@Override
	public List<ModelWashingPlanCustom> getWashingPlanBy(Long pOId) {
		// TODO Auto-generated method stub
		String qry="SELECT a.PO_MST_ID,a.PO_MST_DATE,\r\n" + 
				"a.PO_QTY,a.uom_id,a.uom_name,a.WO_CHD_ID,a.WO_MST_ID,a.WO_DATE,\r\n" + 
				"a.order_owner_id,a.owner_name,a.item_id,a.item_name,\r\n" + 
				"a.WASHING_PLAN_ID,a.WASHING_PLAN_DATE,a.REMARKS,a.ACTIVE_STATUS,a.WASHING_PLAN_QTY\r\n" + 
				"FROM (\r\n" + 
				"SELECT a.PO_MST_ID,a.PO_MST_DATE,\r\n" + 
				"b.PO_QTY,n.uom_id,c.uom_name,f.WO_CHD_ID,h.WO_MST_ID,h.WO_DATE, \r\n" + 
				"j.order_owner_id,k.owner_name,l.item_id,m.item_name,\r\n" + 
				"n.WASHING_PLAN_ID,n.WASHING_PLAN_DATE,n.REMARKS,n.ACTIVE_STATUS,n.WASHING_PLAN_QTY\r\n" + 
				"FROM mlfm_washing_plan n\r\n" + 
				"INNER JOIN mlfm_po_mst a on n.PO_MST_ID=a.PO_MST_ID\r\n" + 
				"INNER JOIN mlfm_po_chd b on a.PO_MST_ID=b.PO_MST_ID\r\n" + 
				"INNER JOIN mlfm_production_plan_chd e on b.PRODUCTION_PLAN_CHD_ID=e.PRODUCTION_PLAN_CHD_ID\r\n" + 
				"INNER JOIN mlfm_production_plan_mst f on e.PRODUCTION_PLAN_MST_ID=f.PRODUCTION_PLAN_MST_ID\r\n" + 
				"INNER JOIN mlfm_wo_chd g on f.WO_CHD_ID=g.WO_CHD_ID\r\n" + 
				"INNER JOIN mlfm_wo_mst h on g.WO_MST_ID=h.WO_MST_ID\r\n" + 
				"INNER JOIN mlfm_design i on a.DESIGN_ID=i.design_id \r\n" + 
				"INNER JOIN mlfm_order j on i.order_id=j.order_id\r\n" + 
				"INNER JOIN mlfm_order_owner k on j.order_owner_id=k.order_owner_id\r\n" + 
				"INNER JOIN mlfm_order_item l on j.order_id=l.order_id\r\n" + 
				"INNER JOIN mlfm_item m on l.item_id=m.item_id\r\n" + 
				"LEFT OUTER JOIN mlfm_uom c on n.UOM_ID=c.uom_id\r\n" + 
				") a where\r\n" + 
				"a.PO_MST_ID=coalesce(:pOId,a.PO_MST_ID) order by a.WASHING_PLAN_DATE DESC";
		
		RowMapper<ModelWashingPlanCustom> serviceMapper=new RowMapper<ModelWashingPlanCustom>() {

			@Override
			public ModelWashingPlanCustom mapRow(ResultSet rs, int rownum) throws SQLException {
				// TODO Auto-generated method stub
				ModelWashingPlanCustom bn= new ModelWashingPlanCustom();
				Double washingPlanQtyUnDone=rs.getDouble("PO_QTY")-rs.getDouble("WASHING_PLAN_QTY");
				
				bn.setwOMstId(rs.getLong("WO_MST_ID"));
				bn.setwODate(rs.getDate("WO_DATE"));
				bn.setpOId(rs.getLong("po_mst_id"));
				bn.setpODate(rs.getDate("PO_MST_DATE"));
				bn.setpOQty(rs.getDouble("PO_QTY"));
				bn.setBuyerId(rs.getLong("ORDER_OWNER_ID"));
				bn.setBuyerName(rs.getString("OWNER_NAME"));
				bn.setItemId(rs.getLong("ITEM_ID"));
				bn.setItemName(rs.getString("ITEM_NAME"));
				bn.setWashingPlanId(rs.getLong("WASHING_PLAN_ID"));
				bn.setWashingPlanDate(rs.getDate("WASHING_PLAN_DATE"));
				bn.setWashingPlanQty(rs.getDouble("WASHING_PLAN_QTY"));
				bn.setWashingPlanQtyUnDone(washingPlanQtyUnDone);
				bn.setUomId(rs.getLong("UOM_ID"));
				bn.setUomName(rs.getString("UOM_NAME"));
				bn.setWashingPlanRemarks(rs.getString("REMARKS"));
				bn.setActiveStatus(rs.getInt("ACTIVE_STATUS"));
				if(bn.getActiveStatus()==1)
				 { bn.setsActive("Active");
				  bn.setActive(true);
				 }
				 else
				 {	 bn.setsActive("InActive");
				     bn.setActive(false);
				     
				 }
				
				return bn;
			}

		};
		
		MapSqlParameterSource parameters = new MapSqlParameterSource();
	      parameters.addValue("pOId", pOId);
	     

		System.out.println("service mapper ");
		return namedParameterJdbcTemplate.query(qry,parameters,serviceMapper);
	}


	
	
	
	

}
