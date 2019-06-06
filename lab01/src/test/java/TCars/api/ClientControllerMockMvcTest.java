package TCars.api;

import TCars.domain.Car;
import TCars.domain.Client;

import TCars.service.GarageManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import java.util.LinkedList;
import java.util.List;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ClientControllerMockMvcTest {


        @Autowired
        private MockMvc mockMvc;

        @MockBean
        private GarageManager service;

        @Test
        public void contextLoads() throws Exception {
            assertNotNull(mockMvc);
        }

        @Test
        public void greetingShouldReturnHelloMessage() throws Exception {
            this.mockMvc.perform(get("/"))
                    //.andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().string(containsString("Hello")));
        }


        @Test
        public void getAllShouldReturnEmptyResults() throws Exception {
            when(service.findAllClients()).thenReturn(new LinkedList<Client>());
            this.mockMvc.perform(get("/clients"))
                    //.andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().json("[]"));
        }

        @Test
        public void getAllClientsShouldReturnSomeResults() throws Exception {
            List<Client> expectedResult = new LinkedList<Client>();
            Client c = new Client();
            c.setId(123l);
            c.setFirstName("Bartłomej");
            c.setSecondName("Dulik");
            expectedResult.add(c);
            when(service.findAllClients()).thenReturn(expectedResult);
            this.mockMvc.perform(get("/persons"))
                    //.andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().json("[{\"id\":123,\"firstName\":\"Bartłomej\",\"secondName\":\"Dulik\"}]"));
        }



        @Test
        public void postNewPersonShouldReallyAddItToDatabase() throws Exception {
            Client c = new Client();
            c.setFirstName("Bartłomiej");
            c.setSecondName("Dulik");
            when(service.addClient(c)).thenReturn(4l);
            this.mockMvc.perform(post("/clients")
                    .content("{\"firstName\":\"Bartłomej\",\"secondName\":\"Dulik\"}")
                    .contentType("application/json"))
                    //.andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().string(containsString("Nowislaw")))
                    .andExpect(content().string(containsString("Dulik")))
                    .andExpect(content().string(containsString("\"id\":4")));
            c.setId(4l);
            verify(service).addClient(c);
        }




}
