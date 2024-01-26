package com.javaweb.api;

import com.javaweb.Model.BuildingDTO;
import com.javaweb.service.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

//Van's project2
@RestController
@PropertySource("classpath:application.properties")
public class BuildingAPI {

	@Value("${dev.nguyen}")
	private String data;

	@Autowired
	private BuildingService buildingService;

	@GetMapping(value = "/api/building/")
	public List<BuildingDTO> getBuilding(@RequestParam Map<String, Object> conditions,
										 @RequestParam (name="typeCode", required = false) List<String> typeCode) {
		List<BuildingDTO> result = buildingService.findAll(conditions, typeCode);
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
	@DeleteMapping(value="/api/building/{id}")
	public void deleteBuilding (@PathVariable Integer id) {
//		System.out.println("Da xoa " + id + " va " + name + " roi nhe!");
		System.out.println(data);
	}
}
