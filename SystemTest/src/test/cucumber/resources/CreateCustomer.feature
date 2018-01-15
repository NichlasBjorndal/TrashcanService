Feature: Create Customer
  Scenario:  Create customer account successfully
    Given I am a customer with name "John Cool" and cpr number "1234567890" with an account in FastMoney Bank
    When I ask the DTU-pay service to create me a Customer Account
    Then the customer account is created with a UUID

  Scenario: Create customer account, no bank account
    Given a customer with the name "James Pryde" and cpr number "1234567899" without an account in FastMoney Bank
    When I ask the DTU-pay service to create me a Customer Account
    Then I receive a 'create customer' error from the system

  Scenario: Create customer account, customer account already exists
    Given I already have a DTUPay account with name "Troels Goodguy" and cpr "1234567893"
    When I ask the DTU-pay service to create me a Customer Account
    Then I receive a 'account already exists' error from the system

  Scenario: Invalid customer name input
    Given I am a customer with invalid name "John" and cpr number "1234567893" with an account in FastMoney Bank
    When I ask the DTU-pay service to create me a Customer Account
    Then I receive a 'invalid input' error from the system

  Scenario: Invalid customer cpr input
    Given I am a customer with name "John Johnsen" and invalid cpr number "0420A" with an account in FastMoney Bank
    When I ask the DTU-pay service to create me a Customer Account
    Then I receive a 'invalid input' error from the system