package com.biziitech.mlfm.daoimpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.biziitech.mlfm.custom.model.ModelPIInquiryList;
import com.biziitech.mlfm.custom.model.ModelProductionCustom;
import com.biziitech.mlfm.custom.model.ModelWOChdListCustom;
import com.biziitech.mlfm.custom.model.ModelWOInquiryData;
import com.biziitech.mlfm.dao.DaoWorkOrder;
import com.biziitech.mlfm.model.ModelDesign;
import com.biziitech.mlfm.model.ModelOrderItemQtySpec;
import com.biziitech.mlfm.model.ModelWOMst;

import com.biziitech.mlfm.repository.WorkOrderRepository;


@Service
public class DaoWorkOrderImp implements DaoWorkOrder {
	
	@Autowired
	private WorkOrderRepository workOrderRepository;
	
	@Autowired
    private DataSource dataSource;
	
    //private JdbcTemplate jdbcTemplate;
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        //this.jdbcTemplate = new JdbcTemplate(dataSource);
        //this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcTemplate=jdbcTemplate;
          }
	
	@Autowired
	private NamedParameterJdbcTemplate namedJdbcTemplate;
    public void setNamedDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.namedJdbcTemplate=namedJdbcTemplate;
          }
    
    @Override
	public void saveWOMst(ModelWOMst modelWOMst) {
	//	if(modelWOMst.isActive())
	//		modelWOMst.setActiveStatus(1);
	//	else
	//		modelWOMst.setActiveStatus(0);
		
		//workOrderRepository.save(modelWOMst);
		workOrderRepository.saveAndFlush(modelWOMst);
		//modelWOMst=workOrderRepository.getOne(modelWOMst.getwOMstId());
		
	}
	
    /*
	 * created by:sas
	 * Date:01/17/2019
	 * 
	 * Purpose: this method execute when checked Work Order option
	 * 
	 * 
	 */
	@Override
    public List<ModelWOInquiryData>getInquiryDataListDoneWO(Long user,Long userCluster,Long orderOwnerId,Long ultimateOwnerId,
    		Date orderStratDate,Date orderEndDate)
	{
		String  qry="select  c.order_item_qty_id order_item_qty_id,a.order_date order_date,d.item_name item_name,c.item_qty item_qty,e.uom_id uom_id,e.uom_name uom_name,b.item_order_id item_order_id, "+
				 " b.item_id item_id,a.entered_by entered_by, h.user_cluster_id user_cluster_id,f.owner_name owner_name,a.order_id order_id,f.order_owner_id order_owner_id,k.order_owner_type_id order_owner_type_id from" +
				 " mlfm_order a inner join mlfm_order_item b on a.order_id=b.order_id"+
				 " inner join mlfm_order_item_qty c on b.item_order_id=c.order_item_id inner join mlfm_item d on b.item_id=d.item_id"+
				 " left outer join mlfm_uom e on c.uom_id=e.uom_id inner join mlfm_order_owner f on a.order_owner_id=f.order_owner_id"+
				 " inner join bg_user g on a.entered_by=g.user_id left outer join mlfm_user_cluster h on g.user_id=h.user_id "+
				 " inner join mlfm_order_owner_type k on k.order_owner_type_id=f.order_owner_type_id "+
				 " and a.active_status=1 and b.active_status=1 and c.active_status=1 "+
				 " and exists (SELECT 1 FROM mlfm_wo_mst p inner join mlfm_wo_chd q on p.wo_mst_id=q.wo_mst_id where p.active_status=1"+
				 " and q.active_status=1 and q.order_item_qty_id=c.order_item_qty_id)" +
				 " where a.entered_by =coalesce(:user,a.entered_by) and"+
				 " h.user_cluster_id=coalesce(:user_cluster,h.user_cluster_id) and" +
				 " f.order_owner_id=COALESCE(:orderOwnerId,f.order_owner_id) and" +
				 " f.order_owner_id=COALESCE(:ultimateOwnerId,f.order_owner_id) and" +
				 " a.order_date between coalesce(:order_strat_date,a.order_date) and" +
				 " coalesce(:order_end_date,a.order_date) ";
		
		/*
		 *  sql editor format
		 *  ====================
		 *select  c.order_item_qty_id order_item_qty_id,a.order_date order_date,d.item_name item_name,c.item_qty item_qty,
e.uom_id uom_id,e.uom_name uom_name,b.item_order_id item_order_id,b.item_id item_id,a.entered_by entered_by,
h.user_cluster_id user_cluster_id,f.owner_name owner_name,a.order_id order_id,
f.order_owner_id order_owner_id,k.order_owner_type_id order_owner_type_id 
from mlfm_order a 
inner join mlfm_order_item b on a.order_id=b.order_id
inner join mlfm_order_item_qty c on b.item_order_id=c.order_item_id 
inner join mlfm_item d on b.item_id=d.item_id
left outer join mlfm_uom e on c.uom_id=e.uom_id 
inner join mlfm_order_owner f on a.order_owner_id=f.order_owner_id
inner join bg_user g on a.entered_by=g.user_id 
left outer join mlfm_user_cluster h on g.user_id=h.user_id 
inner join mlfm_order_owner_type k on k.order_owner_type_id=f.order_owner_type_id 
and a.active_status=1 and b.active_status=1 and c.active_status=1 
and exists (SELECT 1 FROM mlfm_wo_mst p inner join mlfm_wo_chd q on p.wo_mst_id=q.wo_mst_id where p.active_status=1
and q.active_status=1 and q.order_item_qty_id=c.order_item_qty_id)
where a.entered_by =coalesce(@user,a.entered_by)
and h.user_cluster_id=coalesce(@user_cluster,h.user_cluster_id) 
and f.order_owner_id=COALESCE(@orderOwnerId,f.order_owner_id)
and f.order_owner_id=COALESCE(@ultimateOwnerId,f.order_owner_id)
and a.order_date between coalesce(@order_strat_date,a.order_date) and coalesce(@order_end_date,a.order_date)
		 * 
		 
		 */
		
		RowMapper<ModelWOInquiryData> serviceMapper=new RowMapper<ModelWOInquiryData>() {
				@Override
				public ModelWOInquiryData mapRow(ResultSet rs, int rownum) throws SQLException{
					ModelWOInquiryData bn=new ModelWOInquiryData();
			
				bn.setOrderItemQuantityId(rs.getLong("order_item_qty_id"));
				bn.setOrderDate(rs.getDate("order_date"));
				bn.setItemName(rs.getString("item_name"));
				bn.setItemQty(rs.getDouble("item_qty"));
				bn.setUomId(rs.getLong("uom_id"));
				bn.setUomName(rs.getString("uom_name"));
				bn.setItemOrderId(rs.getLong("item_order_id"));
				bn.setItemId(rs.getLong("item_id"));
				bn.setEnteredBy(rs.getLong("entered_by"));
				bn.setUserClusterId(rs.getLong("user_cluster_id"));
				bn.setOrderOwnerName(rs.getString("owner_name"));
				bn.setOrderId(rs.getLong("order_id"));
				bn.setUomName(rs.getString("uom_name"));
				bn.setOwnerId(rs.getLong("order_owner_id"));
				bn.setOrderOwnerTypeId(rs.getLong("order_owner_type_id"));
				
				return bn;
				
				}	
			};
			
			
			MapSqlParameterSource parameters = new MapSqlParameterSource();
		      parameters.addValue("user", user);
		      parameters.addValue("user_cluster", userCluster);
		      parameters.addValue("orderOwnerId", orderOwnerId);
		      parameters.addValue("ultimateOwnerId", ultimateOwnerId);
		      parameters.addValue("order_strat_date", orderStratDate);
		      parameters.addValue("order_end_date", orderEndDate);
		      
		      System.out.println("query " + qry);
			
			return  jdbcTemplate.query(qry,parameters,serviceMapper);
      }
	
	/*
	 * created by:sas
	 * Date:01/17/2019
	 * 
	 * Purpose: this method execute when Unchecked Work Order option
	 * 
	 * 
	 */
	@Override
	public List<ModelWOInquiryData> getInquiryDataListNotDoneWO(Long user,Long userCluster,Long orderOwnerId,Long ultimateOwnerId,
    		Date orderStratDate,Date orderEndDate) 
	{
		String  qry="select  c.order_item_qty_id order_item_qty_id,a.order_date order_date,d.item_name item_name,c.item_qty item_qty,e.uom_id uom_id,e.uom_name uom_name,b.item_order_id item_order_id, "+
				 " b.item_id item_id,a.entered_by entered_by, h.user_cluster_id user_cluster_id,f.owner_name owner_name,a.order_id order_id,f.order_owner_id order_owner_id,k.order_owner_type_id order_owner_type_id from" +
				 " mlfm_order a inner join mlfm_order_item b on a.order_id=b.order_id"+
				 " inner join mlfm_order_item_qty c on b.item_order_id=c.order_item_id inner join mlfm_item d on b.item_id=d.item_id"+
				 " left outer join mlfm_uom e on c.uom_id=e.uom_id inner join mlfm_order_owner f on a.order_owner_id=f.order_owner_id"+
				 " inner join bg_user g on a.entered_by=g.user_id left outer join mlfm_user_cluster h on g.user_id=h.user_id "+
				 " inner join mlfm_order_owner_type k on k.order_owner_type_id=f.order_owner_type_id "+
				 " and a.active_status=1 and b.active_status=1 and c.active_status=1 "+
				 " and not exists (SELECT 1 FROM mlfm_wo_mst p inner join mlfm_wo_chd q on p.wo_mst_id=q.wo_mst_id where p.active_status=1"+
				 " and q.active_status=1 and q.order_item_qty_id=c.order_item_qty_id)" +
				 " where a.entered_by =coalesce(:user,a.entered_by) and"+
				 " h.user_cluster_id=coalesce(:user_cluster,h.user_cluster_id) and" +
				 //" k.order_owner_type_id=coalesce(:order_owner_type_id,k.order_owner_type_id) and"+
				 " f.order_owner_id=COALESCE(:orderOwnerId,f.order_owner_id) and" +
				 " f.order_owner_id=COALESCE(:ultimateOwnerId,f.order_owner_id) and" +
				 " a.order_date between coalesce(:order_strat_date,a.order_date) and" +
				 " coalesce(:order_end_date,a.order_date) "
				 ;
		
		/*
		 * sql editor format
		 * ================
		 * 
		 * select  c.order_item_qty_id order_item_qty_id,a.order_date order_date,d.item_name item_name,c.item_qty item_qty,e.uom_id uom_id,e.uom_name uom_name,b.item_order_id item_order_id, 
b.item_id item_id,a.entered_by entered_by, h.user_cluster_id user_cluster_id,f.owner_name owner_name,a.order_id order_id,f.order_owner_id order_owner_id,k.order_owner_type_id order_owner_type_id 
from mlfm_order a 
inner join mlfm_order_item b on a.order_id=b.order_id
inner join mlfm_order_item_qty c on b.item_order_id=c.order_item_id 
inner join mlfm_item d on b.item_id=d.item_id
left outer join mlfm_uom e on c.uom_id=e.uom_id 
inner join mlfm_order_owner f on a.order_owner_id=f.order_owner_id
inner join bg_user g on a.entered_by=g.user_id left outer join mlfm_user_cluster h on g.user_id=h.user_id 
inner join mlfm_order_owner_type k on k.order_owner_type_id=f.order_owner_type_id 
and a.active_status=1 and b.active_status=1 and c.active_status=1 
and not exists (SELECT 1 FROM mlfm_wo_mst p inner join mlfm_wo_chd q on p.wo_mst_id=q.wo_mst_id
where p.active_status=1 and q.active_status=1 and q.order_item_qty_id=c.order_item_qty_id)
where a.entered_by =coalesce(@user,a.entered_by) 
and h.user_cluster_id=coalesce(@user_cluster,h.user_cluster_id) 
and k.order_owner_type_id=coalesce(@order_owner_type_id,k.order_owner_type_id) 
and f.order_owner_id=COALESCE(@orderOwnerId,f.order_owner_id)
and f.order_owner_id=COALESCE(@ultimateOwnerId,f.order_owner_id) 
and a.order_date between coalesce(@order_strat_date,a.order_date) and coalesce(@order_end_date,a.order_date)
		 * 
		 * 
		 * 
		 * 
		 */
		
		RowMapper<ModelWOInquiryData> serviceMapper=new RowMapper<ModelWOInquiryData>() {
			@Override
			public ModelWOInquiryData mapRow(ResultSet rs, int rownum) throws SQLException{
				ModelWOInquiryData bn=new ModelWOInquiryData();
		
			bn.setOrderItemQuantityId(rs.getLong("order_item_qty_id"));
			bn.setOrderDate(rs.getDate("order_date"));
			bn.setItemName(rs.getString("item_name"));
			bn.setItemQty(rs.getDouble("item_qty"));
			bn.setUomId(rs.getLong("uom_id"));
			bn.setUomName(rs.getString("uom_name"));
			bn.setItemOrderId(rs.getLong("item_order_id"));
			bn.setItemId(rs.getLong("item_id"));
			bn.setEnteredBy(rs.getLong("entered_by"));
			bn.setUserClusterId(rs.getLong("user_cluster_id"));
			bn.setOrderOwnerName(rs.getString("owner_name"));
			bn.setOrderId(rs.getLong("order_id"));
			bn.setUomName(rs.getString("uom_name"));
			bn.setOwnerId(rs.getLong("order_owner_id"));
			bn.setOrderOwnerTypeId(rs.getLong("order_owner_type_id"));
			
			return bn;
			
			}	
		};
		
		
		MapSqlParameterSource parameters = new MapSqlParameterSource();
	      parameters.addValue("user", user);
	      parameters.addValue("user_cluster", userCluster);
	      parameters.addValue("orderOwnerId", orderOwnerId);
	      parameters.addValue("ultimateOwnerId", ultimateOwnerId);
	      parameters.addValue("order_strat_date", orderStratDate);
	      parameters.addValue("order_end_date", orderEndDate);
	      
	      /*
	       *  // parameters.addValue("order_owner_type_id", orderOwnerTypeId);
	      parameters.addValue("orderStratDate", orderStratDate);
	      parameters.addValue("orderEndDate", orderEndDate);
	      parameters.addValue("activeWorkOrder", activeWorkOrder);
	      parameters.addValue("activeStatus", activeStatus);
	      */
	      System.out.println("query " + qry);
		
		return  jdbcTemplate.query(qry,parameters,serviceMapper);
		
		// TODO Auto-generated method stub
		
	}
	

	@Override
	public List<ModelPIInquiryList> getWoDataList(Long ownerType,String owner,String ultimateOwner, Long user, Long cluster, Date startdate, Date enddate) {
	
		//String wqry="select a.wo_mst_id, a.WO_DATE, e.owner_name from mlfm_wo_mst a left outer join mlfm_wo_chd b on a.wo_mst_id=b.wo_mst_id INNER JOIN mlfm_order_owner e on a.ORDER_OWNER_ID=e.order_owner_id inner join mlfm_order_owner_type f ON e.order_owner_type_id=f.order_owner_type_id where not exists (select * from mlfm_pi_chd d where b.wo_chd_id=d.wo_chd_id and d.active_status=1 ) and a.active_status=1 and b.active_status=1 and e.order_owner_type_id=coalesce(:ownerType,e.order_owner_type_id) and UPPER(trim(e.owner_name)) LIKE CONCAT('%',upper(TRIM(:owner)),'%') and UPPER(trim(e.owner_name)) LIKE CONCAT('%',upper(TRIM(:ultimateOwner)),'%') and a.wo_Date BETWEEN :startdate AND :enddate";
		
		String wqry="select a.wo_mst_id, a.WO_DATE, e.owner_name, g.order_id, e.order_owner_id "
				+ "from mlfm_wo_mst a left outer join mlfm_wo_chd b on a.wo_mst_id=b.wo_mst_id"
				+ " inner join mlfm_order_item_qty c on c.ORDER_ITEM_QTY_ID=b.ORDER_ITEM_QTY_ID"
				+ " inner join mlfm_order_item g on g.item_order_id=c.order_item_id "
				+ "INNER JOIN mlfm_order_owner e on a.ORDER_OWNER_ID=e.order_owner_id "
				+ "inner join mlfm_order_owner_type f ON e.order_owner_type_id=f.order_owner_type_id "
				+ "inner join mlfm_order h on h.order_id=g.order_id "
				+ "where not exists (select 1 from mlfm_pi_chd d where b.wo_chd_id=d.wo_chd_id and d.active_status=1 ) "
				+ "and a.active_status=1 and b.active_status=1 and e.order_owner_type_id=coalesce(:ownerType,e.order_owner_type_id) "
				+ "and UPPER(trim(e.owner_name)) LIKE CONCAT('%',upper(TRIM(:owner)),'%') and "
				+ "UPPER(trim(e.owner_name)) LIKE CONCAT('%',upper(TRIM(:ultimateOwner)),'%') and "
				+ "h.entered_by =coalesce(:user,h.entered_by) and h.cluster_id=coalesce(:cluster,h.cluster_id) and "
				+ "a.wo_Date BETWEEN coalesce(:startdate, a.WO_DATE) AND coalesce(:enddate,a.WO_DATE)";
		RowMapper<ModelPIInquiryList> serviceMapper=new RowMapper<ModelPIInquiryList>() {
			public ModelPIInquiryList mapRow(ResultSet rs, int rownum) throws SQLException{
				
				ModelPIInquiryList bn=new ModelPIInquiryList();
		
			    bn.setWoMstId(rs.getLong("wo_mst_id")); 
			    bn.setWoDate(rs.getDate("wo_date"));
			    bn.setOwnerName(rs.getString("owner_name"));
			    bn.setOrderId(rs.getLong("order_id"));
			    bn.setOrderOwnerId(rs.getLong("order_owner_id"));
		 
			    return bn;
			 }
			
		};
		
		  MapSqlParameterSource parameters = new MapSqlParameterSource();
	      parameters.addValue("ownerType", ownerType);
	      
	     // System.out.println("ownerType"+ ownerType);
	      parameters.addValue("owner", owner);
	     // System.out.println("owner"+ owner);
	      
	      parameters.addValue("ultimateOwner", ultimateOwner);
	      parameters.addValue("user", user);
	      parameters.addValue("cluster", cluster);
	      parameters.addValue("startdate", startdate);
	      parameters.addValue("enddate", enddate);
	      
	      return jdbcTemplate.query(wqry, parameters,serviceMapper);
	}
	
	@Override
	public List<ModelPIInquiryList> getWoDataListWithPI(Long ownerType,String owner,String ultimateOwner, Long user, Long cluster, Date startdate, Date enddate) {
	
		//String wqry="select a.wo_mst_id, a.WO_DATE, e.owner_name from mlfm_wo_mst a left outer join mlfm_wo_chd b on a.wo_mst_id=b.wo_mst_id INNER JOIN mlfm_order_owner e on a.ORDER_OWNER_ID=e.order_owner_id inner join mlfm_order_owner_type f ON e.order_owner_type_id=f.order_owner_type_id where not exists (select * from mlfm_pi_chd d where b.wo_chd_id=d.wo_chd_id and d.active_status=1 ) and a.active_status=1 and b.active_status=1 and e.order_owner_type_id=coalesce(:ownerType,e.order_owner_type_id) and UPPER(trim(e.owner_name)) LIKE CONCAT('%',upper(TRIM(:owner)),'%') and UPPER(trim(e.owner_name)) LIKE CONCAT('%',upper(TRIM(:ultimateOwner)),'%') and a.wo_Date BETWEEN :startdate AND :enddate";
		
		String eqry="select a.wo_mst_id, a.WO_DATE, e.owner_name, g.order_id, e.order_owner_id from mlfm_wo_mst a "
				+ "left outer join mlfm_wo_chd b on a.wo_mst_id=b.wo_mst_id "
				+ "inner join mlfm_order_item_qty c on c.ORDER_ITEM_QTY_ID=b.ORDER_ITEM_QTY_ID "
				+ "inner join mlfm_order_item g on g.item_order_id=c.order_item_id "
				+ "INNER JOIN mlfm_order_owner e on a.ORDER_OWNER_ID=e.order_owner_id "
				+ "inner join mlfm_order_owner_type f ON e.order_owner_type_id=f.order_owner_type_id "
				+ "inner join mlfm_order h on h.order_id=g.order_id "
				+ "where exists (select 1 from mlfm_pi_chd d where b.wo_chd_id=d.wo_chd_id and d.active_status=1 ) "
				+ "and a.active_status=1 and b.active_status=1 and e.order_owner_type_id=coalesce(:ownerType,e.order_owner_type_id) "
				+ "and UPPER(trim(e.owner_name)) LIKE CONCAT('%',upper(TRIM(:owner)),'%') "
				+ "and UPPER(trim(e.owner_name)) LIKE CONCAT('%',upper(TRIM(:ultimateOwner)),'%') "
				+ "and h.entered_by =coalesce(:user,h.entered_by) and h.cluster_id=coalesce(:cluster,h.cluster_id) "
				+ "and a.wo_Date BETWEEN coalesce(:startdate, a.WO_DATE) AND coalesce(:enddate,a.WO_DATE)";
		RowMapper<ModelPIInquiryList> serviceMapper=new RowMapper<ModelPIInquiryList>() {
			public ModelPIInquiryList mapRow(ResultSet rs, int rownum) throws SQLException{
				ModelPIInquiryList bn=new ModelPIInquiryList();
		
			    bn.setWoMstId(rs.getLong("wo_mst_id")); 
			    bn.setWoDate(rs.getDate("wo_date"));
			    bn.setOwnerName(rs.getString("owner_name"));
			    bn.setOrderId(rs.getLong("order_id"));
			    bn.setOrderOwnerId(rs.getLong("order_owner_id"));
			    
			    return bn;
			
			}
			
		};
		
		  MapSqlParameterSource parameters = new MapSqlParameterSource();
	      parameters.addValue("ownerType", ownerType);
	      
	     // System.out.println("ownerType"+ ownerType);
	      parameters.addValue("owner", owner);
	     // System.out.println("owner"+ owner);
	      
	      parameters.addValue("ultimateOwner", ultimateOwner);
	      parameters.addValue("user", user);
	      parameters.addValue("cluster", cluster);
	      parameters.addValue("startdate", startdate);
	      parameters.addValue("enddate", enddate);
	      
	    return jdbcTemplate.query(eqry, parameters,serviceMapper);
	    
	}
	
	//creator : sas , date : 01/12/2019
