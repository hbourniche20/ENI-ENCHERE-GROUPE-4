package fr.eni.enchere.dal;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import fr.eni.enchere.bo.ArticleVendu;
import fr.eni.enchere.bo.Enchere;
import fr.eni.enchere.bo.Utilisateur;
import fr.eni.enchere.exception.EnchereException;

public class EncheresDaoJdbcImpl implements EncheresDao {
	

	private final String INSERT_AUCTION = "INSERT INTO ENCHERES(date_enchere, montant_enchere, no_article, no_utilisateur) VALUES(?, ?, ?, ?)";
		
	private static final String SELECT_USER_BY_NO_AUCTION = "SELECT date_enchere, montant_enchere, UTILISATEURS.no_utilisateur, pseudo, credit FROM ENCHERES"
			+ " INNER JOIN UTILISATEURS on ENCHERES.no_utilisateur = UTILISATEURS.no_utilisateur" 
			+ " WHERE no_enchere = (SELECT MAX(no_enchere) FROM ENCHERES WHERE no_article = ?)";

	private final String SELECT_CURRENT_AUCTIONS = "SELECT no_article, nom_article, date_debut_encheres, date_fin_encheres, prix_initial, ARTICLES_VENDUS.no_utilisateur, pseudo FROM ARTICLES_VENDUS "
			+ "INNER JOIN UTILISATEURS ON ARTICLES_VENDUS.no_utilisateur = UTILISATEURS.no_utilisateur "
			+ "WHERE date_debut_encheres <= ? AND date_fin_encheres > ? AND (? = '' OR nom_article LIKE ?) AND (? = 0 OR no_categorie = ?) ";
	
	private final String SELECT_MY_AUCTIONS = "SELECT no_article, nom_article, date_debut_encheres, date_fin_encheres, prix_initial, ARTICLES_VENDUS.no_utilisateur, pseudo FROM ARTICLES_VENDUS "
			+ "INNER JOIN UTILISATEURS ON ARTICLES_VENDUS.no_utilisateur = UTILISATEURS.no_utilisateur "
			+ "WHERE no_article IN (SELECT no_article FROM ENCHERES WHERE no_utilisateur = ?) AND (? = '' OR nom_article LIKE ?) AND (? = 0 OR no_categorie = ?)";
	
	private final String SELECT_MY_WIN_AUCTIONS = "SELECT ARTICLES_VENDUS.no_article, nom_article, date_debut_encheres, date_fin_encheres, prix_initial, ARTICLES_VENDUS.no_utilisateur, pseudo FROM ENCHERES"
			+ " INNER JOIN ARTICLES_VENDUS ON ENCHERES.no_article = ARTICLES_VENDUS.no_article"
			+ " INNER JOIN UTILISATEURS ON ARTICLES_VENDUS.no_utilisateur = UTILISATEURS.no_utilisateur"
			+ " WHERE no_enchere IN (SELECT MAX(no_enchere) AS no_enchere FROM ENCHERES GROUP BY no_article HAVING MAX(no_enchere))"
			+ " AND ENCHERES.no_utilisateur = ? AND date_fin_encheres < ? AND (? = '' OR ARTICLES_VENDUS.nom_article LIKE ?) AND (? = 0 OR no_categorie = ?)";
	
	private final String SELECT_CURRENT_SALES = "SELECT no_article, nom_article, date_debut_encheres, date_fin_encheres, prix_initial, ARTICLES_VENDUS.no_utilisateur, pseudo FROM ARTICLES_VENDUS "
			+ "INNER JOIN UTILISATEURS ON ARTICLES_VENDUS.no_utilisateur = UTILISATEURS.no_utilisateur "
			+ "WHERE date_debut_encheres <= ? AND date_fin_encheres > ? AND ARTICLES_VENDUS.no_utilisateur = ? AND (? = '' OR nom_article LIKE ?) AND (? = 0 OR no_categorie = ?) ";
	
