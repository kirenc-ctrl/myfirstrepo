package com.datajpa.jpademo.transaction;

import jakarta.persistence.Entity;


import java.time.LocalDate;

import com.datajpa.jpademo.wallet.Wallet;

import jakarta.persistence.*;

@Entity
public class Transaction {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	private Double amt;
	private TransactionType transactionType;
	private WalletTransactionStatus trnStatus;
	private String paymentGateway;
	//private Wallet wallet;
	private LocalDate trnDate;
	public Transaction(Integer id, Double amt, TransactionType transactionType, WalletTransactionStatus trnStatus,
			String paymentGateway, Wallet wallet, LocalDate trnDate) {
		this.id = id;
		this.amt = amt;
		this.transactionType = transactionType;
		this.trnStatus = trnStatus;
		this.paymentGateway = paymentGateway;
		this.trnDate = trnDate;
	}
	
	public Transaction() {
		
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Double getAmt() {
		return amt;
	}
	public void setAmt(Double amt) {
		this.amt = amt;
	}
	public TransactionType getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}
	public String getPaymentGateway() {
		return paymentGateway;
	}
	public void setPaymentGateway(String paymentGateway) {
		this.paymentGateway = paymentGateway;
	}
//	public Wallet getWallet() {
//		return wallet;
//	}
//	public void setWallet(Wallet wallet) {
//		this.wallet = wallet;
//	}
	public LocalDate getTrnDate() {
		return trnDate;
	}
	public void setTrnDate(LocalDate trnDate) {
		this.trnDate = trnDate;
	}
	public WalletTransactionStatus getTrnStatus() {
		return trnStatus;
	}
	public void setTrnStatus(WalletTransactionStatus trnStatus) {
		this.trnStatus = trnStatus;
	}
	
	
}
