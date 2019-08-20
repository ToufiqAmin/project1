package com.biziitech.mlfm.dao;

import java.util.ArrayList;
import java.util.List;

import com.biziitech.mlfm.custom.model.ModelUOMCustom;
import com.biziitech.mlfm.model.ModelMachineType;
import com.biziitech.mlfm.model.ModelUOM;


public interface DaoUOM {
public void saveUOM(ModelUOM modelUOM);

public List<ModelUOM> getUOMList();
public List<ModelUOM> getUomName();
public List<ModelUOM> getUOMListByCraiteria(String uomName, String shortCode, String remarks, int  status);



public List<ModelUOMCustom> getUOMListCustom();
}
