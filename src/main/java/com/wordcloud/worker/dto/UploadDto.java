package com.wordcloud.worker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UploadDto {
    private String userToken;
    private byte[] userFile;
    private Integer minimumCount;
    private boolean filterCommonWords;
    private String filterCustomWords;
}
