package fr.eni.enchere.dal;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
	
	private final String SELECT_CURRENT_AUCTIONS = "SELECT no_article, nom_article, date_fin_encheres, prix_initial, ARTICLES_VENDUS.no_utilisateur, pseudo FROM ARTICLES_VENDUS "
			+ "INNER JOIN UTILISATEURS ON ARTICLES_VENDUS.no_utilisateur = UTILISATEURS.no_utilisateur "
			+ "WHERE date_debut_encheres <= ? AND date_fin_encheres > ? AND (? = '' OR nom_article LIKE ?) AND (? = 0 OR no_categorie = ?) ";
	
	private final String SELECT_MY_AUCTIONS = "SELECT no_article, nom_article, date_fin_encheres, prix_initial, ARTICLES_VENDUS.no_utilisateur, pseudo FROM ARTICLES_VENDUS "
			+ "INNER JOIN UTILISATEURS ON ARTICLES_VENDUS.no_utilisateur = UTILISATEURS.no_utilisateur "
			+ "WHERE no_article IN (SELECT no_article FROM ENCHERES WHERE no_utilisateur = ?) AND (? = '' OR nom_article LIKE ?) AND (? = 0 OR no_categorie = ?)";
	
	private final String SELECT_MY_WIN_AUCTIONS = "SELECT ARTICLES_VENDUS.no_article, nom_article, date_fin_encheres, prix_initial, ARTICLES_VENDUS.no_utilisateur, pseudo FROM ARTICLES_VENDUS " 
			+ "INNER JOIN UTILISATEURS ON ARTICLES_VENDUS.no_utilisateur = UTILISATEURS.no_utilisateur " 
			+ "INNER JOIN ENCHERES ON ARTICLES_VENDUS.no_article = ENCHERES.no_article " 
			+ "WHERE ENCHERES.no_utilisateur = ? AND date_fin_encheres < ? AND (? = '' OR ARTICLES_VENDUS.nom_article LIKE ?) AND (? = 0 OR no_categorie = ?) " 
			+ "GROUP BY ARTICLES_VENDUS.no_article, nom_article, date_fin_encheres, prix_initial, ARTICLES_VENDUS.no_utilisateur, pseudo HAVING MAX(montant_enchere)";
	
	private final String SELECT_CURRENT_SALES = "SELECT no_article, nom_article, date_fin_encheres, prix_initial, ARTICLES_VENDUS.no_utilisateur, pseudo FROM ARTICLES_VENDUS "
			+ "INNER JOIN UTILISATEURS ON ARTICLES_VENDUS.no_utilisateur = UTILISATEURS.no_utilisateur "
			+ "WHERE date_debut_encheres <= ? AND date_fin_encheres > ? AND ARTICLES_VENDUS.no_utilisateur = ? AND (? = '' OR nom_article LIKE ?) AND (? = 0 OR no_categorie = ?) ";
	
	private final String SELECT_NOT_BEGIN_SALES = "SELECT no_article, nom_article, date_fin_encheres, prix_initial, ARTICLES_VENDUS.no_utilisateur, pseudo FROM ARTICLES_VENDUS " 
			+ "INNER JOIN UTILISATEURS ON ARTICLES_VENDUS.no_utilisateur = UTILISATEURS.no_utilisateur "
			+ "WHERE date_debut_encheres > ? AND ARTICLES_VENDUS.no_utilisateur = ? AND (? = '' OR nom_article LIKE ?) AND (? = 0 OR no_categorie = ?) ";
	
	private final String SELECT_FINISHED_SALES = "SELECT no_article, nom_article, date_fin_encheres, prix_initial, ARTICLES_VENDUS.no_utilisateur, pseudo FROM ARTICLES_VENDUS " 
			+ "INNER JOIN UTILISATEURS ON ARTICLES_VENDUS.no_utilisateur = UTILISATEURS.no_utilisateur "
			+ "WHERE date_fin_encheres < ? AND ARTICLES_VENDUS.no_utilisateur = ? AND (? = '' OR nom_article LIKE ?) AND (? = 0 OR no_categorie = ?) ";
	
	@Override
	public void insert(Enchere enchere) throws EnchereException {
		try(Connection con = ConnectionProvider.getConnection()) {
			PreparedStatement st = con.prepareStatement(INSERT_AUCTION, PreparedStatement.RETURN_GENERATED_KEYS);
			st.setTimestamp(1, Timestamp.valueOf(enchere.getDateEnchere()));
			st.setInt(2, enchere.getMontantEnchere());
			st.setInt(3, enchere.getArticle().getNoArticle());
			st.setInt(4, enchere.getEncherisseur().getNoUtilisateur());
			
			st.executeUpdate();
			
			ResultSet rs = st.getGeneratedKeys();
			
			if(!rs.next()) {
				throw new SQLException();
			}
			rs.close();
			st.close();
		} catch(SQLException e) {
			System.out.println(e);
			throw new EnchereException();
		}	
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
			throw new EnchereException("Impossible de récupérer la liste de mes ventes en cours");
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
			throw new EnchereException("Impossible de récupérer la liste de mes ventes en cours");
		}
	
		return listeArticles;
	}

}
