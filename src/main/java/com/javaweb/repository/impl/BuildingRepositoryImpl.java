package com.javaweb.repository.impl;

import com.javaweb.builder.BuildingSearchBuilder;
import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.entity.BuildingEntity;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
@Primary
public class BuildingRepositoryImpl  implements BuildingRepository {
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public List<BuildingEntity> findAll(BuildingSearchBuilder buildingSearchBuilder) {
        //JPQL
//        String sql = "FROM BuildingEntity b WHERE b.id = 1";
//        Query query = entityManager.createQuery(sql,BuildingEntity.class);

//        SQL Native
        String sql = "SELECT * FROM building b WHERE b.name like '%E%'";
        Query query = entityManager.createNativeQuery(sql, BuildingEntity.class);
        return query.getResultList();
    }
}
