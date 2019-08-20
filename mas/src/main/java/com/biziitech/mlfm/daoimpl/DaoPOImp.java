package com.biziitech.mlfm.daoimpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.biziitech.mlfm.custom.model.ModelInquiryList;
import com.biziitech.mlfm.custom.model.ModelPOCustom;
import com.biziitech.mlfm.dao.DaoPO;

import com.biziitech.mlfm.model.ModelPO;
import com.biziitech.mlfm.model.ModelPOChd;
import com.biziitech.mlfm.model.ModelPOMst;
import com.biziitech.mlfm.model.ModelProduction;
import com.biziitech.mlfm.model.ModelProductionPlanMst;
import com.biziitech.mlfm.repository.POChdRepository;
import com.biziitech.mlfm.repository.POMstRepository;
import com.biziitech.mlfm.repository.PORepository;
import com.biziitech.mlfm.repository.ProductionRepository;

@Service
public class DaoPOImp implements DaoPO {
	
	@Autowired
	private PORepository pORepository;
	
	@Autowired
	private POMstRepository pOMstRepository;
	
	@Autowired
	private POChdRepository pOChdRepository;
	
	
	@Autowired
    private DataSource dataSource;
	
	 private JdbcTemplate jdbcTemplate;
     public void setDataSource(DataSource dataSource) {
         this.dataSource = dataSource;
         this.jdbcTemplate = new JdbcTemplate(dataSource);
       }
     
     @Autowired
 	private NamedParameterJdbcTemplate namedJdbcTemplate;
     public void setNamedDataSource(DataSource dataSource) {
         this.dataSource = dataSource;
         this.namedJdbcTemplate=namedJdbcTemplate;
           }

	@Override
	public List<ModelPOCustom> getWorkOrderDataUnchecked(Long wOMstId,Long orderOwnerTypeId,String ownerName,Long itemId,Date pPStratDate,Date pPEndDate) {
		
		
		System.out.println("daoPO "+ "buyer name : ");
	
		String  qry_WO="SELECT a.PRODUCTION_PLAN_CHD_ID,a.PRODUCTION_PLAN_DATE,a.PRODUCTION_PLAN_QTY,a.MACHINE_SHIFT_ID, \r\n" + 
				"				c.machine_type_id ,d.design_id,wm.WO_MST_ID,oiq.order_item_qty_id,i.item_name,oo.owner_name,c.MACHINE_NAME,mt.MACHINE_TYPE_NAME,\r\n" + 
				"               s.SHIFT_NAME\r\n" + 
				"			    FROM mlfm_production_plan_chd a \r\n" + 
				"		        INNER JOIN mlfm_machine_shift b on a.MACHINE_SHIFT_ID=b.MACHINE_SHIFT_ID\r\n" + 
				"				INNER JOIN mlfm_machine c on b.MACHINE_ID=c.MACHINE_ID \r\n" + 
				"				INNER JOIN mlfm_machine_type mt on mt.MACHINE_TYPE_ID=c.MACHINE_TYPE_ID \r\n" + 
				"				INNER JOIN mlfm_design d ON c.machine_type_id=mt.machine_type_id\r\n" + 
				"				INNER JOIN mlfm_production_plan_mst ppm ON ppm.production_plan_mst_id=a.production_plan_mst_id\r\n" + 
				"			   INNER JOIN mlfm_wo_chd wc ON wc.wo_chd_id=ppm.wo_chd_id\r\n" + 
				"			   INNER JOIN mlfm_wo_mst wm ON wm.wo_mst_id=wc.wo_mst_id \r\n" + 
				"			   INNER join mlfm_order_item_qty oiq ON ppm.ORDER_ITEM_QTY_ID=oiq.order_item_qty_id and oiq.design_id=d.design_id\r\n" + 
				"			   INNER join mlfm_order_item oi on oiq.order_item_id=oi.item_order_id\r\n" + 
				"			   INNER join mlfm_item i on oi.item_id= i.item_id\r\n" + 
				"			   INNER join mlfm_order_owner oo on wm.ORDER_OWNER_ID=oo.order_owner_id\r\n" + 
				"              INNER JOIN mlfm_shift s ON s.SHIFT_ID=b.SHIFT_ID "+
				"              inner join mlfm_order_owner_type oot on oot.order_owner_type_id=oo.order_owner_type_id " +
				"              WHERE UPPER(trim(oo.owner_name)) LIKE CONCAT('%',upper(TRIM(:ownerName)),'%') AND \r\n" + 
				"              a.PRODUCTION_PLAN_DATE between coalesce(:pPStratDate,a.PRODUCTION_PLAN_DATE) and coalesce(:pPEndDate,a.PRODUCTION_PLAN_DATE) AND \r\n" + 
				"              i.item_id=coalesce(:itemId,i.item_id) AND \r\n" + 
				"              oot.order_owner_type_id=coalesce(:orderOwnerTypeId,oot.order_owner_type_id) AND " + 
				"              wm.WO_MST_ID=coalesce(:wOMstId,wm.WO_MST_ID)";
		
		
		
		
		/*
		 * sql query
		 * 
		 * SELECT a.PRODUCTION_PLAN_CHD_ID,a.PRODUCTION_PLAN_DATE,a.PRODUCTION_PLAN_QTY,a.MACHINE_SHIFT_ID, 
								 c.machine_type_id ,d.design_id,wm.WO_MST_ID,oiq.order_item_qty_id,i.item_name,oo.owner_name,c.MACHINE_NAME,mt.MACHINE_TYPE_NAME,
                                 s.SHIFT_NAME
					  FROM mlfm_production_plan_chd a 
		 INNER JOIN mlfm_machine_shift b on a.MACHINE_SHIFT_ID=b.MACHINE_SHIFT_ID
					 INNER JOIN mlfm_machine c on b.MACHINE_ID=c.MACHINE_ID 
						 INNER JOIN mlfm_machine_type mt on mt.MACHINE_TYPE_ID=c.MACHINE_TYPE_ID 
						 INNER JOIN mlfm_design d ON c.machine_type_id=mt.machine_type_id
							 INNER JOIN mlfm_production_plan_mst ppm ON ppm.production_plan_mst_id=a.production_plan_mst_id
							 INNER JOIN mlfm_wo_chd wc ON wc.wo_chd_id=ppm.wo_chd_id
					 INNER JOIN mlfm_wo_mst wm ON wm.wo_mst_id=wc.wo_mst_id 
			            INNER join mlfm_order_item_qty oiq ON ppm.ORDER_ITEM_QTY_ID=oiq.order_item_qty_id
			       INNER join mlfm_order_item oi on oiq.order_item_id=oi.item_order_id
			          INNER join mlfm_item i on oi.item_id= i.item_id
				           INNER join mlfm_order_owner oo on wm.ORDER_OWNER_ID=oo.order_owner_id
                           INNER JOIN mlfm_shift s ON s.SHIFT_ID=b.SHIFT_ID
		 * 
		 * 
		 * 
		 * 
		 * */
		
		
		RowMapper<ModelPOCustom> serviceMapper=new RowMapper<ModelPOCustom>() {
			@Override
			public ModelPOCustom mapRow(ResultSet rs, int rownum) throws SQLException{
				ModelPOCustom bn=new ModelPOCustom();
		   
				bn.setProductionPlanChdId(rs.getLong("PRODUCTION_PLAN_CHD_ID"));
				bn.setMachineShiftId(rs.getLong("MACHINE_SHIFT_ID"));
				bn.setMachineTypeId(rs.getLong("machine_type_id"));
				bn.setProductionPlanDate(rs.getDate("PRODUCTION_PLAN_DATE"));
				bn.setProductionPlanQty(rs.getDouble("PRODUCTION_PLAN_QTY"));
				bn.setDesignId(rs.getLong("design_id"));
				bn.setMachineName(rs.getString("MACHINE_NAME"));
				bn.setOrderOwnerName(rs.getString("owner_name"));
				bn.setMachineTypeName(rs.getString("MACHINE_TYPE_NAME"));
				bn.setItemName(rs.getString("item_name"));
				bn.setOrderItemQuantityId(rs.getLong("order_item_qty_id"));
				bn.setwOMstId(rs.getLong("WO_MST_ID"));
				bn.setShiftName(rs.getString("SHIFT_NAME"));
			return bn;
			
			}	
		};
		
		
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		
		  parameters.addValue("wOMstId", wOMstId);
		  parameters.addValue("orderOwnerTypeId", orderOwnerTypeId);
		  parameters.addValue("ownerName", ownerName);
		  parameters.addValue("pPStratDate", pPStratDate);
		  parameters.addValue("pPEndDate", pPEndDate);
		  parameters.addValue("itemId", itemId);
		
		 System.out.println("query for when select WO and unchecked PO Done : " + qry_WO);
			
			return  namedJdbcTemplate.query(qry_WO,parameters,serviceMapper);

		
		// TODO Auto-generated method stub
		
	}
	
	
	
