package com.javaweb.converter;

import com.javaweb.Model.BuildingDTO;
import com.javaweb.repository.entity.BuildingEntity;
import com.javaweb.repository.entity.RentAreaEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BuildingDTOConverter {

    @Autowired
    private ModelMapper modelMapper;
    public BuildingDTO toBuildingDTO(BuildingEntity item){
        BuildingDTO building = modelMapper.map(item, BuildingDTO.class);
        building.setAddress(item.getStreet() + ", " + item.getWard() + ", " +
                item.getDistrict().getName());

        List<RentAreaEntity> rentAreas = item.getItems();
        String rentArea = rentAreas.stream().map(it->it.getValue().toString()).collect(Collectors.joining(", "));
        building.setRentArea(rentArea);
        return building;
    }
}
