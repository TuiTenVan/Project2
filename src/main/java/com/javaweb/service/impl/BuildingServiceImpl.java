package com.javaweb.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaweb.Model.BuildingDTO;
import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.DistrictRepository;
import com.javaweb.repository.RentAreaRepository;
import com.javaweb.repository.entity.BuildingEntity;
import com.javaweb.repository.entity.RentAreaEntity;
import com.javaweb.service.BuildingService;

@Service
public class BuildingServiceImpl implements BuildingService {
	
	@Autowired
	private BuildingRepository buildingRepository;
	@Autowired
	private DistrictRepository districtRepository;
	@Autowired
	private RentAreaRepository rentAreaRepository;
	
	@Override
	public List<BuildingDTO> findAll(Map<String, Object> conditions, List<String> typeCode) {
		// TODO Auto-generated method stub
		List<BuildingEntity> buildingEntities = buildingRepository.findAll(conditions, typeCode);
		List<BuildingDTO> result = new ArrayList<BuildingDTO>();

		for(BuildingEntity item : buildingEntities) {
			BuildingDTO building = new BuildingDTO();

			building.setName(item.getName());
			building.setAddress(item.getStreet() + ", " + item.getWard() + ", " +
					districtRepository.findOne(item.getDistrictId()).getName());

			building.setNumberOfBasement(item.getNumberOfBasement());
			building.setManagerName(item.getManagerName());
			building.setManagerPhoneNumber(item.getManagerPhoneNumber());
			building.setFloorArea(item.getFloorArea());
			
			List<RentAreaEntity> rentAreas = rentAreaRepository.findAll(item.getId());
			String rentArea = "";
			for(RentAreaEntity rent : rentAreas) {
				rentArea += rent.getValue() + ", ";
			}
			rentArea = rentArea.substring(0, rentArea.length() - 2);
			building.setRentArea(rentArea);

			building.setRentPrice(item.getRentPrice());
			building.setServiceFee(item.getServiceFee());
			building.setBrokerageFee(item.getBrokerageFee());
			result.add(building);
		}
		return result;
	}

}
