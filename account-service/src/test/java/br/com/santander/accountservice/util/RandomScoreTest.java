package br.com.santander.accountservice.util;

import br.com.santander.accountservice.models.type.Score;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RandomScoreTest {

    @Mock
    private Random generator;

    private RandomScore randomScore;

    @BeforeEach
    void setup() {
        randomScore = new RandomScore(generator);
    }

    @Test
    void scoreFAIR() {
        Mockito.when(generator.nextInt(Mockito.anyInt())).thenReturn(2);
        Score score = randomScore.score();
        assertEquals(score, Score.FAIR);
    }

    @Test
    void scoreGOOD() {
        Mockito.when(generator.nextInt(Mockito.anyInt())).thenReturn(1);
        Score score = randomScore.score();
        assertEquals(score, Score.GOOD);
    }

    @Test
    void scoreEXCELLENT() {
        Mockito.when(generator.nextInt(Mockito.anyInt())).thenReturn(0);
        Score score = randomScore.score();
        assertEquals(score, Score.EXCELLENT);
    }
}