	private final String SELECT_NOT_BEGIN_SALES = "SELECT no_article, nom_article, date_debut_encheres, date_fin_encheres, prix_initial, ARTICLES_VENDUS.no_utilisateur, pseudo FROM ARTICLES_VENDUS " 
			+ "INNER JOIN UTILISATEURS ON ARTICLES_VENDUS.no_utilisateur = UTILISATEURS.no_utilisateur "
			+ "WHERE date_debut_encheres > ? AND ARTICLES_VENDUS.no_utilisateur = ? AND (? = '' OR nom_article LIKE ?) AND (? = 0 OR no_categorie = ?) ";
	
	private final String SELECT_FINISHED_SALES = "SELECT no_article, nom_article, date_debut_encheres, date_fin_encheres, prix_initial, ARTICLES_VENDUS.no_utilisateur, pseudo FROM ARTICLES_VENDUS " 
			+ "INNER JOIN UTILISATEURS ON ARTICLES_VENDUS.no_utilisateur = UTILISATEURS.no_utilisateur "
			+ "WHERE date_fin_encheres < ? AND ARTICLES_VENDUS.no_utilisateur = ? AND (? = '' OR nom_article LIKE ?) AND (? = 0 OR no_categorie = ?) ";
	
	@Override
	public void insert(Connection con, Enchere enchere) throws EnchereException {
		Integer noEnchere = 0;
		
		try {
			PreparedStatement st = con.prepareStatement(INSERT_AUCTION);
			st.setTimestamp(1, Timestamp.valueOf(enchere.getDateEnchere()));
			st.setInt(2, enchere.getMontantEnchere());
			st.setInt(3, enchere.getArticle().getNoArticle());
			st.setInt(4, enchere.getEncherisseur().getNoUtilisateur());
			st.execute();
			st.close();
		} catch(SQLException e) {
			throw new EnchereException();
		}	
	}
	
	@Override
	public Enchere selectLastAuction(Connection con, Integer noArticle) throws EnchereException {
		Enchere enchere = null;
		
		try {
			PreparedStatement st = con.prepareStatement(SELECT_USER_BY_NO_AUCTION);	
			st.setInt(1, noArticle);
			ResultSet rs = st.executeQuery();
			if(rs.next()) {
				enchere = new Enchere();
				enchere.setDateEnchere(rs.getTimestamp(1).toLocalDateTime());
				enchere.setMontantEnchere(rs.getInt(2));
				Utilisateur u = new Utilisateur();
				u.setNoUtilisateur(rs.getInt(3));
				u.setPseudo(rs.getString(4));
				u.setCredit(rs.getInt(5));
				enchere.setEncherisseur(u);
			}
			rs.close();
			st.close();
		} catch(SQLException e) {
			throw new EnchereException();
		}
		
		return enchere;
	}
	
	
	@Override
	public List<ArticleVendu> selectAuctions(LocalDate date, String nomArticle, Integer noCategorie) throws EnchereException {
		List<ArticleVendu> listeArticles = new ArrayList<>();
		
		try(Connection con = ConnectionProvider.getConnection()) {
			PreparedStatement st = con.prepareStatement(SELECT_CURRENT_AUCTIONS);
			st.setDate(1, Date.valueOf(date));
			st.setDate(2, Date.valueOf(date));
			st.setString(3, nomArticle);
			st.setString(4, '%' + nomArticle + '%');
			st.setInt(5, noCategorie);
			st.setInt(6, noCategorie);
			
			ResultSet rs = st.executeQuery();
			while(rs.next()) {
				Utilisateur vendeur = new Utilisateur();
				vendeur.setNoUtilisateur(rs.getInt(6));
				vendeur.setPseudo(rs.getString(7));
				
				ArticleVendu article = new ArticleVendu();
				article.setNoArticle(rs.getInt(1));
				article.setNomArticle(rs.getString(2));
				article.setDateDebutEncheres(rs.getDate(3).toLocalDate());
				article.setDateFinEncheres(rs.getDate(4).toLocalDate());
				article.setPrixVente(rs.getInt(5));
				article.setVendeur(vendeur);

				listeArticles.add(article);
			}
			rs.close();
			st.close();
		} catch(SQLException e) {
			System.out.println(e);
			throw new EnchereException();
		}
		
		return listeArticles;
	}
	
