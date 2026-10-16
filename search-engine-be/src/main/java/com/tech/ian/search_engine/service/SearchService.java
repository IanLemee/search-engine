package com.tech.ian.search_engine.service;

import com.tech.ian.search_engine.model.PrefixModel;
import com.tech.ian.search_engine.repository.PrefixRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import redis.clients.jedis.RedisClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SearchService {
    private final RedisClient redisClient;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final PrefixRepository prefixRepository;

    public SearchService(RedisClient redisClient, KafkaTemplate<String, Object> kafkaTemplate, PrefixRepository prefixRepository) {
        this.redisClient = redisClient;
        this.kafkaTemplate = kafkaTemplate;
        this.prefixRepository = prefixRepository;
    }

    public void add(String word) {
       kafkaTemplate.send("precomputedValues", word);
    }

    public List<String> search(String prefix) {
        List<String> zrevrange = redisClient.zrevrange(prefix, 0, 4);
        if (!zrevrange.isEmpty()) return zrevrange;
        try {
            PrefixModel prefixModelById = prefixRepository.findPrefixModelById(prefix).orElseThrow(Exception::new);
            List<String> res = new ArrayList<>();
            prefixModelById.getMap()
                    .forEach(map -> {
                        List<String> list = map.entrySet().stream().sorted(Map.Entry.<String, Integer>comparingByValue().reversed()).limit(5).map(Map.Entry::getKey).toList();
                        res.addAll(list);
                    });
            return res;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
