package com.biziitech.mlfm.daoimpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.biziitech.mlfm.custom.model.ModelDesignCustom;
import com.biziitech.mlfm.dao.DaoDesignSpec;
import com.biziitech.mlfm.model.ModelDesign;
import com.biziitech.mlfm.model.ModelDesignConsumItem;
import com.biziitech.mlfm.model.ModelDesignSpec;
import com.biziitech.mlfm.model.ModelSpec;
import com.biziitech.mlfm.repository.DesignSpecRepository;
import com.biziitech.mlfm.repository.SpecRepository;
@Service
public class DaoDesignSpecImp implements DaoDesignSpec {
	
	

	
	
	private Long designId;
	public Long getDesignId() {
		return designId;
	}
	public void setDesignId(Long designId) {
		this.designId = designId;
	}
	
	private List<ModelDesignSpec> designSpecList;
	
	
	@Autowired
    private DataSource dataSource;
	
	@Autowired
	private NamedParameterJdbcTemplate namedJdbcTemplate;
    public void setNamedDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.namedJdbcTemplate=namedJdbcTemplate;
          }
	
//	public List<ModelDesignSpec> getDesignSpecList() {
//		return designList;
//	}
//	public void setDesignSpecList(List<ModelDesignSpec> designList) {
//		this.designList = designList;
//	}
	
	
	@Autowired
	DesignSpecRepository designSpecRepository;
	
	
	
	
	
	// parameter Long designId
	@Override
	public void saveSpecification(String specValue,String remarks,Long designId) {
		 ModelDesignSpec spec=new ModelDesignSpec();
		 java.util.Date date = new java.util.Date();
		 java.sql.Timestamp entryTime = new java.sql.Timestamp(date.getTime());
		 
		 ModelDesign modelDesign=new ModelDesign();
		 modelDesign.setDesignId(designId);
		 System.out.println("save specification" + designId);
		 spec.setModelDesign(modelDesign);
		
		 spec.setActive(true);
		 spec.setActiveStatus(1);
		 spec.setEntryTimestamp(entryTime);
		 spec.setRemarks(remarks);
		 spec.setSpecValue(specValue);
		 designSpecRepository.save(spec);
	}
	
	
	
	
	public List<ModelDesignSpec> getDesignSpecList() {
		return designSpecList;
	}
	public void setDesignSpecList(List<ModelDesignSpec> designSpecList) {
		this.designSpecList = designSpecList;
	}
	@Override
	public List<ModelDesignSpec> getDesignSpecDataByDesignId(Long designId) {
		
		List<ModelDesignSpec> designListInquery=designSpecRepository.getDesignSpecDataByDesignId(designId);
		setDesignSpecList(designListInquery);
		// TODO Auto-generated method stub
		return designListInquery;
	}
	

	
	
	
	@Override
	public void saveSpec(ModelDesignSpec modelDesignSpec) {
		
		designSpecRepository.save(modelDesignSpec);
		// TODO Auto-generated method stub
		
	}
	
	
	@Override
	public List<ModelDesignCustom> getDesignSpecListBySpecId(Long specId) {
		
		
		
		String  qry="SELECT ds.design_spec_id,s.spec_id,ds.spec_value,s.spec_name,ds.remarks,ds.active_status\r\n" + 
				"FROM mlfm_design_spec ds\r\n" + 
				"INNER JOIN mlfm_spec s on s.spec_id=ds.spec_id\r\n" + 
				"WHERE ds.design_spec_id=COALESCE(:specId,ds.design_spec_id)";
		/*
		 * sql query
		 * SELECT ds.design_spec_id,ds.spec_value,s.spec_id,s.spec_name,ds.remarks,ds.active_status
FROM mlfm_design_spec ds
INNER JOIN mlfm_spec s on s.spec_id=ds.spec_id
WHERE ds.design_spec_id=COALESCE(@specId,ds.design_spec_id)
		 * 
		 * 
		 * */
		RowMapper<ModelDesignCustom> serviceMapper=new RowMapper<ModelDesignCustom>() {
			@Override
			public ModelDesignCustom mapRow(ResultSet rs, int rownum) throws SQLException{
				ModelDesignCustom bn=new ModelDesignCustom();
				
			bn.setDesignSpecId(rs.getLong("design_spec_id"));
		    bn.setSpecValue(rs.getString("spec_value"));
		    bn.setSpecId(rs.getLong("spec_id"));
		    bn.setSpecName(rs.getString("spec_name"));
		    bn.setRemarks(rs.getString("remarks"));
		    bn.setActiveStatus(rs.getInt("active_status"));
				
			return bn;
			
			}	
		};
		
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		
		  
		parameters.addValue("specId", specId);
	      
	      System.out.println("query for Design Done Search : " + qry);
	      
		// TODO Auto-generated method stub
		return  namedJdbcTemplate.query(qry,parameters,serviceMapper);
	}
	@Override
	public Optional<ModelDesignSpec> findDesignSpecById(Long specId) {
		// TODO Auto-generated method stub
		return designSpecRepository.findById(specId);
	}
	
	
	@Override
	public List<ModelDesignCustom> getAllDesignSpecListByDesignId(Long designId) {
		
		
		String  qry="SELECT ds.design_spec_id,ds.spec_value,s.spec_name,ds.remarks,ds.active_status,d.design_id\r\n" + 
				"FROM mlfm_design_spec ds\r\n" + 
				"INNER JOIN mlfm_spec s on s.spec_id=ds.spec_id\r\n" + 
				"INNER JOIN mlfm_design d ON d.design_id=ds.design_id " +
				"WHERE ds.design_id=COALESCE(:designId,ds.design_id)";
		/*
		 * sql query
		 * 
		 * SELECT ds.design_spec_id,ds.spec_value,s.spec_name,ds.remarks,ds.active_status,d.design_id
FROM mlfm_design_spec ds
INNER JOIN mlfm_spec s on s.spec_id=ds.spec_id
INNER JOIN mlfm_design d ON d.design_id=ds.design_id
WHERE ds.design_id=COALESCE(@designId,ds.design_id)
		 * 
		 * 
		 * */
		RowMapper<ModelDesignCustom> serviceMapper=new RowMapper<ModelDesignCustom>() {
			@Override
			public ModelDesignCustom mapRow(ResultSet rs, int rownum) throws SQLException{
				ModelDesignCustom bn=new ModelDesignCustom();
				
				bn.setDesignSpecId(rs.getLong("design_spec_id"));
			    bn.setSpecValue(rs.getString("spec_value"));
			    bn.setSpecName(rs.getString("spec_name"));
			    bn.setRemarks(rs.getString("remarks"));
			    bn.setActiveStatus(rs.getInt("active_status"));
			    
			    bn.setDesignId(rs.getLong("design_id"));
				
			return bn;
			
			}	
		};
		
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		
		  
		parameters.addValue("designId", designId);
	      
	      System.out.println("query for get All Design consume item object data by design Id : " + qry);
	      
		// TODO Auto-generated method stub
		return  namedJdbcTemplate.query(qry,parameters,serviceMapper);
		
		
		
	//	return null;
	}
	@Override
	public List<ModelDesignSpec> getSpecName(Long designId) {
		// TODO Auto-generated method stub
		return designSpecRepository.getDesignSpecDataByDesignId(designId);
	}
	
	
	
	@Override
	public List<ModelDesignSpec> checkDesignSpecForDesignId(Long specId, Long designId) {
		
		
		
		
		
		String  qry="SELECT a.design_spec_id,a.design_id,a.spec_id \r\n" + 
				"FROM mlfm_design_spec a \r\n" + 
				"WHERE\r\n" + 
				"a.design_id=COALESCE(:designId,a.design_id) AND\r\n" + 
				"a.spec_id=COALESCE(:specId,a.spec_id)";
		/*
		 * sql query
		 * 
		 * SELECT a.design_spec_id,a.design_id,a.spec_id 
FROM mlfm_design_spec a 
WHERE
a.design_id=COALESCE(@designId,a.design_id) AND
a.spec_id=COALESCE(@specId,a.spec_id)
		 * 
		 * 
		 * */
		RowMapper<ModelDesignSpec> serviceMapper=new RowMapper<ModelDesignSpec>() {
			@Override
			public ModelDesignSpec mapRow(ResultSet rs, int rownum) throws SQLException{
				ModelDesignSpec bn=new ModelDesignSpec();
				
				bn.setDesignSpecId(rs.getLong("design_spec_id"));
			  
				
			return bn;
			
			}	
		};
		
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		
		  
		parameters.addValue("designId", designId);
		
		parameters.addValue("specId", specId);
	      
	      System.out.println("query for check spec aginist Design ID : " + qry);
	      
	      System.out.println("query for check spec aginist Design size : " + namedJdbcTemplate.query(qry,parameters,serviceMapper).size());
	      
		// TODO Auto-generated method stub
		return  namedJdbcTemplate.query(qry,parameters,serviceMapper);
		
		
		
		
		
		
		
		
		// TODO Auto-generated method stub
	//	System.out.println("s "+specId+" d "+designId);
	//	return designSpecRepository.checkDesignSpec(designId,specId);
		
		
		
		
		
		
		
		
		
	}
	
}
