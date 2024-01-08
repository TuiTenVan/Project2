package com.javaweb.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.javaweb.Model.BuildingDTO;
import com.javaweb.service.BuildingService;


@RestController
public class BuildingAPI {
	
	@Autowired
	private BuildingService buildingService;

	@GetMapping(value = "/api/building/")
	public List<BuildingDTO> getBuilding(@RequestParam Map<String, Object> conditions) {
		List<BuildingDTO> result = buildingService.findAll(conditions);
		return result;
	}

//	public void valiDate(BuildingDTO buildingDTO) throws FieldRequiredException {
//		if(buildingDTO.getName() == null || buildingDTO.getNumberOfBasement() == null || buildingDTO.getName().equals("")) {
//			throw new FieldRequiredException("name or numberOfBasement is null!");
//		}
//	}
//	@PostMapping(value="/api/building/")
//	public BuildingDTO getBuilding2(@RequestBody BuildingDTO buildingDTO) {
//		return buildingDTO;
//	}
//
//	@DeleteMapping(value="/api/building/{id}/{name}")
//	public void deleteBuilding (@PathVariable Integer id,
//								@PathVariable String name,
//								@RequestParam(value="ward", required = false) String ward) {
//		System.out.println("Da xoa " + id + " va " + name + " roi nhe!");
//	}
}
