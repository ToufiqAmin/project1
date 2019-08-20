package com.biziitech.mlfm.daoimpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.biziitech.mlfm.custom.model.ModelGatePassCustom;
import com.biziitech.mlfm.custom.model.ModelPIReceiveCustom;
import com.biziitech.mlfm.dao.DaoPIReceiveMst;
import com.biziitech.mlfm.model.ModelDeliveryChallan;
import com.biziitech.mlfm.model.ModelPIReceiveMst;
import com.biziitech.mlfm.repository.PIReceiveMstRepository;

@Service
public class DaoPIReceiveMstImp implements DaoPIReceiveMst {
    
	@Autowired
	private PIReceiveMstRepository pIReceiveMstRepository;
	
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	@Autowired
	private JdbcTemplate jdbc;
	
	@Autowired
    private DataSource dataSource;
	
	
	@Override
	public void savePIReceiveMst(ModelPIReceiveMst modelPIReceiveMst) {
		
		pIReceiveMstRepository.save(modelPIReceiveMst);
		
	}



	@Override
	public List<ModelPIReceiveCustom> getPIReceiveMstById(Long id) {
		String qry="SELECT a.active_status,a.PI_RECEIVE_MST_ID,a.RECEIVE_MST_DATE,a.RECEIVE_MST_AMT,b.PI_MST_ID,c.owner_name FROM mlfm_pi_receive_mst a INNER JOIN mlfm_pi_mst b ON a.PI_MST_ID=b.PI_MST_ID INNER JOIN mlfm_order_owner c ON b.ORDER_OWNER_ID=c.order_owner_id WHERE a.PI_RECEIVE_MST_ID=coalesce(:id, a.PI_RECEIVE_MST_ID)";
		RowMapper<ModelPIReceiveCustom> serviceMapper=new RowMapper<ModelPIReceiveCustom>() {
			@Override
			public ModelPIReceiveCustom mapRow(ResultSet rs, int rownum) throws SQLException{
				ModelPIReceiveCustom bn=new ModelPIReceiveCustom();
		        
				bn.setpIReceiveMstId(rs.getLong("pi_receive_mst_id"));
				bn.setReceiveMstDate(rs.getDate("receive_mst_date"));
				bn.setpIMstId(rs.getLong("pi_mst_id"));
				bn.setReceiveAmount(rs.getDouble("receive_mst_amt"));
				bn.setOrderOwnerName(rs.getString("owner_name"));
				bn.setActiveStatus(rs.getInt("active_status"));
						
				
			return bn;
			
			}	
		};
		

		MapSqlParameterSource parameters = new MapSqlParameterSource();
	      
	      parameters.addValue("id",id);
	      

		return  jdbcTemplate.query(qry,parameters,serviceMapper);
	}



	@Override
	public List<ModelPIReceiveCustom> getPIChdById(Long id) {
		String qry="SELECT a.PI_RECEIVE_MST_ID,a.RECEIVE_MST_AMT,a.RECEIVE_MST_DATE,b.PI_CHD_ID,b.PI_MST_ID FROM mlfm_pi_receive_mst a INNER JOIN mlfm_pi_chd b ON a.PI_MST_ID=b.PI_MST_ID WHERE b.PI_MST_ID=coalesce(:id, b.PI_MST_ID)";
		RowMapper<ModelPIReceiveCustom> serviceMapper=new RowMapper<ModelPIReceiveCustom>() {
			@Override
			public ModelPIReceiveCustom mapRow(ResultSet rs, int rownum) throws SQLException{
				ModelPIReceiveCustom bn=new ModelPIReceiveCustom();
		        
				
				bn.setpIReceiveMstId(rs.getLong("pi_receive_mst_id"));
				bn.setReceiveMstDate(rs.getDate("receive_mst_date"));
				bn.setpIMstId(rs.getLong("pi_mst_id"));
				bn.setpIChdId(rs.getLong("pi_chd_id"));
				bn.setReceiveAmount(rs.getDouble("receive_mst_amt"));
						
				
			return bn;
			
			}	
		};
		

		MapSqlParameterSource parameters = new MapSqlParameterSource();
	      
	      parameters.addValue("id",id);
	      

		return  jdbcTemplate.query(qry,parameters,serviceMapper);
	}



	@Override
	public List<ModelPIReceiveCustom> getPIReceiveMstByPIMstId(Long id) {
		String qry="SELECT a.active_status,a.PI_RECEIVE_MST_ID,a.RECEIVE_MST_DATE,a.RECEIVE_MST_AMT,b.PI_MST_ID,c.owner_name FROM mlfm_pi_receive_mst a INNER JOIN mlfm_pi_mst b ON a.PI_MST_ID=b.PI_MST_ID INNER JOIN mlfm_order_owner c ON b.ORDER_OWNER_ID=c.order_owner_id  WHERE b.PI_MST_ID=coalesce(:id, b.PI_MST_ID)";
		RowMapper<ModelPIReceiveCustom> serviceMapper=new RowMapper<ModelPIReceiveCustom>() {
			@Override
			public ModelPIReceiveCustom mapRow(ResultSet rs, int rownum) throws SQLException{
				ModelPIReceiveCustom bn=new ModelPIReceiveCustom();
		        
				bn.setpIReceiveMstId(rs.getLong("pi_receive_mst_id"));
				bn.setReceiveMstDate(rs.getDate("receive_mst_date"));
				bn.setpIMstId(rs.getLong("pi_mst_id"));
				bn.setReceiveAmount(rs.getDouble("receive_mst_amt"));
				bn.setOrderOwnerName(rs.getString("owner_name"));
				bn.setActiveStatus(rs.getInt("active_status"));
						
				
			return bn;
			
			}	
		};
		

		MapSqlParameterSource parameters = new MapSqlParameterSource();
	      
	      parameters.addValue("id",id);
	      

		return  jdbcTemplate.query(qry,parameters,serviceMapper);
	}

	
	public void updateReceiveMst(ModelPIReceiveMst mst){
        try {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.update("update MLFM_PI_RECEIVE_MST set active_status=?,updated_by=?, update_timestamp=? where pi_receive_mst_id=?",mst.getActiveStatus(),mst.getUpdatedBy(),mst.getUpdateTimestamp(),mst.getpIReceiveMstId());
        	
        
        System.out.println("update success");
       }
        
        
        catch(Exception e) {
        	e.printStackTrace();
}

	}
	

}
