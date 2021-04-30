-- Suppression des tables --
DROP TABLE ENCHERES;
DROP TABLE ARTICLES_VENDUS;
DROP TABLE CATEGORIES;
DROP TABLE RETRAITS;
DROP TABLE UTILISATEURS;

-- Création des tables --
CREATE TABLE CATEGORIES (
    no_categorie INTEGER IDENTITY(1,1) NOT NULL CONSTRAINT categorie_pk PRIMARY KEY,
    libelle VARCHAR(30) NOT NULL
);

CREATE TABLE RETRAITS (
	no_retrait INTEGER IDENTITY(1,1) NOT NULL CONSTRAINT retrait_pk PRIMARY KEY,
    rue VARCHAR(30) NOT NULL,
    code_postal VARCHAR(15) NOT NULL,
    ville VARCHAR(30) NOT NULL
);

CREATE TABLE UTILISATEURS (
    no_utilisateur INTEGER IDENTITY(1,1) NOT NULL CONSTRAINT utilisateur_pk PRIMARY KEY,
    pseudo VARCHAR(30) NOT NULL,
    nom VARCHAR(30) NOT NULL,
    prenom VARCHAR(30) NOT NULL,
    email VARCHAR(40) NOT NULL,
    telephone VARCHAR(15),
    rue VARCHAR(30) NOT NULL,
    code_postal VARCHAR(10) NOT NULL,
    ville VARCHAR(30) NOT NULL,
    mot_de_passe VARCHAR(30) NOT NULL,
    credit INTEGER NOT NULL,
    administrateur bit NOT NULL
);

CREATE TABLE ARTICLES_VENDUS (
    no_article INTEGER IDENTITY(1,1) NOT NULL CONSTRAINT articles_vendus_pk PRIMARY KEY,
    nom_article VARCHAR(30) NOT NULL,
    description VARCHAR(300) NOT NULL,
	date_debut_encheres DATE NOT NULL,
    date_fin_encheres DATE NOT NULL,
    prix_initial INTEGER,
    prix_vente INTEGER,
    no_utilisateur INTEGER NOT NULL,
    no_categorie INTEGER NOT NULL,
	no_retrait INTEGER NULL
);

CREATE TABLE ENCHERES(	
	no_enchere INTEGER IDENTITY(1,1) NOT NULL CONSTRAINT enchere_pk PRIMARY KEY,
	date_enchere datetime NOT NULL,
	montant_enchere INTEGER NOT NULL,
	no_article INTEGER NOT NULL,
	no_utilisateur INTEGER NOT NULL
 );
 
ALTER TABLE ENCHERES
    ADD CONSTRAINT encheres_utilisateur_fk FOREIGN KEY ( no_utilisateur ) REFERENCES UTILISATEURS ( no_utilisateur )
    ON DELETE RESTRICT
    ON UPDATE NO ACTION;

ALTER TABLE ENCHERES
    ADD CONSTRAINT encheres_no_article_fk FOREIGN KEY ( no_article ) REFERENCES ARTICLES_VENDUS ( no_article )
    ON DELETE RESTRICT
    ON UPDATE NO ACTION ;
	

ALTER TABLE ARTICLES_VENDUS
    ADD CONSTRAINT articles_vendus_retrait_fk FOREIGN KEY ( no_retrait )
        REFERENCES RETRAITS ( no_retrait )
    ON DELETE RESTRICT
    ON UPDATE NO ACTION;

ALTER TABLE ARTICLES_VENDUS
    ADD CONSTRAINT articles_vendus_categories_fk FOREIGN KEY ( no_categorie )
        REFERENCES categories ( no_categorie )
    ON DELETE RESTRICT
    ON UPDATE NO ACTION;

ALTER TABLE ARTICLES_VENDUS
    ADD CONSTRAINT ventes_utilisateur_fk FOREIGN KEY ( no_utilisateur )
        REFERENCES utilisateurs ( no_utilisateur )
    ON DELETE RESTRICT
    ON UPDATE NO ACTION;


-- Jeu de données --
INSERT INTO CATEGORIES (libelle) VALUES
('Informatique'),
('Ameublement'),
('Vêtements'),
('Sport & Loisirs');

INSERT INTO UTILISATEURS(no_utilisateur, pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur) VALUES
(1, 'admin', 'VERNON', 'Thomas', 'thomas.vernon@gmail.com', '0612843522', '12 impasse des maronniers', '44800', 'ST HERBLAIN', 'E*f84T*uB0*', 0, TRUE),
(2, 'Dupond85', 'DUPOND', 'Jean', 'dupond.jean@gmail.com', '0251478510', '152 rue Nationale', '85000', 'LA ROCHE-SUR-YON', 'azertyuiop', 509, FALSE),
(3, 'BricABrac78', 'MARTIN', 'Danielle', 'danielle.martin@yahoo.fr', '0165228544', '45 avenue des Champs-Elysees', '78000', 'PARIS', 'Brocante*78*', 2177, FALSE),
(4, 'Art35', 'RAVENFELD', 'Oceane', 'oceane.rovenfeld@outlook.fr', '0165987425', '3 square des fantassins', '35000', 'RENNES', 'Pfi*euv0*', 1874, FALSE),
(5, 'AngersBrocante', 'FELDMAN', 'Harry', 'harry.feldman@angers-brocante.fr', '0241314510', '162 route de Nantes', '49000', 'ANGERS', 'AngersBr0cante*', 5024, FALSE),
(6, 'Shadow79', 'TALMOND', 'Tatiana', 'tatania.talmond@orange.fr', '0748551482', '89 rue du marais poitevin', '79000', 'NIORT', 'He025ff', 435, FALSE),
(7, 'OccasionRenouveau', 'BLOMET', 'Patrick', 'blomet.patrick@occasion-renouveau.fr', '0615247851', '241 avenue Foch', '37250', 'SORIGNY', 'Tours37Renouveau', 1247, FALSE);


