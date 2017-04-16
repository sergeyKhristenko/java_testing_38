package stqa.mantis.appmanager;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import stqa.mantis.model.UserData;
import stqa.mantis.model.Users;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

public class DbHelper {

  private final SessionFactory sessionFactory;

  public DbHelper() {
    // A SessionFactory is set up once for an application!
    final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure() // configures settings from hibernate.cfg.xml
            .build();
    sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
  }

  public Users users() {
    Session session = sessionFactory.openSession();
    session.beginTransaction();
    //will get all users except admins
    List<UserData> result = session.createQuery("from UserData where access_level < 90").list();

    session.getTransaction().commit();
    session.close();

    return new Users(result);
  }

}
