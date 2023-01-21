package server.Commands;

import server.BusinessObject;
import server.DataAccessObject;

public class GetCommand implements Command{
    private DataAccessObject dao;

    public  GetCommand(DataAccessObject dao){
        this.dao =dao;
    }
    @Override
    public BusinessObject execute(BusinessObject i) {
        //dao.get(i);
        BusinessObject bo = dao.get(i);
        return bo;
    }
}
