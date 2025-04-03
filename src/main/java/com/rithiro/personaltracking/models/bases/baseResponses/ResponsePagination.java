package com.rithiro.personaltracking.models.bases.baseResponses;

import lombok.Data;

@Data
public class ResponsePagination {
    private Integer page;
    private Integer rowsPerPage;
    private String searchText;
    private String orderBy;
    private Integer total;
    private Integer totalPages;
}
