package info.reinput.reinput_content_service.application.impl;

import info.reinput.reinput_content_service.application.ImageService;
import info.reinput.reinput_content_service.infra.ImageRepository;
import info.reinput.reinput_content_service.infra.port.ImageStoragePort;
import info.reinput.reinput_content_service.insight.domain.Image;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;
    private final ImageStoragePort imageStoragePort;

    @Transactional
    @Override
    public String upload(final MultipartFile file) {
        Image image = Image.from(file.getOriginalFilename());
        String imagePath = imageStoragePort.upload(file);

        return null;

    }

    @Override
    public void deleteImage() {
        // TODO Auto-generated method stub
    }
}
