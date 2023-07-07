package models.cardFactory;


import models.Card;
import models.CardType;
import models.Category;
import models.cards.DefCard;
import models.cards.FIBCard;
import models.cards.MCQCard;
import models.cards.TFCard;

public class CardGenerator {
    public Card newCard(String question, String answer, Category category, CardType cardType)
    {
        switch(cardType)
        {
            case DEFINITION:
                return new DefCard(question,answer,category);
            case FIB:
                return new FIBCard(question,answer,category);
            case MCQ:
                return new MCQCard(question, answer, category);
            case TF:
                return new TFCard(question, answer, category);
        }
        return null;
    }
}
