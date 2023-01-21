package server.Commands;

import server.BusinessObject;

/**
 *
 */

public interface Command {
    BusinessObject execute(BusinessObject i);
}