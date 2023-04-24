package com.github.raresp.proiectip.TownOfSalem.models;

import com.github.raresp.proiectip.TownOfSalem.exceptions.CharacterNotFoundException;
import com.github.raresp.proiectip.TownOfSalem.exceptions.InvalidCharacterException;
import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;
import com.github.raresp.proiectip.TownOfSalem.models.characters.MafiaCharacter;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import static com.jayway.jsonpath.internal.path.PathCompiler.fail;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

import com.github.raresp.proiectip.TownOfSalem.models.Game;


@SpringBootTest
public class GameTests {
    @Test
    public void testGame() {
        // Create a list of mock characters to pass to the constructor
        List<Character> characters = new ArrayList<>();
        Character alice = Mockito.mock(Character.class);
        Character bob = Mockito.mock(Character.class);
        Character charlie = Mockito.mock(Character.class);
        characters.add(alice);
        characters.add(bob);
        characters.add(charlie);

        // Call the constructor
        Game game = new Game(characters);

        // Check that the characters field is initialized correctly
        assertEquals(characters, game.getCharacters());
    }

    @Test
    public void testGetCharacterByName() throws CharacterNotFoundException {
        // Create a list of mock characters to pass to the constructor
        List<Character> characters = new ArrayList<>();
        Character alice = Mockito.mock(Character.class);
        when(alice.getPlayerUsername()).thenReturn("alice");
        Character bob = Mockito.mock(Character.class);
        when(bob.getPlayerUsername()).thenReturn("bob");
        Character charlie = Mockito.mock(Character.class);
        when(charlie.getPlayerUsername()).thenReturn("charlie");

        characters.add(alice);
        characters.add(bob);
        characters.add(charlie);

        // Call the constructor
        Game game = new Game(characters);

        // Check that the characters field with a valid name
        assertEquals(alice, game.getCharacterByName("alice"));

        try {
            game.getCharacterByName("Gigi");
            fail("Expected CharacterNotFoundException was not thrown");
        } catch (CharacterNotFoundException e) {
            // Check that the characters field with an invalid name
        }
    }

    @Test
    public void testRemoveCharacter() throws InvalidCharacterException {
        // Create a list of mock characters to pass to the constructor
        List<Character> characters = new ArrayList<>();
        Character alice = Mockito.mock(Character.class);
        when(alice.getPlayerUsername()).thenReturn("alice");
        Character bob = Mockito.mock(Character.class);
        when(bob.getPlayerUsername()).thenReturn("bob");
        Character charlie = Mockito.mock(Character.class);
        when(charlie.getPlayerUsername()).thenReturn("charlie");

        characters.add(alice);
        characters.add(bob);
        characters.add(charlie);

        // Call the constructor
        Game game = new Game(characters);

        // Check that the characters field is initialized correctly
        assertEquals(characters, game.getCharacters());

        // Remove a character
        game.removeCharacter(alice);

        // Check that the character was removed
        assertEquals(Arrays.asList(bob, charlie), game.getCharacters());

        try {
            game.removeCharacter(alice);
            fail("Expected InvalidCharacterException was not thrown");
        } catch (InvalidCharacterException e) {
            // Check that the characters field with an invalid name
        }
    }

    @Test
    public void testGetCharacters() {
        // Create a list of mock characters to pass to the constructor
        List<Character> characters = new ArrayList<>();
        Character alice = Mockito.mock(Character.class);
        when(alice.getPlayerUsername()).thenReturn("alice");
        Character bob = Mockito.mock(Character.class);
        when(bob.getPlayerUsername()).thenReturn("bob");
        Character charlie = Mockito.mock(Character.class);
        when(charlie.getPlayerUsername()).thenReturn("charlie");

        characters.add(alice);
        characters.add(bob);
        characters.add(charlie);

        // Call the constructor
        Game game = new Game(characters);

        // Check that the characters field is initialized correctly
        assertEquals(characters, game.getCharacters());
    }

//    @Test
//    public void testGetId()
//    {
//        	// Create a list of mock characters to pass to the constructor
//            List<Character> characters = new ArrayList<>();
//            Character alice = Mockito.mock(Character.class);
//            when(alice.getPlayerUsername()).thenReturn("alice");
//            Character bob = Mockito.mock(Character.class);
//            when(bob.getPlayerUsername()).thenReturn("bob");
//            Character charlie = Mockito.mock(Character.class);
//            when(charlie.getPlayerUsername()).thenReturn("charlie");
//
//            characters.add(alice);
//            characters.add(bob);
//            characters.add(charlie);
//
//            // Call the constructor
//            Game game = new Game(characters);
//
//            // Check that the id field is initialized correctly
//            assertEquals(0, game.getId());
//    }

