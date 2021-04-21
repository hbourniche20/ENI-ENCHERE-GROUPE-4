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
	//private final String SELECT_ALL_ARTICLES = "SELECT no_article, nom_article, date_fin_encheres, prix_initial, ARTICLES_VENDUS.no_utilisateur, pseudo FROM ARTICLES_VENDUS INNER JOIN UTILISATEURS ON ARTICLES_VENDUS.no_utilisateur = UTILISATEURS.no_utilisateur WHERE date_fin_encheres >= ? ";
	//private final String SELECT_ALL_ARTICLES_ALL_FILTERS = "SELECT no_article, nom_article, date_fin_encheres, prix_initial, ARTICLES_VENDUS.no_utilisateur, pseudo FROM ARTICLES_VENDUS INNER JOIN UTILISATEURS ON ARTICLES_VENDUS.no_utilisateur = UTILISATEURS.no_utilisateur WHERE date_fin_encheres >= ? AND nom_article LIKE ? AND no_categorie = ?";
	//private final String SELECT_ALL_ARTICLES_FILTER_ONLY_ARTICLE = "SELECT no_article, nom_article, date_fin_encheres, prix_initial, ARTICLES_VENDUS.no_utilisateur, pseudo FROM ARTICLES_VENDUS INNER JOIN UTILISATEURS ON ARTICLES_VENDUS.no_utilisateur = UTILISATEURS.no_utilisateur WHERE date_fin_encheres >= ? AND nom_article LIKE ?";
	//private final String SELECT_ALL_ARTICLES_FILTER_ONLY_CATEGORY = "SELECT no_article, nom_article, date_fin_encheres, prix_initial, ARTICLES_VENDUS.no_utilisateur, pseudo FROM ARTICLES_VENDUS INNER JOIN UTILISATEURS ON ARTICLES_VENDUS.no_utilisateur = UTILISATEURS.no_utilisateur WHERE date_fin_encheres >= ? AND no_categorie = ?";

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

	/*@Override
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
	*/
	
	@Override
	public List<ArticleVendu> selectAuctions(LocalDate date, String nomArticle, Integer noCategorie) throws Exception {
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
			throw new Exception("Impossible de récupérer la liste des enchères en cours");
		}
		
		return listeArticles;
	}
	
	

	@Override
	public List<ArticleVendu> selectCurrentAuctions(List<ArticleVendu> listeArticles, LocalDate date, String nomArticle, Integer noCategorie) throws Exception {		
		listeArticles = selectAuctions(date, nomArticle, noCategorie);
	
		return listeArticles;
	}

	@Override
	public List<ArticleVendu> selectMyAuctions(List<ArticleVendu> listeArticles, LocalDate date, String nomArticle,
			Integer noCategorie, Utilisateur utilisateur) throws Exception {
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
			throw new Exception("Impossible de récupérer la liste de mes enchères");
		}
	
		return listeArticles;
	}

	@Override
	public List<ArticleVendu> selectMyWinAuctions(List<ArticleVendu> listeArticles, LocalDate date, String nomArticle,
			Integer noCategorie, Utilisateur utilisateur) throws Exception {
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
			throw new Exception("Impossible de récupérer la liste de mes enchères remportées");
		}
	
		return listeArticles;
	}

	@Override
	public List<ArticleVendu> selectCurrentSales(List<ArticleVendu> listeArticles, LocalDate date, String nomArticle,
			Integer noCategorie, Utilisateur utilisateur) throws Exception {
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
			throw new Exception("Impossible de récupérer la liste de mes ventes en cours");
		}
	
		return listeArticles;
	}

	@Override
	public List<ArticleVendu> selectNotBeginSales(List<ArticleVendu> listeArticles, LocalDate date, String nomArticle,
			Integer noCategorie, Utilisateur utilisateur) throws Exception {
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
			throw new Exception("Impossible de récupérer la liste de mes ventes en cours");
		}
	
		return listeArticles;
	}

	@Override
	public List<ArticleVendu> selectFinishedSales(List<ArticleVendu> listeArticles, LocalDate date, String nomArticle,
			Integer noCategorie, Utilisateur utilisateur) throws Exception {
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
			throw new Exception("Impossible de récupérer la liste de mes ventes en cours");
		}
	
		return listeArticles;
	}

	


}
