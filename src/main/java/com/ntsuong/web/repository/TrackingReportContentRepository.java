package com.ntsuong.web.repository;

import com.ntsuong.web.dto.response.tracking.TrackingReportDtoResponse;
import com.ntsuong.web.model.TrackingReportContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
public interface TrackingReportContentRepository extends JpaRepository<TrackingReportContent, UUID>, JpaSpecificationExecutor {
    Long countByContentId(UUID id);
    @Query(value =
            "SELECT "+
                    " cast(atr.content_id as varchar) AS contentId, " +
                    "ac.title AS title, ac.slug AS slug," +
                    "count(*) AS totalView " +
                    "FROM aqs_tracking_report_content atr " +
                    "left join aqs_content ac "+
                    "on atr.content_id = ac.id "+
                    "where atr.metric = :metric " +
                    "GROUP BY atr.content_id , ac.title, ac.slug "+
                    "ORDER BY totalView DESC"
            , nativeQuery = true
    )
    List<TrackingReportDtoResponse> findGroupByContentIdWithNativeQuery(@Param("metric") String metric);

}
