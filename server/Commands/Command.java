package server.Commands;

import server.BusinessObject;

/**
 *
 */

public interface Command {
    void execute(BusinessObject i);
}