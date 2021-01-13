package com.sleepseek.property;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PropertyDefinitionRepository extends JpaRepository<PropertyDefinition, Long> {
    List<PropertyDefinition> findPropertyDefinitionsByType(String type);

    PropertyDefinition findByName(String name);
}
