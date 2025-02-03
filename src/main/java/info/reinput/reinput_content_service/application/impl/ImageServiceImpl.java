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
        log.info("[ImageService.upload] file : {}", file.getOriginalFilename());
        Image image = Image.from(file.getOriginalFilename());
        String imagePath = imageStoragePort.upload(file);

        return imageRepository.save(image).getPublicUrl();
    }

    @Transactional
    @Override
    public void delete(final String fileName) {
        log.info("[ImageService.delete] fileName : {}", fileName);
        Image image = imageRepository.findByImagePath(fileName)
                .orElseThrow(() -> new IllegalArgumentException("Image not found"));
        imageStoragePort.delete(image.getImagePath());
        imageRepository.delete(image);
    }
}
