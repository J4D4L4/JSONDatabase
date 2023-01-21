package server.Commands;

import server.BusinessObject;
import server.DataAccessObject;

public class DeleteCommand implements Command{

    DataAccessObject dao;
    public DeleteCommand(DataAccessObject dao){
        this.dao = dao;
    }

    @Override
    public void execute(BusinessObject i) {
        dao.delete(i);

    }
}