package info.reinput.reinput_content_service.infra;

import info.reinput.reinput_content_service.insight.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ImageRepository extends JpaRepository<Image, Long> {
}
