Feature: Pay at merchant
  Scenario: A customer can pay at a merchant
    Given John Doe with CPR number 9859526433 has an account in FastMoney Bank with balance 500
    And John Doe is customer in DTUPay with CPR number 9859526433
    And Jane Doe with CVR number 67184597 has an account in FastMoney Bank with balance 500
    And Jane Doe is merchant in DTUPay with CVR number 67184597
    And the customer requests and receives a barcode
    When A merchant scans the customer's barcode and sends an invoice for a payment of 500
    Then then balance is 0 on the customers account and the balance is 1000 on the merchant's account

  Scenario: A customer cannot pay a merchant with too low fund
    Given Simon Bro with CPR number 1427912340 has an account in FastMoney Bank with balance 100
    And Simon Bro is customer in DTUPay with CPR number 1427912340
    And Gustav Fring with CVR number 7231339 has an account in FastMoney Bank with balance 1337
    And Gustav Fring is merchant in DTUPay with CVR number 7231339
    And the customer requests and receives a barcode
    When A merchant scans the customer's barcode and sends an invoice for a payment of 200
    Then the transfer is denied with an error message
    And then balance is 100 on the customers account and the balance is 1337 on the merchant's account

   # And I have requested a barcode
   # And I have received a barcode
   # When A merchant tries to withdraw too large a fund
   # Then DTUpay denies the transaction
   # And My balance has not been changed

  #Scenario: A merchant cannot use the same barcode twice
    #Given I already have an account
    #And a merchant has an account
    #And customer have requested a barcode
    #And customer have received a barcode
    #When A merchant attempts to verify my barcode for a purchase
    #And DTUpay accepts the barcode and performs the transaction
    #And A merchant attempts to verify my barcode for a purchase
    #Then DTUpay denies the transaction
    #And my balance has changed

  #Scenario: A merchant cannot use a non-existing barcode
   # Given a merchant has an account
   # And a barcode with uuid "00000000-0000-0000-0000-000000000000"
   # When the merchant attempts to verify this barcode for a purchase
   # Then DTUpay denies the transaction


#  Scenario: A merchant cannot be paid by non-existing customers
 #   Given a merchant has an account
  #  And a customer has an account
   # And customer have requested a barcode
   # And customer have received a barcode
   # And a customer then deletes his account
   # When a merchant attempts to verify the customers barcode for a purchase
   # Then DTUpay denies the transaction

  #Scenario: A non-existing merchant cannot get paid by a customer
   # Given a customer has an account
   # And customer have requested a barcode
   # And customer have received a barcode
   # And merchant does not have an account
   # When the merchant attempts to verify this barcode for a purchase
   # Then DTUpay denies the transaction