package com.javaweb.repository.Custom;

import java.util.List;

import com.javaweb.builder.BuildingSearchBuilder;
import com.javaweb.repository.entity.BuildingEntity;

public interface BuildingRepositoryCustom {
	List<BuildingEntity> findAll(BuildingSearchBuilder buildingSearchBuilder);
}
