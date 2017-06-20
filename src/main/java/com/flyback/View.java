package com.flyback;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
public class View implements HasDefinition, SaveableAsTextFile{
    @NonNull
    public final String name;
    @NonNull
    public final String definition;

    @Override
    public String getFileName() {
        return name +".sql";
    }

    @Override
    public String getFileContents() {
        return definition;
    }
}
