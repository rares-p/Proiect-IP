package com.github.raresp.proiectip.TownOfSalem.API;

import HttpRequestTestClient.Connection;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.net.http.HttpResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LobbyAPISteps {

    private final Connection connection = new Connection();
    HttpResponse<String> response;

    public LobbyAPISteps() {
    }

    @When("I send a GET request to {string}")
    public void iSendAGETRequestToLobbies(String path) {
        try {
            var lastCreatedLobbyId = getLastResponseId();
            path = path.replace("{id}", lastCreatedLobbyId);
            response = connection.get(path);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Then("the response status code should be {int}")
    public void theResponseStatusCodeShouldBe(int statusCode) {
        if (response.statusCode() != statusCode) {
            throw new RuntimeException("Response code is not " + statusCode + " but " + response.statusCode());
        }
    }

    @And("the response should be a JSON having the following keys: {string}, {string}, {string};")
    public void theResponseShouldBeAJSONHavingTheFollowingKeys(String id, String state, String waitingToJoin) {
        String body = response.body();

        if(!body.contains("[]"))
        {
            Pattern pattern = Pattern.compile("\\[(\\s*\\{\\s*\""+id+"\":\\s*\"[0-9a-z]{8}-[0-9a-z]{4}-[0-9a-z]{4}-[0-9a-z]{4}-[0-9a-z]{12}\",\\s*\""+state+"\":\\s*\"(WAITING_PLAYERS|STARTED|ENDED)\",\\s*\""+waitingToJoin+"\":\\s*\\[((\\s*\".*\",\\n)*\\s*\".*\")?\\s*]\\s*}(,\\s*\\{\\s*\"id\":\\s*\"[0-9a-z]{8}-[0-9a-z]{4}-[0-9a-z]{4}-[0-9a-z]{4}-[0-9a-z]{12}\",\\s*\"state\":\\s*\"(WAITING_PLAYERS|STARTED|ENDED)\",\\s*\"waitingToJoin\":\\s*\\[((\\s*\".*\",\\n)*\\s*\".*\")?\\s*]\\s*})*)?\\s*]");
            Matcher matcher = pattern.matcher(body);
            if(!matcher.matches())
            {
                throw new RuntimeException("Response body does not match the expected format");
            }
        }
    }

    @When("I send a POST request to {string}")
    public void iSendAPOSTRequestTo(String path) {
        try {
            var lastCreatedLobbyId = getLastResponseId();
            path = path.replace("{id}", lastCreatedLobbyId);
            response = connection.post(path, "");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Then("I delete the lobby")
    public void iDeleteTheLobby() {

    }

    private String getLastResponseId()
    {
        if(response == null)
        {
            return "";
        }

        Pattern p = Pattern.compile("\\s*\"id\":\\s*\"([0-9a-z]{8}-[0-9a-z]{4}-[0-9a-z]{4}-[0-9a-z]{4}-[0-9a-z]{12})\"");
        Matcher m = p.matcher(response.body());

        if(m.find())
        {
            return m.group(1);
        }

        return "";
    }

    @Then("I send a DELETE request to {string}")
    public void iSendADELETERequestTo(String path) {
        try {
            var lastCreatedLobbyId = getLastResponseId();
            path = path.replace("{id}", lastCreatedLobbyId);
            response = connection.delete(path);
        } catch (Exception e) {
            throw new RuntimeException("Could not delete lobby with id " + getLastResponseId() + " because of " + e.getMessage());
        }
    }

    @And("the response should be a JSON having the following keys: {string}, {string}, {string}, {string};")
    public void theResponseShouldBeAJSONHavingTheFollowingKeys(String id, String state, String game, String waitingToJoin) {

        Pattern p = Pattern.compile("\\{\\s*\"id\":\\s*\"([0-9a-z]{8}-[0-9a-z]{4}-[0-9a-z]{4}-[0-9a-z]{4}-[0-9a-z]{12})\",\\s*\"state\":\\s*\"(WAITING_PLAYERS|STARTED|ENDED)\",\\s*\"game\":\\s*null,\\s*\"waitingToJoin\":\\s*\\[((\\s*\".*\",\\n)*\\s*\".*\")?\\s*]\\s*}");

        Matcher m = p.matcher(response.body());
        if(!m.matches())
        {
            throw new RuntimeException("Response body does not match the expected format");
        }
    }

    @Then("I send a POST request to {string} with the following body:")
    public void iSendAPOSTRequestToWithTheFollowingBody(String path, String body) {
        try {
            var lastCreatedLobbyId = getLastResponseId();
            path = path.replace("{id}", lastCreatedLobbyId);

            response = connection.post(path, body);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Then("I check the list of users in the lobby")
    public void iCheckTheListOfUsersInTheLobby() {
        try {
            var lastCreatedLobbyId = getLastResponseId();
            var path = "/lobbies/" + lastCreatedLobbyId;
            response = connection.get(path);

            var responseBody = response.body();

            if(!responseBody.contains("casuneanu"))
            {
                throw new RuntimeException("Response body does not contain the username casunenau");
            }

            if(!responseBody.contains("panaite"))
            {
                throw new RuntimeException("Response body does not contain the username panaite");
            }

            if(!responseBody.contains("tudor"))
            {
                throw new RuntimeException("Response body does not contain the username tudor");
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @And("the response should be a JSON having the following keys: {string}, {string}, {string}, {string}, {string} with the state having the value {string};")
    public void theResponseShouldBeAJSONHavingTheFollowingKeysWithTheStateHavingTheValue(String id, String game, String waitingToJoin, String state, String minimumPlayers, String stateValue) {
        Pattern p = Pattern.compile("\\{\\s*\"" + id + "\":\\s*\"[0-9a-z]{8}-[0-9a-z]{4}-[0-9a-z]{4}-[0-9a-z]{4}-[0-9a-z]{12}\",\\s*\"" + game + "\":\\s*\\{\\s*\"discussionTime\":\\s*[1-9][0-9]*,\\s*\"selectionTime\":\\s*[1-9][0-9]*,\\s*\"votingTime\":\\s*[1-9][0-9]*,\\s*\"nightTime\":\\s*[1-9][0-9]*,\\s*\"dayEndingTime\":\\s*[1-9][0-9]*,\\s*\"nightEndingTime\":\\s*[1-9][0-9]*,\\s*\"timeOfCurrentState\":\\s*\"\\d{4}-\\d{2}-\\d{2}T\\d{1,2}:\\d{1,2}:\\d{1,2}.\\d*\",\\s*\"selections\":\\s*{.*},\\s*\"id\":\\s*\\d*,\\s*\"characters\":\\s*\\[\\s*(\\s*{\\s*\"id\":\\s*\\d*,\\s*\"isAlive\":\\s*(true|false),\\s*\"roleBlocked\":\\s*(true|false),\\s*\"healed\":\\s*(true|false),\\s*\"playerUsername\":\\s*\"[a-zA-Z0-9]+\",\\s*\"actionText\":\\s*\"[a-zA-Z]+\",\\s*\"numberOfSelection\":\\s*\\d+,\\s*\"targets\":\\s*\\[.*],\\s*\"lastInteraction\":\\s*.*,\\s*\"defense\":\\s*\"(None|Basic|Powerful)\",\\s*\"attack\":\\s*\"(None|Basic|Powerful|Unstoppable)\",\\s*\"nightResults\":\\s*\\[.*],\\s*\"role\":\\s*\"(Sheriff|Escort|Mafioso|Doctor)\"\\s*},)+(\\s*{\\s*\"id\":\\s*\\d*,\\s*\"isAlive\":\\s*(true|false),\\s*\"roleBlocked\":\\s*(true|false),\\s*\"healed\":\\s*(true|false),\\s*\"playerUsername\":\\s*\"[a-zA-Z0-9]+\",\\s*\"actionText\":\\s*\"[a-zA-Z]+\",\\s*\"numberOfSelection\":\\s*\\d+,\\s*\"targets\":\\s*\\[.*],\\s*\"lastInteraction\":\\s*.*,\\s*\"defense\":\\s*\"(None|Basic|Powerful)\",\\s*\"attack\":\\s*\"(None|Basic|Powerful|Unstoppable)\",\\s*\"nightResults\":\\s*\\[.*],\\s*\"role\":\\s*\"(Sheriff|Escort|Mafioso|Doctor)\"\\s*})\\s*],\\s*\"gameState\":\\s*\"(Discussion|Selection|Voting|Night|NightEnding|DayEnding|)\",\\s*\"selectedCharacter\":\\s*.*,\\s*\"votingLog\":\\s*\\[],\\s*\"timeOfState\":\\s*\"\\d{4}-\\d{2}-\\d{2}T\\d{1,2}:\\d{2}:\\d{2}.\\d*\",\\s*\"over\":\\s*(true|false),\\s*\"currentUtcTime\":\\s*\"\\d{4}-\\d{2}-\\d{2}T\\d{1,2}:\\d{2}:\\d{2}.\\d*\",\\s*\"mafiaCharacters\":\\s*\\[(\\s*{\\s*\"id\":\\s*\\d*,\\s*\"isAlive\":\\s*(true|false),\\s*\"roleBlocked\":\\s*(true|false),\\s*\"healed\":\\s*(true|false),\\s*\"playerUsername\":\\s*\"[a-zA-Z0-9]+\",\\s*\"actionText\":\\s*\"[a-zA-Z]+\",\\s*\"numberOfSelection\":\\s*\\d+,\\s*\"targets\":\\s*\\[.*],\\s*\"lastInteraction\":\\s*.*,\\s*\"defense\":\\s*\"(None|Basic|Powerful)\",\\s*\"attack\":\\s*\"(None|Basic|Powerful|Unstoppable)\",\\s*\"nightResults\":\\s*\\[.*],\\s*\"role\":\\s*\"(Sheriff|Escort|Mafioso|Doctor)\"\\s*},)*(\\s*{\\s*\"id\":\\s*\\d*,\\s*\"isAlive\":\\s*(true|false),\\s*\"roleBlocked\":\\s*(true|false),\\s*\"healed\":\\s*(true|false),\\s*\"playerUsername\":\\s*\"[a-zA-Z0-9]+\",\\s*\"actionText\":\\s*\"[a-zA-Z]+\",\\s*\"numberOfSelection\":\\s*\\d+,\\s*\"targets\":\\s*\\[.*],\\s*\"lastInteraction\":\\s*.*,\\s*\"defense\":\\s*\"(None|Basic|Powerful)\",\\s*\"attack\":\\s*\"(None|Basic|Powerful|Unstoppable)\",\\s*\"nightResults\":\\s*\\[.*],\\s*\"role\":\\s*\"(Sheriff|Escort|Mafioso|Doctor)\"\\s*})\\s*]\\s*},\\s*\"" + waitingToJoin + "\":\\s*\\[((\\s*\"[a-zA-Z0-9]+\",)*\\s*\"[a-zA-Z0-9]+\")?\\s*],\\s*\"" + state + "\":\\s*\"(STARTED|ENDED)\",\\s*\"" + minimumPlayers + "\":\\s*[0-9]\\s*}");

        Matcher m = p.matcher(response.body());
        if (!m.matches()) {
            throw new RuntimeException("Response does not match pattern");
        }
    }

    @And("the response should be a JSON having the following keys: {string}, {string}, {string}, {string}, {string} with the game and state having the values null and {string} respectively;")
    public void theResponseShouldBeAJSONHavingTheFollowingKeysWithTheGameAndStateHavingTheValuesNullAndRespectively(String id, String game, String waitingToJoin, String state, String minimumPlayers, String stateValue) {
        Pattern p = Pattern.compile("\\{\\s*\""+id+"\":\\s*\"[0-9a-z]{8}-[0-9a-z]{4}-[0-9a-z]{4}-[0-9a-z]{4}-[0-9a-z]{12}\",\\s*\""+game+"\":\\s*null,\\s*\""+waitingToJoin+"\":\\s*\\[((\"[a-zA-Z0-9]+\",)*(\"[a-zA-Z0-9]+\"))?],\\s*\""+state+"\":\\s*\""+stateValue+"\",\\s*\""+minimumPlayers+"\":\\s*[0-9]\\s*}");
        Matcher m = p.matcher(response.body());
        if(!m.matches())
        {
            throw new RuntimeException("Response body does not match the expected format");
        }
    }
}
