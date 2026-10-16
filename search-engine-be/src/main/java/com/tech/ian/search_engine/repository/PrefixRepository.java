package com.tech.ian.search_engine.repository;


import com.tech.ian.search_engine.model.PrefixModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface PrefixRepository extends MongoRepository<PrefixModel, String> {
    @Query("{_id: '?0'}")
    Optional<PrefixModel> findPrefixModelById(String id);
}
