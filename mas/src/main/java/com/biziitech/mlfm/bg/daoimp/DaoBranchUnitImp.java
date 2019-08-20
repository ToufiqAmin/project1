package com.biziitech.mlfm.bg.daoimp;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biziitech.mlfm.bg.dao.DaoBranchUnit;
import com.biziitech.mlfm.bg.model.ModelBranch;
import com.biziitech.mlfm.bg.model.ModelBranchUnit;
import com.biziitech.mlfm.bg.model.ModelOrg;

import com.biziitech.mlfm.repository.BgBranchUnitRepository;
import com.biziitech.mlfm.repository.BgOrgRepository;
import com.biziitech.mlfm.repository.BgBranchRepository;


@Service
public class DaoBranchUnitImp implements DaoBranchUnit{
	
	
	@Autowired
	private BgBranchUnitRepository bgBranchUnitRepository;
	
	@Autowired
	private BgOrgRepository BgOrgRepository;
	
	@Autowired
	private BgBranchRepository BgBranchRepository;
	
	private Long branchUnitId;
	
	
	public void saveBranchUnit(ModelBranchUnit modelBranchUnit) 
	{
		if(modelBranchUnit.isActive())
		{
			modelBranchUnit.setActiveStatus(1);
		}else 
		{
			modelBranchUnit.setActiveStatus(0);
		}
		
		bgBranchUnitRepository.save(modelBranchUnit);
	}


	@Override
	public List<ModelBranchUnit> getBranchUnitList() {
		// TODO Auto-generated method stub
		List<ModelBranchUnit> resultList=bgBranchUnitRepository.findAllBranchUnit();
		List<ModelBranchUnit> branchUnitList= new ArrayList();
		for(ModelBranchUnit unit: resultList) 
		{
			if(unit.getActiveStatus()==1) 
			{
				unit.setsActive("Yes");
				unit.setActive(true);
			}else 
			{
				unit.setsActive("No");
				unit.setActive(false);
			}
			branchUnitList.add(unit);
		}
		
		
		
		return branchUnitList;
	}


	@Override
	public Optional<ModelBranchUnit> getBranchUnitById(Long id) {
		// TODO Auto-generated method stub
		
		Optional<ModelBranchUnit> modelBranchUnit= bgBranchUnitRepository.findById(id);
		

		if(modelBranchUnit.get().getActiveStatus()==1)
		 {
			 modelBranchUnit.get().setActive(true);
			 
		 }
		 
		 else
		 {	 
			 modelBranchUnit.get().setActive(false);
		 }
		
		setBranchUnitId(modelBranchUnit.get().getBranchUnitId());
		
		return modelBranchUnit;
	}


	private void setBranchUnitId(Long branchUnitId) {
		// TODO Auto-generated method stub
		this.branchUnitId=branchUnitId;
		
	}
	
	@Override
	public List<ModelOrg> getOrgByOrgGroup(Long orgGroupId) {
	// TODO Auto-generated method stub
	return BgOrgRepository.findOrgByOrgGroup(orgGroupId);
	}
	



	@Override
	public List<ModelBranchUnit> getBranchListByCraiteria(Long branchId) {
		// TODO Auto-generated method stub
		List<ModelBranchUnit> resultList = bgBranchUnitRepository.findBranchDetails(branchId);
		List<ModelBranchUnit> branchUnitList= new ArrayList<>();
		
		
		for(ModelBranchUnit branchUnit: resultList) {

				if(branchUnit.getActiveStatus()==1)
				 { branchUnit.setsActive("Yes");
				  branchUnit.setActive(true);
				 }
				 
				 else
				 {	branchUnit.setsActive("NO");
				     branchUnit.setActive(false);
				     
				 }
			
			branchUnitList.add(branchUnit);
		}
		
		
		return branchUnitList;
	}


@Override
public List<ModelBranch> getBranchByOrg(Long orgId) {
	// TODO Auto-generated method stub
	return BgBranchRepository.findBranchByOrg(orgId);
}
	
	
	

}
