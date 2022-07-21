package com.ntsuong.web.repository;

import com.ntsuong.web.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 *
 * @author  Nguyen Thanh Suong
 * @version 1.0
 * @since   2022-06-01
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID>, JpaSpecificationExecutor {
    Optional<Category> findById(UUID id);

}