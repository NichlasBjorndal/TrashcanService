package mdb.utils;

import core.persistence.MerchantStore;
import core.user.Merchant;
import core.utils.BankServerUtil;
import dtu.ws.fastmoney.BankServiceException_Exception;
import dtu.ws.fastmoney.User;
import io.swagger.api.impl.MerchantResponse;
import org.junit.After;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

/**
 * @author Simon Pileborg
 */
public class MerchantMessageHandlerTest {
    @After
    public void tearDown() {
        MerchantStore.getInstance().clearStore();
    }

    @Test
    public void createValidMerchant() {
        Merchant validMerchant = new Merchant("19120585","Super","Brusen");

        User validMerchantUser = new User();
        validMerchantUser.setFirstName(validMerchant.getFirstName());
        validMerchantUser.setLastName(validMerchant.getLastName());
        validMerchantUser.setCprNumber(validMerchant.getCvr());

        try {
            BankServerUtil.getServer().retireAccount(BankServerUtil.getServer().getAccountByCprNumber(validMerchantUser.getCprNumber()).getId());
        } catch (BankServiceException_Exception e) {
            //Ignore
        }

        try {
            BankServerUtil.getServer().createAccountWithBalance(validMerchantUser,new BigDecimal(0));
        } catch (BankServiceException_Exception e) {
            e.printStackTrace();
        }

        assertEquals(validMerchant.getUserID().toString(),MerchantMessageHandler.createMerchant(validMerchant));

        try {
            BankServerUtil.getServer().retireAccount(BankServerUtil.getServer().getAccountByCprNumber(validMerchantUser.getCprNumber()).getId());
        } catch (BankServiceException_Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void createMerchantInvalidCvrInputTooShort() {
        Merchant merchantInvalidCvrShort = new Merchant("1234567","Super","Brusen");
        assertEquals(MerchantResponse.INVALID_INPUT.getValue(),MerchantMessageHandler.createMerchant(merchantInvalidCvrShort));
    }

    @Test
    public void createMerchantInvalidCvrInputTooLong() {
        Merchant merchantInvalidCvrLong = new Merchant("123456789","Super","Brusen");
        assertEquals(MerchantResponse.INVALID_INPUT.getValue(),MerchantMessageHandler.createMerchant(merchantInvalidCvrLong));
    }

    @Test
    public void createMerchantInvalidCvrInputContainsLetters() {
        Merchant merchantInvalidCvrLetters = new Merchant("1q2w3e4r","Super","Brusen");
        assertEquals(MerchantResponse.INVALID_INPUT.getValue(),MerchantMessageHandler.createMerchant(merchantInvalidCvrLetters));
    }

    @Test
    public void createMerchantInvalidFirstNameInput() { //
        Merchant merchantInvalidFirstName = new Merchant("12345678","","Brusen");
        assertEquals(MerchantResponse.INVALID_INPUT.getValue(),MerchantMessageHandler.createMerchant(merchantInvalidFirstName));

        merchantInvalidFirstName = new Merchant("12345678",null,"Brusen");
        assertEquals(MerchantResponse.INVALID_INPUT.getValue(),MerchantMessageHandler.createMerchant(merchantInvalidFirstName));

    }

    @Test
    public void createMerchantInvalidLastNameInput() { //
        Merchant merchantInvalidLastName = new Merchant("12345678","Super","");
        assertEquals(MerchantResponse.INVALID_INPUT.getValue(),MerchantMessageHandler.createMerchant(merchantInvalidLastName));

        merchantInvalidLastName = new Merchant("12345678","Super",null);
        assertEquals(MerchantResponse.INVALID_INPUT.getValue(),MerchantMessageHandler.createMerchant(merchantInvalidLastName));
    }

    @Test
    public void createMerchantAlreadyExists() { //
        Merchant merchantAlreadyExists = new Merchant("19120585","Super","Brusen");

        User validMerchantUser = new User();
        validMerchantUser.setFirstName(merchantAlreadyExists.getFirstName());
        validMerchantUser.setLastName(merchantAlreadyExists.getLastName());
        validMerchantUser.setCprNumber(merchantAlreadyExists.getCvr());

        try {
            BankServerUtil.getServer().retireAccount(BankServerUtil.getServer().getAccountByCprNumber(validMerchantUser.getCprNumber()).getId());
        } catch (BankServiceException_Exception e) {
            //Ignore
        }

        try {
            BankServerUtil.getServer().createAccountWithBalance(validMerchantUser,new BigDecimal(0));
        } catch (BankServiceException_Exception e) {
            e.printStackTrace();
        }

        assertEquals(merchantAlreadyExists.getUserID().toString(),MerchantMessageHandler.createMerchant(merchantAlreadyExists));

        try {
            BankServerUtil.getServer().retireAccount(BankServerUtil.getServer().getAccountByCprNumber(validMerchantUser.getCprNumber()).getId());
        } catch (BankServiceException_Exception e) {
            e.printStackTrace();
        }

        assertEquals(MerchantResponse.ALREADY_EXISTS.getValue(),MerchantMessageHandler.createMerchant(merchantAlreadyExists));
    }

    @Test
    public void createMerchantNoBankAccount() {
        Merchant merchantNoBankAccount = new Merchant("87654328","No","Bank");
        assertEquals(MerchantResponse.NO_BANK_ACCOUNT.getValue(),MerchantMessageHandler.createMerchant(merchantNoBankAccount));
    }
}