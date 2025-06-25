package com.igorfragadev.springaiimage.services;

import com.igorfragadev.springaiimage.model.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.content.Media;
import org.springframework.ai.image.ImageModel;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.OpenAiImageModel;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OpenAIServiceImpl implements OpenAIService {

    private final OpenAiImageModel imageModel;

    private final ChatModel chatModel;

    @Override
    public byte[] getImage(Question question) {
        var options = OpenAiImageOptions.builder()
                .height(1024).width(1024)
                .responseFormat("b64_json")
                .model("dall-e-3")
                .quality("hd")//default sd
                .style("natural")//default vivid
                .build();

        ImagePrompt imagePrompt = new ImagePrompt(question.question(), options);

        ImageResponse imageResponse = imageModel.call(imagePrompt);

        return Base64.getDecoder().decode(imageResponse.getResult().getOutput().getB64Json());
    }

    @Override
    public String getDescription(MultipartFile file) {
        OpenAiChatOptions options = OpenAiChatOptions.builder()
                .model(OpenAiApi.ChatModel.CHATGPT_4_O_LATEST)
                .build();

        var media = new Media(MimeTypeUtils.IMAGE_JPEG, file.getResource());
        var userMessage = UserMessage.builder()
                .text("Explain what you see in this picture")
                .media(List.of(media))
                .build();

        ChatResponse chatResponse = chatModel.call(new Prompt(List.of(userMessage), options));
        return chatResponse.getResult().getOutput().getText();
    }
}