
# README - Gestion d'équipes de projet

Ce projet vise à créer un système pour gérer les équipes de devs d'un éditeur de logiciels.

Les routes Postman de l'api sont disponible dans le dossier Postman Export.

## Prérequis
Pour exécuter ce projet, vous aurez besoin de :

* JDK 21 ou plus récent
* Maven 3.6.0 ou plus récent
* MySQL Server (ou tout autre base de données compatible avec Hibernate)

## Configuration de la Base de Données
* Créez une base de données MySQL pour le projet.
* Configurez les propriétés de connexion à votre base de données dans le fichier src/main/resources/hibernate.cfg.xml.
  ```<property name="connection.url">jdbc:mysql://localhost:3306/project</property>```

```<property name="connection.username">root</property>```
```<property name="connection.password">root</property>```
* Modifier la ligne hbm2ddl.auto à "CREATE" pour migrer les données

## Démarrage de l'Application
Construisez l'application avec Maven :
mvn clean install

## Test
mvn test

## Exemple Routes Api
Obtenir tous les projets en cours
* GET {{baseUrl}}/project/getAllInProgress
  Déterminer le prochain projet à démarrer
* GET {{baseUrl}}/project/determineNextProject
  Obtenir tous les projets terminés
* GET {{baseUrl}}/project/getAllFinished
  Obtenir tous les projets planifiés
* GET {{baseUrl}}/project/getAllPlanned
  Créer un projet
* POST {{baseUrl}}/project/create
  Démarrer un projet
* PUT {{baseUrl}}/project/start/Projet innovation
  Terminer un projet
* PUT {{baseUrl}}/project/finish/Projet innovation
  Annuler un projet
* PUT {{baseUrl}}/project/cancel/Projet innovation
  Obtenir les technologies d'un projet par nom de projet
* GET {{baseUrl}}/project/getTechnologysByProject/Projet innovation
  Compétences
  Obtenir les compétences d'un utilisateur par mail ou nom
* GET {{baseUrl}}/skills/user/ben3
  Obtenir les développeurs par niveau de compétences
* GET {{baseUrl}}/skill/searchByTechnoAndLevel/python/EXPERIENCED
  Ajouter ou mettre à jour une compétence de développeur
* POST http://localhost:7071/skill/addOrUpdateSkill/tmy/python/3
  Équipes
  Ajouter un utilisateur à une équipe
* POST {{baseUrl}}/team/addMember
  Transférer un développeur dans une autre équipe
* POST {{baseUrl}}/team/transfertDevelopper/ben/12
  Créer une équipe
* POST {{baseUrl}}/team/createTeam/nameteam2/open/projet1
  Obtenir toutes les équipes
* GET {{baseUrl}}/team/getAllTeams
  Obtenir tous les membres d'une équipe avec ID d'équipe
* GET {{baseUrl}}/team/getAllTeamMember/10
  Utilisateurs
  Obtenir un utilisateur par nom
* GET {{baseUrl}}/user/name/ben3
  Obtenir un utilisateur par son mail
* GET {{baseUrl}}/user/email/ben@gmail.com
  Obtenir tous les utilisateurs
* GET {{baseUrl}}/users
  Supprimer un utilisateur par mail
* DELETE {{baseUrl}}/user/delete/ben/ben@gmail.com
  Mettre à jour un utilisateur
* PUT {{baseUrl}}/user/update/ben2@gmail.com
  Créer un utilisateur
* POST {{baseUrl}}/user/create
  Utilisateurs disponibles
* GET {{baseUrl}}/skill/findAvailableDevelopers
  Obtenir le CV d'un développeur
* GET {{baseUrl}}/user/getDeveloperCv/ben 