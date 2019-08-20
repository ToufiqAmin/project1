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

import com.biziitech.mlfm.custom.model.ModelQCCustom;
import com.biziitech.mlfm.dao.DaoQC;
import com.biziitech.mlfm.model.ModelQC;
import com.biziitech.mlfm.repository.QCRepository;

@Service
public class DaoQCImp implements DaoQC{
	
	@Autowired
	private QCRepository qCRepository;
	
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
	public List<ModelQCCustom> getQCById(Long id) {
		
		String qry="select q.qc_id,q.qc_date,q.qc_qty, q.qc_by,q.active_status,\r\n" + 
				"q.qc_plan_id,q.bed_no,q.mending_required_qty,q.remarks,\r\n" + 
				"q.UOM_ID,a.uom_name,u.user_id,u.user_name,b.qc_plan_date,b.qc_plan_qty from mlfm_qc q\r\n" + 
				"left join bg_user u on q.qc_by=u.user_id\r\n" + 
				"LEFT OUTER JOIN mlfm_uom a on q.UOM_ID=a.uom_id\r\n" +
				"LEFT OUTER JOIN mlfm_qc_plan b on q.qc_plan_id=b.qc_plan_id\r\n" +
				"where q.qc_plan_id=coalesce(:id,q.qc_plan_id)\r\n" + 
				"ORDER BY q.qc_date DESC";
		RowMapper<ModelQCCustom> serviceMapper=new RowMapper<ModelQCCustom>() {
			
			
			public ModelQCCustom mapRow(ResultSet rs, int rownum) throws SQLException{
			ModelQCCustom bn=new ModelQCCustom();
			
			Double qCQtyMax=rs.getDouble("QC_PLAN_QTY");
			
			 bn.setqCId(rs.getLong("QC_ID"));
			 bn.setqCDate(rs.getDate("QC_DATE"));
			 System.out.println(rs.getDate("QC_DATE"));
			 bn.setqCQty(rs.getDouble("QC_QTY"));
			 System.out.println(rs.getDouble("QC_QTY"));
			 bn.setqCBy(rs.getLong("QC_BY"));
			 System.out.println(rs.getLong("QC_BY"));
			 bn.setActiveStatus(rs.getInt("ACTIVE_STATUS"));
			 bn.setUserId(rs.getLong("USER_ID"));
			 bn.setUserName(rs.getString("USER_NAME"));
			 bn.setqCRemarks(rs.getString("REMARKS"));
			 bn.setMendingRequiredQty(rs.getDouble("MENDING_REQUIRED_QTY"));
			 bn.setBedNo(rs.getLong("BED_NO"));
			 bn.setqCPlanId(rs.getLong("QC_PLAN_ID"));
			 bn.setUomId(rs.getLong("UOM_ID"));
			 bn.setUomName(rs.getString("UOM_NAME"));
			 bn.setqCQtyMax(qCQtyMax);
			 
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
	      System.out.println(" in daoQCImp, id:" +id);

		System.out.println("service mapper ");
		return namedParameterJdbcTemplate.query(qry,parameters,serviceMapper);
	
	}
	
	@Override
	public void saveQC(ModelQC modelQC) {
		// TODO Auto-generated method stub

			

		qCRepository.save(modelQC);
	}
	
	@Override
	public void deleteQC(Long qCId) {
		// TODO Auto-generated method stub
		qCRepository.deleteById(qCId);
	}
	
	@Override
	public List<ModelQCCustom> getQCDoneById(Long id) {
		// TODO Auto-generated method stub
		String qry="select q.qc_id,q.qc_date,q.qc_qty, q.qc_by,q.active_status,q.remarks, u.user_id, u.user_name from mlfm_qc q  \r\n" + 			
				"left join bg_user u on q.qc_by=u.user_id  \r\n" + 
				"where q.qc_id=coalesce(:id,q.qc_id)\r\n" + 
				"and q.active_status=1 ORDER BY q.qc_date DESC";
		RowMapper<ModelQCCustom> serviceMapper=new RowMapper<ModelQCCustom>() {
			
			
			public ModelQCCustom mapRow(ResultSet rs, int rownum) throws SQLException{
			ModelQCCustom bn=new ModelQCCustom();
			bn.setqCId(rs.getLong("QC_ID"));
			 bn.setqCDate(rs.getDate("QC_DATE"));
			 System.out.println(rs.getDate("QC_DATE"));
			 bn.setqCQty(rs.getDouble("QC_QTY"));
			 System.out.println(rs.getDouble("QC_QTY"));
			 bn.setqCBy(rs.getLong("QC_BY"));
			 System.out.println(rs.getLong("QC_BY"));
			 bn.setActiveStatus(rs.getInt("ACTIVE_STATUS"));
			 bn.setUserId(rs.getLong("USER_ID"));
			 bn.setUserName(rs.getString("USER_NAME"));
			 bn.setqCRemarks(rs.getString("REMARKS"));
			 
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
	      System.out.println(" in daoQCImp, id:" +id);
	     

		System.out.println("service mapper ");
		return namedParameterJdbcTemplate.query(qry,parameters,serviceMapper);
	}
	
	

	@Override
	public List<ModelQCCustom> getPendingQCOrderDetails(Long buyerId,Long inquiryId,  Long qCById, Date startDate, Date endDate, Long itemId) {
		// TODO Auto-generated method stub

		
		String qry="select a.qc_plan_id, a.qc_plan_date, a.qc_plan_qty,a.uom_id,a.uom_name,a.po_id,a.po_date,\r\n" + 
				"a.order_id,a.order_date,a.item_id,a.item_name,a.order_owner_id,a.owner_name,\r\n" + 
				"a.qc_id,a.qc_date,a.qc_qty,a.qc_by,a.bed_no,a.mending_required_qty\r\n" + 
				"\r\n" + 
				"FROM (\r\n" + 
				"\r\n" + 
				"select a.qc_plan_id, a.qc_plan_date, a.qc_plan_qty,a.uom_id,g.uom_name,a.po_id,b.po_date,\r\n" + 
				"d.order_id,d.order_date,e.item_id,f.item_name,d.order_owner_id,h.owner_name,\r\n" + 
				"n.qc_id,n.qc_date,n.qc_qty,n.qc_by,n.bed_no,n.mending_required_qty\r\n" + 
				"\r\n" + 
				"from mlfm_qc_plan a \r\n" + 
				"\r\n" + 
				"inner join mlfm_po b on a.po_id=b.po_id\r\n" + 
				"inner join mlfm_design c on b.design_id=c.design_id\r\n" + 
				"inner join mlfm_order d on c.order_id=d.order_id\r\n" + 
				"inner join mlfm_order_item e on d.order_id=e.order_id\r\n" + 
				"inner join mlfm_item f on e.item_id=f.item_id\r\n" + 
				//"inner join mlfm_uom g on a.uom_id=g.uom_id\r\n" + 
				"LEFT OUTER join mlfm_uom g on a.uom_id=g.uom_id\r\n" + 
				"inner join mlfm_order_owner h on d.order_owner_id=h.order_owner_id\r\n" + 
				"inner join mlfm_production_plan_chd i on b.production_plan_chd_id=i.production_plan_chd_id\r\n" + 
				"left outer join mlfm_qc n on a.qc_plan_id=n.qc_plan_id\r\n" + 
				"\r\n" + 
				") a "+
				"where a.order_owner_id=coalesce(:buyerId,a.order_owner_id) and a.item_id=coalesce(:itemId,a.item_id) and a.order_id=coalesce(:inquiryId,a.order_id) \r\n" + 
				"and a.qc_plan_date BETWEEN coalesce(:startDate,a.qc_plan_date)\r\n" + 
				" AND coalesce(:endDate,a.qc_plan_date) and coalesce(a.qc_by,9999999999)=coalesce(:qCById,coalesce(a.qc_by,9999999999))\r\n"  + 
				"group by a.qc_plan_id, a.qc_plan_date, a.qc_plan_qty,a.uom_id,a.uom_name,a.po_id,a.po_date,\r\n" + 
				"a.order_id,a.order_date,a.item_id,a.item_name,a.order_owner_id,a.owner_name,\r\n" + 
				"a.qc_id,a.qc_date,a.qc_qty,a.qc_by,a.bed_no,a.mending_required_qty";
			
			/*
		String qry ="select a.qc_plan_id, a.qc_plan_date, a.qc_plan_qty,a.uom_id,g.uom_name,a.po_id,b.po_date,\r\n" + 
				"d.order_id,d.order_date,e.item_id,f.item_name,d.order_owner_id,h.owner_name,\r\n" + 
				"n.qc_id,n.qc_date,n.qc_qty,n.qc_by,n.bed_no,n.mending_required_qty\r\n" + 
				"from mlfm_qc_plan a \r\n" + 
				"inner join mlfm_po b on a.po_id=b.po_id \r\n" + 
				"inner join mlfm_design c on b.design_id=c.design_id\r\n" + 
				"inner join mlfm_order d on c.order_id=d.order_id \r\n" + 
				"inner join mlfm_order_item e on d.order_id=e.order_id \r\n" + 
				"inner join mlfm_item f on e.item_id=f.item_id  \r\n" + 
				"LEFT OUTER join mlfm_uom g on a.uom_id=g.uom_id \r\n" + 
				"inner join mlfm_order_owner h on d.order_owner_id=h.order_owner_id  \r\n" + 
				"inner join mlfm_production_plan_chd i on b.production_plan_chd_id=i.production_plan_chd_id \r\n" + 
				"left outer join mlfm_qc n on a.qc_plan_id=n.qc_plan_id\r\n" + 
				"where h.order_owner_id=coalesce(@buyerId,h.order_owner_id) \r\n" + 
				"and f.item_id=coalesce(@itemId,f.item_id) \r\n" + 
				"and d.order_id=coalesce(@inquiryId,d.order_id)\r\n" + 
				"and a.qc_plan_date BETWEEN coalesce(@startDate,a.qc_plan_date) AND coalesce(@endDate,a.qc_plan_date)";
		*/
		
		/*
		 * select a.qc_plan_id, a.qc_plan_date, a.qc_plan_qty,a.uom_id,a.uom_name,a.po_id,a.po_date,a.order_id,
a.order_date,a.item_id,a.item_name,a.order_owner_id,a.owner_name,a.qc_id,a.qc_date,a.qc_qty,a.qc_by,
a.bed_no,a.mending_required_qty 
FROM (select a.qc_plan_id, a.qc_plan_date, a.qc_plan_qty,a.uom_id,g.uom_name,a.po_id,b.po_date,
d.order_id,d.order_date,e.item_id,f.item_name,d.order_owner_id,h.owner_name,
n.qc_id,n.qc_date,n.qc_qty,n.qc_by,n.bed_no,n.mending_required_qty
from mlfm_qc_plan a 
inner join mlfm_po b on a.po_id=b.po_id 
inner join mlfm_design c on b.design_id=c.design_id
inner join mlfm_order d on c.order_id=d.order_id 
inner join mlfm_order_item e on d.order_id=e.order_id 
inner join mlfm_item f on e.item_id=f.item_id  
LEFT OUTER join mlfm_uom g on a.uom_id=g.uom_id 
inner join mlfm_order_owner h on d.order_owner_id=h.order_owner_id  
inner join mlfm_production_plan_chd i on b.production_plan_chd_id=i.production_plan_chd_id 
left outer join mlfm_qc n on a.qc_plan_id=n.qc_plan_id ) a
where a.order_owner_id=coalesce(@buyerId,a.order_owner_id) and a.item_id=coalesce(@itemId,a.item_id) and a.order_id=coalesce(@inquiryId,a.order_id)
and a.qc_plan_date BETWEEN coalesce(@startDate,a.qc_plan_date) 
AND coalesce(@endDate,a.qc_plan_date) and coalesce(a.qc_by,9999999999)=coalesce(@qCById,coalesce(a.qc_by,9999999999)) 
group by a.qc_plan_id, a.qc_plan_date, a.qc_plan_qty,a.uom_id,a.uom_name,a.po_id,a.po_date,
a.order_id,a.order_date,a.item_id,a.item_name,a.order_owner_id,a.owner_name,
a.qc_id,a.qc_date,a.qc_qty,a.qc_by,a.bed_no,a.mending_required_qty
		 * 
		 * */
					
		RowMapper<ModelQCCustom> serviceMapper=new RowMapper<ModelQCCustom>() {
				public ModelQCCustom mapRow(ResultSet rs, int rownum) throws SQLException{
				ModelQCCustom bn=new ModelQCCustom();
				
				Double qCQtyMax=rs.getDouble("QC_PLAN_QTY")-rs.getDouble("QC_QTY");
			
						
							 
							   
							   bn.setqCPlanId(rs.getLong("QC_PLAN_ID"));
							   bn.setqCPlanDate(rs.getDate("QC_PLAN_DATE"));
							   bn.setqCPlanQty(rs.getDouble("QC_PLAN_QTY"));
								
							   bn.setpOId(rs.getLong("PO_ID"));
							   System.out.println(rs.getLong("PO_ID"));
						       bn.setpODate(rs.getDate("PO_DATE"));;
							   System.out.println(rs.getDate("PO_DATE"));
							   
							   
							   bn.setqCId(rs.getLong("QC_ID"));
							   bn.setqCDate(rs.getDate("QC_DATE"));
							   bn.setqCQty(rs.getDouble("QC_QTY"));
							   
							   
							   bn.setMendingRequiredQty(rs.getDouble("MENDING_REQUIRED_QTY"));
							   bn.setBedNo(rs.getLong("BED_NO"));
							   
							
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
							   bn.setqCQtyMax(qCQtyMax);
							  
							   return bn;
							}

				};
				
				MapSqlParameterSource parameters = new MapSqlParameterSource();
			
				  parameters.addValue("buyerId", buyerId);
			      System.out.println(" in daoQCImp, ownerName:" +buyerId);
			      parameters.addValue("qCById", qCById);
			      parameters.addValue("inquiryId", inquiryId);
			      System.out.println("in daoQCImp, inquiryId: "+inquiryId);
			      parameters.addValue("startDate", startDate);
			      parameters.addValue("endDate", endDate);
			      parameters.addValue("itemId", itemId);

				System.out.println("service mapper ");
				return namedParameterJdbcTemplate.query(qry,parameters,serviceMapper);
				
		
		
		

			}

	@Override
	public List<ModelQCCustom> getPendingQCWODetails(Long buyerId,Long inquiryId, Long qCById, Date startDate, Date endDate, Long itemId) {
		// TODO Auto-generated method stub
		String qry="select a.qc_plan_id, a.qc_plan_date, a.qc_plan_qty,a.uom_id,a.uom_name,a.PO_MST_ID,a.PO_MST_DATE,a.po_qty,\r\n" + 
				"a.wo_chd_id,a.wo_mst_id,a.wo_date,a.item_id,a.item_name,a.order_owner_id,a.owner_name,a.production_id,\r\n" + 
				"a.qc_id,a.qc_date,a.qc_qty,a.qc_by,a.bed_no,a.mending_required_qty\r\n" + 
				"FROM (\r\n" + 
				"select a.qc_plan_id, a.qc_plan_date, a.qc_plan_qty,a.uom_id,c.uom_name,a.PO_MST_ID,b.PO_MST_DATE,o.po_qty,\r\n" + 
				"e.wo_chd_id,f.wo_mst_id,g.wo_date,j.item_id,k.item_name,i.order_owner_id,l.owner_name,m.production_id,\r\n" + 
				"n.qc_id,n.qc_date,n.qc_qty,n.qc_by,n.bed_no,n.mending_required_qty\r\n" + 
				"from mlfm_qc_plan a \r\n" + 
				"inner join mlfm_po_mst b on a.PO_MST_ID = b.PO_MST_ID\r\n" + 
				"INNER JOIN mlfm_po_chd o on b.PO_MST_ID=o.PO_MST_ID\r\n" + 
				"inner join mlfm_uom c on a.uom_id=c.uom_id\r\n" + 
				"inner join mlfm_production_plan_chd d on o.production_plan_chd_id=d.production_plan_chd_id\r\n" + 
				"inner join mlfm_production_plan_mst e on d.production_plan_mst_id=e.production_plan_mst_id\r\n" + 
				"inner join mlfm_wo_chd f on e.wo_chd_id=f.wo_chd_id\r\n" + 
				"inner join mlfm_wo_mst g on f.wo_mst_id=g.wo_mst_id\r\n" + 
				"inner join mlfm_design h on b.design_id=h.design_id\r\n" + 
				"inner join mlfm_order i on h.order_id=i.order_id\r\n" + 
				"inner join mlfm_order_item j on i.order_id=j.order_id\r\n" + 
				"inner join mlfm_item k on j.item_id=k.item_id\r\n" + 
				"inner join mlfm_order_owner l on i.order_owner_id=l.order_owner_id\r\n" + 
				"inner join mlfm_production m on d.production_plan_chd_id=m.production_plan_chd_id\r\n" + 
				"left outer join mlfm_qc n on a.qc_plan_id=n.qc_plan_id\r\n" + 
				") a where \r\n" + 
				"a.order_owner_id=coalesce(:buyerId,a.order_owner_id) and a.item_id=coalesce(:itemId,a.item_id) and a.PO_MST_ID=coalesce(:inquiryId,a.PO_MST_ID)\r\n" + 
				"and coalesce(a.qc_by,9999999999)=coalesce(:qCById,coalesce(a.qc_by,9999999999)) and a.qc_plan_date BETWEEN coalesce(:startDate,a.qc_plan_date)\r\n" + 
				"AND coalesce(:endDate,a.qc_plan_date)\r\n" + 
				"group by a.qc_plan_id, a.qc_plan_date, a.qc_plan_qty,a.uom_id,a.uom_name,a.PO_MST_ID,a.PO_MST_DATE,a.po_qty,\r\n" + 
				"a.wo_chd_id,a.wo_mst_id,a.wo_date,a.item_id,a.item_name,a.order_owner_id,a.owner_name,a.production_id,\r\n" + 
				"a.qc_id,a.qc_date,a.qc_qty,a.qc_by,a.bed_no,a.mending_required_qty";
		
		RowMapper<ModelQCCustom> serviceMapper=new RowMapper<ModelQCCustom>() {
			public ModelQCCustom mapRow(ResultSet rs, int rownum) throws SQLException{
			ModelQCCustom bn=new ModelQCCustom();
			
			Double qCQtyMax=rs.getDouble("QC_PLAN_QTY")-rs.getDouble("QC_QTY");
		
			
		
					bn.setqCPlanId(rs.getLong("QC_PLAN_ID"));
					bn.setqCPlanDate(rs.getDate("QC_PLAN_DATE"));
					bn.setqCPlanQty(rs.getDouble("QC_PLAN_QTY"));
					
					bn.setqCId(rs.getLong("QC_ID"));
					bn.setqCDate(rs.getDate("QC_DATE"));
					bn.setqCQty(rs.getDouble("QC_QTY"));
					
					bn.setpOId(rs.getLong("PO_MST_ID"));
					System.out.println(rs.getLong("PO_MST_ID"));
					bn.setpODate(rs.getDate("PO_MST_DATE"));;
					System.out.println(rs.getDate("PO_MST_DATE"));
					

					
					bn.setMendingRequiredQty(rs.getDouble("MENDING_REQUIRED_QTY"));
					bn.setBedNo(rs.getLong("BED_NO"));
						   
					bn.setwOChdId(rs.getLong("WO_CHD_ID"));
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
				    bn.setqCQtyMax(qCQtyMax);
						  

				    return bn;
				}

			};
						
			  MapSqlParameterSource parameters = new MapSqlParameterSource();
			  parameters.addValue("buyerId", buyerId);
		      System.out.println(" in daoQCImp, ownerName:" +buyerId);
		      parameters.addValue("qCById", qCById);
		      parameters.addValue("inquiryId", inquiryId);
		      System.out.println("in daoQCImp, inquiryId: "+inquiryId);
		      parameters.addValue("startDate", startDate);
		      parameters.addValue("endDate", endDate);
		      parameters.addValue("itemId", itemId);
		
		    System.out.println("service mapper ");
			return namedParameterJdbcTemplate.query(qry,parameters,serviceMapper);
	}



	@Override
	public List<ModelQCCustom> getCompletedQCOrderDetails(Long buyerId, Long inquiryId, Long qCById, Date startDate, Date endDate, Long itemId) {
		// TODO Auto-generated method stub
		String qry="select a.qc_plan_id, a.qc_plan_date, a.qc_plan_qty,a.uom_id,a.uom_name,a.po_id,a.po_date,\r\n" + 
				"a.order_id,a.order_date,a.item_id,a.item_name,a.order_owner_id,a.owner_name,\r\n" + 
				"a.qc_id,a.qc_date,a.qc_qty,a.qc_by,a.bed_no,a.mending_required_qty\r\n" + 
				"\r\n" + 
				"FROM (\r\n" + 
				"\r\n" + 
				"select a.qc_id,a.qc_date,a.qc_qty,a.qc_by,a.mending_required_qty,a.bed_no,\r\n" + 
				"a.qc_plan_id,b.qc_plan_date,b.qc_plan_qty,b.uom_id,h.uom_name,b.po_id,c.po_date,\r\n" + 
				"d.order_id,e.order_date,f.item_id,g.item_name,e.order_owner_id,i.owner_name\r\n" + 
				"\r\n" + 
				"from mlfm_qc a \r\n" + 
				"inner join mlfm_qc_plan b on a.qc_plan_id=b.qc_plan_id\r\n" + 
				"inner join mlfm_po c on b.po_id=c.po_id\r\n" + 
				"inner join mlfm_design d on c.design_id=d.design_id\r\n" + 
				"inner join mlfm_order e on d.order_id=e.order_id\r\n" + 
				"inner join mlfm_order_item f on e.order_id=f.order_id\r\n" + 
				"inner join mlfm_item g on f.item_id=g.item_id\r\n" + 
				"inner join mlfm_uom h on b.uom_id=h.uom_id\r\n" + 
				"inner join mlfm_order_owner i on e.order_owner_id=i.order_owner_id\r\n" + 
				"inner join mlfm_production_plan_chd j on c.production_plan_chd_id=j.production_plan_chd_id\r\n" + 
				"\r\n" + 
				") a \r\n" + 
				"where a.order_owner_id=coalesce(:buyerId,a.order_owner_id) and a.item_id=coalesce(:itemId,a.item_id) and a.order_id=coalesce(:inquiryId,a.order_id) \r\n" + 
				"and coalesce(a.qc_by,9999999999)=coalesce(:qCById,coalesce(a.qc_by,9999999999)) and a.qc_date BETWEEN coalesce(:startDate,a.qc_date)\r\n" + 
				"AND coalesce(:endDate,a.qc_date)\r\n" + 
				"group by a.qc_plan_id, a.qc_plan_date, a.qc_plan_qty,a.uom_id,a.uom_name,a.po_id,a.po_date,\r\n" + 
				"a.order_id,a.order_date,a.item_id,a.item_name,a.order_owner_id,a.owner_name,\r\n" + 
				"a.qc_id,a.qc_date,a.qc_qty,a.qc_by,a.bed_no,a.mending_required_qty";
		
		RowMapper<ModelQCCustom> serviceMapper=new RowMapper<ModelQCCustom>() {
			public ModelQCCustom mapRow(ResultSet rs, int rownum) throws SQLException{
			ModelQCCustom bn=new ModelQCCustom();
			
			Double qCQtyMax=rs.getDouble("QC_PLAN_QTY")-rs.getDouble("QC_QTY");
			


	
			   
			   bn.setqCPlanId(rs.getLong("QC_PLAN_ID"));
			   bn.setqCPlanDate(rs.getDate("QC_PLAN_DATE"));
			   bn.setqCPlanQty(rs.getDouble("QC_PLAN_QTY"));
				
			   bn.setpOId(rs.getLong("PO_ID"));
			   System.out.println(rs.getLong("PO_ID"));
		       bn.setpODate(rs.getDate("PO_DATE"));;
			   System.out.println(rs.getDate("PO_DATE"));
			   
			   
			   bn.setqCId(rs.getLong("QC_ID"));
			   bn.setqCDate(rs.getDate("QC_DATE"));
			   bn.setqCQty(rs.getDouble("QC_QTY"));
			   
			   
			   bn.setMendingRequiredQty(rs.getDouble("MENDING_REQUIRED_QTY"));
			   bn.setBedNo(rs.getLong("BED_NO"));
			   
			
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
			   bn.setqCQtyMax(qCQtyMax);
			
			return bn;
			
			}
		};

		
		 MapSqlParameterSource parameters = new MapSqlParameterSource();
		  parameters.addValue("buyerId", buyerId);
	      System.out.println(" in daoQCImp, ownerName:" +buyerId);
	      parameters.addValue("qCById", qCById);
	      parameters.addValue("inquiryId", inquiryId);
	      System.out.println("in daoQCImp, inquiryId: "+inquiryId);
	      parameters.addValue("startDate", startDate);
	      parameters.addValue("endDate", endDate);
	      parameters.addValue("itemId", itemId);
	
	    System.out.println("service mapper ");
		return namedParameterJdbcTemplate.query(qry,parameters,serviceMapper);
	}

	@Override
	public List<ModelQCCustom> getCompletedQCWODetails(Long buyerId, Long inquiryId, Long qCById, Date startDate, Date endDate, Long itemId) {
		// TODO Auto-generated method stub
		String qry="select a.qc_plan_id, a.qc_plan_date, a.qc_plan_qty,a.uom_id,a.uom_name,\r\n" + 
				"a.PO_MST_ID,a.PO_MST_DATE,a.PO_QTY,\r\n" + 
				"a.wo_chd_id,a.wo_mst_id,a.wo_date,a.item_id,a.item_name,\r\n" + 
				"a.order_owner_id,a.owner_name,\r\n" + 
				"a.qc_id,a.qc_date,a.qc_qty,a.QC_BY,a.bed_no,a.mending_required_qty\r\n" + 
				"FROM (\r\n" + 
				"select a.qc_plan_id, a.qc_plan_date, a.qc_plan_qty,a.uom_id,c.uom_name,\r\n" + 
				"a.PO_MST_ID,b.PO_MST_DATE,o.PO_QTY,\r\n" + 
				"e.wo_chd_id,f.wo_mst_id,g.wo_date,j.item_id,k.item_name,\r\n" + 
				"i.order_owner_id,l.owner_name,\r\n" + 
				"n.qc_id,n.qc_date,n.qc_qty,n.QC_BY,n.bed_no,n.mending_required_qty\r\n" + 
				"from mlfm_qc n\r\n" + 
				"inner join mlfm_qc_plan a on n.qc_plan_id=a.qc_plan_id\r\n" + 
				"inner join mlfm_po_mst b on a.PO_MST_ID = b.PO_MST_ID\r\n" + 
				"INNER JOIN mlfm_po_chd o on b.PO_MST_ID=o.PO_MST_ID\r\n" + 
				"inner join mlfm_uom c on a.uom_id=c.uom_id\r\n" + 
				"inner join mlfm_production_plan_chd p on o.production_plan_chd_id=p.production_plan_chd_id\r\n" + 
				"inner join mlfm_production_plan_mst e on p.production_plan_mst_id=e.production_plan_mst_id\r\n" + 
				"inner join mlfm_wo_chd f on e.wo_chd_id=f.wo_chd_id\r\n" + 
				"inner join mlfm_wo_mst g on f.wo_mst_id=g.wo_mst_id\r\n" + 
				"inner join mlfm_design h on b.design_id=h.design_id\r\n" + 
				"inner join mlfm_order i on h.order_id=i.order_id\r\n" + 
				"inner join mlfm_order_item j on i.order_id=j.order_id\r\n" + 
				"inner join mlfm_item k on j.item_id=k.item_id\r\n" + 
				"inner join mlfm_order_owner l on i.order_owner_id=l.order_owner_id\r\n" + 
				") a\r\n"+  
				" where a.order_owner_id=coalesce(:buyerId,a.order_owner_id) and a.item_id=coalesce(:itemId,a.item_id) and a.PO_MST_ID=coalesce(:inquiryId,a.PO_MST_ID) \r\n" + 
				"and coalesce(a.qc_by,9999999999)=coalesce(:qCById,coalesce(a.qc_by,9999999999)) and a.qc_date BETWEEN coalesce(:startDate,a.qc_date)\r\n" + 
				"AND coalesce(:endDate,a.qc_date)\r\n" + 
				"group by a.qc_plan_id, a.qc_plan_date, a.qc_plan_qty,a.uom_id,a.uom_name,a.PO_MST_ID,a.PO_MST_DATE,a.PO_QTY,\r\n" + 
				"a.wo_chd_id,a.wo_mst_id,a.wo_date,a.item_id,a.item_name,a.order_owner_id,a.owner_name,\r\n" + 
				"a.qc_id,a.qc_date,a.qc_qty,a.QC_BY,a.bed_no,a.mending_required_qty";
		
			RowMapper<ModelQCCustom> serviceMapper=new RowMapper<ModelQCCustom>() {
			public ModelQCCustom mapRow(ResultSet rs, int rownum) throws SQLException{
			ModelQCCustom bn=new ModelQCCustom();
			
			Double qCQtyMax=rs.getDouble("QC_PLAN_QTY")-rs.getDouble("QC_QTY");
						
			bn.setqCPlanId(rs.getLong("QC_PLAN_ID"));
			bn.setqCPlanDate(rs.getDate("QC_PLAN_DATE"));
			bn.setqCPlanQty(rs.getDouble("QC_PLAN_QTY"));
			
			bn.setqCId(rs.getLong("QC_ID"));
			bn.setqCDate(rs.getDate("QC_DATE"));
			bn.setqCQty(rs.getDouble("QC_QTY"));
			
			bn.setpOId(rs.getLong("PO_MST_ID"));
			System.out.println(rs.getLong("PO_MST_ID"));
			bn.setpODate(rs.getDate("PO_MST_DATE"));;
			System.out.println(rs.getDate("PO_MST_DATE"));
						
			bn.setMendingRequiredQty(rs.getDouble("MENDING_REQUIRED_QTY"));
			bn.setBedNo(rs.getLong("BED_NO"));
				   
			bn.setwOChdId(rs.getLong("WO_CHD_ID"));
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
		    bn.setqCQtyMax(qCQtyMax);

			return bn;
				}
			};

			  MapSqlParameterSource parameters = new MapSqlParameterSource();
			  parameters.addValue("buyerId", buyerId);
		      System.out.println(" in daoQCImp, ownerName:" +buyerId);
		      parameters.addValue("qCById", qCById);
		      parameters.addValue("inquiryId", inquiryId);
		      System.out.println("in daoQCImp, inquiryId: "+inquiryId);
		      parameters.addValue("startDate", startDate);
		      parameters.addValue("endDate", endDate);
		      parameters.addValue("itemId", itemId);

			 	System.out.println("service mapper ");
				return namedParameterJdbcTemplate.query(qry,parameters,serviceMapper);
	}







	


	
}
