package com.easy.store.backend.context.s3.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Contenido crudo (bytes, sin base64) de un objeto de S3, usado para servirlo directamente como
 * recurso HTTP (Content-Type + Cache-Control + ETag) en vez de embeberlo en el JSON de un listado.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class S3ObjectContent {

    private byte[] bytes;
    private String contentType;
    private String etag;

}
