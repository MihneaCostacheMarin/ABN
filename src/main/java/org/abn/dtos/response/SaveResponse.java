package org.abn.dtos.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SaveResponse {
    private String message;
    private String timestamp;
    private int status;
    private Long id;
}
