package info.reinput.reinput_content_service.presentation.dto.res;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiResponse<T> (
        Integer status,
        String message,
        T data
){

}
