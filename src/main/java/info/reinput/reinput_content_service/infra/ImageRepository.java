package info.reinput.reinput_content_service.infra;

import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.*;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
