package com.nbr.trp.action.service;

import com.nbr.trp.action.entity.Action;
import com.nbr.trp.action.repository.ActionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ActionServiceImpl implements ActionService{

    @Autowired
    ActionRepository actionRepository;

    @Override
    public Action save(Action action) {
       return actionRepository.save(action);
    }

    @Override
    public Action getAction(String id) {
        return actionRepository.findByActionId(id);
    }

    @Override
    public Action getActionAgent(String tin) {
        return null;
    }

    @Override
    public List<Action> getActionTrp(String tin) {

        return actionRepository.findByReceiverOrderByActionSentDesc(tin);
    }

    @Override
    public Action getActionAgentWithTime(String tin) {
        return null;
    }

    @Override
    public Action getActionTrpWithTime(String tin) {
        return null;
    }

    public Action markRead(String id){
        Action action = actionRepository.findByActionId(id);
        action.setActionRead(new Date());
        return actionRepository.save(action);
    }

    public Action getActionByTypeAndTin(String type, String tin){
        Action action = actionRepository.findActionWithTypeAndTin(type, tin);
        return action;
    }

    public List<Action> getActionByType(String type){
        List<Action> ls = actionRepository.findByActionType(type);
        return ls;
    }
}
