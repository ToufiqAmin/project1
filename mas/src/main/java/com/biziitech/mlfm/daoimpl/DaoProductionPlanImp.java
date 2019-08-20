package com.biziitech.mlfm.daoimpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.biziitech.mlfm.bg.model.ModelUser;
import com.biziitech.mlfm.custom.model.ModelInquiryList;
import com.biziitech.mlfm.custom.model.ModelMachineScheduleData;
import com.biziitech.mlfm.custom.model.ModelProductionCustom;
import com.biziitech.mlfm.dao.DaoProductionPlan;
import com.biziitech.mlfm.model.ModelProductionPlanChd;
import com.biziitech.mlfm.model.ModelProductionPlanMst;
import com.biziitech.mlfm.repository.ProductionPlanRepository;




@Service
public class DaoProductionPlanImp implements DaoProductionPlan {
	
//	private SimpleJdbcTemplate jdbcTemplate;
//
//	@Resource(name = "dataSource")
//	public void setDataSource(DataSource dataSource) {
//		this.jdbcTemplate = new SimpleJdbcTemplate(dataSource);
//	}
	
	@Autowired
	private ProductionPlanRepository productionPlanRepository;
	
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
		public void saveProductionPlan(ModelProductionPlanMst productionPlan) {
        	
        	
			//if(productionPlan.isActive())
			//	productionPlan.setActiveStatus(1);
			//else
			//	productionPlan.setActiveStatus(0);
			
			productionPlanRepository.save(productionPlan);
			
		}
		
		@Override
		public List<ModelUser> getUserList() {
			
			//List<ModelShift> resultList = machineShiftRepository.getAllShift();
			List<ModelUser> resultList = productionPlanRepository.findUser(); // findShift select all active shifts.
			List<ModelUser> userList= new ArrayList<>();
			for(ModelUser type: resultList) {
				if(type.getActiveStatus()==1)
					{
					type.setActive(true);
					type.setsActive("Yes");
					}
				else
				{
					type.setActive(true);

					type.setsActive("No");
				}
				userList.add(type);
			}
			
			
			
			return userList;
		}
		
		
		@Override
		
		//public List<ModelInquiryList>getOrderList(int orderType,String initial_Buyer,String ultimateBuyerName,Long inqueryId,Long planId,Long designBy,Long designId,Long userId,Long userClusterId,Date inqStartDate, Date inqEndDate,Date designStartDate,Date designEndDate,Date planStartDate,Date planEndDate)
		
		public List<ModelInquiryList>getOrderList(String initial_Buyer,String ultimateBuyerName,Long inqueryId,Date inqStartDate, Date inqEndDate)

		{
			System.out.println("daoProductionPlan "+ " initial buyer :" + initial_Buyer+ " ultimate buyer name : " + ultimateBuyerName);
			
			//between coalesce(date_format('" + inqStartDate + "',\"%Y-%m-%d\")
				/*
				 * mlfm_order  a
				 * mlfm_order_item b
				 * mlfm_order_item_qty c
				 *  mlfm_item d
				 *  mlfm_uom e
				 *  mlfm_order_owner f
				 *  mlfm_design l
				*/
//			String  qry=" select * from (  select a.order_id, a.order_date,b.item_order_id,b.item_id,d.item_name,c.order_item_qty_id,c.item_qty,c.uom_id,e.uom_name,c.design_id,a.order_owner_id,f.owner_name from mlfm_order a inner join mlfm_order_item b on a.order_id=b.order_id inner join mlfm_order_item_qty c on b.item_order_id=c.order_item_id inner join mlfm_item d on b.item_id=d.item_id left outer join mlfm_uom e on c.uom_id=e.uom_id inner join mlfm_order_owner f on a.order_owner_id=f.order_owner_id  and a.active_status=1 and b.active_status=1 and c.active_status=1 and not exists (SELECT 1 FROM mlfm_production_plan_mst p where p.ORDER_ITEM_QTY_ID=c.order_item_qty_id) ) a" + 
//					" where a.owner_name like concat('%',:initial_Buyer,'%') and a.owner_name like concat('%',:ultimateBuyerName,'%') and a.order_date between coalesce(:inqStartDate,a.order_date) and coalesce(:inqEndDate,a.order_date) ";
			
//			String  qry_inquiry=" select * from (  select a.order_id, a.order_date,b.item_order_id,b.item_id,d.item_name,c.order_item_qty_id,c.item_qty,c.uom_id,e.uom_name,c.design_id,"+
//			" a.order_owner_id,f.owner_name from mlfm_order a inner join mlfm_order_item b on a.order_id=b.order_id " +
//					" inner join mlfm_order_item_qty c on b.item_order_id=c.order_item_id inner join mlfm_item d on b.item_id=d.item_id " +
//			" left outer join mlfm_uom e on c.uom_id=e.uom_id inner join mlfm_order_owner f on a.order_owner_id=f.order_owner_id  and a.active_status=1 and b.active_status=1 and c.active_status=1 and not exists (SELECT 1 FROM mlfm_production_plan_mst p where p.ORDER_ITEM_QTY_ID=c.order_item_qty_id) ) a" + 
//					" where a.owner_name like concat('%',:initial_Buyer,'%') and" +
//					 " a.owner_name like concat('%',:ultimateBuyerName,'%') and" +
//					 " a.order_id= coalesce(:inqueryId,a.order_id) and" +
//					" a.design_id= coalesce(:designId,a.order_id) and" + 
//					" a.order_date between coalesce(:inqStartDate,a.order_date) and" + 
//					 " coalesce(:inqEndDate,a.order_date) ";
			
			String  qry_inquiry=" select * from (  select a.order_id, a.order_date,b.item_order_id,b.item_id,d.item_name,c.order_item_qty_id,c.item_qty,c.uom_id,e.uom_name,c.design_id,"+
					        " a.order_owner_id,f.owner_name from mlfm_order a inner join mlfm_order_item b on a.order_id=b.order_id " +
							" inner join mlfm_order_item_qty c on b.item_order_id=c.order_item_id inner join mlfm_item d on b.item_id=d.item_id " +
					        " left outer join mlfm_uom e on c.uom_id=e.uom_id inner join mlfm_order_owner f on a.order_owner_id=f.order_owner_id " +
					        "and a.active_status=1 " +
					        "and b.active_status=1 " +
					        "and c.active_status=1 " +
					        "and not exists (SELECT 1 FROM mlfm_production_plan_mst p where p.ORDER_ITEM_QTY_ID=c.order_item_qty_id) ) a" + 
							" where a.owner_name like concat('%',:initial_Buyer,'%') and" +
						    " a.owner_name like concat('%',:ultimateBuyerName,'%') and" +
						    " a.order_id= coalesce(:inqueryId,a.order_id) and" +
							" a.order_date between coalesce(:inqStartDate,a.order_date) and" + 
						    " coalesce(:inqEndDate,a.order_date) ";
			
			
			/* 
			 * sql Query
			 * 
			 *       select * from (  select a.order_id, a.order_date,b.item_order_id,b.item_id,d.item_name,c.order_item_qty_id,c.item_qty,c.uom_id,e.uom_name,c.design_id,
			         a.order_owner_id,f.owner_name from mlfm_order a inner join mlfm_order_item b on a.order_id=b.order_id 
					 inner join mlfm_order_item_qty c on b.item_order_id=c.order_item_id inner join mlfm_item d on b.item_id=d.item_id 
			         left outer join mlfm_uom e on c.uom_id=e.uom_id inner join mlfm_order_owner f on a.order_owner_id=f.order_owner_id 
			         and a.active_status=1 
			         and b.active_status=1
			         and c.active_status=1 
			         and not exists (SELECT 1 FROM mlfm_production_plan_mst p where p.ORDER_ITEM_QTY_ID=c.order_item_qty_id) ) a
					 where a.owner_name like concat('%',@initial_Buyer,'%') and
				     a.owner_name like concat('%',@ultimateBuyerName,'%') and
				     a.order_id= coalesce(@inqueryId,a.order_id) and
					 a.order_date between coalesce(@inqStartDate,a.order_date) and
				     coalesce(@inqEndDate,a.order_date)
			*/

			
			// this query is for considering wo data. This shall be implemented together with inquiry data after completing wo UI. (By: SAS, Date: Jan 9, 2018)
		/*	
			String  qry_wo="select * from (  select a.order_id, a.order_date,b.item_order_id,b.item_id,d.item_name,c.order_item_qty_id,c.item_qty,c.uom_id,e.uom_name,c.design_id,\r\n" + 
					"a.order_owner_id,f.owner_name, wom.wo_mst_id wo_id,wom.wo_date,woc.item_qty wo_qty from mlfm_order a inner join mlfm_order_item b on a.order_id=b.order_id \r\n" + 
					"inner join mlfm_order_item_qty c on b.item_order_id=c.order_item_id inner join mlfm_item d on b.item_id=d.item_id \r\n" + 
					"left outer join mlfm_uom e on c.uom_id=e.uom_id inner join mlfm_order_owner f on a.order_owner_id=f.order_owner_id inner join mlfm_wo_mst wom \r\n" + 
					"on wom.order_owner_id=f.order_owner_id inner join mlfm_wo_chd woc on woc.wo_mst_id=wom.wo_mst_id and woc.order_item_qty_id =c.order_item_qty_id \r\n" + 
					") a " + 
					" where a.owner_name like concat('%',:initial_Buyer,'%') and" +
					" a.owner_name like concat('%',:ultimateBuyerName,'%') and" +
					" a.order_id= coalesce(:inqueryId,a.order_id) and" +
					" a.design_id= coalesce(:designId,a.order_id) and" + 
					" a.order_date between coalesce(:inqStartDate,a.order_date) and" + 
					 " coalesce(:inqEndDate,a.order_date) ";
		*/	
				RowMapper<ModelInquiryList> serviceMapper=new RowMapper<ModelInquiryList>() {
					@Override
					public ModelInquiryList mapRow(ResultSet rs, int rownum) throws SQLException{
						ModelInquiryList bn=new ModelInquiryList();
				
					bn.setOrderId(rs.getLong("order_id"));
					bn.setOrderDate(rs.getDate("order_date"));
					bn.setOrderItemQuantityId(rs.getLong("order_item_qty_id"));
					bn.setOrderOwnerName(rs.getString("owner_name"));
					bn.setItemName(rs.getString("item_name"));
					bn.setItemQty(rs.getDouble("item_qty"));
					bn.setUomName(rs.getString("uom_name"));
					
					//bn.setOrderOwnerId(rs.getLong("order_owner_id"));
					
				 
					return bn;
					
					}	
				};
				
				
				MapSqlParameterSource parameters = new MapSqlParameterSource();
				
				  parameters.addValue("initial_Buyer", initial_Buyer);
				  parameters.addValue("ultimateBuyerName", ultimateBuyerName);
				  parameters.addValue("inqueryId", inqueryId);
			      parameters.addValue("inqStartDate", inqStartDate);
                  parameters.addValue("inqEndDate", inqEndDate);
			     
			    System.out.println("query for when select Inquiry : " + qry_inquiry);
				
				return  namedJdbcTemplate.query(qry_inquiry,parameters,serviceMapper);

		}
		
		
		