    @Test //To be continued with actual MafiaCharacters
    public void testGetMafiaCharacters() {
        // Create a list of mock characters to pass to the constructor
        List<Character> characters = new ArrayList<>();
        Character alice = Mockito.mock(Character.class);
        when(alice.getPlayerUsername()).thenReturn("alice");
        when(alice.getRole()).thenReturn("mafia1");
        Character bob = Mockito.mock(Character.class);
        when(bob.getPlayerUsername()).thenReturn("bob");
        when(bob.getRole()).thenReturn("citizen1");
        Character charlie = Mockito.mock(Character.class);
        when(charlie.getPlayerUsername()).thenReturn("charlie");
        when(charlie.getRole()).thenReturn("detective1");

        characters.add(alice);
        characters.add(bob);
        characters.add(charlie);

        // Call the constructor
        Game game = new Game(characters);

        // Check that the characters field is initialized correctly
        List<MafiaCharacter> mafiaCharacters = game.getMafiaCharacters();

        assertTrue(mafiaCharacters.get(0).getRole().equals("mafia1") &&
                mafiaCharacters.get(1).getRole().equals("citizen1") &&
                mafiaCharacters.get(2).getRole().equals("detective1"));
    }

    @Test
    public void testGetMafiaCharactersIfCharacterIsMafia() {
        // Create a list of mock characters to pass to the constructor
        List<Character> characters = new ArrayList<>();
        Character alice = Mockito.mock(Character.class);
        when(alice.getPlayerUsername()).thenReturn("alice");
        when(alice.getRole()).thenReturn("mafia1");
        Character bob = Mockito.mock(Character.class);
        when(bob.getPlayerUsername()).thenReturn("bob");
        when(bob.getRole()).thenReturn("citizen1");
        Character charlie = Mockito.mock(Character.class);
        when(charlie.getPlayerUsername()).thenReturn("charlie");
        when(charlie.getRole()).thenReturn("detective1");

        characters.add(alice);
        characters.add(bob);
        characters.add(charlie);

        // Call the constructor
        Game game = new Game(characters);

        // Check that the characters field is initialized correctly
        List<MafiaCharacter> mafiaCharacters = game.getMafiaCharacters();

        assertTrue(mafiaCharacters.get(0).getRole().equals("mafia1") &&
                mafiaCharacters.get(1).getRole().equals("citizen1") &&
                mafiaCharacters.get(2).getRole().equals("detective1"));
    }
//    @Test
//    public void testStartGame() {
//
//        List<Character> characters = new ArrayList<>();
//        // ...add some characters to the list...
//
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(2023, 4, 1, 10, 0, 0); // set the current time to May 1, 2023, 10:00:00 AM UTC
//        Game game = Mockito.spy(new Game(characters));
//        Mockito.doReturn(calendar).when(game).getCurrentUtcTime();
//
//        // 3. Call the StartGame() method
//        game.StartGame();
//
//        // 4. Check that the game state and the characters have changed as expected during the game loop
//        // ...assert that the game state and characters have changed correctly...
//    }

    //////////////////CalendarTests
    @Test
    public void testGetCurrentUtcTime() {
        Game game=new Game();
        Calendar result = game.getCurrentUtcTime();
        assertNotNull(result, "getCurrentUtcTime() should return a non-null Calendar object representing the current UTC time.");
    }

    @Test
    public void testGetCurrentUtcTimeWithDelay() {
        int secondsDelay = 10;
        Calendar expectedCalendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        expectedCalendar.add(Calendar.SECOND, secondsDelay);
        Game game=new Game();
        Calendar actualCalendar = game.getCurrentUtcTime(secondsDelay);

        assertEquals(expectedCalendar.getTimeInMillis(), actualCalendar.getTimeInMillis());
    }

}
