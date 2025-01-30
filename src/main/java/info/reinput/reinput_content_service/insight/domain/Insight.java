package info.reinput.reinput_content_service.insight.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "insight")
public class Insight {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "insight_id")
    private Long id;

    @Embedded
    private InsightSummary summary;

    @ElementCollection
    @CollectionTable(name = "insight_images", joinColumns = @JoinColumn(name = "insight_id"))
    private List<Images> images;

    @Embedded
    private InsightDetail detail;

    @OneToMany(mappedBy = "insight", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HashTag> hashTags;

    @Column(name = "folder_id")
    private Long folderId;

    @Embedded
    private TimeAudit timeAudit;
}
