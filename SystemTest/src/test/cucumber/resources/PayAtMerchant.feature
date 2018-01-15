Feature: Pay at merchant
  Scenario: A customer can pay at a merchant
    Given customer account with first name "John", last name "Doe" and CPR number "9859526433" exists with account balance of  "500"
    And merchant account with first name "Jane", last name "Doe" and CVR number "67184597" exists with account balance of "500"
    And the customer has received a barcode
    When A merchant scans the customer's barcode and sends an invoice for a payment of "500"
    #Then the merchant's and customer's balance is changed'


  Scenario: A customer cannot pay a merchant with too low fund
    Given I already have an account
    And a merchant has an account
    And I have requested a barcode
    And I have received a barcode
    When A merchant tries to withdraw too large a fund
    Then DTUpay denies the transaction
    And My balance has not been changed

  Scenario: A merchant cannot use the same barcode twice
    Given I already have an account
    And a merchant has an account
    And customer have requested a barcode
    And customer have received a barcode
    When A merchant attempts to verify my barcode for a purchase
    And DTUpay accepts the barcode and performs the transaction
    And A merchant attempts to verify my barcode for a purchase
    Then DTUpay denies the transaction
    And my balance has changed

  Scenario: A merchant cannot use a non-existing barcode
    Given a merchant has an account
    And a barcode with uuid "00000000-0000-0000-0000-000000000000"
    When the merchant attempts to verify this barcode for a purchase
    Then DTUpay denies the transaction


  Scenario: A merchant cannot be paid by non-existing customers
    Given a merchant has an account
    And a customer has an account
    And customer have requested a barcode
    And customer have received a barcode
    And a customer then deletes his account
    When a merchant attempts to verify the customers barcode for a purchase
    Then DTUpay denies the transaction

  Scenario: A non-existing merchant cannot get paid by a customer
    Given a customer has an account
    And customer have requested a barcode
    And customer have received a barcode
    And merchant does not have an account
    When the merchant attempts to verify this barcode for a purchase
    Then DTUpay denies the transaction