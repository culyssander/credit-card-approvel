package br.com.santander.accountservice.models.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static br.com.santander.accountservice.util.Constants.INVALID_SCORE;

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

        throw new IllegalArgumentException(INVALID_SCORE);
    }
}
