package com.biziitech.mlfm.daoimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biziitech.mlfm.dao.DaoShift;
import com.biziitech.mlfm.model.ModelShift;

import com.biziitech.mlfm.repository.ShiftRepository;

@Service
public class DaoShiftImp implements DaoShift {
	
	@Autowired
	private ShiftRepository shiftRepository;
	
		@Override
		public void saveShift(ModelShift shift) {
			if(shift.isActive())
				shift.setActiveStatus(1);
			else
				shift.setActiveStatus(0);
			
			shiftRepository.save(shift);
			
		}
		
		
		@Override
		public List<ModelShift> getShiftListByCraiteria(String shiftName, String shortCode, String remarks,int status) {
			
			List<ModelShift> resultList = shiftRepository.findShiftDetails(shiftName, shortCode, remarks, status);
			List<ModelShift> shiftList= new ArrayList<>();
			
			for(ModelShift shift: resultList) {
				if(shift.getActiveStatus()==1)
					if(shift.getActiveStatus()==1)
					 { shift.setsActive("Yes");
					 shift.setActive(true);
					 }
					 
					 else
					 {	 shift.setsActive("NO");
					     shift.setActive(false);
					     
					 }
					 
				shiftList.add(shift);
			}
			// TODO Auto-generated method stub
			return shiftList;
		}
		
		

}
