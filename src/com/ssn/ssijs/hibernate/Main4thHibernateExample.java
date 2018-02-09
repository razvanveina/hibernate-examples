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

public class Main4thHibernateExample {

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
    Main4thHibernateExample app = new Main4thHibernateExample();
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
    List result = session.createQuery("from Course").list();

    for (Course c : (List<Course>) result) {
      System.out.println(c);
    }

    session.close();

  }

  private void insert() {
    Session session = sessionFactory.openSession();
    session.beginTransaction();
    Person rst = new Person("rst", 22);
    Person ddi = new Person("ddi", 21);

    Course c1 = new Course("Java");
    Course c2 = new Course("SQL");

    session.save(rst);
    session.save(ddi);

    session.save(c1);
    session.save(c2);

    //    rst.getCourses().add(c1);
    //    rst.getCourses().add(c2);
    //    ddi.getCourses().add(c1);

    c1.getPersons().add(rst);
    c1.getPersons().add(ddi);
    c2.getPersons().add(rst);

    session.getTransaction().commit();
    session.close();
  }
}
