package edu.eci.arsw.exams.moneylaunderingapi.service;

import edu.eci.arsw.exams.moneylaunderingapi.model.SuspectAccount;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
@Component("moneylaundering")
public class MoneyLaunderingServiceStub implements MoneyLaunderingService {

    @Override
    public void updateAccountStatus(SuspectAccount suspectAccount) {
        //TODO
    }

    @Override
    public SuspectAccount getAccountStatus(String accountId) {
        //TODO
        return null;
    }

    @Override
    public List<SuspectAccount> getSuspectAccounts() {
        //TODO
        return null;
    }
}
