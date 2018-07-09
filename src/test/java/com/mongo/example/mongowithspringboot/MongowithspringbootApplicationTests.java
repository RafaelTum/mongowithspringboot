package com.mongo.example.mongowithspringboot;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.mongo.example.mongowithspringboot.data.mongo.document.LocationDocument;
import com.mongo.example.mongowithspringboot.data.mongo.document.UserDocument;
import com.mongo.example.mongowithspringboot.data.mongo.repository.UserRepository;
import com.mongo.example.mongowithspringboot.web.model.UserCreateModel;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
//@ContextConfiguration(classes = TestDBLoader.class)
//@TestPropertySource(locations = "classpath:application-test.properties")
public class MongowithspringbootApplicationTests {


  @Autowired
  private MockMvc mockMvc;

  @Autowired
  UserRepository repository;
  @Autowired
  MongoOperations operations;
  private HttpMessageConverter mappingJackson2HttpMessageConverter;

  @Autowired
  void setConverters(HttpMessageConverter<?>[] converters) {

    this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
        .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
        .findAny()
        .orElse(null);

    assertNotNull("the JSON message converter must not be null",
        this.mappingJackson2HttpMessageConverter);
  }


  UserDocument dave, oliver, carter;

  @Before
  public void setUp() {

    repository.deleteAll();

    dave = repository.save(new UserDocument("5b2920f36867f655b9d186d7","Dave", "Matthews","18","Yerevan"));
     oliver = repository.save(new UserDocument("Oliver August", "Matthews","18","Yerevan"));
    carter = repository.save(new UserDocument("Carter", "Beauford","18","Yerevan"));
  }


  @Test
  public void getallEntities() throws Exception {
    this.mockMvc.perform(get("/api/users")).andDo(print()).andExpect(status().isOk()).
        andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).
        andExpect(content().json(
            "[{\"id\":"+dave.getId()+",\"firstName\":\"Dave\",\"lastName\":\"Matthews\",\"age\":\"18\",\"city\":\"Yerevan\",\"locations\":null},{\"id\":"+oliver.getId()+",\"firstName\":\"Oliver August\",\"lastName\":\"Matthews\",\"age\":\"18\",\"city\":\"Yerevan\",\"locations\": null},{\"id\":"+carter.getId()+",\"firstName\":\"Carter\",\"lastName\":\"Beauford\",\"age\":\"18\",\"city\":\"Yerevan\",\"locations\":null}]"));
  }

  @Test
  public void createUser() throws Exception {
    UserCreateModel userCreateModel = new UserCreateModel();
    userCreateModel.setFirstName("Petros");
    userCreateModel.setLastName("Petros");
    userCreateModel.setAge("53");
    userCreateModel.setCity("Talin");
    LocationDocument locationDocument = new LocationDocument();
    locationDocument.setDate(LocalDate.of(2017,11,11));
    locationDocument.setId("11");
    locationDocument.setCountry("France");
    userCreateModel.setLocations(Arrays.asList(locationDocument));
    String userJson = json(userCreateModel);
    this.mockMvc.perform(
        post("/api/users")
            .contentType(MediaType.APPLICATION_JSON_VALUE).content(userJson)
    )
        .andExpect(status().isOk()).andDo(print());
  }

  @Test
  public void updateEntity() throws Exception {
    this.mockMvc.perform(
        put("/api/users")
            .contentType(MediaType.APPLICATION_JSON_VALUE).content(
            "{\"id\":\"5b2920f36867f655b9d186d7\",\"firstName\":\"Poxos\",\"lastName\":\"Petros\",\"age\":\"53\",\"city\":\"Talin\",\"locations\":[{\"id\":\"1\",\"country\":\"Austria\",\"date\":\"2017-05-28\"}]}")
    )
        .andExpect(status().isOk());
  }

  @Test
  public void deleteEntity() throws Exception {
    this.mockMvc.perform(
        delete("/api/users/{id}", dave)
            .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andDo(print());
  }

  protected String json(Object o) throws IOException {
    MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
    this.mappingJackson2HttpMessageConverter.write(
        o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
    return mockHttpOutputMessage.getBodyAsString();
  }

}
