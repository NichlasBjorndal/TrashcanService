Feature: Obtain Barcode
  Scenario: Customer can obtain a new barcode
    Given I have a DTUPay account
    When I request a new barcode
    Then I receive a new barcode

  Scenario: Customer will not obtain same barcode twice
    Given I have a DTUPay account
    When I request a new barcode two times in a row
    Then I do not receive a barcode I already have

  Scenario: Non-existing customers cannot obtain barcode
    Given the customer does not exist
    When I try to request a new barcode
    Then I receive a 'no customer found' error message




