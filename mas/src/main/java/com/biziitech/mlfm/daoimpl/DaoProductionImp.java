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
import com.biziitech.mlfm.custom.model.ModelProductionCustom;
import com.biziitech.mlfm.dao.DaoProduction;
import com.biziitech.mlfm.model.ModelProduction;
import com.biziitech.mlfm.repository.ProductionRepository;


@Service
public class DaoProductionImp implements DaoProduction{
	
	@Autowired
	private ProductionRepository productionRepository;
	
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
	public List<ModelInquiryList> getOrderListPlanned(String buyerId, String ultimateBuyerId, Long inqueryId,
			Date inqFromDate, Date inqToDate) {
		
		// the following query shows records which have production plan but not produced fully yet.
		
String qry_inquiry="SELECT" + 
				" * " + 
				"FROM" + 
				"    (" + 
				"    SELECT" + 
				"        a.order_id," + 
				"        a.order_date," + 
				"        b.item_order_id," + 
				"        b.item_id," + 
				"        d.item_name," + 
				"        c.order_item_qty_id," + 
				"        c.item_qty," + 
				"        c.uom_id," + 
				"        e.uom_name," + 
				"        c.design_id," + 
				"        a.order_owner_id," + 
				"        f.owner_name" + 
				"    FROM" + 
				"        mlfm_order a" + 
				"    INNER JOIN mlfm_order_item b ON" + 
				"        a.order_id = b.order_id" + 
				"    INNER JOIN mlfm_order_item_qty c ON" + 
				"        b.item_order_id = c.order_item_id" + 
				"    INNER JOIN mlfm_item d ON" + 
				"        b.item_id = d.item_id" + 
				"    LEFT OUTER JOIN mlfm_uom e ON" + 
				"        c.uom_id = e.uom_id\r\n" + 
				"    INNER JOIN mlfm_order_owner f ON" + 
				"        a.order_owner_id = f.order_owner_id AND a.active_status = 1 AND b.active_status = 1 AND c.active_status = 1 " + 
				"        " + 
				"        AND NOT EXISTS(" + 
				"        SELECT" + 
				"            1" + 
				"        FROM\r\n" + 
				"            mlfm_production_plan_chd pc," + 
				"            mlfm_production_plan_mst pm" + 
				"        WHERE" + 
				"            pc.PRODUCTION_PLAN_MST_ID = pm.PRODUCTION_PLAN_MST_ID AND pm.ORDER_ITEM_QTY_ID = c.order_item_qty_id AND pc.active_status = 1" + 
				"        HAVING" + 
				"            SUM(\r\n" + 
				"                COALESCE(pc.production_plan_qty, 0)" + 
				"            ) <=(" + 
				"            SELECT" + 
				"                SUM(COALESCE(pr.production_qty, 0))" + 
				"            FROM\r\n" + 
				"                mlfm_production pr, mlfm_production_plan_chd pc" + 
				"            WHERE\r\n" + 
				"                pr.production_plan_chd_id = pc.production_plan_chd_id" + 
				"        )" + 
				"    )" + 
				") a" +
				" where a.owner_name like concat('%',:buyerId,'%') and" +
			    " a.owner_name like concat('%',:ultimateBuyerId,'%') and" +
			    " a.order_id= coalesce(:inqueryId,a.order_id) and" +
				" a.order_date between coalesce(:inqFromDate,a.order_date) and" + 
			    " coalesce(:inqToDate,a.order_date) ";
		/*SQL excute Qry
		 * 
		 * 
		 * SELECT
				 *  
				FROM
				    ( 
				    SELECT
				        a.order_id,
				       a.order_date,
				        b.item_order_id,
				        b.item_id,
				        d.item_name,
				        c.order_item_qty_id,
				        c.item_qty,
				        c.uom_id,
				        e.uom_name,
				        c.design_id,
				        a.order_owner_id,
				        f.owner_name
				    FROM
				        mlfm_order a
				    INNER JOIN mlfm_order_item b ON
				        a.order_id = b.order_id
				    INNER JOIN mlfm_order_item_qty c ON
				        b.item_order_id = c.order_item_id
				    INNER JOIN mlfm_item d ON
				        b.item_id = d.item_id 
				   LEFT OUTER JOIN mlfm_uom e ON
				        c.uom_id = e.uom_id
			   INNER JOIN mlfm_order_owner f ON
				       a.order_owner_id = f.order_owner_id AND a.active_status = 1 AND b.active_status = 1 AND c.active_status = 1 
				        
				        AND NOT EXISTS( 
				        SELECT
				            1
				        FROM
				            mlfm_production_plan_chd pc, 
				            mlfm_production_plan_mst pm
				        WHERE
				            pc.PRODUCTION_PLAN_MST_ID = pm.PRODUCTION_PLAN_MST_ID AND pm.ORDER_ITEM_QTY_ID = c.order_item_qty_id AND pc.active_status = 1 
				        HAVING
				            SUM(
				                COALESCE(pc.production_plan_qty, 0) 
				            ) <=(
				            SELECT
				                SUM(COALESCE(pr.production_qty, 0)) 
			            FROM
				                mlfm_production pr, mlfm_production_plan_chd pc
				            WHERE
				                pr.production_plan_chd_id = pc.production_plan_chd_id
				        )
				    )
				) a
				 where a.owner_name like concat('%',@buyerId,'%') and
			     a.owner_name like concat('%',@ultimateBuyerId,'%') and
			     a.order_id= coalesce(@inqueryId,a.order_id) and
				 a.order_date between coalesce(@inqFromDate,a.order_date) and
			     coalesce(@inqToDate,a.order_date)
		 * 
		 * 
		 * */
		
		
		RowMapper<ModelInquiryList> serviceMapper=new RowMapper<ModelInquiryList>() {
			@Override
			public ModelInquiryList mapRow(ResultSet rs, int rownum) throws SQLException{
				ModelInquiryList bn=new ModelInquiryList();
		
			bn.setOrderId(rs.getLong("order_id"));
			bn.setOrderDate(rs.getDate("order_date"));
			bn.setOrderItemQuantityId(rs.getLong("order_item_qty_id"));
			//bn.setOrderOwnerName(rs.getString("owner_name"));
			bn.setItemName(rs.getString("item_name"));
			bn.setItemQty(rs.getDouble("item_qty"));
			//bn.setUomName(rs.getString("uom_name"));
			
			//bn.setOrderOwnerId(rs.getLong("order_owner_id"));
			return bn;
			
			}	
		};
		
		
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		
		  parameters.addValue("buyerId", buyerId);
		  parameters.addValue("ultimateBuyerId", ultimateBuyerId);
		  parameters.addValue("inqueryId", inqueryId);
	      parameters.addValue("inqFromDate", inqFromDate);
          parameters.addValue("inqToDate", inqToDate);
	     
	    System.out.println("query for when select Inquiry : " + qry_inquiry);
		
		return  namedJdbcTemplate.query(qry_inquiry,parameters,serviceMapper);
		
		
		// TODO Auto-generated method stub
		//return null;
	}
	
	
	
	
	@Override
	public List<ModelInquiryList> getOrderListNotPlanned(String buyerId, String ultimateBuyerId, Long inqueryId,
			Date inqFromDate, Date inqToDate) {
		/*
		 * mlfm_order ... a
		 * mlfm_order_item_qty c
		 * 
		 * 
		 * */
		String qry_inquiry="SELECT" + 
				" * " + 
				"FROM" + 
				"    (" + 
				"    SELECT" + 
				"        a.order_id," + 
				"        a.order_date," + 
				"        b.item_order_id," + 
				"        b.item_id," + 
				"        d.item_name," + 
				"        c.order_item_qty_id," + 
				"        c.item_qty," + 
				"        c.uom_id," + 
				"        e.uom_name," + 
				"        c.design_id," + 
				"        a.order_owner_id," + 
				"        f.owner_name" + 
				"    FROM" + 
				"        mlfm_order a" + 
				"    INNER JOIN mlfm_order_item b ON" + 
				"        a.order_id = b.order_id" + 
				"    INNER JOIN mlfm_order_item_qty c ON" + 
				"        b.item_order_id = c.order_item_id" + 
				"    INNER JOIN mlfm_item d ON" + 
				"        b.item_id = d.item_id" + 
				"    LEFT OUTER JOIN mlfm_uom e ON" + 
				"        c.uom_id = e.uom_id\r\n" + 
				"    INNER JOIN mlfm_order_owner f ON" + 
				"        a.order_owner_id = f.order_owner_id AND a.active_status = 1 AND b.active_status = 1 AND c.active_status = 1 " + 
				"        " + 
				"        AND NOT EXISTS(" + 
				"        SELECT" + 
				"            1" + 
				"        FROM\r\n" + 
				"            mlfm_production_plan_chd pc," + 
				"            mlfm_production_plan_mst pm" + 
				"        WHERE" + 
				"            pc.PRODUCTION_PLAN_MST_ID = pm.PRODUCTION_PLAN_MST_ID AND pm.ORDER_ITEM_QTY_ID = c.order_item_qty_id AND pc.active_status = 1" + 
				"        HAVING" + 
				"            SUM(\r\n" + 
				"                COALESCE(pc.production_plan_qty, 0)" + 
				"            ) <=(" + 
				"            SELECT" + 
				"                SUM(COALESCE(pr.production_qty, 0))" + 
				"            FROM\r\n" + 
				"                mlfm_production pr, mlfm_production_plan_chd pc" + 
				"            WHERE\r\n" + 
				"                pr.production_plan_chd_id = pc.production_plan_chd_id" + 
				"        )" + 
				"    )" + 
				") a" +
				" where a.owner_name like concat('%',:buyerId,'%') and" +
			    " a.owner_name like concat('%',:ultimateBuyerId,'%') and" +
			    " a.order_id= coalesce(:inqueryId,a.order_id) and" +
				" a.order_date between coalesce(:inqFromDate,a.order_date) and" + 
			    " coalesce(:inqToDate,a.order_date) ";
		
		
		/*
		String  qry_inquiry=" select * from (  select a.order_id, a.order_date,b.item_order_id,b.item_id,d.item_name,c.order_item_qty_id,c.item_qty,c.uom_id,e.uom_name,c.design_id,"+
		        " a.order_owner_id,f.owner_name from mlfm_order a inner join mlfm_order_item b on a.order_id=b.order_id " +
				" inner join mlfm_order_item_qty c on b.item_order_id=c.order_item_id inner join mlfm_item d on b.item_id=d.item_id " +
		        " left outer join mlfm_uom e on c.uom_id=e.uom_id inner join mlfm_order_owner f on a.order_owner_id=f.order_owner_id " +
		        "and a.active_status=1 " +
		        "and b.active_status=1 " +
		        "and c.active_status=1 " +
		        "and not exists (SELECT 1 FROM mlfm_production_plan_mst p where p.ORDER_ITEM_QTY_ID=c.order_item_qty_id) ) a" + 
				" where a.owner_name like concat('%',:buyerId,'%') and" +
			    " a.owner_name like concat('%',:ultimateBuyerId,'%') and" +
			    " a.order_id= coalesce(:inqueryId,a.order_id) and" +
				" a.order_date between coalesce(:planStartDate,a.order_date) and" + 
			    " coalesce(:planEndDate,a.order_date) ";
		*/
		
		RowMapper<ModelInquiryList> serviceMapper=new RowMapper<ModelInquiryList>() {
			@Override
			public ModelInquiryList mapRow(ResultSet rs, int rownum) throws SQLException{
				ModelInquiryList bn=new ModelInquiryList();
		
			bn.setOrderId(rs.getLong("order_id"));
			bn.setOrderDate(rs.getDate("order_date"));
			bn.setOrderItemQuantityId(rs.getLong("order_item_qty_id"));
			//bn.setOrderOwnerName(rs.getString("owner_name"));
			bn.setItemName(rs.getString("item_name"));
			bn.setItemQty(rs.getDouble("item_qty"));
			//bn.setUomName(rs.getString("uom_name"));
			
			//bn.setOrderOwnerId(rs.getLong("order_owner_id"));
			
		 
			return bn;
			
			}	
		};
		
		
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		
		  parameters.addValue("buyerId", buyerId);
		  parameters.addValue("ultimateBuyerId", ultimateBuyerId);
		  parameters.addValue("inqueryId", inqueryId);
	      parameters.addValue("inqFromDate", inqFromDate);
          parameters.addValue("inqToDate", inqToDate);
	     
	    System.out.println("query for when select Inquiry : " + qry_inquiry);
		
		return  namedJdbcTemplate.query(qry_inquiry,parameters,serviceMapper);
		
		
		// TODO Auto-generated method stub
		//return null;
	}
	
	
	@Override
	public List<ModelProductionCustom> getSearchListPending(Long orderOwnerId, Long wOId,
			Date pOStartDate, Date pOEndDate) {
		
	 /*
		
		String qry_WO="SELECT p.PO_ID,d.NO_OF_STITCHES,p.PO_DATE,oo.owner_name,p.DESIGN_ID,i.item_name,p.DTM,p.PO_QTY,p.REMARKS," + 
			"p.ACTIVE_STATUS,mt.MACHINE_TYPE_NAME,oiq.order_item_qty_id,ppc.PRODUCTION_PLAN_CHD_ID,\r\n" + 
			"wc.WO_MST_ID,wm.ORDER_OWNER_ID\r\n" + 
			"FROM mlfm_po p\r\n" + 
			"INNER JOIN mlfm_machine_type mt ON mt.MACHINE_TYPE_ID=p.MACHINE_TYPE_ID\r\n" + 
			"INNER JOIN mlfm_production_plan_chd ppc ON ppc.PRODUCTION_PLAN_CHD_ID=p.PRODUCTION_PLAN_CHD_ID\r\n" + 
			"INNER join mlfm_production_plan_mst ppm ON ppm.PRODUCTION_PLAN_MST_ID = ppc.PRODUCTION_PLAN_MST_ID\r\n" + 
			"INNER JOIN mlfm_wo_chd wc ON wc.WO_CHD_ID=ppm.WO_CHD_ID\r\n" + 
			"INNER JOIN mlfm_wo_mst wm ON wm.WO_MST_ID=wc.WO_MST_ID\r\n" + 
			"INNER JOIN mlfm_order_owner oo ON oo.order_owner_id=wm.ORDER_OWNER_ID\r\n" + 
			"INNER join mlfm_order_item_qty oiq ON oiq.order_item_qty_id=ppm.ORDER_ITEM_QTY_ID\r\n" + 
			"INNER JOIN mlfm_order_item oi ON oi.item_order_id=oiq.order_item_id\r\n" + 
			"INNER JOIN mlfm_item i ON i.item_id=oi.item_id\r\n" +
			"INNER JOIN mlfm_design d ON d.design_id=p.DESIGN_ID " +
			"AND p.ACTIVE_STATUS=1 AND ppc.ACTIVE_STATUS=1 AND wm.ACTIVE_STATUS=1 AND oiq.active_status=1\r\n" + 
			"AND NOT EXISTS (SELECT 1 FROM mlfm_production pro WHERE pro.PO_ID=p.PO_ID)\r\n" + 
			"where oo.order_owner_id=coalesce(:orderOwnerId,oo.order_owner_id)\r\n" + 
			"AND wm.WO_MST_ID= coalesce(:wOId,wm.WO_MST_ID) \r\n" + 
			"AND p.PO_DATE between coalesce(:pOStartDate,p.PO_DATE) and\r\n" + 
			"			     coalesce(:pOEndDate,p.PO_DATE)";
			
			*/
		
		
		
		
		/*
		 * sql Qry
		 * 
		 *SELECT p.PO_ID,p.PO_DATE,oo.owner_name,p.DESIGN_ID,i.item_name,p.DTM,p.PO_QTY,p.REMARKS,p.ACTIVE_STATUS,
mt.MACHINE_TYPE_NAME,d.NO_OF_STITCHES,
wc.WO_MST_ID,wm.ORDER_OWNER_ID
FROM mlfm_po p
INNER JOIN mlfm_machine_type mt ON mt.MACHINE_TYPE_ID=p.MACHINE_TYPE_ID
INNER JOIN mlfm_production_plan_chd ppc ON ppc.PRODUCTION_PLAN_CHD_ID=p.PRODUCTION_PLAN_CHD_ID
INNER join mlfm_production_plan_mst ppm ON ppm.PRODUCTION_PLAN_MST_ID = ppc.PRODUCTION_PLAN_MST_ID
INNER JOIN mlfm_wo_chd wc ON wc.WO_CHD_ID=ppm.WO_CHD_ID
INNER JOIN mlfm_wo_mst wm ON wm.WO_MST_ID=wc.WO_MST_ID
INNER JOIN mlfm_order_owner oo ON oo.order_owner_id=wm.ORDER_OWNER_ID
INNER join mlfm_order_item_qty oiq ON oiq.order_item_qty_id=ppm.ORDER_ITEM_QTY_ID
INNER JOIN mlfm_order_item oi ON oi.item_order_id=oiq.order_item_id
INNER JOIN mlfm_item i ON i.item_id=oi.item_id
INNER JOIN mlfm_design d ON d.design_id=p.DESIGN_ID
AND p.ACTIVE_STATUS=1 AND ppc.ACTIVE_STATUS=1 AND wm.ACTIVE_STATUS=1 AND oiq.active_status=1
AND NOT EXISTS (SELECT 1 FROM mlfm_production pro WHERE pro.PO_ID=p.PO_ID)
where oo.order_owner_id=coalesce(@orderOwnerId,oo.order_owner_id)
AND wm.WO_MST_ID= coalesce(@wOId,wm.WO_MST_ID) 
AND p.PO_DATE between coalesce(@pOStartDate,p.PO_DATE) and
			     coalesce(@pOEndDate,p.PO_DATE)
         * 
		 * */
		
		
		
		
	/*	
	String qry_WO="SELECT p.PO_MST_ID,p.PO_MST_DATE,mt.MACHINE_TYPE_NAME,d.design_id,pc.PO_QTY,oo.owner_name,f.FABRIC_TYPE_NAME,d.NO_OF_STITCHES,d.DTM,i.item_name,p.ACTIVE_STATUS,p.REMARKS,pc.MACHINE_SHIFT_ID,\r\n" + 
			"ppc.PRODUCTION_PLAN_CHD_ID,oiq.order_item_qty_id,wc.WO_CHD_ID,mt.MACHINE_TYPE_ID\r\n" + 
			"FROM mlfm_po_mst p\r\n" + 
			"INNER JOIN mlfm_po_chd pc ON pc.PO_MST_ID=p.PO_MST_ID\r\n" + 
			"INNER JOIN mlfm_machine_type mt ON mt.MACHINE_TYPE_ID=pc.MACHINE_TYPE_ID\r\n" + 
			"INNER JOIN mlfm_production_plan_chd ppc ON ppc.PRODUCTION_PLAN_CHD_ID=pc.PRODUCTION_PLAN_CHD_ID\r\n" + 
			"INNER join mlfm_production_plan_mst ppm ON ppm.PRODUCTION_PLAN_MST_ID = ppc.PRODUCTION_PLAN_MST_ID\r\n" + 
			"INNER JOIN mlfm_wo_chd wc ON wc.WO_CHD_ID=ppm.WO_CHD_ID\r\n" + 
			"INNER JOIN mlfm_wo_mst wm ON wm.WO_MST_ID=wc.WO_MST_ID\r\n" + 
			"INNER JOIN mlfm_order_owner oo ON oo.order_owner_id=wm.ORDER_OWNER_ID\r\n" + 
			"INNER join mlfm_order_item_qty oiq ON oiq.order_item_qty_id=ppm.ORDER_ITEM_QTY_ID\r\n" + 
			"INNER JOIN mlfm_order_item oi ON oi.item_order_id=oiq.order_item_id\r\n" + 
			"INNER JOIN mlfm_item i ON i.item_id=oi.item_id\r\n" + 
			"INNER JOIN mlfm_design d ON d.design_id=p.DESIGN_ID\r\n" + 
			"INNER JOIN mlfm_fabric_type f ON f.FABRIC_TYPE_ID = d.FABRIC_TYPE_ID\r\n" + 
			"AND p.ACTIVE_STATUS=1 AND ppc.ACTIVE_STATUS=1 AND wm.ACTIVE_STATUS=1 AND oiq.active_status=1\r\n" + 
			//"AND NOT EXISTS (SELECT 1 FROM mlfm_production pro WHERE pro.PO_MST_ID=p.PO_MST_ID)\r\n" + 
			"where oo.order_owner_id=coalesce(:orderOwnerId,oo.order_owner_id)\r\n" + 
			"AND wm.WO_MST_ID= coalesce(:wOId,wm.WO_MST_ID) \r\n" + 
			"AND p.PO_MST_DATE between coalesce(:pOStartDate,p.PO_MST_DATE) and coalesce(:pOEndDate,p.PO_MST_DATE)";
			
	*/
	
	/* use PO_Mst 
	 * 
	 *SELECT p.PO_MST_ID,p.PO_MST_DATE,mt.MACHINE_TYPE_NAME,d.design_id,pc.PO_QTY,oo.owner_name,f.FABRIC_TYPE_NAME,d.NO_OF_STITCHES,d.DTM,i.item_name,p.ACTIVE_STATUS,p.REMARKS,pc.MACHINE_SHIFT_ID,
ppc.PRODUCTION_PLAN_CHD_ID,oiq.order_item_qty_id,wc.WO_CHD_ID,mt.MACHINE_TYPE_ID
FROM mlfm_po_mst p
INNER JOIN mlfm_po_chd pc ON pc.PO_MST_ID=p.PO_MST_ID
INNER JOIN mlfm_machine_type mt ON mt.MACHINE_TYPE_ID=pc.MACHINE_TYPE_ID
INNER JOIN mlfm_production_plan_chd ppc ON ppc.PRODUCTION_PLAN_CHD_ID=pc.PRODUCTION_PLAN_CHD_ID
INNER join mlfm_production_plan_mst ppm ON ppm.PRODUCTION_PLAN_MST_ID = ppc.PRODUCTION_PLAN_MST_ID
INNER JOIN mlfm_wo_chd wc ON wc.WO_CHD_ID=ppm.WO_CHD_ID
INNER JOIN mlfm_wo_mst wm ON wm.WO_MST_ID=wc.WO_MST_ID
INNER JOIN mlfm_order_owner oo ON oo.order_owner_id=wm.ORDER_OWNER_ID
INNER join mlfm_order_item_qty oiq ON oiq.order_item_qty_id=ppm.ORDER_ITEM_QTY_ID
INNER JOIN mlfm_order_item oi ON oi.item_order_id=oiq.order_item_id
INNER JOIN mlfm_item i ON i.item_id=oi.item_id
INNER JOIN mlfm_design d ON d.design_id=p.DESIGN_ID
INNER JOIN mlfm_fabric_type f ON f.FABRIC_TYPE_ID = d.FABRIC_TYPE_ID
AND p.ACTIVE_STATUS=1 AND ppc.ACTIVE_STATUS=1 AND wm.ACTIVE_STATUS=1 AND oiq.active_status=1
AND NOT EXISTS (SELECT 1 FROM mlfm_production pro WHERE pro.PO_MST_ID=p.PO_MST_ID)
where oo.order_owner_id=coalesce(@orderOwnerId,oo.order_owner_id)
AND wm.WO_MST_ID= coalesce(@wOId,wm.WO_MST_ID) 
AND p.PO_MST_DATE between coalesce(@pOStartDate,p.PO_MST_DATE) and coalesce(@pOEndDate,p.PO_MST_DATE)

	 */
	String qry_WO="SELECT pm.PO_MST_ID,pm.PO_MST_DATE,pc.PO_CHD_DATE,oo.owner_name,\r\n" + 
			"mt.MACHINE_TYPE_NAME,d.design_id,i.item_name,ft.FABRIC_TYPE_NAME,\r\n" + 
			"d.DTM,pc.PO_QTY,pc.REMARKS,pc.ACTIVE_STATUS,pc.PO_CHD_ID,pc.PRODUCTION_PLAN_CHD_ID,\r\n" + 
			"o.order_id,oiq.order_item_qty_id,ppc.MACHINE_SHIFT_ID,wc.WO_CHD_ID,d.NO_OF_STITCHES,mt.MACHINE_TYPE_ID\r\n" + 
			"FROM mlfm_po_mst pm\r\n" + 
			"INNER JOIN mlfm_po_chd pc ON pc.PO_MST_ID=pm.PO_MST_ID\r\n" + 
			"INNER JOIN mlfm_production_plan_chd ppc ON ppc.PRODUCTION_PLAN_CHD_ID=pc.PRODUCTION_PLAN_CHD_ID\r\n" + 
			"INNER JOIN mlfm_production_plan_mst ppm ON ppm.PRODUCTION_PLAN_MST_ID=ppc.PRODUCTION_PLAN_MST_ID\r\n" + 
			"INNER JOIN mlfm_design d ON d.design_id=pm.DESIGN_ID\r\n" + 
			"INNER join mlfm_fabric_type ft ON ft.FABRIC_TYPE_ID=d.FABRIC_TYPE_ID\r\n" + 
			"INNER JOIN mlfm_wo_chd wc ON wc.WO_CHD_ID=ppm.WO_CHD_ID\r\n" + 
			"INNER JOIN mlfm_wo_mst wm ON wm.WO_MST_ID=wc.WO_MST_ID\r\n" + 
			"INNER JOIN mlfm_order_owner oo ON oo.order_owner_id=wm.ORDER_OWNER_ID\r\n" + 
			"INNER join mlfm_order_item_qty oiq ON oiq.order_item_qty_id=ppm.ORDER_ITEM_QTY_ID\r\n" + 
			"INNER JOIN mlfm_order_item oi ON oi.item_order_id=oiq.order_item_id\r\n" + 
			"INNER JOIN mlfm_item i on i.item_id = oi.item_id\r\n" + 
			"INNER JOIN mlfm_order o ON o.order_id=oi.order_id\r\n" + 
			"INNER JOIN mlfm_machine_shift ms ON ms.MACHINE_SHIFT_ID=ppc.MACHINE_SHIFT_ID\r\n" + 
			"INNER JOIN mlfm_machine m ON m.MACHINE_ID=ms.MACHINE_ID\r\n" + 
			"INNER JOIN mlfm_machine_type mt on mt.MACHINE_TYPE_ID=m.machine_type_id\r\n" + 
			"where oo.order_owner_id=coalesce(:orderOwnerId,oo.order_owner_id)\r\n" + 
			"AND wm.WO_MST_ID= coalesce(:wOId,wm.WO_MST_ID)\r\n" + 
			"and pm.PO_MST_DATE between coalesce(:pOStartDate,pm.PO_MST_DATE) and coalesce(:pOEndDate,pm.PO_MST_DATE)";
	
	
	/*
	 * SELECT pm.PO_MST_ID,pm.PO_MST_DATE,pc.PO_CHD_DATE,oo.owner_name,
mt.MACHINE_TYPE_NAME,d.design_id,i.item_name,ft.FABRIC_TYPE_NAME,
d.DTM,pc.PO_QTY,pc.REMARKS,pc.ACTIVE_STATUS,pc.PO_CHD_ID,pc.PRODUCTION_PLAN_CHD_ID,
o.order_id,oiq.order_item_qty_id,ppc.MACHINE_SHIFT_ID,wc.WO_CHD_ID
FROM mlfm_po_mst pm
INNER JOIN mlfm_po_chd pc ON pc.PO_MST_ID=pm.PO_MST_ID
INNER JOIN mlfm_production_plan_chd ppc ON ppc.PRODUCTION_PLAN_CHD_ID=pc.PRODUCTION_PLAN_CHD_ID
INNER JOIN mlfm_production_plan_mst ppm ON ppm.PRODUCTION_PLAN_MST_ID=ppc.PRODUCTION_PLAN_MST_ID
INNER JOIN mlfm_design d ON d.design_id=pm.DESIGN_ID
INNER join mlfm_fabric_type ft ON ft.FABRIC_TYPE_ID=d.FABRIC_TYPE_ID
INNER JOIN mlfm_wo_chd wc ON wc.WO_CHD_ID=ppm.WO_CHD_ID
INNER JOIN mlfm_wo_mst wm ON wm.WO_MST_ID=wc.WO_MST_ID
INNER JOIN mlfm_order_owner oo ON oo.order_owner_id=wm.ORDER_OWNER_ID
INNER join mlfm_order_item_qty oiq ON oiq.order_item_qty_id=ppm.ORDER_ITEM_QTY_ID
INNER JOIN mlfm_order_item oi ON oi.item_order_id=oiq.order_item_id
INNER JOIN mlfm_item i on i.item_id = oi.item_id
INNER JOIN mlfm_order o ON o.order_id=oi.order_id
INNER JOIN mlfm_machine_shift ms ON ms.MACHINE_SHIFT_ID=ppc.MACHINE_SHIFT_ID
INNER JOIN mlfm_machine m ON m.MACHINE_ID=ms.MACHINE_ID
INNER JOIN mlfm_machine_type mt on mt.MACHINE_TYPE_ID=m.machine_type_id
where oo.order_owner_id=coalesce(@orderOwnerId,oo.order_owner_id)
AND wm.WO_MST_ID= coalesce(@wOId,wm.WO_MST_ID)
and pm.PO_MST_DATE between coalesce(@pOStartDate,pm.PO_MST_DATE) and coalesce(@pOEndDate,pm.PO_MST_DATE)
	 * 
	 * 
	 * 
	 * */
		
		
		RowMapper<ModelProductionCustom> serviceMapper=new RowMapper<ModelProductionCustom>() {
			@Override
			public ModelProductionCustom mapRow(ResultSet rs, int rownum) throws SQLException{
				ModelProductionCustom bn=new ModelProductionCustom();
				
				
				
				
		        bn.setpODate(rs.getDate("PO_MST_DATE"));
		        bn.setOwnerName(rs.getString("owner_name"));
		        bn.setDesignId(rs.getLong("DESIGN_ID"));
		        bn.setMachineTypeName(rs.getString("MACHINE_TYPE_NAME"));
		        bn.setItemName(rs.getString("item_name"));
		        bn.setdTM(rs.getString("DTM"));
		        bn.setpOQty(rs.getDouble("PO_QTY"));
		        bn.setRemarks(rs.getString("REMARKS"));
		        bn.setActiveStatus(rs.getInt("ACTIVE_STATUS"));
		        bn.setFabricTypeName(rs.getString("FABRIC_TYPE_NAME"));
		        bn.setpOId(rs.getLong("PO_MST_ID"));
		        
		        bn.setOrderItemQtyId(rs.getLong("order_item_qty_id"));
		        bn.setwOChdId(rs.getLong("WO_CHD_ID"));
		        bn.setMachineShiftId(rs.getLong("MACHINE_SHIFT_ID"));
		        bn.setProductionPlanChdId(rs.getLong("PRODUCTION_PLAN_CHD_ID"));
		        bn.setNoOfStitches(rs.getInt("NO_OF_STITCHES"));
		        bn.setMachineTypeId(rs.getLong("MACHINE_TYPE_ID"));
		        bn.setOrderId(rs.getLong("order_id"));
		       // bn.setDesignId(rs.getLong("design_id"));
		       
	
		
			return bn;
			
			}	
		};
		
		
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		
		  parameters.addValue("orderOwnerId", orderOwnerId);
		  //parameters.addValue("ultimateBuyerId", ultimateBuyerId);
		  parameters.addValue("wOId", wOId);
	      parameters.addValue("pOStartDate", pOStartDate);
          parameters.addValue("pOEndDate", pOEndDate);
          
	     
	    System.out.println("query for when select Inquiry : " + qry_WO);
		
		return  namedJdbcTemplate.query(qry_WO,parameters,serviceMapper);
		
		
		// TODO Auto-generated method stub
		//return null;
	}
	
