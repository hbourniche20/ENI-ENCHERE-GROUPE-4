package fr.eni.enchere.dal;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import fr.eni.enchere.bo.ArticleVendu;
import fr.eni.enchere.bo.Categorie;
import fr.eni.enchere.bo.Utilisateur;

public class EncheresDaoJdbcImpl implements EncheresDao {

	private final String SELECT_ALL_CATEGORIES = "SELECT no_categorie, libelle FROM CATEGORIES";
	private final String SELECT_ALL_ARTICLES = "SELECT no_article, nom_article, date_fin_encheres, prix_initial, ARTICLES_VENDUS.no_utilisateur, pseudo FROM ARTICLES_VENDUS INNER JOIN UTILISATEURS ON ARTICLES_VENDUS.no_utilisateur = UTILISATEURS.no_utilisateur WHERE date_fin_encheres >= ? ";
	private final String SELECT_ALL_ARTICLES_ALL_FILTERS = "SELECT no_article, nom_article, date_fin_encheres, prix_initial, ARTICLES_VENDUS.no_utilisateur, pseudo FROM ARTICLES_VENDUS INNER JOIN UTILISATEURS ON ARTICLES_VENDUS.no_utilisateur = UTILISATEURS.no_utilisateur WHERE date_fin_encheres >= ? AND nom_article LIKE ? AND no_categorie = ?";
	private final String SELECT_ALL_ARTICLES_FILTER_ONLY_ARTICLE = "SELECT no_article, nom_article, date_fin_encheres, prix_initial, ARTICLES_VENDUS.no_utilisateur, pseudo FROM ARTICLES_VENDUS INNER JOIN UTILISATEURS ON ARTICLES_VENDUS.no_utilisateur = UTILISATEURS.no_utilisateur WHERE date_fin_encheres >= ? AND nom_article LIKE ?";
	private final String SELECT_ALL_ARTICLES_FILTER_ONLY_CATEGORY = "SELECT no_article, nom_article, date_fin_encheres, prix_initial, ARTICLES_VENDUS.no_utilisateur, pseudo FROM ARTICLES_VENDUS INNER JOIN UTILISATEURS ON ARTICLES_VENDUS.no_utilisateur = UTILISATEURS.no_utilisateur WHERE date_fin_encheres >= ? AND no_categorie = ?";

	@Override
	public List<Categorie> selectAllCategories() throws Exception {
		List<Categorie> listeCategories = new ArrayList<>();

		try(Connection con = ConnectionProvider.getConnection()) {
			Statement st  = con.createStatement();
			ResultSet rs = st.executeQuery(SELECT_ALL_CATEGORIES);

			while(rs.next()) {
				Categorie c = new Categorie();
				c.setNoCategorie(rs.getInt(1));
				c.setLibelle(rs.getString(2));
				
				listeCategories.add(c);
			}
			rs.close();
			st.close();
		} catch(SQLException e) {
			throw new Exception("Impossible de récupérer les listes de catégories");
		}
	
		return listeCategories;
	}

	@Override
	public List<ArticleVendu> selectAllArticles(LocalDate date) throws Exception {
		List<ArticleVendu> listeArticles = new ArrayList<>();

		try(Connection con = ConnectionProvider.getConnection()) {
			PreparedStatement st  = con.prepareStatement(SELECT_ALL_ARTICLES);
			st.setDate(1, Date.valueOf(date));
			ResultSet rs = st.executeQuery();

			while(rs.next()) {
				Utilisateur vendeur = new Utilisateur();
				vendeur.setNoUtilisateur(rs.getInt(5));
				vendeur.setPseudo(rs.getString(6));
				
				ArticleVendu article = new ArticleVendu();
				article.setNoArticle(rs.getInt(1));
				article.setNomArticle(rs.getString(2));
				article.setDateFinEncheres(rs.getDate(3).toLocalDate());
				article.setPrixVente(rs.getInt(4));
				article.setVendeur(vendeur);

				listeArticles.add(article);
			}
			rs.close();
			st.close();
		} catch(SQLException e) {
			throw new Exception("Impossible de récupérer la liste des articles");
		}
	
		return listeArticles;
	}

	@Override
	public List<ArticleVendu> selectAllArticlesWithFilters(LocalDate date, String nomArticle, Integer noCategorie) throws Exception {
		List<ArticleVendu> listeArticles = new ArrayList<>();
		
		try(Connection con = ConnectionProvider.getConnection()) {
			
			PreparedStatement st = chooseRequest(con, nomArticle, noCategorie);
			st.setDate(1, Date.valueOf(date));
			ResultSet rs = st.executeQuery();

			while(rs.next()) {
				Utilisateur vendeur = new Utilisateur();
				vendeur.setNoUtilisateur(rs.getInt(5));
				vendeur.setPseudo(rs.getString(6));
				
				ArticleVendu article = new ArticleVendu();
				article.setNoArticle(rs.getInt(1));
				article.setNomArticle(rs.getString(2));
				article.setDateFinEncheres(rs.getDate(3).toLocalDate());
				article.setPrixVente(rs.getInt(4));
				article.setVendeur(vendeur);

				listeArticles.add(article);
			}
			rs.close();
			st.close();
		} catch(SQLException e) {
			System.out.println(e);
			throw new Exception("Impossible de récupérer la liste des articles filtrés");
		}
	
		return listeArticles;
	}

	private PreparedStatement chooseRequest(Connection con, String nomArticle, Integer noCategorie) throws SQLException {
		PreparedStatement st = null;
		String nomRecherche = null;
		
		if(!nomArticle.equals("") && noCategorie == 0) {
			// On demande un article sans choisir de catégorie
			st = con.prepareStatement(SELECT_ALL_ARTICLES_FILTER_ONLY_ARTICLE);
			nomRecherche = '%' + nomArticle + '%';
			st.setString(2, nomRecherche);
		} else if(!nomArticle.equals("") && noCategorie != 0) {
			// On demande un article pour une catégorie choisie
			st  = con.prepareStatement(SELECT_ALL_ARTICLES_ALL_FILTERS);
			nomRecherche = '%' + nomArticle + '%';
			st.setString(2, nomRecherche);
			st.setInt(3, noCategorie);
		} else if (nomArticle.equals("") & noCategorie != 0) {
			// On choisi uniquement une catégorie
			st  = con.prepareStatement(SELECT_ALL_ARTICLES_FILTER_ONLY_CATEGORY);
			st.setInt(2, noCategorie);
		} else {
			// On choisi les articles par défaut
			st  = con.prepareStatement(SELECT_ALL_ARTICLES);
		}
		
		return st;
	}

}
