package info.reinput.reinput_content_service.insight.domain;

import info.reinput.reinput_content_service.util.S3FilePathUtil;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "image")
public class Image {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "insight_id")
    private Insight insight;

    private String imagePath;

    public static Image from(String originalFileName){
        String path = S3FilePathUtil.generateFilePath(originalFileName);
        return Image.builder()
                .imagePath(path)
                .build();
    }

    public String getPublicUrl(){
        return String.format("https://static.reinput.info/%s", imagePath);
    }
}
