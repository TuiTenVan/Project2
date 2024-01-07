package com.javaweb.api;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.javaweb.Model.BuildingDTO;
import com.javaweb.service.BuildingService;

//duong van du da xuat hien
@RestController
public class BuildingAPI {
	
	@Autowired
	private BuildingService buildingService;

	@GetMapping(value = "/api/building/")
	public List<BuildingDTO> getBuilding(@RequestParam Map<String, String> params,
            						@RequestParam(value = "typeCode", required = false) List<String> typeCode) {
		Map<String, Object> conditions = new LinkedHashMap<>();
		conditions.put("name", params.get("name"));
		conditions.put("districtId", params.get("districtId"));
		conditions.put("floorArea", params.get("floorArea"));
		conditions.put("street", params.get("street"));
		conditions.put("ward", params.get("ward"));
		conditions.put("numberOfBasement", params.get("numberOfBasement"));
		conditions.put("direction", params.get("direction"));
		conditions.put("level", params.get("level"));
		conditions.put("managerName", params.get("managerName"));
		conditions.put("managerPhoneNumber", params.get("managerPhoneNumber"));
		conditions.put("minRentPrice", params.get("minRentPrice"));
		conditions.put("maxRentPrice", params.get("maxRentPrice"));
		conditions.put("minRentArea", params.get("minRentArea"));
		conditions.put("maxRentArea", params.get("maxRentArea"));
		conditions.put("typeCode", typeCode);
		conditions.put("id", params.get("id"));
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
