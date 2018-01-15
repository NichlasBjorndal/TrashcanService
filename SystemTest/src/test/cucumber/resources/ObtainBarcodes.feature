Feature: Obtain Barcode
  Scenario: Customer can obtain a new barcode
    Given I have a DTUPay account
    When I request a new barcode
    Then I receive a new barcode

  Scenario: Customer will not obtain same barcode twice
    Given I have a customer account
    And I have requested a number of barcodes
    When I request a new barcode
    Then I do not receive a barcode I already have

  Scenario: Non-existing customers cannot obtain barcode
    Given the customer does not exist
    When the customer tries to obtain barcode
    Then the customer is returned an error




