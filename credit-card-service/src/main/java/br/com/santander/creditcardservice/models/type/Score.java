package br.com.santander.creditcardservice.models.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Score {

    FAIR(2, "Fair"),
    GOOD(1, "Good"),
    EXCELLENT(0, "Excellent");

    private Integer key;
    private String value;

    public static Score fromKey(Integer key) {
        for(Score score : Score.values()) {
            if (score.key.equals(key)) {
                return score;
            }
        }

        throw new IllegalArgumentException("INVALID SCORE");
    }
}
