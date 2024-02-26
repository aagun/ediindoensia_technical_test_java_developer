package dev.aagun.ediindonesia.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;

@SpringBootTest
@AutoConfigureMockMvc
@RequiredArgsConstructor
class UserControllerTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("It should be return JSON response and valid properties")
    void testSetDataUserRequestJsonAndWillReturnJsonResponse() throws Exception {
        int userId = 10;
        User user = User.builder()
                .userid(userId)
                .namalengkap("Agun")
                .username("agun10")
                .password("SuperSecretPassword")
                .status('T')
                .build();

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(Objects.requireNonNull(objectToJson(user))))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value("informational"))
                .andExpect(jsonPath("$.data").doesNotExist());

        assertThat(userRepository.findById(userId))
                .isPresent()
                .hasValueSatisfying(p -> assertThat(p)
                        .usingRecursiveComparison()
                        .ignoringFields("password")
                        .isEqualTo(user));
    }

    @Test
    @DisplayName("It should be return XML response and valid properties")
    void testSetDataUserRequestXmlAndWillReturnXmlResponse() throws Exception {
        int userId = 10;
        User user = User.builder()
                .userid(userId)
                .namalengkap("Agun")
                .username("agun10")
                .password("SuperSecretPassword")
                .status('T')
                .build();

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_XML)
                        .accept(MediaType.APPLICATION_XML)
                        .content(Objects.requireNonNull(objectToXml(user))))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_XML))
                .andExpect(xpath("/UserResponse/status").string(is("success")))
                .andExpect(xpath("/UserResponse/data").exists());


        assertThat(userRepository.findById(userId))
                .isPresent()
                .hasValueSatisfying(p -> assertThat(p)
                        .usingRecursiveComparison()
                        .ignoringFields("password")
                        .isEqualTo(user));
    }

    @Test
    void testDelDataUserSuccessfully() throws Exception {
        int userId = 10;
        User user = User.builder()
                .userid(userId)
                .namalengkap("Agun")
                .username("agun10")
                .password("SuperSecretPassword")
                .status('T')
                .build();

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_XML)
                .accept(MediaType.APPLICATION_XML)
                .content(Objects.requireNonNull(objectToXml(user))));

        mockMvc.perform(delete("/api/users/" + userId)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        assertThat(userRepository.findById(userId)).isNotPresent();
    }

    @Test
    void testGeDataUserSuccessfully() throws Exception {
        int userId = 10;
        User user = User.builder()
                .userid(userId)
                .namalengkap("Agun")
                .username("agun10")
                .password("SuperSecretPassword")
                .status('T')
                .build();

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_XML)
                .accept(MediaType.APPLICATION_XML)
                .content(Objects.requireNonNull(objectToXml(user))));

        mockMvc.perform(get("/api/users/" + userId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data", hasSize(1)));
    }

    @Test
    void testGeDataAllUserAndWillReturnJSONSuccessfully() throws Exception {
        User user = User.builder()
                .userid(10)
                .namalengkap("Agun")
                .username("agun10")
                .password("SuperSecretPassword")
                .status('T')
                .build();

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_XML)
                .content(Objects.requireNonNull(objectToXml(user))));

        user = User.builder()
                .userid(11)
                .namalengkap("Agun")
                .username("agun11")
                .password("SuperSecretPassword")
                .status('T')
                .build();

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_XML)
                .content(Objects.requireNonNull(objectToXml(user))));


        mockMvc.perform(get("/api/users/all")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data", hasSize(2)));
    }

    @Test
    void testGeDataAllUserAndWillReturnXMLSuccessfully() throws Exception {
        User user = User.builder()
                .userid(10)
                .namalengkap("Agun")
                .username("agun10")
                .password("SuperSecretPassword")
                .status('T')
                .build();

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_XML)
                .content(Objects.requireNonNull(objectToXml(user))));

        user = User.builder()
                .userid(11)
                .namalengkap("Agun")
                .username("agun11")
                .password("SuperSecretPassword")
                .status('T')
                .build();

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_XML)
                .content(Objects.requireNonNull(objectToXml(user))));


        mockMvc.perform(get("/api/users/all")
                        .accept(MediaType.APPLICATION_XML))
                .andExpect(status().isOk())
                .andExpect(xpath("/UserResponse/data").nodeCount(2));
    }

    @Test
    void testGeDataUserAndWillReturnXMLSuccessfully() throws Exception {
        Integer userId = 10;
        User user = User.builder()
                .userid(userId)
                .namalengkap("Agun")
                .username("agun10")
                .password("SuperSecretPassword")
                .status('T')
                .build();

        mockMvc.perform(post("/api/users/")
                .contentType(MediaType.APPLICATION_XML)
                .content(Objects.requireNonNull(objectToXml(user))));

        user = User.builder()
                .userid(11)
                .namalengkap("Agun")
                .username("agun11")
                .password("SuperSecretPassword")
                .status('T')
                .build();

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_XML)
                .content(Objects.requireNonNull(objectToXml(user))));


        mockMvc.perform(get("/api/users/" + userId)
                        .accept(MediaType.APPLICATION_XML))
                .andExpect(status().isOk())
                .andExpect(xpath("/UserResponse/data").nodeCount(1));
    }

    @Test
    void testGeDataUserAndWillReturnJSONSuccessfully() throws Exception {
        Integer userId = 10;
        User user = User.builder()
                .userid(10)
                .namalengkap("Agun")
                .username("agun10")
                .password("SuperSecretPassword")
                .status('T')
                .build();

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_XML)
                .content(Objects.requireNonNull(objectToXml(user))));

        user = User.builder()
                .userid(11)
                .namalengkap("Agun")
                .username("agun11")
                .password("SuperSecretPassword")
                .status('T')
                .build();

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_XML)
                .content(Objects.requireNonNull(objectToXml(user))));


        mockMvc.perform(get("/api/users/" + userId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data", hasSize(1)));
    }
    private String objectToJson(Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            fail("Failed to convert object to json");
            return null;
        }
    }

    private String objectToXml(User user) {
        try {
            ObjectMapper xmlMapper = new XmlMapper();
            return xmlMapper.writeValueAsString(user);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert object to XML", e);
        }
    }
}