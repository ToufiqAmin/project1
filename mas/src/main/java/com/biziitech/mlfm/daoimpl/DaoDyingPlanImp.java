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

import com.biziitech.mlfm.custom.model.ModelDyingPlanCustom;
import com.biziitech.mlfm.dao.DaoDyingPlan;
import com.biziitech.mlfm.model.ModelDyingPlan;
import com.biziitech.mlfm.repository.DyingPlanRepository;

@Service
public class DaoDyingPlanImp implements DaoDyingPlan{
	
	@Autowired
	private DyingPlanRepository dyingPlanRepository;
	
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
	public void saveDyingPlan(ModelDyingPlan modelDyingPlan) {
		// TODO Auto-generated method stub
		
		dyingPlanRepository.save(modelDyingPlan);
		
	}

	@Override
	public void deleteDyingPlan(Long dyingPlanId) {
		// TODO Auto-generated method stub
		dyingPlanRepository.deleteById(dyingPlanId);
	}

	@Override
	public List<ModelDyingPlanCustom> getPendingDyingPlanPODetails(Long pOId, Long buyerId, Date startDate,
			Date endDate) {
		// TODO Auto-generated method stub
		String qry="SELECT a.PO_MST_ID,a.PO_MST_DATE,\r\n" + 
				"a.PO_CHD_ID,a.PO_QTY,a.UOM_ID,a.uom_name,\r\n" + 
				"a.order_owner_id,a.owner_name,a.item_id,a.item_name,\r\n" + 
				"a.DYING_PLAN_ID,a.DYING_PLAN_DATE,a.DYING_PLAN_QTY\r\n" + 
				"FROM (\r\n" + 
				"SELECT a.PO_MST_ID,a.PO_MST_DATE,\r\n" + 
				"b.PO_CHD_ID,b.PO_QTY,b.UOM_ID,c.uom_name,\r\n" + 
				"e.order_owner_id,f.owner_name,g.item_id,h.item_name,\r\n" + 
				"i.DYING_PLAN_ID,i.DYING_PLAN_DATE,i.DYING_PLAN_QTY \r\n" + 
				"FROM mlfm_po_mst a\r\n" + 
				"INNER JOIN mlfm_po_chd b on a.PO_MST_ID=b.PO_MST_ID\r\n" + 
				"LEFT OUTER JOIN mlfm_uom c on b.UOM_ID=c.uom_id\r\n" + 
				"INNER JOIN mlfm_design d on a.DESIGN_ID=d.design_id\r\n" + 
				"INNER JOIN mlfm_order e on d.order_id=e.order_id \r\n" + 
				"INNER JOIN mlfm_order_owner f on e.order_owner_id=f.order_owner_id\r\n" + 
				"INNER JOIN mlfm_order_item g on e.order_id=g.order_id\r\n" + 
				"INNER JOIN mlfm_item h on g.item_id=h.item_id \r\n" + 
				"LEFT OUTER JOIN mlfm_dying_plan i on a.PO_MST_ID=i.PO_MST_ID\r\n" + 
				") a\r\n" + 
				"where a.PO_MST_ID=coalesce(:pOId,a.PO_MST_ID) and a.order_owner_id=coalesce(:buyerId,a.order_owner_id)\r\n" + 
				"and a.PO_MST_DATE BETWEEN coalesce(:startDate,a.PO_MST_DATE) AND coalesce(:endDate,a.PO_MST_DATE) \r\n" + 
				"group by a.PO_MST_ID,a.PO_MST_DATE,\r\n" + 
				"a.PO_CHD_ID,a.PO_QTY,a.UOM_ID,a.uom_name,\r\n" + 
				"a.order_owner_id,a.owner_name,a.item_id,a.item_name, \r\n" + 
				"a.DYING_PLAN_ID,a.DYING_PLAN_DATE,a.DYING_PLAN_QTY";
		
		RowMapper<ModelDyingPlanCustom> serviceMapper=new RowMapper<ModelDyingPlanCustom>() {

			@Override
			public ModelDyingPlanCustom mapRow(ResultSet rs, int rownum) throws SQLException {
				// TODO Auto-generated method stub
				ModelDyingPlanCustom bn= new ModelDyingPlanCustom();
				Double dyingPlanQtyUnDone= rs.getDouble("PO_QTY")-rs.getDouble("DYING_PLAN_QTY");
												
				bn.setpOId(rs.getLong("PO_MST_ID"));
				bn.setpODate(rs.getDate("PO_MST_DATE"));
				bn.setpOQty(rs.getDouble("PO_QTY"));
				bn.setItemId(rs.getLong("ITEM_ID"));
				bn.setItemName(rs.getString("ITEM_NAME"));
				bn.setBuyerId(rs.getLong("ORDER_OWNER_ID"));
				bn.setBuyerName(rs.getString("OWNER_NAME"));
				bn.setUomId(rs.getLong("UOM_ID"));
				bn.setUomName(rs.getString("UOM_NAME"));
				bn.setDyingPlanQtyUnDone(dyingPlanQtyUnDone);
				bn.setDyingPlanQty(rs.getDouble("DYING_PLAN_QTY"));
				
				return bn;
			}

		};
		
		MapSqlParameterSource parameters = new MapSqlParameterSource();
	      parameters.addValue("pOId", pOId);
	      System.out.println(" in daoDyingPlanImp, pOId:" +pOId);
	      parameters.addValue("buyerId", buyerId);
	      System.out.println(" in daoDyingPlanImp, buyerId:" +buyerId);
	      parameters.addValue("startDate", startDate);
	      parameters.addValue("endDate", endDate);

		System.out.println("service mapper ");
		return namedParameterJdbcTemplate.query(qry,parameters,serviceMapper);
	}

