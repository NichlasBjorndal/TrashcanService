Feature: Create Merchant
  Scenario: Create merchant successfully
    Given I have a merchant account in the FastMoney Bank with name "Al" "Alsen" and CVR "65980983"
    When I ask DTU-Pay to create me a merchant account with name "Big Al AS" and CVR "65980983"
    Then An account is created with CVR "65980983"

  Scenario: Create merchant account, when one already exists in DTU-Pay
    Given I have a merchant account in the FastMoney Bank with name "Al" "Blsen" and CVR "61201241"
    When I ask DTU-Pay to create me a merchant account with name "Big Bl AS" and CVR "61201241"
    And I again ask DTU-Pay to create me a merchant account with name "Big Bl AS" and CVR "61201241"
    Then I receive a 'create merchant' error from the system

  Scenario: Create merchant account, CVR-number is invalid
    Given That the CVR-number is invalid
    When I ask DTU-Pay to create me a merchant account with name "Not Real AS" and CVR "23afiluk"
    Then I receive a 'create merchant' error from the system
    And the merchant account is not created

  Scenario: Create merchant account, name is invalid
    Given That my merchant last or first name contains errors
    When  I ask DTU-Pay to create me a merchant account with name "au48esx57zyw4" and CVR "65980983"
    Then I receive a 'create merchant' error from the system
    And the merchant account is not created

  Scenario: Create merchant account, no bank account
    Given That I do not have a merchant account in the FastMoney Bank
    When I ask DTU-Pay to create me a merchant account with name "Hej AS" and CVR "65980983"
    Then I receive a 'create merchant' error from the system
    And the merchant account is not created