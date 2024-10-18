package br.com.santander.accountservice.util;

import br.com.santander.accountservice.models.type.Score;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class RandomScore {

    public Score score() {
        Random generator = new Random();
        int random = generator.nextInt(2);

        return Score.fromKey(random);
    }
}
