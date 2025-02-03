package info.reinput.reinput_content_service.application;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    String upload(final MultipartFile file);
    void delete(final String fileName);
}
