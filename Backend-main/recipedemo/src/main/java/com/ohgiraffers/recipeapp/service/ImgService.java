package com.ohgiraffers.recipeapp.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ImgService {

    private final RestTemplate restTemplate;

    public ImgService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getGptImg(String imgurl) {
        String url = "http://127.0.0.1:8000/gptimg/"+imgurl;
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            return response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            return "API 호출 실패: " + e.getMessage();
        }
    }

    public String getClovaImg(String imgurl) {
        String url = "http://127.0.0.1:8000/gptimg/"+imgurl;
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            return response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            return "API 호출 실패: " + e.getMessage();
        }
    }
}
