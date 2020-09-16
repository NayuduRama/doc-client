package com.docclient.service.impl;

import com.docclient.exceptionhandling.ServiceException;
import com.docclient.service.DocClientService;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.request.body.Body;
import com.mashape.unirest.request.body.MultipartBody;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import static com.docclient.util.ServiceConstants.AUTHORIZATION;
import static com.docclient.util.ServiceConstants.BASE_URL;
import static com.docclient.util.ServiceConstants.CONTENT_TYPE;
import static com.mashape.unirest.http.Unirest.get;

@RestController
public class DocClientServiceImpl implements DocClientService {

    private static String URL = BASE_URL + "/files";

    @Override
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String uploadFile(@RequestParam("path") String path, HttpServletRequest request) {

        try {
            Map<String, String> inputHeaders = new HashMap<>();
            inputHeaders.put("accept", "application/json");
            inputHeaders.put("Authorization", "User xcuw8px3IW28+zOIxdrj0euP0x5ZLbGMG/+m6WvKmvg=, Organization " +
                    "a0d31a32c4f77aef4ada3b2ef3d4e0be, Element +RVlDJdvXH5IHauT2mQfJsSsdLQVwbFS7xXheatMCXI=");
            Map<String, Object> inputQuery = new HashMap<>();
            inputQuery.put("path", path);
            Part part = request.getPart("file");
            InputStream is = part.getInputStream();
            MultipartBody multipartBody =
                    Unirest.post("https://staging.cloud-elements.com/elements/api-v2/files").queryString(inputQuery).headers(inputHeaders).field("file"
                            , is, ContentType.parse(part.getContentType()), part.getName());
            HttpResponse<String> response =
                    multipartBody.mode(HttpMultipartMode.BROWSER_COMPATIBLE.toString()).asString();
            System.out.println(response.getBody());
            return response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public String downloadFile(@RequestParam("path") String path) {

        try {
            Map<String, String> inputHeaders = new HashMap<>();
            inputHeaders.put("accept", CONTENT_TYPE);
            inputHeaders.put("Authorization", AUTHORIZATION);
            Map<String, Object> inputQuery = new HashMap<>();
            inputQuery.put("path", path);
            HttpResponse<InputStream> response =
                    get(BASE_URL + "/files").queryString(inputQuery).headers(inputHeaders).asBinary();
            InputStream is = response.getBody();
            File file = new File("/Users/rama/Desktop/test" + inputQuery.get("path"));
            file.createNewFile();
            FileOutputStream os = new FileOutputStream(file);
            Writer writer = new OutputStreamWriter(os);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line = null;
            while ((line = reader.readLine()) != null) {
                writer.write(line);
                writer.write("\n");
                writer.flush();
            }
            writer.close();
            reader.close();
            os.flush();
            os.close();
            is.close();
            System.out.println(response.getStatus());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String getFoldersContents() {

        try {
            Map<String, String> inputHeaders = new HashMap<>();
            inputHeaders.put("accept", CONTENT_TYPE);
            inputHeaders.put("Authorization", AUTHORIZATION);
            Map<String, Object> inputQuery = new HashMap<>();
            inputQuery.put("path", "/");
            HttpResponse<JsonNode> response =
                    get(BASE_URL + "/folders/contents").queryString(inputQuery).headers(inputHeaders).asJson();
            System.out.println(response.getStatus());
            System.out.println(response.getBody());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public Body deleteFile(@RequestParam("path") String path) throws Exception {

        char ch = path.charAt(0);
        if (path == "") {
            throw new ServiceException(HttpStatus.BAD_REQUEST, "Please specify Name of the file");
        }
        if (String.valueOf(ch) != "/") {
            throw new ServiceException(HttpStatus.BAD_REQUEST, "Please add / before Name of the file");
        }

        try {
            Map<String, String> inputHeaders = new HashMap<>();
            inputHeaders.put("accept", CONTENT_TYPE);
            inputHeaders.put("Authorization", AUTHORIZATION);
            Map<String, Object> inputQuery = new HashMap<>();
            inputQuery.put("path", path);

            HttpResponse<String> response =
                    Unirest.delete(BASE_URL + "/files").queryString(inputQuery).headers(inputHeaders).asString();
            System.out.println(response.getStatus());


        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    //    @ExceptionHandler(value = NullPointerExceptions.class)
    //    public String handleNullPointerException(Exception e){
    //        System.out.println("Null Pointer Exception Occurred"+e);
    //        return "Please specify file Name";
    //    }
    //
    //    @ExceptionHandler(value = OtherException.class)
    //    public String handleOtherException(Exception e){
    //        System.out.println("Null Pointer Exception Occurred"+e);
    //        return "Please specify file Name";
    //    }
}
