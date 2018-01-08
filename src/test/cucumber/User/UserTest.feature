Feature: User information retrieval
  Scenario: User can request and receive a barcode
    Given I exist on the system
    When I request a barcode
    Then I can receive barcode

  Scenario: User can fetch basic information about themselves
    Given I exist on the system
    Then I can fetch my name
    And I can fetch my user ID
    And I can fetch my cpr number

