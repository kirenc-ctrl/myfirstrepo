package com.datajpa.jpademo.wallet;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("v1/wallets")
@CrossOrigin(origins = "*")
public class WalletController {

	@Autowired
	private WalletService walletService;
	
	@GetMapping
	public String info() {
		return "Wallet App. is Running Successfully";
	}
	
	//Rest API
	//Register New User
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public Wallet RegisterNewWallet(@Valid @RequestBody Wallet newWallet) {
		return this.walletService.registerNewWalletUser(newWallet);
	}
	
	@GetMapping("/{id}")
	public Wallet getWalletByID(@PathVariable("id") Integer id) {
		return this.walletService.getUserWalletById(id);
	}
	
	//Put or Patch or Post can be used
	@PatchMapping
	public Double addFundsToWalletByID(@RequestBody WalletDTO walletDto) {
		return this.walletService.addFundsToWalletById(walletDto.getToId(), walletDto.getAmount());		
	}
	
	@PatchMapping("/withdraw")
	public Double withdrawFundsFromWalletByID(@RequestBody WalletDTO walletDto) {
		return this.walletService.withdrawFundsFromWalletById(walletDto.getFromId(), walletDto.getAmount());		
	}
	
	@PatchMapping("/transfer")
	public Boolean transferFundsBetweenWallets(@RequestBody WalletDTO walletDto) {
		return this.walletService.fundTransfer(walletDto.getFromId(), walletDto.getToId(), walletDto.getAmount());		
	}
	
	@PatchMapping("/deactivate/{id}")
	public Boolean deactivateAccount(@PathVariable("id") Integer id) {
		return this.walletService.deactivateWalletById(id);
	}
	
	/*
	 * @PatchMapping("/activate/{id}") public Boolean
	 * activateAccount(@PathVariable("id") Integer id) { return
	 * this.walletService.activateWalletById(id); }
	 */
	
	@GetMapping("/all")
	public List<Wallet> getAllWallets() {
		return this.walletService.getAllWallets();
	}
	
}
