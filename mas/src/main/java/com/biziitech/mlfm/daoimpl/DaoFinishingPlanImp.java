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


import com.biziitech.mlfm.custom.model.ModelFinishingPlanCustom;
import com.biziitech.mlfm.dao.DaoFinishingPlan;
import com.biziitech.mlfm.model.ModelFinishingPlan;
import com.biziitech.mlfm.repository.FinishingPlanRepository;

@Service
public class DaoFinishingPlanImp implements DaoFinishingPlan{
	
	@Autowired
	private FinishingPlanRepository finishingPlanRepository;
	
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
	public void saveFinishingPlan(ModelFinishingPlan modelFinishingPlan) {
		// TODO Auto-generated method stub
		finishingPlanRepository.save(modelFinishingPlan);
	}

	@Override
	public void deleteFinishingPlan(Long finishingPlanId) {
		// TODO Auto-generated method stub
		finishingPlanRepository.deleteById(finishingPlanId);
	}

	@Override
	public List<ModelFinishingPlanCustom> getPendingFinisingPlanPODetails(Long pOId, Long buyerId, Date startDate,
			Date endDate) {
		// TODO Auto-generated method stub

		/*
		String qry="SELECT a.PO_MST_ID,a.PO_MST_DATE,\r\n" + 
				"a.PO_CHD_ID,a.PO_QTY,a.UOM_ID,a.uom_name,\r\n" + 
				"a.order_owner_id,a.owner_name,a.item_id,a.item_name,\r\n" + 
				"a.finishing_PLAN_ID,a.finishing_PLAN_DATE,a.finishing_PLAN_QTY\r\n" + 
				"FROM (\r\n" + 
				"SELECT a.PO_MST_ID,a.PO_MST_DATE,\r\n" + 
				"b.PO_CHD_ID,b.PO_QTY,b.UOM_ID,c.uom_name,\r\n" + 
				"e.order_owner_id,f.owner_name,g.item_id,h.item_name,\r\n" + 
				"i.finishing_PLAN_ID,i.finishing_PLAN_DATE,i.finishing_PLAN_QTY\r\n" + 
				"FROM mlfm_po_mst a\r\n" + 
				"INNER JOIN mlfm_po_chd b on a.PO_MST_ID=b.PO_MST_ID\r\n" + 
				"LEFT OUTER JOIN mlfm_uom c on b.UOM_ID=c.uom_id\r\n" + 
				"INNER JOIN mlfm_design d on a.DESIGN_ID=d.design_id\r\n" + 
				"INNER JOIN mlfm_order e on d.order_id=e.order_id\r\n" + 
				"INNER JOIN mlfm_order_owner f on e.order_owner_id=f.order_owner_id\r\n" + 
				"INNER JOIN mlfm_order_item g on e.order_id=g.order_id\r\n" + 
				"INNER JOIN mlfm_item h on g.item_id=h.item_id\r\n" + 
				"LEFT OUTER JOIN mlfm_finishing_plan i on a.PO_MST_ID=i.PO_MST_ID\r\n" + 
				") a\r\n" + 
				"where a.PO_MST_ID=coalesce(:pOId,a.PO_MST_ID) and f.order_owner_id=coalesce(:buyerId,f.order_owner_id)\r\n" + 
				"and a.PO_MST_DATE BETWEEN coalesce(:startDate,a.PO_MST_DATE) AND coalesce(:endDate,a.PO_MST_DATE)\r\n" + 
				"group by a.PO_MST_ID,a.PO_MST_DATE,\r\n" + 
				"a.PO_CHD_ID,a.PO_QTY,a.UOM_ID,a.uom_name,\r\n" + 
				"a.order_owner_id,a.owner_name,a.item_id,a.item_name,\r\n" + 
				"a.finishing_PLAN_ID,a.finishing_PLAN_DATE,a.finishing_PLAN_QTY";
		
		*/
		
		
		String qry ="SELECT a.PO_MST_ID,a.PO_MST_DATE,b.PO_CHD_ID,b.PO_QTY,b.UOM_ID,c.uom_name,f.owner_name,f.ORDER_OWNER_ID,\r\n" + 
				"g.item_id,h.item_name,i.finishing_PLAN_ID,i.finishing_PLAN_DATE,i.finishing_PLAN_QTY\r\n" + 
				"FROM mlfm_po_mst a\r\n" + 
				"INNER JOIN mlfm_po_chd b on a.PO_MST_ID=b.PO_MST_ID\r\n" + 
				"LEFT OUTER JOIN mlfm_uom c on b.UOM_ID=c.uom_id\r\n" + 
				"INNER JOIN mlfm_design d on a.DESIGN_ID=d.design_id\r\n" + 
				"INNER JOIN mlfm_order e on d.order_id=e.order_id\r\n" + 
				"INNER JOIN mlfm_order_owner f on e.order_owner_id=f.order_owner_id\r\n" + 
				"INNER JOIN mlfm_order_item g on e.order_id=g.order_id \r\n" + 
				"INNER JOIN mlfm_item h on g.item_id=h.item_id\r\n" + 
				"LEFT OUTER JOIN mlfm_finishing_plan i on a.PO_MST_ID=i.PO_MST_ID\r\n" + 
				"where a.PO_MST_ID=coalesce(:pOId,a.PO_MST_ID) \r\n" + 
				"and f.order_owner_id=coalesce(:buyerId,f.order_owner_id)\r\n" + 
				"and a.PO_MST_DATE BETWEEN coalesce(:startDate,a.PO_MST_DATE) AND coalesce(:endDate,a.PO_MST_DATE)\r\n" + 
				"group by a.PO_MST_ID,a.PO_MST_DATE,b.PO_CHD_ID,b.PO_QTY,b.UOM_ID,c.uom_name,f.owner_name,\r\n" + 
				"g.item_id,h.item_name,i.finishing_PLAN_ID,i.finishing_PLAN_DATE,i.finishing_PLAN_QTY";
			
		
		
		/*
		 * 
		 * SELECT a.PO_MST_ID,a.PO_MST_DATE,b.PO_CHD_ID,b.PO_QTY,b.UOM_ID,c.uom_name,f.owner_name,
g.item_id,h.item_name,i.finishing_PLAN_ID,i.finishing_PLAN_DATE,i.finishing_PLAN_QTY
FROM mlfm_po_mst a
INNER JOIN mlfm_po_chd b on a.PO_MST_ID=b.PO_MST_ID
LEFT OUTER JOIN mlfm_uom c on b.UOM_ID=c.uom_id
INNER JOIN mlfm_design d on a.DESIGN_ID=d.design_id
INNER JOIN mlfm_order e on d.order_id=e.order_id
INNER JOIN mlfm_order_owner f on e.order_owner_id=f.order_owner_id
INNER JOIN mlfm_order_item g on e.order_id=g.order_id 
INNER JOIN mlfm_item h on g.item_id=h.item_id
LEFT OUTER JOIN mlfm_finishing_plan i on a.PO_MST_ID=i.PO_MST_ID
where a.PO_MST_ID=coalesce(@pOId,a.PO_MST_ID) 
and f.order_owner_id=coalesce(@buyerId,f.order_owner_id)
and a.PO_MST_DATE BETWEEN coalesce(@startDate,a.PO_MST_DATE) AND coalesce(@endDate,a.PO_MST_DATE)
group by a.PO_MST_ID,a.PO_MST_DATE,b.PO_CHD_ID,b.PO_QTY,b.UOM_ID,c.uom_name,f.owner_name,
g.item_id,h.item_name,i.finishing_PLAN_ID,i.finishing_PLAN_DATE,i.finishing_PLAN_QTY
		 * 
		 * 
		 * */
		
		RowMapper<ModelFinishingPlanCustom> serviceMapper=new RowMapper<ModelFinishingPlanCustom>() {

			@Override
			public ModelFinishingPlanCustom mapRow(ResultSet rs, int rownum) throws SQLException {
				// TODO Auto-generated method stub
				ModelFinishingPlanCustom bn= new ModelFinishingPlanCustom();
				Double finishingPlanQtyUnDone= rs.getDouble("PO_QTY")-rs.getDouble("FINISHING_PLAN_QTY");
												
				bn.setpOId(rs.getLong("PO_MST_ID"));
				bn.setpODate(rs.getDate("PO_MST_DATE"));
				bn.setpOQty(rs.getDouble("PO_QTY"));
				bn.setItemId(rs.getLong("ITEM_ID"));
				bn.setItemName(rs.getString("ITEM_NAME"));
				bn.setBuyerId(rs.getLong("ORDER_OWNER_ID"));
				bn.setBuyerName(rs.getString("OWNER_NAME"));
				bn.setUomId(rs.getLong("UOM_ID"));
				bn.setUomName(rs.getString("UOM_NAME"));
				bn.setFinishingPlanQtyUnDone(finishingPlanQtyUnDone);
				bn.setFinishingPlanQty(rs.getDouble("FINISHING_PLAN_QTY"));
				
				return bn;
			}

		};
		
		MapSqlParameterSource parameters = new MapSqlParameterSource();
	      parameters.addValue("pOId", pOId);
	      System.out.println(" in daoFinishingPlanImp, pOId:" +pOId);
	      parameters.addValue("buyerId", buyerId);
	      System.out.println(" in daoFinishingPlanImp, buyerId:" +buyerId);
	      parameters.addValue("startDate", startDate);
	      parameters.addValue("endDate", endDate);

		System.out.println("service mapper ");
		return namedParameterJdbcTemplate.query(qry,parameters,serviceMapper);
	}

