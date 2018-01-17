Feature: Create Merchant
  Scenario: Create merchant successfully
    Given I have a merchant account in the FastMoney Bank with name "Al" "Alsen" and CVR "65980983"
    When I ask DTU-Pay to create me a merchant account with name "Big Al" "AS" and CVR "65980983"
    Then The account was created successfully

  Scenario: Create merchant account, when one already exists in DTU-Pay
    Given I have a merchant account in the FastMoney Bank with name "Al" "Blsen" and CVR "61201241"
    When I ask DTU-Pay to create me a merchant account with name "Big Bl" "AS" and CVR "61201241"
    And I again ask DTU-Pay to create me a merchant account with name "Big Bl" "AS" and CVR "61201241"
    Then I receive a "Merchant already exist in DTUPay" error from the system

  Scenario: Create merchant account, CVR-number is invalid
    Given I ask DTU-Pay to create me a merchant account with name "Not Real" "AS" and CVR "23afiluk"
    Then I receive a "Invalid input" error from the system

  Scenario: Create merchant account, CVR-number is invalid
    Given I ask DTU-Pay to create me a merchant account with name "Not Real" "AS" and CVR "1234567"
    Then I receive a "Invalid input" error from the system

  Scenario: Create merchant account, CVR-number is invalid
    Given I ask DTU-Pay to create me a merchant account with name "Not Real" "AS" and CVR "123456789"
    Then I receive a "Invalid input" error from the system

  Scenario: Create merchant account, name is invalid
    Given  I ask DTU-Pay to create me a merchant account with first name "au48esx57zyw4" no last name and CVR "65980983"
    Then I receive a "Invalid input" error from the system

  Scenario: Create merchant account, no bank account
    Given That I do not have a merchant account in the FastMoney Bank with CVR "71089347"
    When I ask DTU-Pay to create me a merchant account with name "Hej" "AS" and CVR "71089347"
    Then I receive a "Merchant doesn't have bank account" error from the system

  Scenario: Create multiple merchant accounts
    Given I have a merchant account in the FastMoney Bank with name "Al" "Alsen" and CVR "65980983"
    And I have a merchant account in the FastMoney Bank with name "Bl" "Blsen" and CVR "61201241"
    When I ask DTU-Pay to create me a merchant account with name "Al" "Alsen" and CVR "65980983"
    Then The account was created successfully
    When I again ask DTU-Pay to create me a merchant account with name "Big Bl" "AS" and CVR "61201241"
    Then The account was created successfully
