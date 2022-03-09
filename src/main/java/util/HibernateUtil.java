package util;

import entity.CustomEntity;

import entity.DepartmentEntity;
import entity.PersonEntity;
import entity.ProfessorEntity;
import exception.EntityForeignKeyViolation;
import exception.EntityForeignKeysAlreadyExist;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;

import javax.persistence.EntityNotFoundException;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceException;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import static util.ManagamentUtil.*;

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

    public void create(CustomEntity<?> entity) {

        this.session = this.sessionFactory.openSession();
        this.session.beginTransaction();
        this.session.save(entity);
        this.session.flush();
        this.session.close();
        showSuccessfullyEntityCreated(entity.name());
    }

    public List<ProfessorEntity> getProfessorsByDepartment(int departmentId) {
        this.session = this.sessionFactory.openSession();
        Query<ProfessorEntity> query = this.session.createQuery("from " + ProfessorEntity.class.getSimpleName() + " e where e."+  "departmentId" + "=:param ", ProfessorEntity.class);
        query.setParameter("param",departmentId);
        this.session.beginTransaction();
        List<ProfessorEntity> queryList = query.list();
        this.session.flush();
        this.session.close();
        return queryList;
    }

    // Nos aseguramos de que las fk existan
    public void createProfessor(ProfessorEntity professor) throws EntityForeignKeysAlreadyExist {
        CustomEntity<ProfessorEntity> professorByPersonId = (CustomEntity<ProfessorEntity>) this.findByField(professor, "personId", String.valueOf(professor.getPersonId()));
        CustomEntity<ProfessorEntity> professorByDeptId = (CustomEntity<ProfessorEntity>) this.findByField(professor, "departmentId", String.valueOf(professor.getDepartmentId()));

        if(null != professorByPersonId && (professorByPersonId.getId() == professorByDeptId.getId())) throw new EntityForeignKeysAlreadyExist();
        this.create(professor);
    }

    public void update(CustomEntity<?> entity) throws EntityNotFoundException, EntityForeignKeyViolation {
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
        catch(PersistenceException e) {
            throw new EntityForeignKeyViolation();
        }

        showSuccessfullyEntityUpdated(entity.name());
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
        Query<?> query = this.session.createQuery("from " + entity.getClass().getSimpleName() + " e where e."+  field + "=:param ", entity.getClass());
        if (fieldValue.matches("\\d+"))
         {
             int intValue = Integer.parseInt(fieldValue);
             query.setParameter("param", intValue);
         }
         else {
            query.setParameter("param", fieldValue);
        }
        CustomEntity<?> retrievedEntity = (CustomEntity<?>) query.uniqueResult();
        this.session.flush();
        this.session.close();
        return retrievedEntity;
    }


    public <T> List<CustomEntity<T>> findAll(Class<CustomEntity<T>> baseClass) {
        this.session = this.sessionFactory.openSession();
        this.session.beginTransaction();
        Query<CustomEntity<T>> query = this.session.<T>createQuery("FROM " + baseClass.getSimpleName());
        List<CustomEntity<T>> resultList = query.list();
        return resultList;
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
