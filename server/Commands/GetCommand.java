package server.Commands;

import server.BusinessObject;
import server.DataAccessObject;
import server.SingleDB;

public class GetCommand implements Command{
    private DataAccessObject dao;

    public  GetCommand(DataAccessObject dao){
        this.dao = dao;
    }
    @Override
    public BusinessObject execute(BusinessObject buisnessObjectin) {
        //dao.get(i);
        //BusinessObject toGet = new SingleDB(i.getId(),"");
        BusinessObject bo = this.dao.get(buisnessObjectin);
        //BusinessObject bo = dao.ds.getData().get(i.getId());
        return bo;
    }
}