		public List<ModelInquiryList>getOrderListPlanned(String initial_Buyer,String ultimateBuyerName,Long inqueryId,Date inqStartDate, Date inqEndDate)

		{
			System.out.println("daoProductionPlan "+ " initial buyer :" + initial_Buyer+ " ultimate buyer name : " + ultimateBuyerName);
			
			
			String  qry_inquiry=" select * from (  select a.order_id, a.order_date,b.item_order_id,b.item_id,d.item_name,c.order_item_qty_id,c.item_qty,c.uom_id,e.uom_name,c.design_id,"+
					        " a.order_owner_id,f.owner_name from mlfm_order a inner join mlfm_order_item b on a.order_id=b.order_id " +
							" inner join mlfm_order_item_qty c on b.item_order_id=c.order_item_id inner join mlfm_item d on b.item_id=d.item_id " +
					        " left outer join mlfm_uom e on c.uom_id=e.uom_id inner join mlfm_order_owner f on a.order_owner_id=f.order_owner_id " +
					        "and a.active_status=1 " +
					        "and b.active_status=1 " +
					        "and c.active_status=1 " +
					        "and not exists (SELECT 1 FROM mlfm_production_plan_mst p where p.ORDER_ITEM_QTY_ID=c.order_item_qty_id) ) a" + 
							" where a.owner_name like concat('%',:initial_Buyer,'%') and" +
						    " a.owner_name like concat('%',:ultimateBuyerName,'%') and" +
						    " a.order_id= coalesce(:inqueryId,a.order_id) and" +
							" a.order_date between coalesce(:inqStartDate,a.order_date) and" + 
						    " coalesce(:inqEndDate,a.order_date) ";
			/* 
			 * sql Query
			 * 
			 *       select * from (  select a.order_id, a.order_date,b.item_order_id,b.item_id,d.item_name,c.order_item_qty_id,c.item_qty,c.uom_id,e.uom_name,c.design_id,
			         a.order_owner_id,f.owner_name from mlfm_order a inner join mlfm_order_item b on a.order_id=b.order_id 
					 inner join mlfm_order_item_qty c on b.item_order_id=c.order_item_id inner join mlfm_item d on b.item_id=d.item_id 
			         left outer join mlfm_uom e on c.uom_id=e.uom_id inner join mlfm_order_owner f on a.order_owner_id=f.order_owner_id 
			         and a.active_status=1 
			         and b.active_status=1
			         and c.active_status=1 
			         and not exists (SELECT 1 FROM mlfm_production_plan_mst p where p.ORDER_ITEM_QTY_ID=c.order_item_qty_id) ) a
					 where a.owner_name like concat('%',@initial_Buyer,'%') and
				     a.owner_name like concat('%',@ultimateBuyerName,'%') and
				     a.order_id= coalesce(@inqueryId,a.order_id) and
					 a.order_date between coalesce(@inqStartDate,a.order_date) and
				     coalesce(@inqEndDate,a.order_date)
			*/

				RowMapper<ModelInquiryList> serviceMapper=new RowMapper<ModelInquiryList>() {
					@Override
					public ModelInquiryList mapRow(ResultSet rs, int rownum) throws SQLException{
						ModelInquiryList bn=new ModelInquiryList();
				
					bn.setOrderId(rs.getLong("order_id"));
					bn.setOrderDate(rs.getDate("order_date"));
					bn.setOrderItemQuantityId(rs.getLong("order_item_qty_id"));
					bn.setOrderOwnerName(rs.getString("owner_name"));
					bn.setItemName(rs.getString("item_name"));
					bn.setItemQty(rs.getDouble("item_qty"));
					bn.setUomName(rs.getString("uom_name"));
					
					//bn.setOrderOwnerId(rs.getLong("order_owner_id"));
					
				 
					return bn;
					
					}	
				};
				
				
				MapSqlParameterSource parameters = new MapSqlParameterSource();
				
				  parameters.addValue("initial_Buyer", initial_Buyer);
				  parameters.addValue("ultimateBuyerName", ultimateBuyerName);
				  parameters.addValue("inqueryId", inqueryId);
			      parameters.addValue("inqStartDate", inqStartDate);
                  parameters.addValue("inqEndDate", inqEndDate);
			     
			    System.out.println("query for when select Inquiry : " + qry_inquiry);
				
				return  namedJdbcTemplate.query(qry_inquiry,parameters,serviceMapper);

		}
		
		
		
		
		
		
		
//		public List<ModelInquiryList>getWOList(String initial_Buyer,String ultimateBuyerName,Long inqueryId,Long planId,
//				Long designBy,Long designId,Long userId,Date inqStartDate, Date inqEndDate,Date designStartDate,
//				Date designEndDate,Date planStartDate,Date planEndDate){
        public List<ModelInquiryList>getWOList(Long orderOwnerTypeId,Long initialBuyerId,Long ultimateBuyerId,Long wOMstId,Long itemId,
        		Date wOStartDate,Date wOEndDate){
			
			/*
			String  qry_wo="select * from (  select a.order_id, a.order_date,b.item_order_id,b.item_id,d.item_name,c.order_item_qty_id,c.item_qty,c.uom_id,e.uom_name,c.design_id,\r\n" + 
					"a.order_owner_id,f.owner_name, wom.wo_mst_id wo_id,wom.wo_date,woc.item_qty wo_qty from mlfm_order a inner join mlfm_order_item b on a.order_id=b.order_id \r\n" + 
					"inner join mlfm_order_item_qty c on b.item_order_id=c.order_item_id inner join mlfm_item d on b.item_id=d.item_id \r\n" + 
					"left outer join mlfm_uom e on c.uom_id=e.uom_id inner join mlfm_order_owner f on a.order_owner_id=f.order_owner_id inner join mlfm_wo_mst wom \r\n" + 
					"on wom.order_owner_id=f.order_owner_id inner join mlfm_wo_chd woc on woc.wo_mst_id=wom.wo_mst_id and woc.order_item_qty_id =c.order_item_qty_id \r\n" + 
					") a " + 
					" where a.owner_name like concat('%',:initial_Buyer,'%') and" +
					" a.owner_name like concat('%',:ultimateBuyerName,'%') and" +
					" a.order_id= coalesce(:inqueryId,a.order_id) and" +
					" a.design_id= coalesce(:designId,a.order_id) and" + 
					" a.order_date between coalesce(:inqStartDate,a.order_date) and" + 
					 " coalesce(:inqEndDate,a.order_date) ";
			
			
			 * sql Query
			 * 
			 * select * from (  select a.order_id, a.order_date,b.item_order_id,b.item_id,d.item_name,c.order_item_qty_id,c.item_qty,c.uom_id,e.uom_name,c.design_id, 
					a.order_owner_id,f.owner_name, wom.wo_mst_id wo_id,wom.wo_date,woc.item_qty wo_qty from mlfm_order a inner join mlfm_order_item b on a.order_id=b.order_id  
					inner join mlfm_order_item_qty c on b.item_order_id=c.order_item_id inner join mlfm_item d on b.item_id=d.item_id
					left outer join mlfm_uom e on c.uom_id=e.uom_id inner join mlfm_order_owner f on a.order_owner_id=f.order_owner_id inner join mlfm_wo_mst wom
					on wom.order_owner_id=f.order_owner_id inner join mlfm_wo_chd woc on woc.wo_mst_id=wom.wo_mst_id and woc.order_item_qty_id =c.order_item_qty_id
					) a 
					 where a.owner_name like concat('%',@initial_Buyer,'%') and
					 a.owner_name like concat('%',@ultimateBuyerName,'%') and
					 a.order_id= coalesce(@inqueryId,a.order_id) and
					 a.design_id= coalesce(@designId,a.order_id) and
					 a.order_date between coalesce(@inqStartDate,a.order_date) and
					 coalesce(@inqEndDate,a.order_date)
			 * */
			
			String queryWONotDane="SELECT wc.WO_CHD_ID,wm.WO_MST_ID,wm.WO_DATE,wc.ORDER_ITEM_QTY_ID,wc.ITEM_QTY,oo.owner_name,i.item_name,u.uom_name,\r\n" + 
					"wc.REMARKS,wc.ACTIVE_STATUS,o.order_id \r\n" + 
					"FROM mlfm_wo_mst wm\r\n" + 
					"INNER JOIN mlfm_wo_chd wc ON wc.WO_MST_ID=wm.WO_MST_ID\r\n" + 
					"INNER JOIN mlfm_order_owner oo ON oo.order_owner_id=wm.ORDER_OWNER_ID\r\n" + 
					"INNER JOIN mlfm_order_item_qty oiq ON oiq.order_item_qty_id=wc.ORDER_ITEM_QTY_ID\r\n" + 
					"INNER JOIN mlfm_order_owner_type oot ON oot.order_owner_type_id=oo.order_owner_type_id " +
					"INNER JOIN mlfm_order_item oi on oi.item_order_id=oiq.order_item_id\r\n" + 
					" INNER JOIN mlfm_order o on o.order_id=oi.order_id "+
					"INNER JOIN mlfm_item i on i.item_id=oi.item_id\r\n" + 
					"LEFT OUTER JOIN mlfm_uom u ON u.uom_id=oiq.uom_id\r\n" + 
					"AND wc.ACTIVE_STATUS=1  \r\n" + 
					"WHERE oo.order_owner_id=coalesce(:initialBuyerId,oo.order_owner_id) AND\r\n" + 
					"oo.order_owner_id = coalesce(:ultimateBuyerId,oo.order_owner_id) AND\r\n" + 
					"oot.order_owner_type_id=COALESCE(:orderOwnerTypeId,oot.order_owner_type_id) AND " +
					"wm.WO_MST_ID=coalesce(:wOMstId,wm.WO_MST_ID) AND\r\n" + 
					"i.item_id=coalesce(:itemId,i.item_id) AND\r\n" + 
					"wm.WO_DATE BETWEEN COALESCE (:wOStartDate,wm.WO_DATE) AND COALESCE(:wOEndDate,wm.WO_DATE)";
			
			
			/*
			 * sql Qry
			 * 
			 * 
			 * SELECT wc.WO_CHD_ID,wm.WO_MST_ID,wm.WO_DATE,wc.ORDER_ITEM_QTY_ID,wc.ITEM_QTY,oo.owner_name,i.item_name,u.uom_name,
wc.REMARKS,wc.ACTIVE_STATUS,o.order_id
FROM mlfm_wo_mst wm
INNER JOIN mlfm_wo_chd wc ON wc.WO_MST_ID=wm.WO_MST_ID
INNER JOIN mlfm_order_owner oo ON oo.order_owner_id=wm.ORDER_OWNER_ID
INNER JOIN mlfm_order_owner_type oot ON oot.order_owner_type_id=oo.order_owner_type_id
INNER JOIN mlfm_order_item_qty oiq ON oiq.order_item_qty_id=wc.ORDER_ITEM_QTY_ID
INNER JOIN mlfm_order_item oi on oi.item_order_id=oiq.order_item_id
INNER JOIN mlfm_order o on o.order_id=oi.order_id
INNER JOIN mlfm_item i on i.item_id=oi.item_id
LEFT OUTER JOIN mlfm_uom u ON u.uom_id=oiq.uom_id
AND wc.ACTIVE_STATUS=1  
WHERE oo.order_owner_id=coalesce(@orderOwnerId,oo.order_owner_id) AND
oo.order_owner_id = coalesce(@ultimateOwnerId,oo.order_owner_id) AND
oot.order_owner_type_id=COALESCE(@orderOwnerTypeId,oot.order_owner_type_id) AND
wm.WO_MST_ID=coalesce(@wOMstId,wm.WO_MST_ID) AND
i.item_id=coalesce(@itemId,i.item_id) AND
wm.WO_DATE BETWEEN COALESCE (@wOStartDate,wm.WO_DATE) AND COALESCE(@wOEndDate,wm.WO_DATE)
			 * 
			 * 
			 * */
			
			
				RowMapper<ModelInquiryList> serviceMapper=new RowMapper<ModelInquiryList>() {
					@Override
					public ModelInquiryList mapRow(ResultSet rs, int rownum) throws SQLException{
						ModelInquiryList bn=new ModelInquiryList();
						
						bn.setwOChdId(rs.getLong("WO_CHD_ID"));
						bn.setwOMstId(rs.getLong("WO_MST_ID"));
						bn.setwODate(rs.getDate("WO_DATE"));
						bn.setOrderItemQuantityId(rs.getLong("ORDER_ITEM_QTY_ID"));
						bn.setwOQty(rs.getDouble("ITEM_QTY"));
						bn.setOrderOwnerName(rs.getString("owner_name"));
						bn.setItemName(rs.getString("item_name"));
						bn.setUomName(rs.getString("uom_name"));
						bn.setRemarks(rs.getString("REMARKS"));
						bn.setActiveStatus(rs.getInt("ACTIVE_STATUS"));
						bn.setOrderId(rs.getLong("order_id"));
				
//					bn.setOrderId(rs.getLong("order_id"));
//					bn.setOrderDate(rs.getDate("order_date"));
//					bn.setOrderItemQuantityId(rs.getLong("order_item_qty_id"));
//					bn.setOrderOwnerName(rs.getString("owner_name"));
//					bn.setItemName(rs.getString("item_name"));
//					bn.setItemQty(rs.getDouble("item_qty"));
//					bn.setUomName(rs.getString("uom_name"));
//                  bn.setOrderOwnerId(rs.getLong("order_owner_id"));
					
				 
					return bn;
					
					}	
				};
				
				
				MapSqlParameterSource parameters = new MapSqlParameterSource();
				
				  parameters.addValue("orderOwnerTypeId", orderOwnerTypeId);
				  parameters.addValue("initialBuyerId", initialBuyerId);
				  parameters.addValue("ultimateBuyerId", ultimateBuyerId);
				  parameters.addValue("wOMstId", wOMstId);
				  parameters.addValue("itemId", itemId);
			      parameters.addValue("wOStartDate", wOStartDate);
                  parameters.addValue("wOEndDate", wOEndDate);
				
//				  parameters.addValue("initial_Buyer", initial_Buyer);
//				  parameters.addValue("ultimateBuyerName", ultimateBuyerName);
//				  parameters.addValue("inqueryId", inqueryId);
//				  parameters.addValue("designId", designId);
//			      parameters.addValue("inqStartDate", inqStartDate);
//                  parameters.addValue("inqEndDate", inqEndDate);
			     
			    System.out.println("query for when select WO and checked : not planned " + queryWONotDane);
				
				return  namedJdbcTemplate.query(queryWONotDane,parameters,serviceMapper);
			
	
		}
		
        
		public List<ModelInquiryList>getWOListPlanned(Long orderOwnerTypeId,Long initialBuyerId,Long ultimateBuyerId,Long wOMstId,Long itemId,
				Long userId,Date planStartDate,Date planEndDate){
			
			/*
			String  qry_wo="select * from (  select a.order_id, a.order_date,b.item_order_id,b.item_id,d.item_name,c.order_item_qty_id,c.item_qty,c.uom_id,e.uom_name,c.design_id,\r\n" + 
					"a.order_owner_id,f.owner_name, wom.wo_mst_id wo_id,wom.wo_date,woc.item_qty wo_qty from mlfm_order a inner join mlfm_order_item b on a.order_id=b.order_id \r\n" + 
					"inner join mlfm_order_item_qty c on b.item_order_id=c.order_item_id inner join mlfm_item d on b.item_id=d.item_id \r\n" + 
					"left outer join mlfm_uom e on c.uom_id=e.uom_id inner join mlfm_order_owner f on a.order_owner_id=f.order_owner_id inner join mlfm_wo_mst wom \r\n" + 
					"on wom.order_owner_id=f.order_owner_id inner join mlfm_wo_chd woc on woc.wo_mst_id=wom.wo_mst_id and woc.order_item_qty_id =c.order_item_qty_id \r\n" + 
					") a " + 
					" where a.owner_name like concat('%',:initial_Buyer,'%') and" +
					" a.owner_name like concat('%',:ultimateBuyerName,'%') and" +
					" a.order_id= coalesce(:inqueryId,a.order_id) and" +
					" a.design_id= coalesce(:designId,a.order_id) and" + 
					" a.order_date between coalesce(:inqStartDate,a.order_date) and" + 
					 " coalesce(:inqEndDate,a.order_date) ";
			
			
			 * sql Query
			 * 
			 * select * from (  select a.order_id, a.order_date,b.item_order_id,b.item_id,d.item_name,c.order_item_qty_id,c.item_qty,c.uom_id,e.uom_name,c.design_id, 
					a.order_owner_id,f.owner_name, wom.wo_mst_id wo_id,wom.wo_date,woc.item_qty wo_qty from mlfm_order a inner join mlfm_order_item b on a.order_id=b.order_id  
					inner join mlfm_order_item_qty c on b.item_order_id=c.order_item_id inner join mlfm_item d on b.item_id=d.item_id
					left outer join mlfm_uom e on c.uom_id=e.uom_id inner join mlfm_order_owner f on a.order_owner_id=f.order_owner_id inner join mlfm_wo_mst wom
					on wom.order_owner_id=f.order_owner_id inner join mlfm_wo_chd woc on woc.wo_mst_id=wom.wo_mst_id and woc.order_item_qty_id =c.order_item_qty_id
					) a 
					 where a.owner_name like concat('%',@initial_Buyer,'%') and
					 a.owner_name like concat('%',@ultimateBuyerName,'%') and
					 a.order_id= coalesce(@inqueryId,a.order_id) and
					 a.design_id= coalesce(@designId,a.order_id) and
					 a.order_date between coalesce(@inqStartDate,a.order_date) and
					 coalesce(@inqEndDate,a.order_date)
			 * */
			
			
			String wODataPlannedQry = "SELECT ppm.PRODUCTION_PLAN_MST_ID,ppm.PLAN_DATE,wc.WO_CHD_ID,wm.WO_MST_ID,wm.WO_DATE,ppm.ORDER_ITEM_QTY_ID,oo.owner_name,\r\n" + 
					"wc.ITEM_QTY,i.item_name,u.uom_name,wc.ACTIVE_STATUS,ppm.PLANNED_BY,o.order_id \r\n" + 
					"FROM mlfm_production_plan_mst ppm\r\n" + 
					"INNER JOIN mlfm_production_plan_chd ppc ON ppc.PRODUCTION_PLAN_MST_ID=ppm.PRODUCTION_PLAN_MST_ID\r\n" + 
					"INNER JOIN mlfm_wo_chd wc ON wc.WO_CHD_ID=ppm.WO_CHD_ID\r\n" + 
					"INNER JOIN mlfm_wo_mst wm on wm.WO_MST_ID=wc.WO_MST_ID\r\n" + 
					"INNER join mlfm_order_owner oo ON oo.order_owner_id=wm.ORDER_OWNER_ID\r\n" + 
					"INNER JOIN mlfm_order_owner_type oot ON oot.order_owner_type_id=oo.order_owner_type_id " +
					"INNER JOIN mlfm_order_item_qty oiq ON oiq.order_item_qty_id=wc.ORDER_ITEM_QTY_ID\r\n" + 
					"INNER JOIN mlfm_order_item oi on oi.item_order_id=oiq.order_item_id\r\n" +
					" INNER JOIN mlfm_order o ON o.order_id=oi.order_id " +
					"INNER JOIN mlfm_item i on i.item_id=oi.item_id\r\n" + 
					"INNER JOIN bg_user bu on bu.user_id=ppm.PLANNED_BY\r\n" + 
					"LEFT OUTER JOIN mlfm_uom u ON u.uom_id=oiq.uom_id\r\n" + 
					"AND ppm.ACTIVE_STATUS=1 AND ppc.ACTIVE_STATUS=1\r\n" + 
					"WHERE oo.order_owner_id=coalesce(:initialBuyerId,oo.order_owner_id) AND\r\n" + 
					"oo.order_owner_id = coalesce(:ultimateBuyerId,oo.order_owner_id) AND\r\n" + 
					" oot.order_owner_type_id=COALESCE(:orderOwnerTypeId,oot.order_owner_type_id) AND " +
					"wm.WO_MST_ID=coalesce(:wOMstId,wm.WO_MST_ID) AND\r\n" + 
					"i.item_id=coalesce(:itemId,i.item_id) AND\r\n" + 
					"bu.user_id=COALESCE(:userId,bu.user_id) AND\r\n" + 
					"ppm.PLAN_DATE BETWEEN COALESCE (:planStartDate,ppm.PLAN_DATE) AND COALESCE(:planEndDate,ppm.PLAN_DATE)";
			
			
			/*
			 * 
			 * sql Qry
			 * 
			 *SELECT ppm.PRODUCTION_PLAN_MST_ID,ppm.PLAN_DATE,wc.WO_CHD_ID,wm.WO_MST_ID,wm.WO_DATE,ppm.ORDER_ITEM_QTY_ID,oo.owner_name,
wc.ITEM_QTY,i.item_name,u.uom_name,wc.ACTIVE_STATUS,ppm.PLANNED_BY,o.order_id
FROM mlfm_production_plan_mst ppm
INNER JOIN mlfm_production_plan_chd ppc ON ppc.PRODUCTION_PLAN_MST_ID=ppm.PRODUCTION_PLAN_MST_ID
INNER JOIN mlfm_wo_chd wc ON wc.WO_CHD_ID=ppm.WO_CHD_ID
INNER JOIN mlfm_wo_mst wm on wm.WO_MST_ID=wc.WO_MST_ID
INNER join mlfm_order_owner oo ON oo.order_owner_id=wm.ORDER_OWNER_ID
INNER JOIN mlfm_order_owner_type oot ON oot.order_owner_type_id=oo.order_owner_type_id
INNER JOIN mlfm_order_item_qty oiq ON oiq.order_item_qty_id=wc.ORDER_ITEM_QTY_ID
INNER JOIN mlfm_order_item oi on oi.item_order_id=oiq.order_item_id
INNER JOIN mlfm_order o ON o.order_id=oi.order_id
INNER JOIN mlfm_item i on i.item_id=oi.item_id
INNER JOIN bg_user bu on bu.user_id=ppm.PLANNED_BY
LEFT OUTER JOIN mlfm_uom u ON u.uom_id=oiq.uom_id
AND ppm.ACTIVE_STATUS=1 AND ppc.ACTIVE_STATUS=1
WHERE oo.order_owner_id=coalesce(@orderOwnerId,oo.order_owner_id) AND
oo.order_owner_id = coalesce(@ultimateOwnerId,oo.order_owner_id) AND
oot.order_owner_type_id=COALESCE(@orderOwnerTypeId,oot.order_owner_type_id) AND
wm.WO_MST_ID=coalesce(@wOMstId,wm.WO_MST_ID) AND
i.item_id=coalesce(@itemId,i.item_id) AND
bu.user_id=COALESCE(@plannedBy,bu.user_id) AND
ppm.PLAN_DATE BETWEEN COALESCE (@wOStartDate,ppm.PLAN_DATE) AND COALESCE(@wOEndDate,ppm.PLAN_DATE)
			 * 
			 * */
			
				RowMapper<ModelInquiryList> serviceMapper=new RowMapper<ModelInquiryList>() {
					@Override
					public ModelInquiryList mapRow(ResultSet rs, int rownum) throws SQLException{
						ModelInquiryList bn=new ModelInquiryList();
						
					bn.setProductionPlanMstId(rs.getLong("PRODUCTION_PLAN_MST_ID"));
					bn.setwOMstId(rs.getLong("WO_MST_ID"));
				    bn.setwODate(rs.getDate("WO_DATE"));
				    bn.setOrderItemQuantityId(rs.getLong("ORDER_ITEM_QTY_ID"));
				    bn.setOrderOwnerName(rs.getString("owner_name"));
				    bn.setItemName(rs.getString("item_name"));
				    bn.setwOQty(rs.getDouble("ITEM_QTY"));
				    bn.setUomName(rs.getString("uom_name"));
				    bn.setActiveStatus(rs.getInt("ACTIVE_STATUS"));
				    bn.setOrderId(rs.getLong("order_id"));
	
					return bn;
					
					}	
				};
				
				
				MapSqlParameterSource parameters = new MapSqlParameterSource();
				
				
				  parameters.addValue("orderOwnerTypeId", orderOwnerTypeId);
				  parameters.addValue("initialBuyerId", initialBuyerId);
				  parameters.addValue("ultimateBuyerId", ultimateBuyerId);
				  parameters.addValue("wOMstId", wOMstId);
				  parameters.addValue("itemId", itemId);
				  parameters.addValue("userId", userId);
			      parameters.addValue("planStartDate", planStartDate);
                  parameters.addValue("planEndDate", planEndDate);
			     
			    System.out.println("query for when select WO " + wODataPlannedQry);
				
				return  namedJdbcTemplate.query(wODataPlannedQry,parameters,serviceMapper);
			
	
		}
		
		

