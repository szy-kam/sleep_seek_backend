package com.sleepseek.stay;

import com.google.common.collect.Maps;

import java.util.*;

class InMemoryStayRepository implements StayRepository{

    private final Map<Long, Stay> stays = Maps.newConcurrentMap();

    @Override
    public Optional<Stay> findById(Long id) {
        return Optional.ofNullable(stays.get(id));
    }

    @Override
    public boolean existsById(Long id) {
        return stays.containsKey(id);
    }

    @Override
    public Stay save(Stay stay) {
        return stays.put(stay.getId(), stay);
    }

    @Override
    public List<Stay> findInRange(Integer from, Integer to) {
        Stay[] staysArray = (Stay[])stays.values().toArray();
        return new ArrayList<>(Arrays.asList(staysArray).subList(from, to));
    }
}
