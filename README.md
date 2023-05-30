# Hibernate 6 migration L2 cache issue example

This repository contains the code to reproduce an issue with the L2 cache, when I try to get a lazy fetch field
(`@ManyToOne`) which is part of a composite id (using and `@IdClass`) when the object is store in L2

see [HHH-16673](https://hibernate.atlassian.net/browse/HHH-16673) for details.