	@Override
	public List<ArticleVendu> selectCurrentAuctions(List<ArticleVendu> listeArticles, LocalDate date, String nomArticle, Integer noCategorie) throws EnchereException {		
		listeArticles = selectAuctions(date, nomArticle, noCategorie);
	
		return listeArticles;
	}

	@Override
	public List<ArticleVendu> selectMyAuctions(List<ArticleVendu> listeArticles, LocalDate date, String nomArticle,	Integer noCategorie, Utilisateur utilisateur) throws EnchereException {
		try(Connection con = ConnectionProvider.getConnection()) {
			PreparedStatement st = con.prepareStatement(SELECT_MY_AUCTIONS);
			st.setInt(1, utilisateur.getNoUtilisateur());
			st.setString(2, nomArticle);
			st.setString(3, '%' + nomArticle + '%');
			st.setInt(4, noCategorie);
			st.setInt(5, noCategorie);
			
			ResultSet rs = st.executeQuery();
			while(rs.next()) {
				Utilisateur vendeur = new Utilisateur();
				vendeur.setNoUtilisateur(rs.getInt(6));
				vendeur.setPseudo(rs.getString(7));
				
				ArticleVendu article = new ArticleVendu();
				article.setNoArticle(rs.getInt(1));
				article.setNomArticle(rs.getString(2));
				article.setDateDebutEncheres(rs.getDate(3).toLocalDate());
				article.setDateFinEncheres(rs.getDate(4).toLocalDate());
				article.setPrixVente(rs.getInt(5));
				article.setVendeur(vendeur);

				listeArticles.add(article);
			}
			rs.close();
			st.close();
		} catch(SQLException e) {
			System.out.println(e);
			throw new EnchereException("Impossible de récupérer la liste de mes enchères");
		}
	
		return listeArticles;
	}

	@Override
	public List<ArticleVendu> selectMyWinAuctions(List<ArticleVendu> listeArticles, LocalDate date, String nomArticle, Integer noCategorie, Utilisateur utilisateur) throws EnchereException {
		try(Connection con = ConnectionProvider.getConnection()) {
			PreparedStatement st = con.prepareStatement(SELECT_MY_WIN_AUCTIONS);
			st.setInt(1, utilisateur.getNoUtilisateur());
			st.setDate(2, Date.valueOf(date));
			st.setString(3, nomArticle);
			st.setString(4, '%' + nomArticle + '%');
			st.setInt(5, noCategorie);
			st.setInt(6, noCategorie);
			
			ResultSet rs = st.executeQuery();
			while(rs.next()) {
				Utilisateur vendeur = new Utilisateur();
				vendeur.setNoUtilisateur(rs.getInt(6));
				vendeur.setPseudo(rs.getString(7));
				
				ArticleVendu article = new ArticleVendu();
				article.setNoArticle(rs.getInt(1));
				article.setNomArticle(rs.getString(2));
				article.setDateDebutEncheres(rs.getDate(3).toLocalDate());
				article.setDateFinEncheres(rs.getDate(4).toLocalDate());
				article.setPrixVente(rs.getInt(5));
				article.setVendeur(vendeur);

				listeArticles.add(article);
			}
			rs.close();
			st.close();
		} catch(SQLException e) {
			System.out.println(e);
			throw new EnchereException("Impossible de récupérer la liste de mes enchères remportées");
		}
	
		return listeArticles;
	}

