package it.eng.utility.email.operation.impl.archiveoperation.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import it.eng.aurigamailbusiness.bean.Dizionario;
import it.eng.aurigamailbusiness.bean.TAnagFruitoriCaselleBean;
import it.eng.aurigamailbusiness.bean.TParametersBean;
import it.eng.aurigamailbusiness.bean.TRelCaselleFruitoriBean;
import it.eng.aurigamailbusiness.bean.TValDizionarioBean;
import it.eng.aurigamailbusiness.database.dao.DaoTAnagFruitoriCaselle;
import it.eng.aurigamailbusiness.database.dao.DaoTRelCaselleFruitori;
import it.eng.aurigamailbusiness.database.dao.DaoTValDizionario;
import it.eng.aurigamailbusiness.database.utility.DaoFactory;
import it.eng.aurigamailbusiness.structure.MessageInfos;
import it.eng.core.business.TFilterFetch;
import it.eng.utility.email.process.exception.MailServerException;

public class Utilities {

	public Utilities() {
	}

	private Logger log = LogManager.getLogger(Utilities.class);

	/**
	 * metodo per conoscere la necessità di salvare i destinatari in rubrica
	 * 
	 * @return
	 */
	public boolean salvataggioInRubricaDestinatari(MessageInfos message, Map<String, TParametersBean> parametri) {
		TParametersBean parametroRubrica = parametri.get(Constants.PARAMETER_KEY_INS_DEST_RUBRICA);
		boolean salvataggioInRubrica = false;
		if (parametroRubrica.getStrValue().equalsIgnoreCase(Constants.VALUE_TRUE)
				|| parametroRubrica.getStrValue().equalsIgnoreCase(Constants.VALUE_PARAMETER_IN_INGRESSO)) {
			salvataggioInRubrica = true;
		}
		return salvataggioInRubrica;
	}

	/**
	 * metodo per conoscere la necessità di salvare il mittente in rubrica
	 * 
	 * @return
	 */
	public boolean salvataggioInRubricaMittente(MessageInfos message, Map<String, TParametersBean> parametri) {
		TParametersBean parametroRubrica = parametri.get(Constants.PARAMETER_KEY_INS_MITT_RUBRICA);
		boolean salvataggioInRubrica = false;
		if (parametroRubrica != null && parametroRubrica.getStrValue() != null && parametroRubrica.getStrValue().equalsIgnoreCase(Constants.VALUE_TRUE)) {
			salvataggioInRubrica = true;
		}
		if (parametroRubrica != null && parametroRubrica.getStrValue() != null && message.isPec()
				&& parametroRubrica.getStrValue().equalsIgnoreCase(Constants.VALUE_PARAMETER_PEC)) {
			salvataggioInRubrica = true;
		}
		return salvataggioInRubrica;
	}

	/**
	 * ottengo l'id fruitore AOO della casella di interesse
	 * 
	 * @param idCasella
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String ricavaFruitoreCasella(String idCasella) throws Exception {
		List<TRelCaselleFruitoriBean> listaRelCaselleFruitori = new ArrayList<TRelCaselleFruitoriBean>();
		TFilterFetch<TRelCaselleFruitoriBean> filterFetch = new TFilterFetch<TRelCaselleFruitoriBean>();
		TRelCaselleFruitoriBean filtro = new TRelCaselleFruitoriBean();
		filtro.setIdCasella(idCasella);
		filtro.setFlgAnn(false);
		filterFetch.setFilter(filtro);
		try {
			listaRelCaselleFruitori = DaoFactory.getDao(DaoTRelCaselleFruitori.class).search(filterFetch).getData();
			if (listaRelCaselleFruitori.size() == 0) {
				log.error("Impossibile ricavare il fruitore della casella");
				throw new MailServerException("Impossibile ricavare il fruitore della casella");
			}
			TRelCaselleFruitoriBean relCaselleFruitori = listaRelCaselleFruitori.get(0);
			return restituisceFruitoreCasella(relCaselleFruitori);
		} catch (Exception e) {
			log.error("Impossibile ricavare il fruitore della casella", e);
			throw e;
		}
	}

	/**
	 * ricava il fruitore di tipo di riferimento
	 * 
	 * @param relCaselleFruitori
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String restituisceFruitoreCasella(TRelCaselleFruitoriBean relCaselleFruitori) throws Exception {
		List<TAnagFruitoriCaselleBean> listaFruitori = new ArrayList<TAnagFruitoriCaselleBean>();
		TFilterFetch<TAnagFruitoriCaselleBean> filterFetch = new TFilterFetch<TAnagFruitoriCaselleBean>();
		TAnagFruitoriCaselleBean filtro = new TAnagFruitoriCaselleBean();
		filtro.setIdFruitoreCasella(relCaselleFruitori.getIdFruitoreCasella());
		filtro.setFlgAttivo(true);
		filterFetch.setFilter(filtro);
		try {
			listaFruitori = DaoFactory.getDao(DaoTAnagFruitoriCaselle.class).search(filterFetch).getData();
			if (listaFruitori.size() == 0) {
				log.error("Impossibile ricavare il fruitore della casella");
				throw new MailServerException("Impossibile ricavare il fruitore della casella");
			}
			TAnagFruitoriCaselleBean fruitore = listaFruitori.get(0);
			return fruitore.getIdEnteAOO();
		} catch (Exception e) {
			log.error("Impossibile ricavare il fruitore della casella", e);
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	public TValDizionarioBean ricavaDizionarioDaValore(Dizionario diz) throws Exception {
		TFilterFetch<TValDizionarioBean> ff = new TFilterFetch<TValDizionarioBean>();
		TValDizionarioBean fil = new TValDizionarioBean();
		fil.setValore(diz.getValue());
		ff.setFilter(fil);
		List<TValDizionarioBean> valori = null;
		try {
			valori = DaoFactory.getDao(DaoTValDizionario.class).search(ff).getData();
		} catch (Exception e) {
			log.error("Impossibile ottenere il dizionario desiderato", e);
			throw e;
		}
		if (valori == null || valori.size() == 0 || valori.size() > 1) {
			log.error("Impossibile ottenere il dizionario desiderato");
			throw new MailServerException("Impossibile ottenere il dizionario desiderato");
		}
		return valori.get(0);
	}

}
