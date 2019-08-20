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

import com.biziitech.mlfm.custom.model.ModelPackingCustom;
import com.biziitech.mlfm.dao.DaoPacking;
import com.biziitech.mlfm.model.ModelPacking;
import com.biziitech.mlfm.repository.PackingRepository;

@Service
public class DaoPackingImp implements DaoPacking{
	
	@Autowired
	private PackingRepository packingRepository;
	
	
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
	public void savePacking(ModelPacking modelPacking) {
		// TODO Auto-generated method stub
		
		
		packingRepository.save(modelPacking);
		
	}

	@Override
	public List<ModelPackingCustom> getPackingById(Long id) {
		// TODO Auto-generated method stub
		String qry="select p.packing_date,p.packed_qty, p.packed_by,p.packing_id,p.active_status,p.remarks,u.user_id,u.user_name from mlfm_packing p \r\n" + 
				"left join bg_user u on p.packed_by=u.user_id  \r\n" +
				"where p.production_id=coalesce(:id,p.production_id) and p.active_status=1\r\n" + 
				"ORDER BY p.packing_date DESC";
RowMapper<ModelPackingCustom> serviceMapper=new RowMapper<ModelPackingCustom>() {
			
			
			public ModelPackingCustom mapRow(ResultSet rs, int rownum) throws SQLException{
				ModelPackingCustom bn=new ModelPackingCustom();
				 bn.setPackingDate(rs.getDate("PACKING_DATE"));
				 System.out.println(rs.getDate("PACKING_DATE"));
				 bn.setPackedQty(rs.getDouble("PACKED_QTY"));
				 System.out.println(rs.getDouble("PACKED_QTY"));
				 bn.setPackedBy(rs.getLong("PACKED_BY"));
				 System.out.println(rs.getLong("PACKED_BY"));
				 bn.setUserId(rs.getLong("USER_ID"));
				 bn.setUserName(rs.getString("USER_NAME"));
				 bn.setActiveStatus(rs.getInt("ACTIVE_STATUS"));
				 bn.setPackedRemarks(rs.getString("REMARKS"));
				 bn.setPackingId(rs.getLong("PACKING_ID"));
				 
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
	      System.out.println(" in daoPackedImp, id:" +id);

		System.out.println("service mapper ");
		return namedParameterJdbcTemplate.query(qry,parameters,serviceMapper);
	}

	@Override
	public List<ModelPackingCustom> getPackingDateById(Long id, Date startDate, Date endDate) {
		// TODO Auto-generated method stub
		String qry="select p.packing_date,p.packed_qty, p.packed_by,p.packing_id,p.active_status,p.remarks,u.user_id,u.user_name from mlfm_packing p \r\n" + 
				"left join bg_user u on p.packed_by=u.user_id  \r\n" +
				"where p.production_id=coalesce(:id,p.production_id) and p.active_status=1\r\n" + 
				"and p.packing_date BETWEEN coalesce(:startDate,p.packing_date) AND coalesce(:endDate,p.packing_date) ORDER BY p.packing_date DESC";
RowMapper<ModelPackingCustom> serviceMapper=new RowMapper<ModelPackingCustom>() {
			
			
			public ModelPackingCustom mapRow(ResultSet rs, int rownum) throws SQLException{
				ModelPackingCustom bn=new ModelPackingCustom();
				 bn.setPackingDate(rs.getDate("PACKING_DATE"));
				 System.out.println(rs.getDate("PACKING_DATE"));
				 bn.setPackedQty(rs.getDouble("PACKED_QTY"));
				 System.out.println(rs.getDouble("PACKED_QTY"));
				 bn.setPackedBy(rs.getLong("PACKED_BY"));
				 System.out.println(rs.getLong("PACKED_BY"));
				 bn.setUserId(rs.getLong("USER_ID"));
				 bn.setUserName(rs.getString("USER_NAME"));
				 bn.setActiveStatus(rs.getInt("ACTIVE_STATUS"));
				 bn.setPackedRemarks(rs.getString("REMARKS"));
				 bn.setPackingId(rs.getLong("PACKING_ID"));
				 
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
	      System.out.println(" in daoPackedImp, id:" +id);
 	      parameters.addValue("startDate", startDate);
 	     System.out.println(" in daoPackedImp, startDate:" +startDate);
      	  parameters.addValue("endDate", endDate);
      	System.out.println(" in daoPackedImp, endDate:" +endDate);
		System.out.println("service mapper ");
		return namedParameterJdbcTemplate.query(qry,parameters,serviceMapper);
	}

	@Override
	public List<ModelPackingCustom> getPendingPackingOrderDetails(Long buyerTypeId, Long buyerId, Long inquiryId,
			Long packedById, Date startDate, Date endDate, Long itemId) {
		// TODO Auto-generated method stub
		String qry="select a.production_id, a.production_date, a.production_qty, a.order_item_qty_id, a.item_qty, \r\n" + 
				"a.uom_id, a.item_id,a.item_name, a.order_id, a.order_date,a.order_owner_id, a.owner_name,\r\n" + 
				"a.uom_name, a.packing_status, sum(a.packed_qty) packed_qty\r\n" + 
				"FROM ( \r\n" + 
				"select a.production_id, a.production_date, a.production_qty, d.order_item_qty_id, d.item_qty,  \r\n" + 
				"d.uom_id, e.item_id,f.item_name, g.order_id, g.order_date,h.order_owner_type_id,g.order_owner_id, h.owner_name, \r\n" + 
				"i.uom_name, (select if (exists(select 1 from mlfm_packing p where p.PRODUCTION_ID=a.PRODUCTION_ID), 1,0)) packing_data_exists, \r\n" + 
				"(select  if ( exists (select 1 from mlfm_packing p where p.PRODUCTION_ID=a.production_id  \r\n" + 
				"and p.active_status=1 having sum(p.PACKED_QTY) >=a.PRODUCTION_QTY),'C', 'P')) packing_status,p.PACKED_QTY,p.packed_by,  \r\n" + 
				"c.WO_CHD_ID  from mlfm_production a inner join mlfm_production_plan_chd b on a.production_plan_chd_id=b.production_plan_chd_id\r\n" + 
				"inner join mlfm_production_plan_mst c on b.production_plan_mst_id=c.production_plan_mst_id\r\n" + 
				"inner join mlfm_order_item_qty d on c.order_item_qty_id=d.order_item_qty_id inner join mlfm_order_item e on d.order_item_id=e.item_order_id \r\n" + 
				"inner join mlfm_item f on e.item_id=f.item_id inner join mlfm_order g on e.order_id=g.order_id\r\n" + 
				"inner join mlfm_order_owner h on g.order_owner_id=h.order_owner_id inner join mlfm_uom i on d.uom_id=i.uom_id left OUTER join mlfm_packing p on p.PRODUCTION_ID=a.PRODUCTION_ID where a.active_status=1) a where \r\n" + 
				"a.wo_chd_id is null and a.order_item_qty_id is not null  and packing_status='P'and a.order_owner_id=coalesce(:buyerId,a.order_owner_id)\r\n" + 
				"and a.order_owner_type_id=coalesce(:buyerTypeId,a.order_owner_type_id) and a.item_id=coalesce(:itemId,a.item_id)\r\n" + 
				"and a.order_id=coalesce(:inquiryId,a.order_id) and a.order_date BETWEEN coalesce(:startDate,a.order_date) AND coalesce(:endDate,a.order_date) \r\n" + 
				"and coalesce(a.packed_by,9999999999)=coalesce(:packedById,coalesce(a.packed_by,9999999999)) \r\n" + 
				"group by a.production_id, a.production_date, a.production_qty, a.order_item_qty_id, a.item_qty,\r\n" + 
				"a.uom_id, a.item_id,item_name, a.order_id, a.order_date,a.order_owner_id, a.owner_name, a.uom_name";
		RowMapper<ModelPackingCustom> serviceMapper=new RowMapper<ModelPackingCustom>() {
			public ModelPackingCustom mapRow(ResultSet rs, int rownum) throws SQLException{
				ModelPackingCustom bn=new ModelPackingCustom();
					 Double packingQty= rs.getDouble("PRODUCTION_QTY")-rs.getDouble("PACKED_QTY");
					 
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
							   bn.setPackingStatus(rs.getString("PACKING_STATUS"));					   
							  //bn.setqCQtySum(rs.getDouble("QC_QTY"));
							  //bn.setMendingQtySum(rs.getDouble("MENDED_QTY"));
							  //bn.setDyingQtySum(rs.getDouble("DYING_QTY"));
							  //bn.setFinishedQtySum(rs.getDouble("FINISHED_QTY"));
							    bn.setPackedQtySum(rs.getDouble("PACKED_QTY"));
							    bn.setPackingQty(packingQty);

					
					 return bn;
					
				}
			};

		      MapSqlParameterSource parameters = new MapSqlParameterSource();  
			      parameters.addValue("buyerTypeId", buyerTypeId);
			      System.out.println("buyerTypeId"+ buyerTypeId);
			      parameters.addValue("buyerId", buyerId);
			      System.out.println(" in daoPackingImp, ownerName:" +buyerId);
			      parameters.addValue("packedById", packedById);
			      parameters.addValue("inquiryId", inquiryId);
			      System.out.println("in daoPackingImp, inquiryId: "+inquiryId);
			      parameters.addValue("startDate", startDate);
			      parameters.addValue("endDate", endDate);
			      parameters.addValue("itemId", itemId);
		      

			System.out.println("service mapper ");
			return namedParameterJdbcTemplate.query(qry,parameters,serviceMapper);
	}

	@Override
	public List<ModelPackingCustom> getPendingPackingWODetails(Long buyerTypeId, Long buyerId, Long inquiryId,
			Long packedById, Date startDate, Date endDate, Long itemId) {
		// TODO Auto-generated method stub
		String qry="select a.production_id, a.production_date, a.production_qty, a.order_item_qty_id, a.item_qty, \r\n" + 
				"a.uom_id, a.item_id,a.item_name, a.order_id, a.order_date,a.order_owner_id, a.owner_name,\r\n" + 
				"a.uom_name, a.packing_status, sum(a.packed_qty) packed_qty,a.WO_CHD_ID \r\n" + 
				"FROM ( \r\n" + 
				"select a.production_id, a.production_date, a.production_qty, d.order_item_qty_id, d.item_qty,  \r\n" + 
				"d.uom_id, e.item_id,f.item_name, g.order_id, g.order_date,h.order_owner_type_id,g.order_owner_id, h.owner_name, \r\n" + 
				"i.uom_name, (select if (exists(select 1 from mlfm_packing p where p.PRODUCTION_ID=a.PRODUCTION_ID), 1,0)) packing_data_exists, \r\n" + 
				"(select  if ( exists (select 1 from mlfm_packing p where p.PRODUCTION_ID=a.production_id  \r\n" + 
				"and p.active_status=1 having sum(p.PACKED_QTY) >=a.PRODUCTION_QTY),'C', 'P')) packing_status,p.PACKED_QTY,p.packed_by,  \r\n" + 
				"c.WO_CHD_ID  from mlfm_production a inner join mlfm_production_plan_chd b on a.production_plan_chd_id=b.production_plan_chd_id\r\n" + 
				"inner join mlfm_production_plan_mst c on b.production_plan_mst_id=c.production_plan_mst_id\r\n" + 
				"inner join mlfm_order_item_qty d on c.order_item_qty_id=d.order_item_qty_id inner join mlfm_order_item e on d.order_item_id=e.item_order_id \r\n" + 
				"inner join mlfm_item f on e.item_id=f.item_id inner join mlfm_order g on e.order_id=g.order_id\r\n" + 
				"inner join mlfm_order_owner h on g.order_owner_id=h.order_owner_id inner join mlfm_uom i on d.uom_id=i.uom_id left OUTER join mlfm_packing p on p.PRODUCTION_ID=a.PRODUCTION_ID where a.active_status=1) a where \r\n" + 
				"a.wo_chd_id is not null  and packing_status='P'and a.order_owner_id=coalesce(:buyerId,a.order_owner_id)\r\n" + 
				"and a.order_owner_type_id=coalesce(:buyerTypeId,a.order_owner_type_id) and a.item_id=coalesce(:itemId,a.item_id)\r\n" + 
				"and a.order_id=coalesce(:inquiryId,a.order_id) and a.order_date BETWEEN coalesce(:startDate,a.order_date) AND coalesce(:endDate,a.order_date) \r\n" + 
				"and coalesce(a.packed_by,9999999999)=coalesce(:packedById,coalesce(a.packed_by,9999999999)) \r\n" + 
				"group by a.production_id, a.production_date, a.production_qty, a.order_item_qty_id, a.item_qty,\r\n" + 
				"a.uom_id, a.item_id,item_name, a.order_id, a.order_date,a.order_owner_id, a.owner_name, a.uom_name";
		RowMapper<ModelPackingCustom> serviceMapper=new RowMapper<ModelPackingCustom>() {
			public ModelPackingCustom mapRow(ResultSet rs, int rownum) throws SQLException{
				ModelPackingCustom bn=new ModelPackingCustom();
					 Double packingQty= rs.getDouble("PRODUCTION_QTY")-rs.getDouble("PACKED_QTY");
					 
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
							   bn.setInquiryId(rs.getLong("WO_CHD_ID"));
				   			   System.out.println(rs.getLong("WO_CHD_ID"));
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
							   bn.setPackingStatus(rs.getString("PACKING_STATUS"));					   
							  //bn.setqCQtySum(rs.getDouble("QC_QTY"));
							  //bn.setMendingQtySum(rs.getDouble("MENDED_QTY"));
							  //bn.setDyingQtySum(rs.getDouble("DYING_QTY"));
							  //bn.setFinishedQtySum(rs.getDouble("FINISHED_QTY"));
							    bn.setPackedQtySum(rs.getDouble("PACKED_QTY"));
							    bn.setPackingQty(packingQty);

					
					 return bn;
					
				}
			};

		      MapSqlParameterSource parameters = new MapSqlParameterSource();  
			      parameters.addValue("buyerTypeId", buyerTypeId);
			      System.out.println("buyerTypeId"+ buyerTypeId);
			      parameters.addValue("buyerId", buyerId);
			      System.out.println(" in daoPackingImp, ownerName:" +buyerId);
			      parameters.addValue("packedById", packedById);
			      parameters.addValue("inquiryId", inquiryId);
			      System.out.println("in daoPackingImp, inquiryId: "+inquiryId);
			      parameters.addValue("startDate", startDate);
			      parameters.addValue("endDate", endDate);
			      parameters.addValue("itemId", itemId);
		      

			System.out.println("service mapper ");
			return namedParameterJdbcTemplate.query(qry,parameters,serviceMapper);
	}

	@Override
	public List<ModelPackingCustom> getCompletedPackingOrderDetails(Long buyerTypeId, Long buyerId, Long inquiryId,
			Long packedById, Date startDate, Date endDate, Long itemId) {
		// TODO Auto-generated method stub
		String qry="select a.production_id, a.production_date, a.production_qty, a.order_item_qty_id, a.item_qty, \r\n" + 
				"a.uom_id, a.item_id,a.item_name, a.order_id, a.order_date,a.order_owner_id, a.owner_name,\r\n" + 
				"a.uom_name, a.packing_status, sum(a.packed_qty) packed_qty\r\n" + 
				"FROM ( \r\n" + 
				"select a.production_id, a.production_date, a.production_qty, d.order_item_qty_id, d.item_qty,  \r\n" + 
				"d.uom_id, e.item_id,f.item_name, g.order_id, g.order_date,h.order_owner_type_id,g.order_owner_id, h.owner_name, \r\n" + 
				"i.uom_name, (select if (exists(select 1 from mlfm_packing p where p.PRODUCTION_ID=a.PRODUCTION_ID), 1,0)) packing_data_exists, \r\n" + 
				"(select  if ( exists (select 1 from mlfm_packing p where p.PRODUCTION_ID=a.production_id  \r\n" + 
				"and p.active_status=1 having sum(p.PACKED_QTY) >=a.PRODUCTION_QTY),'C', 'P')) packing_status,p.PACKED_QTY,p.packed_by,  \r\n" + 
				"c.WO_CHD_ID  from mlfm_production a inner join mlfm_production_plan_chd b on a.production_plan_chd_id=b.production_plan_chd_id\r\n" + 
				"inner join mlfm_production_plan_mst c on b.production_plan_mst_id=c.production_plan_mst_id\r\n" + 
				"inner join mlfm_order_item_qty d on c.order_item_qty_id=d.order_item_qty_id inner join mlfm_order_item e on d.order_item_id=e.item_order_id \r\n" + 
				"inner join mlfm_item f on e.item_id=f.item_id inner join mlfm_order g on e.order_id=g.order_id\r\n" + 
				"inner join mlfm_order_owner h on g.order_owner_id=h.order_owner_id inner join mlfm_uom i on d.uom_id=i.uom_id left OUTER join mlfm_packing p on p.PRODUCTION_ID=a.PRODUCTION_ID where a.active_status=1) a where \r\n" + 
				"a.wo_chd_id is null and a.order_item_qty_id is not null  and packing_status='C'and a.order_owner_id=coalesce(:buyerId,a.order_owner_id)\r\n" + 
				"and a.order_owner_type_id=coalesce(:buyerTypeId,a.order_owner_type_id) and a.item_id=coalesce(:itemId,a.item_id)\r\n" + 
				"and a.order_id=coalesce(:inquiryId,a.order_id) and a.order_date BETWEEN coalesce(:startDate,a.order_date) AND coalesce(:endDate,a.order_date) \r\n" + 
				"and coalesce(a.packed_by,9999999999)=coalesce(:packedById,coalesce(a.packed_by,9999999999)) \r\n" + 
				"group by a.production_id, a.production_date, a.production_qty, a.order_item_qty_id, a.item_qty,\r\n" + 
				"a.uom_id, a.item_id,item_name, a.order_id, a.order_date,a.order_owner_id, a.owner_name, a.uom_name";
		RowMapper<ModelPackingCustom> serviceMapper=new RowMapper<ModelPackingCustom>() {
			public ModelPackingCustom mapRow(ResultSet rs, int rownum) throws SQLException{
				ModelPackingCustom bn=new ModelPackingCustom();
					 Double packingQty= rs.getDouble("PRODUCTION_QTY")-rs.getDouble("PACKED_QTY");
					 
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
							   bn.setPackingStatus(rs.getString("PACKING_STATUS"));					   
							  //bn.setqCQtySum(rs.getDouble("QC_QTY"));
							  //bn.setMendingQtySum(rs.getDouble("MENDED_QTY"));
							  //bn.setDyingQtySum(rs.getDouble("DYING_QTY"));
							  //bn.setFinishedQtySum(rs.getDouble("FINISHED_QTY"));
							    bn.setPackedQtySum(rs.getDouble("PACKED_QTY"));
							    bn.setPackingQty(packingQty);

					
					 return bn;
					
				}
			};

		      MapSqlParameterSource parameters = new MapSqlParameterSource();  
			      parameters.addValue("buyerTypeId", buyerTypeId);
			      System.out.println("buyerTypeId"+ buyerTypeId);
			      parameters.addValue("buyerId", buyerId);
			      System.out.println(" in daoPackingImp, ownerName:" +buyerId);
			      parameters.addValue("packedById", packedById);
			      parameters.addValue("inquiryId", inquiryId);
			      System.out.println("in daoPackingImp, inquiryId: "+inquiryId);
			      parameters.addValue("startDate", startDate);
			      parameters.addValue("endDate", endDate);
			      parameters.addValue("itemId", itemId);
		      

			System.out.println("service mapper ");
			return namedParameterJdbcTemplate.query(qry,parameters,serviceMapper);
	}

	@Override
	public List<ModelPackingCustom> getCompletedPackingWODetails(Long buyerTypeId, Long buyerId, Long inquiryId,
			Long packedById, Date startDate, Date endDate, Long itemId) {
		// TODO Auto-generated method stub
		String qry="select a.production_id, a.production_date, a.production_qty, a.order_item_qty_id, a.item_qty, \r\n" + 
				"a.uom_id, a.item_id,a.item_name, a.order_id, a.order_date,a.order_owner_id, a.owner_name,\r\n" + 
				"a.uom_name, a.packing_status, sum(a.packed_qty) packed_qty,a.WO_CHD_ID \r\n" + 
				"FROM ( \r\n" + 
				"select a.production_id, a.production_date, a.production_qty, d.order_item_qty_id, d.item_qty,  \r\n" + 
				"d.uom_id, e.item_id,f.item_name, g.order_id, g.order_date,h.order_owner_type_id,g.order_owner_id, h.owner_name, \r\n" + 
				"i.uom_name, (select if (exists(select 1 from mlfm_packing p where p.PRODUCTION_ID=a.PRODUCTION_ID), 1,0)) packing_data_exists, \r\n" + 
				"(select  if ( exists (select 1 from mlfm_packing p where p.PRODUCTION_ID=a.production_id  \r\n" + 
				"and p.active_status=1 having sum(p.PACKED_QTY) >=a.PRODUCTION_QTY),'C', 'P')) packing_status,p.PACKED_QTY,p.packed_by,  \r\n" + 
				"c.WO_CHD_ID  from mlfm_production a inner join mlfm_production_plan_chd b on a.production_plan_chd_id=b.production_plan_chd_id\r\n" + 
				"inner join mlfm_production_plan_mst c on b.production_plan_mst_id=c.production_plan_mst_id\r\n" + 
				"inner join mlfm_order_item_qty d on c.order_item_qty_id=d.order_item_qty_id inner join mlfm_order_item e on d.order_item_id=e.item_order_id \r\n" + 
				"inner join mlfm_item f on e.item_id=f.item_id inner join mlfm_order g on e.order_id=g.order_id\r\n" + 
				"inner join mlfm_order_owner h on g.order_owner_id=h.order_owner_id inner join mlfm_uom i on d.uom_id=i.uom_id left OUTER join mlfm_packing p on p.PRODUCTION_ID=a.PRODUCTION_ID where a.active_status=1) a where \r\n" + 
				"a.wo_chd_id is not null  and packing_status='C'and a.order_owner_id=coalesce(:buyerId,a.order_owner_id)\r\n" + 
				"and a.order_owner_type_id=coalesce(:buyerTypeId,a.order_owner_type_id) and a.item_id=coalesce(:itemId,a.item_id)\r\n" + 
				"and a.order_id=coalesce(:inquiryId,a.order_id) and a.order_date BETWEEN coalesce(:startDate,a.order_date) AND coalesce(:endDate,a.order_date) \r\n" + 
				"and coalesce(a.packed_by,9999999999)=coalesce(:packedById,coalesce(a.packed_by,9999999999)) \r\n" + 
				"group by a.production_id, a.production_date, a.production_qty, a.order_item_qty_id, a.item_qty,\r\n" + 
				"a.uom_id, a.item_id,item_name, a.order_id, a.order_date,a.order_owner_id, a.owner_name, a.uom_name";
		RowMapper<ModelPackingCustom> serviceMapper=new RowMapper<ModelPackingCustom>() {
			public ModelPackingCustom mapRow(ResultSet rs, int rownum) throws SQLException{
				ModelPackingCustom bn=new ModelPackingCustom();
					 Double packingQty= rs.getDouble("PRODUCTION_QTY")-rs.getDouble("PACKED_QTY");
					 
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
							   bn.setInquiryId(rs.getLong("WO_CHD_ID"));
				   			   System.out.println(rs.getLong("WO_CHD_ID"));
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
							   bn.setPackingStatus(rs.getString("PACKING_STATUS"));					   
							  //bn.setqCQtySum(rs.getDouble("QC_QTY"));
							  //bn.setMendingQtySum(rs.getDouble("MENDED_QTY"));
							  //bn.setDyingQtySum(rs.getDouble("DYING_QTY"));
							  //bn.setFinishedQtySum(rs.getDouble("FINISHED_QTY"));
							    bn.setPackedQtySum(rs.getDouble("PACKED_QTY"));
							    bn.setPackingQty(packingQty);

					
					 return bn;
					
				}
			};

		      MapSqlParameterSource parameters = new MapSqlParameterSource();  
			      parameters.addValue("buyerTypeId", buyerTypeId);
			      System.out.println("buyerTypeId"+ buyerTypeId);
			      parameters.addValue("buyerId", buyerId);
			      System.out.println(" in daoPackingImp, ownerName:" +buyerId);
			      parameters.addValue("packedById", packedById);
			      parameters.addValue("inquiryId", inquiryId);
			      System.out.println("in daoPackingImp, inquiryId: "+inquiryId);
			      parameters.addValue("startDate", startDate);
			      parameters.addValue("endDate", endDate);
			      parameters.addValue("itemId", itemId);
		      

			System.out.println("service mapper ");
			return namedParameterJdbcTemplate.query(qry,parameters,serviceMapper);
	}

	@Override
	public List<ModelPackingCustom> getPendingPackingOrderDetailsByPackingDate(Long buyerTypeId, Long buyerId,
			Long inquiryId, Long packedById, Date startDate, Date endDate, Long itemId) {
		// TODO Auto-generated method stub
		String qry="select a.production_id, a.production_date, a.production_qty, a.order_item_qty_id, a.item_qty, \r\n" + 
				"a.uom_id, a.item_id,a.item_name, a.order_id, a.order_date,a.order_owner_id, a.owner_name,\r\n" + 
				"a.uom_name, a.packing_status, sum(a.packed_qty) packed_qty,a.packing_date \r\n" + 
				"FROM ( \r\n" + 
				"select a.production_id, a.production_date, a.production_qty, d.order_item_qty_id, d.item_qty,  \r\n" + 
				"d.uom_id, e.item_id,f.item_name, g.order_id, g.order_date,h.order_owner_type_id,g.order_owner_id, h.owner_name, \r\n" + 
				"i.uom_name, (select if (exists(select 1 from mlfm_packing p where p.PRODUCTION_ID=a.PRODUCTION_ID), 1,0)) packing_data_exists, \r\n" + 
				"(select  if ( exists (select 1 from mlfm_packing p where p.PRODUCTION_ID=a.production_id  \r\n" + 
				"and p.active_status=1 having sum(p.PACKED_QTY) >=a.PRODUCTION_QTY),'C', 'P')) packing_status,p.PACKED_QTY,p.packed_by,  \r\n" + 
				"p.packing_date,c.WO_CHD_ID  from mlfm_production a inner join mlfm_production_plan_chd b on a.production_plan_chd_id=b.production_plan_chd_id\r\n" + 
				"inner join mlfm_production_plan_mst c on b.production_plan_mst_id=c.production_plan_mst_id\r\n" + 
				"inner join mlfm_order_item_qty d on c.order_item_qty_id=d.order_item_qty_id inner join mlfm_order_item e on d.order_item_id=e.item_order_id \r\n" + 
				"inner join mlfm_item f on e.item_id=f.item_id inner join mlfm_order g on e.order_id=g.order_id\r\n" + 
				"inner join mlfm_order_owner h on g.order_owner_id=h.order_owner_id inner join mlfm_uom i on d.uom_id=i.uom_id left OUTER join mlfm_packing p on p.PRODUCTION_ID=a.PRODUCTION_ID where a.active_status=1) a where \r\n" + 
				"a.wo_chd_id is null and a.order_item_qty_id is not null  and packing_status='P'and a.order_owner_id=coalesce(:buyerId,a.order_owner_id)\r\n" + 
				"and a.order_owner_type_id=coalesce(:buyerTypeId,a.order_owner_type_id) and a.item_id=coalesce(:itemId,a.item_id)\r\n" + 
				"and a.order_id=coalesce(:inquiryId,a.order_id) and a.packing_date BETWEEN coalesce(:startDate,a.packing_date) AND coalesce(:endDate,a.packing_date) \r\n" + 
				"and coalesce(a.packed_by,9999999999)=coalesce(:packedById,coalesce(a.packed_by,9999999999)) \r\n" + 
				"group by a.production_id, a.production_date, a.production_qty, a.order_item_qty_id, a.item_qty,\r\n" + 
				"a.uom_id, a.item_id,item_name, a.order_id, a.order_date,a.order_owner_id, a.owner_name, a.uom_name";
		RowMapper<ModelPackingCustom> serviceMapper=new RowMapper<ModelPackingCustom>() {
			public ModelPackingCustom mapRow(ResultSet rs, int rownum) throws SQLException{
				ModelPackingCustom bn=new ModelPackingCustom();
					 Double packingQty= rs.getDouble("PRODUCTION_QTY")-rs.getDouble("PACKED_QTY");
					 
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
							   bn.setPackingStatus(rs.getString("PACKING_STATUS"));					   
							  //bn.setqCQtySum(rs.getDouble("QC_QTY"));
							  //bn.setMendingQtySum(rs.getDouble("MENDED_QTY"));
							  //bn.setDyingQtySum(rs.getDouble("DYING_QTY"));
							  //bn.setFinishedQtySum(rs.getDouble("FINISHED_QTY"));
							    bn.setPackedQtySum(rs.getDouble("PACKED_QTY"));
							    bn.setPackingQty(packingQty);

					
					 return bn;
					
				}
			};

		      MapSqlParameterSource parameters = new MapSqlParameterSource();  
			      parameters.addValue("buyerTypeId", buyerTypeId);
			      System.out.println("buyerTypeId"+ buyerTypeId);
			      parameters.addValue("buyerId", buyerId);
			      System.out.println(" in daoPackingImp, ownerName:" +buyerId);
			      parameters.addValue("packedById", packedById);
			      parameters.addValue("inquiryId", inquiryId);
			      System.out.println("in daoPackingImp, inquiryId: "+inquiryId);
			      parameters.addValue("startDate", startDate);
			      parameters.addValue("endDate", endDate);
			      parameters.addValue("itemId", itemId);
		      

			System.out.println("service mapper ");
			return namedParameterJdbcTemplate.query(qry,parameters,serviceMapper);
	}

	@Override
	public List<ModelPackingCustom> getPendingPackingWODetailsByPackingDate(Long buyerTypeId, Long buyerId,
			Long inquiryId, Long packedById, Date startDate, Date endDate, Long itemId) {
		// TODO Auto-generated method stub
		String qry="select a.production_id, a.production_date, a.production_qty, a.order_item_qty_id, a.item_qty, \r\n" + 
				"a.uom_id, a.item_id,a.item_name, a.order_id, a.order_date,a.order_owner_id, a.owner_name,\r\n" + 
				"a.uom_name, a.packing_status, sum(a.packed_qty) packed_qty,a.WO_CHD_ID,a.packing_date \r\n" + 
				"FROM ( \r\n" + 
				"select a.production_id, a.production_date, a.production_qty, d.order_item_qty_id, d.item_qty,  \r\n" + 
				"d.uom_id, e.item_id,f.item_name, g.order_id, g.order_date,h.order_owner_type_id,g.order_owner_id, h.owner_name, \r\n" + 
				"i.uom_name, (select if (exists(select 1 from mlfm_packing p where p.PRODUCTION_ID=a.PRODUCTION_ID), 1,0)) packing_data_exists, \r\n" + 
				"(select  if ( exists (select 1 from mlfm_packing p where p.PRODUCTION_ID=a.production_id  \r\n" + 
				"and p.active_status=1 having sum(p.PACKED_QTY) >=a.PRODUCTION_QTY),'C', 'P')) packing_status,p.PACKED_QTY,p.packed_by,  \r\n" + 
				"p.packing_date,c.WO_CHD_ID  from mlfm_production a inner join mlfm_production_plan_chd b on a.production_plan_chd_id=b.production_plan_chd_id\r\n" + 
				"inner join mlfm_production_plan_mst c on b.production_plan_mst_id=c.production_plan_mst_id\r\n" + 
				"inner join mlfm_order_item_qty d on c.order_item_qty_id=d.order_item_qty_id inner join mlfm_order_item e on d.order_item_id=e.item_order_id \r\n" + 
				"inner join mlfm_item f on e.item_id=f.item_id inner join mlfm_order g on e.order_id=g.order_id\r\n" + 
				"inner join mlfm_order_owner h on g.order_owner_id=h.order_owner_id inner join mlfm_uom i on d.uom_id=i.uom_id left OUTER join mlfm_packing p on p.PRODUCTION_ID=a.PRODUCTION_ID where a.active_status=1) a where \r\n" + 
				"a.wo_chd_id is not null  and packing_status='P'and a.order_owner_id=coalesce(:buyerId,a.order_owner_id)\r\n" + 
				"and a.order_owner_type_id=coalesce(:buyerTypeId,a.order_owner_type_id) and a.item_id=coalesce(:itemId,a.item_id)\r\n" + 
				"and a.order_id=coalesce(:inquiryId,a.order_id) and a.packing_date BETWEEN coalesce(:startDate,a.packing_date) AND coalesce(:endDate,a.packing_date) \r\n" + 
				"and coalesce(a.packed_by,9999999999)=coalesce(:packedById,coalesce(a.packed_by,9999999999)) \r\n" + 
				"group by a.production_id, a.production_date, a.production_qty, a.order_item_qty_id, a.item_qty,\r\n" + 
				"a.uom_id, a.item_id,item_name, a.order_id, a.order_date,a.order_owner_id, a.owner_name, a.uom_name";
		RowMapper<ModelPackingCustom> serviceMapper=new RowMapper<ModelPackingCustom>() {
			public ModelPackingCustom mapRow(ResultSet rs, int rownum) throws SQLException{
				ModelPackingCustom bn=new ModelPackingCustom();
					 Double packingQty= rs.getDouble("PRODUCTION_QTY")-rs.getDouble("PACKED_QTY");
					 
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
							   bn.setInquiryId(rs.getLong("WO_CHD_ID"));
				   			   System.out.println(rs.getLong("WO_CHD_ID"));
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
							   bn.setPackingStatus(rs.getString("PACKING_STATUS"));					   
							  //bn.setqCQtySum(rs.getDouble("QC_QTY"));
							  //bn.setMendingQtySum(rs.getDouble("MENDED_QTY"));
							  //bn.setDyingQtySum(rs.getDouble("DYING_QTY"));
							  //bn.setFinishedQtySum(rs.getDouble("FINISHED_QTY"));
							    bn.setPackedQtySum(rs.getDouble("PACKED_QTY"));
							    bn.setPackingQty(packingQty);

					
					 return bn;
					
				}
			};

		      MapSqlParameterSource parameters = new MapSqlParameterSource();  
			      parameters.addValue("buyerTypeId", buyerTypeId);
			      System.out.println("buyerTypeId"+ buyerTypeId);
			      parameters.addValue("buyerId", buyerId);
			      System.out.println(" in daoPackingImp, ownerName:" +buyerId);
			      parameters.addValue("packedById", packedById);
			      parameters.addValue("inquiryId", inquiryId);
			      System.out.println("in daoPackingImp, inquiryId: "+inquiryId);
			      parameters.addValue("startDate", startDate);
			      parameters.addValue("endDate", endDate);
			      parameters.addValue("itemId", itemId);
		      

			System.out.println("service mapper ");
			return namedParameterJdbcTemplate.query(qry,parameters,serviceMapper);
	}

	@Override
	public List<ModelPackingCustom> getCompletedPackingOrderDetailsByPackingDate(Long buyerTypeId, Long buyerId,
			Long inquiryId, Long packedById, Date startDate, Date endDate, Long itemId) {
		// TODO Auto-generated method stub
		String qry="select a.production_id, a.production_date, a.production_qty, a.order_item_qty_id, a.item_qty, \r\n" + 
				"a.uom_id, a.item_id,a.item_name, a.order_id, a.order_date,a.order_owner_id, a.owner_name,\r\n" + 
				"a.uom_name, a.packing_status, sum(a.packed_qty) packed_qty,a.packing_date \r\n" + 
				"FROM ( \r\n" + 
				"select a.production_id, a.production_date, a.production_qty, d.order_item_qty_id, d.item_qty,  \r\n" + 
				"d.uom_id, e.item_id,f.item_name, g.order_id, g.order_date,h.order_owner_type_id,g.order_owner_id, h.owner_name, \r\n" + 
				"i.uom_name, (select if (exists(select 1 from mlfm_packing p where p.PRODUCTION_ID=a.PRODUCTION_ID), 1,0)) packing_data_exists, \r\n" + 
				"(select  if ( exists (select 1 from mlfm_packing p where p.PRODUCTION_ID=a.production_id  \r\n" + 
				"and p.active_status=1 having sum(p.PACKED_QTY) >=a.PRODUCTION_QTY),'C', 'P')) packing_status,p.PACKED_QTY,p.packed_by,  \r\n" + 
				"p.packing_date,c.WO_CHD_ID  from mlfm_production a inner join mlfm_production_plan_chd b on a.production_plan_chd_id=b.production_plan_chd_id\r\n" + 
				"inner join mlfm_production_plan_mst c on b.production_plan_mst_id=c.production_plan_mst_id\r\n" + 
				"inner join mlfm_order_item_qty d on c.order_item_qty_id=d.order_item_qty_id inner join mlfm_order_item e on d.order_item_id=e.item_order_id \r\n" + 
				"inner join mlfm_item f on e.item_id=f.item_id inner join mlfm_order g on e.order_id=g.order_id\r\n" + 
				"inner join mlfm_order_owner h on g.order_owner_id=h.order_owner_id inner join mlfm_uom i on d.uom_id=i.uom_id left OUTER join mlfm_packing p on p.PRODUCTION_ID=a.PRODUCTION_ID where a.active_status=1) a where \r\n" + 
				"a.wo_chd_id is null and a.order_item_qty_id is not null  and packing_status='C'and a.order_owner_id=coalesce(:buyerId,a.order_owner_id)\r\n" + 
				"and a.order_owner_type_id=coalesce(:buyerTypeId,a.order_owner_type_id) and a.item_id=coalesce(:itemId,a.item_id)\r\n" + 
				"and a.order_id=coalesce(:inquiryId,a.order_id) and a.packing_date BETWEEN coalesce(:startDate,a.packing_date) AND coalesce(:endDate,a.packing_date) \r\n" + 
				"and coalesce(a.packed_by,9999999999)=coalesce(:packedById,coalesce(a.packed_by,9999999999)) \r\n" + 
				"group by a.production_id, a.production_date, a.production_qty, a.order_item_qty_id, a.item_qty,\r\n" + 
				"a.uom_id, a.item_id,item_name, a.order_id, a.order_date,a.order_owner_id, a.owner_name, a.uom_name";
		RowMapper<ModelPackingCustom> serviceMapper=new RowMapper<ModelPackingCustom>() {
			public ModelPackingCustom mapRow(ResultSet rs, int rownum) throws SQLException{
				ModelPackingCustom bn=new ModelPackingCustom();
					 Double packingQty= rs.getDouble("PRODUCTION_QTY")-rs.getDouble("PACKED_QTY");
					 
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
							   bn.setPackingStatus(rs.getString("PACKING_STATUS"));					   
							  //bn.setqCQtySum(rs.getDouble("QC_QTY"));
							  //bn.setMendingQtySum(rs.getDouble("MENDED_QTY"));
							  //bn.setDyingQtySum(rs.getDouble("DYING_QTY"));
							  //bn.setFinishedQtySum(rs.getDouble("FINISHED_QTY"));
							    bn.setPackedQtySum(rs.getDouble("PACKED_QTY"));
							    bn.setPackingQty(packingQty);

					
					 return bn;
					
				}
			};

		      MapSqlParameterSource parameters = new MapSqlParameterSource();  
			      parameters.addValue("buyerTypeId", buyerTypeId);
			      System.out.println("buyerTypeId"+ buyerTypeId);
			      parameters.addValue("buyerId", buyerId);
			      System.out.println(" in daoPackingImp, ownerName:" +buyerId);
			      parameters.addValue("packedById", packedById);
			      parameters.addValue("inquiryId", inquiryId);
			      System.out.println("in daoPackingImp, inquiryId: "+inquiryId);
			      parameters.addValue("startDate", startDate);
			      parameters.addValue("endDate", endDate);
			      parameters.addValue("itemId", itemId);
		      

			System.out.println("service mapper ");
			return namedParameterJdbcTemplate.query(qry,parameters,serviceMapper);
	}

	@Override
	public List<ModelPackingCustom> getCompletedPackingWODetailsByPackingDate(Long buyerTypeId, Long buyerId,
			Long inquiryId, Long packedById, Date startDate, Date endDate, Long itemId) {
		// TODO Auto-generated method stub
		String qry="select a.production_id, a.production_date, a.production_qty, a.order_item_qty_id, a.item_qty, \r\n" + 
				"a.uom_id, a.item_id,a.item_name, a.order_id, a.order_date,a.order_owner_id, a.owner_name,\r\n" + 
				"a.uom_name, a.packing_status, sum(a.packed_qty) packed_qty,a.WO_CHD_ID,a.packing_date \r\n" + 
				"FROM ( \r\n" + 
				"select a.production_id, a.production_date, a.production_qty, d.order_item_qty_id, d.item_qty,  \r\n" + 
				"d.uom_id, e.item_id,f.item_name, g.order_id, g.order_date,h.order_owner_type_id,g.order_owner_id, h.owner_name, \r\n" + 
				"i.uom_name, (select if (exists(select 1 from mlfm_packing p where p.PRODUCTION_ID=a.PRODUCTION_ID), 1,0)) packing_data_exists, \r\n" + 
				"(select  if ( exists (select 1 from mlfm_packing p where p.PRODUCTION_ID=a.production_id  \r\n" + 
				"and p.active_status=1 having sum(p.PACKED_QTY) >=a.PRODUCTION_QTY),'C', 'P')) packing_status,p.PACKED_QTY,p.packed_by,  \r\n" + 
				"p.packing_date,c.WO_CHD_ID  from mlfm_production a inner join mlfm_production_plan_chd b on a.production_plan_chd_id=b.production_plan_chd_id\r\n" + 
				"inner join mlfm_production_plan_mst c on b.production_plan_mst_id=c.production_plan_mst_id\r\n" + 
				"inner join mlfm_order_item_qty d on c.order_item_qty_id=d.order_item_qty_id inner join mlfm_order_item e on d.order_item_id=e.item_order_id \r\n" + 
				"inner join mlfm_item f on e.item_id=f.item_id inner join mlfm_order g on e.order_id=g.order_id\r\n" + 
				"inner join mlfm_order_owner h on g.order_owner_id=h.order_owner_id inner join mlfm_uom i on d.uom_id=i.uom_id left OUTER join mlfm_packing p on p.PRODUCTION_ID=a.PRODUCTION_ID where a.active_status=1) a where \r\n" + 
				"a.wo_chd_id is not null  and packing_status='C'and a.order_owner_id=coalesce(:buyerId,a.order_owner_id)\r\n" + 
				"and a.order_owner_type_id=coalesce(:buyerTypeId,a.order_owner_type_id) and a.item_id=coalesce(:itemId,a.item_id)\r\n" + 
				"and a.order_id=coalesce(:inquiryId,a.order_id) and a.packing_date BETWEEN coalesce(:startDate,a.packing_date) AND coalesce(:endDate,a.packing_date) \r\n" + 
				"and coalesce(a.packed_by,9999999999)=coalesce(:packedById,coalesce(a.packed_by,9999999999)) \r\n" + 
				"group by a.production_id, a.production_date, a.production_qty, a.order_item_qty_id, a.item_qty,\r\n" + 
				"a.uom_id, a.item_id,item_name, a.order_id, a.order_date,a.order_owner_id, a.owner_name, a.uom_name";
		RowMapper<ModelPackingCustom> serviceMapper=new RowMapper<ModelPackingCustom>() {
			public ModelPackingCustom mapRow(ResultSet rs, int rownum) throws SQLException{
				ModelPackingCustom bn=new ModelPackingCustom();
					 Double packingQty= rs.getDouble("PRODUCTION_QTY")-rs.getDouble("PACKED_QTY");
					 
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
							   bn.setInquiryId(rs.getLong("WO_CHD_ID"));
				   			   System.out.println(rs.getLong("WO_CHD_ID"));
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
							   bn.setPackingStatus(rs.getString("PACKING_STATUS"));					   
							  //bn.setqCQtySum(rs.getDouble("QC_QTY"));
							  //bn.setMendingQtySum(rs.getDouble("MENDED_QTY"));
							  //bn.setDyingQtySum(rs.getDouble("DYING_QTY"));
							  //bn.setFinishedQtySum(rs.getDouble("FINISHED_QTY"));
							    bn.setPackedQtySum(rs.getDouble("PACKED_QTY"));
							    bn.setPackingQty(packingQty);

					
					 return bn;
					
				}
			};

		      MapSqlParameterSource parameters = new MapSqlParameterSource();  
			      parameters.addValue("buyerTypeId", buyerTypeId);
			      System.out.println("buyerTypeId"+ buyerTypeId);
			      parameters.addValue("buyerId", buyerId);
			      System.out.println(" in daoPackingImp, ownerName:" +buyerId);
			      parameters.addValue("packedById", packedById);
			      parameters.addValue("inquiryId", inquiryId);
			      System.out.println("in daoPackingImp, inquiryId: "+inquiryId);
			      parameters.addValue("startDate", startDate);
			      parameters.addValue("endDate", endDate);
			      parameters.addValue("itemId", itemId);
		      

			System.out.println("service mapper ");
			return namedParameterJdbcTemplate.query(qry,parameters,serviceMapper);
	}

}
