package com.biziitech.mlfm.dao;

import java.util.List;

import com.biziitech.mlfm.model.ModelOrderOwnerFeedback;

public interface DaoOrderOwnerFeedback {

	public void saveFeedback(ModelOrderOwnerFeedback  feedback);
	
	public List<ModelOrderOwnerFeedback> getFeedbackList(Long id);
	
	
}
