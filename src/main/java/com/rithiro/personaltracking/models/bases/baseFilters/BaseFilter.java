package com.rithiro.personaltracking.models.bases.baseFilters;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BaseFilter {

    @NotNull(message = "Page is required")
    @Min(value = 1, message = "Page must be positive")
    private Integer page;

    @NotNull(message = "Rows per page is required")
    @Min(value = 1, message = "Rows per page must be positive")
    private Integer rowsPerPage;
    private String searchText;
    private String orderBy;
}
