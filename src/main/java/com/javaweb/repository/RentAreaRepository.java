package com.javaweb.repository;

import com.javaweb.repository.entity.BuildingEntity;
import com.javaweb.repository.entity.RentAreaEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface RentAreaRepository {
    List<RentAreaEntity> findAll(Integer rentArea);
}
