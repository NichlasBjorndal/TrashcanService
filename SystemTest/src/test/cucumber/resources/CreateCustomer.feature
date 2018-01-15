Feature: Create Customer
  Scenario:  Create customer account successfully
    Given I am a customer with name "John" and cpr number "1234" with an account in FastMoney Bank
    When I ask the DTU-pay service to create me a Customer Account
    Then the customer account is created with cpr number "1234"

  Scenario: Create customer account, no bank account
    Given a customer with the name "James" and cpr number "5678" without an account in FastMoney Bank
    When I ask the DTU-pay service to create me a Customer Account
    Then I receive a 'create customer' error from the system

  Scenario: Create customer account, customer account already exists
    Given I already have a DTUPay account with name "Troels" and cpr "1337"
    When I ask the DTU-pay service to create me a Customer Account
    Then I receive a 'account already exists' error from the system



