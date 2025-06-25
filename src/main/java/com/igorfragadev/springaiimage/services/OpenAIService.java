package com.igorfragadev.springaiimage.services;


import com.igorfragadev.springaiimage.model.Question;
import org.springframework.web.multipart.MultipartFile;

public interface OpenAIService {

    byte[] getImage(Question question);

    String getDescription(MultipartFile file);
}
