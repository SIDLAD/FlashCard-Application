package models.cards;

import models.Card;
import models.CardType;
import models.Category;

public class MCQCard extends Card {
    public MCQCard(String question, String answer, Category category) {
        super(question, answer, category, CardType.MCQ);
    }

//    public String toString() {
//        return "Card{" +
//                "type= Multiple Choice Question(MCQ) Card \\" +
//                "question='" + this.getQuestion() + '\'' +
//                ", answer='" + this.getAnswer() + '\'' +
//                '}';
//    }
}
