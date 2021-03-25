package it.eng.hybrid.module.pieChart.url;

import it.eng.hybrid.module.pieChart.dataset.PieDataBean;
import it.eng.hybrid.module.pieChart.parameters.ReportParameterBean;
import it.eng.hybrid.module.pieChart.ui.PieChartApplication;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.jfree.chart.urls.CategoryURLGenerator;
import org.jfree.data.category.CategoryDataset;

public class CustomBarUrlGenerator implements UrlGeneratorInterface, CategoryURLGenerator{

	private String level;

	public CustomBarUrlGenerator(String level){
		this.level = level;
	}

	@Override
	public String generateURL(CategoryDataset paramCategoryDataset,
			int paramInt1, int paramInt2) {
		ReportParameterBean lReportParameterBean = PieChartApplication.getParameters();
		String url;
		PieDataBean lPieDataBean =  PieChartApplication.mapValues.get(Integer.valueOf(level)-1).get(paramInt1);
		try {
			url = lReportParameterBean.getPercentageServlet() + "?idUtente=" + lReportParameterBean.getIdUtente() +
			"&idSchema=" + lReportParameterBean.getIdSchema() + "&idDominio=" + lReportParameterBean.getIdDominio() +
			"&level=" + level +
			"&richiesta=" + URLEncoder.encode(lPieDataBean.getIdSoggetto(), "UTF-8") +
			"&da=" + lReportParameterBean.getDa() + "&a=" + lReportParameterBean.getA() + "&tipoReport=" + lReportParameterBean.getTipoReport()+
			"&tipoDiRegistrazione=" + lReportParameterBean.getTipoRegistrazione();
			System.out.println(url);
			return url;
		} catch (UnsupportedEncodingException e1) {
			return null;
		}
	}
	
	public String getLevel() {
		return level;
	}

}
