package com.biziitech.mlfm.bg.daoimp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biziitech.mlfm.bg.dao.DaoCurrency;
import com.biziitech.mlfm.bg.model.ModelCurrency;
import com.biziitech.mlfm.model.ModelUOM;
import com.biziitech.mlfm.repository.CurrencyRepository;
import com.biziitech.mlfm.repository.ModuleRepository;

@Service
public class DaoCurrencyImp implements DaoCurrency {

	@Autowired
	private CurrencyRepository currencyRepository;
	
	@Override
	public void saveCurrency(ModelCurrency currency) {
		
		if(currency.isActive()) {
			currency.setActiveStatus(1);
		}
		else {
			currency.setActiveStatus(0);
		}
		
		
		currencyRepository.save(currency);
		
	}

	@Override
	public List<ModelCurrency> getCurrencyListByCraiteria(String currencyName, String shortCode, String remarks,
			int status) {
		List<ModelCurrency> resultList = currencyRepository.findCureencyDetails(currencyName, shortCode, remarks, status);
		List<ModelCurrency> currencyList= new ArrayList<>();
		
		for(ModelCurrency currency: resultList) {
			if(currency.getActiveStatus()==1)
				if(currency.getActiveStatus()==1)
				 { currency.setsActive("Yes");
				 currency.setActive(true);
				 }
				 
				 else
				 {	 currency.setsActive("NO");
				     currency.setActive(false);
				     
				 }
				 
			currencyList.add(currency);
		}
		return currencyList;
	}

}
