package com.biziitech.mlfm.bg.daoimp;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biziitech.mlfm.bg.dao.DaoBranch;
import com.biziitech.mlfm.bg.model.ModelBranch;
import com.biziitech.mlfm.repository.BgBranchRepository;


@Service
public class DaoBranchImp implements DaoBranch{
	
	@Autowired
	private BgBranchRepository bgBranchRepository;
	
	
	
	private Long branchId;
	
	public void saveBranch(ModelBranch modelBranch)
	{
		if(modelBranch.isActive()) 
		{
			modelBranch.setActiveStatus(1);
		}else 
		{
			modelBranch.setActiveStatus(0);
		}
		
		bgBranchRepository.save(modelBranch);
		
		
	}

	@Override
	public List<ModelBranch> getBranchName() {
		// TODO Auto-generated method stub
		return bgBranchRepository.findAll();
	}

	@Override
	public List<ModelBranch> getBranchList() {
		// TODO Auto-generated method stub
		List<ModelBranch> resultList = bgBranchRepository.findBranch();
		List<ModelBranch> orgList= new ArrayList<>();
		
		for(ModelBranch branch: resultList) {
			if(branch.getActiveStatus()==1)
				if(branch.getActiveStatus()==1)
				 { branch.setsActive("Yes");
				  branch.setActive(true);
				 }
				 
				 else
				 {	 branch.setsActive("NO");
				     branch.setActive(false);
				     
				 }
				 
			orgList.add(branch);
	}
		return orgList;	
	
	}

	@Override
	public Optional<ModelBranch> getBranchById(Long id) {
		// TODO Auto-generated method stub
		Optional<ModelBranch> modelBranch= bgBranchRepository.findById(id);
		
		if(modelBranch.get().getActiveStatus()==1)
		{
			modelBranch.get().setActive(true);
		}
		else 
		{
			modelBranch.get().setActive(false);
		}
		
		setOrgbranchId(modelBranch.get().getBranchId());
		
		return modelBranch;
	}

	private void setOrgbranchId(Long branchId) {
		// TODO Auto-generated method stub
		this.branchId=branchId;
		
	}

	@Override
	public List<ModelBranch> getBranchByOrgId(Long id) {
		// TODO Auto-generated method stub
		List<ModelBranch> resultList = bgBranchRepository.findBranchByOrg(id);
		List<ModelBranch> branchList= new ArrayList<>();
		
		for(ModelBranch branch: resultList) {
			if(branch.getActiveStatus()==1)
				if(branch.getActiveStatus()==1)
				 { branch.setsActive("Yes");
				  branch.setActive(true);
				 }
				 
				 else
				 {	 branch.setsActive("NO");
				     branch.setActive(false);
				     
				 }
				 
			branchList.add(branch);
	}
		return branchList;
	}
	

}
