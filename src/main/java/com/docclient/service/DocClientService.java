package com.docclient.service;

import com.mashape.unirest.request.body.Body;

import javax.servlet.http.HttpServletRequest;

public interface DocClientService {

    String uploadFile(String path, HttpServletRequest request);

    String downloadFile(String path);

    String getFoldersContents();

    Body deleteFile(String path);


}