//	@Override
//	public List<ModelPIInquiryList>getWoMstList(Long id){
//		
//		String qry ="select a.WO_MST_ID,a.WO_DATE,a.USER_WO_NO,e.owner_name from mlfm_wo_mst a inner join mlfm_order_owner e on a.ORDER_OWNER_ID=e.order_owner_id where a.active_status=1 and e.active_status=1 and a.WO_MST_ID=id ";
//		
//		RowMapper<ModelPIInquiryList> serviceMapper=new RowMapper<ModelPIInquiryList>() {
//			public ModelPIInquiryList mapRow(ResultSet rs, int rownum) throws SQLException{
//				
//				ModelPIInquiryList bn=new ModelPIInquiryList();
//		        bn.setWoMstId(rs.getLong("wo_mst_id")); 
//			    bn.setWoDate(rs.getDate("wo_date"));
//			    bn.setWoDate(rs.getDate("USER_WO_NO"));
//			    bn.setOwnerName(rs.getString("owner_name"));
//			    
//			    return bn;
//			}
//		};
//		
//		MapSqlParameterSource parameters = new MapSqlParameterSource();
//	      parameters.addValue("id", id);
//	      
//	      return jdbcTemplate.query(qry, parameters,serviceMapper);
//		
//	}
	
	
	@Override
	public Optional<ModelWOMst> findWOMstById(Long id) {
		
		//try {
		Optional<ModelWOMst> modelWOMstOpt=workOrderRepository.findById(id);
		//System.out.println("get findById object " + workOrderRepository.findById(id).get().getModelOrderOwner().getContactPersonName());
		//System.out.println(" owner name from dao :" + modelWOMstOpt.get().getModelOrderOwner().getContactPersonName());
			return   workOrderRepository.findById(id);
			
			
		//}
		//catch(Exception e) {
			
			//e.printStackTrace();
			//return null;
		//}
		
	}

	@Override
	public List<ModelWOChdListCustom> getWOChdList(Long inquiryItemQtyId,Long wOMstId) {
		
		// last working qry
//		String  qry=" select  c.order_item_qty_id order_item_qty_id,a.order_date order_date,d.item_name item_name,c.item_qty item_qty," +
//				    " e.uom_id uom_id,e.uom_name uom_name,b.item_order_id item_order_id,b.item_id item_id,a.entered_by entered_by," +
//				    " h.user_cluster_id user_cluster_id,f.owner_name owner_name,"+
//				    " a.order_id order_id,f.order_owner_id order_owner_id,k.order_owner_type_id order_owner_type_id,c.remarks from" +
//		            " mlfm_order a inner join mlfm_order_item b on a.order_id=b.order_id" +
//		            " inner join mlfm_order_item_qty c on b.item_order_id=c.order_item_id inner join mlfm_item d on b.item_id=d.item_id" +
//		            " left outer join mlfm_uom e on c.uom_id=e.uom_id inner join mlfm_order_owner f on a.order_owner_id=f.order_owner_id" +
//		            " inner join bg_user g on a.entered_by=g.user_id left outer join mlfm_user_cluster h on g.user_id=h.user_id" +
//		            " inner join mlfm_order_owner_type k on k.order_owner_type_id=f.order_owner_type_id " +
//		            " and a.active_status=1 and b.active_status=1 and c.active_status=1 " +
//		            " and not exists (SELECT 1 FROM mlfm_wo_mst p inner join mlfm_wo_chd q on p.wo_mst_id=q.wo_mst_id where p.active_status=1"+
//		            " and q.active_status=1 and q.order_item_qty_id=c.order_item_qty_id)"+
//		            " where c.order_item_qty_id=coalesce(:inquiryItemQtyId,c.order_item_qty_id)";
//		
		
		String  qry="select  c.order_item_qty_id order_item_qty_id,c.remarks remarks,y.wo_mst_id wo_mst_id,a.order_date order_date,d.item_name item_name,c.item_qty item_qty,e.uom_id uom_id,e.uom_name uom_name,b.item_order_id item_order_id, "+
				 " b.item_id item_id,a.entered_by entered_by, h.user_cluster_id user_cluster_id,f.owner_name owner_name,a.order_id order_id,f.order_owner_id order_owner_id,k.order_owner_type_id order_owner_type_id from" +
				 " mlfm_order a inner join mlfm_order_item b on a.order_id=b.order_id"+
				 " inner join mlfm_order_item_qty c on b.item_order_id=c.order_item_id inner join mlfm_item d on b.item_id=d.item_id"+
				 " left outer join mlfm_uom e on c.uom_id=e.uom_id inner join mlfm_order_owner f on a.order_owner_id=f.order_owner_id"+
				 " inner join bg_user g on a.entered_by=g.user_id left outer join mlfm_user_cluster h on g.user_id=h.user_id "+
				 " inner join mlfm_order_owner_type k on k.order_owner_type_id=f.order_owner_type_id "+
				 "INNER JOIN mlfm_wo_mst y on y.ORDER_OWNER_ID=a.order_owner_id"+
				 " and a.active_status=1 and b.active_status=1 and c.active_status=1 "+
				 " and not exists (SELECT 1 FROM mlfm_wo_mst p inner join mlfm_wo_chd q on p.wo_mst_id=q.wo_mst_id where p.active_status=1"+
				 " and q.active_status=1 and q.order_item_qty_id=c.order_item_qty_id)" +
				 " where c.order_item_qty_id=coalesce(:inquiryItemQtyId,c.order_item_qty_id) " +
				 " and y.wo_mst_id=:wOMstId";
		
		/*
		 * 
		 * select
c.order_item_qty_id order_item_qty_id,c.remarks remarks,y.wo_mst_id wo_mst_id,a.order_date order_date,d.item_name item_name,c.item_qty item_qty,e.uom_id uom_id,e.uom_name uom_name,b.item_order_id item_order_id, 
b.item_id item_id,a.entered_by entered_by, h.user_cluster_id user_cluster_id,f.owner_name owner_name,
a.order_id order_id,f.order_owner_id order_owner_id,k.order_owner_type_id order_owner_type_id 
from mlfm_order a 
inner join mlfm_order_item b on a.order_id=b.order_id 
inner join mlfm_order_item_qty c on b.item_order_id=c.order_item_id 
inner join mlfm_item d on b.item_id=d.item_id
left outer join mlfm_uom e on c.uom_id=e.uom_id 
inner join mlfm_order_owner f on a.order_owner_id=f.order_owner_id 
inner join bg_user g on a.entered_by=g.user_id 
left outer join mlfm_user_cluster h on g.user_id=h.user_id 
inner join mlfm_order_owner_type k on k.order_owner_type_id=f.order_owner_type_id 
INNER JOIN mlfm_wo_mst y on y.ORDER_OWNER_ID=a.order_owner_id
and a.active_status=1 and b.active_status=1 and c.active_status=1 
and not exists (SELECT 1 FROM mlfm_wo_mst p inner join mlfm_wo_chd q on p.wo_mst_id=q.wo_mst_id 
where p.active_status=1 and q.active_status=1 and q.order_item_qty_id=c.order_item_qty_id)
where c.order_item_qty_id=coalesce(@inquiryItemQtyId,c.order_item_qty_id) 
and y.wo_mst_id=COALESCE(@wOMstId,y.WO_MST_ID)
		 * 
		 * 
		 */
		
		
		
		
		
		/*
		
		String  qry=" select  c.order_item_qty_id order_item_qty_id,a.order_date order_date,d.item_name item_name,c.item_qty item_qty," +
			    " e.uom_id uom_id,e.uom_name uom_name,b.item_order_id item_order_id,b.item_id item_id,a.entered_by entered_by," +
			    " h.user_cluster_id user_cluster_id,f.owner_name owner_name,y.wo_mst_id wo_mst_id,"+
			    " a.order_id order_id,f.order_owner_id order_owner_id,k.order_owner_type_id order_owner_type_id,c.remarks from" +
	            " mlfm_order a inner join mlfm_order_item b on a.order_id=b.order_id" +
	            " inner join mlfm_order_item_qty c on b.item_order_id=c.order_item_id inner join mlfm_item d on b.item_id=d.item_id" +
	            " left outer join mlfm_uom e on c.uom_id=e.uom_id inner join mlfm_order_owner f on a.order_owner_id=f.order_owner_id" +
	            " inner join bg_user g on a.entered_by=g.user_id left outer join mlfm_user_cluster h on g.user_id=h.user_id" +
	            " inner join mlfm_order_owner_type k on k.order_owner_type_id=f.order_owner_type_id " +
	            "INNER JOIN mlfm_wo_mst y on y.ORDER_OWNER_ID=a.order_owner_id"+
	            " and a.active_status=1 and b.active_status=1 and c.active_status=1 " +
	            " and not exists (SELECT 1 FROM mlfm_wo_mst p inner join mlfm_wo_chd q on p.wo_mst_id=q.wo_mst_id where p.active_status=1"+
	            " and q.active_status=1 and q.order_item_qty_id=c.order_item_qty_id)"+
	            " where c.order_item_qty_id=coalesce(:inquiryItemQtyId,c.order_item_qty_id)";
		*/
		RowMapper<ModelWOChdListCustom> serviceMapper=new RowMapper<ModelWOChdListCustom>() {
				@Override
				public ModelWOChdListCustom mapRow(ResultSet rs, int rownum) throws SQLException{
					ModelWOChdListCustom bn=new ModelWOChdListCustom();
					
					bn.setOrderItemQuantityId(rs.getLong("order_item_qty_id"));
					bn.setOrderDate(rs.getDate("order_date"));
					bn.setItemName(rs.getString("item_name"));
					bn.setItemQty(rs.getDouble("item_qty"));
					bn.setUomId(rs.getLong("uom_id"));
					bn.setUomName(rs.getString("uom_name"));
					bn.setItemOrderId(rs.getLong("item_order_id"));
					bn.setItemId(rs.getLong("item_id"));
					bn.setEnteredBy(rs.getLong("entered_by"));
					bn.setUserClusterId(rs.getLong("user_cluster_id"));
					bn.setOrderOwnerName(rs.getString("owner_name"));
					bn.setOrderId(rs.getLong("order_id"));
					bn.setUomName(rs.getString("uom_name"));
					bn.setOwnerId(rs.getLong("order_owner_id"));
					bn.setOrderOwnerTypeId(rs.getLong("order_owner_type_id"));
					bn.setRemarks(rs.getString("remarks"));
					bn.setwOMstId(rs.getLong("wo_mst_id"));
					
					return bn;
				
				}	
			};
			
			MapSqlParameterSource parameters = new MapSqlParameterSource();
		      parameters.addValue("inquiryItemQtyId", inquiryItemQtyId);
		      parameters.addValue("wOMstId", wOMstId);
		      
		      System.out.println("query  " + qry);
			
			return  jdbcTemplate.query(qry,parameters,serviceMapper);
            // TODO Auto-generated method stub
	
	}

	@Override
	public List<ModelWOMst> getInUsedWOMstList() {
		List<ModelWOMst> modelWOMst=workOrderRepository.findInUsedWOMstList();
		
		// TODO Auto-generated method stub
		return modelWOMst;
	}

	@Override
	public List<ModelWOInquiryData> getWOMstListFindbyWOMstId(Long wOMstId) {
		
		
		String  qry="SELECT wom.WO_MST_ID,wom.WO_DATE,wom.USER_WO_NO,wom.ORDER_OWNER_ID,wom.REMARKS,oo.owner_name,wom.ACTIVE_STATUS\r\n" + 
				",oot.order_owner_type_id\r\n" + 
				"FROM mlfm_wo_mst wom \r\n" + 
				"INNER join mlfm_order_owner oo on oo.order_owner_id=wom.ORDER_OWNER_ID\r\n" + 
				"INNER JOIN mlfm_order_owner_type oot ON oot.order_owner_type_id=oo.order_owner_type_id\r\n" + 
				"WHERE wom.WO_MST_ID=COALESCE(:wOMstId,wom.WO_MST_ID)";
		
		/*
		 *SELECT wom.WO_MST_ID,wom.WO_DATE,wom.USER_WO_NO,wom.ORDER_OWNER_ID,wom.REMARKS,oo.owner_name,wom.ACTIVE_STATUS
,oot.order_owner_type_id
FROM mlfm_wo_mst wom 
INNER join mlfm_order_owner oo on oo.order_owner_id=wom.ORDER_OWNER_ID
INNER JOIN mlfm_order_owner_type oot ON oot.order_owner_type_id=oo.order_owner_type_id
WHERE wom.WO_MST_ID=COALESCE(@wOMstId,wom.WO_MST_ID)
		 * 
		 * */
	
	RowMapper<ModelWOInquiryData> serviceMapper=new RowMapper<ModelWOInquiryData>() {
		@Override
		public ModelWOInquiryData mapRow(ResultSet rs, int rownum) throws SQLException{
			ModelWOInquiryData bn=new ModelWOInquiryData();
			
			
			bn.setwOMstId(rs.getLong("WO_MST_ID"));
			bn.setwODate(rs.getDate("WO_DATE"));
			bn.setUserWONo(rs.getString("USER_WO_NO"));
			bn.setOwnerId(rs.getLong("ORDER_OWNER_ID"));
			bn.setOrderOwnerName(rs.getString("owner_name"));
			bn.setRemarks(rs.getString("REMARKS"));
			bn.setActiveStatus(rs.getInt("ACTIVE_STATUS"));
			bn.setOrderOwnerTypeId(rs.getLong("order_owner_type_id"));
			
			
		
//			bn.setMachineSheduleId(rs.getLong("MACHINE_SCHEDULE_ID"));
//			bn.setMachineName(rs.getString("MACHINE_NAME"));
//			bn.setMachineSheduleName(rs.getString("machine_schedule_name"));
//			bn.setMachineShiftName(rs.getString("machine_shift_name"));
//			bn.setScheduleDate(rs.getDate("schedule_date"));

	    return bn;
		
		}	
	};
	
	MapSqlParameterSource parameters = new MapSqlParameterSource();
	
	  parameters.addValue("wOMstId", wOMstId);
	  
	 System.out.println("query : " + qry);
      
	// TODO Auto-generated method stub
	return  namedJdbcTemplate.query(qry,parameters,serviceMapper);
		
	
	}

	@Override
	public List<ModelWOInquiryData> getWOMstListDoneData(Long orderItemQtyId) {
		
		

		String  qry="SELECT oiq.order_item_qty_id,o.order_id,wm.WO_MST_ID,wm.USER_WO_NO,wm.WO_DATE,\r\n" + 
				"oo.owner_name,wm.REMARKS,wm.ACTIVE_STATUS\r\n" + 
				"FROM mlfm_order o\r\n" + 
				"INNER join mlfm_order_item oi on oi.order_id=o.order_id\r\n" + 
				"INNER JOIN mlfm_order_item_qty oiq on oiq.order_item_id=oi.item_order_id\r\n" + 
				"INNER JOIN mlfm_wo_chd wc on wc.ORDER_ITEM_QTY_ID=oiq.order_item_qty_id\r\n" + 
				"INNER JOIN mlfm_wo_mst wm on wm.WO_MST_ID=wc.WO_MST_ID\r\n" + 
				"INNER join mlfm_order_owner oo on oo.order_owner_id=o.order_owner_id\r\n" + 
				"where oiq.order_item_qty_id=COALESCE(:orderItemQtyId,oiq.order_item_qty_id)";
		
		/*
		 *SELECT oiq.order_item_qty_id,o.order_id,wm.WO_MST_ID,wm.USER_WO_NO,wm.WO_DATE,
oo.owner_name,wm.REMARKS,wm.ACTIVE_STATUS
FROM mlfm_order o
INNER join mlfm_order_item oi on oi.order_id=o.order_id
INNER JOIN mlfm_order_item_qty oiq on oiq.order_item_id=oi.item_order_id
INNER JOIN mlfm_wo_chd wc on wc.ORDER_ITEM_QTY_ID=oiq.order_item_qty_id
INNER JOIN mlfm_wo_mst wm on wm.WO_MST_ID=wc.WO_MST_ID
INNER join mlfm_order_owner oo on oo.order_owner_id=o.order_owner_id
where oiq.order_item_qty_id=COALESCE(@orderItemQtyId,oiq.order_item_qty_id)
		 * 
		 * */
	
	RowMapper<ModelWOInquiryData> serviceMapper=new RowMapper<ModelWOInquiryData>() {
		@Override
		public ModelWOInquiryData mapRow(ResultSet rs, int rownum) throws SQLException{
			ModelWOInquiryData bn=new ModelWOInquiryData();
			
			
			bn.setwOMstId(rs.getLong("WO_MST_ID"));
			bn.setwODate(rs.getDate("WO_DATE"));
			bn.setUserWONo(rs.getString("USER_WO_NO"));
			//bn.setOwnerId(rs.getLong("ORDER_OWNER_ID"));
			bn.setOrderOwnerName(rs.getString("owner_name"));
			bn.setRemarks(rs.getString("REMARKS"));
			bn.setActiveStatus(rs.getInt("ACTIVE_STATUS"));
			
		

	    return bn;
		
		}	
	};
	
	MapSqlParameterSource parameters = new MapSqlParameterSource();
	
	  parameters.addValue("orderItemQtyId", orderItemQtyId);
	  
	 System.out.println("query : " + qry);
      
	// TODO Auto-generated method stub
	return  namedJdbcTemplate.query(qry,parameters,serviceMapper);
	
	}

	@Override
	public List<ModelWOInquiryData> getWOChdListDoneData(Long wOMstId) {
		
		
		String  qry="SELECT wc.WO_CHD_ID,wc.ORDER_ITEM_QTY_ID,oiq.item_Qty,i.item_name,wc.ITEM_RATE,wc.ITEM_QTY,wc.REMARKS,\r\n" + 
				"wc.ACTIVE_STATUS,wc.CURRENCY_ID,wc.COMMISSION_RATE,wc.COMMISSION_PER_UOM,wc.COMMISSION_TOTAL,u.uom_name,uom.uom_name,bc.CURRENCY_NAME,wc.WO_MST_ID\r\n" + 
				"FROM mlfm_wo_chd wc\r\n" + 
				"INNER JOIN mlfm_order_item_qty oiq on oiq.order_item_qty_id=wc.ORDER_ITEM_QTY_ID\r\n" + 
				"INNER JOIN mlfm_order_item oi on oi.item_order_id=oiq.order_item_id\r\n" + 
				"INNER JOIN mlfm_item i ON i.item_id=oi.item_id\r\n" + 
				"LEFT OUTER JOIN mlfm_uom u on u.uom_id=i.uom_id\r\n" + 
				"LEFT OUTER JOIN mlfm_uom uom on uom.uom_id = wc.COMMISSION_PER_UOM\r\n" + 
				"LEFT OUTER JOIN bg_currency bc ON bc.CURRENCY_ID=wc.CURRENCY_ID\r\n" + 
				"WHERE wc.WO_MST_ID=COALESCE(:wOMstId,wc.WO_MST_ID)";
		
		/*
		 *
		 *SELECT wc.WO_CHD_ID,wc.ORDER_ITEM_QTY_ID,oiq.item_Qty,i.item_name,wc.ITEM_RATE,wc.ITEM_QTY,wc.REMARKS,
wc.ACTIVE_STATUS,wc.CURRENCY_ID,wc.COMMISSION_RATE,wc.COMMISSION_PER_UOM,wc.COMMISSION_TOTAL,u.uom_name,uom.uom_name,bc.CURRENCY_NAME,wc.WO_MST_ID
FROM mlfm_wo_chd wc
INNER JOIN mlfm_order_item_qty oiq on oiq.order_item_qty_id=wc.ORDER_ITEM_QTY_ID
INNER JOIN mlfm_order_item oi on oi.item_order_id=oiq.order_item_id
INNER JOIN mlfm_item i ON i.item_id=oi.item_id
LEFT OUTER JOIN mlfm_uom u on u.uom_id=i.uom_id
LEFT OUTER JOIN mlfm_uom uom on uom.uom_id = wc.COMMISSION_PER_UOM
LEFT OUTER JOIN bg_currency bc ON bc.CURRENCY_ID=wc.CURRENCY_ID
WHERE wc.WO_MST_ID=COALESCE(@wOMstId,wc.WO_MST_ID)
		 * 
		 * */
	
	RowMapper<ModelWOInquiryData> serviceMapper=new RowMapper<ModelWOInquiryData>() {
		@Override
		public ModelWOInquiryData mapRow(ResultSet rs, int rownum) throws SQLException{
			ModelWOInquiryData bn=new ModelWOInquiryData();
			
			
			bn.setwOMstId(rs.getLong("WO_MST_ID"));
			bn.setwOChdId(rs.getLong("WO_CHD_ID"));
			bn.setOrderItemQuantityId(rs.getLong("ORDER_ITEM_QTY_ID"));
			bn.setItemQty(rs.getDouble("item_Qty"));
			bn.setItemName(rs.getString("item_name"));
			bn.setItemRate(rs.getDouble("ITEM_RATE"));
			bn.setwOChdItemQty(rs.getDouble("ITEM_QTY"));
			bn.setActiveStatus(rs.getInt("ACTIVE_STATUS"));
			bn.setRemarks(rs.getString("REMARKS"));
			bn.setCurrencyId(rs.getLong("CURRENCY_ID"));
			bn.setCommissionRate(rs.getDouble("COMMISSION_RATE"));
			bn.setCommissionTotal(rs.getDouble("COMMISSION_TOTAL"));
			bn.setCommissionPerUOM(rs.getLong("COMMISSION_PER_UOM"));
			bn.setUomName(rs.getString("uom_name"));
			bn.setCommissionPerUOMName(rs.getString("uom_name"));
			bn.setCurrencyName(rs.getString("CURRENCY_NAME"));
			
			 System.out.println("database woMstId : " + rs.getLong("WO_MST_ID"));
			
		 return bn;
		
		}	
	};
	
	MapSqlParameterSource parameters = new MapSqlParameterSource();
	
	  parameters.addValue("wOMstId", wOMstId);
	  
	 System.out.println("query : " + qry);
      
	// TODO Auto-generated method stub
	return  namedJdbcTemplate.query(qry,parameters,serviceMapper);
	
		
		
		
		// TODO Auto-generated method stub
	//	return null;
	}
	
	
	@Override
	public Optional<ModelWOMst> findWOMst(Long wOMstId) {
		
		
		
		// TODO Auto-generated method stub
		return workOrderRepository.findById(wOMstId);
	}


	
	
}
