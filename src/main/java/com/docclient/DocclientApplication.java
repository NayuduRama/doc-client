package com.docclient;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
//import org.junit.Test;
import com.mashape.unirest.request.body.MultipartBody;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static com.mashape.unirest.http.Unirest.*;

//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNotNull;

@SpringBootApplication
public class DocclientApplication {


    /**
     * Implement CRUDS on files C => Create R => Retrieve U => Update D => Delte S => Search
     *
     * @param args
     * @throws Exception
     */


    public static void main(String[] args) throws Exception {
        SpringApplication.run(DocclientApplication.class, args);


    }

    public void getFolderContents() throws Exception {
        Map<String, String> inputHeaders = new HashMap<>();
        inputHeaders.put("accept", "application/json");
        inputHeaders.put("Authorization", "User xcuw8px3IW28+zOIxdrj0euP0x5ZLbGMG/+m6WvKmvg=, Organization " +
                "a0d31a32c4f77aef4ada3b2ef3d4e0be, Element +RVlDJdvXH5IHauT2mQfJsSsdLQVwbFS7xXheatMCXI=");
        Map<String, Object> inputQuery = new HashMap<>();
        inputQuery.put("path", "/");
        String url = "https://staging.cloud-elements.com/elements/api-v2/folders/contents";
        HttpResponse<JsonNode> response = get(url).queryString(inputQuery).headers(inputHeaders).asJson();
        System.out.println(response.getStatus());
        System.out.println(response.getBody());

    }

    public void downloadFile() throws Exception {

        Map<String, String> inputHeaders = new HashMap<>();
        inputHeaders.put("accept", "application/json");
        inputHeaders.put("Authorization", "User xcuw8px3IW28+zOIxdrj0euP0x5ZLbGMG/+m6WvKmvg=, Organization " +
                "a0d31a32c4f77aef4ada3b2ef3d4e0be, Element +RVlDJdvXH5IHauT2mQfJsSsdLQVwbFS7xXheatMCXI=");
        Map<String, Object> inputQuery = new HashMap<>();
        inputQuery.put("path", "/Screen Shot 2020-08-18 at 19.35.05.png");
        String url = "https://staging.cloud-elements.com/elements/api-v2/files";
        HttpResponse<InputStream> response = get(url).queryString(inputQuery).headers(inputHeaders).asBinary();
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


    }


}
