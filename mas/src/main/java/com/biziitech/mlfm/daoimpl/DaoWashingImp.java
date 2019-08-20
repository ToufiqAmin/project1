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

import com.biziitech.mlfm.custom.model.ModelWashingCustom;
import com.biziitech.mlfm.dao.DaoWashing;
import com.biziitech.mlfm.model.ModelWashing;
import com.biziitech.mlfm.repository.WashingRepository;

@Service
public class DaoWashingImp implements DaoWashing{
	
	@Autowired
	private WashingRepository washingRepository;
	
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
	public void saveWashing(ModelWashing modelWashing) {
		// TODO Auto-generated method stub
		
		washingRepository.save(modelWashing);
		
	}
	
	@Override
	public List<ModelWashingCustom> getWashingById(Long washingPlanId) {
		// TODO Auto-generated method stub
		String qry="select w.washing_date,w.washed_qty, w.washed_by,w.washing_id,\r\n" + 
				"w.active_status,w.remarks,w.washing_plan_id,u.user_id,u.user_name,\r\n" + 
				"w.UOM_ID,o.uom_name\r\n" + 
				"from mlfm_washing w \r\n" + 
				"left join bg_user u on w.washed_by=u.user_id\r\n" + 
				"LEFT OUTER JOIN mlfm_uom o on w.UOM_ID=o.uom_id\r\n" + 
				"where w.washing_plan_id=coalesce(:washingPlanId,w.washing_plan_id)\r\n" + 
				"ORDER BY w.WASHING_DATE DESC";
		RowMapper<ModelWashingCustom> serviceMapper=new RowMapper<ModelWashingCustom>() {
			
			
			public ModelWashingCustom mapRow(ResultSet rs, int rownum) throws SQLException{
				ModelWashingCustom bn=new ModelWashingCustom();
				
				 bn.setWashingPlanId(rs.getLong("WASHING_PLAN_ID"));
				 bn.setWashingId(rs.getLong("WASHING_ID"));
				 bn.setWashingDate(rs.getDate("WASHING_DATE"));
				 System.out.println(rs.getDate("WASHING_DATE"));
				 bn.setWashedQty(rs.getDouble("WASHED_QTY"));
				 System.out.println(rs.getDouble("WASHED_QTY"));
				 bn.setWashedBy(rs.getLong("WASHED_BY"));
				 System.out.println(rs.getLong("WASHED_BY"));
				 bn.setUserId(rs.getLong("USER_ID"));
				 bn.setUserName(rs.getString("USER_NAME"));
				 bn.setActiveStatus(rs.getInt("ACTIVE_STATUS"));
				 bn.setWashingRemarks(rs.getString("REMARKS"));
				 bn.setWashingId(rs.getLong("WASHING_ID"));
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
	      parameters.addValue("washingPlanId", washingPlanId);
	      System.out.println(" in daoWashingImp, washingPlanId:" +washingPlanId);
	     

		System.out.println("service mapper ");
		return namedParameterJdbcTemplate.query(qry,parameters,serviceMapper);
	}

	@Override
	public List<ModelWashingCustom> getWashedById(Long id, Date startDate, Date endDate) {
		// TODO Auto-generated method stub
		String qry="select w.washing_date,w.washed_qty, w.washed_by,w.washing_id,w.active_status,w.remarks,u.user_id,u.user_name from mlfm_washing w \r\n" + 
				"left join bg_user u on w.washed_by=u.user_id  \r\n" + 
				"where w.production_id=coalesce(:id,w.production_id) and w.active_status=1 \r\n"+
				"and w.washing_date BETWEEN coalesce(:startDate,w.washing_date) AND coalesce(:endDate,w.washing_date) ORDER BY w.WASHING_DATE DESC";
		RowMapper<ModelWashingCustom> serviceMapper=new RowMapper<ModelWashingCustom>() {
			
			
			public ModelWashingCustom mapRow(ResultSet rs, int rownum) throws SQLException{
				ModelWashingCustom bn=new ModelWashingCustom();
				 bn.setWashingDate(rs.getDate("WASHING_DATE"));
				 System.out.println(rs.getDate("WASHING_DATE"));
				 bn.setWashedQty(rs.getDouble("WASHED_QTY"));
				 System.out.println(rs.getDouble("WASHED_QTY"));
				 bn.setWashedBy(rs.getLong("WASHED_BY"));
				 System.out.println(rs.getLong("WASHED_BY"));
				 bn.setUserId(rs.getLong("USER_ID"));
				 bn.setUserName(rs.getString("USER_NAME"));
				 bn.setActiveStatus(rs.getInt("ACTIVE_STATUS"));
				 bn.setWashingRemarks(rs.getString("REMARKS"));
				 bn.setWashingId(rs.getLong("WASHING_ID"));
				 
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
	      System.out.println(" in daoWashingImp, id:" +id);
	      parameters.addValue("startDate", startDate);
	      parameters.addValue("endDate", endDate);

		System.out.println("service mapper ");
		return namedParameterJdbcTemplate.query(qry,parameters,serviceMapper);
	}

	@Override
	public List<ModelWashingCustom> getPendingWashingOrderDetails(Long buyerId, Long inquiryId,
			Long washedById, Date startDate, Date endDate, Long itemId) {
		// TODO Auto-generated method stub
		String qry="select a.production_id, a.production_date, a.production_qty, a.order_item_qty_id, a.item_qty,\r\n" + 
				"a.uom_id, a.item_id,a.item_name, a.order_id, a.order_date,a.order_owner_id, a.owner_name,\r\n" + 
				"a.uom_name, a.washing_status, sum(a.washed_qty) washed_qty \r\n" + 
				"FROM ( \r\n" + 
				"select a.production_id, a.production_date, a.production_qty, d.order_item_qty_id, d.item_qty,  \r\n" + 
				"d.uom_id, e.item_id,f.item_name, g.order_id, g.order_date,h.order_owner_type_id,g.order_owner_id, h.owner_name, \r\n" + 
				"i.uom_name, (select if (exists(select 1 from mlfm_washing w where w.PRODUCTION_ID=a.PRODUCTION_ID), 1,0)) washing_data_exists, \r\n" + 
				"(select  if ( exists (select 1 from mlfm_washing w where w.PRODUCTION_ID=a.production_id \r\n" + 
				"and w.active_status=1 having sum(w.WASHED_QTY) >=a.PRODUCTION_QTY),'C', 'P')) washing_status,\r\n" + 
				"w.WASHED_QTY,w.washed_by, c.WO_CHD_ID  from mlfm_production a inner join mlfm_production_plan_chd b on a.production_plan_chd_id=b.production_plan_chd_id\r\n" + 
				"inner join mlfm_production_plan_mst c on b.production_plan_mst_id=c.production_plan_mst_id \r\n" + 
				"inner join mlfm_order_item_qty d on c.order_item_qty_id=d.order_item_qty_id inner join mlfm_order_item e on d.order_item_id=e.item_order_id\r\n" + 
				"inner join mlfm_item f on e.item_id=f.item_id inner join mlfm_order g on e.order_id=g.order_id\r\n" + 
				"inner join mlfm_order_owner h on g.order_owner_id=h.order_owner_id inner join mlfm_uom i on d.uom_id=i.uom_id left OUTER join mlfm_washing w on w.PRODUCTION_ID=a.PRODUCTION_ID \r\n" + 
				"where a.active_status=1) a where   \r\n" + 
				"a.wo_chd_id is null and a.order_item_qty_id is not null  and washing_status='P'and a.order_owner_id=coalesce(:buyerId,a.order_owner_id)\r\n" + 
				"and a.item_id=coalesce(:itemId,a.item_id) and a.order_id=coalesce(:inquiryId,a.order_id) "+
				"and a.order_date BETWEEN coalesce(:startDate,a.order_date) AND coalesce(:endDate,a.order_date) and coalesce(a.washed_by,9999999999)=coalesce(:washedById,coalesce(a.washed_by,9999999999))\r\n" + 
				"group by a.production_id, a.production_date, a.production_qty, a.order_item_qty_id, a.item_qty,\r\n" + 
				"a.uom_id, a.item_id,item_name, a.order_id, a.order_date,a.order_owner_id, a.owner_name, \r\n" + 
				"a.uom_name";
		RowMapper<ModelWashingCustom> serviceMapper=new RowMapper<ModelWashingCustom>() {
			public ModelWashingCustom mapRow(ResultSet rs, int rownum) throws SQLException{
			ModelWashingCustom bn=new ModelWashingCustom();
			Double washingQty= rs.getDouble("PRODUCTION_QTY")-rs.getDouble("WASHED_QTY");
					
						   bn.setProductionId(rs.getLong("production_id"));
						   System.out.println(rs.getLong("PRODUCTION_ID"));
						   bn.setProductionDate(rs.getDate("PRODUCTION_DATE"));
						   System.out.println(rs.getDate("PRODUCTION_DATE"));
						   bn.setProductionQty(rs.getDouble("PRODUCTION_QTY"));
						   System.out.println(rs.getDouble("PRODUCTION_QTY"));
						   bn.setInquiryItemQtyId(rs.getLong("ORDER_ITEM_QTY_ID"));
						   System.out.println(rs.getLong("ORDER_ITEM_QTY_ID"));
						   bn.setInquiryItemQty(rs.getDouble("ITEM_QTY"));
						   System.out.println(rs.getDouble("ITEM_QTY"));
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
						   bn.setWashingStatus(rs.getString("WASHING_STATUS"));					   
						  // bn.setqCQtySum(rs.getDouble("QC_QTY"));
						  // bn.setMendingQtySum(rs.getDouble("MENDED_QTY"));
						   bn.setWashedQty(rs.getDouble("WASHED_QTY"));
						   bn.setWashingQty(washingQty);
						   System.out.println("WashingQty: "+washingQty);
						   return bn;
						}

			};
			MapSqlParameterSource parameters = new MapSqlParameterSource();
			

			  parameters.addValue("buyerId", buyerId);
		      System.out.println(" in daoWashingImp, ownerName:" +buyerId);
		      parameters.addValue("washedById", washedById);
		      parameters.addValue("inquiryId", inquiryId);
		      System.out.println("in daoWashingImp, inquiryId: "+inquiryId);
		      parameters.addValue("startDate", startDate);
		      parameters.addValue("endDate", endDate);
		      parameters.addValue("itemId", itemId);

			System.out.println("service mapper ");
			return namedParameterJdbcTemplate.query(qry,parameters,serviceMapper);
	}

	@Override
	public List<ModelWashingCustom> getPendingWashingWODetails(Long buyerId, Long pOId,
			Long washedById, Date startDate, Date endDate, Long itemId) {
		// TODO Auto-generated method stub
		String qry="SELECT a.PO_MST_ID,a.PO_MST_DATE, \r\n" + 
				"a.PO_QTY,a.uom_id,a.uom_name,a.WO_CHD_ID,a.WO_MST_ID,a.WO_DATE,\r\n" + 
				"a.order_owner_id,a.owner_name,a.item_id,a.item_name,\r\n" + 
				"a.WASHING_PLAN_ID,a.WASHING_PLAN_DATE,a.WASHING_PLAN_QTY,\r\n" + 
				"a.WASHING_ID,a.WASHING_DATE,a.WASHED_QTY,a.WASHED_BY\r\n" + 
				"FROM (\r\n" + 
				"SELECT a.PO_MST_ID,a.PO_MST_DATE, \r\n" + 
				"b.PO_QTY,b.uom_id,c.uom_name,f.WO_CHD_ID,h.WO_MST_ID,h.WO_DATE, \r\n" + 
				"j.order_owner_id,k.owner_name,l.item_id,m.item_name,\r\n" + 
				"n.WASHING_PLAN_ID,n.WASHING_PLAN_DATE,n.WASHING_PLAN_QTY,\r\n" + 
				"o.WASHING_ID,o.WASHING_DATE,o.WASHED_QTY,o.WASHED_BY\r\n" + 
				"FROM mlfm_washing_plan n\r\n" + 
				"INNER JOIN mlfm_po_mst a on n.PO_MST_ID=a.PO_MST_ID\r\n" + 
				"INNER JOIN mlfm_po_chd b on a.PO_MST_ID=b.PO_MST_ID\r\n" + 
				"LEFT OUTER JOIN mlfm_uom c on b.UOM_ID=c.uom_id\r\n" + 
				"INNER JOIN mlfm_production_plan_chd e on b.PRODUCTION_PLAN_CHD_ID=e.PRODUCTION_PLAN_CHD_ID \r\n" + 
				"INNER JOIN mlfm_production_plan_mst f on e.PRODUCTION_PLAN_MST_ID=f.PRODUCTION_PLAN_MST_ID\r\n" + 
				"INNER JOIN mlfm_wo_chd g on f.WO_CHD_ID=g.WO_CHD_ID \r\n" + 
				"INNER JOIN mlfm_wo_mst h on g.WO_MST_ID=h.WO_MST_ID\r\n" + 
				"INNER JOIN mlfm_design i on a.DESIGN_ID=i.design_id \r\n" + 
				"INNER JOIN mlfm_order j on i.order_id=j.order_id\r\n" + 
				"INNER JOIN mlfm_order_owner k on j.order_owner_id=k.order_owner_id \r\n" + 
				"INNER JOIN mlfm_order_item l on j.order_id=l.order_id\r\n" + 
				"INNER JOIN mlfm_item m on l.item_id=m.item_id\r\n" + 
				"LEFT OUTER JOIN mlfm_washing o on n.WASHING_PLAN_ID=o.WASHING_PLAN_ID\r\n" + 
				") a where \r\n" + 
				"a.PO_MST_ID=coalesce(:pOId,a.PO_MST_ID) and a.order_owner_id=coalesce(:buyerId,a.order_owner_id)\r\n" + 
				"and a.item_id=coalesce(:itemId,a.item_id) and coalesce(a.WASHED_BY,9999999999)=coalesce(:washedById,coalesce(a.WASHED_BY,9999999999))\r\n" + 
				"and a.WASHING_PLAN_DATE BETWEEN coalesce(:startDate,a.WASHING_PLAN_DATE) AND coalesce(:endDate,a.WASHING_PLAN_DATE)\r\n" + 
				"group by a.PO_MST_ID,a.PO_MST_DATE,\r\n" + 
				"a.PO_QTY,a.uom_id,a.uom_name,a.WO_CHD_ID,a.WO_MST_ID,a.WO_DATE,\r\n" + 
				"a.order_owner_id,a.owner_name,a.item_id,a.item_name,\r\n" + 
				"a.WASHING_PLAN_ID,a.WASHING_PLAN_DATE,a.WASHING_PLAN_QTY,\r\n" + 
				"a.WASHING_ID,a.WASHING_DATE,a.WASHED_QTY,a.WASHED_BY";
		RowMapper<ModelWashingCustom> serviceMapper=new RowMapper<ModelWashingCustom>() {
			public ModelWashingCustom mapRow(ResultSet rs, int rownum) throws SQLException{
			ModelWashingCustom bn=new ModelWashingCustom();			
			
			 bn.setwOMstId(rs.getLong("WO_MST_ID"));
		     bn.setwODate(rs.getDate("WO_DATE"));
		     bn.setpOMstId(rs.getLong("PO_MST_ID"));
		     bn.setpOMstDate(rs.getDate("PO_MST_DATE"));
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
		     bn.setWashingPlanId(rs.getLong("WASHING_PLAN_ID"));
		     bn.setWashingPlanDate(rs.getDate("WASHING_PLAN_DATE"));
		     bn.setWashingPlanQty(rs.getDouble("WASHING_PLAN_QTY"));
			 bn.setWashedQty(rs.getDouble("WASHED_QTY"));

			    
			   return bn;
			}

};
		MapSqlParameterSource parameters = new MapSqlParameterSource();

		parameters.addValue("buyerId", buyerId);
		System.out.println(" in daoMendingImp, ownerName:" +buyerId);
		parameters.addValue("washedById", washedById);
		parameters.addValue("pOId", pOId);
		System.out.println("in daoMendingImp, pOId: "+pOId);
		parameters.addValue("startDate", startDate);
		parameters.addValue("endDate", endDate);
		parameters.addValue("itemId", itemId);

		System.out.println("service mapper ");
		return namedParameterJdbcTemplate.query(qry,parameters,serviceMapper);
	}

	@Override
	public List<ModelWashingCustom> getCompletedWashingOrderDetails(Long buyerId, Long inquiryId,
			Long washedById, Date startDate, Date endDate, Long itemId) {
		// TODO Auto-generated method stub
		String qry="select a.production_id, a.production_date, a.production_qty, a.order_item_qty_id, a.item_qty,\r\n" + 
				"a.uom_id, a.item_id,a.item_name, a.order_id, a.wo_chd_id,a.order_date,a.order_owner_id, a.owner_name,\r\n" + 
				"a.uom_name, a.washing_status, sum(a.washed_qty) washed_qty,a.washing_date \r\n" + 
				"FROM ( \r\n" + 
				"select a.production_id, a.production_date, a.production_qty, d.order_item_qty_id, d.item_qty,  \r\n" + 
				"d.uom_id, e.item_id,f.item_name, g.order_id, g.order_date,h.order_owner_type_id,g.order_owner_id, h.owner_name, \r\n" + 
				"i.uom_name, (select if (exists(select 1 from mlfm_washing w where w.PRODUCTION_ID=a.PRODUCTION_ID), 1,0)) washing_data_exists, \r\n" + 
				"(select  if ( exists (select 1 from mlfm_washing w where w.PRODUCTION_ID=a.production_id \r\n" + 
				"and w.active_status=1 having sum(w.WASHED_QTY) >=a.PRODUCTION_QTY),'C', 'P')) washing_status,\r\n" + 
				"w.WASHED_QTY,w.washed_by,w.washing_date, c.WO_CHD_ID  from mlfm_production a inner join mlfm_production_plan_chd b on a.production_plan_chd_id=b.production_plan_chd_id\r\n" + 
				"inner join mlfm_production_plan_mst c on b.production_plan_mst_id=c.production_plan_mst_id \r\n" + 
				"inner join mlfm_order_item_qty d on c.order_item_qty_id=d.order_item_qty_id inner join mlfm_order_item e on d.order_item_id=e.item_order_id\r\n" + 
				"inner join mlfm_item f on e.item_id=f.item_id inner join mlfm_order g on e.order_id=g.order_id\r\n" + 
				"inner join mlfm_order_owner h on g.order_owner_id=h.order_owner_id inner join mlfm_uom i on d.uom_id=i.uom_id left OUTER join mlfm_washing w on w.PRODUCTION_ID=a.PRODUCTION_ID \r\n" + 
				"where a.active_status=1) a where   \r\n" + 
				"a.wo_chd_id is null and a.order_item_qty_id is not null and washing_status='C'and a.order_owner_id=coalesce(:buyerId,a.order_owner_id)\r\n" + 
				"and a.item_id=coalesce(:itemId,a.item_id) and a.order_id=coalesce(:inquiryId,a.order_id) "+
				"and a.order_date BETWEEN coalesce(:startDate,a.order_date) AND coalesce(:endDate,a.order_date) and coalesce(a.washed_by,9999999999)=coalesce(:washedById,coalesce(a.washed_by,9999999999))\r\n" + 
				"group by a.production_id, a.production_date, a.production_qty, a.order_item_qty_id, a.item_qty,\r\n" + 
				"a.uom_id, a.item_id,item_name, a.order_id, a.order_date,a.order_owner_id, a.owner_name, \r\n" + 
				"a.uom_name";
		RowMapper<ModelWashingCustom> serviceMapper=new RowMapper<ModelWashingCustom>() {
			public ModelWashingCustom mapRow(ResultSet rs, int rownum) throws SQLException{
			ModelWashingCustom bn=new ModelWashingCustom();
			Double washingQty= rs.getDouble("PRODUCTION_QTY")-rs.getDouble("WASHED_QTY");
			
					
						   bn.setProductionId(rs.getLong("production_id"));
						   System.out.println(rs.getLong("PRODUCTION_ID"));
						   bn.setProductionDate(rs.getDate("PRODUCTION_DATE"));
						   System.out.println(rs.getDate("PRODUCTION_DATE"));
						   bn.setProductionQty(rs.getDouble("PRODUCTION_QTY"));
						   System.out.println(rs.getDouble("PRODUCTION_QTY"));
						   bn.setInquiryItemQtyId(rs.getLong("ORDER_ITEM_QTY_ID"));
						   System.out.println(rs.getLong("ORDER_ITEM_QTY_ID"));
						   bn.setInquiryItemQty(rs.getDouble("ITEM_QTY"));
						   System.out.println(rs.getDouble("ITEM_QTY"));
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
						   bn.setWashingStatus(rs.getString("WASHING_STATUS"));					   
						  // bn.setqCQtySum(rs.getDouble("QC_QTY"));
						  // bn.setMendingQtySum(rs.getDouble("MENDED_QTY"));
						   bn.setWashedQty(rs.getDouble("WASHED_QTY"));
						   bn.setWashingQty(washingQty);
						   System.out.println("WashingQty: "+washingQty);
						   return bn;
						}

			};
			MapSqlParameterSource parameters = new MapSqlParameterSource();
			
			  parameters.addValue("buyerId", buyerId);
		      System.out.println(" in daoWashingImp, ownerName:" +buyerId);
		      parameters.addValue("washedById", washedById);
		      parameters.addValue("inquiryId", inquiryId);
		      System.out.println("in daoWashingImp, inquiryId: "+inquiryId);
		      parameters.addValue("startDate", startDate);
		      parameters.addValue("endDate", endDate);
		      parameters.addValue("itemId", itemId);

			System.out.println("service mapper ");
			return namedParameterJdbcTemplate.query(qry,parameters,serviceMapper);
	}

	@Override
	public List<ModelWashingCustom> getCompletedWashingWODetails(Long buyerId, Long pOId,
			Long washedById, Date startDate, Date endDate, Long itemId) {
		// TODO Auto-generated method stub
		String qry="SELECT a.PO_MST_ID,a.PO_MST_DATE, \r\n" + 
				"a.PO_QTY,a.uom_id,a.uom_name,a.WO_CHD_ID,a.WO_MST_ID,a.WO_DATE, \r\n" + 
				"a.order_owner_id,a.owner_name,a.item_id,a.item_name,\r\n" + 
				"a.WASHING_PLAN_ID,a.WASHING_PLAN_DATE,a.WASHING_PLAN_QTY,\r\n" + 
				"a.WASHING_ID,a.WASHING_DATE,a.WASHED_QTY,a.WASHED_BY\r\n" + 
				"FROM (\r\n" + 
				"SELECT a.PO_MST_ID,a.PO_MST_DATE, \r\n" + 
				"b.PO_QTY,b.uom_id,c.uom_name,f.WO_CHD_ID,h.WO_MST_ID,h.WO_DATE, \r\n" + 
				"j.order_owner_id,k.owner_name,l.item_id,m.item_name,\r\n" + 
				"n.WASHING_PLAN_ID,n.WASHING_PLAN_DATE,n.WASHING_PLAN_QTY,\r\n" + 
				"o.WASHING_ID,o.WASHING_DATE,o.WASHED_QTY,o.WASHED_BY\r\n" + 
				"FROM mlfm_washing o\r\n" + 
				"INNER JOIN mlfm_washing_plan n on o.WASHING_PLAN_ID=n.WASHING_PLAN_ID\r\n" + 
				"INNER JOIN mlfm_po_mst a on n.PO_MST_ID=a.PO_MST_ID\r\n" + 
				"INNER JOIN mlfm_po_chd b on a.PO_MST_ID=b.PO_MST_ID\r\n" + 
				"LEFT OUTER JOIN mlfm_uom c on b.UOM_ID=c.uom_id\r\n" + 
				"INNER JOIN mlfm_production_plan_chd e on b.PRODUCTION_PLAN_CHD_ID=e.PRODUCTION_PLAN_CHD_ID \r\n" + 
				"INNER JOIN mlfm_production_plan_mst f on e.PRODUCTION_PLAN_MST_ID=f.PRODUCTION_PLAN_MST_ID\r\n" + 
				"INNER JOIN mlfm_wo_chd g on f.WO_CHD_ID=g.WO_CHD_ID \r\n" + 
				"INNER JOIN mlfm_wo_mst h on g.WO_MST_ID=h.WO_MST_ID\r\n" + 
				"INNER JOIN mlfm_design i on a.DESIGN_ID=i.design_id \r\n" + 
				"INNER JOIN mlfm_order j on i.order_id=j.order_id\r\n" + 
				"INNER JOIN mlfm_order_owner k on j.order_owner_id=k.order_owner_id \r\n" + 
				"INNER JOIN mlfm_order_item l on j.order_id=l.order_id\r\n" + 
				"INNER JOIN mlfm_item m on l.item_id=m.item_id\r\n" + 
				") a where \r\n" + 
				"a.PO_MST_ID=coalesce(:pOId,a.PO_MST_ID) and a.order_owner_id=coalesce(:buyerId,a.order_owner_id)\r\n" + 
				"and a.item_id=coalesce(:itemId,a.item_id) and coalesce(a.WASHED_BY,9999999999)=coalesce(:washedById,coalesce(a.WASHED_BY,9999999999))\r\n" + 
				"and a.WASHING_DATE BETWEEN coalesce(:startDate,a.WASHING_DATE) AND coalesce(:endDate,a.WASHING_DATE)\r\n" + 
				"group by a.PO_MST_ID,a.PO_MST_DATE, \r\n" + 
				"a.PO_QTY,a.uom_id,a.uom_name,a.WO_CHD_ID,a.WO_MST_ID,a.WO_DATE, \r\n" + 
				"a.order_owner_id,a.owner_name,a.item_id,a.item_name,\r\n" + 
				"a.WASHING_PLAN_ID,a.WASHING_PLAN_DATE,a.WASHING_PLAN_QTY,\r\n" + 
				"a.WASHING_ID,a.WASHING_DATE,a.WASHED_QTY,a.WASHED_BY";
		RowMapper<ModelWashingCustom> serviceMapper=new RowMapper<ModelWashingCustom>() {
			public ModelWashingCustom mapRow(ResultSet rs, int rownum) throws SQLException{
			ModelWashingCustom bn=new ModelWashingCustom();
			
			 bn.setwOMstId(rs.getLong("WO_MST_ID"));
		     bn.setwODate(rs.getDate("WO_DATE"));
		     bn.setpOMstId(rs.getLong("PO_MST_ID"));
		     bn.setpOMstDate(rs.getDate("PO_MST_DATE"));
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
		     bn.setWashingPlanId(rs.getLong("WASHING_PLAN_ID"));
		     bn.setWashingPlanDate(rs.getDate("WASHING_PLAN_DATE"));
		     bn.setWashingPlanQty(rs.getDouble("WASHING_PLAN_QTY"));
			 bn.setWashedQty(rs.getDouble("WASHED_QTY"));
			    
			   return bn;
			}

};
		MapSqlParameterSource parameters = new MapSqlParameterSource();

		parameters.addValue("buyerId", buyerId);
		System.out.println(" in daoWashingImp, ownerName:" +buyerId);
		parameters.addValue("washedById", washedById);
		parameters.addValue("pOId", pOId);
		System.out.println("in daoWashingImp, pOId: "+pOId);
		parameters.addValue("startDate", startDate);
		parameters.addValue("endDate", endDate);
		parameters.addValue("itemId", itemId);

		System.out.println("service mapper ");
		return namedParameterJdbcTemplate.query(qry,parameters,serviceMapper);
	}





	

}
