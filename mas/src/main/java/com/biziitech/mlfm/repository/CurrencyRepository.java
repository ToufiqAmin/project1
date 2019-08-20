package com.biziitech.mlfm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.biziitech.mlfm.bg.model.ModelCurrency;
import com.biziitech.mlfm.model.ModelUOM;

public interface CurrencyRepository extends JpaRepository <ModelCurrency,Long> {

	@Query("select a from BG_CURRENCY a  where a.currencyName LIKE CONCAT('%',:currencyName,'%') and a.shortCode LIKE CONCAT('%',:shortCode,'%') and a.remarks LIKE CONCAT('%',:remarks,'%') and a.activeStatus=:status")
	public List <ModelCurrency> findCureencyDetails(@Param("currencyName")String currencyName, @Param("shortCode")String shortCode, @Param("remarks")String remarks, @Param("status") int status);
	
	@Query("select a from BG_CURRENCY a where a.activeStatus=1 order by a.currencyName" )
	public List<ModelCurrency> findCurrency();
	
	
}
