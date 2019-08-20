package com.biziitech.mlfm.bg.dao;

import java.util.List;

import com.biziitech.mlfm.bg.model.ModelCurrency;

public interface DaoCurrency {

	public void saveCurrency(ModelCurrency currency);
	
	public List<ModelCurrency> getCurrencyListByCraiteria(String currencyName, String shortCode, String remarks, int  status);
}
