package com.biziitech.mlfm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.biziitech.mlfm.model.ModelPurchasePrice;

public interface PurchasePriceRepository extends JpaRepository <ModelPurchasePrice,Long>{

	@Query("select a from MLFM_PURCHASE_PRICE a where a.modelUOM.uomId=:uomId")
	public List<ModelPurchasePrice> findPriceByUOM(@Param("uomId") Long uOMId);
}