		@Override
		public List<ModelMachineScheduleData> getMachineSheduleList(Long machineTypeId, Long machineId,Long machineShiftId,Date startDate,Date endDate) {
			
			//productionPlanRepository.findMachineSheduleList(machine,machineShiftName, inquery_end_date, inquery_start_date);
			
			String  qry="select * from (select c.machine_type_id,c.machine_id, b.machine_shift_id, a.MACHINE_SCHEDULE_ID,\r\n" + 
					"a.machine_schedule_name,a.schedule_date,c.MACHINE_NAME,b.machine_shift_name,u.uom_name,a.ACTIVE_STATUS,s.SHIFT_NAME\r\n" + 
					"from mlfm_machine_schedule a \r\n" + 
					"inner join mlfm_machine_shift b on a.machine_shift_id=b.machine_shift_id\r\n" + 
					"INNER JOIN mlfm_shift s on s.SHIFT_ID=b.SHIFT_ID " +
					"inner join mlfm_machine c on c.MACHINE_ID=b.MACHINE_ID\r\n" + 
					"LEFT OUTER JOIN mlfm_uom u ON u.uom_id=c.capacity_uom\r\n" + 
					"where a.ACTIVE_STATUS=1) a where \r\n" + 
					"a.machine_type_id =coalesce(:machineTypeId,a.machine_type_id) \r\n" + 
					"and a.machine_id =coalesce(:machineId,a.machine_id) \r\n" + 
					"and a.machine_shift_id=coalesce(:machineShiftId,a.machine_shift_id) \r\n" + 
					"and a.schedule_date between coalesce(:startDate,a.schedule_date) and coalesce(:endDate,a.schedule_date)";
			/*
			 * sql query
			 * 
			 * select * from (select c.machine_type_id,c.machine_id, b.machine_shift_id, a.MACHINE_SCHEDULE_ID,
a.machine_schedule_name,a.schedule_date,c.MACHINE_NAME,b.machine_shift_name,u.uom_name,a.ACTIVE_STATUS,
s.SHIFT_NAME
from mlfm_machine_schedule a 
inner join mlfm_machine_shift b on a.machine_shift_id=b.machine_shift_id
INNER JOIN mlfm_shift s on s.SHIFT_ID=b.SHIFT_ID
inner join mlfm_machine c on c.MACHINE_ID=b.MACHINE_ID
LEFT OUTER JOIN mlfm_uom u ON u.uom_id=c.capacity_uom
where a.ACTIVE_STATUS=1) a where 
a.machine_type_id =coalesce(@machineTypeId,a.machine_type_id) 
and a.machine_id =coalesce(@machineId,a.machine_id) 
and a.machine_shift_id=coalesce(@machineShiftId,a.machine_shift_id) 
and a.schedule_date between coalesce(@startDate,a.schedule_date) and coalesce(@endDate,a.schedule_date)
			 * 
			 * */
			
			RowMapper<ModelMachineScheduleData> serviceMapper=new RowMapper<ModelMachineScheduleData>() {
				@Override
				public ModelMachineScheduleData mapRow(ResultSet rs, int rownum) throws SQLException{
					ModelMachineScheduleData bn=new ModelMachineScheduleData();
					
					
					bn.setMachineSheduleId(rs.getLong("MACHINE_SCHEDULE_ID"));
					bn.setMachineName(rs.getString("MACHINE_NAME"));
					bn.setMachineSheduleName(rs.getString("machine_schedule_name"));
					bn.setMachineShiftName(rs.getString("machine_shift_name"));
					bn.setScheduleDate(rs.getDate("schedule_date"));
					bn.setActiveStatus(rs.getInt("ACTIVE_STATUS"));
					bn.setMachineShiftId(rs.getLong("machine_shift_id"));
					bn.setUomName(rs.getString("uom_name"));
					bn.setShiftName(rs.getString("SHIFT_NAME"));
					bn.setMachineId(rs.getLong("machine_id"));
	
			    return bn;
				
				}	
			};
			
			MapSqlParameterSource parameters = new MapSqlParameterSource();
			
			  parameters.addValue("machineTypeId", machineTypeId);
			  parameters.addValue("machineId", machineId);
		      parameters.addValue("machineShiftId", machineShiftId);
              parameters.addValue("startDate", startDate);
		      parameters.addValue("endDate", endDate);
		      
		      
		      System.out.println("query for machine Schedule Search : " + qry);
		      
			// TODO Auto-generated method stub
			return  namedJdbcTemplate.query(qry,parameters,serviceMapper);
		}
		
		
		@Override
		public List<ModelMachineScheduleData> getMachineSheduleListUnchecked(Long machineTypeId, Long machineId,Long machineShiftId,Date startDate,Date endDate) {
			
			
			
			String  qry="select * from (select c.machine_type_id,c.machine_id, b.machine_shift_id, a.MACHINE_SCHEDULE_ID,\r\n" + 
					"a.machine_schedule_name,a.schedule_date,c.MACHINE_NAME,b.machine_shift_name,s.SHIFT_NAME\r\n" + 
					"from mlfm_machine_schedule a \r\n" + 
					"inner join mlfm_machine_shift b on a.machine_shift_id=b.machine_shift_id \r\n" + 
					"INNER JOIN mlfm_shift s ON s.SHIFT_ID=b.SHIFT_ID\r\n" + 
					"inner join mlfm_machine c on c.MACHINE_ID=b.MACHINE_ID where a.ACTIVE_STATUS=1) a \r\n" + 
					"where \r\n" + 
					"a.machine_type_id =coalesce(:machineTypeId,a.machine_type_id) \r\n" + 
					"and a.machine_id =coalesce(:machineId,a.machine_id) \r\n" + 
					"and a.machine_shift_id=coalesce(:machineShiftId,a.machine_shift_id) \r\n" + 
					"and a.schedule_date between coalesce(:startDate,a.schedule_date) and coalesce(:endDate,a.schedule_date)";
			/*
			 * sql query
			 * 
			 * select * from (select c.machine_type_id,c.machine_id, b.machine_shift_id, a.MACHINE_SCHEDULE_ID,
a.machine_schedule_name,a.schedule_date,c.MACHINE_NAME,b.machine_shift_name,s.SHIFT_NAME
from mlfm_machine_schedule a 
inner join mlfm_machine_shift b on a.machine_shift_id=b.machine_shift_id 
INNER JOIN mlfm_shift s ON s.SHIFT_ID=b.SHIFT_ID
inner join mlfm_machine c on c.MACHINE_ID=b.MACHINE_ID where a.ACTIVE_STATUS=1) a 
where 
a.machine_type_id =coalesce(@machineTypeId,a.machine_type_id) 
and a.machine_id =coalesce(@machineId,a.machine_id) 
and a.machine_shift_id=coalesce(@machineShiftId,a.machine_shift_id) 
and a.schedule_date between coalesce(@startDate,a.schedule_date) and coalesce(@endDate,a.schedule_date)
			 * 
			 * */
			RowMapper<ModelMachineScheduleData> serviceMapper=new RowMapper<ModelMachineScheduleData>() {
				@Override
				public ModelMachineScheduleData mapRow(ResultSet rs, int rownum) throws SQLException{
					ModelMachineScheduleData bn=new ModelMachineScheduleData();
					
					
					bn.setMachineSheduleId(rs.getLong("MACHINE_SCHEDULE_ID"));
					bn.setMachineName(rs.getString("MACHINE_NAME"));
					bn.setMachineSheduleName(rs.getString("machine_schedule_name"));
					bn.setMachineShiftName(rs.getString("machine_shift_name"));
					bn.setScheduleDate(rs.getDate("schedule_date"));
					bn.setShiftName(rs.getString("SHIFT_NAME"));
	
			    return bn;
				
				}	
			};
			
			MapSqlParameterSource parameters = new MapSqlParameterSource();
			
			  parameters.addValue("machineTypeId", machineTypeId);
			  parameters.addValue("machineId", machineId);
		      parameters.addValue("machineShiftId", machineShiftId);

		      parameters.addValue("startDate", startDate);
		      parameters.addValue("endDate", endDate);
		      
		      
		      System.out.println("query for machine Schedule Search : " + qry);
		      
			// TODO Auto-generated method stub
			return  namedJdbcTemplate.query(qry,parameters,serviceMapper);
		}
		
		
		

