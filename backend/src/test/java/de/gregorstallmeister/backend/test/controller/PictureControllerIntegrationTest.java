package de.gregorstallmeister.backend.test.controller;

import de.gregorstallmeister.backend.repository.PictureRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class PictureControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    PictureRepository pictureRepository;

    @Test
    @DirtiesContext
    void postPicture() {
        // given: the class members, nothing else

        // when + then
        try {
            mockMvc.perform(MockMvcRequestBuilders
                            .post("/api/picture")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("""
                                     {
                                          "imagePath": "https://gregorstallmeister.de/fotogalerie/bilder/test123.jpg",
                                          "location": "Langeoog",
                                          "instant": "2025-03-26T09:17:30+01:00"                                                                                          \s
                                     }
                                    \s"""))
                    .andExpect(MockMvcResultMatchers.status().isCreated())
                    .andExpect(MockMvcResultMatchers.content().json("""
                            
                                     {
                                          "imagePath": "https://gregorstallmeister.de/fotogalerie/bilder/test123.jpg",
                                          "location": "Langeoog",
                                          "instant": "2025-03-26T08:17:30Z"                                                                                          \s
                                     }
                            """))
                    .andExpect(jsonPath("$.id").isNotEmpty());
        } catch (Exception e) {
            System.out.println(e.toString());
            Assertions.fail();
        }
    }
}