	@Override
	public List<ModelPOCustom> getWorkOrderDataChecked(Long wOMstId,Long orderOwnerTypeId,String ownerName,Long itemId,Date pOStratDate, Date pOEndDate) {
		
System.out.println("daoPO "+ "buyer name : ");
		
		String  qry_WO="SELECT ppc.PRODUCTION_PLAN_CHD_ID,ms.MACHINE_SHIFT_ID,mt.MACHINE_TYPE_ID,ppc.PRODUCTION_PLAN_DATE,ppc.PRODUCTION_PLAN_QTY,mt.MACHINE_TYPE_NAME,p.DESIGN_ID,m.MACHINE_NAME,wm.WO_MST_ID,\r\n" + 
				       "oiq.order_item_qty_id,i.item_name,oo.owner_name,s.SHIFT_NAME,p.PO_DATE \r\n" + 
				"FROM mlfm_po p \r\n" + 
				"INNER JOIN mlfm_production_plan_chd ppc on p.PRODUCTION_PLAN_CHD_ID=ppc.PRODUCTION_PLAN_CHD_ID\r\n" + 
				"INNER JOIN mlfm_machine_type mt on mt.MACHINE_TYPE_ID=p.MACHINE_TYPE_ID\r\n" + 
				"INNER JOIN mlfm_machine_shift ms on ms.MACHINE_SHIFT_ID=p.MACHINE_SHIFT_ID\r\n" + 
				"INNER JOIN mlfm_machine m on m.MACHINE_ID=ms.MACHINE_ID\r\n" + 
				"INNER JOIN mlfm_production_plan_mst ppm ON ppm.production_plan_mst_id=ppc.production_plan_mst_id\r\n" + 
				"INNER JOIN mlfm_wo_chd wc ON wc.wo_chd_id=ppm.wo_chd_id\r\n" + 
				"INNER JOIN mlfm_wo_mst wm ON wm.wo_mst_id=wc.wo_mst_id \r\n" + 
				"INNER join mlfm_order_item_qty oiq ON ppm.ORDER_ITEM_QTY_ID=oiq.order_item_qty_id\r\n" + 
				"INNER join mlfm_order_item oi on oiq.order_item_id=oi.item_order_id\r\n" + 
				"INNER join mlfm_item i on oi.item_id= i.item_id\r\n" + 
				"INNER join mlfm_order_owner oo on wm.ORDER_OWNER_ID=oo.order_owner_id " +
				"INNER JOIN mlfm_shift s on s.SHIFT_ID=ms.SHIFT_ID " +
				" inner join mlfm_order_owner_type oot on oot.order_owner_type_id=oo.order_owner_type_id " +
				"WHERE oot.order_owner_type_id=coalesce(:orderOwnerTypeId,oot.order_owner_type_id) AND " +
				"UPPER(trim(oo.owner_name)) LIKE CONCAT('%',upper(TRIM(:ownerName)),'%') AND " +
				"i.item_id=coalesce(:itemId,i.item_id) AND " + 
				" wm.WO_MST_ID=coalesce(:wOMstId,wm.WO_MST_ID) AND " +
				"p.PO_DATE between coalesce(:pOStratDate,p.PO_DATE) and coalesce(:pOEndDate,p.PO_DATE)";
		
		/*
		 * sql query
		 * 
		 * SELECT ppc.PRODUCTION_PLAN_CHD_ID,ms.MACHINE_SHIFT_ID,mt.MACHINE_TYPE_ID,ppc.PRODUCTION_PLAN_DATE,ppc.PRODUCTION_PLAN_QTY,mt.MACHINE_TYPE_NAME,p.DESIGN_ID,m.MACHINE_NAME,wm.WO_MST_ID,
oiq.order_item_qty_id,i.item_name,oo.owner_name,s.SHIFT_NAME
FROM mlfm_po p 
INNER JOIN mlfm_production_plan_chd ppc on p.PRODUCTION_PLAN_CHD_ID=ppc.PRODUCTION_PLAN_CHD_ID
INNER JOIN mlfm_machine_type mt on mt.MACHINE_TYPE_ID=p.MACHINE_TYPE_ID
INNER JOIN mlfm_machine_shift ms on ms.MACHINE_SHIFT_ID=p.MACHINE_SHIFT_ID
INNER JOIN mlfm_machine m on m.MACHINE_ID=ms.MACHINE_ID
INNER JOIN mlfm_production_plan_mst ppm ON ppm.production_plan_mst_id=ppc.production_plan_mst_id
INNER JOIN mlfm_wo_chd wc ON wc.wo_chd_id=ppm.wo_chd_id
INNER JOIN mlfm_wo_mst wm ON wm.wo_mst_id=wc.wo_mst_id 
INNER join mlfm_order_item_qty oiq ON ppm.ORDER_ITEM_QTY_ID=oiq.order_item_qty_id
INNER join mlfm_order_item oi on oiq.order_item_id=oi.item_order_id
INNER join mlfm_item i on oi.item_id= i.item_id
INNER join mlfm_order_owner oo on wm.ORDER_OWNER_ID=oo.order_owner_id
INNER JOIN mlfm_shift s on s.SHIFT_ID=ms.SHIFT_ID WHERE 
p.PO_DATE between coalesce(@pOStratDate,p.PO_DATE) and coalesce(@pOEndDate,p.PO_DATE)
		 * 
		 * 
		 * 
		 * 
		 * */
		
		
			RowMapper<ModelPOCustom> serviceMapper=new RowMapper<ModelPOCustom>() {
			@Override
			public ModelPOCustom mapRow(ResultSet rs, int rownum) throws SQLException{
				ModelPOCustom bn=new ModelPOCustom();
		         
				
				bn.setProductionPlanChdId(rs.getLong("PRODUCTION_PLAN_CHD_ID"));
				bn.setMachineShiftId(rs.getLong("MACHINE_SHIFT_ID"));
				bn.setMachineTypeId(rs.getLong("machine_type_id"));
				bn.setProductionPlanDate(rs.getDate("PRODUCTION_PLAN_DATE"));
				bn.setProductionPlanQty(rs.getDouble("PRODUCTION_PLAN_QTY"));
				bn.setDesignId(rs.getLong("design_id"));
				bn.setMachineName(rs.getString("MACHINE_NAME"));
				bn.setOrderOwnerName(rs.getString("owner_name"));
				bn.setMachineTypeName(rs.getString("MACHINE_TYPE_NAME"));
				bn.setItemName(rs.getString("item_name"));
				bn.setOrderItemQuantityId(rs.getLong("order_item_qty_id"));
				bn.setwOMstId(rs.getLong("WO_MST_ID"));
				
				bn.setShiftName(rs.getString("SHIFT_NAME"));
				
				
				bn.setpODate(rs.getDate("PO_DATE"));
			
				return bn;
			
			}	
		};
		
		
		
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		
		  //parameters.addValue("initial_Buyer", initial_Buyer);
		  
		  parameters.addValue("wOMstId", wOMstId);
		  parameters.addValue("orderOwnerTypeId", orderOwnerTypeId);
		  parameters.addValue("ownerName", ownerName);
		  parameters.addValue("itemId", itemId);
		
		  parameters.addValue("pOStratDate", pOStratDate);
	      parameters.addValue("pOEndDate", pOEndDate);
		
		 System.out.println("query for when select WO and unchecked PO Done : " + qry_WO);
			
			return  namedJdbcTemplate.query(qry_WO,parameters,serviceMapper);
		
		// TODO Auto-generated method stub
	
	}
	
	