		@Override
		public Optional<ModelProductionPlanMst> findProductionplanMstById(Long id) {
			// TODO Auto-generated method stub
			return productionPlanRepository.findById(id);
		}

		@Override
		public List<ModelProductionCustom> getProductionPlanMstById(Long productionPlanMstId) {
			
			String  qry="SELECT ppm.PRODUCTION_PLAN_MST_ID,ppm.PLAN_DATE,bu.user_name,bu.user_id,ppm.REF_NO,ppm.REMARKS,ppm.ACTIVE_STATUS,oo.owner_name\r\n" + 
					"					FROM mlfm_production_plan_mst ppm\r\n" + 
					"					LEFT OUTER JOIN bg_user bu ON bu.user_id=ppm.PLANNED_BY\r\n" + 
					"                    LEFT OUTER JOIN mlfm_wo_chd wc ON wc.WO_CHD_ID=ppm.WO_CHD_ID\r\n" + 
					"                    LEFT OUTER JOIN mlfm_wo_mst wm on wm.WO_MST_ID=wc.WO_MST_ID\r\n" + 
					"                    LEFT OUTER JOIN mlfm_order_owner oo ON oo.order_owner_id=wm.ORDER_OWNER_ID\r\n" + 
					"					where ppm.PRODUCTION_PLAN_MST_ID=COALESCE(:productionPlanMstId,ppm.PRODUCTION_PLAN_MST_ID)";
			
			/*
			 *SELECT ppm.PRODUCTION_PLAN_MST_ID,ppm.PLAN_DATE,bu.user_name,bu.user_id,ppm.REF_NO,ppm.REMARKS,ppm.ACTIVE_STATUS,oo.owner_name
					FROM mlfm_production_plan_mst ppm
					LEFT OUTER JOIN bg_user bu ON bu.user_id=ppm.PLANNED_BY
                    LEFT OUTER JOIN mlfm_wo_chd wc ON wc.WO_CHD_ID=ppm.WO_CHD_ID
                    LEFT OUTER JOIN mlfm_wo_mst wm on wm.WO_MST_ID=wc.WO_MST_ID
                    LEFT OUTER JOIN mlfm_order_owner oo ON oo.order_owner_id=wm.ORDER_OWNER_ID
					where ppm.PRODUCTION_PLAN_MST_ID=COALESCE(@productionPlanMstId,ppm.PRODUCTION_PLAN_MST_ID)
			 * 
			 * */
		
		RowMapper<ModelProductionCustom> serviceMapper=new RowMapper<ModelProductionCustom>() {
			@Override
			public ModelProductionCustom mapRow(ResultSet rs, int rownum) throws SQLException{
				ModelProductionCustom bn=new ModelProductionCustom();
				
				bn.setProductionPlanMstId(rs.getLong("PRODUCTION_PLAN_MST_ID"));
				bn.setPlanDate(rs.getDate("PLAN_DATE"));
				bn.setUserName(rs.getString("user_name"));
				bn.setRefNo(rs.getString("REF_NO"));
				bn.setRemarks(rs.getString("REMARKS"));
				bn.setActiveStatus(rs.getInt("ACTIVE_STATUS"));
				
				bn.setUserId(rs.getLong("user_id"));
				
				bn.setOwnerName(rs.getString("owner_name"));
//				bn.setMachineSheduleId(rs.getLong("MACHINE_SCHEDULE_ID"));
//				bn.setMachineName(rs.getString("MACHINE_NAME"));
//				bn.setMachineSheduleName(rs.getString("machine_schedule_name"));
//				bn.setMachineShiftName(rs.getString("machine_shift_name"));
//				bn.setScheduleDate(rs.getDate("schedule_date"));

		    return bn;
			
			}	
		};
		
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		
		  parameters.addValue("productionPlanMstId", productionPlanMstId);
		  
		 System.out.println("query : " + qry);
	      
		// TODO Auto-generated method stub
		return  namedJdbcTemplate.query(qry,parameters,serviceMapper);
			// TODO Auto-generated method stub
		//	return null;
		}

