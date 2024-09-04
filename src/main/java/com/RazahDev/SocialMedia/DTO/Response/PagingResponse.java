package com.RazahDev.SocialMedia.DTO.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PagingResponse {
    private Integer totalPages;
    private Integer currentPage;
    private Long totalElement;
    private Integer size;
    private Boolean hasNext;
    private Boolean hasPrevious;
}