	@Override
	public void savePO(ModelPO modelPO) {
		
		pORepository.save(modelPO);
		// TODO Auto-generated method stub
		
	}

	@Override
	public ModelPO getPOList(Long pOId) {
		
		
		// TODO Auto-generated method stub
		return pORepository.findPOById(pOId);

		
		
	}

	@Override
	public ModelPO getPOByDesignId(Long designId) {
		
		
		// TODO Auto-generated method stub
		return pORepository.findPOByDesignId(designId);
	}

	@Override
	public List<ModelPOCustom>getProductionPlanDataPONotDone(Long orderOwnerTypeId,
			Long orderOwnerId,
			Long wOMstId,
			Long itemId,
			Long designId, 
			Date planStartDate,
			Date planEndDate) {
//		public List<ModelPOCustom>getProductionPlanDataPONotDone(Long orderOwnerTypeId, Long orderOwnerId, Long wOMstId,Long itemId,Long designId,String userDesignNo, 
//				Date planStartDate, Date planEndDate) {
		// query production plan mst data which have not included in PO yet.
		
		String sql=" select a.production_plan_mst_id, plan_date,d.order_owner_id, d.owner_name,a.planned_by,f.user_name, a.ref_no,a.remarks, " +
		" c.wo_mst_id, b.wo_chd_id, g.design_id, h.lace_type_id, (CASE WHEN h.lace_type_id=1 THEN 'Chemical' ELSE 'Fabric' end) lace_type_name, h.fabric_type_id," + 
		" i.fabric_type_name,k.item_id, k.item_name, h.dtm from mlfm_production_plan_mst a " +
		" inner join mlfm_wo_chd b on b.wo_chd_id=a.wo_chd_id inner join mlfm_wo_mst c on c.wo_mst_id=b.wo_mst_id" +
		" inner join mlfm_order_owner d on d.order_owner_id=c.order_owner_id inner join mlfm_order_owner_type e" +
		" on e.order_owner_type_id=d.order_owner_type_id inner join bg_user f on f.user_id=a.planned_by " + 
		" inner join mlfm_order_item_qty g on g.order_item_qty_id=b.order_item_qty_id" +
		" left outer join mlfm_design h on h.design_id=g.design_id" +
		" left outer join mlfm_fabric_type i on i.fabric_type_id=h.fabric_type_id " +
		" inner join mlfm_order_item j on j.item_order_id=g.order_item_id  " +
		" inner join mlfm_item k on k.item_id=j.item_id" +
		" where e.order_owner_type_id=coalesce(:orderOwnerTypeId,e.order_owner_type_id)" + 
		" and d.order_owner_Id=coalesce(:orderOwnerId,d.order_owner_id) and c.wo_mst_id=coalesce(:wOMstId,c.wo_mst_id)" +
		" and k.item_id=coalesce(:itemId,k.item_id) " +
		" and h.design_id=coalesce(:designId,h.design_id) " +
		//" and h.user_design_no=coalesce(:userDesignNo,h.user_design_no) " +
		" and a.plan_date between coalesce(:planStartDate,a.plan_date) and coalesce(:planEndDate,a.plan_date) and a.active_status=1" +
		" order by a.plan_date";
		
		
		/*
		 * 
		 * select a.production_plan_mst_id, plan_date,d.order_owner_id, d.owner_name,a.planned_by,f.user_name, a.ref_no,a.remarks,
c.wo_mst_id, b.wo_chd_id, g.design_id, h.lace_type_id, (CASE WHEN h.lace_type_id=1 THEN 'Chemical' ELSE 'Fabric' end) lace_type_name, h.fabric_type_id,
i.fabric_type_name,k.item_id, k.item_name, h.dtm from mlfm_production_plan_mst a
inner join mlfm_wo_chd b on b.wo_chd_id=a.wo_chd_id inner join mlfm_wo_mst c on c.wo_mst_id=b.wo_mst_id
inner join mlfm_order_owner d on d.order_owner_id=c.order_owner_id inner join mlfm_order_owner_type e
on e.order_owner_type_id=d.order_owner_type_id inner join bg_user f on f.user_id=a.planned_by 
inner join mlfm_order_item_qty g on g.order_item_qty_id=b.order_item_qty_id
left outer join mlfm_design h on h.design_id=g.design_id
left outer join mlfm_fabric_type i on i.fabric_type_id=h.fabric_type_id
inner join mlfm_order_item j on j.item_order_id=g.order_item_id
inner join mlfm_item k on k.item_id=j.item_id
where e.order_owner_type_id=coalesce(@orderOwnerTypeId,e.order_owner_type_id) and k.item_id=coalesce(@itemId,k.item_id) and h.design_id=coalesce(@designId,h.design_id)
and d.order_owner_Id=coalesce(@orderOwnerId,d.order_owner_id) and c.wo_mst_id=coalesce(@wOMstId,c.wo_mst_id) 
and a.plan_date between coalesce(@planStartDate,a.plan_date) and coalesce(@planEndDate,a.plan_date) and a.active_status=1 order by a.plan_date
		 * 
		 * */
		
				
      	RowMapper<ModelPOCustom> serviceMapper=new RowMapper<ModelPOCustom>() {
			@Override
			public ModelPOCustom mapRow(ResultSet rs, int rownum) throws SQLException{
				ModelPOCustom bn=new ModelPOCustom();
		         
				
				bn.setProductionPlanMstId(rs.getLong("production_plan_mst_id"));
				bn.setPlanMstDate(rs.getDate("plan_date"));
				bn.setOrderOwnerId(rs.getLong("order_owner_id"));
				bn.setOrderOwnerName(rs.getString("owner_name"));
				bn.setwOMstId(rs.getLong("wo_mst_id"));
				bn.setwOChdId(rs.getLong("wo_chd_id"));
				bn.setRefNo(rs.getString("ref_no"));
				bn.setRemarks(rs.getString("remarks"));
				
				bn.setPlannedById(rs.getLong("planned_by"));
				bn.setPlannedByName(rs.getString("user_name"));
				bn.setDesignId(rs.getLong("design_id"));
				bn.setLaceTypeId(rs.getInt("lace_type_id"));
				bn.setLaceTypeName(rs.getString("lace_type_name"));
				bn.setFabricTypeId(rs.getLong("fabric_type_id"));
				bn.setFabricTypeName(rs.getString("fabric_type_name"));
				
				bn.setItemId(rs.getLong("item_id"));
				bn.setItemName(rs.getString("item_name"));
				
				bn.setdTM(rs.getString("dtm"));
			    
				return bn;
			
			}	
		};
	
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		
		  parameters.addValue("wOMstId", wOMstId);
		  parameters.addValue("orderOwnerTypeId", orderOwnerTypeId);
		  parameters.addValue("orderOwnerId", orderOwnerId);
		  parameters.addValue("itemId", itemId);
		  parameters.addValue("designId", designId);
		 // parameters.addValue("userDesignNo", userDesignNo);
		  parameters.addValue("planStartDate", planStartDate);
	      parameters.addValue("planEndDate", planEndDate);
		
		 System.out.println("query for when select WO and unchecked PO Done : " + sql);
			
			return  namedJdbcTemplate.query(sql,parameters,serviceMapper);		
		
	}
	
	
	@Override
	public List<ModelPOCustom>getProductionPlanDataPODone(Long orderOwnerTypeId, Long orderOwnerId, Long wOMstId,Long itemId,Long designId, 
			Date pODoneFrom, Date pODoneTo) {
		
		
		String sql="SELECT ppm.production_plan_mst_id,ppm.plan_date,oo.order_owner_id,oo.owner_name,ppm.planned_by,bu.user_name, ppm.ref_no,ppm.remarks,wm.wo_mst_id,wc.wo_chd_id, d.design_id,d.lace_type_id,(CASE WHEN d.lace_type_id=1 THEN 'Chemical' ELSE 'Fabric' end) lace_type_name,d.fabric_type_id,ft.fabric_type_name,\r\n" + 
				"i.item_id,i.item_name,d.dtm\r\n" + 
				"FROM mlfm_po_chd pc\r\n" + 
				"INNER JOIN mlfm_production_plan_chd ppc on ppc.PRODUCTION_PLAN_CHD_ID=pc.PRODUCTION_PLAN_CHD_ID\r\n" + 
				"INNER JOIN mlfm_production_plan_mst ppm on ppm.PRODUCTION_PLAN_MST_ID=ppc.PRODUCTION_PLAN_MST_ID\r\n" + 
				"inner join mlfm_wo_chd wc on wc.wo_chd_id=ppm.wo_chd_id\r\n" + 
				"inner join mlfm_wo_mst wm on wm.wo_mst_id=wc.wo_mst_id\r\n" + 
				"inner join mlfm_order_owner oo on oo.order_owner_id=wm.order_owner_id\r\n" + 
				"inner join mlfm_order_owner_type oot on oot.order_owner_type_id=oo.order_owner_type_id\r\n" + 
				"inner join bg_user bu on bu.user_id=ppm.planned_by\r\n" + 
				"inner join mlfm_order_item_qty oiq on oiq.order_item_qty_id=wc.order_item_qty_id\r\n" + 
				"left outer join mlfm_design d on d.design_id=oiq.design_id\r\n" + 
				"left outer join mlfm_fabric_type ft on ft.fabric_type_id=d.fabric_type_id\r\n" + 
				"inner join mlfm_order_item oi on oi.item_order_id=oiq.order_item_id\r\n" + 
				"inner join mlfm_item i on i.item_id=oi.item_id\r\n" + 
				"where oot.order_owner_type_id=coalesce(:orderOwnerTypeId,oot.order_owner_type_id)\r\n" + 
				"and oo.order_owner_Id=coalesce(:orderOwnerId,oo.order_owner_id) \r\n" + 
				"and wc.wo_mst_id=coalesce(:wOMstId,wc.wo_mst_id)\r\n" + 
				" and i.item_id=coalesce(:itemId,i.item_id) " +
				" and d.design_id=coalesce(:designId,d.design_id) " +
				"and ppm.plan_date between coalesce(:pODoneFrom,ppm.plan_date) and coalesce(:pODoneTo,ppm.plan_date) and ppm.active_status=1 order by ppm.plan_date";
		
		
		
		/*
		 * 
		 * SELECT ppm.production_plan_mst_id,ppm.plan_date,oo.order_owner_id,oo.owner_name,ppm.planned_by,bu.user_name, ppm.ref_no,ppm.remarks,wm.wo_mst_id,wc.wo_chd_id, d.design_id,d.lace_type_id,d.fabric_type_id,ft.fabric_type_name,
i.item_id,i.item_name,d.dtm
FROM mlfm_po_chd pc
INNER JOIN mlfm_production_plan_chd ppc on ppc.PRODUCTION_PLAN_CHD_ID=pc.PRODUCTION_PLAN_CHD_ID
INNER JOIN mlfm_production_plan_mst ppm on ppm.PRODUCTION_PLAN_MST_ID=ppc.PRODUCTION_PLAN_MST_ID
inner join mlfm_wo_chd wc on wc.wo_chd_id=ppm.wo_chd_id
inner join mlfm_wo_mst wm on wm.wo_mst_id=wc.wo_mst_id
inner join mlfm_order_owner oo on oo.order_owner_id=wm.order_owner_id
inner join mlfm_order_owner_type oot on oot.order_owner_type_id=oo.order_owner_type_id
inner join bg_user bu on bu.user_id=ppm.planned_by
inner join mlfm_order_item_qty oiq on oiq.order_item_qty_id=wc.order_item_qty_id
left outer join mlfm_design d on d.design_id=oiq.design_id
left outer join mlfm_fabric_type ft on ft.fabric_type_id=d.fabric_type_id
inner join mlfm_order_item oi on oi.item_order_id=oiq.order_item_id
inner join mlfm_item i on i.item_id=oi.item_id
where oot.order_owner_type_id=coalesce(@orderOwnerTypeId,oot.order_owner_type_id)
and oo.order_owner_Id=coalesce(@orderOwnerId,oo.order_owner_id) 
and wc.wo_mst_id=coalesce(@wOMstId,wc.wo_mst_id)
and ppm.plan_date between coalesce(@pODoneFrom,ppm.plan_date) and coalesce(@pODoneTo,ppm.plan_date) and ppm.active_status=1 order by ppm.plan_date
		 * 
		 * */
				
      	RowMapper<ModelPOCustom> serviceMapper=new RowMapper<ModelPOCustom>() {
			@Override
			public ModelPOCustom mapRow(ResultSet rs, int rownum) throws SQLException{
				ModelPOCustom bn=new ModelPOCustom();
		         
				
				bn.setProductionPlanMstId(rs.getLong("production_plan_mst_id"));
				bn.setPlanMstDate(rs.getDate("plan_date"));
				bn.setOrderOwnerId(rs.getLong("order_owner_id"));
				bn.setOrderOwnerName(rs.getString("owner_name"));
				bn.setwOMstId(rs.getLong("wo_mst_id"));
				bn.setwOChdId(rs.getLong("wo_chd_id"));
				bn.setRefNo(rs.getString("ref_no"));
				bn.setRemarks(rs.getString("remarks"));
				
				bn.setPlannedById(rs.getLong("planned_by"));
				bn.setPlannedByName(rs.getString("user_name"));
				bn.setDesignId(rs.getLong("design_id"));
				bn.setLaceTypeId(rs.getInt("lace_type_id"));
				bn.setLaceTypeName(rs.getString("lace_type_name"));
				bn.setFabricTypeId(rs.getLong("fabric_type_id"));
				bn.setFabricTypeName(rs.getString("fabric_type_name"));
				
				bn.setItemId(rs.getLong("item_id"));
				bn.setItemName(rs.getString("item_name"));
				
				bn.setdTM(rs.getString("dtm"));
			    
				return bn;
			
			}	
		};
	
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		
		  parameters.addValue("wOMstId", wOMstId);
		  parameters.addValue("orderOwnerTypeId", orderOwnerTypeId);
		  parameters.addValue("orderOwnerId", orderOwnerId);
		  
		  parameters.addValue("itemId", itemId);
		  parameters.addValue("designId", designId);
		  
		  parameters.addValue("pODoneFrom", pODoneFrom);
	      parameters.addValue("pODoneTo", pODoneTo);
		
		 System.out.println("checked checked PO Done : " + sql);
			
			return  namedJdbcTemplate.query(sql,parameters,serviceMapper);		
		
	}
	
	
	