		@Override
		public List<ModelProductionCustom> getProductionPlanChdByMstId(Long productionPlanMstId) {
			
			String  qry="SELECT ppc.PRODUCTION_PLAN_MST_ID,ppc.PRODUCTION_PLAN_CHD_ID,ppc.PRODUCTION_PLAN_DATE,ppc.PRODUCTION_PLAN_QTY,\r\n" + 
					"ppc.REMARKS,ppc.ACTIVE_STATUS,m.MACHINE_NAME,s.SHIFT_NAME,u.uom_name\r\n" + 
					"FROM mlfm_production_plan_chd ppc\r\n" + 
					"INNER JOIN mlfm_machine_shift ms ON ms.MACHINE_SHIFT_ID=ppc.MACHINE_SHIFT_ID\r\n" + 
					"INNER JOIN mlfm_machine m ON m.MACHINE_ID=ms.MACHINE_ID\r\n" + 
					"INNER JOIN mlfm_shift s ON s.SHIFT_ID=ms.SHIFT_ID\r\n" + 
					"LEFT OUTER JOIN mlfm_uom u ON u.uom_id=m.capacity_uom\r\n" + 
					"WHERE ppc.PRODUCTION_PLAN_MST_ID=COALESCE(:productionPlanMstId,ppc.PRODUCTION_PLAN_MST_ID)";
			
			/*
			 * sql query
			 * 
			 * 
			 *SELECT ppc.PRODUCTION_PLAN_MST_ID,ppc.PRODUCTION_PLAN_CHD_ID,ppc.PRODUCTION_PLAN_DATE,ppc.PRODUCTION_PLAN_QTY,
ppc.REMARKS,ppc.ACTIVE_STATUS,m.MACHINE_NAME,s.SHIFT_NAME,u.uom_name
FROM mlfm_production_plan_chd ppc
INNER JOIN mlfm_machine_shift ms ON ms.MACHINE_SHIFT_ID=ppc.MACHINE_SHIFT_ID
INNER JOIN mlfm_machine m ON m.MACHINE_ID=ms.MACHINE_ID
INNER JOIN mlfm_shift s ON s.SHIFT_ID=ms.SHIFT_ID
LEFT OUTER JOIN mlfm_uom u ON u.uom_id=m.capacity_uom
WHERE ppc.PRODUCTION_PLAN_MST_ID=COALESCE(@productionPlanMstId,ppc.PRODUCTION_PLAN_MST_ID)
			 * 
			 * */
			
			RowMapper<ModelProductionCustom> serviceMapper=new RowMapper<ModelProductionCustom>() {
				@Override
				public ModelProductionCustom mapRow(ResultSet rs, int rownum) throws SQLException{
					ModelProductionCustom bn=new ModelProductionCustom();
					
					
					bn.setProductionPlanMstId(rs.getLong("PRODUCTION_PLAN_MST_ID"));
					bn.setProductionPlanChdId(rs.getLong("PRODUCTION_PLAN_CHD_ID"));
					bn.setPlanDate(rs.getDate("PRODUCTION_PLAN_DATE"));
					bn.setProductionPlanQty(rs.getDouble("PRODUCTION_PLAN_QTY"));
					bn.setMachineName(rs.getString("MACHINE_NAME"));
					bn.setShiftName(rs.getString("SHIFT_NAME"));
					bn.setUomName(rs.getString("uom_name"));
					bn.setActiveStatus(rs.getInt("ACTIVE_STATUS"));
					bn.setRemarks(rs.getString("REMARKS"));
					
					
					 System.out.println("Shift Name : " + rs.getString("SHIFT_NAME"));
					
//					bn.setProductionPlanMstId(rs.getLong("PRODUCTION_PLAN_MST_ID"));
//					bn.setPlanDate(rs.getDate("PLAN_DATE"));
//					bn.setUserName(rs.getString("user_name"));
//					bn.setRefNo(rs.getString("REF_NO"));
//					bn.setRemarks(rs.getString("REMARKS"));
//					bn.setActiveStatus(rs.getInt("ACTIVE_STATUS"));


			    return bn;
				
				}	
			};
			
			MapSqlParameterSource parameters = new MapSqlParameterSource();
			
			  parameters.addValue("productionPlanMstId", productionPlanMstId);
			  
			 System.out.println("query : " + qry);
		      
			
			return  namedJdbcTemplate.query(qry,parameters,serviceMapper);
			
			// TODO Auto-generated method stub
		//	return null;
		}

