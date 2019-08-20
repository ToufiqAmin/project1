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
import com.biziitech.mlfm.dao.DaoDesignConsumItem;
import com.biziitech.mlfm.model.ModelDesign;
import com.biziitech.mlfm.model.ModelDesignConsumItem;
import com.biziitech.mlfm.model.ModelDesignSpec;
import com.biziitech.mlfm.repository.DesignConsumeItemRepository;
@Service
public class DaoDessignConsumItemImp implements DaoDesignConsumItem {
	
	
	
	@Autowired
    private DataSource dataSource;
	
	@Autowired
	private NamedParameterJdbcTemplate namedJdbcTemplate;
    public void setNamedDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.namedJdbcTemplate=namedJdbcTemplate;
          }
	
	
	
	
	
	@Autowired
private DesignConsumeItemRepository consumeItemRepository;
@Override
public void saveConsume(Long designId,Long itemQty,String remarks) {
	 ModelDesignConsumItem consumeItem=new ModelDesignConsumItem();
	 
	 ModelDesign modelDesign=new ModelDesign();
	 java.util.Date date = new java.util.Date();
	 java.sql.Timestamp entryTime = new java.sql.Timestamp(date.getTime());
	 
	 modelDesign.setDesignId(designId);
	 consumeItem.setModelDesign(modelDesign);
	 
	 consumeItem.setItemQty(itemQty);
	 consumeItem.setRemarks(remarks);
	 consumeItem.setActive(true);
	 consumeItem.setActiveStatus(1);
	 consumeItem.setEntryTimestamp(entryTime);
	 consumeItemRepository.save(consumeItem);
}


private List<ModelDesignConsumItem> designConsumeListItem;

public List<ModelDesignConsumItem> getDesignConsumeListItem() {
	return designConsumeListItem;
}


public void setDesignConsumeListItem(List<ModelDesignConsumItem> designConsumeListItem) {
	this.designConsumeListItem = designConsumeListItem;
}



@Override
public List<ModelDesignConsumItem> getDesignConsumItemList(Long designId) {
	
	List<ModelDesignConsumItem> designConsumItemList= consumeItemRepository.getDesignConsumItemByDesignId(designId);
	setDesignConsumeListItem(designConsumItemList);
// TODO Auto-generated method stub
	return designConsumItemList;
	//return null;
}


@Override
public void saveDesignConsumeItem(ModelDesignConsumItem modelDesignConsumItem) {
	
	consumeItemRepository.save(modelDesignConsumItem);
	// TODO Auto-generated method stub
	
}


@Override
public List<ModelDesignCustom> getDesignConsumItemListByDesignConItemId(Long designConsumeItemId) {
	
	String  qry="SELECT dci.design_consum_item_id,dci.remarks,dci.item_qty,dci.active_status,i.item_id,i.item_name,u.uom_id\r\n" + 
			"FROM mlfm_design_consum_item dci\r\n" + 
			"INNER JOIN mlfm_item i on i.item_id=dci.item_id\r\n" + 
			"LEFT OUTER JOIN mlfm_uom u ON u.uom_id=dci.uom_id\r\n" + 
			"WHERE dci.design_consum_item_id=COALESCE(:designConsumeItemId,dci.design_consum_item_id)";
	/*
	 * sql query
	 * 
	 * SELECT dci.design_consum_item_id,dci.remarks,dci.item_qty,dci.active_status,i.item_id,i.item_name,u.uom_id
FROM mlfm_design_consum_item dci
INNER JOIN mlfm_item i on i.item_id=dci.item_id
LEFT OUTER JOIN mlfm_uom u ON u.uom_id=dci.uom_id
WHERE dci.design_consum_item_id=COALESCE(@designConsumeItemId,dci.design_consum_item_id)
	 * 
	 * 
	 * */
	RowMapper<ModelDesignCustom> serviceMapper=new RowMapper<ModelDesignCustom>() {
		@Override
		public ModelDesignCustom mapRow(ResultSet rs, int rownum) throws SQLException{
			ModelDesignCustom bn=new ModelDesignCustom();
			
		bn.setDesignConsumItemId(rs.getLong("design_consum_item_id"));
		bn.setItemQty(rs.getLong("item_qty"));
		bn.setItemId(rs.getLong("item_id"));
		bn.setItemName(rs.getString("item_name"));
	    bn.setRemarks(rs.getString("remarks"));
	    bn.setActiveStatus(rs.getInt("active_status"));
	    bn.setUomId(rs.getLong("uom_id"));
			
		return bn;
		
		}	
	};
	
	MapSqlParameterSource parameters = new MapSqlParameterSource();
	
	  
	parameters.addValue("designConsumeItemId", designConsumeItemId);
      
      System.out.println("query for get Design consume item object data : " + qry);
      
	// TODO Auto-generated method stub
	return  namedJdbcTemplate.query(qry,parameters,serviceMapper);
	

}