	@Override
	
	
	
	public List<ModelPOCustom>getProductionPlanChdData(Long pPlanMstId) {
		/*
		String sql=" select a.production_plan_mst_id, b.production_plan_chd_id,b.production_plan_date," +
		" d.machine_name,e.shift_name, b.production_plan_qty, b.remarks, d.capacity_per_shift_per_day machine_capacity_qty, " + 
		" (select uom_name from mlfm_uom u where u.uom_id=d.capacity_uom) machine_capacity_uom_name" + 
		" from mlfm_production_plan_mst a " +
		" inner join mlfm_production_plan_chd b on b.production_plan_mst_id=a.production_plan_mst_id " + 
		" inner join mlfm_machine_shift c on c.machine_shift_id=b.machine_shift_id" +
		" inner join mlfm_machine d on d.machine_id=c.machine_id" +
		" inner join mlfm_shift e on e.shift_id=c.shift_id"+
		" where a.production_plan_mst_id=:pPlanMstId and a.active_status=1 and b.active_status=1" + 
		" order by b.production_plan_date";
		*/
		
		
		//  30/04/2019 
		String sql=" select a.production_plan_mst_id, b.production_plan_chd_id,b.production_plan_date,b.machine_shift_id," +
				" d.machine_name,e.shift_name, b.production_plan_qty, b.remarks, d.capacity_per_shift_per_day machine_capacity_qty, " + 
				" (select uom_name from mlfm_uom u where u.uom_id=d.capacity_uom) machine_capacity_uom_name" + 
				" from mlfm_production_plan_mst a " +
				" inner join mlfm_production_plan_chd b on b.production_plan_mst_id=a.production_plan_mst_id " + 
				" inner join mlfm_machine_shift c on c.machine_shift_id=b.machine_shift_id" +
				" inner join mlfm_machine d on d.machine_id=c.machine_id" +
				" inner join mlfm_shift e on e.shift_id=c.shift_id"+
				" where a.production_plan_mst_id=:pPlanMstId and a.active_status=1 and b.active_status=1" + 
				" order by b.production_plan_date";
		/*
		 * select de.NO_OF_STITCHES,a.production_plan_mst_id, b.production_plan_chd_id,b.production_plan_date,b.machine_shift_id,d.machine_name,
e.shift_name,b.production_plan_qty, b.remarks,d.capacity_per_shift_per_day machine_capacity_qty,
(select uom_name from mlfm_uom u where u.uom_id=d.capacity_uom) machine_capacity_uom_name
from mlfm_production_plan_mst a
inner join mlfm_production_plan_chd b on b.production_plan_mst_id=a.production_plan_mst_id
inner join mlfm_machine_shift c on c.machine_shift_id=b.machine_shift_id
inner join mlfm_machine d on d.machine_id=c.machine_id
LEFT OUTER JOIN mlfm_machine_type mt on mt.MACHINE_TYPE_ID=d.machine_type_id
LEFT OUTER JOIN mlfm_design de on de.MACHINE_TYPE=mt.MACHINE_TYPE_ID  
inner join mlfm_shift e on e.shift_id=c.shift_id
where a.production_plan_mst_id=COALESCE(@pPlanMstId,a.PRODUCTION_PLAN_MST_ID) 
and a.active_status=1 and b.active_status=1 order by b.production_plan_date
		 * 
		 * 
		 * */
		
		
		
		
      	RowMapper<ModelPOCustom> serviceMapper=new RowMapper<ModelPOCustom>() {
			@Override
			public ModelPOCustom mapRow(ResultSet rs, int rownum) throws SQLException{
				ModelPOCustom bn=new ModelPOCustom();
		         
				
				bn.setProductionPlanMstId(rs.getLong("production_plan_mst_id"));
				bn.setProductionPlanChdId(rs.getLong("production_plan_chd_id"));
				bn.setProductionPlanDate(rs.getDate("production_plan_date"));
				bn.setMachineName(rs.getString("machine_name"));
				bn.setShiftName(rs.getString("shift_name"));
				bn.setProductionPlanQty(rs.getDouble("production_plan_qty"));
				bn.setRemarksChd(rs.getString("remarks"));
				bn.setMachineCapacityQty(rs.getDouble("machine_capacity_qty"));
				bn.setMachineCapacityUOMName(rs.getString("machine_capacity_uom_name"));
			    bn.setMachineShiftId(rs.getLong("machine_shift_id"));
				return bn;
			
			}	
		};
	
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		
		  parameters.addValue("pPlanMstId", pPlanMstId);
	
		
		 System.out.println("query for production plan chd : " + sql);
			
			return  namedJdbcTemplate.query(sql,parameters,serviceMapper);	
		
	}
	
	
	public void savePOMst(ModelPOMst modelPOMst) {
		
		pOMstRepository.save(modelPOMst);
		
	}

