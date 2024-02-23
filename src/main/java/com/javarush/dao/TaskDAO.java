package com.javarush.dao;

import com.javarush.domain.Task;

import org.hibernate.query.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class TaskDAO {

    private final SessionFactory sessionFactory;

    public TaskDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public int getAllCount() {
        Query<Long> query = sessionFactory.getCurrentSession().createQuery("select count(t) from Task t", Long.class);
        return Math.toIntExact(query.uniqueResult());
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public List<Task> getAllTasks(int offset, int count) {
        Query query = sessionFactory.getCurrentSession().createQuery("from Task t");
        query.setFirstResult(offset);
        query.setMaxResults(count);
        return query.getResultList();
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public Optional<Task> findById(Integer id) {
        Task task = sessionFactory.getCurrentSession().find(Task.class, id);
        return Optional.ofNullable(task);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void saveOrUpdateTask(Task task) {
        sessionFactory.getCurrentSession().saveOrUpdate(task);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteTask(Task task){
        sessionFactory.getCurrentSession().remove(task);
    }
}
