package com.datajpa.jpademo.wallet;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

//@Repository
public interface WalletRepository extends JpaRepository<Wallet, Integer> {

	//Option 1: Custom JPQL Query by Method Name
	Optional<Wallet> findByEmail(String email); //Select wallet from Wallet wallet where wallet.email = "email"
	
	//Option 2: Passing JPQL Query by Annotations
	@Query("Select wallet from Wallet wallet where wallet.email = ?1")
	Wallet searchForWalledByByEmail(String email); 
	
	//Option 3: Passing JPQL Query by Annotations
	@Query(value = "Select * from wallet where email = ?1", nativeQuery = true)
	Wallet searchForWalledByByEmailUsingNativeQuery(String email);
	
	@Query("Select wallet from Wallet wallet")
	List<Wallet> getAllWallets();
	
}
