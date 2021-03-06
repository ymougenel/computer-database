package com.excilys.database.ui.commands;

import java.sql.SQLException;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;

import com.excilys.database.entities.Company;
import com.excilys.database.entities.Computer;
import com.excilys.database.persistence.ComputerDaoInterface;
import com.excilys.database.persistence.DatabaseConnection;

public class InsertComputer extends CommandBD {
    private Computer computer;
    @Autowired
    private ComputerDaoInterface computerDAO;
    public InsertComputer() {
        this.name = "Insert computer";
        this.shortcut = "ic";
    }

    public InsertComputer(String s) {
        super(s);
        this.name = "Insert computer";
    }

    @Override
    public void execute(DatabaseConnection bdr) throws SQLException {
        computerDAO.create(computer);

    }

    @Override
    public boolean optionsFit(String[] values) {
        if (!(values[0].equals(this.shortcut))) {
            return false;
        }
        System.out.println("Insertcompt launched");
        computer = new Computer();
        for (int i = 1; i < values.length; i += 2) {
            switch (values[i]) {
                case ("-name"):
                    computer.setName(values[i + 1]);
                break;
                case ("-id"):
                    computer.setId(Long.parseLong(values[i + 1]));
                break;
                case ("-introduced"):
                    computer.setIntroduced(LocalDate.parse(values[i + 1]));
                break;
                case ("-discontinued"):
                    computer.setDiscontinued(LocalDate.parse(values[i + 1]));
                break;
                case ("-company"):
                    Company c = new Company();
                c.setId(Long.parseLong(values[i + 1]));
                computer.setCompany(c);
                break;
                default:
                    System.err.println("Error No matching found for:" + values[i]);
                    break;
            }
        }
        return true;
    }

}