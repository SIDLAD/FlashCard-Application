package models.cards;

import models.Card;
import models.CardType;
import models.Category;

public class FIBCard extends Card {
    public FIBCard(String question, String answer, Category category) {
        super(question, answer, category, CardType.FIB);
    }

//    public String toString() {
//        return "Card{" +
//                "type= Fill in the blanks Card \\" +
//                "question='" + this.getQuestion() + '\'' +
//                ", answer='" + this.getAnswer() + '\'' +
//                '}';
//    }
}
