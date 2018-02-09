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

public class Main3rdHibernateExample3 {

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
    Main3rdHibernateExample3 app = new Main3rdHibernateExample3();
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

    for (Event e : (List<Event>) result) {
      System.out.println(e);
    }

    // session.getTransaction().commit();
    session.close();

  }

  private void insert() {
    Session session = sessionFactory.openSession();
    session.beginTransaction();
    Person rst = new Person("rst", 22);
    Person ddi = new Person("ddi", 21);

    Event ev1 = new Event("Our very first event!", new Date());
    Event ev2 = new Event("A follow up event", new Date());

    session.save(rst);
    session.save(ddi);

    session.save(ev1);
    session.save(ev2);

    rst.setEvent(ev1);
    ddi.setEvent(ev2);

    session.getTransaction().commit();
    session.close();
  }
}
