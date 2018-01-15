package mdb;

import core.user.Customer;
import dtu.ws.fastmoney.Account;
import dtu.ws.fastmoney.BankService;
import dtu.ws.fastmoney.BankServiceException_Exception;
import dtu.ws.fastmoney.BankServiceService;
import mdb.utils.BankserverUtil;
import mdb.utils.GsonWrapper;
import persistence.CustomerStore;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;

@MessageDriven(name = "CreateCustomerMDB", activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "queue/CreateCustomerQueue"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge")})
public class CreateCustomerMDB extends BaseMDB {
    @Override
    protected String processMessage(String receivedText) {
        Customer customer = (Customer) GsonWrapper.fromJson(receivedText, Customer.class);

        CustomerStore instance = CustomerStore.getInstance();

        String response;

        //Checks if input it valid
        if(!isValidInput(customer)){
            response = "invalidInput";
        }

        //Checks if an account already exists
        else if(instance.cprExists(customer)){
            response = "accountExistsError";
        }

        //Checks if the customer doesn't have a bank account
        else if(!CheckIfBankAccountExists(customer.getCpr())){
            response = "noBankAccountError";
        } else {
            instance.saveCustomer(customer);
            response = customer.getUserID().toString();
        }
        return GsonWrapper.toJson(response);
    }

    private boolean isValidInput(Customer customer) {

        boolean isValidName = !(customer.getFirstName() == null || customer.getFirstName().isEmpty() || customer.getLastName() == null || customer.getLastName().isEmpty());
        boolean isValidCpr = customer.getCpr().length() == 10 && customer.getCpr().matches("[0-9]+");
        return isValidCpr && isValidName;
    }

    private boolean CheckIfBankAccountExists(String cpr) {
        BankService server = BankserverUtil.GetServer();

        Account accountByCprNumber = null;

        try {
             accountByCprNumber = server.getAccountByCprNumber(cpr);
        } catch (BankServiceException_Exception e) {
            e.printStackTrace();
        }

        return accountByCprNumber != null;
    }
}
