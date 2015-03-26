package com.llama.tech.doctor.maps;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.URL;
import java.nio.file.Files;

import org.json.JSONArray;
import org.json.JSONObject;

import com.llama.tech.utils.dict.LlamaDict;
import com.llama.tech.utils.dict.LlamaDict.UnhashableTypeException;
import com.opencsv.CSVReader;


public class ConsultaGeografica implements Serializable
{
	private static final String BASE_URL = "http://api.geonames.org/postalCodeLookupJSON?postalcode=%s&country=%s&username=andfoy";
	private static final String BASE_WIKIPEDIA_URL = "http://api.geonames.org/wikipediaSearchJSON?q=%s&maxRows=10&username=andfoy";
	private static final String FILE_PATH = "data/countryPostCodeInfo.csv";
	private String[] searchFields;
	private LlamaDict<String, LlamaDict<String, String>> globalInfo;
	
	public ConsultaGeografica() throws IOException, UnhashableTypeException
	{
		globalInfo = new LlamaDict<String, LlamaDict<String, String>>(20);
		
		
//		File f = new File(getClass().getClassLoader().getResource(FILE_PATH).getFile());
//		
		File f = new File(FILE_PATH);
		if (!f.exists()) {
		    InputStream link = (getClass().getClassLoader().getResourceAsStream(FILE_PATH));
		    Files.copy(link, f.getAbsoluteFile().toPath());
		}
		
		FileReader fr = new FileReader(f);
		CSVReader reader = new CSVReader(fr);
		searchFields = reader.readNext();
		String[] info;
		while((info = reader.readNext()) != null)
		{
			LlamaDict<String, String> d = new LlamaDict<String, String>(10);
		    d.addEntry(searchFields[0], info[0]);
		    for(int i = 2; i < info.length; i++)
		    {
		    	d.addEntry(searchFields[i], info[i]);
		    }
		    globalInfo.addEntry(info[1], d);
		}
		reader.close();
	}
	
    public String coordenadasGeograficasPorCodigoPostal(String zip, String pais) throws IOException
    {
    	String isoCode = globalInfo.getValue(pais).getValue("countryCode"); 
    	System.out.println(String.format(BASE_URL, zip, isoCode));
    	URL url = new URL(String.format(BASE_URL, zip, isoCode));
    	String contents = getJSONFormat(url);
    	JSONObject postalInfo = new JSONObject(contents);
    	JSONArray postalCodes = postalInfo.getJSONArray("postalcodes");
    	JSONObject info = postalCodes.getJSONObject(0);
    	return info.getDouble("lat") +","+info.getDouble("lng");
    	
    }
    
    public String coordenadasGeograficasPorCiudad(String city) throws IOException
    {
    	URL url = new URL(String.format(BASE_WIKIPEDIA_URL, city));
    	String contents = getJSONFormat(url);
    	JSONObject postalInfo = new JSONObject(contents);
    	JSONArray postalCodes = postalInfo.getJSONArray("geonames");
    	JSONObject info = postalCodes.getJSONObject(0);
    	return info.getDouble("lat") +","+info.getDouble("lng");
    	
    }
    
    public String coordenadasGeograficasPorPais(String country) throws IOException
    {
    	return coordenadasGeograficasPorCiudad(country);
    	
    }
    
    private static String getJSONFormat(URL urlReq) throws IOException
	{
    	System.out.println(urlReq);
		StringBuilder sb = new StringBuilder();
		try(BufferedReader in = new BufferedReader(new InputStreamReader(urlReq.openStream())))
		{
			String inputLine;
			while((inputLine = in.readLine()) != null)
			{
				sb.append(inputLine);
			}
		}
		return sb.toString();
	}
	
	public static void main(String...args)
	{
		try {
			ConsultaGeografica c = new ConsultaGeografica();
			System.out.println(c.coordenadasGeograficasPorCodigoPostal("1010", "Austria"));
			System.out.println(c.coordenadasGeograficasPorCiudad("Prague"));
			System.out.println(c.coordenadasGeograficasPorPais("Czech"));
		} catch (IOException | UnhashableTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

