Feature: Lobby endpoints
  Test the endpoints in the lobby service:
  POST: /lobbies --- creates a new lobby
  DELETE: /lobbies/{id} --- deletes a specific lobby

  POST: /lobbies/{id}/add_user --- adds a user to a specific lobby

  POST: /lobbies/{id}/start_game --- starts a game in a specific lobby


  Scenario: Get all lobbies
    When I send a GET request to "/lobbies"
    Then the response status code should be 200
    And the response should be a JSON having the following keys: "id", "state", "waitingToJoin";

  Scenario: Create a lobby, get lobby details, add some users, check if they have been added and delete lobby
    When I send a POST request to "/lobbies"
    Then the response status code should be 201
    And the response should be a JSON having the following keys: "id", "game", "waitingToJoin", "state", "minimumPlayers" with the game and state having the values null and "WAITING_PLAYERS" respectively;
    Then I send a GET request to "/lobbies/{id}"
    And the response status code should be 200
    And the response should be a JSON having the following keys: "id", "state", "game", "waitingToJoin";
    Then I send a POST request to "/lobbies/{id}/add_user" with the following body:
      """
      {
        "username": "casuneanu"
      }
      """
    And the response status code should be 200
    And the response should be a JSON having the following keys: "id", "game", "waitingToJoin", "state", "minimumPlayers" with the game and state having the values null and "WAITING_PLAYERS" respectively;
    Then I send a POST request to "/lobbies/{id}/add_user" with the following body:
        """
        {
            "username": "panaite"
        }
        """
    And the response status code should be 200
    And the response should be a JSON having the following keys: "id", "game", "waitingToJoin", "state", "minimumPlayers" with the game and state having the values null and "WAITING_PLAYERS" respectively;
    Then I send a POST request to "/lobbies/{id}/add_user" with the following body:
        """
        {
            "username": "tudor"
        }
        """
    And the response status code should be 200
    And the response should be a JSON having the following keys: "id", "game", "waitingToJoin", "state", "minimumPlayers" with the game and state having the values null and "WAITING_PLAYERS" respectively;
    Then I send a POST request to "/lobbies/{id}/add_user" with the following body:
        """
        {
            "username": "tedy"
        }
        """
    Then I check the list of users in the lobby
    Then I send a DELETE request to "/lobbies/{id}"
    And the response status code should be 200

    Scenario: Start a game in a lobby and get the state of it
      Given I send a POST request to "/lobbies"
      And I send a POST request to "/lobbies/{id}/add_user" with the following body:
        """
        {
            "username": "casuneanu"
        }
        """
        And I send a POST request to "/lobbies/{id}/add_user" with the following body:
            """
            {
                "username": "panaite"
            }
            """
        And I send a POST request to "/lobbies/{id}/add_user" with the following body:
            """
            {
                "username": "tudor"
            }
            """
      And I send a POST request to "/lobbies/{id}/add_user" with the following body:
          """
          {
              "username": "tedy"
          }
          """
        And I send a POST request to "/lobbies/{id}/start_game"
        Then the response status code should be 200
        And the response should be a JSON having the following keys: "id", "game", "waitingToJoin", "state", "minimumPlayers" with the state having the value "STARTED";
        Then I send a GET request to "/lobbies/{id}"