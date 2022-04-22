/**
 * 
 */
package com.bank.account.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.bank.account.model.Card;

/**
 * @author Ravi
 *
 */

@DataJpaTest
public class CardRepositoryTest {

	@Autowired
	private CardRepository cardRepository;

	@Test
	public void givenCard_whenSave_thenGetOk() {

		Card card1 = Card.builder().cardType(Card.CardType.DEBIT).cardNumber("8283788142425").cvv(345).build();
		cardRepository.save(card1);

		Card card2 = Card.builder().cardType(Card.CardType.CREDIT).cardNumber("8283788188614").cvv(345).build();
		card2 = cardRepository.save(card2);

		Optional<Card> card3 = cardRepository.findById(card2.getId());
		assertTrue(card3.isPresent());
		assertEquals("8283788188614", card3.get().getCardNumber());
		assertEquals("CREDIT", card3.get().getCardType().getType());
	}

	@Test
	public void givenCard_whenSave_thenGetByCardNumber() {

		Card card1 = Card.builder().cardType(Card.CardType.DEBIT).cardNumber("333333333333").cvv(345).build();
		cardRepository.save(card1);

		Optional<Card> card3 = cardRepository.findByCardNumber("333333333333");
		assertTrue(card3.isPresent());
		assertEquals(345, card3.get().getCvv());
		assertEquals("DEBIT", card3.get().getCardType().getType());
	}
}
