package com.igorfragadev.springaiimage.services;

import com.igorfragadev.springaifunctions.functions.StockPriceServiceFunction;
import com.igorfragadev.springaifunctions.functions.WeatherServiceFunction;
import com.igorfragadev.springaifunctions.model.Answer;
import com.igorfragadev.springaifunctions.model.Question;
import com.igorfragadev.springaifunctions.model.StockPriceRequest;
import com.igorfragadev.springaifunctions.model.WeatherRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.tool.function.FunctionToolCallback;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OpenAIServiceImpl implements OpenAIService {

    @Value("${sfg.aiapp.apiNinjasKey}")
    private String apiNinjasKey;

    final OpenAiChatModel openAiChatModel;
    final GeocodingService geocodingService;

    @Override
    public Answer getAnswer(Question question) {
        var promptOptions = OpenAiChatOptions.builder()
                .toolCallbacks(List.of(FunctionToolCallback
                        .builder("CurrentWeather", new WeatherServiceFunction(geocodingService))
                        .description("Get the current weather for a location")
                        .inputType(WeatherRequest.class)
                        .build()))
                .build();

        Message userMessage = new PromptTemplate(question.question()).createMessage();

        Message systemMessage = new SystemPromptTemplate("You are a weather service. You receive weather information from a service based on the metrics system. " +
                "if the user says that is american or british, provide the response in imperial system, converting from celsius to fahrenheit and the same for all other units, from metrics to imperial system, " +
                "otherwise, provide it on the international system, e.g. temperature in celsius and wind in km/h and the same for every other units received in the response. " +
                "Please provide all the details about the weather as received from the service in a friendly format").createMessage();

        var response = openAiChatModel.call(new Prompt(List.of(systemMessage, userMessage), promptOptions));

        return new Answer(response.getResult().getOutput().getText());
    }

    @Override
    public Answer getStockPrice(Question question) {
        var promptOptions = OpenAiChatOptions.builder()
                .toolCallbacks(List.of(FunctionToolCallback
                        .builder("CurrentStockPrice", new StockPriceServiceFunction(apiNinjasKey))
                        .description("Get the current stock price for a ticker")
                        .inputType(StockPriceRequest.class)
                        .build()))
                .build();

        Message userMessage = new PromptTemplate(question.question()).createMessage();

        Message systemMessage = new SystemPromptTemplate("You are a stock price AI assistant. You receive stock price information from a service with real time data. " +
                "When the user asks for the price of a ticker, respond with the received data, containing all it's details and converting the updated field from Epoch Unix timestamp to the actual date and time at GMT timezone. " +
                "If the user asks to see the updated field date at a specific timezone, convert the Epoch Unix timestamp to the specified timezone by the user input.").createMessage();

        var response = openAiChatModel.call(new Prompt(List.of(systemMessage, userMessage), promptOptions));

        return new Answer(response.getResult().getOutput().getText());
    }
}
