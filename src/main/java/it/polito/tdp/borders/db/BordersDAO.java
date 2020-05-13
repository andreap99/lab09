package it.polito.tdp.borders.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import it.polito.tdp.borders.model.Border;
import it.polito.tdp.borders.model.Country;

public class BordersDAO {
	
	Map<Integer, Country> idMap = new HashMap<>();
	
	public List<Country> loadAllCountries() {

		String sql = "SELECT ccode, StateAbb, StateNme FROM country ORDER BY StateAbb";
		List<Country> result = new ArrayList<Country>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Country c = new Country(rs.getInt("CCode"), rs.getString("StateNme"), rs.getString("StateAbb"));
				idMap.put(rs.getInt("CCode"), c);
				result.add(c);
			}
			
			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}

	public List<Border> getCountryPairs(int anno) {

		final String sql = "SELECT * FROM contiguity WHERE year<=? AND conttype=1 AND state1no<state2no";
		List<Border> confini = new ArrayList<>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				confini.add(new Border(rs.getInt("dyad"), idMap.get(rs.getInt("state1no")), idMap.get(rs.getInt("state2no"))));
			}
			
			conn.close();
			return confini;
			
		}catch(SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore db");
		}
	}

	public Set<Country> getConfinanti(Country adesso, int anno) {
		Set<Country> lista = new HashSet<>();
		final String sql = "SELECT * FROM contiguity WHERE state1no=? AND conttype=1 AND year<=?";
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, adesso.getId());
			st.setInt(2, anno);
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				int id = rs.getInt("state2no");
				Country c = idMap.get(id);
				lista.add(c);
			}
			conn.close();
			return lista;
		}catch(SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("ERRORE DB");
		}
		
	}
}
