package com.mendozanews.apinews.model.payload;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class ApiResponse {
    private String hora = new Date().toString();
    private String mensaje;
    private String url;

    public ApiResponse(String mensaje, String url) {
        this.mensaje = mensaje;
        this.url = url.replace("uri=", "");
    }
}
