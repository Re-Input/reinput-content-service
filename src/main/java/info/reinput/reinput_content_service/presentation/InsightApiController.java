package info.reinput.reinput_content_service.presentation;

import info.reinput.reinput_content_service.application.InsightService;
import info.reinput.reinput_content_service.application.dto.InsightCountCollection;
import info.reinput.reinput_content_service.application.dto.InsightSummaryCollection;
import info.reinput.reinput_content_service.presentation.dto.req.InsightCreateReq;
import info.reinput.reinput_content_service.presentation.dto.res.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

    @Operation(summary = "Count Insight",
            description = "폴더에 속한 Insight 개수를 조회합니다.")
    @GetMapping("/count/folder/{folderId}")
    public ResponseEntity<ApiResponse<Long>> countInsight(
            @PathVariable final Long folderId,
            @Parameter(hidden = true) @RequestHeader("X-User-Id") final Long memberId) {
        log.info("countInsight request : {}", folderId);
        ApiResponse<Long> response = ApiResponse.<Long>builder()
                .status(HttpStatus.OK.value())
                .message("Insight count")
                .data(insightService.countInsight(folderId, memberId))
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Count Insights",
            description = "폴더에 속한 Insight 개수를 조회합니다.")
    @GetMapping("/count/folders/{folderIds}")
    public ResponseEntity<ApiResponse<InsightCountCollection>> countInsight(
            @PathVariable final List<Long> folderIds,
            @Parameter(hidden = true) @RequestHeader("X-User-Id") final Long memberId) {
        log.info("countInsight request : {}", folderIds);
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


    @Operation(summary = "[208] [미완성]Save Image",
            description = "이미지를 저장합니다. Reminder 관련 서비스와 연동되어 있지 않습니다.")
    @PostMapping("/v1")
    public ResponseEntity<ApiResponse<InsightRes>> saveImage(
            @Parameter(hidden = true) @RequestHeader("X-User-Id") final Long memberId,
            @RequestBody final InsightCreateReq insightCreateReq){
        log.info("[saveImage] insightReq memberId: {}", memberId);

        ApiResponse<InsightRes> response = ApiResponse.<InsightRes>builder()
                .status(201)
                .message("Image saved")
                .data(InsightRes.from(insightService.saveInsight(insightCreateReq.toDto(), memberId)))
                .build();

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
