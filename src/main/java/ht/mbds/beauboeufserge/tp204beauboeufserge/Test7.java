package ht.mbds.beauboeufserge.tp204beauboeufserge;

import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;
import dev.langchain4j.service.AiServices;
import ht.mbds.beauboeufserge.tp204beauboeufserge.outil.meteo.MeteoTool;

/**
 * NOTE : ce test échoue avec l'erreur Gemini 400 INVALID_ARGUMENT
 * "Function call is missing a thought_signature in functionCall parts."
 * dès le 1er appel d'outil, quel que soit l'outil utilisé (reproduit aussi avec
 * un outil Calculator.sqrt() minimal, pour isoler le problème).
 * Testé avec langchain4j 1.12.1, 1.13.0 (même bug) et 1.18.1 (packages internes
 * restructurés, casse la compilation du code du cours).
 * Cause probable : bug de LangChain4j dans la gestion du champ thoughtSignature
 * exigé par les modèles Gemini "thinking" (gemini-flash-latest) lors du renvoi
 * du résultat d'un outil au LLM. Tickets GitHub similaires (mais pas identiques :
 * concernent VertexAI) : langchain4j/langchain4j#4097 et #5134.
 * gemini-2.0-flash (sans "thinking") évite ce bug mais n'est plus disponible
 * sur le tier gratuit (quota 0).
 */
public class Test7 {
    public static void main(String[] args) {
        String cle = System.getenv("GEMINI_KEY");
        ChatModel model = GoogleAiGeminiChatModel.builder()
                .apiKey(cle)
                .modelName("gemini-flash-latest")
                .temperature(0.3)
                .build();

        AssistantMeteo assistant = AiServices.builder(AssistantMeteo.class)
                .chatModel(model)
                .chatMemory(MessageWindowChatMemory.withMaxMessages(20))
                .tools(new MeteoTool())
                .build();

        String reponse = assistant.chat(
                "J'ai prévu d'aller aujourd'hui à la ville dont la latitude est 48.85 et la longitude est 2.35 pour un séjour de 3 jours. Est-ce que tu me conseilles de mettre un parapluie dans ma valise ?");
        System.out.println(reponse);

        String reponse2 = assistant.chat("Finalement, je ne vais partir que demain. Est-ce que tu me conseilles de prendre un parapluie ?");
        System.out.println(reponse2);
    }
}