	@Override
	public List<ModelPOCustom> getPOMstData(Long pOMstId) {
		
		String sql=" SELECT pm.PO_MST_ID,pm.PO_MST_DATE,pm.USER_PO_NO,pm.DESIGN_ID,pm.REMARKS,pm.ACTIVE_STATUS\r\n" + 
				"FROM mlfm_po_mst pm\r\n" + 
				"where pm.PO_MST_ID=COALESCE(:pOMstId,pm.PO_MST_ID)";
		
		
		/*
		 * 
		 * SELECT pm.PO_MST_ID,pm.PO_MST_DATE,pm.USER_PO_NO,pm.DESIGN_ID,pm.REMARKS,pm.ACTIVE_STATUS
           FROM mlfm_po_mst pm
           where pm.PO_MST_ID=COALESCE(@pOMstId,pm.PO_MST_ID)
		 * 
		 * 
		 * */
						
		      	RowMapper<ModelPOCustom> serviceMapper=new RowMapper<ModelPOCustom>() {
					@Override
					public ModelPOCustom mapRow(ResultSet rs, int rownum) throws SQLException{
						ModelPOCustom bn=new ModelPOCustom();
						
						
						
						bn.setpOMstId(rs.getLong("PO_MST_ID"));
				        bn.setDesignId(rs.getLong("DESIGN_ID"));
				        bn.setpOMstDate(rs.getDate("PO_MST_DATE"));
				        bn.setUserPONo(rs.getString("USER_PO_NO"));
				        bn.setRemarks(rs.getString("REMARKS"));
				        bn.setActiveStatus(rs.getInt("ACTIVE_STATUS"));
						

						return bn;
					
					}	
				};
			
				MapSqlParameterSource parameters = new MapSqlParameterSource();
				
				  parameters.addValue("pOMstId", pOMstId);
			
				
				 System.out.println("query for production plan chd : " + sql);
					
					return  namedJdbcTemplate.query(sql,parameters,serviceMapper);	
		
		// TODO Auto-generated method stub
		//return null;
	}

