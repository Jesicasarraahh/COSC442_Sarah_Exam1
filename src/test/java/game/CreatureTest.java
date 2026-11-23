package game;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

//exam 1 question 2c integration test for Creature.modifyHP()
public class CreatureTest {

    private World world;
    private Creature creature;

    // making sure that the creature is alive
    // 100 HP
    // the world being created is a 3x3 world tiles
    @BeforeEach
    void setUp() {

        Tile[][][] tiles = new Tile[3][3][1];

        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                tiles[x][y][0] = Tile.FLOOR;
            }
        }

        world = new World(tiles);
        // creatue josh has 100HP, 10 attack, 5 defense
        creature = new Creature(
                world, '@', Color.GRAY, "josh", 100, 10, 5);

        creature.x = 1;
        creature.y = 1;
        creature.z = 0;

        // creating a new AI for the creature
        new CreatureAi(creature);

        // adding the creature to the world list so that it can be tested
        world.add(creature);
    }

    @Test
    void testStayAlive() {
        // we are subtracting 99 from the creatures HP which is 100
        // that leaves us with 1 HP which means the creature is still alive
        creature.modifyHp(-99, "Test damage");
        // checking to see if the creature is still alive and has 1 HP
        assertEquals(1, creature.hp());
        // checking to see if the cause of death is null since the creature is still
        // alive
        assertEquals("Test damage", creature.causeOfDeath());
        // checking to see if the creature is still in the world list
        assertSame(creature, world.creature(1, 1, 0));

        assertNull(world.item(1, 1, 0));
    }

    // testing to see if the creature dies when its HP reaches 0
    @Test
    void testDieAtZero() {
        // we are subtracting 100 from the creatures HP which is 100
        // that leaves us with 0 HP which means the creature is dead
        creature.modifyHp(-100, "Test damage");
        // checking to see if the creature is dead and has 0 HP
        assertEquals(0, creature.hp());
        // checking to see if the cause of death is "Test damage" since the creature is
        // dead
        assertEquals("Test damage", creature.causeOfDeath());
        // checking to see if the creature is no longer in the world list
        assertNull(world.creature(1, 1, 0));

        Item corpse = world.item(1, 1, 0);
        assertNotNull(corpse);
        assertEquals("josh corpse", corpse.name());
    }

    // testing to see if the creature dies when its HP goes below 0
    @Test
    void testDieBelowZero() {
        // we are subtracting 101 from the creatures HP which is 100
        // that leaves us with -1 HP which means the creature is dead
        creature.modifyHp(-101, "Test damage");

        assertEquals(-1, creature.hp());
        assertEquals("Test damage", creature.causeOfDeath());
        assertNull(world.creature(1, 1, 0));

        Item corpse = world.item(1, 1, 0);
        assertNotNull(corpse);
        assertEquals("josh corpse", corpse.name());
    }

    @Test
    void testDeathMessage() {
        // Create a list to save the creature's messages.
        List<String> messages = new ArrayList<>();

        // Attach an AI that saves messages instead of ignoring them.
        new CreatureAi(creature) {
            // Replace onNotify() for this test only.
            @Override
            public void onNotify(String message) {
                // Save each received message.
                messages.add(message);
            }
        };

        // Reduce the creature's starting HP from 100 to 0.
        creature.modifyHp(-100, "Test damage");

        // Check that the creature received the death message.
        assertTrue(messages.contains("You die."));
    }
}