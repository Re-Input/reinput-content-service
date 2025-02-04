package info.reinput.reinput_content_service.insight.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EntityListeners;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Embeddable
@Builder
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class TimeAudit {

    @CreatedDate
    @Column(updatable = false, name = "created_at")
    protected LocalDateTime createdAt;
    @LastModifiedDate
    @Column(name = "updated_at")
    protected LocalDateTime updatedAt;

    public static TimeAudit of() {
        TimeAudit timeAudit = TimeAudit.builder()
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        return timeAudit;
    }
}