	@Override
	public List<ModelFinishingPlanCustom> getCompletedFinishingPlanPODetails(Long pOId, Long buyerId, Date startDate,
			Date endDate) {
		// TODO Auto-generated method stub
String qry="SELECT a.PO_MST_ID,a.PO_MST_DATE,\r\n" + 
		"a.PO_CHD_ID,a.PO_QTY,a.UOM_ID,a.uom_name,\r\n" + 
		"a.order_owner_id,a.owner_name,a.item_id,a.item_name,\r\n" + 
		"a.finishing_PLAN_ID,a.finishing_PLAN_DATE,a.finishing_PLAN_QTY\r\n" + 
		"FROM (\r\n" + 
		"SELECT a.PO_MST_ID,a.PO_MST_DATE,\r\n" + 
		"b.PO_CHD_ID,b.PO_QTY,b.UOM_ID,c.uom_name,\r\n" + 
		"e.order_owner_id,f.owner_name,g.item_id,h.item_name,\r\n" + 
		"i.finishing_PLAN_ID,i.finishing_PLAN_DATE,i.finishing_PLAN_QTY\r\n" + 
		"FROM mlfm_finishing_plan i\r\n" + 
		"INNER JOIN mlfm_po_mst a on i.PO_MST_ID=a.PO_MST_ID\r\n" + 
		"INNER JOIN mlfm_po_chd b on a.PO_MST_ID=b.PO_MST_ID\r\n" + 
		"LEFT OUTER JOIN mlfm_uom c on b.UOM_ID=c.uom_id\r\n" + 
		"INNER JOIN mlfm_design d on a.DESIGN_ID=d.design_id\r\n" + 
		"INNER JOIN mlfm_order e on d.order_id=e.order_id\r\n" + 
		"INNER JOIN mlfm_order_owner f on e.order_owner_id=f.order_owner_id\r\n" + 
		"INNER JOIN mlfm_order_item g on e.order_id=g.order_id\r\n" + 
		"INNER JOIN mlfm_item h on g.item_id=h.item_id\r\n" + 
		") a\r\n" + 
		"where a.PO_MST_ID=coalesce(:pOId,a.PO_MST_ID) and a.order_owner_id=coalesce(:buyerId,a.order_owner_id)\r\n" + 
		"and a.finishing_PLAN_DATE BETWEEN coalesce(:startDate,a.finishing_PLAN_DATE) AND coalesce(:endDate,a.finishing_PLAN_DATE)\r\n" + 
		"group by a.PO_MST_ID,a.PO_MST_DATE,\r\n" + 
		"a.PO_CHD_ID,a.PO_QTY,a.UOM_ID,a.uom_name,\r\n" + 
		"a.order_owner_id,a.owner_name,a.item_id,a.item_name,\r\n" + 
		"a.finishing_PLAN_ID,a.finishing_PLAN_DATE,a.finishing_PLAN_QTY";
		
		RowMapper<ModelFinishingPlanCustom> serviceMapper=new RowMapper<ModelFinishingPlanCustom>() {

			@Override
			public ModelFinishingPlanCustom mapRow(ResultSet rs, int rownum) throws SQLException {
				// TODO Auto-generated method stub
				ModelFinishingPlanCustom bn= new ModelFinishingPlanCustom();
				Double finishingPlanQtyUnDone= rs.getDouble("PO_QTY")-rs.getDouble("FINISHING_PLAN_QTY");
												
				bn.setpOId(rs.getLong("PO_MST_ID"));
				bn.setpODate(rs.getDate("PO_MST_DATE"));
				bn.setpOQty(rs.getDouble("PO_QTY"));
				bn.setItemId(rs.getLong("ITEM_ID"));
				bn.setItemName(rs.getString("ITEM_NAME"));
				bn.setBuyerId(rs.getLong("ORDER_OWNER_ID"));
				bn.setBuyerName(rs.getString("OWNER_NAME"));
				bn.setUomId(rs.getLong("UOM_ID"));
				bn.setUomName(rs.getString("UOM_NAME"));
				bn.setFinishingPlanQtyUnDone(finishingPlanQtyUnDone);
				bn.setFinishingPlanQty(rs.getDouble("FINISHING_PLAN_QTY"));
				
				return bn;
			}

		};
		
		MapSqlParameterSource parameters = new MapSqlParameterSource();
	      parameters.addValue("pOId", pOId);
	      System.out.println(" in daoFinishingPlanImp, pOId:" +pOId);
	      parameters.addValue("buyerId", buyerId);
	      System.out.println(" in daoFinishingPlanImp, buyerId:" +buyerId);
	      parameters.addValue("startDate", startDate);
	      parameters.addValue("endDate", endDate);

		System.out.println("service mapper ");
		return namedParameterJdbcTemplate.query(qry,parameters,serviceMapper);
	}

