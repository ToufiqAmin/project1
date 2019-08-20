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

import com.biziitech.mlfm.custom.model.ModelMendingCustom;
import com.biziitech.mlfm.dao.DaoMending;
import com.biziitech.mlfm.model.ModelMending;
import com.biziitech.mlfm.repository.MendingRepository;

@Service
public class DaoMendingImp implements DaoMending{
	
	@Autowired
	private MendingRepository mendingRepository;
	
	
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
	public void saveMending(ModelMending modelMending) {
		// TODO Auto-generated method stub
		
		
		
		mendingRepository.save(modelMending);
		
	}

	@Override
	public List<ModelMendingCustom> getMendingByMendingPlanId(Long mendingPlanId) {
		// TODO Auto-generated method stub
		String qry="SELECT a.MENDING_ID,a.MENDING_DATE,a.MENDED_QTY,\r\n" + 
				"a.MENDED_BY,a.REMARKS,a.ACTIVE_STATUS,\r\n" + 
				"a.MENDING_PLAN_ID,a.QC_ID,b.user_id,b.user_name,\r\n" + 
				"a.UOM_ID,c.uom_name,d.mending_plan_date,d.mending_plan_qty\r\n" + 
				"FROM mlfm_mending a \r\n" + 
				"LEFT OUTER JOIN bg_user b on a.MENDED_BY=b.user_id\r\n" + 
				"LEFT OUTER JOIN mlfm_uom c on a.UOM_ID=c.uom_id\r\n" + 
				"LEFT OUTER JOIN mlfm_mending_plan d on a.MENDING_PLAN_ID=d.MENDING_PLAN_ID\r\n" + 
				"where a.MENDING_PLAN_ID=coalesce(:mendingPlanId,a.MENDING_PLAN_ID) \r\n" + 
				"ORDER BY a.MENDING_DATE DESC ";
		
		
		RowMapper<ModelMendingCustom> serviceMapper=new RowMapper<ModelMendingCustom>() {
			
			
			public ModelMendingCustom mapRow(ResultSet rs, int rownum) throws SQLException{
				ModelMendingCustom bn=new ModelMendingCustom();
				 bn.setMendingDate(rs.getDate("MENDING_DATE"));
				 System.out.println(rs.getDate("MENDING_DATE"));
				 bn.setMendedQty(rs.getDouble("MENDED_QTY"));
				 System.out.println(rs.getDouble("MENDED_QTY"));
				 bn.setMendedBy(rs.getLong("MENDED_BY"));
				 System.out.println(rs.getLong("MENDED_BY"));
				 bn.setUserId(rs.getLong("USER_ID"));
				 bn.setUserName(rs.getString("USER_NAME"));
				 bn.setActiveStatus(rs.getInt("ACTIVE_STATUS"));
				 bn.setMendingRemarks(rs.getString("REMARKS"));
				 bn.setMendingPlanId(rs.getLong("MENDING_PLAN_ID"));
				 bn.setqCId(rs.getLong("QC_ID"));
				 bn.setMendingPlanQty(rs.getDouble("MENDING_PLAN_QTY"));
				 bn.setMendingId(rs.getLong("MENDING_ID"));
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
	      parameters.addValue("mendingPlanId", mendingPlanId);
	      System.out.println(" in daoMendingImp, mendingPlanId:" +mendingPlanId);

		System.out.println("service mapper ");
		return namedParameterJdbcTemplate.query(qry,parameters,serviceMapper);
	}

	@Override
	public List<ModelMendingCustom> getMendedById(Long id, Date startDate, Date endDate) {
		// TODO Auto-generated method stub
		String qry="select m.mending_id,m.mending_date,m.mended_qty, m.mended_by,m.active_status,m.remarks, u.user_id, u.user_name from mlfm_mending m\r\n" + 
				"left join bg_user u on m.mended_by=u.user_id  \r\n" +
				"where m.production_id=coalesce(:id,m.production_id) and m.mending_date BETWEEN coalesce(:startDate,m.mending_date) AND coalesce(:endDate,m.mending_date) and m.active_status=1\r\n" + 
				"ORDER BY m.mending_date DESC";
		
		
		RowMapper<ModelMendingCustom> serviceMapper=new RowMapper<ModelMendingCustom>() {
			
			
			public ModelMendingCustom mapRow(ResultSet rs, int rownum) throws SQLException{
				ModelMendingCustom bn=new ModelMendingCustom();
				 bn.setMendingDate(rs.getDate("MENDING_DATE"));
				 System.out.println(rs.getDate("MENDING_DATE"));
				 bn.setMendedQty(rs.getDouble("MENDED_QTY"));
				 System.out.println(rs.getDouble("MENDED_QTY"));
				 bn.setMendedBy(rs.getLong("MENDED_BY"));
				 System.out.println(rs.getLong("MENDED_BY"));
				 bn.setUserId(rs.getLong("USER_ID"));
				 bn.setUserName(rs.getString("USER_NAME"));
				 bn.setActiveStatus(rs.getInt("ACTIVE_STATUS"));
				 bn.setMendingRemarks(rs.getString("REMARKS"));
				 bn.setMendingId(rs.getLong("MENDING_ID"));
				 
				 if(bn.getActiveStatus()==1)
				 { bn.setsActive("Yes");
				  bn.setActive(true);
				 }
				 
				 else
				 {	 bn.setsActive("NO");
				     bn.setActive(false);
				     
				 }
				
				 return bn;
				
			}
		};
		MapSqlParameterSource parameters = new MapSqlParameterSource();
	      parameters.addValue("id", id);
	      System.out.println(" in daoMendingImp, id:" +id);
	      parameters.addValue("startDate", startDate);
	      parameters.addValue("endDate", endDate);

		System.out.println("service mapper ");
		return namedParameterJdbcTemplate.query(qry,parameters,serviceMapper);
	}
	
	@Override
	public List<ModelMendingCustom> getPendingMendingOrderDetails(Long buyerId, Long inquiryId, Long mendedById,
			Date startDate, Date endDate, Long itemId) {
		// TODO Auto-generated method stub
		String qry="select a.MENDING_PLAN_ID,a.MENDING_PLAN_DATE,a.MENDING_PLAN_QTY,\r\n" + 
				"a.REMARKS,a.ACTIVE_STATUS,a.QC_ID,a.QC_DATE,a.UOM_ID,a.uom_name,\r\n" + 
				"a.PO_MST_ID,a.PO_MST_DATE,\r\n" + 
				"a.order_id,a.order_date,a.order_owner_id,a.owner_name,\r\n" + 
				"a.item_id,a.item_name,a.MENDING_ID,a.MENDING_DATE,a.MENDED_QTY\r\n" + 
				"FROM (\r\n" + 
				"select a.MENDING_PLAN_ID,a.MENDING_PLAN_DATE,a.MENDING_PLAN_QTY,\r\n" + 
				"a.REMARKS,a.ACTIVE_STATUS,a.QC_ID,b.QC_DATE,a.UOM_ID,d.uom_name,\r\n" + 
				"a.PO_MST_ID,e.PO_MST_DATE,\r\n" + 
				"f.order_id,g.order_date,g.order_owner_id,h.owner_name, \r\n" + 
				"i.item_id,j.item_name,k.MENDING_ID,k.MENDING_DATE,k.MENDED_QTY\r\n" + 
				"FROM mlfm_mending_plan a \r\n" + 
				"INNER JOIN mlfm_qc b on a.QC_ID=b.QC_ID \r\n" + 
				"INNER JOIN mlfm_qc_plan c on b.QC_PLAN_ID=c.QC_PLAN_ID\r\n" + 
				"INNER JOIN mlfm_po_mst e on a.PO_MST_ID=e.PO_MST_ID\r\n" + 
				"INNER JOIN mlfm_design f on e.DESIGN_ID=f.design_id\r\n" + 
				"INNER JOIN mlfm_order g on f.order_id=g.order_id\r\n" + 
				"INNER JOIN mlfm_order_owner h on g.order_owner_id=h.order_owner_id\r\n" + 
				"INNER JOIN mlfm_order_item i on g.order_id=i.order_id\r\n" + 
				"INNER JOIN mlfm_item j on i.item_id=j.item_id\r\n" + 
				"LEFT OUTER JOIN mlfm_mending k on a.MENDING_PLAN_ID=k.MENDING_PLAN_ID\r\n" + 
				"LEFT OUTER JOIN mlfm_uom d on a.UOM_ID=d.uom_id\r\n" + 
				") a where\r\n" + 
				"a.order_id=coalesce(:inquiryId,a.order_id) and a.order_owner_id=coalesce(:buyerId,a.order_owner_id) \r\n" + 
				"and a.MENDING_PLAN_DATE BETWEEN coalesce(:startDate,a.MENDING_PLAN_DATE) AND coalesce(:endDate,a.MENDING_PLAN_DATE)\r\n" + 
				"group by a.MENDING_PLAN_ID,a.MENDING_PLAN_DATE,a.MENDING_PLAN_QTY,\r\n" + 
				"a.REMARKS,a.ACTIVE_STATUS,a.QC_ID,a.QC_DATE,a.UOM_ID,a.uom_name,\r\n" + 
				"a.PO_MST_ID,a.PO_MST_DATE, \r\n" + 
				"a.order_id,a.order_date,a.order_owner_id,a.owner_name,\r\n" + 
				"a.item_id,a.item_name,a.MENDING_ID,a.MENDING_DATE,a.MENDED_QTY";
				
		RowMapper<ModelMendingCustom> serviceMapper=new RowMapper<ModelMendingCustom>() {
			public ModelMendingCustom mapRow(ResultSet rs, int rownum) throws SQLException{
			ModelMendingCustom bn=new ModelMendingCustom();
			Double mendingQtyMax= rs.getDouble("MENDING_PLAN_QTY")-rs.getDouble("MENDED_QTY");

			 			   bn.setpOMstId(rs.getLong("PO_MST_ID"));
			 			   bn.setpODate(rs.getDate("PO_MST_DATE"));
				           bn.setMendingPlanId(rs.getLong("MENDING_PLAN_ID"));
				           bn.setMendingPlanDate(rs.getDate("MENDING_PLAN_DATE"));
				           bn.setMendingPlanQty(rs.getDouble("MENDING_PLAN_QTY"));
				           bn.setqCId(rs.getLong("QC_ID"));
				           bn.setqCDate(rs.getDate("QC_DATE"));
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
						   bn.setMendingId(rs.getLong("MENDING_ID"));
						   bn.setMendingDate(rs.getDate("MENDING_DATE"));
						   bn.setMendedQty(rs.getDouble("MENDED_QTY"));
						   bn.setMendingQtyMax(mendingQtyMax);
						   System.out.println("mendingQtyMax: "+mendingQtyMax);
						   
						   
						   return bn;
						}

			};
		
			MapSqlParameterSource parameters = new MapSqlParameterSource();
			

			  parameters.addValue("buyerId", buyerId);
		      System.out.println(" in daoMendingImp, ownerName:" +buyerId);
		      parameters.addValue("mendedById", mendedById);
		      parameters.addValue("inquiryId", inquiryId);
		      System.out.println("in daoMendingImp, inquiryId: "+inquiryId);
		      parameters.addValue("startDate", startDate);
		      parameters.addValue("endDate", endDate);
		      parameters.addValue("itemId", itemId);

			System.out.println("service mapper ");
			return namedParameterJdbcTemplate.query(qry,parameters,serviceMapper);
	}

	
	
	@Override
	public List<ModelMendingCustom> getPendingMendingWODetails(Long buyerId, Long inquiryId, Long mendedById,
			Date startDate, Date endDate, Long itemId) {
		// TODO Auto-generated method stub
		String qry="select a.MENDING_PLAN_ID,a.MENDING_PLAN_DATE,a.MENDING_PLAN_QTY,\r\n" + 
				"a.REMARKS,a.ACTIVE_STATUS,a.QC_ID,a.QC_DATE,a.UOM_ID,a.uom_name,\r\n" + 
				"a.PO_MST_ID,a.PO_MST_DATE,\r\n" + 
				"a.order_id,a.order_date,a.order_owner_id,a.owner_name,\r\n" + 
				"a.item_id,a.item_name,a.WO_MST_ID,a.WO_DATE,a.MENDING_ID,a.MENDING_DATE,a.MENDED_QTY\r\n" + 
				"FROM (\r\n" + 
				"select a.MENDING_PLAN_ID,a.MENDING_PLAN_DATE,a.MENDING_PLAN_QTY,\r\n" + 
				"a.REMARKS,a.ACTIVE_STATUS,a.QC_ID,b.QC_DATE,a.UOM_ID,d.uom_name,\r\n" + 
				"a.PO_MST_ID,e.PO_MST_DATE,\r\n" + 
				"f.order_id,g.order_date,g.order_owner_id,h.owner_name,\r\n" + 
				"i.item_id,j.item_name,n.WO_MST_ID,o.WO_DATE,\r\n" + 
				"p.MENDING_ID,p.MENDING_DATE,p.MENDED_QTY\r\n" + 
				"FROM mlfm_mending_plan a\r\n" + 
				"INNER JOIN mlfm_qc b on a.QC_ID=b.QC_ID\r\n" + 
				"INNER JOIN mlfm_qc_plan c on b.QC_PLAN_ID=c.QC_PLAN_ID\r\n" + 
				"INNER JOIN mlfm_po_mst e on a.PO_MST_ID=e.PO_MST_ID\r\n" + 
				"INNER JOIN mlfm_design f on e.DESIGN_ID=f.design_id\r\n" + 
				"INNER JOIN mlfm_order g on f.order_id=g.order_id\r\n" + 
				"INNER JOIN mlfm_order_owner h on g.order_owner_id=h.order_owner_id\r\n" + 
				"INNER JOIN mlfm_order_item i on g.order_id=i.order_id\r\n" + 
				"INNER JOIN mlfm_item j on i.item_id=j.item_id\r\n" + 
				"INNER JOIN mlfm_po_chd k on e.PO_MST_ID=k.PO_MST_ID\r\n" + 
				"INNER JOIN mlfm_production_plan_chd l on k.PRODUCTION_PLAN_CHD_ID=l.PRODUCTION_PLAN_CHD_ID\r\n" + 
				"INNER JOIN mlfm_production_plan_mst m on l.PRODUCTION_PLAN_MST_ID=m.PRODUCTION_PLAN_MST_ID\r\n" + 
				"INNER JOIN mlfm_wo_chd n on m.WO_CHD_ID=n.WO_CHD_ID\r\n" + 
				"INNER JOIN mlfm_wo_mst o on n.WO_MST_ID=o.WO_MST_ID\r\n" + 
				"LEFT OUTER JOIN mlfm_mending p on a.MENDING_PLAN_ID=p.MENDING_PLAN_ID\r\n" + 
				"LEFT OUTER JOIN mlfm_uom d on a.UOM_ID=d.uom_id\r\n" + 
				") a where \r\n" + 
				"a.PO_MST_ID=coalesce(:inquiryId,a.PO_MST_ID) and a.order_owner_id=coalesce(:buyerId,a.order_owner_id)\r\n" + 
				"and a.MENDING_PLAN_DATE BETWEEN coalesce(:startDate,a.MENDING_PLAN_DATE) AND coalesce(:endDate,a.MENDING_PLAN_DATE)\r\n" + 
				"group by a.MENDING_PLAN_ID,a.MENDING_PLAN_DATE,a.MENDING_PLAN_QTY,\r\n" + 
				"a.REMARKS,a.ACTIVE_STATUS,a.QC_ID,a.QC_DATE,a.UOM_ID,a.uom_name,\r\n" + 
				"a.PO_MST_ID,a.PO_MST_DATE,\r\n" + 
				"a.order_id,a.order_date,a.order_owner_id,a.owner_name,\r\n" + 
				"a.item_id,a.item_name,a.WO_MST_ID,a.WO_DATE,a.MENDING_ID,a.MENDING_DATE,a.MENDED_QTY";
		
		
		RowMapper<ModelMendingCustom> serviceMapper=new RowMapper<ModelMendingCustom>() {
			public ModelMendingCustom mapRow(ResultSet rs, int rownum) throws SQLException{
			ModelMendingCustom bn=new ModelMendingCustom();
			Double mendingQtyMax= rs.getDouble("MENDING_PLAN_QTY")-rs.getDouble("MENDED_QTY");
			 
			
			 bn.setpOMstId(rs.getLong("PO_MST_ID"));
			 bn.setpODate(rs.getDate("PO_MST_DATE"));
			 bn.setMendingPlanId(rs.getLong("MENDING_PLAN_ID"));
	         bn.setMendingPlanDate(rs.getDate("MENDING_PLAN_DATE"));
	         bn.setMendingPlanQty(rs.getDouble("MENDING_PLAN_QTY"));
	         bn.setqCId(rs.getLong("QC_ID"));
	         bn.setqCDate(rs.getDate("QC_DATE"));
			 bn.setwOMstId(rs.getLong("WO_MST_ID"));
 	         bn.setwODate(rs.getDate("WO_DATE"));
			 bn.setItemId(rs.getLong("ITEM_ID"));
		     System.out.println(rs.getLong("ITEM_ID"));
			 bn.setItemName(rs.getString("ITEM_NAME"));
			 System.out.println(rs.getString("ITEM_NAME"));			 
			 bn.setBuyerId(rs.getLong("ORDER_OWNER_ID"));
			 System.out.println(rs.getLong("ORDER_OWNER_ID"));
			 bn.setBuyerName(rs.getString("OWNER_NAME"));
			 System.out.println(rs.getString("OWNER_NAME"));
		     bn.setUomName(rs.getString("UOM_NAME"));
			 System.out.println(rs.getString("UOM_NAME"));
			 bn.setUomId(rs.getLong("UOM_ID"));
			 System.out.println(rs.getLong("UOM_ID"));
			 bn.setMendingId(rs.getLong("MENDING_ID"));
			 bn.setMendingDate(rs.getDate("MENDING_DATE"));
			 bn.setMendedQty(rs.getDouble("MENDED_QTY"));
			 bn.setMendingQtyMax(mendingQtyMax);
			 System.out.println("mendingQtyMax: "+mendingQtyMax);
						    
			 return bn;
						}

			};
			MapSqlParameterSource parameters = new MapSqlParameterSource();
			

			  parameters.addValue("buyerId", buyerId);
		      System.out.println(" in daoMendingImp, ownerName:" +buyerId);
		      parameters.addValue("mendedById", mendedById);
		      parameters.addValue("inquiryId", inquiryId);
		      System.out.println("in daoMendingImp, inquiryId: "+inquiryId);
		      parameters.addValue("startDate", startDate);
		      parameters.addValue("endDate", endDate);
		      parameters.addValue("itemId", itemId);

			System.out.println("service mapper ");
			return namedParameterJdbcTemplate.query(qry,parameters,serviceMapper);
	}

	@Override
	public List<ModelMendingCustom> getCompletedMendingOrderDetails(Long buyerId, Long inquiryId, Long mendedById,
			Date startDate, Date endDate, Long itemId) {
		// TODO Auto-generated method stub

		String qry="select a.MENDING_PLAN_ID,a.MENDING_PLAN_DATE,a.MENDING_PLAN_QTY,\r\n" + 
				"a.REMARKS,a.ACTIVE_STATUS,a.QC_ID,a.QC_DATE,a.UOM_ID,a.uom_name,\r\n" + 
				"a.PO_MST_ID,a.PO_MST_DATE,\r\n" + 
				"a.order_id,a.order_date,a.order_owner_id,a.owner_name,\r\n" + 
				"a.item_id,a.item_name,a.MENDING_ID,a.MENDING_DATE,a.MENDED_QTY\r\n" + 
				"FROM (\r\n" + 
				"select a.MENDING_PLAN_ID,a.MENDING_PLAN_DATE,a.MENDING_PLAN_QTY,\r\n" + 
				"a.REMARKS,a.ACTIVE_STATUS,a.QC_ID,b.QC_DATE,k.UOM_ID,d.uom_name,\r\n" + 
				"a.PO_MST_ID,e.PO_MST_DATE,\r\n" + 
				"f.order_id,g.order_date,g.order_owner_id,h.owner_name,\r\n" + 
				"i.item_id,j.item_name,k.MENDING_ID,k.MENDING_DATE,k.MENDED_QTY\r\n" + 
				"FROM mlfm_mending k\r\n" + 
				"INNER JOIN mlfm_mending_plan a on k.MENDING_PLAN_ID=a.MENDING_PLAN_ID\r\n" + 
				"INNER JOIN mlfm_qc b on a.QC_ID=b.QC_ID\r\n" + 
				"INNER JOIN mlfm_qc_plan c on b.QC_PLAN_ID=c.QC_PLAN_ID\r\n" + 
				"INNER JOIN mlfm_po_mst e on a.PO_MST_ID=e.PO_MST_ID\r\n" + 
				"INNER JOIN mlfm_design f on e.DESIGN_ID=f.design_id\r\n" + 
				"INNER JOIN mlfm_order g on f.order_id=g.order_id\r\n" + 
				"INNER JOIN mlfm_order_owner h on g.order_owner_id=h.order_owner_id\r\n" + 
				"INNER JOIN mlfm_order_item i on g.order_id=i.order_id\r\n" + 
				"INNER JOIN mlfm_item j on i.item_id=j.item_id\r\n" +
				"LEFT OUTER JOIN mlfm_uom d on k.UOM_ID=d.uom_id\r\n" + 
				") a where \r\n" + 
				"a.order_id=coalesce(:inquiryId,a.order_id) and a.order_owner_id=coalesce(:buyerId,a.order_owner_id)\r\n" + 
				"and a.MENDING_DATE BETWEEN coalesce(:startDate,a.MENDING_DATE) AND coalesce(:endDate,a.MENDING_DATE)\r\n" + 
				"group by a.MENDING_PLAN_ID,a.MENDING_PLAN_DATE,a.MENDING_PLAN_QTY,\r\n" + 
				"a.REMARKS,a.ACTIVE_STATUS,a.QC_ID,a.QC_DATE,a.UOM_ID,a.uom_name,\r\n" + 
				"a.PO_MST_ID,a.PO_MST_DATE,\r\n" + 
				"a.order_id,a.order_date,a.order_owner_id,a.owner_name,\r\n" + 
				"a.item_id,a.item_name,a.MENDING_ID,a.MENDING_DATE,a.MENDED_QTY";
		
		RowMapper<ModelMendingCustom> serviceMapper=new RowMapper<ModelMendingCustom>() {
			public ModelMendingCustom mapRow(ResultSet rs, int rownum) throws SQLException{
			ModelMendingCustom bn=new ModelMendingCustom();
			Double mendingQtyMax= rs.getDouble("MENDING_PLAN_QTY")-rs.getDouble("MENDED_QTY");

			 			   bn.setpOMstId(rs.getLong("PO_MST_ID"));
			 			   bn.setpODate(rs.getDate("PO_MST_DATE"));
				           bn.setMendingPlanId(rs.getLong("MENDING_PLAN_ID"));
				           bn.setMendingPlanDate(rs.getDate("MENDING_PLAN_DATE"));
				           bn.setMendingPlanQty(rs.getDouble("MENDING_PLAN_QTY"));
				           bn.setqCId(rs.getLong("QC_ID"));
				           bn.setqCDate(rs.getDate("QC_DATE"));
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
						   bn.setMendingId(rs.getLong("MENDING_ID"));
						   bn.setMendingDate(rs.getDate("MENDING_DATE"));
						   bn.setMendedQty(rs.getDouble("MENDED_QTY"));
						   bn.setMendingQtyMax(mendingQtyMax);
						   System.out.println("mendingQtyMax: "+mendingQtyMax);
						   
						   
						   return bn;
			
			}
		};
		
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		
		  parameters.addValue("buyerId", buyerId);
	      System.out.println(" in daoMendingImp, ownerName:" +buyerId);
	      parameters.addValue("mendedById", mendedById);
	      parameters.addValue("inquiryId", inquiryId);
	      System.out.println("in daoMendingImp, inquiryId: "+inquiryId);
	      parameters.addValue("startDate", startDate);
	      parameters.addValue("endDate", endDate);
	      parameters.addValue("itemId", itemId);

		System.out.println("service mapper ");
		return namedParameterJdbcTemplate.query(qry,parameters,serviceMapper);
		

	}

	@Override
	public List<ModelMendingCustom> getCompletedMendingWODetails(Long buyerId, Long inquiryId, Long mendedById,
			Date startDate, Date endDate, Long itemId) {
		// TODO Auto-generated method stub

		
		String qry="select a.MENDING_PLAN_ID,a.MENDING_PLAN_DATE,a.MENDING_PLAN_QTY,\r\n" + 
				"a.REMARKS,a.ACTIVE_STATUS,a.QC_ID,a.QC_DATE,a.UOM_ID,a.uom_name,\r\n" + 
				"a.PO_MST_ID,a.PO_MST_DATE,\r\n" + 
				"a.order_id,a.order_date,a.order_owner_id,a.owner_name,\r\n" + 
				"a.item_id,a.item_name,a.WO_MST_ID,a.WO_DATE,a.MENDING_ID,a.MENDING_DATE,a.MENDED_QTY\r\n" + 
				"FROM (\r\n" + 
				"select a.MENDING_PLAN_ID,a.MENDING_PLAN_DATE,a.MENDING_PLAN_QTY,\r\n" + 
				"a.REMARKS,a.ACTIVE_STATUS,a.QC_ID,b.QC_DATE,p.UOM_ID,d.uom_name,\r\n" + 
				"a.PO_MST_ID,e.PO_MST_DATE,\r\n" + 
				"f.order_id,g.order_date,g.order_owner_id,h.owner_name,\r\n" + 
				"i.item_id,j.item_name,n.WO_MST_ID,o.WO_DATE,\r\n" + 
				"p.MENDING_ID,p.MENDING_DATE,p.MENDED_QTY\r\n" + 
				"FROM mlfm_mending p\r\n" + 
				"INNER JOIN mlfm_mending_plan a on p.MENDING_PLAN_ID=a.MENDING_PLAN_ID\r\n" + 
				"INNER JOIN mlfm_qc b on a.QC_ID=b.QC_ID\r\n" + 
				"INNER JOIN mlfm_qc_plan c on b.QC_PLAN_ID=c.QC_PLAN_ID\r\n" + 
				"INNER JOIN mlfm_po_mst e on a.PO_MST_ID=e.PO_MST_ID\r\n" + 
				"INNER JOIN mlfm_design f on e.DESIGN_ID=f.design_id\r\n" + 
				"INNER JOIN mlfm_order g on f.order_id=g.order_id\r\n" + 
				"INNER JOIN mlfm_order_owner h on g.order_owner_id=h.order_owner_id\r\n" + 
				"INNER JOIN mlfm_order_item i on g.order_id=i.order_id\r\n" + 
				"INNER JOIN mlfm_item j on i.item_id=j.item_id\r\n" + 
				"INNER JOIN mlfm_po_chd k on e.PO_MST_ID=k.PO_MST_ID\r\n" + 
				"INNER JOIN mlfm_production_plan_chd l on k.PRODUCTION_PLAN_CHD_ID=l.PRODUCTION_PLAN_CHD_ID\r\n" + 
				"INNER JOIN mlfm_production_plan_mst m on l.PRODUCTION_PLAN_MST_ID=m.PRODUCTION_PLAN_MST_ID\r\n" + 
				"INNER JOIN mlfm_wo_chd n on m.WO_CHD_ID=n.WO_CHD_ID\r\n" + 
				"INNER JOIN mlfm_wo_mst o on n.WO_MST_ID=o.WO_MST_ID\r\n" +
				"LEFT OUTER JOIN mlfm_uom d on p.UOM_ID=d.uom_id\r\n" + 
				") a where \r\n" + 
				"a.PO_MST_ID=coalesce(:inquiryId,a.PO_MST_ID) and a.order_owner_id=coalesce(:buyerId,a.order_owner_id)\r\n" + 
				"and a.MENDING_DATE BETWEEN coalesce(:startDate,a.MENDING_DATE) AND coalesce(:endDate,a.MENDING_DATE)\r\n" + 
				"group by a.MENDING_PLAN_ID,a.MENDING_PLAN_DATE,a.MENDING_PLAN_QTY,\r\n" + 
				"a.REMARKS,a.ACTIVE_STATUS,a.QC_ID,a.QC_DATE,a.UOM_ID,a.uom_name,\r\n" + 
				"a.PO_MST_ID,a.PO_MST_DATE,\r\n" + 
				"a.order_id,a.order_date,a.order_owner_id,a.owner_name,\r\n" + 
				"a.item_id,a.item_name,a.WO_MST_ID,a.WO_DATE,a.MENDING_ID,a.MENDING_DATE,a.MENDED_QTY";
		
		
		RowMapper<ModelMendingCustom> serviceMapper=new RowMapper<ModelMendingCustom>() {
			public ModelMendingCustom mapRow(ResultSet rs, int rownum) throws SQLException{
			ModelMendingCustom bn=new ModelMendingCustom();
			Double mendingQtyMax= rs.getDouble("MENDING_PLAN_QTY")-rs.getDouble("MENDED_QTY");
			 
			
			 bn.setpOMstId(rs.getLong("PO_MST_ID"));
			 bn.setpODate(rs.getDate("PO_MST_DATE"));
			 bn.setMendingPlanId(rs.getLong("MENDING_PLAN_ID"));
	         bn.setMendingPlanDate(rs.getDate("MENDING_PLAN_DATE"));
	         bn.setMendingPlanQty(rs.getDouble("MENDING_PLAN_QTY"));
	         bn.setqCId(rs.getLong("QC_ID"));
	         bn.setqCDate(rs.getDate("QC_DATE"));
			 bn.setwOMstId(rs.getLong("WO_MST_ID"));
 	         bn.setwODate(rs.getDate("WO_DATE"));
			 bn.setItemId(rs.getLong("ITEM_ID"));
		     System.out.println(rs.getLong("ITEM_ID"));
			 bn.setItemName(rs.getString("ITEM_NAME"));
			 System.out.println(rs.getString("ITEM_NAME"));			 
			 bn.setBuyerId(rs.getLong("ORDER_OWNER_ID"));
			 System.out.println(rs.getLong("ORDER_OWNER_ID"));
			 bn.setBuyerName(rs.getString("OWNER_NAME"));
			 System.out.println(rs.getString("OWNER_NAME"));
		     bn.setUomName(rs.getString("UOM_NAME"));
			 System.out.println(rs.getString("UOM_NAME"));
			 bn.setUomId(rs.getLong("UOM_ID"));
			 System.out.println(rs.getLong("UOM_ID"));
			 bn.setMendingId(rs.getLong("MENDING_ID"));
			 bn.setMendingDate(rs.getDate("MENDING_DATE"));
			 bn.setMendedQty(rs.getDouble("MENDED_QTY"));
			 bn.setMendingQtyMax(mendingQtyMax);
			 System.out.println("mendingQtyMax: "+mendingQtyMax);
						    
						   return bn;
						}

			};
			MapSqlParameterSource parameters = new MapSqlParameterSource();
			

			  parameters.addValue("buyerId", buyerId);
		      System.out.println(" in daoMendingImp, ownerName:" +buyerId);
		      parameters.addValue("mendedById", mendedById);
		      parameters.addValue("inquiryId", inquiryId);
		      System.out.println("in daoMendingImp, inquiryId: "+inquiryId);
		      parameters.addValue("startDate", startDate);
		      parameters.addValue("endDate", endDate);
		      parameters.addValue("itemId", itemId);

			System.out.println("service mapper ");
			return namedParameterJdbcTemplate.query(qry,parameters,serviceMapper);
			


	}



	

}
