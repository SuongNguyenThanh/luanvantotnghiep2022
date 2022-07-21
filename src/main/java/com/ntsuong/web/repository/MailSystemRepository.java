package com.ntsuong.web.repository;

import com.ntsuong.web.model.MailSystem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 *
 * @author  Nguyen Thanh Suong
 * @version 1.0
 * @since   2022-06-01
 */
@Repository
public interface MailSystemRepository extends JpaRepository<MailSystem, UUID>, JpaSpecificationExecutor {

    List<MailSystem> findByStatus(int status);
}