		@Override
		public List<ModelProductionCustom> getProductionPlanChdByChdId(Long productionPlanChdId) {
			
			
			String  qry="SELECT ppc.PRODUCTION_PLAN_MST_ID,ppc.PRODUCTION_PLAN_CHD_ID,ppc.PRODUCTION_PLAN_DATE,ppc.PRODUCTION_PLAN_QTY,\r\n" + 
					"ppc.REMARKS,ppc.ACTIVE_STATUS,m.MACHINE_NAME,s.SHIFT_NAME,u.uom_name,ms.MACHINE_SHIFT_NAME,s.SHIFT_NAME,\r\n" + 
					"ms.MACHINE_SHIFT_ID,mt.MACHINE_TYPE_NAME\r\n" + 
					"FROM mlfm_production_plan_chd ppc\r\n" + 
					"INNER JOIN mlfm_machine_shift ms ON ms.MACHINE_SHIFT_ID=ppc.MACHINE_SHIFT_ID\r\n" + 
					"INNER JOIN mlfm_machine m ON m.MACHINE_ID=ms.MACHINE_ID\r\n" + 
					"INNER JOIN mlfm_shift s ON s.SHIFT_ID=ms.SHIFT_ID\r\n" + 
					"INNER JOIN mlfm_machine_type mt ON mt.MACHINE_TYPE_ID=m.machine_type_id\r\n" + 
					"LEFT OUTER JOIN mlfm_uom u ON u.uom_id=m.capacity_uom\r\n" + 
					"WHERE ppc.PRODUCTION_PLAN_CHD_ID=COALESCE(:productionPlanChdId,ppc.PRODUCTION_PLAN_CHD_ID)";
			
			/*
			 * sql query
			 * 
			 * 
			 * SELECT ppc.PRODUCTION_PLAN_MST_ID,ppc.PRODUCTION_PLAN_CHD_ID,ppc.PRODUCTION_PLAN_DATE,ppc.PRODUCTION_PLAN_QTY,
ppc.REMARKS,ppc.ACTIVE_STATUS,m.MACHINE_NAME,s.SHIFT_NAME,u.uom_name,ms.MACHINE_SHIFT_NAME,s.SHIFT_NAME,
ms.MACHINE_SHIFT_ID,mt.MACHINE_TYPE_NAME
FROM mlfm_production_plan_chd ppc
INNER JOIN mlfm_machine_shift ms ON ms.MACHINE_SHIFT_ID=ppc.MACHINE_SHIFT_ID
INNER JOIN mlfm_machine m ON m.MACHINE_ID=ms.MACHINE_ID
INNER JOIN mlfm_shift s ON s.SHIFT_ID=ms.SHIFT_ID
INNER JOIN mlfm_machine_type mt ON mt.MACHINE_TYPE_ID=m.machine_type_id
LEFT OUTER JOIN mlfm_uom u ON u.uom_id=m.capacity_uom
WHERE ppc.PRODUCTION_PLAN_CHD_ID=COALESCE(@productionPlanChdId,ppc.PRODUCTION_PLAN_CHD_ID)
			 * 
			 * */
			
			
			RowMapper<ModelProductionCustom> serviceMapper=new RowMapper<ModelProductionCustom>() {
				@Override
				public ModelProductionCustom mapRow(ResultSet rs, int rownum) throws SQLException{
					ModelProductionCustom bn=new ModelProductionCustom();
					
					
					bn.setProductionPlanMstId(rs.getLong("PRODUCTION_PLAN_MST_ID"));
					bn.setProductionPlanChdId(rs.getLong("PRODUCTION_PLAN_CHD_ID"));
					bn.setPlanDate(rs.getDate("PRODUCTION_PLAN_DATE"));
					bn.setProductionPlanQty(rs.getDouble("PRODUCTION_PLAN_QTY"));
					bn.setMachineName(rs.getString("MACHINE_NAME"));
					bn.setShiftName(rs.getString("SHIFT_NAME"));
					bn.setUomName(rs.getString("uom_name"));
					bn.setActiveStatus(rs.getInt("ACTIVE_STATUS"));
					bn.setRemarks(rs.getString("REMARKS"));
					
                    bn.setMachineShiftId(rs.getLong("MACHINE_SHIFT_ID"));
                    bn.setMachineShiftName(rs.getString("MACHINE_SHIFT_NAME"));
                    
                    bn.setMachineTypeName(rs.getString("MACHINE_TYPE_NAME"));


			    return bn;
				
				}	
			};
			
			MapSqlParameterSource parameters = new MapSqlParameterSource();
			
			  parameters.addValue("productionPlanChdId", productionPlanChdId);
			  
			 System.out.println("query : " + qry);
		      
			
			return  namedJdbcTemplate.query(qry,parameters,serviceMapper);
			
			
			
			
			// TODO Auto-generated method stub
			//return null;
		}

