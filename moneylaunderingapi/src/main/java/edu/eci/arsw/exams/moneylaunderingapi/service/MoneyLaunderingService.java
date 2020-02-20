package edu.eci.arsw.exams.moneylaunderingapi.service;

import edu.eci.arsw.exams.moneylaunderingapi.model.SuspectAccount;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

public interface MoneyLaunderingService {
    void updateAccountStatus(SuspectAccount suspectAccount);
    SuspectAccount getAccountStatus(String accountId);
    List<SuspectAccount> getSuspectAccounts();
}
