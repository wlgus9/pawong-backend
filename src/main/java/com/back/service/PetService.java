package com.back.service;

import com.back.domain.Pet;
import com.back.global.exception.CustomException;
import com.back.global.response.ResponseMessage;
import com.back.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.back.global.response.ResponseMessage.PET_SEARCH_FAIL;

@Service
@RequiredArgsConstructor
public class PetService {
    private final PetRepository petRepository;

    public Pet findPet(UUID ownerId) {
        return petRepository.findByOwnerId(ownerId)
                .orElseThrow(() -> new CustomException(PET_SEARCH_FAIL));
    }
}
