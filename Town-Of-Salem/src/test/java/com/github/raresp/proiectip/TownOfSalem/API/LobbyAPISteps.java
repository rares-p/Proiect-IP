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

    @When("I send a GET request to \"lobbies\"")
    public void iSendAGETRequestToLobbies() {
        try {
            response = connection.get("/lobbies");
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
            Pattern pattern = Pattern.compile("\\[(\\s*\\{\\s*\"id\":\\s*\"[0-9a-z]{8}-[0-9a-z]{4}-[0-9a-z]{4}-[0-9a-z]{4}-[0-9a-z]{12}\",\\s*\"state\":\\s*\"(WAITING_PLAYERS|STARTED|ENDED)\",\\s*\"waitingToJoin\":\\s*\\[((\\s*\".*\",\\n)*\\s*\".*\")?\\s*]\\s*}(,\\s*\\{\\s*\"id\":\\s*\"[0-9a-z]{8}-[0-9a-z]{4}-[0-9a-z]{4}-[0-9a-z]{4}-[0-9a-z]{12}\",\\s*\"state\":\\s*\"(WAITING_PLAYERS|STARTED|ENDED)\",\\s*\"waitingToJoin\":\\s*\\[((\\s*\".*\",\\n)*\\s*\".*\")?\\s*]\\s*})*)?\\s*]");
            Matcher matcher = pattern.matcher(body);
            if(!matcher.matches())
            {
                throw new RuntimeException("Response body does not match the expected format");
            }
        }
    }

    @When("I send a POST request to \"lobbies\"")
    public void iSendAPOSTRequestTo() {
        try {
            response = connection.post("/lobbies", null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @And("the response should be a JSON having the following keys: {string}, {string}, {string}, {string}, {string};")
    public void theResponseShouldBeAJSONHavingTheFollowingKeys(String id, String game, String waitingToJoin, String state, String minimumPlayers) {

        var bodyResponse = response.body();

        Pattern p = Pattern.compile("\\{\\s*\"id\":\\s*\"[0-9a-z]{8}-[0-9a-z]{4}-[0-9a-z]{4}-[0-9a-z]{4}-[0-9a-z]{12}\",\\s*\"game\":\\s*null,\\s*\"waitingToJoin\":\\s*\\[],\\s*\"state\":\\s*\"WAITING_PLAYERS\",\\s*\"minimumPlayers\":\\s*[0-9]}");

        Matcher m = p.matcher(response.body());
        if(!m.matches())
        {
            throw new RuntimeException("Response body does not match the expected format");
        }

    }

    @Then("I delete the lobby")
    public void iDeleteTheLobby() {
        try {
            var lastCreatedLobbyId = getLastResponseId();
            response = connection.delete("/lobbies", lastCreatedLobbyId);
        } catch (Exception e) {
            throw new RuntimeException("Could not delete lobby with id " + getLastResponseId() + " because of " + e.getMessage());
        }
    }

    public String getLastResponseId()
    {
        Pattern p = Pattern.compile("\\s*\"id\":\\s*\"([0-9a-z]{8}-[0-9a-z]{4}-[0-9a-z]{4}-[0-9a-z]{4}-[0-9a-z]{12})\"");
        Matcher m = p.matcher(response.body());

        if(m.find())
        {
            return m.group(1);
        }
        else
        {
            throw new RuntimeException("Last response does not contain a valid id");
        }
    }
}
