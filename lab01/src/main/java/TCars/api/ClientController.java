package TCars.api;

import TCars.domain.Client;
import TCars.service.GarageManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

@RestController
public class ClientController {


    @Autowired
    GarageManager garageManager;

    @RequestMapping("/clients")
    public List<Client> getClients() {
        List<Client> clients = new LinkedList<>();
        for (Client c: garageManager.findAllClients()){
            clients.add(c.clone());
        }
        return clients;
    }

    @RequestMapping(value = "/clients",method = RequestMethod.POST)
    public Client addClient(@RequestBody Client nclient) {
        nclient.setId(garageManager.addClient(nclient));
        return nclient;
    }

    @RequestMapping(value = "/clients",method = RequestMethod.PUT)
    public Client updateClient(@RequestBody Client nclient) {
        garageManager.updateClient(nclient);
        return nclient;
    }


    @RequestMapping("/")
    public String index() {
        return "Hello World";
    }

    @RequestMapping(value = "/clients/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Client getClient(@PathVariable("id") Long id) throws SQLException {
        return garageManager.findClientById(id).clone();
    }

    @RequestMapping(value = "/clients", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Client> getPersons(@RequestParam(value = "filter", required = false) String f) throws SQLException {
        List<Client> clients = new LinkedList<Client>();
        for (Client p : garageManager.findAllClients()) {
            if (f == null) {
                clients.add(p.clone());
            } else if (p.getFirstName().contains(f)) {
                clients.add(p);
            }
        }
        return clients;
    }

    @RequestMapping(value = "/clients/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteClient(@PathVariable("id") Long id) throws SQLException {
        garageManager.deleteClient(garageManager.findClientById(id));
        return "OK";
    }



}
