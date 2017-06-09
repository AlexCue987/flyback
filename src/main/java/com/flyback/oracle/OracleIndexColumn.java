package com.flyback.oracle;

import com.flyback.IndexColumn;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
public class OracleIndexColumn implements IndexColumn {
    @NonNull
    private final String indexName;
    @NonNull
    private final String columnName;
    @NonNull
    private final Long columnPosition;
}
