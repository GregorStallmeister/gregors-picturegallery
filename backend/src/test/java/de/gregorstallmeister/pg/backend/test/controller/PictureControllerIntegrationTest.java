package de.gregorstallmeister.pg.backend.test.controller;

import de.gregorstallmeister.pg.backend.model.Picture;
import de.gregorstallmeister.pg.backend.repository.PictureRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.Instant;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
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
    @WithMockUser
    void postPicture() {
        // given: the class members, nothing else

        // when + then
        try {
            mockMvc.perform(MockMvcRequestBuilders
                            .post("/api/picture")
                            .with(user("admin").roles("ADMIN"))
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("""
                                     {
                                          "imagePath": "https://gregorstallmeister.de/fotogalerie/bilder/test123.jpg",
                                          "location": "Langeoog",
                                          "instant": "2025-03-26T09:17:30+01:00",
                                          "positionInGrid": "latitude=53.7474&longitude=7.4926"                                                                                          \s
                                     }
                                    \s"""))
                    .andExpect(MockMvcResultMatchers.status().isCreated())
                    .andExpect(MockMvcResultMatchers.content().json("""
                            
                                     {
                                          "imagePath": "https://gregorstallmeister.de/fotogalerie/bilder/test123.jpg",
                                          "location": "Langeoog",
                                          "instant": "2025-03-26T08:17:30Z",
                                          "positionInGrid": "latitude=53.7474&longitude=7.4926"                                                                                          \s
                                     }
                            """))
                    .andExpect(jsonPath("$.id").isNotEmpty());
        } catch (Exception e) {
            Assertions.fail();
        }
    }

    @Test
    @DirtiesContext
        // This test must run without a MockUser, because get does not require a login.
    void getAllPictures() {
        // given
        Picture picture1 = new Picture(
                "testID-123",
                "https://gregorstallmeister.de/fotogalerie/bilder/test123.jpg",
                "Langeoog",
                Instant.parse("2025-03-26T09:17:30+01:00"),
                "latitude=53.7474&longitude=7.4926");
        Picture picture2 = new Picture(
                "testID-124",
                "https://gregorstallmeister.de/fotogalerie/bilder/test124.jpg",
                "Carolinensiel",
                Instant.parse("2025-08-26T09:17:30+02:00"),
                "latitude=53.6922&longitude=7.8025");
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
                                  "instant": "2025-03-26T08:17:30Z",
                                  "positionInGrid": "latitude=53.7474&longitude=7.4926"
                              },
                              {
                                  "id": "testID-124",
                                  "imagePath": "https://gregorstallmeister.de/fotogalerie/bilder/test124.jpg",
                                  "location": "Carolinensiel",
                                  "instant": "2025-08-26T07:17:30Z",
                                  "positionInGrid": "latitude=53.6922&longitude=7.8025"
                              }
                            ]
                            """));
        } catch (Exception e) {
            Assertions.fail();
        }
    }

    @Test
    @DirtiesContext
        // This test must run without a MockUser, because get does not require a login.
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
        // This test must run without a MockUser, because get does not require a login.
    void getPictureById() {
        // given
        Picture picture = new Picture(
                "testID-123",
                "https://gregorstallmeister.de/fotogalerie/bilder/test123.jpg",
                "Langeoog",
                Instant.parse("2025-03-26T09:17:30+01:00"),
                "latitude=53.7474&longitude=7.4926");
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
                                  instant: "2025-03-26T08:17:30Z",
                                  "positionInGrid": "latitude=53.7474&longitude=7.4926"
                            }
                            """));
        } catch (Exception e) {
            Assertions.fail();
        }
    }

    @Test
    @DirtiesContext
        // This test must run without a MockUser, because get does not require a login.
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
    @WithMockUser
    void updatePicture() {
        // given
        String id = "testID-123";
        Picture picture = new Picture(
                id,
                "https://gregorstallmeister.de/fotogalerie/bilder/test123.jpg",
                "Langeoog",
                Instant.parse("2025-03-26T09:17:30+01:00"),
                "latitude=53.7474&longitude=7.4926");
        pictureRepository.insert(picture);

        // when + then
        try {
            mockMvc.perform(MockMvcRequestBuilders.put("/api/picture/" + id)
                            .with(user("admin").roles("ADMIN"))
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("""                                    
                                    {
                                        "imagePath": "https://gregorstallmeister.de/fotogalerie/bilder/test123.jpg",
                                        "location": "Mainz",
                                        "instant": "2024-03-26T09:17:30+01:00",
                                        "positionInGrid": "latitude=51.7474&longitude=7.4926"
                                    }
                                    """))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.content().json("""
                            {
                                        "id": "testID-123",
                                        "imagePath": "https://gregorstallmeister.de/fotogalerie/bilder/test123.jpg",
                                        "location": "Mainz",
                                        "instant": "2024-03-26T08:17:30Z",
                                        "positionInGrid": "latitude=51.7474&longitude=7.4926"
                            }
                            """));
        } catch (Exception e) {
            Assertions.fail();
        }
    }

    @Test
    @DirtiesContext
    @WithMockUser
    void updatePictureWhenNotPresent() {
        // given: No picture, nothing but the class members

        // when + then
        try {
            mockMvc.perform(MockMvcRequestBuilders.put("/api/picture/test123")
                            .with(user("admin").roles("ADMIN"))
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("""                                    
                                    {
                                        "imagePath": "https://gregorstallmeister.de/fotogalerie/bilder/test123.jpg",
                                        "location": "Mainz",
                                        "instant": "2024-03-26T09:17:30+01:00",
                                        "positionInGrid": "latitude=53.7474&longitude=7.4926"
                                    }
                                    """))
                    .andExpect(MockMvcResultMatchers.status().isNotFound())
                    .andExpect(MockMvcResultMatchers.content().json("""
                            {
                                "message": "An error occurred: Picture to update was not found with ID: test123"
                            }
                            """))
                    .andExpect(jsonPath("$.instant").isNotEmpty())
                    .andExpect(jsonPath("$.id").isNotEmpty());
        } catch (Exception e) {
            Assertions.fail();
        }
    }

    @Test
    @DirtiesContext
    void deletePicture() {
        // given
        String id = "test-1234";
        Picture picture = new Picture(
                id,
                "https://gregorstallmeister.de/fotogalerie/bilder/test123.jpg",
                "unbekannt",
                Instant.parse("2025-03-26T09:17:30+01:00"),
                "latitude=53.7474&longitude=7.4926");
        pictureRepository.insert(picture);

        // when + then
        try {
            mockMvc.perform(MockMvcRequestBuilders.delete("/api/picture/" + id)
                            .with(user("admin").roles("ADMIN")))
                    .andExpect(MockMvcResultMatchers.status().isNoContent())
                    .andExpect(MockMvcResultMatchers.content().string(""));
        } catch (Exception e) {
            Assertions.fail();
        }
    }

    @Test
    @DirtiesContext
    @WithMockUser
    void deletePictureWhenNotPresent() {
        // given
        String id = "test-1234";

        // when + then
        try {
            mockMvc.perform(MockMvcRequestBuilders.delete("/api/picture/" + id)
                            .with(user("admin").roles("ADMIN")))
                    .andExpect(MockMvcResultMatchers.status().isNotFound())
                    .andExpect(MockMvcResultMatchers.content().json("""
                            {
                                "message": "An error occurred: Picture to delete was not found with ID: test-1234"
                            }
                            """))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.instant").isNotEmpty())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty());
        } catch (Exception e) {
            Assertions.fail();
        }
    }
}
