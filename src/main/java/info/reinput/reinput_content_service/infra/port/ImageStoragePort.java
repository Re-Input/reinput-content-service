package info.reinput.reinput_content_service.infra.port;

import org.springframework.web.multipart.MultipartFile;

public interface ImageStoragePort {
    String upload(final MultipartFile file);
    void delete(final String path);
}