	@Override
	public List<ModelProductionCustom> getSearchListDoneProduction(Long orderOwnerId, Long wOId,
			Date pOStartDate, Date pOEndDate) {
		
		/*
		String qry_done_production ="SELECT p.PO_ID,p.PO_DATE,oo.owner_name,p.DESIGN_ID,i.item_name,p.DTM,p.PO_QTY,p.REMARKS,p.ACTIVE_STATUS,mt.MACHINE_TYPE_NAME,\r\n" + 
				"wc.WO_MST_ID,wm.ORDER_OWNER_ID\r\n" + 
				"FROM mlfm_production pro\r\n" + 
				"INNER JOIN mlfm_po p ON p.PO_ID=pro.PO_ID\r\n" + 
				"INNER JOIN mlfm_machine_type mt ON mt.MACHINE_TYPE_ID=p.MACHINE_TYPE_ID\r\n" + 
				"INNER JOIN mlfm_production_plan_chd ppc ON ppc.PRODUCTION_PLAN_CHD_ID=p.PRODUCTION_PLAN_CHD_ID\r\n" + 
				"INNER join mlfm_production_plan_mst ppm ON ppm.PRODUCTION_PLAN_MST_ID = ppc.PRODUCTION_PLAN_MST_ID\r\n" + 
				"INNER JOIN mlfm_wo_chd wc ON wc.WO_CHD_ID=ppm.WO_CHD_ID\r\n" + 
				"INNER JOIN mlfm_wo_mst wm ON wm.WO_MST_ID=wc.WO_MST_ID\r\n" + 
				"INNER JOIN mlfm_order_owner oo ON oo.order_owner_id=wm.ORDER_OWNER_ID\r\n" + 
				"INNER join mlfm_order_item_qty oiq ON oiq.order_item_qty_id=ppm.ORDER_ITEM_QTY_ID\r\n" + 
				"INNER JOIN mlfm_order_item oi ON oi.item_order_id=oiq.order_item_id\r\n" + 
				"INNER JOIN mlfm_item i ON i.item_id=oi.item_id\r\n" + 
				"AND pro.ACTIVE_STATUS=1 AND p.ACTIVE_STATUS=1 AND ppc.ACTIVE_STATUS=1 AND wm.ACTIVE_STATUS=1 AND oiq.active_status=1\r\n" + 
				"where oo.order_owner_id=coalesce(:orderOwnerId,oo.order_owner_id)\r\n" + 
				"AND wm.WO_MST_ID= coalesce(:wOId,wm.WO_MST_ID) \r\n" + 
				"AND p.PO_DATE between coalesce(:pOStartDate,p.PO_DATE) and\r\n" + 
				"			     coalesce(:pOEndDate,p.PO_DATE)";
				
				
				*/
		
		/*
		 * Sql Query
		 * 
		 * SELECT p.PO_ID,p.PO_DATE,oo.owner_name,p.DESIGN_ID,i.item_name,p.DTM,p.PO_QTY,p.REMARKS,p.ACTIVE_STATUS,mt.MACHINE_TYPE_NAME,
wc.WO_MST_ID,wm.ORDER_OWNER_ID
FROM mlfm_production pro
INNER JOIN mlfm_po p ON p.PO_ID=pro.PO_ID
INNER JOIN mlfm_machine_type mt ON mt.MACHINE_TYPE_ID=p.MACHINE_TYPE_ID
INNER JOIN mlfm_production_plan_chd ppc ON ppc.PRODUCTION_PLAN_CHD_ID=p.PRODUCTION_PLAN_CHD_ID
INNER join mlfm_production_plan_mst ppm ON ppm.PRODUCTION_PLAN_MST_ID = ppc.PRODUCTION_PLAN_MST_ID
INNER JOIN mlfm_wo_chd wc ON wc.WO_CHD_ID=ppm.WO_CHD_ID
INNER JOIN mlfm_wo_mst wm ON wm.WO_MST_ID=wc.WO_MST_ID
INNER JOIN mlfm_order_owner oo ON oo.order_owner_id=wm.ORDER_OWNER_ID
INNER join mlfm_order_item_qty oiq ON oiq.order_item_qty_id=ppm.ORDER_ITEM_QTY_ID
INNER JOIN mlfm_order_item oi ON oi.item_order_id=oiq.order_item_id
INNER JOIN mlfm_item i ON i.item_id=oi.item_id
AND pro.ACTIVE_STATUS=1 AND p.ACTIVE_STATUS=1 AND ppc.ACTIVE_STATUS=1 AND wm.ACTIVE_STATUS=1 AND oiq.active_status=1
where oo.order_owner_id=coalesce(@orderOwnerId,oo.order_owner_id)
AND wm.WO_MST_ID= coalesce(@wOId,wm.WO_MST_ID) 
AND p.PO_DATE between coalesce(@pOStartDate,p.PO_DATE) and
			     coalesce(@pOEndDate,p.PO_DATE)
		 * 
		 * */
		
		/*
		String qry_done_production ="SELECT p.PO_MST_ID,p.PO_MST_DATE,mt.MACHINE_TYPE_NAME,oo.owner_name,p.DESIGN_ID,i.item_name,d.DTM,pc.PO_QTY,p.REMARKS,p.ACTIVE_STATUS,f.FABRIC_TYPE_NAME\r\n" + 
				"FROM mlfm_production pro\r\n" + 
				"INNER JOIN mlfm_po_mst p ON p.PO_MST_ID=pro.PO_MST_ID\r\n" + 
				"INNER JOIN mlfm_po_chd pc ON pc.PO_MST_ID=p.PO_MST_ID\r\n" + 
				"INNER JOIN mlfm_machine_type mt ON mt.MACHINE_TYPE_ID=pc.MACHINE_TYPE_ID\r\n" + 
				"INNER JOIN mlfm_production_plan_chd ppc ON ppc.PRODUCTION_PLAN_CHD_ID=pc.PRODUCTION_PLAN_CHD_ID\r\n" + 
				"INNER join mlfm_production_plan_mst ppm ON ppm.PRODUCTION_PLAN_MST_ID = ppc.PRODUCTION_PLAN_MST_ID\r\n" + 
				"INNER JOIN mlfm_wo_chd wc ON wc.WO_CHD_ID=ppm.WO_CHD_ID\r\n" + 
				"INNER JOIN mlfm_wo_mst wm ON wm.WO_MST_ID=wc.WO_MST_ID\r\n" + 
				"INNER JOIN mlfm_order_owner oo ON oo.order_owner_id=wm.ORDER_OWNER_ID\r\n" + 
				"INNER join mlfm_order_item_qty oiq ON oiq.order_item_qty_id=ppm.ORDER_ITEM_QTY_ID\r\n" + 
				"INNER JOIN mlfm_order_item oi ON oi.item_order_id=oiq.order_item_id\r\n" + 
				"INNER JOIN mlfm_item i ON i.item_id=oi.item_id\r\n" + 
				"INNER JOIN mlfm_design d ON d.design_id=p.DESIGN_ID\r\n" + 
				"INNER JOIN mlfm_fabric_type f ON f.FABRIC_TYPE_ID=d.FABRIC_TYPE_ID\r\n" + 
				"AND pro.ACTIVE_STATUS=1 AND p.ACTIVE_STATUS=1 AND ppc.ACTIVE_STATUS=1 AND wm.ACTIVE_STATUS=1 AND oiq.active_status=1\r\n" + 
				"where oo.order_owner_id=coalesce(:orderOwnerId,oo.order_owner_id)\r\n" + 
				"AND wm.WO_MST_ID= coalesce(:wOId,wm.WO_MST_ID) \r\n" + 
				"AND p.PO_MST_DATE between coalesce(:pOStartDate,p.PO_MST_DATE) and\r\n" + 
				"			     coalesce(:pOEndDate,p.PO_MST_DATE)";
		*/
		
		
		
		/*
		 * SELECT p.PO_MST_ID,p.PO_MST_DATE,mt.MACHINE_TYPE_NAME,oo.owner_name,p.DESIGN_ID,i.item_name,d.DTM,pc.PO_QTY,p.REMARKS,p.ACTIVE_STATUS,f.FABRIC_TYPE_NAME
FROM mlfm_production pro
INNER JOIN mlfm_po_mst p ON p.PO_MST_ID=pro.PO_MST_ID
INNER JOIN mlfm_po_chd pc ON pc.PO_MST_ID=p.PO_MST_ID
INNER JOIN mlfm_machine_type mt ON mt.MACHINE_TYPE_ID=pc.MACHINE_TYPE_ID
INNER JOIN mlfm_production_plan_chd ppc ON ppc.PRODUCTION_PLAN_CHD_ID=pc.PRODUCTION_PLAN_CHD_ID
INNER join mlfm_production_plan_mst ppm ON ppm.PRODUCTION_PLAN_MST_ID = ppc.PRODUCTION_PLAN_MST_ID
INNER JOIN mlfm_wo_chd wc ON wc.WO_CHD_ID=ppm.WO_CHD_ID
INNER JOIN mlfm_wo_mst wm ON wm.WO_MST_ID=wc.WO_MST_ID
INNER JOIN mlfm_order_owner oo ON oo.order_owner_id=wm.ORDER_OWNER_ID
INNER join mlfm_order_item_qty oiq ON oiq.order_item_qty_id=ppm.ORDER_ITEM_QTY_ID
INNER JOIN mlfm_order_item oi ON oi.item_order_id=oiq.order_item_id
INNER JOIN mlfm_item i ON i.item_id=oi.item_id
INNER JOIN mlfm_design d ON d.design_id=p.DESIGN_ID
INNER JOIN mlfm_fabric_type f ON f.FABRIC_TYPE_ID=d.FABRIC_TYPE_ID
AND pro.ACTIVE_STATUS=1 AND p.ACTIVE_STATUS=1 AND ppc.ACTIVE_STATUS=1 AND wm.ACTIVE_STATUS=1 AND oiq.active_status=1
where oo.order_owner_id=coalesce(@orderOwnerId,oo.order_owner_id)
AND wm.WO_MST_ID= coalesce(@wOId,wm.WO_MST_ID) 
AND p.PO_MST_DATE between coalesce(@pOStartDate,p.PO_MST_DATE) and
			     coalesce(@pOEndDate,p.PO_MST_DATE)
		 */
		
		
		String qry_done_production ="SELECT pm.PO_MST_ID,pm.PO_MST_DATE,pc.PO_CHD_DATE,oo.owner_name,\r\n" + 
				"mt.MACHINE_TYPE_NAME,d.design_id,i.item_name,ft.FABRIC_TYPE_NAME,\r\n" + 
				"d.DTM,pc.PO_QTY,pc.REMARKS,pc.ACTIVE_STATUS,pc.PO_CHD_ID,pc.PRODUCTION_PLAN_CHD_ID,\r\n" + 
				"o.order_id,oiq.order_item_qty_id,ppc.MACHINE_SHIFT_ID,wc.WO_CHD_ID\r\n" + 
				"FROM mlfm_production pro\r\n" + 
				"INNER JOIN mlfm_po_mst pm ON pm.PO_MST_ID=pro.PO_MST_ID\r\n" + 
				"INNER JOIN mlfm_po_chd pc ON pc.PO_MST_ID=pm.PO_MST_ID\r\n" + 
				"INNER JOIN mlfm_production_plan_chd ppc ON ppc.PRODUCTION_PLAN_CHD_ID=pc.PRODUCTION_PLAN_CHD_ID\r\n" + 
				"INNER JOIN mlfm_production_plan_mst ppm ON ppm.PRODUCTION_PLAN_MST_ID=ppc.PRODUCTION_PLAN_MST_ID\r\n" + 
				"INNER JOIN mlfm_design d ON d.design_id=pm.DESIGN_ID\r\n" + 
				"INNER join mlfm_fabric_type ft ON ft.FABRIC_TYPE_ID=d.FABRIC_TYPE_ID\r\n" + 
				"INNER JOIN mlfm_wo_chd wc ON wc.WO_CHD_ID=ppm.WO_CHD_ID\r\n" + 
				"INNER JOIN mlfm_wo_mst wm ON wm.WO_MST_ID=wc.WO_MST_ID\r\n" + 
				"INNER JOIN mlfm_order_owner oo ON oo.order_owner_id=wm.ORDER_OWNER_ID\r\n" + 
				"INNER join mlfm_order_item_qty oiq ON oiq.order_item_qty_id=ppm.ORDER_ITEM_QTY_ID\r\n" + 
				"INNER JOIN mlfm_order_item oi ON oi.item_order_id=oiq.order_item_id\r\n" + 
				"INNER JOIN mlfm_item i on i.item_id = oi.item_id\r\n" + 
				"INNER JOIN mlfm_order o ON o.order_id=oi.order_id\r\n" + 
				"INNER JOIN mlfm_machine_shift ms ON ms.MACHINE_SHIFT_ID=ppc.MACHINE_SHIFT_ID\r\n" + 
				"INNER JOIN mlfm_machine m ON m.MACHINE_ID=ms.MACHINE_ID\r\n" + 
				"INNER JOIN mlfm_machine_type mt on mt.MACHINE_TYPE_ID=m.machine_type_id and Pro.active_status=1\r\n" + 
				"where oo.order_owner_id=coalesce(:orderOwnerId,oo.order_owner_id)\r\n" + 
				"AND wm.WO_MST_ID= coalesce(:wOId,wm.WO_MST_ID) \r\n" + 
				"AND pm.PO_MST_DATE between coalesce(:pOStartDate,pm.PO_MST_DATE) and\r\n" + 
				"			     coalesce(:pOEndDate,pm.PO_MST_DATE)";
		
		
		/*
		 * SELECT pm.PO_MST_ID,pm.PO_MST_DATE,pc.PO_CHD_DATE,oo.owner_name,
mt.MACHINE_TYPE_NAME,d.design_id,i.item_name,ft.FABRIC_TYPE_NAME,
d.DTM,pc.PO_QTY,pc.REMARKS,pc.ACTIVE_STATUS,pc.PO_CHD_ID,pc.PRODUCTION_PLAN_CHD_ID,
o.order_id,oiq.order_item_qty_id,ppc.MACHINE_SHIFT_ID,wc.WO_CHD_ID
FROM mlfm_production pro
INNER JOIN mlfm_po_mst pm ON pm.PO_MST_ID=pro.PO_MST_ID
INNER JOIN mlfm_po_chd pc ON pc.PO_MST_ID=pm.PO_MST_ID
INNER JOIN mlfm_production_plan_chd ppc ON ppc.PRODUCTION_PLAN_CHD_ID=pc.PRODUCTION_PLAN_CHD_ID
INNER JOIN mlfm_production_plan_mst ppm ON ppm.PRODUCTION_PLAN_MST_ID=ppc.PRODUCTION_PLAN_MST_ID
INNER JOIN mlfm_design d ON d.design_id=pm.DESIGN_ID
INNER join mlfm_fabric_type ft ON ft.FABRIC_TYPE_ID=d.FABRIC_TYPE_ID
INNER JOIN mlfm_wo_chd wc ON wc.WO_CHD_ID=ppm.WO_CHD_ID
INNER JOIN mlfm_wo_mst wm ON wm.WO_MST_ID=wc.WO_MST_ID
INNER JOIN mlfm_order_owner oo ON oo.order_owner_id=wm.ORDER_OWNER_ID
INNER join mlfm_order_item_qty oiq ON oiq.order_item_qty_id=ppm.ORDER_ITEM_QTY_ID
INNER JOIN mlfm_order_item oi ON oi.item_order_id=oiq.order_item_id
INNER JOIN mlfm_item i on i.item_id = oi.item_id
INNER JOIN mlfm_order o ON o.order_id=oi.order_id
INNER JOIN mlfm_machine_shift ms ON ms.MACHINE_SHIFT_ID=ppc.MACHINE_SHIFT_ID
INNER JOIN mlfm_machine m ON m.MACHINE_ID=ms.MACHINE_ID
INNER JOIN mlfm_machine_type mt on mt.MACHINE_TYPE_ID=m.machine_type_id
where oo.order_owner_id=coalesce(@orderOwnerId,oo.order_owner_id)
AND wm.WO_MST_ID= coalesce(@wOId,wm.WO_MST_ID) 
AND pm.PO_MST_DATE between coalesce(@pOStartDate,pm.PO_MST_DATE) and
			     coalesce(@pOEndDate,pm.PO_MST_DATE)
		 * 
		 * 
		 * */
		
		RowMapper<ModelProductionCustom> serviceMapper=new RowMapper<ModelProductionCustom>() {
			@Override
			public ModelProductionCustom mapRow(ResultSet rs, int rownum) throws SQLException{
				ModelProductionCustom bn=new ModelProductionCustom();
	
				
			bn.setpOId(rs.getLong("PO_MST_ID"));
	        bn.setpODate(rs.getDate("PO_MST_DATE"));
	        bn.setOwnerName(rs.getString("owner_name"));
	        bn.setMachineTypeName(rs.getString("MACHINE_TYPE_NAME"));
	        bn.setDesignId(rs.getLong("DESIGN_ID"));
	        bn.setItemName(rs.getString("item_name"));
	        bn.setdTM(rs.getString("DTM"));
	        bn.setpOQty(rs.getDouble("PO_QTY"));
	        bn.setRemarks(rs.getString("REMARKS"));
	        bn.setActiveStatus(rs.getInt("ACTIVE_STATUS"));
	        bn.setFabricTypeName(rs.getString("FABRIC_TYPE_NAME"));
	        
	        //System.out.println("Owner name :  " +rs.getString("owner_name"));
	        
	       
			return bn;
			
			}	
		};
		
		
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		
		  parameters.addValue("orderOwnerId", orderOwnerId);
		  //parameters.addValue("ultimateBuyerId", ultimateBuyerId);
		  parameters.addValue("wOId", wOId);
	      parameters.addValue("pOStartDate", pOStartDate);
          parameters.addValue("pOEndDate", pOEndDate);
          
	     
	    System.out.println("query : " + qry_done_production);
		
		return  namedJdbcTemplate.query(qry_done_production,parameters,serviceMapper);
		
		
		// TODO Auto-generated method stub
		//return null;
	}

	
	
	
	@Override
	public List<ModelProductionCustom> getPlanDetails(Long orderItemQtyId) {
		/*
		String  qry_inquiry="SELECT c.order_item_qty_id,a.production_plan_mst_id,c.production_plan_date " +
						"FROM mlfm_order_item_qty c INNER JOIN mlfm_production_plan_mst a ON a.ORDER_ITEM_QTY_ID=c.order_item_qty_id " +
						"where c.order_item_qty_id =coalesce(@orderItemQtyId,c.order_item_qty_id)";
				
			*/	
		
		
	//	last working
		/*
		String  qry_inquiry=" SELECT c.order_item_qty_id,a.production_plan_mst_id,pc.production_plan_date, mcs.MACHINE_SHIFT_ID, " + 
				            " mc.MACHINE_ID,mc.MACHINE_NAME, s.SHIFT_ID,s.SHIFT_NAME,pc.PRODUCTION_PLAN_QTY " + 
		                    " FROM mlfm_order_item_qty c INNER JOIN mlfm_production_plan_mst a ON a.ORDER_ITEM_QTY_ID=c.order_item_qty_id " + 
                            " inner join mlfm_production_plan_chd pc on pc.PRODUCTION_PLAN_MST_ID=a.PRODUCTION_PLAN_MST_ID " +
                            " inner join mlfm_machine_schedule ms on ms.MACHINE_SHIFT_ID=pc.MACHINE_SHIFT_ID " +
                            " inner join mlfm_machine_shift mcs on mcs.MACHINE_SHIFT_ID=pc.MACHINE_SHIFT_ID " +
                            " inner join mlfm_machine mc on mc.MACHINE_ID=mcs.MACHINE_ID " +
                            " inner join mlfm_shift s on s.SHIFT_ID=mcs.SHIFT_ID " +
		                    " where pc.ACTIVE_STATUS=1 and c.order_item_qty_id =coalesce(:orderItemQtyId,c.order_item_qty_id) ";
		*/
		String  qry_inquiry=" SELECT c.order_item_qty_id,a.production_plan_mst_id,pc.production_plan_date, mcs.MACHINE_SHIFT_ID, " + 
	            " mc.MACHINE_ID,mc.MACHINE_NAME, s.SHIFT_ID,s.SHIFT_NAME,pc.PRODUCTION_PLAN_QTY,u.uom_name,u.uom_id,c.item_Qty," +
	            "c.design_id,pc.PRODUCTION_PLAN_CHD_ID " + 
                " FROM mlfm_order_item_qty c INNER JOIN mlfm_production_plan_mst a ON a.ORDER_ITEM_QTY_ID=c.order_item_qty_id " + 
                " inner join mlfm_production_plan_chd pc on pc.PRODUCTION_PLAN_MST_ID=a.PRODUCTION_PLAN_MST_ID " +
                " inner join mlfm_machine_schedule ms on ms.MACHINE_SHIFT_ID=pc.MACHINE_SHIFT_ID " +
                " inner join mlfm_machine_shift mcs on mcs.MACHINE_SHIFT_ID=pc.MACHINE_SHIFT_ID " +
                " inner join mlfm_machine mc on mc.MACHINE_ID=mcs.MACHINE_ID " +
                " inner join mlfm_shift s on s.SHIFT_ID=mcs.SHIFT_ID " +
                " inner join mlfm_uom u on c.uom_id=u.uom_id " +
                " where pc.ACTIVE_STATUS=1 and c.order_item_qty_id =coalesce(:orderItemQtyId,c.order_item_qty_id) ";
		
		
				RowMapper<ModelProductionCustom> serviceMapper=new RowMapper<ModelProductionCustom>() {
					@Override
					public ModelProductionCustom mapRow(ResultSet rs, int rownum) throws SQLException{
						ModelProductionCustom bn=new ModelProductionCustom();
				
					
					bn.setPlanDate(rs.getDate("production_plan_date"));
					bn.setMachineName(rs.getString("MACHINE_NAME"));
					bn.setShiftName(rs.getString("SHIFT_NAME"));
					bn.setMachineId(rs.getLong("MACHINE_ID"));
					bn.setShiftId(rs.getLong("SHIFT_ID"));
					bn.setOrderItemQtyId(rs.getLong("order_item_qty_id"));
					bn.setProductionPlanQty(rs.getDouble("PRODUCTION_PLAN_QTY"));
					bn.setUomId(rs.getLong("uom_id"));
					bn.setUomName(rs.getString("uom_name"));
					bn.setItemQty(rs.getDouble("item_Qty"));
					bn.setMachineShiftId(rs.getLong("MACHINE_SHIFT_ID"));
					bn.setDesignId(rs.getLong("design_id"));
					bn.setProductionPlanChdId(rs.getLong("PRODUCTION_PLAN_CHD_ID"));
					
					
					return bn;
					
					}	
				};
				
				
				MapSqlParameterSource parameters = new MapSqlParameterSource();
				
				  parameters.addValue("orderItemQtyId", orderItemQtyId);
				  
			     
			    System.out.println("Production details list qry : " + qry_inquiry);
				
				return  namedJdbcTemplate.query(qry_inquiry,parameters,serviceMapper);
				
	
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveProduction(ModelProduction modelProduction) {
		productionRepository.save(modelProduction);
		
		
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<ModelProductionCustom> getProductionById(Long productionId) {
		/*
		String  qry=" SELECT\r\n" + 
				"    p.PRODUCTION_ID,\r\n" + 
				"    p.production_date,\r\n" + 
				"    mcs.MACHINE_SHIFT_ID,\r\n" + 
				"    mc.MACHINE_ID,\r\n" + 
				"    mc.MACHINE_NAME,\r\n" + 
				"    s.SHIFT_ID,\r\n" + 
				"    s.SHIFT_NAME,\r\n" + 
				"    p.PRODUCTION_QTY\r\n" + 
				"    FROM mlfm_production p \r\n" + 
				"    INNER JOIN mlfm_machine_shift mcs ON p.MACHINE_SHIFT_ID=mcs.MACHINE_SHIFT_ID\r\n" + 
				"    INNER join mlfm_machine mc on mc.MACHINE_ID=mcs.MACHINE_ID\r\n" + 
				//"    INNER JOIN mlfm_shift s on s.SHIFT_ID=mcs.SHIFT_ID WHERE mcs.ACTIVE_STATUS=1 and mc.ACTIVE_STATUS=1 AND s.ACTIVE_STATUS=1 AND p.PRODUCTION_ID=coalesce(:productionId,p.PRODUCTION_ID)";
				"    INNER JOIN mlfm_shift s on s.SHIFT_ID=mcs.SHIFT_ID WHERE p.PRODUCTION_ID=coalesce(:productionId,p.PRODUCTION_ID)";
		*/
		String  qry="SELECT p.PRODUCTION_ID, p.production_date, mcs.MACHINE_SHIFT_ID, mc.MACHINE_ID, mc.MACHINE_NAME, s.SHIFT_ID,"
				+ " s.SHIFT_NAME, p.PRODUCTION_QTY,p.REMARKS,p.ACTIVE_STATUS,p.PRODUCTION_REF_NO,p.NO_OF_STITCHES "
				+ "FROM mlfm_production p "
				+ "INNER JOIN mlfm_machine_shift mcs ON p.MACHINE_SHIFT_ID=mcs.MACHINE_SHIFT_ID "
				+ "INNER join mlfm_machine mc on mc.MACHINE_ID=mcs.MACHINE_ID "
				+ "INNER JOIN mlfm_shift s on s.SHIFT_ID=mcs.SHIFT_ID "
				+ "WHERE p.PRODUCTION_ID=coalesce(:productionId,p.PRODUCTION_ID)";
		
		/*
		 * SELECT p.PRODUCTION_ID, p.production_date, mcs.MACHINE_SHIFT_ID, mc.MACHINE_ID, mc.MACHINE_NAME, s.SHIFT_ID, s.SHIFT_NAME,
		  p.PRODUCTION_QTY,p.REMARKS,p.ACTIVE_STATUS,p.PRODUCTION_REF_NO,p.NO_OF_STITCHES  
		  FROM mlfm_production p
	      INNER JOIN mlfm_machine_shift mcs ON p.MACHINE_SHIFT_ID=mcs.MACHINE_SHIFT_ID
	      INNER join mlfm_machine mc on mc.MACHINE_ID=mcs.MACHINE_ID 
	      INNER JOIN mlfm_shift s on s.SHIFT_ID=mcs.SHIFT_ID
	     WHERE p.PRODUCTION_ID=coalesce(@productionId,p.PRODUCTION_ID)
		
		 */
		
		
		
		
		
		RowMapper<ModelProductionCustom> serviceMapper=new RowMapper<ModelProductionCustom>() {
			@Override
			public ModelProductionCustom mapRow(ResultSet rs, int rownum) throws SQLException{
				ModelProductionCustom bn=new ModelProductionCustom();
				
			bn.setProductionId(rs.getLong("PRODUCTION_ID"));
			
			bn.setProductionDate(rs.getDate("production_date"));
			
			bn.setMachineName(rs.getString("MACHINE_NAME"));
			
			bn.setShiftName(rs.getString("SHIFT_NAME"));
			
			bn.setRemarks(rs.getString("REMARKS"));
			
			bn.setActiveStatus(rs.getInt("ACTIVE_STATUS"));
			
			bn.setRefNo(rs.getString("PRODUCTION_REF_NO"));
			
			bn.setNoOfStitches(rs.getInt("NO_OF_STITCHES"));
			
			
			bn.setProductionQty(rs.getDouble("PRODUCTION_QTY"));
			System.out.println("  Id : " +rs.getLong("PRODUCTION_ID") );

			
			return bn;
			
			}	
		};
		
		
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		
		  parameters.addValue("productionId", productionId);
		  
	     
	    System.out.println("Production details qry(find by Id)  : " + qry);
		
		return  namedJdbcTemplate.query(qry,parameters,serviceMapper);
		
		// TODO Auto-generated method stub
		
	}

	@Override
	public Optional<ModelProduction> findProductiontById(Long productionId) {
		// TODO Auto-generated method stub
		return productionRepository.findById(productionId);
	}

	@Override
	public List<ModelProductionCustom> getProductionByIdForproductionEdit(Long productionId) {
		/*
		SELECT p.PRODUCTION_ID,p.PRODUCTION_DATE,p.NO_OF_STITCHES,p.PRODUCTION_REF_NO,p.PRODUCTION_QTY,
p.REMARKS,p.ACTIVE_STATUS 
FROM mlfm_production p 
WHERE p.PRODUCTION_ID=COALESCE(@productionId,p.PRODUCTION_ID)
		*/
		String  qry="SELECT p.PRODUCTION_ID,p.PRODUCTION_DATE,p.NO_OF_STITCHES,p.PRODUCTION_REF_NO,p.PRODUCTION_QTY,\r\n" + 
				"p.REMARKS,p.ACTIVE_STATUS \r\n" + 
				"FROM mlfm_production p \r\n" + 
				"WHERE p.PRODUCTION_ID=COALESCE(:productionId,p.PRODUCTION_ID)";
		
		RowMapper<ModelProductionCustom> serviceMapper=new RowMapper<ModelProductionCustom>() {
			@Override
			public ModelProductionCustom mapRow(ResultSet rs, int rownum) throws SQLException{
				ModelProductionCustom bn=new ModelProductionCustom();
				
			bn.setProductionId(rs.getLong("PRODUCTION_ID"));
			bn.setProductionDate(rs.getDate("PRODUCTION_DATE"));
			bn.setRemarks(rs.getString("REMARKS"));
			bn.setActiveStatus(rs.getInt("ACTIVE_STATUS"));
			bn.setProductionQty(rs.getDouble("PRODUCTION_QTY"));
			bn.setRefNo(rs.getString("PRODUCTION_REF_NO"));
			bn.setNoOfStitches(rs.getInt("NO_OF_STITCHES"));
			
		//	System.out.println("rs.getString(\"PRODUCTION_REF_NO\")  : " + rs.getString("PRODUCTION_REF_NO"));
		//  System.out.println("rs.getInt(\"NO_OF_STITCHES\"  : " + rs.getInt("NO_OF_STITCHES"));

			
			return bn;
			
			}	
		};
		
		
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		
		  parameters.addValue("productionId", productionId);
		  
	     
	    System.out.println("Production details qry(find by Id)  : " + qry);
		
		return  namedJdbcTemplate.query(qry,parameters,serviceMapper);
		
		// TODO Auto-generated method stub
	}

	@Override
	public List<ModelProductionCustom> getProductionByPOMstId(Long pOMstId) {
		
		/*
		SELECT p.PRODUCTION_ID,p.PRODUCTION_DATE,p.NO_OF_STITCHES,p.PRODUCTION_REF_NO,p.PRODUCTION_QTY,mc.MACHINE_NAME,s.SHIFT_NAME,
p.REMARKS,p.ACTIVE_STATUS 
FROM mlfm_production p
LEFT OUTER JOIN mlfm_machine_shift mcs ON p.MACHINE_SHIFT_ID=mcs.MACHINE_SHIFT_ID
LEFT OUTER JOIN mlfm_machine mc on mc.MACHINE_ID=mcs.MACHINE_ID 
LEFT OUTER JOIN mlfm_shift s on s.SHIFT_ID=mcs.SHIFT_ID
WHERE p.PO_MST_ID=COALESCE(@pOMstId,p.PO_MST_ID)
		*/
		String  qry="SELECT p.PRODUCTION_ID,p.PRODUCTION_DATE,p.NO_OF_STITCHES,p.PRODUCTION_REF_NO,p.PRODUCTION_QTY,mc.MACHINE_NAME,s.SHIFT_NAME,\r\n" + 
				"p.REMARKS,p.ACTIVE_STATUS \r\n" + 
				"FROM mlfm_production p\r\n" + 
				"LEFT OUTER JOIN mlfm_machine_shift mcs ON p.MACHINE_SHIFT_ID=mcs.MACHINE_SHIFT_ID\r\n" + 
				"LEFT OUTER JOIN mlfm_machine mc on mc.MACHINE_ID=mcs.MACHINE_ID \r\n" + 
				"LEFT OUTER JOIN mlfm_shift s on s.SHIFT_ID=mcs.SHIFT_ID\r\n" + 
				"WHERE p.PO_MST_ID=COALESCE(:pOMstId,p.PO_MST_ID)";
		
		RowMapper<ModelProductionCustom> serviceMapper=new RowMapper<ModelProductionCustom>() {
			@Override
			public ModelProductionCustom mapRow(ResultSet rs, int rownum) throws SQLException{
				ModelProductionCustom bn=new ModelProductionCustom();
				
			bn.setProductionId(rs.getLong("PRODUCTION_ID"));
			bn.setProductionDate(rs.getDate("PRODUCTION_DATE"));
			bn.setRemarks(rs.getString("REMARKS"));
			bn.setActiveStatus(rs.getInt("ACTIVE_STATUS"));
			bn.setProductionQty(rs.getDouble("PRODUCTION_QTY"));
			bn.setRefNo(rs.getString("PRODUCTION_REF_NO"));
			bn.setNoOfStitches(rs.getInt("NO_OF_STITCHES"));
            bn.setMachineName(rs.getString("MACHINE_NAME"));
			bn.setShiftName(rs.getString("SHIFT_NAME"));
		
			
			return bn;
			
			}	
		};
		
		
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		
		  parameters.addValue("pOMstId", pOMstId);
		  
	     
	    System.out.println("Production details qry(find by Id)  : " + qry);
		
		return  namedJdbcTemplate.query(qry,parameters,serviceMapper);
		
		// TODO Auto-generated method stub
	}

}
