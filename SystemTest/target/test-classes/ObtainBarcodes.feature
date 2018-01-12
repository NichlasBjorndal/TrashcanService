Feature: Obtain Barcode
  Scenario: User can obtain a new barcode
    Given I have a user account
    When I request a new barcode
    Then I receive a new barcode

  Scenario: User will not obtain same barcode twice
    Given I have a user account
    When I request a new barcode
    And I request another new barcode
    Then I do not receive the same barcode twice



