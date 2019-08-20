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

import com.biziitech.mlfm.custom.model.ModelMendingPlanCustom;
import com.biziitech.mlfm.dao.DaoMendingPlan;
import com.biziitech.mlfm.model.ModelMendingPlan;
import com.biziitech.mlfm.repository.MendingPlanRepository;



@Service
public class DaoMendingPlanImp implements DaoMendingPlan{
	
	
	@Autowired
	private MendingPlanRepository mendingPlanRepository;;
		
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
	public void saveMendingPlan(ModelMendingPlan modelMendingPlan) {
		// TODO Auto-generated method stub
		
		mendingPlanRepository.save(modelMendingPlan);
		
	}

	@Override
	public void deleteMendingPlan(Long mendingPlanId) {
		// TODO Auto-generated method stub
		
		mendingPlanRepository.deleteById(mendingPlanId);
		
	}

	@Override
	public List<ModelMendingPlanCustom> getPendingMendingPlanDetails(Long pOId, Long buyerId, Date startDate,
			Date endDate) {
		// TODO Auto-generated method stub
		String qry="select a.qc_plan_id, a.qc_plan_date, a.qc_plan_qty,a.uom_id,a.uom_name,a.po_mst_id,a.PO_MST_DATE,a.po_qty,\r\n" + 
				"a.wo_chd_id,a.wo_mst_id,a.wo_date,a.item_id,a.item_name,a.order_owner_id,a.owner_name,\r\n" + 
				"a.qc_id,a.qc_date,a.qc_qty,a.qc_by,a.bed_no,a.mending_required_qty,a.MENDING_PLAN_ID,a.MENDING_PLAN_QTY \r\n" + 
				"FROM (\r\n" + 
				"select a.qc_plan_id, a.qc_plan_date, a.qc_plan_qty,a.uom_id,c.uom_name,a.po_mst_id,b.PO_MST_DATE,m.po_qty,\r\n" + 
				"e.wo_chd_id,f.wo_mst_id,g.wo_date,j.item_id,k.item_name,i.order_owner_id,l.owner_name,\r\n" + 
				"n.qc_id,n.qc_date,n.qc_qty,n.qc_by,n.bed_no,n.mending_required_qty,o.MENDING_PLAN_ID,o.MENDING_PLAN_QTY \r\n" + 
				"from mlfm_qc_plan a\r\n" + 
				"inner join mlfm_po_mst b on a.po_mst_id=b.po_mst_id\r\n" + 
				"INNER JOIN mlfm_po_chd m on b.PO_MST_ID=m.PO_MST_ID\r\n" + 
				"inner join mlfm_uom c on a.uom_id=c.uom_id\r\n" + 
				"inner join mlfm_production_plan_chd d on m.production_plan_chd_id=d.production_plan_chd_id\r\n" + 
				"inner join mlfm_production_plan_mst e on d.production_plan_mst_id=e.production_plan_mst_id\r\n" + 
				"inner join mlfm_wo_chd f on e.wo_chd_id=f.wo_chd_id\r\n" + 
				"inner join mlfm_wo_mst g on f.wo_mst_id=g.wo_mst_id\r\n" + 
				"inner join mlfm_design h on b.design_id=h.design_id\r\n" + 
				"inner join mlfm_order i on h.order_id=i.order_id\r\n" + 
				"inner join mlfm_order_item j on i.order_id=j.order_id\r\n" + 
				"inner join mlfm_item k on j.item_id=k.item_id \r\n" + 
				"inner join mlfm_order_owner l on i.order_owner_id=l.order_owner_id\r\n" + 
				"left outer join mlfm_qc n on a.qc_plan_id=n.qc_plan_id\r\n" + 
				"LEFT OUTER JOIN mlfm_mending_plan o on n.QC_ID=o.QC_ID\r\n" + 
				") a \r\n" + 
				"where a.po_mst_id=coalesce(:pOId,a.po_mst_id) and a.order_owner_id=coalesce(:buyerId,a.order_owner_id)\r\n" + 
				"and a.PO_MST_DATE BETWEEN coalesce(:startDate,a.PO_MST_DATE) AND coalesce(:endDate,a.PO_MST_DATE)\r\n" + 
				"group by a.qc_plan_id, a.qc_plan_date, a.qc_plan_qty,a.uom_id,a.uom_name,a.po_mst_id,a.PO_MST_DATE,a.po_qty,\r\n" + 
				"a.wo_chd_id,a.wo_mst_id,a.wo_date,a.item_id,a.item_name,a.order_owner_id,a.owner_name,\r\n" + 
				"a.qc_id,a.qc_date,a.qc_qty,a.qc_by,a.bed_no,a.mending_required_qty,a.MENDING_PLAN_ID,a.MENDING_PLAN_QTY";
		
		RowMapper<ModelMendingPlanCustom> serviceMapper=new RowMapper<ModelMendingPlanCustom>() {

			@Override
			public ModelMendingPlanCustom mapRow(ResultSet rs, int rownum) throws SQLException {
				// TODO Auto-generated method stub
				ModelMendingPlanCustom bn= new ModelMendingPlanCustom();
				Double mendingPlanQtyUnDone=rs.getDouble("MENDING_REQUIRED_QTY")-rs.getDouble("MENDING_PLAN_QTY");
				
				
				bn.setwOMstId(rs.getLong("WO_MST_ID"));
				bn.setwODate(rs.getDate("WO_DATE"));
				bn.setpOId(rs.getLong("po_mst_id"));
				bn.setpODate(rs.getDate("PO_MST_DATE"));
				bn.setpOQty(rs.getDouble("PO_QTY"));
				bn.setBuyerId(rs.getLong("ORDER_OWNER_ID"));
				bn.setBuyerName(rs.getString("OWNER_NAME"));
				bn.setItemId(rs.getLong("ITEM_ID"));
				bn.setItemName(rs.getString("ITEM_NAME"));
				bn.setqCPlanId(rs.getLong("QC_PLAN_ID"));
				bn.setqCPlanDate(rs.getDate("QC_PLAN_DATE"));
				bn.setqCPlanQty(rs.getDouble("QC_PLAN_QTY"));
				bn.setqCId(rs.getLong("QC_ID"));
				bn.setqCQty(rs.getDouble("QC_QTY"));
				bn.setBedNo(rs.getLong("BED_NO"));
				bn.setMendingRequiredQty(rs.getDouble("MENDING_REQUIRED_QTY"));
				bn.setUomId(rs.getLong("UOM_ID"));
				bn.setUomName(rs.getString("UOM_NAME"));
				bn.setMendingPlanQtyUnDone(mendingPlanQtyUnDone);
				
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
	public List<ModelMendingPlanCustom> getCompletedMendingPlanDetails(Long pOId, Long buyerId, Date startDate,
			Date endDate) {
		// TODO Auto-generated method stub
		String qry="select a.qc_plan_id, a.qc_plan_date, a.qc_plan_qty,a.uom_id,a.uom_name,a.po_mst_id,a.PO_MST_DATE,a.po_qty,\r\n" + 
				"a.wo_chd_id,a.wo_mst_id,a.wo_date,a.item_id,a.item_name,a.order_owner_id,a.owner_name,\r\n" + 
				"a.qc_id,a.qc_date,a.qc_qty,a.qc_by,a.bed_no,a.mending_required_qty,a.MENDING_PLAN_ID,a.MENDING_PLAN_QTY \r\n" + 
				"FROM (\r\n" + 
				"select a.qc_plan_id, a.qc_plan_date, a.qc_plan_qty,o.uom_id,c.uom_name,a.po_mst_id,b.PO_MST_DATE,m.po_qty,\r\n" + 
				"e.wo_chd_id,f.wo_mst_id,g.wo_date,j.item_id,k.item_name,i.order_owner_id,l.owner_name,\r\n" + 
				"o.qc_id,n.qc_date,n.qc_qty,n.qc_by,n.bed_no,n.mending_required_qty,o.MENDING_PLAN_ID,o.MENDING_PLAN_QTY \r\n" + 
				"from mlfm_mending_plan o \r\n" + 
				"inner join mlfm_qc n on o.qc_id=n.qc_id\r\n" + 
				"inner join mlfm_qc_plan a on n.qc_plan_id=a.qc_plan_id \r\n" + 
				"inner join mlfm_po_mst b on a.po_mst_id=b.po_mst_id \r\n" + 
				"INNER JOIN mlfm_po_chd m on b.PO_MST_ID=m.PO_MST_ID \r\n" + 
				"LEFT OUTER JOIN mlfm_uom c on o.uom_id=c.uom_id\r\n" + 
				"inner join mlfm_production_plan_chd d on m.production_plan_chd_id=d.production_plan_chd_id\r\n" + 
				"inner join mlfm_production_plan_mst e on d.production_plan_mst_id=e.production_plan_mst_id\r\n" + 
				"inner join mlfm_wo_chd f on e.wo_chd_id=f.wo_chd_id\r\n" + 
				"inner join mlfm_wo_mst g on f.wo_mst_id=g.wo_mst_id\r\n" + 
				"inner join mlfm_design h on b.design_id=h.design_id\r\n" + 
				"inner join mlfm_order i on h.order_id=i.order_id\r\n" + 
				"inner join mlfm_order_item j on i.order_id=j.order_id\r\n" + 
				"inner join mlfm_item k on j.item_id=k.item_id\r\n" + 
				"inner join mlfm_order_owner l on i.order_owner_id=l.order_owner_id\r\n" + 
				"where o.po_mst_id=b.po_mst_id\r\n" + 
				") a \r\n" + 
				"where a.po_mst_id=coalesce(:pOId,a.po_mst_id) and a.order_owner_id=coalesce(:buyerId,a.order_owner_id) \r\n" + 
				"and a.PO_MST_DATE BETWEEN coalesce(:startDate,a.PO_MST_DATE) AND coalesce(:endDate,a.PO_MST_DATE)\r\n" + 
				"group by a.qc_plan_id, a.qc_plan_date, a.qc_plan_qty,a.uom_id,a.uom_name,a.po_mst_id,a.PO_MST_DATE,a.po_qty,\r\n" + 
				"a.wo_chd_id,a.wo_mst_id,a.wo_date,a.item_id,a.item_name,a.order_owner_id,a.owner_name,\r\n" + 
				"a.qc_id,a.qc_date,a.qc_qty,a.qc_by,a.bed_no,a.mending_required_qty";
		
		RowMapper<ModelMendingPlanCustom> serviceMapper=new RowMapper<ModelMendingPlanCustom>() {

			@Override
			public ModelMendingPlanCustom mapRow(ResultSet rs, int rownum) throws SQLException {
				// TODO Auto-generated method stub
				ModelMendingPlanCustom bn= new ModelMendingPlanCustom();
				Double mendingPlanQtyUnDone=rs.getDouble("MENDING_REQUIRED_QTY")-rs.getDouble("MENDING_PLAN_QTY");
				
				
				bn.setwOMstId(rs.getLong("WO_MST_ID"));
				bn.setwODate(rs.getDate("WO_DATE"));
				bn.setpOId(rs.getLong("po_mst_id"));
				bn.setpODate(rs.getDate("PO_MST_DATE"));
				bn.setpOQty(rs.getDouble("PO_QTY"));
				bn.setBuyerId(rs.getLong("ORDER_OWNER_ID"));
				bn.setBuyerName(rs.getString("OWNER_NAME"));
				bn.setItemId(rs.getLong("ITEM_ID"));
				bn.setItemName(rs.getString("ITEM_NAME"));
				bn.setqCPlanId(rs.getLong("QC_PLAN_ID"));
				bn.setqCPlanDate(rs.getDate("QC_PLAN_DATE"));
				bn.setqCPlanQty(rs.getDouble("QC_PLAN_QTY"));
				bn.setqCId(rs.getLong("QC_ID"));
				bn.setqCQty(rs.getDouble("QC_QTY"));
				bn.setBedNo(rs.getLong("BED_NO"));
				bn.setMendingRequiredQty(rs.getDouble("MENDING_REQUIRED_QTY"));
				bn.setUomId(rs.getLong("UOM_ID"));
				bn.setUomName(rs.getString("UOM_NAME"));
				bn.setMendingPlanQtyUnDone(mendingPlanQtyUnDone);
		
		
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
	public List<ModelMendingPlanCustom> getMendingPlanById(Long mendingPlanId) {
		// TODO Auto-generated method stub
		String qry="SELECT a.MENDING_PLAN_ID,a.MENDING_PLAN_DATE,a.PO_ID,\r\n" + 
				"a.MENDING_PLAN_QTY,a.REMARKS,a.ACTIVE_STATUS,\r\n" + 
				"a.QC_ID,b.mending_required_qty,a.PO_MST_ID,\r\n" + 
				"a.UOM_ID,d.uom_name,g.order_owner_id,h.owner_name,\r\n" + 
				"i.item_id,j.item_name\r\n" + 
				"FROM mlfm_mending_plan a\r\n" + 
				"INNER JOIN mlfm_qc b on a.QC_ID=b.QC_ID\r\n" + 
				"INNER JOIN mlfm_qc_plan c on b.QC_PLAN_ID=c.QC_PLAN_ID\r\n" + 
				"INNER JOIN mlfm_po_mst e on a.PO_MST_ID=e.PO_MST_ID\r\n" + 
				"INNER JOIN mlfm_design f on e.DESIGN_ID=f.design_id \r\n" + 
				"INNER JOIN mlfm_order g on f.order_id=g.order_id\r\n" + 
				"INNER JOIN mlfm_order_owner h on g.order_owner_id=h.order_owner_id\r\n" + 
				"INNER JOIN mlfm_order_item i on g.order_id=i.order_id\r\n" + 
				"INNER JOIN mlfm_item j on i.item_id=j.item_id\r\n" + 
				"LEFT OUTER JOIN mlfm_uom d on a.UOM_ID=d.uom_id\r\n" + 
				"WHERE a.MENDING_PLAN_ID=coalesce(:mendingPlanId,a.MENDING_PLAN_ID) ORDER BY a.MENDING_PLAN_DATE DESC";
		
		RowMapper<ModelMendingPlanCustom> serviceMapper=new RowMapper<ModelMendingPlanCustom>() {

			@Override
			public ModelMendingPlanCustom mapRow(ResultSet rs, int rownum) throws SQLException {
				// TODO Auto-generated method stub
				ModelMendingPlanCustom bn= new ModelMendingPlanCustom();
				
				
				bn.setBuyerId(rs.getLong("ORDER_OWNER_ID"));
				bn.setBuyerName(rs.getString("OWNER_NAME"));
				bn.setItemId(rs.getLong("ITEM_ID"));
				bn.setItemName(rs.getString("ITEM_NAME"));
				bn.setUomId(rs.getLong("UOM_ID"));
				bn.setUomName(rs.getString("UOM_NAME"));
				bn.setMendingPlanId(rs.getLong("MENDING_PLAN_ID"));
				bn.setMendingPlanDate(rs.getDate("MENDING_PLAN_DATE"));
				bn.setpOId(rs.getLong("PO_MST_ID"));
				bn.setMendingPlanQty(rs.getDouble("MENDING_PLAN_QTY"));
				bn.setMendingPlanRemarks(rs.getString("REMARKS"));
				bn.setqCId(rs.getLong("QC_ID"));
				bn.setMendingRequiredQty(rs.getDouble("MENDING_REQUIRED_QTY"));
				bn.setActiveStatus(rs.getInt("ACTIVE_STATUS"));
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
	      System.out.println(" in daoMendingPlanImp, mendingPlanId:" +mendingPlanId);
	     

		System.out.println("service mapper ");
		return namedParameterJdbcTemplate.query(qry,parameters,serviceMapper);
	}

	@Override
	public List<ModelMendingPlanCustom> getMendingPlanBy(Long pOId, Long qCId) {
		// TODO Auto-generated method stub
		String qry="SELECT a.MENDING_PLAN_ID,a.MENDING_PLAN_DATE,a.PO_MST_ID,\r\n" + 
				"a.MENDING_PLAN_QTY,a.REMARKS,a.ACTIVE_STATUS,a.QC_ID,a.mending_required_qty,\r\n" + 
				"a.order_owner_id,a.owner_name,a.UOM_ID,a.uom_name,a.item_id,a.item_name \r\n" + 
				"FROM ( \r\n" + 
				"SELECT a.MENDING_PLAN_ID,a.MENDING_PLAN_DATE,a.PO_MST_ID, \r\n" + 
				"a.MENDING_PLAN_QTY,a.REMARKS,a.ACTIVE_STATUS,a.QC_ID,k.mending_required_qty, \r\n" + 
				"e.order_owner_id,h.owner_name,a.UOM_ID,i.uom_name,f.item_id,g.item_name\r\n" + 
				"FROM mlfm_mending_plan a \r\n" + 
				"INNER join mlfm_qc k on a.QC_ID=k.QC_ID \r\n" + 
				"INNER JOIN mlfm_qc_plan c on k.QC_PLAN_ID=c.QC_PLAN_ID\r\n" + 
				"INNER JOIN mlfm_po_mst b on a.PO_MST_ID=b.PO_MST_ID\r\n" + 
				"inner join mlfm_design d on b.design_id=d.design_id \r\n" + 
				"inner join mlfm_order e on d.order_id=e.order_id\r\n" + 
				"inner join mlfm_order_item f on e.order_id=f.order_id \r\n" + 
				"inner join mlfm_item g on f.item_id=g.item_id\r\n" + 
				"inner join mlfm_order_owner h on e.order_owner_id=h.order_owner_id\r\n" + 
				"LEFT OUTER JOIN mlfm_uom i on a.UOM_ID=i.uom_id\r\n" + 
				") a\r\n" + 
				"WHERE a.PO_MST_ID=coalesce(:pOId,a.PO_MST_ID) ORDER BY a.MENDING_PLAN_DATE DESC";
		
		RowMapper<ModelMendingPlanCustom> serviceMapper=new RowMapper<ModelMendingPlanCustom>() {

			@Override
			public ModelMendingPlanCustom mapRow(ResultSet rs, int rownum) throws SQLException {
				// TODO Auto-generated method stub
				ModelMendingPlanCustom bn= new ModelMendingPlanCustom();
				
				
				bn.setMendingPlanId(rs.getLong("MENDING_PLAN_ID"));
				bn.setMendingPlanDate(rs.getDate("MENDING_PLAN_DATE"));
				bn.setpOId(rs.getLong("PO_MST_ID"));
				bn.setMendingPlanQty(rs.getDouble("MENDING_PLAN_QTY"));
				bn.setMendingPlanRemarks(rs.getString("REMARKS"));
				bn.setBuyerId(rs.getLong("ORDER_OWNER_ID"));
				bn.setBuyerName(rs.getString("OWNER_NAME"));
				bn.setItemId(rs.getLong("ITEM_ID"));
				bn.setItemName(rs.getString("ITEM_NAME"));
				bn.setUomId(rs.getLong("UOM_ID"));
				bn.setUomName(rs.getString("UOM_NAME"));
				bn.setqCId(rs.getLong("QC_ID"));
				bn.setMendingRequiredQty(rs.getDouble("MENDING_REQUIRED_QTY"));
				bn.setActiveStatus(rs.getInt("ACTIVE_STATUS"));
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
	      System.out.println(" in daoMendingPlanImp, pOId:" +pOId);
	      parameters.addValue("qCId", qCId);
	      System.out.println(" in daoMendingPlanImp, qCId:" +qCId);
	    

		System.out.println("service mapper ");
		return namedParameterJdbcTemplate.query(qry,parameters,serviceMapper);
	}

	@Override
	public List<ModelMendingPlanCustom> getQCPlanByPOId(Long pOId) {
		// TODO Auto-generated method stub
		String qry="SELECT a.QC_PLAN_ID,a.QC_PLAN_DATE,a.QC_PLAN_QTY,a.ACTIVE_STATUS,\r\n" + 
				"a.REMARKS,a.PO_MST_ID,a.UOM_ID,a.uom_name,a.item_id,a.item_name,\r\n" + 
				"a.order_owner_id,a.owner_name\r\n" + 
				"FROM (\r\n" + 
				"SELECT a.QC_PLAN_ID,a.QC_PLAN_DATE,a.QC_PLAN_QTY,a.ACTIVE_STATUS,\r\n" + 
				"a.REMARKS,a.PO_MST_ID,a.UOM_ID,b.uom_name,f.item_id,g.item_name,\r\n" + 
				"e.order_owner_id,h.owner_name\r\n" + 
				"FROM mlfm_qc_plan a \r\n" + 
				"INNER JOIN mlfm_uom b on a.UOM_ID=b.uom_id\r\n" + 
				"INNER JOIN mlfm_po_mst c on a.PO_MST_ID=c.PO_MST_ID\r\n" + 
				"INNER JOIN mlfm_po_chd i on i.PO_MST_ID=c.PO_MST_ID\r\n" + 
				"inner join mlfm_design d on c.design_id=d.design_id\r\n" + 
				"inner join mlfm_order e on d.order_id=e.order_id \r\n" + 
				"inner join mlfm_order_item f on e.order_id=f.order_id \r\n" + 
				"inner join mlfm_item g on f.item_id=g.item_id \r\n" + 
				"inner join mlfm_order_owner h on e.order_owner_id=h.order_owner_id\r\n" + 
				") a  \r\n" + 
				"WHERE a.PO_MST_ID=coalesce(:pOId,a.PO_MST_ID)";
		
		RowMapper<ModelMendingPlanCustom> serviceMapper=new RowMapper<ModelMendingPlanCustom>() {

			@Override
			public ModelMendingPlanCustom mapRow(ResultSet rs, int rownum) throws SQLException {
				// TODO Auto-generated method stub
				ModelMendingPlanCustom bn= new ModelMendingPlanCustom();
				
				
				bn.setqCPlanId(rs.getLong("QC_PLAN_ID"));
				bn.setqCPlanDate(rs.getDate("QC_PLAN_DATE"));
				bn.setqCPlanQty(rs.getDouble("QC_PLAN_QTY"));
				bn.setqCPlanRemarks(rs.getString("REMARKS"));
				bn.setUomId(rs.getLong("UOM_ID"));
				bn.setUomName(rs.getString("uom_name"));
				bn.setpOId(rs.getLong("PO_MST_ID"));
				System.out.println("in daoMendingPlan, PO_MST_ID: "+rs.getLong("PO_MST_ID"));
				bn.setBuyerId(rs.getLong("ORDER_OWNER_ID"));
				bn.setBuyerName(rs.getString("OWNER_NAME"));
				bn.setItemId(rs.getLong("ITEM_ID"));
				bn.setItemName(rs.getString("ITEM_NAME"));
				bn.setActiveStatus(rs.getInt("ACTIVE_STATUS"));
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
	      System.out.println(" in daoMendingPlanImp, pOId:" +pOId);

	    

		System.out.println("service mapper ");
		return namedParameterJdbcTemplate.query(qry,parameters,serviceMapper);
	}

	@Override
	public List<ModelMendingPlanCustom> getQCByPOId(Long pOId) {
		// TODO Auto-generated method stub
		String qry="SELECT a.QC_ID,a.QC_DATE,a.QC_QTY,a.QC_BY,a.user_name,a.REMARKS,\r\n" + 
				"a.ACTIVE_STATUS,a.bed_no,a.mending_required_qty,\r\n" + 
				"a.QC_PLAN_ID,a.QC_PLAN_DATE,a.QC_PLAN_QTY,\r\n" + 
				"a.PO_MST_ID,a.PO_MST_DATE,a.PO_QTY,a.order_owner_id,a.owner_name,\r\n" + 
				"a.UOM_ID,a.uom_name,a.item_id,a.item_name\r\n" + 
				"\r\n" + 
				"FROM (\r\n" + 
				"SELECT a.QC_ID,a.QC_DATE,a.QC_QTY,a.QC_BY,u.user_name,a.REMARKS,\r\n" + 
				"a.ACTIVE_STATUS,a.bed_no,a.mending_required_qty,\r\n" + 
				"a.QC_PLAN_ID,b.QC_PLAN_DATE,b.QC_PLAN_QTY,\r\n" + 
				"b.PO_MST_ID,c.PO_MST_DATE,m.PO_QTY,e.order_owner_id,h.owner_name,\r\n" + 
				"b.UOM_ID,i.uom_name,f.item_id,g.item_name\r\n" + 
				"FROM mlfm_qc a \r\n" + 
				"LEFT OUTER join mlfm_qc_plan b on a.QC_PLAN_ID=b.QC_PLAN_ID\r\n" + 
				"INNER JOIN mlfm_po_mst c on b.PO_MST_ID=c.PO_MST_ID\r\n" + 
				"INNER join mlfm_po_chd m on c.PO_MST_ID=m.PO_MST_ID\r\n" + 
				"inner join mlfm_design d on c.design_id=d.design_id\r\n" + 
				"inner join mlfm_order e on d.order_id=e.order_id \r\n" + 
				"inner join mlfm_order_item f on e.order_id=f.order_id \r\n" + 
				"inner join mlfm_item g on f.item_id=g.item_id \r\n" + 
				"inner join mlfm_order_owner h on e.order_owner_id=h.order_owner_id\r\n" + 
				"inner join mlfm_uom i on b.uom_id=i.uom_id\r\n" + 
				"LEFT OUTER join bg_user u on a.qc_by=u.user_id\r\n" + 
				"\r\n" + 
				") a \r\n" + 
				"WHERE a.PO_MST_ID=coalesce(:pOId,a.PO_MST_ID)";
		
		RowMapper<ModelMendingPlanCustom> serviceMapper=new RowMapper<ModelMendingPlanCustom>() {

			@Override
			public ModelMendingPlanCustom mapRow(ResultSet rs, int rownum) throws SQLException {
				// TODO Auto-generated method stub
				ModelMendingPlanCustom bn= new ModelMendingPlanCustom();
				
				bn.setqCId(rs.getLong("QC_ID"));
				bn.setqCQty(rs.getDouble("QC_QTY"));
				bn.setqCDate(rs.getDate("QC_DATE"));
				bn.setqCBy(rs.getLong("QC_BY"));
				bn.setqCRemarks(rs.getString("REMARKS"));
				bn.setUserName(rs.getString("USER_NAME"));
				bn.setUomId(rs.getLong("UOM_ID"));
				bn.setUomName(rs.getString("uom_name"));
				bn.setBuyerId(rs.getLong("ORDER_OWNER_ID"));
				bn.setBuyerName(rs.getString("OWNER_NAME"));
				bn.setItemId(rs.getLong("ITEM_ID"));
				bn.setItemName(rs.getString("ITEM_NAME"));
				bn.setBedNo(rs.getLong("BED_NO"));
				bn.setMendingRequiredQty(rs.getDouble("MENDING_REQUIRED_QTY"));
				bn.setActiveStatus(rs.getInt("ACTIVE_STATUS"));
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
	      System.out.println(" in daoMendingPlanImp, pOId:" +pOId);

	    

		System.out.println("service mapper ");
		return namedParameterJdbcTemplate.query(qry,parameters,serviceMapper);
	}
	
	
	

}
