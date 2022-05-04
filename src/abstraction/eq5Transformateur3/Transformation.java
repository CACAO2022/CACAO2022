package abstraction.eq5Transformateur3;

import abstraction.eq8Romu.filiere.Filiere;
import abstraction.eq8Romu.produits.Chocolat;
import abstraction.eq8Romu.produits.Feve;
import abstraction.eq8Romu.produits.Gamme;

//Karla
public class Transformation extends AcheteurBourse {

	// Payer à la banque
	public boolean payer(double montant) {
		return Filiere.LA_FILIERE.getBanque().virer(Filiere.LA_FILIERE.getActeur("EQ5"), this.cryptogramme, Filiere.LA_FILIERE.getActeur("EQ8"), montant);
	}
	
	// Transformation Classique
	public void transformationClassique (Feve f, double quantité, boolean B) {
		if (stockFeves.getProduitsEnStock().contains(f)) {
			if (stockFeves.getstock(f)-quantité >=0) {
				stockFeves.utiliser(f, quantité);
				stockChocolat.ajouter(Chocolat.get(f.getGamme(),f.isBioEquitable(), B), quantité);
				double montant = quantité * this.coutTransformation.getValeur() ;
				if (B = true) {
					montant += quantité * this.coutOriginal.getValeur();
				}
			payer(montant);
			}
			else {
				double newquantite =  stockFeves.getstock(f);
				stockFeves.utiliser(f, newquantite);
				stockChocolat.ajouter(Chocolat.get(f.getGamme(),f.isBioEquitable(), B), newquantite);
				double montant = newquantite * this.coutTransformation.getValeur() ;
				if (B = true) {
					montant += newquantite * this.coutOriginal.getValeur();
				}
			payer(montant);
			}
		}
	}
	
	// Transformation qui augmente la qualité
	public void transformationUpgrade (Feve f, double quantité, boolean B) {
		double r = this.rendement.getValeur();
		if (stockFeves.getProduitsEnStock().contains(f)) {
			if (stockFeves.getstock(f)-quantité >=0) {
				stockFeves.utiliser(f, quantité);
				stockChocolat.ajouter(Chocolat.get(f.getGamme(),f.isBioEquitable(), B), r*quantité);
				double montant = quantité * this.coutTransformation.getValeur() ;
				if (B = true) {
					montant += quantité * this.coutOriginal.getValeur();
				}
			payer(montant);
			}
			else {
				double newquantite =  stockFeves.getstock(f);
				stockFeves.utiliser(f, newquantite);
				
				if (f.getGamme() == Gamme.BASSE) {
					stockChocolat.ajouter(Chocolat.get(Gamme.MOYENNE,f.isBioEquitable(), B), r*newquantite);
				}
				else {
					stockChocolat.ajouter(Chocolat.get(Gamme.HAUTE,f.isBioEquitable(), B), r*newquantite);
				}
				
				double montant = newquantite * this.coutTransformation.getValeur() ;
				if (B = true) {
					montant += newquantite * this.coutOriginal.getValeur();
				}
			payer(montant);
			}
		}
	}
	
}