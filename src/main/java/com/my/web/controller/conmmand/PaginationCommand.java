package com.my.web.controller.conmmand;

import com.my.db.entity.Entity;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

public abstract class PaginationCommand<T extends Entity> extends Command {
    protected List<T> setAttributes(HttpServletRequest req, List<T> entities) {
        entities = entities.stream()
                .distinct()
                .collect(Collectors.toList());

        int size = this.getSize(entities);
        int page = this.getPage(req, size);

        entities = entities
                .stream()
                .skip(page * 5L)
                .limit(5).collect(Collectors.toList());

        req.setAttribute("size", size);
        req.setAttribute("page", page);
        return entities;
    }

    protected int getPage(HttpServletRequest req, int size) {
        if (req.getParameter("page") == null) {
            return 0;
        } else {
            try {
                int page = Integer.parseInt(req.getParameter("page"));
                if (page < 0) {
                    page = 0;
                }

                if (page > size) {
                    page = size;
                }

                return page;
            } catch (NumberFormatException var3) {
                return 0;
            }
        }
    }

    protected int getSize(List<T> entities) {
        int size = entities.size();
        if (size % 5 == 0) {
            --size;
        }

        size /= 5;
        return size;
    }
}