	@Override
	public ModelPOChd savePOChd(ModelPOChd modelPOChd) {
		
		pOChdRepository.save(modelPOChd);
		// TODO Auto-generated method stub
		
		return null;		
	}

	@Override
	public List<ModelPOCustom> getPOChdData(Long pOChdId) {
		
		
		String sql=" SELECT pc.PO_CHD_ID,pc.PO_MST_ID,pc.PO_CHD_DATE,pc.PRODUCTION_PLAN_CHD_ID,pc.PO_QTY,pc.REMARKS\r\n" + 
				"FROM mlfm_po_chd pc\r\n" + 
				"where pc.PO_CHD_ID=COALESCE(:pOChdId,pc.PO_CHD_ID)";
		
		
		/*
		 * 
		 * SELECT pc.PO_CHD_ID,pc.PO_MST_ID,pc.PO_CHD_DATE,pc.PRODUCTION_PLAN_CHD_ID,pc.PO_QTY,pc.REMARKS
FROM mlfm_po_chd pc
where pc.PO_CHD_ID=COALESCE(@pOChdId,pc.PO_CHD_ID)
		 * 
		 * 
		 * */
						
		      	RowMapper<ModelPOCustom> serviceMapper=new RowMapper<ModelPOCustom>() {
					@Override
					public ModelPOCustom mapRow(ResultSet rs, int rownum) throws SQLException{
						ModelPOCustom bn=new ModelPOCustom();
						
						
						
						bn.setpOMstId(rs.getLong("PO_MST_ID"));
						bn.setpOChdId(rs.getLong("PO_CHD_ID"));
						bn.setProductionPlanChdId(rs.getLong("PRODUCTION_PLAN_CHD_ID"));
				        bn.setpOChdDate(rs.getDate("PO_CHD_DATE"));
				        bn.setpOQty(rs.getDouble("PO_QTY"));
				        bn.setRemarks(rs.getString("REMARKS"));
				        
						

						return bn;
					
					}	
				};
			
				MapSqlParameterSource parameters = new MapSqlParameterSource();
			    parameters.addValue("pOChdId", pOChdId);
			    System.out.println("query for PO chd : " + sql);
					
				return  namedJdbcTemplate.query(sql,parameters,serviceMapper);	
		
		// TODO Auto-generated method stub
		// return null;
	}

