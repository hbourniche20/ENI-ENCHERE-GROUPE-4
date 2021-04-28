package fr.eni.enchere.dal;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.eni.enchere.bo.ArticleVendu;
import fr.eni.enchere.bo.Categorie;

import fr.eni.enchere.bo.Enchere;
import fr.eni.enchere.bo.Retrait;
import fr.eni.enchere.bo.Utilisateur;
import fr.eni.enchere.exception.ArticleVenduException;


public class ArticleVenduDaoJdbcImpl implements ArticleVenduDao {
	

	private final String INSERT = "INSERT INTO ARTICLES_VENDUS(NOM_ARTICLE,DESCRIPTION,DATE_DEBUT_ENCHERES,DATE_FIN_ENCHERES,PRIX_INITIAL,NO_CATEGORIE,NO_UTILISATEUR,NO_RETRAIT) VALUES (?,?,?,?,?,?,?,?)";
	
	private final String INSERT_RETRAIT = "INSERT INTO RETRAITS(RUE,CODE_POSTAL,VILLE) VALUES (?,?,?)  ";
	
	private final String UPDATE_ARTICLE = "UPDATE ARTICLES_VENDUS SET NOM_ARTICLE = ?, DESCRIPTION = ?, DATE_DEBUT_ENCHERES = ?, DATE_FIN_ENCHERES = ?, PRIX_INITIAL =?, NO_CATEGORIE = ? , NO_RETRAIT = ? WHERE NO_ARTICLE = ?";
	
	private final String SELECT_ARTICLE_BY_ID = "SELECT ARTICLES_VENDUS.no_article, nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, ARTICLES_VENDUS.no_utilisateur, u1.pseudo, u1.telephone, "
			+ "ARTICLES_VENDUS.no_categorie, libelle, RETRAITS.rue, RETRAITS.code_postal, RETRAITS.ville, ENCHERES.no_utilisateur, u2.pseudo, montant_enchere FROM ARTICLES_VENDUS " 
			+ "INNER JOIN UTILISATEURS u1 ON ARTICLES_VENDUS.no_utilisateur = u1.no_utilisateur " 
			+ "INNER JOIN CATEGORIES ON ARTICLES_VENDUS.no_categorie = CATEGORIES.no_categorie " 
			+ "INNER JOIN RETRAITS  ON ARTICLES_VENDUS.no_retrait = RETRAITS.no_retrait " 
			+ "LEFT JOIN ENCHERES ON ARTICLES_VENDUS.no_article = ENCHERES.no_article "
			+ "LEFT JOIN UTILISATEURS u2 ON ENCHERES.no_utilisateur = u2.no_utilisateur "
			+ "WHERE ARTICLES_VENDUS.no_article = ? ";
	
	private static final String DELETE = "DELETE FROM ARTICLES_VENDUS WHERE no_article = ?";

	@Override
	public void addArticleVendu(ArticleVendu a) throws Exception {
		// TODO Auto-generated method stub
		
		try(Connection c = ConnectionProvider.getConnection()){
			String request = INSERT;
			System.out.println(a.getNoArticle());
			
			try {
			c.setAutoCommit(false);
			
			PreparedStatement pstt = c.prepareStatement(INSERT_RETRAIT,PreparedStatement.RETURN_GENERATED_KEYS);
		
			
			pstt.setString(1, a.getLieuRetrait().getRue());
			pstt.setString(2, a.getLieuRetrait().getCodePostal());
			pstt.setString(3, a.getLieuRetrait().getVille());
			
			pstt.executeUpdate();
			
			ResultSet rs = pstt.getGeneratedKeys();
			
			if(rs.next()){
			
			a.getLieuRetrait().setNoRetrait(rs.getInt(1));
			
			rs.close();
			pstt.close();
			}
			
			
			pstt = c.prepareStatement(request);
			
			pstt.setString(1, a.getNomArticle());
			pstt.setString(2, a.getDescription());
			pstt.setDate(3, Date.valueOf(a.getDateDebutEncheres()));
			pstt.setDate(4, Date.valueOf(a.getDateFinEncheres()));
			pstt.setInt(5, a.getMiseAPrix());
			pstt.setInt(6, a.getCategorieArticle().getNoCategorie());
			pstt.setInt(7, a.getVendeur().getNoUtilisateur());
			pstt.setInt(8, a.getLieuRetrait().getNoRetrait());
					
			pstt.executeUpdate();
			pstt.close();
			
			c.commit();
			System.out.println("Ajout fait !");
			}catch (Exception e ){
				c.rollback();
				throw e;
				
			}
		}catch(SQLException s) {
			s.printStackTrace();
		}	
	}
	

