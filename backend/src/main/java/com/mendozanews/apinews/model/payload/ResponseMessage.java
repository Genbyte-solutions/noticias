package com.mendozanews.apinews.model.payload;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseMessage {
    private String mensaje;
    private Object recurso;
}
