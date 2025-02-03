package info.reinput.reinput_content_service.infra.port;

import info.reinput.reinput_content_service.insight.domain.Image;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;

import java.io.IOException;


@Component
@Slf4j
@RequiredArgsConstructor
public class ImageStorageAdapter implements ImageStoragePort {

    private final S3Client s3Client;

    @Value("${aws.s3.bucket}")
    private String bucket;

    @Override
    public String upload(final MultipartFile file){
        Image image = Image.from(file.getOriginalFilename());

        try{
            s3Client.putObject(putObjectRequest -> putObjectRequest
                    .bucket(bucket)
                    .key(image.getImagePath())
                    .contentType(file.getContentType())
                    .acl(ObjectCannedACL.PUBLIC_READ)
                    .build(), RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
            return image.getImagePath();
        } catch (IOException e){
            log.error("[ImageStoragePort] Failed to upload image", e);
            //todo : custom exception
            throw new RuntimeException("Failed to upload image");
        }
    }

    @Override
    public void delete(final String imagePath){
        s3Client.deleteObject(deleteObjectRequest -> deleteObjectRequest
                .bucket(bucket)
                .key(imagePath)
                .build());
    }

}
