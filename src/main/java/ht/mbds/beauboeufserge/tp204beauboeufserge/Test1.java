package ht.mbds.beauboeufserge.tp204beauboeufserge;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;

public class Test1 {
    public static void main(String[] args) {
        String cle = System.getenv("GEMINI_KEY");
        ChatModel modele = GoogleAiGeminiChatModel.builder()
                .apiKey(cle)
                .modelName("gemini-flash-latest")
                .temperature(0.7)
                .build();

        // 1. Question simple
        String reponse = modele.chat("Quelle est la capitale de l'Italie ?");
        System.out.println("Question simple : " + reponse);

        // 2. Demande l'heure (le LLM ne peut pas la connaître en temps réel)
        String reponseHeure = modele.chat("Quelle heure est-il ?");
        System.out.println("Heure : " + reponseHeure);

        // 3. Deux questions séparées, sans mémoire : le LLM doit "oublier" le nom
        String reponsePresentation = modele.chat("Bonjour, je m'appelle Serge");
        System.out.println("Présentation : " + reponsePresentation);
        String reponseNom = modele.chat("Quel est mon nom ?");
        System.out.println("Nom : " + reponseNom);
    }
}