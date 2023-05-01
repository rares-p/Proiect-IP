Feature: Lobby endpoints
  Test the endpoints in the lobby service:
  POST: /lobbies --- creates a new lobby
  DELETE: /lobbies/{id} --- deletes a specific lobby

  POST: /lobbies/{id}/add_user --- adds a user to a specific lobby

  POST: /lobbies/{id}/start_game --- starts a game in a specific lobby


  Scenario: Get all lobbies
    When I send a GET request to "lobbies"
    Then the response status code should be 200
    And the response should be a JSON having the following keys: "id", "state", "waitingToJoin";

    Scenario: Create a lobby and delete lobby
      When I send a POST request to "lobbies"
      Then the response status code should be 201
      And the response should be a JSON having the following keys: "id", "game", "waitingToJoin", "state", "minimumPlayers";
      Then I delete the lobby
      And the response status code should be 200