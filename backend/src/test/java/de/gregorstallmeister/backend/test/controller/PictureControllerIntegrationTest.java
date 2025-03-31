package de.gregorstallmeister.backend.test.controller;

import de.gregorstallmeister.backend.model.Picture;
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

import java.time.Instant;

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
            Assertions.fail();
        }
    }

    @Test
    @DirtiesContext
    void getAllPictures() {
        // given
        Picture picture1 = new Picture(
                "testID-123",
                "https://gregorstallmeister.de/fotogalerie/bilder/test123.jpg",
                "Langeoog",
                Instant.parse("2025-03-26T09:17:30+01:00"));
        Picture picture2 = new Picture(
                "testID-124",
                "https://gregorstallmeister.de/fotogalerie/bilder/test124.jpg",
                "Carolinensiel",
                Instant.parse("2025-08-26T09:17:30+02:00"));
        pictureRepository.insert(picture1);
        pictureRepository.insert(picture2);

        // when + then
        try {
            mockMvc.perform(MockMvcRequestBuilders.get("/api/picture"))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.content().json("""
                            [
                              {
                                  "id": "testID-123",
                                  "imagePath": "https://gregorstallmeister.de/fotogalerie/bilder/test123.jpg",
                                  "location": "Langeoog",
                                  instant: "2025-03-26T08:17:30Z"
                              },
                              {
                                  "id": "testID-124",
                                  "imagePath": "https://gregorstallmeister.de/fotogalerie/bilder/test124.jpg",
                                  "location": "Carolinensiel",
                                  instant: "2025-08-26T07:17:30Z"
                              }
                            ]
                            """));
        } catch (Exception e) {
            Assertions.fail();
        }
    }

    @Test
    @DirtiesContext
    void getAllPicturesWhenNoneIsPresent() {
        // given: Nothing

        // when + then
        try {
            mockMvc.perform(MockMvcRequestBuilders.get("/api/picture"))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.content().json("""                         
                                []
                            """));
        } catch (Exception e) {
            Assertions.fail();
        }
    }

    @Test
    @DirtiesContext
    void getPictureById() {
        // given
        Picture picture = new Picture(
                "testID-123",
                "https://gregorstallmeister.de/fotogalerie/bilder/test123.jpg",
                "Langeoog",
                Instant.parse("2025-03-26T09:17:30+01:00"));
        pictureRepository.insert(picture);

        // when + then
        try {
            mockMvc.perform(MockMvcRequestBuilders.get("/api/picture/testID-123"))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.content().json("""                          
                            {
                                  "id": "testID-123",
                                  "imagePath": "https://gregorstallmeister.de/fotogalerie/bilder/test123.jpg",
                                  "location": "Langeoog",
                                  instant: "2025-03-26T08:17:30Z"
                            }
                            """));
        } catch (Exception e) {
            Assertions.fail();
        }
    }

    @Test
    @DirtiesContext
    void getPictureByIdWhenNotPresent() {
        // given: no Picture, nothing else but the class members

        // when + then
        try {
            mockMvc.perform(MockMvcRequestBuilders.get("/api/picture/testID-123"))
                    .andExpect(MockMvcResultMatchers.status().isNotFound())
                    .andExpect(MockMvcResultMatchers.content().string(""));
        } catch (Exception e) {
            Assertions.fail();
        }
    }

    @Test
    @DirtiesContext
    void updatePicture() {
        // given
        String id = "testID-123";
        Picture picture = new Picture(
                id,
                "https://gregorstallmeister.de/fotogalerie/bilder/test123.jpg",
                "Langeoog",
                Instant.parse("2025-03-26T09:17:30+01:00"));
        pictureRepository.insert(picture);

        // when + then
        try {
            mockMvc.perform(MockMvcRequestBuilders.put("/api/picture/" + id)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("""                                    
                                    {
                                        "imagePath": "https://gregorstallmeister.de/fotogalerie/bilder/test123.jpg",
                                        "location": "Mainz",
                                        "instant": "2024-03-26T09:17:30+01:00"
                                    }
                                    """))
                .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.content().json("""
                            {
                                        "id": "testID-123",
                                        "imagePath": "https://gregorstallmeister.de/fotogalerie/bilder/test123.jpg",
                                        "location": "Mainz",
                                        "instant": "2024-03-26T08:17:30Z"
                            }
                            """));
        } catch (Exception e) {
            Assertions.fail();
        }
    }

    @Test
    @DirtiesContext
    void updatePictureWhenNotPresent() {
        // given: No picture, nothing but the class members

        // when + then
        try {
            mockMvc.perform(MockMvcRequestBuilders.put("/api/picture/test123")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("""                                    
                                    {
                                        "imagePath": "https://gregorstallmeister.de/fotogalerie/bilder/test123.jpg",
                                        "location": "Mainz",
                                        "instant": "2024-03-26T09:17:30+01:00"
                                    }
                                    """))
                    .andExpect(MockMvcResultMatchers.status().isNotFound())
                    .andExpect(MockMvcResultMatchers.content().json("""
                            {
                                "message": "An error occurred: Picture to update not found with ID: test123"
                            }
                            """))
                    .andExpect(jsonPath("$.instant").isNotEmpty());
        }
        catch (Exception e) {
            Assertions.fail();
        }
    }
}
