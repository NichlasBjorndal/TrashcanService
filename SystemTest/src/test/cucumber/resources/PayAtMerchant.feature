Feature: Pay at merchant
  Scenario: A user can pay at a merchant
    Given I already have an account
    And I have received a barcode
    When A merchant attempts to verify my barcode for a purchase
    Then DTUpay accepts the barcode and performs the transaction
    And My balance has changed

  Scenario: A user cannot pay a merchant with too low fund
    Given I already have an account
    And I have received a barcode
    When A merchant tries to withdraw too large a fund
    Then DTUpay denies the transaction
    And My balance has not been changed

  Scenario: A merchant cannot use the same barcode twice
    Given I already have an account
    And I have received a barcode
    When A merchant attempts to verify my barcode for a purchase
    And DTUpay accepts the barcode and performs the transaction
    And A merchant attempts to verify my barcode for a purchase
    Then DTUpay denies the transaction
    And my balance has changed