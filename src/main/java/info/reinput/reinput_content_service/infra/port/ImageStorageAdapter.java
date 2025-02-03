package info.reinput.reinput_content_service.infra.port;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;


@Component
@RequiredArgsConstructor
public class ImageStorageAdapter implements ImageStoragePort {

    private final S3Client s3Client;

    @Value("${aws.s3.bucket}")
    private String bucket;

    @Override
    public String upload(final MultipartFile file){
        return null; //todo: implement
    }

    @Override
    public void delete(final String imagePath){

    }

}
