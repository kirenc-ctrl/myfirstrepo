package com.datajpa.jpademo.jpa_mappings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/accounts")
public class UserAccountController {
	
	
	private final UserAccountRepository userAccountRepository; //Has a dependency
	private final AddressRepository addressRepository; //Has a dependency
	private final PostRepository postRepository; //Has a dependency
	
	@Autowired
	public UserAccountController(UserAccountRepository userAccountRepository, AddressRepository addressRepository, PostRepository postRepository) {
		this.userAccountRepository = userAccountRepository;
		this.addressRepository = addressRepository;
		this.postRepository = postRepository;
	}

	@GetMapping
	public String info() {
		return "User Account App. is Running Successfully";
	}
	
	@PostMapping
	@Transactional
	public UserAccount registerNewAccount(@RequestBody UserAccount newAccount) {
		return this.userAccountRepository.save(newAccount);
	}
	
	@PostMapping("/{id}/address")
	//@Transactional
	public UserAccount registerNewAddress(@RequestBody Address newAddr, @PathVariable("id") Integer userId) {
		UserAccount userAcc = this.userAccountRepository.findById(userId).orElseThrow(() -> new RuntimeException("User ID: " + userId + " does not exists"));
		Address address = this.addressRepository.save(newAddr);
		userAcc.setAddress(address);
		return this.userAccountRepository.save(userAcc);
	}

	@PostMapping("/{id}/posts")
	//@Transactional
	public UserAccount postReview(@RequestBody Post newPost, @PathVariable("id") Integer userId) {
		UserAccount userAcc = this.userAccountRepository.findById(userId).orElseThrow(() -> new RuntimeException("User ID: " + userId + " does not exists"));
		Post reviewPost = this.postRepository.save(newPost);
		userAcc.getPosts().add(reviewPost);
		return this.userAccountRepository.save(userAcc);
	}
	
	@GetMapping("/{id}")
	public UserAccount getAllDetailsOfUser(@PathVariable() Integer id) {
		UserAccount userAcc = this.userAccountRepository.findById(id).orElseThrow(() -> new RuntimeException("The ID: " + id + " does not exist"));
		return userAcc;
	}
}
