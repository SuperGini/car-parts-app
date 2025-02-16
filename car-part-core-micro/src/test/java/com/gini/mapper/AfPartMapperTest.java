package com.gini.mapper;


import com.gini.dto.AfPartRequest;
import com.gini.dto.Currency;
import com.gini.dto.PriceRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AfPartMapperTest {


    @Test
    @DisplayName("""
            GIVEN AfPartRequest
            WILL return an entity AftermarketPart
            """)
    void returnsAfPart() {
        //given
        var afpartRequest = new AfPartRequest()
                .price(new PriceRequest().price(new BigDecimal(20)).currency(Currency.RON))
                .afPartNumber("f-205-356");

        //when
        var actual = AfPartMapper.mapFrom(afpartRequest);

        //then
        assertEquals(BigDecimal.valueOf(20), actual.getPrice().getPartPrice());
        assertEquals(Currency.RON.name(), actual.getPrice().getCurrency().name());
        assertEquals("f-205-356", actual.getAfPartNumber());
    }

}