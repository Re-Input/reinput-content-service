package info.reinput.reinput_content_service.presentation.dto.res;

import lombok.Builder;

@Builder
public record ApiResponse<T> (
        Integer status,
        String message,
        T data
){

}
