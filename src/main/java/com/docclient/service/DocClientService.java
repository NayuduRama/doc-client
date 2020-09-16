package com.docclient.service;

import com.mashape.unirest.request.body.Body;

import javax.servlet.http.HttpServletRequest;

public interface DocClientService {

    /**
     *
     * @param path
     * @param request
     * @return
     */
    String uploadFile(String path, HttpServletRequest request);

    String downloadFile(String path);

    String getFoldersContents();

    Body deleteFile(String path) throws Exception;


}