	@Override
	public List<ModelPOCustom> getPOMstDoneData(Long pPChdId) {
		
		
		String sql="select pm.PO_MST_ID,pm.PO_MST_DATE,pm.USER_PO_NO,pm.DESIGN_ID,pm.REMARKS,pm.ACTIVE_STATUS\r\n" + 
				"from mlfm_po_chd pc\r\n" + 
				"INNER JOIN mlfm_production_plan_chd ppc ON ppc.PRODUCTION_PLAN_CHD_ID=pc.PRODUCTION_PLAN_CHD_ID\r\n" + 
				"INNER JOIN mlfm_po_mst pm ON pm.PO_MST_ID=pc.PO_MST_ID\r\n" + 
				"where pc.PRODUCTION_PLAN_CHD_ID=COALESCE(:pPChdId,pc.PRODUCTION_PLAN_CHD_ID)";
		
		
		/*
		 * 
		 * for po done mst
		 * 
		 * select pm.PO_MST_ID,pm.PO_MST_DATE,pm.USER_PO_NO,pm.DESIGN_ID
from mlfm_po_chd pc
INNER JOIN mlfm_production_plan_chd ppc ON ppc.PRODUCTION_PLAN_CHD_ID=pc.PRODUCTION_PLAN_CHD_ID
INNER JOIN mlfm_po_mst pm ON pm.PO_MST_ID=pc.PO_MST_ID
where pc.PRODUCTION_PLAN_CHD_ID=COALESCE(@pPChdId,pc.PRODUCTION_PLAN_CHD_ID)
		 * 
		 * */
						
		      	RowMapper<ModelPOCustom> serviceMapper=new RowMapper<ModelPOCustom>() {
					@Override
					public ModelPOCustom mapRow(ResultSet rs, int rownum) throws SQLException{
						ModelPOCustom bn=new ModelPOCustom();
						
						
						

				        
						bn.setpOMstId(rs.getLong("PO_MST_ID"));
				        bn.setDesignId(rs.getLong("DESIGN_ID"));
				        bn.setpOMstDate(rs.getDate("PO_MST_DATE"));
				        bn.setUserPONo(rs.getString("USER_PO_NO"));
				        bn.setRemarks(rs.getString("REMARKS"));
				         bn.setActiveStatus(rs.getInt("ACTIVE_STATUS"));

						return bn;
					
					}	
				};
			
				MapSqlParameterSource parameters = new MapSqlParameterSource();
			    parameters.addValue("pPChdId", pPChdId);
			    System.out.println("query for PO Mst Done : " + sql);
					
				return  namedJdbcTemplate.query(sql,parameters,serviceMapper);	
		
		
		// TODO Auto-generated method stub
	//	return null;
	}

