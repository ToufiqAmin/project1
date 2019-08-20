package com.biziitech.mlfm.bg.daoimp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biziitech.mlfm.bg.dao.DaoOrgBranch;
import com.biziitech.mlfm.bg.model.ModelOrgBranch;
import com.biziitech.mlfm.repository.BgOrgBranchRepository;


@Service
public class DaoOrgBranchImp implements DaoOrgBranch{
	
	@Autowired
	private BgOrgBranchRepository BgOrgBranchRepository;
	
	
	@Override
	public List<ModelOrgBranch> getOrgBranchList() {
		
		List<ModelOrgBranch> resultList = BgOrgBranchRepository.findOrgBranch();
		List<ModelOrgBranch> orgList= new ArrayList<>();
		
		for(ModelOrgBranch orgBranch: resultList) {
			if(orgBranch.getActiveStatus()==1)
				if(orgBranch.getActiveStatus()==1)
				 { orgBranch.setsActive("Yes");
				  orgBranch.setActive(true);
				 }
				 
				 else
				 {	 orgBranch.setsActive("NO");
				     orgBranch.setActive(false);
				     
				 }
				 
			orgList.add(orgBranch);
		}
		
		
		
		return orgList;
	}


	@Override
	public List<ModelOrgBranch> getOrgBranchName() {
		// TODO Auto-generated method stub
		return BgOrgBranchRepository.findAll();
	}

}
