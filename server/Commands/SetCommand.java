package server.Commands;

import server.BusinessObject;
import server.DataAccessObject;

public class SetCommand implements Command{
    DataAccessObject dao;
    public SetCommand(DataAccessObject dao){
        this.dao = dao;
    }

    @Override
    public void execute(BusinessObject i) {
        dao.set(i);

    }
}
