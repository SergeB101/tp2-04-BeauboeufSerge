package ht.mbds.beauboeufserge.tp204beauboeufserge;

import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.googleai.GoogleAiEmbeddingModel;
import dev.langchain4j.model.output.Response;
import dev.langchain4j.store.embedding.CosineSimilarity;

import java.time.Duration;

public class Test4 {
    public static void main(String[] args) {
        String cle = System.getenv("GEMINI_KEY");
        EmbeddingModel modele = GoogleAiEmbeddingModel.builder()
                .apiKey(cle)
                .modelName("gemini-embedding-001")
                .taskType(GoogleAiEmbeddingModel.TaskType.SEMANTIC_SIMILARITY)
                .outputDimensionality(300)
                .timeout(Duration.ofSeconds(10))
                .build();

        comparer(modele, "Le chat dort sur le canapé.", "Le félin fait la sieste sur le sofa.");
        comparer(modele, "Le chat dort sur le canapé.", "La bourse a chuté de 3% aujourd'hui.");
        comparer(modele, "J'adore la cuisine haïtienne.", "Le griot et le riz djon djon sont délicieux.");
        comparer(modele, "Il pleut à Paris.", "Il fait beau à Marseille.");
    }

    private static void comparer(EmbeddingModel modele, String phrase1, String phrase2) {
        Response<Embedding> reponse1 = modele.embed(phrase1);
        Response<Embedding> reponse2 = modele.embed(phrase2);
        double similarite = CosineSimilarity.between(reponse1.content(), reponse2.content());
        System.out.printf("« %s » / « %s » -> %.4f%n", phrase1, phrase2, similarite);
    }
}