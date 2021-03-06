package dev.jmvg.codenation.errorflow.api.filter.query.implementation;

import dev.jmvg.codenation.errorflow.api.filter.EventFilter;
import dev.jmvg.codenation.errorflow.api.model.Event;
import dev.jmvg.codenation.errorflow.api.filter.query.EventRepositoryQuery;
import dev.jmvg.codenation.errorflow.api.model.Event_;
import dev.jmvg.codenation.errorflow.api.utils.EnumConverter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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

    @Override
    public Page<Event> filter(EventFilter eventFilter, Pageable pageable) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Event> criteria = builder.createQuery(Event.class);

        Root<Event> root = criteria.from(Event.class);
        Predicate[] predicates = createRestrictions(eventFilter, builder, root);

        criteria.where(predicates);

        TypedQuery<Event> query = manager.createQuery(criteria);

        addRestrictionPaginationQuery(query, pageable);

        return new PageImpl<>(query.getResultList(), pageable, totalResults(eventFilter));
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
                    builder.equal(root.get(Event_.level), new EnumConverter().convert(eventFilter.getLevel().toString())
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

    private void addRestrictionPaginationQuery(TypedQuery<Event> query, Pageable pageable) {
        int atualPage = pageable.getPageNumber();
        int totalResultsCount = pageable.getPageSize();
        int firstResultPage = atualPage * totalResultsCount;

        query.setFirstResult(firstResultPage);
        query.setMaxResults(totalResultsCount);

    }

    private Long totalResults(EventFilter eventFilter) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);

        Root<Event> root = criteria.from(Event.class);
        Predicate[] predicates = createRestrictions(eventFilter, builder, root);

        criteria.where(predicates);
        criteria.select(builder.count(root));

        return manager.createQuery(criteria).getSingleResult();
    }
}
