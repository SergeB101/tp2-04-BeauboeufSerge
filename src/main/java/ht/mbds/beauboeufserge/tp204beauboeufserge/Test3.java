package ht.mbds.beauboeufserge.tp204beauboeufserge;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;
import dev.langchain4j.model.input.Prompt;
import dev.langchain4j.model.input.PromptTemplate;

import java.util.Map;

public class Test3 {
    public static void main(String[] args) {
        String cle = System.getenv("GEMINI_KEY");
        ChatModel modele = GoogleAiGeminiChatModel.builder()
                .apiKey(cle)
                .modelName("gemini-flash-latest")
                .temperature(0.7)
                .build();

        PromptTemplate template = PromptTemplate.from(
                "Traduis le texte suivant en anglais : {{texte}}");

        Prompt prompt = template.apply(Map.of("texte", "Bonjour, comment allez-vous aujourd'hui ?"));

        String reponse = modele.chat(prompt.text());
        System.out.println("Traduction : " + reponse);
    }
}