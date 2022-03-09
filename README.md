"# cide" 
Proyecto para similar un CRUD de personas, profesores y departamentos
He usado JPA y su implementación con Hibernate, intentando usar al máximo las sentencias DML 

A la hora usar JPA/Hibernate, he encontrado que la configuración para con la bbdd ha sido algo complicada, sin embargo tras
esos 10 minutos de configuración, he generado las Entidades de Java a partir de la bddd, usando el "database first" approach de Hibernate

Como JPA se basa en genéricos, he empleado esta estrategia y he visto que no es tan fácil como parece, por ejemplo a la hora de "castear" una lista de 
la clase Query de Hibernate a la implementación de la Interface CustomEntity como pertoque

Las ventajas que he encontrado en cuanto al uso de JPA/Hibernate:
    -Aporta una enorme facilidad de extender tus clases y métodos principales (save, saveOrUpdate, get, update y delete) con apenas ningún cambio
    -Claro que como desventaja, está el uso de genéricos y la dificultad que conlleva, para poder aprovechar al 100% los usos de JPA/Hibernate
    -Frente a otras herramientas como JDBC, aporta una abstracción enorme con la que sólo tendrás que preocuparte de aportar unas pocas queries personalizadas
    -La configuración es muy extensible, puedes hacer que cualquier cambio de la bddd se refleje automáticamente en la Entidad
    -En general, destacaría las herramientas ORM que ofrece hibernate, pues facilita y agiliza enormemente la persistencia en bddd de nuestro programa
    -Hibernate automáticamente guarda caché sobre la persistencia actual, mejorando la eficiencia

Desventajas del uso de JPA/Hibernate:
    -Para aprovecharlo al máximo, hay que usar genéricos y eso es ciertamente complicado si no estás acostumbrado
    -Configurar hibernate las primeras veces puede ser tedioso y caótico
    -Al proveer abstracción, ante un error puedes verte "perdido" pues no sabes exactamente qué está pasando
    -Ciertas consultas no son todo lo eficiente que podrían ser, por ejemplo los joins de HQL son o(n) y no o(1) (por eso en mi caso he hecho joins "manuales" o(1))