		@Override
		public List<ModelProductionCustom> getProductionPlanChdDateByMstId(Long productionPlanMstId) {
			
			String  qry="SELECT ppc.PRODUCTION_PLAN_MST_ID,ppc.PRODUCTION_PLAN_CHD_ID,ppc.PRODUCTION_PLAN_DATE\r\n" + 
					"					FROM mlfm_production_plan_chd ppc\r\n" + 
					"                    INNER JOIN mlfm_production_plan_mst ppm ON ppm.PRODUCTION_PLAN_MST_ID=ppc.PRODUCTION_PLAN_MST_ID\r\n" + 
					"					where ppc.PRODUCTION_PLAN_MST_ID=COALESCE(:productionPlanMstId,ppc.PRODUCTION_PLAN_MST_ID)";
			
			/*
			 * sql query
			 * 
			 * 
			 * SELECT ppc.PRODUCTION_PLAN_MST_ID,ppc.PRODUCTION_PLAN_CHD_ID,ppc.PRODUCTION_PLAN_DATE
					FROM mlfm_production_plan_chd ppc
                    INNER JOIN mlfm_production_plan_mst ppm ON ppm.PRODUCTION_PLAN_MST_ID=ppc.PRODUCTION_PLAN_MST_ID
					where ppc.PRODUCTION_PLAN_MST_ID=COALESCE(20190202956,ppc.PRODUCTION_PLAN_MST_ID)
			 * 
			 * */
			
			
			RowMapper<ModelProductionCustom> serviceMapper=new RowMapper<ModelProductionCustom>() {
				@Override
				public ModelProductionCustom mapRow(ResultSet rs, int rownum) throws SQLException{
					ModelProductionCustom bn=new ModelProductionCustom();
					
					
					bn.setProductionPlanMstId(rs.getLong("PRODUCTION_PLAN_MST_ID"));
					bn.setPlanDate(rs.getDate("PRODUCTION_PLAN_DATE"));
					


			    return bn;
				
				}	
			};
			
			MapSqlParameterSource parameters = new MapSqlParameterSource();
			
			  parameters.addValue("productionPlanMstId", productionPlanMstId);
			  
			 System.out.println("query : " + qry);
		      
			
			return  namedJdbcTemplate.query(qry,parameters,serviceMapper);
		}

