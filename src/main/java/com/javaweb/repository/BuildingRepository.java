package com.javaweb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javaweb.repository.Custom.BuildingRepositoryCustom;
import com.javaweb.repository.entity.BuildingEntity;

public interface BuildingRepository extends JpaRepository<BuildingEntity, Integer>, BuildingRepositoryCustom {

	void deleteByIdIn(Integer[] ids);
	List<BuildingEntity> findByNameContaining(String s);
	List<BuildingEntity> findByNameContainingAndStreetContaining(String s, String x);
}
