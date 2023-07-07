package models.cards;

import models.Card;
import models.CardType;
import models.Category;

public class DefCard extends Card {
    public DefCard(String question, String answer, Category category) {
        super(question, answer, category, CardType.DEFINITION);
    }

//    public String toString() {
//        return "Card{" +
//                "type= Definition Card \\" +
//                "question='" + this.getQuestion() + '\'' +
//                ", answer='" + this.getAnswer() + '\'' +
//                '}';
//    }
}
