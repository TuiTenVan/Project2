package com.javaweb.api;

import com.javaweb.Model.BuildingDTO;
import com.javaweb.Model.BuildingRequestDTO;
import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.entity.BuildingEntity;
import com.javaweb.repository.entity.DistrictEntity;
import com.javaweb.service.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;

//Van's project2
@RestController
@PropertySource("classpath:application.properties")
@Transactional
public class BuildingAPI {

	@Value("${dev.nguyen}")
	private String data;

	@Autowired
	private BuildingService buildingService;
	
	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private BuildingRepository buildingRepository;

	@GetMapping(value = "/api/building/")
	public List<BuildingDTO> getBuilding(@RequestParam Map<String, Object> conditions,
										 @RequestParam (name="typeCode", required = false) List<String> typeCode) {
		List<BuildingDTO> result = buildingService.findAll(conditions, typeCode);
		return result;

	}

//	@GetMapping(value = "/api/building/{id}")
//	public BuildingDTO getBuildingById(@PathVariable Integer id) {
//		BuildingDTO result = new BuildingDTO();
//		BuildingEntity building = buildingRepository.findById(id).get();
//		return result;
//	}

	@GetMapping(value = "/api/building/{name}/{street}")
	public BuildingDTO getBuilding(@PathVariable String name,
									@PathVariable String street) {
		BuildingDTO result = new BuildingDTO();
		List<BuildingEntity> building = buildingRepository.findByNameContainingAndStreetContaining(name, street);
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

	@PostMapping(value="/api/building/")
	
	public void createBuilding(@RequestBody BuildingRequestDTO buildingRequestDTO){
		BuildingEntity buildingEntity = new BuildingEntity();
		buildingEntity.setName(buildingRequestDTO.getName());
		buildingEntity.setWard(buildingRequestDTO.getWard());
		DistrictEntity districtEntity = new DistrictEntity();
		districtEntity.setId(buildingRequestDTO.getDistrictId());
		buildingEntity.setDistrict(districtEntity);
		buildingEntity.setStreet(buildingRequestDTO.getStreet());
//		entityManager.persist(buildingEntity); //JPA
		buildingRepository.save(buildingEntity); //Spring-Data-JPA
	}
	
	@PutMapping(value="/api/building/")

	public void updateBuilding(@RequestBody BuildingRequestDTO buildingRequestDTO){
		BuildingEntity buildingEntity = buildingRepository.findById(buildingRequestDTO.getId()).get();
		buildingEntity.setId(4);
		buildingEntity.setName(buildingRequestDTO.getName());
		buildingEntity.setWard(buildingRequestDTO.getWard());
		DistrictEntity districtEntity = new DistrictEntity();
		districtEntity.setId(buildingRequestDTO.getDistrictId());
		buildingEntity.setDistrict(districtEntity);
		buildingEntity.setStreet(buildingRequestDTO.getStreet());
//		entityManager.merge(buildingEntity); //JPA
		buildingRepository.save(buildingEntity); //Spring-Data-JPA
	}
	@DeleteMapping(value="/api/building/{ids}")
	public void deleteById (@PathVariable Integer[] ids) {
		buildingRepository.deleteByIdIn(ids);
	}
}
