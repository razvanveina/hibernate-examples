/*
 * Copyright (c) 2018 SSI Schaefer Noell GmbH
 *
 * $Header: $
 */

package com.ssn.ssijs.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

/**
 * @author <a href="mailto:rveina@ssi-schaefer-noell.com">rveina</a>
 * @version $Revision: $, $Date: $, $Author: $
 */

public class Main5thHibernateExample {

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
    Main5thHibernateExample app = new Main5thHibernateExample();
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
    Query query = session.createQuery("from Person where name=:name");
    query.setParameter("name", "rst");
    Person uniqueResult = (Person) query.uniqueResult();

    System.out.println(uniqueResult);

    session.close();
  }

  private void insert() {
    Session session = sessionFactory.openSession();
    session.beginTransaction();
    Person rst = new Person("rst", 22);
    Person ddi = new Person("ddi", 21);

    session.save(rst);
    session.save(ddi);

    session.getTransaction().commit();
    session.close();
  }
}
