package info.reinput.reinput_content_service.insight.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "hash_tag")
@AllArgsConstructor
@Builder
@Entity
public class HashTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hash_tag_id")
    private Long id;

    @Column(name = "hash_tag_name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "insight_id")
    private Insight insight;

    public static List<HashTag> of(List<String> hashTags, Insight insight) {
        return hashTags.stream()
                .map(tag -> HashTag.builder()
                        .name(tag)
                        .insight(insight)
                        .build())
                .toList();
    }
}