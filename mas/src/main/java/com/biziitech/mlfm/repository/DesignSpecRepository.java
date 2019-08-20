package com.biziitech.mlfm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import com.biziitech.mlfm.model.ModelDesignSpec;

public interface DesignSpecRepository extends JpaRepository <ModelDesignSpec,Long>{
	
	@Query("select a from MLFM_DESIGN_SPEC a where a.modelDesign.designId=COALESCE(:designId,a.modelDesign.designId)")
	public List<ModelDesignSpec> getDesignSpecDataByDesignId(@Param("designId") Long designId);
	
	//@Query("select a from MLFM_DESIGN_SPEC a where a.modelDesign.designId=COALESCE(:designId,a.modelDesign.designId) and a.modelSpec.specId=COALECSE(:specId,a.modelSpec.specId)")
	  @Query("select b from MLFM_DESIGN_SPEC b where b.modelDesign.designId=COALESCE(:designId,b.modelDesign.designId) and b.modelSpec.specId=COALESCE(:specId,b.modelSpec.specId)")
	public List<ModelDesignSpec> checkDesignSpec(@Param("designId") Long designId,@Param("specId") Long specId);
	

}
