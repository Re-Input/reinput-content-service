package info.reinput.reinput_content_service.infra;

import info.reinput.reinput_content_service.insight.domain.Insight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InsightRepository extends JpaRepository<Insight, Long>, InsightRepositoryCustom {

    @Query("SELECT i FROM Insight i left join fetch i.hashTags h left join fetch i.images WHERE i.folderId = :folderId")
    List<Insight> findByFolderId(Long folderId);
}
