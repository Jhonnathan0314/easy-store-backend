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

}
