package com.hhi.ees.platform.Entities;

import javax.persistence.*;
import java.util.Date;

@Entity
public class QueryEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name="query", length=1024)
    private String query;

    @Temporal(TemporalType.DATE)
    private Date dateQuery;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getQuery() {
        return query;
    }
    public void setQuery(String query) {
        this.query = query;
    }

    public Date getDateQuery() {
        return dateQuery;
    }
    public void setDateQuery(Date dateQuery) {
        this.dateQuery = dateQuery;
    }

    @Override
    public String toString() {
        return "QueryEntity{" +
                "id=" + id +
                ", query='" + query + '\'' +
                ", dateQuery=" + dateQuery +
                '}';
    }
}
