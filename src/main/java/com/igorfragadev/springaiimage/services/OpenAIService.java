package com.igorfragadev.springaiimage.services;

import com.igorfragadev.springaifunctions.model.Answer;
import com.igorfragadev.springaifunctions.model.Question;

public interface OpenAIService {

    Answer getAnswer(Question question);
    Answer getStockPrice(Question question);

}
