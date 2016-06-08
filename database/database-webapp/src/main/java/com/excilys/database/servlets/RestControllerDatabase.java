package com.excilys.database.servlets;

import com.excilys.database.entities.Computer;
import com.excilys.database.entities.ComputerDTO;
import com.excilys.database.entities.Page;
import com.excilys.database.mapper.ComputerWrapper;
import com.excilys.database.services.ComputerServiceInterface;
import com.excilys.database.validators.ComputerValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by yann on 07/06/16.
 */
@RestController
@RequestMapping("/rest")
public class RestControllerDatabase {

    @Autowired
    private ComputerServiceInterface computerService;

    @RequestMapping(value = "/computer/", method = RequestMethod.GET)
    public ResponseEntity<List<Computer>> listAllComputers() {
        List<Computer> computers = computerService.listComputers(null, 1,10, Page.CompanyTable.NAME, Page.Order.ASC);
        if(computers.isEmpty()){
            return new ResponseEntity<List<Computer>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<Computer>>(computers, HttpStatus.OK);
    }

    @RequestMapping(value = "/computer/{id}", method = RequestMethod.GET)
    public ResponseEntity<ComputerDTO> getComputer(@PathVariable("id") long id) {
        Computer computer = computerService.findComputer(id);
        if (computer == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<ComputerDTO>(new ComputerDTO(computer), HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/create/", method = RequestMethod.POST)
    public ResponseEntity<Void> createUser(@RequestBody ComputerDTO computer, UriComponentsBuilder ucBuilder) {
        if (computer.getCompanyId()==null) {
            computer.setCompanyId("");

        }
        computer.setId("");
        List<String> errors = ComputerValidator.computerValidation(computer,false);

        if (errors.isEmpty()) {
            Computer created = this.computerService.insertComputer(ComputerWrapper.wrapToComputer(computer));

            // after a succeful POST, we need to redirect to the GET page using the new ID
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(ucBuilder.path("/computer/{id}").buildAndExpand(created.getId()).toUri());
            //TODO fix redirection
            return new ResponseEntity<>(headers, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    public ResponseEntity<ComputerDTO> updateComputer(@Valid @RequestBody ComputerDTO computer) {
        computer.setCompanyId("");
        List<String> errors = ComputerValidator.computerValidation(computer,false);

        if (errors.isEmpty()) {
            Computer created = this.computerService.updateComputer(ComputerWrapper.wrapToComputer(computer));
            return new ResponseEntity<ComputerDTO>(new ComputerDTO(created),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<ComputerDTO> deleteComputer(@PathVariable("id") long id) {
        Computer comp = new Computer();
        comp.setId(id);
        computerService.deleteComputer(comp);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
