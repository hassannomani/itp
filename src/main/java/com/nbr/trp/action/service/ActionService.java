package com.nbr.trp.action.service;

import com.nbr.trp.action.entity.Action;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ActionService {

    public Action save(Action action);

    public Action getAction(String id);

    public Action getActionAgent(String tin);

    public List<Action> getActionTrp(String tin);

    public Action getActionAgentWithTime(String tin);

    public Action getActionTrpWithTime(String tin);

    public Action markRead(String id);

    public Action getActionByTypeAndTin(String type, String tin);

    public List<Action> getActionByType(String type);

}
