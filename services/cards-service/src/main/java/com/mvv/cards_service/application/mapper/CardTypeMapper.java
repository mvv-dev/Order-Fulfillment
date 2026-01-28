package com.mvv.cards_service.application.mapper;

import com.mvv.cards_service.application.dto.HttpCreateCardTypeDTO;
import com.mvv.cards_service.application.usecase.command.CreateCardTypeCommand;
import com.mvv.cards_service.domain.model.CardType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CardTypeMapper {

    CreateCardTypeCommand toDomain(HttpCreateCardTypeDTO createCardTypeDTO);

}
