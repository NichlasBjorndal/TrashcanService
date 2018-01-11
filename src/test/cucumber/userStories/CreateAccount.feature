Feature: Create Account
  Scenario: Create user account successfully
    When I ask the DTUpay service to create me an Account
    Then I receive a UUID
    And the account is created

  Scenario: User account already exists
    Given I already have an account
    When I ask the DTUpay service to create me an Account
    Then I receive an error from the system

  Scenario: CPR-number is invalid
    Given That my CPR-number is invalid
    When I ask the DTUpay service to create me an Account
    Then I receive an error from the system
    And the account is not created

  Scenario: Name is invalid
    Given That my last or first name contains errors
    When I ask the DTUpay service to create me an Account
    Then I receive an error from the system
    And the account is not created
