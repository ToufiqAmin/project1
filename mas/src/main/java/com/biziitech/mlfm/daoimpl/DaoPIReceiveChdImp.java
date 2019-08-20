package com.biziitech.mlfm.daoimpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.biziitech.mlfm.custom.model.ModelPIReceiveCustom;
import com.biziitech.mlfm.dao.DaoPIReceiveChd;
import com.biziitech.mlfm.model.ModelPIReceiveChd;
import com.biziitech.mlfm.repository.PIReceiveChdRepository;

@Service
public class DaoPIReceiveChdImp implements DaoPIReceiveChd {
    
	@Autowired
	private PIReceiveChdRepository pIReceiveChdRepository;
	
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	@Autowired
	private JdbcTemplate jdbc;
	
	
	@Override
	public void savePIReceiveChd(ModelPIReceiveChd modelPIReceiveChd) {
		
		
		pIReceiveChdRepository.save(modelPIReceiveChd);
		
	}


	@Override
	public List<ModelPIReceiveCustom> getPIReceiveChdByPIMstId(Long id) {
		String qry="SELECT a.active_status,a.PI_RECEIVE_MST_ID,a.PI_CHD_ID,b.RECEIVE_MST_DATE,b.PI_MST_ID,b.RECEIVE_MST_AMT FROM mlfm_pi_receive_chd a INNER JOIN mlfm_pi_receive_mst b ON a.PI_RECEIVE_MST_ID=b.PI_RECEIVE_MST_ID WHERE b.PI_RECEIVE_MST_ID=coalesce(:id, b.PI_RECEIVE_MST_ID)";
		RowMapper<ModelPIReceiveCustom> serviceMapper=new RowMapper<ModelPIReceiveCustom>() {
			@Override
			public ModelPIReceiveCustom mapRow(ResultSet rs, int rownum) throws SQLException{
				ModelPIReceiveCustom bn=new ModelPIReceiveCustom();
		        
				bn.setpIReceiveMstId(rs.getLong("pi_receive_mst_id"));
				bn.setReceiveMstDate(rs.getDate("receive_mst_date"));
				bn.setpIMstId(rs.getLong("pi_mst_id"));
				bn.setpIChdId(rs.getLong("pi_chd_id"));
				bn.setReceiveAmount(rs.getDouble("receive_mst_amt"));
				bn.setActiveStatus(rs.getInt("active_status"));
						
				
			return bn;
			
			}	
		};
		

		MapSqlParameterSource parameters = new MapSqlParameterSource();
	      
	      parameters.addValue("id",id);
	      

		return  jdbcTemplate.query(qry,parameters,serviceMapper);
	}

}