	@Override
	public List<ModelPOCustom> getPOChdDoneData(Long pOMstId) {
		
		
		String sql="SELECT pc.PO_CHD_ID,pc.PO_MST_ID,ppm.PRODUCTION_PLAN_MST_ID,pc.PO_CHD_DATE,pc.PRODUCTION_PLAN_CHD_ID,pc.PO_QTY,pc.REMARKS\r\n" + 
				"FROM mlfm_po_chd pc\r\n" + 
				"inner JOIN mlfm_production_plan_chd ppc on ppc.PRODUCTION_PLAN_CHD_ID=pc.PRODUCTION_PLAN_CHD_ID\r\n" + 
				"INNER JOIN mlfm_production_plan_mst ppm ON ppm.PRODUCTION_PLAN_MST_ID=ppc.PRODUCTION_PLAN_MST_ID\r\n" + 
				"where pc.PO_MST_ID=COALESCE(:pOMstId,pc.PO_MST_ID)";
		
		
		/*
		 * 
		 * SELECT pc.PO_CHD_ID,pc.PO_MST_ID,ppm.PRODUCTION_PLAN_MST_ID,pc.PO_CHD_DATE,pc.PRODUCTION_PLAN_CHD_ID,pc.PO_QTY,pc.REMARKS
FROM mlfm_po_chd pc
inner JOIN mlfm_production_plan_chd ppc on ppc.PRODUCTION_PLAN_CHD_ID=pc.PRODUCTION_PLAN_CHD_ID
INNER JOIN mlfm_production_plan_mst ppm ON ppm.PRODUCTION_PLAN_MST_ID=ppc.PRODUCTION_PLAN_MST_ID
where pc.PO_MST_ID=COALESCE(@pOMstId,pc.PO_MST_ID)
		 * 
		 * 
		 * */
						
		      	RowMapper<ModelPOCustom> serviceMapper=new RowMapper<ModelPOCustom>() {
					@Override
					public ModelPOCustom mapRow(ResultSet rs, int rownum) throws SQLException{
						ModelPOCustom bn=new ModelPOCustom();
						
						
						
						bn.setpOMstId(rs.getLong("PO_MST_ID"));
						bn.setpOChdId(rs.getLong("PO_CHD_ID"));
						bn.setProductionPlanChdId(rs.getLong("PRODUCTION_PLAN_CHD_ID"));
						bn.setProductionPlanMstId(rs.getLong("PRODUCTION_PLAN_MST_ID"));
				        bn.setpOChdDate(rs.getDate("PO_CHD_DATE"));
				        bn.setpOQty(rs.getDouble("PO_QTY"));
				        bn.setRemarks(rs.getString("REMARKS"));
				        
						

						return bn;
					
					}	
				};
			
				MapSqlParameterSource parameters = new MapSqlParameterSource();
			    parameters.addValue("pOMstId", pOMstId);
			    System.out.println("query for PO chd : " + sql);
					
				return  namedJdbcTemplate.query(sql,parameters,serviceMapper);	
		
		
		// TODO Auto-generated method stub
		//return null;
	}

	@Override
	public Optional<ModelPOMst> findPOMstById(Long pOMstId) {
		// TODO Auto-generated method stub
		return pOMstRepository.findById(pOMstId);
	}

}
