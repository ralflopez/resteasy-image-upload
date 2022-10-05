package com.example.resteasyimageupload;

import javax.ws.rs.*;
import javax.ws.rs.core.MultivaluedMap;

import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@Path("/hello-world")
public class HelloResource {
    @GET
    @Produces("text/plain")
    public String getHello() {
        return "Hello World!";
    }

    @POST
    @Produces("text/plain")
    @Consumes("multipart/form-data")
    public String hello(MultipartFormDataInput input) {
        Map<String, List<InputPart>> formParts = input.getFormDataMap();

        List<InputPart> inPart = formParts.get("file");

        for (InputPart inputPart : inPart) {

            try {

                // Retrieve headers, read the Content-Disposition header to obtain the original name of the file
                MultivaluedMap<String, String> headers = inputPart.getHeaders();
                String fn = parseFileName(headers);
                String mime = getMimeType(headers);
                System.out.println(fn);

                // Handle the body of that part with an InputStream
                InputStream istream = inputPart.getBody(InputStream.class,null);
                final byte[] data = istream.readAllBytes();
                System.out.println(data);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return "Hello, World!";
    }

    // Parse Content-Disposition header to get the original file name
    private String parseFileName(MultivaluedMap<String, String> headers) {

        String[] contentDispositionHeader = headers.getFirst("Content-Disposition").split(";");

        for (String name : contentDispositionHeader) {

            if ((name.trim().startsWith("filename"))) {

                String[] tmp = name.split("=");

                String fileName = tmp[1].trim().replaceAll("\"","");

                return fileName;
            }
        }
        return "randomName";
    }

    private String getMimeType(MultivaluedMap<String, String> headers) {
        return headers.getFirst("Content-Type");
    }
}