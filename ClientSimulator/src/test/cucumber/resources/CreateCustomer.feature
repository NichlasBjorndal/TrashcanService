Feature: Create Customer
  Scenario: Create customer account successfully
    Given I have a customer bank account in the FastMoney Bank
    When I ask the DTU-pay service to create me a Customer Account
    Then the customer account is created

  Scenario: Create customer account, customer account already exists
    Given I already have an account
    When I ask the DTU-pay service to create me a Customer Account
    Then I receive a 'create customer' error from the system

  Scenario: Create customer account, CPR-number is invalid
    Given That my CPR-number is invalid
    When I ask the DTU-pay service to create me a Customer Account
    Then I receive a 'create customer' error from the system
    And the customer account is not created

  Scenario: Create customer account, name is invalid
    Given That my last or first name contains errors
    When I ask the DTU-pay service to create me a Customer Account
    Then I receive a 'create merchant' error from the system
    And the customer account is not created

  Scenario: Create customer account, no bank account
    Given That I do not have a customer account in the FastMoney Bank
    When I ask the DTU-pay service to create me a Customer Account
    Then I receive a 'create merchant' error from the system
    And the customer account is not created