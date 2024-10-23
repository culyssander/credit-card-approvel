package br.com.santander.accountservice.util;

import br.com.santander.accountservice.models.type.Score;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class RandomScore {

    private Random generator;

    public RandomScore(Random generator) {
        this.generator = generator;
    }

    public Score score() {
        int random = generator.nextInt(2);

        return Score.fromKey(random);
    }
}
