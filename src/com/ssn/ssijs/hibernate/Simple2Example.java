/*
 * Copyright (c) 2018 SSI Schaefer Noell GmbH
 *
 * $Header: $
 */

package com.ssn.ssijs.hibernate;

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

public class Simple2Example {

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
    Simple2Example app = new Simple2Example();
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
    List result = session.createQuery("from Person").list();

    for (Person p : (List<Person>) result) {
      System.out.println(p);
    }
    // session.getTransaction().commit();
    session.close();
  }

  private void insert() {
    Session session = sessionFactory.openSession();
    session.beginTransaction();
    session.save(new Person("rst", 22));
    session.save(new Person("ddi", 21));
    session.getTransaction().commit();
    session.close();
  }
}