INSERT INTO RETRAITS (no_retrait, rue, code_postal, ville) VALUES
(1, '55 rue du commerce', '85000', 'LA ROCHE-SUR-YON'),
(2, '170 avenue Jean Jaures', '49000', 'ANGERS'),
(3, '162 route de Nantes', '49000', 'ANGERS'),
(4, '14 rue du lavoir', '79100', 'THOUARS');


INSERT INTO ARTICLES_VENDUS(no_article, nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, no_utilisateur, no_categorie, no_retrait) VALUES
(1, 'Fauteuil vintage', 'Fauteuil jaune canard années 70 en très bon état', DATE '2021-03-15', DATE '2021-04-01', 180, 300, 2, 2, 1),      
(2, 'Smartphone Apple Iphone', 'Apple Iphone XR Bleu ciel 64 Go (2018), présence de quelques rayures sur la coque', DATE '2021-03-22', DATE '2021-05-01', 620, NULL, 6, 1, 4),      
(3, 'Veste tendance', 'Veste blazer homme taille L gris avec coudière marron', DATE '2021-04-10', DATE '2021-05-02', 109, NULL, 5, 3, 2),      
(4, 'Vélo eletrique', 'Vélo eletrique femme 14 vitesses à assistance electrique (batterie avec autonomie de 12h)', DATE '2021-04-08', DATE '2021-04-20', 149, 200, 5, 4, 3),      
(5, 'Frigo américain', 'Frigo américain Samsung 2/3 frigo 1/3 congélateur avec distributeur eau & glaçons', DATE '2021-04-17', DATE '2021-05-02', 889, NULL, 6, 2, 4),      
(6, 'PC Asus', 'PC Asus comprenant une tour avec watercooling, ecran 24" et accessoires pour un usage gaming', DATE '2021-04-23', DATE '2021-05-05', 729, NULL, 2, 1, 1);     

        
INSERT INTO ENCHERES(no_enchere, date_enchere, montant_enchere, no_article, no_utilisateur) VALUES
(1, TIMESTAMP '2021-03-15 09:04:37', 180, 1, 7),
(2, TIMESTAMP '2021-03-15 09:04:37', 200, 1, 4),
(3, TIMESTAMP '2021-03-19 18:34:10', 225, 1, 7),
(4, TIMESTAMP '2021-03-22 06:21:42', 620, 2, 7),
(5, TIMESTAMP '2021-03-23 17:52:21', 250, 1, 5),
(6, TIMESTAMP '2021-03-31 14:20:42', 300, 1, 4),
(7, TIMESTAMP '2021-04-02 08:26:45', 630, 2, 2),
(8, TIMESTAMP '2021-04-05 22:09:34', 640, 2, 5),
(9, TIMESTAMP '2021-04-08 13:21:41', 149, 4, 4),
(10, TIMESTAMP '2021-04-09 19:07:51', 160, 4, 2),
(11, TIMESTAMP '2021-04-10 15:20:47', 115, 3, 4),
(12, TIMESTAMP '2021-04-10 20:24:56', 117, 3, 3),
(13, TIMESTAMP '2021-04-11 11:46:23', 170, 4, 6),
(14, TIMESTAMP '2021-04-12 04:13:22', 180, 4, 2),
(15, TIMESTAMP '2021-04-12 09:53:24', 645, 2, 3),
(16, TIMESTAMP '2021-04-13 12:38:25', 120, 3, 6),
(17, TIMESTAMP '2021-04-13 14:03:31', 130, 3, 3),
(18, TIMESTAMP '2021-04-13 16:29:40', 185, 4, 4),
(19, TIMESTAMP '2021-04-14 11:55:08', 190, 4, 6),
(20, TIMESTAMP '2021-04-14 18:34:33', 195, 4, 2),
(21, TIMESTAMP '2021-04-15 09:04:37', 200, 4, 6),
(22, TIMESTAMP '2021-04-15 12:31:05', 132, 3, 4),
(23, TIMESTAMP '2021-04-15 16:42:02', 135, 3, 3),
(24, TIMESTAMP '2021-04-15 21:04:15', 650, 2, 7),
(25, TIMESTAMP '2021-04-17 06:34:18', 989, 5, 3),
(26, TIMESTAMP '2021-04-18 13:40:54', 1000, 5, 5),
(27, TIMESTAMP '2021-04-18 19:37:21', 1049, 5, 7),
(28, TIMESTAMP '2021-04-18 21:04:15', 1081, 5, 3),
(29, TIMESTAMP '2021-04-19 09:21:20', 137, 3, 4),
(30, TIMESTAMP '2021-04-20 11:38:45', 1100, 5, 5),
(31, TIMESTAMP '2021-04-20 15:02:18', 1150, 5, 3),
(32, TIMESTAMP '2021-04-20 19:40:54', 150, 3, 3),
(33, TIMESTAMP '2021-04-21 08:54:51', 1179, 5, 5);