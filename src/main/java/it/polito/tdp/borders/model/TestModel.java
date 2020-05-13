package it.polito.tdp.borders.model;

public class TestModel {

	public static void main(String[] args) {

		Model model = new Model();

		System.out.println("TestModel -- TODO");
		model.creaGrafo(2006);
		
		Country paese1 = new Country(910, "Papua New Guinea", "PNG");
		Country paese2 = new Country(325, "Italia", "ITA");
		//System.out.println(model.statiConfinanti(new Country(360, "Romania", "ROM")));
		
		//for(Country c : model.viciniBreadth(paese1))
		//	System.out.println(c);
		
		//for(Country c : model.viciniDepth(paese1))
			//System.out.println(c);
		
		for(Country c : model.viciniIterazione(paese1))
			System.out.println(c);
//		System.out.println("Creo il grafo relativo al 2000");
//		model.createGraph(2000);
		
//		List<Country> countries = model.getCountries();
//		System.out.format("Trovate %d nazioni\n", countries.size());

//		System.out.format("Numero componenti connesse: %d\n", model.getNumberOfConnectedComponents());
		
//		Map<Country, Integer> stats = model.getCountryCounts();
//		for (Country country : stats.keySet())
//			System.out.format("%s %d\n", country, stats.get(country));		
		
	}

}