	@Override
	public List<ModelFinishingPlanCustom> getFinishingPlanByPOId(Long pOId) {
		// TODO Auto-generated method stub
String qry="select a.finishing_plan_id,a.finishing_plan_date,\r\n" + 
		"a.PO_MST_ID,b.PO_MST_DATE,c.po_qty,a.finishing_plan_qty,a.active_status, \r\n" + 
		"a.remarks,a.uom_id,l.uom_name,oi.item_id,i.item_name,e.order_owner_id,f.owner_name \r\n" + 
		"from MLFM_finishing_PLAN a\r\n" + 
		"inner join mlfm_po_mst b on a.PO_MST_ID = b.PO_MST_ID\r\n" + 
		"INNER JOIN mlfm_po_chd c on b.PO_MST_ID=c.PO_MST_ID\r\n" + 
		"inner join mlfm_design d on b.design_id = d.design_id \r\n" + 
		"inner join mlfm_order e on d.order_id=e.order_id\r\n" + 
		"inner join mlfm_order_item oi on oi.order_id=e.order_id\r\n" + 
		"inner join mlfm_item i on i.item_id=oi.item_id \r\n" + 
		"inner join mlfm_uom l on a.UOM_ID=l.uom_id\r\n" + 
		"inner join mlfm_order_owner f on e.order_owner_id=f.order_owner_id\r\n" + 
		"where a.PO_MST_ID=coalesce(:pOId,a.PO_MST_ID) ORDER BY a.finishing_plan_date DESC";
		
		RowMapper<ModelFinishingPlanCustom> serviceMapper=new RowMapper<ModelFinishingPlanCustom>() {

			@Override
			public ModelFinishingPlanCustom mapRow(ResultSet rs, int rownum) throws SQLException {
				// TODO Auto-generated method stub
				ModelFinishingPlanCustom bn= new ModelFinishingPlanCustom();
//				Double finishingPlanQtyUnDone= rs.getDouble("PO_QTY")-rs.getDouble("FINISHING_PLAN_QTY");
				Double finishingPlanQtyUnDone= rs.getDouble("PO_QTY");
												
				bn.setpOId(rs.getLong("PO_MST_ID"));
				bn.setpODate(rs.getDate("PO_MST_DATE"));
				bn.setpOQty(rs.getDouble("PO_QTY"));
				bn.setItemId(rs.getLong("ITEM_ID"));
				bn.setItemName(rs.getString("ITEM_NAME"));
				bn.setBuyerId(rs.getLong("ORDER_OWNER_ID"));
				bn.setBuyerName(rs.getString("OWNER_NAME"));
				bn.setUomId(rs.getLong("UOM_ID"));
				bn.setUomName(rs.getString("UOM_NAME"));
				bn.setFinishingPlanQtyUnDone(finishingPlanQtyUnDone);
				bn.setFinishingPlanQty(rs.getDouble("FINISHING_PLAN_QTY"));
				bn.setFinishingPlanDate(rs.getDate("FINISHING_PLAN_DATE"));
				System.out.println(rs.getDate("FINISHING_PLAN_DATE"));
				bn.setFinishingPlanQty(rs.getDouble("FINISHING_PLAN_QTY"));
				bn.setFinishingPlanId(rs.getLong("FINISHING_PLAN_ID"));
				bn.setActiveStatus(rs.getInt("ACTIVE_STATUS"));
				System.out.println(rs.getInt("ACTIVE_STATUS"));
				bn.setFinishingPlanRemarks(rs.getString("REMARKS"));
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
	      System.out.println(" in daoFinishingPlanImp, pOId:" +pOId);
	    

		System.out.println("service mapper ");
		return namedParameterJdbcTemplate.query(qry,parameters,serviceMapper);
	}

	@Override
	public List<ModelFinishingPlanCustom> getFinishingPlanByFinishingPlanId(Long finishingPlanId) {
		// TODO Auto-generated method stub
String qry="select a.finishing_plan_id,a.finishing_plan_date,\r\n" + 
		"a.PO_MST_ID,b.PO_MST_DATE,c.po_qty,a.finishing_plan_qty,a.active_status, \r\n" + 
		"a.remarks,a.uom_id,l.uom_name,oi.item_id,i.item_name,e.order_owner_id,f.owner_name \r\n" + 
		"from MLFM_finishing_PLAN a\r\n" + 
		"inner join mlfm_po_mst b on a.PO_MST_ID = b.PO_MST_ID\r\n" + 
		"INNER JOIN mlfm_po_chd c on b.PO_MST_ID=c.PO_MST_ID\r\n" + 
		"inner join mlfm_design d on b.design_id = d.design_id \r\n" + 
		"inner join mlfm_order e on d.order_id=e.order_id\r\n" + 
		"inner join mlfm_order_item oi on oi.order_id=e.order_id\r\n" + 
		"inner join mlfm_item i on i.item_id=oi.item_id \r\n" + 
		"inner join mlfm_uom l on a.UOM_ID=l.uom_id\r\n" + 
		"inner join mlfm_order_owner f on e.order_owner_id=f.order_owner_id\r\n" + 
		"where a.finishing_plan_id=coalesce(:finishingPlanId,a.finishing_plan_id) \r\n" + 
		"ORDER BY a.finishing_plan_date DESC";
		
		RowMapper<ModelFinishingPlanCustom> serviceMapper=new RowMapper<ModelFinishingPlanCustom>() {

			@Override
			public ModelFinishingPlanCustom mapRow(ResultSet rs, int rownum) throws SQLException {
				// TODO Auto-generated method stub
				ModelFinishingPlanCustom bn= new ModelFinishingPlanCustom();
//				Double finishingPlanQtyUnDone= rs.getDouble("PO_QTY")-rs.getDouble("FINISHING_PLAN_QTY");
				Double finishingPlanQtyUnDone= rs.getDouble("PO_QTY");
												
				bn.setpOId(rs.getLong("PO_MST_ID"));
				bn.setpODate(rs.getDate("PO_MST_DATE"));
				bn.setpOQty(rs.getDouble("PO_QTY"));
				bn.setItemId(rs.getLong("ITEM_ID"));
				bn.setItemName(rs.getString("ITEM_NAME"));
				bn.setBuyerId(rs.getLong("ORDER_OWNER_ID"));
				bn.setBuyerName(rs.getString("OWNER_NAME"));
				bn.setUomId(rs.getLong("UOM_ID"));
				bn.setUomName(rs.getString("UOM_NAME"));
				bn.setFinishingPlanQtyUnDone(finishingPlanQtyUnDone);
				bn.setFinishingPlanQty(rs.getDouble("FINISHING_PLAN_QTY"));
				bn.setFinishingPlanDate(rs.getDate("FINISHING_PLAN_DATE"));
				System.out.println(rs.getDate("FINISHING_PLAN_DATE"));
				bn.setFinishingPlanQty(rs.getDouble("FINISHING_PLAN_QTY"));
				bn.setFinishingPlanId(rs.getLong("FINISHING_PLAN_ID"));
				bn.setActiveStatus(rs.getInt("ACTIVE_STATUS"));
				System.out.println(rs.getInt("ACTIVE_STATUS"));
				bn.setFinishingPlanRemarks(rs.getString("REMARKS"));
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
	      parameters.addValue("finishingPlanId", finishingPlanId);
	      System.out.println(" in daoFinishingPlanImp, finishingPlanId:" +finishingPlanId);
	    

		System.out.println("service mapper ");
		return namedParameterJdbcTemplate.query(qry,parameters,serviceMapper);
	}
	
	
	
	
	

}
