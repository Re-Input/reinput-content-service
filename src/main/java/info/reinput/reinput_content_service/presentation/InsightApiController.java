package info.reinput.reinput_content_service.presentation;

import info.reinput.reinput_content_service.application.InsightService;
import info.reinput.reinput_content_service.application.dto.InsightCountCollection;
import info.reinput.reinput_content_service.application.dto.InsightSummaryCollection;
import info.reinput.reinput_content_service.presentation.dto.req.InsightCreateReq;
import info.reinput.reinput_content_service.presentation.dto.req.InsightPatchReq;
import info.reinput.reinput_content_service.presentation.dto.res.ApiResponse;
import info.reinput.reinput_content_service.presentation.dto.res.InsightViewRes;
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

    @Operation(summary = "[201] GetInsihgt Detail",
            description = "인사이트 상세정보를 조회합니다.")
    @GetMapping("/{insightId}/v1")
    public ResponseEntity<ApiResponse<InsightRes>> getInsightDetail(
            @PathVariable final Long insightId,
            @RequestHeader("X-User-Id") final Long memberId) {
        log.info("[getInsightDetail] insightId: {}, memberId: {} ", insightId, memberId);
        ApiResponse<InsightRes> response = ApiResponse.<InsightRes>builder()
                .status(HttpStatus.OK.value())
                .message("Success get insight detail")
                .data(InsightRes.from(insightService.getInsightDetail(insightId, memberId)))
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


    @Operation(summary = "[208] Save Insight",
            description = "인사이트를 저장합니다.")
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

    @Operation(summary = "[209] Edit Insight",
            description = "인사이트를 수정합니다. 기존 이미지에서 추가된 이미지가 반영됩니다. 별도로 이미지가 삭제되지 않습니다.")
    @PatchMapping("/v1")
    public ResponseEntity<ApiResponse<InsightRes>> patchInsight(
            @Parameter(hidden = true) @RequestHeader("X-User-Id") final Long memberId,
            @RequestBody final InsightPatchReq insightPatchReq) {
        log.info("[patchInsight] insightPatchReq memberId: {}", memberId);

        ApiResponse<InsightRes> response = ApiResponse.<InsightRes>builder()
                .status(200)
                .message("Insight edited")
                .data(InsightRes.from(insightService.editInsight(insightPatchReq.toDto(), memberId)))
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "[209] Search Insight",
            description = "키워드로 Insight를 검색합니다.")
    @GetMapping("/search/{keyword}/v1")
    public ResponseEntity<ApiResponse<InsightSummaryCollection>> searchInsight(
            @PathVariable final String keyword,
            @Parameter(hidden = true) @RequestHeader("X-User-Id") final Long memberId) {
        log.info("[searchInsight] keyword: {}, memberId: {}", keyword, memberId);

        ApiResponse<InsightSummaryCollection> response = ApiResponse.<InsightSummaryCollection>builder()
                .status(HttpStatus.OK.value())
                .message("Success search insight")
                .data(insightService.searchInsight(keyword, memberId))
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }



    @Operation(summary = "[212] Search Insight by Tag",
            description = "태그로 Insight를 검색합니다.")
    @GetMapping("/search/{folderId}/{tag}")
    public ResponseEntity<ApiResponse<InsightSummaryCollection>> searchInsightByTag(
            @PathVariable final Long folderId,
            @PathVariable final String tag,
            @Parameter(hidden = true) @RequestHeader("X-User-Id") final Long memberId) {
        log.info("[searchInsightByTag] folderId: {}, tag: {}, memberId: {}", folderId, tag, memberId);

        ApiResponse<InsightSummaryCollection> response = ApiResponse.<InsightSummaryCollection>builder()
                .status(HttpStatus.OK.value())
                .message("Success search insight")
                .data(insightService.searchInsightByTag(folderId, tag, memberId))
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @Operation(summary = "시스템 연동용 viewed Insights",
            description = "viewed insights")
    @GetMapping("/viewed/{insightId}")
    public ResponseEntity<ApiResponse<InsightViewRes>> viewedInsight(
            @PathVariable final Long insightId,
            @Parameter(hidden = true) @RequestHeader("X-User-Id") final Long memberId) {
        log.info("[viewedInsight] insightId: {}, memberId: {}", insightId, memberId);

        ApiResponse<InsightViewRes> response = ApiResponse.<InsightViewRes>builder()
                .status(HttpStatus.OK.value())
                .message("Success viewed insight")
                .data(InsightViewRes.from(insightService.getInsight(insightId, memberId)))
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
  
    @Operation(summary = "시스템 연동용 get insightIds by memberId",
            description = "get insightIds by memberId")
    @GetMapping("/ids/{memberId}")
    public ResponseEntity<ApiResponse<List<Long>>> getInsightIdsByMemberId(
            @PathVariable final Long memberId) {
        log.info("[getInsightIdsByMemberId] memberId: {}", memberId);

        ApiResponse<List<Long>> response = ApiResponse.<List<Long>>builder()
                .status(HttpStatus.OK.value())
                .message("Success get insightIds")
                .data(insightService.getInsightIdsByMemberId(memberId))
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "시스템 연동용 copy insights",
            description = "copy insights")
    @GetMapping("/copy/{folderId}")
    public ResponseEntity<ApiResponse<Long>> copyInsight(
            @PathVariable final Long folderId,
            @Parameter(hidden = true) @RequestHeader("X-User-Id") final Long memberId) {
        log.info("[copyInsight] folderId: {}, memberId: {}", folderId, memberId);

        ApiResponse<Long> response = ApiResponse.<Long>builder()
                .status(HttpStatus.OK.value())
                .message("Success copy insight")
                .data(insightService.copyInsight(folderId, memberId))
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "시스템 연동용 get insight summaries by insightIds",
            description = "get insight summaries by insightIds")
    @GetMapping("/summaries/{insightIds}")
    public ResponseEntity<ApiResponse<InsightSummaryCollection>> getInsightSummariesByInsightIds(
            @PathVariable final List<Long> insightIds,
            @Parameter(hidden = true) @RequestHeader("X-User-Id") final Long memberId) {
        log.info("[getInsightSummariesByInsightIds] insightIds: {}, memberId: {}", insightIds, memberId);

        ApiResponse<InsightSummaryCollection> response = ApiResponse.<InsightSummaryCollection>builder()
                .status(HttpStatus.OK.value())
                .message("Success get insight summaries")
                .data(insightService.getInsightSummariesByInsightIds(insightIds, memberId))
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