		@Override
		public List<ModelMachineScheduleData> getAvailableMachine(Long machineTypeId) {
			
			String  qry="SELECT ms.SCHEDULE_DATE,m.MACHINE_NAME,mt.MACHINE_TYPE_ID\r\n" + 
					"FROM mlfm_machine_schedule ms \r\n" + 
					"INNER join mlfm_machine_shift msh ON msh.MACHINE_SHIFT_ID=ms.MACHINE_SHIFT_ID\r\n" + 
					"INNER JOIN mlfm_machine m on m.MACHINE_ID=msh.MACHINE_ID\r\n" + 
					"INNER JOIN mlfm_machine_type mt on mt.MACHINE_TYPE_ID=m.machine_type_id\r\n" + 
					"WHERE mt.MACHINE_TYPE_ID=COALESCE(:machineTypeId,mt.MACHINE_TYPE_ID)";
			
			/*
			 * sql query
			 * 
			 * 
			 * SELECT ms.SCHEDULE_DATE,m.MACHINE_NAME,mt.MACHINE_TYPE_ID
FROM mlfm_machine_schedule ms 
INNER join mlfm_machine_shift msh ON msh.MACHINE_SHIFT_ID=ms.MACHINE_SHIFT_ID
INNER JOIN mlfm_machine m on m.MACHINE_ID=msh.MACHINE_ID
INNER JOIN mlfm_machine_type mt on mt.MACHINE_TYPE_ID=m.machine_type_id
WHERE mt.MACHINE_TYPE_ID=COALESCE(@machineTypeId,mt.MACHINE_TYPE_ID)
			 * 
			 * */
			
			
			RowMapper<ModelMachineScheduleData> serviceMapper=new RowMapper<ModelMachineScheduleData>() {
				@Override
				public ModelMachineScheduleData mapRow(ResultSet rs, int rownum) throws SQLException{
					ModelMachineScheduleData bn=new ModelMachineScheduleData();
					
					bn.setScheduleDate(rs.getDate("SCHEDULE_DATE"));
					bn.setMachineName(rs.getString("MACHINE_NAME"));
					//bn.setProductionPlanMstId(rs.getLong("PRODUCTION_PLAN_MST_ID"));
					//bn.setPlanDate(rs.getDate("PRODUCTION_PLAN_DATE"));
					


			    return bn;
				
				}	
			};
			
			MapSqlParameterSource parameters = new MapSqlParameterSource();
			
			  parameters.addValue("machineTypeId", machineTypeId);
			  
			 System.out.println("query : " + qry);
		      
			
			return  namedJdbcTemplate.query(qry,parameters,serviceMapper);
			
			
		}

		@Override
		public List<ModelProductionCustom> getInUsedProductionPlanMst(Long wOMstId) {
			
			String  qry="SELECT ppm.PRODUCTION_PLAN_MST_ID,ppm.PLAN_DATE,bu.user_name,bu.user_id,ppm.REF_NO,ppm.REMARKS,\r\n" + 
					"ppm.ACTIVE_STATUS,oo.owner_name\r\n" + 
					"FROM mlfm_production_plan_mst ppm \r\n" + 
					"LEFT OUTER JOIN bg_user bu ON bu.user_id=ppm.PLANNED_BY\r\n" + 
					"INNER JOIN mlfm_wo_chd wc ON wc.WO_CHD_ID=ppm.WO_CHD_ID \r\n" + 
					"INNER JOIN mlfm_wo_mst wm ON wm.WO_MST_ID=wc.WO_MST_ID\r\n" + 
					" LEFT OUTER JOIN mlfm_order_owner oo ON oo.order_owner_id=wm.ORDER_OWNER_ID\r\n" + 
					"WHERE wm.WO_MST_ID=COALESCE(:wOMstId,wm.WO_MST_ID)";
			
			/*
			 * sql query
			 * 
			 * 
			 * SELECT ppm.PRODUCTION_PLAN_MST_ID,ppm.PLAN_DATE,bu.user_name,bu.user_id,ppm.REF_NO,ppm.REMARKS,
ppm.ACTIVE_STATUS,oo.owner_name
FROM mlfm_production_plan_mst ppm 
LEFT OUTER JOIN bg_user bu ON bu.user_id=ppm.PLANNED_BY
INNER JOIN mlfm_wo_chd wc ON wc.WO_CHD_ID=ppm.WO_CHD_ID 
INNER JOIN mlfm_wo_mst wm ON wm.WO_MST_ID=wc.WO_MST_ID
 LEFT OUTER JOIN mlfm_order_owner oo ON oo.order_owner_id=wm.ORDER_OWNER_ID
WHERE wm.WO_MST_ID=COALESCE(@wOMstId,wm.WO_MST_ID)
			 * 
			 * */
			
			
			RowMapper<ModelProductionCustom> serviceMapper=new RowMapper<ModelProductionCustom>() {
				@Override
				public ModelProductionCustom mapRow(ResultSet rs, int rownum) throws SQLException{
					ModelProductionCustom bn=new ModelProductionCustom();
					
				
					bn.setProductionPlanMstId(rs.getLong("PRODUCTION_PLAN_MST_ID"));
					bn.setPlanDate(rs.getDate("PLAN_DATE"));
					bn.setUserName(rs.getString("user_name"));
					bn.setRefNo(rs.getString("REF_NO"));
					bn.setRemarks(rs.getString("REMARKS"));
					bn.setActiveStatus(rs.getInt("ACTIVE_STATUS"));
					bn.setUserId(rs.getLong("user_id"));
					bn.setOwnerName(rs.getString("owner_name"));
					
                return bn;
				
				}	
			};
			
			MapSqlParameterSource parameters = new MapSqlParameterSource();
			
			  parameters.addValue("wOMstId", wOMstId);
			  
			 System.out.println("query : " + qry);
		      
			
			return  namedJdbcTemplate.query(qry,parameters,serviceMapper);
			
			
			
			// TODO Auto-generated method stub
		//	return null;
		}
		
		
//		@Override
//		public List<ModelMachineScheduleData>getProductionPlanChdList(){
//		String  qry="select c.machine_type_id,c.machine_id, b.machine_shift_id, a.MACHINE_SCHEDULE_ID," + 
//			    " a.machine_schedule_name,a.schedule_date,c.MACHINE_NAME,b.machine_shift_name from mlfm_machine_schedule a " +
//	            " inner join mlfm_machine_shift b on a.machine_shift_id=b.machine_shift_id inner join mlfm_machine c on c.MACHINE_ID=b.MACHINE_ID" + 
//	            " where a.ACTIVE_STATUS=1";
//		/*
//		 *qry for SQL
//		 *
//		 *select c.machine_type_id,c.machine_id, b.machine_shift_id, a.MACHINE_SCHEDULE_ID,
//			a.machine_schedule_name,a.schedule_date,c.MACHINE_NAME,b.machine_shift_name from mlfm_machine_schedule a 
//		     inner join mlfm_machine_shift b on a.machine_shift_id=b.machine_shift_id inner join mlfm_machine c on c.MACHINE_ID=b.MACHINE_ID
//		     where a.ACTIVE_STATUS=1 
//		 * 
//		 * 
//		 */
//		RowMapper<ModelMachineScheduleData> serviceMapper=new RowMapper<ModelMachineScheduleData>() {
//			@Override
//			public ModelMachineScheduleData mapRow(ResultSet rs, int rownum) throws SQLException{
//				ModelMachineScheduleData bn=new ModelMachineScheduleData();
//				bn.setMachineSheduleId(rs.getLong("MACHINE_SCHEDULE_ID"));
//				bn.setMachineName(rs.getString("MACHINE_NAME"));
//				bn.setMachineSheduleName(rs.getString("machine_schedule_name"));
//				bn.setMachineShiftName(rs.getString("machine_shift_name"));
//				bn.setScheduleDate(rs.getDate("schedule_date"));
//				bn.setMachineShiftId(rs.getLong("machine_shift_id"));
//		    return bn;
//			}	
//		};
//		MapSqlParameterSource parameters = new MapSqlParameterSource();
//		System.out.println("query for productioon Plan Chd List : " + qry);

//		return  namedJdbcTemplate.query(qry,parameters,serviceMapper);
//		}

	
	}






	

