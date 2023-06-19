# Hibernate 6 migration issues

This repository contains the code to reproduce issues encountered during my hibernate 5 to 6 migration.

1. An issue with the L2 cache, when I try to get a lazy fetch field
   (`@ManyToOne`) which is part of a composite id (using an `@IdClass`) when the object is store in L2.
    - see [HHH-16673](https://hibernate.atlassian.net/browse/HHH-16673) for details.
    - fixed in `6.2.5.Final`

2. An issue that occurs when I try to delete an entity (or all entities) that contain an embedded with a many-to-many
   association.
    - see [HHH-16810](https://hibernate.atlassian.net/browse/HHH-16810) for details.

3. An issue that occurs when I try to delete an entity that have a composite id declared in an `@IdClass`
   which has one of its fields is mapped from the primary key of a `@ManyToOne` association.
    - see [HHH-16821](https://hibernate.atlassian.net/browse/HHH-16821) for details.