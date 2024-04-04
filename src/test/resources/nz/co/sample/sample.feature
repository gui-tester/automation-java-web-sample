@sample
Feature: Search for Dog Breeds on Google

  Scenario: Search for Labrador
    Given Google Search has loaded
    When I search for Labrador
    Then I see results that match the word Labrador
