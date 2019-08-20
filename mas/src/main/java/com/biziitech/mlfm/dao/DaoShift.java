package com.biziitech.mlfm.dao;

import java.util.List;

import com.biziitech.mlfm.model.ModelShift;


public interface DaoShift {
	
	public void saveShift(ModelShift shift);
	public List<ModelShift> getShiftListByCraiteria(String shiftName, String shortCode, String remarks, int  status);
	


}
