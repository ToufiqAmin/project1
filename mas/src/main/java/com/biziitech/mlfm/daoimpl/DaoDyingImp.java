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

import com.biziitech.mlfm.custom.model.ModelDyingCustom;
import com.biziitech.mlfm.dao.DaoDying;
import com.biziitech.mlfm.model.ModelDying;
import com.biziitech.mlfm.repository.DyingRepository;

@Service
public class DaoDyingImp implements DaoDying{
	
	@Autowired
	private DyingRepository dyingRepository;
	
	
	
	@Autowired
    private DataSource dataSource;
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
    
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.namedParameterJdbcTemplate=namedParameterJdbcTemplate;
          }
	

	@Override
	public void saveDying(ModelDying modelDying) {
		// TODO Auto-generated method stub
		
		
		dyingRepository.save(modelDying);
	}

	@Override
	public List<ModelDyingCustom> getDyingById(Long id) {
		// TODO Auto-generated method stub
		String qry="select dy.dying_date,dy.dying_qty, dy.dying_by,dy.dying_plan_id,\r\n" + 
				"dy.active_status,dy.remarks,dy.dying_id,u.user_id,u.user_name,\r\n" + 
				"dy.UOM_ID,m.uom_name\r\n" + 
				"from mlfm_dying dy\r\n" + 
				"left join bg_user u on dy.dying_by=u.user_id\r\n" + 
				"LEFT OUTER JOIN mlfm_uom m on dy.UOM_ID=m.uom_id \r\n" + 
				"where dy.DYING_PLAN_ID=coalesce(:id,dy.DYING_PLAN_ID)\r\n" + 
				"ORDER BY dy.dying_date DESC";
		
RowMapper<ModelDyingCustom> serviceMapper=new RowMapper<ModelDyingCustom>() {
			
			
			public ModelDyingCustom mapRow(ResultSet rs, int rownum) throws SQLException{
				ModelDyingCustom bn=new ModelDyingCustom();
				 bn.setDyingDate(rs.getDate("DYING_DATE"));
				 System.out.println(rs.getDate("DYING_DATE"));
				 bn.setDyingQty(rs.getDouble("DYING_QTY"));
				 System.out.println(rs.getDouble("DYING_QTY"));
				 bn.setDyingBy(rs.getLong("DYING_BY"));
				 System.out.println(rs.getLong("DYING_BY"));
				 bn.setUserId(rs.getLong("USER_ID"));
				 bn.setUserName(rs.getString("USER_NAME"));
				 bn.setActiveStatus(rs.getInt("ACTIVE_STATUS"));
				 bn.setDyingRemarks(rs.getString("REMARKS"));
				 bn.setDyingId(rs.getLong("DYING_ID"));
				 bn.setDyingPlanId(rs.getLong("DYING_PLAN_ID"));
				 bn.setUomId(rs.getLong("UOM_ID"));
				 bn.setUomName(rs.getString("UOM_NAME"));
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
	      parameters.addValue("id", id);
	      System.out.println(" in daoDyingImp, id:" +id);

		System.out.println("service mapper ");
		return namedParameterJdbcTemplate.query(qry,parameters,serviceMapper);
	}

	
	@Override
	public List<ModelDyingCustom> getDyingDoneById(Long id) {
		// TODO Auto-generated method stub
		String qry="select dy.dying_date,dy.dying_qty, dy.dying_by,dy.active_status,dy.remarks,dy.dying_id,u.user_id,u.user_name from mlfm_dying dy \r\n" +
				"left join bg_user u on dy.dying_by=u.user_id  \r\n" + 
				"where dy.DYING_PLAN_ID=coalesce(:id,dy.DYING_PLAN_ID)\r\n"+
				"ORDER BY dy.dying_date DESC ";
		
RowMapper<ModelDyingCustom> serviceMapper=new RowMapper<ModelDyingCustom>() {
			
			
			public ModelDyingCustom mapRow(ResultSet rs, int rownum) throws SQLException{
				ModelDyingCustom bn=new ModelDyingCustom();
				 bn.setDyingDate(rs.getDate("DYING_DATE"));
				 System.out.println(rs.getDate("DYING_DATE"));
				 bn.setDyingQty(rs.getDouble("DYING_QTY"));
				 System.out.println(rs.getDouble("DYING_QTY"));
				 bn.setDyingBy(rs.getLong("DYING_BY"));
				 System.out.println(rs.getLong("DYING_BY"));
				 bn.setUserId(rs.getLong("USER_ID"));
				 bn.setUserName(rs.getString("USER_NAME"));
				 bn.setActiveStatus(rs.getInt("ACTIVE_STATUS"));
				 bn.setDyingRemarks(rs.getString("REMARKS"));
				 bn.setDyingId(rs.getLong("DYING_ID"));
				 
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
	      parameters.addValue("id", id);
	      System.out.println(" in daoDyingImp, id:" +id);


		System.out.println("service mapper ");
		return namedParameterJdbcTemplate.query(qry,parameters,serviceMapper);
	}
	
	
	
	
	@Override
	public List<ModelDyingCustom> getPendingDyingOrderDetails(Long buyerId, Long pOId,
			Long dyingById, Date startDate, Date endDate, Long itemId) {
		// TODO Auto-generated method stub
		String qry="select a.dying_plan_id, a.dying_plan_date, a.dying_plan_qty,a.UOM_ID,a.uom_name,a.PO_MST_ID,a.PO_MST_DATE,\r\n" + 
				"a.order_id,a.order_date,a.item_id,a.item_name,a.order_owner_id,a.owner_name,\r\n" + 
				"a.dying_id,a.DYING_DATE,a.DYING_QTY,a.DYING_BY,a.REMARKS,a.ACTIVE_STATUS\r\n" + 
				"FROM (\r\n" + 
				"select a.dying_plan_id, a.dying_plan_date, a.dying_plan_qty,j.UOM_ID,m.uom_name,a.PO_MST_ID,b.PO_MST_DATE,\r\n" + 
				"d.order_id,d.order_date,e.item_id,f.item_name,d.order_owner_id,h.owner_name,\r\n" + 
				"n.dying_id,n.DYING_DATE,n.DYING_QTY,n.DYING_BY,n.REMARKS,n.ACTIVE_STATUS\r\n" + 
				"from mlfm_dying_plan a \r\n" + 
				"inner join mlfm_po_mst b on b.PO_MST_ID = a.PO_MST_ID\r\n" + 
				"INNER JOIN mlfm_po_chd j on b.PO_MST_ID=j.PO_MST_ID\r\n" + 
				"INNER JOIN mlfm_uom m on j.UOM_ID=m.uom_id\r\n" + 
				"inner join mlfm_design c on b.design_id=c.design_id\r\n" + 
				"inner join mlfm_order d on c.order_id=d.order_id\r\n" + 
				"inner join mlfm_order_item e on d.order_id=e.order_id \r\n" + 
				"inner join mlfm_item f on e.item_id=f.item_id \r\n" + 
				"inner join mlfm_order_owner h on d.order_owner_id=h.order_owner_id\r\n" + 
				"left outer join mlfm_dying n on a.dying_plan_id=n.dying_plan_id\r\n" + 
				") a \r\n" + 
				"where a.order_owner_id=coalesce(:buyerId,a.order_owner_id) and a.item_id=coalesce(:itemId,a.item_id) and a.PO_MST_ID=coalesce(:pOId,a.PO_MST_ID)\r\n" + 
				"and a.dying_plan_date BETWEEN coalesce(:startDate,a.dying_plan_date) AND coalesce(:endDate,a.dying_plan_date)\r\n" + 
				"and coalesce(a.DYING_BY,9999999999)=coalesce(:dyingById,coalesce(a.DYING_BY,9999999999)) \r\n" + 
				"group by a.dying_plan_id, a.dying_plan_date, a.dying_plan_qty,a.UOM_ID,a.uom_name,a.PO_MST_ID,a.PO_MST_DATE,\r\n" + 
				"a.order_id,a.order_date,a.item_id,a.item_name,a.order_owner_id,a.owner_name,\r\n" + 
				"a.dying_id,a.DYING_DATE,a.DYING_QTY,a.DYING_BY,a.REMARKS,a.ACTIVE_STATUS";
		
		RowMapper<ModelDyingCustom> serviceMapper=new RowMapper<ModelDyingCustom>() {
			public ModelDyingCustom mapRow(ResultSet rs, int rownum) throws SQLException{
			ModelDyingCustom bn=new ModelDyingCustom();
			Double dyingQtyUnDone= rs.getDouble("DYING_PLAN_QTY")-rs.getDouble("DYING_QTY");
					
						   
						   bn.setDyingPlanId(rs.getLong("DYING_PLAN_ID"));
						   bn.setDyingPlanDate(rs.getDate("DYING_PLAN_DATE"));
						   bn.setDyingPlanQty(rs.getDouble("DYING_PLAN_QTY"));
						   bn.setDyingId(rs.getLong("DYING_ID"));
						   bn.setDyingDate(rs.getDate("DYING_DATE"));
						   bn.setDyingQty(rs.getDouble("DYING_QTY"));
						   
						   bn.setInquiryId(rs.getLong("ORDER_ID"));
						   bn.setInquiryDate(rs.getDate("ORDER_DATE"));
						   
						   bn.setpOId(rs.getLong("PO_MST_ID"));
						   bn.setpODate(rs.getDate("PO_MST_DATE"));
						   
						   bn.setItemId(rs.getLong("ITEM_ID"));
						   System.out.println(rs.getLong("ITEM_ID"));
						   bn.setItemName(rs.getString("ITEM_NAME"));
					       System.out.println(rs.getString("ITEM_NAME"));
						   bn.setInquiryId(rs.getLong("ORDER_ID"));
						   System.out.println(rs.getLong("ORDER_ID"));
						   bn.setInquiryDate(rs.getDate("ORDER_DATE"));
						   System.out.println(rs.getDate("ORDER_DATE"));
						   bn.setBuyerId(rs.getLong("ORDER_OWNER_ID"));
						   System.out.println(rs.getLong("ORDER_OWNER_ID"));
						   bn.setBuyerName(rs.getString("OWNER_NAME"));
						   System.out.println(rs.getString("OWNER_NAME"));
						   bn.setUomId(rs.getLong("UOM_ID"));
						   System.out.println(rs.getLong("UOM_ID"));
						   bn.setUomName(rs.getString("UOM_NAME"));
						   System.out.println(rs.getString("UOM_NAME"));
						  
						   bn.setDyingQtyUnDone(dyingQtyUnDone);
						   
						   return bn;
						}

			};
		
			MapSqlParameterSource parameters = new MapSqlParameterSource();
			
			  parameters.addValue("buyerId", buyerId);
		      System.out.println(" in daoDyingImp, ownerName:" +buyerId);
		      parameters.addValue("dyingById", dyingById);
		      parameters.addValue("pOId", pOId);
		      System.out.println("in daoDyingImp, pOId: "+pOId);
		      parameters.addValue("startDate", startDate);
		      parameters.addValue("endDate", endDate);
		      parameters.addValue("itemId", itemId);

			System.out.println("service mapper ");
			return namedParameterJdbcTemplate.query(qry,parameters,serviceMapper);
	}

	@Override
	public List<ModelDyingCustom> getPendingDyingWODetails(Long buyerId, Long pOId, Long dyingById,
			Date startDate, Date endDate, Long itemId) {
		// TODO Auto-generated method stub
		String qry="select a.dying_plan_id, a.dying_plan_date, a.dying_plan_qty,a.UOM_ID,a.uom_name,a.PO_MST_ID,a.PO_MST_DATE,\r\n" + 
				"a.order_id,a.order_date,a.item_id,a.item_name,a.order_owner_id,a.owner_name,\r\n" + 
				"a.dying_id,a.DYING_DATE,a.DYING_QTY,a.DYING_BY,a.REMARKS,a.ACTIVE_STATUS,\r\n" + 
				"a.WO_MST_ID,a.WO_DATE\r\n" + 
				"FROM (\r\n" + 
				"select a.dying_plan_id, a.dying_plan_date, a.dying_plan_qty,j.UOM_ID,m.uom_name,a.PO_MST_ID,b.PO_MST_DATE,\r\n" + 
				"d.order_id,d.order_date,e.item_id,f.item_name,d.order_owner_id,h.owner_name,\r\n" + 
				"n.dying_id,n.DYING_DATE,n.DYING_QTY,n.DYING_BY,n.REMARKS,n.ACTIVE_STATUS,\r\n" + 
				"k.WO_MST_ID,l.WO_DATE\r\n" + 
				"from mlfm_dying_plan a \r\n" + 
				"inner join mlfm_po_mst b on b.PO_MST_ID = a.PO_MST_ID\r\n" + 
				"INNER JOIN mlfm_po_chd j on b.PO_MST_ID=j.PO_MST_ID\r\n" + 
				"LEFT OUTER JOIN mlfm_uom m on j.UOM_ID=m.uom_id\r\n" + 
				"inner join mlfm_design c on b.design_id=c.design_id\r\n" + 
				"inner join mlfm_order d on c.order_id=d.order_id\r\n" + 
				"inner join mlfm_order_item e on d.order_id=e.order_id \r\n" + 
				"inner join mlfm_item f on e.item_id=f.item_id \r\n" + 
				"inner join mlfm_order_owner h on d.order_owner_id=h.order_owner_id\r\n" + 
				"left outer join mlfm_dying n on a.dying_plan_id=n.dying_plan_id\r\n" + 
				"INNER JOIN mlfm_production_plan_chd g on j.PRODUCTION_PLAN_CHD_ID=g.PRODUCTION_PLAN_CHD_ID\r\n" + 
				"INNER JOIN mlfm_production_plan_mst i on g.PRODUCTION_PLAN_MST_ID=i.PRODUCTION_PLAN_MST_ID\r\n" + 
				"INNER JOIN mlfm_wo_chd k on i.WO_CHD_ID=k.WO_CHD_ID\r\n" + 
				"INNER JOIN mlfm_wo_mst l on k.WO_MST_ID=l.WO_MST_ID\r\n" + 
				") a \r\n" + 
				"where a.order_owner_id=coalesce(:buyerId,a.order_owner_id) and a.item_id=coalesce(:itemId,a.item_id) and a.PO_MST_ID=coalesce(:pOId,a.PO_MST_ID)\r\n" + 
				"and a.dying_plan_date BETWEEN coalesce(:startDate,a.dying_plan_date) AND coalesce(:endDate,a.dying_plan_date)\r\n" + 
				"and coalesce(a.DYING_BY,9999999999)=coalesce(:dyingById,coalesce(a.DYING_BY,9999999999)) \r\n" + 
				"group by a.dying_plan_id, a.dying_plan_date, a.dying_plan_qty,a.UOM_ID,a.uom_name,a.PO_MST_ID,a.PO_MST_DATE,\r\n" + 
				"a.order_id,a.order_date,a.item_id,a.item_name,a.order_owner_id,a.owner_name,\r\n" + 
				"a.dying_id,a.DYING_DATE,a.DYING_QTY,a.DYING_BY,a.REMARKS,a.ACTIVE_STATUS,\r\n" + 
				"a.WO_MST_ID,a.WO_DATE";
		
		RowMapper<ModelDyingCustom> serviceMapper=new RowMapper<ModelDyingCustom>() {
			public ModelDyingCustom mapRow(ResultSet rs, int rownum) throws SQLException{
			ModelDyingCustom bn=new ModelDyingCustom();
			Double dyingQtyUnDone= rs.getDouble("DYING_PLAN_QTY")-rs.getDouble("DYING_QTY");
			
			   
			   bn.setDyingPlanId(rs.getLong("DYING_PLAN_ID"));
			   bn.setDyingPlanDate(rs.getDate("DYING_PLAN_DATE"));
			   bn.setDyingPlanQty(rs.getDouble("DYING_PLAN_QTY"));
			   bn.setDyingId(rs.getLong("DYING_ID"));
			   bn.setDyingDate(rs.getDate("DYING_DATE"));
			   bn.setDyingQty(rs.getDouble("DYING_QTY"));
			   
			   bn.setwOMstId(rs.getLong("WO_MST_ID"));
			   bn.setwODate(rs.getDate("WO_DATE"));
			   
			   bn.setpOId(rs.getLong("PO_MST_ID"));
			   bn.setpODate(rs.getDate("PO_MST_DATE"));
			   
			   bn.setItemId(rs.getLong("ITEM_ID"));
			   System.out.println(rs.getLong("ITEM_ID"));
			   bn.setItemName(rs.getString("ITEM_NAME"));
		       System.out.println(rs.getString("ITEM_NAME"));
			   bn.setInquiryId(rs.getLong("ORDER_ID"));
			   System.out.println(rs.getLong("ORDER_ID"));
			   bn.setInquiryDate(rs.getDate("ORDER_DATE"));
			   System.out.println(rs.getDate("ORDER_DATE"));
			   bn.setBuyerId(rs.getLong("ORDER_OWNER_ID"));
			   System.out.println(rs.getLong("ORDER_OWNER_ID"));
			   bn.setBuyerName(rs.getString("OWNER_NAME"));
			   System.out.println(rs.getString("OWNER_NAME"));
			   bn.setUomId(rs.getLong("UOM_ID"));
			   System.out.println(rs.getLong("UOM_ID"));
			   bn.setUomName(rs.getString("UOM_NAME"));
			   System.out.println(rs.getString("UOM_NAME"));
			  
			   bn.setDyingQtyUnDone(dyingQtyUnDone);
			   
			   return bn;
			}

		};
	
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		
		  parameters.addValue("buyerId", buyerId);
	      System.out.println(" in daoDyingImp, ownerName:" +buyerId);
	      parameters.addValue("dyingById", dyingById);
	      parameters.addValue("pOId", pOId);
	      System.out.println("in daoDyingImp, pOId: "+pOId);
	      parameters.addValue("startDate", startDate);
	      parameters.addValue("endDate", endDate);
	      parameters.addValue("itemId", itemId);

		System.out.println("service mapper ");
		return namedParameterJdbcTemplate.query(qry,parameters,serviceMapper);
	}

	@Override
	public List<ModelDyingCustom> getCompletedDyingOrderDetails(Long buyerId, Long pOId,
			Long dyingById, Date startDate, Date endDate, Long itemId) {
		// TODO Auto-generated method stub
String qry="select a.dying_plan_id, a.dying_plan_date, a.dying_plan_qty,a.UOM_ID,a.uom_name,a.PO_MST_ID,a.PO_MST_DATE,\r\n" + 
		"a.order_id,a.order_date,a.item_id,a.item_name,a.order_owner_id,a.owner_name,\r\n" + 
		"a.dying_id,a.DYING_DATE,a.DYING_QTY,a.DYING_BY,a.REMARKS,a.ACTIVE_STATUS\r\n" + 
		"FROM (\r\n" + 
		"select a.dying_plan_id, a.dying_plan_date, a.dying_plan_qty,j.UOM_ID,m.uom_name,a.PO_MST_ID,b.PO_MST_DATE,\r\n" + 
		"d.order_id,d.order_date,e.item_id,f.item_name,d.order_owner_id,h.owner_name,\r\n" + 
		"n.dying_id,n.DYING_DATE,n.DYING_QTY,n.DYING_BY,n.REMARKS,n.ACTIVE_STATUS\r\n" + 
		"from mlfm_dying n\r\n" + 
		"INNER JOIN mlfm_dying_plan a on n.DYING_PLAN_ID=a.DYING_PLAN_ID \r\n" + 
		"inner join mlfm_po_mst b on b.PO_MST_ID = a.PO_MST_ID\r\n" + 
		"INNER JOIN mlfm_po_chd j on b.PO_MST_ID=j.PO_MST_ID\r\n" + 
		"INNER JOIN mlfm_uom m on j.UOM_ID=m.uom_id\r\n" + 
		"inner join mlfm_design c on b.design_id=c.design_id\r\n" + 
		"inner join mlfm_order d on c.order_id=d.order_id\r\n" + 
		"inner join mlfm_order_item e on d.order_id=e.order_id \r\n" + 
		"inner join mlfm_item f on e.item_id=f.item_id \r\n" + 
		"inner join mlfm_order_owner h on d.order_owner_id=h.order_owner_id\r\n" + 
		") a \r\n" + 
		"where a.order_owner_id=coalesce(:buyerId,a.order_owner_id) and a.item_id=coalesce(:itemId,a.item_id) and a.PO_MST_ID=coalesce(:pOId,a.PO_MST_ID)\r\n" + 
		"and a.DYING_DATE BETWEEN coalesce(:startDate,a.DYING_DATE) AND coalesce(:endDate,a.DYING_DATE)\r\n" + 
		"and coalesce(a.DYING_BY,9999999999)=coalesce(:dyingById,coalesce(a.DYING_BY,9999999999)) \r\n" + 
		"group by a.dying_plan_id, a.dying_plan_date, a.dying_plan_qty,a.UOM_ID,a.uom_name,a.PO_MST_ID,a.PO_MST_DATE,\r\n" + 
		"a.order_id,a.order_date,a.item_id,a.item_name,a.order_owner_id,a.owner_name,\r\n" + 
		"a.dying_id,a.DYING_DATE,a.DYING_QTY,a.DYING_BY,a.REMARKS,a.ACTIVE_STATUS";
		
		RowMapper<ModelDyingCustom> serviceMapper=new RowMapper<ModelDyingCustom>() {
			public ModelDyingCustom mapRow(ResultSet rs, int rownum) throws SQLException{
			ModelDyingCustom bn=new ModelDyingCustom();
			Double dyingQtyUnDone= rs.getDouble("DYING_PLAN_QTY")-rs.getDouble("DYING_QTY");
			
			   
			   bn.setDyingPlanId(rs.getLong("DYING_PLAN_ID"));
			   bn.setDyingPlanDate(rs.getDate("DYING_PLAN_DATE"));
			   bn.setDyingPlanQty(rs.getDouble("DYING_PLAN_QTY"));
			   bn.setDyingId(rs.getLong("DYING_ID"));
			   bn.setDyingDate(rs.getDate("DYING_DATE"));
			   bn.setDyingQty(rs.getDouble("DYING_QTY"));
			   
			   bn.setInquiryId(rs.getLong("ORDER_ID"));
			   bn.setInquiryDate(rs.getDate("ORDER_DATE"));
			   
			   bn.setpOId(rs.getLong("PO_MST_ID"));
			   bn.setpODate(rs.getDate("PO_MST_DATE"));
			   
			   bn.setItemId(rs.getLong("ITEM_ID"));
			   System.out.println(rs.getLong("ITEM_ID"));
			   bn.setItemName(rs.getString("ITEM_NAME"));
		       System.out.println(rs.getString("ITEM_NAME"));
			   bn.setInquiryId(rs.getLong("ORDER_ID"));
			   System.out.println(rs.getLong("ORDER_ID"));
			   bn.setInquiryDate(rs.getDate("ORDER_DATE"));
			   System.out.println(rs.getDate("ORDER_DATE"));
			   bn.setBuyerId(rs.getLong("ORDER_OWNER_ID"));
			   System.out.println(rs.getLong("ORDER_OWNER_ID"));
			   bn.setBuyerName(rs.getString("OWNER_NAME"));
			   System.out.println(rs.getString("OWNER_NAME"));
			   bn.setUomId(rs.getLong("UOM_ID"));
			   System.out.println(rs.getLong("UOM_ID"));
			   bn.setUomName(rs.getString("UOM_NAME"));
			   System.out.println(rs.getString("UOM_NAME"));
			  
			   bn.setDyingQtyUnDone(dyingQtyUnDone);
			   
			   return bn;
						}

			};
		
			MapSqlParameterSource parameters = new MapSqlParameterSource();
			

			  parameters.addValue("buyerId", buyerId);
		      System.out.println(" in daoDyingImp, ownerName:" +buyerId);
		      parameters.addValue("dyingById", dyingById);
		      parameters.addValue("pOId", pOId);
		      System.out.println("in daoDyingImp, pOId: "+pOId);
		      parameters.addValue("startDate", startDate);
		      parameters.addValue("endDate", endDate);
		      parameters.addValue("itemId", itemId);

			System.out.println("service mapper ");
			return namedParameterJdbcTemplate.query(qry,parameters,serviceMapper);
	}

	@Override
	public List<ModelDyingCustom> getCompletedDyingWODetails(Long buyerId, Long pOId,
			Long dyingById, Date startDate, Date endDate, Long itemId) {
		// TODO Auto-generated method stub
String qry="select a.dying_plan_id, a.dying_plan_date, a.dying_plan_qty,a.UOM_ID,a.uom_name,a.PO_MST_ID,a.PO_MST_DATE,\r\n" + 
		"a.order_id,a.order_date,a.item_id,a.item_name,a.order_owner_id,a.owner_name,\r\n" + 
		"a.dying_id,a.DYING_DATE,a.DYING_QTY,a.DYING_BY,a.REMARKS,a.ACTIVE_STATUS,\r\n" + 
		"a.WO_MST_ID,a.WO_DATE\r\n" + 
		"FROM (\r\n" + 
		"select a.dying_plan_id, a.dying_plan_date, a.dying_plan_qty,j.UOM_ID,m.uom_name,a.PO_MST_ID,b.PO_MST_DATE,\r\n" + 
		"d.order_id,d.order_date,e.item_id,f.item_name,d.order_owner_id,h.owner_name,\r\n" + 
		"n.dying_id,n.DYING_DATE,n.DYING_QTY,n.DYING_BY,n.REMARKS,n.ACTIVE_STATUS,\r\n" + 
		"k.WO_MST_ID,l.WO_DATE\r\n" + 
		"from mlfm_dying n\r\n" + 
		"INNER JOIN mlfm_dying_plan a on n.dying_plan_id=a.DYING_PLAN_ID\r\n" + 
		"inner join mlfm_po_mst b on b.PO_MST_ID = a.PO_MST_ID\r\n" + 
		"INNER JOIN mlfm_po_chd j on b.PO_MST_ID=j.PO_MST_ID\r\n" + 
		"LEFT OUTER JOIN mlfm_uom m on j.UOM_ID=m.uom_id\r\n" + 
		"inner join mlfm_design c on b.design_id=c.design_id\r\n" + 
		"inner join mlfm_order d on c.order_id=d.order_id\r\n" + 
		"inner join mlfm_order_item e on d.order_id=e.order_id \r\n" + 
		"inner join mlfm_item f on e.item_id=f.item_id \r\n" + 
		"inner join mlfm_order_owner h on d.order_owner_id=h.order_owner_id\r\n" + 
		"INNER JOIN mlfm_production_plan_chd g on j.PRODUCTION_PLAN_CHD_ID=g.PRODUCTION_PLAN_CHD_ID\r\n" + 
		"INNER JOIN mlfm_production_plan_mst i on g.PRODUCTION_PLAN_MST_ID=i.PRODUCTION_PLAN_MST_ID\r\n" + 
		"INNER JOIN mlfm_wo_chd k on i.WO_CHD_ID=k.WO_CHD_ID\r\n" + 
		"INNER JOIN mlfm_wo_mst l on k.WO_MST_ID=l.WO_MST_ID\r\n" + 
		") a \r\n" + 
		"where a.order_owner_id=coalesce(:buyerId,a.order_owner_id) and a.item_id=coalesce(:itemId,a.item_id) and a.PO_MST_ID=coalesce(:pOId,a.PO_MST_ID)\r\n" + 
		"and a.DYING_DATE BETWEEN coalesce(:startDate,a.DYING_DATE) AND coalesce(:endDate,a.DYING_DATE)\r\n" + 
		"and coalesce(a.DYING_BY,9999999999)=coalesce(:dyingById,coalesce(a.DYING_BY,9999999999)) \r\n" + 
		"group by a.dying_plan_id, a.dying_plan_date, a.dying_plan_qty,a.UOM_ID,a.uom_name,a.PO_MST_ID,a.PO_MST_DATE,\r\n" + 
		"a.order_id,a.order_date,a.item_id,a.item_name,a.order_owner_id,a.owner_name,\r\n" + 
		"a.dying_id,a.DYING_DATE,a.DYING_QTY,a.DYING_BY,a.REMARKS,a.ACTIVE_STATUS,\r\n" + 
		"a.WO_MST_ID,a.WO_DATE";
		
		RowMapper<ModelDyingCustom> serviceMapper=new RowMapper<ModelDyingCustom>() {
			public ModelDyingCustom mapRow(ResultSet rs, int rownum) throws SQLException{
			ModelDyingCustom bn=new ModelDyingCustom();
			Double dyingQtyUnDone= rs.getDouble("DYING_PLAN_QTY")-rs.getDouble("DYING_QTY");
			
			   
			   bn.setDyingPlanId(rs.getLong("DYING_PLAN_ID"));
			   bn.setDyingPlanDate(rs.getDate("DYING_PLAN_DATE"));
			   bn.setDyingPlanQty(rs.getDouble("DYING_PLAN_QTY"));
			   bn.setDyingId(rs.getLong("DYING_ID"));
			   bn.setDyingDate(rs.getDate("DYING_DATE"));
			   bn.setDyingQty(rs.getDouble("DYING_QTY"));
			   
			   bn.setwOMstId(rs.getLong("WO_MST_ID"));
			   bn.setwODate(rs.getDate("WO_DATE"));
			   
			   bn.setpOId(rs.getLong("PO_MST_ID"));
			   bn.setpODate(rs.getDate("PO_MST_DATE"));
			   
			   bn.setItemId(rs.getLong("ITEM_ID"));
			   System.out.println(rs.getLong("ITEM_ID"));
			   bn.setItemName(rs.getString("ITEM_NAME"));
		       System.out.println(rs.getString("ITEM_NAME"));
			   bn.setInquiryId(rs.getLong("ORDER_ID"));
			   System.out.println(rs.getLong("ORDER_ID"));
			   bn.setInquiryDate(rs.getDate("ORDER_DATE"));
			   System.out.println(rs.getDate("ORDER_DATE"));
			   bn.setBuyerId(rs.getLong("ORDER_OWNER_ID"));
			   System.out.println(rs.getLong("ORDER_OWNER_ID"));
			   bn.setBuyerName(rs.getString("OWNER_NAME"));
			   System.out.println(rs.getString("OWNER_NAME"));
			   bn.setUomId(rs.getLong("UOM_ID"));
			   System.out.println(rs.getLong("UOM_ID"));
			   bn.setUomName(rs.getString("UOM_NAME"));
			   System.out.println(rs.getString("UOM_NAME"));
			  
			   bn.setDyingQtyUnDone(dyingQtyUnDone);
			   
			   return bn;
			}

		};
	
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		
		  parameters.addValue("buyerId", buyerId);
	      System.out.println(" in daoDyingImp, ownerName:" +buyerId);
	      parameters.addValue("dyingById", dyingById);
	      parameters.addValue("pOId", pOId);
	      System.out.println("in daoDyingImp, pOId: "+pOId);
	      parameters.addValue("startDate", startDate);
	      parameters.addValue("endDate", endDate);
	      parameters.addValue("itemId", itemId);

		System.out.println("service mapper ");
		return namedParameterJdbcTemplate.query(qry,parameters,serviceMapper);
	}


	













}
