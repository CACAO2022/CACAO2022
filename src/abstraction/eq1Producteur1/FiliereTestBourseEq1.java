package abstraction.eq1Producteur1;

import java.util.HashMap;

import abstraction.eq2Producteur2.Producteur2;
import abstraction.eq8Romu.Romu;
import abstraction.eq8Romu.appelsOffres.SuperviseurVentesAO;
import abstraction.eq8Romu.bourseCacao.BourseCacao;
import abstraction.eq8Romu.bourseCacao.ExempleAcheteurBourseCacao;
import abstraction.eq8Romu.bourseCacao.ExempleVendeurBourseCacao;
import abstraction.eq8Romu.clients.ClientFinal;
import abstraction.eq8Romu.contratsCadres.ExempleTransformateurContratCadreVendeurAcheteur;
import abstraction.eq8Romu.contratsCadres.SuperviseurVentesContratCadre;
import abstraction.eq8Romu.filiere.Filiere;
import abstraction.eq8Romu.produits.Chocolat;
import abstraction.eq8Romu.produits.Feve;


//Auteur Global : Khéo basé sur le code de eq8
public class FiliereTestBourseEq1  extends Filiere {
	private static final double DISTRIBUTIONS_ANNUELLES[][] = {
			//Jan1 Jan2 Fev1 Fev2 Mar1 Mar2 Avr1 Avr2 Mai1 Mai2 Jui1 Jui2 Jul1 Jul2 Aou1 Aou2 Sep1 Sep2 Oct1 Oct2 Nov1 Nov2 Dec1 Dec2
			{ 4.5, 4.5, 4.5, 4.5, 4.0, 4.0, 4.0, 4.0, 4.0, 4.0, 4.0, 4.0, 4.0, 4.0, 4.0, 4.0, 4.0, 4.0, 4.0, 4.0, 4.5, 4.5, 4.5, 4.5, },			
			{ 5.5, 5.5, 5.0, 5.0, 4.5, 4.0, 4.0, 4.0, 4.0, 3.5, 3.0, 3.0, 3.0, 3.0, 3.0, 3.0, 3.5, 4.0, 4.0, 4.5, 5.0, 5.0, 5.5, 5.5, },			
			{ 3.5, 3.5, 6.0, 3.5, 3.5, 3.5, 3.5, 3.5, 9.0, 3.5, 3.0, 3.0, 3.0, 3.0, 3.0, 3.0, 3.5, 3.5, 3.5, 3.5, 3.5, 3.5, 9.0, 9.0, },			
			{ 3.0, 3.0, 6.0, 3.0, 3.0, 3.0, 3.0, 3.0, 9.0, 3.0, 3.0, 2.0, 2.0, 2.0, 2.0, 2.0, 3.0, 3.0, 3.0, 3.0, 3.0, 3.0,15.0,15.0, },			
			{ 3.0, 3.0, 7.0, 3.0, 3.0, 3.0, 3.0, 3.0,10.0, 3.0, 3.0, 2.0, 2.0, 2.0, 2.0, 2.0, 3.0, 3.0, 3.0,10.0, 3.0, 3.0,11.0,10.0, },			
			{ 3.0, 3.0,10.0, 3.0, 3.0, 3.0, 3.0, 3.0,12.0, 3.0, 2.0, 1.0, 1.0, 1.0, 1.0, 1.0, 2.0, 3.0, 3.0, 3.0, 3.0, 3.0,15.0,15.0, },			
			{ 3.0, 3.0,11.0, 3.0, 3.0, 3.0, 3.0, 3.0,13.0, 3.0, 2.0, 1.0, 1.0, 1.0, 1.0, 1.0, 2.0, 3.0, 3.0,10.0, 3.0, 3.0,11.0,10.0, },			
	};

	private BourseCacao bourse;
	private SuperviseurVentesContratCadre superviseurCC;
	private SuperviseurVentesAO superviseurAO;

	public FiliereTestBourseEq1() {
		super();
		HashMap<Chocolat, Double> repartitionInitiale = new HashMap<Chocolat, Double>();
		repartitionInitiale.put(Chocolat.HQ_BE_O, 2.5); // Haute Qualite  ,  Bio-Equitable  , Original
		repartitionInitiale.put(Chocolat.HQ_BE,   2.5); // Haute Qualite  ,  Bio-Equitable  , pas Original 
		repartitionInitiale.put(Chocolat.HQ_O,   10.0); // Haute Qualite  ,pas Bio-Equitable, Original
		repartitionInitiale.put(Chocolat.HQ,     15.0); // Haute Qualite  ,pas Bio-Equitable, pas Original 
		repartitionInitiale.put(Chocolat.MQ_BE_O, 2.5); // Moyenne Qualite,  Bio-Equitable  , Original
		repartitionInitiale.put(Chocolat.MQ_BE,   2.5); // Moyenne Qualite,  Bio-Equitable  , pas Original 
		repartitionInitiale.put(Chocolat.MQ_O,   10.0); // Moyenne Qualite,pas Bio-Equitable, Original
		repartitionInitiale.put(Chocolat.MQ,     15.0); // Moyenne Qualite,pas Bio-Equitable, pas Original 
		repartitionInitiale.put(Chocolat.BQ_O,   15.0); // Basse Qualite  ,pas Bio-Equitable, Original
		repartitionInitiale.put(Chocolat.BQ,     25.0); // Basse Qualite  ,pas Bio-Equitable, pas Original 

		ClientFinal  cf = new ClientFinal(7200000000.0 , repartitionInitiale, DISTRIBUTIONS_ANNUELLES);

		this.ajouterActeur(cf);
		
		this.superviseurCC=(new SuperviseurVentesContratCadre());
		this.ajouterActeur(this.superviseurCC);
		this.superviseurAO=new SuperviseurVentesAO();
		this.ajouterActeur(this.superviseurAO);
		
		
		this.ajouterActeur(new ExempleAcheteurBourseCacao(Feve.FEVE_BASSE, 0, 100000));
		this.ajouterActeur(new ExempleAcheteurBourseCacao(Feve.FEVE_MOYENNE, 0, 10000));
		this.ajouterActeur(new ExempleAcheteurBourseCacao(Feve.FEVE_MOYENNE_BIO_EQUITABLE, 0, 100000));
		this.ajouterActeur(new ExempleAcheteurBourseCacao(Feve.FEVE_HAUTE, 0, 25000));
		this.ajouterActeur(new ExempleAcheteurBourseCacao(Feve.FEVE_HAUTE_BIO_EQUITABLE, 0, 205000));
		
		this.ajouterActeur(new ExempleTransformateurContratCadreVendeurAcheteur(Feve.FEVE_BASSE));
		this.ajouterActeur(new ExempleTransformateurContratCadreVendeurAcheteur(Feve.FEVE_MOYENNE));
		this.ajouterActeur(new ExempleTransformateurContratCadreVendeurAcheteur(Feve.FEVE_MOYENNE_BIO_EQUITABLE));
		this.ajouterActeur(new ExempleTransformateurContratCadreVendeurAcheteur(Feve.FEVE_HAUTE));
		this.ajouterActeur(new ExempleTransformateurContratCadreVendeurAcheteur(Feve.FEVE_HAUTE_BIO_EQUITABLE));
		
		
		this.ajouterActeur(new Producteur1());
		this.ajouterActeur(new Producteur2());
		this.bourse=new BourseCacao();
		this.ajouterActeur(this.bourse);

	}
}