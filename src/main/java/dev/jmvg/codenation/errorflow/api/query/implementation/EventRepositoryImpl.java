package dev.jmvg.codenation.errorflow.api.query.implementation;

import dev.jmvg.codenation.errorflow.api.filter.EventFilter;
import dev.jmvg.codenation.errorflow.api.model.Event;
import dev.jmvg.codenation.errorflow.api.model.Event_;
import dev.jmvg.codenation.errorflow.api.query.EventRepositoryQuery;
import dev.jmvg.codenation.errorflow.api.utils.EnumConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Component
public class EventRepositoryImpl implements EventRepositoryQuery {

    @PersistenceContext
    private EntityManager manager;

    EnumConverter converter = new EnumConverter();

    @Override
    public List<Event> filter(EventFilter eventFilter) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Event> criteria = builder.createQuery(Event.class);

        Root<Event> root = criteria.from(Event.class);
        Predicate[] predicates = createRestrictions(eventFilter, builder, root);

        criteria.where(predicates);

        TypedQuery<Event> query = manager.createQuery(criteria);

        return query.getResultList();
    }

    private Predicate[] createRestrictions(EventFilter eventFilter,
                                           CriteriaBuilder builder, Root<Event> root) {
        List<Predicate> predicates = new ArrayList<>();

        if(!StringUtils.isEmpty(eventFilter.getDescription())){
            predicates.add(builder.like(
                    builder.lower(root.get(Event_.description)),  "%" + eventFilter.getDescription().toLowerCase() + "%"
            ));
        }
        if(eventFilter.getLevel() != null) {
            predicates.add(
                    builder.equal(root.get(Event_.level), converter.convert(eventFilter.getLevel().toString())
            ));
        }
        if(!StringUtils.isEmpty(eventFilter.getOrigin())){
            predicates.add(builder.like(
               builder.lower(root.get(Event_.origin)), "%" + eventFilter.getOrigin().toLowerCase() + "%"
            ));
        }
        if(eventFilter.getEventDateFrom() != null){
            predicates.add(
                    builder.greaterThanOrEqualTo(root.get(Event_.createdAt), eventFilter.getEventDateFrom())
            );
        }
        if(eventFilter.getEventDateTo() != null){
            predicates.add(
                    builder.lessThanOrEqualTo(root.get(Event_.createdAt), eventFilter.getEventDateTo())
            );
        }
        return predicates.toArray(new Predicate[predicates.size()]);
    }
}
