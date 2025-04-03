package com.rithiro.personaltracking.utils;

public class PaginationUtility {
    public static Integer calTotalPages(Integer totalRows, Integer rowsPerPage) {
        if (rowsPerPage > 0)
            return Math.ceilDiv(totalRows, rowsPerPage);
        return 0;
    }

    public static Integer calRowsOffset(Integer page, Integer rowsPerPage) {
        Integer base = page - 1;
        return rowsPerPage * base;
    }
}
