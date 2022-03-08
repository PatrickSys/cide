package util;

import entity.CustomEntity;

import entity.DepartmentEntity;
import entity.PersonEntity;
import entity.ProfessorEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;

import javax.persistence.EntityNotFoundException;
import javax.persistence.OptimisticLockException;

import static util.ManagamentUtil.showSuccessfullyEntityDeleted;

/************************************************************************
 Made by        PatrickSys
 Date           03/02/2022
 Package        services
 Description:
 ************************************************************************/


public class HibernateUtil {

    Configuration configuration;
    ServiceRegistry serviceRegistry;
    SessionFactory sessionFactory;
    Session session;


    public HibernateUtil() {
        this.initializeConfig();
    }

    public void create(CustomEntity<?> object) {

        this.session = this.sessionFactory.openSession();
        this.session.beginTransaction();
        this.session.persist(object);
        this.session.flush();
        this.session.close();

    }

    public void update(CustomEntity<?> entity) throws EntityNotFoundException {
        this.session = this.sessionFactory.openSession();
        this.session.beginTransaction();
        this.session.update(entity);
        try {
            this.session.flush();
        }
        catch (OptimisticLockException e) {
            this.session.close();
            throw new EntityNotFoundException(entity.name());
        }
        this.session.close();
        }

    public void delete(CustomEntity<?> entity) throws EntityNotFoundException {
        this.session = this.sessionFactory.openSession();
        this.session.beginTransaction();
        this.session.delete(entity);
        try {
            this.session.flush();
        }
        catch (OptimisticLockException e) {
            this.session.close();
            throw new EntityNotFoundException(entity.name());
        }
        showSuccessfullyEntityDeleted(entity.name());
        this.session.close();
    }


    public CustomEntity<?> findById(int id, CustomEntity<?> entity) {
        this.session = this.sessionFactory.openSession();
        this.session.beginTransaction();
        CustomEntity<?> retrievedEntity = this.session.get(entity.getClass(), id);
        this.session.flush();
        this.session.close();

        if(null == retrievedEntity) {
            throw new EntityNotFoundException(entity.name());
        }
        return retrievedEntity;
    }

    public CustomEntity<?> findByField(CustomEntity<?> entity, String field, String fieldValue) {
        this.session = this.sessionFactory.openSession();
        this.session.beginTransaction();
        Query<?> query = this.session.createQuery("from " + entity.getClass().getName() + " e where e."+  field + "=:param ", entity.getClass());
        if (fieldValue.matches("\\d+"))
         {
             int intValue = Integer.parseInt(fieldValue);
             query.setParameter("param", intValue);
         }

         else {
            query.setParameter("param", fieldValue);
        }
        System.out.println(query);
        CustomEntity<?> retrievedEntity = (CustomEntity<?>) query.uniqueResult();
        System.out.println(retrievedEntity);
        this.session.flush();
        this.session.close();
        return retrievedEntity;
    }

    private void initializeConfig() {

        if (this.sessionFactory == null) {
            // loads configuration and mappings
            this.configuration = new Configuration().configure();
            this.configuration.addAnnotatedClass(DepartmentEntity.class);
            this.configuration.addAnnotatedClass(PersonEntity.class);
            this.configuration.addAnnotatedClass(ProfessorEntity.class);

            this.serviceRegistry
                    = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();

            // builds a session factory from the service registry
            this.sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        }


        this.session = this.sessionFactory.openSession();

    }



}
