package com.ntsuong.web.repository;

import com.ntsuong.web.model.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 *
 * @author  Nguyen Thanh Suong
 * @version 1.0
 * @since   2022-06-01
 */
@Repository
public interface AuthorityRepository  extends JpaRepository<Authority, UUID>, JpaSpecificationExecutor {

}
