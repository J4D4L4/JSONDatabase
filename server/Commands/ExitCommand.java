package server.Commands;

import server.BusinessObject;
import server.DataAccessObject;

public class ExitCommand implements Command {
    DataAccessObject dao;
    public ExitCommand(DataAccessObject dao){
        this.dao = dao;
    }

    @Override
    public BusinessObject execute(BusinessObject i) {

        System.exit(0);

        return i;
    }
}
