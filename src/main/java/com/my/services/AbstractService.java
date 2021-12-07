package com.my.services;

import com.my.db.entity.Account;
import com.my.db.entity.Entity;
import com.my.db.managers.Manager;
import com.my.util.StackTrace;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractService <T extends Entity> {
    protected abstract Manager<T> getManager();
    protected static final Logger log = Logger.getLogger(AbstractService.class);

    private final Manager<T> manager = getManager();

    protected T abstractGet(long id) {
        try {
            return manager.get(id);
        } catch (SQLException e) {
            log.error(e.getMessage()); log.error(StackTrace.getStackTrace(e));
            return null;
        }
    }

    protected List<T> abstractSearch(String find) {
        try {
            return manager.search(find);
        } catch (SQLException e) {
            log.error(e); log.error(StackTrace.getStackTrace(e));
            return new ArrayList<>();
        }
    }

    protected List<T> abstractGetAll() {
        try {
            return manager.getAll();
        } catch (SQLException e) {
            log.error(e); log.error(StackTrace.getStackTrace(e));
            return new ArrayList<>();
        }
    }

    protected List<T> abstractGetAll(Comparator<T> comparator) {
        try {
            List<T> list = manager.getAll();
            return  list.stream()
                    .sorted(comparator)
                    .collect(Collectors.toList());
        } catch (SQLException e) {
            log.error(e); log.error(StackTrace.getStackTrace(e));
            return new ArrayList<>();
        }
    }

    protected boolean abstractInsert(T entity) {
        try {
            manager.insert(entity);
            return true;
        } catch (SQLException e) {
            log.error(e); log.error(StackTrace.getStackTrace(e));
            return false;
        }
    }

    protected boolean abstractUpdate(T entity) {
        try {
            manager.update(entity);
            return true;
        } catch (SQLException e) {
            log.error(e); log.error(StackTrace.getStackTrace(e));
            return false;
        }
    }
}
