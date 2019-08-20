package com.biziitech.mlfm.daoimpl;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.biziitech.mlfm.bg.model.ModelModule;
import com.biziitech.mlfm.dao.DaoOrderOwnerFeedback;
import com.biziitech.mlfm.model.ModelOrder;
import com.biziitech.mlfm.model.ModelOrderItemQtySpec;
import com.biziitech.mlfm.model.ModelOrderOwnerFeedback;
import com.biziitech.mlfm.repository.OrderOwnerFeedbackRepository;

@Service
public class DaoOrderOwnerFeedbackImp implements DaoOrderOwnerFeedback {
   
	@Autowired
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private OrderOwnerFeedbackRepository orderOwnerFeedbackRepository;

	
	private List<ModelOrderOwnerFeedback> feedbackList;
	public List<ModelOrderOwnerFeedback> getFeedbackList() {
		return feedbackList;
	}
	public void setFeedbackList(List<ModelOrderOwnerFeedback> feedbackList) {
		this.feedbackList = feedbackList;
	}
	
	@Override
	public void saveFeedback(ModelOrderOwnerFeedback feedback) {
		
		
		orderOwnerFeedbackRepository.save(feedback);
		
	}


	@Override
	public List<ModelOrderOwnerFeedback> getFeedbackList(Long id) {
		
		List<ModelOrderOwnerFeedback> feedbackListByQtyId=orderOwnerFeedbackRepository.findFeddbackById(id);
		setFeedbackList(feedbackListByQtyId);
		return feedbackListByQtyId;
	}

	
	public void updateFeedback(ModelOrderOwnerFeedback feedback){
        try {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.update("update MLFM_ORDER_OWNER_FEEDBACK SET remarks=?, order_status=?, cancel_reason=?, active_status=?, feedback_date=?, updated_by=?, update_timestamp=? where order_owner_feedback_id=?",feedback.getRemarks(),feedback.getOrderStatus(),feedback.getCancelReason(),feedback.getActiveStatus(),feedback.getFeedbackDate(),feedback.getUpdatedBy(),feedback.getUpdateTimestamp(),feedback.getOrderOwnerFeedbackId());
        }
        
        catch(Exception e) {
        	e.printStackTrace();
}

	}


 
}
