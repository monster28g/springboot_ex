package com.hhi.ees.platform.repository;

import com.hhi.ees.platform.Entities.QueryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QueryRepository extends JpaRepository<QueryEntity, Long> {
}
