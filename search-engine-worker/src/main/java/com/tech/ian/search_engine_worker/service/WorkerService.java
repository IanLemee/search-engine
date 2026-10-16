package com.tech.ian.search_engine_worker.service;

import com.tech.ian.search_engine_worker.model.PrefixModel;
import com.tech.ian.search_engine_worker.repository.PrefixRepository;
import com.tech.ian.search_engine_worker.trie.Trie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import redis.clients.jedis.RedisClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class WorkerService {
    private final PrefixRepository prefixRepository;
    private final Trie trie;
    private final RedisClient redis;

    private static final Logger LOGGER = LoggerFactory.getLogger(WorkerService.class);

    public WorkerService(PrefixRepository prefixRepository, Trie trie, RedisClient redis) {
        this.prefixRepository = prefixRepository;
        this.trie = trie;
        this.redis = redis;
    }

    @KafkaListener(topics = "precomputedValues", groupId = "search-worker")
    public void addWord(String word) {
        LOGGER.info("Adding word: {}", word);
        Map<String, String> stringListMap = trie.addWord(word);
        for (Map.Entry<String, String> entry : stringListMap.entrySet()) {
            saveRedis(entry);
            saveMongo(entry);
        }
    }

    private void saveRedis(Map.Entry<String, String> entry) {
        LOGGER.info("Persisting at cache level key: {} and value: {}",entry.getKey(), entry.getValue());
        redis.zincrby(entry.getKey(), 1, entry.getValue());
    }

    private void saveMongo(Map.Entry<String, String> entry) {
        LOGGER.info("Persisting at database level key: {} and value: {}",entry.getKey(), entry.getValue());
        PrefixModel prefixModel = prefixRepository.findPrefixModelById(entry.getKey()).orElseGet(() -> new PrefixModel(entry.getKey(), new ArrayList<>()));
        if (prefixModel.getMap().isEmpty()) {
            prefixModel.getMap().add(new HashMap<>());
        }
        prefixModel.getMap().forEach(map ->
                map.put(entry.getValue(), map.getOrDefault(entry.getValue(), 0) +1)
        );
        prefixRepository.save(prefixModel);
    }
}
