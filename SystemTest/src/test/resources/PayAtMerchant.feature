Feature: Pay at merchant

  Scenario: A customer can pay at a merchant
    Given no accounts in FastMoney Bank and DTUPay exists with CPR 9859526433 and CVR 67184597
    Given John Doe with CPR number 9859526433 has an account in FastMoney Bank with balance 500
    And John Doe is customer in DTUPay with CPR number 9859526433
    And Jane Doe with CVR number 67184597 has an account in FastMoney Bank with balance 500
    And Jane Doe is merchant in DTUPay with CVR number 67184597
    And the customer requests and receives a barcode
    When A merchant scans the barcode and sends an invoice for a payment of 500
    Then the transfer is accepted with response code 201
    And then balance is 0 on the customers account and the balance is 1000 on the merchant's account

  Scenario: A customer cannot pay a merchant with too low fund
    Given no accounts in FastMoney Bank and DTUPay exists with CPR 1427912340 and CVR 47803166
    Given Niels Nielsen with CPR number 1427912340 has an account in FastMoney Bank with balance 100
    And Niels Nielsen is customer in DTUPay with CPR number 1427912340
    And Jan Fring with CVR number 47803166 has an account in FastMoney Bank with balance 1337
    And Jan Fring is merchant in DTUPay with CVR number 47803166
    And the customer requests and receives a barcode
    When A merchant scans the barcode and sends an invoice for a payment of 200
    Then the transfer is denied with response code 400
    And then balance is 100 on the customers account and the balance is 1337 on the merchant's account
  
  Scenario: A merchant cannot use the same barcode twice
    Given no accounts in FastMoney Bank and DTUPay exists with CPR 4016591427 and CVR 22171596
    Given Jens Jensen with CPR number 4016591427 has an account in FastMoney Bank with balance 1000
    And Jens Jensen is customer in DTUPay with CPR number 4016591427
    And Frida Fedtbiks with CVR number 22171596 has an account in FastMoney Bank with balance 5555
    And Frida Fedtbiks is merchant in DTUPay with CVR number 22171596
    And the customer requests and receives a barcode
    When A merchant scans the barcode and sends an invoice for a payment of 100
    Then the transfer is accepted with response code 201
    When A merchant scans the barcode and sends an invoice for a payment of 200
    Then the transfer is denied with response code 405
    And then balance is 900 on the customers account and the balance is 5655 on the merchant's account

    Scenario: A merchant cannot use a non-existing barcode
    Given no accounts in FastMoney Bank and DTUPay exists with CPR 0000000000 and CVR 36947525
    Given Tog Portalen with CVR number 36947525 has an account in FastMoney Bank with balance 2323
    And Tog Portalen is merchant in DTUPay with CVR number 36947525
    And the merchant has a barcode with UUID 00000000-0000-0000-0000-000000000000
    When A merchant scans the barcode and sends an invoice for a payment of 995
    Then the transfer is denied with response code 405
    And then the balance is 2323 on the merchant's account

  Scenario: A non-existing merchant in DTUpay cannot get paid by a customer
   Given no accounts in FastMoney Bank and DTUPay exists with CPR 6523254577 and CVR 74412900
   Given Flemming Jensen with CPR number 6523254577 has an account in FastMoney Bank with balance 9999
   And Flemming Jensen is customer in DTUPay with CPR number 6523254577
   And the customer requests and receives a barcode
   When A merchant scans the barcode and sends an invoice for a payment of 300
   Then the transfer is denied with response code 406
   And then balance is 9999 on the customer's account

  Scenario: Merchant cannot get paid to a non-existing account in FastMoney Bank
    Given no accounts in FastMoney Bank and DTUPay exists with CPR 7354277112 and CVR 62774955
    Given Jesper Jensen with CPR number 7354277112 has an account in FastMoney Bank with balance 100
    And Jesper Jensen is customer in DTUPay with CPR number 7354277112
    And Turkish Kitchen with CVR number 62774955 has an account in FastMoney Bank with balance 100000000
    And Turkish Kitchen is merchant in DTUPay with CVR number 62774955
    And the customer requests and receives a barcode
    When the FastMoney account with CPR number 62774955 is deleted
    And A merchant scans the barcode and sends an invoice for a payment of 40
    Then the transfer is denied with response code 403
    And then balance is 100 on the customer's account

  Scenario: Merchant cannot get paid to a non-existing account in FastMoney Bank
    Given no accounts in FastMoney Bank and DTUPay exists with CPR 3113542555 and CVR 88476904
    Given Jesper Jensen with CPR number 3113542555 has an account in FastMoney Bank with balance 100
    And Jesper Jensen is customer in DTUPay with CPR number 3113542555
    And Turkish Kitchen with CVR number 88476904 has an account in FastMoney Bank with balance 100000000
    And Turkish Kitchen is merchant in DTUPay with CVR number 88476904
    And the customer requests and receives a barcode
    When the FastMoney account with CPR number 3113542555 is deleted
    And A merchant scans the barcode and sends an invoice for a payment of 30
    Then the transfer is denied with response code 403
    And then the balance is 100000000 on the merchant's account

  Scenario: A customer cannot pay negative amount
    Given no accounts in FastMoney Bank and DTUPay exists with CPR 9859526433 and CVR 67184597
    Given John Doe with CPR number 9859526433 has an account in FastMoney Bank with balance 500
    And John Doe is customer in DTUPay with CPR number 9859526433
    And Jane Doe with CVR number 67184597 has an account in FastMoney Bank with balance 500
    And Jane Doe is merchant in DTUPay with CVR number 67184597
    And the customer requests and receives a barcode
    When A merchant scans the barcode and sends an invoice for a payment of -500
    Then the transfer is accepted with response code 400
    And then balance is 500 on the customers account and the balance is 500 on the merchant's account

