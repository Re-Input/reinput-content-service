package info.reinput.reinput_content_service.insight.domain;

import info.reinput.reinput_content_service.util.S3FilePathUtil;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Image {
    private String imagePath;

    public static Image from(String originalFileName){
        String path = S3FilePathUtil.generateFilePath(originalFileName);
        return new Image(path);
    }

    public String getPublicUrl(){
        return String.format("https://static.reinput.info/%s", imagePath);
    }
}