@Override
public Optional<ModelDesignConsumItem> findDesignConsumeItemByConsumeId(Long designConsumeItemId) {
	// TODO Auto-generated method stub
	return consumeItemRepository.findById(designConsumeItemId);
}


@Override
public List<ModelDesignCustom> getAllDesignConsumItemListByDesignId(Long designId) {
	//
	String  qry="SELECT dci.design_consum_item_id,dci.remarks,dci.item_qty,dci.active_status,i.item_name,d.design_id,u.uom_name,\r\n" + 
			"i.item_id \r\n" + 
			"FROM mlfm_design_consum_item dci\r\n" + 
			"INNER JOIN mlfm_design d ON d.design_id=dci.design_id\r\n" + 
			"INNER JOIN mlfm_item i on i.item_id=dci.item_id\r\n" + 
			"LEFT OUTER JOIN mlfm_uom u ON u.uom_id=dci.uom_id\r\n" + 
			"WHERE dci.design_id=COALESCE(:designId,dci.design_id)";
	/*
	 * sql query
	 * 
	 * SELECT dci.design_consum_item_id,dci.remarks,dci.item_qty,dci.active_status,i.item_name,d.design_id,u.uom_name,
i.item_id 
FROM mlfm_design_consum_item dci
INNER JOIN mlfm_design d ON d.design_id=dci.design_id
INNER JOIN mlfm_item i on i.item_id=dci.item_id
LEFT OUTER JOIN mlfm_uom u ON u.uom_id=dci.uom_id
WHERE dci.design_id=COALESCE(@designId,dci.design_id)
	 * 
	 * 
	 * */
	RowMapper<ModelDesignCustom> serviceMapper=new RowMapper<ModelDesignCustom>() {
		@Override
		public ModelDesignCustom mapRow(ResultSet rs, int rownum) throws SQLException{
			ModelDesignCustom bn=new ModelDesignCustom();
			
		bn.setDesignConsumItemId(rs.getLong("design_consum_item_id"));
		bn.setItemQty(rs.getLong("item_qty"));
		bn.setItemId(rs.getLong("item_id"));
		bn.setItemName(rs.getString("item_name"));
	    bn.setRemarks(rs.getString("remarks"));
	    bn.setActiveStatus(rs.getInt("active_status"));
	    
	    bn.setDesignId(rs.getLong("design_id"));
	    
	    bn.setUomName(rs.getString("uom_name"));
			
		return bn;
		
		}	
	};
	
	MapSqlParameterSource parameters = new MapSqlParameterSource();
	
	  
	parameters.addValue("designId", designId);
      
      System.out.println("query for get Design consume item object data : " + qry);
      
	// TODO Auto-generated method stub
	return  namedJdbcTemplate.query(qry,parameters,serviceMapper);
}


@Override
public List<ModelDesignConsumItem> getConsumItemNae(Long designId) {
	// TODO Auto-generated method stub
	return consumeItemRepository.getDesignConsumItemByDesignId(designId);
}





}
