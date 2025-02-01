package info.reinput.reinput_content_service.presentation;

import info.reinput.reinput_content_service.application.InsightService;
import info.reinput.reinput_content_service.application.dto.InsightCountCollection;
import info.reinput.reinput_content_service.application.dto.InsightSummaryCollection;
import info.reinput.reinput_content_service.presentation.dto.res.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/insight")
public class InsightApiController {

    private final InsightService insightService;

    @GetMapping("/count/folder/{folderId}")
    public ResponseEntity<ApiResponse<Long>> countInsight(
            @PathVariable final Long folderId,
            @RequestHeader("X-User-Id") final Long memberId) {
        log.info("[countInsight] folderId: {}, memberId: {} ", folderId, memberId);
        ApiResponse<Long> response = ApiResponse.<Long>builder()
                .status(HttpStatus.OK.value())
                .message("Insight count")
                .data(insightService.countInsight(folderId, memberId))
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/count/folders/{folderIds}")
    public ResponseEntity<ApiResponse<InsightCountCollection>> countInsight(
            @PathVariable final List<Long> folderIds,
            @RequestHeader("X-User-Id") final Long memberId) {
        log.info("[countInsight] folderIds: {}, memberId: {} ", folderIds, memberId);
        ApiResponse<InsightCountCollection> response = ApiResponse.<InsightCountCollection>builder()
                .status(HttpStatus.OK.value())
                .message("Insight count")
                .data(insightService.countInsight(folderIds, memberId))
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/share/{shareId}")
    public ResponseEntity<ApiResponse<InsightSummaryCollection>> getSharedInsightSummaries(
            @PathVariable final String shareId,
            @RequestHeader("X-User-Id") final Long memberId) {
        log.info("[getSharedInsightSummaries] shareId: {}, memberId: {} ", shareId, memberId);
        ApiResponse<InsightSummaryCollection> response = ApiResponse.<InsightSummaryCollection>builder()
                .status(HttpStatus.OK.value())
                .message("Success get shared folder insights summaries")
                .data(insightService.getSharedInsightSummaries(shareId, memberId))
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
