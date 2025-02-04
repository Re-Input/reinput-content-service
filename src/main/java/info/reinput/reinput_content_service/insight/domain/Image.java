package info.reinput.reinput_content_service.insight.domain;

import info.reinput.reinput_content_service.util.S3FilePathUtil;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

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

    public static List<Image> of(List<String> imagePaths, Insight insight){
        return imagePaths.stream()
                .map(path -> Image.builder()
                        .imagePath(path)
                        .insight(insight)
                        .build())
                .toList();
    }

    public String getPublicUrl(){
        return String.format("https://static.reinput.info/%s", imagePath);
    }
}
