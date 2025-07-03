package com.example.easybank.cards.service;

import com.example.easybank.cards.constatnts.CardConstants;
import com.example.easybank.cards.dto.CardDto;
import com.example.easybank.cards.entity.Card;
import com.example.easybank.cards.exception.CardAlreadyExistsException;
import com.example.easybank.cards.exception.ResourceNotFoundException;
import com.example.easybank.cards.mapper.CardMapper;
import com.example.easybank.cards.repository.CardRepository;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

/**
 * Belyaev.
 * 27.06.2025
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CardService {

  private final CardRepository cardRepository;

  @Transactional
  public void createCard(String mobileNumber) {
    cardRepository.findByMobileNumber(mobileNumber)
        .ifPresent(card -> {
          throw new CardAlreadyExistsException("Card already registered with given mobileNumber " + mobileNumber);
        });
    cardRepository.save(createNewCard(mobileNumber));
  }

  private Card createNewCard(String mobileNumber) {
    Card newCard = new Card();
    long randomCardNumber = 100000000000L + new Random().nextInt(900000000);
    newCard.setCardNumber(Long.toString(randomCardNumber));
    newCard.setMobileNumber(mobileNumber);
    newCard.setCardType(CardConstants.CREDIT_CARD);
    newCard.setTotalLimit(CardConstants.NEW_CARD_LIMIT);
    newCard.setAmountUsed(0);
    newCard.setAvailableAmount(CardConstants.NEW_CARD_LIMIT);
    return newCard;
  }

  public CardDto fetchCard(String mobileNumber) {
    return cardRepository.findByMobileNumber(mobileNumber)
        .map(card -> CardMapper.mapToCardDto(card, new CardDto()))
        .orElseThrow(() -> new ResourceNotFoundException("Card", "mobileNumber", mobileNumber));
  }


  @Transactional
  public boolean updateCard(CardDto cardDto) {
    Card card = cardRepository.findByCardNumber(cardDto.getCardNumber()).orElseThrow(
        () -> new ResourceNotFoundException("Card", "CardNumber", cardDto.getCardNumber()));
    CardMapper.mapToCard(cardDto, card);
    cardRepository.save(card);
    return true;
  }

  @Transactional
  public boolean deleteCard(String mobileNumber) {
    Card card = cardRepository.findByMobileNumber(mobileNumber).orElseThrow(
        () -> new ResourceNotFoundException("Card", "mobileNumber", mobileNumber)
    );
    cardRepository.deleteById(card.getCardId());
    return true;
  }
}
