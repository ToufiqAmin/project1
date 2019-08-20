package com.biziitech.mlfm.bg.daoimp;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biziitech.mlfm.bg.dao.DaoBranchUnitLoc;
import com.biziitech.mlfm.bg.model.ModelBranchUnitLoc;
import com.biziitech.mlfm.repository.BgBranchUnitLocRepository;

@Service
public class DaoBranchUnitLocImp implements DaoBranchUnitLoc{
	
	@Autowired
	private BgBranchUnitLocRepository bgBranchUnitLocRepository;
	
	private Long branchUnitLocId;

	@Override
	public List<ModelBranchUnitLoc> getBranchUnitLocList() {
		// TODO Auto-generated method stub
		
		List<ModelBranchUnitLoc> resultList=bgBranchUnitLocRepository.findAllBranchUnitLoc();
		List<ModelBranchUnitLoc> branchUnitLocList= new ArrayList();
		for(ModelBranchUnitLoc unitLoc: resultList) 
		{
			if(unitLoc.getActiveStatus()==1) 
			{
				unitLoc.setsActive("Yes");
				unitLoc.setActive(true);
			}else 
			{
				unitLoc.setsActive("No");
				unitLoc.setActive(false);
			}
			branchUnitLocList.add(unitLoc);
		}
		return branchUnitLocList;
	}

	@Override
	public void saveBranchUnitLoc(ModelBranchUnitLoc modelBranchUnitLoc) {
		// TODO Auto-generated method stub
		
		if(modelBranchUnitLoc.isActive()) 
		{
			modelBranchUnitLoc.setActiveStatus(1);
		}else 
		{
			modelBranchUnitLoc.setActiveStatus(0);
		}
		
		bgBranchUnitLocRepository.save(modelBranchUnitLoc);
		
//		return null;
	}

	@Override
	public Optional<ModelBranchUnitLoc> getBranchUnitLocById(Long id) {
		// TODO Auto-generated method stub
		Optional<ModelBranchUnitLoc> modelBranchUnitLoc= bgBranchUnitLocRepository.findById(id);
		

		if(modelBranchUnitLoc.get().getActiveStatus()==1)
		 {
			 modelBranchUnitLoc.get().setActive(true);
			 
		 }
		 
		 else
		 {	 
			 modelBranchUnitLoc.get().setActive(false);
		 }
		
		setBranchUnitLocId(modelBranchUnitLoc.get().getBranchUnitLocId());
		
		return modelBranchUnitLoc;

	}

	private void setBranchUnitLocId(Long branchUnitLocId) {
		// TODO Auto-generated method stub
		this.branchUnitLocId=branchUnitLocId;
		
	}

	@Override
	public List<ModelBranchUnitLoc> getBranchUnitLocListByBranchUnitId(Long id) {
		// TODO Auto-generated method stub
		List<ModelBranchUnitLoc> resultList = bgBranchUnitLocRepository.findBranchUnitLocByBranchUnitId(id);	
		List<ModelBranchUnitLoc> branchUnitLocList= new ArrayList();
		for(ModelBranchUnitLoc unitLoc: resultList) 
		{
			if(unitLoc.getActiveStatus()==1) 
			{
				unitLoc.setsActive("Yes");
				unitLoc.setActive(true);
			}else 
			{
				unitLoc.setsActive("No");
				unitLoc.setActive(false);
			}
			branchUnitLocList.add(unitLoc);
		}
		return branchUnitLocList;
	
	}

}