	@Override
	public List<ModelDyingPlanCustom> getCompletedDyingPlanPODetails(Long pOId, Long buyerId, Date startDate,
			Date endDate) {
		// TODO Auto-generated method stub
String qry="select a.dying_plan_id, a.dying_plan_date,a.dying_plan_qty,a.PO_MST_ID,a.PO_MST_DATE,a.po_qty,\r\n" + 
		"a.item_id,a.item_name,a.order_owner_id,a.owner_name,a.uom_id,a.uom_name\r\n" + 
		"FROM (\r\n" + 
		"select a.dying_plan_id, a.dying_plan_date,a.dying_plan_qty, a.PO_MST_ID,b.PO_MST_DATE,m.po_qty,\r\n" + 
		"oi.item_id,i.item_name,e.order_owner_id,f.owner_name,m.uom_id,l.uom_name				\r\n" + 
		"from mlfm_dying_plan a \r\n" + 
		"inner join mlfm_po_mst b on b.PO_MST_ID = a.PO_MST_ID\r\n" + 
		"inner join mlfm_design d on b.design_id = d.design_id \r\n" + 
		"inner join mlfm_order e on d.order_id=e.order_id \r\n" + 
		"inner join mlfm_order_item oi on oi.order_id=e.order_id  \r\n" + 
		"inner join mlfm_item i on i.item_id=oi.item_id \r\n" + 
		"inner join mlfm_order_item_qty k on oi.item_order_id=k.order_item_id\r\n" + 
		"inner join mlfm_order_owner f on e.order_owner_id=f.order_owner_id\r\n" + 
		"INNER JOIN mlfm_po_chd m on b.PO_MST_ID=m.PO_MST_ID\r\n" + 
		"left outer join mlfm_uom l on m.uom_id=l.uom_id \r\n" + 
		") a where\r\n" + 
		"a.PO_MST_ID=coalesce(:pOId,a.PO_MST_ID) and a.order_owner_id=coalesce(:buyerId,a.order_owner_id)\r\n" + 
		"and a.dying_plan_date BETWEEN coalesce(:startDate,a.dying_plan_date) AND coalesce(:endDate,a.dying_plan_date) \r\n" + 
		"group by a.dying_plan_id, a.dying_plan_date,a.dying_plan_qty,a.PO_MST_ID,a.PO_MST_DATE,a.po_qty,\r\n" + 
		"a.item_id,a.item_name,a.order_owner_id,a.owner_name,a.uom_id,a.uom_name";
		
		RowMapper<ModelDyingPlanCustom> serviceMapper=new RowMapper<ModelDyingPlanCustom>() {

			@Override
			public ModelDyingPlanCustom mapRow(ResultSet rs, int rownum) throws SQLException {
				// TODO Auto-generated method stub
				ModelDyingPlanCustom bn= new ModelDyingPlanCustom();
				Double dyingPlanQtyUnDone= rs.getDouble("PO_QTY")-rs.getDouble("DYING_PLAN_QTY");
												
				bn.setpOId(rs.getLong("PO_MST_ID"));
				bn.setpODate(rs.getDate("PO_MST_DATE"));
				bn.setpOQty(rs.getDouble("PO_QTY"));
				bn.setItemId(rs.getLong("ITEM_ID"));
				bn.setItemName(rs.getString("ITEM_NAME"));
				bn.setBuyerId(rs.getLong("ORDER_OWNER_ID"));
				bn.setBuyerName(rs.getString("OWNER_NAME"));
				bn.setUomId(rs.getLong("UOM_ID"));
				bn.setUomName(rs.getString("UOM_NAME"));
				bn.setDyingPlanQtyUnDone(dyingPlanQtyUnDone);
				bn.setDyingPlanQty(rs.getDouble("DYING_PLAN_QTY"));
				
				return bn;
			}

		};
		
		MapSqlParameterSource parameters = new MapSqlParameterSource();
	      parameters.addValue("pOId", pOId);
	      System.out.println(" in daoDyingPlanImp, pOId:" +pOId);
	      parameters.addValue("buyerId", buyerId);
	      System.out.println(" in daoDyingPlanImp, buyerId:" +buyerId);
	      parameters.addValue("startDate", startDate);
	      parameters.addValue("endDate", endDate);

		System.out.println("service mapper ");
		return namedParameterJdbcTemplate.query(qry,parameters,serviceMapper);
	}

