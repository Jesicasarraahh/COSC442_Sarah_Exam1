package game;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;


public class CombatRulesTest {

    @ParameterizedTest
    @CsvSource({
        "40,   1,  41, false",
        "40,   0,  40, false",
        "40,  -1,  39, false",
        "98,   1,  99, false",
        "99,   1, 100, false",
        "100,  1, 100, false",
        "3,   -1,   2, false",
        "2,   -1,   1, false",
        "1,   -1,   0, true",
        "1,   -2,  -1, true"
    })
    void testHealthRules(int currentHp, int amount, int expectedHp, boolean expectedDead) {
        // Arrange:
        int maxHp = 100;

        // Act
        int actualHp =
                CombatRules.calculateNewHp(currentHp, maxHp, amount);

        // Assert
        assertEquals(expectedHp, actualHp, "Incorrect resulting HP");

       
        assertEquals(expectedDead, CombatRules.isDead(expectedHp),
                "Incorrect death result");
    }
}