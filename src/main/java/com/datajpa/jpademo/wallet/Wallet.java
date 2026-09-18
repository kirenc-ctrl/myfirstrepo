package com.datajpa.jpademo.wallet;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.datajpa.jpademo.transaction.Transaction;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.*;


@Entity
public class Wallet {
	@Id
	@GeneratedValue
	private Integer id;
	@Email
	private String email;
	
	@NotNull
	@Size(min=3, max = 30, message = "Name must be within a minimum of 3 and maximum of 30 characters")
	private String name;
	@NotNull
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", 
			message = "Password must contain At least one lowercase letter, one uppercase letter, At least one digit, At least one special character from @, $, !, %, *, ?, & and a Minimum length of 8 characters")
	private String password;
	@Min(value = 100, message = "Minimum Opening Balance must be Rs.100")
	private Double balance;
	private LocalDate createdOnDate;
	private Boolean isActive;
	
	@OneToMany(fetch = FetchType.LAZY)
	private List<Transaction> walletTrns = new ArrayList<>(); 

	public Wallet(Integer id, @Email String email,
			@NotNull @Size(min = 3, max = 30, message = "Name must be within a minimum of 3 and maximum of 30 characters") String name,
			@NotNull @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", message = "Password must contain At least one lowercase letter, one uppercase letter, At least one digit, At least one special character from @, $, !, %, *, ?, & and a Minimum length of 8 characters") String password,
			@Min(value = 100, message = "Minimum Opening Balance must be Rs.100") Double balance,
			LocalDate createdOnDate, Boolean isActive, List<Transaction> walletTrns) {
		this.id = id;
		this.email = email;
		this.name = name;
		this.password = password;
		this.balance = balance;
		this.createdOnDate = createdOnDate;
		this.isActive = isActive;
		this.walletTrns = walletTrns;
	}

	public Wallet(Integer id, String email, String name, String password, Double balance, LocalDate createdOnDate) {
		this.id = id;
		this.email = email;
		this.name = name;
		this.password = password;
		this.balance = balance;
		this.createdOnDate = createdOnDate;
		this.isActive = true;
	}

	public Wallet() {
		this.isActive = true;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public LocalDate getCreatedOnDate() {
		return createdOnDate;
	}

	public void setCreatedOnDate(LocalDate createdOnDate) {
		this.createdOnDate = createdOnDate;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public List<Transaction> getWalletTrns() {
		return walletTrns;
	}

	public void setWalletTrns(List<Transaction> walletTrns) {
		this.walletTrns = walletTrns;
	}

}
