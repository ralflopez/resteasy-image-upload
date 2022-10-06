package com.example.resteasyimageupload;

import org.jboss.resteasy.annotations.providers.multipart.PartType;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;

import javax.ws.rs.FormParam;

public class ProfilePhotoUploadResource extends ImageUploadResource {
    @FormParam("file")
    @PartType("image/*")
    private InputPart photoInputPart;

    public InputPart getPhotoInputPart() {
        return photoInputPart;
    }

    @Override
    InputPart getInputPart() {
        return photoInputPart;
    }
}
