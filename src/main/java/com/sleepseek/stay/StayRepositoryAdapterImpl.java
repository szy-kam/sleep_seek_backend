package com.sleepseek.stay;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

class StayRepositoryAdapterImpl implements StayRepositoryAdapter {
    private final StayRepository stayRepository;

    StayRepositoryAdapterImpl(StayRepository stayRepository) {
        this.stayRepository = stayRepository;
    }

    @Override
    public boolean existsById(Long id) {
        return stayRepository.existsById(id);
    }

    @Override
    public Page<Stay> findAllByParameters(StaySearchParameters parameters) {
        return stayRepository.findAll(PageRequest.of(parameters.getPageNumber(), parameters.getPageSize()));
    }

    @Override
    public Page<Stay> findAllByUser_Username(String username, Pageable pageable) {
        return stayRepository.findAllByUser_Username(username, pageable);
    }

    @Override
    public Stay save(Stay stay) {
        return stayRepository.save(stay);
    }

    @Override
    public Optional<Stay> findById(Long id) {
        return stayRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        stayRepository.deleteById(id);
    }
}
