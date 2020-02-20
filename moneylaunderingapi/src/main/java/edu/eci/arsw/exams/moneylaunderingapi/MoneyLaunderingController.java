package edu.eci.arsw.exams.moneylaunderingapi;


import edu.eci.arsw.exams.moneylaunderingapi.model.SuspectAccount;
import edu.eci.arsw.exams.moneylaunderingapi.service.MoneyLaunderingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MoneyLaunderingController
{
	@Autowired
    @Qualifier("moneylaundering")
    MoneyLaunderingService moneyLaunderingService;

    @RequestMapping( value = "/fraud-bank-accounts")
    public List<SuspectAccount> offendingAccounts() {
        return moneyLaunderingService.getSuspectAccounts();
    }
 
}
