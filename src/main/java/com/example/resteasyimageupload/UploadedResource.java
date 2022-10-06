package com.example.resteasyimageupload;

import org.jboss.resteasy.plugins.providers.multipart.InputPart;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import java.io.IOException;
import java.io.InputStream;

public abstract class UploadedResource {
    private byte[] data;
    private MediaType contentType;

    private MultivaluedMap<String, String> headers;

    abstract InputPart getInputPart();

    public byte[] getData() throws IOException {
        final InputPart inputPart = getInputPart();
        if (doesNotHaveValue(data)) {
            data = inputPart
                    .getBody(InputStream.class, null)
                    .readAllBytes();
        }
        return data;
    }

    public MediaType getContentType() {
        final InputPart inputPart = getInputPart();
        if (doesNotHaveValue(contentType)) {
            contentType = inputPart.getMediaType();
        }
        return contentType;
    }

    public MultivaluedMap<String, String> getHeaders() {
        if (doesNotHaveValue(headers)) {
            headers = getInputPart().getHeaders();
        }
        return headers;
    }

    private boolean doesNotHaveValue(Object property) {
        return property == null && getInputPart() != null;
    }
}
