package de.gregorstallmeister.pg.backend.test.controller;

import de.gregorstallmeister.pg.backend.controller.GlobalExceptionHandler;
import de.gregorstallmeister.pg.backend.repository.ErrorMessageRepository;
import de.gregorstallmeister.pg.backend.repository.PictureRepository;
import de.gregorstallmeister.pg.backend.service.ErrorMessageService;
import org.bson.assertions.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
class GlobalExceptionHandlerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    private final PictureRepository pictureRepository = mock(PictureRepository.class);
    private final ErrorMessageRepository errorMessageRepository = mock(ErrorMessageRepository.class);

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(pictureRepository)
                .setControllerAdvice(new GlobalExceptionHandler(new ErrorMessageService(errorMessageRepository)))
                .build();
    }

    @Test
    @DirtiesContext
    void internalServerError() {
        try {
            // given
            when(pictureRepository.findAll()).thenThrow(new RuntimeException("Database not reachable!"));

            // when + then
            mockMvc.perform(MockMvcRequestBuilders.get("/api/picture/"))
                    .andExpect(MockMvcResultMatchers.status().is5xxServerError());
        } catch (Exception e) {
            Assertions.fail();
        }
    }
}