	@Override
	public List<ModelDyingPlanCustom> getDyingPlanByPOId(Long pOId) {
		// TODO Auto-generated method stub
		String qry="select a.dying_plan_id,a.dying_plan_date,a.PO_MST_ID,b.PO_MST_DATE,c.po_qty,\r\n" + 
				"a.dying_plan_qty,a.active_status,a.remarks,a.uom_id,l.uom_name,\r\n" + 
				"oi.item_id,i.item_name,e.order_owner_id,f.owner_name\r\n" + 
				"from MLFM_DYING_PLAN a\r\n" + 
				"inner join mlfm_po_mst b on a.PO_MST_ID = b.PO_MST_ID \r\n" + 
				"INNER JOIN mlfm_po_chd c on b.PO_MST_ID=c.PO_MST_ID\r\n" + 
				"inner join mlfm_design d on b.design_id = d.design_id\r\n" + 
				"inner join mlfm_order e on d.order_id=e.order_id\r\n" + 
				"inner join mlfm_order_item oi on oi.order_id=e.order_id \r\n" + 
				"inner join mlfm_item i on i.item_id=oi.item_id\r\n" + 
				"inner join mlfm_order_owner f on e.order_owner_id=f.order_owner_id\r\n" + 
				"LEFT OUTER join mlfm_uom l on a.UOM_ID=l.uom_id\r\n" + 
				"where a.PO_MST_ID=coalesce(:pOId,a.PO_MST_ID) ORDER BY a.dying_plan_date DESC";
		
		
		RowMapper<ModelDyingPlanCustom> serviceMapper=new RowMapper<ModelDyingPlanCustom>() {

			@Override
			public ModelDyingPlanCustom mapRow(ResultSet rs, int rownum) throws SQLException {
				// TODO Auto-generated method stub
				ModelDyingPlanCustom bn= new ModelDyingPlanCustom();
				//Double dyingPlanQtyUnDone=rs.getDouble("PO_QTY")-rs.getDouble("DYING_PLAN_QTY");
				Double dyingPlanQtyUnDone=rs.getDouble("PO_QTY");
				
				bn.setpOId(rs.getLong("PO_MST_ID"));
				bn.setpODate(rs.getDate("PO_MST_DATE"));
				bn.setpOQty(rs.getDouble("PO_QTY"));
				bn.setDyingPlanDate(rs.getDate("DYING_PLAN_DATE"));
				System.out.println(rs.getDate("DYING_PLAN_DATE"));
				bn.setDyingPlanQty(rs.getDouble("DYING_PLAN_QTY"));
				bn.setItemId(rs.getLong("ITEM_ID"));
				bn.setItemName(rs.getString("ITEM_NAME"));
				bn.setDyingPlanId(rs.getLong("DYING_PLAN_ID"));
				bn.setActiveStatus(rs.getInt("ACTIVE_STATUS"));
				System.out.println(rs.getInt("ACTIVE_STATUS"));
				bn.setDyingPlanRemarks(rs.getString("REMARKS"));
				bn.setBuyerId(rs.getLong("ORDER_OWNER_ID"));
				bn.setBuyerName(rs.getString("OWNER_NAME"));
				bn.setUomId(rs.getLong("UOM_ID"));
				bn.setUomName(rs.getString("UOM_NAME"));
				bn.setDyingPlanQtyUnDone(dyingPlanQtyUnDone);
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
	      System.out.println(" in daoDyingPlanImp, pOId:" +pOId);
	
		
			System.out.println("service mapper ");
			return namedParameterJdbcTemplate.query(qry,parameters,serviceMapper);
	}

	@Override
	public List<ModelDyingPlanCustom> getDyingPlanByDyingPlanId(Long dyingPlanId) {
		// TODO Auto-generated method stub
		String qry="select a.dying_plan_id,a.dying_plan_date,a.PO_MST_ID,b.PO_MST_DATE,c.po_qty,\r\n" + 
				"a.dying_plan_qty,a.active_status,a.remarks,a.uom_id,l.uom_name,\r\n" + 
				"oi.item_id,i.item_name,e.order_owner_id,f.owner_name\r\n" + 
				"from MLFM_DYING_PLAN a\r\n" + 
				"inner join mlfm_po_mst b on a.PO_MST_ID = b.PO_MST_ID\r\n" + 
				"INNER JOIN mlfm_po_chd c on b.PO_MST_ID=c.PO_MST_ID\r\n" + 
				"inner join mlfm_design d on b.design_id = d.design_id\r\n" + 
				"inner join mlfm_order e on d.order_id=e.order_id\r\n" + 
				"inner join mlfm_order_item oi on oi.order_id=e.order_id\r\n" + 
				"inner join mlfm_item i on i.item_id=oi.item_id \r\n" + 
				"inner join mlfm_order_owner f on e.order_owner_id=f.order_owner_id\r\n" + 
				"LEFT OUTER join mlfm_uom l on a.UOM_ID=l.uom_id\r\n" + 
				"where a.dying_plan_id=coalesce(:dyingPlanId,a.dying_plan_id) ORDER BY a.dying_plan_date DESC";
		
		RowMapper<ModelDyingPlanCustom> serviceMapper=new RowMapper<ModelDyingPlanCustom>() {

			@Override
			public ModelDyingPlanCustom mapRow(ResultSet rs, int rownum) throws SQLException {
				// TODO Auto-generated method stub
				ModelDyingPlanCustom bn= new ModelDyingPlanCustom();
//				Double dyingPlanQtyUnDone=rs.getDouble("PO_QTY")-rs.getDouble("DYING_PLAN_QTY");
				Double dyingPlanQtyUnDone=rs.getDouble("PO_QTY");
				
				bn.setDyingPlanId(rs.getLong("DYING_PLAN_ID"));
				bn.setpOId(rs.getLong("PO_MST_ID"));
				bn.setpODate(rs.getDate("PO_MST_DATE"));
				bn.setpOQty(rs.getDouble("PO_QTY"));
				bn.setDyingPlanDate(rs.getDate("DYING_PLAN_DATE"));
				System.out.println(rs.getDate("DYING_PLAN_DATE"));
				bn.setDyingPlanQty(rs.getDouble("DYING_PLAN_QTY"));
				bn.setItemId(rs.getLong("ITEM_ID"));
				bn.setItemName(rs.getString("ITEM_NAME"));
				bn.setDyingPlanId(rs.getLong("DYING_PLAN_ID"));
				bn.setActiveStatus(rs.getInt("ACTIVE_STATUS"));
				System.out.println(rs.getInt("ACTIVE_STATUS"));
				bn.setDyingPlanRemarks(rs.getString("REMARKS"));
				bn.setBuyerId(rs.getLong("ORDER_OWNER_ID"));
				bn.setBuyerName(rs.getString("OWNER_NAME"));
				bn.setUomId(rs.getLong("UOM_ID"));
				bn.setUomName(rs.getString("UOM_NAME"));
				bn.setDyingPlanQtyUnDone(dyingPlanQtyUnDone);
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
	      parameters.addValue("dyingPlanId", dyingPlanId);
	      System.out.println(" in daoQCPlanImp, dyingPlanId:" +dyingPlanId);
	
		
			System.out.println("service mapper ");
			return namedParameterJdbcTemplate.query(qry,parameters,serviceMapper);
	}

	
	
	
	
	
}
