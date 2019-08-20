package com.biziitech.mlfm.daoimpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.biziitech.mlfm.custom.model.ModelWOChdListCustom;
import com.biziitech.mlfm.custom.model.ModelWOInquiryData;
import com.biziitech.mlfm.dao.DaoWOItemQtySpec;
import com.biziitech.mlfm.model.ModelOrderItemQty;
import com.biziitech.mlfm.model.ModelOrderItemQtySpec;
import com.biziitech.mlfm.model.ModelSpec;
import com.biziitech.mlfm.model.ModelUOM;
import com.biziitech.mlfm.model.ModelWOItemQtySpec;
import com.biziitech.mlfm.repository.WOItemQtySpecRepository;

@Service
public class DaoWOItemQtySpecImp implements DaoWOItemQtySpec {
	
	
	
	@Autowired
    private DataSource dataSource;
	
   
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
	
	
	
	@Autowired
	WOItemQtySpecRepository wOItemQtySpecRepository;

	@Override
	public void saveWOItemQtySpec(ModelOrderItemQtySpec modelOrderItemQtySpec) {
		// TODO Auto-generated method stub
		
		  
		  
              ModelWOItemQtySpec modelWOItemQtySpec= new ModelWOItemQtySpec();
              ModelSpec modelSpec = new ModelSpec();   // ModelSpec declaration
              modelSpec.setSpecId(modelOrderItemQtySpec.getModelSpec().getSpecId());
              
              //ModelUOM modelUOM=new ModelUOM();
              modelWOItemQtySpec.setModelUOMWO(modelOrderItemQtySpec.getModelUOM());
              modelWOItemQtySpec.setModelSpecWO(modelSpec);
              modelWOItemQtySpec.setwOSpecValue(modelOrderItemQtySpec.getSpecValue());
              
              modelWOItemQtySpec.setModelSpecOrder(modelSpec);
              modelWOItemQtySpec.setOrderSpecValue(modelOrderItemQtySpec.getSpecValue());
              modelWOItemQtySpec.setModelUOMOrder(modelOrderItemQtySpec.getModelUOM());
              modelWOItemQtySpec.setModelOrderItemQty(modelOrderItemQtySpec.getModelOrderItemQty());
              
              modelWOItemQtySpec.setActiveStatus(modelOrderItemQtySpec.getActiveStatus());
              
              wOItemQtySpecRepository.save(modelWOItemQtySpec);
             
	}

	@Override
	public List<ModelWOItemQtySpec> getWOItemQtySpecList(Long inquiryItemQtyId) {
		
		return wOItemQtySpecRepository.findWOItemQtySpec(inquiryItemQtyId);
		
		// TODO Auto-generated method stub
		 
	}
	
	
	
	@Override
	public void saveWOItemQtySpecNew(ModelWOItemQtySpec modelWOItemQtySpec) {
		
		wOItemQtySpecRepository.save(modelWOItemQtySpec);
		
		
	}

	
	@Override
	public int chkAlreadySavedWOItemQtySpec(Long orderItemQtyId,Long specId) {
		List<ModelWOItemQtySpec> modelWOItemQtySpecList= wOItemQtySpecRepository.findWOItemQtySpecByOIQtyId(orderItemQtyId,specId);
		
	     return modelWOItemQtySpecList.size();
	
	
	}

	@Override
	public List<ModelWOChdListCustom> getWOItemQtySpecListCustom(Long orderItemQtyId) {
		
		String  qry="SELECT s.WO_ITEM_QTY_SPEC_ID,s.ORDER_SPEC_ID,sp.spec_name order_spec_name,s.ORDER_SPEC_VALUE,u.uom_name,s.ORDER_SPEC_UOM_ID,\r\n" + 
				"s.WO_SPEC_ID,sp1.spec_name wo_spec_name, s.WO_SPEC_VALUE,s.WO_SPEC_UOM_ID,s.REMARKS \r\n" + 
				"FROM mlfm_wo_item_qty_spec s\r\n" + 
				"LEFT OUTER JOIN mlfm_spec sp on sp.spec_id=s.ORDER_SPEC_ID\r\n" + 
				"LEFT OUTER JOIN mlfm_spec sp1 on sp1.spec_id=s.WO_SPEC_ID\r\n" + 
				"LEFT OUTER JOIN mlfm_uom u ON u.uom_id=s.ORDER_SPEC_UOM_ID\r\n" + 
				"WHERE s.ORDER_ITEM_QTY_ID=COALESCE(:orderItemQtyId,s.ORDER_ITEM_QTY_ID)";
		
		/*
		 *SELECT s.WO_ITEM_QTY_SPEC_ID,s.ORDER_SPEC_ID,sp.spec_name order_spec_name,s.ORDER_SPEC_VALUE,u.uom_name,s.ORDER_SPEC_UOM_ID,
s.WO_SPEC_ID,sp1.spec_name wo_spec_name, s.WO_SPEC_VALUE,s.WO_SPEC_UOM_ID,s.REMARKS 
FROM mlfm_wo_item_qty_spec s
LEFT OUTER JOIN mlfm_spec sp on sp.spec_id=s.ORDER_SPEC_ID
LEFT OUTER JOIN mlfm_spec sp1 on sp1.spec_id=s.WO_SPEC_ID
LEFT OUTER JOIN mlfm_uom u ON u.uom_id=s.ORDER_SPEC_UOM_ID
WHERE s.ORDER_ITEM_QTY_ID=COALESCE(@orderItemQtyId,s.ORDER_ITEM_QTY_ID)
		 * 
		 * */
	
	RowMapper<ModelWOChdListCustom> serviceMapper=new RowMapper<ModelWOChdListCustom>() {
		@Override
		public ModelWOChdListCustom mapRow(ResultSet rs, int rownum) throws SQLException{
			ModelWOChdListCustom bn=new ModelWOChdListCustom();
			
			bn.setwOItemQtySpecId(rs.getLong("WO_ITEM_QTY_SPEC_ID"));
			
			bn.setOrderSpecId(rs.getLong("ORDER_SPEC_ID"));
			bn.setOrderSpecName(rs.getString("order_spec_name"));
			
			bn.setOrderSpecValue(rs.getString("ORDER_SPEC_VALUE"));
			
			bn.setOrderUomId(rs.getLong("ORDER_SPEC_UOM_ID"));
			bn.setOrderUomName(rs.getString("uom_name"));
			
			bn.setwOSpecId(rs.getLong("WO_SPEC_ID"));
			bn.setwOSpecName(rs.getString("wo_spec_name"));
			
			bn.setwOSpecValue(rs.getString("WO_SPEC_VALUE"));
			
			bn.setwOUomId(rs.getLong("WO_SPEC_UOM_ID"));
			bn.setwOUomName(rs.getString("uom_name"));
			
			
			bn.setRemarks(rs.getString("REMARKS"));
			
			
			System.out.println("wo spec name : " + rs.getString("wo_spec_name"));
			
			
		return bn;
		
		}	
	};
	
	MapSqlParameterSource parameters = new MapSqlParameterSource();
	parameters.addValue("orderItemQtyId", orderItemQtyId);
	System.out.println("query : " + qry);
      
	// TODO Auto-generated method stub
	return  namedJdbcTemplate.query(qry,parameters,serviceMapper);
		
	
	}
	
	



}
