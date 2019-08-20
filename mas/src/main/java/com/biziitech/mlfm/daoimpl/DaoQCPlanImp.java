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

import com.biziitech.mlfm.custom.model.ModelQCCustom;
import com.biziitech.mlfm.custom.model.ModelQCPlanCustom;
import com.biziitech.mlfm.dao.DaoQCPlan;
import com.biziitech.mlfm.model.ModelQCPlan;
import com.biziitech.mlfm.repository.QCPlanRepository;


@Service
public class DaoQCPlanImp implements DaoQCPlan{
	
	@Autowired
	private QCPlanRepository qCPlanRepository;
	
	@Autowired
    private DataSource dataSource;
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
    
	
	
    public void setNamedDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.namedParameterJdbcTemplate=namedParameterJdbcTemplate;
          }
	
    /*
    @Autowired
	private NamedParameterJdbcTemplate namedJdbcTemplate;
    public void setNamedDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.namedJdbcTemplate=namedJdbcTemplate;
          }
          
          */
	

	@Override
	public void saveQCPlan(ModelQCPlan modelQCPlan) {
		// TODO Auto-generated method stub
		
		qCPlanRepository.save(modelQCPlan);
		
	}

	@Override
	public List<ModelQCPlanCustom> getPendingQCPlanPODetails(Long pOId,Long buyerId, Date startDate, Date endDate) {
		// TODO Auto-generated method stub
		
		String qry="SELECT a.PO_MST_ID,a.PO_MST_DATE,\r\n" + 
				"a.PO_CHD_ID,a.PO_QTY,a.UOM_ID,a.uom_name,\r\n" + 
				"a.order_owner_id,a.owner_name,a.item_id,a.item_name,\r\n" + 
				"a.QC_PLAN_ID,a.QC_PLAN_DATE,a.QC_PLAN_QTY\r\n" + 
				"FROM (\r\n" + 
				"SELECT a.PO_MST_ID,a.PO_MST_DATE,\r\n" + 
				"b.PO_CHD_ID,b.PO_QTY,b.UOM_ID,c.uom_name,\r\n" + 
				"e.order_owner_id,f.owner_name,g.item_id,h.item_name,\r\n" + 
				"i.QC_PLAN_ID,i.QC_PLAN_DATE,i.QC_PLAN_QTY\r\n" + 
				"FROM mlfm_po_mst a\r\n" + 
				"INNER JOIN mlfm_po_chd b on a.PO_MST_ID=b.PO_MST_ID\r\n" +
				//"INNER JOIN mlfm_uom c on b.UOM_ID=c.uom_id\r\n" + 
				"LEFT OUTER JOIN mlfm_uom c on b.UOM_ID=c.uom_id\r\n" + 
				"INNER JOIN mlfm_design d on a.DESIGN_ID=d.design_id\r\n" + 
				"INNER JOIN mlfm_order e on d.order_id=e.order_id\r\n" + 
				"INNER JOIN mlfm_order_owner f on e.order_owner_id=f.order_owner_id\r\n" + 
				"INNER JOIN mlfm_order_item g on e.order_id=g.order_id\r\n" + 
				"INNER JOIN mlfm_item h on g.item_id=h.item_id\r\n" + 
				"LEFT OUTER JOIN mlfm_qc_plan i on a.PO_MST_ID=i.PO_MST_ID\r\n" + 
				") a\r\n" + 
				"where a.PO_MST_ID=coalesce(:pOId,a.PO_MST_ID) and a.order_owner_id=coalesce(:buyerId,a.order_owner_id)\r\n" + 
				"and a.PO_MST_DATE BETWEEN coalesce(:startDate,a.PO_MST_DATE) AND coalesce(:endDate,a.PO_MST_DATE)\r\n" + 
				"group by a.PO_MST_ID,a.PO_MST_DATE,\r\n" + 
				"a.PO_CHD_ID,a.PO_QTY,a.UOM_ID,a.uom_name,\r\n" + 
				"a.order_owner_id,a.owner_name,a.item_id,a.item_name,\r\n" + 
				"a.QC_PLAN_ID,a.QC_PLAN_DATE,a.QC_PLAN_QTY";
		
		RowMapper<ModelQCPlanCustom> serviceMapper=new RowMapper<ModelQCPlanCustom>() {

			@Override
			public ModelQCPlanCustom mapRow(ResultSet rs, int rownum) throws SQLException {
				// TODO Auto-generated method stub
				ModelQCPlanCustom bn= new ModelQCPlanCustom();
				Double qCPlanQtyUnDone= rs.getDouble("PO_QTY")-rs.getDouble("QC_PLAN_QTY");
				
				
				
				bn.setpOId(rs.getLong("PO_MST_ID"));
				bn.setpODate(rs.getDate("PO_MST_DATE"));
				bn.setpOQty(rs.getDouble("PO_QTY"));
				bn.setItemId(rs.getLong("ITEM_ID"));
				bn.setItemName(rs.getString("ITEM_NAME"));
				bn.setBuyerId(rs.getLong("ORDER_OWNER_ID"));
				bn.setBuyerName(rs.getString("OWNER_NAME"));
				bn.setUomId(rs.getLong("UOM_ID"));
				bn.setUomName(rs.getString("UOM_NAME"));
				bn.setqCPlanQtyUnDone(qCPlanQtyUnDone);
				bn.setqCPlanQty(rs.getDouble("QC_PLAN_QTY"));
				
				return bn;
			}

		};
		
		MapSqlParameterSource parameters = new MapSqlParameterSource();
	      parameters.addValue("pOId", pOId);
	      System.out.println(" in daoQCPlanImp, pOId:" +pOId);
	      parameters.addValue("buyerId", buyerId);
	      System.out.println(" in daoQCPlanImp, buyerId:" +buyerId);
	      parameters.addValue("startDate", startDate);
	      parameters.addValue("endDate", endDate);

		System.out.println("service mapper ");
		return namedParameterJdbcTemplate.query(qry,parameters,serviceMapper);
	}



	@Override
	public List<ModelQCPlanCustom> getCompletedQCPlanPODetails(Long pOId,Long buyerId, Date startDate, Date endDate) {
		// TODO Auto-generated method stub
		
		/*
		String qry="select a.po_id,a.po_date,a.po_qty,a.item_id,a.item_name,a.order_owner_id,a.owner_name,a.QC_PLAN_QTY\r\n" + 
				"\r\n" + 
				"FROM (\r\n" + 
				"select a.po_id,a.po_date,a.po_qty,b.item_id,c.item_name,e.order_owner_id,f.owner_name,\r\n" + 
				"(select if (exists(select * from mlfm_qc_plan q where q.PO_ID=a.PO_ID), 1,0)) qc_plan_data_exists,\r\n" + 
				"(select  if ( exists (select 1 from mlfm_qc_plan q where q.PO_ID=a.PO_ID and q.active_status=1 \r\n" + 
				"having sum(q.QC_PLAN_QTY) >=a.PO_QTY),'C', 'P')) qc_plan_status,q.qc_plan_date,q.QC_PLAN_QTY\r\n" + 
				"from mlfm_po a \r\n" + 
				"inner join mlfm_po_consum_item b on a.po_id=b.po_id inner join mlfm_item c on b.item_id=c.item_id\r\n" + 
				"inner join mlfm_design d on a.design_id = d.design_id inner join mlfm_order e on d.order_id=e.order_id\r\n" + 
				"inner join mlfm_order_owner f on e.order_owner_id=f.order_owner_id\r\n" + 
				"left OUTER join mlfm_qc_plan q on q.PO_ID=a.PO_ID\r\n" + 
				"where a.active_status=1 \r\n" + 
				") a where \r\n" + 
				"qc_plan_status='C' and a.po_id=coalesce(:pOId,a.po_id) \r\n" + 
				"and a.qc_plan_date BETWEEN coalesce(:startDate,a.qc_plan_date) AND coalesce(:endDate,a.qc_plan_date)\r\n" + 
				"group by a.po_id,a.po_date,a.po_qty,a.item_id,a.item_name,a.order_owner_id,a.owner_name";
		*/
		
		String qry="select a.qc_plan_id, a.qc_plan_date,a.qc_plan_qty,a.PO_MST_ID,a.PO_MST_DATE,a.po_qty,\r\n" + 
				"a.item_id,a.item_name,a.order_owner_id,a.owner_name,a.uom_id,a.uom_name\r\n" + 
				"FROM (\r\n" + 
				"select a.qc_plan_id, a.qc_plan_date,a.qc_plan_qty, a.PO_MST_ID,b.PO_MST_DATE,c.po_qty,\r\n" + 
				"oi.item_id,i.item_name,e.order_owner_id,f.owner_name,c.UOM_ID,m.uom_name\r\n" + 
				"from mlfm_qc_plan a\r\n" + 
				"inner join mlfm_po_mst b on b.PO_MST_ID = a.PO_MST_ID\r\n" + 
				"INNER JOIN mlfm_po_chd c on b.PO_MST_ID=c.PO_MST_ID\r\n" + 
				//"INNER JOIN mlfm_uom m on c.UOM_ID=m.uom_id\r\n" +
				"LEFT OUTER JOIN mlfm_uom m on c.UOM_ID=m.uom_id\r\n" +
				"inner join mlfm_design d on b.design_id = d.design_id \r\n" + 
				"inner join mlfm_order e on d.order_id=e.order_id\r\n" + 
				"inner join mlfm_order_item oi on oi.order_id=e.order_id \r\n" + 
				"inner join mlfm_item i on i.item_id=oi.item_id \r\n" + 
				"inner join mlfm_order_item_qty k on oi.item_order_id=k.order_item_id \r\n" + 
				"inner join mlfm_order_owner f on e.order_owner_id=f.order_owner_id \r\n" + 
				") a where \r\n" + 
				"a.PO_MST_ID=coalesce(:pOId,a.PO_MST_ID) and a.order_owner_id=coalesce(:buyerId,a.order_owner_id)\r\n" + 
				"and a.qc_plan_date BETWEEN coalesce(:startDate,a.qc_plan_date) AND coalesce(:endDate,a.qc_plan_date)\r\n" + 
				"group by a.qc_plan_id, a.qc_plan_date,a.qc_plan_qty,a.PO_MST_ID,a.PO_MST_DATE,a.po_qty,\r\n" + 
				"a.item_id,a.item_name,a.order_owner_id,a.owner_name,a.uom_id,a.uom_name";
		
		
		
		
		RowMapper<ModelQCPlanCustom> serviceMapper=new RowMapper<ModelQCPlanCustom>() {

			@Override
			public ModelQCPlanCustom mapRow(ResultSet rs, int rownum) throws SQLException {
				// TODO Auto-generated method stub
				ModelQCPlanCustom bn= new ModelQCPlanCustom();
				
				Double qCPlanQtyUnDone= rs.getDouble("PO_QTY")-rs.getDouble("QC_PLAN_QTY");
				
				bn.setpOId(rs.getLong("PO_MST_ID"));
				bn.setpODate(rs.getDate("PO_MST_DATE"));
				bn.setpOQty(rs.getDouble("PO_QTY"));
				bn.setItemId(rs.getLong("ITEM_ID"));
				bn.setItemName(rs.getString("ITEM_NAME"));
				bn.setBuyerId(rs.getLong("ORDER_OWNER_ID"));
				bn.setBuyerName(rs.getString("OWNER_NAME"));
				bn.setUomId(rs.getLong("UOM_ID"));
				bn.setUomName(rs.getString("UOM_NAME"));
				bn.setqCPlanQtyUnDone(qCPlanQtyUnDone);
				bn.setqCPlanQty(rs.getDouble("QC_PLAN_QTY"));
				
				return bn;
			}

		};
		
		MapSqlParameterSource parameters = new MapSqlParameterSource();
	      parameters.addValue("pOId", pOId);
	      System.out.println(" in daoQCPlanImp, pOId:" +pOId);
	      parameters.addValue("buyerId", buyerId);
	      System.out.println(" in daoQCPlanImp, buyerId:" +buyerId);
	      parameters.addValue("startDate", startDate);
	      parameters.addValue("endDate", endDate);

		System.out.println("service mapper ");
		return namedParameterJdbcTemplate.query(qry,parameters,serviceMapper);
	}



	@Override
	public List<ModelQCPlanCustom> getQCPlanByPOId(Long pOId) {
		// TODO Auto-generated method stub
		String qry="select a.qc_plan_id,a.qc_plan_date,a.PO_MST_ID,b.PO_MST_DATE,c.po_qty,a.qc_plan_qty,a.active_status,\r\n" + 
				"a.remarks,a.uom_id,l.uom_name,oi.item_id,i.item_name,e.order_owner_id,f.owner_name \r\n" + 
				"from MLFM_QC_PLAN a\r\n" + 
				"inner join mlfm_po_mst b on a.PO_MST_ID = b.PO_MST_ID\r\n" + 
				"INNER JOIN mlfm_po_chd c on b.PO_MST_ID=c.PO_MST_ID\r\n" + 
				"inner join mlfm_design d on b.design_id = d.design_id \r\n" + 
				"inner join mlfm_order e on d.order_id=e.order_id\r\n" + 
				"inner join mlfm_order_item oi on oi.order_id=e.order_id\r\n" + 
				"inner join mlfm_item i on i.item_id=oi.item_id \r\n" + 
				"inner join mlfm_uom l on a.UOM_ID=l.uom_id \r\n" + 
				"inner join mlfm_order_owner f on e.order_owner_id=f.order_owner_id\r\n" + 
				"where a.PO_MST_ID=coalesce(:pOId,a.PO_MST_ID) ORDER BY a.qc_plan_date DESC";
		
		RowMapper<ModelQCPlanCustom> serviceMapper=new RowMapper<ModelQCPlanCustom>() {

			@Override
			public ModelQCPlanCustom mapRow(ResultSet rs, int rownum) throws SQLException {
				// TODO Auto-generated method stub
				ModelQCPlanCustom bn= new ModelQCPlanCustom();
				//Double qCPlanQtyUnDone=rs.getDouble("PO_QTY")-rs.getDouble("QC_PLAN_QTY");
				Double qCPlanQtyUnDone=rs.getDouble("PO_QTY");
				
				bn.setpOId(rs.getLong("PO_MST_ID"));
				bn.setpODate(rs.getDate("PO_MST_DATE"));
				bn.setpOQty(rs.getDouble("PO_QTY"));
				bn.setqCPlanDate(rs.getDate("QC_PLAN_DATE"));
				System.out.println(rs.getDate("QC_PLAN_DATE"));
				bn.setqCPlanQty(rs.getDouble("QC_PLAN_QTY"));
				bn.setItemId(rs.getLong("ITEM_ID"));
				bn.setItemName(rs.getString("ITEM_NAME"));
				bn.setqCPlanId(rs.getLong("QC_PLAN_ID"));
				bn.setActiveStatus(rs.getInt("ACTIVE_STATUS"));
				System.out.println(rs.getInt("ACTIVE_STATUS"));
				bn.setqCPlanRemarks(rs.getString("REMARKS"));
				bn.setBuyerId(rs.getLong("ORDER_OWNER_ID"));
				bn.setBuyerName(rs.getString("OWNER_NAME"));
				bn.setUomId(rs.getLong("UOM_ID"));
				bn.setUomName(rs.getString("UOM_NAME"));
				bn.setqCPlanQtyUnDone(qCPlanQtyUnDone);
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
	      parameters.addValue("pOId", pOId);
	      System.out.println(" in daoQCPlanImp, pOId:" +pOId);
	
		
			System.out.println("service mapper ");
			return namedParameterJdbcTemplate.query(qry,parameters,serviceMapper);
	}



	@Override
	public List<ModelQCPlanCustom> getQCPlanByQCPlanId(Long qCPlanId) {
		// TODO Auto-generated method stub
		String qry="select a.qc_plan_id,a.qc_plan_date,a.PO_MST_ID,b.PO_MST_DATE,c.po_qty,a.qc_plan_qty,a.active_status,\r\n" + 
				"a.remarks,a.uom_id,l.uom_name,oi.item_id,i.item_name,e.order_owner_id,f.owner_name \r\n" + 
				"from MLFM_QC_PLAN a\r\n" + 
				"inner join mlfm_po_mst b on a.PO_MST_ID = b.PO_MST_ID\r\n" + 
				"INNER JOIN mlfm_po_chd c on b.PO_MST_ID=c.PO_MST_ID\r\n" + 
				"inner join mlfm_design d on b.design_id = d.design_id \r\n" + 
				"inner join mlfm_order e on d.order_id=e.order_id\r\n" + 
				"inner join mlfm_order_item oi on oi.order_id=e.order_id\r\n" + 
				"inner join mlfm_item i on i.item_id=oi.item_id \r\n" + 
				"inner join mlfm_uom l on a.UOM_ID=l.uom_id \r\n" + 
				"inner join mlfm_order_owner f on e.order_owner_id=f.order_owner_id\r\n" + 
				"where a.qc_plan_id=coalesce(:qCPlanId,a.qc_plan_id) ORDER BY a.qc_plan_date DESC";
		
		RowMapper<ModelQCPlanCustom> serviceMapper=new RowMapper<ModelQCPlanCustom>() {

			@Override
			public ModelQCPlanCustom mapRow(ResultSet rs, int rownum) throws SQLException {
				// TODO Auto-generated method stub
				ModelQCPlanCustom bn= new ModelQCPlanCustom();
				//Double qCPlanQtyUnDone=rs.getDouble("PO_QTY")-rs.getDouble("QC_PLAN_QTY");
				Double qCPlanQtyUnDone=rs.getDouble("PO_QTY");
				
				
				bn.setqCPlanId(rs.getLong("QC_PLAN_ID"));
				bn.setpOId(rs.getLong("PO_MST_ID"));
				bn.setpODate(rs.getDate("PO_MST_DATE"));
				bn.setpOQty(rs.getDouble("PO_QTY"));
				bn.setqCPlanDate(rs.getDate("QC_PLAN_DATE"));
				System.out.println(rs.getDate("QC_PLAN_DATE"));
				bn.setqCPlanQty(rs.getDouble("QC_PLAN_QTY"));
				bn.setItemId(rs.getLong("ITEM_ID"));
				bn.setItemName(rs.getString("ITEM_NAME"));
				bn.setqCPlanId(rs.getLong("QC_PLAN_ID"));
				bn.setActiveStatus(rs.getInt("ACTIVE_STATUS"));
				System.out.println(rs.getInt("ACTIVE_STATUS"));
				bn.setqCPlanRemarks(rs.getString("REMARKS"));
				bn.setBuyerId(rs.getLong("ORDER_OWNER_ID"));
				bn.setBuyerName(rs.getString("OWNER_NAME"));
				bn.setUomId(rs.getLong("UOM_ID"));
				bn.setUomName(rs.getString("UOM_NAME"));
				bn.setqCPlanQtyUnDone(qCPlanQtyUnDone);
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
	      parameters.addValue("qCPlanId", qCPlanId);
	      System.out.println(" in daoQCPlanImp, qCPlanId:" +qCPlanId);
	
		
			System.out.println("service mapper ");
			return namedParameterJdbcTemplate.query(qry,parameters,serviceMapper);
	}

	@Override
	public void deleteQCPlan(Long qCPlanId) {
		// TODO Auto-generated method stub
		
		
		qCPlanRepository.deleteById(qCPlanId);
		
	}




	
	
	
	
	

}
