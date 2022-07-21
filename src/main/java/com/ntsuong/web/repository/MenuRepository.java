package com.ntsuong.web.repository;

import com.ntsuong.web.model.Menu;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 *
 * @author  Nguyen Thanh Suong
 * @version 1.0
 * @since   2022-06-01
 */
@Repository
public interface MenuRepository extends JpaRepository<Menu, UUID>, JpaSpecificationExecutor {
    Optional<Menu> findById(UUID id);
    List<Menu> findByParent(Menu menu, Sort sort);
}
