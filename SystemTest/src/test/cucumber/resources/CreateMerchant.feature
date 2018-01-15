Feature: Create Merchant
  Scenario: Create merchant successfully
    Given I have a merchant account in the FastMoney Bank
    When I ask DTU-Pay to create me a merchant account
    Then An account is created

  Scenario: Create merchant account, when one already exists in DTU-Pay
    Given I have a merchant account in the FastMoney Bank
    And I have earlier asked DTU-Pay to create me a merchant account
    When I ask DTU-Pay to create me a merchant account
    Then I receive a 'create merchant' error from the system

  Scenario: Create merchant account, CVR-number is invalid
    Given That the CVR-number is invalid
    When I ask DTU-Pay to create me a merchant account
    Then I receive a 'create merchant' error from the system
    And the merchant account is not created

  Scenario: Create merchant account, name is invalid
    Given That my merchant last or first name contains errors
    When  I ask DTU-Pay to create me a merchant account
    Then I receive a 'create merchant' error from the system
    And the merchant account is not created

  Scenario: Create merchant account, no bank account
    Given That I do not have a merchant account in the FastMoney Bank
    When I ask DTU-pay to create me a merchant account
    Then I receive a 'create merchant' error from the system
    And the merchant account is not created