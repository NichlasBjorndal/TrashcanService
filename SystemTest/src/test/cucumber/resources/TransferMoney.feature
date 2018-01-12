Feature: Transfer Money
  Scenario: Money can be transferred between client and merchant
    Given a client and merchant exists
    And client have 1000 money
    And merchant have 1000 money
    When transferring 500 money from client to merchant
    Then client will have 500 money
    And merchant will have 1500 money

  Scenario: Client cannot have negative funds
    Given a client and a merchant exists
    And client have 1000 money
    And merchant have 1000 money
    When transferring 1001 money from client to merchant
    Then client will have 1000 money
    And merchant will have 1000 money

  Scenario: Cannot transfer negative funds
    Given a client and a merchant exists
    And client have 1000 money
    And merchant have 1000 money
    When transferring -1 money from client to merchant
    Then client will have 1000 money
    And merchant will have 1000 money

