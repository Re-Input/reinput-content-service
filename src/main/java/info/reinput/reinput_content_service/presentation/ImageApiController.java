package info.reinput.reinput_content_service.presentation;

import info.reinput.reinput_content_service.application.ImageService;
import info.reinput.reinput_content_service.insight.domain.Image;
import info.reinput.reinput_content_service.presentation.dto.req.InsightCreateReq;
import info.reinput.reinput_content_service.presentation.dto.res.ApiResponse;
import info.reinput.reinput_content_service.presentation.dto.res.ImageUrlRes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/insight/image")
public class ImageApiController {

    private final ImageService imageService;

    @Operation(summary = "[204] Upload Image",
            description = "이미지를 업로드합니다.")
    @PostMapping("/v1")
    public ResponseEntity<ApiResponse<ImageUrlRes>> uploadImage(
            @Parameter(hidden = true) @RequestHeader("X-User-Id") final Long memberId,
            @RequestParam("file") final MultipartFile file){
        log.info("[uploadImage] file : {}", memberId);

        ApiResponse<ImageUrlRes> response = ApiResponse.<ImageUrlRes>builder()
                .status(201)
                .message("Image uploaded")
                .data(ImageUrlRes.from(imageService.upload(file)))
                .build();

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "[210] Delete Image",
            description = "이미지를 삭제합니다.")
    @DeleteMapping("/{fileName}/v1")
    public ResponseEntity<ApiResponse<Void>> deleteImage(
            @PathVariable final String fileName,
            @Parameter(hidden = true) @RequestHeader("X-User-Id") final Long memberId){
        log.info("[deleteImage] fileName : {}, memberId : {}", fileName, memberId);

        imageService.delete(fileName);

        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .status(204)
                .message("Image deleted")
                .build();

        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }

}
