/*
 * Copyright (c) 2018 SSI Schaefer Noell GmbH
 *
 * $Header: $
 */

package com.ssn.ssijs.hibernate;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

/**
 * @author <a href="mailto:rveina@ssi-schaefer-noell.com">rveina</a>
 * @version $Revision: $, $Date: $, $Author: $
 */

public class SimpleExample {

  private SessionFactory sessionFactory;

  protected void setUp() {
    // A SessionFactory is set up once for an application!
    final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure() // configures settings from hibernate.cfg.xml
      .build();
    try {
      sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
    } catch (Exception e) {
      // The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
      // so destroy it manually.
      StandardServiceRegistryBuilder.destroy(registry);
    }
  }

  public static void main(String[] args) {
    SimpleExample app = new SimpleExample();
    app.setUp();
    app.insert();
    app.select();
    app.finish();
  }

  private void finish() {
    sessionFactory.close();
  }

  private void select() {
    Session session = sessionFactory.openSession();
    // session.beginTransaction();
    List result = session.createQuery("from Event").list();

    for (Event event : (List<Event>) result) {
      System.out.println("Event (" + event.getDate() + ") : " + event.getTitle());
    }
    // session.getTransaction().commit();
    session.close();
  }

  private void insert() {
    Session session = sessionFactory.openSession();
    session.beginTransaction();
    session.save(new Event("Our very first event!", new Date()));
    session.save(new Event("A follow up event", new Date()));
    session.getTransaction().commit();
    session.close();
  }
}
