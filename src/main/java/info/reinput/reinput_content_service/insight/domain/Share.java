package info.reinput.reinput_content_service.insight.domain;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Share {
    private String id;
    private boolean isCopyable;

    public static Share createShare(boolean copyable) {
        return Share.builder()
                .id(UUID.randomUUID().toString())
                .isCopyable(copyable)
                .build();
    }

    public Share updateShare(boolean copyable) {
        this.isCopyable = copyable;
        return this;
    }
}
