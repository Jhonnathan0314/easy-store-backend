package com.easy.store.backend.context.s3.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class S3File {

    private String extension;
    private String name;
    private String content;
    private String context;
    private Long accountId;
    private Long size;

    @Override
    public String toString() {
        return "S3File{" +
                "extension='" + extension + '\'' +
                ", name='" + name + '\'' +
                ", content='" + (content.isEmpty() ? "" : content.substring(0, 10)) + '\'' +
                ", context='" + context + '\'' +
                ", accountId=" + accountId +
                ", size=" + size +
                '}';
    }
}
