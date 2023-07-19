package ru.practicum.main_service.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import ru.practicum.main_service.model.EventState;
import ru.practicum.main_service.model.entity.EventEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.List;

public class CustomEventRepositoryImpl implements CustomEventRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<EventEntity> getFilteredUserEvents(String search, List<Long> categories, Boolean paid,
                                                   LocalDateTime startDate, LocalDateTime endDate,
                                                   boolean onlyAvailable, Pageable pageable) {

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<EventEntity> query = builder.createQuery(EventEntity.class);
        Root<EventEntity> event = query.from(EventEntity.class);
        Predicate criteria = builder.conjunction();

        criteria = builder.and(criteria, builder.equal(event.get("state"), EventState.PUBLISHED));

        if (search != null && !search.isBlank()) {
            String searchExpression = "%" + search.toLowerCase() + "%";
            Predicate searchPredicate = builder.or(
                    builder.like(builder.lower(event.get("annotation")), searchExpression),
                    builder.like(builder.lower(event.get("description")), searchExpression)
            );
            criteria = builder.and(criteria, searchPredicate);
        }

        if (categories != null && !categories.isEmpty()) {
            criteria = builder.and(criteria, event.get("category").get("id").in(categories));
        }

        if (paid != null) {
            criteria = builder.and(criteria, builder.equal(event.get("paid"), paid));
        }

        if (startDate != null) {
            criteria = builder.and(criteria, builder.greaterThanOrEqualTo(event.get("eventDate"), startDate));
        }

        if (endDate != null) {
            criteria = builder.and(criteria, builder.lessThan(event.get("eventDate"), endDate));
        } else if (startDate == null) {
            criteria = builder.and(criteria, builder.greaterThan(event.get("eventDate"), LocalDateTime.now()));
        }

        if (onlyAvailable) {
            criteria = builder.and(criteria, builder.lessThan(event.get("confirmedRequests"),
                    event.get("participantLimit")));
        }

        return executeQuery(pageable, query, event, criteria);
    }

    @Override
    public Page<EventEntity> getFilteredAdminEvents(List<Long> users, List<EventState> states, List<Long> categories,
                                                    LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<EventEntity> query = builder.createQuery(EventEntity.class);
        Root<EventEntity> event = query.from(EventEntity.class);
        Predicate criteria = builder.conjunction();

        if (users != null && !users.isEmpty()) {
            criteria = builder.and(criteria, event.get("initiator").get("id").in(users));
        }

        if (states != null && !states.isEmpty()) {
            criteria = builder.and(criteria, event.get("state").in(states));
        }

        if (categories != null && !categories.isEmpty()) {
            criteria = builder.and(criteria, event.get("category").get("id").in(categories));
        }

        if (startDate != null) {
            criteria = builder.and(criteria, builder.greaterThanOrEqualTo(event.get("eventDate"), startDate));
        }

        if (endDate != null) {
            criteria = builder.and(criteria, builder.lessThan(event.get("eventDate"), endDate));
        }

        return executeQuery(pageable, query, event, criteria);
    }

    private Page<EventEntity> executeQuery(Pageable pageable, CriteriaQuery<EventEntity> query, Root<EventEntity> event, Predicate criteria) {
        query.select(event).where(criteria);
        TypedQuery<EventEntity> typedQuery = entityManager.createQuery(query);
        int pageSize = pageable.getPageSize();
        typedQuery.setFirstResult(pageable.getPageNumber() * pageSize);
        typedQuery.setMaxResults(pageSize);
        List<EventEntity> events = typedQuery.getResultList();
        long count = countTotalEvents(criteria);
        return new PageImpl<>(events, pageable, count);
    }

    private long countTotalEvents(Predicate criteria) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = builder.createQuery(Long.class);
        Root<EventEntity> countRoot = countQuery.from(EventEntity.class);
        countQuery.select(builder.count(countRoot)).where(criteria);
        return entityManager.createQuery(countQuery).getSingleResult();
    }
}
