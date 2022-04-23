/**
 * 
 */
package com.bank.account.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bank.account.model.Card;


@Repository
public interface CardRepository extends JpaRepository<Card, UUID> {
	Optional<Card> findByCardNumber(String cardNo);
}
