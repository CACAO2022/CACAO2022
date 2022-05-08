// julien 27/04

package abstraction.eq5Transformateur3;

import java.util.List;
import java.util.Random;

import abstraction.eq8Romu.contratsCadres.Echeancier;
import abstraction.eq8Romu.contratsCadres.ExemplaireContratCadre;
import abstraction.eq8Romu.contratsCadres.IAcheteurContratCadre;
import abstraction.eq8Romu.contratsCadres.IVendeurContratCadre;
import abstraction.eq8Romu.contratsCadres.SuperviseurVentesContratCadre;
import abstraction.eq8Romu.filiere.Filiere;
import abstraction.eq8Romu.produits.Feve;
import abstraction.eq8Romu.produits.Gamme;

public class AcheteurContrat extends Transformateur3Acteur implements IAcheteurContratCadre {

	//Karla
	/* Initier un contrat */
	public void lanceruncontrat(Feve f) {
		List<IVendeurContratCadre> L =  ((SuperviseurVentesContratCadre)(Filiere.LA_FILIERE.getActeur("Sup.CCadre"))).getVendeurs(f);
		Echeancier e = new Echeancier(Filiere.LA_FILIERE.getEtape()+1, 10, 100); //100 kg de feves sur 10 steps
		if (L.size()!=0) {
			if (L.size()== 1) {
				((SuperviseurVentesContratCadre)(Filiere.LA_FILIERE.getActeur("Sup.CCadre"))).demandeAcheteur((IAcheteurContratCadre)Filiere.LA_FILIERE.getActeur("EQ5"), L.get(0), (Object)f,  e, this.cryptogramme, false);
			}
			else {
				// On choisit aléatoirement un des producteurs
				Random randomizer = new Random();
				IVendeurContratCadre random = L.get(randomizer.nextInt(L.size()));
				((SuperviseurVentesContratCadre)(Filiere.LA_FILIERE.getActeur("Sup.CCadre"))).demandeAcheteur((IAcheteurContratCadre)Filiere.LA_FILIERE.getActeur("EQ5"), random, (Object)f,  e, this.cryptogramme, false);
			}
		}
	}
	
	//Karla
	/* on regarde l etat de nos stocks et on lance la procédure demande 
	acheteur + get vendeur de la classe supperviseur vente cadre */
	public void next() {
		super.next();
		for (Feve f : this.stockFeves.getProduitsEnStock()) {
			if (this.stockFeves.getstock(f) < this.SeuilMinFeves) {
				lanceruncontrat(f);
			}
		}
	}

	// Julien & Karla
	public boolean achete(Object produit) {
		if  (!( produit instanceof Feve) ) {
			return false;
		}
		if (( (Feve) produit).isBioEquitable() && 
				(( (Feve) produit).getGamme()==Gamme.MOYENNE ||
						( (Feve) produit).getGamme()==Gamme.HAUTE)) {
			return true;
		}
		return false;
	}

	// Julien & Karla
	// On accepte tout le temps l'echeancier
	public Echeancier contrePropositionDeLAcheteur(ExemplaireContratCadre contrat) {
		List<Echeancier> listeEcheanciers=contrat.getEcheanciers();
		int l = listeEcheanciers.size();
		return listeEcheanciers.get(l-1);
	}

	// Julien & Karla
	public double contrePropositionPrixAcheteur(ExemplaireContratCadre contrat) {
		double prixT = contrat.getPrix();
		if (prixT < this.seuilMaxAchat) {
			return prixT;
		}
		else {
			double nouveauprix = 0.7*prixT;
			if (nouveauprix< this.seuilMaxAchat) {
				return nouveauprix;
			}
			return 0.0;
		}
	}

	// Julien & Karla
	// on pourra par la suite mettre fin aux autres négociations pour un même produit : on achete tout pour l'instant
	public void notificationNouveauContratCadre(ExemplaireContratCadre contrat) {
	}

	// Julien & Karla
	// si la quantité reçue est inférieure à celle prévue : en acheter à la bourse ?
	public void receptionner(Object produit, double quantite, ExemplaireContratCadre contrat) {
		Feve f= ((Feve) produit);
		this.stockFeves.ajouter(f, quantite);
	}
	
}
	
