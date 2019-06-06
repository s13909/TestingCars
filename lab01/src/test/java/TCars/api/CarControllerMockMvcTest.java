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
public class CarControllerMockMvcTest {



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

                    .andExpect(status().isOk())
                    .andExpect(content().string(containsString("Hello World")));
          }


        @Test
        public void getAllShouldReturnEmptyResults() throws Exception {
            when(service.findAllCars()).thenReturn(new LinkedList<Car>());
            this.mockMvc.perform(get("/cars"))
                    //.andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().json("[]"));
        }

        @Test
        public void getAllCarsShouldReturnSomeResults() throws Exception {
            List<Car> expectedResult = new LinkedList<Car>();
            Car c = new Car();
            c.setId(125l);
            c.setMake("Fiat");
            c.setModel("125p");
            c.setColor("Yellow");
            expectedResult.add(c);
            when(service.findAllCars()).thenReturn(expectedResult);
            this.mockMvc.perform(get("/cars"))
                    //.andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().json("[{\"id\":125,\"make\":\"Fiat\",\"model\":\"125p\",\"color\":\"Yellow\",\"client\":null}]"));
        }


        @Test
        public void postNewCarShouldReallyAddItToDatabase() throws Exception {
            Car c = new Car();
            c.setMake("Fiat");
            c.setModel("125p");
            c.setColor("Yellow");
            when(service.addCar(c)).thenReturn(0l);
            this.mockMvc.perform(post("/cars")
                        .content("{\"make\":\"Fiat\",\"model\":\"125p\",\"color\":\"Yellow\",\"client\":null}")
                        .contentType("application/json"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().string(containsString("Fiat")))
                    .andExpect(content().string(containsString("125p")))
                    .andExpect(content().string(containsString("\"id\":0")));
            c.setId(0l);
            verify(service).addCar(c);
        }




}