	@Override
	public ArticleVendu selectArticleVenduById(Integer noArticle) throws ArticleVenduException {
		ArticleVendu article = null;
		
		try(Connection con = ConnectionProvider.getConnection()) {
			PreparedStatement st = con.prepareStatement(SELECT_ARTICLE_BY_ID);
			st.setInt(1, noArticle);
			
			ResultSet rs = st.executeQuery();
			while(rs.next()) {
				if(article == null) {
					article = new ArticleVendu();
					article.setNoArticle(rs.getInt(1));
					article.setNomArticle(rs.getString(2));
					article.setDescription(rs.getString(3));
					article.setDateDebutEncheres(rs.getDate(4).toLocalDate());
					article.setDateFinEncheres(rs.getDate(5).toLocalDate());
					article.setMiseAPrix(rs.getInt(6));
					
					Utilisateur vendeur = new Utilisateur();
	 				vendeur.setNoUtilisateur(rs.getInt(7));
	 				vendeur.setPseudo(rs.getString(8));
	 				vendeur.setTelephone(rs.getString(9));
	 				article.setVendeur(vendeur);
	 				
	 				Categorie categorieArticle = new Categorie();
	 				categorieArticle.setNoCategorie(rs.getInt(10));
	 				categorieArticle.setLibelle(rs.getString(11));
	 				article.setCategorieArticle(categorieArticle);
	 				
	 				Retrait lieuRetrait = new Retrait();
	 				lieuRetrait.setRue(rs.getString(12));
	 				lieuRetrait.setCodePostal(rs.getString(13));
	 				lieuRetrait.setVille(rs.getString(14));
	 				article.setLieuRetrait(lieuRetrait);
				}
				
				if(rs.getInt(15) != 0) {
	 				Enchere enchere = new Enchere();
	 				
	 				Utilisateur encherisseur = new Utilisateur();
	 				encherisseur.setNoUtilisateur(rs.getInt(15));
	 				encherisseur.setPseudo(rs.getString(16));
					
	 				enchere.setEncherisseur(encherisseur);
	 				enchere.setMontantEnchere(rs.getInt(17));
					article.getEncheres().add(enchere);
				}
			}
			rs.close();
			st.close();
		} catch(SQLException e) {
			throw new ArticleVenduException();
		}
		
		return article;
	}

	@Override
	public void delete(Integer noArticle) throws ArticleVenduException {
		try(Connection con = ConnectionProvider.getConnection()) {
			PreparedStatement st = con.prepareStatement(DELETE);
			st.setInt(1, noArticle);
			st.executeUpdate();
			st.close();
		} catch(SQLException e) {
			throw new ArticleVenduException(ArticleVenduException.DELETE);
		}
		
	}


	@Override
	public void updateArticleVendu(Integer noArticle) throws ArticleVenduException {
		// TODO Auto-generated method stub
		try(Connection c = ConnectionProvider.getConnection()){
			
			ArticleVendu a = new ArticleVendu();
			
			System.out.println(a.getNoArticle());
			
			try {
			c.setAutoCommit(false);
			
			PreparedStatement pstt = c.prepareStatement(INSERT_RETRAIT,PreparedStatement.RETURN_GENERATED_KEYS);
		
			
			pstt.setString(1, a.getLieuRetrait().getRue());
			pstt.setString(2, a.getLieuRetrait().getCodePostal());
			pstt.setString(3, a.getLieuRetrait().getVille());
			
			pstt.executeUpdate();
			
			ResultSet rs = pstt.getGeneratedKeys();
			
			if(rs.next()){
			
			a.getLieuRetrait().setNoRetrait(rs.getInt(1));
			
			rs.close();
			pstt.close();
			}
			
			
			pstt = c.prepareStatement(UPDATE_ARTICLE);
			
			pstt.setString(1, a.getNomArticle());
			pstt.setString(2, a.getDescription());
			pstt.setDate(3, Date.valueOf(a.getDateDebutEncheres()));
			pstt.setDate(4, Date.valueOf(a.getDateFinEncheres()));
			pstt.setInt(5, a.getMiseAPrix());
			pstt.setInt(6, a.getCategorieArticle().getNoCategorie());
			pstt.setInt(7, a.getVendeur().getNoUtilisateur());
			pstt.setInt(8, a.getLieuRetrait().getNoRetrait());
			pstt.setInt(9, a.getNoArticle());
					
			pstt.executeUpdate();
			pstt.close();
			
			c.commit();
			
			System.out.println("Modification faite !");
			}catch (Exception e ){
				c.rollback();
				throw e;
				
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}	
			
		}
	}


