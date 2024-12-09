package com.nbr.trp.action.repository;

import com.nbr.trp.action.entity.Action;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface ActionRepository extends JpaRepository<Action, String> {

    Action save(Action action);

    Action findByActionId(String id);

    List<Action> findBySender(String tin);

    List<Action> findByReceiverOrderByActionSentDesc(String tin);

    List<Action> findByActionType(String type);
    @Query(value = "select top 1 * from action where receiver= :tin and action_type= :type order by action_sent desc",nativeQuery = true)
    Action findActionWithTypeAndTin(@Param("type") String type, @Param("tin") String tin);

}
