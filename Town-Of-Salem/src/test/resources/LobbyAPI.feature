Feature: Lobby endpoints
  Test the endpoints in the lobby service:
    POST: /lobbies --- creates a new lobby
    DELETE: /lobbies/{id} --- deletes a specific lobby

    POST: /lobbies/{id}/add_user --- adds a user to a specific lobby

    POST: /lobbies/{id}/start_game --- starts a game in a specific lobby



  Scenario: Get all lobbies
    Given I have a list of lobbies
    When I send a GET request to "/lobbies"
    Then the response status code should be 200