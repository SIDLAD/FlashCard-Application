package models;

public abstract class Card {
    private int id;
    private String question;
    private String answer;
    private final Category category;
    private final CardType cardType;


    public Card(String question, String answer, Category category, CardType type) {
        this.question = question;
        this.answer = answer;
        this.category = category;
        this.cardType = type;
    }

    public int getId() {
        return this.id;
    }

    public String getQuestion() {
        return this.question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return this.answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Category getCategory() {
        return category;
    }

    public CardType getCardType() {
        return cardType;
    }

    @Override
    public String toString() {
//        return "Card{" +
//                "question='" + question + '\'' +
//                ", answer='" + answer + '\'' +
//                '}';
        return cardType.toString() + " : " + question;
    }
}