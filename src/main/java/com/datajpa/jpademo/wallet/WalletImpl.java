package com.datajpa.jpademo.wallet;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.datajpa.jpademo.transaction.Transaction;
import com.datajpa.jpademo.transaction.TransactionRepository;
import com.datajpa.jpademo.transaction.TransactionType;
import com.datajpa.jpademo.transaction.WalletTransactionStatus;



@Service
public class WalletImpl implements WalletService{

	@Autowired
	private WalletRepository walletRepository;
	private TransactionRepository trnRepository;
	
	public WalletImpl (WalletRepository walletRepository, TransactionRepository trnRepository) {
		this.walletRepository = walletRepository;
		this.trnRepository = trnRepository;
	}
	
	@Override
	public Wallet registerNewWalletUser(Wallet newWallet) {
		if(this.walletRepository.findByEmail(newWallet.getEmail()).isPresent()) {
			throw new WalletException("Account with Given E-mail: " + newWallet.getEmail() + " already exists");
		}
		newWallet.setCreatedOnDate(LocalDate.now());
		return this.walletRepository.save(newWallet);
	}

	@Override
	public Wallet getUserWalletById(Integer id) {
		Optional<Wallet> foundWalletOpt = this.walletRepository.findById(id);
		if (foundWalletOpt.isPresent()){
			return foundWalletOpt.get();
		} 
		return null;
	}

	@Override
	public Wallet updateUserWallet(Wallet updateWallet) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Double addFundsToWalletById(Integer id, Double balance) {
		//Wallet foundWallet = this.walletRepository.findById(id).orElse(null);
		Wallet foundWallet = this.walletRepository.findById(id).orElseThrow(()->new WalletException("Wallet ID: "+ id + " does not exist"));
		Double currentBalance = foundWallet.getBalance();
		currentBalance = currentBalance + balance;
		foundWallet.setBalance(currentBalance);
		this.walletRepository.save(foundWallet);
		return currentBalance;
	}

	@Override
	@org.springframework.transaction.annotation.Transactional
	public Double withdrawFundsFromWalletById(Integer id, Double amount) {
		Wallet foundWallet = this.walletRepository.findById(id).orElseThrow(()->new WalletException("Wallet ID: "+ id + " does not exist"));
		Double currentBalance = foundWallet.getBalance();
		if (currentBalance - amount >= 0) {
			currentBalance = currentBalance - amount;
			foundWallet.setBalance(currentBalance);
		} else {
			throw new WalletException("Insufficient Balance in Source Account: "+ id);
		}
		return currentBalance;
	}

	@Override
	public Boolean fundTransfer(Integer fromId, Integer toId, Double amount) {
		//Check for all exceptions before processing
		Wallet fromWallet = this.walletRepository.findById(fromId).orElseThrow(()->new WalletException("Wallet ID: "+ fromId + " does not exist"));
		Wallet toWallet = this.walletRepository.findById(toId).orElseThrow(()->new WalletException("Wallet ID: "+ toId + " does not exist"));
		LocalDate trnDate = LocalDate.now();
		Transaction debitTrn = new Transaction();  //Transient
		debitTrn.setPaymentGateway("Wallet");
		debitTrn.setTransactionType(TransactionType.DEBIT);
		debitTrn.setTrnDate(trnDate);
		debitTrn.setAmt(amount);
		if (fromWallet.getBalance() < amount) {
			debitTrn.setTrnStatus(WalletTransactionStatus.FAILED);
			debitTrn = this.trnRepository.save(debitTrn);
			fromWallet.getWalletTrns().add(debitTrn);
			this.walletRepository.save(fromWallet);
			throw new WalletException("Insufficient Balance in Source Account: "+ fromId);
		} else {
			debitTrn.setTrnStatus(WalletTransactionStatus.SUCCESS);
			Double currentBalanceInSrcWallet = fromWallet.getBalance();
			Double currentBalanceInDestWallet = toWallet.getBalance();
			currentBalanceInSrcWallet = currentBalanceInSrcWallet - amount;
			currentBalanceInDestWallet = currentBalanceInDestWallet + amount;
			toWallet.setBalance(currentBalanceInDestWallet);
			fromWallet.setBalance(currentBalanceInSrcWallet);
			Transaction creditTrn = new Transaction();  //Transient
			creditTrn.setPaymentGateway("Wallet");
			creditTrn.setTransactionType(TransactionType.CREDIT);
			creditTrn.setTrnStatus(WalletTransactionStatus.SUCCESS);
			creditTrn.setTrnDate(trnDate);
			creditTrn.setAmt(amount);
			debitTrn = this.trnRepository.save(debitTrn);
			creditTrn = this.trnRepository.save(creditTrn);
			fromWallet.getWalletTrns().add(debitTrn);
			toWallet.getWalletTrns().add(creditTrn);
			this.walletRepository.save(fromWallet);
			this.walletRepository.save(toWallet);
			return true;
		}
		
		
	}

	@Override
	@Transactional
	public Boolean deactivateWalletById(Integer id) {
		Wallet wallet = this.walletRepository.findById(id).orElseThrow(()->new WalletException("Wallet ID: "+ id + " does not exist"));
		if (!wallet.getIsActive()) throw new WalletException("Wallet is already inactive");
		wallet.setIsActive(false);
		return true;
	}

	@Override
	@Transactional
	public Boolean activateWalletById(Integer id) {
		Wallet wallet = this.walletRepository.findById(id).orElseThrow(()->new WalletException("Wallet ID: "+ id + " does not exist"));
		if (wallet.getIsActive()) throw new WalletException("Wallet is already Active");
		wallet.setIsActive(true);
		return true;
	}
	
	
	  @Override 
	  public List<Wallet> getAllWallets(){ 
		  return  this.walletRepository.getAllWallets(); 
	  }
	 
	

}
