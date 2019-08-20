package com.biziitech.mlfm.bg.dao;

import java.util.List;

import com.biziitech.mlfm.bg.model.ModelObjectProcess;

public interface DaoObjectProcess {

	public void saveObjectProcess(ModelObjectProcess objectProcess);
	public List<ModelObjectProcess> getObjectProcessFromObject(Long id);
}
