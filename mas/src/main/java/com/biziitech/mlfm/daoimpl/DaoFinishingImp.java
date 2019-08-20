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

import com.biziitech.mlfm.custom.model.ModelFinishingCustom;
import com.biziitech.mlfm.dao.DaoFinishing;
import com.biziitech.mlfm.model.ModelFinishing;
import com.biziitech.mlfm.repository.FinishingRepository;

@Service
public class DaoFinishingImp implements DaoFinishing{

	@Autowired
	private FinishingRepository finishingRepository;
	
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
	public void saveFinishing(ModelFinishing modelFinishing) {
		// TODO Auto-generated method stub
		
		
		finishingRepository.save(modelFinishing);
		
	}

	@Override
	public List<ModelFinishingCustom> getFinishingById(Long finishingPlanId) {
		// TODO Auto-generated method stub
		String qry="select f.finishing_date,f.finished_qty, f.finished_by,f.finishing_id,\r\n" + 
				"f.active_status,f.remarks,u.user_id,u.user_name,f.finishing_plan_id from mlfm_finishing f\r\n" + 
				"left join bg_user u on f.finished_by=u.user_id \r\n" + 
				"where f.finishing_plan_id=coalesce(:finishingPlanId,f.finishing_plan_id)\r\n" + 
				"ORDER BY f.finishing_date DESC";
		
RowMapper<ModelFinishingCustom> serviceMapper=new RowMapper<ModelFinishingCustom>() {
			
			
			public ModelFinishingCustom mapRow(ResultSet rs, int rownum) throws SQLException{
				ModelFinishingCustom bn=new ModelFinishingCustom();
				 bn.setFinishingDate(rs.getDate("FINISHING_DATE"));
				 System.out.println(rs.getDate("FINISHING_DATE"));
				 bn.setFinishedQty(rs.getDouble("FINISHED_QTY"));
				 System.out.println(rs.getDouble("FINISHED_QTY"));
				 bn.setFinishedBy(rs.getLong("FINISHED_BY"));
				 System.out.println(rs.getLong("FINISHED_BY"));
				 bn.setUserId(rs.getLong("USER_ID"));
				 bn.setUserName(rs.getString("USER_NAME"));
				 bn.setActiveStatus(rs.getInt("ACTIVE_STATUS"));
				 bn.setFinishedRemarks(rs.getString("REMARKS"));
				 bn.setFinishingId(rs.getLong("FINISHING_ID"));
				 bn.setFinishingPlanId(rs.getLong("FINISHING_PLAN_ID"));
				 
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
	      System.out.println(" in daoFinishImp, id:" +finishingPlanId);

		System.out.println("service mapper ");
		return namedParameterJdbcTemplate.query(qry,parameters,serviceMapper);
	}

	@Override
	public List<ModelFinishingCustom> getFinishingDoneById(Long finishingId) {
		// TODO Auto-generated method stub
		String qry="select f.finishing_date,f.finished_qty, f.finished_by,f.finishing_id,\r\n" + 
				"f.active_status,f.remarks,u.user_id,u.user_name from mlfm_finishing f\r\n" + 
				"left join bg_user u on f.finished_by=u.user_id \r\n" + 
				"where f.finishing_id=coalesce(:finishingId,f.finishing_id)\r\n" + 
				"ORDER BY f.finishing_date DESC"; 
				
		
		RowMapper<ModelFinishingCustom> serviceMapper=new RowMapper<ModelFinishingCustom>() {
		public ModelFinishingCustom mapRow(ResultSet rs, int rownum) throws SQLException{
			ModelFinishingCustom bn=new ModelFinishingCustom();
			 bn.setFinishingDate(rs.getDate("FINISHING_DATE"));
			 System.out.println(rs.getDate("FINISHING_DATE"));
			 bn.setFinishedQty(rs.getDouble("FINISHED_QTY"));
			 System.out.println(rs.getDouble("FINISHED_QTY"));
			 bn.setFinishedBy(rs.getLong("FINISHED_BY"));
			 System.out.println(rs.getLong("FINISHED_BY"));
			 bn.setUserId(rs.getLong("USER_ID"));
			 bn.setUserName(rs.getString("USER_NAME"));
			 bn.setActiveStatus(rs.getInt("ACTIVE_STATUS"));
			 bn.setFinishedRemarks(rs.getString("REMARKS"));
			 bn.setFinishingId(rs.getLong("FINISHING_ID"));
			 
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
      parameters.addValue("finishingId", finishingId);
      System.out.println(" in daoFinishImp, finishingId:" +finishingId);
      

	System.out.println("service mapper ");
	return namedParameterJdbcTemplate.query(qry,parameters,serviceMapper);
	}

	@Override
	public List<ModelFinishingCustom> getPendingFinishingOrderDetails(Long buyerId, Long pOId,
			Long finishedById, Date startDate, Date endDate, Long itemId) {
		// TODO Auto-generated method stub
		String qry="select a.finishing_PLAN_ID,a.finishing_PLAN_DATE,a.finishing_PLAN_QTY,\r\n" + 
				"a.REMARKS,a.ACTIVE_STATUS,a.UOM_ID,a.uom_name,\r\n" + 
				"a.PO_MST_ID,a.PO_MST_DATE,\r\n" + 
				"a.order_id,a.order_date,a.order_owner_id,a.owner_name,\r\n" + 
				"a.item_id,a.item_name,a.finishing_ID,a.finishing_DATE,a.finished_QTY\r\n" + 
				"FROM (\r\n" + 
				"select a.finishing_PLAN_ID,a.finishing_PLAN_DATE,a.finishing_PLAN_QTY,\r\n" + 
				"a.REMARKS,a.ACTIVE_STATUS,a.UOM_ID,d.uom_name,\r\n" + 
				"a.PO_MST_ID,e.PO_MST_DATE,\r\n" + 
				"f.order_id,g.order_date,g.order_owner_id,h.owner_name,\r\n" + 
				"i.item_id,j.item_name,k.finishing_ID,k.finishing_DATE,k.finished_QTY\r\n" + 
				"FROM mlfm_finishing_plan a\r\n" + 
				"INNER JOIN mlfm_po_mst e on a.PO_MST_ID=e.PO_MST_ID\r\n" + 
				"INNER JOIN mlfm_design f on e.DESIGN_ID=f.design_id\r\n" + 
				"INNER JOIN mlfm_order g on f.order_id=g.order_id\r\n" + 
				"INNER JOIN mlfm_order_owner h on g.order_owner_id=h.order_owner_id\r\n" + 
				"INNER JOIN mlfm_order_item i on g.order_id=i.order_id\r\n" + 
				"INNER JOIN mlfm_item j on i.item_id=j.item_id\r\n" + 
				"LEFT OUTER JOIN mlfm_finishing k on a.finishing_PLAN_ID=k.finishing_PLAN_ID\r\n" + 
				"LEFT OUTER JOIN mlfm_uom d on a.UOM_ID=d.uom_id\r\n" + 
				") a where\r\n" + 
				"a.PO_MST_ID=coalesce(:pOId,a.PO_MST_ID) and a.order_owner_id=coalesce(:buyerId,a.order_owner_id)\r\n" + 
				"and a.finishing_PLAN_DATE BETWEEN coalesce(:startDate,a.finishing_PLAN_DATE) AND coalesce(:endDate,a.finishing_PLAN_DATE)\r\n" + 
				"group by a.finishing_PLAN_ID,a.finishing_PLAN_DATE,a.finishing_PLAN_QTY,\r\n" + 
				"a.REMARKS,a.ACTIVE_STATUS,a.UOM_ID,a.uom_name,\r\n" + 
				"a.PO_MST_ID,a.PO_MST_DATE,\r\n" + 
				"a.order_id,a.order_date,a.order_owner_id,a.owner_name,\r\n" + 
				"a.item_id,a.item_name,a.finishing_ID,a.finishing_DATE,a.finished_QTY";
		RowMapper<ModelFinishingCustom> serviceMapper=new RowMapper<ModelFinishingCustom>() {
			public ModelFinishingCustom mapRow(ResultSet rs, int rownum) throws SQLException{
				ModelFinishingCustom bn=new ModelFinishingCustom();
				Double finishingPlanQtyUnDone= rs.getDouble("FINISHING_PLAN_QTY")-rs.getDouble("FINISHED_QTY");
					 

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
							   bn.setFinishingQtyUnDone(finishingPlanQtyUnDone);
							   bn.setFinishingPlanId(rs.getLong("FINISHING_PLAN_ID"));
							   bn.setFinishingPlanDate(rs.getDate("FINISHING_PLAN_DATE"));
							   bn.setFinishingPlanQty(rs.getDouble("FINISHING_PLAN_QTY"));
							   bn.setpOId(rs.getLong("PO_MST_ID"));
							   bn.setpODate(rs.getDate("PO_MST_DATE"));
							   bn.setFinishingId(rs.getLong("finishing_ID"));
							   bn.setFinishingDate(rs.getDate("finishing_DATE"));
							   bn.setFinishedQty(rs.getDouble("finished_QTY"));
					
					 return bn;
					
				}
			};

		      MapSqlParameterSource parameters = new MapSqlParameterSource();  
			      parameters.addValue("buyerId", buyerId);
			      System.out.println(" in daoFinishingImp, ownerName:" +buyerId);
			      parameters.addValue("finishedById", finishedById);
			      parameters.addValue("pOId", pOId);
			      System.out.println("in daoFinishingImp, pOId: "+pOId);
			      parameters.addValue("startDate", startDate);
			      parameters.addValue("endDate", endDate);
			      parameters.addValue("itemId", itemId);
		      

			System.out.println("service mapper ");
			return namedParameterJdbcTemplate.query(qry,parameters,serviceMapper);
	}

	@Override
	public List<ModelFinishingCustom> getPendingFinishingWODetails(Long buyerId, Long pOId,
			Long finishedById, Date startDate, Date endDate, Long itemId) {
		// TODO Auto-generated method stub
		String qry="select a.finishing_PLAN_ID,a.finishing_PLAN_DATE,a.finishing_PLAN_QTY,\r\n" + 
				"a.REMARKS,a.ACTIVE_STATUS,a.UOM_ID,a.uom_name,\r\n" + 
				"a.PO_MST_ID,a.PO_MST_DATE,\r\n" + 
				"a.order_id,a.order_date,a.order_owner_id,a.owner_name,\r\n" + 
				"a.item_id,a.item_name,a.WO_MST_ID,a.WO_DATE,\r\n" + 
				"a.finishing_ID,a.finishing_DATE,a.finished_QTY\r\n" + 
				"FROM (\r\n" + 
				"select a.finishing_PLAN_ID,a.finishing_PLAN_DATE,a.finishing_PLAN_QTY,\r\n" + 
				"a.REMARKS,a.ACTIVE_STATUS,a.UOM_ID,d.uom_name,\r\n" + 
				"a.PO_MST_ID,e.PO_MST_DATE,\r\n" + 
				"f.order_id,g.order_date,g.order_owner_id,h.owner_name,\r\n" + 
				"i.item_id,j.item_name,n.WO_MST_ID,o.WO_DATE,\r\n" + 
				"p.finishing_ID,p.finishing_DATE,p.finished_QTY\r\n" + 
				"FROM mlfm_finishing_plan a\r\n" + 
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
				"LEFT OUTER JOIN mlfm_finishing p on a.finishing_PLAN_ID=p.finishing_PLAN_ID\r\n" + 
				"LEFT OUTER JOIN mlfm_uom d on a.UOM_ID=d.uom_id\r\n" + 
				") a where\r\n" + 
				"a.PO_MST_ID=coalesce(:pOId,a.PO_MST_ID) and a.order_owner_id=coalesce(:buyerId,a.order_owner_id)\r\n" + 
				"and a.finishing_PLAN_DATE BETWEEN coalesce(:startDate,a.finishing_PLAN_DATE) AND coalesce(:endDate,a.finishing_PLAN_DATE)\r\n" + 
				"group by a.finishing_PLAN_ID,a.finishing_PLAN_DATE,a.finishing_PLAN_QTY,\r\n" + 
				"a.REMARKS,a.ACTIVE_STATUS,a.UOM_ID,a.uom_name,\r\n" + 
				"a.PO_MST_ID,a.PO_MST_DATE,\r\n" + 
				"a.order_id,a.order_date,a.order_owner_id,a.owner_name,\r\n" + 
				"a.item_id,a.item_name,a.WO_MST_ID,a.WO_DATE,\r\n" + 
				"a.finishing_ID,a.finishing_DATE,a.finished_QTY";
		RowMapper<ModelFinishingCustom> serviceMapper=new RowMapper<ModelFinishingCustom>() {
			public ModelFinishingCustom mapRow(ResultSet rs, int rownum) throws SQLException{
				ModelFinishingCustom bn=new ModelFinishingCustom();
				Double finishingPlanQtyUnDone= rs.getDouble("FINISHING_PLAN_QTY")-rs.getDouble("FINISHED_QTY");
					 

							   bn.setItemId(rs.getLong("ITEM_ID"));
							   System.out.println(rs.getLong("ITEM_ID"));
							   bn.setItemName(rs.getString("ITEM_NAME"));
						       System.out.println(rs.getString("ITEM_NAME"));
							   bn.setwOMstId(rs.getLong("WO_MST_ID"));
							   bn.setwODate(rs.getDate("WO_DATE"));
							   bn.setBuyerId(rs.getLong("ORDER_OWNER_ID"));
							   System.out.println(rs.getLong("ORDER_OWNER_ID"));
							   bn.setBuyerName(rs.getString("OWNER_NAME"));
							   System.out.println(rs.getString("OWNER_NAME"));
							   bn.setUomId(rs.getLong("UOM_ID"));
							   System.out.println(rs.getLong("UOM_ID"));
							   bn.setUomName(rs.getString("UOM_NAME"));
							   System.out.println(rs.getString("UOM_NAME"));
							   bn.setFinishingQtyUnDone(finishingPlanQtyUnDone);
							   bn.setFinishingPlanId(rs.getLong("FINISHING_PLAN_ID"));
							   bn.setFinishingPlanDate(rs.getDate("FINISHING_PLAN_DATE"));
							   bn.setFinishingPlanQty(rs.getDouble("FINISHING_PLAN_QTY"));
							   bn.setpOId(rs.getLong("PO_MST_ID"));
							   bn.setpODate(rs.getDate("PO_MST_DATE"));
							   bn.setFinishingId(rs.getLong("finishing_ID"));
							   bn.setFinishingDate(rs.getDate("finishing_DATE"));
							   bn.setFinishedQty(rs.getDouble("finished_QTY"));
					
					 return bn;
					
				}
			};

		      MapSqlParameterSource parameters = new MapSqlParameterSource();  
			      parameters.addValue("buyerId", buyerId);
			      System.out.println(" in daoFinishingImp, ownerName:" +buyerId);
			      parameters.addValue("finishedById", finishedById);
			      parameters.addValue("pOId", pOId);
			      System.out.println("in daoFinishingImp, pOId: "+pOId);
			      parameters.addValue("startDate", startDate);
			      parameters.addValue("endDate", endDate);
			      parameters.addValue("itemId", itemId);
		      

			System.out.println("service mapper ");
			return namedParameterJdbcTemplate.query(qry,parameters,serviceMapper);
	}

	@Override
	public List<ModelFinishingCustom> getCompletedFinishingOrderDetails(Long buyerId, Long pOId,
			Long finishedById, Date startDate, Date endDate, Long itemId) {
		// TODO Auto-generated method stub
		String qry="select a.finishing_PLAN_ID,a.finishing_PLAN_DATE,a.finishing_PLAN_QTY,\r\n" + 
				"a.REMARKS,a.ACTIVE_STATUS,a.UOM_ID,a.uom_name,\r\n" + 
				"a.PO_MST_ID,a.PO_MST_DATE,\r\n" + 
				"a.order_id,a.order_date,a.order_owner_id,a.owner_name,\r\n" + 
				"a.item_id,a.item_name,a.finishing_ID,a.finishing_DATE,a.finished_QTY\r\n" + 
				"FROM (\r\n" + 
				"select a.finishing_PLAN_ID,a.finishing_PLAN_DATE,a.finishing_PLAN_QTY,\r\n" + 
				"a.REMARKS,a.ACTIVE_STATUS,a.UOM_ID,d.uom_name,\r\n" + 
				"a.PO_MST_ID,e.PO_MST_DATE,\r\n" + 
				"f.order_id,g.order_date,g.order_owner_id,h.owner_name,\r\n" + 
				"i.item_id,j.item_name,k.finishing_ID,k.finishing_DATE,k.finished_QTY\r\n" + 
				"FROM mlfm_finishing k\r\n" + 
				"INNER JOIN mlfm_finishing_plan a on k.FINISHING_PLAN_ID=a.FINISHING_PLAN_ID\r\n" + 
				"INNER JOIN mlfm_po_mst e on a.PO_MST_ID=e.PO_MST_ID\r\n" + 
				"INNER JOIN mlfm_design f on e.DESIGN_ID=f.design_id\r\n" + 
				"INNER JOIN mlfm_order g on f.order_id=g.order_id\r\n" + 
				"INNER JOIN mlfm_order_owner h on g.order_owner_id=h.order_owner_id\r\n" + 
				"INNER JOIN mlfm_order_item i on g.order_id=i.order_id\r\n" + 
				"INNER JOIN mlfm_item j on i.item_id=j.item_id\r\n" + 
				"LEFT OUTER JOIN mlfm_uom d on a.UOM_ID=d.uom_id\r\n" + 
				") a where\r\n" + 
				"a.PO_MST_ID=coalesce(:pOId,a.PO_MST_ID) and a.order_owner_id=coalesce(:buyerId,a.order_owner_id)\r\n" + 
				"and a.finishing_DATE BETWEEN coalesce(:startDate,a.finishing_DATE) AND coalesce(:endDate,a.finishing_DATE)\r\n" + 
				"group by a.finishing_PLAN_ID,a.finishing_PLAN_DATE,a.finishing_PLAN_QTY,\r\n" + 
				"a.REMARKS,a.ACTIVE_STATUS,a.UOM_ID,a.uom_name,\r\n" + 
				"a.PO_MST_ID,a.PO_MST_DATE,\r\n" + 
				"a.order_id,a.order_date,a.order_owner_id,a.owner_name,\r\n" + 
				"a.item_id,a.item_name,a.finishing_ID,a.finishing_DATE,a.finished_QTY";
		RowMapper<ModelFinishingCustom> serviceMapper=new RowMapper<ModelFinishingCustom>() {
			public ModelFinishingCustom mapRow(ResultSet rs, int rownum) throws SQLException{
				ModelFinishingCustom bn=new ModelFinishingCustom();
				Double finishingPlanQtyUnDone= rs.getDouble("FINISHING_PLAN_QTY")-rs.getDouble("FINISHED_QTY");
					 
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
							   bn.setFinishingQtyUnDone(finishingPlanQtyUnDone);
							   bn.setFinishingPlanId(rs.getLong("FINISHING_PLAN_ID"));
							   bn.setFinishingPlanDate(rs.getDate("FINISHING_PLAN_DATE"));
							   bn.setFinishingPlanQty(rs.getDouble("FINISHING_PLAN_QTY"));
							   bn.setpOId(rs.getLong("PO_MST_ID"));
							   bn.setpODate(rs.getDate("PO_MST_DATE"));
							   bn.setFinishingId(rs.getLong("finishing_ID"));
							   bn.setFinishingDate(rs.getDate("finishing_DATE"));
							   bn.setFinishedQty(rs.getDouble("finished_QTY"));

					
					 return bn;
					
				}
			};

		      MapSqlParameterSource parameters = new MapSqlParameterSource();  
			      parameters.addValue("buyerId", buyerId);
			      System.out.println(" in daoFinishingImp, ownerName:" +buyerId);
			      parameters.addValue("finishedById", finishedById);
			      parameters.addValue("pOId", pOId);
			      System.out.println("in daoFinishingImp, pOId: "+pOId);
			      parameters.addValue("startDate", startDate);
			      parameters.addValue("endDate", endDate);
			      parameters.addValue("itemId", itemId);
		      

			System.out.println("service mapper ");
			return namedParameterJdbcTemplate.query(qry,parameters,serviceMapper);
	}

	@Override
	public List<ModelFinishingCustom> getCompletedFinishingWODetails(Long buyerId, Long pOId,
			Long finishedById, Date startDate, Date endDate, Long itemId) {
		// TODO Auto-generated method stub
		String qry="select a.finishing_PLAN_ID,a.finishing_PLAN_DATE,a.finishing_PLAN_QTY,\r\n" + 
				"a.REMARKS,a.ACTIVE_STATUS,a.UOM_ID,a.uom_name,\r\n" + 
				"a.PO_MST_ID,a.PO_MST_DATE,\r\n" + 
				"a.order_id,a.order_date,a.order_owner_id,a.owner_name,\r\n" + 
				"a.item_id,a.item_name,a.WO_MST_ID,a.WO_DATE,\r\n" + 
				"a.finishing_ID,a.finishing_DATE,a.finished_QTY\r\n" + 
				"FROM (\r\n" + 
				"select a.finishing_PLAN_ID,a.finishing_PLAN_DATE,a.finishing_PLAN_QTY,\r\n" + 
				"a.REMARKS,a.ACTIVE_STATUS,a.UOM_ID,d.uom_name,\r\n" + 
				"a.PO_MST_ID,e.PO_MST_DATE,\r\n" + 
				"f.order_id,g.order_date,g.order_owner_id,h.owner_name,\r\n" + 
				"i.item_id,j.item_name,n.WO_MST_ID,o.WO_DATE,\r\n" + 
				"p.finishing_ID,p.finishing_DATE,p.finished_QTY\r\n" + 
				"FROM mlfm_finishing p\r\n" + 
				"INNER JOIN mlfm_finishing_plan a on p.FINISHING_PLAN_ID=a.FINISHING_PLAN_ID\r\n" + 
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
				"LEFT OUTER JOIN mlfm_uom d on a.UOM_ID=d.uom_id\r\n" + 
				") a where\r\n" + 
				"a.PO_MST_ID=coalesce(:pOId,a.PO_MST_ID) and a.order_owner_id=coalesce(:buyerId,a.order_owner_id)\r\n" + 
				"and a.finishing_DATE BETWEEN coalesce(:startDate,a.finishing_DATE) AND coalesce(:endDate,a.finishing_DATE)\r\n" + 
				"group by a.finishing_PLAN_ID,a.finishing_PLAN_DATE,a.finishing_PLAN_QTY,\r\n" + 
				"a.REMARKS,a.ACTIVE_STATUS,a.UOM_ID,a.uom_name,\r\n" + 
				"a.PO_MST_ID,a.PO_MST_DATE,\r\n" + 
				"a.order_id,a.order_date,a.order_owner_id,a.owner_name,\r\n" + 
				"a.item_id,a.item_name,a.WO_MST_ID,a.WO_DATE,\r\n" + 
				"a.finishing_ID,a.finishing_DATE,a.finished_QTY";
		RowMapper<ModelFinishingCustom> serviceMapper=new RowMapper<ModelFinishingCustom>() {
			public ModelFinishingCustom mapRow(ResultSet rs, int rownum) throws SQLException{
				ModelFinishingCustom bn=new ModelFinishingCustom();
					 Double finishingPlanQtyUnDone= rs.getDouble("FINISHING_PLAN_QTY")-rs.getDouble("FINISHED_QTY");
					 

							   bn.setItemId(rs.getLong("ITEM_ID"));
							   System.out.println(rs.getLong("ITEM_ID"));
							   bn.setItemName(rs.getString("ITEM_NAME"));
						       System.out.println(rs.getString("ITEM_NAME"));
						       bn.setwOMstId(rs.getLong("WO_MST_ID"));
							   bn.setwODate(rs.getDate("WO_DATE"));
							   bn.setBuyerId(rs.getLong("ORDER_OWNER_ID"));
							   System.out.println(rs.getLong("ORDER_OWNER_ID"));
							   bn.setBuyerName(rs.getString("OWNER_NAME"));
							   System.out.println(rs.getString("OWNER_NAME"));
							   bn.setUomId(rs.getLong("UOM_ID"));
							   System.out.println(rs.getLong("UOM_ID"));
							   bn.setUomName(rs.getString("UOM_NAME"));
							   System.out.println(rs.getString("UOM_NAME"));
							   bn.setFinishingQtyUnDone(finishingPlanQtyUnDone);
							   bn.setFinishingPlanId(rs.getLong("FINISHING_PLAN_ID"));
							   bn.setFinishingPlanDate(rs.getDate("FINISHING_PLAN_DATE"));
							   bn.setFinishingPlanQty(rs.getDouble("FINISHING_PLAN_QTY"));
							   bn.setpOId(rs.getLong("PO_MST_ID"));
							   bn.setpODate(rs.getDate("PO_MST_DATE"));
							   bn.setFinishingId(rs.getLong("finishing_ID"));
							   bn.setFinishingDate(rs.getDate("finishing_DATE"));
							   bn.setFinishedQty(rs.getDouble("finished_QTY"));

					
					 return bn;
					
				}
			};

		      MapSqlParameterSource parameters = new MapSqlParameterSource();  
			      parameters.addValue("buyerId", buyerId);
			      System.out.println(" in daoFinishingImp, ownerName:" +buyerId);
			      parameters.addValue("finishedById", finishedById);
			      parameters.addValue("pOId", pOId);
			      System.out.println("in daoFinishingImp, pOId: "+pOId);
			      parameters.addValue("startDate", startDate);
			      parameters.addValue("endDate", endDate);
			      parameters.addValue("itemId", itemId);
		      

			System.out.println("service mapper ");
			return namedParameterJdbcTemplate.query(qry,parameters,serviceMapper);
	}



}
