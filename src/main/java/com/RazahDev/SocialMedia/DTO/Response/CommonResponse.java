package com.RazahDev.SocialMedia.DTO.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CommonResponse <T>{
    private Integer statusCode;
    private String message;
    private T data;
    private PagingResponse pagingResponse;
}
