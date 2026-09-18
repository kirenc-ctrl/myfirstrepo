package com.datajpa.jpademo.wallet;

import java.util.List;

public interface WalletService {

	
    // CRUD
    Wallet registerNewWalletUser(Wallet newWallet);

    Wallet getUserWalletById(Integer id);

    Wallet updateUserWallet(Wallet updateWallet);

    // 1. Add Funds
    Double addFundsToWalletById(Integer id, Double balance);

    // 2. Withdraw funds
    Double withdrawFundsFromWalletById(Integer id, Double amount);

    // 3. Transfer funds
    Boolean fundTransfer(Integer fromId, Integer toId, Double amount);

    // 4. Activate / deactivate my account
    Boolean deactivateWalletById(Integer id);

    Boolean activateWalletById(Integer id);
    
    List<Wallet> getAllWallets();


}