-- Script de création de la base de données ENCHERES
--	 type :			SQL Server 2012
--
-- 2020/11/02 ACH : ordre de création des tables remanié selon les dépendances
--					renommage des PK et FK selon les normes
--					ajout des contraintes du modèle métier
--					ajout PS de suppression d'un utilisateur


--	Procédure stockée de suppression d'un utilisateur et des articles qu'il vend

CREATE PROCEDURE sp_supprimerUtilisateur
		@no_utilisateur INTEGER	 
AS
	DELETE FROM ARTICLES_VENDUS
	WHERE no_vendeur = @no_utilisateur;

	DELETE FROM UTILISATEURS
	WHERE no_utilisateur = @no_utilisateur;
GO 



-- Table CATEGORIES

CREATE TABLE CATEGORIES (
	no_categorie	INTEGER		NOT NULL	IDENTITY(1,1),
	libelle			VARCHAR(30)	NOT NULL
);

ALTER TABLE CATEGORIES ADD CONSTRAINT PK_CATEGORIES 
	PRIMARY KEY (no_categorie);



-- Table UTILISATEURS 

CREATE TABLE UTILISATEURS (
		no_utilisateur		INTEGER		NOT NULL	IDENTITY(1,1),
																-- Contrôle unicité sur pseudo
		pseudo						VARCHAR(30)		NOT NULL	CONSTRAINT UN_UTILISATEURS_PSEUDO UNIQUE(pseudo),
		nom								VARCHAR(30)		NOT NULL,
		prenom						VARCHAR(30)		NOT NULL,
																-- Contrôle unicité sur e-mail
		email							VARCHAR(20)		NOT NULL	CONSTRAINT UN_UTILISATEURS_EMAIL UNIQUE(email),
		telephone					CHAR(10),
		rue								VARCHAR(30)		NOT NULL,
		code_postal				CHAR(5)				NOT NULL,
		ville							VARCHAR(30)		NOT NULL,
		mot_de_passe			VARCHAR(50)		NOT NULL,
																		-- Valeur par défaut du crédit à 0 et vérif valeur positive		
		credit						INTEGER				NOT NULL	CONSTRAINT DF_UTILISATEURS_CREDIT DEFAULT 0,
																							CONSTRAINT CK_UTILISATEURS_CREDIT CHECK(credit >= 0),
													-- Valeur par défaut "n'est pas admin"
		administrateur	 	BIT 					NOT NULL	CONSTRAINT DF_UTILISATEURS_ADMIN DEFAULT 0,
);

ALTER TABLE UTILISATEURS ADD CONSTRAINT PK_UTILISATEURS
	PRIMARY KEY (no_utilisateur);



-- Table ARTICLES_VENDUS

CREATE TABLE ARTICLES_VENDUS (
		no_article						INTEGER 			NOT NULL	IDENTITY(1,1),
		nom_article						VARCHAR(30)		NOT NULL,
		description						VARCHAR(300)	NOT NULL,
		date_debut_encheres		DATETIME			NOT NULL,
		date_fin_encheres			DATETIME			NOT NULL,
		prix_initial					INTEGER				NOT NULL,
		prix_vente						INTEGER				NOT NULL,
		chemin_photo          VARCHAR(300)  NULL,
		-- un seul no_utilisateur n'est pas suffisant pour gérer acheteur et vendeur
		no_vendeur						INTEGER				NOT NULL,
		-- la cardinalité de "achete" ARTICLES => UTILISATEURS est 0..1 et non 1 
		no_acheteur						INTEGER				NULL,
		no_categorie					INTEGER				NOT NULL
);

ALTER TABLE ARTICLES_VENDUS ADD CONSTRAINT PK_ARTICLES_VENDUS
	PRIMARY KEY (no_article);

ALTER TABLE ARTICLES_VENDUS ADD CONSTRAINT FK_ARTICLES_CATEGORIES
	FOREIGN KEY ( no_categorie ) REFERENCES CATEGORIES ( no_categorie )
	ON DELETE no action 
	ON UPDATE no action; 

ALTER TABLE ARTICLES_VENDUS ADD CONSTRAINT FK_ARTICLES_UTILISATEURS_VEND
	FOREIGN KEY ( no_vendeur ) REFERENCES UTILISATEURS ( no_utilisateur )
	--Si un utlisateur est supprimé, ses articles à vendre également
	--mais risque de boucle (ON DELETE CASCADE refusé par SQL Server)
	--La suppression des articles vendus par l'utilisateur supprimé sera gérée
	--par la procédure stockée : ps_supprimerUtilisateur( @no_utilisateur )
	ON DELETE no action 
	ON UPDATE no action;

ALTER TABLE ARTICLES_VENDUS ADD CONSTRAINT FK_ARTICLES_UTILISATEURS_ACHETE 
	FOREIGN KEY ( no_acheteur ) REFERENCES UTILISATEURS ( no_utilisateur )
	--Si un utlisateur est supprimé, ses articles achetés ne le sont plus
	ON DELETE SET NULL
	ON UPDATE no action; 

ALTER TABLE ARTICLES_VENDUS ADD CONSTRAINT CK_ARTICLES_DATES 
	-- Vérif que date fin soit bien ultérieure à date début
	CHECK(date_debut_encheres < date_fin_encheres);



-- Table RETRAITS

CREATE TABLE RETRAITS (
	no_article		INTEGER		 		NOT NULL,
	rue						VARCHAR(30) 	NOT NULL,
	code_postal		CHAR(5) 			NOT NULL,
	ville					VARCHAR(30) 	NOT NULL
);

ALTER TABLE RETRAITS ADD CONSTRAINT PK_RETRAITS 
	PRIMARY KEY (no_article);

ALTER TABLE RETRAITS ADD CONSTRAINT FK_RETRAITS_ARTICLES 
	FOREIGN KEY ( no_article ) REFERENCES ARTICLES_VENDUS ( no_article )
	--Si un article est supprimé, son lieu de retrait également
	ON DELETE CASCADE 
	ON UPDATE no action;
 


-- Table ENCHERES
 
CREATE TABLE ENCHERES (
	no_utilisateur		INTEGER 	NOT NULL,
	no_article				INTEGER		NOT NULL,
	date_enchere			DATETIME	NOT NULL,
	montant_enchere		INTEGER		NOT NULL

);

ALTER TABLE ENCHERES ADD CONSTRAINT PK_ENCHERES 
	PRIMARY KEY (no_utilisateur, no_article);
	
ALTER TABLE ENCHERES ADD CONSTRAINT FK_ENCHERES_ARTICLES 
	FOREIGN KEY ( no_article ) REFERENCES ARTICLES_VENDUS ( no_article )
	--Si un article est supprimé, ses enchères également
	ON DELETE CASCADE 
	ON UPDATE no action;

ALTER TABLE ENCHERES ADD CONSTRAINT FK_ENCHERES_UTILISATEURS 
	FOREIGN KEY ( no_utilisateur ) REFERENCES UTILISATEURS ( no_utilisateur )
	--Si un utilisateur est supprimé, ses enchères également
	ON DELETE CASCADE 
	ON UPDATE no action;

