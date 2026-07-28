package ht.mbds.beauboeufserge.tp204beauboeufserge;

import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;
import dev.langchain4j.model.output.TokenUsage;

public class Test2 {
    public static void main(String[] args) {
        String cle = System.getenv("GEMINI_KEY");
        ChatModel modele = GoogleAiGeminiChatModel.builder()
                .apiKey(cle)
                .modelName("gemini-flash-latest")
                .temperature(0.7)
                .build();

        ChatResponse reponse = modele.chat(UserMessage.from("Quelle est la capitale de l'Italie ?"));
        System.out.println("Réponse : " + reponse.aiMessage().text());

        TokenUsage usage = reponse.tokenUsage();
        System.out.println("Tokens entrée : " + usage.inputTokenCount());
        System.out.println("Tokens sortie : " + usage.outputTokenCount());
        System.out.println("Tokens total : " + usage.totalTokenCount());
    }
}