	@Override
	public List<ArticleVendu> selectCurrentSales(List<ArticleVendu> listeArticles, LocalDate date, String nomArticle, Integer noCategorie, Utilisateur utilisateur) throws EnchereException {
		try(Connection con = ConnectionProvider.getConnection()) {
			
			PreparedStatement st = con.prepareStatement(SELECT_CURRENT_SALES);
			st.setDate(1, Date.valueOf(date));
			st.setDate(2, Date.valueOf(date));
			st.setInt(3, utilisateur.getNoUtilisateur());
			st.setString(4, nomArticle);
			st.setString(5, '%' + nomArticle + '%');
			st.setInt(6, noCategorie);
			st.setInt(7, noCategorie);
			
			ResultSet rs = st.executeQuery();
			while(rs.next()) {
				Utilisateur vendeur = new Utilisateur();
				vendeur.setNoUtilisateur(rs.getInt(6));
				vendeur.setPseudo(rs.getString(7));
				
				ArticleVendu article = new ArticleVendu();
				article.setNoArticle(rs.getInt(1));
				article.setNomArticle(rs.getString(2));
				article.setDateDebutEncheres(rs.getDate(3).toLocalDate());
				article.setDateFinEncheres(rs.getDate(4).toLocalDate());
				article.setPrixVente(rs.getInt(5));
				article.setVendeur(vendeur);

				listeArticles.add(article);
			}
			rs.close();
			st.close();
		} catch(SQLException e) {
			System.out.println(e);
			throw new EnchereException("Impossible de récupérer la liste de mes ventes en cours");
		}
	
		return listeArticles;
	}

	@Override
	public List<ArticleVendu> selectNotBeginSales(List<ArticleVendu> listeArticles, LocalDate date, String nomArticle, Integer noCategorie, Utilisateur utilisateur) throws EnchereException {
		try(Connection con = ConnectionProvider.getConnection()) {
			PreparedStatement st = con.prepareStatement(SELECT_NOT_BEGIN_SALES);
			st.setDate(1, Date.valueOf(date));
			st.setInt(2, utilisateur.getNoUtilisateur());
			st.setString(3, nomArticle);
			st.setString(4, '%' + nomArticle + '%');
			st.setInt(5, noCategorie);
			st.setInt(6, noCategorie);
			
			ResultSet rs = st.executeQuery();
			while(rs.next()) {
				Utilisateur vendeur = new Utilisateur();
				vendeur.setNoUtilisateur(rs.getInt(6));
				vendeur.setPseudo(rs.getString(7));
				
				ArticleVendu article = new ArticleVendu();
				article.setNoArticle(rs.getInt(1));
				article.setNomArticle(rs.getString(2));
				article.setDateDebutEncheres(rs.getDate(3).toLocalDate());
				article.setDateFinEncheres(rs.getDate(4).toLocalDate());
				article.setPrixVente(rs.getInt(5));
				article.setVendeur(vendeur);

				listeArticles.add(article);
			}
			rs.close();
			st.close();
		} catch(SQLException e) {
			System.out.println(e);
			throw new EnchereException("Impossible de récupérer la liste de mes ventes non débutées");
		}
	
		return listeArticles;
	}

	@Override
	public List<ArticleVendu> selectFinishedSales(List<ArticleVendu> listeArticles, LocalDate date, String nomArticle, Integer noCategorie, Utilisateur utilisateur) throws EnchereException {
		try(Connection con = ConnectionProvider.getConnection()) {
			PreparedStatement st = con.prepareStatement(SELECT_FINISHED_SALES);
			st.setDate(1, Date.valueOf(date));
			st.setInt(2, utilisateur.getNoUtilisateur());
			st.setString(3, nomArticle);
			st.setString(4, '%' + nomArticle + '%');
			st.setInt(5, noCategorie);
			st.setInt(6, noCategorie);
			
			ResultSet rs = st.executeQuery();
			while(rs.next()) {
				Utilisateur vendeur = new Utilisateur();
				vendeur.setNoUtilisateur(rs.getInt(6));
				vendeur.setPseudo(rs.getString(7));
				
				ArticleVendu article = new ArticleVendu();
				article.setNoArticle(rs.getInt(1));
				article.setNomArticle(rs.getString(2));
				article.setDateDebutEncheres(rs.getDate(3).toLocalDate());
				article.setDateFinEncheres(rs.getDate(4).toLocalDate());
				article.setPrixVente(rs.getInt(5));
				article.setVendeur(vendeur);

				listeArticles.add(article);
			}
			rs.close();
			st.close();
		} catch(SQLException e) {
			System.out.println(e);
			throw new EnchereException("Impossible de récupérer la liste de mes ventes terminées");
		}
	
		return listeArticles;
	}

}
