package com.igorfragadev.springaiimage.controllers;

import com.igorfragadev.springaifunctions.model.Answer;
import com.igorfragadev.springaifunctions.model.Question;
import com.igorfragadev.springaifunctions.services.OpenAIService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class QuestionController {

    final OpenAIService openAIService;

    @PostMapping("/weather")
    public Answer askQuestion(@RequestBody Question question) {
        return openAIService.getAnswer(question);
    }

    @PostMapping("/stock")
    public Answer askStockPrice(@RequestBody Question question) {
        return openAIService.getStockPrice(question);
    }

}
