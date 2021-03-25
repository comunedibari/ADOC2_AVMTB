package it.eng.utility.milano.searchemail;

public class SearchMailImpl implements SearchEmail{

	@Override
	public boolean checkIfExistEmail(String idMessage, String address) throws Exception {
		QueryExecutor lQueryExecutor = new QueryExecutor(idMessage, address);
		return lQueryExecutor.executeQuery();
	}

}
